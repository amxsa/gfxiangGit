package com.jlit.xms.oauth.handler;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jlit.oauth.AbstractOauth2Handler;
import com.jlit.oauth.AccessTokenModel;
import com.jlit.oauth.OauthDataUtil;
import com.jlit.oauth.bean.UserVo;
import com.jlit.uums.webservice.bean.Platform;
import com.jlit.uums.webservice.bean.UserMenus;
import com.jlit.xms.enums.OauthSystemSetting;
public class XmsOauth2Handler extends AbstractOauth2Handler {
	private final static Logger logger=Logger.getLogger(XmsOauth2Handler.class);
	/**
	 * 无参构�?方法
	 */
	public XmsOauth2Handler(){
		System.out.println("----------------------调用XmsOauth2Handler的无参构造方法...");
		//初始化oauth认证通用参数
		super.setAuthorizeUri(OauthSystemSetting.getInstance().getAuthorizeUri());
		super.setAccessTokenUri(OauthSystemSetting.getInstance().getAccessTokenUri());
		super.setUserMeUri(OauthSystemSetting.getInstance().getUserMeUri());
		super.setUserMemusUri(OauthSystemSetting.getInstance().getUserMemusUri());
		super.setUserPlatformsUri(OauthSystemSetting.getInstance().getUserPlatformsUri());
		super.setLogoutUri(OauthSystemSetting.getInstance().getLogoutUri());
		
	}
	@Override
	public void beforeAuthrizedHandler(HttpServletRequest request,
			HttpServletResponse response) {
		 String fromURL = request.getHeader("Referer");   
		//logger.warn("来源url:"+fromURL);
		//清除会话信息
		request.getSession().invalidate();
		
	}
	@Override
	public void afterAuthrizeHandler(AccessTokenModel accessTokenModel,
			HttpServletRequest request, HttpServletResponse response) {
		//获取用户信息
		try {
			String userJsonStr=getUserInfo(accessTokenModel.getAccessToken());
			
			//用户信息
			UserVo userVo=OauthDataUtil.Json2UserVo(userJsonStr);
			//保存用户session信息
			request.getSession().setAttribute("userId", userVo.getId());
			request.getSession().setAttribute("userAccount", userVo.getUserAccount());
			request.getSession().setAttribute("userName", userVo.getUserName());
			request.getSession().setAttribute("userType", userVo.getUserType());
			request.getSession().setAttribute("userVo", userVo);
			//获取用户拥有权限的可访问的平台列�?
			String ps=getUserPlatForms(accessTokenModel.getAccessToken());
			logger.info("获取到用户有权限访问的平台："+ps);
			List<Platform> platforms=OauthDataUtil.Json2PlatForms(ps);
			request.getSession().setAttribute("platforms", platforms);
			
			String menus=getUserMemus(accessTokenModel.getAccessToken());
			
			UserMenus userMenus=OauthDataUtil.Json2UserMenus(menus);
			logger.info("!!!!!!!!!!!!!!!!!!!!!!");
			if(userMenus.getRootMenus().size()>0){
				//保存用户session信息
				request.getSession().setAttribute("userId", userVo.getId());
				request.getSession().setAttribute("userAccount", userVo.getUserAccount());
				request.getSession().setAttribute("userName", userVo.getUserName());
				request.getSession().setAttribute("userType", userVo.getUserType());
				request.getSession().setAttribute("userTypeName", userVo.getUserTypeName());
				request.getSession().setAttribute("userVo", userVo);
				request.getSession().setAttribute("authLevel", userVo.getAuthLevel());
				//保存菜单session信息
				request.getSession().setAttribute("rootMenus", userMenus.getRootMenus());
				request.getSession().setAttribute("subMenus", userMenus.getSubMenus());
				
				//保存权限session信息
				request.getSession().setAttribute("userPermissions", userMenus.getUserPermissions());
				
				response.sendRedirect("main.html");
			}else{
				response.sendRedirect("noPurview.html");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取用户信息异常，e:"+e.getMessage());
		} 
				
		
	}
	
	
	

}
