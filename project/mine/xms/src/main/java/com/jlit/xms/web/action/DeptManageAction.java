package com.jlit.xms.web.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jlit.db.support.Page;
import com.jlit.xms.model.Dept;
import com.jlit.xms.model.Emp;
import com.jlit.xms.model.Level;
import com.jlit.xms.model.PageData;
import com.jlit.xms.service.DeptService;
import com.jlit.xms.service.LevelService;
import com.jlit.xms.util.PaginatedListHelper;
import com.jlit.xms.web.queryBean.DeptQueryBean;
import com.jlit.xms.web.vo.DeptVo;

@Controller
@RequestMapping("/deptManage")
public class DeptManageAction extends BaseAction{
	
	@Resource
	private DeptService deptService;
	@Resource
	private LevelService levelService;
	
	@RequestMapping(value="/list")
	@SuppressWarnings("unchecked")
	public String searchDeptList(ModelMap map,Dept dept,String page,DeptQueryBean queryBean) throws Exception{
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("queryBean", queryBean);
		Page data = deptService.getPageData(new PageData<Dept>(null, (page==null?1:Integer.parseInt(page)), dept, queryMap));
		List<Dept> list=(List<Dept>) data.getData();
		data.setData(DeptVo.getVoList(list));
		PaginatedListHelper listHelper = new PaginatedListHelper(data);
		map.put("deptList", listHelper);
		map.put("queryBean", queryBean);
		map.put("dept", dept);
		return "/pages/dept/deptList";
	}
	
	@RequestMapping(value="/getLevel")
	@ResponseBody
	public Object getLevel(){
		Map<String,Object> map=new HashMap<String,Object>();
		Integer result=0;
		List<Level> list=new ArrayList<Level>();
		try {
			list = deptService.getLevels();
		} catch (Exception e) {
			result=1;
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("list", list);
		return map;
	}
	
	@RequestMapping(value="/getDept")
	@ResponseBody
	public Object getDept(){
		Map<String,Object> map=new HashMap<String,Object>();
		Integer result=0;
		List<Dept> list=new ArrayList<Dept>();
		try {
			list = deptService.queryForList(new Dept());
		} catch (Exception e) {
			result=1;
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("list", list);
		return map;
	}
	
	@RequestMapping(value="/deleteDept")
	public Object deleteDept(String id,ModelMap map,Dept dept,String page,DeptQueryBean queryBean) throws Exception{
		deptService.deleteById(id);
		return searchDeptList(map, dept, page, queryBean);
	}
	
	@RequestMapping(value="/updateDept")
	public String updateDept(String id,HttpServletRequest request) throws Exception{
		Dept dept=null;
		if (StringUtils.isNotBlank(id)) {
			dept = deptService.getById(id);
		}
		request.setAttribute("dept", dept);
		return "/pages/dept/deptAdd";
	}
	
	@RequestMapping(value="/viewDept")
	public String viewDept(String id,HttpServletRequest request) throws Exception{
		String names="";
		Dept dept=null;
		if (StringUtils.isNotBlank(id)) {
			dept = deptService.getById(id);
		}
		List<Emp> emps = dept.getEmps();
		if (emps.size()>0) {
			for (Emp emp : emps) {
				names+=emp.getName()+",";
			}
		}
		request.setAttribute("dept", dept);
		if (names.length()>0) {
			request.setAttribute("names", names.substring(0, names.length()-1));
		}
		return "/pages/dept/deptDetails";
	}
	
	
//	@RequestMapping(value="/save")
//	@ResponseBody
//	public Object save(@RequestParam("file") CommonsMultipartFile[] photos,Dept dept){
//		String[] photoFileName={".jpg"};
//		File destFile;
////		String serverUrl=InitSystemPara.systemParaMap.get("fileServerUrl")+"/imagesUpload";
//		String serverUrl="http://115.28.56.254:8090/fms/imagesUpload";
//		try {
//			if(photos != null){
//				File[] files = new File[photos.length];
////				DiskFileItem df=(DiskFileItem) photos[0].getFileItem();
////				files[0]=df.getStoreLocation();
////				
////				destFile = FileUtil.changeSuffix(files[0], photoFileName[0]);
////				files[0]=destFile;
//				
//				String retVal=HttpClientUtil.transImgToFms(files,serverUrl);
//				JSONObject retObj=JSONObject.fromObject(retVal);
//				JSONArray list=retObj.getJSONArray("list");
//				String photoUrl = list.getJSONObject(0).getString("o_path");
//				String thumPhotoUrl = list.getJSONObject(0).getString("t_path");
//				dept.setLogo(thumPhotoUrl);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		deptService.insert(dept);
//		return null;
//	}
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(Dept dept,ModelMap map,String levelId,String id){
		List<Dept> depts=new ArrayList<Dept>();
		Integer result=0;
		try {
			if (StringUtils.isNotBlank(id)) {
				Level level = levelService.getById(levelId);
				depts = deptService.getByName(dept.getName());
				if (depts.size()>0) {
					if (depts.get(0).getId().equals(id)) {
						dept.setLevel(level);
						deptService.update(dept);
					}else{
						result=1;//重名
					}
				}else{
					dept.setLevel(level);
					deptService.update(dept);
				}
			}else{
				depts = deptService.getByName(dept.getName());
				if (depts.size()>0) {
					return 1;
				}
				dept.setLevel(levelService.getById(levelId));
				dept.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				deptService.insert(dept);
			}
		} catch (Exception e) {
			result=2;
			e.printStackTrace();
		}
		return result;
	}
}
