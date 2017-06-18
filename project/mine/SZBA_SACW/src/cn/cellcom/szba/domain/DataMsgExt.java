package cn.cellcom.szba.domain;

import java.util.Date;

import cn.cellcom.szba.common.DateTool;
import cn.cellcom.szba.common.Env;

public class DataMsgExt {
	private String state;
	private int code;
	private String msg;
	private Object object;
	private String time;
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
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
	public DataMsgExt(String state, int code, String msg,Object object) {
		this.state = state;
		this.code = code;
		this.msg = msg;
		this.object = object;
		this.time = DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	public DataMsgExt(String state, int code, String msg) {
		this.state = state;
		this.code = code;
		this.msg = msg;
		this.time = DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	public DataMsgExt(String state, int code) {
		this.state = state;
		this.code = code;
		this.msg = Env.CODE_RESULT.get(code);
		this.time = DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	public DataMsgExt(int code) {
		this.state = "F";
		this.code = code;
		this.msg = Env.CODE_RESULT.get(code);
		this.time = DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	public DataMsgExt(String msg) {
		this.state = "F";
		this.code = -999;
		this.msg = msg;
		this.time = DateTool.formateTime2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public DataMsgExt(){
		
	}



}
