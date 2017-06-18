package hcho.nsfw.user.action;

import hcho.core.action.BaseAction;
import hcho.core.exception.ActionException;
import hcho.core.util.QueryHelper;
import hcho.nsfw.role.service.RoleService;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.entity.UserRole;
import hcho.nsfw.user.service.UserService;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
public class UserAction extends BaseAction {
	
	
	private User user;
	
	@Resource
	private UserService userService;
	
//	private List<User> listUser;
	
	private String[] selectedRow;

	private File headImg;
	private String headImgFileName;
	private String headImgContentType;
	private File userExcel;
	private String userExcelFileName;
	private String userexcelContentType;
	
	private String[] userRoleIds;
	@Resource
	private RoleService roleService;
	
	private String strName;
	/**
	 * 查找用户列表  进入列表页面
	 * @return
	 */
	public String listUI(){
		
		try {
			QueryHelper queryHelper=new QueryHelper(User.class, "u");
			if (user!=null) {
				if (StringUtils.isNotBlank(user.getName())) {
					//title在地址栏传过来是加码后的   解码后使用
					user.setName(decode(user.getName()));
					queryHelper.addCondition("u.name like ?", "%"+user.getName()+"%");
				}
			}
			//根据创建时间降序 排列
//			queryHelper.addOrderByProperty("",QueryHelper.ORDER_BY_DESC);
			//根据条件搜索 进行分页查询列表
			pageResult=userService.findObjects(queryHelper,getPageNo(),getPageSize());
			
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
		strName=user.getName();
		user=null;//-----------------------------
		ActionContext.getContext().getContextMap().put("rolelist",roleService.findObjects());
		return "addUI";
	}
	/**
	 * 保存操作  
	 * @return
	 */
	public String save(){
		if (user!=null) {
			try {
				if (headImg!=null) {
					//获取存储路径
					String realPath = ServletActionContext.getServletContext().getRealPath("/upload/user");
					//所有图片使用UUID加上文件后缀名
					String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
					//获得新图片
					FileUtils.copyFile(headImg,new File( realPath,fileName));
					user.setHeadImg(fileName);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			userService.saveUserAndRole(user,userRoleIds);
			//userService.save(user);
		}
		user=null;
		return "list";
	}
	/**
	 * 若有Id值  进入编辑页面
	 * @return
	 */
	public String editUI(){
		strName=user.getName();
		//加载用户角色列表
		ActionContext.getContext().getContextMap().put("rolelist",roleService.findObjects());
		if (user!=null&&StringUtils.isNotBlank(user.getId())) {
			//回显角色
			user=userService.findById(user.getId());
		List<UserRole> userRoles=userService.findUserRolesByUserId(user.getId());
			if (userRoles!=null&&userRoles.size()>0) {
				userRoleIds = new String[userRoles.size()];
				for (int i = 0; i < userRoles.size(); i++) {
					userRoleIds[i] = userRoles.get(i).getId().getRole()
							.getRoleId();
				}
			}
			
		}
		
		
		return "editUI";
	}
	/**
	 * 更新  
	 * @return
	 */
	public String update(){
		
		if (user!=null) {
			//同时更新头像 
			try {
				if (headImg!=null) {
					String realPath = ServletActionContext.getServletContext().getRealPath("/upload/user");
					String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf("."));
					FileUtils.copyFile(headImg,new File( realPath,fileName));
					user.setHeadImg("user/"+fileName);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
//			userService.update(user);
			userService.updateUserAndRole(user,userRoleIds);
		}
		user=null;
		return "list";
	}
	/**
	 * 单个删除
	 * @return
	 * @throws  ActionException   测试异常
	 */
	public String delete() throws ActionException {
		strName=user.getName();
		try {
			if (user!=null) {
				//int i=1/0;
				userService.delete(user.getId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ActionException("异常："+e.getMessage());
		}
		return "list";
	}
	/**
	 * 删除选中的多个或一个
	 * @return
	 */
	public String deleteSelected(){
		strName=user.getName();
		if (selectedRow!=null) {
			for (String id : selectedRow) {
				userService.delete(id);
			}
		}
		return "list";
	}
	/**
	 * 导出Excel文件
	 */
	public void doExport(){
		List<User> userList = userService.findObjects();
		//导出excel
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;fileName="+new String("用户列表.xls".getBytes(),"iso8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			userService.export(userList,outputStream);
			if (outputStream!=null) {
				outputStream.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	
	public String doImport(){
		
		if (userExcel!=null) {
			if (userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
				userService.importExcel(userExcel);
			}
		}
		return "list";
	}
	/**
	 * 根据账号（新增的时候）和id（用于修改的时候 此时账号是相同的  ）去数据库查询
	 * @throws IOException
	 */
	public void verifyAccount() throws IOException{
		
		if (user!=null) {
			String msg="true";
			List<User> userlist = userService.findByAccountOrId(user.getAccount(),user.getId());
			if (userlist!=null&&userlist.size()>0) {
				//说明账号已经存在
				msg="false";
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			//以普通文本方式响应
			response.setContentType("text/html");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(msg.getBytes());
			outputStream.close();
		}
	}
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public File getUserExcel() {
		return userExcel;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	public String getUserExcelFileName() {
		return userExcelFileName;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	public String getUserexcelContentType() {
		return userexcelContentType;
	}
	public void setUserexcelContentType(String userexcelContentType) {
		this.userexcelContentType = userexcelContentType;
	}
	public String[] getUserRoleIds() {
		return userRoleIds;
	}
	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	
}
