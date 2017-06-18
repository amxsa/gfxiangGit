package hcho.test.entity;

/**
 * RolePriId entity. @author MyEclipse Persistence Tools
 */

public class RolePriId implements java.io.Serializable {

	// Fields

	private TPrivilege TPrivilege;
	private TRole TRole;

	// Constructors

	/** default constructor */
	public RolePriId() {
	}

	/** full constructor */
	public RolePriId(TPrivilege TPrivilege, TRole TRole) {
		this.TPrivilege = TPrivilege;
		this.TRole = TRole;
	}

	// Property accessors

	public TPrivilege getTPrivilege() {
		return this.TPrivilege;
	}

	public void setTPrivilege(TPrivilege TPrivilege) {
		this.TPrivilege = TPrivilege;
	}

	public TRole getTRole() {
		return this.TRole;
	}

	public void setTRole(TRole TRole) {
		this.TRole = TRole;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolePriId))
			return false;
		RolePriId castOther = (RolePriId) other;

		return ((this.getTPrivilege() == castOther.getTPrivilege()) || (this
				.getTPrivilege() != null && castOther.getTPrivilege() != null && this
				.getTPrivilege().equals(castOther.getTPrivilege())))
				&& ((this.getTRole() == castOther.getTRole()) || (this
						.getTRole() != null && castOther.getTRole() != null && this
						.getTRole().equals(castOther.getTRole())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTPrivilege() == null ? 0 : this.getTPrivilege()
						.hashCode());
		result = 37 * result
				+ (getTRole() == null ? 0 : this.getTRole().hashCode());
		return result;
	}

}