package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.cellcom.czt.common.Env;

public class TOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	public String getConfigure() {
		return configure;
	}
	public void setConfigure(String configure) {
		this.configure = configure;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getFromPart() {
		return fromPart;
	}
	public void setFromPart(String fromPart) {
		this.fromPart = fromPart;
	}
	public Integer getDealCount() {
		return dealCount;
	}
	public void setDealCount(Integer dealCount) {
		this.dealCount = dealCount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSpcode() {
		return spcode;
	}
	public void setSpcode(String spcode) {
		this.spcode = spcode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private Timestamp submitTime;
	private String configure;
	private Integer count ;
	private Long unitPrice;
	private Long total;
	private String state;
	private String account;
	private String fromPart;
	private Integer dealCount;
	private String remark;
	private String spcode;
	private String receiveTelephone;
	private String receiveName;
	private String receiveAddress;
	private String otherOrderId;
	private String orderType;
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOtherOrderId() {
		return otherOrderId;
	}
	public void setOtherOrderId(String otherOrderId) {
		this.otherOrderId = otherOrderId;
	}
	public String getReceiveTelephone() {
		return receiveTelephone;
	}
	public void setReceiveTelephone(String receiveTelephone) {
		this.receiveTelephone = receiveTelephone;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	
	public String getFromPartStr(){
		return Env.ORDER_FROM_PART.get(fromPart);
	}
	
	public String getStateStr(){
		return Env.ORDER_STATE.get(state);
	}
	
	public String getConfigureStr(){
		return Env.ORDER_CONFIGURE.get(configure);
	}
	
}
