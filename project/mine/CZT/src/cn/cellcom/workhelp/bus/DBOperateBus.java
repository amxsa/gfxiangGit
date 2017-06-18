package cn.cellcom.workhelp.bus;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.ConnConf;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.workhelp.po.JdbcResult;

public class DBOperateBus {
	private static final Log log = LogFactory.getLog(DBOperateBus.class);
	public JdbcResult executeQuery(String sql,ConnConf conf) {
		if (StringUtils.isBlank(sql)) {
			return null;
		}
		JDBC jdbc = null;
		if(conf==null){
			jdbc =  ApplicationContextTool.getJdbc();
		}else{
			jdbc = new JDBC(conf);
		}
		String[] str = null;
		try {
			if(conf==null){
				str= jdbc.query(ApplicationContextTool.getDataSource(),
						sql);
			}else{
				str= jdbc.query(jdbc.getConnection(),
						sql);
			}
			 
			if(str!=null){
				JdbcResult result = new JdbcResult();
				result.setCount(Integer.parseInt(str[0]));
				result.setResult(str[1]);
				return result;
			}
			return null;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		

	}

	public int executeUpdate(String sql,ConnConf conf) {
		JDBC jdbc = null;
		if(conf==null){
			jdbc =  ApplicationContextTool.getJdbc();
		}else{
			jdbc = new JDBC(conf);
		}
		try {
			if(conf==null){
				return jdbc.update(ApplicationContextTool.getDataSource(), sql,
						null);
			}else{
				return jdbc.update(jdbc.getConnection(), sql,
						null);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
	}

	
}
