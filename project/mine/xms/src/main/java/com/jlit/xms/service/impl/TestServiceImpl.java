package com.jlit.xms.service.impl;

import org.springframework.stereotype.Service;

import com.jlit.xms.common.spring.ApplicationContextTool;
import com.jlit.xms.model.TestCommodity;
import com.jlit.xms.service.TestService;
import common.DB.JDBC;

@Service(value="testService")
public class TestServiceImpl extends BaseServiceImpl<TestCommodity>  implements TestService {

	@Override
	public int insertByObject(TestCommodity commodity) {
		
//		return testCommodityMapper.insertByObject(commodity);
		return testCommodityMapper.insert(commodity);
	}

	@Override
	public TestCommodity getById(String commodityId) {
		// TODO Auto-generated method stub
		return testCommodityMapper.getById(commodityId);
	}

	@Override
	public TestCommodity getObject(JDBC jdbc, String commodityId) {
		if (jdbc==null) {
			jdbc=ApplicationContextTool.getJdbc();
		}
		String sql="select * from test_commodity";
		try {
			TestCommodity commodity = jdbc.queryForObject(ApplicationContextTool.getDataSource(), sql, TestCommodity.class, null);
			return commodity;
		} catch  (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
