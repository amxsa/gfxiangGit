package common.DB;

public class ProcedureParameter {
	private Object parameterValue;
	private JDBC.PROCEDURE_PARAMTER_TYPE type;
	public ProcedureParameter(){
		
	}
	public ProcedureParameter(Object parameterValue,
			JDBC.PROCEDURE_PARAMTER_TYPE type) {
		this.parameterValue = parameterValue;
		this.type = type;
	}

	public Object getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}

	public JDBC.PROCEDURE_PARAMTER_TYPE getType() {
		return this.type;
	}

	public void setType(JDBC.PROCEDURE_PARAMTER_TYPE type) {
		this.type = type;
	}

	public String toString() {
		return "[parameterValue=" + this.parameterValue + ", type=" + this.type
				+ "]";
	}
}
