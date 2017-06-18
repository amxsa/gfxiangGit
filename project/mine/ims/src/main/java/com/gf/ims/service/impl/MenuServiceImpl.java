package com.gf.ims.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.ApplicationContextTool;
import com.gf.ims.common.env.Env;
import com.gf.ims.service.MenuService;
import com.gf.imsCommon.ims.module.Menu;

@Service(value="menuService")
public class MenuServiceImpl implements MenuService {

	@Override
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
				/*if (menu.getStatus().equals("D")) {
					sql="delete from menu where id=?";
					int count = jdbc.update(ApplicationContextTool.getDataSource(), sql, new Object[] {menu.getId()});
					if (count > 0) {
						record = menu;
					}
				}else{*/
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
	}
}
