package com.gf.ims.file.common;

import java.io.IOException;
import java.util.Properties;
/**
 * global资源文件静态数据
 * @author laizs
 * @time 2016-5-11上午10:44:29
 * @file GlobalStaticValue.java
 *
 */
public class GlobalStaticValue {
	/**
	 * 资源文件名
	 */
	private static final String GLOBAL_PROPERTIES = "com/gf/ims/resources/global.properties";
	
	/**
	 * 持久属性集
	 */
	private static Properties properties;
	/**
	 * 文件服务器文件访问的服务器地址
	 */
	public static String upload_server_address="";
	
	
	static {
		try {
			properties = new Properties();
			properties.load(GlobalStaticValue.class.getClassLoader().getResourceAsStream(GLOBAL_PROPERTIES));
			upload_server_address = properties.getProperty("upload_server_address");
			System.out.println("global全局变量文件上传地址加载成功------"+upload_server_address);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("获取全局变量失败;");
		}

	}

}
