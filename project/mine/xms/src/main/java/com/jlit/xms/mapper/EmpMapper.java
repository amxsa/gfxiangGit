package com.jlit.xms.mapper;

import java.util.List;

import com.jlit.xms.model.Emp;

public interface EmpMapper extends BaseMapper<Emp>{

	List<Emp> getByName(String name);
	
}