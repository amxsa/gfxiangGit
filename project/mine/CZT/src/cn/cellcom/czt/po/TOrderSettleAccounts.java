package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TOrderSettleAccounts implements Serializable {
	private  String id;
	private  String orderId;
	private  String account;
	private  String originator;
	private  String projectName;
	private  Integer saleMoney;
	private  String contractNo;
	private  String invoiceNo;
	private  String state;
	private  String resultReason;
	private  String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(Integer saleMoney) {
		this.saleMoney = saleMoney;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getResultReason() {
		return resultReason;
	}
	public void setResultReason(String resultReason) {
		this.resultReason = resultReason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	private  Timestamp submitTime;
}	
