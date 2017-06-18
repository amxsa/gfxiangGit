package cn.cellcom.czt.action.permission;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.czt.bus.permission.PermissionBus;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.RoleMenuCheck;
import cn.cellcom.czt.po.TPermissionUrl;

import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class PermissionAction extends Struts2Base {
	public String showMenuTreeByRoleId() throws IOException {
		String roleId = getParameter("roleId");
		PermissionBus bus = new PermissionBus();
		List<RoleMenuCheck> list = bus.findMenuTreeByRoleId(roleId);
		System.out.println(JSONArray.fromObject(list).toString());
		PrintTool.print(getResponse(), JSONArray.fromObject(list), "json");
		return null;
	}

	public String showUrlByMenuId() throws IOException {
		String menuId = getParameter("menuId");
		PermissionBus bus = new PermissionBus();
		List<TPermissionUrl> list = bus.findUrlByMenuId(menuId);
		System.out.println(JSONArray.fromObject(list).toString());
		PrintTool.print(getResponse(), JSONArray.fromObject(list), "json");
		return null;
	}
	
	

	public String saveMenuPermission() throws IOException {
		String[] permissionIdStr = getRequest().getParameterValues(
				"permissionId");
		String menuId = getParameter("menuId");
		Data data = new Data(false, "失败");
		if (permissionIdStr != null && permissionIdStr.length > 0) {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				for (int i = 0; i < permissionIdStr.length; i++) {
					if (StringUtils.isNotBlank(permissionIdStr[i])) {
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"insert into t_menu_permission(menu_id,permission_id) values(?,?)",
								new Object[] { Integer.parseInt(menuId),
										Integer.parseInt(permissionIdStr[i]) });
					}
				}
				data.setState(true);
				data.setMsg("成功");
				PrintTool.print(getResponse(), JSONObject.fromObject(data),
						"json");
				return null;
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		PrintTool.print(getResponse(), JSONObject.fromObject(data), "json");
		return null;

	}

}
