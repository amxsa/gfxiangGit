package cn.cellcom.web.struts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.cellcom.czt.common.Env;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class MyInterceptor extends AbstractInterceptor {
	private static final Log log = LogFactory.getLog(MyInterceptor.class);
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();

		// 找到运行的ActionProxy对象，并打印其要运行的方法名
		String method = invocation.getProxy().getMethod();
		String actionName = invocation.getProxy().getActionName();
		// 获取request
		HttpServletRequest request = ServletActionContext.getRequest();
		log.info("ip:"+request.getRemoteAddr()+";Action:"
				+ invocation.getAction().getClass().getName()+";Method:"+method);
		// 获取response
		HttpServletResponse response = ServletActionContext.getResponse();
		//获取url
		String tempUrl = request.getRequestURI();
		
		if(tempUrl.indexOf("workhelp")>-1)
			return invocation.invoke();
		if(tempUrl.endsWith(".do")){
			
			if(Env.ALLOW_URL.contains(actionName)){
				return invocation.invoke();
			}else{
				if (session.get("login") == null) {
					request.setAttribute("result", "用户未登录或登录超时");
					return "timeout";
				} else {
					return invocation.invoke();
				}
			}
		}else{
			return invocation.invoke();
		}
	}

}
