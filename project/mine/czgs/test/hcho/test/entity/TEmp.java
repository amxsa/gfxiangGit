package hcho.test.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TEmp entity. @author MyEclipse Persistence Tools
 */

public class TEmp implements java.io.Serializable {

	// Fields

	private String empId;
	private String deptId;
	private String name;
	private Set REmpRoles = new HashSet(0);
	private Set TLeaders = new HashSet(0);

	// Constructors

	/** default constructor */
	public TEmp() {
	}

	/** minimal constructor */
	public TEmp(String deptId) {
		this.deptId = deptId;
	}

	/** full constructor */
	public TEmp(String deptId, String name, Set REmpRoles, Set TLeaders) {
		this.deptId = deptId;
		this.name = name;
		this.REmpRoles = REmpRoles;
		this.TLeaders = TLeaders;
	}

	// Property accessors

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getREmpRoles() {
		return this.REmpRoles;
	}

	public void setREmpRoles(Set REmpRoles) {
		this.REmpRoles = REmpRoles;
	}

	public Set getTLeaders() {
		return this.TLeaders;
	}

	public void setTLeaders(Set TLeaders) {
		this.TLeaders = TLeaders;
	}

}