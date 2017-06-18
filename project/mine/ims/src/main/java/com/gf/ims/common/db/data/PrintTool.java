package com.gf.ims.common.db.data;

import java.io.IOException;
import java.io.PrintWriter;


import java.util.Collection;
import java.util.Iterator;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintTool {
	private static Log log = LogFactory.getLog(PrintTool.class);

	public static void print(HttpServletResponse response, Object obj,
			String type) throws IOException {
		if (StringUtils.isBlank(type))
			response.setContentType("text/html;charset=utf-8");
		else
			response.setContentType("text/" + type + ";charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}

	/**
	 * 打印集合
	 * 
	 * @param collection
	 * @param isLog
	 *            是否显示日志
	 */
	public static void print(Collection<?> collection, boolean isLog) {
		if (collection == null) {
			System.out.println("collection is null");
			return;
		}
		Iterator<?> it = collection.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (isLog) {
				log.info(obj);
			} else {
				System.out.println(obj);
			}
		}
	}

	/**
	 * 打印 数组
	 * 
	 * @param objects
	 * @param isLog
	 *            是否显示日志
	 */
	public static void print(Object[] objects, boolean isLog) {
		if (objects == null) {
			System.out.println("objects is null");
			return;
		}
		for (int i = 0, len = objects.length; i < len; i++) {
			Object object = objects[i];
			if (isLog)
				log.info(object);
			else
				System.out.println(object);
		}
	}

	/**
	 * 打印 map
	 * 
	 * @param objects
	 * @param isLog
	 *            是否显示日志
	 */
	public static void printMap(Map<?, ?> map, boolean isLog) {
		if (map == null) {
			System.out.println("map is null");
			return;
		}
		Iterator<?> it = map.keySet().iterator();
		StringBuffer str = new StringBuffer("[");
		while (it.hasNext()) {
			Object key = it.next();
			str.append(key).append(":").append(map.get(key)).append(",");
		}
		str.deleteCharAt(str.length());
		if(isLog){
			log.info(str.toString());
		}else{
			System.out.println(str.toString());
		}
	}

	public static void printBean(String model, Object obj, boolean isLog) {
		if (isLog)
			log.info(StringUtils.isBlank(model) ? ToStringBuilder
					.reflectionToString(obj) : model + ":"
					+ ToStringBuilder.reflectionToString(obj));
		else
			System.out.println(StringUtils.isBlank(model) ? ToStringBuilder
					.reflectionToString(obj) : model + ":"
					+ ToStringBuilder.reflectionToString(obj));
	}

	public static void printSQLORHQL(String module,String str, Object[] objs, boolean isLog) {
		StringBuilder sb = new StringBuilder(module);
		sb.append(" 执行语句： ").append(str).append("  参数  [").append(
				ArrayTool.getStrByArray(objs, ",")).append("]");

		if (isLog)
			log.info(sb.toString());
		else
			System.out.println(sb.toString());
	}
	
	public static void printLog(Map<String, String> map) {
		StringBuilder logStr = new StringBuilder("手机端执行：");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if(!"filestream".equals(entry.getKey())){
				if(entry.getKey().endsWith(".flow")||entry.getKey().endsWith(".php")){
					logStr.append(entry.getKey()).append("?");
				}else{
					logStr.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
				}
				
			}
			
			
		}
		log.info(logStr.toString());
	}
	
	public static void printLog(String model,Map<String, String> map) {
		StringBuilder logStr = new StringBuilder(model);
		logStr.append("执行:");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if(!"filestream".equals(entry.getKey())){
				if(entry.getKey().endsWith(".flow")||entry.getKey().endsWith(".php")){
					logStr.append(entry.getKey()).append("?");
				}else{
					logStr.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
				}
				
			}
			
			
		}
		log.info(logStr.toString());
	}

	public static void main(String[] args) {

	}
}
