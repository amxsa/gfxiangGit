package com.gf.imsCommon.ims.module;

import java.util.Date;

public class Role {
	
	private String id;
	private String roleName;
	private String roleNote;
	private String createAccount;
	private Date createTime;
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String id, String roleName, String roleNote, String createAccount, Date createTime) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.roleNote = roleNote;
		this.createAccount = createAccount;
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleNote() {
		return roleNote;
	}
	public void setRoleNote(String roleNote) {
		this.roleNote = roleNote;
	}
	public String getCreateAccount() {
		return createAccount;
	}
	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
