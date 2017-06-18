package cn.cellcom.szba.flowdomain;

public class FlowConf {

	private String flowId;
	private String flowName;
	 
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	@Override
	public String toString() {
		return "flowConf [flowId=" + flowId + ", flowName=" + flowName + "]";
	}
	 
	 
}
