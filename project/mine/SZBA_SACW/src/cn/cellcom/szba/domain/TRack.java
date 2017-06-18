package cn.cellcom.szba.domain;

import java.util.Date;

public class TRack {
	
	private Long id;
	private String rackNum;
	private String rackName;
	private Long rackHeight;
	private Long rackLength;
	private Long rackLoad;
	private Long storeroomId;
	private String storeroomName;
	private Date createTime;
	private String validStatus;
	private Long storehouseId;  //仓库id
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRackNum() {
		return rackNum;
	}
	public void setRackNum(String rackNum) {
		this.rackNum = rackNum;
	}
	public String getRackName() {
		return rackName;
	}
	public void setRackName(String rackName) {
		this.rackName = rackName;
	}
	public Long getRackHeight() {
		return rackHeight;
	}
	public void setRackHeight(Long rackHeight) {
		this.rackHeight = rackHeight;
	}
	public Long getRackLength() {
		return rackLength;
	}
	public void setRackLength(Long rackLength) {
		this.rackLength = rackLength;
	}
	public Long getRackLoad() {
		return rackLoad;
	}
	public void setRackLoad(Long rackLoad) {
		this.rackLoad = rackLoad;
	}
	public Long getStoreroomId() {
		return storeroomId;
	}
	public void setStoreroomId(Long storeroomId) {
		this.storeroomId = storeroomId;
	}
	public String getStoreroomName() {
		return storeroomName;
	}
	public void setStoreroomName(String storeroomName) {
		this.storeroomName = storeroomName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	public Long getStorehouseId() {
		return storehouseId;
	}
	public void setStorehouseId(Long storehouseId) {
		this.storehouseId = storehouseId;
	}
}
