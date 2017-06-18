package cn.cellcom.szba.vo;

import java.util.Date;

import cn.cellcom.szba.common.DateTool;

public class MyInTmporary {
	private String taskId;
	private String procInstId;
	private Long applicationId;
	private String applicationName;
	private String applyBasis;
	private String applicationStatus;
	private String caseName;
	private String caseId;
	private String accountName;
	private String departmentName;
	
	private String propertyStatus;
	private String proTableType;
	private String hisStatus;
	
	private Long propertyId;
	
	private Date applicationCreateTime;
	
	
	public String getApplicationCreateTimeStr(){
		if(applicationCreateTime!=null){
			return DateTool.formateTime2String(applicationCreateTime);
		}
		
		return "";
	}
	
	public Date getApplicationCreateTime() {
		return applicationCreateTime;
	}
	public void setApplicationCreateTime(Date applicationCreateTime) {
		this.applicationCreateTime = applicationCreateTime;
	}
	public String getHisStatus() {
		return hisStatus;
	}
	public void setHisStatus(String hisStatus) {
		this.hisStatus = hisStatus;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	
	
	public String getApplyBasis() {
		return applyBasis;
	}
	public void setApplyBasis(String applyBasis) {
		this.applyBasis = applyBasis;
	}
	public String getProTableType() {
		return proTableType;
	}
	public void setProTableType(String proTableType) {
		this.proTableType = proTableType;
	}
	@Override
	public String toString() {
		return "MyInTemporary [taskId=" + taskId + ", procInstId=" + procInstId + ", applicationId=" + applicationId + ", applicationName=" + applicationName + ", applyBasis=" + applyBasis
				+ ", applicationStatus=" + applicationStatus + ", caseName=" + caseName + ", caseId=" + caseId + ", accountName=" + accountName + ", departmentName=" + departmentName
				+ ", propertyStatus=" + propertyStatus + ", proTableType=" + proTableType + ", hisStatus=" + hisStatus + ", propertyId=" + propertyId + "]";
	}
	
	
}
