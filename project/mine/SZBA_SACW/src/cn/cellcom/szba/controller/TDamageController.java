package cn.cellcom.szba.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
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

import cn.cellcom.szba.biz.TCasePropertyBiz;
import cn.cellcom.szba.biz.TDamageBiz;

import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.MyPagerUtil;

import cn.cellcom.szba.domain.TCase;
import cn.cellcom.szba.domain.TDemage;

import cn.cellcom.szba.domain.TProperty;

import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.util.ArrayUtil;

@Controller
@RequestMapping("/damage")
public class TDamageController {
	private static Log log = LogFactory.getLog(TDamageController.class);
	/**
	 * 查询损毁
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page){
		
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = requ.getParameterMap();
		ListAndPager<TDemage> list;
		try {
			list = TDamageBiz.queryForPage(map, page);
		 
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TDemage> tDemages = list.getList();
			
			
			
			requ.setAttribute("tDemages", tDemages);
			
			requ.setAttribute("page", proPage);
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error("",e);
		}
		return "/jsp/damage/demage";
	}
	
	@RequestMapping("/queryForTProPage")
	public String queryForTProPage(HttpServletRequest request, Pager page,ConditionParam condition) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> paramsMap = request.getParameterMap();
		
		ListAndPager<TProperty> list;
		list = TDamageBiz.queryForTProPage(paramsMap, page);
		page = list.getPager();
		List<TProperty> tProperties = list.getList();
		request.setAttribute("tProperties", tProperties);
		request.setAttribute("page", page);
		request.setAttribute("condition", condition);
		request.setAttribute("condiParams", request.getParameterMap());
		return "/jsp/damage/damagePreAddorEditProList";
	}
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request){
		String pId=request.getParameter("pId");
		String pno=request.getParameter("pno");
		String DTime=request.getParameter("DTime");
		String dAccount=request.getParameter("dAccount");
		String description=request.getParameter("description");
		String msg=TDamageBiz.insert(pId,pno,DTime,dAccount,description);
		request.setAttribute("msg", msg);
		return "/jsp/damage/addDamage";
		
	}

	@RequestMapping("/editDamage")
	public String editDamage(HttpServletRequest requ){
		String action=requ.getParameter("action");
		if("goto".equals(action)){
			
			String did=requ.getParameter("did");
			TDemage tDemage=TDamageBiz.findByTDId(did);
			requ.setAttribute("tDemage", tDemage);
			
		}
		else if("edit".equals(action)){
			
			String id=requ.getParameter("id");
			
			String pno=requ.getParameter("pno");
			
			String DTime=requ.getParameter("DTime");
			
			String dAccount=requ.getParameter("dAccount");
			String description=requ.getParameter("description");
			String  result=TDamageBiz.editById(id,pno,DTime,dAccount,description);
			
			TDemage tDemage=TDamageBiz.findByTDId(id);
			requ.setAttribute("tDemage", tDemage);
			requ.setAttribute("msg", result);
		}
		
		return "/jsp/damage/editDamage";
	
		
		
		
	}

	/**
	 * 损毁删除
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delDamage(HttpServletRequest requ){
		String DId = requ.getParameter("DId");
		String message = "";
		if(StringUtils.isBlank(DId)){
			DId = "-1";
		}
		
		try {
			int num=TDamageBiz.updateById(DId);
			if(num!=-1){
				message = "Y";
			}
		} catch (Exception e) {
			log.error("",e);
			message = "F";
		}
		
		
		return message;
	}
	
}
