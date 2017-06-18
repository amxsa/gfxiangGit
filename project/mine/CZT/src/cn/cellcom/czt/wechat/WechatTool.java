package cn.cellcom.czt.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.encrypt.MD5;

import cn.cellcom.czt.common.ConfLoad;

public class WechatTool {
	private static final Log log = LogFactory.getLog(WechatTool.class);

	/**
	 * 获取AppId
	 * 
	 * @return
	 */
	public String getAppId() {
		return ConfLoad.getProperty("WECHAT_APPID");
	}

	/**
	 * 获取AppSecret
	 * 
	 * @return
	 */
	public String getAppSecret() {
		return ConfLoad.getProperty("WECHAT_APPSECRET");
	}

	public String getToken() {
		JSONObject json = getTokenJson();
		if (json != null && json.containsKey("access_token")) {
			String accessToken = json.getString("access_token");
			return accessToken;
		}
		return null;
	}

	private JSONObject getTokenJson() {

		String merchid = ConfLoad.getProperty("WECHAT_SERVICE_NO");
		long stamp = System.currentTimeMillis();
		String key = ConfLoad.getProperty("ACCESS_TOKEN_KEY");
		String vcode = MD5.getMD5((merchid + stamp + key).getBytes());
		StringBuffer url = new StringBuffer(
				ConfLoad.getProperty("ACCESS_TOKEN_URL"));
		url.append("api/weixin/token?merchid=").append(merchid)
				.append("&stamp=").append(stamp);
		url.append("&vcode=").append(vcode);
		String result = HttpUrlClient.doGet("微信获取全局Token", url.toString(),
				null, "UTF-8", 8000, true);
		log.info("微信获取全局Token结果:" + result);
		if (result != null) {
			JSONObject obj = JSONObject.fromObject(result);
			if (obj.containsKey("token")) {
				return obj.getJSONObject("token");
			}
		}
		return null;
	}

	public static String getTokenId() {
		String tokenId = null;

		LoginParam login = new LoginParam();
		login.setPassword("API!@#890");
		login.setAccount("api_weixin");
		login.setAccountType("1");

		JSONObject obj = JSONObject.fromObject(login);
		JSONObject resp = HttpUtils.post(ConfLoad.getProperty("WECHAT_PAY_URL")
				+ "api/sys/login", obj);
		if (null != resp) {
			tokenId = resp.getString("tokenId");
			System.out.println("获取当前tokenId:" + tokenId);
		}
		return tokenId;
	}

	public JSONObject getSign(String url) {
		String tokenId = getTokenId();
		SDKsignParam sign = new SDKsignParam();
		sign.setTokenId(tokenId);
		sign.setUrl(url);

		JSONObject obj = JSONObject.fromObject(sign);
		JSONObject resp = HttpUtils.post(ConfLoad.getProperty("WECHAT_PAY_URL")
				+ "api/weixinCoupon/get_wx_js_sdk_sign", obj);
		if (null != resp) {
			System.out.println(resp.toString());
			return resp;

		}
		return null;

	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserinfo(String accessToken, String openid)
			throws Exception {
		if (accessToken != null) {
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("access_token", accessToken);
			params.put("openid", openid);
			StringBuffer url = new StringBuffer();
			url.append("https://api.weixin.qq.com/cgi-bin/user/info?");
			url.append("access_token=").append(accessToken).append("&openid=")
					.append(openid);
			String result = HttpUrlClient.doGet("微信获取关注者信息", url.toString(),
					params, "UTF-8", 8000, true);
			log.info(openid + "微信获取关注者信息结果:" + result);
			if (result != null) {
				JSONObject obj = JSONObject.fromObject(result);
				if (obj != null) {
					if (obj.get("errcode") != null) {
						throw new Exception(obj.getString("errmsg"));
					}
					UserInfo user = (UserInfo) JSONObject.toBean(obj,
							UserInfo.class);
					return user;
				}
			}
		}
		return null;
	}

	public static final String inputStream2String(InputStream in)
			throws UnsupportedEncodingException, IOException {
		if (in == null)
			return "";

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}

	public static void main(String[] args) throws Exception {
		WechatTool wechatTool = new WechatTool();
		System.out.println(wechatTool.getToken());
		System.out.println(wechatTool.getUserinfo(wechatTool.getToken(),
				"okU2Tjjidurcjd-peWw0PCnLtppo"));

	}

}
