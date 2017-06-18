package com.gf.ims.common.listener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gf.ims.common.env.ApplicationContextTool;


public class GlobalListener implements ServletContextListener {

	private static final Log logger = LogFactory.getLog(GlobalListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.warn("-----------------容器开始销毁-----------------");
		logger.warn("*****************容器完成销毁*****************");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("-----------------容器开始初始化-----------------");
		ServletContext context=servletContextEvent.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		if (webApplicationContext == null) {
			logger.error("获取spring的容器失败！");
		}
		else {
			logger.info("获取spring的容器成功！");
			ApplicationContextTool.setContext(webApplicationContext);
		}
		logger.info("*****************容器完成初始化*****************");
	}
}
