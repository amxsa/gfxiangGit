package cn.cellcom.czt.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.TLinkman;
import cn.cellcom.czt.po.TManager;

import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class LinkmanManagerAction extends Struts2Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory
			.getLog(LinkmanManagerAction.class);

	public String showLinkman() {
		String name = getParameter("name");
		String telephone = getParameter("telephone");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_linkman where account =  ?  ");
		TManager manager = (TManager) getSession().getAttribute("login");
		List<Object> params = new ArrayList<Object>();
		params.add(manager.getAccount());
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and name  like ? ");
			params.add("%" + name + "%");
			getRequest().setAttribute("name", name);
		}
		if (StringUtils.isNotBlank(telephone)) {
			sql.append(" and telephone =  ?  ");
			params.add(telephone);
			getRequest().setAttribute("telephone", telephone);
		}
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TLinkman> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TLinkman.class);
			if (pageResult != null) {
				List<TLinkman> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					TLinkman po = null;
					for (int i = 0, len = list.size(); i < len; i++) {

					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showLinkman";

	}

	public String addLinkman() {
		String name = getParameter("name");
		String telephone = getParameter("telephone");
		String address = getParameter("address");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_linkman where account =  ?  and telephone = ?  ");
		TManager manager = (TManager) getSession().getAttribute("login");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			TLinkman linkman = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(), sql.toString(),
					TLinkman.class, new Object[] { manager.getAccount(),
							telephone });
			if (linkman == null) {
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_linkman(account,name,telephone,address) values(?,?,?,?)",
								new Object[] { manager.getAccount(), name,
										telephone, address });
				if (count > 0) {
					getRequest().setAttribute("success", "新增成功");
					return "success";
				}
			} else {
				getRequest().setAttribute("result", "该号码已存在");
				return "error";
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "error";
	}

	public String deleteLinkman() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from t_linkman  where account = ? and id = ?  ");

			TManager manager = (TManager) getSession().getAttribute("login");
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				int count = jdbc.update(ApplicationContextTool.getDataSource(),
						sql.toString(), new Object[] { manager.getAccount(),
								Long.valueOf(id) });
				if (count > 0) {
					PrintTool.print(getResponse(), "true|删除成功", null);
				} else {
					PrintTool.print(getResponse(), "false|删除失败", null);
				}
				PrintTool.print(getResponse(), "true|删除成功", null);
			} catch (Exception e) {
				log.error("删除联系人失败", e);
				PrintTool.print(getResponse(), "false|删除失败", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择联系人", null);
		}
		return null;
	}

}
