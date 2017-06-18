package com.gf.ims.common.env;

import java.sql.Connection;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import com.gf.ims.common.db.jdbc.JDBC;

public class Env {
	private static final Log logger = LogFactory.getLog(Env.class);
	public static JDBC jdbc=null;
	public static DataSource DS=null;
	public static WebApplicationContext context=null;
	public static Connection conn=null;
	
	static{
		try {
			jdbc=ApplicationContextTool.getJdbc();
			DS=ApplicationContextTool.getDataSource();
			context= ApplicationContextTool.getContext();
			conn=DS.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getUUID(){
		
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
