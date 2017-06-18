package cn.cellcom.szba.domain;

import java.util.Date;

public class TPolice {
	private String poliId;
	private String poliName;
	private TDepartment dept; // 部门信息
	private String deptID; // 为了封装数据方便，加入部门id
	private String deptName;
	private String poliGender;
	private String idType; // 证件类型
	private String idNum; // 证件号码
	private String poliPhone;
	private String poliType;
	private Date createTime;
	private TAccount creator;
	private int priority; // 优先级
	private String creatorId; // 方便数据封装， 加入创建者id字段
	private TAccount relaAccount; // 关联的账户
	private TRecord record; // 笔录
	private TDetail detailList; // 清单
	private String validStatus;
	private String tableType;
	private String mixId;

	
	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getMixId() {
		return mixId;
	}

	public void setMixId(String mixId) {
		this.mixId = mixId;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	

	public String getPoliId() {
		return poliId;
	}

	public void setPoliId(String poliId) {
		this.poliId = poliId;
	}

	public String getPoliName() {
		return poliName;
	}

	public void setPoliName(String poliName) {
		this.poliName = poliName;
	}

	public TDepartment getDept() {
		return dept;
	}

	public void setDept(TDepartment dept) {
		this.dept = dept;
	}

	public String getPoliGender() {
		return poliGender;
	}

	public void setPoliGender(String poliGender) {
		this.poliGender = poliGender;
	}

	public String getPoliPhone() {
		return poliPhone;
	}

	public void setPoliPhone(String poliPhone) {
		this.poliPhone = poliPhone;
	}

	public String getPoliType() {
		return poliType;
	}

	public void setPoliType(String poliType) {
		this.poliType = poliType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TAccount getCreator() {
		return creator;
	}

	public void setCreator(TAccount creator) {
		this.creator = creator;
	}

	public TAccount getRelaAccount() {
		return relaAccount;
	}

	public void setRelaAccount(TAccount relaAccount) {
		this.relaAccount = relaAccount;
	}

	public TRecord getRecord() {
		return record;
	}

	public void setRecord(TRecord record) {
		this.record = record;
	}

	public TDetail getDetailList() {
		return detailList;
	}

	public void setDetailList(TDetail detailList) {
		this.detailList = detailList;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
