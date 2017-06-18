package com.jlit.xms.util;

import javax.servlet.http.HttpServletRequest;

import com.jlit.model.User;

public class WebUtil {
	public final static String USERINFOS_SESSION_KEY="userInfo";
	/**
	 * 绑定Request对象
	 * @param request
	 * @param o
	 * @param name
	 */
	public static void setRequest(HttpServletRequest request,Object o,String name){
		request.setAttribute(name, o);
	}
	/**
	 * 获取当前用户会话信息（从memcached中获取）
	 * @param request
	 * @param response
	 * @return UserInfo对象  ：用户已经登录   null:用户未登陆
	 */
	public static User getCurrentUser(HttpServletRequest request){
		User userInfo =(User) request.getSession().getAttribute("userInfo");
		return userInfo;
	}
}
