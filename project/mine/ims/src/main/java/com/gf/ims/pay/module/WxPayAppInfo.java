package com.gf.ims.pay.module;

/**
 * 微信支付app信息model
 */
public class WxPayAppInfo {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 微信开放平台申请的App的appid
	 */
	private String appid;
	/**
	 * 微信商家账户的商户号
	 */
	private String mchId;
	/**
	 * 微信商家账户设置的API密钥，在商户平台设置
	 */
	private String apiKey;
	public WxPayAppInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WxPayAppInfo(String id, String appid, String mchId, String apiKey) {
		super();
		this.id = id;
		this.appid = appid;
		this.mchId = mchId;
		this.apiKey = apiKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	
}
