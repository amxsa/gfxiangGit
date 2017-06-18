package com.jlit.xms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import com.jlit.xms.model.Menu;
import com.jlit.xms.model.TestCommodity;
import com.jlit.xms.web.queryBean.MenuQueryBean;

public interface MenuMapper extends BaseMapper<Menu>{
	
	@Insert(value="insert into test_commodity (id, name,logo,current_price,category_id)values (#{id}, #{name}, #{logo},#{currentPrice},#{categoryId} )")
	public int insertByObject(TestCommodity co);

	//@Select(value="select * from test_commodity where id=#{commodityId}")
	//public TestCommodity getById(String commodityId);

	public List<Menu> searchPageData(MenuQueryBean queryBean);
	
	public Integer searchRecords(MenuQueryBean queryBean);

}