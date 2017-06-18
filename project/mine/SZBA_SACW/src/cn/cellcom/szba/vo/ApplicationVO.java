package cn.cellcom.szba.vo;

import java.util.Date;
import java.util.List;

import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.domain.TAttachment;
import cn.cellcom.szba.domain.TProperty;

public class ApplicationVO {
	private Long applicationId;//
	private String applicationStatus;//
	private String taskId;
	private String procInstId;
	private String procDefId;
	private String propertyStatus;
	private Date applicationCreateTime;
	private String applyBasis; // 申请理由依据
	private String applicationName;
	private List<TProperty> pros; // 财物列表
	private List<TAttachment> attachment; // 附件列表
	private DisposalAndProcedure dpResult;
	private String account;
	private String accountName;
	private String caseName;
	private String caseId;
	private String jzcaseId;
	private Long departmentId;
	private String departmentName;
	private String saveDateType; // 保存期限类型：短期，长期
	private Date expirationDate; // 保存到期日期
	private Date planStorageDate; // 计划入库/出库/归还时间/计划移库时间
	private Date planStartDate; //当前借用时间 
	private Double duration;  //时长
	private String deliverType; // 送货方式：取货，自送
	private String targetStorehouse; // 待入仓库
	private String handler;  //实施人（处理人）
	private Date handleTime; //处理时间（处理时间）
	private String hisStatus;
	private Long parentId;
	
	private Long leadDepartmentId;  //牵头部门
	private String leadDepartmentName;
	private Long assistDepartmentId;  //协助部门
	private String assistDepartmentName;
	private String applicationNo;   //申请单编号
	private String preTaskName;  //上级任务节点的名称
	private String currTaskName;  //当前任务节点的名称
	
	private String disposalDescription;
	private String procedureCode;
	
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
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

	public String getApplyBasis() {
		return applyBasis;
	}

	public void setApplyBasis(String applyBasis) {
		this.applyBasis = applyBasis;
	}

	public List<TAttachment> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<TAttachment> attachment) {
		this.attachment = attachment;
	}

	public DisposalAndProcedure getDpResult() {
		return dpResult;
	}

	public void setDpResult(DisposalAndProcedure dpResult) {
		this.dpResult = dpResult;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public List<TProperty> getPros() {
		return pros;
	}

	public void setPros(List<TProperty> pros) {
		this.pros = pros;
	}

	public String getSaveDateType() {
		return saveDateType;
	}

	public void setSaveDateType(String saveDateType) {
		this.saveDateType = saveDateType;
	}

	public Date getPlanStorageDate() {
		return planStorageDate;
	}

	public void setPlanStorageDate(Date planStorageDate) {
		this.planStorageDate = planStorageDate;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getTargetStorehouse() {
		return targetStorehouse;
	}

	public void setTargetStorehouse(String targetStorehouse) {
		this.targetStorehouse = targetStorehouse;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
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

	public String getPropertyStatus() {
		return propertyStatus;
	}

	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}

	public Date getApplicationCreateTime() {
		return applicationCreateTime;
	}

	public void setApplicationCreateTime(Date applicationCreateTime) {
		this.applicationCreateTime = applicationCreateTime;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getHisStatus() {
		return hisStatus;
	}

	public void setHisStatus(String hisStatus) {
		this.hisStatus = hisStatus;
	}

	public String getJzcaseId() {
		return jzcaseId;
	}

	public void setJzcaseId(String jzcaseId) {
		this.jzcaseId = jzcaseId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getLeadDepartmentId() {
		return leadDepartmentId;
	}

	public void setLeadDepartmentId(Long leadDepartmentId) {
		this.leadDepartmentId = leadDepartmentId;
	}

	public String getLeadDepartmentName() {
		return leadDepartmentName;
	}

	public void setLeadDepartmentName(String leadDepartmentName) {
		this.leadDepartmentName = leadDepartmentName;
	}

	public Long getAssistDepartmentId() {
		return assistDepartmentId;
	}

	public void setAssistDepartmentId(Long assistDepartmentId) {
		this.assistDepartmentId = assistDepartmentId;
	}

	public String getAssistDepartmentName() {
		return assistDepartmentName;
	}

	public void setAssistDepartmentName(String assistDepartmentName) {
		this.assistDepartmentName = assistDepartmentName;
	}
	
	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getPreTaskName() {
		return preTaskName;
	}

	public void setPreTaskName(String preTaskName) {
		this.preTaskName = preTaskName;
	}

	public String getDisposalDescription() {
		return disposalDescription;
	}

	public void setDisposalDescription(String disposalDescription) {
		this.disposalDescription = disposalDescription;
	}

	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	public String getCurrTaskName() {
		return currTaskName;
	}

	public void setCurrTaskName(String currTaskName) {
		this.currTaskName = currTaskName;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "ApplicationVO [applicationId=" + applicationId
				+ ", applicationStatus=" + applicationStatus + ", taskId="
				+ taskId + ", procInstId=" + procInstId + ", procDefId="
				+ procDefId + ", propertyStatus=" + propertyStatus
				+ ", applicationCreateTime=" + applicationCreateTime
				+ ", applyBasis=" + applyBasis + ", applicationName="
				+ applicationName + ", pros=" + pros + ", attachment="
				+ attachment + ", dpResult=" + dpResult + ", account="
				+ account + ", accountName=" + accountName + ", caseName="
				+ caseName + ", caseId=" + caseId + ", jzcaseId=" + jzcaseId
				+ ", departmentId=" + departmentId + ", departmentName="
				+ departmentName + ", saveDateType=" + saveDateType
				+ ", expirationDate=" + expirationDate + ", planStorageDate="
				+ planStorageDate + ", planStartDate=" + planStartDate
				+ ", duration=" + duration + ", deliverType=" + deliverType
				+ ", targetStorehouse=" + targetStorehouse + ", handler="
				+ handler + ", handleTime=" + handleTime + ", hisStatus="
				+ hisStatus + ", parentId=" + parentId + ", leadDepartmentId="
				+ leadDepartmentId + ", leadDepartmentName="
				+ leadDepartmentName + ", assistDepartmentId="
				+ assistDepartmentId + ", assistDepartmentName="
				+ assistDepartmentName + ", applicationNo=" + applicationNo
				+ ", preTaskName=" + preTaskName + ", currTaskName="
				+ currTaskName + ", disposalDescription=" + disposalDescription
				+ ", procedureCode=" + procedureCode + "]";
	}

}
