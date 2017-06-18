package cn.cellcom.szba.domain;

import java.util.Date;

public class TAppProTransLog {
	private Long id;
	private Long applicationId;
	private String propertyId;
	private String propertyStatus;
	private String actualPosition;
	private Date createTime;
	private String account;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	public String getActualPosition() {
		return actualPosition;
	}
	public void setActualPosition(String actualPosition) {
		this.actualPosition = actualPosition;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
}
