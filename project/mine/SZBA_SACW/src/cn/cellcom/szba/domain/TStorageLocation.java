package cn.cellcom.szba.domain;

import java.util.Date;

public class TStorageLocation {
	
	private Long id;
	private String locationNum;  //库位编号
	private String locationName;  //库位名称
	private String capacity;  //体积大小
	private String locationType;  //库位类型
	private Long depositNum;  //存放数量
	private Long goodsNum;     //已放数量
	private String buildNum;  //楼号
	private String buildLevel;  //楼层
	private String counterNum;  //柜号
	private String address;  //地址
	private String inventoryStatus; //库存状态，’F’表示库存满了，’U’表示库存空闲，’E’表示库存为未满
	private String rfidNum; //FRID标签码
	private Long storeroomID; //库房id
	private Date createTime; //创建时间
	private String validStatus; //删除状态  
	private Long rackId; //货架id
	private String rackName; //货架名称
	private Long storeHouseId;  //仓库id
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLocationNum() {
		return locationNum;
	}


	public void setLocationNum(String locationNum) {
		this.locationNum = locationNum;
	}

	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getCapacity() {
		return capacity;
	}


	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}


	public String getLocationType() {
		return locationType;
	}


	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}


	public Long getDepositNum() {
		return depositNum;
	}


	public void setDepositNum(Long depositNum) {
		this.depositNum = depositNum;
	}


	public Long getGoodsNum() {
		return goodsNum;
	}


	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}


	public String getBuildNum() {
		return buildNum;
	}


	public void setBuildNum(String buildNum) {
		this.buildNum = buildNum;
	}


	public String getBuildLevel() {
		return buildLevel;
	}


	public void setBuildLevel(String buildLevel) {
		this.buildLevel = buildLevel;
	}


	public String getCounterNum() {
		return counterNum;
	}


	public void setCounterNum(String counterNum) {
		this.counterNum = counterNum;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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


	public Long getStoreroomID() {
		return storeroomID;
	}


	public void setStoreroomID(Long storeroomID) {
		this.storeroomID = storeroomID;
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


	public Long getRackId() {
		return rackId;
	}


	public void setRackId(Long rackId) {
		this.rackId = rackId;
	}


	public String getRackName() {
		return rackName;
	}


	public void setRackName(String rackName) {
		this.rackName = rackName;
	}


	public Long getStoreHouseId() {
		return storeHouseId;
	}


	public void setStoreHouseId(Long storeHouseId) {
		this.storeHouseId = storeHouseId;
	}

	@Override
	public String toString() {
		return "TStorageLocation [id=" + id + ", locationNum=" + locationNum
				+ ", capacity=" + capacity + ", locationType=" + locationType
				+ ", depositNum=" + depositNum + ", goodsNum=" + goodsNum
				+ ", buildNum=" + buildNum + ", buildLevel=" + buildLevel
				+ ", counterNum=" + counterNum + ", address=" + address
				+ ", inventoryStatus=" + inventoryStatus + ", rfidNum="
				+ rfidNum + ", storeroomID=" + storeroomID + ", createTime="
				+ createTime + ", validStatus=" + validStatus + ", rackId="
				+ rackId + ", rackName=" + rackName + "]";
	}
	
}
