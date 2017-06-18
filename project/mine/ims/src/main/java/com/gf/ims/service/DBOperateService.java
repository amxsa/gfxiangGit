package com.gf.ims.service;

import com.gf.ims.common.db.bean.JdbcResult;
import com.gf.ims.common.db.jdbc.ConnConf;

public interface DBOperateService {
	public JdbcResult executeQuery(String sql,ConnConf conf);

	public int executeUpdate(String sql,ConnConf conf);
}
