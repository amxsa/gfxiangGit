package cn.cellcom.szba.vo.template;

public class TemplateMessage {
   
	private String casename;
	private String proname;
	private String transactor;
	
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getTransactor() {
		return transactor;
	}
	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}
	
	@Override
	public String toString() {
		return "templateMessage [casename=" + casename + ", proname=" + proname
				+ ", transactor=" + transactor + "]";
	}
	
	
}
