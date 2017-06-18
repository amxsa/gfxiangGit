package cn.cellcom.czt.common;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONArray;
import cn.cellcom.common.date.DateTool;


public class DataPo implements Serializable {
	private String state;
	private int code;
	private String msg;
	private String time;
	private JSONArray parambuf;
	
	
	
	
	public JSONArray getParambuf() {
		return parambuf;
	}
	public void setParambuf(JSONArray parambuf) {
		this.parambuf = parambuf;
	}
	public DataPo(String state, int code, String msg) {
		this.state = state;
		this.code = code;
		this.msg = msg;
		this.time = DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	public DataPo(String state, int code) {
		this.state = state;
		this.code = code;
		this.msg = Env.CODE_RESULT.get(code);
		this.time = DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		if(Env.CODE_RESULT.containsKey(code)){
			this.msg = Env.CODE_RESULT.get(code);
		}
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public DataPo(){
		
	}
}
