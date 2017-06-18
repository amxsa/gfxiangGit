package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TSyncTDCode implements Serializable {
	private Integer id;
	private String tdcodemd5;
	private String barcode;
	private String fluxcard;
	private Timestamp limiteTime;
	private String state;
	private String operateType;
	private String mobilenum;
	public String getMobilenum() {
		return mobilenum;
	}
	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	private Integer groupId;
	private Timestamp submitTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTdcodemd5() {
		return tdcodemd5;
	}
	public void setTdcodemd5(String tdcodemd5) {
		this.tdcodemd5 = tdcodemd5;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getFluxcard() {
		return fluxcard;
	}
	public void setFluxcard(String fluxcard) {
		this.fluxcard = fluxcard;
	}
	public Timestamp getLimiteTime() {
		return limiteTime;
	}
	public void setLimiteTime(Timestamp limiteTime) {
		this.limiteTime = limiteTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
}
