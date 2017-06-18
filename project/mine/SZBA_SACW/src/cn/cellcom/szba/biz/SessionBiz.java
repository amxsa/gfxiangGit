package cn.cellcom.szba.biz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 
 * @author kchguo
 * session操作工具类
 */
public class SessionBiz {
	//以下为登录时保存在session中的Map对应的key,具体见登录的存储过程的定义
	private final static String LOGIN_FORM="loginForm";
	private final static String ROLES_INFO="rolesInfo";
	
	
	public static Map<String, Object> getMapByKey(HttpSession session,String key) {
		return (Map<String, Object>)session.getAttribute(key);
	}
	public static List<Map<String, Object>> getListByKey(HttpSession session,String key) {
		return (List<Map<String, Object>>)session.getAttribute(key);
	}
	
	
	public static Map<String, Object> getLoginForm(HttpSession session) {
		return (Map<String, Object>)getMapByKey(session,LOGIN_FORM);
	}
	
	public static List<Map<String,Object>> getRolesInfo(HttpSession session) {
		return getListByKey(session,ROLES_INFO);
	}
	
	public static String getAccount(HttpSession session) {
		return (String)getLoginForm(session).get("ACCOUNT");
	}
	
	public static Long getDepartmentId(HttpSession session) {
		return Long.valueOf(getLoginForm(session).get("DEPARTMENT_ID").toString());
	}
}
