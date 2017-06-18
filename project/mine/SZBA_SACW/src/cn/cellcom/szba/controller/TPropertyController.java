package cn.cellcom.szba.controller;

import static cn.cellcom.szba.common.Env.DATA_MSG;
import static cn.cellcom.szba.common.Env.ERROR_VIEW;
import static cn.cellcom.szba.common.Env.OK_VIEW;
import static cn.cellcom.szba.common.Env.SYS_ERROR;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cellcom.szba.biz.SessionBiz;
import cn.cellcom.szba.biz.TAttachmentBiz;
import cn.cellcom.szba.biz.TCasePropertyBiz;
import cn.cellcom.szba.biz.TDetailListBiz;
import cn.cellcom.szba.biz.TExtractRecordBiz;
import cn.cellcom.szba.biz.TPictureBiz;
import cn.cellcom.szba.biz.TPropertyBiz;
import cn.cellcom.szba.biz.TPropertyLogBiz;
import cn.cellcom.szba.biz.TRecordBiz;
import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.utils.execl.ExcelTemplate;
import cn.cellcom.szba.common.utils.execl.ExcelTool;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TAttachment;
import cn.cellcom.szba.domain.TCase;
import cn.cellcom.szba.domain.TCaseDisposal;
import cn.cellcom.szba.domain.TCivilian;
import cn.cellcom.szba.domain.TDetail;
import cn.cellcom.szba.domain.TExtractRecord;
import cn.cellcom.szba.domain.THandleResult;
import cn.cellcom.szba.domain.TPicture;
import cn.cellcom.szba.domain.TPolice;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TPropertyAttribute;
import cn.cellcom.szba.domain.TPropertyDetail;
import cn.cellcom.szba.domain.TRecord;
import cn.cellcom.szba.domain.TStoreHouse;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.cellcom.szba.service.FlowService;
import cn.cellcom.szba.vo.PropertyVO;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.util.ArrayUtil;
import cn.open.web.ServletUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/property")
public class TPropertyController {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Resource
	private FlowService flowService;
	
	
	/**
	 * 财物查询+财物登记+财物处置
	 * @param requ
	 * @param page
	 * @param condition
	 * @return
	 */
	@RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest requ, Pager page, ConditionParam condition){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = requ.getParameterMap();
		
		String viewName = requ.getParameter("viewName");
		ArrayUtil.print(Env.viewNameList, true);
		//viewName没有定义的情况下返回错误
		if(!Env.viewNameList.contains(viewName)) {
			requ.setAttribute(Env.DATA_MSG, Env.REQUIRE_PARAM);
			return ERROR_VIEW;
		}
		
		Map<String, Object> loginForm =SessionBiz.getLoginForm(requ.getSession());
		
		ListAndPager<PropertyVO> list;
		List<PropertyVO> properties = new ArrayList<PropertyVO>();
		String link = "";
		try {
			list = TPropertyBiz.queryForPage(map, page, loginForm, viewName);
		 
			Pager proPage = list.getPager();
			if(proPage != null){
				proPage.setSizePerPage(page.getSizePerPage());
				properties = list.getList();
				
				ArrayUtil.print(properties, true);
				//link = MyPagerUtil.getLink(proPage, "searchForm");
			}
			
			//requ.setAttribute("link", link);
			requ.setAttribute("page", proPage);
			requ.setAttribute("properties", properties);
			requ.setAttribute("condition", condition);
			
		} catch (Exception e) {
			log.error("",e);
		}
		
		return "/jsp/propertyInfo/"+viewName;
	}
	
	/**
	 * 库存管理-移库
	 * 返回仓库信息，用于成功显示
	 * */
	@RequestMapping("/inventoryMoveLocation")
	public void inventoryMoveLocation(HttpServletRequest requ,HttpServletResponse response){
		TStoreHouse entity=TPropertyBiz.inventoryMoveLocation(requ);
		try {
			PrintTool.print(response, PrintTool.printJSON(entity), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	/**
	 * 获取财物详情用于修改或者查看
	 * */
	@RequestMapping("/queryDetailById")
	public void queryDetailById(HttpServletRequest requ,HttpServletResponse response){
		String proId=requ.getParameter("proId");
		TPropertyDetail detail=TPropertyBiz.findDetailById(proId);
		try {
			PrintTool.print(response, PrintTool.printJSON(detail), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	
	/**
	 * 所有的查看财物详情
	 * @param proId
	 * @param condition
	 * @param page
	 * @param requ
	 * @return
	 */
	@RequestMapping("/queryDetail")
	public String queryDetail(String proId, ConditionParam condition, Pager page,HttpServletRequest requ){
		HttpSession session = requ.getSession();
		
		String viewName = requ.getParameter("viewName");
		
		
		TDetail detail = TPropertyBiz.queryDetail(proId);
		TProperty pro = new TProperty();
		if(detail.getProperties().size()>0){
			pro = detail.getProperties().get(0);
		}
		
		List<TPicture> picList = TPictureBiz.queryPhotographs(String.valueOf(pro.getProId()), "", "");
		List<TAttachment> attList = TAttachmentBiz.queryAll("21", pro.getProId());
		
		List<String> picJsonList = new ArrayList<String>();
		List<String> attJsonList = new ArrayList<String>();
		
		TCase tCase = detail.gettCase();
		
		TCivilian owner = new TCivilian();
		if(detail.getOwner().size() > 0){
			owner = detail.getOwner().get(0);
		}
		
		List<TPolice> polices = new ArrayList<TPolice>();
		polices = detail.getPolices();
		
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyyMMddHHmmss")	
				.create();
		for(TPicture pic: picList){
			String picJson = gson.toJson(pic);
			picJsonList.add(picJson);
		}
		
		for(TAttachment att : attList){
			String attJson = gson.toJson(att);
			attJsonList.add(attJson);
		}
		
		requ.setAttribute("detail", detail);
		requ.setAttribute("pro", pro);
		requ.setAttribute("tCase", tCase);
		requ.setAttribute("owner", owner);
		requ.setAttribute("polices", polices);
		requ.setAttribute("picJsonList", picJsonList);
		requ.setAttribute("attJsonList", attJsonList);
		
		if(condition == null){
			condition = new ConditionParam();
		}
		requ.setAttribute("condition", condition);
		requ.setAttribute("page", page);
		
		return "/jsp/propertyInfo/"+viewName;
	}
	
	/**
	 * 添加财物关联案件 pansenxin
	 * */
	@RequestMapping("/queryCase")
	public String queryCase(HttpServletRequest request, Pager page) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> paramsMap = request.getParameterMap();
		Map<String, Object> loginForm = (Map<String, Object>) request
				.getSession().getAttribute("loginForm");
		ListAndPager<TCase> dbRequest = TCasePropertyBiz.queryCaseToPro(paramsMap, page,
				loginForm);
		List<TCase> list = dbRequest.getList();
         		if (ArrayUtil.isNotEmpty(list)) {
			TCase po = null;
			for (int i = 0, len = list.size(); i < len; i++) {
				po = list.get(i);
			}
		}
		page = dbRequest.getPager();
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("condiParams", request.getParameterMap());
		return "/jsp/propertyInfo/propertyPreAddCaseList";
	}
	
	/***
	 * 财物修改 pansenxin
	 * */
	@RequestMapping("/updateData")
	@ResponseBody
	public void updateData(HttpServletRequest requ,TProperty pro,String[] pictureJson,
			String[] attachmentJson,HttpServletResponse response){
		//创建人
		HttpSession session = requ.getSession();
		String accStr = ((Map)session.getAttribute("loginForm")).get("ACCOUNT").toString();
		TAccount creator = new TAccount();
		creator.setAccount(accStr);
		Gson gson = new GsonBuilder().create();
		List<TPicture> picList = new ArrayList<TPicture>();
		if(pictureJson != null){
			for(String pic : pictureJson){
				pic.replaceAll("\'", "\"");
				picList.add(gson.fromJson(pic, TPicture.class));
			}
		}
		for(TPicture pic : picList){
			String imgUrl = pic.getOriginalUrl();
			pic.setOriginalUrl(imgUrl.replace(Env.ENV_PRO.getProperty("photo_base_url"), ""));
			String thumUrl = "";
			if(imgUrl.lastIndexOf(".") < 0){
				thumUrl = imgUrl+"_thum."+FilenameUtils.getExtension(imgUrl);
			}else{
				thumUrl = imgUrl.substring(0, imgUrl.lastIndexOf(".")) +"_thum."+ FilenameUtils.getExtension(imgUrl);
			}
			pic.setThumbnailUrl(thumUrl);
			pic.setCreator(creator);
			pic.setType("21");
		}
		List<TAttachment> attList = new ArrayList<TAttachment>();
		if(attachmentJson != null){
			for(String att : attachmentJson){
				att.replaceAll("\'", "\"");
				attList.add(gson.fromJson(att, TAttachment.class));
			}
		}
		for(TAttachment att : attList){
			String attUrl = att.getUploadUrl();
			att.setUploadUrl(attUrl.replace(Env.ENV_PRO.getProperty("file_base_url"), ""));
			att.setCreator(creator);
			att.setType("21");
		}
		String departmentId = ((Map)session.getAttribute("loginForm")).get("DEPARTMENT_ID").toString();
		boolean ret=TPropertyBiz.update(pro,picList,pro.getOwnerInfo(),pro.getEveryOneInfo(),
				pro.getCateAttrInfo(),pro.getBgPerson(),pro.getJzPerson(),pro.getDsPerson(),attList,departmentId);
		DataMsg dataMsg=new DataMsg();
		if(ret){
			dataMsg.setState("S");
			dataMsg.setMsg("修改成功");
		}else{
			dataMsg.setState("F");
			dataMsg.setMsg("修改失败");
		}
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error("", ex);
		}
	}
	
	/***
	 * 财物添加 pansenxin
	 * */
	@RequestMapping("/addData")
	@ResponseBody
	public void add(HttpServletRequest requ,TProperty pro,String[] pictureJson,
			String[] attachmentJson,HttpServletResponse response){
		//创建人
		HttpSession session = requ.getSession();
		String accStr = ((Map)session.getAttribute("loginForm")).get("ACCOUNT").toString();
		TAccount creator = new TAccount();
		creator.setAccount(accStr);
		pro.setAccount(accStr);
		pro.setTransactor(accStr);
		Gson gson = new GsonBuilder().create();
		List<TPicture> picList = new ArrayList<TPicture>();
		if(pictureJson != null){
			for(String pic : pictureJson){
				pic.replaceAll("\'", "\"");
				picList.add(gson.fromJson(pic, TPicture.class));
			}
		}
		for(TPicture pic : picList){
			String imgUrl = pic.getOriginalUrl();
			pic.setOriginalUrl(imgUrl.replace(Env.ENV_PRO.getProperty("photo_base_url"), ""));
			String thumUrl = "";
			if(imgUrl.lastIndexOf(".") < 0){
				thumUrl = imgUrl+"_thum."+FilenameUtils.getExtension(imgUrl);
			}else{
				thumUrl = imgUrl.substring(0, imgUrl.lastIndexOf(".")) +"_thum."+ FilenameUtils.getExtension(imgUrl);
			}
			pic.setThumbnailUrl(thumUrl);
			pic.setCreator(creator);
			pic.setType("21");
		}
		List<TAttachment> attList = new ArrayList<TAttachment>();
		if(attachmentJson != null){
			for(String att : attachmentJson){
				att.replaceAll("\'", "\"");
				attList.add(gson.fromJson(att, TAttachment.class));
			}
		}
		for(TAttachment att : attList){
			String attUrl = att.getUploadUrl();
			att.setUploadUrl(attUrl.replace(Env.ENV_PRO.getProperty("file_base_url"), ""));
			att.setCreator(creator);
			att.setType("21");
		}
		String departmentId = ((Map)session.getAttribute("loginForm")).get("DEPARTMENT_ID").toString();
		String ret=TPropertyBiz.add(pro,picList,pro.getOwnerInfo(),pro.getEveryOneInfo(),attList,
				pro.getCateAttrInfo(),pro.getBgPerson(),pro.getJzPerson(),pro.getDsPerson(),pro.getIdentifyInfo(),departmentId);
		DataMsg dataMsg=new DataMsg();
		if(StringUtils.isNotBlank(ret)){
			dataMsg.setState("S");
			dataMsg.setMsg("添加成功");
			
			//记录财物轨迹
			TPropertyLogBiz.save(requ,ret,"添加");
		}else{
			dataMsg.setState("F");
			dataMsg.setMsg("添加失败");
		}
		JSONObject json = JSONObject.fromObject(dataMsg);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException ex) {
			log.error("",ex);
		}
	}
	
	//根据财物id查询财物属性
	@RequestMapping("/findPropertyAttributeByPropertyId")
	public void findPropertyAttributeByPropertyId(HttpServletRequest requ, HttpServletResponse response){
		Map<String, String[]> paramsMap = requ.getParameterMap();
		List<TPropertyAttribute> list = TPropertyBiz.findPropertyAttributeByPropertyId(paramsMap);
		try {
			PrintTool.print(response, PrintTool.printJSONArray(list), "json");
		} catch (IOException e) {
			log.error("", e);
		}
	}
	

	/**
	 * 财物修改
	 * @param requ
	 * @param pro
	 * @param owner
	 * @param detail
	 * @param page
	 * @param picture
	 * @param attachment
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(HttpServletRequest requ, TProperty pro, 
			TCivilian owner, TPolice police, TDetail detail, Pager page, 
			String[] pictureJson, String[] attachmentJson,String cateAttrInfo){
		pro.setTableType(requ.getParameter("proTableType"));
		
		Gson gson = new GsonBuilder().create();
		
		List<TPicture> picList = new ArrayList<TPicture>();
		List<TAttachment> attList = new ArrayList<TAttachment>();
		if(pictureJson != null){
			for(String pic : pictureJson){
				pic.replaceAll("\'", "\"");
				picList.add(gson.fromJson(pic, TPicture.class));
			}
		}
		if(attachmentJson != null){
			for(String att : attachmentJson){
				att.replaceAll("\'", "\"");
				attList.add(gson.fromJson(att, TAttachment.class));
			}
			
		}
		/*String[] imgUrlArray = new String[5];
		if(imageUploadName != null){
			imgUrlArray = imageUploadName.split(",");
		}*/
		
		MyPagerUtil.initPager(page);
		
		HttpSession session = requ.getSession();
		String accStr = ((Map)session.getAttribute("loginForm")).get("ACCOUNT").toString();
		TAccount creator = new TAccount();
		creator.setAccount(accStr);
		
		String viewName = requ.getParameter("viewName");
		
		String message = "";
		try {
			owner.setCreator(creator);
			police.setCreator(creator);
			
			
			for(TPicture pic : picList){
				
				String imgUrl = pic.getOriginalUrl();
				
				pic.setOriginalUrl(imgUrl.replace(Env.ENV_PRO.getProperty("photo_base_url"), ""));
				String thumUrl = "";
				if(imgUrl.lastIndexOf(".") < 0){
					thumUrl = imgUrl+"_thum."+FilenameUtils.getExtension(imgUrl);
				}else{
					thumUrl = imgUrl.substring(0, imgUrl.lastIndexOf(".")) +"_thum."+ FilenameUtils.getExtension(imgUrl);
				}
				pic.setThumbnailUrl(thumUrl);
				pic.setCreator(creator);
				pic.setPropertyID(pro.getProId());
				pic.setType("21");
				
			}
			
			for(TAttachment att : attList){
				
				String attUrl = att.getUploadUrl();
				
				att.setUploadUrl(attUrl.replace(Env.ENV_PRO.getProperty("file_base_url"), ""));
				att.setCreator(creator);
				att.setType("21");
				att.setPropertyID(pro.getProId());
			}
			
			TPropertyBiz.update(pro, owner, police, detail, picList, attList,cateAttrInfo);
			
			message = "Y";
			
			
		}catch(Exception e) {
			log.error("", e);
			message = "F";
		}
		return message;
	}

	/**
	 * 财物删除
	 * @param requ
	 * @param page
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delProperty(HttpServletRequest requ, Pager page, ConditionParam condition){
		String properId = requ.getParameter("proId");
		String message = "";
		
		try {
			TPropertyBiz.delProperty(properId);
			message = "Y";
		} catch (Exception e) {
			log.error("", e);
			message = "F";
			
		}
		requ.setAttribute("page", page);
		requ.setAttribute("condtion", condition);
		return message;
	}
	
	@InitBinder
	public void initDate(WebDataBinder bind){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(fmt, true);
		bind.registerCustomEditor(Date.class, editor);
	}
	
	/**
	 * 案结处置
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping("/caseDisposal")
	public String caseDisposal(HttpServletRequest httpRequest){
		HttpSession session = httpRequest.getSession();
		
		Map<String, Object> loginForm =SessionBiz.getLoginForm( session);
		List<Long> roleIDs = new ArrayList<Long>();
		String account = (String) loginForm.get("ACCOUNT");
		
		DataMsg dataMsg = null;
		
		try {
			
			String json = ServletUtil.getRequestBody(httpRequest);
			log.info("提交处置申请时的json====" + json);
			
			TCaseDisposal disposal = new TCaseDisposal();
			disposal = (TCaseDisposal) JsonUtil.jsonToBean(json,
					TCaseDisposal.class);
			disposal.setDisposalPerson(account);
		
			long disposalId = TPropertyBiz.caseDisposal(disposal);
			
			disposal.setDisposalId(disposalId);
			
			for(TAttachment att : disposal.getAttachment()){
				TAccount creator = new TAccount();
				creator.setAccount(account);
				att.setCreator(creator);
				att.setType("25");
			}
		
			TAttachmentBiz.addAttachInfo(disposal.getAttachment(), "25", String.valueOf(disposalId));
		} catch (Exception e) {
			log.error("", e);
			
			httpRequest.setAttribute(DATA_MSG, SYS_ERROR);
			return ERROR_VIEW;
		}
		return dataMsg == null || "Y".equals(dataMsg.getState()) ? OK_VIEW
				: ERROR_VIEW;
	}
	
	@RequestMapping("/queryRecordById")
	public String queryRecordById(String recordId, String tableName, HttpServletRequest requ){
		switch(tableName){
		case "REC":
			TRecord record = TRecordBiz.queryRecordById(recordId);
			requ.setAttribute("record", record);
			return "/jsp/record/watchRecord";
		case "EXTRAC":
			TExtractRecord extRecord = TExtractRecordBiz.queryRecordByProId(recordId);
			requ.setAttribute("record", extRecord);
			return "/jsp/record/watchExtractRecord";
		}
		return null;
	}
	
	@RequestMapping("/queryDetailByProId")
	public String queryDetailByProId(String proId, HttpServletRequest requ){
		TDetail detail = null;
		try {
			detail = TDetailListBiz.queryByProId(proId);
			requ.setAttribute("detail", detail);
		} catch (Exception e) {
			log.error("", e);
		}
		
		return "/jsp/detailList/watchDetail";
		
	}
	
	/**
	 * 跳转到财物处置登记页面
	 * @param proId
	 * @param request
	 * @return
	 */
	@RequestMapping("/toPreHandleResult")
	public String toPreHandleResult(String proId, String handleResultId, HttpServletRequest request){
		List<TProperty> propertyList = TPropertyBiz.queryByIds(new String[]{proId});
		request.setAttribute("propertyList", propertyList);
		
		THandleResult result = TPropertyBiz.getHandleResultById(handleResultId);
		request.setAttribute("result", result);
		
		List<TAttachment> attachmentList = TAttachmentBiz.queryAll(Env.TYPE_HANDLE_RESULT, handleResultId);
		request.setAttribute("attachmentList", attachmentList);
		
		return "/jsp/propertyInfo/handleResultPreAdd";
	}
	
	/**
	 * 财物处置登记
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addHandleResult")
	public void addHandleResult(THandleResult handleResult, String[] attachmentJson, HttpServletRequest request, HttpServletResponse response){
		Type type = new TypeToken<List<TAttachment>>(){}.getType();
		String json= Arrays.toString(attachmentJson);
		log.info("====>attachmentJson = " + json);
		List<TAttachment> attachmentList = JsonUtil.jsonToList(json, type);
		
		try {
			String account = SessionBiz.getAccount(request.getSession());
			handleResult.setAccount(account);
			TAccount creator  = new TAccount();
			creator.setAccount(account);
			for(TAttachment att : attachmentList){
				att.setCreator(creator);
			}
			TPropertyBiz.addHandleResult(handleResult, attachmentList);
			PrintTool.print(response, "Y", "");
		} catch (IOException e) {
			log.error("", e);
			try {
				PrintTool.print(response, "N", "");
			} catch (IOException e1) {
				log.error("", e1);
			}
		}
	}
	
	//导出财物统计
	@RequestMapping("/exportAllProperty")
	public String exportAllProperty(HttpServletRequest request,Pager page,HttpServletResponse response) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String viewName = request.getParameter("viewName");
		if(!Env.viewNameList.contains(viewName)) {
		     request.setAttribute(Env.DATA_MSG, Env.REQUIRE_PARAM);
			 return ERROR_VIEW;
		}
		Map<String, Object> loginForm =SessionBiz.getLoginForm(request.getSession());
			
		ListAndPager<PropertyVO> list;
		List<PropertyVO> properties = new ArrayList<PropertyVO>();
		try {
			list = TPropertyBiz.queryForPage(map, page, loginForm, viewName);
		    properties = list.getList();
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			response.setHeader("Content-Disposition", "attachment;"
					+ " filename="
					+ new String("财物统计.xls".getBytes(), "ISO-8859-1"));
			ExcelTool.writeExcelByList("财物统计", properties, new String[] {
					"caseName", "jzcaseId", "suspectName","name","categoryName","origin","quantity","unit","characteristic","transactor","createTime","authority","status","intoHouseTime","intoHousePerson","storehouseName","locationName","outHouseTime","outHousePerson" },
					new String[] { "案件名称", "案件编号", "涉案人","财物名称","财物类别","财物来源","财物数量","计量单位","财物特征","登记人","登记时间","登记部门","财物状态","入库时间","入库经办人","所在仓库","库位","出库时间","出库经办人" },
					response.getOutputStream(),
			ExcelTemplate.ExcelVersion.XLS);
			return null;
				
		} catch(Exception e){
			e.printStackTrace();
			log.error("",e);
		}
			return null;
		}
	
	//导出财物入库统计
	@RequestMapping("/exportInProperty")
	public String exportInProperty(HttpServletRequest request,Pager page,HttpServletResponse response) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String viewName = request.getParameter("viewName");
		if(!Env.viewNameList.contains(viewName)) {
			request.setAttribute(Env.DATA_MSG, Env.REQUIRE_PARAM);
			return ERROR_VIEW;
		}
		Map<String, Object> loginForm =SessionBiz.getLoginForm(request.getSession());
				
		ListAndPager<PropertyVO> list;
		List<PropertyVO> properties = new ArrayList<PropertyVO>();
		try {
			list = TPropertyBiz.queryForPage(map, page, loginForm, viewName);
			properties = list.getList();
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			response.setHeader("Content-Disposition", "attachment;"
						+ " filename="
						+ new String("财物入库统计.xls".getBytes(), "ISO-8859-1"));
			ExcelTool.writeExcelByList("财物入库统计", properties, new String[] {
						"caseName", "jzcaseId", "suspectName","name","quantity","unit","transactor","status","intoHouseTime","handler","storehouseName","locationName" },
						new String[] { "案件名称", "案件编号", "涉案人","财物名称","财物数量","计量单位","登记人","财物状态","入库时间","经办人","所在仓库","库位" },
						response.getOutputStream(),
			ExcelTemplate.ExcelVersion.XLS);
			return null;
					
		}catch(Exception e){
			e.printStackTrace();
			log.error("",e);
		}
			return null;
		}
	
	//导出财物出库统计
	@RequestMapping("/exportOutProperty")
	public String exportOutProperty(HttpServletRequest request,Pager page,HttpServletResponse response) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String viewName = request.getParameter("viewName");
		if(!Env.viewNameList.contains(viewName)) {
			request.setAttribute(Env.DATA_MSG, Env.REQUIRE_PARAM);
			return ERROR_VIEW;
		}
		Map<String, Object> loginForm =SessionBiz.getLoginForm(request.getSession());
				
		ListAndPager<PropertyVO> list;
		List<PropertyVO> properties = new ArrayList<PropertyVO>();
		try {
			list = TPropertyBiz.queryForPage(map, page, loginForm, viewName);
			properties = list.getList();
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			response.setHeader("Content-Disposition", "attachment;"
						+ " filename="
						+ new String("财物出库统计.xls".getBytes(), "ISO-8859-1"));
			ExcelTool.writeExcelByList("财物出库统计", properties, new String[] {
					"caseName", "jzcaseId", "suspectName","name","quantity","unit","transactor","status","outHouseTime","handler","storehouseName","locationName" },
					new String[] { "案件名称", "案件编号", "涉案人","财物名称","财物数量","计量单位","登记人","财物状态","出库时间","经办人","所在仓库","库位" },
					response.getOutputStream(),
		    ExcelTemplate.ExcelVersion.XLS);
			return null;
					
		}catch(Exception e){
			e.printStackTrace();
			log.error("",e);
		}
			return null;
		}
}
