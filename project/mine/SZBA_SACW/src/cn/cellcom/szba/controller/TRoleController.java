package cn.cellcom.szba.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.cellcom.szba.biz.TAccountBiz;
import cn.cellcom.szba.biz.TIconBiz;
import cn.cellcom.szba.biz.TRoleBiz;
import cn.cellcom.szba.common.DataMsgTool;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TIcon;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.vo.RoleMenuCheck;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.util.CommonUtil;

@Controller
@RequestMapping("/role")
public class TRoleController {
	
	private static Log log = LogFactory.getLog(TAccountController.class);
	
	private TRoleBiz roleBiz = new TRoleBiz();
	
	/**
	 * 查询用户列表
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page){
		MyPagerUtil.initPager(page);
		
		Map<String, String[]> map = requ.getParameterMap();
		ListAndPager<TRole> list;
		try {
			list = roleBiz.queryForPage(map, page);

			requ.setAttribute("data", list.getList());
			requ.setAttribute("page", list.getPager());
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/role/roleManage";
	}
	
	/**
	 * 查询用户列表
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/allotIconRole")
	public String allotIconRole(HttpServletRequest request){
		String ids = request.getParameter("iconids");
		String result = "";
        String[] iconIds = ids.split(",");
		String[] roleIds = request.getParameterValues("roles");
		for(int i=0;i<roleIds.length;i++){
			result = roleBiz.iconAllot(iconIds,roleIds[i]);
		}
		request.setAttribute("message", result);
		Pager page = new Pager();
		toList(request,page);
		return "/jsp/role/roleList";
	}
	
	@RequestMapping("/allotIconRoleRe")
	public String allotIconRoleRe(HttpServletRequest request){
		String result = "";
		String[] iconIds = request.getParameterValues("icons");
		String roleId = request.getParameter("roleId");
		result = roleBiz.iconAllot(iconIds,roleId);
		request.setAttribute("message", result);
		request.setAttribute("roleId", roleId);
		Pager page = new Pager();
		toListRole(request,page);
		
		return "/jsp/icon/iconManageRe";
	}
	
	/**
	 * 给用户分配角色
	 * @param request
	 * @return
	 */
	@RequestMapping("/allotAccountRole")
	public String allotAccountRole(HttpServletRequest request){
		String account = request.getParameter("account");
		String[] roleIds = request.getParameterValues("roles");
		String roleStr="";
		for(int i=0;i<roleIds.length;i++){
			roleStr=roleStr+roleIds[i]+",";
		}
		if(StringUtils.isNotBlank(roleStr)){
			roleStr=roleStr.substring(0,roleStr.length()-1);
		}
		roleBiz.updateAccountRole(account,roleStr);
		request.setAttribute("message", "success");
		Pager page = new Pager();
		toList(request,page);
		return "/jsp/role/roleList2";
	}
	
	
	/**
	 * 给角色分配用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/allotRoleAccount")
	public String allotRoleAccount(HttpServletRequest request, Pager page){
		String roleId = request.getParameter("roleId");
		String result = "";
		String[] accounts = request.getParameterValues("accounts");
		for(int i=0;i<accounts.length;i++){
			result = roleBiz.accountAllot(accounts[i],roleId);
		}
		request.setAttribute("message", result);
		request.setAttribute("roleId", roleId);
		request.setAttribute("page", page);
		toListAccount(request,page);
		return "/jsp/account/accountList";
	}
	

	/*
	 * 桌面图标分配给角色
	 */
	@RequestMapping("/allotIcon")
	public String allotIcon(HttpServletRequest request, Pager page){
		String[] iconIds = request.getParameterValues("icons");
		StringBuilder sb=new StringBuilder();
		for(int i =0 ;i<iconIds.length;i++){
			CommonUtil.makeupArrayStr(sb, iconIds[i], ",");
		}
		toList(request,page);
		request.setAttribute("iconIds", sb.toString());
		return "/jsp/role/roleList";
	}
	
	@RequestMapping("/queryRole")
	public String queryRole(HttpServletRequest request, Pager page){
		String account = request.getParameter("account");
		toList(request,page);
		request.setAttribute("account", account);
		List<TRole> roleList= TRoleBiz.accountRoles(account);
		String roles="";
		if(roleList!=null&&!roleList.isEmpty()){
			for(TRole role:roleList){
				roles=roles+role.getId()+";";
			}
		}
		roles=roles!=""?roles.substring(0,roles.length()-1):"";
		request.setAttribute("roles", roles);
		return "/jsp/role/roleList2";
	}
	
	public void toList(HttpServletRequest request, Pager page){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		ListAndPager<TRole> list;
		try {
			list = roleBiz.roleList(map, page);
			request.setAttribute("data", list.getList());
			request.setAttribute("page", list.getPager());
			request.setAttribute("params", request.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	public void toListAccount(HttpServletRequest request, Pager page){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		ListAndPager<TAccount> list;
		TAccountBiz accountBiz = new TAccountBiz();
		try {
			list = accountBiz.queryForPage(map, page);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			request.setAttribute("data", list.getList());
			request.setAttribute("page", list.getPager());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	public  void toListRole(HttpServletRequest request, Pager page){
		MyPagerUtil.initPager(page);
		String accStr = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
		ListAndPager<TIcon> list;
		TIconBiz tIconBiz = new TIconBiz();
		try {
			list = tIconBiz.queryIconPage(accStr, page);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TIcon> icons = list.getList();
			request.setAttribute("data", icons);
			request.setAttribute("page", proPage);
			request.setAttribute("params", request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 角色管理 分配菜单
	@RequestMapping("/toDistributeMenu")
	public void toDistributeMenu(HttpServletRequest request, HttpServletResponse response, String roleId){
		try{
			List<RoleMenuCheck> list = TRoleBiz.findMenuTreeByRoleId(roleId);
			PrintTool.print(response, PrintTool.printJSONArray(list), "json");
		} catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	@RequestMapping("/distributeMenu")
	public void distributeMenu(HttpServletRequest request, HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String data = request.getParameter("data");
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<RoleMenuCheck>>() {}.getType();
	    List<RoleMenuCheck> checkedList = gson.fromJson(data, collectionType);
	    TRoleBiz.updateRoleMenuByRoleId(roleId, checkedList);
	    try{
    		PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgSuccess()), "json");
	    } catch (IOException e1) {
			log.error(e1.getMessage(),e1);
		}
	}
	
	/*
	 * 添加桌面图标
	 */
	@RequestMapping("add")
	public String add(HttpServletRequest request, TRole entity,HttpServletResponse response){
		String result = TRoleBiz.insert(entity);
		request.setAttribute("result", result);
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("result", result);
        try {
			PrintTool.print(response, PrintTool.printJSON(map), "json");
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		//request.setAttribute("result", result);
		return null;
	}
}
