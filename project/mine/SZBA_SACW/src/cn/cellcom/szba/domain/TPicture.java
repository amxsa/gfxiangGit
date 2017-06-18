package cn.cellcom.szba.domain;

import java.util.Date;

public class TPicture {

	private String picID;  //  照片记录编号
	private String type;   //  照片所属类型
	private String thumbnailUrl; // 照片缩略图路径
	private String originalUrl; //  照片原图路径
	private String picDescription; //描述
	private TAccount creator; //创建者
	private String caseID;   //案件id
	private String propertyID; //财物id
	private String elecEvidenceID; //电子物证id
	private String name;
	private Date createDate;   //   照片上传时间
	private Integer priority;
	
	private String captureTime;  //拍照时间
	private String capturePlace;  //拍照地址
	private String capturer;  //拍照人
	private Integer width;
	private Integer height;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	
	
	public TAccount getCreator() {
		return creator;
	}
	public void setCreator(TAccount creator) {
		this.creator = creator;
	}
	
	public String getPicID() {
		return picID;
	}
	public void setPicID(String picID) {
		this.picID = picID;
	}
	public String getPropertyID() {
		return propertyID;
	}
	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}
	public String getElecEvidenceID() {
		return elecEvidenceID;
	}
	public void setElecEvidenceID(String elecEvidenceID) {
		this.elecEvidenceID = elecEvidenceID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPicDescription() {
		return picDescription;
	}
	public void setPicDescription(String picDescription) {
		this.picDescription = picDescription;
	}
	public String getCaptureTime() {
		return captureTime;
	}
	public void setCaptureTime(String captureTime) {
		this.captureTime = captureTime;
	}
	public String getCapturePlace() {
		return capturePlace;
	}
	public void setCapturePlace(String capturePlace) {
		this.capturePlace = capturePlace;
	}
	public String getCapturer() {
		return capturer;
	}
	public void setCapturer(String capturer) {
		this.capturer = capturer;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
}
