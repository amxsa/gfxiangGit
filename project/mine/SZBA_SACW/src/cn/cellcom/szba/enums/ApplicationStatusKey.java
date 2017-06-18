package cn.cellcom.szba.enums;

/**
 * 申请单状态枚举类
 * 
 * @author 郭克城
 * 
 */
public enum ApplicationStatusKey {

	/**
	 * 初始化状态，已提交(审批中)
	 */
	N("已提交(审批中)"),
	/**
	 * 审批通过
	 */
	Y("审批通过"),

	/**
	 * 审批不通过
	 */
	F("审批不通过"),
	/**
	 * 已实施
	 */
	S("已实施")
	;
	private String cnStatus;
	private ApplicationStatusKey(String str){
		cnStatus = str;
	}
	public String getCnKey(){
		return cnStatus;
	}
}
