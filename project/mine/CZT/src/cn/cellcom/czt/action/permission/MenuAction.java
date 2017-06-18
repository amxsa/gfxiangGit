package cn.cellcom.czt.action.permission;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.bus.permission.MenuBus;
import cn.cellcom.czt.common.CommonResponse;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TMenu;

import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class MenuAction extends Struts2Base {
	private static Log log = LogFactory.getLog(MenuAction.class);

	public String loadMenus() {
		MenuBus menuBus = new MenuBus();
		List<TMenu> list = menuBus.queryMenuTree();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", list);
		JSONObject json = JSONObject.fromObject(data);
		try {
			PrintTool.print(getResponse(), json.toString(), "json");
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	public String saveMenu() {
		String id = StringUtils.trimToNull(getParameter("id"));
		String name = StringUtils.trimToNull(getParameter("name"));
		String url = StringUtils.trimToNull(getParameter("url"));
		String target = StringUtils.trimToNull(getParameter("target"));
		String css = StringUtils.trimToNull(getParameter("css"));
		String icon = StringUtils.trimToNull(getParameter("icon"));
		String description = StringUtils
				.trimToNull(getParameter("description"));
		String parentId = StringUtils.trimToNull(getParameter("parentId"));
		String isLeaf = StringUtils.trimToNull(getParameter("isLeaf"));
		String priority = StringUtils.trimToNull(getParameter("priority"));
		String status = StringUtils.trimToNull(getParameter("status"));
		String cntidName = StringUtils.trimToNull(getParameter("cntidName"));
		String sqlCondition = StringUtils.trimToNull(getParameter("sqlCondition"));
		String levels = StringUtils.trimToNull(getParameter("levels"));
		TMenu menu = new TMenu();
		menu.setName(name);
		menu.setId(id == null ? null : Integer.parseInt(id));
		menu.setUrl(url);
		menu.setTarget(target);
		menu.setCss(css);
		menu.setIcon(icon);
		menu.setDescription(description);
		menu.setParentId(parentId == null ? null : Integer.parseInt(parentId));
		menu.setIsLeaf(isLeaf);
		menu.setPriority(priority == null ? null : Integer.parseInt(priority));
		menu.setStatus(status);
		menu.setCntidName(cntidName);
		menu.setSqlCondition(sqlCondition);
		menu.setLevels(levels==null?null:Integer.parseInt(levels));
		MenuBus menuBus = new MenuBus();
		try {
			TMenu record = menuBus.saveMenu(menu);
			CommonResponse cr = new CommonResponse();
			if (null != record) {
				cr.setSuccess(true);
				cr.setData(record);
			} else {
				cr.setSuccess(false);
			}
			PrintTool.print(getResponse(), JSONObject.fromObject(cr), "json");
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	public String saveRoleMenu() throws IOException {
		String menuId = getParameter("menuId");
		String roleId = getParameter("roleId");
		Data data = new Data(false, "操作失败");
		if (StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(roleId)) {
			String[] menuIds =  menuId.split(",");
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				jdbc.update(ApplicationContextTool.getDataSource(),
						"delete from  t_role_menu where role_id = ? ",
						new Object[] { Integer.parseInt(roleId) });
				for (int i = 0; i < menuIds.length; i++) {
					if (StringUtils.isNotBlank(menuIds[i])) {
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"insert into t_role_menu(role_id,menu_id,create_time) values(?,?,?)",
								new Object[] {
										Integer.parseInt(roleId),
										Integer.parseInt(menuIds[i]),
										DateTool.formateTime2String(new Date(),
												"yyyy-MM-dd HH:mm:ss") });
					}
				}
				data.setState(true);
				data.setMsg("操作成功");
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
