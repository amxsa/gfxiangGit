package cn.cellcom.szba.vo;

public class MyFormKeyResult {
	//同意时处理人列表
	String agreeHandlers;
	
	//不同意时处理人列表
	String disagreeHandlers;
	
	
	//财物状态,分别为同意和不同意的状态
	String propertyStatus;
	//申请单状态,分别为同意和不同意的状态
	String applicationStatus;
	
	
	
	public String getAgreeHandlers() {
		return agreeHandlers;
	}
	public void setAgreeHandlers(String agreeHandlers) {
		this.agreeHandlers = agreeHandlers;
	}
	public String getDisagreeHandlers() {
		return disagreeHandlers;
	}
	public void setDisagreeHandlers(String disagreeHandlers) {
		this.disagreeHandlers = disagreeHandlers;
	}
	public String getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	@Override
	public String toString() {
		return "MyFormKeyResult [agreeHandlers=" + agreeHandlers
				+ ", disagreeHandlers=" + disagreeHandlers
				+ ", propertyStatus=" + propertyStatus + ", applicationStatus="
				+ applicationStatus + "]";
	}
}
