package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.Date;

public class TRegister implements Serializable {
	private String number;
	private String password;
	private String name;
	private String sex;
	private Integer age;
	private String idcard;
	private String depart;
	private String position;
	private String saleMan;
	private String saleDepart;
	private Integer type;
	private Long setid;
	private String status;
	private Date regTime;
	private Integer usage;
	private String lan;
	private Date updateTime;
	private String areacode;
	private String setinfo;
	public String getSetinfo() {
		return setinfo;
	}
	public void setSetinfo(String setinfo) {
		this.setinfo = setinfo;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	private Date cancelTime;
	private Integer isFree;
	private Integer isLong;

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSaleMan() {
		return saleMan;
	}
	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}
	public String getSaleDepart() {
		return saleDepart;
	}
	public void setSaleDepart(String saleDepart) {
		this.saleDepart = saleDepart;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getSetid() {
		return setid;
	}
	public void setSetid(Long setid) {
		this.setid = setid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Integer getUsage() {
		return usage;
	}
	public void setUsage(Integer usage) {
		this.usage = usage;
	}
	public String getLan() {
		return lan;
	}
	public void setLan(String lan) {
		this.lan = lan;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	public Integer getIsLong() {
		return isLong;
	}
	public void setIsLong(Integer isLong) {
		this.isLong = isLong;
	}
	public String getServNbr() {
		return servNbr;
	}
	public void setServNbr(String servNbr) {
		this.servNbr = servNbr;
	}
	public Integer getIsPrepaid() {
		return isPrepaid;
	}
	public void setIsPrepaid(Integer isPrepaid) {
		this.isPrepaid = isPrepaid;
	}
	public Integer getIsSendsms() {
		return isSendsms;
	}
	public void setIsSendsms(Integer isSendsms) {
		this.isSendsms = isSendsms;
	}
	public Integer getLastTotal() {
		return lastTotal;
	}
	public void setLastTotal(Integer lastTotal) {
		this.lastTotal = lastTotal;
	}
	public Date getLastUpdateTotalTime() {
		return lastUpdateTotalTime;
	}
	public void setLastUpdateTotalTime(Date lastUpdateTotalTime) {
		this.lastUpdateTotalTime = lastUpdateTotalTime;
	}
	public String getCallinType() {
		return callinType;
	}
	public void setCallinType(String callinType) {
		this.callinType = callinType;
	}
	public String getNogetType() {
		return nogetType;
	}
	public void setNogetType(String nogetType) {
		this.nogetType = nogetType;
	}
	private String servNbr;
	private Integer isPrepaid;
	private Integer isSendsms;
	private Integer lastTotal;
	private Date lastUpdateTotalTime;
	private String callinType;
	private String nogetType;
}
