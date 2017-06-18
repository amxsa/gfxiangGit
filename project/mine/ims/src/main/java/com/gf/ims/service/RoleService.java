package com.gf.ims.service;

import java.util.List;

import com.gf.ims.common.page.PageResult;
import com.gf.ims.web.queryBean.RoleQueryBean;
import com.gf.imsCommon.ims.module.Role;

public interface RoleService {
	PageResult<Role> findRoleList(RoleQueryBean roleQueryBean);

	Role findByName(String roleName);

	void update(Role role);

	Role save(Role role);

	Role getById(String roleId);

	void deleteRoleById(String roleId);

	String getMenusJsonByRoleId(String roleId);

	void saveRoleMenus(String id, String menus);

	List<Role> getRoleList();
}
