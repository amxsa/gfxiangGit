package cn.cellcom.czt.bus.permission;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.czt.po.TMenu;
import cn.cellcom.web.spring.ApplicationContextTool;

public class MenuBus {
	public List<TMenu> queryMenuTree() {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			List<TMenu> list = jdbc
					.query(ApplicationContextTool.getDataSource(),
							"select a.*  from t_menu a LEFT JOIN t_menu b on a.parent_id = b.id where a.status='Y' order by  a.levels asc ,a.priority asc ",
							TMenu.class, null, 0, 0);
			if (list != null) {
				sort(list);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private List<TMenu> sort(List<TMenu> list){
		if(list!=null){
//			for(int i=0;i<list.size();i++){
//				if
//			}
			Collections.sort(list);
			return list;
		}
		return null;
	}

	public TMenu saveMenu(TMenu menu) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		TMenu record = null;
		try {
			if (menu.getId() != null) {
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"update t_menu set name = ?, parent_id = ?, url = ?, priority = ?, target = ?, "
										+ "css = ?, icon = ?, levels = ?, description = ?, status = ?, is_leaf = ?,cntid_name= ? ,sql_condition= ?  where id = ? ",
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
			} else {
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_menu(name, parent_id, url, priority, target, css, icon, levels, "
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
							"select max(id) from t_menu", Integer.class, null);
					menu.setId(id);
					record = menu;
				}

			}
			return record;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
