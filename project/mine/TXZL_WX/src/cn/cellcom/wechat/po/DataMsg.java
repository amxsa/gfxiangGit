package cn.cellcom.wechat.po;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

public class DataMsg implements Serializable {
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public JSONObject getObj() {
		return obj;
	}

	public void setObj(JSONObject obj) {
		this.obj = obj;
	}

	private String msg;
	private List list;

	public List<Object> getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	private boolean state;
	private JSONObject obj;

	public DataMsg(boolean state, String msg) {
		this.state = state;
		this.msg = msg;
	}
	public DataMsg(){
		
	}
}
