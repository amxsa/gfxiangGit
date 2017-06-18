package com.gf.ims.common.env;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import com.gf.ims.common.db.jdbc.JDBC;


public class ApplicationContextTool {

	public static boolean info = false;

	private static final Log logger = LogFactory.getLog(ApplicationContextTool.class);

	private static WebApplicationContext context = null;

	public ApplicationContextTool() {

	}

	public static WebApplicationContext getContext() {
		return context;
	}

	public static void setContext(WebApplicationContext context) {
		ApplicationContextTool.context = context;
	}

	public static Object getBean(String beanName) {
		if (context.containsBean(beanName))
			return context.getBean(beanName);
		return null;
	}

	public static DataSource getDataSource() {
		if (null == context) {
			return null;
		}

		return (DataSource) context.getBean("jdbcDataSource");
	}

	public static Connection getConnection() {
		try {
			DataSource ds = getDataSource();
			if (null == ds) {
				return null;
			}
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error(e);
			return null;
		}
	}
	
	public static JDBC getJdbc() {
		return (JDBC) context.getBean("jdbc");
	}

}
