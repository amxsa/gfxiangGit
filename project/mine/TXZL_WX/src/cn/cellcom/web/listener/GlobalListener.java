package cn.cellcom.web.listener;


import java.io.IOException;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.po.TCity;
import cn.cellcom.wechat.po.TMobileHead;



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
			//加载城市
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			
			try {
				List<TCity> cities = jdbc.query(ApplicationContextTool.getDataSource(), "select * from t_city where postcode is not null", TCity.class, null, 0, 0);
				if(cities!=null&&cities.size()>0){
					for (TCity city : cities) {
						Env.CODE_CITY.put(city.getPostcode(), city);
					}
					context.setAttribute("CODE_CITY", Env.CODE_CITY);
				}
				logger.info("加载城市区号为："+Env.CODE_CITY.size());
				List<TCity> province =jdbc.query(ApplicationContextTool.getDataSource(), "select * from t_city where postcode is  null", TCity.class, null, 0, 0);
				if(province!=null&&province.size()>0){
					for (TCity city : province) {
						Env.PROVINCE.put(city.getId(), city.getName());
					}
				}
				List<TMobileHead> mobiles = jdbc.query(ApplicationContextTool.getDataSource(), "select * from t_mobile_head", TMobileHead.class, null, 0, 0);
				if(mobiles!=null&&mobiles.size()>0){
					for(int i=0,len=mobiles.size();i<len;i++){
						Env.MOBILE_HEAD_LIST.add(mobiles.get(i));
					}
				}
				logger.info("加载手机号段为："+Env.MOBILE_HEAD_LIST.size());
				loadWhiteUrl();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
		}
		logger.info("*****************容器完成初始化*****************");
	}
	
	private void loadWhiteUrl() throws IOException {
		List<String> lines = FileUtils.readLines(new ClassPathResource(
				"/conf/allow_all_url.txt").getFile());
		if (lines!=null&&lines.size()>0) {
			for (String line : lines) {
				if (!StringUtils.startsWith(line, "#")
						&& StringUtils.isNotBlank(line)) {
					Env.ALLOW_ALL_URL.add(line);
				}
			}
		}
		lines = FileUtils.readLines(new ClassPathResource(
				"/conf/allow_wechat_url.txt").getFile());
		if (lines!=null&&lines.size()>0){
			for (String line : lines) {
				if (!StringUtils.startsWith(line, "#")
						&& StringUtils.isNotBlank(line)) {
					Env.ALLOW_WECHAT_URL.add(line);
				}
			}
		}
		lines = FileUtils.readLines(new ClassPathResource(
				"/conf/allow_wechat_bind_url.txt").getFile());
		if (lines!=null&&lines.size()>0) {
			for (String line : lines) {
				if (!StringUtils.startsWith(line, "#")
						&& StringUtils.isNotBlank(line)) {
					Env.ALLOW_WECHAT_BIND_URL.add(line);
				}
			}
		}
		System.out.println("-----white url--------");
		//ArrayUtil.print(Env.ALLOW_WECHATURL, false);
	}
	
	
}
