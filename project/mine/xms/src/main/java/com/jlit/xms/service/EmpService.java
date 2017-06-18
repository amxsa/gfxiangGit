package com.jlit.xms.service;

import java.util.List;

import com.jlit.xms.model.Emp;
import com.jlit.xms.web.vo.EmpVo;

public interface EmpService extends BaseService<Emp>{

	List<Emp> getByName(String name);

	EmpVo getVoById(String id);
	
}
