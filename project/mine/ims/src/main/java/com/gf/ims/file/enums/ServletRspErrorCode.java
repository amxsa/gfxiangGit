package com.gf.ims.file.enums;
/**
 * PAD端请求错误码
 * @author wiliam
 *
 */
public class ServletRspErrorCode {
	
	/**
	 * 消息解析成功
	 */
	public final static String MESSAGE_RSP_SUCCESS = "0";
	
	/**
	 * 消息格式错误Code
	 */
	public final static String MESSAGE_FORMAT_ERROR_CODE = "001";

	/**
	 * 消息格式错误Content
	 */
	public final static String MESSAGE_FORMAT_ERROR_CONTENT = "消息格式错误";
	
	/**
	 * Token验证错误Code
	 */
	public final static String MESSAGE_TOKEN_VALIDATE_ERROR_CODE = "002";

	/**
	 * Token验证错误Content
	 */
	public final static String MESSAGE_TOKEN_VALIDATE_ERROR_CONTENT = "Token验证错误";
	
	/**
	 * Token过期错误Code
	 */
	public final static String MESSAGE_TOKEN_OVER_TIME_ERROR_CODE = "003";

	/**
	 * Token过期错误Content
	 */
	public final static String MESSAGE_TOKEN_OVER_TIME_ERROR_CONTENT = "Token过期";
	
	/**
	 * 找不到对应的方法错误Code
	 */
	public final static String MESSAGE_NO_EXIST_METHOD_ERROR_CODE = "004";

	/**
	 * 找不到对应的方法错误Content
	 */
	public final static String MESSAGE_NO_EXIST_METHOD_ERROR_CONTENT = "找不到对应的方法";
	
	/**
	 * 服务器响应异常Code
	 */
	public final static String MESSAGE_SERVICE_RESPONSE_ERROR_CODE = "005";
	
	/**
	 * 服务器响应异常Content
	 */
	public final static String MESSAGE_SERVICE_RESPONSE_ERROR_CONTENT = "服务器响应异常";
	/**
	 * 服务器处理任务超时Code
	 */
	public final static String MESSAGE_SERVICE_TIMEOUT_ERROR_CODE = "006";
	
	/**
	 * 服务器处理任务超时Content
	 */
	public final static String MESSAGE_SERVICE_TIMEOUT_ERROR__CONTENT = "服务器处理任务超时异常";
	
	/**
	 * 设备号已被使用Code
	 */
	public final static String MESSAGE_SERIAL_ISEXIST_ERROR_CODE = "007";
	
	/**
	 * 设备号已被使用Content
	 */
	public final static String MESSAGE_SERIAL_ISEXIST_ERROR__CONTENT = "设备号已被使用";
	
	/**
	 * 用户不存在Code
	 */
	public final static String MESSAGE_ACCOUNT_ISNOTEXIST_ERROR_CODE = "008";
	
	/**
	 * 用户不存在Content
	 */
	public final static String MESSAGE_ACCOUNT_ISNOTEXIST_ERROR__CONTENT = "用户不存在";
	
	/**
	 * 设备具体类型Code
	 */
	public final static String MESSAGE_DEVICE_TYPE_ERROR_CODE = "009";
	
	/**
	 * 设备具体类型Content
	 */
	public final static String MESSAGE_DEVICE_TYPE_ERROR_CONTENT = "设备具体类型格式不正确";
	
	/**
	 * 设备号不存在Code
	 */
	public final static String MESSAGE_SERIAL_ISNOTEXIST_ERROR_CODE = "010";
	
	/**
	 * 设备号不存在Content
	 */
	public final static String MESSAGE_SERIAL_ISNOTEXIST_ERROR__CONTENT = "设备号不存在";
	
	/**
	 * 设备号不存在Code
	 */
	public final static String MESSAGE_BINDSERIAL_ISNOTEXIST_ERROR_CODE = "011";
	
	/**
	 * 设备号不存在Content
	 */
	public final static String MESSAGE_BINDSERIAL_ISNOTEXIST_ERROR__CONTENT = "用户没有绑定设备";
	
	/**
	 * 设备号不存在Code
	 */
	public final static String MESSAGE_PARAM_IS_NULL_ERROR_CODE = "012";
	
	/**
	 * 设备号不存在Content
	 */
	public final static String MESSAGE_PARAM_IS_NULL_ERROR__CONTENT = "传入参数有为空字段";
}
