package hcho.nsfw.role.service.impl;

import hcho.core.service.impl.BaseServiceImpl;
import hcho.nsfw.role.dao.RoleDao;
import hcho.nsfw.role.entity.Role;
import hcho.nsfw.role.service.RoleService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	private RoleDao roleDao;
	
	@Resource
	public void setRoleDao(RoleDao roleDao){
		super.setBaseDao(roleDao);
		this.roleDao=roleDao;
	}


	@Override
	public void update(Role role) {
		// TODO Auto-generated method stub
		//更新之前 先删除关系
		roleDao.deletePrivilegesByRoleId(role.getRoleId());
		roleDao.update(role);
	}




}
