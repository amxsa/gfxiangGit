package cn.cellcom.szba.common;

import java.util.Date;

import cn.open.db.Pager;

/**
 * 查询条件类
 * @author hzj
 */
public class ConditionParam {

	private Long applicationId; //申请单id
	private String applicationNo; //申请单编号
	private String condiProCode; //财务编号
	
	public String getCondiProCode() {
		return condiProCode;
	}
	public void setCondiProCode(String condiProCode) {
		this.condiProCode = condiProCode;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	
	private String condiProNo;  //财物编号
	private String condiProId;  //财物id
	private String condiProName;  //财物名称
	private String condiProStatus; //财物状态
	private String condiJzcaseId;  //案件id
	private String condiCaseId;  //警踪案件id
	private String condiCaseName; //案件名称
	private Date condiStartTime; //查询时间：开始
	private Date condiEndTime;  //查询时间：结束
	private String proInStatus; //入库状态
	
	private String condiIntoPer;  //入库人
	private Date condiIntoStartTime; //入库时间：开始
	private Date condiIntoEndTime;  //入库时间：结束
	private String condiStoreHouseName;   //所在仓库
	private Integer currentIndex;
	private Integer totalCount;
	private Integer totalPage;
	private Integer sizePerPage;
	
	private Date condigStartTime; //财物轨迹查询时间：开始
	private Date condigEndTime;  //财物轨迹查询时间：结束
	
	public String getCondiJzcaseId() {
		return condiJzcaseId;
	}
	public void setCondiJzcaseId(String condiJzcaseId) {
		this.condiJzcaseId = condiJzcaseId;
	}
	
	public String getCondiProId() {
		return condiProId;
	}
	public void setCondiProId(String condiProId) {
		this.condiProId = condiProId;
	}
	public String getCondiProName() {
		return condiProName;
	}
	public void setCondiProName(String condiProName) {
		this.condiProName = condiProName;
	}
	public String getCondiProStatus() {
		return condiProStatus;
	}
	public void setCondiProStatus(String condiProStatus) {
		this.condiProStatus = condiProStatus;
	}
	public String getCondiCaseId() {
		return condiCaseId;
	}
	public void setCondiCaseId(String condiCaseId) {
		this.condiCaseId = condiCaseId;
	}
	public String getCondiCaseName() {
		return condiCaseName;
	}
	public void setCondiCaseName(String condiCaseName) {
		this.condiCaseName = condiCaseName;
	}
	public Date getCondiStartTime() {
		return condiStartTime;
	}
	public void setCondiStartTime(Date condiStartTime) {
		this.condiStartTime = condiStartTime;
	}
	public Date getCondiEndTime() {
		return condiEndTime;
	}
	public void setCondiEndTime(Date condiEndTime) {
		this.condiEndTime = condiEndTime;
	}
	public String getCondiProNo() {
		return condiProNo;
	}
	public void setCondiProNo(String condiProNo) {
		this.condiProNo = condiProNo;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getProInStatus() {
		return proInStatus;
	}
	public void setProInStatus(String proInStatus) {
		this.proInStatus = proInStatus;
	}
	public String getCondiIntoPer() {
		return condiIntoPer;
	}
	public void setCondiIntoPer(String condiIntoPer) {
		this.condiIntoPer = condiIntoPer;
	}
	public Date getCondiIntoStartTime() {
		return condiIntoStartTime;
	}
	public void setCondiIntoStartTime(Date condiIntoStartTime) {
		this.condiIntoStartTime = condiIntoStartTime;
	}
	public Date getCondiIntoEndTime() {
		return condiIntoEndTime;
	}
	public void setCondiIntoEndTime(Date condiIntoEndTime) {
		this.condiIntoEndTime = condiIntoEndTime;
	}
	public String getCondiStoreHouseName() {
		return condiStoreHouseName;
	}
	public void setCondiStoreHouseName(String condiStoreHouseName) {
		this.condiStoreHouseName = condiStoreHouseName;
	}
	public Integer getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(Integer currentIndex) {
		this.currentIndex = currentIndex;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getSizePerPage() {
		return sizePerPage;
	}
	public void setSizePerPage(Integer sizePerPage) {
		this.sizePerPage = sizePerPage;
	}
	public Date getCondigStartTime() {
		return condigStartTime;
	}
	public void setCondigStartTime(Date condigStartTime) {
		this.condigStartTime = condigStartTime;
	}
	public Date getCondigEndTime() {
		return condigEndTime;
	}
	public void setCondigEndTime(Date condigEndTime) {
		this.condigEndTime = condigEndTime;
	}
}
