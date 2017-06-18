package cn.cellcom.workhelp.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.ConnConf;
import cn.cellcom.web.struts.Struts2Base;
import cn.cellcom.workhelp.bus.DBOperateBus;
import cn.cellcom.workhelp.bus.WorkHelpCommon;
import cn.cellcom.workhelp.po.JdbcResult;
import cn.cellcom.workhelp.po.WorkSQL;

public class SQLAction extends Struts2Base {
	private static Log log = LogFactory.getLog(SQLAction.class);
	public static Map<String, String> PasswordKey = new HashMap<String, String>();
	private final static String SELECT = "select";
	private final static String INSERT = "insert";
	private final static String DELETE = "delete";
	private final static String UPDATE = "update";
	private final static String EXECUTE_FULL = "execute";
	private final static String EXECUTE = "exec";
	static {
		PasswordKey.put("0", ")");
		PasswordKey.put("1", "!");
		PasswordKey.put("2", "@");
		PasswordKey.put("3", "#");
		PasswordKey.put("4", "$");
		PasswordKey.put("5", "%");
		PasswordKey.put("6", "^");
		PasswordKey.put("7", "&");
	}

	public String login() {

		int weekIndex = new Date().getDay();
		String name = getParameter("name");
		String password = getParameter("password");
		String returnStr = "fail";
		String prePwd1 = null;
		String prePwd2 = "@$^*";
		switch (weekIndex) {
		case 0:
			prePwd1 = "cellcoM";
			break;
		case 1:
			prePwd1 = "Cellcom";
			break;
		case 2:
			prePwd1 = "cEllcom";
			break;
		case 3:
			prePwd1 = "ceLlcom";
			break;
		case 4:
			prePwd1 = "celLcom";
			break;
		case 5:
			prePwd1 = "cellCom";
			break;
		case 6:
			prePwd1 = "cellcOm";
			break;
		default:
			prePwd1 = "cellcom";
			break;
		}
		StringBuffer realPassword = new StringBuffer();
		realPassword.append(prePwd1).append(prePwd2)
				.append(PasswordKey.get(weekIndex + ""));
		System.out.println(">>>>>" + realPassword);
		if ("cellcom".equals(name) && password.equals(realPassword.toString())) {
			returnStr = "success";
			WorkSQL workSQL = new WorkSQL();
			workSQL.setUsername(name);
			workSQL.setPassword(password);
			getSession().setAttribute("workSQL", workSQL);
			return "success";
		} else {
			getRequest().setAttribute("msg", "用户名和密码不匹配");
			return "util";
		}
	}

	public String executeSql() throws Exception {
		WorkSQL workSQL = (WorkSQL) getSession().getAttribute("workSQL");
		if (workSQL == null) {
			return "util";
		}
		String sql = "";

		String param = getParameter("sql");
		String dataBaseName = getParameter("dataBaseName");
		getRequest().setAttribute("dataBaseName", dataBaseName);
		param = param == null ? "" : param;
		String[] sqls = param.split("\r\n");
		for (String str : sqls) {
			if (str.startsWith("--") || str.startsWith("//")) {
				continue;
			} else {
				sql = str;
			}
		}
		try {
			if (StringUtils.isNotBlank(sql)) {
				long start = System.currentTimeMillis();
				String[] strArr = sql.trim().split("\\s+");
				String firstWord = strArr[0];
				if (sql.indexOf("where") < 0
						&& (firstWord.equalsIgnoreCase(SELECT)
								|| firstWord.equalsIgnoreCase(UPDATE) || firstWord
									.equalsIgnoreCase(DELETE))) {
					throw new Exception("您所提交的SQL语句没带where子句,为了数据库安全,建议加上,谢谢合作");
				}
				if (firstWord.equalsIgnoreCase(EXECUTE)
						|| firstWord.equalsIgnoreCase(EXECUTE_FULL)) {

					// request.setAttribute("content", result.toString());
				} else if (firstWord.equalsIgnoreCase(SELECT)) {
					DBOperateBus dbBus = new DBOperateBus();
					JdbcResult result = dbBus.executeQuery(sql,init(dataBaseName));
					if (result != null) {
						getRequest().setAttribute("total", result.getCount());
						getRequest()
								.setAttribute("content", result.getResult());
					}
				} else if (firstWord.equalsIgnoreCase(UPDATE)
						|| firstWord.equalsIgnoreCase(DELETE)
						|| firstWord.equalsIgnoreCase(INSERT)) {
					DBOperateBus dbBus = new DBOperateBus();
					int rows = dbBus.executeUpdate(sql,init(dataBaseName));
					getRequest().setAttribute("count", rows);
				} else {
					// request.setAttribute("total",0);
					getRequest().setAttribute("content", "非法的sql语句或目前系统未支持");
				}
				getRequest().setAttribute("costTimes",
						(System.currentTimeMillis() - start));
				getRequest().setAttribute("sql", getParameter("sql"));

				log.info(getRequest().getRemoteAddr() + "成功执行SQL:" + sql);
			}
		} catch (SQLException e) {
			log.error("手工执行SQL", e);
			getRequest().setAttribute("result", e.getMessage());
			return "error";
		}
		return "success";
	}

	private ConnConf init(String dataBaseName) {
		if ("CZT_WX".equals(dataBaseName)) {
			ConnConf conf = new ConnConf(
					"com.mysql.jdbc.Driver",
					"root",
					"123456Qw",
					"jdbc:mysql://192.168.39.103:3306/CZT_WX?generateSimpleParameterMetadata=true&characterEncoding=utf8");
			return conf;
		}
		return null;

	}
}
