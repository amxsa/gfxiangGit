package com.gf.imsCommon.ims.module;

import java.util.Date;

public class User {
	
	private String id;
	private String userType;
	private String userName;
	private String nickName;
	private String userAccount;
	private String password;
	private String mobile;
	private String accountStatus;
	private Date createTime;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String id, String userType, String userName, String nickName, String userAccount, String password,
			String mobile, String accountStatus, Date createTime) {
		super();
		this.id = id;
		this.userType = userType;
		this.userName = userName;
		this.nickName = nickName;
		this.userAccount = userAccount;
		this.password = password;
		this.mobile = mobile;
		this.accountStatus = accountStatus;
		this.createTime = createTime;
	}
	
	public User(String id, String userType, String userAccount, String mobile) {
		super();
		this.id = id;
		this.userType = userType;
		this.userAccount = userAccount;
		this.mobile = mobile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
