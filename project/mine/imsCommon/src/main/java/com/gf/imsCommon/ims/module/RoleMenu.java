package com.gf.imsCommon.ims.module;

public class RoleMenu {
	
	private String id;
	private String roleId;
	private String menuId;
	public RoleMenu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RoleMenu(String id, String roleId, String menuId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.menuId = menuId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
