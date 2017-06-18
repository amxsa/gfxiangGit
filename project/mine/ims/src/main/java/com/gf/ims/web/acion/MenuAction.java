package com.gf.ims.web.acion;


import java.io.IOException;
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
import com.gf.ims.service.MenuService;
import com.gf.imsCommon.ims.module.Menu;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/menu")
public class MenuAction{
	Logger log =Logger.getLogger(MenuAction.class);
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value="/menuManage")
	public String menuManage(HttpServletRequest request,HttpServletResponse response){
		
		return "/pages/main/showMenu";
	}
	
	@RequestMapping(value="/getMenus")
	@ResponseBody
	public Object getMenus(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
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
	}
}
