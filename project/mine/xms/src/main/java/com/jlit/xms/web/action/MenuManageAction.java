package com.jlit.xms.web.action;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jlit.db.support.Page;
import com.jlit.xms.model.Menu;
import com.jlit.xms.model.PageData;
import com.jlit.xms.service.MenuService;
import com.jlit.xms.util.PaginatedListHelper;
import com.jlit.xms.web.queryBean.MenuQueryBean;

@Controller
@RequestMapping("/menuManage")
public class MenuManageAction extends BaseAction{
	
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value="/list")
	public String searchMenuList(ModelMap map,Menu menu,String page,MenuQueryBean queryBean) throws Exception{
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("queryBean", queryBean);
		Page data = menuService.getPageData(new PageData<Menu>(null, (page==null?1:Integer.parseInt(page)), menu, queryMap));
		PaginatedListHelper listHelper = new PaginatedListHelper(data);
		map.put("menuList", listHelper);
		map.put("queryBean", queryBean);
		map.put("menu", menu);
		return "/pages/menu/menuList";
	}
	
}
