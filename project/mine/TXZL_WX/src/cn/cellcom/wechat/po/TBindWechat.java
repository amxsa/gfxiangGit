package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class TBindWechat implements Serializable {
	private String regNo;
	private String wechatNo;
	private Date bindTime;
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	public Date getBindTime() {
		return bindTime;
	}
	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}
}
