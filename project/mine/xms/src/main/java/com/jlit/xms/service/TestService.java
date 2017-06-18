package com.jlit.xms.service;

import com.jlit.xms.model.TestCommodity;
import common.DB.JDBC;

public interface TestService extends BaseService<TestCommodity>{

	int insertByObject(TestCommodity commodity);

	TestCommodity getById(String commodityId);

	TestCommodity getObject(JDBC jdbc, String commodityId);

}
