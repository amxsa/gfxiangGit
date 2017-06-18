package cn.cellcom.szba.domain;

import java.util.Date;

public class TStoreRoom {
	private Long id;
	private String storeroomNum;
	private String storeroomName;
	private Long storeroomArea;
	private String storeroomType;
	private String storeroomAddr;
	private String roomNum;
	private Long storehouseId;
	private String storehouseName;
	private Date createTime;
	private String validStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStoreroomNum() {
		return storeroomNum;
	}
	public void setStoreroomNum(String storeroomNum) {
		this.storeroomNum = storeroomNum;
	}
	public String getStoreroomName() {
		return storeroomName;
	}
	public void setStoreroomName(String storeroomName) {
		this.storeroomName = storeroomName;
	}
	public Long getStoreroomArea() {
		return storeroomArea;
	}
	public void setStoreroomArea(Long storeroomArea) {
		this.storeroomArea = storeroomArea;
	}
	public String getStoreroomType() {
		return storeroomType;
	}
	public void setStoreroomType(String storeroomType) {
		this.storeroomType = storeroomType;
	}
	public String getStoreroomAddr() {
		return storeroomAddr;
	}
	public void setStoreroomAddr(String storeroomAddr) {
		this.storeroomAddr = storeroomAddr;
	}
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public Long getStorehouseId() {
		return storehouseId;
	}
	public void setStorehouseId(Long storehouseId) {
		this.storehouseId = storehouseId;
	}
	public String getStorehouseName() {
		return storehouseName;
	}
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
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
	
	@Override
	public String toString() {
		return "TStoreroom [id=" + id + ", storeroomNum=" + storeroomNum
				+ ", storeroomName=" + storeroomName + ", storeroomArea="
				+ storeroomArea + ", storeroomType=" + storeroomType
				+ ", storeroomAddr=" + storeroomAddr + ", roomNum=" + roomNum
				+ ", storehouseId=" + storehouseId + ", createTime="
				+ createTime + ", validStatus=" + validStatus + "]";
	}
	
	
}
