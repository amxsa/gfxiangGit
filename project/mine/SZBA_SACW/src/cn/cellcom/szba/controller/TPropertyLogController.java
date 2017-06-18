package cn.cellcom.szba.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TPropertyLogBiz;
import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.RequestUtils;
import cn.cellcom.szba.domain.TPropertyLog;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/propertyLog")
public class TPropertyLogController {

	private static Log log = LogFactory.getLog(TPropertyLogController.class);
	
	private TPropertyLogBiz propertyLog = new TPropertyLogBiz();

	
	/**
	 * 查询访问日志列表
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page, ConditionParam condition){
		MyPagerUtil.initPager(page);
		
		
		//财物列表跳进来，从头分页
		if("first".equals(requ.getParameter("type"))){
			page.setCurrentIndex(0);
			page.setSizePerPage(5);
		}
		Map<String, String[]> map = requ.getParameterMap();
		ListAndPager<TPropertyLog> list;
		try {
			list = propertyLog.queryForPage(map, page);
		 
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TPropertyLog> proLogs = list.getList();
			
			if(StringUtils.isNotBlank(requ.getParameter("refererUrl"))){
				requ.setAttribute("refererUrl", requ.getParameter("refererUrl"));
			}else{
				String refererUrl = RequestUtils.getRefererUrl(requ);
				requ.setAttribute("refererUrl", refererUrl);
			}
			
			requ.setAttribute("data", proLogs);
			requ.setAttribute("pages", proPage);
			requ.setAttribute("params", requ.getParameterMap());
			requ.setAttribute("conditions", JsonUtil.beanToJson(condition));
			requ.setAttribute("condition", condition);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/propertyInfo/propertyLogList";
	}
	
	@InitBinder
	public void initDate(WebDataBinder bind){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(fmt, true);
		bind.registerCustomEditor(Date.class, editor);
	}
	
}

