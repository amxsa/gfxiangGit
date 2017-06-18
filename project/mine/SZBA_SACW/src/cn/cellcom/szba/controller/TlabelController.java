package cn.cellcom.szba.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import cn.cellcom.szba.biz.TDamageBiz;
import cn.cellcom.szba.biz.TLabelBiz;
import cn.cellcom.szba.biz.TPropertyBiz;
import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.domain.TDemage;
import cn.cellcom.szba.domain.TLabel;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
@Controller
@RequestMapping("/label")
public class TlabelController {
	private static Log log = LogFactory.getLog(TlabelController.class);

	/**
	 * 查询标签
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page){
		
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = requ.getParameterMap();
		ListAndPager<TLabel> list;
		try {
			list = TLabelBiz.queryForPage(map, page);
		 
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TLabel> tLabels = list.getList();
			
			
			
			requ.setAttribute("tLabels", tLabels);
			
			requ.setAttribute("page", proPage);
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/label/label";
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
		return "/jsp/label/labelPreAddorEditProList";
	}
	@RequestMapping("/insert")
	public String insert(HttpServletRequest requ){
		String accStr = ((Map)requ.getSession().getAttribute("loginForm")).get("NAME").toString();
		String pno=requ.getParameter("pno");
		
		String newName=requ.getParameter("newName");
		
		String description=requ.getParameter("description");
		
		String msg=TLabelBiz.insert(pno,newName,description,accStr);
		requ.setAttribute("msg", msg);
		return "/jsp/label/addLabel";
		
	}
	
	@RequestMapping("/editLabel")
	public String editDamage(HttpServletRequest requ){
		String action=requ.getParameter("action");
		if("forward".equals(action)){
			String lid=requ.getParameter("lid");
			
			TLabel tLabel=TLabelBiz.findByTDId(lid);
			requ.setAttribute("tLabel", tLabel);
		}
		if("edit".equals(action)){
			String message = "";
			
			try {
				String accStr = ((Map)requ.getSession().getAttribute("loginForm")).get("NAME").toString();
				String pno=requ.getParameter("pno");
				
				String id=requ.getParameter("id");
				String newName=requ.getParameter("newName");
				String olaName=requ.getParameter("olaName");
				String description=requ.getParameter("description");
				TLabelBiz.editLabel(id,pno,olaName,newName,description,accStr);
				message = "Y";
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				message = "F";
				
			}
			requ.setAttribute("msg", message);
		}
		
		return "/jsp/label/editLabel";
	
		
		
		
	}
	@RequestMapping("/delete")
	@ResponseBody
	public String delDamage(HttpServletRequest requ){
		String lid = requ.getParameter("lid");
		String message = "";
		if(StringUtils.isBlank(lid)){
			lid = "-1";
		}
		
		try {
			int num=TLabelBiz.updateById(lid);
			if(num!=-1){
				message = "Y";
				
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			message = "F";
			
		}
		
		
		return message;
	}
}
