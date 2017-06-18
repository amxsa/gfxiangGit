package cn.cellcom.szba.common;

import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.web.context.WebApplicationContext;

import cn.cellcom.szba.service.MessageService;
import cn.open.db.JDBC;

public class SpringWebApplicataionContext {
	private static WebApplicationContext context = null;

	private static String DATASOURCE_NAME = "dataSource";

	private static DataSource dataSource = null;

	public static WebApplicationContext getContext() {
		return context;
	}

	public static void setContext(WebApplicationContext context) {
		SpringWebApplicataionContext.context = context;
	}

	public static DataSource getDataSource() {
		if (context == null && dataSource != null)
			return dataSource;
		return (DataSource) context.getBean(DATASOURCE_NAME);
	}

	public static void setDataSource(DataSource dataSource) {
		SpringWebApplicataionContext.dataSource = dataSource;
	}

	public static JDBC getJdbc() {
		return (JDBC) context.getBean("jdbc");
	}

	/*
	 * 工作流相关
	 */
	public static ProcessEngine getProcessEngine() {
		return (ProcessEngine) context.getBean("processEngine");
	}

	public static RepositoryService getRepositoryService() {
		return (RepositoryService) context.getBean("repositoryService");
	}

	public static RuntimeService getRuntimeService() {
		return (RuntimeService) context.getBean("runtimeService");
	}

	public static FormService getFormService() {
		return (FormService) context.getBean("formService");
	}

	public static TaskService getTaskService() {
		return (TaskService) context.getBean("taskService");
	}

	public static HistoryService getHistoryService() {
		return (HistoryService) context.getBean("historyService");
	}

	public static IdentityService getIdentityService() {
		return (IdentityService) context.getBean("identityService");
	}

	public static ManagementService getManagementService() {
		return (ManagementService) context.getBean("managementService");
	}
	
	//消息接口
	public static MessageService getMessageService() {
		return (MessageService) context.getBean("messageService");
	}
	
}
