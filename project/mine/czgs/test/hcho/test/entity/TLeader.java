package hcho.test.entity;

/**
 * TLeader entity. @author MyEclipse Persistence Tools
 */

public class TLeader implements java.io.Serializable {

	// Fields

	private TLeaderId id;
	private String deptId;
	private String name;

	// Constructors

	/** default constructor */
	public TLeader() {
	}

	/** minimal constructor */
	public TLeader(TLeaderId id) {
		this.id = id;
	}

	/** full constructor */
	public TLeader(TLeaderId id, String deptId, String name) {
		this.id = id;
		this.deptId = deptId;
		this.name = name;
	}

	// Property accessors

	public TLeaderId getId() {
		return this.id;
	}

	public void setId(TLeaderId id) {
		this.id = id;
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

}