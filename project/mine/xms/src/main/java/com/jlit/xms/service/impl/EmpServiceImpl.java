package com.jlit.xms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jlit.xms.common.spring.ApplicationContextTool;
import com.jlit.xms.model.Emp;
import com.jlit.xms.service.EmpService;
import com.jlit.xms.web.vo.EmpVo;
import common.DB.JDBC;

@Service(value="empService")
public class EmpServiceImpl extends BaseServiceImpl<Emp>  implements EmpService {

	@Override
	public List<Emp> getByName(String name) {
		// TODO Auto-generated method stub
		return empMapper.getByName(name);
	}

	@Override
	public EmpVo getVoById(String id) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		StringBuffer sql=new StringBuffer();
		sql.append(" select e.id id,e.name name,e.id_card idCard,e.phone phone,e.birthday birthday, ");
		sql.append(" e.info info,e.age age,d.name deptName ");
		sql.append(" from t_emp e left join t_dept d on e.dept_id=d.id ");
		sql.append(" where e.id=? ");
		try {
			EmpVo vo = jdbc.queryForObject(ApplicationContextTool.getDataSource(), sql.toString(), EmpVo.class, new Object[]{id} );
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
