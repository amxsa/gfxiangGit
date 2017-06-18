package cn.cellcom.szba.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TPropertyBiz;
import cn.cellcom.szba.biz.TWarehouseBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.RequestUtils;
import cn.cellcom.szba.common.utils.TRoleUtils;
import cn.cellcom.szba.domain.TDepartment;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.domain.TWarehouse;
import cn.cellcom.szba.enums.RoleKey;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/warehouse")
public class TWarehouseController {
	private static Log log = LogFactory.getLog(TWarehouseController.class);
	
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page){
		/*log.info("/warehouse/queryForPage");
		
		MyPagerUtil.initPager(page);
		String view = requ.getParameter("viewName");
		
		ListAndPager<TProperty> listAndPager = null;
		
		Map<String, String[]> params = requ.getParameterMap();
		Map loginForm = (Map) requ.getSession().getAttribute("loginForm");
		
		listAndPager = TPropertyBiz.queryForPage(params, page, loginForm, view);
		List<TProperty> properties = listAndPager.getList();
		page = listAndPager.getPager();
		
		requ.setAttribute("page", page);
		requ.setAttribute("params", params);
		requ.setAttribute("properties", properties);*/
		
		return null;
		//return "/jsp/warehouseInfo/"+view;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/toWarehouseManage")
	public String toWarehouseManage(HttpServletRequest request, Pager page){
		MyPagerUtil.initPager(page);
		
		Map<String, String[]> map = request.getParameterMap();
		ListAndPager<TWarehouse> list;
		try {
			List<TRole> roles = TRoleUtils.getCurrRoles(request.getSession());
			String departmentId = "";
			if(TRoleUtils.contain(roles, RoleKey.XTGLY.toString())){//如果是系统管理员则departmentId为传过来的值
				departmentId = request.getParameter("departmentID");
				if(null==departmentId || departmentId.equals("-1")){
					departmentId = "";
				}
			} else{//其他库管理员则只能查询所在部门
				departmentId = ((Map)request.getSession().getAttribute("loginForm")).get("DEPARTMENT_ID").toString();
			}
			Map<String, String[]> extraMap = new HashMap<String, String[]>();
			extraMap.put("departmentId", new String[]{departmentId});
			extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
			list = TWarehouseBiz.queryForPage(extraMap, page);
			
			request.setAttribute("roles", roles);
			request.setAttribute("data", list.getList());
			request.setAttribute("page", list.getPager());
			request.setAttribute("params", request.getParameterMap());
		} catch (Exception e) {
			log.error(e);
		}
		return "/jsp/warehouse/warehouseManage";
	}
	
	@RequestMapping("/toAddWarehouse")
	public String toAddWarehouse(HttpServletRequest request){
		request.setAttribute("roles", TRoleUtils.getCurrRoles(request.getSession()));
		request.setAttribute("params", request.getParameterMap());
		return "/jsp/warehouse/addWarehouse";
	}

	@RequestMapping("/addWarehouse")
	public String addWarehouse(HttpServletRequest request, HttpServletResponse response, TWarehouse entity){
		String account = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
		entity.setCreator(account);
		entity.setValidStatus("Y");
		boolean result = TWarehouseBiz.insert(entity);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		JSONObject json = JSONObject.fromObject(data);
		try{
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("/toUpdateWarehouse")
	public String toUpdateWarehouse(HttpServletRequest request){
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		TWarehouse record = TWarehouseBiz.findWarehouseById(id);
		request.setAttribute("data", record);
		request.setAttribute("roles", TRoleUtils.getCurrRoles(request.getSession()));
		request.setAttribute("params", request.getParameterMap());
		return "/jsp/warehouse/updateWarehouse";
	}
	
	@RequestMapping("/updateWarehouse")
	public String updateWarehouse(HttpServletRequest request, HttpServletResponse response, TWarehouse entity){
		boolean result = TWarehouseBiz.update(entity);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		JSONObject json = JSONObject.fromObject(data);
		try{
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("/toShowWarehouse")
	public String toShowWarehouse(HttpServletRequest request){
		String idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		TWarehouse record = TWarehouseBiz.findWarehouseById(id);
		request.setAttribute("data", record);
//		request.setAttribute("roles", TRoleUtils.getCurrRoles(request.getSession()));
		request.setAttribute("params", request.getParameterMap());
		return "/jsp/warehouse/showWarehouse";
	}
	
	@RequestMapping("/deleteWarehouse")
	public String updateWarehouse(HttpServletRequest request, HttpServletResponse response, Long id){
		boolean result = TWarehouseBiz.delete(id);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		JSONObject json = JSONObject.fromObject(data);
		try{
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("/loadWarehouses")
	public void loadWarehouses(HttpServletRequest request, HttpServletResponse response){
		String departmentId = ((Map)request.getSession().getAttribute("loginForm")).get("DEPARTMENT_ID").toString();
		List<TWarehouse> list = TWarehouseBiz.findWarehouseByDepartment(departmentId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", list);
		JSONObject json = JSONObject.fromObject(data);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}

}