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

import cn.cellcom.szba.biz.TStoreRoomBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.ResultCode;
import cn.cellcom.szba.common.utils.TRoleUtils;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TStoreHouse;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/storeroom")
public class TStoreroomController {
	
	private static Log log = LogFactory.getLog(TStoreroomController.class);
	
	//根据角色以及部门，获取仓库
	private List<TStoreHouse> getStoreHouseByCurrentRole(HttpServletRequest requ){
		Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
		List<TStoreHouse> storeHouses=TStoreRoomBiz.queryStoreHouse(ret[0],ret[1]);
		return storeHouses;
	}
	
	/**
	 * 跳转到库房管理页面
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ,Pager page){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map=requ.getParameterMap();
		ListAndPager<TStoreRoom> list;
		try {
			Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
			list=TStoreRoomBiz.queryForPage(map, page,String.valueOf(ret[0]),ret[1]);
			requ.setAttribute("data", list.getList());
			requ.setAttribute("page", list.getPager());
			requ.setAttribute("params", requ.getParameterMap());
			requ.setAttribute("storehouseId", requ.getParameter("storehouseId"));
			List<TStoreHouse> storeHouses=getStoreHouseByCurrentRole(requ);
			requ.setAttribute("storeHouseList", storeHouses);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/storeroom/storeroomManager";
	}
	
	/**
	 * 增加库房
	 */
	@RequestMapping("/toAdd")
	public void toAdd(HttpServletRequest requ,HttpServletResponse response,TStoreRoom entity){
		String result = TStoreRoomBiz.insert(entity);
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
		requ.setAttribute("params", requ.getParameterMap());
		List<TStoreHouse> storeHouses=getStoreHouseByCurrentRole(requ);
		requ.setAttribute("storeHouseList", storeHouses);
		return "/jsp/storeroom/addstoreroom";
	}
	/**
	 * 保存修改信息
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest requ,HttpServletResponse response,TStoreRoom entity){
		int result=TStoreRoomBiz.update(entity);
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
		String storeroomNum=requ.getParameter("storeroomNum");
		TStoreRoom entity=TStoreRoomBiz.queryById(storeroomNum);
		List<TStoreHouse> storeHouses=getStoreHouseByCurrentRole(requ);
		requ.setAttribute("storeHouseList", storeHouses);
		requ.setAttribute("entity", entity);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/storeroom/editstoreroom";
	}
	/**
	 * 跳转到详细页面
	 */
	@RequestMapping("/toDetailPage")
	public String toDetailPage(HttpServletRequest requ){
		String storeroomNum=requ.getParameter("storeroomNum");
		TStoreRoom entity=TStoreRoomBiz.queryById(storeroomNum);
		requ.setAttribute("entity", entity);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/storeroom/detailstoreroom";
	}
	
	@RequestMapping("/getStoreHouseByDept")
	public void getStoreHouseByDept(Long deptId, HttpServletRequest request, HttpServletResponse response){
		Long[] ret=TRoleUtils.getDeptIdByCurrentRole(request);
		List<TStoreHouse> storeHouse=TStoreRoomBiz.queryStoreHouse(ret[0],1l);
		try {
			PrintTool.print(response, PrintTool.printJSONArray(storeHouse), "json");
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
}
