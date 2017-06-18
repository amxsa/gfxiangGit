package cn.cellcom.czt.po;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class DataMsg implements Serializable {
	private String state="success";
	private String  referer="";
	private String info = null;
	private String status="Correct";
	private JSONObject data;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	public DataMsg() {

	}
	public DataMsg(String state, String info) {
		this.state = state;
		this.info = info;
	}
	
	
	
}
