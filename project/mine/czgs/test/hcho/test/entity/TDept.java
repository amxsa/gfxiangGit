package hcho.test.entity;

/**
 * TDept entity. @author MyEclipse Persistence Tools
 */

public class TDept implements java.io.Serializable {

	// Fields

	private String deptId;
	private TOrg TOrg;
	private String name;

	// Constructors

	/** default constructor */
	public TDept() {
	}

	/** full constructor */
	public TDept(TOrg TOrg, String name) {
		this.TOrg = TOrg;
		this.name = name;
	}

	// Property accessors

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public TOrg getTOrg() {
		return this.TOrg;
	}

	public void setTOrg(TOrg TOrg) {
		this.TOrg = TOrg;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}