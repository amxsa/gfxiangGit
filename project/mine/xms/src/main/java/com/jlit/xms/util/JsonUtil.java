package com.jlit.xms.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.jlit.model.Commodity;
/**
 * Json工具类
 * @author sucs
 *
 */
public class JsonUtil {
	
	private static Logger logger=Logger.getLogger(JsonUtil.class);
	
	private static Gson gson =null;
			
	static{
		gson=new GsonBuilder()
//			.registerTypeAdapter(Integer.class, new JsonAdapter<Integer>())
//			.registerTypeAdapter(Double.class, new JsonAdapter<Double>())
//			.registerTypeAdapter(Float.class, new JsonAdapter<Float>())
//			.registerTypeAdapter(Boolean.class, new JsonAdapter<Boolean>())
//			.registerTypeAdapter(Byte.class, new JsonAdapter<Byte>())
//			.registerTypeAdapter(Short.class, new JsonAdapter<Short>())
//			.registerTypeAdapter(Long.class, new JsonAdapter<Long>())
//			.registerTypeAdapter(Number.class, new JsonAdapter<Number>())
			.create(); 
	}
	
	/**
	 * 将对象转换成json字符串,支持: javabean,map,list,array
	 * @param obj 要转换的对象 
	 * @return
	 */
	public static String toJson(Object obj){
		try {
			return gson.toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("JsonUtil将对象转换成json字符串出现异常",e);
		}
	} 
	
	/**
	 * 将json字符串转换成对象
	 * @param clazz  要转换成的类型
	 * @param jsonString json字符串
	 * @return
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz){
		try {
			return gson.fromJson(jsonString, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("JsonUtil将json字符串转换成对象出现异常:"+jsonString,e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> fromJson(String json) {
		return 	gson.fromJson(json, Map.class);
	} 
	
	/**
	 * 从request中获取json数据,参数名默认为json
	 * @param request 请求
	 * @param clazz json要转换成的数据类型
	 * @return
	 */
	public static <T> T getJsonData(HttpServletRequest request,Class<T> clazz){
		
		T obj = null;
		
		try {
			obj = getJsonData(request, "json", clazz);
		} catch (Exception e1) {
			logger.error(e1);
			return null;
		}
		return obj;
		
	}
	
	/**
	 * 从request中获取json数据
	 * @param request 请求
	 * @param jsonparam 参数名
	 * @param clazz 要转换成的数据类型
	 * @return
	 */
	public static <T> T getJsonData(HttpServletRequest request,String jsonparam,Class<T> clazz){
		String json=request.getParameter(jsonparam);
		
		if(StringUtils.isEmpty(json)){
			return getData(request,clazz);
		}
		
		return fromJson(json,clazz);
	}
	
	/**
	 * 从request中获取表单提交的数据
	 * @param request request
	 * @param clazz 要转换成的数据类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getData(HttpServletRequest request,Class<T> clazz){
		
		Enumeration<String> e=request.getParameterNames();
		
		try {
			
			T instance=clazz.newInstance();
			
			Field[] props = clazz.getDeclaredFields();
			
			Map<String,String> map=new HashMap<String,String>();
			
			for (Field property : props) {
				
				try {
					//属性名
					String fname=property.getName();
					//序列化名
					String sname=property.getName();
					
					if(property.isAnnotationPresent(SerializedName.class)){
						SerializedName serialized=property.getAnnotation(SerializedName.class);
						sname=serialized.value();
					}
					map.put(sname,fname);
					
				} catch (Exception e1) {
					
					logger.info("获取属性："+property.getName()+"失败");
				}
			}
			
			boolean flag=false;
			
			while (e.hasMoreElements()) {
				
				String param = e.nextElement();
				
				if(map.containsKey(param)){
					BeanUtils.setProperty(instance,map.get(param),request.getParameter(param));
					flag=true;
				}
			}
			return flag?instance:null;
		} catch (Exception e1) {
			logger.error(e1);
		}
		return null;
	}

	public static Object fromJson(String json,Type type) {
		try {
			
			if(json==null||json.trim()==""){
				return null;
			}
			
			return gson.fromJson(json, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
