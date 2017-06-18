package cn.cellcom.wechat.po;

import java.util.Date;



public  class TCallforwardLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String regNo;

	private String cfType;

	private String cfNumber;

	private Long seqid;

	private Date submitTime;

	private String handleStatus;

	private Date operateTime;
	
	private String operateTimeStr;

	private String operateType;
	
	private String handleDesc;
	
	private String operator;

	// Constructors

	public String getHandleDesc() {
		return handleDesc;
	}

	public void setHandleDesc(String handleDesc) {
		this.handleDesc = handleDesc;
	}

	/** default constructor */
	public TCallforwardLog() {
	}

	/** minimal constructor */
	public TCallforwardLog(String regNo, String cfType,
			String cfNumber, Long seqid, Date submitTime, String handleStatus,
			Date operateTime) {
		this.regNo = regNo;
		this.cfType = cfType;
		this.cfNumber = cfNumber;
		this.seqid = seqid;
		this.submitTime = submitTime;
		this.handleStatus = handleStatus;
		this.operateTime = operateTime;
	}

	/** full constructor */
	public TCallforwardLog(String regNo, String cfType,
			String cfNumber, Long seqid, Date submitTime, String handleStatus,
			Date operateTime, String operateType) {
		this.regNo = regNo;
		this.cfType = cfType;
		this.cfNumber = cfNumber;
		this.seqid = seqid;
		this.submitTime = submitTime;
		this.handleStatus = handleStatus;
		this.operateTime = operateTime;
		this.operateType = operateType;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getCfType() {
		return this.cfType;
	}

	public void setCfType(String cfType) {
		this.cfType = cfType;
	}

	public String getCfNumber() {
		return this.cfNumber;
	}

	public void setCfNumber(String cfNumber) {
		this.cfNumber = cfNumber;
	}

	public Long getSeqid() {
		return this.seqid;
	}

	public void setSeqid(Long seqid) {
		this.seqid = seqid;
	}

	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getHandleStatus() {
		return this.handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateTimeStr() {
		return operateTimeStr;
	}

	public void setOperateTimeStr(String operateTimeStr) {
		this.operateTimeStr = operateTimeStr;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}