package cn.cellcom.szba.service.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.databean.DepartmentItem;
import cn.cellcom.szba.databean.Flownode;
import cn.open.db.JDBC;
import cn.open.util.ArrayUtil;
import cn.open.util.CommonUtil;

public class ActivitiUtil {

	private static Log log = LogFactory.getLog(ActivitiUtil.class);
	
	private static ProcessEngine pe = SpringWebApplicataionContext
			.getProcessEngine();

	/**
	 * 部署流程
	 * 
	 * @param bpmn
	 * @param png
	 */
	public static Deployment deploy(String bpmn, String png) {
		if (StringUtils.isBlank(bpmn) || StringUtils.isBlank(png)) {
			throw new RuntimeException("");
		}
		Deployment deployment = pe.getRepositoryService().createDeployment()
				.addClasspathResource(bpmn).addClasspathResource(png).deploy();
		return deployment;
	}

	/**
	 * 启动流程
	 * 
	 * @param key
	 *            根据指定的key
	 * @return
	 */
	public static ProcessInstance startupByKey(String key) {
		// key 对应bpmn文件中的<process id="myProcess"中id的值
		ProcessInstance processInstance = pe.getRuntimeService()
				.startProcessInstanceByKey(key);
		return processInstance;
	}

	/**
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	public static ProcessInstance startupByKey(String key, String businessID,
			Map<String, Object> map) {
		// key 对应bpmn文件中的<process id="myProcess"中id的值
		ProcessInstance processInstance = pe.getRuntimeService()
				.startProcessInstanceByKey(key, businessID, map);
		return processInstance;
	}
	
	
	/**
	 * 根据条件返回对应的处理人列表,以逗号隔开。
	 * @param departmentID
	 * @param roleIDs
	 * @return
	 * @throws Exception
	 */
	public static String getHandlers(List<Long> deptIds, List<Long> roleIDs)
			throws Exception {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		StringBuilder sb = new StringBuilder(
				"select distinct a.account_id from t_role_account a,t_account b where a.account_id=b.account");

		if (ArrayUtil.isNotEmpty(roleIDs)) {
			StringBuilder s = new StringBuilder();
			for (Long roleID : roleIDs) {
				CommonUtil.makeupArrayStr(s, roleID, ",");
			}
			sb.append(" and a.role_id in (").append(s.toString()).append(") ");
		}
		if (ArrayUtil.isNotEmpty(deptIds)) {
			StringBuilder s = new StringBuilder();
			for (Long deptId : deptIds) {
				CommonUtil.makeupArrayStr(s, deptId, ",");
			}
			sb.append(" and b.department_id in (").append(s.toString()).append(") ");
		}
	/*	if (departmentID != null) {
			sb.append(" and b.department_id=?");
		}

		List<Object> params = new ArrayList<Object>();
		if (departmentID != null) {
			params.add(departmentID);
		}*/
		List<String> accountIDList = jdbc.query(Env.DS, sb.toString(),
				String.class);
		
		StringBuilder sBuilder=new StringBuilder();
		if(ArrayUtil.isNotEmpty(accountIDList)) {
			for(String acocuntID:accountIDList) {
				CommonUtil.makeupArrayStr(sBuilder, acocuntID, ",");
			}
		}
		return sBuilder.toString();
	}
	
	public static String getHandlers(Map<String, Object> loginForm, Flownode flownode) throws Exception {
		if(StringUtils.isBlank(flownode.getRoles())) {
			return "";
		}
		//Long departmentID=null;
		List<Long> deptIds = null;
		if(flownode.isSameAsCurrentDept()) {
			
		//if(StringUtils.isNotBlank(departmetnId)) {
			//departmentID=((BigDecimal)loginForm.get(departmetnId)).longValue();
			Long departmentID=((BigDecimal)loginForm.get("DEPARTMENT_ID")).longValue();
			deptIds = new ArrayList<Long>();
			deptIds.add(departmentID);
		}else{
			deptIds = getHandleDept(flownode.getDepartments());
		}
		List<Long> roleIDs=ArrayUtil.convert(StringUtils.split(flownode.getRoles(), ","), Long.class);
		return getHandlers(deptIds,roleIDs);
	}
	
	/**
	 * 获取当前任务的下个非网关节点
	 * @param task   当前任务
	 * @param params 判断流程流向的参数
	 * @return
	 * @throws Exception
	 */
	public static  PvmActivity getNext(Task task,Map<String, String[]> params) throws Exception {
		ProcessDefinitionEntity def = 
				(ProcessDefinitionEntity)((RepositoryServiceImpl)Env.repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());
		ProcessInstance pi = Env.runtimeService.createProcessInstanceQuery()//
				.processInstanceId(task.getProcessInstanceId())//使用流程实例ID查询
				.singleResult();
		
		ActivityImpl activityImpl = def.findActivity(pi.getActivityId());

		System.out.println("当前任务"+activityImpl.getProperty("name")); //输出某个节点的某种属性 
		List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();//获取从某个节点出来的所有线路
		
		for(PvmTransition tr:outTransitions){
			String express=(String)tr.getProperty("conditionText");
			
			PvmActivity ac = tr.getDestination();
			
			if(express != null){
				PvmActivity pvm = getPvm(ac, express, params);
				if(pvm!= null){
					return pvm;
				}
			}else{
				if(ac.isExclusive()){
					List<PvmTransition> outgoingTransitions = ac.getOutgoingTransitions();
					for(PvmTransition excPv : outgoingTransitions){
						PvmActivity pvm = getPvm(excPv.getDestination(), (String)excPv.getProperty("conditionText"), params);
						if(pvm != null){
							return pvm;
						}
					}
				}
			}
			
		} 
	
		
		return null;
	}

	public static Map<String, String> getPreAndCurrTaskName(String procDefId, String procInsId){
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isBlank(procDefId)){
			return map;
		}
		ProcessDefinitionEntity def = 
				(ProcessDefinitionEntity)((RepositoryServiceImpl)Env.repositoryService)
				.getDeployedProcessDefinition(procDefId);
		
		ProcessInstance pi = Env.runtimeService.createProcessInstanceQuery()//
				.processInstanceId(procInsId)//使用流程实例ID查询
				.singleResult();
		
		List<ActivityImpl> activities = def.getActivities();
		
		String preTaskName = "";
		if(pi != null && activities != null){
			ActivityImpl currActivity = def.findActivity(pi.getActivityId());
			map.put("currTaskName", currActivity.getProperty("name").toString());
			
			int index = activities.lastIndexOf(currActivity);
			ActivityImpl parentActivity = null;
			if(index > 0){
				parentActivity = activities.get(index-1);
			}
			
			if(parentActivity != null){
				preTaskName = (String) parentActivity.getProperty("name");
			}
		}else{
			preTaskName = (String) activities.get(activities.size()-3).getProperty("name");
		}
		map.put("preTaskName", preTaskName);
		return map;
	}
	/**
	 * 判读是否为结束节点
	 * @param task  当前任务
	 * @param params 判断流程流向的参数
	 * @return
	 */
	public static boolean isEndEvent(Task task,Map<String, String[]> params){
		try {
			PvmActivity next = null;
			next = getNext(task, params);
			
			List<PvmTransition> out = next.getOutgoingTransitions();
			
			if(out.size() <= 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("找不到下个流程节点");
		}
		return false;
	}
	
	/**
	 * 
	 * @param ac 指定流程连线的下个节点
	 * @param express 判断表达式
	 * @param params  判断流程流向的参数
	 * @return
	 */
	public static PvmActivity getPvm(PvmActivity ac, String express, Map<String, String[]>params){
		
		express=express.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\$", "").replaceAll("outcome", "status");
		//express = express.replace("outcome", "status");
		try {
			if(FlownodeEngine.runCondition(express,params)) {
				return ac;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static List<Long> getHandleDept(List<DepartmentItem> depts) {
		List<Long> deptIds = null;
		Connection con =  null;
		try {
			con = Env.DS.getConnection();
			CallableStatement  pstmt = null; 
			String sql = "{call sp_get_department(?,?)}"; 
			
			pstmt = con.prepareCall(sql); 
			
			oracle.sql.ArrayDescriptor desc = oracle.sql.ArrayDescriptor.createDescriptor("ARRAY_DEPARTMENT_CONDITIONS",con);
			StructDescriptor structDesc = StructDescriptor.createDescriptor("TYPE_DEPARTMENT_CONDITION", con);
			ArrayList<STRUCT> pstruct = new ArrayList<STRUCT>();
			
			Object[] object1=null; 
			STRUCT struct = null;
			if(depts != null){
				for (DepartmentItem item : depts){ 
					object1 = new Object[2];
					object1[0] = item.getDepartmentId();
					object1[1] = item.getIsRecursion()?'1':'0';
					struct = new STRUCT(structDesc, con, object1);
				    pstruct.add(struct);
				}  
				ARRAY array = new ARRAY(desc,con,pstruct.toArray());
			      
				pstmt.setArray(1, array); 
				pstmt.registerOutParameter(2, OracleTypes.CURSOR);
				
				pstmt.executeUpdate();
				
				ResultSet rs = (ResultSet)pstmt.getObject(2);
				deptIds = new ArrayList<Long>();
				while(rs.next()){
				 	deptIds.add(rs.getLong(1));
				}
			}
			
			
		} catch (Exception e) {
			log.error("", e);
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					log.error("", e);
				}
			}
		}
        
        return deptIds != null ? deptIds : new ArrayList<Long>();
	}

	/**
	 * 写入批注,由于使用了activiti底层API写入批注.需要从session中取得当前登录人
	 * @param account 当前登录用户账号
	 * @param status 审批状态，Y表示同意， N表示不同意， S表示办案民警发起申请
	 * @param result 审批意见
	 * @param taskId 任务id
	 * @param procInstId 流程实例id
	 */
	public static void addComment(String account, String status, String result, String taskId, String procInstId){
		
		Authentication.setAuthenticatedUserId(account);
		if("Y".equals(status)){
			result = "【审批同意】 " + result; 
		}else if("N".equals(status)){
			result = "【审批不同意】 " + result; 
		}else if("S".equals(status)){
			result = "【发起申请】";
		}
		
		Env.taskService.addComment(taskId, procInstId, result);
	}
}
