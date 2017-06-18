package cn.cellcom.szba.domain;

import java.util.List;

//by pansenxin
public class TPropertyDetail {

	private TProperty tproperty;
	private List<TPicture> tpictureList;  //图片信息
	private TCivilian ownerInfo;  //持有人信息
	private TCivilian everyOneInfo;  //所有人信息
	private TCase caseInfo;  //案件信息
	private TCivilian bgPerson;  //保管人信息
	private TCivilian jzPerson;  //见证人信息
	private TCivilian dsPerson;   //当事人信息
	private TIdentify identifyInfo;  //鉴定信息
	private List<TAttachment> attachmentList;  //附件信息
	
	public TProperty getTproperty() {
		return tproperty;
	}
	public void setTproperty(TProperty tproperty) {
		this.tproperty = tproperty;
	}
	public List<TPicture> getTpictureList() {
		return tpictureList;
	}
	public void setTpictureList(List<TPicture> tpictureList) {
		this.tpictureList = tpictureList;
	}
	public TCivilian getOwnerInfo() {
		return ownerInfo;
	}
	public void setOwnerInfo(TCivilian ownerInfo) {
		this.ownerInfo = ownerInfo;
	}
	public TCivilian getEveryOneInfo() {
		return everyOneInfo;
	}
	public void setEveryOneInfo(TCivilian everyOneInfo) {
		this.everyOneInfo = everyOneInfo;
	}
	public TCase getCaseInfo() {
		return caseInfo;
	}
	public void setCaseInfo(TCase caseInfo) {
		this.caseInfo = caseInfo;
	}
	public TCivilian getBgPerson() {
		return bgPerson;
	}
	public void setBgPerson(TCivilian bgPerson) {
		this.bgPerson = bgPerson;
	}
	public TCivilian getJzPerson() {
		return jzPerson;
	}
	public void setJzPerson(TCivilian jzPerson) {
		this.jzPerson = jzPerson;
	}
	public TCivilian getDsPerson() {
		return dsPerson;
	}
	public void setDsPerson(TCivilian dsPerson) {
		this.dsPerson = dsPerson;
	}
	public TIdentify getIdentifyInfo() {
		return identifyInfo;
	}
	public void setIdentifyInfo(TIdentify identifyInfo) {
		this.identifyInfo = identifyInfo;
	}
	public List<TAttachment> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<TAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
}
