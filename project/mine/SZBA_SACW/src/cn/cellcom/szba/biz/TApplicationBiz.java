package cn.cellcom.szba.biz;

import static cn.cellcom.szba.common.Env.taskService;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.PropertyUtils;
import cn.cellcom.szba.common.RequestUtils;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.common.utils.IdHolderUtils;
import cn.cellcom.szba.databean.DepartmentAndRoles;
import cn.cellcom.szba.databean.Disposal;
import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.databean.Flownode;
import cn.cellcom.szba.databean.Procedure;
import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TAppProTransLog;
import cn.cellcom.szba.domain.TApplication;
import cn.cellcom.szba.domain.TApplicationProperty;
import cn.cellcom.szba.domain.TAttachment;
import cn.cellcom.szba.domain.TCase;
import cn.cellcom.szba.domain.TDemage;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TStorageLocation;
import cn.cellcom.szba.enums.ActualPosition;
import cn.cellcom.szba.enums.DamageStatusKey;
import cn.cellcom.szba.enums.DisposalKey;
import cn.cellcom.szba.enums.ProcedureKey;
import cn.cellcom.szba.enums.PropertyStatusKey;
import cn.cellcom.szba.enums.RoleIdKey;
import cn.cellcom.szba.service.impl.ActivitiUtil;
import cn.cellcom.szba.service.impl.FlownodeEngine;
import cn.cellcom.szba.vo.ApplicationVO;
import cn.cellcom.szba.vo.ProcessTrack;
import cn.cellcom.szba.vo.PropertyVO;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

import com.google.gson.reflect.TypeToken;

@Component("applicationBiz")
public class TApplicationBiz {
	private static Log log = LogFactory.getLog(TApplicationBiz.class);
	private static String sqlQueryForInAndOut = 
			"select v.application_id,v.his_status, v.task_id,v.proc_inst_id,v.proc_def_id, v.application_name,v.application_status,v.case_name,v.case_id ,v.jzcase_id, v.application_account account, v.application_create_time, v.handler, v.handle_time, v.procedure_task, v.target_storehouse, v.application_no, v.apply_basis, v.plan_storage_date,v.procedure_code, v.leaddepartment_id as \"leadDepartmentId\", v.assistdepartment_id as \"assistDepartmentId\""
			+ " from v_hi_ru_tasklist_case v "
			+ " left join t_application_property ap on ap.application_id=v.application_id"
			+ " left join t_property pro on ap.property_id=pro.id "
			+ " where  1 = 1" 
			+ " and  ( v.user_id = string:account)"
			+ " and ( v.application_id = int:applicationId) "
			+ " and ( v.application_no = string:applicationNo) "
			+ " and ( pro.name like blike:condiProName) "
			+ " and ( pro.id = string:condiProCode) "
			+ " and ( pro.pro_no = string:condiProNo) "
			+ " and ( v.jzcase_id like plike:condiJzcaseId)"
			+ " and ( v.case_name like blike:condiCaseName)"
			+ " and ( pro.id in strings:propertyIds)"
			+ " and ( v.application_create_time >= date:condiStartTime) "
			+ " and ( v.application_create_time <= date:condiEndTime) " 
			+ " and ( v.procedure_task in strings:procedureTask) " 
			+ " order by v.his_status asc, v.application_create_time desc";

	private static String SQL_QUERY_T_APPLICATION_BY_ID = "select x.target_storehouse as \"targetStorehouse\", x.EXPIRATION_DATE as \"expirationDate\",x.SAVE_DATE_TYPE as \"saveDateType\",x.plan_storage_date as \"planStorageDate\",x.DELIVER_TYPE as \"deliverType\",x.id, x.apply_Basis," 
			+ "   x.status, x.create_time as \"createTime\", x.account, x.procedure_Code as \"procedureCode\","
			+ "	x.procedure_Description, x.disposal_Code, x.disposal_Description, x.leadDepartment_id as \"leadDepartmentId\", x.assistDepartment_id as \"assistDepartmentId\"" +
			", x.application_no,x.parent_id,x.plan_start_date, x.duration " 
			+ "	from t_application x " 
			+ "	where x.id = ?";
	
	private static String SQL_QUERY_T_APPLICATION_BY_PARENTID = "select x.target_storehouse , x.EXPIRATION_DATE ,x.SAVE_DATE_TYPE ,x.plan_storage_date ,x.DELIVER_TYPE ,x.id application_id, x.apply_Basis," 
			+ "   x.status application_status, x.create_time application_create_time, x.account, x.procedure_code,"
			+ "	x.procedure_description, x.disposal_Code, x.disposal_Description, x.leadDepartment_id as \"leadDepartmentId\", x.assistDepartment_id as \"assistDepartmentId\"" +
			", x.application_no,x.parent_id, e.proc_inst_id_ proc_inst_id, e.proc_def_id_ proc_def_id" 
			+ "	from t_application x " 
			+ " left join act_ru_execution e on e.business_key_=x.id" 
			+ "	where x.parent_id = ?";

	static String insertApplicationSql = "insert into t_application (apply_basis,account,procedure_code,procedure_description,disposal_code,disposal_description,application_name, case_id, department_id) values (?,?,?,?,?,?,?,?,?)";

	static String SQL_QUERY_TASK = " select * from v_ru_tasklist_case v "
			+ " where 1=1 and (v.user_id =string*account) "
			+ " and (v.APPLICATION_CREATE_TIME >= date:condiStartTime) " 
			+ " and (v.APPLICATION_CREATE_TIME <= date:condiEndTime)" 
			+ " and (v.id like plike:condiProId) and (v.\"NAME\" like blike:condiProName)"
			+ " and (v.jzcase_id like plike:condiJzcaseId) "
			+ " and (v.case_name like blike:condiCaseName)" 
			+ " and (v.application_id like plike:applicationId) "
			+ " and ( v.application_no = string:applicationNo) "
			+ " order by v.application_create_time desc";

	static String SQL_QUERY_HI_TASK = " select * from v_hi_tasklist_case v "
			+ " where 1=1 and " +
			" case when  v.assignee is null then  case when v.parent_id is null and (application_account = string*account) then 1 else 0 end else case when v.parent_id is null and (v.assignee = string*account) then 1 else 0 end end = 1"
			+ " and (v.APPLICATION_CREATE_TIME >= date:condiStartTime) " 
			+ " and (v.APPLICATION_CREATE_TIME <= date:condiEndTime)" 
			+ " and (v.id like plike:condiProId) and (v.\"NAME\" like blike:condiProName)"
			+ " and (v.jzcase_id like plike:condiJzcaseId) "
			+ " and (v.case_name like blike:condiCaseName)" 
			+ " and (v.application_id like plike:applicationId) "
			+ " and ( v.application_no = string:applicationNo) "
			+ " order by v.application_create_time desc";

	private static String SQL_QUERY_TASK_BY_APPID = 
			"select v.task_id,v.proc_inst_id,v.application_id,v.application_name,v.application_status,c.case_name,c.case_id,acc.name account_name,dept.name department_name,pro.id property_id,v.application_no " +
			" from v_ru_tasklist v "
			+ " left join t_case c on v.case_id = c.case_id"
			+ " left join t_account acc on acc.account=v.application_account " 
			+ " left join t_department dept on acc.department_id=dept.id  " 
			+ " left join t_application_property ap on ap.application_id = v.application_id " 
			+ " left join t_property pro on ap.property_id= pro.id  " 
			+ " where v.application_id = ?";
	private static String SQL_QUERY_HANDLERSNAME_BY_ACCOUNT = " select * from t_account where account  = ?";
	
	//执行入库
	private static String SQL_QUERY_BE_IN="select distinct tcase.CASE_NAME,tcase.CASE_ID,app.ID as application_id,app.CREATE_TIME as application_create_time,app.DEPARTMENT_ID,app.ACCOUNT,app.HANDLE_TIME,app.HANDLER,app.application_no from " +
			"T_APPLICATION  app left join T_CASE tcase on app.CASE_ID=tcase.CASE_ID left join T_APPLICATION_PROPERTY ap on ap.application_id=app.id left join t_property p on ap.property_id=p.id" +
			" where app.VALID_STATUS='Y' and app.PROCEDURE_CODE in ('PROCEDURE001') and app.STATUS='Y'"
			+ " and ( ap.property_id = string:condiProCode) "
			+ " and (p.name like blike:condiProName)";
	
	public static ListAndPager<ApplicationVO> queryForBeIn(Map<String, String[]> params, Pager pager) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<ApplicationVO> list=new ListAndPager<ApplicationVO>();
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_BE_IN, params);
			String sql = dbRequest.getSql();
			Object[] objs = dbRequest.getParameters();
			list=jdbc.queryPage(Env.DS, sql, ApplicationVO.class, pager.getCurrentIndex(), pager.getSizePerPage(), objs);
		}  catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	/**
	 * 发起申请的业务操作
	 * 
	 * @param applicationVO
	 */
	/**
	 * @param applicationVO
	 * @param request
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED) 
	public DataMsg doApply(ApplicationVO applicationVO, HttpServletRequest request) throws Exception {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		HttpSession session = request.getSession();
		DataMsg dataMsg = new DataMsg();

		Connection conn = null;
		List<TProperty> errorPropertyList = new ArrayList<TProperty>();
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			Map<String, Object> loginForm = SessionBiz.getLoginForm(session);
			// 角色
			List<Long> roleIDs = new ArrayList<Long>();
			// 账号
			String account = SessionBiz.getAccount(session);
			Long departmentId = Long.valueOf(loginForm.get("DEPARTMENT_ID").toString());

			String proStatus = "";
			//申请的财物
			List<TProperty> propertyList = applicationVO.getPros();
			
			//流程代码，及处置方式描述
			DisposalAndProcedure dpResult = applicationVO.getDpResult();
			Procedure procedure = dpResult.getProcedure();
			Disposal disposal = dpResult.getDisposal();

			// 找到对应的activiti流程key
			String processKey = getProcessKey(procedure);
			if (StringUtils.isBlank(processKey)) {
				dataMsg.setState("F");
				dataMsg.setMsg("无法找到对应的执行流程");
				return dataMsg;
			}
			// 根据activiti流程key找到对应发起申请之后的处理人列表
			DepartmentAndRoles dar = getDepartmentAndRole(processKey, request);
			// 获取处理人列表
			List<Long> deptIds = new ArrayList<Long>();
			if(dar.getDepartmentID() != null){
				deptIds.add(dar.getDepartmentID());
			}
			
			String handlers = ActivitiUtil.getHandlers(deptIds, dar.getRoleIDs());
			log.info("处理人列表:" + handlers);
			if (StringUtils.isBlank(handlers)) {
				dataMsg.setState("F");
				dataMsg.setMsg("下一个环节没处理人可以处理");
				return dataMsg;
			}

			applicationVO.setAccount(account);
			//设置申请单名称
			if(applicationVO.getCaseName() == null){
				applicationVO.setApplicationName(applicationVO.getCaseName() + disposal.getDescription());
			}else{
				applicationVO.setApplicationName(applicationVO.getCaseName() + disposal.getDescription());
			}
			//设置申请单编号
			if(StringUtils.isBlank( applicationVO.getApplicationNo() )){
				applicationVO.setApplicationNo(IdHolderUtils.getApplicationNo(Env.departmentMap.get(departmentId).getName(), Env.APPLICATIONNO));
			}
			applicationVO.setDepartmentId(departmentId);
			//生成申请单
			jdbc.saveObject(conn, applicationVO);
			//申请单id
			Long applicationID = jdbc.queryForObject(conn, "select SEQ_APPLICATION.currval from dual", Long.class);
			applicationVO.setApplicationId(applicationID);
			
			int updateCount = 0;
			TAppProTransLog apLog = null;
			TApplicationProperty ap = null;
			for(TProperty pro : propertyList){
				proStatus = getPropertyStatusKey(procedure).toString();
				
				//更新财物状态,以及财物保管方式、保管条件描述的属性
				updateCount = jdbc.update(conn, "update t_property set status = ?, save_method = ?, envi_demand = ?, damage_status = ?, is_running = 'Y' where id = ? and is_running = 'N'", 
						proStatus, pro.getSaveMethod(), pro.getEnviDemand(), pro.getDamageStatus(), pro.getProId());
				//如果更新失败，则说明当前财物已经被申请
				if(updateCount <= 0){
					pro.setIsRunning("Y");
					errorPropertyList.add(pro);
				}else{
					//只有没被申请过的财物才向t_application_propety和t_app_pro_trans_log表插入数据
					apLog = new TAppProTransLog();
					apLog.setAccount(account);
					apLog.setActualPosition(pro.getActualPosition());
					apLog.setApplicationId(applicationID);
					apLog.setPropertyStatus(proStatus);
					apLog.setPropertyId(pro.getProId());
					jdbc.saveObject(conn, apLog);
					
					ap = new TApplicationProperty();
					ap.setApplicationId(applicationID);
					ap.setPropertyId(pro.getProId());
					jdbc.saveObject(conn, ap);
					
					//如果有损毁状态信息，则进行损毁登记 ;处置不需要，所以只需要在个方法里增加代码
					if(StringUtils.isNotBlank(pro.getDamageStatus()) && !DamageStatusKey.WH.toString().equals(pro.getDamageStatus()) ){
						TDemage damage = new TDemage();
						damage.setDamageAccount(account);
						damage.setDescription(pro.getDamageReason());
						damage.setProNO(pro.getProNo());
						damage.setPropertyId(pro.getProId());
						damage.setApplicationId(applicationID);
						damage.setDamageStatus(pro.getDamageStatus());
						TDamageBiz.insertByApplication(damage, jdbc, conn);
					}
				}
			}
			//当不符合申请的财物数量和总财物数量一样的时候，提示用户重新申请
			if(errorPropertyList.size() == propertyList.size()){
				dataMsg = getEmptyPropertyTip(errorPropertyList);
				
				conn.rollback();
				return dataMsg;
			}
			
			//保存附件
			saveAttachment(applicationVO, jdbc, conn);
			
			// 启动流程,并指定流程变量,存申请人的ID,将申请单ID也作为流程变量存储并传递至activiti中business_key中
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("proposer", account);

			String objID = String.valueOf(applicationID);
			variables.put("objID", objID);

			ProcessInstance pi = ActivitiUtil.startupByKey(processKey, objID, variables);
			// 提交申请,完成任务
			Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
			
			if (task != null) {
				Map<String, Object> v = new HashMap<String, Object>();
				v.put("handlers", handlers);

				// 写入批注
				ActivitiUtil.addComment(account, "S", applicationVO.getApplyBasis(), task.getId(), task.getProcessInstanceId());
				
				// 根据登录者的部门ID+暂存库管理员角色 ，得到处理人员列表
				taskService.complete(task.getId(), v);
				
				/*Env.messageService.SendMessageToMore("待办通知--" + DateTool.formateTime2String(new Date(), "MM-dd HH:mm"), "您有待办事务，<a href='" + request.getContextPath()
						+ "/applicationOrder/queryForApprovalPaper.do'>点击查看</a>", "1", handlers, EnvMessage.IS_RELATIVE.IS_RELATIVE.value());*/
			}
			
			//当存在不符合流程的财物，给用户提示
			if(errorPropertyList.size() > 0){
				dataMsg = getErrorPropertyTip(errorPropertyList, applicationVO);
			}else{
				dataMsg = getSuccessTip(applicationVO);
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
		}finally{
			jdbc.closeAll();
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("", e);
				}
			}
			conn = null;
		}
		
		return dataMsg;
	}

	/**
	 * 由于财物处置流程比较特殊，用以下方法做财物处置申请
	 * 在做处置的流程的时候，还需要去关注JudgeOutHouse类，这个类是用来判断财物是否需要出库的
	 * @param applicationVO
	 * @param request
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED) 
	public DataMsg doApplyHandle(ApplicationVO topApplicationVO, HttpServletRequest request){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		HttpSession session = request.getSession();
		DataMsg dataMsg = new DataMsg();

		Connection conn = null;
		//不符合流程的财物列表
		List<TProperty> errorPropertyList = new ArrayList<TProperty>();
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			Map<String, Object> loginForm = SessionBiz.getLoginForm(session);
			// 账号
			String account = SessionBiz.getAccount(session);
			Long departmentId = Long.valueOf(loginForm.get("DEPARTMENT_ID").toString());

			//申请的财物
			List<TProperty> pros = topApplicationVO.getPros();
			//流程代码，及处置方式描述
			DisposalAndProcedure dpResult = topApplicationVO.getDpResult();
			Procedure procedure = dpResult.getProcedure();
			Disposal disposal = dpResult.getDisposal();
			
			topApplicationVO.setAccount(account);
			//设置父申请单名称
			if(topApplicationVO.getCaseName() == null){
				topApplicationVO.setApplicationName(topApplicationVO.getCaseName() + disposal.getDescription());
			}else{
				topApplicationVO.setApplicationName(topApplicationVO.getCaseName() + disposal.getDescription());
			}
			//设置父申请单编号
			if(StringUtils.isBlank(topApplicationVO.getApplicationNo())){
				topApplicationVO.setApplicationNo(IdHolderUtils.getApplicationNo(Env.departmentMap.get(departmentId).getName(), Env.APPLICATIONNO));
			}
			topApplicationVO.setDepartmentId(departmentId);
			jdbc.saveObject(conn, topApplicationVO);
			//父申请单id
			Long applicationID = jdbc.queryForObject(conn, "select SEQ_APPLICATION.currval from dual", Long.class);
			topApplicationVO.setApplicationId(applicationID);
			
			Map<String, ApplicationVO> applicationMap = new HashMap<String, ApplicationVO>();
			
			//保存附件
			saveAttachment(topApplicationVO, jdbc, conn);
			
			for(TProperty p : pros){
				/************财物分单*************/
				splitApplicationVO(applicationMap, p, topApplicationVO);
			}
			
			Set<Entry<String, ApplicationVO>> entrySet = applicationMap.entrySet();
			ApplicationVO vo = null;
			for(Entry<String, ApplicationVO> entry : entrySet){
				vo = entry.getValue();

				//子申请单中的财物
				List<TProperty> propertyList = vo.getPros();

				// 找到对应的activiti流程key
				String processKey = getProcessKey(procedure);
				if (StringUtils.isBlank(processKey)) {
					dataMsg.setState("F");
					dataMsg.setMsg("无法找到对应的执行流程");
					return dataMsg;
				}
				
				// 根据activiti流程key找到对应发起申请之后的处理人列表
				DepartmentAndRoles dar = getDepartmentAndRole(processKey, request);
				// 获取处理人列表
				List<Long> deptIds = new ArrayList<Long>();
				if(dar.getDepartmentID() != null){
					deptIds.add(dar.getDepartmentID());
				}
				
				String handlers = ActivitiUtil.getHandlers(deptIds, dar.getRoleIDs());
				log.info("处理人列表:" + handlers);
				if (StringUtils.isBlank(handlers)) {
					dataMsg.setState("F");
					dataMsg.setMsg("下一个环节没处理人可以处理");
					return dataMsg;
				}

				vo.setAccount(account);
				vo.setApplicationName(topApplicationVO.getApplicationName());
				vo.setApplicationNo(IdHolderUtils.getApplicationNo(Env.departmentMap.get(departmentId).getName(), Env.APPLICATIONNO));
				vo.setDepartmentId(departmentId);
				vo.setCaseName(topApplicationVO.getCaseName());
				vo.setCaseId(topApplicationVO.getCaseId());
				vo.setJzcaseId(topApplicationVO.getJzcaseId());
				
				//生成子申请单
				jdbc.saveObject(conn, vo);
				Long voID = jdbc.queryForObject(conn, "select SEQ_APPLICATION.currval from dual", Long.class);
				
				String proStatus = "";
				String actualPosition = "";
				String disposalKey = vo.getDpResult().getDisposal().getCode();

				int updateCount = 0;
				for(TProperty pro : propertyList){
					proStatus = getHandlePropertyStatus(disposalKey);
					actualPosition = pro.getActualPosition();
					
					//记录流程中，财物与申请单之间的关系
					TAppProTransLog apLog = new TAppProTransLog();
					apLog.setAccount(account);
					apLog.setActualPosition(pro.getActualPosition());
					apLog.setApplicationId(voID);
					apLog.setPropertyStatus(proStatus);
					apLog.setPropertyId(pro.getProId());
					
					//记录财物当前对应的申请单
					TApplicationProperty ap = new TApplicationProperty();
					ap.setApplicationId(voID);
					ap.setPropertyId(pro.getProId());
					
					//向t_application_propety和t_app_pro_trans_log表插入数据
					jdbc.saveObject(conn, ap);
					jdbc.saveObject(conn, apLog);
					//更新财物状态
					updateCount = jdbc.update(conn, "update t_property set status = ? ,is_running = 'Y' where id = ? and is_running = 'N'", proStatus, pro.getProId());
					//如果更新失败，则说明当前财物已经被申请
					if(updateCount <= 0){
						pro.setIsRunning("Y");
						errorPropertyList.add(pro);
					}
				}
				//当不符合申请的财物数量和总财物数量一样的时候，提示用户重新申请
				if(errorPropertyList.size() == propertyList.size()){
					dataMsg = getEmptyPropertyTip(errorPropertyList);
					conn.rollback();
					return dataMsg;
				}
				
				// 启动流程,并指定流程变量,存申请人的ID,将申请单ID也作为流程变量存储并传递至activiti中business_key中
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("proposer", account);

				String objID = String.valueOf(voID);
				variables.put("objID", objID);

				ProcessInstance pi = ActivitiUtil.startupByKey(processKey, objID, variables);
				// 提交申请,完成任务
				Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
				if (task != null) {
					Map<String, Object> v = new HashMap<String, Object>();
					v.put("handlers", handlers);

					v.put("oriActualPosition", actualPosition);
					v.put("disposalKey", disposalKey);
					
					// 写入批注
					ActivitiUtil.addComment(account, "S", vo.getApplyBasis(), task.getId(), task.getProcessInstanceId());
					// 根据登录者的部门ID+暂存库管理员角色 ，得到处理人员列表
					taskService.complete(task.getId(), v);

				}
			}
			
			//当存在不符合流程的财物，给用户提示
			if(errorPropertyList.size() > 0){
				dataMsg = getErrorPropertyTip(errorPropertyList, topApplicationVO);
			}else{
				dataMsg = getSuccessTip(topApplicationVO);
			}
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
		}finally{
			jdbc.closeAll();
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("", e);
				}
			}
			conn = null;
		}
		
		return dataMsg;
	}
	
	private static void splitApplicationVO(Map<String, ApplicationVO> map, TProperty property, ApplicationVO parentApplication){
		 //处置方式：退还、没收、变卖、拍卖、发还、销毁，收缴国库
		String key = property.getDisposalKey();
		String actualPosition = property.getActualPosition();
		
		//将财物处置方式和财物的实际的组合作为分单的标准
		ApplicationVO app = map.get(key+actualPosition);
		if(app == null){
			app = new ApplicationVO();
			
			//设置未子申请单的流程key和流程的处置方式
			DisposalAndProcedure dpResult = new DisposalAndProcedure();
			Procedure procedure = new Procedure(ProcedureKey.PROCEDURE003.toString(), "财物处置流程");
			dpResult.setProcedure(procedure);
			Disposal disposal = new Disposal();
			disposal.setDescription(DisposalKey.valueOf(key).getCnStatus());
			disposal.setCode(key);
			dpResult.setDisposal(disposal);
			app.setDpResult(dpResult);
			
			//处置理由：所有的子申请单的处置理由都和父申请的一样
			if(StringUtils.isNotBlank(property.getDisposalReason())){
				app.setApplyBasis(parentApplication.getApplyBasis());
			}
			app.setParentId(parentApplication.getApplicationId());
			app.setApplicationName(parentApplication.getApplicationName());
			
			List<TProperty> pros = new ArrayList<TProperty>();
			pros.add(property);
			app.setPros(pros);
			
			map.put(key+actualPosition, app);
		}else{
			if(StringUtils.isNotBlank(property.getDisposalReason())){
				app.setApplyBasis(parentApplication.getApplyBasis());
			}
			app.getPros().add(property);
		}
		
		return;
	}
	
	/**
	 * 获取申请单列表（我的已办，我的未办）
	 * 
	 * @param accStr
	 * @param page 
	 * @return
	 */
	public static ListAndPager<ApplicationVO> queryForPage(Map<String, String[]> params, Pager page, String applicationStyle) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<ApplicationVO> list = new ListAndPager<ApplicationVO>();
		DbRequest dbRequest = null;
		String sql = "";
		Object[] objParams = null;
		try {
			if ("done".equals(applicationStyle)) {
				
				dbRequest = SqlParser.parse(SQL_QUERY_HI_TASK, params);
				sql = dbRequest.getSql();
				objParams = dbRequest.getParameters();
				list = jdbc.queryPage(Env.DS, sql, ApplicationVO.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
				
			} else {
				dbRequest = SqlParser.parse(SQL_QUERY_TASK, params);
				sql = dbRequest.getSql();
				objParams = dbRequest.getParameters();
				list = jdbc.queryPage(Env.DS, sql, ApplicationVO.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			}
			List<ApplicationVO> voList = list.getList();
			setTaskNameForApplication(voList);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			jdbc.closeAll();
		}
		return list;
	}

	/**
	 * 查询申请单详情
	 * @param id
	 * @return
	 */
	public static TApplication queryById(long id) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TApplication application = new TApplication();
		try {
			application = jdbc.queryForObject(Env.DS, SQL_QUERY_T_APPLICATION_BY_ID, TApplication.class, id);
			TCase tCase = TCasePropertyBiz.getByApplicationId(application.getId());
			application.settCase(tCase);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			jdbc.closeAll();
		}
		return application;
	}
	/**
	 * 查询子申请单列表
	 * @param id
	 * @return
	 */
	public static List<ApplicationVO> queryByParentId(long id) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<ApplicationVO> applyList = null;
		try {
			applyList = jdbc.query(Env.DS, SQL_QUERY_T_APPLICATION_BY_PARENTID, ApplicationVO.class, id);
			setTaskNameForApplication(applyList);
		} catch (Exception e) {
			log.error(e);
		} finally {
			jdbc.closeAll();
		}
		return applyList;
	}

	/**
	 * 根据申请单ID返回历史批注信息
	 * 
	 * @param applicationId
	 * @return
	 */
	public static List<Comment> findCommentByApplicationId(Long applicationId) {
		HistoricProcessInstance hpi = Env.historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(applicationId)).singleResult();
		List<Comment> commentList = Env.taskService.getProcessInstanceComments(hpi.getId());
		return commentList;
	}

	/**
	 * 实际审批
	 * @param request
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED) 
	public DataMsg handle(ApplicationVO app, HttpServletRequest request) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		
		DataMsg dataMsg = new DataMsg();

		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			
			//处理审批单
			finishTask(app, request, conn, jdbc);
			
			conn.commit();
			
			/******发消息*********//*
			if (!isEnd) {
				Env.messageService.SendMessageToMore("待办通知--" + DateTool.formateTime2String(new Date(), "MM-dd HH:mm"), "您有待办事务，<a href='" + request.getContextPath()
						+ "/applicationOrder/queryForApprovalPaper.do'>点击查看</a>", "1", handlers, EnvMessage.IS_RELATIVE.IS_RELATIVE.value());
			} else {
				
				String proposer = (String) Env.historyService.createHistoricVariableInstanceQuery().executionId(excId).variableName("proposer").singleResult().getValue();
				Env.messageService.SendMessage("审批完毕--" + DateTool.formateTime2String(new Date(), "MM-dd HH:mm"), "您的申请已经审批完毕，<a href='" + request.getContextPath()
						+ "/applicationOrder/queryForPage.do'>点击查看</a>", "1", proposer, EnvMessage.IS_RELATIVE.IS_RELATIVE.value());
			}*/
			
		} catch (ActivitiObjectNotFoundException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			throw new RuntimeException(e);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
		} finally {
			jdbc.closeAll();
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("", e);
				}
		}
		dataMsg.setState("Y");
		return dataMsg;
	}
	
	@Transactional(propagation=Propagation.REQUIRED) 
	public DataMsg batchHandle(List<ApplicationVO> apps, HttpServletRequest request) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		
		DataMsg dataMsg = new DataMsg();

		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			
			for(ApplicationVO app : apps){
				//批量处理审批单
				finishTask(app, request, conn, jdbc);
			}
			
			conn.commit();
			
		} catch (ActivitiObjectNotFoundException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			throw new RuntimeException(e);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
		} finally {
			jdbc.closeAll();
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("", e);
				}
		}
		dataMsg.setState("Y");
		return dataMsg;
	}
	
	public static void finishTask(ApplicationVO app,  HttpServletRequest request, Connection conn, JDBC jdbc) throws Exception{
		HttpSession session = request.getSession();
		Map<String, Object> loginForm = SessionBiz.getLoginForm(session);
		String account = SessionBiz.getAccount(session);
		
		String status = request.getParameter("status"); // 同意否
		String result = request.getParameter("result"); // 审批意见
		String taskId = app.getTaskId();   //任务id
		String procInstId = app.getProcInstId(); //流程实例id
		Long appId = app.getApplicationId();  //申请单id
		
		// 通过任务ID得到formKey的值
		TaskFormData formData = Env.formService.getTaskFormData(taskId);
		String formKey = formData.getFormKey();
		String flowname = formKey;
		Task task = Env.taskService.createTaskQuery().taskId(taskId).singleResult();
		if (StringUtils.isBlank(formKey)) {

			String defineId = task.getProcessDefinitionId();
			ProcessDefinition pd = Env.repositoryService.createProcessDefinitionQuery().processDefinitionId(defineId).singleResult();
			String processKey = pd.getKey();
			flowname = processKey + "_" + task.getTaskDefinitionKey();
		}

		// 从流程变量里取出moneyStatus的值
		String oriActualPosition = (String) Env.taskService.getVariable(taskId, "oriActualPosition");
		String disposalKey = (String) Env.taskService.getVariable(taskId, "disposalKey");

		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		extraMap.put("oriActualPosition", new String[] { oriActualPosition });
		extraMap.put("disposalKey", new String[] { disposalKey });
		
		extraMap = RequestUtils.addExtraParamToRequestParamMap(request.getParameterMap(), extraMap);

		Flownode flownode = FlownodeEngine.getFlownode(flowname, extraMap);
		log.info("flownode========" + flownode);
		//String departmentId = flownode.getDepartmentId();
		
		String propertyStatus = flownode.getPropertyStatus();
		String actualPosition = flownode.getActualPosition();
		String applicationStatus = flownode.getApplicationStatus();
		log.info("taskId1 = " + taskId);

		String handlers = ActivitiUtil.getHandlers(loginForm, flownode);
		//下个节点是不是结束节点
		boolean isEnd = ActivitiUtil.isEndEvent(task, extraMap);

		/*********审批时附带的业务逻辑****************/
		//appId = CommonUtil.isLong(applicationId, true);
		// 更新申请单状态
		jdbc.update(conn, "update t_application set status=? where id=?", applicationStatus,appId);
		if(isEnd){
			//查询是否有上级订单
			Long parentId = jdbc.queryForObject(Env.DS, "select a.id from t_application a join t_application b on a.id = b.parent_id where b.id = ?", Long.class, appId);
			if(parentId != null){
				//查询上级订单是否还有未完成的子单
				Long isRunningCount = jdbc.queryForObject(Env.DS, "select count(1) from t_application a join t_application b on a.id = b.parent_id join t_application_property ap on ap.application_id = b.id join t_property p on p.ID = ap.property_id where p.is_running = 'Y' and b.id = ?", Long.class, appId);
				//如果没有则将上级订单状态改为已实施
				if(isRunningCount <= 0){
					jdbc.update(conn, "update t_application set status='S' where id=?", parentId);
				}
			}
				
		}

		List<TProperty> propertyList = TPropertyBiz.queryByApplicationId(appId);
		
		
		for(TProperty pro : propertyList){
			TAppProTransLog apLog = new TAppProTransLog();
			apLog.setAccount(account);
			apLog.setActualPosition(actualPosition);
			apLog.setApplicationId(appId);
			apLog.setPropertyStatus(propertyStatus);
			apLog.setPropertyId(pro.getProId());
			
			//向t_application_propety表插入数据
			jdbc.saveObject(conn, apLog);
			//更新财物状态
			if(isEnd){
				jdbc.update(conn, "update t_property set status = ?, actual_position = ?, is_running = 'N' where id = ?", propertyStatus, actualPosition, pro.getProId());
			}else{
				jdbc.update(conn, "update t_property set status = ?, actual_position = ? where id = ?", propertyStatus, actualPosition, pro.getProId());
			}
			
			String isOut = request.getParameter("isOut");
			String isIn = request.getParameter("isIn");
			if(StringUtils.isNotBlank(isIn) || StringUtils.isNotBlank(isOut)){
				//更新出库入库时间和经办人
				//判断是否是入库
				if( "Y".equals(isIn) ){
					
					updatePropertyOperation(jdbc, conn, pro.getProId(), account, false);
					jdbc.update(conn, "update t_application set handler=?, handle_time=? where id=?", account, new Date(),appId);
					
				//判断是否是出库操作	
				}else if( "Y".equals(isOut) ){
					//以下是针对出库的处理
					updatePropertyOperation(jdbc, conn, pro.getProId(), account, true);
					//出库需要解绑库位
					unbindWareHouse(pro, jdbc, conn);
					jdbc.update(conn, "update t_application set handler=?, handle_time=? where id=?", account, new Date(),appId);
				}
			}
		}
		
		/**入库特殊处理*/
    	String proJson = request.getParameter("proJson");
    	if(!PropertyUtils.isProJsonBlank(proJson)){
			Type type = new TypeToken<List<TProperty>>(){}.getType();
			propertyList = JsonUtil.jsonToList(proJson, type);
			for(TProperty pro:propertyList){
				if(pro.getWarehouseId()!=null&&"Y".equals(status)){
					updateWareHouse(pro,jdbc,conn);
				}
			}
    	}
    	
    	Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("status", status);
		// 拾取任务
		Env.taskService.claim(taskId, account);
		// 存放下个节点的处理人
		variables.put("handlers", handlers);
		
		// 写入批注
		ActivitiUtil.addComment(account, status, result, taskId, procInstId);
		
		log.info("taskId1 = " + taskId);
    	// 完成任务
    	Env.taskService.complete(taskId, variables);
	}
	
	//入库修改库位信息
	private static void updateWareHouse(TProperty pro,JDBC jdbc,Connection conn) throws Exception{
		try {
			jdbc.update(conn, "update t_property set warehouse_id = ?,store_num = ?,storehouse_id = ? where id = ?", pro.getWarehouseId(),pro.getStoreNum(),pro.getStoreHouseId(), pro.getProId());
			TStorageLocation ts=jdbc.queryForObject(conn,"select * from t_storage_location where id = ?" , TStorageLocation.class,pro.getWarehouseId());
			if(ts!=null){
				String inventoryStatus="E";
				Long newNum=ts.getGoodsNum()+pro.getStoreNum();
				if(newNum.longValue()==ts.getDepositNum().longValue()){
					inventoryStatus="F";
				}else if(newNum.longValue()<ts.getDepositNum().longValue()){
					inventoryStatus="E";
				}
				jdbc.update(conn, "update t_storage_location set inventory_status = ?,goods_num=? where id = ?",inventoryStatus,newNum, pro.getWarehouseId());
			}
		} catch (SQLException e) {
			log.error("", e);
		}
	}
	//出库解绑库位
	private static void unbindWareHouse(TProperty pro,JDBC jdbc,Connection conn) throws Exception{
		try {
			jdbc.update(conn, "update t_property set warehouse_id = null,store_num=0,storehouse_id=0 where id = ?", pro.getProId());
			TStorageLocation ts=jdbc.queryForObject(conn,"select * from t_storage_location where id = ?" , TStorageLocation.class,pro.getWarehouseId());
			if(ts!=null){
				Long newNum=0l;
				if(pro.getStoreNum()!=null){
					newNum=ts.getGoodsNum()-pro.getStoreNum();
				}
				String inventoryStatus="E";
				if(newNum.longValue()==0){
					inventoryStatus="U";
				}else if(newNum.longValue()>0){
					inventoryStatus="E";
				}
				jdbc.update(conn, "update t_storage_location set inventory_status = ?,goods_num=? where id = ?",inventoryStatus,newNum, pro.getWarehouseId());
			}
		} catch (SQLException e) {
			log.error("", e);
		}
	}
		
	/**
	 * 查找出入库申请单
	 * @param params
	 * @param page
	 * @return
	 */
	public static ListAndPager<ApplicationVO> queryForInAndOut(Map<String, String[]> params, Pager page) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<ApplicationVO> list = new ListAndPager<ApplicationVO>();
		try {
			//入库申请列表根据状态查询
			String newSql=sqlQueryForInAndOut;
			String proInStatus=RequestUtils.getAttribute(params, "proInStatus", 0);
			if(StringUtils.isNotBlank(proInStatus)){
				int idx=sqlQueryForInAndOut.indexOf("order by");
				String strPre=sqlQueryForInAndOut.substring(0, idx);
				String strNext=sqlQueryForInAndOut.substring(idx);
				if("1".equals(proInStatus)){
					//已入库
					newSql=strPre+ "and ( pro.warehouse_id is not null) "+strNext;
				}else if("2".equals(proInStatus)){
					//未入库
					newSql=strPre+ "and ( pro.warehouse_id is null) "+strNext;
				}
			}
			DbRequest dbRequest = SqlParser.parse(newSql, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, ApplicationVO.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			List<ApplicationVO> voList = list.getList();
			setTaskNameForApplication(voList);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return list;
	}


	/**
	 * 根据申请单id查询下一个环节的处理人
	 * 
	 * @param applicationId
	 * @return
	 */
	public static String queryHandlersByAppId(long applicationId) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String handlersStr = "";
		try {
			ApplicationVO myInTemp = jdbc.queryForObject(Env.DS, SQL_QUERY_TASK_BY_APPID, ApplicationVO.class, applicationId);
			if (myInTemp != null) {
				handlersStr = (String) Env.taskService.getVariable(myInTemp.getTaskId(), "handlers");
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return handlersStr;
	}
	
	/**
	 * 根据businesskey查询已处理环节和未处理环节
	 * @param businessKey
	 */
	public static List<ProcessTrack> queryTrackByApplicationId(Long applicationId){
		
		List<HistoricTaskInstance> hisList = 
				Env.historyService.createHistoricTaskInstanceQuery()
				.processInstanceBusinessKey(String.valueOf(applicationId))
				.taskDeleteReason("completed").list();
		List<Comment> commentList = findCommentByApplicationId(applicationId);
		
		List<ProcessTrack> trackList = new ArrayList<ProcessTrack>();
		
		for(HistoricTaskInstance his : hisList){
				ProcessTrack t = new ProcessTrack();
				t.setUserTaskName(his.getName());
				t.setStartTime(his.getStartTime());
				t.setFinishTime(his.getEndTime());
				t.setHandlers(his.getAssignee());
				for(Comment comment : commentList){
					if(his.getId().equals(comment.getTaskId())){
						//因为getFullMessage部署在linux平台会返回乱码。先不用该方法。
						//t.setComment(comment.getFullMessage());
						t.setComment(((org.activiti.engine.impl.persistence.entity.CommentEntity)comment).getMessage());
					}
				}
				trackList.add(t);
		}
		
		return trackList;
	}
	
	/**
	 * 获取流程图的访问路径
	 * @param applicationId
	 * @return
	 */
	public static String getPicPathByApplicationId(Long applicationId){
		
		List<HistoricTaskInstance> hisList = 
				Env.historyService.createHistoricTaskInstanceQuery()
				.processInstanceBusinessKey(String.valueOf(applicationId))
				.listPage(0, 1);
		
		String picPath = "";
		if(hisList != null && hisList.size() > 0){
			picPath = hisList.get(0).getProcessDefinitionId();
			picPath = picPath.substring(0, picPath.indexOf(":")) + ".png";
		}
		
		return Env.ENV_PRO.getProperty("processimg_base_path")+ picPath;
	}
	
	public static DataMsg doIn(Long applicationId, PropertyVO pro){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		DataMsg dataMsg = new DataMsg();
		try {
			conn = Env.DS.getConnection();
			String currStatus = "";
			String actualPosition = "";
			
			if(PropertyStatusKey.DRZCK.toString().equals(pro.getStatus())){
				currStatus = PropertyStatusKey.YRZCK.toString();
				actualPosition = ActualPosition.ZCK.toString();
			}else if(PropertyStatusKey.DRZXK.toString().equals(pro.getStatus())){
				currStatus = PropertyStatusKey.YRZXK.toString();
				actualPosition = ActualPosition.ZXK.toString();
			}else{
				return Env.PRO_INTO_ILLEGAL;
			}
			jdbc.update(conn, "update t_application set status = 'S' where id = ?", applicationId);
			jdbc.update(conn, "update t_property x set x.status = ?, x.actual_position = ? where x.id exists (select property_id from t_application_property where application_id = ? )", currStatus, actualPosition,applicationId);
			
			conn.commit();
		} catch (Exception e) {
			log.error("", e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				log.error("", e1);
			}
			
			return Env.SYS_ERROR;
		}finally{
			jdbc.closeAll();
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					log.error("", e);
				}
			}
		}
		dataMsg.setState("Y");
		return dataMsg;
	}
	
	/**
	 * 
	 * @param processKey
	 * @param request
	 * @return
	 */
	private static DepartmentAndRoles getDepartmentAndRole(String processKey, HttpServletRequest request) {
		DepartmentAndRoles dar = new DepartmentAndRoles();
		Long departmentID = null;
		List<Long> roleIDs = null;
		Map<String, Object> loginForm =SessionBiz.getLoginForm(request.getSession());
		switch (processKey) {
		case "CommonPropertyInTemporary":
			departmentID = ((java.math.BigDecimal) loginForm.get("DEPARTMENT_ID")).longValue();
			roleIDs = Arrays.asList(3L);
			break;
		case "CommonPropertyInCenter":case "PropertyHandle":case "PropertyInvokeOutTemporary":
		case "PropertyInvokeOutCenter":case "PropertyInvokeIntoTemporary":case "PropertyInvokeIntoCenter":
		case "SpecialPropertyInCenter":
			departmentID = ((java.math.BigDecimal) loginForm.get("DEPARTMENT_ID")).longValue();
			roleIDs = Arrays.asList(2L);
			break;
		case "DoDestoryByBAMJTemporary":
			departmentID = ((java.math.BigDecimal) loginForm.get("DEPARTMENT_ID")).longValue();
			roleIDs = Arrays.asList(RoleIdKey.BADWLD.getRoleId());
			break;
		case "DoDestoryByBAMJCenter":
			departmentID = ((java.math.BigDecimal) loginForm.get("DEPARTMENT_ID")).longValue();
			roleIDs = Arrays.asList(RoleIdKey.BADWLD.getRoleId());
			break;
		case "DoDestoryByZAKMJ":
			departmentID = ((java.math.BigDecimal) loginForm.get("DEPARTMENT_ID")).longValue();
			roleIDs = Arrays.asList(RoleIdKey.ZAKLD.getRoleId());
			break;
		default:
			break;
		}
		dar.setDepartmentID(departmentID);
		dar.setRoleIDs(roleIDs);
		return dar;
	}

	/**
	 * 获取对应的activiti流程key
	 * 
	 * @param procedure
	 * @return
	 */
	private static String getProcessKey(Procedure procedure) {
		String processKey = "";
		switch (procedure.getCode()) {
		case "PROCEDURE001":
			processKey = "CommonPropertyInTemporary";
			break;
		case "PROCEDURE002":
			processKey = "CommonPropertyInCenter";
			break;
		case "PROCEDURE003":
			processKey = "PropertyHandle";
			break;
		case "PROCEDURE004":
			processKey = "PropertyInvokeOutTemporary";
			break;
		case "PROCEDURE005":
			processKey = "PropertyInvokeOutCenter";
			break;
		case "PROCEDURE006":
			processKey = "PropertyInvokeIntoTemporary";
			break;
		case "PROCEDURE007":
			processKey = "PropertyInvokeIntoCenter";
			break;
		case "PROCEDURE008":
			processKey = "SpecialPropertyInCenter";
			break;
		case "PROCEDURE009":
			processKey = "DoDestoryByBAMJTemporary";
			break;
		case "PROCEDURE010":
			processKey = "DoDestoryByBAMJCenter";
			break;
		case "PROCEDURE011":
			processKey = "DoDestoryByZAKMJ";
			break;
		default:
			break;
		}
		return processKey;
	}

	/**
	 * 根据流程code和任务节点id，来确定需要访问的viewName
	 * 
	 * @param procedure
	 * @return
	 */
	public static String getProcessKey(String procedureCode,String actTasKId) {
		String viewName = "";
		switch (procedureCode) {
		case "PROCEDURE001":
			if("usertask2".equals(actTasKId)){
			  viewName = "preIn";
			}else{
			  viewName = "intoApplicationPreHandle";
			}
			break;
		case "PROCEDURE002":
			if("usertask5".equals(actTasKId)){
			  viewName = "preIn";
			}else{
			  viewName = "moveApplicationPreHandle";
			}
			break;
		case "PROCEDURE003":
			if("usertask7".equals(actTasKId)||"usertask6".equals(actTasKId)){
			  viewName = "preOut";
			}else{
			  viewName = "handleApplicationPreHandle";
			}
			break;
		case "PROCEDURE004":
			if("usertask3".equals(actTasKId)){
			  viewName = "preOut";
			}else{
			  viewName = "invokeOutApplicationPreHandle";
			}
			break;
		case "PROCEDURE005":
			if("usertask3".equals(actTasKId)){
			  viewName = "preOut";
			}else{
			  viewName = "invokeOutApplicationPreHandle";
			}
			break;
		case "PROCEDURE006":
			if("usertask3".equals(actTasKId)){
			  viewName="preIn";
			}else{
			  viewName = "invokeOutApplicationPreHandle";
			}
			break;
		case "PROCEDURE007":
			if("usertask4".equals(actTasKId)){
			  viewName="preIn";
			}else{
			  viewName = "invokeOutApplicationPreHandle";
			}
			break;
		case "PROCEDURE008":
			if("usertask5".equals(actTasKId)){
			  viewName="preIn";
			}else{
			  viewName = "intoApplicationPreHandle";
			}
			break;
		case "PROCEDURE009":
			if("usertask3".equals(actTasKId)){
			  viewName = "preOut";
			}else{
			  viewName = "destoryApplicationPreHandle";
			}
			break;
		case "PROCEDURE010":
			if("usertask4".equals(actTasKId)){
			  viewName = "preOut";
			}else{
			  viewName = "destoryApplicationPreHandle";
			}
			break;
		case "PROCEDURE011":
			if("usertask4".equals(actTasKId)){
			  viewName = "preOut";
			}else{
			  viewName = "destoryApplicationPreHandle";
			}
			break;
		default:
			break;
		}
		return viewName;
	}

	private static PropertyStatusKey getPropertyStatusKey(Procedure procedure) {
		PropertyStatusKey psk = null;
		switch (procedure.getCode()) {
		case "PROCEDURE001":
			psk = PropertyStatusKey.SQRZCK;
			break;
		case "PROCEDURE002":
			psk = PropertyStatusKey.SQRZXK;
			break;
		case "PROCEDURE003":
			//psk = "PropertyHandle";
			break;
		case "PROCEDURE004":
			psk = PropertyStatusKey.SQDY;
			break;
		case "PROCEDURE005":
			psk = PropertyStatusKey.SQDY;
			break;
		case "PROCEDURE006":
			psk = PropertyStatusKey.SQDYGH;
			break;
		case "PROCEDURE007":
			psk = PropertyStatusKey.SQDYGH;
			break;
		case "PROCEDURE008":
			psk = PropertyStatusKey.SQRZXK;
		case "PROCEDURE009":
			psk = PropertyStatusKey.SQXH;
			break;
		case "PROCEDURE010":
			psk = PropertyStatusKey.SQXH;
			break;
		case "PROCEDURE011":
			psk = PropertyStatusKey.SQXH;
			break;
		default:
			break;
		}
		return psk;
	}
	
	private static String getHandlePropertyStatus(String disposalKey) {
		switch(disposalKey){
		case "TH":
			return PropertyStatusKey.SQTH.toString();
		case "MS":
			return PropertyStatusKey.SQMS.toString();
		case "PM":
			return PropertyStatusKey.SQPM.toString();
		case "FH":
			return PropertyStatusKey.SQFH.toString();
		case "XH":
			return PropertyStatusKey.SQXH.toString();
		case "SJGK":
			return PropertyStatusKey.SQSJGK.toString();
		case "BM":
			return PropertyStatusKey.SQBM.toString();
		default:
			return null;
		}
	}
	
	public static void updatePropertyOperation(JDBC jdbc, Connection conn, String proId, String account, boolean isOut) throws Exception{
		Integer count = jdbc.queryForObject(conn, "select count(1) from t_property_operation where property_id = ?", Integer.class, proId);
		Object[] params = new Object[]{new Date(), account, proId};
		if(count > 0){
			if(isOut){
				jdbc.update(conn, "update t_property_operation set out_house_time = ?, out_house_person = ? where property_id = ?", params);
			}else{
				jdbc.update(conn, "update t_property_operation set into_house_time = ?, into_house_person = ? where property_id = ?", params);
			}
		}else{
			if(isOut){
				jdbc.update(conn, "insert into t_property_operation(out_house_time, out_house_person,property_id) values(?, ?, ?)", params);
			}else{
				jdbc.update(conn, "insert into t_property_operation(into_house_time, into_house_person,property_id) values(?, ?, ?)", params);
			}
			
		}
		
	}
	
	private static void saveAttachment(ApplicationVO vo, JDBC jdbc, Connection conn) throws SQLException{
		int priority = 0;
		List<Object> params = null;
		if(vo.getAttachment() != null){
			for(TAttachment att : vo.getAttachment()){
				params = new ArrayList<Object>();
				params.add(att.getType());
				params.add(att.getUploadUrl());
				params.add(vo.getAccount());
				params.add(priority);
				params.add(att.getAttaName());
				params.add(vo.getApplicationId());
				params.add(att.getDescription());
				jdbc.update(conn, "insert into t_attachment(type , upload_url, creator,  priority, name, apply_id , description) values(?, ?, ?, ?, ?, ?, ?)", params.toArray());
				
				priority++;
			}
		}
	}
	
	private static DataMsg getErrorPropertyTip(List<TProperty> errorPropertyList, ApplicationVO applicationVO){
		DataMsg dataMsg = new DataMsg();
		dataMsg.setState("Y");
		
		StringBuilder builder = new StringBuilder("申请提交成功，申请单号："+applicationVO.getApplicationNo()+"\n");
		builder.append("以下财物已被其他人申请了，系统已自动除外：\n");
		for(TProperty e : errorPropertyList){
			builder.append("财物编号："+e.getProNo() + ", 财物名称：" + e.getProName() + ";\n");
		}
		dataMsg.setMsg(builder.toString());
		dataMsg.setObj(applicationVO);
		return dataMsg;
	}
	
	private static DataMsg getSuccessTip(ApplicationVO applicationVO){
		DataMsg dataMsg = new DataMsg();
		dataMsg.setState("Y");
		dataMsg.setMsg("申请提交成功，申请单号：" + applicationVO.getApplicationNo() + "\n");
		dataMsg.setObj(applicationVO);
		
		return dataMsg;
	}

	private static DataMsg getEmptyPropertyTip(List<TProperty> errorPropertyList){
		DataMsg dataMsg = new DataMsg();
		
		dataMsg.setState("F");
		dataMsg.setMsg("选中财物已全部被申请，请重新选择财物");
		dataMsg.setObj(errorPropertyList);
		return dataMsg;
	}
	/**
	 * 给出关于不符合规则财物信息的提示
	 * @param procedureCode
	 * @param illegalProperties
	 * @return
	 */
	public static DataMsg getIllegalPropertyTip(String procedureCode, List<TProperty> illegalProperties){
		DataMsg dataMsg = new DataMsg();
		dataMsg.setState("F");
		String cnProcedure ="";
		if(ProcedureKey.PROCEDURE001.toString().equals(procedureCode)){
			cnProcedure = "入保管室保管,请直接入中心库";
		}else if(ProcedureKey.PROCEDURE008.toString().equals(procedureCode)){
			cnProcedure = "直接入中心库，请先入保管室保管";
		}else{
			cnProcedure = Env.PROOPERATETYPE.get(procedureCode);
		}
		
		StringBuilder builder = new StringBuilder("以下财物不能"+cnProcedure+":\n");
		for(TProperty property : illegalProperties){
			builder.append("财物编号："+ property.getProNo() + ";财物名称："+property.getProName()+"\n");
		}
		dataMsg.setMsg(builder.toString());
		dataMsg.setObj(illegalProperties);
		return dataMsg;
	}
	
	private static void setTaskNameForApplication(List<ApplicationVO> applyList){
		if(applyList != null){
			for(ApplicationVO vo : applyList){
				Map<String, String> map = ActivitiUtil.getPreAndCurrTaskName(vo.getProcDefId(), vo.getProcInstId());
				vo.setPreTaskName(map.get("preTaskName"));
				if(map.get("currTaskName") == null && map.get("preTaskName") != null){
					vo.setCurrTaskName("已结束");	
				}else{
					vo.setCurrTaskName(map.get("currTaskName"));	
				}
				
			}
		}
	}
	
}
