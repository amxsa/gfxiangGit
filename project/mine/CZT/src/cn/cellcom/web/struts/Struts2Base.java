package cn.cellcom.web.struts;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public abstract class Struts2Base extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	
	public static HttpSession getSession(boolean isNew) {
		return ServletActionContext.getRequest().getSession(isNew);
	}

	
	public static Object getSessionAttribute(String name) {
		HttpSession session = getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}

	
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
}
