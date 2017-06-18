package cn.cellcom.web.listener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.DB.JdbcResultSet;
import cn.cellcom.czt.bus.TDCodeGroupBus;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.TTdcodeGroup;
import cn.cellcom.web.spring.ApplicationContextTool;

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
			loadTDCodeGroup();
		}
		logger.info("*****************容器完成初始化*****************");
	}
	private void loadTDCodeGroup(){
		Map<Integer, TTdcodeGroup> list = new TDCodeGroupBus().getGroup();
		if(list!=null){
			ApplicationContextTool.getContext().getServletContext().setAttribute("TDCODE_GROUP", list);
		}
	}
	
	
}
