package hcho.test.entity;

/**
 * TLeaderId entity. @author MyEclipse Persistence Tools
 */

public class TLeaderId implements java.io.Serializable {

	// Fields

	private TEmp TEmp;
	private String position;

	// Constructors

	/** default constructor */
	public TLeaderId() {
	}

	/** full constructor */
	public TLeaderId(TEmp TEmp, String position) {
		this.TEmp = TEmp;
		this.position = position;
	}

	// Property accessors

	public TEmp getTEmp() {
		return this.TEmp;
	}

	public void setTEmp(TEmp TEmp) {
		this.TEmp = TEmp;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TLeaderId))
			return false;
		TLeaderId castOther = (TLeaderId) other;

		return ((this.getTEmp() == castOther.getTEmp()) || (this.getTEmp() != null
				&& castOther.getTEmp() != null && this.getTEmp().equals(
				castOther.getTEmp())))
				&& ((this.getPosition() == castOther.getPosition()) || (this
						.getPosition() != null
						&& castOther.getPosition() != null && this
						.getPosition().equals(castOther.getPosition())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTEmp() == null ? 0 : this.getTEmp().hashCode());
		result = 37 * result
				+ (getPosition() == null ? 0 : this.getPosition().hashCode());
		return result;
	}

}