package com.gf.imsCommon.ims.module;

import java.util.Date;
public class Orders implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6740213427425453105L;
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 订单流水号
	 */
	private String orderNo;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 订单总价，单位：元
	 */
	private Double totalPrice;
	/**
	 * 订单类型：
	 */
	private Integer type;
	/**
	 * 支付方式：1支付宝 2货到付款
	 */
	private Integer payMethod;
	/**
	 * 支付状态：0未支付 1已支付
	 */
	private Integer payStatus;
	/**
	 * 订单状态：实物订单（0订单提交->1已支付->2商品出库->3等待收货-4完成）虚拟物品（0订单提交-1已支付-4完成）
	 */
	private Integer status;
	/**
	 * 订单简略信息
	 */
	private String orderInfo;
	/**
	 * 支付系统支付流水号（upp中订单流水号）
	 */
	private String tradeNo;
	/**
	 * 第三方支付流水号（支付宝流水号&微信订单号）
	 */
	private String outTradeNo;
	/**
	 * 买家支付账号（第三方支付平台买家的账号）
	 */
	private String  buyerEmail;
	/**
	 * 订单生成时间
	 */
	private Date createTime;
	/**
	 * 支付完成时间
	 */
	private Date payTime;
	/**
	 * 支付名称
	 */
	private String payName;
	/**
	 * 订单完成时间
	 */
	private Date finishTime;
	/**
	 * 渠道标识：1 web端 2：wap端（无线移动端）3：android 4：ios
	 */
	private Integer channelFlag;
	
	private Integer isRefunded;
	
	
	/**
	 * 店铺id
	 */
	private String  sellerId;
	/**
	 * 店铺名称
	 */
	private String  sellerName;
	/**
	 * 订单备注
	 */
	private String remarks;
	/**
	 * 是否发送短信消息（0：已发送，1：未发送）
	 */
	private Integer sendMessageFlag;
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Orders(String id, String orderNo, String userId, Double totalPrice, Integer type, Integer payMethod,
			Integer payStatus, Integer status, String orderInfo, String tradeNo, String outTradeNo, String buyerEmail,
			Date createTime, Date payTime, String payName, Date finishTime, Integer channelFlag, Integer isRefunded,
			String sellerId, String sellerName, String remarks, Integer sendMessageFlag) {
		super();
		this.id = id;
		this.orderNo = orderNo;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.type = type;
		this.payMethod = payMethod;
		this.payStatus = payStatus;
		this.status = status;
		this.orderInfo = orderInfo;
		this.tradeNo = tradeNo;
		this.outTradeNo = outTradeNo;
		this.buyerEmail = buyerEmail;
		this.createTime = createTime;
		this.payTime = payTime;
		this.payName = payName;
		this.finishTime = finishTime;
		this.channelFlag = channelFlag;
		this.isRefunded = isRefunded;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.remarks = remarks;
		this.sendMessageFlag = sendMessageFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getChannelFlag() {
		return channelFlag;
	}
	public void setChannelFlag(Integer channelFlag) {
		this.channelFlag = channelFlag;
	}
	public Integer getIsRefunded() {
		return isRefunded;
	}
	public void setIsRefunded(Integer isRefunded) {
		this.isRefunded = isRefunded;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getSendMessageFlag() {
		return sendMessageFlag;
	}
	public void setSendMessageFlag(Integer sendMessageFlag) {
		this.sendMessageFlag = sendMessageFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}