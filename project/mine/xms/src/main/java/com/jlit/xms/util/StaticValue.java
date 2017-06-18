package com.jlit.xms.util;

import java.io.IOException;
import java.util.Properties;

public class StaticValue {

	
	/**
	 * 资源文件名
	 */
	private static final String GLOBAL_PROPERTIES = "com/jlit/mms/resources/global.properties";
	
	/**
	 * 持久属性集
	 */
	private static Properties properties;
	
	
	
	
	public static String umpccAccount;
	
	public static String productId;
	
	public static String ptConnectTimeout;
	
	public static String mobileUserFeedback;
	
	public static String chimsUrl;
	
    public static String  xmpp_server_address;
	
	public static String  xmpp_server_port;
	
	public static String fms_server_address;
	
	public static String upload_server_address;
	
	static {
		try {
			properties = new Properties();
			properties.load(StaticValue.class.getClassLoader().getResourceAsStream(GLOBAL_PROPERTIES));
			
			
			umpccAccount = properties.getProperty("umpccAccount");
			
			productId = properties.getProperty("productId");
			
			ptConnectTimeout = properties.getProperty("ptConnectTimeout");
			
			mobileUserFeedback = properties.getProperty("mobileUserFeedback");
			
			chimsUrl = properties.getProperty("chims_url");
			
			xmpp_server_address = properties.getProperty("xmpp_server_address");
			xmpp_server_port = properties.getProperty("xmpp_server_port");
			fms_server_address = properties.getProperty("fms_server_address");
			
			upload_server_address = properties.getProperty("upload_server_address");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("获取全局变量失败;");
		}

	}

}
