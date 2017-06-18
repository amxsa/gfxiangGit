package cn.cellcom.szba.controller;

import static cn.cellcom.szba.common.Env.ERROR_VIEW;
import static cn.cellcom.szba.common.Env.OK_VIEW;
import static cn.cellcom.szba.common.Env.SYS_ERROR;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

import net.sf.json.JSONArray;

import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.SessionBiz;
import cn.cellcom.szba.biz.TApplicationBiz;
import cn.cellcom.szba.biz.TAttachmentBiz;
import cn.cellcom.szba.biz.TLockerBiz;
import cn.cellcom.szba.biz.TPropertyBiz;
import cn.cellcom.szba.biz.TRackBiz;
import cn.cellcom.szba.biz.TStorageLocationBiz;
import cn.cellcom.szba.biz.TStoreRoomBiz;
import cn.cellcom.szba.common.ConditionParam;
import cn.cellcom.szba.common.DateEditor;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.MyPagerUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.PropertyUtils;
import cn.cellcom.szba.common.RequestUtils;
import cn.cellcom.szba.common.utils.IdHolderUtils;
import cn.cellcom.szba.common.utils.TRoleUtils;
import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TApplication;
import cn.cellcom.szba.domain.TAttachment;
import cn.cellcom.szba.domain.TDepartment;
import cn.cellcom.szba.domain.TDetail;
import cn.cellcom.szba.domain.TLocker;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TRack;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.domain.TStorageLocation;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.cellcom.szba.enums.ProcedureKey;
import cn.cellcom.szba.enums.RoleKey;
import cn.cellcom.szba.service.FlowService;
import cn.cellcom.szba.service.JudgeProcessService;
import cn.cellcom.szba.service.impl.JudgeProcessServiceImpl;
import cn.cellcom.szba.vo.ApplicationVO;
import cn.cellcom.szba.vo.ProcessTrack;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.util.ArrayUtil;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/applicationOrder")
public class TApplicationController {
     
    private static Log log = LogFactory.getLog(TApplicationController.class);
	
	@Resource
	private FlowService flowService;
	@Resource
	private JudgeProcessService judgeProcessService;
	
	@Resource
	private TApplicationBiz applicationBiz;
	/**
	 * 查找申请单列表--(我的已办，我的未办)
	 */
    @RequestMapping("/queryForPage")
	public String queryForPage(HttpServletRequest request,Pager page, ConditionParam condition){
        
		MyPagerUtil.initPager(page);
		
		Map<String, String[]> map = request.getParameterMap();
		ListAndPager<ApplicationVO> list;
		String applicationStyle = request.getParameter("applicationStyle");
		try {
			
			String accStr = (SessionBiz.getLoginForm(request.getSession())).get("ACCOUNT").toString();
			Map<String, String[]> extraMap = new HashMap<String, String[]>();
			extraMap.put("account", new String[]{accStr});
			extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
			list = TApplicationBiz.queryForPage(extraMap, page,applicationStyle);
			Pager proPage = list.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<ApplicationVO> messages = list.getList();
			
			request.setAttribute("data", messages);
			request.setAttribute("page", proPage);
			//request.setAttribute("params", request.getParameterMap());
			request.setAttribute("condition", condition);
			request.setAttribute("conditions", JsonUtil.beanToJson(condition));
			request.setAttribute("applicationStyle", applicationStyle);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
	    }if("done".equals(applicationStyle)){
			return "/jsp/applicationOrder/applicationDoneOrderList";
		}else{
			return "/jsp/applicationOrder/applicationDoOrderList";
		}
		
	}
	
  
    
	/**
	 * 进入处理(入库，出库，审批等)的页面
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping("/queryForPreHandle")
	public String queryForPreHandle(HttpServletRequest httpRequest, ConditionParam conditions){
		String viewName = httpRequest.getParameter("viewName");
		String proStatus= httpRequest.getParameter("proStatus");
		
		HttpSession session = httpRequest.getSession();
		List<Map<String, Object>> rolesInfo = (List<Map<String, Object>>) session.getAttribute("rolesInfo");
		List<TRole> roles = new ArrayList<TRole>();
		
		for(Map<String, Object> info : rolesInfo){
			TRole r = new TRole();
			r.setRoleKey((String)info.get("ROLE_KEY"));
			roles.add(r);
		}
		//查询财物信息
		//TDetail detail  = TPropertyBiz.queryForApply(proId);
		//httpRequest.setAttribute("detail", detail);
		
		/*if("applyHandle".equals(viewName)){//财物处置页面
			List<DisposalAndProcedure> dpList = new ArrayList<DisposalAndProcedure>();
			if(detail.getProperties().size() > 0){
				
				dpList = flowService.getDisposalAndProcedure(roles, detail.getProperties().get(0).getProType(), detail.getProperties().get(0).getProStatus());
			}
			httpRequest.setAttribute("dpList", dpList);
		}*/
/*		
		if("intoApplicationPreHandle".equals(viewName)
				||"outTemporaryHandle".equals(viewName)
				||"preIn".equals(viewName)){//财物出入库页面
*/			String taskIdStr = httpRequest.getParameter("taskId"); //任务ID
			String procInstId = httpRequest.getParameter("procInstId"); 
			Long taskId = Long.parseLong(taskIdStr);
			httpRequest.setAttribute("taskId", taskId);
			httpRequest.setAttribute("procInstId", procInstId);
			//httpRequest.setAttribute("proId", proId);
			httpRequest.setAttribute("proStatus", proStatus);
			// 这里查询审批信息。
			
			String applicationIdStr = httpRequest.getParameter("applicationId"); //申请单ID
			Long applicationId = Long.parseLong(applicationIdStr);
			TApplication application = TApplicationBiz.queryById(applicationId);
			httpRequest.setAttribute("application", application);
	
			//查询财物信息
			List<TProperty> propertyList = TPropertyBiz.queryByApplicationId(application.getId());
			httpRequest.setAttribute("propertyList", propertyList);
			
			
			//查询批注信息
			List<Comment> commentList = TApplicationBiz.findCommentByApplicationId(applicationId);
			httpRequest.setAttribute("commentList", commentList);
			
			//查询附件信息
			List<TAttachment> attachmentList = TAttachmentBiz.queryAll(Env.TYPE_APPLICATION, applicationId.toString());
			httpRequest.setAttribute("attachmentList", attachmentList);
			
			httpRequest.setAttribute("conditions", JsonUtil.beanToJson(conditions));
			if("preIn".equals(viewName)){
				Long[] ret=TRoleUtils.getDeptIdByCurrentRole(httpRequest);
				List<TStoreRoom> lRooms =TStoreRoomBiz.queryName(ret[0],ret[1]);
				httpRequest.setAttribute("lRooms", lRooms);	
	    		
	    		List<TRack> tRacks=TRackBiz.queryName();
	    		httpRequest.setAttribute("tRacks", tRacks);	
			}
		
		
		String refererUrl = RequestUtils.getRefererUrl(httpRequest);
		httpRequest.setAttribute("refererUrl", refererUrl);
		return "/jsp/applicationOrder/"+viewName;
	}
    
	/**
	 * 进入申请(入库，出库，审批等)的页面 ，支持批量财物操作
	 * @param httpRequest
	 * @param proId
	 * @return
	 */
	@RequestMapping("/queryForBatchApply")
	public String queryForBatchApply(HttpServletRequest httpRequest, String[] proId, ConditionParam conditions){
		String viewName = httpRequest.getParameter("viewName");
		String refererUrl = RequestUtils.getRefererUrl(httpRequest);
		httpRequest.setAttribute("refererUrl", refererUrl);
		
		httpRequest.setAttribute("conditions", JsonUtil.beanToJson(conditions));
		
		//查询财物信息
		TDetail detail  = TPropertyBiz.queryForBatchApply(proId);
		httpRequest.setAttribute("detail", detail);
		/*if(detail.getProperties().size() < 0){
			
			String referer = httpRequest.getHeader("referer");
			referer.replace("http://"+httpRequest.getHeader("host"), "");
			referer.replace(httpRequest.getContextPath(), "");
			httpRequest.setAttribute("referer", referer);
			httpRequest.setAttribute("excessPros", detail.getExcessPros());
			return referer;
		}*/
		
		HttpSession session = httpRequest.getSession();
		String accountID = SessionBiz.getAccount(session);
		Long departmentId = SessionBiz.getDepartmentId(session);
		
		TAccount account = Env.accountMap.get(accountID);
		TDepartment department = Env.departmentMap.get(account.getDepartmentID());
		httpRequest.setAttribute("account", account);
		httpRequest.setAttribute("department", department);
		
		if(viewName.contains("/")){
			return "/jsp/"+viewName;
		}else{
			return "/jsp/applicationOrder/"+viewName;
		}
	}
	
	/**
	 * 实际处理(入库，出库，实施入库、实施出库、审批等)的controller
	 * @param request
	 * @param resp
	 * @return
	 */
    @RequestMapping("/approveHandle")
	public String approveHandle(HttpServletRequest request, HttpServletResponse resp){
    	
    	ApplicationVO app = new ApplicationVO();
    	String applicationId = request.getParameter("applicationId"); // 申请单ID
		String taskId = request.getParameter("taskId");
		String procInstId = request.getParameter("procInstId");
		
		app.setApplicationId(Long.valueOf(applicationId));
		app.setTaskId(taskId);
		app.setProcInstId(procInstId);
		
		DataMsg dataMsg;
		try {
			dataMsg = applicationBiz.handle(app, request);
		} catch (Exception e) {
			log.error("", e);
			dataMsg = new DataMsg();
			dataMsg.setState("N");
			dataMsg.setMsg("申请单审批失败，请重新审批");
		}
		request.setAttribute(Env.DATA_MSG, dataMsg);
		return (dataMsg == null || "Y".equals(dataMsg.getState())) ? OK_VIEW: ERROR_VIEW;
	}
    
    /**
     * 批量审批处理(入库，出库，实施入库、实施出库、审批等)的controller
     * @param request
     * @param resp
     * @return
     */
    @RequestMapping("/batchApproveHandle")
    public String batchApproveHandle(HttpServletRequest request, HttpServletResponse resp){
    	String applicationJsonStr = request.getParameter("applicationJsonStr");
    	Type type = new TypeToken<List<ApplicationVO>>(){}.getType();
    	List<ApplicationVO> applicationList = JsonUtil.jsonToList(applicationJsonStr, type);
    	DataMsg dataMsg = null;
    	try {
    		applicationBiz.batchHandle(applicationList, request);
		} catch (Exception e) {
			log.error("", e);
			dataMsg = new DataMsg();
			dataMsg.setState("N");
			dataMsg.setMsg("申请单批量审批失败，请重新审批");
		}
    	request.setAttribute(Env.DATA_MSG, dataMsg);
    	return (dataMsg == null || "Y".equals(dataMsg.getState())) ? OK_VIEW: ERROR_VIEW;
    }
    
	/**
	 * 查询申请单详细信息
	 */
	@RequestMapping("/queryById")
	public String queryById(HttpServletRequest request, ConditionParam conditions){
		TApplication application =null;
		try {
			Long id = Long.parseLong(request.getParameter("applicationId"));
			//查询申请单详细信息
			application = TApplicationBiz.queryById(id);
			request.setAttribute("application", application);
			request.setAttribute("conditions", JsonUtil.beanToJson(conditions));
			String refererUrl = RequestUtils.getRefererUrl(request);
			request.setAttribute("refererUrl", refererUrl);
			
			List<TAttachment> attachmentList = null;
			List<TProperty> propertyList = null;
			if(!ProcedureKey.PROCEDURE003.toString().equals(application.getProcedureCode())
					|| application.getParentId() != null){
				//查询财物信息
				propertyList = TPropertyBiz.queryByApplicationId(id);
				
				request.setAttribute("propertyList", propertyList);
				
				String isPrint = request.getParameter("isPrint");
				if("Y".equals(isPrint)){
					return "/jsp/applicationOrder/applicationOrderDetailForPrint";
				}
				//查询批注信息
				List<Comment> commentList = TApplicationBiz.findCommentByApplicationId(id);
				request.setAttribute("commentList", commentList);
				
				if(StringUtils.isBlank(application.getParentId())){
					attachmentList = TAttachmentBiz.queryAll(Env.TYPE_APPLICATION, id.toString());
				}else{
					attachmentList = TAttachmentBiz.queryAll(Env.TYPE_APPLICATION, application.getParentId());
				}
				request.setAttribute("attachmentList", attachmentList);
				
				return "/jsp/applicationOrder/applicationOrderDetail";
			}else{
				List<ApplicationVO> childrenApplications = TApplicationBiz.queryByParentId(id);
				for(ApplicationVO vo : childrenApplications){
					propertyList = TPropertyBiz.queryByApplicationId(vo.getApplicationId());
					vo.setPros(propertyList);
				}
				
				attachmentList = TAttachmentBiz.queryAll(Env.TYPE_APPLICATION, id.toString());
				request.setAttribute("attachmentList", attachmentList);
				
				request.setAttribute("childrenApplications", childrenApplications);
				return "/jsp/applicationOrder/applicationOrderDetailForHandle";
			}
			
			
		} catch (Exception e) {
			log.error(e);
			
		}
		return "/jsp/applicationOrder/applicationOrderDetail";
	}
	
	
	/**
	 * 提交申请的方法
	 * @param requ
	 * @return
	 */
	@RequestMapping("/doApplyNew")
	public void doApplyNew(ApplicationVO applicationVo, HttpServletRequest request, HttpServletResponse resp) {
		DataMsg dataMsg = null;
		String dpResultStr = request.getParameter("dpResultStr");
		String proJson = request.getParameter("proJson");
		log.info("====>proJson = " + proJson);
		String[] attachmentStr = request.getParameterValues("attachmentJson");
		String attachmentJson= Arrays.toString(attachmentStr);
		log.info("====>attachmentJson = " + attachmentJson);
		String[] proIds = request.getParameterValues("proId");
		try {
			if(PropertyUtils.isProJsonBlank(proJson)){
				dataMsg = new DataMsg();
				dataMsg.setState("F");
				dataMsg.setMsg("请选择财物");
				PrintTool.print(resp, PrintTool.printJSON(dataMsg), "json");
				return;
			}
			DisposalAndProcedure dpResult = (DisposalAndProcedure) JsonUtil.jsonToBean(dpResultStr, DisposalAndProcedure.class);
			List<TProperty> propertyList = null;
			List<TAttachment> attachmentList = null;
			
			Type type = new TypeToken<List<TProperty>>(){}.getType();
			propertyList = JsonUtil.jsonToList(proJson, type);
			
			type = new TypeToken<List<TAttachment>>(){}.getType();
			attachmentList = JsonUtil.jsonToList(attachmentJson, type);
			
			applicationVo.setDpResult(dpResult);
			applicationVo.setPros(propertyList);
			applicationVo.setTargetStorehouse(dpResult.getDisposal().getDescription());
			applicationVo.setAttachment(attachmentList);
			
			log.info(applicationVo.toString());
			
			//判断申请的财物是否符合当前选中流程
			List<TProperty> illegalProperties = getIllegalProperty(dpResult, propertyList);
			if(illegalProperties.size() <= 0){
				if(!("PROCEDURE003".equals(dpResult.getProcedure().getCode()))){
					dataMsg = applicationBiz.doApply(applicationVo, request);
				}else{
					dataMsg = applicationBiz.doApplyHandle(applicationVo, request);
				}
			}else{
				dataMsg = TApplicationBiz.getIllegalPropertyTip(dpResult.getProcedure().getCode(), illegalProperties);
			}
			
			PrintTool.print(resp, PrintTool.printJSON(dataMsg), "json");
		} catch (Exception e) {
			log.error("", e);
			try {
				PrintTool.print(resp, PrintTool.printJSON(SYS_ERROR), "json");
			} catch (IOException e1) {
				log.error("", e1);
			}
		}
		return;
	}	

	/**
	 * 入库审核(中心库)列表 
	 * @param request
	 * @param page
	 * @param condition
	 * @param resp
	 * @return
	 */
	@RequestMapping("/queryForIn")
	public String queryForIn(HttpServletRequest request,Pager page, ConditionParam condition,
			HttpServletResponse resp) {
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String accStr = SessionBiz.getAccount(request.getSession());
		
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		
		List<String> procedureTasks = new ArrayList<String>();
		List<TRole> currRoles = TRoleUtils.getCurrRoles(request.getSession());
		if(TRoleUtils.contain(currRoles, RoleKey.BADWLD.toString())){
			procedureTasks.add("PROCEDURE008usertask2");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKGLY.toString())){
			procedureTasks.add("PROCEDURE008usertask3");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKLD.toString())){
			procedureTasks.add("PROCEDURE008usertask4");
		}
		
		if(procedureTasks.size() <= 0){
			procedureTasks.add("ERROR");
		}
		
		extraMap.put("procedureTask", (String[]) ArrayUtil.list2array(procedureTasks, String.class));
		
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		queryIntoApplicationList(request, page, condition, extraMap);
		
		
		return "/jsp/applicationOrder/intoApplicationList";
	}

	
	/**
	 * 调用出库/归还审核列表
	 * @param request
	 * @param page
	 * @param condition
	 * @param resp
	 * @return
	 */
	@RequestMapping("/queryForInvoke")
	public String queryForInvoke(HttpServletRequest request,Pager page, ConditionParam condition,
			HttpServletResponse resp){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String accStr = SessionBiz.getAccount(request.getSession());
		
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		
		List<String> procedureTasks = new ArrayList<String>();
		List<TRole> currRoles = TRoleUtils.getCurrRoles(request.getSession());
		if(TRoleUtils.contain(currRoles, RoleKey.BADWLD.toString())){
			procedureTasks.add("PROCEDURE004usertask2");
			procedureTasks.add("PROCEDURE005usertask2");
			procedureTasks.add("PROCEDURE006usertask2");
			procedureTasks.add("PROCEDURE007usertask2");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKLD.toString())){
			procedureTasks.add("PROCEDURE005usertask3");
			procedureTasks.add("PROCEDURE007usertask3");
		}
		
		if(procedureTasks.size() <= 0){
			procedureTasks.add("ERROR");
		}
		
		extraMap.put("procedureTask", (String[]) ArrayUtil.list2array(procedureTasks, String.class));
		
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		queryIntoApplicationList(request, page, condition, extraMap);
		
		return "/jsp/applicationOrder/invokeOutApplicationList";
	}

	/**
	 * 移库审核列表
	 * @param request
	 * @param page
	 * @param condition
	 * @param resp
	 * @return
	 */
	@RequestMapping("/queryForMove")
	public String queryForMove(HttpServletRequest request,Pager page, ConditionParam condition,
			HttpServletResponse resp){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String accStr = SessionBiz.getAccount(request.getSession());
		
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		
		List<String> procedureTasks = new ArrayList<String>();
		List<TRole> currRoles = TRoleUtils.getCurrRoles(request.getSession());
		if(TRoleUtils.contain(currRoles, RoleKey.BADWLD.toString())){
			procedureTasks.add("PROCEDURE002usertask2");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKLD.toString())){
			procedureTasks.add("PROCEDURE002usertask4");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKGLY.toString())){
			procedureTasks.add("PROCEDURE002usertask3");
		}
		
		if(procedureTasks.size() <= 0){
			procedureTasks.add("ERROR");
		}
		
		extraMap.put("procedureTask", (String[]) ArrayUtil.list2array(procedureTasks, String.class));
		
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		queryIntoApplicationList(request, page, condition, extraMap);
		
		return "/jsp/applicationOrder/moveApplicationList";
	}
	
	@RequestMapping("/queryForHandle")
	public String queryForHandle(HttpServletRequest request,Pager page, ConditionParam condition,
			HttpServletResponse resp){
		
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String accStr = SessionBiz.getAccount(request.getSession());
		
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		
		List<String> procedureTasks = new ArrayList<String>();
		List<TRole> currRoles = TRoleUtils.getCurrRoles(request.getSession());
		if(TRoleUtils.contain(currRoles, RoleKey.BADWLD.toString())){
			procedureTasks.add("PROCEDURE003usertask2");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.FJLD.toString())){
			procedureTasks.add("PROCEDURE003usertask3");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKLD.toString())){
			procedureTasks.add("PROCEDURE003usertask5");
		}
		
		if(procedureTasks.size() <= 0){
			procedureTasks.add("ERROR");
		}
		
		extraMap.put("procedureTask", (String[]) ArrayUtil.list2array(procedureTasks, String.class));
		
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		queryIntoApplicationList(request, page, condition, extraMap);
		
		return "/jsp/applicationOrder/handleApplicationList";
	}
	/**
	 * 查询下个审批人
	 * @param applicationId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/queryNextHandlers")
	public void queryNextHandlers(Long applicationId, HttpServletResponse response) throws IOException{
		
		String handlers = TApplicationBiz.queryHandlersByAppId(applicationId);
		
		PrintTool.print(response, handlers, "");
	}
	
	
	/**
	 * 实施入库的列表查询
	 * @param bind
	 */
	@RequestMapping("/queryForBeIn")
	public String queryForBeIn(HttpServletRequest request,Pager page, ConditionParam condition,
			HttpServletResponse resp) {
		
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String accStr = SessionBiz.getAccount(request.getSession());
		
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		
		List<String> procedureTasks = new ArrayList<String>();
		List<TRole> currRoles = TRoleUtils.getCurrRoles(request.getSession());
		if(TRoleUtils.contain(currRoles, RoleKey.ZCKGLY.toString())){
			procedureTasks.add("PROCEDURE001usertask2");
			procedureTasks.add("PROCEDURE006usertask3");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKGLY.toString())){
			procedureTasks.add("PROCEDURE002usertask5");
			procedureTasks.add("PROCEDURE008usertask5");
			procedureTasks.add("PROCEDURE007usertask4");
		}
		if(procedureTasks.size() <= 0){
			procedureTasks.add("ERROR");
		}
		
		extraMap.put("procedureTask", (String[]) ArrayUtil.list2array(procedureTasks, String.class));
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		queryIntoApplicationList(request, page, condition, extraMap);
		
		return "/jsp/applicationOrder/preInApplicationList";
	}
	
	/**
	 * 实施出库审核列表
	 */
	@RequestMapping("/queryForBeOut")
	public String queryForBeOut(HttpServletRequest request,Pager page, ConditionParam condition,
			HttpServletResponse resp){
		MyPagerUtil.initPager(page);
		Map<String, String[]> map = request.getParameterMap();
		String accStr = SessionBiz.getAccount(request.getSession());
		
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		
		List<TRole> currRoles = TRoleUtils.getCurrRoles(request.getSession());
		List<String> procedureTasks = new ArrayList<String>();
		if(TRoleUtils.contain(currRoles, RoleKey.ZCKGLY.toString())){
			procedureTasks.add("PROCEDURE003usertask6");
			procedureTasks.add("PROCEDURE004usertask3");
			
			//民警销毁，财物出库
			procedureTasks.add("PROCEDURE009usertask3");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKGLY.toString())){
			procedureTasks.add("PROCEDURE003usertask7");
			procedureTasks.add("PROCEDURE005usertask4");
			
			//民警销毁，财物出库
			procedureTasks.add("PROCEDURE010usertask4");
			//治安科民警销毁，财物出库
			procedureTasks.add("PROCEDURE011usertask4");
		}
		if(procedureTasks.size() <= 0){
			procedureTasks.add("ERROR");
		}
		extraMap.put("procedureTask", (String[]) ArrayUtil.list2array(procedureTasks, String.class));
		
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		queryIntoApplicationList(request, page, condition, extraMap);
		
		
		return "/jsp/applicationOrder/preOutApplicationList";
	}
	
	/**
	 * 根据申请单查询流程跟踪信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryTrackByApplicationId")
	public String queryTrackByApplicationId(HttpServletRequest request){
		String applicationId =request.getParameter("applicationId");
		
		List<ProcessTrack> trackList = TApplicationBiz.queryTrackByApplicationId(Long.valueOf(applicationId));
		String picPath = TApplicationBiz.getPicPathByApplicationId(Long.valueOf(applicationId));
		request.setAttribute("trackList", trackList);
		request.setAttribute("picPath", picPath);
		return "/jsp/applicationOrder/showProcessTrack";
	}

	/**
	 * 执行入库前的页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/preIn")
	public String preIn(HttpServletRequest request,HttpServletResponse response) {
		try {
    		// 这里查询审批信息。
    			
    		String applicationIdStr = request.getParameter("applicationId"); //申请单ID
    		Long applicationId = Long.parseLong(applicationIdStr);
    		TApplication application = TApplicationBiz.queryById(applicationId);
    		request.setAttribute("application", application);
    		
    		//查询财物信息
    		List<TProperty> propertyList = TPropertyBiz.queryByApplicationId(application.getId());
    		request.setAttribute("propertyList", propertyList);
    		
    		
    		//查询批注信息
    		List<Comment> commentList = TApplicationBiz.findCommentByApplicationId(applicationId);
    		request.setAttribute("commentList", commentList);
    		
    		Long[] ret=TRoleUtils.getDeptIdByCurrentRole(request);
			List<TStoreRoom> lRooms =TStoreRoomBiz.queryName(ret[0],ret[1]);	
    		request.setAttribute("lRooms", lRooms);	
    		
    		List<TRack> tRacks=TRackBiz.queryName();
    		request.setAttribute("tRacks", tRacks);	
    		
    		} catch (Exception e) {
    			log.error("", e);
    		}
    		return "/jsp/applicationOrder/preIn";
	}
	
	@RequestMapping("/showTrack")
	public void showTrack(HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();
		String storeroomId=request.getParameter("storeroomId");
		List<TRack> tRacks=TRackBiz.queryNameBySid(storeroomId);
		JSONArray jArray=JSONArray.fromObject(tRacks);
		out.print(jArray);
		
	}

	@RequestMapping("/showById")
	public String showById(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String viewname=request.getParameter("viewname");
		if("showLockById".equals(viewname)){
			String rackId=request.getParameter("rackId");
			List<TLocker> tLockers=TLockerBiz.queryLockById(rackId);
			request.setAttribute("tLockers", tLockers);
			
		}
		if("showStorageLocationById".equals(viewname)){
			String stroomId=request.getParameter("stroomId");
			List<TStorageLocation> tStorageLocations=TStorageLocationBiz.queryStorageLocationById(stroomId);
			request.setAttribute("tStorageLocations", tStorageLocations);
			
		}
		
		request.setAttribute("msg", "ok");
		return "/jsp/applicationOrder/preIn";
	
		
	}
	
	/**
	 * 集中销毁审核
	 * @param request
	 * @param page
	 * @param condition
	 * @param resp
	 * @return
	 */
	@RequestMapping("/queryForDestory")
	public String queryForDestory(HttpServletRequest request,Pager page, ConditionParam condition,
			HttpServletResponse resp){
		MyPagerUtil.initPager(page);
		String viewName = request.getParameter("viewName");
		Map<String, String[]> map = request.getParameterMap();
		String accStr = SessionBiz.getAccount(request.getSession());
		
		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("account", new String[]{accStr});
		
		List<String> procedureTasks = new ArrayList<String>();
		List<TRole> currRoles = TRoleUtils.getCurrRoles(request.getSession());
		if(TRoleUtils.contain(currRoles, RoleKey.BADWLD.toString())&&"destoryApplicationList".equals(viewName)){
			//办案民警销毁（暂存库）办案单位领导审核
			procedureTasks.add("PROCEDURE009usertask2");
			//办案民警销毁（中心库）办案单位领导审核
			procedureTasks.add("PROCEDURE010usertask2");
		}
		/*if(TRoleUtils.contain(currRoles, RoleKey.ZCKGLY.toString())&&"destoryApplicationList".equals(viewName)){
			//办案民警销毁（暂存库）暂存库管理员审核
			procedureTasks.add("PROCEDURE009usertask3");
		}*/
		if(TRoleUtils.contain(currRoles, RoleKey.ZAKLD.toString())&&"destoryApplicationList".equals(viewName)){
			//治安科民警销毁 治安科领导审核
			procedureTasks.add("PROCEDURE011usertask2");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZXKLD.toString())&&"destoryApplicationList".equals(viewName)){
			//办案民警销毁（中心库）中心库领导审核
			procedureTasks.add("PROCEDURE010usertask3");
			//治安科民警销毁（中心库）中心库领导审核
			procedureTasks.add("PROCEDURE011usertask3");
		}
		/*if(TRoleUtils.contain(currRoles, RoleKey.ZXKGLY.toString())&&"destoryApplicationList".equals(viewName)){
			//办案民警销毁（中心库）中心库管理员审核
			procedureTasks.add("PROCEDURE010usertask4");
			//治安科民警销毁（中心库）中心库管理员审核
			procedureTasks.add("PROCEDURE011usertask4");
		}*/
		if(TRoleUtils.contain(currRoles, RoleKey.BAMJ.toString())&&"destoryImplApplicationList".equals(viewName)){
			//办案民警销毁（暂存库）办案民警销毁
			procedureTasks.add("PROCEDURE009usertask4");
			//办案民警销毁（中心库）办案民警销毁
			procedureTasks.add("PROCEDURE010usertask5");
		}
		if(TRoleUtils.contain(currRoles, RoleKey.ZAKMJ.toString())&&"destoryImplApplicationList".equals(viewName)){
			//治安科民警销毁（中心库）治安科民警销毁
			procedureTasks.add("PROCEDURE011usertask5");
		}
		if(procedureTasks.size() <= 0){
			procedureTasks.add("ERROR");
		}
		
		extraMap.put("procedureTask", (String[]) ArrayUtil.list2array(procedureTasks, String.class));
		
		extraMap = RequestUtils.addExtraParamToRequestParamMap(map, extraMap);
		
		queryIntoApplicationList(request, page, condition, extraMap);
		
		return "/jsp/applicationOrder/"+viewName;
	}
	
	/**
	 * 实际执行入库动作
	 * @param request
	 * @param response
	 * @return
	
	@RequestMapping("/doIn")
	public void doIn(PropertyVO pro, HttpServletRequest request,HttpServletResponse response) {
		DataMsg dataMsg = null;
		try {
			//获取参数
			String applicationId = request.getParameter("applicationId");
			
			dataMsg = TApplicationBiz.doIn(Long.valueOf(applicationId), pro);
			
			PrintTool.print(response, dataMsg, "json");
		} catch (Exception e) {
			log.error("", e);
		}
		return;
	}
	 */
	/**
	 * 查询入库申请单列表的公用操作
	 * @param request
	 * @param page
	 * @param condition
	 */
	public void queryIntoApplicationList(HttpServletRequest request, Pager page,ConditionParam condition, Map<String, String[]> extraMap){
		
		try {
			
			ListAndPager<ApplicationVO> taskList = TApplicationBiz.queryForInAndOut(extraMap, page);
			Pager proPage = taskList.getPager();
			proPage.setSizePerPage(page.getSizePerPage());
			List<ApplicationVO> messages = taskList.getList();
			
			ArrayUtil.print(messages, true);
			request.setAttribute(Env.RESULT, messages);
			request.setAttribute("page", proPage);
			request.setAttribute("condition", condition);
		}catch(Exception e){
			log.error("", e);
		}
	}
	
	/**
	 * 动态调用判断财物是否符合申请条件的方法
	 * @param dpResult
	 * @param propertyList
	 * @return
	 */
	public List<TProperty> getIllegalProperty(DisposalAndProcedure dpResult, List<TProperty> propertyList){
		List<TProperty> illegalProperties = new ArrayList<TProperty>();
		String methodName = "";
		boolean flag = true;
		
		switch(dpResult.getProcedure().getCode()){
			case "PROCEDURE001":
				methodName = "judgeCommonPropertyInTemporary";
				break;
			case "PROCEDURE002":
				methodName = "judgeCommonPropertyInCenter";
				break;
			case "PROCEDURE003":case "PROCEDURE004":case "PROCEDURE005":
				break;
			case "PROCEDURE006":
				methodName = "judgeProInvokeIntoTemporary";
				break;
			case "PROCEDURE007":
				methodName = "judgeProInvokeIntoCenter";
				break;
			case "PROCEDURE008":
				methodName = "judgeSpecailPropertyInCenter";
				break;
			case "PROCEDURE009":
				methodName = "judgeProDoDestroy";
				break;
			case "PROCEDURE010":
				methodName = "judgeProDoDestroy";
				break;
			case "PROCEDURE011":
				methodName = "judgeProDoDestroy";
				break;
		}
		
		Class<JudgeProcessServiceImpl> clazz = JudgeProcessServiceImpl.class;
		try {
			if(StringUtils.isNotBlank(methodName)){
				
				Method method = clazz.getMethod(methodName, TProperty.class);
				for(TProperty property : propertyList){
					flag = (boolean) method.invoke(judgeProcessService, property);;
					
					if(!flag){
						illegalProperties.add(property);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("", e);
		}
		
		return illegalProperties;
	}
	
	@RequestMapping("/getApplicationNo")
	public void getApplicationNo(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		
		Map<String, Object> loginForm = SessionBiz.getLoginForm(session);
		String applicationNo = IdHolderUtils.getApplicationNo(loginForm.get("DEPART_NAME").toString(), Env.APPLICATIONNO);
		
		try {
			PrintTool.print(response, applicationNo, "");
		} catch (IOException e) {
			log.error("", e);
		}
		return;
	}
	
	@RequestMapping("/getViewName")
	public void getViewName(HttpServletRequest request, HttpServletResponse response){
		String path = request.getContextPath();
		String taskId = request.getParameter("taskId");
		String procInstId = request.getParameter("procInstId");
		String formUrl = request.getParameter("formUrl");
		String applicationIdStr = request.getParameter("applicationId"); //申请单ID
		Long applicationId = Long.parseLong(applicationIdStr);
		TApplication application = TApplicationBiz.queryById(applicationId);
		
		// 找到对应的activiti流程taskId
		Task task = Env.taskService.createTaskQuery().taskId(taskId).singleResult();
		String actTasKId = task.getTaskDefinitionKey();
		if(StringUtils.isNotBlank(actTasKId)){
			// 找到对应的activiti流程key
			String viewName = TApplicationBiz.getProcessKey(application.getProcedureCode(),actTasKId);
			String url = path+"/applicationOrder/queryForPreHandle.do?viewName="+viewName+"&taskId="+taskId+"&applicationId="+applicationId+"&procInstId="+procInstId+"&formUrl="+formUrl;
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}else{
			log.error("task is not null");
		}
	}
	
	@InitBinder
	public void initDate(WebDataBinder bind){
		bind.registerCustomEditor(Date.class, new DateEditor());
	}
	
}    
