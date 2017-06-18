package cn.cellcom.szba.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TCasePropertyBiz;
import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.DataMsg;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.domain.TCase;
import cn.cellcom.szba.domain.TProperty;

import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.util.ArrayUtil;

@Controller
@RequestMapping("/caseProperty")
public class TCasePropertyController {
	private Log log = LogFactory.getLog(this.getClass());
	TCasePropertyBiz biz = new TCasePropertyBiz();

	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest request, Pager page) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> paramsMap = request.getParameterMap();
		Map<String, Object> loginForm = (Map<String, Object>) request
				.getSession().getAttribute("loginForm");
		ListAndPager<TCase> dbRequest = biz.queryForPage(paramsMap, page,
				loginForm);
		List<TCase> list = dbRequest.getList();
         		if (ArrayUtil.isNotEmpty(list)) {
			TCase po = null;
			for (int i = 0, len = list.size(); i < len; i++) {
				po = list.get(i);
				System.out.println(po.getJzcaseID()+"--"+po.getCaseID());
			}
		}
		page = dbRequest.getPager();
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("condiParams", request.getParameterMap());
		return "/jsp/caseProperty/caseList";
	}

	/**
	 * 取消案件财务绑定
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/cancelBind")
	public String cancelBind(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Map<String, Object> loginForm = (Map<String, Object>) request
				.getSession().getAttribute("loginForm");
		String account = (String) loginForm.get("ACCOUNT");
		String proId = request.getParameter("proId");
		Map<String, String> params = new HashMap<String, String>();
		params.put("proId", proId);
		params.put("operateType", "cancelBind");
		DataMsg data=new DataMsg(false,"取消案件关联失败");
		try {
			data = TCasePropertyBiz.updateCaseProperty(params);
		} catch (SQLException e) {
			log.error("",e);
		}
		log.info(account + "取消案件关联结果:" + PrintTool.printJSON(data).toString());
		PrintTool.print(response, PrintTool.printJSON(data), "JSON");
		return null;
	}
	/**
	 * 案件财务绑定
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	@RequestMapping("/bind")
	public String bind(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Map<String, Object> loginForm = (Map<String, Object>) request
				.getSession().getAttribute("loginForm");
		String account = (String) loginForm.get("ACCOUNT");
		// tempProIds,tempJzcaseID关联的参数
		String tempProIds = request.getParameter("tempProIds");
		String tempJzcaseID = request.getParameter("tempJzcaseID");
		//proId 取消关联的参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("tempProIds", tempProIds);
		params.put("tempJzcaseID", tempJzcaseID);
		params.put("operateType", "bind");
		DataMsg data=new DataMsg(false,"案件财务绑定失败");
		try {
			data = TCasePropertyBiz.updateCaseProperty(params);
		} catch (SQLException e) {
			log.error("",e);
		}
		log.info(account + "案件关联结果:" + PrintTool.printJSON(data).toString());
		PrintTool.print(response, PrintTool.printJSON(data), "JSON");
		return null;
	}
}
