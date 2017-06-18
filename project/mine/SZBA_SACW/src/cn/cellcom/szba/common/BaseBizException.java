package cn.cellcom.szba.common;

/**
 * 自定义异常
 * spring对继承RuntimeException的才会回滚
 * @author ourongkai
 *
 */
public class BaseBizException extends RuntimeException{
	//异常代码
	private int code;
	
	public BaseBizException() {
		super();
    }
	
	public BaseBizException(int code,String message) {
		super(message);
		this.code = code;
	}
	
	public BaseBizException(int code) {
		super(Env.CODE_RESULT.get(code));
		this.code = code;
	}
	
	public BaseBizException(String message) {
		super(message);
		this.code = Env.EXCCODE;
	}
	
	public BaseBizException(int code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
	
	public BaseBizException(String message, Throwable cause) {
		super(message, cause);
		this.code = Env.EXCCODE;
	}
	
	public BaseBizException(Throwable cause) {
        super(cause);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
