package cn.cellcom.czt.po;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

public class Data implements Serializable {
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
	private List<Object> list;
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	private boolean state;
	private JSONObject obj;
	
	public Data(boolean state,String msg){
		this.state = state;
		this.msg = msg;
	}
}
