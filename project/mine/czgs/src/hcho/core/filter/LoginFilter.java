package hcho.core.filter;

import hcho.core.constant.Constant;
import hcho.core.permission.PermissionCheck;
import hcho.nsfw.user.entity.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) res;
		
		//获取相对访问路径（短名）
		String uri = request.getRequestURI();
		
		//判断是否是登陆请求  若是就放行
		if (!uri.contains("/sys/login_")) {
			//判断session域中是否有用户信息  若没有 就说明是非登录   跳转到登陆界面
			if (request.getSession().getAttribute(Constant.USER)!=null) {
				
				//用户登录的情况下  子系统判断其权限是否能访问特定的子系统
				if (uri.contains("/nsfw/")) {
					//WebApplicationContext可以获取到已经实例化了的IOc容器
					WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					
					PermissionCheck pc = (PermissionCheck) context.getBean("permissionCheck");
					User user = (User) request.getSession().getAttribute(Constant.USER);
					//判断nsfw子系统是否可以访问
					if (pc.Check(user, "nsfw")) {
						chain.doFilter(request, response);
					}else{
						//进入提示拒绝访问界面
						response.sendRedirect(request.getContextPath()+"/sys/login_toNoPermissionUI.action");
					}
					
				}else{//非nsfw子系统就放行
					chain.doFilter(request, response);
				}
			}else{//未登陆时 跳转到登陆界面
				response.sendRedirect(request.getContextPath()+"/sys/login_toLoginUI.action");
			}
		}else{//登陆操作
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
