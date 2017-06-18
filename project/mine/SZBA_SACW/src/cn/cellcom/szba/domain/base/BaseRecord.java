package cn.cellcom.szba.domain.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.cellcom.szba.domain.TCase;
import cn.cellcom.szba.domain.TCivilian;
import cn.cellcom.szba.domain.TPolice;

public class BaseRecord {
	private Date createTime; //笔录创建时间
	private String creator; //创建人
	private TCase tCase;  //案件
	private String transactor; //办理人
	private String type;   //笔录类型
	private String reason; // 事由和目的
	private String result; //过程和结果
	private String sign;

	private List<TPolice> polices;  //办案民警
	private List<TCivilian> civilians; //普通民众
	private List<TPolice> recorders;  //记录员
	private List<TCivilian> witnesses; //见证人
	
	public BaseRecord(){
		polices = new ArrayList<TPolice>();
		recorders = new ArrayList<TPolice>();
		witnesses = new ArrayList<TCivilian>();
		civilians = new ArrayList<TCivilian>();
	}
	
	
	public List<TCivilian> getCivilians() {
		return civilians;
	}


	public void setCivilians(List<TCivilian> civilians) {
		this.civilians = civilians;
	}


	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public TCase gettCase() {
		return tCase;
	}
	public void settCase(TCase tCase) {
		this.tCase = tCase;
	}
	public String getTransactor() {
		return transactor;
	}
	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<TPolice> getPolices() {
		return polices;
	}
	public void setPolices(List<TPolice> polices) {
		this.polices = polices;
	}
	public List<TPolice> getRecorders() {
		return recorders;
	}
	public void setRecorders(List<TPolice> recorders) {
		this.recorders = recorders;
	}
	public List<TCivilian> getWitnesses() {
		return witnesses;
	}
	public void setWitnesses(List<TCivilian> witnesses) {
		this.witnesses = witnesses;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}
}
