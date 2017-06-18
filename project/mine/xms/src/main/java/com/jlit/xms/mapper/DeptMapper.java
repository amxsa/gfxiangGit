package com.jlit.xms.mapper;

import java.util.List;

import com.jlit.xms.model.Dept;

public interface DeptMapper extends BaseMapper<Dept>{

	List<Dept> getByName(String name);
	
}