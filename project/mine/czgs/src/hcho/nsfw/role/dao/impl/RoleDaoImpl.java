package hcho.nsfw.role.dao.impl;

import org.hibernate.Query;

import hcho.core.dao.impl.BaseDaoImpl;
import hcho.nsfw.role.dao.RoleDao;
import hcho.nsfw.role.entity.Role;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{

	@Override
	public void deletePrivilegesByRoleId(String roleId) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery("delete from RolePrivilege where id.role.roleId=?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

	
	

}
