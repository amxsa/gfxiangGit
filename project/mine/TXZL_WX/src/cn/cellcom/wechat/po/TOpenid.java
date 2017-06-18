package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class TOpenid implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String openid;
	private String nickname;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	private Date submitTime;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
}
