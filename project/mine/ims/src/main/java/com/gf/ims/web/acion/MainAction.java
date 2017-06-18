package com.gf.ims.web.acion;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gf.ims.service.MenuService;
import com.gf.imsCommon.ims.module.Menu;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/main")
public class MainAction{
	Logger log =Logger.getLogger(MainAction.class);
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value="/login")
	public String login(){
		
		return "/jsp/loginUI";
	}
	
	@RequestMapping(value="/home")
	public String home(HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("pagi", "home");
		return "/jsp/home/home";
	}
	
	@RequestMapping(value="/top")
	public String top(HttpServletRequest request,HttpServletResponse response){
		
		return "/jsp/nsfw/top";
	}
	
	@RequestMapping(value="/left")
	public String left(){
		
		return "/jsp/nsfw/left";
	}
	
	@RequestMapping(value="/nsfw")
	public String nsfw(HttpServletRequest request,HttpServletResponse response){
		List<Menu> list = menuService.queryMenuTree("0");
		
		request.getSession().setAttribute("pagi", "nsfw");
		request.getSession().setAttribute("menus", list);
		String menusJSON = JSONArray.fromObject(list).toString();
		request.getSession().setAttribute("menusJSON", menusJSON);
		
		return "/jsp/nsfw/frame";
	}
	
	@RequestMapping(value="/icon")
	public String icon(HttpServletRequest request,HttpServletResponse response){
		
		return "/pages/main/icon";
	}
	
}
