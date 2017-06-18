package cn.cellcom.szba.vo;

import cn.cellcom.szba.domain.TMenu;

public class RoleMenuCheck extends TMenu{
	
	private String checked;
	private String roleId;
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
