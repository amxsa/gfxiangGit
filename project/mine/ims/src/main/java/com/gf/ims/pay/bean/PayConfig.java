package com.gf.ims.pay.bean;
/**
 * 支付配置信息
 */
public class PayConfig {
	/**
	 * 支付伙伴编号
	 */
	private String partnerNo;
	/**
	 * 支付伙伴MD5密钥
	 */
	private String secretKey;
	/**
	 * 支付请求url
	 */
	private String payGateWay;
	/**
	 * 支付结果同步通知页面url
	 */
	private String returnUrl;
	/**
	 * 支付结果服务器异步通知url
	 */
	private String notifyUrl;
	/**
	 * 退款请求url
	 */
	private String refundApplyUrl;
	/**
	 * 退款结果服务器异步通知url
	 */
	private String refundNotifyUrl;
	/**
	 * 获取微信支付预约单信息接口
	 */
	private String payGetWxPrepayidUrl;
	/**
	 * 获取订单微信支付的支付状态接口
	 */
	private String getWxPayOrderStatusUrl;
	
	/**
	 * 获取支付宝快捷支付状态接口
	 */
	private String getAliPayOrderStatusUrl;
	
	/**
	 * 支付宝二维码扫码支付预下单请求接口
	 */
	private String alipayQrcodePrepayOrderUrl;
	
	/**
	 * 支付宝快捷支付请求路径
	 */
	private String payGetAliPrepayidUrl;
	
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getPayGateWay() {
		return payGateWay;
	}
	public void setPayGateWay(String payGateWay) {
		this.payGateWay = payGateWay;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getRefundApplyUrl() {
		return refundApplyUrl;
	}
	public void setRefundApplyUrl(String refundApplyUrl) {
		this.refundApplyUrl = refundApplyUrl;
	}
	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}
	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}
	public String getPayGetWxPrepayidUrl() {
		return payGetWxPrepayidUrl;
	}
	public void setPayGetWxPrepayidUrl(String payGetWxPrepayidUrl) {
		this.payGetWxPrepayidUrl = payGetWxPrepayidUrl;
	}
	public String getGetWxPayOrderStatusUrl() {
		return getWxPayOrderStatusUrl;
	}
	public void setGetWxPayOrderStatusUrl(String getWxPayOrderStatusUrl) {
		this.getWxPayOrderStatusUrl = getWxPayOrderStatusUrl;
	}
	public String getAlipayQrcodePrepayOrderUrl() {
		return alipayQrcodePrepayOrderUrl;
	}
	public void setAlipayQrcodePrepayOrderUrl(String alipayQrcodePrepayOrderUrl) {
		this.alipayQrcodePrepayOrderUrl = alipayQrcodePrepayOrderUrl;
	}
	public String getPayGetAliPrepayidUrl() {
		return payGetAliPrepayidUrl;
	}
	public void setPayGetAliPrepayidUrl(String payGetAliPrepayidUrl) {
		this.payGetAliPrepayidUrl = payGetAliPrepayidUrl;
	}
	public String getGetAliPayOrderStatusUrl() {
		return getAliPayOrderStatusUrl;
	}
	public void setGetAliPayOrderStatusUrl(String getAliPayOrderStatusUrl) {
		this.getAliPayOrderStatusUrl = getAliPayOrderStatusUrl;
	}
	
}
