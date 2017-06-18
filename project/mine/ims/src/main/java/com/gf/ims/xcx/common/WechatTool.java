package com.gf.ims.xcx.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gf.ims.service.RedisCacheService;
import com.gf.ims.xcx.po.UserInfo;

import net.sf.json.JSONObject;


public class WechatTool {
	private static final Log log= LogFactory.getLog(WechatTool.class);
	/**
	 * 将微信自动回复的信息转成Map
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws IOException, DocumentException {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		if (document != null) {
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			if (elementList != null) {
				for (Element e : elementList)
					map.put(e.getName(), e.getText());
			}
		}
		if (inputStream != null) {
			// 释放资源
			inputStream.close();
		}
		inputStream = null;

		return map;
	}

	/**
	 * 获取AppId
	 * 
	 * @return
	 */
	public static String getAppId() {
		return ConfLoad.getProperty("WECHAT_APPID");
	}

	/**
	 * 获取AppSecret
	 * 
	 * @return
	 */
	public static String getAppSecret() {
		return ConfLoad.getProperty("WECHAT_APPSECRET");
	}

	/**
	 * 获取全局Token
	 * 
	 * @return
	 */
	public static String getToken() {
//		if(Env.ACCESS_TOKEN_LIMITE_TIME.size()==0){
//			JSONObject json = getTokenJson();
//			if (json != null && json.containsKey("access_token")) {
//				String accessToken = json.getString("access_token");
//				Env.ACCESS_TOKEN_LIMITE_TIME.put("ACCESS_TOKEN", new Object[]{accessToken,DateTool.diff(new Date(), "HOUR", 1)});
//				return accessToken;
//			}
//		}else{
//			Object[] obj = Env.ACCESS_TOKEN_LIMITE_TIME.get("ACCESS_TOKEN");
//			String accessToken =String.valueOf(obj[0]) ;
//			Date limiteDate = (Date)obj[1];
//			if(limiteDate.after(new Date())){
//				return accessToken;
//			}else{
//				JSONObject json = getTokenJson();
//				if (json != null && json.containsKey("access_token")) {
//					accessToken = json.getString("access_token");
//					Env.ACCESS_TOKEN_LIMITE_TIME.put("ACCESS_TOKEN", new Object[]{accessToken,DateTool.diff(new Date(), "HOUR", 1)});
//					return accessToken;
//				}
//			}
//		}
		return null;
	}
	
	private static  JSONObject getTokenJson(){
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
		url.append(getAppId()).append("&secret=").append(getAppSecret());
		
		String result = HttpUrlClient.doGet("微信获取全局Token", url.toString(),
				null, "UTF-8", 8000, true);
		if (result != null) {
			JSONObject obj = JSONObject.fromObject(result);
//			if (obj != null && obj.containsKey("access_token")) {
//				return obj.getString("access_token");
//			}
			return obj;
		}
		return null;
	}
	
	

	/**
	 * 构造Oauth code
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getOauthCode(String redirectUri) {
		StringBuilder str=  new StringBuilder();
		str.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
		str.append(getAppId()).append("&redirect_uri=").append(URLEncoder.encode(redirectUri));
		str.append("&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
		return str.toString();
	}
	
	public String getCode2openid(String code){
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
		url.append(getAppId()).append("&secret=").append(getAppSecret()).append("&code=");
		url.append(code).append("&grant_type=authorization_code");
		String result = HttpUrlClient.doGet("微信菜单直接获取openid", url.toString(),
				null, "UTF-8", 8000, true);
		if (result != null) {
			JSONObject obj = JSONObject.fromObject(result);
			if(obj!=null){
				return obj.getString("openid");
			}
		}
		return null;
	}

	/**
	 * 刷新 access_token
	 * 
	 * @param refreshToken
	 */
	public String getRefreshToken(String refreshToken) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("appid", getAppId());
		params.put("grant_type", "refresh_token");
		params.put("refresh_token", refreshToken);
		String result = HttpUrlClient.doGet("微信刷新Token",
				"https://api.weixin.qq.com/sns/oauth2/refresh_token?", params,
				"UTF-8", 8000, true);
		return result;
	}

	
	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public static UserInfo getUserinfo(String accessToken, String openid){
		if (accessToken != null&&StringUtils.isNotBlank(openid)) {
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("access_token", accessToken);
			params.put("openid", openid);
			StringBuffer url = new StringBuffer();
			url.append("https://api.weixin.qq.com/cgi-bin/user/info?");
			String result = HttpUrlClient.doGet("微信获取关注者信息", url.toString(),
					params, "UTF-8", 8000, true);
			if (result != null) {
				JSONObject obj = JSONObject.fromObject(result);
				log.info(obj.toString());
				if (obj != null) {
					if (obj.get("errcode") != null) {
						return null;
					}
					UserInfo user = (UserInfo) JSONObject.toBean(obj,
							UserInfo.class);
					return user;
				}
			}
		}
		return null;
	}

	/**
	 * 获取帐号的关注者列表
	 * 
	 * @param accessToken
	 * @param nextOpenid
	 * @return
	 */
	public JSONObject getFollwersList(String accessToken, String nextOpenid)
			throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("next_openid", nextOpenid);
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/user/get?");
		String result = HttpUrlClient.doGet("微信获取关注者信息", url.toString(),
				params, "UTF-8", 8000, true);
		if (result != null) {
			JSONObject obj = JSONObject.fromObject(result);
			if (obj != null) {
				if (obj.get("errcode") != null) {
					throw new Exception(obj.getString("errmsg"));
				}
				return obj;
			}
		}
		return null;

	}

	public static void main(String[] args) {
		HttpUrlClient client = new HttpUrlClient();
		String url ="http://t.gd118114.cn/user/wechat/refreshToken.do?";
		String result = client.doPost("", url, null, "utf-8", 10000, false);
		System.out.println(result);
	}

	/**
	 * 
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public static JSONObject code2SessionKey(String code) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("appid", ConfLoad.getProperty("APP_ID"));
		params.put("secret", ConfLoad.getProperty("SECRET_ID"));
		params.put("js_code", code);
		params.put("grant_type", ConfLoad.getProperty("GRANT_TYPE"));
		StringBuffer url = new StringBuffer();
		url.append(ConfLoad.getProperty("SESSION_URL")).append("?");
		String result = HttpUrlClient.doGet("获取session_key",url.toString(),params, "UTF-8", 8000, true);
		if (result != null) {
			JSONObject obj = JSONObject.fromObject(result);
			if (obj != null) {
				if (obj.get("errcode") != null) {
					throw new Exception(obj.getString("errmsg"));
				}
				return obj;
			}
		}
		return null;
	}

	public static  boolean checkSession(String threeSession,RedisCacheService redisCacheService){
		String userSession = redisCacheService.get(threeSession);
		if (StringUtils.isNotBlank(userSession)) {
			return true;
		}
		return false;
	}
	
	public static String getSessionKey(String threeSession,RedisCacheService redisCacheService){
		String userSession = redisCacheService.get(threeSession);
		String sessionKey = userSession.substring(0, userSession.lastIndexOf("&"));
		return sessionKey;
	}
	public static String getOpenId(String threeSession,RedisCacheService redisCacheService){
		String userSession = redisCacheService.get(threeSession);
		String openId = userSession.substring(userSession.lastIndexOf("&")+1);
		return openId;
	}
}
