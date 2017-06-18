package hcho.nsfw.user.dao.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import hcho.core.constant.Constant;
import hcho.core.dao.impl.BaseDaoImpl;
import hcho.nsfw.role.entity.Role;
import hcho.nsfw.role.entity.RolePrivilege;
import hcho.nsfw.user.dao.UserDao;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.entity.UserRole;
import hcho.nsfw.user.entity.UserRoleId;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public List<User> findByAccountOrId(String account, String id) {
		// TODO Auto-generated method stub
		
		String sHQL="from User where account=?";
		if (StringUtils.isNotBlank(id)) {
			sHQL+=" and id !=?";
		}
		Query query = getCurrentSession().createQuery(sHQL);
		query.setParameter(0, account);
		if (StringUtils.isNotBlank(id)) {
			query.setParameter(1, id);
		}
		return query.list();
		
	}

	/**
	 * 根据用户Id删除用户角色  （更新之前需删除用户角色 ）
	 */
	@Override
	public void deleteUserRoleByUserId(Serializable id) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery("delete from UserRole where id.userId=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}

	@Override
	public void saveUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(userRole);
	}
	
	
	@Override
	public List<UserRole> findUserRolesByUserId(Serializable id) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery("from UserRole where id.userId=?");
		query.setParameter(0, id);
		return query.list();
		
	}

	
	
	@Override
	public Set<String> findPrivilegeByUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		
		Role role = userRole.getId().getRole();
		Query query = getCurrentSession().createQuery("from RolePrivilege where role_id=?");
		query.setParameter(0, role.getRoleId());
		List<RolePrivilege> list = query.list();
		
		
		Set<String> set=new HashSet<String>();
		for (RolePrivilege rolePrivilege : list) {
			String code = rolePrivilege.getId().getCode();
			String privilege = Constant.PRIVILEGE_MAP.get(code);
			set.add(privilege);
		}
		return set;
	}

	@Override
	public List<User> findUsersByAccountAndPass(String account, String password) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery("from User where account=? and password=?");
		query.setParameter(0, account);
		query.setParameter(1, password);
		
		return query.list();
	}

	

}
