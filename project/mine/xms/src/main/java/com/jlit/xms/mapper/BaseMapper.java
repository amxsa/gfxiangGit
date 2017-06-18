package com.jlit.xms.mapper;

import java.util.List;

import com.jlit.xms.model.PageData;


public interface BaseMapper<T> {
	
	
	 int insert(T entity);
	 
	 //根据对象主键查询
	 T getById(String id);
	 
	 //根据对象主键删除
	 int deleteById(String id);
	 
	 int deleteByIds(String[] ids);
	 //根据对象主键修改
	 int update(T entity);
	 
	 //根据对象动态生成SQL语句
	 List<T> queryForList(T entity);
	 
	 //根据页面数据查询页面数据
	 public List<T> selectPageList( PageData<T> pd);
		
	 //根据页面数据查询总记录数
	 public Integer selectRecords(PageData<T> pd);

}
