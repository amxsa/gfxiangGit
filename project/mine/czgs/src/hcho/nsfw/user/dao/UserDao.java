package hcho.nsfw.user.dao;

import hcho.core.dao.BaseDao;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.entity.UserRole;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface UserDao extends BaseDao<User>{

	List<User> findByAccountOrId(String account, String id);

	//保存用户角色
	void saveUserRole(UserRole userRole);

	List<UserRole> findUserRolesByUserId(Serializable id);

	void deleteUserRoleByUserId(Serializable id);

	Set<String> findPrivilegeByUserRole(UserRole userRole);

	List<User> findUsersByAccountAndPass(String account, String password);



	
}
