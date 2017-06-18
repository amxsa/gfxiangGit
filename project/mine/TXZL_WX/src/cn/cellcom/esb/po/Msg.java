package cn.cellcom.esb.po;

public class Msg {
	private boolean flag;
	private String msg;
	
	
	public Msg(){
		
	}
	public Msg(boolean flag ,String msg){
		this.flag = flag;
		this.msg = msg;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
