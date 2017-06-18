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

import cn.cellcom.szba.biz.TRackBiz;
import cn.cellcom.szba.biz.TStoreRoomBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.ResultCode;
import cn.cellcom.szba.common.utils.TRoleUtils;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TRack;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/rack")
public class TRackController {
	private static Log log = LogFactory.getLog(TRackController.class);
	
	//根据角色以及部门，获取仓库
	private List<TStoreRoom> getStoreRoomByCurrentRole(HttpServletRequest requ){
		Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
		List<TStoreRoom> storeRoomList =TStoreRoomBiz.queryName(ret[0],ret[1]);
		return storeRoomList;
	}
		
	@RequestMapping("/queryForPageToStorage")
	public String queryForPageToStorage(HttpServletRequest requ,Pager page){
		MyPagerUtil.initPager(page);
		Map<String , String[]> map=requ.getParameterMap();
		ListAndPager<TRack> list;
		try {
			Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
			list=TRackBiz.queryForPage(map, page,String.valueOf(ret[0]),ret[1]);
			
			List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
			requ.setAttribute("storeRoomList", storeRoomList);
			requ.setAttribute("data", list.getList());
			requ.setAttribute("page", list.getPager());
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/storageLocation/addstorageLocationRackList";
	}
	
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ,Pager page){
		MyPagerUtil.initPager(page);
		Map<String , String[]> map=requ.getParameterMap();
		ListAndPager<TRack> list;
		try {
			Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
			list=TRackBiz.queryForPage(map, page,String.valueOf(ret[0]),ret[1]);
			
			List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
			requ.setAttribute("storeRoomList", storeRoomList);
			requ.setAttribute("data", list.getList());
			requ.setAttribute("page", list.getPager());
			requ.setAttribute("params", requ.getParameterMap());
			requ.setAttribute("storeroomId", requ.getParameter("storeroomId"));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/rack/rackManager";
	}
	/**
	 * 增加货架
	 */
	@RequestMapping("/toAdd")
	public void toAdd(HttpServletRequest requ,HttpServletResponse response,TRack entity){
		String result = TRackBiz.insert(entity);
		DataMsg dataMsg=new DataMsg();
		dataMsg.setState(result);
		dataMsg.setMsg(result);
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error(ex);
		}
	}
	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/toAddPage")
	public String toAddPage(HttpServletRequest requ){
		List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
		requ.setAttribute("storeRoomList", storeRoomList);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/rack/addRack";
	}
	/**
	 * 保存修改信息
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest requ,HttpServletResponse response,TRack entity){
		int result=TRackBiz.update(entity);
		DataMsg dataMsg=new DataMsg();
		if(result>0){
			dataMsg.setState(ResultCode.SUCCESS.getCode());
			dataMsg.setMsg("修改成功");
		}else{
			dataMsg.setState(ResultCode.FAILED.getCode());
			dataMsg.setMsg("修改失败");
		}
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error(ex);
		}
	}
	/**
	 * 跳转到编辑页面
	 */
	@RequestMapping("/toEditPage")
	public String toEditPage(HttpServletRequest requ){
		String locationNum=requ.getParameter("rackNum");
		TRack entity=TRackBiz.queryById(locationNum);
		List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
		requ.setAttribute("storeRoomList", storeRoomList);
		requ.setAttribute("params", requ.getParameterMap());
		requ.setAttribute("entity", entity);
		return "/jsp/rack/editRack";
	}
	/**
	 * 跳转到详细页面
	 */
	@RequestMapping("/toDetailPage")
	public String toDetailPage(HttpServletRequest requ){
		String locationNum=requ.getParameter("rackNum");
		TRack entity=TRackBiz.queryById(locationNum);
		List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
		requ.setAttribute("storeRoomList", storeRoomList);
		requ.setAttribute("entity", entity);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/rack/detailRack";
	}
}
