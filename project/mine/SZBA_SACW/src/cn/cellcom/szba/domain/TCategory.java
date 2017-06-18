package cn.cellcom.szba.domain;

import java.util.Date;
import java.util.List;

public class TCategory {

	private Long id;
	private String name;
	private String saveDemand;
	private String packDemand;
	private String enviDemand;
	private Date createTime;
	private Date updateTime;
	private int levels;
	private Long parentId;
	private Long priority;
	private String validStatus;
	private TCategory parCategory; //上级类别
	private String attrString;
	private String type;  //类别 用于工单流程
	
	public Long getId() {
		
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSaveDemand() {
		return saveDemand;
	}
	public void setSaveDemand(String saveDemand) {
		this.saveDemand = saveDemand;
	}
	public String getPackDemand() {
		return packDemand;
	}
	public void setPackDemand(String packDemand) {
		this.packDemand = packDemand;
	}
	public String getEnviDemand() {
		return enviDemand;
	}
	public void setEnviDemand(String enviDemand) {
		this.enviDemand = enviDemand;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getLevels() {
		return levels;
	}
	public void setLevels(int levels) {
		this.levels = levels;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public TCategory getParCategory() {
		return parCategory;
	}
	public void setParCategory(TCategory parCategory) {
		this.parCategory = parCategory;
	}
	public String getAttrString() {
		return attrString;
	}
	public void setAttrString(String attrString) {
		this.attrString = attrString;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
