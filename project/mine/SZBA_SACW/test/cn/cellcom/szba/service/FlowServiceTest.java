package cn.cellcom.szba.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.velocity.VelocityEngineUtils;

import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.enums.CategoryAttributeKey;
import cn.cellcom.szba.enums.PropertyStatusKeyOld;
import cn.cellcom.szba.enums.RoleKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring.xml",
		"classpath:spring-flow.xml"
	})
public class FlowServiceTest {
	
	@Autowired
	private FlowService flowService;
	@Autowired
	private VelocityEngine velocityEngine;

	@Test
	public void getDisposalAndProcessTest(){
		List<TRole> roles = new ArrayList<TRole>();
		TRole r = new TRole();
		r.setRoleKey(RoleKey.BAMJ.toString());
		roles.add(r);
		r = new TRole();
//		r.setRoleKey(RoleKey.ZAKMJ.toString());
//		roles.add(r);
		List<DisposalAndProcedure> list = flowService.getDisposalAndProcedure(roles, 
				CategoryAttributeKey.YBCW.toString(), PropertyStatusKeyOld.ZXK.toString());
		for(DisposalAndProcedure dap : list){
			System.out.println(dap);
		}
	}
	
	@Test
	public void velocityTest(){
		
		String filePath = "velocity_rzck_template.vm";
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("name","张三");
		//param.put("phone", "13143770789");
		param.put("casename","2015/11/20特大案件");
		param.put("transactor","刘先生");
		param.put("proname","重型武器");
		String contents = generateTemplateString(filePath, param);
		String[] str = contents.split("-");
		String title = str[0].replaceAll("title：","");
		String content = str[1].replaceAll("content：","");
		System.out.println("titie="+title);
		System.out.println("content="+content);
	
	}
	
	private String generateTemplateString(String templateFilePath, Map<String, Object> model){
		String content = null;
		try{
//			VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
////			bean.setResourceLoaderPath("E:/workspaces/ws3/SZBA_SACW/template/");
//			bean.setPreferFileSystemAccess(true);
//			Properties velocityProperties = new Properties();
//			velocityProperties.setProperty("input.encoding", "UTF-8");
//			velocityProperties.setProperty("output.encoding", "UTF-8");
//			bean.setVelocityProperties(velocityProperties);
//			VelocityEngine velocityEngine = bean.createVelocityEngine();
			String templatePath = "" + templateFilePath;
		
			content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templatePath, "UTF-8", model);
		} catch(Exception e){
		}
		return content;
	}

}
