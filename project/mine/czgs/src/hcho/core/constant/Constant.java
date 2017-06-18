package hcho.core.constant;

import java.util.HashMap;
import java.util.Map;


public class Constant {
	
	//用户在session中的标识
	public static String USER="SYS_USER";
	
	public static String PRIVILEGE_XZGL="xzgl";
	public static String PRIVILEGE_HQFW="hqfw";
	public static String PRIVILEGE_ZXXX="zxxx";
	public static String PRIVILEGE_NSFW="nsfw";
	public static String PRIVILEGE_SPACE="space";
	
	public static Map<String, String>  PRIVILEGE_MAP;
	static{
		PRIVILEGE_MAP=new HashMap<String, String>();
		PRIVILEGE_MAP.put(PRIVILEGE_XZGL, "行政管理");
		PRIVILEGE_MAP.put(PRIVILEGE_HQFW, "后勤服务");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXXX, "在线学习");
		PRIVILEGE_MAP.put(PRIVILEGE_NSFW, "纳税服务");
		PRIVILEGE_MAP.put(PRIVILEGE_SPACE, "我的空间");
	}
}
