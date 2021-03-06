package com.gf.ims.xcx.common;


import java.io.IOException;
import java.util.Properties;



public class ConfLoad {
	private static  Properties pp = null ;
	static{
		init();
	}
	public static Properties init(){
		if(pp==null){
			pp  = new Properties();
			try {
				pp.load(ConfLoad.class.getResourceAsStream("/com/gf/ims/resources/wxxcx.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return pp;
		}
		return pp;
	}
	public static String getProperty(String key) {
		if (pp.containsKey(key)) {
			return String.valueOf(pp.get(key));
		}
		return null;
	}
}
