package com.gf.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.Env;
import com.gf.ims.service.CommonService;
import com.gf.imsCommon.ims.module.Area;
import com.gf.imsCommon.ims.module.City;
import com.gf.imsCommon.ims.module.Province;
import com.mysql.fabric.xmlrpc.base.Array;

@Service(value="commonService")
public class CommonServiceImpl implements CommonService {

	@Override
	public List<Province> getProvince() {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from province where 1=1 ");
		try {
			List<Province> list = jdbc.query(Env.DS, sql.toString(), Province.class, null,0,0);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<City> getCityByFather(String provinceId) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from city where 1=1 ");
		List<Object> conditions=new ArrayList<Object>();
		try {
			if (StringUtils.isNotBlank(provinceId)) {
				sql.append(" and father_id=?  ");
				conditions.add(provinceId);
			}
			List<City> list = jdbc.query(Env.DS, sql.toString(), City.class, conditions.toArray(),0,0);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Area> getAreaByFather(String cityId) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from area where 1=1 ");
		List<Object> conditions=new ArrayList<Object>();
		try {
			if (StringUtils.isNotBlank(cityId)) {
				sql.append(" and father_id=?  ");
				conditions.add(cityId);
			}
			List<Area> list = jdbc.query(Env.DS, sql.toString(), Area.class, conditions.toArray(),0,0);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	/*@Override
	public PageResult<Role> findRoleList(RoleQueryBean roleQueryBean) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from role  where 1=1 ");
		if (StringUtils.isNotBlank(roleQueryBean.getRoleName())) {
			sql.append("and role_name like ").append("'%").append(roleQueryBean.getRoleName()).append("%'");
		}
		try {
			PageResult<Role> pageResult = PageUtil.queryPageDataMysql(jdbc, Env.DS, new PageTool(roleQueryBean.getPageNumber(), roleQueryBean.getPageSize()), sql.toString(), null, Role.class);
			return pageResult;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return null;
	}

	@Override
	public Role findByName(String roleName) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from role  where 1=1 ");
		if (StringUtils.isNotBlank(roleName)) {
			sql.append("and role_name =").append("'").append(roleName).append("'");
		}
		try {
			jdbc.queryForObject(Env.DS, sql.toString(), Role.class, null);
			List<Role> list = jdbc.query(Env.DS, sql.toString(), Role.class, null,0,0);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Role role) {
		StringBuffer sql=new StringBuffer();
		sql.append(" update role set");
		sql.append(" role_name=?,role_note=?, create_account=?,create_time=? where id=? ");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{role.getRoleName(),role.getRoleNote(),role.getCreateAccount(),role.getCreateTime(),role.getId()};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
	}

	@Override
	public Role save(Role role) {
		StringBuffer sql=new StringBuffer();
		sql.append(" insert into role(");
		sql.append(" id,role_name,role_note, create_account,create_time)values(?,?,?,?,?)");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{role.getId(),role.getRoleName(),role.getRoleNote(),role.getCreateAccount(),role.getCreateTime()};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return role;
		
	}

	@Override
	public Role getById(String roleId) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from role  where 1=1 ");
		if (StringUtils.isNotBlank(roleId)) {
			sql.append("and id =").append("'").append(roleId).append("'");
		}
		try {
			jdbc.queryForObject(Env.DS, sql.toString(), Role.class, null);
			List<Role> list = jdbc.query(Env.DS, sql.toString(), Role.class, null,0,0);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteRoleById(String roleId) {
		String sql1=" delete from role where id=? ";
		String sql2=" delete from role_menu where role_id=? ";
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{roleId};
		try {
			jdbc.update(Env.DS, sql1, params);
			jdbc.update(Env.DS, sql2, params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		
	}

	@Override
	public String getMenusJsonByRoleId(String roleId) {
		StringBuffer menuIds = new StringBuffer();
		List<RoleMenu> roleMenus = null;
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from role_menu  where 1=1 and role_id=? ");
		if (StringUtils.isNotBlank(roleId)) {
			List<RoleMenu> list;
			try {
				list = jdbc.query(Env.DS, sql.toString(), RoleMenu.class, new Object[]{roleId},0,0);
				if(list.size()>0){
					for(RoleMenu rm : list){
						menuIds.append(rm.getMenuId());
						menuIds.append(",");
					}
					return menuIds.substring(0,menuIds.length()-1).toString();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void saveRoleMenus(String roleId,String menus) {
			String idArray[] = menus.split(",");
			JDBC jdbc = Env.jdbc;
			try {
				StringBuffer sql=new StringBuffer("delete from role_menu  where  role_id=? ");
				jdbc.update(Env.DS, sql.toString(), new Object[]{roleId});
				if (idArray.length>0) {
					for(int i = 0;i<idArray.length;i++){
						String insert="insert into role_menu(id,role_id,menu_id)values(?,?,?)";
						jdbc.update(Env.DS, insert, new Object[]{Env.getUUID(),roleId,idArray[i]});
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				jdbc.closeAll();
			}
	}*/

}
