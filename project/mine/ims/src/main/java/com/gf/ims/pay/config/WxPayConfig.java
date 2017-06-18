package com.gf.ims.pay.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * 微信支付配置信息
 *
 */
public class WxPayConfig {
	private static final Logger logger=Logger.getLogger(WxPayConfig.class);
	/**
	 * 资源文件名
	 */
	private static final String GLOBAL_PROPERTIES = "com/gf/ims/resources/pay/wxPay.properties";
    //app_id,景医卫app的app_id
    public static  String APP_ID;
    //微信商家账户的商户号，景联公司微信商户号
    public static String MCH_ID;
    //API密钥，在商户平台设置
    public static String API_KEY;
    //微信支付的微信服务通知upp系统的异步通知地址
	public static String  wx_pay_notify_url;
	//微信支付下单接口地址（预付单）
	public static String wx_pay_unifiedorder;
	//微信查询订单接口地址
	public static String wx_pay_orderquery;
	//微信退款接口
	public static String wx_pay_refund;
	/**
	 * 持久属性集
	 */
	private static Properties properties;
	static {
		try {
			properties = new Properties();
			properties.load(WxPayConfig.class.getClassLoader().getResourceAsStream(GLOBAL_PROPERTIES));
			
			APP_ID = properties.getProperty("APP_ID");
			MCH_ID = properties.getProperty("MCH_ID");
			API_KEY = properties.getProperty("API_KEY");
			wx_pay_notify_url = properties.getProperty("wx_pay_notify_url");
			wx_pay_unifiedorder = properties.getProperty("wx_pay_unifiedorder");
			wx_pay_orderquery = properties.getProperty("wx_pay_orderquery");
			wx_pay_refund = properties.getProperty("wx_pay_refund");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("获取微信支付的配置信息出错！");
		}

	}
}
