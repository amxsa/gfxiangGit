package com.gf.ims.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.Env;
import com.gf.ims.common.page.PageResult;
import com.gf.ims.common.page.PageTool;
import com.gf.ims.common.page.PageUtil;
import com.gf.ims.service.RoleService;
import com.gf.ims.web.queryBean.RoleQueryBean;
import com.gf.imsCommon.ims.module.Role;
import com.gf.imsCommon.ims.module.RoleMenu;

@Service(value="roleService")
public class RoleServiceImpl implements RoleService {

	@Override
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
	}

	@Override
	public List<Role> getRoleList() {
		JDBC jdbc = Env.jdbc;
		try {
			StringBuffer sql=new StringBuffer("select * from role  where  1=1 ");
			List<Role> list = jdbc.query(Env.DS, sql.toString(), Role.class, null, 0, 0);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return null;
	}

	/*@Override
	public List<Menu> queryMenuTree(String type) {
		JDBC jdbc = Env.jdbc;
		String sql="";
		if (type.equals("1")) {//获取所有
			sql="select a.*  from menu a LEFT JOIN menu b on a.parent_id = b.id  order by  a.levels asc ,a.priority asc ";
		}else{
			sql="select a.*  from menu a LEFT JOIN menu b on a.parent_id = b.id where a.status='Y'  order by  a.levels asc ,a.priority asc ";
		}
		try {
			List<Menu> list = jdbc.query(Env.DS,sql,Menu.class, null, 0, 0);
			if (list != null) {
				Collections.sort(list);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Menu saveMenu(Menu menu) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		Menu record = null;
		try {
			if (menu.getId() != null) {
				String sql="";
				if (menu.getStatus().equals("D")) {
					sql="delete from menu where id=?";
					int count = jdbc.update(ApplicationContextTool.getDataSource(), sql, new Object[] {menu.getId()});
					if (count > 0) {
						record = menu;
					}
				}else{
					sql="update menu set name = ?, parent_id = ?, url = ?, priority = ?, target = ?, "
							+ "css = ?, icon = ?, levels = ?, description = ?, status = ?, is_leaf = ?,cntid_name= ? ,sql_condition= ?  where id = ? ";
					int count = jdbc.update(ApplicationContextTool.getDataSource(),sql,
							new Object[] { menu.getName(),
									menu.getParentId(), menu.getUrl(),
									menu.getPriority(), menu.getTarget(),
									menu.getCss(), menu.getIcon(),
									menu.getLevels(),
									menu.getDescription(),
									menu.getStatus(), menu.getIsLeaf(),menu.getCntidName(),menu.getSqlCondition(),
									menu.getId() });
					if (count > 0) {
						record = menu;
					}
				//}
			} else {
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into menu(name, parent_id, url, priority, target, css, icon, levels, "
										+ "description, status, is_leaf,cntid_name,sql_condition) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
								new Object[] { menu.getName(),
										menu.getParentId(), menu.getUrl(),
										menu.getPriority(), menu.getTarget(),
										menu.getCss(), menu.getIcon(),
										menu.getLevels(),
										menu.getDescription(),
										menu.getStatus(), menu.getIsLeaf(),menu.getCntidName(),menu.getSqlCondition() });
				if (count > 0) {
					Integer id = jdbc.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select max(id) from menu", Integer.class, null);
					menu.setId(id);
					record = menu;
				}

			}
			return record;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
