package com.jlit.xms.web.action;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jlit.db.support.Page;
import com.jlit.xms.model.Dept;
import com.jlit.xms.model.Emp;
import com.jlit.xms.model.PageData;
import com.jlit.xms.service.DeptService;
import com.jlit.xms.service.EmpService;
import com.jlit.xms.util.PaginatedListHelper;
import com.jlit.xms.web.queryBean.DeptQueryBean;
import com.jlit.xms.web.queryBean.EmpQueryBean;
import com.jlit.xms.web.vo.EmpVo;

@Controller
@RequestMapping("/empManage")
@SessionAttributes({"deptIds","deptNames"})
public class EmpManageAction extends BaseAction{
	
	@Resource
	private EmpService empService;
	@Autowired
	private DeptService deptService;
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value="/list")
	public String searchList(ModelMap map,Emp emp,String page,EmpQueryBean queryBean,@RequestParam(value="type",required=false)String type) throws Exception{
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("queryBean", queryBean);
		Page data = empService.getPageData(new PageData<Emp>(null, (page==null?1:Integer.parseInt(page)), emp, queryMap));
		PaginatedListHelper listHelper = new PaginatedListHelper(data);
		List<Dept> deptList = deptService.queryForList(new Dept());
		map.put("empList", listHelper);
		map.put("queryBean", queryBean);
		map.put("emp", emp);
		map.put("deptList", deptList);
		if (type==null) {
			map.put("deptIds", "");
			map.put("deptNames", "");
		}
		
		return "/pages/emp/empList";
	}
	@RequestMapping(value="/saveDeptInfo")
	@ResponseBody
	public Object saveDeptInfo(String deptIds,String deptNames,ModelMap map){
		map.put("deptIds", deptIds);
		map.put("deptNames", deptNames);
		return 0;
	}
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(Emp emp,ModelMap map,String deptId,String id){
		List<Emp> emps=new ArrayList<Emp>();
		Integer result=0;
		try {
			if (StringUtils.isNotBlank(id)) {
				Dept dept = deptService.getById(deptId);
				emps = empService.getByName(emp.getName());
				if (emps.size()>0) {
					if (emps.get(0).getId().equals(id)) {
						emp.setDept(dept);
						empService.update(emp);
					}else{
						result=1;//重名
					}
				}else{
					emp.setDept(dept);
					empService.update(emp);
				}
			}else{
				emps = empService.getByName(emp.getName());
				if (emps.size()>0) {
					return 1;
				}
				emp.setDept(deptService.getById(deptId));
				emp.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				empService.insert(emp);
			}
		} catch (Exception e) {
			result=2;
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/deleteEmp")
	public Object deleteEmp(String id,ModelMap map,Emp emp,String page,EmpQueryBean queryBean,String type) throws Exception{
		empService.deleteById(id);
		return searchList(map, emp, page, queryBean,type);
	}
	
	@RequestMapping(value="/updateEmp")
	public String updateEmp(String id,HttpServletRequest request) throws Exception{
		Emp emp=null;
		if (StringUtils.isNotBlank(id)) {
			emp = empService.getById(id);
		}
		request.setAttribute("emp", emp);
		request.setAttribute("easyBirthday", sdf.format(emp.getBirthday()));
		return "/pages/emp/empAdd";
	}
	
	@RequestMapping(value="/viewEmp")
	public String viewEmp(String id,HttpServletRequest request) throws Exception{
		Emp emp=null;
		if (StringUtils.isNotBlank(id)) {
			emp = empService.getById(id);
		}
		EmpVo vo=empService.getVoById(id);
		request.setAttribute("emp", vo);
		return "/pages/emp/empDetails";
	}
}
