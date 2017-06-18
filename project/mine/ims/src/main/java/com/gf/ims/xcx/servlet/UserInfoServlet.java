package com.gf.ims.xcx.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gf.ims.common.util.PKCS7Encoder;
import com.gf.ims.common.util.ServletUtil;
import com.gf.ims.service.RedisCacheService;
import com.gf.ims.xcx.common.WechatTool;

import net.sf.json.JSONObject;

public class UserInfoServlet extends HttpServlet {

	private static final long serialVersionUID = -6609873050420278015L;
	Logger log = Logger.getLogger(UserInfoServlet.class);
	private RedisCacheService redisCacheService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		redisCacheService = (RedisCacheService) ctx.getBean("redisCacheService");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 组装响应数据并发送
		exractRspInvoke(req, resp);
	}

	/**
	 * 处理请求
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private synchronized void exractRspInvoke(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String result = "";
		String reason = "";
		JSONObject json = null;
		// 程序完成标志
		boolean endFlag = false;
		String encryptedData = "";// 包括敏感数据在内的完整用户信息的加密数据
		String iv = "";// 使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
		String threeSession = "";// 第三方存储的sessionid
		// html5回调参数
		String callbackName = null;
		callbackName = req.getParameter("jsoncallback");
		String str = req.getParameter("str");
		String reqStr = "";
		String session="";
		try {
			// 客户端请求JSON串
			if (null == str) {
				reqStr = ServletUtil.praseRequst(req);
			} else {
				reqStr = URLDecoder.decode(str, "UTF-8");
			}
			log.info("reqStr:" + reqStr);
			json = JSONObject.fromObject(reqStr);
			threeSession = json.getString("threeSession");
			if (threeSession!=null) {
				session = redisCacheService.get(threeSession);
				if (StringUtils.isBlank(session)) {
					throw new RuntimeException("第三方session:"+threeSession+"过期");
				}
			}else{
				throw new RuntimeException("第三方session:"+threeSession+"过期");
			}
			encryptedData = json.getString("encryptedData");
			iv = json.getString("iv");
		} catch (Exception e) {
			e.printStackTrace();
			result="1";
			reason=e.getMessage();
			endFlag = true;
		}

		if (!endFlag) {
			try {
				boolean online = WechatTool.checkSession(threeSession, redisCacheService);
				if (online) {
					String sessionKey = WechatTool.getSessionKey(threeSession, redisCacheService);
					System.out.println("sessionKey--------------------"+sessionKey);
					String s = decrypt(encryptedData, sessionKey, iv, "UTF-8");
					System.out.println(s);
				}
				result = "0";
			} catch (Exception e) {
				e.printStackTrace();
				result = "1";
				reason = e.getMessage();
			}

		}
		// 消息响应
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		resultMap.put("reason", reason);
		resultMap.put("threeSession", threeSession);
		log.info("respStr:" + JSONObject.fromObject(resultMap).toString());
		// html5回调
		if (null != callbackName && !"".equals(callbackName)) {
			String renderStr = callbackName + "(" + JSONObject.fromObject(resultMap).toString() + ")";
			ServletUtil.sendResponse(resp, renderStr);
		} else {
			ServletUtil.sendResponse(resp, JSONObject.fromObject(resultMap).toString());
		}
	}

	public String decrypt(String encryptedData, String sessionKey, String iv, String encodingFormat) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

			byte[] _encryptedData = Base64.decodeBase64(encryptedData);
			byte[] _sessionKey = Base64.decodeBase64(sessionKey);
			byte[] _iv = Base64.decodeBase64(iv);
			SecretKeySpec secretKeySpec = new SecretKeySpec(_sessionKey, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] original = cipher.doFinal(_encryptedData);
			
			byte[] bytes = PKCS7Encoder.decode(original);
			String originalString = new String(bytes, "UTF-8");
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
