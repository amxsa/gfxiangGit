package cn.cellcom.szba.domain;

import java.util.Date;
import java.util.List;

public class TDemage {

	private String id;
	
	private String jzcaseId;
	private String caseName;
	private String damageAccount;
	private Date createTime;
	private String description;
	private String proNO;
	private String proName; // 财物名称
	private Double proQuantity; // 财物数量
	private String proUnit;
	private String proType;
	private String validStatus;
	private String propertyId;
	private Long applicationId;
	private String damageStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getDamageAccount() {
		return damageAccount;
	}
	public void setDamageAccount(String damageAccount) {
		this.damageAccount = damageAccount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProNO() {
		return proNO;
	}
	public void setProNO(String proNO) {
		this.proNO = proNO;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Double getProQuantity() {
		return proQuantity;
	}
	public void setProQuantity(Double proQuantity) {
		this.proQuantity = proQuantity;
	}
	public String getProUnit() {
		return proUnit;
	}
	public void setProUnit(String proUnit) {
		this.proUnit = proUnit;
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public String getJzcaseId() {
		return jzcaseId;
	}
	public void setJzcaseId(String jzcaseId) {
		this.jzcaseId = jzcaseId;
	}
	
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public String getDamageStatus() {
		return damageStatus;
	}
	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}
	@Override
	public String toString() {
		return "TDemage [id=" + id + ", jzcaseId=" + jzcaseId + ", caseName="
				+ caseName + ", damageAccount=" + damageAccount
				+ ", createTime=" + createTime + ", description=" + description
				+ ", proNO=" + proNO + ", proName=" + proName
				+ ", proQuantity=" + proQuantity + ", proUnit=" + proUnit
				+ ", proType=" + proType + ", validStatus=" + validStatus
				+ ", propertyId=" + propertyId + ", applicationId="
				+ applicationId + ", damageStatus=" + damageStatus + "]";
	}
	
}
