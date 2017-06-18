package cn.cellcom.szba.activiti;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring.xml",
		"classpath:activiti.xml"
	})
public class TestActiviti {

	@Resource
	private RepositoryService repositoryService;
	
	@Test
	public void initDeploy(){
		/*repositoryService.createDeployment()
		.addClasspathResource("diagrams/CommonPropertyInCenter.bpmn")
		.addClasspathResource("diagrams/CommonPropertyInCenter.png")
		.name("一般财物入中心库")
		.deploy();*/
		/*repositoryService.createDeployment()
		.addClasspathResource("diagrams/CommonPropertyInTemporary.bpmn")
		.addClasspathResource("diagrams/CommonPropertyInTemporary.png")
		.name("一般财物入暂存库")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/DoDestroy.bpmn")
		.addClasspathResource("diagrams/DoDestroy.png")
		.name("特殊财物集中销毁")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/PropertyHandle.bpmn")
		.addClasspathResource("diagrams/PropertyHandle.png")
		.name("财物处置流程")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/PropertyInvokeIntoCenter.bpmn")
		.addClasspathResource("diagrams/PropertyInvokeIntoCenter.png")
		.name("财物调用归还中心库")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/PropertyInvokeIntoTemporary.bpmn")
		.addClasspathResource("diagrams/PropertyInvokeIntoTemporary.png")
		.name("财物调用归还暂存库")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/PropertyInvokeOutTemporary.bpmn")
		.addClasspathResource("diagrams/PropertyInvokeOutTemporary.png")
		.name("财物调用出暂存库")
		.deploy();*/
		
		/*repositoryService.createDeployment()
		.addClasspathResource("diagrams/PropertyInvokeOutCenter.bpmn")
		.addClasspathResource("diagrams/PropertyInvokeOutCenter.png")
		.name("财物调用出中心库")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/SpecialPropertyInCenter.bpmn")
		.addClasspathResource("diagrams/SpecialPropertyInCenter.png")
		.name("特殊财物入中心库流程")
		.deploy();*/
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/DoDestoryByBAMJTemporary.bpmn")
		.name("办案民警集中销毁（暂存库）")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/DoDestoryByBAMJCenter.bpmn")
		.name("办案民警集中销毁（中心库）")
		.deploy();
		
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/DoDestoryByZAKMJ.bpmn")
		.name("治安科民警集中销毁")
		.deploy();
	}
	
	
	//@Test
	public void deleDeployByDeployId(){
		try {
			repositoryService.deleteDeployment("722501", true);
			
			System.out.println("删除流程完成");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("删除失败");
		}
		
		
	}
}
