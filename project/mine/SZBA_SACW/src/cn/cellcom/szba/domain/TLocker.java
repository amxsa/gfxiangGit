package cn.cellcom.szba.domain;

import java.util.Date;

public class TLocker {

	private long id;
	private String lockerNum;
	private long lockerVolume;
	private String lockerType;
	private long goodsNum;
	private String inventoryStatus;
	private String rfidNum;
	private long rackID;
	private Date createTime;
	private String validStatus;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLockerNum() {
		return lockerNum;
	}
	public void setLockerNum(String lockerNum) {
		this.lockerNum = lockerNum;
	}
	public long getLockerVolume() {
		return lockerVolume;
	}
	public void setLockerVolume(long lockerVolume) {
		this.lockerVolume = lockerVolume;
	}
	public String getLockerType() {
		return lockerType;
	}
	public void setLockerType(String lockerType) {
		this.lockerType = lockerType;
	}
	public long getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(long goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getInventoryStatus() {
		return inventoryStatus;
	}
	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}
	public String getRfidNum() {
		return rfidNum;
	}
	public void setRfidNum(String rfidNum) {
		this.rfidNum = rfidNum;
	}
	public long getRackID() {
		return rackID;
	}
	public void setRackID(long rackID) {
		this.rackID = rackID;
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
		return "TLocker [id=" + id + ", lockerNum=" + lockerNum
				+ ", lockerVolume=" + lockerVolume + ", lockerType="
				+ lockerType + ", goodsNum=" + goodsNum + ", inventoryStatus="
				+ inventoryStatus + ", rfidNum=" + rfidNum + ", rackID="
				+ rackID + ", createTime=" + createTime + ", validStatus="
				+ validStatus + "]";
	}
	
	
}
