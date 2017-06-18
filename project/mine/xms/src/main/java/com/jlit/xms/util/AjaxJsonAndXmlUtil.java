package com.jlit.xms.util;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 后台与前台交互数据工具类
 * @author wenl
 */
public class AjaxJsonAndXmlUtil {

	/**
	 * Json字符串输出到前台
	 * @param json 
	 * @param response
	 * @throws Exception
	 */
	public static void writeJson(String json, HttpServletResponse response){
		try{
	        response.setHeader("ContentType", "text/json");  
	        response.setCharacterEncoding("utf-8");  
	        PrintWriter pw = response.getWriter();  
	        pw.write(json);  
	        pw.flush();  
	        pw.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Xml字符串输出到前台
	 * @param xml
	 * @param response
	 */
	public static void writeXml(String xml, HttpServletResponse response){
		try{
	        response.setHeader("ContentType", "text/xml");  
	        response.setCharacterEncoding("utf-8");  
	        PrintWriter pw = response.getWriter();  
	        pw.write(xml);  
	        pw.flush();  
	        pw.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * html字符串输出到前台
	 * @param xml
	 * @param response
	 */
	public static void writeHtml(String xml, HttpServletResponse response){
		try{
	        response.setHeader("ContentType", "text/html");  
	        response.setCharacterEncoding("utf-8");  
	        PrintWriter pw = response.getWriter();  
	        pw.write(xml);  
	        pw.flush();  
	        pw.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 向前台页面返回Json格式的字符串流
	 * 该方法的数据源只针对一种对象
	 * @param list -源数据
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public static void writeJsonList(List<? extends Object> list, HttpServletResponse response){
		JSONArray jsonArray=JSONArray.fromObject(list,getJsonConfig());
		String jsonString = ("{\"root\":"+ jsonArray.toString() + "}");
		writeJson(jsonString,response);
	}
	
	/**
	 * JsonConfig 配置
	 * 忽略注解为
	 * 		javax.persistence.OneToMany
	 * 		javax.persistence.ManyToOne 的field
	 * @return
	 */
	private static  JsonConfig getJsonConfig(){
		JsonConfig config = new JsonConfig();
		config.addIgnoreFieldAnnotation("javax.persistence.OneToMany");
		config.addIgnoreFieldAnnotation("javax.persistence.ManyToOne");
		return config;
	}
}
 