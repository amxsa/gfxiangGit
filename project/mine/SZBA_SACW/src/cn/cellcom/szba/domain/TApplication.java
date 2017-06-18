package cn.cellcom.szba.domain;

import java.util.Date;

import cn.cellcom.szba.common.DateTool;

/**
 * 申请表
 * 
 * @author kchguo
 * 
 */
public class TApplication {

	private Long id; // 申请单ID
	private String applyBasis; // 申请理由依据
	private String proIds; // 财物列表
	private String attachments; // 附件列表
	private String status; // 申请单状态
	private Date createTime;
	private String account;
	// code=PROCEDURE001, description=一般财物入暂存库流程
	private String procedureCode; // Procedure.code
	private String procedureDescription; // Procedure.description

	// code=RZCK, description=入暂存库
	private String disposalCode;
	private String disposalDescription;
	
	// 关联查询字段
	private String accountName;// 申请人姓名。
	private String departmentId;// 部门编号
	private String departmentName; //部门名称

	private String procInstId; //流程实例id
	
	private Long leadDepartmentId;  //牵头部门
	private String leadDepartmentName;
	private Long assistDepartmentId;  //协助部门
	private String assistDepartmentName;
	private String applicationNo;  //申请单编号
	private String parentId;  //上级申请单id
	
	private TCase tCase;
	
	private Date expirationDate;
	private String saveDateType;
	private Date planStorageDate;
	private String deliverType;
	private String targetStorehouse;
	private Date planStartDate; //当前借用时间 
	private Double duration;  //时长
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplyBasis() {
		return applyBasis;
	}

	public void setApplyBasis(String applyBasis) {
		this.applyBasis = applyBasis;
	}

	public String getProIds() {
		return proIds;
	}

	public void setProIds(String proIds) {
		this.proIds = proIds;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	public String getProcedureDescription() {
		return procedureDescription;
	}

	public void setProcedureDescription(String procedureDescription) {
		this.procedureDescription = procedureDescription;
	}

	public String getDisposalCode() {
		return disposalCode;
	}

	public void setDisposalCode(String disposalCode) {
		this.disposalCode = disposalCode;
	}

	public String getDisposalDescription() {
		return disposalDescription;
	}

	public void setDisposalDescription(String disposalDescription) {
		this.disposalDescription = disposalDescription;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCreateTimeStr(){
		if(createTime!=null){
			return DateTool.formateTime2String(createTime);
		}
		
		return "";
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public TCase gettCase() {
		return tCase;
	}

	public void settCase(TCase tCase) {
		this.tCase = tCase;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
		return "TApplication [id=" + id + ", applyBasis=" + applyBasis
				+ ", proIds=" + proIds + ", attachments=" + attachments
				+ ", status=" + status + ", createTime=" + createTime
				+ ", account=" + account + ", procedureCode=" + procedureCode
				+ ", procedureDescription=" + procedureDescription
				+ ", disposalCode=" + disposalCode + ", disposalDescription="
				+ disposalDescription + ", accountName=" + accountName
				+ ", departmentId=" + departmentId + ", departmentName="
				+ departmentName + ", procInstId=" + procInstId
				+ ", leadDepartmentId=" + leadDepartmentId
				+ ", leadDepartmentName=" + leadDepartmentName
				+ ", assistDepartmentId=" + assistDepartmentId
				+ ", assistDepartmentName=" + assistDepartmentName
				+ ", applicationNo=" + applicationNo + ", parentId=" + parentId
				+ ", tCase=" + tCase + ", expirationDate=" + expirationDate
				+ ", saveDateType=" + saveDateType + ", planStorageDate="
				+ planStorageDate + ", deliverType=" + deliverType
				+ ", targetStorehouse=" + targetStorehouse + ", planStartDate="
				+ planStartDate + ", duration=" + duration + "]";
	}


}
