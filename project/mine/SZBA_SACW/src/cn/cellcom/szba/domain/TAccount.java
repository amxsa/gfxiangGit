package cn.cellcom.szba.domain;

import java.util.Date;

public class TAccount {
	private String account;
	private String workno;
	private String password;
	private String status;
	private String mobile;
	private String name;
	private String gender;
	private String idType;
	private String idNum;
	private Long departmentID;
	private Date createTime;
	private Date updateTime;
	private Date expireTime;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getWorkno() {
		return workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public Long getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(Long departmentID) {
		this.departmentID = departmentID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return "TAccount [account=" + account + ", workno=" + workno
				+ ", password=" + password + ", status=" + status + ", mobile="
				+ mobile + ", name=" + name + ", gender=" + gender
				+ ", idType=" + idType + ", idNum=" + idNum + ", departmentID="
				+ departmentID + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", expireTime=" + expireTime + "]";
	}
}
