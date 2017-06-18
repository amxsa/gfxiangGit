package com.jlit.xms.service;

import java.util.List;

import com.jlit.xms.model.Dept;
import com.jlit.xms.model.Level;
import common.DB.JDBC;

public interface DeptService extends BaseService<Dept>{

	List<Level> getLevels();

	void testTransaction(JDBC jdbc);

	List<Dept> getByName(String name);
	
}
