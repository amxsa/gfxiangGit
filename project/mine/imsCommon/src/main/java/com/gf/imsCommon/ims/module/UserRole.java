package com.gf.imsCommon.ims.module;

public class UserRole {
	
	private String id;
	private String userId;
	private String roleId;
	public UserRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserRole(String id, String userId, String roleId) {
		super();
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
