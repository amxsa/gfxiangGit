package com.jlit.xms.web.queryBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmpQueryBean extends BaseQueryBean {

	private String idCard;
	
	private String startTime;
	private String endTime;
	private String phone;
	private String deptIds;
	private String deptNames;
	private List<String> deptIdList;
	public EmpQueryBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmpQueryBean(String idCard, String startTime, String endTime,
			String phone, String deptIds, String deptNames) {
		super();
		this.idCard = idCard;
		this.startTime = startTime;
		this.endTime = endTime;
		this.phone = phone;
		this.deptIds = deptIds;
		this.deptNames = deptNames;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	public String get() {
		return deptNames;
	}
	public List<String> getDeptIdList() {
		List<String> list=new ArrayList<String>();
		if (this.deptIds.length()>0) {
			String[] strings = this.deptIds.split(",");
			list= Arrays.asList(strings);
		}
		return list;
	}
	
}
