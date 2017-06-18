package hcho.nsfw.role.action;

import hcho.core.action.BaseAction;
import hcho.core.constant.Constant;
import hcho.core.exception.ActionException;
import hcho.core.util.QueryHelper;
import hcho.nsfw.info.entity.Info;
import hcho.nsfw.role.entity.Role;
import hcho.nsfw.role.entity.RolePrivilege;
import hcho.nsfw.role.entity.RolePrivilegeId;
import hcho.nsfw.role.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

@Controller
public class RoleAction extends BaseAction {
	
	
	private Role role;
	
	@Resource
	private RoleService roleService;
	
//	private List<Role> listrole;
	
	private String[] privileges;

	private String strName;
	
	/**
	 * 查找用户列表  进入列表页面
	 * @return
	 */
	public String listUI(){
		
		ServletActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		
		try {
			QueryHelper queryHelper=new QueryHelper(Role.class, "r");
			if (role!=null) {
				if (StringUtils.isNotBlank(role.getName())) {
					//title在地址栏传过来是加码后的   解码后使用
					role.setName(decode(role.getName()));
					queryHelper.addCondition("r.name like ?", "%"+role.getName()+"%");
				}
			}
			
			
			//根据条件搜索 进行分页查询列表
			pageResult=roleService.findObjects(queryHelper,getPageNo(),getPageSize());
			
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
		
		strName=role.getName();
		role=null;
		ServletActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		return "addUI";
	}
	/**
	 * 保存操作  
	 * @return
	 */
	public String save(){
		try {
			if (role!=null) {
				//将权限遍历并  级联存储
				if (privileges!=null) {
					Set<RolePrivilege> set=new HashSet<RolePrivilege>();
					for (String code  : privileges) {
						set.add(new RolePrivilege(new RolePrivilegeId(role,code)));
					}
					role.setPrivileges(set);
				}
				roleService.save(role);
			}
			role=null;
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
		
		strName=role.getName();
		//权限加载
		ServletActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		
		if (role!=null&&StringUtils.isNotBlank(role.getRoleId())) {
			//角色回显
			role=roleService.findById(role.getRoleId());
			//权限回显
			if (role.getPrivileges()!=null&&role.getPrivileges().size()>0) {
				privileges=new String[role.getPrivileges().size()];
				int i=0;
				for (RolePrivilege rp : role.getPrivileges()) {
					privileges[i++]=rp.getId().getCode();
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
		try {
			if (role!=null) {
				//将权限遍历并  级联存储
				if (privileges!=null) {
					Set<RolePrivilege> set=new HashSet<RolePrivilege>();
					for (String code  : privileges) {
						set.add(new RolePrivilege(new RolePrivilegeId(role,code)));
					}
					role.setPrivileges(set);
				}
				roleService.update(role);
			}
			role=null;
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
		strName=role.getName();
		if (role!=null&&StringUtils.isNotBlank(role.getRoleId())) {
			roleService.delete(role.getRoleId());
		}
		return "list";
	}
	/**
	 * 删除选中的多个或一个
	 * @return
	 */
	public String deleteSelected(){
		strName=role.getName();
		if (selectedRow!=null) {
			for (String id : selectedRow) {
				roleService.delete(id);
			}
		}
		return "list";
	}
	
	
//	public void setListrole(List<Role> listrole) {
//		this.listrole = listrole;
//	}
//	public List<Role> getListrole() {
//		return listrole;
//	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role Role) {
		this.role = Role;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	
	public void setPrivileges(String[] privileges) {
		this.privileges = privileges;
	}
	
	public String[] getPrivileges() {
		return privileges;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	
}
