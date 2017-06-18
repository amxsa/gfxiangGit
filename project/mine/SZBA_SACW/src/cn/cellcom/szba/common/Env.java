package cn.cellcom.szba.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import cn.cellcom.szba.domain.DataMsg;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TDepartment;
import cn.cellcom.szba.service.MessageService;
import cn.open.common.Environment;


public class Env {
	// 映射的是/conf/env.properties文件.在GlobalLisener中加载
	public static Properties ENV_PRO = null;
	
	public static List<String> WHITE_URL=new ArrayList<String>();
	//数据源
	public static DataSource DS = SpringWebApplicataionContext.getDataSource();
	
	//部门数据
	public static Map<Long,TDepartment> departmentMap=new HashMap<Long,TDepartment>();
	//账号数据 
	public static Map<String,TAccount> accountMap=new HashMap<String,TAccount>();
	
	//工作流相关
	public static ProcessEngine processEngine=SpringWebApplicataionContext.getProcessEngine();
	public static FormService formService=SpringWebApplicataionContext.getFormService();
	public static HistoryService historyService=SpringWebApplicataionContext.getHistoryService();
	public static IdentityService identityService=SpringWebApplicataionContext.getIdentityService();
	public static ManagementService managementService=SpringWebApplicataionContext.getManagementService();
	public static RepositoryService repositoryService=SpringWebApplicataionContext.getRepositoryService();
	public static RuntimeService runtimeService=SpringWebApplicataionContext.getRuntimeService();
	public static TaskService taskService=SpringWebApplicataionContext.getTaskService();
	
	//消息接口
	public static MessageService messageService=SpringWebApplicataionContext.getMessageService();

	//日期格式
	public static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
	//用于对oracle数据库date字段类型存在空值进行排序时，设置的最小时间
	public static final String MINTIME = "1970-01-01 00:00:00";  
	
	public static final String DATA_MSG="dataMsg";
	public static final String ERROR_VIEW="/common/error";
	public static final String OK_VIEW="/common/ok";
	
	public static final String TIMESTAMP="timestamp";
	public static final String AUTHSTRING="authstring";
	public static final String SESSION_ID="sessionID";
	
	
	//用户状态
	public static final String ACCOUNT_STATUS_Y="Y";
	
	//错误码
	public static final DataMsg SYS_ERROR=new DataMsg("F", "1","系统错误,请稍后重试");
	public static final DataMsg AUTHSTRING_ERROR=new DataMsg("F", "2","参数检验有误");
	public static final DataMsg REQUIRE_PARAM=new DataMsg("F", "3","缺少参数");
	public static final DataMsg NO_ACCOUNT_ERROR=new DataMsg("F", "4","账号不存在");
	public static final DataMsg BAD_ACCOUNT_ERROR=new DataMsg("F", "5","账号状态失效");
	public static final DataMsg ACCOUNT_PASSWORD_ERROR=new DataMsg("F", "8","密码错误");
	public static final DataMsg ACCOUNT_EXPIRE_ERROR=new DataMsg("F", "9","账号已过期");
	public static final DataMsg RESET_PASSWORD_ERROR=new DataMsg("F", "10","修改密码失败");
	public static final DataMsg DELETE_ERROR=new DataMsg("F", "11","删除失败");
	public static final DataMsg CASE_DELETE_ERROR=new DataMsg("F", "12","此案件不允许删除");
	public static final DataMsg POLICE_NO_NULL=new DataMsg("F", "13","警员信息不能为空");
	public static final DataMsg POLICE_INFO_LACK=new DataMsg("F", "14","警员信息不全");
	public static final DataMsg CIVI_INFO_LACK=new DataMsg("F", "15","民众信息不全");
	public static final DataMsg PRO_INFO_LACK=new DataMsg("F", "16","财物信息不全");
	public static final DataMsg PRO_INTO_ILLEGAL=new DataMsg("F", "17","财物不符合入库要求");
	
	
	//性别标识
	public static final String MALE = "M";
	public static final String FEMALE = "F";
	
	//缩略图分辨率
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	
	//证件类型
	//身份证
	public static final String IDENTITY_CARD = "IDCard";
	//护照
	public static final String PASSPORT = "passport";
	
	//分页
	//每页的条数
	//public static final Integer SIZE_PER_PAGE=Environment.SIZE_PER_PAGE;
	public static final Integer SIZE_PER_PAGE=5;
	
	//当前页数的请求参数名称
	public static final String currentIndex=Environment.currentIndex;
	//每页要显示多少条的请求参数名称
	public static final String sizePerPage=Environment.sizePerPage;
	
	public static final String RESULT = "result";
	public static final String LINK = "link";
	public static final String JS = "js";
	
	public final static int EXCCODE = 1;
	public final static Map<Integer, String> CODE_RESULT = new HashMap<Integer,String>();
	
	public static List<String> viewNameList=new ArrayList<String>();
	static {
		
		CODE_RESULT.put(1, "系统错误,请稍后重试");
		CODE_RESULT.put(0, "操作成功");
		//-999已定义成自定义异常代码EXCEPTION_CODE，不要再在此定义
		
	}

	public static String APPLICATIONNO = "APPLICATIONNO"; 
	
	public static ArrayList<String> rolesQueryAll = (ArrayList<String>) SpringWebApplicataionContext.getContext().getBean("rolesQueryAll");
	public static ArrayList<String> rolesQueryByDept = (ArrayList<String>) SpringWebApplicataionContext.getContext().getBean("rolesQueryByDept");
	public static ArrayList<String> handlerProperties = (ArrayList<String>) SpringWebApplicataionContext.getContext().getBean("handlerProperties");
	public static ArrayList<String> editProperties = (ArrayList<String>) SpringWebApplicataionContext.getContext().getBean("editProperties");
	public static HashMap<String, String> PROVIEWNAMEGROUP1 = (HashMap<String, String>) SpringWebApplicataionContext.getContext().getBean("proViewNameGroup1");
	public static HashMap<String, String> PROVIEWNAMEGROUP2 = (HashMap<String, String>) SpringWebApplicataionContext.getContext().getBean("proViewNameGroup2");
	public static HashMap<String, String> PROVIEWNAMEGROUP3 = (HashMap<String, String>) SpringWebApplicataionContext.getContext().getBean("proViewNameGroup3");
	public static HashMap<String, String> PROVIEWNAMEGROUP4 = (HashMap<String, String>) SpringWebApplicataionContext.getContext().getBean("proViewNameGroup4");
	public static HashMap<String, String> PROVIEWNAMEGROUP5 = (HashMap<String, String>) SpringWebApplicataionContext.getContext().getBean("proViewNameGroup5");
	public static HashMap<String, String> PROVIEWNAMEGROUP6 = (HashMap<String, String>) SpringWebApplicataionContext.getContext().getBean("proViewNameGroup6");
	//财物轨迹跟踪，财物操作类型
	public static HashMap<String, String> PROOPERATETYPE = (HashMap<String, String>) SpringWebApplicataionContext.getContext().getBean("proOperateType");
 	//案件、人员、笔录和清单类型常量的定义
	/**
	 * 案件
	 */
	public static final String TYPE_CASE = "0";
	/**
	 * 刑事案件
	 */
	public static String TYPE_CASE_CRIMINAL = "1";
	/**
	 * 行政案件
	 */
	public static String TYPE_CASE_ADMINISTRATION = "2";
	/**办案民警
	 */
	public static final String TYPE_POLICE = "14";
	/**侦查员
	 */
	public static final String TYPE_INVESTIGATOR = "15";
	/**记录员
	 */
	public static final String TYPE_RECORDER = "16";
	/**见证人
	 */
	public static final String TYPE_WITNESSES = "17";
	/**持有人
	 */
	public static final String TYPE_HOLDER = "18";
	/**保管人
	 */
	public static final String TYPE_KEEPER = "19";
	/**其他在场人员a
	 */
	public static final String TYPE_PERSONNELS = "20";
	/**其他在场人员a
	 */
	public static final String TYPE_EXTRACT_PERSON = "26";
	/**
	 * 财物
	 */
	public static final String TYPE_PROPERTY = "21";
	/**
	 * 电子物证提取登记表 
	 */
	public static final String TYPE_ELEC_RECORD = "23";
	/**
	 * 申请表 
	 */
	public static final String TYPE_APPLICATION = "24";
	/**
	 * 案结处置依据
	 */
	public static final String TYPE_CASE_DISPOSAL = "25";
	/**
	 * 所有人
	 */
	public static final String TYPE_OWNER = "32";
	/**
	 * 财物处置登记
	 */
	public static final String TYPE_HANDLE_RESULT = "33";
}
