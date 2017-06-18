package cn.cellcom.web.page;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;


public class PageBus {
	public <T> PageResult<T> queryPageDataOracle(JDBC jdbc,
			DataSource dataSource, PageTool page, String SQL,
			Object[] params, Class<T> clazz, String tableAlias)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		StringBuffer SQLCount = new StringBuffer();
		SQLCount.append("SELECT COUNT(1) AS CNT ");
		int a = PatternTool.indexOfFirst(SQL.toString(), "\\s+(?i)from");
		SQLCount.append(StringUtils.substring(SQL, a));
		Integer count = jdbc.queryForObject(dataSource, SQLCount.toString(),
				Integer.class,params);
		if (count == null || count <= 0) {
			return null;
		}
		page.init(page.getPageRecord(), count, page.getCurrentPage());
		List<T> list = jdbc.query(dataSource,
				appendOraclePageStr(SQL, page, tableAlias), clazz,
				params==null?null:params, 0, 0);
		if (list != null && list.size() > 0) {
			PageResult<T> result = new PageResult<T>();
			result.setPage(page);
			result.setContent(list);
			return result;
		}
		return null;
	}
	
	public <T> PageResult<T> queryPageData(JDBC jdbc,
			DataSource dataSource, PageTool page, String SQL,
			Object[] params, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		StringBuffer SQLCount = new StringBuffer();
		SQLCount.append("select count(*) ");
		int a = PatternTool.indexOfFirst(SQL.toString(), "\\s+(?i)from");
		SQLCount.append(StringUtils.substring(SQL, a));
		int b = PatternTool.indexOfFirst(SQLCount.toString(), "\\s+(?i)order\\s+(?i)by");
		String tempSql = SQLCount.toString();
		if(b>0)
			tempSql= StringUtils.substring(SQLCount.toString(),0, b);
		Integer count = jdbc.queryForObject(dataSource,tempSql ,
				Integer.class,params);
		if (count == null || count <= 0) {
			return null;
		}
		page.init(page.getPageRecord(), count, page.getCurrentPage());
		List<T> list = jdbc.query(dataSource,SQL, clazz,
				params==null?null:params, page.getStart(), page.getPageRecord());
		if (list != null && list.size() > 0) {
			PageResult<T> result = new PageResult<T>();
			result.setPage(page);
			result.setContent(list);
			return result;
		}
		return null;
	}
	
	public <T> PageResult<T> queryPageDataMysql(JDBC jdbc,
			DataSource dataSource, PageTool page, String SQL,
			Object[] params, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		StringBuffer SQLCount = new StringBuffer();
		SQLCount.append("SELECT COUNT(1) AS CNT ");
		int a = PatternTool.indexOfFirst(SQL.toString(), "\\s+(?i)from");
		SQLCount.append(StringUtils.substring(SQL, a));
		Integer count = jdbc.queryForObject(dataSource, SQLCount.toString(),
				Integer.class,params);
		if (count == null || count <= 0) {
			return null;
		}
		page.init(page.getPageRecord(), count, page.getCurrentPage());
		//StringBuffer SQLPage = new StringBuffer
		List<T> list = jdbc.query(dataSource,SQL+" limit "+page.getStart()+","+page.getPageRecord(), clazz,
				params==null?null:params,0, 0);
		if (list != null && list.size() > 0) {
			PageResult<T> result = new PageResult<T>();
			result.setPage(page);
			result.setContent(list);
			return result;
		}
		return null;
	}

	private String appendOraclePageStr(String SQL, PageTool page,
			String tableAlias) throws InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, SQLException {
		StringBuffer pageSQL = new StringBuffer("SELECT * FROM (");
		pageSQL.append(" SELECT ROWNUM NUM,").append(tableAlias)
				.append(".* FROM (");
		pageSQL.append(SQL);
		pageSQL.append(")").append(tableAlias == null ? "T" : tableAlias)
				.append(" WHERE ROWNUM<=");
		pageSQL.append(page.getSize()).append(") WHERE NUM >")
				.append(page.getStart());
		return pageSQL.toString();

	}
}
