package cn.cellcom.szba.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TElecEvidence {
	private String elecEvidenceID;
	private String name;
	private double quantity;
	private String unit;
	private String characteristic;
	private int priority;
	private String remark;
	private TAccount creator;
	private Date createTime;
	private String elecRecordID;
	private List<TPicture> photographs;
	
	private String caseId;
	private String caseName;
	
	private String sign;
	
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public TElecEvidence() {
		photographs = new ArrayList<TPicture>();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TAccount getCreator() {
		return creator;
	}

	public void setCreator(TAccount creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getElecEvidenceID() {
		return elecEvidenceID;
	}

	public void setElecEvidenceID(String elecEvidenceID) {
		this.elecEvidenceID = elecEvidenceID;
	}

	public String getElecRecordID() {
		return elecRecordID;
	}

	public void setElecRecordID(String elecRecordID) {
		this.elecRecordID = elecRecordID;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public List<TPicture> getPhotographs() {
		return photographs;
	}

	public void setPhotographs(List<TPicture> photographs) {
		this.photographs = photographs;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
}
