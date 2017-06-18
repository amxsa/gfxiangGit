package cn.cellcom.szba.domain;

import java.util.Date;

public class TCase {

	private String caseID;  //案件编号
	private String jzcaseID; //警综案件编号
	private String caseType; //案件类型
	private String caseName; //案件名称
	private String suspectName; //嫌疑人姓名
	private String casePlace;  //案发地点
	private Date startTime;    //开始时间
	private Date endTime;      //结束时间
	private String serialNum;  //序号
	private String leader;  //责任领导
	private TAccount creator;
	private Date occurDate; //立案时间
	
	public Date getOccurDate() {
		return occurDate;
	}
	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}
	private String caseStatus; //案件状态
	private int caseNature; //案件性质
	private Long departmentId;
	private String departmentName;
	private String creatorTemp;
	
	public String getCreatorTemp() {
		return creatorTemp;
	}
	public void setCreatorTemp(String creatorTemp) {
		this.creatorTemp = creatorTemp;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	
	
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	public int getCaseNature() {
		return caseNature;
	}
	public void setCaseNature(int caseNature) {
		this.caseNature = caseNature;
	}
	public TAccount getCreator() {
		return creator;
	}
	public void setCreator(TAccount creator) {
		this.creator = creator;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getJzcaseID() {
		return jzcaseID;
	}
	public void setJzcaseID(String jzcaseID) {
		this.jzcaseID = jzcaseID;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getSuspectName() {
		return suspectName;
	}
	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}
	public String getCasePlace() {
		return casePlace;
	}
	public void setCasePlace(String casePlace) {
		this.casePlace = casePlace;
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
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
}
