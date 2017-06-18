package com.jlit.xms.service;

import java.util.List;

import com.jlit.db.support.Page;
import com.jlit.xms.model.PageData;

public interface BaseService<T> {

	 int insert(T entity);
	 
	 T getById(String id);
	 
	 int deleteById(String id);
	 
	 int deleteByIds(String[] ids);
	 
	 int update(T entity);
	 
	 List<T> queryForList(T entity);
	 
	 List<T> selectPageList(PageData<T> pd);
	
	 PageData<T> selectPageData(PageData<T> pd);

	 Page getPageData(PageData<T> pd);
}
