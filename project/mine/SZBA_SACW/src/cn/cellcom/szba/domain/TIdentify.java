package cn.cellcom.szba.domain;

import java.util.Date;

public class TIdentify {
	private Integer id; 
	private String ideResult; //鉴定结果
	private String ideDate; //鉴定日期
	private String ideUint; //鉴定机构
	private String idePerson; //鉴定人
	private String ideHandle; //经办人
	private Date createTime; //创建时间
	private String creator; //创建人
	private Date updateTime; //修改时间
	private String updater; //修改人
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdeResult() {
		return ideResult;
	}
	public void setIdeResult(String ideResult) {
		this.ideResult = ideResult;
	}
	public String getIdeDate() {
		return ideDate;
	}
	public void setIdeDate(String ideDate) {
		this.ideDate = ideDate;
	}
	public String getIdeUint() {
		return ideUint;
	}
	public void setIdeUint(String ideUint) {
		this.ideUint = ideUint;
	}
	public String getIdePerson() {
		return idePerson;
	}
	public void setIdePerson(String idePerson) {
		this.idePerson = idePerson;
	}
	public String getIdeHandle() {
		return ideHandle;
	}
	public void setIdeHandle(String ideHandle) {
		this.ideHandle = ideHandle;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
}
