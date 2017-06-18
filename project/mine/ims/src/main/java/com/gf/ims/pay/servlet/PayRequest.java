package com.gf.ims.pay.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * 支付http请求信息封装
 * @author laizs
 * @time 2014-5-27 下午4:12:10
 * @file PayRequest.java
 */
public class PayRequest {
	/**
	 * 支持的支付方式
	 */
	public static Map<String,String> supportPayMethodMap=new HashMap<String, String>();
	/**
	 * 支持的来源渠道
	 */
	public static Map<String,String> supportChannelFlagMap=new HashMap<String, String>();
	static{
		supportPayMethodMap.put("1", "支付宝");
		supportPayMethodMap.put("2", "微信APP支付");
		supportPayMethodMap.put("3", "余额支付（纯余额）");
		supportPayMethodMap.put("4", "支付宝扫码支付");
		
		supportChannelFlagMap.put("1", "web");
		supportChannelFlagMap.put("2", "wap");
		supportChannelFlagMap.put("3", "android");
		supportChannelFlagMap.put("4", "ios");
	}
	
	
	/**
	 * http请求对象
	 */
	private HttpServletRequest requst;
	/**
	 * 支付方式
	 */
	private int payMethod;
	/**
	 * 支付订单流水号
	 */
	private String orderNo;
	/**
	 * channel_flag 渠道标识：1 web端 2：wap端（无线移动端），如果此参数为空，则系统默认为1
	 */
	private Integer channel_flag=1;
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public HttpServletRequest getRequst() {
		return requst;
	}
	public void setRequst(HttpServletRequest requst) {
		this.requst = requst;
	}
	
	
	public Integer getChannel_flag() {
		return channel_flag;
	}
	public void setChannel_flag(Integer channel_flag) {
		this.channel_flag = channel_flag;
	}
	private PayRequest(){
		
	}
	/**
	 * 解析请求信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static PayRequest parseRequest(HttpServletRequest request) throws Exception{
		PayRequest payRequest=new PayRequest();
		payRequest.requst=request;
		
		payRequest.orderNo=request.getParameter("orderNo");
		if(StringUtils.isBlank(payRequest.orderNo)){
			throw new Exception("orderNo must not be null");
		}
		
		String pay_method=request.getParameter("pay_method");
		if(StringUtils.isBlank("pay_method")){
			throw new Exception("pay_method must not be null");
		}
		if(StringUtils.isBlank(supportPayMethodMap.get(pay_method))){
			throw new Exception("pay_method is not support");
		}
		payRequest.payMethod=Integer.parseInt(pay_method);
		
		String channel_flag=request.getParameter("channel_flag");
		if(StringUtils.isBlank(channel_flag)){
			throw new Exception("channel_flag must not be null");
		}
		if(StringUtils.isBlank(supportChannelFlagMap.get(channel_flag))){
			throw new Exception("channel_flag is not support");
		}
		payRequest.setChannel_flag(Integer.parseInt(channel_flag));
		
		return payRequest;
	}
	/**
	 * 解析请求信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static PayRequest parseRequest(JSONObject jsonObject) throws Exception{
		PayRequest payRequest=new PayRequest();
		
		payRequest.orderNo=(String) jsonObject.get("orderNo");
		if(StringUtils.isBlank(payRequest.orderNo)){
			throw new Exception("orderNo must not be null");
		}
		
		String pay_method=(String) jsonObject.get("pay_method");
		if(StringUtils.isBlank("pay_method")){
			throw new Exception("pay_method must not be null");
		}
		if(StringUtils.isBlank(supportPayMethodMap.get(pay_method))){
			throw new Exception("pay_method is not support");
		}
		payRequest.payMethod=Integer.parseInt(pay_method);
		
		String channel_flag=(String) jsonObject.get("channel_flag");;
		if(StringUtils.isBlank(channel_flag)){
			throw new Exception("channel_flag must not be null");
		}
		if(StringUtils.isBlank(supportChannelFlagMap.get(channel_flag))){
			throw new Exception("channel_flag is not support");
		}
		payRequest.setChannel_flag(Integer.parseInt(channel_flag));
		
		return payRequest;
	}
}
