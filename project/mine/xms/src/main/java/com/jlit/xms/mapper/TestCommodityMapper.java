package com.jlit.xms.mapper;

import org.apache.ibatis.annotations.Insert;

import com.jlit.xms.model.TestCommodity;

public interface TestCommodityMapper extends BaseMapper<TestCommodity>{
	
	@Insert(value="insert into test_commodity (id, name,logo,current_price,category_id)values (#{id}, #{name}, #{logo},#{currentPrice},#{categoryId} )")
	public int insertByObject(TestCommodity co);

	//@Select(value="select * from test_commodity where id=#{commodityId}")
	public TestCommodity getById(String commodityId);
}