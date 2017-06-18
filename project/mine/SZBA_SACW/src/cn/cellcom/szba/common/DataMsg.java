package cn.cellcom.szba.common;

import net.sf.json.JSONObject;

public class DataMsg {
	private boolean state;
	private int code;
	private String msg;
	private JSONObject data;
	private String time;
	private Object obj;
	
	public DataMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public DataMsg() {

	}

	public DataMsg(boolean state,String msg) {
		this.state = state;
		this.msg = msg;
	}
	

}
