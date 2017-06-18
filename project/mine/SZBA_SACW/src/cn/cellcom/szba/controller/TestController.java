package cn.cellcom.szba.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.SessionBiz;
import cn.cellcom.szba.biz.TPropertyBiz;
import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.vo.PropertyVO;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/property")
public class TestController {
	
	
	@RequestMapping("/sort")
	public String  mytest(HttpServletRequest req,ConditionParam condition,Pager page){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = req.getParameterMap();
		String viewName=(String) req.getParameter("viewName");
		List<PropertyVO> pro=new ArrayList<>();
		Map<String, Object> loginForm = SessionBiz.getLoginForm(req.getSession());
		ListAndPager<PropertyVO> list = TPropertyBiz.queryForPage(map, page, loginForm, viewName);
		
		Pager proPage=list.getPager();
		if (proPage!=null) {
			proPage.setSizePerPage(page.getSizePerPage());
			pro=list.getList();
		}
		req.setAttribute("condition", condition);
		req.setAttribute("page", page);
		req.setAttribute("properties", pro);
		
		return "/jsp/propertyInfo/test";
	}
}
