package com.gf.ims.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.ApplicationContextTool;
import com.gf.ims.common.env.Env;
import com.gf.ims.common.page.PageResult;
import com.gf.ims.common.page.PageTool;
import com.gf.ims.common.page.PageUtil;
import com.gf.ims.common.util.Md5;
import com.gf.ims.service.UserService;
import com.gf.ims.web.queryBean.UserQueryBean;
import com.gf.imsCommon.ims.module.Questions;
import com.gf.imsCommon.ims.module.User;
import com.gf.imsCommon.ims.module.UserCheck;
import com.gf.imsCommon.ims.module.UserDetail;
import com.gf.imsCommon.ims.module.UserRole;

@Service(value="userService")
public class UserServiceImpl implements UserService {

	@Override
	public List<Questions> getQuestions() {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from questions  where 1=1 ");
		 try {
			List<Questions> list = jdbc.query(Env.DS, sql.toString(), Questions.class, null,0,0);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User saveUser(User user, UserDetail userDetail, List<UserCheck> userCheckList) {
		JDBC jdbc=ApplicationContextTool.getJdbc();
		Connection conn = null;
		try {
			conn=jdbc.getConnection();
			conn.setAutoCommit(false);
			
			String userUpdateSql="update user set user_type=?,user_name=?,nick_name=?,user_account=?,password=?,mobile=?,create_time=?,account_status=? where id=? ";
			jdbc.update(conn, userUpdateSql, new Object[]{user.getUserType(),user.getUserName(),user.getNickName()
					,user.getUserAccount(),user.getPassword(),user.getMobile(),user.getCreateTime(),user.getAccountStatus(),user.getId()});
			
			UserDetail ud=getUserDetailById(user.getId());
			String userDetailSql="";
			if (ud==null) {
				userDetailSql="insert into user_detail(user_id,thum_image_path,image_path,province_id,city_id,area_id,detail_address,sex)values(?,?,?,?,?,?,?,?)";
				jdbc.update(conn, userDetailSql, new Object[]{userDetail.getUserId(),userDetail.getThumImagePath(),userDetail.getImagePath(),
						userDetail.getProvinceId(),userDetail.getCityId(),userDetail.getAreaId(),userDetail.getDetailAddress(),userDetail.getSex()});
			}else{
				userDetailSql="update user_detail set thum_image_path=?,image_path=?,province_id=?,city_id=?,area_id=?,detail_address=?,sex=? where user_id=? ";
				jdbc.update(conn, userDetailSql, new Object[]{userDetail.getThumImagePath(),userDetail.getImagePath(),
						userDetail.getProvinceId(),userDetail.getCityId(),userDetail.getAreaId(),userDetail.getDetailAddress(),userDetail.getSex(),userDetail.getUserId()});
			}
			
			if (userCheckList.size()>0) {
				String deleteCheckSql="delete from user_check where user_id=?  ";
				jdbc.update(conn, deleteCheckSql, new Object[]{user.getId()});
				String userCheckSql="insert into user_check(id,user_id,question_id,answer)values(?,?,?,?)";
				for (UserCheck uc : userCheckList) {
					jdbc.update(conn, userCheckSql, new Object[]{uc.getId(),uc.getUserId(),uc.getQuestionId(),uc.getAnswer()});
				}
			}
			conn.commit();
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			jdbc.closeAll();
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public UserDetail getUserDetailById(String id) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from user_detail  where user_id=").append("'").append(id).append("'");
		 try {
			 UserDetail ud = jdbc.queryForObject(Env.DS, sql.toString(), UserDetail.class,null);
			return ud;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PageResult<User> findUserList(UserQueryBean userQueryBean) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from user  where 1=1 ");
		if (StringUtils.isNotBlank(userQueryBean.getMobile())) {
			sql.append("and mobile like ").append("'%").append(userQueryBean.getMobile()).append("%'");
		}
		if (StringUtils.isNotBlank(userQueryBean.getNickName())) {
			sql.append("and nick_name like ").append("'%").append(userQueryBean.getNickName()).append("%'");
		}
		if (StringUtils.isNotBlank(userQueryBean.getUserAccount())) {
			sql.append("and user_account like ").append("'%").append(userQueryBean.getUserAccount()).append("%'");
		}
		if (StringUtils.isNotBlank(userQueryBean.getUserName())) {
			sql.append("and user_name like ").append("'%").append(userQueryBean.getUserName()).append("%'");
		}
		if (StringUtils.isNotBlank(userQueryBean.getUserType())) {
			sql.append("and user_type = ").append(userQueryBean.getUserType());
		}
		try {
			PageResult<User> pageResult = PageUtil.queryPageDataMysql(jdbc, Env.DS, new PageTool(userQueryBean.getPageNumber(), userQueryBean.getPageSize()), sql.toString(), null, User.class);
			return pageResult;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return null;
	}

	@Override
	public User addUser(User user, String[] roleIds) {
		JDBC jdbc=ApplicationContextTool.getJdbc();
		Connection conn = null;
		try {
			conn=jdbc.getConnection();
			conn.setAutoCommit(false);
			String userSql="insert into user(id,user_type,user_account,password,mobile,create_time,account_status)values(?,?,?,?,?,?,?)";
			jdbc.update(conn, userSql, new Object[]{user.getId(),user.getUserType()
					,user.getUserAccount(),new Md5().getMD5Str("888888"),user.getMobile(),user.getCreateTime(),user.getAccountStatus()});
			String userRoleSql="insert into user_role(id,user_id,role_id)values(?,?,?)";
			for (String roleId : roleIds) {
				jdbc.update(conn, userRoleSql, new Object[]{Env.getUUID(),user.getId(),roleId});
			}
			conn.commit();
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			jdbc.closeAll();
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public User getById(String userId) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from user  where id=").append("'").append(userId).append("'");
		 try {
			User user = jdbc.queryForObject(Env.DS, sql.toString(), User.class,null);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getByAccount(String userAccount) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from user  where user_account=").append("'").append(userAccount).append("'");
		 try {
			User user = jdbc.queryForObject(Env.DS, sql.toString(), User.class,null);
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateUser(User user, String[] roleIds) {
		JDBC jdbc=ApplicationContextTool.getJdbc();
		Connection conn = null;
		try {
			conn=jdbc.getConnection();
			conn.setAutoCommit(false);
			String userSql="update user set user_type=?,user_account=?,password=?,mobile=?,create_time=?,account_status=? where id=? ";
			jdbc.update(conn, userSql, new Object[]{user.getUserType()
					,user.getUserAccount(),user.getPassword(),user.getMobile(),user.getCreateTime(),user.getAccountStatus(),user.getId()});
			
			String deleteRoleSql="delete from user_role where user_id=? ";
			jdbc.update(conn, deleteRoleSql,  new Object[]{user.getId()});
			
			String userRoleSql="insert into user_role(id,user_id,role_id)values(?,?,?)";
			for (String roleId : roleIds) {
				jdbc.update(conn, userRoleSql, new Object[]{Env.getUUID(),user.getId(),roleId});
			}
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			jdbc.closeAll();
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	@Override
	public List<UserRole> getUserRoleListByUserId(String userId) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from user_role  where user_id=").append("'").append(userId).append("'");
		 try {
			List<UserRole> list = jdbc.query(Env.DS, sql.toString(), UserRole.class,null,0,0);
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User user) {
		JDBC jdbc=ApplicationContextTool.getJdbc();
		Connection conn = null;
		try {
			conn=jdbc.getConnection();
			conn.setAutoCommit(false);
			String userSql="update user set user_type=?,user_account=?,password=?,mobile=?,create_time=?,account_status=? where id=? ";
			jdbc.update(conn, userSql, new Object[]{user.getUserType()
					,user.getUserAccount(),user.getPassword(),user.getMobile(),user.getCreateTime(),user.getAccountStatus(),user.getId()});
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			jdbc.closeAll();
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public User getByMobile(String mobile) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from user  where mobile=").append("'").append(mobile).append("'");
		 try {
			User user = jdbc.queryForObject(Env.DS, sql.toString(), User.class,null);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserCheck> getUserCheckList(String userId) {
		JDBC jdbc = Env.jdbc;
		StringBuffer sql=new StringBuffer("select * from user_check  where user_id=").append("'").append(userId).append("'");
		 try {
			List<UserCheck> list = jdbc.query(Env.DS, sql.toString(), UserCheck.class,null,0,0);
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
