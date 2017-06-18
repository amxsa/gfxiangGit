package cn.cellcom.szba.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TDetail {

	private String listID; // 清单编号
	private String type; // 清单类型
	private Date createDate; // 清单创建时间
	private Date date; // 扣押清单——扣押日期
	private List<TProperty> properties; // 财务
	private List<TPolice> polices; // 如果是提取痕迹物证登记表，则该字段表示提取人，否则表示办案民警
	private List<TCivilian> civilians; // 如果是提取痕迹物证登记表，则该字段表示提取人，否则表示办案民警
	private List<TCivilian> witnesses; // 见证人
	private List<TCivilian> owner; // 持有人
	private List<TCivilian> keeper; // 保管人
	private TAccount creator;
	private String detpName; // 创建者的部门名，便于数据封装和显示
	private TCase tCase; // 关联案件
	private String caseId;
	private String recordId;
	private List<TProperty> excessPros; //需要剔除的财物列表

	public TDetail() {
		properties = new ArrayList<TProperty>(); // 
		excessPros = new ArrayList<TProperty>(); // 
		polices = new ArrayList<TPolice>(); // 如果是提取痕迹物证登记表，则该字段表示提取人，否则表示办案民警
		civilians = new ArrayList<TCivilian>();
		witnesses = new ArrayList<TCivilian>(); // 见证人
		owner = new ArrayList<TCivilian>(); // 持有人
		keeper = new ArrayList<TCivilian>(); // 保管人
	}

	
	public List<TCivilian> getCivilians() {
		return civilians;
	}


	public void setCivilians(List<TCivilian> civilians) {
		this.civilians = civilians;
	}


	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getDetpName() {
		return detpName;
	}

	public void setDetpName(String detpName) {
		this.detpName = detpName;
	}

	public String getListID() {
		return listID;
	}

	public void setListID(String listID) {
		this.listID = listID;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<TProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<TProperty> properties) {
		this.properties = properties;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public TAccount getCreator() {
		return creator;
	}

	public void setCreator(TAccount creator) {
		this.creator = creator;
	}

	public TCase gettCase() {
		return tCase;
	}

	public void settCase(TCase tCase) {
		this.tCase = tCase;
	}

	public List<TPolice> getPolices() {
		return polices;
	}

	public void setPolices(List<TPolice> polices) {
		this.polices = polices;
	}

	public List<TCivilian> getWitnesses() {
		return witnesses;
	}

	public void setWitnesses(List<TCivilian> witnesses) {
		this.witnesses = witnesses;
	}

	public List<TCivilian> getOwner() {
		return owner;
	}

	public void setOwner(List<TCivilian> owner) {
		this.owner = owner;
	}

	public List<TCivilian> getKeeper() {
		return keeper;
	}

	public void setKeeper(List<TCivilian> keeper) {
		this.keeper = keeper;
	}
	
	public List<TProperty> getExcessPros() {
		return excessPros;
	}

	public void setExcessPros(List<TProperty> excessPros) {
		this.excessPros = excessPros;
	}


	@Override
	public String toString() {
		return "TDetail [listID=" + listID + ", type=" + type + ", createDate=" + createDate + ", date=" + date + ", properties=" + properties + ", polices=" + polices + ", witnesses="
				+ witnesses + ", owner=" + owner + ", keeper=" + keeper + ", creator=" + creator + ", detpName=" + detpName + ", tCase=" + tCase + "]";
	}

}
