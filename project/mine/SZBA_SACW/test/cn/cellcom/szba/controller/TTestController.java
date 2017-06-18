package cn.cellcom.szba.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cellcom.szba.biz.TCategoryBiz;
import cn.cellcom.szba.biz.TIdHolderBiz;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.common.utils.IdHolderUtils;
import cn.cellcom.szba.common.utils.XmlConvertUtil;
import cn.cellcom.szba.databean.CategoryAttribute;
import cn.cellcom.szba.databean.Disposal;
import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.databean.Flownode;
import cn.cellcom.szba.databean.Flownodes;
import cn.cellcom.szba.databean.Procedure;
import cn.cellcom.szba.domain.IdHolder;
import cn.cellcom.szba.domain.TCategory;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.service.FlowService;
import cn.cellcom.szba.service.TestService;
import cn.open.web.ServletUtil;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/test")
public class TTestController {
	
	@Autowired
	private TestService testService;
	@Autowired
	private FlowService flowService;
	
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ProcessEngine processEngine ;
	
	@RequestMapping("importCategory")
	public void importCategory(HttpServletRequest requ){
		try {
			Workbook rwb = null;
			Cell cell = null;
			// 创建输入流
			InputStream stream = new FileInputStream("E:\\data\\excel\\category.xls");

			// 获取Excel文件对象
			rwb = Workbook.getWorkbook(stream);

			// 获取文件的指定工作表 默认的第一个
			Sheet sheet = rwb.getSheet(0);

			// 行数(表头的目录不需要，从1开始)
			for (int i = 1; i < sheet.getRows(); i++) {

				cell = sheet.getCell(0, i);
				String name=cell.getContents();
				cell = sheet.getCell(1, i);
				String parent=cell.getContents();
				cell = sheet.getCell(2, i);
				int levels=Integer.parseInt(cell.getContents());
				if(levels==1){
					TCategory category=new TCategory();
					category.setName(name);
					category.setLevels(levels);
					category.setParentId(1l);
					category.setValidStatus("Y");
					TCategoryBiz.save(category);
					System.out.println("name="+name+";parent="+parent+";level="+levels);
				}
				/*if(levels==2){
					TCategory category=new TCategory();
					category.setName(name);
					category.setLevels(levels);
					category.setValidStatus("Y");
					Long pid=1l;
					List<TCategory> categoryList=TCategoryBiz.findCategoryByName(parent);
					if(categoryList!=null&&!categoryList.isEmpty()){
						TCategory p=categoryList.get(0);
						pid=p.getId();
						System.out.println("name="+name+";pid="+p.getId()+";pname="+p.getName());
					}
					category.setParentId(pid);
					TCategoryBiz.save(category);
				}*/
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping("toCategory")
	public String toCategory(HttpServletRequest requ){
		Map<String, Disposal> disposalMapper = testService.getDisposalMapper();
		List<Disposal> disposalList = new ArrayList<Disposal>();
		for(String key : disposalMapper.keySet()){
			disposalList.add(disposalMapper.get(key));
		}
		requ.setAttribute("disposal", disposalList);
		
		Map<String, Procedure> procedureMapper = testService.getProcedureMapper();
		List<Procedure> procedureList = new ArrayList<Procedure>();
		for(String key : procedureMapper.keySet()){
			procedureList.add(procedureMapper.get(key));
		}
		requ.setAttribute("procedure", procedureMapper);
		
		Map<String, CategoryAttribute> categoryAttributeMapper = testService.getCategoryAttributeMapper(); 
		List<CategoryAttribute> categoryAttributeList = new ArrayList<CategoryAttribute>();
		for(String key : categoryAttributeMapper.keySet()){
			categoryAttributeList.add(categoryAttributeMapper.get(key));
		}
		requ.setAttribute("categoryAttribute", categoryAttributeList);
		
		Map<String, TRole> roleMapper = testService.getRoleMapper(); 
		List<TRole> roleList = new ArrayList<TRole>();
		for(String key : roleMapper.keySet()){
			roleList.add(roleMapper.get(key));
		}
		requ.setAttribute("role", roleList);
		
		return "/test/categoryAttribute";
	}
	
	@RequestMapping("getDisposalAndProcedure")
	public void getDisposalAndProcedure(HttpServletRequest request,
			HttpServletResponse response){
		String categoryId = request.getParameter("categoryId");
		String status = request.getParameter("status");
		String role = request.getParameter("role");
		List<TRole> roleList = new ArrayList<TRole>();
		TRole r = new TRole();
		r.setRoleKey(role);
		roleList.add(r);
		List<DisposalAndProcedure> list = flowService.getDisposalAndProcedure(roleList, categoryId, status);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", list);
		JSONObject json = JSONObject.fromObject(data);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deploy")
	public String deploy(){
		repositoryService.createDeployment()
		.addClasspathResource("diagrams/SpecialPropertyInCenter.bpmn")
		.addClasspathResource("diagrams/SpecialPropertyInCenter.png")
		.name("特殊财物入中心库流程")
		.deploy();
		return Env.OK_VIEW;
	}
	@RequestMapping("/deleteDeployment")
	public String deleteDeployment(){
		repositoryService.deleteDeployment("162501",true);
		return "";
	}
	
	@RequestMapping("/generateProcessXml")
	public void generateProcessXml(HttpServletRequest request, HttpServletResponse response){//, String jsonStr, String fileName, String dirPath){
		String[] fullStrArguments = new String[3];
		Writer writer = null;
		try {
			String fullStr = ServletUtil.getRequestBody(request);
			if(fullStr!=null){
				fullStrArguments = fullStr.split("#");
			}
			if(fullStrArguments.length != 3){
				PrintTool.print(response, "error", "html");
				return;
			}
			String jsonStr = fullStrArguments[0];
			String fileName = fullStrArguments[1];
			String dirPath = fullStrArguments[2];
			
			Type type = new TypeToken<List<Flownode>>(){}.getType();		
			JsonUtil.jsonToList(jsonStr, type);
			
			List<Flownode> flownodeList = JsonUtil.jsonToList(jsonStr, type);
			
			Flownodes flownodes = new Flownodes();
			flownodes.setFlownodes(flownodeList);
			
			String xml = XmlConvertUtil.convertToXml(flownodes, "utf-8");
		
			String fullPath = "";
			if(StringUtils.isBlank(fileName) || StringUtils.isBlank(dirPath)){
				PrintTool.print(response, "目录和文件名不能为空", "html");
			}
			
			File file = new File(dirPath);
			
			if(!file.exists()){
				file.mkdir();
			}
			
			File file2 = new File(dirPath, fileName); 
			writer = new FileWriter(file2);
			writer.write(xml);
			writer.flush();
			
			System.out.println(dirPath+"/"+fileName);
			PrintTool.print(response, "success", "html");
		} catch (IOException e) {
			e.printStackTrace();
			try {
				PrintTool.print(response, "error", "html");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return ;
		
	}
	
	@RequestMapping("/addIdHolder")
	public void addIdHolder(HttpServletRequest request,HttpServletResponse response, IdHolder idHolder){
		System.out.println("idType="+idHolder.getIdType()+";idValue="+idHolder.getIdValue());
		TIdHolderBiz.insert(idHolder);
		JSONObject json = JSONObject.fromObject(idHolder);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getIdHolder")
	public void getIdHolder(HttpServletRequest request,HttpServletResponse response, IdHolder idHolder){
		System.out.println("idType="+idHolder.getIdType());
		String retNo=IdHolderUtils.getApplicationNo("西乡所",Env.APPLICATIONNO);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("retNo", retNo);
		JSONObject json = JSONObject.fromObject(data);
		try {
			PrintTool.print(response, json.toString(), "json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
