package com.jlit.xms.interceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jlit.oauth.OauthDataUtil;
import com.jlit.oauth.bean.UserVo;
import com.jlit.xms.oauth.handler.XmsOauth2Handler;
public class UserRoleAuthorizationInterceptor implements HandlerInterceptor  {

	private static final long serialVersionUID = 5067790608840427509L;
	/**
	 * log4j日志
	 */
	private static final Log logger = LogFactory.getLog(UserRoleAuthorizationInterceptor.class);
	@Resource
	private XmsOauth2Handler oauth2Handler;

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		boolean isSessionValid=false;//session是否有效
		String url = request.getRequestURL().toString();
		logger.info("requestUrl: "+url);
		//使用url带过来的accessToken参数，去oauth2服务器换取用户信息
		String accessToken=request.getParameter("accessToken");
		//判断url带过来的accessToken参数和session中记录的参数是否一致，如果不一致，清除session信息
		String oldAccessToken=(String) request.getSession().getAttribute("accessToken");
		if(StringUtils.isNotBlank(accessToken)&&StringUtils.isNotBlank(oldAccessToken)&& !oldAccessToken.equals(accessToken)){
			//清除会话信息
			//request.getSession().invalidate();
			//删除session中存储的用户信息
			request.getSession().removeAttribute("userId");
			request.getSession().removeAttribute("userAccount");
			request.getSession().removeAttribute("userName");
			request.getSession().removeAttribute("userType");
			request.getSession().removeAttribute("userTypeName");
			request.getSession().removeAttribute("userVo");
			request.getSession().removeAttribute("authLevel");
			request.getSession().removeAttribute("accessToken");
		}
		String userAccount = (String)request.getSession().getAttribute("userAccount");
		UserVo userVo=(UserVo)request.getSession().getAttribute("userVo");
		if(userAccount == null || null==userVo){//session超时
			try {
				//使用url带过来的accessToken参数，去oauth2服务器换取用户信息
				if(StringUtils.isNotBlank(accessToken)){
					String userJsonStr=this.oauth2Handler.getUserInfo(accessToken);
					//用户信息
					userVo=OauthDataUtil.Json2UserVo(userJsonStr);
					//------------保存session信息---------------------------------------------------
					//保存用户session信息
					request.getSession().setAttribute("userId", userVo.getId());
					request.getSession().setAttribute("userAccount", userVo.getUserAccount());
					request.getSession().setAttribute("userName", userVo.getUserName());
					request.getSession().setAttribute("userType", userVo.getUserType());
					request.getSession().setAttribute("userTypeName", userVo.getUserTypeName());
					request.getSession().setAttribute("userVo", userVo);
					request.getSession().setAttribute("authLevel", userVo.getAuthLevel());
					request.getSession().setAttribute("accessToken", accessToken);//accessToken存到session
					isSessionValid=true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("使用accessToken获取用户信息失败，e:"+e.getMessage());
			}
		}else{
			isSessionValid=true;
		}
		return isSessionValid;
	}
	//return "NOTLOGIN";//项目的session过期，使用accessToken换取用户信息又失效，只好返回未登陆页面发起重新授权认证

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}
