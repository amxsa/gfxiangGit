package com.gf.ims.service.impl;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gf.ims.common.db.bean.JdbcResult;
import com.gf.ims.common.db.jdbc.ConnConf;
import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.ApplicationContextTool;
import com.gf.ims.service.DBOperateService;

@Service(value="dbOperateService")
public class DBOperateServiceImpl implements  DBOperateService{

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
				str= jdbc.query(ApplicationContextTool.getDataSource(),sql);
			}else{
				str= jdbc.query(jdbc.getConnection(),sql);
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
