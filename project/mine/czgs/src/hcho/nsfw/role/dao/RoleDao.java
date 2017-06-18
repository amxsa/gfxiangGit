package hcho.nsfw.role.dao;

import hcho.core.dao.BaseDao;
import hcho.nsfw.role.entity.Role;

public interface RoleDao extends BaseDao<Role>{
	
		//根据角色id删除该角色对应的所有权限
		public void deletePrivilegesByRoleId(String roleId);
}
