package cn.cellcom.szba.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

import cn.open.http.HttpClientHelper;
import cn.open.http.HttpResult;

public class HttpHelper {
	
	public static String postMapFile(String url, Map<String,String> params,Map<String,File> file, Header...header) {
		String ret="";
		if(StringUtils.isBlank(url))
			return ret;
		HttpClientHelper hh=new HttpClientHelper();
		HttpResult hr=hh.post(url, params,file, header);
		try {
			return hr.getText();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String postMap(String url, String jsonStr, Header...header) {
		String ret="";
		if(StringUtils.isBlank(url))
			return ret;
		HttpClientHelper hh=new HttpClientHelper();
		Map<String,String> mapStr = toHashMap(jsonStr);
		HttpResult hr=hh.post(url, mapStr, header);
		try {
			return hr.getText();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String postStr(String url, String jsonStr, Header...header) {
		String ret="";
		if(StringUtils.isBlank(url))
			return ret;
		HttpClientHelper hh=new HttpClientHelper();
		HttpResult hr=hh.post(url, jsonStr, header);
		try {
			return hr.getText();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static HashMap<String, String> toHashMap(Object object) {
		HashMap<String, String> data = new HashMap<String, String>();
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = String.valueOf(jsonObject.get(key));
			data.put(key, value);
		}
		return data;
	}
}
