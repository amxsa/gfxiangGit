package cn.cellcom.szba.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cellcom.szba.biz.TCategoryBiz;
import cn.cellcom.szba.common.CommonResponse;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.domain.TCategory;
import cn.cellcom.szba.domain.TCategoryAttribute;
import cn.open.util.CommonUtil;

@RequestMapping("/category")
@Controller
public class TCategoryController {
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping("/queryCategorys")
	public void queryCategorys(HttpServletRequest requ, HttpServletResponse response){
		List<TCategory> list = TCategoryBiz.findCategory();
		try {
			PrintTool.print(response, PrintTool.printJSONArray(list), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response, TCategory entity){
		try {
			TCategory category = TCategoryBiz.save(entity);
			CommonResponse cr = new CommonResponse();
			if(null!=category){
				cr.setSuccess(true);
				cr.setData(category);
			} else {
				cr.setSuccess(false);
			}
			PrintTool.print(response, PrintTool.printJSON(cr), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	@RequestMapping("/queryById")
	public void queryById(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		
		Long categoryId = CommonUtil.isLong(request.getParameter("categoryId"), true);
		TCategory category = TCategoryBiz.queryById(categoryId);
		
		try {
			response.getWriter().write(category!=null?category.getName():"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//根据分类id查询分类属性
	@RequestMapping("/findCategoryAttributeByCategoryId")
	public void findCategoryAttributeByCategoryId(HttpServletRequest requ, HttpServletResponse response){
		//Map<String, String[]> paramsMap = requ.getParameterMap();
		List<TCategoryAttribute> list = TCategoryBiz.findCategoryAttributeByCategoryId(requ);
		try {
			PrintTool.print(response, PrintTool.printJSONArray(list), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
}
