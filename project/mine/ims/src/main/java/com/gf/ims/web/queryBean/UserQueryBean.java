package com.gf.ims.web.queryBean;

public class UserQueryBean extends BaseQueryBean{
	private String userName;

	private String userType;
	
	private String nickName;
	
	private String userAccount;
	
	private String mobile;

	public UserQueryBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserQueryBean(int pageSize, int pageNumber) {
		super(pageSize, pageNumber);
		// TODO Auto-generated constructor stub
	}

	public UserQueryBean(String userName, String userType, String nickName, String userAccount, String mobile) {
		super();
		this.userName = userName;
		this.userType = userType;
		this.nickName = nickName;
		this.userAccount = userAccount;
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
