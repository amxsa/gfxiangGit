package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class TLeaveword implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String ANum;

	private String BNum;

	private String recordfile;

	private Date recordtime;

	private String readstatus;

	private Date lreadtime;

	private String regNo;

	private String msgtype;

	private String callid;
	
	private Date startTime;

	private String ANumName;
	private String ANumCity;
	
	private String shortName;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getANumName() {
		return ANumName;
	}

	public void setANumName(String aNumName) {
		ANumName = aNumName;
	}

	public String getANumCity() {
		return ANumCity;
	}

	public void setANumCity(String aNumCity) {
		ANumCity = aNumCity;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getANum() {
		return ANum;
	}

	public void setANum(String aNum) {
		ANum = aNum;
	}

	public String getBNum() {
		return BNum;
	}

	public void setBNum(String bNum) {
		BNum = bNum;
	}

	public String getRecordfile() {
		return recordfile;
	}

	public void setRecordfile(String recordfile) {
		this.recordfile = recordfile;
	}

	public Date getRecordtime() {
		return recordtime;
	}

	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}

	public String getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}

	public Date getLreadtime() {
		return lreadtime;
	}

	public void setLreadtime(Date lreadtime) {
		this.lreadtime = lreadtime;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getCallid() {
		return callid;
	}

	public void setCallid(String callid) {
		this.callid = callid;
	}

}
