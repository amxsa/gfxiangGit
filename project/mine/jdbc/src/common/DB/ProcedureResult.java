package common.DB;

import java.util.List;

public class ProcedureResult {
	private List<JdbcResultSet> resultList;
	private List<Object> outputList;

	public List<JdbcResultSet> getResultList() {
		return this.resultList;
	}

	public void setResultList(List<JdbcResultSet> resultList) {
		this.resultList = resultList;
	}

	public List<Object> getOutputList() {
		return this.outputList;
	}

	public void setOutputList(List<Object> outputList) {
		this.outputList = outputList;
	}
}
