package com.gf.ims.pay.module;

import java.util.Date;

public class TradeOrder {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 支付方式 1 支付宝
	 */
	private Integer payMethod;
	/**
	 * 本系统订单号（唯一性约束）
	 */
	private String orderNo;
	/**
	 * 订单来源标识：1 内部订单 2 合作方订单
	 */
	private Integer orderSourceFlag;
	/**
	 * 合作方编号
	 */
	private String partnerNo;
	/**
	 * 商品名称(长度小于256字符串)
	 */
	private String orderSubject;
	/**
	 * 商品描述（长度小于1000字符串）
	 */
	private String orderBody;
	/**
	 * 订单交易总额 单位为RMB-Yuan。取值范围为[0.01，100000000.00]
	 */
	private Double totalFee;
	/**
	 * 商品展示网址
	 */
	private String showUrl;
	/**
	 * 真实订单号，关联合作方系统订单或本系统内部订单
	 */
	private String outTradeNo;
	/**
	 * 内部订单类型：1 物业维修服务 2 场馆服务 3...
	 */
	private Integer insideOrderType;
	/**
	 * 合作方订单页面跳转同步通知页面路径
	 */
	private String returnUrl;
	/**
	 * 合作方订单服务器异步通知页面路径
	 */
	private String notifyUrl;
	/**
	 * 第三方支付系统交易流水号（支付完成后填写）
	 */
	private String tradeNo;
	/**
	 * 买家第三方支付账号，如买家支付宝账号（支付完成后填写）
	 */
	private String buyerEmail;
	/**
	 * 卖家第三方支付账号，如卖家支付宝账号（支付完成后填写）
	 */
	private String sellerEmail;
	/**
	 * 订单支付状态：0 未支付 1 支付成功
	 */
	private Integer tradeStatus;
	/**
	 * 订单审核状态：0 未审核 1已审核
	 */
	private Integer checkStatus;
	/**
	 * 订单审核状态：0 未审核 1已审核
	 */
	private String checkerAccount;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 订单支付完成时间
	 */
	private Date tradeTime;
	/**
	 * 订单审核时间
	 */
	private Date checkTime;
	/**
	 * 是否有退款记录: 0无退款记录 1 有退款记录
	 */
	private Integer isRefunded;
	/**
	 * 退款记录次数，默认0
	 */
	private Integer refundTimes;
	/**
	 * 剩余金额，如果有退款，小于totle_fee
	 */
	private Double leftFee;
	/**
	 * 渠道标识：1 web端 2：wap端（无线移动端）
	 */
	private Integer channelFlag;
	/**
	 * 默认支付方式:directPay（余额支付）bankPay（网银支付） cartoon（卡通）creditPay（信用支付）CASH（网点支付）
	 */
	private String paymethod;
	/**
	 * 银行简码
	 */
	private String defaultbank;
	/**
	 * 客户端appid（微信支付appid）
	 */
	private String appid;
	public TradeOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TradeOrder(String id, Integer payMethod, String orderNo, Integer orderSourceFlag, String partnerNo,
			String orderSubject, String orderBody, Double totalFee, String showUrl, String outTradeNo,
			Integer insideOrderType, String returnUrl, String notifyUrl, String tradeNo, String buyerEmail,
			String sellerEmail, Integer tradeStatus, Integer checkStatus, String checkerAccount, Date createTime,
			Date tradeTime, Date checkTime, Integer isRefunded, Integer refundTimes, Double leftFee,
			Integer channelFlag, String paymethod, String defaultbank, String appid) {
		super();
		this.id = id;
		this.payMethod = payMethod;
		this.orderNo = orderNo;
		this.orderSourceFlag = orderSourceFlag;
		this.partnerNo = partnerNo;
		this.orderSubject = orderSubject;
		this.orderBody = orderBody;
		this.totalFee = totalFee;
		this.showUrl = showUrl;
		this.outTradeNo = outTradeNo;
		this.insideOrderType = insideOrderType;
		this.returnUrl = returnUrl;
		this.notifyUrl = notifyUrl;
		this.tradeNo = tradeNo;
		this.buyerEmail = buyerEmail;
		this.sellerEmail = sellerEmail;
		this.tradeStatus = tradeStatus;
		this.checkStatus = checkStatus;
		this.checkerAccount = checkerAccount;
		this.createTime = createTime;
		this.tradeTime = tradeTime;
		this.checkTime = checkTime;
		this.isRefunded = isRefunded;
		this.refundTimes = refundTimes;
		this.leftFee = leftFee;
		this.channelFlag = channelFlag;
		this.paymethod = paymethod;
		this.defaultbank = defaultbank;
		this.appid = appid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getOrderSourceFlag() {
		return orderSourceFlag;
	}
	public void setOrderSourceFlag(Integer orderSourceFlag) {
		this.orderSourceFlag = orderSourceFlag;
	}
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}
	public String getOrderSubject() {
		return orderSubject;
	}
	public void setOrderSubject(String orderSubject) {
		this.orderSubject = orderSubject;
	}
	public String getOrderBody() {
		return orderBody;
	}
	public void setOrderBody(String orderBody) {
		this.orderBody = orderBody;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public String getShowUrl() {
		return showUrl;
	}
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Integer getInsideOrderType() {
		return insideOrderType;
	}
	public void setInsideOrderType(Integer insideOrderType) {
		this.insideOrderType = insideOrderType;
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
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public Integer getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getCheckerAccount() {
		return checkerAccount;
	}
	public void setCheckerAccount(String checkerAccount) {
		this.checkerAccount = checkerAccount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Integer getIsRefunded() {
		return isRefunded;
	}
	public void setIsRefunded(Integer isRefunded) {
		this.isRefunded = isRefunded;
	}
	public Integer getRefundTimes() {
		return refundTimes;
	}
	public void setRefundTimes(Integer refundTimes) {
		this.refundTimes = refundTimes;
	}
	public Double getLeftFee() {
		return leftFee;
	}
	public void setLeftFee(Double leftFee) {
		this.leftFee = leftFee;
	}
	public Integer getChannelFlag() {
		return channelFlag;
	}
	public void setChannelFlag(Integer channelFlag) {
		this.channelFlag = channelFlag;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	public String getDefaultbank() {
		return defaultbank;
	}
	public void setDefaultbank(String defaultbank) {
		this.defaultbank = defaultbank;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
}
