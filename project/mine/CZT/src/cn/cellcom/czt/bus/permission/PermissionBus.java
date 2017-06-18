package cn.cellcom.czt.bus.permission;

import java.util.List;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.czt.po.RoleMenuCheck;
import cn.cellcom.czt.po.TPermissionUrl;
import cn.cellcom.web.spring.ApplicationContextTool;

public class PermissionBus {
	public List<RoleMenuCheck> findMenuTreeByRoleId(String roleId) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		List<RoleMenuCheck> list = null;
		try {
			list = jdbc
					.query(ApplicationContextTool.getDataSource(),
							"select x.*, case when y.id is not null then 'Y' else 'N' end as checked from ( select a.*  from t_menu a LEFT JOIN t_menu b on a.parent_id = b.id) x left join t_role_menu y on x.id = y.menu_id and y.role_id = ?",
							RoleMenuCheck.class,
							new Object[] { Integer.parseInt(roleId) }, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<TPermissionUrl> findUrlByMenuId(String menuId) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		List<TPermissionUrl> list = null;
		//select a.name,b.id,b.permission_id,b.url,b.status from t_permission a left join t_permission_url b on a.id = b.permission_id  where a.menu_id = ? 
		try {
			list = jdbc
					.query(ApplicationContextTool.getDataSource(),
							"select * from t_permission a where a.menu_id = ? ",
							TPermissionUrl.class,
							new Object[] { Integer.parseInt(menuId) }, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
