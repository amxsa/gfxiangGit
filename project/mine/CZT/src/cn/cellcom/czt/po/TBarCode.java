package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TBarCode implements Serializable {
	private String tdcodemd5;
	public String getTdcodemd5() {
		return tdcodemd5;
	}
	public void setTdcodemd5(String tdcodemd5) {
		this.tdcodemd5 = tdcodemd5;
	}
	private String barcode;
	private String account;
	private String spcode;
	public String getSpcode() {
		return spcode;
	}
	public void setSpcode(String spcode) {
		this.spcode = spcode;
	}
	private Timestamp submitTime;
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
}
