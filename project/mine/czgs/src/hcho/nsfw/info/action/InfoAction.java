package hcho.nsfw.info.action;

import hcho.core.action.BaseAction;
import hcho.core.exception.ActionException;
import hcho.core.util.QueryHelper;
import hcho.nsfw.info.entity.Info;
import hcho.nsfw.info.service.InfoService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

@Controller
public class InfoAction extends BaseAction {
	
	
	private Info info;
	
	@Resource
	private InfoService infoService;
	
//	private List<Info> listInfo;
	
	private String strTitle;
	
	
	
	/**
	 * 查找用户列表  进入列表页面
	 * @return
	 */
	public String listUI(){
		
		ServletActionContext.getContext().getContextMap().put("infoTypeMap",Info.INFO_TYPE_MAP);
		
		try {
			QueryHelper queryHelper=new QueryHelper(Info.class, "i");
			if (info!=null) {
				if (StringUtils.isNotBlank(info.getTitle())) {
					//title在地址栏传过来是加码后的   解码后使用
					info.setTitle(decode(info.getTitle()));
					queryHelper.addCondition("i.title like ?", "%"+info.getTitle()+"%");
				}
			}
			//根据创建时间降序 排列
			queryHelper.addOrderByProperty("i.createTime",QueryHelper.ORDER_BY_DESC);
			
			//没加入分页功能前  根据条件搜索列表
			/*String sql=queryHelper.getListHql();
			List<Object> params = queryHelper.getParameter();
			listInfo=infoService.findObjects(sql,params);*/
			
			//根据条件搜索 进行分页查询列表
			pageResult=infoService.findObjects(queryHelper,getPageNo(),getPageSize());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
		
		
		return "listUI";
	}
	/**
	 * 新增操作
	 * @return
	 */
	public String addUI(){
		
		ServletActionContext.getContext().getContextMap().put("infoTypeMap",Info.INFO_TYPE_MAP);
		strTitle=info.getTitle();
		info=new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	/**
	 * 保存操作  
	 * @return
	 */
	public String add(){
		
		try {
			if (info!=null) {
				infoService.save(info);
			}
			info=null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return "list";
	}
	/**
	 * 若有Id值  进入编辑页面
	 * @return
	 */
	public String editUI(){
		strTitle=info.getTitle();
		ServletActionContext.getContext().getContextMap().put("infoTypeMap",Info.INFO_TYPE_MAP);
		
		if (info!=null&&StringUtils.isNotBlank(info.getInfoId())) {
			
			info=infoService.findById(info.getInfoId());
			
		}
		
		return "editUI";
	}
	/**
	 * 更新  
	 * @return
	 */
	public String edit(){
		try {
			if (info!=null) {
				
				infoService.update(info);
			}
			info=null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return "list";
	}
	/**
	 * 单个删除
	 * @return
	 * @throws  ActionException   测试异常
	 */
	public String delete() throws ActionException {
		strTitle=info.getTitle();
		if (info!=null&&StringUtils.isNotBlank(info.getInfoId())) {
			infoService.delete(info.getInfoId());
		}
		
		return "list";
	}
	/**
	 * 删除选中的多个或一个
	 * @return
	 */
	public String deleteSelected(){
		strTitle=info.getTitle();
		if (selectedRow!=null) {
			for (String id : selectedRow) {
				infoService.delete(id);
			}
		}
		
		return "list";
	}
	
	//更新状态
	public void doPublicInfo() throws IOException{
		if (info!=null) {
			//先用临时信息变量更新
			Info temp = infoService.findById(info.getInfoId());
			temp.setState(info.getState());
			infoService.update(temp);
			
			//
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write("状态已更新".getBytes("utf-8"));
			outputStream.close();
		}
	}
	
	
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public InfoService getInfoService() {
		return infoService;
	}
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}
//	public List<Info> getListInfo() {
//		return listInfo;
//	}
//	public void setListInfo(List<Info> listInfo) {
//		this.listInfo = listInfo;
//	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	

}
