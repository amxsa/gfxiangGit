package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

import cn.cellcom.common.date.DateTool;
import cn.cellcom.wechat.common.Env;



public class TPoliteOffWx implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String regNo;
	private String type;
	private Date startTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	private Date expireTime;
	private String state;
	private Date submitTime;
	
	public String getStartExpireTime(){
		if(startTime!=null&&expireTime!=null){
			StringBuffer str = new StringBuffer();
			str.append(DateTool.formateTime2String(startTime, "HH:mm"));
			str.append("-").append(DateTool.formateTime2String(expireTime, "HH:mm"));
			return str.toString();
		}
		return "";
	}
	public String getStartPart(){
		if(startTime!=null)
			return DateTool.formateTime2String(startTime, "HH:mm");
		return "";
		
	}
	public String getExpirePart(){
		if(expireTime!=null)
			return DateTool.formateTime2String(expireTime, "HH:mm");
		return "";
		
	}
	
	public String getName(){
		if(Env.POLITEOFF.containsKey(type)){
			return Env.POLITEOFF.get(type);
		}
		return "";
	}
}
