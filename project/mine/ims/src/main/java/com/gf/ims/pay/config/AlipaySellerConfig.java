package com.gf.ims.pay.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 支付宝集成买家账号信息
 * @author laizs
 * @time 2014-6-3 下午1:39:39
 * @file AlipaySellerConfig.java
 * @package pcom.jlit.upp.config
 * @project upp
 */
public class AlipaySellerConfig {
	private static final Logger logger=Logger.getLogger(AlipaySellerConfig.class);
	/**
	 * 资源文件名
	 */
	private static final String GLOBAL_PROPERTIES = "com/jlit/upp/resources/alipayConfig.properties";
	//合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static  String partner;
	// 交易安全检验码，由数字和字母组成的32位字符串
	public static String key;
	// 签约支付宝账号或卖家收款支付宝帐户
	public static String seller_email;
	//wap无线支付给支付宝的回调地址
    public static  String wap_pay_call_back_url;
    //wap无线支付给支付宝异步通知地址
    public static  String wap_pay_notify_url;
    //web端pc支付给支付宝的回调地址
    public static  String web_pay_return_url;
    //web端pc支付给支付宝的异步通知地址
    public static  String web_pay_notify_url;
    //支付宝面对面支付服务窗app_id
    public static String f2f_pay_appid;
    //支付宝面对面支付RSA私钥（使用openssl生成的密钥）
    public static String f2f_pay_rsa_private_key;
    //支付宝面对面支付RSA公钥（使用openssl生成的密钥）
    public static String f2f_pay_rsa_public_key;
    //支付宝面对面支付异步通知地址
    public static String f2f_pay_notify_url;
    //支付宝跨界支付异步通知地址
    public static String ali_direct_pay_notify_url;
    //支付宝跨界支付同步通知地址
    public static String ali_direct_pay_return_url;
	/**
	 * 持久属性集
	 */
	private static Properties properties;
	static {
		try {
			properties = new Properties();
			properties.load(AlipaySellerConfig.class.getClassLoader().getResourceAsStream(GLOBAL_PROPERTIES));
			
			partner = properties.getProperty("partner");
			key = properties.getProperty("key");
			seller_email = properties.getProperty("seller_email");
			wap_pay_call_back_url = properties.getProperty("wap_pay_call_back_url");
			wap_pay_notify_url = properties.getProperty("wap_pay_notify_url");
			web_pay_return_url = properties.getProperty("web_pay_return_url");
			web_pay_notify_url = properties.getProperty("web_pay_notify_url");
			f2f_pay_appid = properties.getProperty("f2f_pay_appid");
			f2f_pay_rsa_private_key = properties.getProperty("f2f_pay_rsa_private_key");
			f2f_pay_rsa_public_key = properties.getProperty("f2f_pay_rsa_public_key");
			f2f_pay_notify_url = properties.getProperty("f2f_pay_notify_url");
			ali_direct_pay_notify_url = properties.getProperty("ali_direct_pay_notify_url");
			ali_direct_pay_return_url = properties.getProperty("ali_direct_pay_return_url");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("获取支付宝商家的配置信息出错！");
		}

	}
}
