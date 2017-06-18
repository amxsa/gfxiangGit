package com.jlit.xms.web.action;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlit.model.User;
import com.jlit.xms.api.servlet.LoginServlet;
import com.jlit.xms.common.spring.ApplicationContextTool;
import com.jlit.xms.service.DeptService;

import common.DB.JDBC;

@Controller
@RequestMapping("/userManage")
public class UserManageAction extends BaseAction{
	Logger log =Logger.getLogger(UserManageAction.class);
	@Resource
	private DeptService deptService;
	
	@RequestMapping(value="/login")
	@ResponseBody
	public Object getUser(String userAccount,String pwd,ModelMap map){
		Integer result=1;
		User user =null;
		log.info("userAccount=="+userAccount+"----pwd==="+pwd);
		try {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			String sql="select * from user where user_account=? and password=? ";
			user = jdbc.queryForObject(ApplicationContextTool.getDataSource(), sql, User.class, new Object[]{userAccount,pwd});
			if (user==null) {
				result=0;
			}
		} catch (Exception e) {
			result=-1;
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("user", user);
		log.info(map.toString());
		return map;
	}
	
}
