package cn.cellcom.szba.biz;
import static cn.cellcom.szba.common.Env.DS;
import static cn.cellcom.szba.common.Env.ENV_PRO;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.run.Arguments;
import cn.cellcom.szba.run.SqlConsumeTime;
import cn.cellcom.szba.service.impl.FlownodeEngine;
import cn.open.db.JDBC;
import cn.open.db.sqlutils.TableMapXmlFactory;
import cn.open.util.ArrayUtil;

public class GlobalListener implements ServletContextListener {
	private static Log log = LogFactory.getLog(GlobalListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			// 清除数据库驱动程序
			JDBC.deregisterDrivers();
		} catch (SQLException e) {
			log.error("", e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		SpringWebApplicataionContext.setContext(context);

		// 读取env.properties信息,加载至内存
		try {
			Resource resource = new ClassPathResource("/conf/env.properties");
			ENV_PRO = PropertiesLoaderUtils.loadProperties(resource);
			ENV_PRO.list(System.out);
			
			loadWhiteUrl();
			//无业务意义的查询.因为首次登录比较慢,很有可能第一次执行sql的时候才去初始化连接池,故在此先做好。
			initDatasource();
			
			//initBusinessData
			initBusinessData();
			
			//初始化流程结点引擎
			String realPath = servletContext.getRealPath(FlownodeEngine.getRootDirectoryPath());
			FlownodeEngine.setRealPath(realPath);
			FlownodeEngine.init();
			//开启文件监视流程结点文件，若有更新则刷新结点
			FileAlterationMonitor monitor = new FileAlterationMonitor(5000);
			FileAlterationObserver observer = new FileAlterationObserver(new File(FlownodeEngine.getRealPath()));
			monitor.addObserver(observer);
			observer.addListener(new FlownodeFileListener());
			monitor.start();
			//启动重新加载业务数据的线程
			new Thread(new Arguments()).start();
			//获取日志文件中sql记录并且耗时大于2的记录
			//Timer timer = new Timer();
			//timer.schedule(new SqlConsumeTime(), SqlConsumeTime.getFixedTime(), SqlConsumeTime.getMilliseconds());
			
			TableMapXmlFactory.loadTableMapXml("classpath:/xml");
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
	}
	
	private void initBusinessData() throws Exception {
		
		Env.viewNameList.addAll(Env.PROVIEWNAMEGROUP1.keySet());
		Env.viewNameList.addAll(Env.PROVIEWNAMEGROUP2.keySet());
		Env.viewNameList.addAll(Env.PROVIEWNAMEGROUP3.keySet());
		Env.viewNameList.addAll(Env.PROVIEWNAMEGROUP4.keySet());
		Env.viewNameList.addAll(Env.PROVIEWNAMEGROUP5.keySet());
		Env.viewNameList.addAll(Env.PROVIEWNAMEGROUP6.keySet());
		TAccountBiz.initDepartments();
		TAccountBiz.initAccounts();
	}

	
	private void initDatasource() throws SQLException {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		jdbc.query(DS, "select sysdate from dual");
	}

	/**
	 * 加载URL白名单列表
	 * 
	 * @throws IOException
	 */
	private void loadWhiteUrl() throws IOException {
		List<String> lines = FileUtils.readLines(new ClassPathResource(
				"/conf/white_url.txt").getFile());
		if (ArrayUtil.isNotEmpty(lines)) {
			for (String line : lines) {
				if (!StringUtils.startsWith(line, "#")&&StringUtils.isNotBlank(line)) {
					Env.WHITE_URL.add(line);
				}
			}
		}
		System.out.println("-----white url--------");
		ArrayUtil.print(Env.WHITE_URL, false);
	}
}
