package cn.cellcom.szba.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;


import cn.cellcom.szba.biz.TElecEvildenceBiz;
import cn.cellcom.szba.biz.TPropertyBiz;
import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.RequestUtils;
import cn.cellcom.szba.controller.TApplicationController;
import cn.cellcom.szba.domain.TApplication;
import cn.cellcom.szba.domain.TElecEvidence;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/elecevidence")
public class TElecEvidenceController {
	
	private static Log log = LogFactory.getLog(TApplicationController.class);

	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest request, Pager page, ConditionParam condition){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		ListAndPager<TElecEvidence> list;
		String accStr = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		list =  TElecEvildenceBiz.queryForPage(extraMap, page);
		
		Pager proPage = list.getPager();
		proPage.setSizePerPage(page.getSizePerPage());
		List<TElecEvidence> properties = list.getList();
		
		request.setAttribute("page", proPage);
		request.setAttribute("properties", properties);
		request.setAttribute("condition", condition);
		return "/jsp/elecEvidence/elecEvidenceList";
	}
	
	@InitBinder
	public void initDate(WebDataBinder bind){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(fmt, true);
		bind.registerCustomEditor(Date.class, editor);
	}
}
