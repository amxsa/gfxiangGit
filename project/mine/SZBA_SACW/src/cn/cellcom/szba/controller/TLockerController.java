package cn.cellcom.szba.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TLockerBiz;
import cn.cellcom.szba.biz.TRackBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.domain.TLocker;
import cn.cellcom.szba.domain.TRack;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/locker")
public class TLockerController {
private static Log log = LogFactory.getLog(TLockerController.class);
	
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ,Pager page){
		MyPagerUtil.initPager(page);
		Map<String , String[]> map=requ.getParameterMap();
		ListAndPager<TLocker> list;
		try {
			list=TLockerBiz.queryForPage(map, page);
			List<TRack> rackList =TRackBiz.queryName();
			requ.setAttribute("rackList", rackList);
			requ.setAttribute("data", list.getList());
			requ.setAttribute("page", list.getPager());
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/locker/lockerManager";
	}
	/**
	 * 增加货架
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest requ,TLocker entity){
		String result = TLockerBiz.insert(entity);
		requ.setAttribute("message", result);
		return toAddPage(requ);
	}
	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/toAddPage")
	public String toAddPage(HttpServletRequest requ){
		List<TRack> rackList =TRackBiz.queryName();
		requ.setAttribute("rackList", rackList);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/locker/addLocker";
	}
	/**
	 * 保存修改信息
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest requ,TLocker entity){
			int result=TLockerBiz.update(entity);
			if(result>0){
				requ.setAttribute("message", "success");
			}
			requ.setAttribute("getLockerNum", entity.getLockerNum());
			return toEditPage(requ);
	}
	/**
	 * 跳转到编辑页面
	 */
	@RequestMapping("/toEditPage")
	public String toEditPage(HttpServletRequest requ){
		String lockerNum=requ.getParameter("lockerNum");
		TLocker entity=TLockerBiz.queryById(lockerNum);
		List<TRack> rackList =TRackBiz.queryName();
		requ.setAttribute("rackList", rackList);
		requ.setAttribute("entity", entity);
		return "/jsp/locker/editLocker";
	}
	/**
	 * 跳转到详细页面
	 */
	@RequestMapping("/toDetailPage")
	public String toDetailPage(HttpServletRequest requ){
		String lockerNum=requ.getParameter("lockerNum");
		TLocker entity=TLockerBiz.queryById(lockerNum);
		List<TRack> rackList =TRackBiz.queryName();
		requ.setAttribute("rackList", rackList);
		requ.setAttribute("entity", entity);
		return "/jsp/locker/detailLocker";
	}
}
