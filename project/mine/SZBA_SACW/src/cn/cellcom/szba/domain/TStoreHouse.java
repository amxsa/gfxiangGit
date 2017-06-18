package cn.cellcom.szba.domain;

import java.util.Date;

public class TStoreHouse {

	private Long id;
	private String storehouseNum;
	private String storehouseName;
	private String storehouseType;
	private String remark;
	private Date createTime;
	private String validStatus;
	private Long deptId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStorehouseNum() {
		return storehouseNum;
	}
	public void setStorehouseNum(String storehouseNum) {
		this.storehouseNum = storehouseNum;
	}
	public String getStorehouseName() {
		return storehouseName;
	}
	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
	public String getStorehouseType() {
		return storehouseType;
	}
	public void setStorehouseType(String storehouseType) {
		this.storehouseType = storehouseType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptID(Long deptId) {
		this.deptId = deptId;
	}
	
	@Override
	public String toString() {
		return "TStoreHouse [id=" + id + ", storehouseNum=" + storehouseNum
				+ ", storehouseName=" + storehouseName + ", storehouseType="
				+ storehouseType + ", remark=" + remark + ", createTime="
				+ createTime + ", validStatus=" + validStatus + ", deptID="
				+ deptId + "]";
	}
}
