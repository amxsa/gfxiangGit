package com.gf.ims.pay.module;

import java.util.Date;
public class TradeRefundApply {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 支付方式 1 支付宝 2货到付款
	 */
	private Integer payMethod;
	/**
	 * 订单来源标识：1 内部订单 2 合作方订单
	 */
	private Integer orderSourceFlag;
	/**
	 * 退款申请单号(来自合作方)
	 */
	private String refundApplyNo;
	/**
	 * 支付宝交易流水号（支付完成后填写）
	 */
	private String tradeNo;
	/**
	 * 真实订单号，关联合作方系统订单或本系统内部订单
	 */
	private String out_tradeNo;
	/**
	 * 本系统订单号
	 */
	private String orderNo;
	/**
	 * 退款批次表id
	 */
	private String refundId;
	/**
	 * 退款批次号
	 */
	private String batchNo;
	/**
	 * 合作方编号
	 */
	private String partnerNo;
	/**
	 * 商品名称(长度小于128字符串)
	 */
	private String orderSubject;
	/**
	 * 退款金额，单位元
	 */
	private Double refundFee;
	/**
	 * 退款理由
	 */
	private String refundReason;
	/**
	 * 消费者登录名
	 */
	private String userAccount;
	/**
	 * 商家管理员登录名
	 */
	private String sellerAccount;
	/**
	 * 退款时间(合作方传递参数)
	 */
	private String refundTimeStr;
	/**
	 * 合作方订单服务器异步通知页面路径
	 */
	private String notifyUrl;
	/**
	 * 合作方订单服务器异步通知页面路径
	 */
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 退款结束时间（退款结束时填写）
	 */
	private Date refundFinishTime;
	/**
	 * 操作者第一次阅读时间
	 */
	private Date readTime;
	/**
	 * 阅读状态：0 未阅 1已阅
	 */
	private Integer readStatus;
	/**
	 * 退款状态：0 未退款 1 退款成功 2退款失败
	 */
	private Integer refundStatus;
	/**
	 * 退款失败错误代码（退款失败时填写）
	 */
	private String errorCode;
	/**
	 * 退款失败错误代码含义（退款失败时填写）
	 */
	private String errorCodeDesc;
	/**
	 * 操作者账号
	 */
	private String operatorAccount;
	public TradeRefundApply() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TradeRefundApply(String id, Integer payMethod, Integer orderSourceFlag, String refundApplyNo, String tradeNo,
			String out_tradeNo, String orderNo, String refundId, String batchNo, String partnerNo, String orderSubject,
			Double refundFee, String refundReason, String userAccount, String sellerAccount, String refundTimeStr,
			String notifyUrl, Date createTime, Date refundFinishTime, Date readTime, Integer readStatus,
			Integer refundStatus, String errorCode, String errorCodeDesc, String operatorAccount) {
		super();
		this.id = id;
		this.payMethod = payMethod;
		this.orderSourceFlag = orderSourceFlag;
		this.refundApplyNo = refundApplyNo;
		this.tradeNo = tradeNo;
		this.out_tradeNo = out_tradeNo;
		this.orderNo = orderNo;
		this.refundId = refundId;
		this.batchNo = batchNo;
		this.partnerNo = partnerNo;
		this.orderSubject = orderSubject;
		this.refundFee = refundFee;
		this.refundReason = refundReason;
		this.userAccount = userAccount;
		this.sellerAccount = sellerAccount;
		this.refundTimeStr = refundTimeStr;
		this.notifyUrl = notifyUrl;
		this.createTime = createTime;
		this.refundFinishTime = refundFinishTime;
		this.readTime = readTime;
		this.readStatus = readStatus;
		this.refundStatus = refundStatus;
		this.errorCode = errorCode;
		this.errorCodeDesc = errorCodeDesc;
		this.operatorAccount = operatorAccount;
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
	public Integer getOrderSourceFlag() {
		return orderSourceFlag;
	}
	public void setOrderSourceFlag(Integer orderSourceFlag) {
		this.orderSourceFlag = orderSourceFlag;
	}
	public String getRefundApplyNo() {
		return refundApplyNo;
	}
	public void setRefundApplyNo(String refundApplyNo) {
		this.refundApplyNo = refundApplyNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOut_tradeNo() {
		return out_tradeNo;
	}
	public void setOut_tradeNo(String out_tradeNo) {
		this.out_tradeNo = out_tradeNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	public Double getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getSellerAccount() {
		return sellerAccount;
	}
	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}
	public String getRefundTimeStr() {
		return refundTimeStr;
	}
	public void setRefundTimeStr(String refundTimeStr) {
		this.refundTimeStr = refundTimeStr;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getRefundFinishTime() {
		return refundFinishTime;
	}
	public void setRefundFinishTime(Date refundFinishTime) {
		this.refundFinishTime = refundFinishTime;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	public Integer getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorCodeDesc() {
		return errorCodeDesc;
	}
	public void setErrorCodeDesc(String errorCodeDesc) {
		this.errorCodeDesc = errorCodeDesc;
	}
	public String getOperatorAccount() {
		return operatorAccount;
	}
	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}
	
}
