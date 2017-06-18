package cn.cellcom.szba.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.cellcom.szba.domain.base.BaseRecord;

public class TRecord extends BaseRecord{
	private String recordID; //笔录编号
	private Date startTime;   //笔录开始填写时间
	private Date endTime;     //笔录填写结束时间
	private String target; // 对象
	private String place;  //地点
	private String listId; //对应清单的id
	
	private List<TCivilian> clients;   //当事人
	private List<TCivilian> personnels; //在场人员
	
	public TRecord(){
		super();
		clients = new ArrayList<TCivilian>();   //当事人
		personnels = new ArrayList<TCivilian>(); //在场人员
	}

	
	public String getListId() {
		return listId;
	}


	public void setListId(String listId) {
		this.listId = listId;
	}


	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<TCivilian> getClients() {
		return clients;
	}

	public void setClients(List<TCivilian> clients) {
		this.clients = clients;
	}

	public List<TCivilian> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<TCivilian> personnels) {
		this.personnels = personnels;
	}
	
	
	
}
