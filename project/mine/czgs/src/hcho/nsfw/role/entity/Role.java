package hcho.nsfw.role.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable{
	
	private String roleId;
	private String name;
	private String state;
	private Set<RolePrivilege> privileges=new HashSet<RolePrivilege>();
	
	public static String ROLE_STATE_VALID="1";
	public static String ROLE_STATE_INVALID="0";
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String roleId) {
		super();
		this.roleId = roleId;
	}
	public Role(String roleId, String name, String state,
			Set<RolePrivilege> privileges) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.state = state;
		this.privileges = privileges;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<RolePrivilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<RolePrivilege> privileges) {
		this.privileges = privileges;
	}
	
}
