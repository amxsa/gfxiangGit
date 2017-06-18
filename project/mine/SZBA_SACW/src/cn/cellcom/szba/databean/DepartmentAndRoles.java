package cn.cellcom.szba.databean;

import java.util.List;

public class DepartmentAndRoles {
	private Long departmentID;
	private List<Long> roleIDs;

	public Long getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(Long departmentID) {
		this.departmentID = departmentID;
	}

	public List<Long> getRoleIDs() {
		return roleIDs;
	}

	public void setRoleIDs(List<Long> roleIDs) {
		this.roleIDs = roleIDs;
	}

	public DepartmentAndRoles() {
	}

	public DepartmentAndRoles(Long departmentID, List<Long> roleIDs) {
		super();
		this.departmentID = departmentID;
		this.roleIDs = roleIDs;
	}

	@Override
	public String toString() {
		return "DepartmentAndRoles [departmentID=" + departmentID
				+ ", roleIDs=" + roleIDs + "]";
	}
}
