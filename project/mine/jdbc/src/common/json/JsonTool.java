package common.json;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import common.json.JsonDateValueProcessor;
import net.sf.ezmorph.object.DateMorpher;


public class JsonTool {

	/**
	 * json 转成 bean(集合对象)
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> Collection json2Bean(String jsonString, Class<T> clazz) {
		setDateFormat();
		try {
			Collection<T> list = JSONArray.toCollection(JSONArray
					.fromObject(jsonString), clazz);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("json转成bean异常", e);
		}
	}
	
	/**
	 * json 转成 bean(单个对象)
	 * @param <T>
	 * @param jsonObject
	 * @param clazz
	 * @return
	 */
	public static <T> Object json2Bean(JSONObject jsonObject, Class<T> clazz) {
		setDateFormat();
		try {
			return JSONObject.toBean(jsonObject, clazz);
		} catch (Exception e) {
			throw new RuntimeException("json转成bean异常", e);
		}
	}
	
	/**
	 * json(json字符串) 转成 map
	 * @param jsonString json字符串
	 * @return
	 */
	public static Map<String, Object> json2Map(String jsonString) {
		try {
			return json2Map(JSONObject.fromObject(jsonString));
		} catch (Exception e) {
			throw new RuntimeException("json转成map异常", e);
		}
	}

	/**
	 * json(json对象) 转成 map
	 * @param jsonObject
	 * @return
	 */
	public static Map json2Map(JSONObject jsonObject) {
		setDateFormat();
		Map<String,Object> map = new HashMap<String,Object>();
		for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	/**
	 * bean 转成 json
	 * @param bean
	 * @param datePattern
	 * @return
	 */
	public static JSONObject bean2Json(Object bean, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		try {
			jsonConfig.registerJsonValueProcessor(Date.class,
					datePattern == null ? new JsonDateValueProcessor()
							: new JsonDateValueProcessor(datePattern));
			return JSONObject.fromObject(bean, jsonConfig);
		} catch (Exception e) {
			throw new RuntimeException("bean转成json异常", e);
		}
	}
	
	/**
	 * bean集合 转成 JSONArray
	 * @param collection
	 * @param datePattern
	 * @return
	 */
	
	public static JSONArray bean2Json(Collection collection, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		try {
			jsonConfig.registerJsonValueProcessor(Date.class,
					datePattern == null ? new JsonDateValueProcessor()
							: new JsonDateValueProcessor(datePattern));
			return JSONArray.fromObject(collection, jsonConfig);
		} catch (Exception e) {
			throw new RuntimeException("collection(存储bean)转成json异常", e);
		}
	}

	private static void setDateFormat() {
		JSONUtils.getMorpherRegistry()
				.registerMorpher(
						new DateMorpher(new String[] { "yyyy-MM-dd",
								"yyyy-MM-dd HH:mm:ss", "yyyyMMdd",
								"yyyyMMdd HHmmss" }));
	}

	/**
	 * 获取object转成json字符
	 * @param object
	 * @param datePattern
	 * @return
	 * @throws Exception
	 */
	public static String getJsonString(Object object, String datePattern) throws Exception {
		JsonConfig jsonConfig = new JsonConfig();
		try {
			jsonConfig.registerJsonValueProcessor(Date.class,
					datePattern == null ? new JsonDateValueProcessor()
							: new JsonDateValueProcessor(datePattern));
			if(object instanceof Collection || object instanceof Object[]){
				return JSONArray.fromObject(object, jsonConfig).toString();
			}else{
				return JSONObject.fromObject(object, jsonConfig).toString();
			}
		} catch (Exception e) {
			throw new RuntimeException("获取json字符异常", e);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
