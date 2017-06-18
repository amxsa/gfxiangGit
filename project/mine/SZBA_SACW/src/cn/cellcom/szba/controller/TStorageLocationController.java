package cn.cellcom.szba.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import cn.cellcom.szba.biz.TStorageLocationBiz;
import cn.cellcom.szba.biz.TStoreRoomBiz;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.ResultCode;
import cn.cellcom.szba.common.utils.TRoleUtils;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TRack;
import cn.cellcom.szba.domain.TStorageLocation;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;

@Controller
@RequestMapping("/storageLocation")
public class TStorageLocationController {
	private static Log log=LogFactory.getLog(TStorageLocationController.class);
		
	//根据角色以及部门，获取保管室
	private List<TStoreRoom> getStoreRoomByCurrentRole(HttpServletRequest requ){
		Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
		List<TStoreRoom> storeRoomList =TStoreRoomBiz.queryName(ret[0],ret[1]);
		return storeRoomList;
	}
	
	//根据角色以及部门，获取货架
	private List<TRack> getRackByCurrentRole(HttpServletRequest requ){
		Pager page=new Pager();
		page.setCurrentIndex(1);
		page.setSizePerPage(999999999);
		Map<String , String[]> map=requ.getParameterMap();
		Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
		ListAndPager<TRack> list=TRackBiz.queryForPage(map, page,String.valueOf(ret[0]),ret[1]);
		List<TRack> rackList=list.getList();
		return rackList;
	}
	
	@RequestMapping("/getStoreAndRack")
	public void getStoreAndRack(HttpServletRequest requ, HttpServletResponse response){
		List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
		List<TRack> rackList=getRackByCurrentRole(requ);
		Map<String,List> retMap=new HashMap<String,List>();
		retMap.put("storeRoomList", storeRoomList);
		retMap.put("rackList", rackList);
		try {
			PrintTool.print(response, PrintTool.printJSON(retMap), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	@RequestMapping("/queryBySidOrRid")
	public void queryBySidOrRid(HttpServletRequest requ, HttpServletResponse response){
		String storeroomId=requ.getParameter("storeroomId");
		String rackId=requ.getParameter("rackId");
		Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
		List<TStorageLocation> list = TStorageLocationBiz.queryBySidOrRid(storeroomId, rackId,String.valueOf(ret[0]),ret[1]);
		List<TStorageLocation> retList=new ArrayList<TStorageLocation>();
		if(list!=null&&!list.isEmpty()){
			TStorageLocation retObj=null;
			for(TStorageLocation tsl:list){
				retObj=new TStorageLocation();
				retObj.setId(tsl.getId());
				retObj.setAddress(tsl.getAddress());
				retObj.setBuildLevel(tsl.getBuildLevel());
				retObj.setBuildNum(tsl.getBuildNum());
				retObj.setCapacity(tsl.getCapacity());
				retObj.setCounterNum(tsl.getCounterNum());
				retObj.setDepositNum(tsl.getDepositNum());
				retObj.setGoodsNum(tsl.getGoodsNum());
				retObj.setInventoryStatus(tsl.getInventoryStatus());
				retObj.setLocationName(tsl.getLocationName());
				retObj.setLocationNum(tsl.getLocationNum());
				retObj.setLocationType(tsl.getLocationType());
				retObj.setRackId(tsl.getRackId());
				retObj.setRackName(tsl.getRackName());
				retObj.setRfidNum(tsl.getRfidNum());
				retObj.setStoreroomID(tsl.getStoreroomID());
				
				Long storeHouseId=null;
				if(tsl.getStoreroomID()!=null){
					TStoreRoom tsr=TStoreRoomBiz.queryByRId(tsl.getStoreroomID());
					if(tsr!=null)
						storeHouseId=tsr.getStorehouseId();
					
				}else if(tsl.getRackId()!=null){
					TRack tr=TRackBiz.queryByRId(tsl.getRackId());
					if(tr!=null){
						TStoreRoom tsr=TStoreRoomBiz.queryByRId(tr.getStoreroomId());
						if(tsr!=null)
							storeHouseId=tsr.getStorehouseId();
					}
				}
				retObj.setStoreHouseId(storeHouseId);
				retList.add(retObj);
			}
		}
		try {
			PrintTool.print(response, PrintTool.printJSONArray(retList), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ,Pager page){
		MyPagerUtil.initPager(page);
		Map<String , String[]> map=requ.getParameterMap();
		ListAndPager<TStorageLocation> list;
		try {
			Long[] ret=TRoleUtils.getDeptIdByCurrentRole(requ);
			list=TStorageLocationBiz.queryForPage(map, page,String.valueOf(ret[0]),ret[1]);
			
			List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
			requ.setAttribute("storeRoomList", storeRoomList);
			if(list.getList()!=null&&!list.getList().isEmpty()){
				for(int i=0;i<list.getList().size();i++){
					TStorageLocation ts=list.getList().get(i);
					if(ts.getRackId()!=null){
						TRack entity=TStorageLocationBiz.queryRackById(ts.getRackId());
						list.getList().get(i).setRackName(entity.getRackNum());
					}
				}
			}
			List<TRack> rackList=getRackByCurrentRole(requ);
			requ.setAttribute("rackList", rackList);
			
			requ.setAttribute("data", list.getList());
			requ.setAttribute("page", list.getPager());
			requ.setAttribute("storeroomID", requ.getParameter("storeroomID"));
			requ.setAttribute("rackId", requ.getParameter("rackId"));
			requ.setAttribute("params", requ.getParameterMap());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "/jsp/storageLocation/storageLocationManager";
	}
	/**
	 * 增加库房
	 */
	@RequestMapping("/toAdd")
	public void toAdd(HttpServletRequest requ,HttpServletResponse response,TStorageLocation entity){
		String result = TStorageLocationBiz.insert(entity);
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
		return "/jsp/storageLocation/addstorageLocation";
	}
	/**
	 * 保存修改信息
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest requ,HttpServletResponse response,TStorageLocation entity){
		int result=TStorageLocationBiz.update(entity);
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
		String locationNum=requ.getParameter("locationNum");
		TStorageLocation entity=TStorageLocationBiz.queryById(locationNum);
		if(entity.getRackId()!=null){
			TRack tr=TStorageLocationBiz.queryRackById(entity.getRackId());
			entity.setRackName(tr.getRackNum());
		}
		List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
		requ.setAttribute("storeRoomList", storeRoomList);
		requ.setAttribute("entity", entity);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/storageLocation/editstorageLocation";
	}
	/**
	 * 跳转到详细页面
	 */
	@RequestMapping("/toDetailPage")
	public String toDetailPage(HttpServletRequest requ){
		String locationNum=requ.getParameter("locationNum");
		TStorageLocation entity=TStorageLocationBiz.queryById(locationNum);
		if(entity.getRackId()!=null){
			TRack tr=TStorageLocationBiz.queryRackById(entity.getRackId());
			entity.setRackName(tr.getRackNum());
		}
		
		List<TStoreRoom> storeRoomList =getStoreRoomByCurrentRole(requ);
		requ.setAttribute("storeRoomList", storeRoomList);
		
		requ.setAttribute("entity", entity);
		requ.setAttribute("params", requ.getParameterMap());
		return "/jsp/storageLocation/detailstorageLocation";
	}
}
