package com.gf.ims.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 订单工具
 * @author laizs
 * @time 2014-5-27 下午2:20:37
 * @file OrdersUtil.java
 */
public class OrdersUtil {
	/**
	 * 时间格式，精确到毫秒
	 */
	public final static SimpleDateFormat SDF=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 * 订单流水号生成，系统生成唯一的订单号
	 * 采用时间格式精确到毫秒与4位随机数的方式结合
	 * @return
	 */
	public static String generateOrderNo( ){
		String timeStr=SDF.format(new Date());
		//四位随机号，不足四位补全至4位
		int i= RandomUtils.nextInt(10000);
		String no=String.format("%04d",i);
		return timeStr+no;
	}
	/**
	 * 退款申请流水号生成，系统生成唯一的订单号
	 * 采用时间格式精确到毫秒与4位随机数的方式结合，前面多加RA
	 * @return
	 */
	public static String generateRefundApplyNo( ){
		String timeStr=SDF.format(new Date());
		//四位随机号，不足四位补全至4位
		int i= RandomUtils.nextInt(10000);
		String no=String.format("%04d",i);
		return "RA"+timeStr+no;
	}
	public static void main(String[] args) {
		int i=Integer.parseInt("1234567");
		String no=String.format("%05d",i);
		System.out.println(no);
	}
	
	/**
	 * 总订单流水号生成，系统生成唯一的订单号
	 * 采用时间格式精确到毫秒与5位随机数的方式结合
	 * @return
	 */
	public static String generateTotalOrderNo( ){
		String timeStr=SDF.format(new Date());
		//四位随机号，不足四位补全至4位
		int i= RandomUtils.nextInt(100000);
		String no=String.format("%04d",i);
		return timeStr+no;
	}
}
