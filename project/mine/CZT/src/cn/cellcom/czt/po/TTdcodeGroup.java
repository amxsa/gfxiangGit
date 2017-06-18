package cn.cellcom.czt.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class TTdcodeGroup implements Serializable {
	private Integer number; 
	private String name; 
	private Timestamp submitTime;
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	
}
