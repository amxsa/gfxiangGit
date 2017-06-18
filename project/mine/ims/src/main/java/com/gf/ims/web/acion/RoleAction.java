package com.gf.ims.web.acion;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gf.ims.common.env.CommonResponse;
import com.gf.ims.common.env.Env;
import com.gf.ims.common.page.PageResult;
import com.gf.ims.service.MenuService;
import com.gf.ims.service.RoleService;
import com.gf.ims.web.queryBean.RoleQueryBean;
import com.gf.imsCommon.ims.module.Menu;
import com.gf.imsCommon.ims.module.Role;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/role")
public class RoleAction{
	Logger log =Logger.getLogger(RoleAction.class);
	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value="/roleManage")
	public String roleManage(RoleQueryBean roleQueryBean,HttpServletRequest request,HttpServletResponse response){
		String pageStr=request.getParameter("pageStr");
		Integer page=pageStr==null?1:Integer.parseInt(pageStr);
		if (roleQueryBean==null) {
			roleQueryBean=new RoleQueryBean();
		}
		roleQueryBean.setPageNumber(page);
		roleQueryBean.setPageSize(5);
		PageResult<Role> roleList = roleService.findRoleList(roleQueryBean);
		request.setAttribute("pageResult", roleList);
		request.setAttribute("roleQueryBean", roleQueryBean);
		return "/pages/main/role/roleList";
	}
	
	@RequestMapping(value="/roleAdd")
	public String roleAdd(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		if (StringUtils.isNotBlank(roleId)) {
			Role role=roleService.getById(roleId);
			request.setAttribute("role", role);
		}
		return "/pages/main/role/roleAdd";
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(HttpServletRequest request) throws ParseException {
		String id = request.getParameter("id");
		String roleName = request.getParameter("roleName");
		String roleNote = request.getParameter("roleNote");
		String createAccount = request.getParameter("createAccount");
		String time = request.getParameter("createTime");
		String menus = request.getParameter("menus");
		Role role=new Role(id,roleName,roleNote,createAccount,null);
		if (StringUtils.isNotBlank(time)) {
			Date createTime = new Date(time);
			role.setCreateTime(createTime);
		}
		CommonResponse cr=new CommonResponse();
		if (StringUtils.isBlank(role.getRoleName())) {
			cr.setSuccess(false);
			cr.setMessage("角色名称不可为空");
			return cr;
		}
		Role r=roleService.findByName(role.getRoleName());
		if (r!=null) {
			if (StringUtils.isNotBlank(role.getId())) {
				if (!r.getId().equals(role.getId())) {
					cr.setSuccess(false);
					cr.setMessage("角色名称重复");
					return cr;
				}
			}else{
				cr.setSuccess(false);
				cr.setMessage("角色名称重复");
				return cr;
			}
		}
		
		try {
			if (StringUtils.isNotBlank(role.getId())) {//编辑
					roleService.update(role);
			}else{
				role.setRoleName(roleName);
				role.setRoleNote(roleNote);
				role.setId(Env.getUUID());
				role.setCreateTime(new Date());
				role.setCreateAccount("admin");
				role=roleService.save(role);
			}
			roleService.saveRoleMenus(role.getId(),menus);
		} catch (Exception e1) {
			cr.setSuccess(false);
			cr.setMessage("服务器异常");
			e1.printStackTrace();
		}
		cr.setMessage("保存成功");
		return cr;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request){
		CommonResponse cr=new CommonResponse();
		String roleId = request.getParameter("roleId");
		try {
			if (StringUtils.isNotBlank(roleId)) {
				roleService.deleteRoleById(roleId);
			}
		} catch (Exception e1) {
			cr.setSuccess(false);
			cr.setMessage("服务器异常");
			e1.printStackTrace();
		}
		cr.setMessage("删除成功");
		return cr;
	}
	
	//获取所有菜单json
	@RequestMapping(value="/getMenus")
	@ResponseBody
	public Object getMenus(HttpServletRequest request){
		List<Menu> menuList = menuService.queryMenuTree("");
		return menuList;
	}
	
	//已选菜单json
	@RequestMapping(value="/getCheckedMenus")
	@ResponseBody
	public Object getCheckedMenus(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		String json=roleService.getMenusJsonByRoleId(roleId);
		if (json==null) {
			json = "noData";
		}
		return json;
	}
	
	
	/*@RequestMapping(value="/getMenus")
	@ResponseBody
	public Object getMenus(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		String a="&#xe"+type;
		List<Menu> list = menuService.queryMenuTree(type);
		return list;
	}
	
	@RequestMapping(value="/saveMenu")
	@ResponseBody
	public Object saveMenu(HttpServletRequest request,HttpServletResponse response) {
		String id = StringUtils.trimToNull(request.getParameter("id"));
		String name = StringUtils.trimToNull(request.getParameter("name"));
		String url = StringUtils.trimToNull(request.getParameter("url"));
		String target = StringUtils.trimToNull(request.getParameter("target"));
		String css = StringUtils.trimToNull(request.getParameter("css"));
		String icon = StringUtils.trimToNull(request.getParameter("icon"));
		String description = StringUtils.trimToNull(request.getParameter("description"));
		String parentId = StringUtils.trimToNull(request.getParameter("parentId"));
		String isLeaf = StringUtils.trimToNull(request.getParameter("isLeaf"));
		String priority = StringUtils.trimToNull(request.getParameter("priority"));
		String status = StringUtils.trimToNull(request.getParameter("status"));
		String cntidName = StringUtils.trimToNull(request.getParameter("cntidName"));
		String sqlCondition = StringUtils.trimToNull(request.getParameter("sqlCondition"));
		String levels = StringUtils.trimToNull(request.getParameter("levels"));
		Menu menu = new Menu();
		menu.setName(name);
		menu.setId(id == null ? null : Integer.parseInt(id));
		menu.setUrl(url);
		menu.setTarget(target);
		menu.setCss(css);
		menu.setIcon(icon);
		menu.setDescription(description);
		menu.setParentId(parentId == null ? null : Integer.parseInt(parentId));
		menu.setIsLeaf(isLeaf);
		menu.setPriority(priority == null ? null : Integer.parseInt(priority));
		menu.setStatus(status);
		menu.setCntidName(cntidName);
		menu.setSqlCondition(sqlCondition);
		menu.setLevels(levels==null?null:Integer.parseInt(levels));
		try {
			Menu record = menuService.saveMenu(menu);
			CommonResponse cr = new CommonResponse();
			if (null != record) {
				cr.setSuccess(true);
				cr.setData(record);
			} else {
				cr.setSuccess(false);
			}
			return cr;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}*/
}
