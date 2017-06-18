package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TOrderState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String account ;
	private String orderId;
	private String state;
	private String describeMsg;
	private Timestamp submitTime;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDescribeMsg() {
		return describeMsg;
	}
	public void setDescribeMsg(String describeMsg) {
		this.describeMsg = describeMsg;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	
}
