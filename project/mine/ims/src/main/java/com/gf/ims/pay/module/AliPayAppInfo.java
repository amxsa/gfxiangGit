package com.gf.ims.pay.module;

/**
 * 阿里支付app信息model
 */
public class AliPayAppInfo {
	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 合作者身份(PID)
	 */
	private String pid;
	
	/**
	 * 商家支付宝id
	 */
	private String appid;
	/**
	 * 支付宝商家账户的商户号
	 */
	private String mchId;
	/**
	 * 支付宝商家安全校验码
	 */
	private String apiKey;
	
	/**
	 * 商家自己生成的rsa密钥
	 */
	private String parnerRSA;
	
	/**
	 * 商家自己生成的rsa密钥（PKCS8格式密钥）
	 */
	private String parnerRSAPKCS8;
	
	/**
	 * 商家自己生成的rsa公钥
	 */
	private String parnerPublicRSA;

	public AliPayAppInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AliPayAppInfo(String id, String pid, String appid, String mchId, String apiKey, String parnerRSA,
			String parnerRSAPKCS8, String parnerPublicRSA) {
		super();
		this.id = id;
		this.pid = pid;
		this.appid = appid;
		this.mchId = mchId;
		this.apiKey = apiKey;
		this.parnerRSA = parnerRSA;
		this.parnerRSAPKCS8 = parnerRSAPKCS8;
		this.parnerPublicRSA = parnerPublicRSA;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getParnerRSA() {
		return parnerRSA;
	}

	public void setParnerRSA(String parnerRSA) {
		this.parnerRSA = parnerRSA;
	}

	public String getParnerRSAPKCS8() {
		return parnerRSAPKCS8;
	}

	public void setParnerRSAPKCS8(String parnerRSAPKCS8) {
		this.parnerRSAPKCS8 = parnerRSAPKCS8;
	}

	public String getParnerPublicRSA() {
		return parnerPublicRSA;
	}

	public void setParnerPublicRSA(String parnerPublicRSA) {
		this.parnerPublicRSA = parnerPublicRSA;
	}
	
	
}
