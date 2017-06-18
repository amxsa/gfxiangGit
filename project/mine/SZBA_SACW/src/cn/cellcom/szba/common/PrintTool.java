package cn.cellcom.szba.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintTool {
	private static Log log = LogFactory.getLog(PrintTool.class);
	public static void print(HttpServletResponse response, Object obj,String type)
			throws IOException {
		if(StringUtils.isBlank(type))
			response.setContentType("text/html;charset=utf-8");
		else
			response.setContentType("text/"+type+";charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(obj.toString());
		writer.flush();
		writer.close();
	}
	public static String printStrByArray(Object[] str) {
		if (str == null||str.length==0)
			return "";
		else {
			StringBuffer returnStr = new StringBuffer("");
			for (int i = 0; i < str.length; i++) {
				if (null != str[i]) {
					returnStr.append(str[i]).append(",");
				}
			}
			return returnStr.toString();
		}
	}
	
	public static Object printJSON(Object obj){
		return JSONObject.fromObject(obj);
	}
	public static Object printJSONArray(List list){
		return JSONArray.fromObject(list);
	}
	
}
