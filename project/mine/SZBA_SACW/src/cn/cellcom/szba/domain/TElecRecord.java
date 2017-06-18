package cn.cellcom.szba.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.cellcom.szba.domain.base.BaseRecord;

public class TElecRecord extends BaseRecord{

	private String elecRecordID;
	private Date startTime;
	private Date endTime;
	private String medium;
	private String savePlace; //存放地点
	private String extractPlace; //提取地点
	private String policeName;//提取人姓名,方便数据封装
	private String policeType; //提取人类型
	
	private List<TCivilian> owners;
	private List<TCivilian> personnels;
	
	public TElecRecord(){
		super();
		owners = new ArrayList<TCivilian>();
		personnels = new ArrayList<TCivilian>();
	}

	public String getElecRecordID() {
		return elecRecordID;
	}

	public void setElecRecordID(String elecRecordID) {
		this.elecRecordID = elecRecordID;
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

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getSavePlace() {
		return savePlace;
	}

	public void setSavePlace(String savePlace) {
		this.savePlace = savePlace;
	}

	public String getExtractPlace() {
		return extractPlace;
	}

	public void setExtractPlace(String extractPlace) {
		this.extractPlace = extractPlace;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getPoliceType() {
		return policeType;
	}

	public void setPoliceType(String policeType) {
		this.policeType = policeType;
	}

	public List<TCivilian> getOwners() {
		return owners;
	}

	public void setOwners(List<TCivilian> owners) {
		this.owners = owners;
	}

	public List<TCivilian> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<TCivilian> personnels) {
		this.personnels = personnels;
	}
	
}
