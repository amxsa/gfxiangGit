package com.jlit.xms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jlit.xms.common.spring.ApplicationContextTool;
import com.jlit.xms.model.Dept;
import com.jlit.xms.model.Level;
import com.jlit.xms.service.DeptService;
import common.DB.JDBC;

@Service(value="deptService")
public class DeptServiceImpl extends BaseServiceImpl<Dept>  implements DeptService {

	@Override
	public List<Level> getLevels() {
		
		return levelMapper.queryForList(new Level());
	}

	@Override
	public void testTransaction(JDBC jdbc) {
		if (jdbc==null) {
			jdbc=ApplicationContextTool.getJdbc();
		}
			Dept dept1 = deptMapper.getById("1");
			dept1.setName("测试1");
			deptMapper.update(dept1);
			throw new RuntimeException("hahahhaha");
	}

	@Override
	public List<Dept> getByName(String name) {
		// TODO Auto-generated method stub
		return deptMapper.getByName(name);
	}

}
