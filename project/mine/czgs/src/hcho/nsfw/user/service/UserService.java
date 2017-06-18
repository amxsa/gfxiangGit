package hcho.nsfw.user.service;

import hcho.core.service.BaseService;
import hcho.core.util.QueryHelper;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.entity.UserRole;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;

public interface UserService extends BaseService<User>{
	
	
	public void export(List<User> userList, ServletOutputStream outputStream);

	public void importExcel(File userExcel);

	public List<User> findByAccountOrId(String account, String id);

	//同时保存用户和角色（不动实体类）
	public void saveUserAndRole(User user,String...userRoleIds);
	//更加用户Id查询用户角色
	public List<UserRole> findUserRolesByUserId(Serializable id);

	public void updateUserAndRole(User user, String...userRoleIds);
	//1、根据用户ID查询出该用户的所有中文权限（不重复）
	public Set<String> findPrivilegeById(Serializable id);

	public List<User> findUsersByAccountAndPass(String account, String password);

	
}
