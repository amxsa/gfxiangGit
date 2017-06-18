package com.gf.ims.pay.module;

import java.util.Date;
public class TradeRefund {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 退款批次号（唯一性约束）
	 */
	private String batchNo; 
	/**
	 * 申请退款的总笔数
	 */
	private Integer batchNum;
	/**
	 * 申请退款单笔数据集
	 */
	private String detailData;
	/**
	 * 退款成功总数（退款完成后填写）
	 */
	private Integer successNum;
	/**
	 * 退款结果明细（退款完成后填写）
	 */
	private String resultDetails;
	/**
	 * 退款状态：0 未完成 1已经完成
	 */
	private Integer status;
	/**
	 * 操作者账号
	 */
	private String opratorAccount;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 退款结果反馈时间（退款完成后填写）
	 */
	private Date finishTime;
	/**
	 * 商家支付宝账号
	 */
	private String sellerEmail;
	public TradeRefund() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TradeRefund(String id, String batchNo, Integer batchNum, String detailData, Integer successNum,
			String resultDetails, Integer status, String opratorAccount, Date createTime, Date finishTime,
			String sellerEmail) {
		super();
		this.id = id;
		this.batchNo = batchNo;
		this.batchNum = batchNum;
		this.detailData = detailData;
		this.successNum = successNum;
		this.resultDetails = resultDetails;
		this.status = status;
		this.opratorAccount = opratorAccount;
		this.createTime = createTime;
		this.finishTime = finishTime;
		this.sellerEmail = sellerEmail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(Integer batchNum) {
		this.batchNum = batchNum;
	}
	public String getDetailData() {
		return detailData;
	}
	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public String getResultDetails() {
		return resultDetails;
	}
	public void setResultDetails(String resultDetails) {
		this.resultDetails = resultDetails;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOpratorAccount() {
		return opratorAccount;
	}
	public void setOpratorAccount(String opratorAccount) {
		this.opratorAccount = opratorAccount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	
}
