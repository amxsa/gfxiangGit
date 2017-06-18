package cn.cellcom.szba.common;

public enum ResultCode {
	SUCCESS("S", "成功"), 
	FAILED("F", "失败"),
	PARAM_ERROR("1002", " 请求参数异常"), 
	NO_DATA("1003", "没有数据"),
	NO_RIGHT("1004", "没有权限访问资源"),
	NO_LOGIN("1005", "尚未登录或登录失效"),
	OTHER("3001","其他");

	private String code;

	private String msg;

	private ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
