package cn.cellcom.szba.vo;

import java.math.BigDecimal;
import java.util.Date;

public class PropertyVO {
	
	private String jzcaseId;
	
	private String caseName;
	
	private Date registerDate ;
	
	private String suspectName;
	
	private Date handleTime;//办理时间
	
	private String handler;//经办人
	
	//private String warename;//库位名	
	
	private String civiName; //持有人姓名
	
	private Date intoHouseTime; //财物入库时间
    
    private String intoHousePerson; //财物入库经办人
    
    private Date outHouseTime;   //财物出库时间
    
    private String outHousePerson; //财物出库经办人
    
    private String locationName;  //库位名称
    
    private Long locationId;  //库位编号
    
    private String storehouseName; //所属仓库名
    
    private String storehouseNum; //所属仓库编号
    
    private String handleResultStatus; //处置登记状态
    
    private Long handleResultId; //处置登记表id
    
    private Long storeNum;  //所需库位大小

    private String categoryName;//财物类别
    
	private String damageReason; //损毁原因
	//------以上为非t_property表的字段
	private String id;

	private String name;

	private Double quantity;

	private String owner;

	private String phone;

	private String characteristic;

	private String position;

	private String method;

	private String place;

	private String authority;

	private String remark;

	private Date createTime;

	private String creator;

	private Long priority;

	private String validStatus;

	private String unit;

	private String rfidNum;

	private Long warehouseId;

	private String processStatus;

	private String seizureBasis;

	private BigDecimal categoryId;

	private String status;

	private String nature;

	private String seizurePlace;

	private String type;

	private String origin;

	private String tableType;

	private String saveDemand;

	private String saveMethod;

	private String enviDemand;

	private String mixId;

	private String caseId;

	private String transactor;

	private String isSign;

	private String actualPosition;
	
    private String appraiResult;

    private String appraiSituation;
    
    private String fromPart;
    
    private String proNo;
    
    private String isRunning;
    
    private String damageStatus; //损毁状态

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRfidNum() {
		return rfidNum;
	}

	public void setRfidNum(String rfidNum) {
		this.rfidNum = rfidNum;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getSeizureBasis() {
		return seizureBasis;
	}

	public void setSeizureBasis(String seizureBasis) {
		this.seizureBasis = seizureBasis;
	}

	public BigDecimal getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getSeizurePlace() {
		return seizurePlace;
	}

	public void setSeizurePlace(String seizurePlace) {
		this.seizurePlace = seizurePlace;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getSaveDemand() {
		return saveDemand;
	}

	public void setSaveDemand(String saveDemand) {
		this.saveDemand = saveDemand;
	}

	public String getSaveMethod() {
		return saveMethod;
	}

	public void setSaveMethod(String saveMethod) {
		this.saveMethod = saveMethod;
	}

	public String getEnviDemand() {
		return enviDemand;
	}

	public void setEnviDemand(String enviDemand) {
		this.enviDemand = enviDemand;
	}

	public String getMixId() {
		return mixId;
	}

	public void setMixId(String mixId) {
		this.mixId = mixId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(String actualPosition) {
		this.actualPosition = actualPosition;
	}

	public String getAppraiResult() {
		return appraiResult;
	}

	public void setAppraiResult(String appraiResult) {
		this.appraiResult = appraiResult;
	}

	public String getAppraiSituation() {
		return appraiSituation;
	}

	public void setAppraiSituation(String appraiSituation) {
		this.appraiSituation = appraiSituation;
	}
	
	public String getFromPart() {
		return fromPart;
	}

	public void setFromPart(String fromPart) {
		this.fromPart = fromPart;
	}

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public String getJzcaseId() {
		return jzcaseId!=null?jzcaseId:"未关联案件";
	}

	public void setJzcaseId(String jzcaseId) {
		this.jzcaseId = jzcaseId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getStorehouseName() {
		return storehouseName;
	}

	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}
	
	public String getCiviName() {
		return civiName;
	}

	public void setCiviName(String civiName) {
		this.civiName = civiName;
	}
	
	public Date getIntoHouseTime() {
		return intoHouseTime;
	}

	public void setIntoHouseTime(Date intoHouseTime) {
		this.intoHouseTime = intoHouseTime;
	}

	public String getIntoHousePerson() {
		return intoHousePerson;
	}

	public void setIntoHousePerson(String intoHousePerson) {
		this.intoHousePerson = intoHousePerson;
	}

	public Date getOutHouseTime() {
		return outHouseTime;
	}

	public void setOutHouseTime(Date outHouseTime) {
		this.outHouseTime = outHouseTime;
	}

	public String getOutHousePerson() {
		return outHousePerson;
	}

	public void setOutHousePerson(String outHousePerson) {
		this.outHousePerson = outHousePerson;
	}


	public String getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(String isRunning) {
		this.isRunning = isRunning;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(String storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getHandleResultStatus() {
		return handleResultStatus;
	}

	public void setHandleResultStatus(String handleResultStatus) {
		this.handleResultStatus = handleResultStatus;
	}

	public Long getHandleResultId() {
		return handleResultId;
	}

	public void setHandleResultId(Long handleResultId) {
		this.handleResultId = handleResultId;
	}
	
	public Long getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Long storeNum) {
		this.storeNum = storeNum;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDamageReason() {
		return damageReason;
	}

	public void setDamageReason(String damageReason) {
		this.damageReason = damageReason;
	}

	public String getDamageStatus() {
		return damageStatus;
	}

	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	@Override
	public String toString() {
		return "PropertyVO [jzcaseId=" + jzcaseId + ", caseName=" + caseName
				+ ", registerDate=" + registerDate + ", suspectName="
				+ suspectName + ", handleTime=" + handleTime + ", handler="
				+ handler + ", civiName=" + civiName + ", intoHouseTime="
				+ intoHouseTime + ", intoHousePerson=" + intoHousePerson
				+ ", outHouseTime=" + outHouseTime + ", outHousePerson="
				+ outHousePerson + ", locationName=" + locationName
				+ ", locationId=" + locationId + ", storehouseName="
				+ storehouseName + ", storehouseNum=" + storehouseNum
				+ ", handleResultStatus=" + handleResultStatus
				+ ", handleResultId=" + handleResultId + ", storeNum="
				+ storeNum + ", categoryName=" + categoryName
				+ ", damageReason=" + damageReason + ", id=" + id + ", name="
				+ name + ", quantity=" + quantity + ", owner=" + owner
				+ ", phone=" + phone + ", characteristic=" + characteristic
				+ ", position=" + position + ", method=" + method + ", place="
				+ place + ", authority=" + authority + ", remark=" + remark
				+ ", createTime=" + createTime + ", creator=" + creator
				+ ", priority=" + priority + ", validStatus=" + validStatus
				+ ", unit=" + unit + ", rfidNum=" + rfidNum + ", warehouseId="
				+ warehouseId + ", processStatus=" + processStatus
				+ ", seizureBasis=" + seizureBasis + ", categoryId="
				+ categoryId + ", status=" + status + ", nature=" + nature
				+ ", seizurePlace=" + seizurePlace + ", type=" + type
				+ ", origin=" + origin + ", tableType=" + tableType
				+ ", saveDemand=" + saveDemand + ", saveMethod=" + saveMethod
				+ ", enviDemand=" + enviDemand + ", mixId=" + mixId
				+ ", caseId=" + caseId + ", transactor=" + transactor
				+ ", isSign=" + isSign + ", actualPosition=" + actualPosition
				+ ", appraiResult=" + appraiResult + ", appraiSituation="
				+ appraiSituation + ", fromPart=" + fromPart + ", proNo="
				+ proNo + ", isRunning=" + isRunning + ", damageStatus="
				+ damageStatus + "]";
	}

	//以下兼容原来的JSP
	public String getProId() {
		return id;
	}
	public Double getProQuantity() {
		return quantity;
	}
	
	

}