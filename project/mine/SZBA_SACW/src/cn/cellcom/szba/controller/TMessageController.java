package cn.cellcom.szba.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TMessageBiz;
import cn.cellcom.szba.common.DateTool;
import cn.cellcom.szba.common.EnvMessage;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.RequestUtils;
import cn.cellcom.szba.common.utils.HttpHelper;
import cn.cellcom.szba.domain.TMessage;
import cn.cellcom.szba.service.MessageService;
import cn.cellcom.szba.vo.template.TemplateMessage;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;




@Controller
@RequestMapping("/message")
public class TMessageController {
  
	private static Log log = LogFactory.getLog(TMessageController.class);
	
	private TMessageBiz messageBiz = new TMessageBiz();
	
	@Resource
	private MessageService messageService;
	
	@RequestMapping("/test")
	public  String test(HttpServletRequest request){
		System.out.println(EnvMessage.SYS_TIT_CON.getTitle()+EnvMessage.SYS_TIT_CON.getContent());
		System.out.println(EnvMessage.TYPE.SYS_TRIGGER.value());
		System.out.println(EnvMessage.IS_RELATIVE.IS_RELATIVE.value());
		messageService.SendMessage(EnvMessage.SYS_TIT_CON.getTitle(),
				EnvMessage.SYS_TIT_CON.getContent(),
				EnvMessage.TYPE.SYS_TRIGGER.value(), "1001", EnvMessage.IS_RELATIVE.IS_RELATIVE.value());
		
		return null;
	}
	
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest request,Pager page){
        
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		ListAndPager<TMessage> list;
		try {
			String accStr = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
			Map<String, String[]> extraMap = new HashMap<String, String[]>();
			extraMap.put("account", new String[]{accStr});
			extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
			list = messageBiz.queryForPage(extraMap, page);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TMessage> messages = list.getList();
			request.setAttribute("data", messages);
			request.setAttribute("page", proPage);
			request.setAttribute("params", request.getParameterMap());
			request.setAttribute("condiStartTime", request.getParameter("condiStartTime"));
			request.setAttribute("condiEndTime", request.getParameter("condiEndTime"));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/message/messageInfo";
	}
	
	@RequestMapping("/queryForPage2")
	public String queryForPage2(HttpServletRequest request,HttpServletResponse response,Pager page){
        
		MyPagerUtil.initPager(page);
		String params = request.getParameter("params");
		log.info("params=" + params);
		Map<String,String> mapStr = HttpHelper.toHashMap(params);
		page.setCurrentIndex(Integer.parseInt(mapStr.get("currentIndex")));
		page.setSizePerPage(Integer.parseInt(mapStr.get("sizePerPage")));
		
		ListAndPager<TMessage> list;
		try {
			String accStr = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
			list = messageBiz.queryForPage2(accStr, page);
			Pager proPage = list.getPager();
			List<TMessage> messages = list.getList();
			for(int i=0;i<messages.size();i++){
				if(messages.get(i).getCreateTime()!=null){
				   messages.get(i).setCreateTimeStr2(DateTool.formateTime2String(messages.get(i).getCreateTime()));
				}
			}
			Map<String,Object> map = new HashMap<String,Object>();
	        map.put("list", messages);
	        map.put("page", proPage);
	        map.put("params", request.getParameterMap());
	        PrintTool.print(response, PrintTool.printJSON(map), "json");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/queryById")
	public String queryById(HttpServletRequest request){
        
		
		TMessage result = new TMessage();
		try {
			long id = Long.parseLong(request.getParameter("id"));
			result = messageBiz.queryById(id);
			request.setAttribute("data", result);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/message/messageDetail";
	}
	
	@RequestMapping("/deleteById")
	public String deleteById(HttpServletRequest request){
        
		
		String message = "";
		try {
			long id = Long.parseLong(request.getParameter("id"));
			message = messageBiz.deleteById(id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		Pager page = new Pager();
		toList(request,page);
		return "/jsp/message/messageInfo";
	}
	
	@RequestMapping("/batchDeleteByIds")
	public String batchDeleteByIds(HttpServletRequest request){
        
		String[] messageIds = request.getParameterValues("messageIds");
		String message = "";
		try {
			for(String str:messageIds){
			  long id = Long.parseLong(str);
			  message = messageBiz.deleteById(id); 
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		Pager page = new Pager();
		toList(request,page);
		return "/jsp/message/messageInfo";
	}
	
	public void toList(HttpServletRequest request,Pager page){
		MyPagerUtil.initPager(page);
		ListAndPager<TMessage> list;
		try {
			String accStr = ((Map)request.getSession().getAttribute("loginForm")).get("ACCOUNT").toString();
			list = messageBiz.queryForPage3(accStr, page);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<TMessage> messages = list.getList();
			request.setAttribute("data", messages);
			request.setAttribute("page", proPage);
			request.setAttribute("params", request.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

}
