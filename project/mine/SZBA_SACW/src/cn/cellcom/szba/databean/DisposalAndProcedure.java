package cn.cellcom.szba.databean;

/**
 * 处置方式流程类
 * 
 * @author 陈奕平
 *
 */
public class DisposalAndProcedure {

	private Disposal disposal;
	private Procedure procedure;

	public DisposalAndProcedure() {
	}

	public DisposalAndProcedure(Disposal disposal, Procedure procedure) {
		this.disposal = disposal;
		this.procedure = procedure;
	}

	public Disposal getDisposal() {
		return disposal;
	}

	public void setDisposal(Disposal disposal) {
		this.disposal = disposal;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	@Override
	public String toString() {
		return "{Disposal:" + disposal.toString() + ", Procedure:" + procedure.toString() + "}";
	}

}
