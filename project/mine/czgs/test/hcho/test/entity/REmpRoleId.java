package hcho.test.entity;

/**
 * REmpRoleId entity. @author MyEclipse Persistence Tools
 */

public class REmpRoleId implements java.io.Serializable {

	// Fields

	private TEmp TEmp;
	private String roleId;

	// Constructors

	/** default constructor */
	public REmpRoleId() {
	}

	/** full constructor */
	public REmpRoleId(TEmp TEmp, String roleId) {
		this.TEmp = TEmp;
		this.roleId = roleId;
	}

	// Property accessors

	public TEmp getTEmp() {
		return this.TEmp;
	}

	public void setTEmp(TEmp TEmp) {
		this.TEmp = TEmp;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof REmpRoleId))
			return false;
		REmpRoleId castOther = (REmpRoleId) other;

		return ((this.getTEmp() == castOther.getTEmp()) || (this.getTEmp() != null
				&& castOther.getTEmp() != null && this.getTEmp().equals(
				castOther.getTEmp())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this
						.getRoleId() != null && castOther.getRoleId() != null && this
						.getRoleId().equals(castOther.getRoleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTEmp() == null ? 0 : this.getTEmp().hashCode());
		result = 37 * result
				+ (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}

}