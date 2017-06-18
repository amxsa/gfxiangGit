package cn.cellcom.szba.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TIcon;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.domain.TRoleDesktopIcon;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TIconBiz {
	
	Log log = LogFactory.getLog(this.getClass());
	
	//查询所有图标
	private static String SQL_QUERY_ICON_LIST="select * from t_desktop_icon";
	
	private static String SQL_INSERT_ICON = 
			"INSERT INTO t_desktop_icon(name, description, icon, priority, url, target) " + 
			"VALUES(?,?,?,?,?,?)";
	
	//根据用户查询功能
	private static String SQL_QUERY_ACCOUNT_FUNCTION ="select distinct b.* from T_ROLE_DESKTOP_ICON a left join T_DESKTOP_ICON b on a.DESKTOP_ICON_ID=b.ID " +
	      " where a.ROLE_ID in (select role_id from t_role_account where account_id=?) and b.ID is not null " +
	      " order by b.priority,b.ID";
	
	private static String SQL_UPDATE_ICON_BY_ID ="UPDATE t_desktop_icon set name = ?,  description = ?," +
			" icon = ?, priority = ?, url = ?, target = ? where id=?";
	
	private static String SQL_QUERY_ICON_BY_ID = "select * from t_desktop_icon where id=?";
	
	private static String SQL_DELETE_ICON_BY_ID = "delete from t_desktop_icon where id=?";
	
	private static String SQL_DELETE_ROLE_CON_BY_ID = "delete from t_role_desktop_icon where id=?";
	
	public ListAndPager<TIcon> queryIconPage(String account,
			Pager page) {
		
     JDBC jdbc = SpringWebApplicataionContext.getJdbc();
     ListAndPager<TIcon> list = new ListAndPager<TIcon>();
     String sql = "select * from t_desktop_icon ";
		try {
		   list = jdbc.queryPage(Env.DS, sql, TIcon.class, page.getCurrentIndex(), 15);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} 
		return list;
	}
	/**
	 * 查询角色下菜单
	 * @return
	 */
	public List<TRoleDesktopIcon> roleIconList(String rolId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String sql = "select * from T_ROLE_DESKTOP_ICON where ROLE_ID = ? ";
		List<TRoleDesktopIcon> list ;
		try{
			list = jdbc.query(Env.DS, sql, TRoleDesktopIcon.class,rolId);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<TIcon> iconList(){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TIcon> list;
		try{
			list=jdbc.query(Env.DS, SQL_QUERY_ICON_LIST, TIcon.class);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public List<TIcon> accountIcons(String account){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TIcon> list = new ArrayList<TIcon>();
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_ACCOUNT_FUNCTION, TIcon.class, account);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return list;
		
	}
	
	public String insert(TIcon entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			int rows = jdbc.update(Env.DS, SQL_INSERT_ICON, entity.getName(), entity.getDescription(),
					  entity.getIcon(), entity.getPriority(), entity.getUrl(), entity.getTarget());
			if(rows == 1){
				result = "success";
			}
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} 
		return result;
	}

	public TIcon queryById(long id) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TIcon entity = null;
		try{
			entity = jdbc.queryForObject(Env.DS, SQL_QUERY_ICON_BY_ID, TIcon.class, id);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} 
		return entity;
	}

	public String save(TIcon entity, long id) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			int rows = jdbc.update(Env.DS, SQL_UPDATE_ICON_BY_ID, entity.getName(), entity.getDescription(),
					  entity.getIcon(), entity.getPriority(), entity.getUrl(), entity.getTarget(),id);
			
			if(rows == 1){
				result = "success";
			}
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} 
		return result;
	}

	public String del(long id) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			int rowsIcon = jdbc.update(Env.DS, SQL_DELETE_ICON_BY_ID, id);
			int rowsRole = jdbc.update(Env.DS, SQL_DELETE_ROLE_CON_BY_ID, id);
			if(rowsIcon == 1&&rowsRole>=1){
				result = "success";
			}
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} 
		return result;
	}
}
