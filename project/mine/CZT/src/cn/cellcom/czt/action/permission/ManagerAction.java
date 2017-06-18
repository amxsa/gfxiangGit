package cn.cellcom.czt.action.permission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.TDepartment;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TRole;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class ManagerAction extends Struts2Base {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(RoleAction.class);
	
	public String showManager() {
		
		String name = getParameter("nameParam");
		String account = getParameter("accountParam");
		StringBuffer sql = new StringBuffer();
		sql.append(" select m.*,r.`name` 'role_name',d.`name` 'department_name'  ");
		sql.append(" from t_manager m LEFT JOIN t_role r on m.roleid=r.id  ");
		sql.append(" LEFT JOIN t_department d on m.department_id=d.id ");
		sql.append(" where account != 'admin' ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(name)) {
			sql.append(" and name  like ? ");
			params.add("%" + name + "%");
			getRequest().setAttribute("nameParam", name);
		}
		if (StringUtils.isNotBlank(account)) {
			sql.append(" and account like  ?  ");
			params.add("%"+account+"%");
			getRequest().setAttribute("accountParam", account);
		}
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TManager> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TManager.class);
			if (pageResult != null) {
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("areaCode", Env.AREACODE);
		return "showManager";
	}
	public String preAddManager(){
		setPageData();
		return "addManager";
	}
	public String addManager() {
		
		String account = getParameter("account");
		String name = getParameter("name");
		String telephone = getParameter("telephone");
		String password = getParameter("password");
		String password2 = getParameter("password2");
		String address = getParameter("address");
		String areacode = getParameter("areacode");
		String roleid = getParameter("roleid");
		String departmentId = getParameter("departmentId");
		try {
			if (!password.equals(password2)) {
				PrintTool.print(getResponse(), "false|密码不一致", null);
			}
			
			JDBC jdbc = ApplicationContextTool.getJdbc();
		
			TManager manager = jdbc.queryForObject(ApplicationContextTool.getDataSource(), 
					"select * from t_manager where account=?", TManager.class, new Object[]{account});
			if (manager==null) {
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_manager(account,password,name,telephone,address,areacode,roleid,state,reg_time,department_id)"
										+ "values(?,?,?,?,?,?,?,?,?,?)",
								new Object[] {account,password,name,telephone,address,areacode,roleid,"Y",DateTool.formateTime2String(new Date(),"yyyy-MM-dd HH-mm-ss"),departmentId });
				if (count > 0) {
					PrintTool.print(getResponse(), "true|新增数据成功", null);
				}
			}else{
				PrintTool.print(getResponse(), "false|该账号已被注册", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String deleteManager() throws IOException {
		String account = getParameter("account");
		if (StringUtils.isNotBlank(account)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from t_manager  where  account = ?  ");

			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				int count = jdbc.update(ApplicationContextTool.getDataSource(),
						sql.toString(), new Object[] {
								account });
				if (count > 0) {
					PrintTool.print(getResponse(), "true|删除成功", null);
				} else {
					PrintTool.print(getResponse(), "false|删除失败", null);
				}
				PrintTool.print(getResponse(), "true|删除成功", null);
			} catch (Exception e) {
				log.error("删除账户失败", e);
				PrintTool.print(getResponse(), "false|删除失败", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择账户", null);
		}
		return showManager();
	}
	/**
	 * 进入更新页面
	 * @return
	 */
	public String preUpdateManager(){
		String account = getParameter("account");
		JDBC jdbc = ApplicationContextTool.getJdbc();
				try {
					if (account!=null) {
						TManager manager = jdbc.queryForObject(
								ApplicationContextTool.getDataSource(),
								"select * from t_manager where account=?", TManager.class,
								new Object[] {account });
						getRequest().setAttribute("manager", manager);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		setPageData();
		return "updateManager";
	}
	
	public String updateManager(){
		String account = getParameter("account");
		String name = getParameter("name");
		String telephone = getParameter("telephone");
		String password = getParameter("password");
		String address = getParameter("address");
		String areacode = getParameter("areacode");
		String roleid = getParameter("roleid");
		String state = getParameter("state");
		String departmentId = getParameter("departmentId");
		
		try {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			jdbc.update(ApplicationContextTool.getDataSource(),
					"update t_manager set password=?,name=?,telephone=?,address=?,areacode=?,roleid=?,state=?,reg_time=?,department_id=? where account=?",
					new Object[] {password,name,telephone,address,areacode,roleid,state,DateTool.formateTime2String(new Date(),"yyyy-MM-dd HH-mm-ss"),departmentId,account});
				PrintTool.print(getResponse(), "true|更新成功", null);
			
		} catch (Exception e) {
			try {
				PrintTool.print(getResponse(), "false|更新失败", null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 设置页面下拉列表所需数据(角色，组织，地市)
	 */
	public void setPageData(){
		try {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			List<TRole> roleList = jdbc.query(ApplicationContextTool.getDataSource(),
					"select * from t_role where 1=1", TRole.class, null, 0, 0);
			List<TDepartment> departList = jdbc.query(ApplicationContextTool.getDataSource(),
					"select * from t_department where 1=1", TDepartment.class, null, 0, 0);
			if (roleList!=null) {
				getRequest().setAttribute("roleList", roleList);
			}
			if (departList!=null) {
				getRequest().setAttribute("departList", departList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getRequest().setAttribute("areaCode", Env.AREACODE);
	}
}
