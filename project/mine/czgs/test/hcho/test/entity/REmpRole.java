package hcho.test.entity;

/**
 * REmpRole entity. @author MyEclipse Persistence Tools
 */

public class REmpRole implements java.io.Serializable {

	// Fields

	private REmpRoleId id;
	private String state;

	// Constructors

	/** default constructor */
	public REmpRole() {
	}

	/** minimal constructor */
	public REmpRole(REmpRoleId id) {
		this.id = id;
	}

	/** full constructor */
	public REmpRole(REmpRoleId id, String state) {
		this.id = id;
		this.state = state;
	}

	// Property accessors

	public REmpRoleId getId() {
		return this.id;
	}

	public void setId(REmpRoleId id) {
		this.id = id;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}