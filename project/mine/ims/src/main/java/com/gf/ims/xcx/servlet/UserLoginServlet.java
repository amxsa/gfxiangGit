package com.gf.ims.xcx.servlet;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gf.ims.common.env.Env;
import com.gf.ims.common.util.ServletUtil;
import com.gf.ims.service.RedisCacheService;
import com.gf.ims.xcx.common.WechatTool;

import net.sf.json.JSONObject;

public class UserLoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = -6609873050420278015L;
	Logger log =Logger.getLogger(UserLoginServlet.class);
	private RedisCacheService redisCacheService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		redisCacheService=(RedisCacheService)ctx.getBean("redisCacheService");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//组装响应数据并发送
		exractRspInvoke(req,resp);
	}
	/**
	 * 处理请求
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private synchronized void exractRspInvoke(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String result = "";
		String reason = "";
		//程序完成标志
		boolean endFlag=false;
		JSONObject json=null;
		String threeSession="";
		//html5回调参数
		String callbackName = null;		
		callbackName = req.getParameter("jsoncallback");
		String str = req.getParameter("str");
		String reqStr = "";
		try{
			//客户端请求JSON串
			if(null==str){
				 reqStr = ServletUtil.praseRequst(req);
			}else{
				reqStr  = URLDecoder.decode(str, "UTF-8");
			}
			log.info("reqStr:"+reqStr);
			json= JSONObject.fromObject(reqStr);
		}catch (Exception e) {
			e.printStackTrace();
			endFlag=true;
		}
		
		if(!endFlag){
			try {
				String code = json.getString("code");
				JSONObject jo=WechatTool.code2SessionKey(code);
				log.info("获取session_key微信服务器返回值："+jo.toString());
				String sessionKey=(String) jo.get("session_key");
				String openId=(String) jo.get("openid");
				String key = Env.getUUID();
				StringBuffer sb=new StringBuffer(sessionKey).append("&").append(openId);
				redisCacheService.putStringWithSeconds(key,sb.toString(),(long)2*60*60);
				threeSession=key;
				result="0";
			} catch (Exception e) {
				e.printStackTrace();
				result="1";
				reason = e.getMessage();
			}
			
		}
		//消息响应
		Map resultMap=new HashMap<String,String>();
		resultMap.put("result", result);
		resultMap.put("reason", reason);
		resultMap.put("threeSession", threeSession);
		log.info("respStr:"+JSONObject.fromObject(resultMap).toString());
		//html5回调
		if(null!=callbackName&&!"".equals(callbackName)){
			String renderStr = callbackName+"("+JSONObject.fromObject(resultMap).toString()+")"; 			
			ServletUtil.sendResponse(resp,renderStr);
		}else{
			ServletUtil.sendResponse(resp, JSONObject.fromObject(resultMap).toString());
		}
	}
	
}
