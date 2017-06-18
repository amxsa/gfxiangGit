package cn.cellcom.szba.domain;

import java.util.Date;

public class TLabel {
	private String id;
	private String description;
	private String oldLname;
	private String newLname;
	private String proNO;
	private String proName; // 财物名称
	private String jzcaseId;
	private String caseName;
	private String proType;
	private String changeAccount;
	private Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldLname() {
		return oldLname;
	}
	public void setOldLname(String oldLname) {
		this.oldLname = oldLname;
	}
	public String getNewLname() {
		return newLname;
	}
	public void setNewLname(String newLname) {
		this.newLname = newLname;
	}
	
	
	public String getProNO() {
		return proNO;
	}
	public void setProNO(String proNO) {
		this.proNO = proNO;
	}
	public String getJzcaseId() {
		return jzcaseId;
	}
	public void setJzcaseId(String jzcaseId) {
		this.jzcaseId = jzcaseId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType;
	}
	public String getChangeAccount() {
		return changeAccount;
	}
	public void setChangeAccount(String changeAccount) {
		this.changeAccount = changeAccount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "TLabel [id=" + id + ", description=" + description
				+ ", oldLname=" + oldLname + ", newLname=" + newLname
				+ ", proNO=" + proNO + ", proName=" + proName + ", jzcaseId="
				+ jzcaseId + ", caseName=" + caseName + ", proType=" + proType
				+ ", changeAccount=" + changeAccount + ", createTime="
				+ createTime + "]";
	}
	
	
	
}
