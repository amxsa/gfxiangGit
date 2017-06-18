package cn.cellcom.szba.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.open.db.Pager;

public class RequestUtils {

	public static String getAttribute(Map<String, String[]> params, String key, Integer index){
		if(null == params || 0 == params.size()){
			return "";
		}
		String[] str = params.get(key);
		if(null == str || 0 == str.length) { 
			return "";
		}
		if(str.length > index){
			return str[index];
		}
		return "";
	}
	
	public static Pager getRequestPage(HttpServletRequest request, String key){
		
		return (Pager) request.getAttribute(key);
	}
	
	public static Map<String, String[]> addExtraParamToRequestParamMap(Map<String, String[]> requestParameters, Map<String, String[]> extraMap){
		for(String key : requestParameters.keySet()){
			extraMap.put(key, requestParameters.get(key));
		}
		return extraMap;
	}
	
	public static String getRefererUrl(HttpServletRequest httpRequest){
		String referer = httpRequest.getHeader("referer");
		if(referer != null){
			referer = referer.replace("http://"+httpRequest.getHeader("host"), "").replace(httpRequest.getContextPath(), "");
			
			String viewName = httpRequest.getParameter("viewName");
			if(referer.indexOf("viewName") == -1 && StringUtils.isNotBlank(viewName)){
				if(referer.indexOf("?") == -1){
					referer += "?viewName="+viewName;
				}else{
					referer += "&viewName="+viewName;
				}
			}
		}else{
			referer = "";
		}
		
		return referer;
	}
	
}
