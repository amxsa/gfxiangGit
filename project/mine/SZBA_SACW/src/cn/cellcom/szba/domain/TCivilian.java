package cn.cellcom.szba.domain;

import java.util.Date;

public class TCivilian {
	private String civiId; 
	private String civiName; //人员姓名
	private String civiGender;  //性别
	private String idType;  //证件类型
	private String idNum;  //证件号码
	private String civiType;    //民众类型,”17”:见证人 “18”:持有人  ”19”:保管人 “20”:其他在场人员	“22”:当事人
	private Date createTime; //创建时间
	private TAccount creator; //创建人
	private String creatorId;  //方便数据封装， 加入创建者id字段
	private TDetail detailList; //清单
	private int priority;    //优先级
	private String civiPhone;
	private String civiAddress;  //住址
	private String deptName;
	private String validStatus;
	private String mixId;
	private String tableType;
	private String unit;  //单位
	
	public String getMixId() {
		return mixId;
	}
	public void setMixId(String mixId) {
		this.mixId = mixId;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getCiviId() {
		return civiId;
	}
	public void setCiviId(String civiId) {
		this.civiId = civiId;
	}
	public String getCiviName() {
		return civiName;
	}
	public void setCiviName(String civiName) {
		this.civiName = civiName;
	}
	public String getCiviGender() {
		return civiGender;
	}
	public void setCiviGender(String civiGender) {
		this.civiGender = civiGender;
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
	public String getCiviType() {
		return civiType;
	}
	public void setCiviType(String civiType) {
		this.civiType = civiType;
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
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public TDetail getDetailList() {
		return detailList;
	}
	public void setDetailList(TDetail detailList) {
		this.detailList = detailList;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getCiviPhone() {
		return civiPhone;
	}
	public void setCiviPhone(String civiPhone) {
		this.civiPhone = civiPhone;
	}
	public String getCiviAddress() {
		return civiAddress;
	}
	public void setCiviAddress(String civiAddress) {
		this.civiAddress = civiAddress;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
