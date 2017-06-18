package com.gf.ims.web.acion;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gf.ims.common.db.bean.JdbcResult;
import com.gf.ims.common.db.jdbc.ConnConf;
import com.gf.ims.service.DBOperateService;

@Controller
@RequestMapping("/sql")
public class SQLAction {

	private final static String SELECT = "select";
	private final static String INSERT = "insert";
	private final static String DELETE = "delete";
	private final static String UPDATE = "update";
	private final static String EXECUTE_FULL = "execute";
	private final static String EXECUTE = "exec";
	private static final Log log = LogFactory.getLog(SQLAction.class);
	
	@Resource
	private DBOperateService dbOperateService;
	
	@RequestMapping("/sqlPage")
	public String sqlPage(HttpServletRequest request) throws Exception {
		return "/pages/main/sql";
	}
	
	@RequestMapping("/executeSql")
	public String executeSql(HttpServletRequest request) throws Exception {
		String sql = "";
		String param = request.getParameter("sql");
		String dataBaseName = request.getParameter("dataBaseName");
		request.setAttribute("dataBaseName", dataBaseName);
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
				if (sql.indexOf("where") < 0&& (firstWord.equalsIgnoreCase(SELECT)|| firstWord.equalsIgnoreCase(UPDATE) || firstWord.equalsIgnoreCase(DELETE))) {
					throw new Exception("您所提交的SQL语句没带where子句,为了数据库安全,建议加上,谢谢合作");
				}
				if (firstWord.equalsIgnoreCase(EXECUTE)|| firstWord.equalsIgnoreCase(EXECUTE_FULL)) {

					// request.setAttribute("content", result.toString());
				} else if (firstWord.equalsIgnoreCase(SELECT)) {
					JdbcResult result = dbOperateService.executeQuery(sql,init(dataBaseName));
					if (result != null) {
						request.setAttribute("total", result.getCount());
						request.setAttribute("content", result.getResult());
					}
				} else if (firstWord.equalsIgnoreCase(UPDATE)
						|| firstWord.equalsIgnoreCase(DELETE)
						|| firstWord.equalsIgnoreCase(INSERT)) {
					int rows = dbOperateService.executeUpdate(sql,init(dataBaseName));
					request.setAttribute("count", rows);
				} else {
					// request.setAttribute("total",0);
					request.setAttribute("content", "非法的sql语句或目前系统未支持");
				}
				request.setAttribute("costTimes",(System.currentTimeMillis() - start));
				request.setAttribute("sql",request.getParameter("sql"));

				log.info(request.getRemoteAddr() + "成功执行SQL:" + sql);
			}
		} catch (SQLException e) {
			log.error("手工执行SQL", e);
			request.setAttribute("result", e.getMessage());
			return "error";
		}
		return "/pages/main/sql";
	}

	
	private ConnConf init(String dataBaseName) {
		if (!"ims_yun".equals(dataBaseName)) {
			ConnConf conf = new ConnConf(
					"com.mysql.jdbc.Driver",
					"wsrp",
					"jl2012",
					"jdbc:mysql://127.0.0.1:3306/"+dataBaseName+"?generateSimpleParameterMetadata=true&characterEncoding=utf8");
			return conf;
		}
		return null;
	}
}
