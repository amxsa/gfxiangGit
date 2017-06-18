package cn.cellcom.czt.action.permission;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.czt.po.TRole;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class RoleAction extends Struts2Base {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RoleAction.class);

	public String showRole() {
		String name = getParameter("nameParam");
		String description = getParameter("descriptionParam");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_role where 1=1  ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and name  like ? ");
			params.add("%" + name + "%");
			getRequest().setAttribute("nameParam", name);
		}
		if (StringUtils.isNotBlank(description)) {
			sql.append(" and description like  ?  ");
			params.add("%" + description + "%");
			getRequest().setAttribute("descriptionParam", description);
		}
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TRole> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TRole.class);
			if (pageResult != null) {
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showRole";
	}

	public String preAddRole() {

		return "addRole";
	}

	public String addRole() {

		String name = getParameter("name");
		String description = getParameter("description");
		String priority = getParameter("priority");
		try {
		 
		JDBC jdbc = ApplicationContextTool.getJdbc();
		int count;
		
			TRole role = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_role where name=?", TRole.class, new Object[]{name});
			if (role==null) {
				count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_role(name,priority,description,create_time) values(?,?,?,?)",
								new Object[] { name, priority, description,
										new Date() });
				if (count > 0) {
					PrintTool.print(getResponse(), "true|新增数据成功", null);
				}
			}else{
				PrintTool.print(getResponse(), "false|该角色名已存在", null);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				PrintTool.print(getResponse(), "false|未知错误", null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除角色
	 * 
	 * @return
	 * @throws IOException
	 */
	public String deleteRole() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {

			JDBC jdbc = ApplicationContextTool.getJdbc();
			Connection conn = null;

			try {
				StringBuffer sql = new StringBuffer();
				sql.append("delete from t_role  where  id = ?  ");
				conn = ApplicationContextTool.getConnection();
				conn.setAutoCommit(false);
				int count = jdbc.update(conn, sql.toString(),
						new Object[] { Integer.parseInt(id) });

				jdbc.update(conn, "delete from t_manager where roleid=?",
						new Object[] { Integer.parseInt(id) });
				jdbc.update(conn, "delete from t_role_menu where role_id=?",
						new Object[] { Integer.parseInt(id) });

				conn.commit();
				conn.setAutoCommit(true);
				if (count > 0) {
					PrintTool.print(getResponse(), "true|删除成功", null);
				} else {
					PrintTool.print(getResponse(), "false|删除失败", null);
				}
				PrintTool.print(getResponse(), "true|删除成功", null);
			} catch (Exception e) {
				try {
					if (conn != null)
						conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				log.error("删除联系人失败", e);
				PrintTool.print(getResponse(), "false|删除失败", null);
			} finally {
				jdbc.closeAll();
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择联系人", null);
		}
		return null;
	}

	public String preUpdateRole() {
		String id = getParameter("id");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			if (id != null) {
				TRole role = jdbc.queryForObject(
						ApplicationContextTool.getDataSource(),
						"select * from t_role where id=?", TRole.class,
						new Object[] { Integer.parseInt(id) });
				getRequest().setAttribute("role", role);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "updateRole";
	}

	public String updateRole() {
		String id = getParameter("id");
		String name = getParameter("name");
		String description = getParameter("description");
		String priority = getParameter("priority");
		String oldName = getParameter("oldName");//更新之前的名称
		try {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			
			if (name.equals(oldName)) {
				jdbc.update(ApplicationContextTool.getDataSource(),
						"update t_role set name=?,priority=?,description=?,create_time=? where id=?",
						new Object[] {name, priority, description,new Date(), Integer.parseInt(id)} );
				PrintTool.print(getResponse(), "true|更新成功", null);
			}else{
				TRole role = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
						"select * from t_role where name=?", TRole.class, new Object[]{name});
				if (role!=null) {
					PrintTool.print(getResponse(), "false|已存在该角色名", null);
				}else{
					jdbc.update(ApplicationContextTool.getDataSource(),
							"update t_role set name=?,priority=?,description=?,create_time=? where id=?",
							new Object[] {name, priority, description,new Date(), Integer.parseInt(id)} );
					PrintTool.print(getResponse(), "true|更新成功", null);
				}
			}
		} catch (Exception e1) {
			try {
				PrintTool.print(getResponse(), "false|更新失败", null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e1.printStackTrace();
		}
		return null;

	}

	public TRole getRole(Integer id) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			if (id != null) {
				TRole role = jdbc.queryForObject(
						ApplicationContextTool.getDataSource(),
						"select * from t_role where id=?", TRole.class,
						new Object[] { id });
				return role;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
