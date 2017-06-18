package hcho.core.permission.impl;

import java.util.List;

import javax.annotation.Resource;

import hcho.core.permission.PermissionCheck;
import hcho.nsfw.role.entity.Role;
import hcho.nsfw.role.entity.RolePrivilege;
import hcho.nsfw.user.entity.User;
import hcho.nsfw.user.entity.UserRole;
import hcho.nsfw.user.service.UserService;

public class PermissionCheckImpl implements PermissionCheck {

	
	@Resource
	private UserService userService;
	
	@Override
	public boolean Check(User user, String code) {
		// TODO Auto-generated method stub
		List<UserRole> userRoles = user.getUserRoles();
		
		
		if (userRoles==null) {
			userRoles=userService.findUserRolesByUserId(user.getId());
		}
		if (userRoles!=null) {
			//遍历得到用户的每一个角色
			for (UserRole userRole : userRoles) {
				Role role = userRole.getId().getRole();
				//遍历得到每个角色所拥有的权限
				for (RolePrivilege rp : role.getPrivileges()) {
					//如果角色中的权限中有该权限 就返回true
					if (rp.getId().getCode().equals(code)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

}
