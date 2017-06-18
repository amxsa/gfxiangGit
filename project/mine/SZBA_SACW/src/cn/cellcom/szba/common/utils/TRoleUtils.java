package cn.cellcom.szba.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.cellcom.szba.biz.SessionBiz;
import cn.cellcom.szba.biz.TRoleBiz;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.enums.RoleIdKey;

public class TRoleUtils {

	public static boolean contain(List<TRole> roles, String roleKey){
		if(null == roles || 0 == roles.size()){
			return false;
		}
		for(TRole r : roles){
			if(r.getRoleKey().equalsIgnoreCase(roleKey)){
				return true;
			}
		}
		return false;
	}
	
	public static List<TRole> getCurrRoles(HttpSession session){
		List<Map<String, Object>> rolesInfo = (List<Map<String, Object>>) session.getAttribute("rolesInfo");
		List<TRole> roles = new ArrayList<TRole>();
		
		for(Map<String, Object> info : rolesInfo){
			TRole r = new TRole();
			r.setRoleKey((String)info.get("ROLE_KEY"));
			roles.add(r);
		}
		return roles;
	}
	
	public static boolean compareRoles(List<TRole> roles, List<String> roleCodes){
		boolean flag = false;
		
		for(String role : roleCodes){
			if(TRoleUtils.contain(roles, role)){
				flag = true;
				break;
			}
			flag = false;
		}
		return flag;
	}
	
	//根据角色以及部门，获取部门，角色类型，主要用于仓库管理（库位，保管室，货架）
	public static Long[] getDeptIdByCurrentRole(HttpServletRequest requ){
		Long deptId=null;
		Long type=2l;
		HttpSession session = requ.getSession();
		deptId = SessionBiz.getDepartmentId(session);
		List<TRole> roles = TRoleBiz.accountRoles(SessionBiz.getAccount(session));
		if((TRoleUtils.contain(roles,String.valueOf(RoleIdKey.ZXKGLY))||TRoleUtils.contain(roles,String.valueOf(RoleIdKey.ZXKLD)))&&TRoleUtils.contain(roles,String.valueOf(RoleIdKey.ZCKGLY))){
			type=1l;
		}else if(TRoleUtils.contain(roles,String.valueOf(RoleIdKey.ZXKGLY))||TRoleUtils.contain(roles,String.valueOf(RoleIdKey.ZXKLD))){
			//暂存库管理员只能看到自己仓库下的保管室，中心库管理一样
			deptId=0l;
		}
		Long[] ret=new Long[]{deptId,type};
		return ret;
	}
}
