package cn.cellcom.szba.biz.setting;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TMenu;
import cn.open.db.JDBC;

public class TMenuBiz {
	private static Log log = LogFactory.getLog(TMenuBiz.class);
	
	private static String SQL_QUERY_MENU = "select id, name, parent_id, url, priority, target, css, icon, levels, description, "
			+ " status, create_time, is_leaf from t_menu where status = 'Y' start with levels = 0 "
			+ " connect by prior id = parent_id order siblings by priority";
	
	private static String SQL_INSERT_MENU = "insert into t_menu(name, parent_id, url, priority, target, css, icon, levels, "
			+ "description, status, is_leaf) values(?,?,?,?,?,?,?,?,?,?,?)";
	
	private static String SQL_QUERY_MENU_BY_ID = "select id, name, parent_id, url, priority, target, css, icon, levels, description, "
			+ " status, create_time, is_leaf from t_menu where id = ?";
	
	private static String SQL_UPDATE_MENU = "update t_menu set name = ?, parent_id = ?, url = ?, priority = ?, target = ?, "
			+ "css = ?, icon = ?, levels = ?, description = ?, status = ?, is_leaf = ? where id = ? ";
	
	public static List<TMenu> findMenus(){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TMenu> list = null;
		
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_MENU, TMenu.class);
			
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public static TMenu saveMenu(TMenu menu){
		if(null==menu) return null;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TMenu record = null;
		try {
			if(null==menu.getId()){ // insert
				
				Long id = jdbc.insertWidthID(Env.DS, "seq_menu", Long.class, SQL_INSERT_MENU, menu.getName(), menu.getParentId(), menu.getUrl(), menu.getPriority(), menu.getTarget(),
						menu.getCss(), menu.getIcon(), menu.getLevels(), menu.getDescription(), menu.getStatus(), menu.getIsLeaf());
				if(id != null){
					menu.setId(id);
					record = menu;
				}
				
			} else{
				int row = jdbc.update(Env.DS, SQL_UPDATE_MENU, menu.getName(), menu.getParentId(), menu.getUrl(), menu.getPriority(), menu.getTarget(),
						menu.getCss(), menu.getIcon(), menu.getLevels(), menu.getDescription(), menu.getStatus(), menu.getIsLeaf(), menu.getId());
				if(row > 0){
					record = menu;
				}
			}
		} catch (Exception e) {
			log.error("", e);
		} finally{
			jdbc.closeAll();
		}
		return record;
	}
}