package cn.cellcom.szba.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cellcom.szba.biz.SessionBiz;
import cn.cellcom.szba.biz.TTempletBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TTemplet;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/templet")
public class TTempletController {

	private TTempletBiz templetBiz = new TTempletBiz();
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 添加财物关联案件 pansenxin
	 * */
	@RequestMapping("/queryTemplet")
	public String queryTemplet(HttpServletRequest request, Pager page) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> paramsMap = request.getParameterMap();
		
		ListAndPager<TTemplet> dbRequest = templetBiz.queryTemplet(paramsMap, page);
		List<TTemplet> list = dbRequest.getList();
		page = dbRequest.getPager();
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("contentType", request.getParameter("contentType"));
		return "/jsp/templet/templetList";
	}
	

	@RequestMapping("/add")
	@ResponseBody
	public void add(TTemplet t, HttpServletRequest httpRequest,
			HttpServletResponse response) {
		String accStr = (SessionBiz.getLoginForm(httpRequest.getSession()))
				.get("ACCOUNT").toString();
		t.setAccount(accStr);
		t.setType("1"); // 自定义
		DataMsg dataMsg = new DataMsg();
		try {
			boolean ret = templetBiz.add(t);
			if (ret) {
				dataMsg.setState("S");
				dataMsg.setMsg("添加成功");
			} else {
				dataMsg.setState("F");
				dataMsg.setMsg("添加失败");
			}
		} catch (Exception e) {
			log.error("", e);
			dataMsg.setState("F");
			dataMsg.setMsg("添加失败");
		}
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error("", ex);
		}
	}

	@RequestMapping("/update")
	@ResponseBody
	public void update(TTemplet t, HttpServletRequest httpRequest,
			HttpServletResponse response) {
		DataMsg dataMsg = new DataMsg();
		try {
			boolean ret = templetBiz.update(t);
			if (ret) {
				dataMsg.setState("S");
				dataMsg.setMsg("修改成功");
			} else {
				dataMsg.setState("F");
				dataMsg.setMsg("修改失败");
			}
		} catch (Exception e) {
			dataMsg.setState("F");
			dataMsg.setMsg("修改失败");
			log.error("", e);
		}
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error("", ex);
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		String id=httpRequest.getParameter("id");
		DataMsg dataMsg = new DataMsg();
		try {
			boolean ret = templetBiz.delete(id);
			if (ret) {
				dataMsg.setState("S");
				dataMsg.setMsg("删除成功");
			} else {
				dataMsg.setState("F");
				dataMsg.setMsg("删除失败");
			}
		} catch (Exception e) {
			dataMsg.setState("F");
			dataMsg.setMsg("删除失败");
			log.error("", e);
			e.printStackTrace();
		}
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error("", ex);
		}
	}
	
	@RequestMapping("/queryById")
	@ResponseBody
	public void queryById(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		String id=httpRequest.getParameter("id");
		TTemplet t = templetBiz.queryDetail(id);
		DataMsg dataMsg = new DataMsg();
		dataMsg.setObj(t);
		dataMsg.setState("S");
		dataMsg.setMsg("查询成功");
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error("", ex);
		}
	}

	@RequestMapping("/queryDetail")
	public String queryDetail(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		String id=httpRequest.getParameter("id");
		String type=httpRequest.getParameter("type");
		TTemplet t = templetBiz.queryDetail(id);
		httpRequest.setAttribute("templet", t);
		String retView="";
		if("look".equals(type)){
			retView="/jsp/templet/templetLook";
		}else if("update".equals(type)){
			retView="/jsp/templet/templetUpdate";
		}
		return retView;
	}
}
