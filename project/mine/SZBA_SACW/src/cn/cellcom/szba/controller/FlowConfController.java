package cn.cellcom.szba.controller;



import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.common.Env;

@Controller
@RequestMapping("/flowconf")
public class FlowConfController {
	
	private static Log log = LogFactory.getLog(FlowConfController.class);
	 
	@RequestMapping("/flowList")
	public String flowList(HttpServletRequest request){
		List<ProcessDefinition> list = Env.repositoryService.createProcessDefinitionQuery().list();
        request.setAttribute(Env.RESULT, list);
		return "/jsp/flow/flowList";
	}

	@RequestMapping("deleteFlow")
	public String deleteFlow(HttpServletRequest request) {
		String deploymentId=request.getParameter("deploymentId");
		log.info(deploymentId+">>>>>>");
		Env.repositoryService.deleteDeployment(deploymentId, true);
		return flowList(request);
	}
	
	/**
	 * 流程详情
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("detailFlow")
	public String detailFlow(HttpServletRequest request) throws IOException {
		String key=request.getParameter("key");
		
		//查出流程实例列表
		List<ProcessInstance> processInstanceList = Env.runtimeService.createProcessInstanceQuery().processDefinitionKey(key).list();

		//查出流程任务列表
		List<Task> taskList = Env.taskService.createTaskQuery().processDefinitionKey(key).orderByTaskCreateTime().desc().list();
		
		request.setAttribute("processInstanceList",processInstanceList );
		request.setAttribute("taskList",taskList );
		    
		//返回页面显示 
		return "/jsp/flow/flowDetail";
	}	 
	
	/**
	 * 查看流程图
	 * @param request
	 * @return
	 */
	@RequestMapping("imageFlow")
	public String imageFlow(HttpServletRequest request){
		String key=request.getParameter("key");
		
				//查询部署编号
				List<ProcessDefinition> dlist = Env.repositoryService.createProcessDefinitionQuery().list();
				String deploymentId="";
				for (int i = 0; i < dlist.size(); i++) {
					if(key.equals(dlist.get(i).getKey())){
						deploymentId=dlist.get(i).getDeploymentId();
						break;
					}
				}
				
				//查看流程图
				List<String> list=Env.repositoryService.getDeploymentResourceNames(deploymentId);
				
				String resourceName="";
				if(list!=null&&list.size()>0){
					for (String name : list) {
						if(name.indexOf(".png")>=0){
							resourceName=name;
						}
					}
				}
				
				InputStream imageStream=Env.repositoryService.getResourceAsStream(deploymentId, resourceName);
				
				request.setAttribute("inputStream", imageStream);
				
				return "/jsp/flow/flowImage";
	}
	
}

