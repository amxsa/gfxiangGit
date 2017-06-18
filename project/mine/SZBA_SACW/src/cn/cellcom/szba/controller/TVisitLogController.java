package cn.cellcom.szba.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TVisitLogBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.domain.TVisitLog;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/visitLog")
public class TVisitLogController {

	private static Log log = LogFactory.getLog(TVisitLogController.class);
	
	private TVisitLogBiz visitLogBiz = new TVisitLogBiz();

	
	/**
	 * 查询访问日志列表
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page){
		MyPagerUtil.initPager(page);
		
		Map<String, String[]> map = requ.getParameterMap();
		ListAndPager<TVisitLog> list;
		try {
			list = visitLogBiz.queryForPage(map, page);
		 
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TVisitLog> accounts = list.getList();

			requ.setAttribute("data", accounts);
			requ.setAttribute("page", proPage);
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/visitLog/visitLogManage";
	}
	
	/**
	 * 跳转访问日志详细页面
	 * @param requ
	 * @return
	 */
	@RequestMapping("todetailPage")
	public String todetailPage(HttpServletRequest requ){
		
		String id = requ.getParameter("id");
		TVisitLog visitLog = visitLogBiz.queryById(id);
		requ.setAttribute("entity", visitLog);
		
		return "/jsp/visitLog/visitLogDetail";
	}
	
}

