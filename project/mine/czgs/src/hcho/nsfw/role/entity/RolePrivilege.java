package hcho.nsfw.role.entity;

import java.io.Serializable;

public class RolePrivilege implements Serializable{
	
	private RolePrivilegeId id;

	public RolePrivilege() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RolePrivilege(RolePrivilegeId id) {
		super();
		this.id = id;
	}

	public RolePrivilegeId getId() {
		return id;
	}

	public void setId(RolePrivilegeId id) {
		this.id = id;
	}
	
	
}
