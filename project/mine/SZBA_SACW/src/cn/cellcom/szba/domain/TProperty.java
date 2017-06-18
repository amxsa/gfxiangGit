package cn.cellcom.szba.domain;

import java.util.ArrayList;
import java.util.List;

public class TProperty {
	private String proId;
	private String proName; // 财物名称
	private Double proQuantity; // 财物数量
	private String proUnit;
	private String proOwner; // 财物持有者名称
	private String proPhone; // 持有者手机
	private String proCharacteristic; // 财物特征
	private String proPosition; // 提取部位，对应提取痕迹物证登记表的提取部位字段，其他清单类型该字段为null
	private String proMethod; // 对应提取痕迹物证登记表的提取方法字段
	private String proPlace; // 财物所在地
	private String proDepartment; // 登记机关
	private String proRfidNum; // 电子标签编号
	private Long warehouseId;// 库位id
	private String wareSerialNumber; // 库位编号
	private TWarehouse warehouse; // 库位
	private String proProcessStatus; // 流程状态
	private String proSeizureBasis; // 扣押依据
	private TCategory categroy;
	private String proStatus;
	private String proNature;
	private String proSeizurePlace;
	private String proType;
	private String proOrigin;
	private String proRemark; // 备注
	private String proPriority;
	private String saveDemand; // 保管要求
	private String saveMethod; // 保存方式
	private String enviDemand; // 存储条件要求
	private String appraiResult; // 鉴定结论
	private String appraiSituation; // 鉴定情况
	private TAccount creator;
	private String account; //
	private List<TPicture> photographs; // 照片集合
	private String validStatus;
	private String actualPosition; // 财物实际位置

	private String caseId;
	private String jzcaseId;
	private String caseName;

	private String civiName;

	private Long categoryId;// 财务分类Id
	private String categoryName;
	private String listId;
	private String proMixId;
	private String tableType;

	private Long deptId;
	private String transactor;
	
	private String proIsSave;  //是否库中保存 1是，2否
	private String proSavePlace;  //保存地址
	private Integer proEvaluateValue;  //评估价值
	
	private String ownerInfo;  //财物持有人信息
	private String everyOneInfo; //财物所有人信息
	private String cateAttrInfo;  //财物分类属性
	private String bgPerson;  //保管人信息
	private String jzPerson;  //见证人信息
	private String dsPerson;  //当事人信息
	private String identifyInfo;  //鉴定信息
	private Long identifyId;  //鉴定信息id 
	
	private String proNo;  //财物编号
	private String disposalKey; //对于财物处置时候，对于单个财物的处置方式表示
	private String disposalReason; //对于财物处置时候，对于单个财物的处置申请理由
	private String isRunning;
	private String proSpec;
	private Long storeNum;  //财物入库时所占用的大小
	private Long storeHouseId;  //仓库id
	private String proFormPart;  //渠道 APP/web
	private String damageStatus; //损毁状态
	private String damageReason; //损毁原因
	
	public TProperty() {
		photographs = new ArrayList<TPicture>();
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Double getProQuantity() {
		return proQuantity;
	}

	public void setProQuantity(Double proQuantity) {
		this.proQuantity = proQuantity;
	}

	public String getProUnit() {
		return proUnit;
	}

	public void setProUnit(String proUnit) {
		this.proUnit = proUnit;
	}

	public String getProOwner() {
		return proOwner;
	}

	public void setProOwner(String proOwner) {
		this.proOwner = proOwner;
	}

	public String getProPhone() {
		return proPhone;
	}

	public void setProPhone(String proPhone) {
		this.proPhone = proPhone;
	}

	public String getProCharacteristic() {
		return proCharacteristic;
	}

	public void setProCharacteristic(String proCharacteristic) {
		this.proCharacteristic = proCharacteristic;
	}

	public String getProPosition() {
		return proPosition;
	}

	public void setProPosition(String proPosition) {
		this.proPosition = proPosition;
	}

	public String getProMethod() {
		return proMethod;
	}

	public void setProMethod(String proMethod) {
		this.proMethod = proMethod;
	}

	public String getProPlace() {
		return proPlace;
	}

	public void setProPlace(String proPlace) {
		this.proPlace = proPlace;
	}

	public String getProDepartment() {
		return proDepartment;
	}

	public void setProDepartment(String proDepartment) {
		this.proDepartment = proDepartment;
	}

	public String getProRfidNum() {
		return proRfidNum;
	}

	public void setProRfidNum(String proRfidNum) {
		this.proRfidNum = proRfidNum;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWareSerialNumber() {
		return wareSerialNumber;
	}

	public void setWareSerialNumber(String wareSerialNumber) {
		this.wareSerialNumber = wareSerialNumber;
	}

	public TWarehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(TWarehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getProProcessStatus() {
		return proProcessStatus;
	}

	public void setProProcessStatus(String proProcessStatus) {
		this.proProcessStatus = proProcessStatus;
	}

	public String getProSeizureBasis() {
		return proSeizureBasis;
	}

	public void setProSeizureBasis(String proSeizureBasis) {
		this.proSeizureBasis = proSeizureBasis;
	}

	public TCategory getCategroy() {
		return categroy;
	}

	public void setCategroy(TCategory categroy) {
		this.categroy = categroy;
	}

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public String getProNature() {
		return proNature;
	}

	public void setProNature(String proNature) {
		this.proNature = proNature;
	}

	public String getProSeizurePlace() {
		return proSeizurePlace;
	}

	public void setProSeizurePlace(String proSeizurePlace) {
		this.proSeizurePlace = proSeizurePlace;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getProOrigin() {
		return proOrigin;
	}

	public void setProOrigin(String proOrigin) {
		this.proOrigin = proOrigin;
	}

	public String getProRemark() {
		return proRemark;
	}

	public void setProRemark(String proRemark) {
		this.proRemark = proRemark;
	}

	public String getProPriority() {
		return proPriority;
	}

	public void setProPriority(String proPriority) {
		this.proPriority = proPriority;
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

	public TAccount getCreator() {
		return creator;
	}

	public void setCreator(TAccount creator) {
		this.creator = creator;
	}

	public List<TPicture> getPhotographs() {
		return photographs;
	}

	public void setPhotographs(List<TPicture> photographs) {
		this.photographs = photographs;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getJzcaseId() {
		return jzcaseId;
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

	public String getCiviName() {
		return civiName;
	}

	public void setCiviName(String civiName) {
		this.civiName = civiName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getProMixId() {
		return proMixId;
	}

	public void setProMixId(String proMixId) {
		this.proMixId = proMixId;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	public String getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(String actualPosition) {
		this.actualPosition = actualPosition;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getProIsSave() {
		return proIsSave;
	}

	public void setProIsSave(String proIsSave) {
		this.proIsSave = proIsSave;
	}

	public String getProSavePlace() {
		return proSavePlace;
	}

	public void setProSavePlace(String proSavePlace) {
		this.proSavePlace = proSavePlace;
	}

	public Integer getProEvaluateValue() {
		return proEvaluateValue;
	}

	public void setProEvaluateValue(Integer proEvaluateValue) {
		this.proEvaluateValue = proEvaluateValue;
	}

	public String getOwnerInfo() {
		return ownerInfo;
	}

	public void setOwnerInfo(String ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

	public String getEveryOneInfo() {
		return everyOneInfo;
	}

	public void setEveryOneInfo(String everyOneInfo) {
		this.everyOneInfo = everyOneInfo;
	}

	public String getCateAttrInfo() {
		return cateAttrInfo;
	}

	public void setCateAttrInfo(String cateAttrInfo) {
		this.cateAttrInfo = cateAttrInfo;
	}

	public String getBgPerson() {
		return bgPerson;
	}

	public void setBgPerson(String bgPerson) {
		this.bgPerson = bgPerson;
	}

	public String getJzPerson() {
		return jzPerson;
	}

	public void setJzPerson(String jzPerson) {
		this.jzPerson = jzPerson;
	}

	public String getDsPerson() {
		return dsPerson;
	}

	public void setDsPerson(String dsPerson) {
		this.dsPerson = dsPerson;
	}

	public String getIdentifyInfo() {
		return identifyInfo;
	}

	public void setIdentifyInfo(String identifyInfo) {
		this.identifyInfo = identifyInfo;
	}

	public Long getIdentifyId() {
		return identifyId;
	}

	public void setIdentifyId(Long identifyId) {
		this.identifyId = identifyId;
	}

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public String getDisposalKey() {
		return disposalKey;
	}

	public void setDisposalKey(String disposalKey) {
		this.disposalKey = disposalKey;
	}

	public String getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(String isRunning) {
		this.isRunning = isRunning;
	}

	public String getProSpec() {
		return proSpec;
	}

	public void setProSpec(String proSpec) {
		this.proSpec = proSpec;
	}

	public String getDisposalReason() {
		return disposalReason;
	}

	public void setDisposalReason(String disposalReason) {
		this.disposalReason = disposalReason;
	}

	public Long getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Long storeNum) {
		this.storeNum = storeNum;
	}

	public Long getStoreHouseId() {
		return storeHouseId;
	}

	public void setStoreHouseId(Long storeHouseId) {
		this.storeHouseId = storeHouseId;
	}

	public String getProFormPart() {
		return proFormPart;
	}

	public void setProFormPart(String proFormPart) {
		this.proFormPart = proFormPart;
	}

	public String getDamageStatus() {
		return damageStatus;
	}

	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	public String getDamageReason() {
		return damageReason;
	}

	public void setDamageReason(String damageReason) {
		this.damageReason = damageReason;
	}
	
}
