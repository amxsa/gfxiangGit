package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class TColorPrintPart implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5700094152244438778L;
	private Long id;
	private String regNo;//主叫号码（开通彩印服务号码）
	private String content;
	private Date startTime;
	private Date endTime;
	private String isLoop;//是否每天重复  Y/N
	public TColorPrintPart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TColorPrintPart(Long id, String regNo, String content,
			Date startTime, Date endTime, String isLoop) {
		super();
		this.id = id;
		this.regNo = regNo;
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isLoop = isLoop;
	}

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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "TColorPrintPart [id=" + id + ", regNo=" + regNo + ", content="
				+ content + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", isLoop=" + isLoop + "]";
	}

	public String getIsLoop() {
		return isLoop;
	}
	public void setIsLoop(String isLoop) {
		this.isLoop = isLoop;
	}
	
	
}
