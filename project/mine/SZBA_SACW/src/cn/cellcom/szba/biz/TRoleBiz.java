package cn.cellcom.szba.biz;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TIcon;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.domain.TRoleAccount;
import cn.cellcom.szba.domain.TRoleDesktopIcon;
import cn.cellcom.szba.vo.RoleMenuCheck;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TRoleBiz {
	private static Log log = LogFactory.getLog(TRoleBiz.class);

	private static String SQL_QUERY_ROLE =
			"select * from t_role where 1 = 1 and (name like blike:name) order by priority";
	
	private static String SQL_QUERY_ROLE_LIST =
			"select * from t_role ";

	//更新用户角色信息
	private static String SQL_DELETE_ACCOUNT_ROLE="delete  from T_ROLE_ACCOUNT where ACCOUNT_ID = ?";
	
	private static String SQL_INSERT_ACCOUNT_ROLE="insert into T_ROLE_ACCOUNT(account_id,role_id) values(?,?)";
	
	//根据用户查询用户角色信息
	private static String SQL_QUERY_ACCOUNT_ROLE ="select b.* from t_role_account a left join t_role b on a.role_id=b.id " +
				" where a.account_id = ? order by b.priority";
	
	private static String SQL_INSERT_ROLE_ICON =
			"insert into T_ROLE_DESKTOP_ICON(DESKTOP_ICON_ID,ROLE_ID) values(?,?) ";
	
	private static String SQL_INSERT_ROLE_ACCOUNT =
			"insert into T_ROLE_ACCOUNT(ACCOUNT_ID,ROLE_ID) values(?,?) ";
	
	private static String SQL_QUERY_MANE_TREE_ROLE_CHECK = "select x.*, case when y.id is not null then 'Y' else 'N' end as checked from ( "
			+ " select id, name, parent_id, url, priority, target, css, icon, levels, description, status, create_time"
			+ " from t_menu where status = 'Y' start with levels = 1 connect by prior id = parent_id order siblings by priority) x"
			+ " left join t_role_menu y on x.id = y.menu_id and y.role_id = ?";
	
	private static String SQL_DELETE_ROLE_MENU_BY_ROLE_ID = "delete from t_role_menu where role_id = ?";
	
	private static String SQL_INSERT_ROLE_MENU = 
			"insert into t_role_menu(menu_id, role_id, create_time) values(?,?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'))";
	
	private static String SQL_INSERT_ROLE="insert into t_role(name,description,priority,role_key) values(?,?,?,?)";
	/**
	 * 带条件的分页查询
	 * @param params 条件
	 * @param page   分页参数
	 * @return
	 */
	public ListAndPager<TRole> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TRole> list = new ListAndPager<TRole>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_ROLE, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TRole.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
		
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}

	/**
	 * 根据用户查询用户角色信息
	 * @param params
	 * @param page
	 * @return 
	 * @return
	 */
	public static List<TRole> accountRoles(String account){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TRole> list = new ArrayList<TRole>();
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_ACCOUNT_ROLE, TRole.class, account);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public static List<TRole> roleList(){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TRole> list;
		try{
			list=jdbc.query(Env.DS, SQL_QUERY_ROLE_LIST, TRole.class);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	/**
	 * 更新用户角色
	 * @param params
	 * @param page
	 * @return
	 */
	public void updateAccountRole(String account,String role){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try{
			jdbc.update(Env.DS, SQL_DELETE_ACCOUNT_ROLE, account);
			String [] roleStr=role.split(",");
			for (int i = 0; i < roleStr.length; i++) {
				jdbc.update(Env.DS, SQL_INSERT_ACCOUNT_ROLE, account,roleStr[i]);
			}
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
	}
	
	public ListAndPager<TRole> roleList(Map<String, String[]> params, Pager page) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TRole> list = new ListAndPager<TRole>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_ROLE_LIST, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TRole.class, page.getCurrentIndex(), 15, objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}

	public String iconAllot(String[] iconIds, String roleId) {
		
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		Long rolId = Long.parseLong(roleId);
		for(int i=0;i<iconIds.length;i++){
			String sql = "select * from T_ROLE_DESKTOP_ICON where DESKTOP_ICON_ID = ? and ROLE_ID = ? ";
			List<TRoleDesktopIcon> list = new ArrayList<TRoleDesktopIcon>();
			try {
		      list = jdbc.query(Env.DS, sql, TRoleDesktopIcon.class, Long.parseLong(iconIds[i]),rolId);
		      if(list.size()!=0){
		    	 result ="exist";
		      }else{
		    	 int rows =jdbc.update(Env.DS, SQL_INSERT_ROLE_ICON,Long.parseLong(iconIds[i]),rolId);
		    	 if(rows>0){
					result = "success";
				 }
		      }
			} catch (Exception e) {
				log.error(e);
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	public String accountAllot(String account, String rolId) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		
		
			String sql = "select * from T_ROLE_ACCOUNT where ACCOUNT_ID = ? and ROLE_ID = ? ";
			List<TRoleAccount> list = new ArrayList<TRoleAccount>();
			try {
		      list = jdbc.query(Env.DS, sql, TRoleAccount.class, account,rolId);
		      if(list.size()!=0){
		    	 result ="exist";
		      }else{
		    	 int rows =jdbc.update(Env.DS, SQL_INSERT_ROLE_ACCOUNT,account,Long.parseLong(rolId));
		    	 if(rows>0){
					result = "success";
				 }
		      }
			} catch (Exception e) {
				log.error(e);
				throw new RuntimeException(e);
			}
		return result;
	}	
	
	public static List<RoleMenuCheck> findMenuTreeByRoleId(String roleId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<RoleMenuCheck> list = null;
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_MANE_TREE_ROLE_CHECK, RoleMenuCheck.class, roleId);
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public static boolean updateRoleMenuByRoleId(String roleId, List<RoleMenuCheck> data){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		boolean result = true;
		Connection conn = null;
		try{
		// 先删除roleId的所有菜单
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			jdbc.update(conn, SQL_DELETE_ROLE_MENU_BY_ROLE_ID, roleId);
			
		// 重新插入菜单记录。
			for(RoleMenuCheck record: data){
				jdbc.update(conn, SQL_INSERT_ROLE_MENU, record.getId(), roleId, DateUtil.getCurrentDatetime());
			}
			
			conn.commit();
		} catch(Exception e){
			log.error(e.getMessage(), e);
			result=false;
		} finally{
			jdbc.closeAll();
		}
		return result;
	}

	public static String insert(TRole entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			int rows = jdbc.update(Env.DS, SQL_INSERT_ROLE,entity.getName(),entity.getDescription()
					,entity.getPriority(),entity.getRoleKey());
			if(rows == 1){
				result = "success";
			}
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} 
		return result;
	}
}
