package cn.cellcom.szba.domain;

/**
 * 一个通用的http响应体包含的数据信息
 * @author guoguo
 *
 */
public class DataMsg {
	private String state;//状态，一般Y为成功。F为失败。
	private String code; //错误号，state=F时有意义
	private String msg;  //错误号对应的错误消息
	private String timestamp;//时间
	private Object obj; //数据
	
	public DataMsg() {
	}
	
	public DataMsg(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public DataMsg(String state, String code, String msg) {
		this.state = state;
		this.code = code;
		this.msg = msg;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "DataMsg [state=" + state + ", code=" + code + ", msg=" + msg
				+ ", timestamp=" + timestamp + ", obj=" + obj + "]";
	}
}
