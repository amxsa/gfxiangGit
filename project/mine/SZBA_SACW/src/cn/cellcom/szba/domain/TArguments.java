package cn.cellcom.szba.domain;

public class TArguments {
	private String service;
	private int	flag;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "TArguments [service=" + service + ", flag=" + flag + "]";
	} 
	
}
