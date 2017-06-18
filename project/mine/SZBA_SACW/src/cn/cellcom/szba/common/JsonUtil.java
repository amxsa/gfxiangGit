package cn.cellcom.szba.common;

import java.lang.reflect.Type;
import java.util.List;

import net.sf.json.JSONObject;

import com.google.gson.Gson;

public class JsonUtil {

	/**  
     * 将json字符串转换成java对象  
     *
     * @param bean  
     * @return  
     */
	public static Object jsonToBean(String json, Class clazz){
		Gson gson = new Gson();
		Object bean = gson.fromJson(json, clazz);
		return bean;
	}
	
	/**
	 * 将json转成List
	 * @return
	 */
	public static <T> List<T> jsonToList(String json , Type type){
		Gson gson = new Gson();
		List<T> list = gson.fromJson(json, type);
		return list;
	}
	
	 /**  
     * 将java对象转换成json字符串  
     *
     * @param bean  
     * @return  
     */
    public static String beanToJson(Object bean) {
 
        JSONObject json = JSONObject.fromObject(bean);
         
        return json.toString();
 
    }
}
