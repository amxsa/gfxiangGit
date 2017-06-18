package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.common.Env;

public class TBindDeviceFluxcard implements Serializable {
	private Integer id;
	private String account;
	private String batchId;
	
	private String orderId;
	private String sn;
	private String mobile;
	private String operateName;
	private String remark;
	private String orderState;
	private Long mobilenum;
	private String barcode;
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Long getMobilenum() {
		return mobilenum;
	}
	public void setMobilenum(Long mobilenum) {
		this.mobilenum = mobilenum;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
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
	private Timestamp submitTime;
	private Timestamp limiteTime;
	
	public Timestamp getLimiteTime() {
		return limiteTime;
	}
	public void setLimiteTime(Timestamp limiteTime) {
		this.limiteTime = limiteTime;
	}
	public String getOrderStateStr(){
		if(Env.ORDER_STATE.containsKey(this.orderState)){
			return Env.ORDER_STATE.get(this.orderState);
		}
		return "";
	}
	
	
}
