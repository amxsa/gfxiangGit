
package cn.cellcom.czt.wechat;

public class WeChatBuyPost {

	private String openId;			// 支付该笔订单的用户 ID
	private String appId;			// 公众号 id
	private int isSubscribe;		// 用户是否关注了公众号。1 为关注，0 为未关注
	private long timeStamp;			// 时间戳
	private String nonceStr;		// 随机字符串；字段来源：商户生成的随机字符
	private String appSignature;	// 字段名称：签名
	private String signMethod;		// SHA1
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(int isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getAppSignature() {
		return appSignature;
	}
	public void setAppSignature(String appSignature) {
		this.appSignature = appSignature;
	}
	public String getSignMethod() {
		return signMethod;
	}
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	

	
}