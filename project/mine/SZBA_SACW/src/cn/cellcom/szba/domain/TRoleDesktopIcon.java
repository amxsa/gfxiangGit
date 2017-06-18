package cn.cellcom.szba.domain;

import java.util.Date;

public class TRoleDesktopIcon {
  
	private long id;
	private long desktopIconId;
	private long roleId;
	private Date createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDesktopIconId() {
		return desktopIconId;
	}
	public void setDesktopIconId(long desktopIconId) {
		this.desktopIconId = desktopIconId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
