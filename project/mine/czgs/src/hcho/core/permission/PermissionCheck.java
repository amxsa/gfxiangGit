package hcho.core.permission;

import hcho.nsfw.user.entity.User;

public interface PermissionCheck {

	
	
	/**
	 * 判断用户是否有code对应的权限
	 * @param user
	 * @param code  权限所对应的标识符
	 * @return  
	 */
	public boolean Check(User user,String code);
}
