package cn.cellcom.czt.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.date.DateTool;

import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TMenu;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class LoginManagerAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(LoginManagerAction.class);

	

	public String login() {
		String username = getParameter("username");
		String password = getParameter("password");
		if (StringUtils.isNotBlank(username)
				&& StringUtils.isNotBlank(password)) {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				TManager po = jdbc
						.queryForObject(
								ApplicationContextTool.getDataSource(),
								"select * from t_manager where account = ? and password =  ? and state='Y' ",
								TManager.class, new String[] { username,
										password });
				if (po != null) {
					List<TMenu> list = loadMenu(po);
					log.info(JSONArray.fromObject(list).toString());
					log.info("用户名:"
							+ po.getAccount()
							+ "登录后台，登录时间:"
							+ DateTool.formateTime2String(new Date(),
									"yyyy-MM-dd HH:mm:ss"));
					getSession().setAttribute("menus", list);
					getSession().setAttribute("login", po);
					return "main";
				} else {
					getRequest().setAttribute("result", "用户名或密码错误");
					return "login";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			getRequest().setAttribute("result", "用户名或密码错误");
			return "login";
		}
		return "login";

	}

	private List<TMenu> loadMenu(TManager po) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			List<TMenu> list = null;
			
			if(po.getRoleid()==0){
				String sql="select * from t_menu where status='Y' ";
				list = jdbc
						.query(ApplicationContextTool.getDataSource(),
								sql,
								TMenu.class, null, 0, 0);
			}else{
				String sql="select c.*  from  t_role_menu b  left join  t_menu c on b.menu_id = c.id where b.role_id = ? and c.status='Y'  order by c.levels,c.priority ";
				list = jdbc
						.query(ApplicationContextTool.getDataSource(),
								sql,
								TMenu.class, new Object[] { po.getRoleid() }, 0, 0);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String logout() {
		Enumeration<String> sessions = getSession().getAttributeNames();
		while (sessions.hasMoreElements()) {
			getSession().removeAttribute(sessions.nextElement());
		}
		return "login";
	}

	public String updateManager() {
		String name = getParameter("name");
		String telephone = getParameter("telephone");
		String address = getParameter("address");
		if (StringUtils.isNotBlank(telephone)
				&& StringUtils.isNotBlank(address)) {
			if (telephone.length() > 20 || address.length() > 50) {
				getRequest().setAttribute("result", "电话号码或地址错误");
			} else {
				JDBC jdbc = ApplicationContextTool.getJdbc();
				TManager po = (TManager) getSession().getAttribute("login");
				try {
					int count = jdbc
							.update(ApplicationContextTool.getDataSource(),
									"update t_manager set name=? , telephone = ? , address = ? where account =  ?  ",
									new String[] { name, telephone, address,
											po.getAccount() });
					if (count > 0) {
						po.setAddress(address);
						po.setTelephone(telephone);
						po.setName(name);
						getRequest().setAttribute("result", "修改信息成功");
						return "updateManager";
					} else {
						getRequest().setAttribute("result", "修改信息失败");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			getRequest().setAttribute("result", "电话号码或地址错误");
		}
		return "error";
	}

	public String updatePassword() {
		String oldPassword = getParameter("oldPassword");
		String newPassword = getParameter("newPassword");
		String confirmPassword = getParameter("confirmPassword");
		TManager po = (TManager) getSession().getAttribute("login");
		if (po.getPassword().equals(oldPassword)) {
			if (StringUtils.isNotBlank(newPassword)) {
				if (newPassword.equals(confirmPassword)) {
					JDBC jdbc = ApplicationContextTool.getJdbc();
					int count;
					try {
						count = jdbc
								.update(ApplicationContextTool.getDataSource(),
										"update t_manager set password = ?  where account =  ?  ",
										new String[] { newPassword,
												po.getAccount() });
						if (count > 0) {
							po.setPassword(newPassword);
							getRequest().setAttribute("result", "修改密码成功");
							return "updatePassword";
						} else {
							getRequest().setAttribute("result", "修改密码失败");
						}
					} catch (SQLException e) {
						e.printStackTrace();
						log.error("修改密码失败", e);
					}
				} else {
					getRequest().setAttribute("result", "新旧密码不一致");
				}
			} else {
				getRequest().setAttribute("result", "新密码不能为空");
			}
		} else {
			getRequest().setAttribute("result", "旧密码错误");
		}
		return "error";
	}
}
