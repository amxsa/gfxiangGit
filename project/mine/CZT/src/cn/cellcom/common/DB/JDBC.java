package cn.cellcom.common.DB;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import javax.sql.DataSource;
import oracle.jdbc.driver.OracleResultSet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.bean.JavaBean;
import cn.cellcom.common.data.ArrayTool;
import cn.cellcom.common.data.DataTool;

public class JDBC {
	// private static final String SYBASE_SELECT_SYSOBJECTS =
	// "select * from sysobjects";
	private static Log log = LogFactory.getLog(JDBC.class);
	private ConnConf connProperty;
	private Connection conn;
	private PreparedStatement ps;
	private CallableStatement cs;
	private ResultSet rs;

	private boolean createNewPreparedStatement = true;

	private boolean createCallableStatement = true;

	private boolean mappingCheck = false;

	// private static Map<Connection, String> busyConnection = new Hashtable();

	public JDBC(ConnConf connProperty) {
		this.connProperty = connProperty;
	}

	public JDBC(String url, String user, String password) {
		this.connProperty = new ConnConf(null, user, password, url);
	}

	public boolean isCreateNewPreparedStatement() {
		return this.createNewPreparedStatement;
	}

	public void setCreateNewPreparedStatement(boolean createNewPreparedStatement) {
		this.createNewPreparedStatement = createNewPreparedStatement;
	}

	public boolean isCreateCallableStatement() {
		return this.createCallableStatement;
	}

	public void setCreateCallableStatement(boolean createCallableStatement) {
		this.createCallableStatement = createCallableStatement;
	}

	public boolean isMappingCheck() {
		return this.mappingCheck;
	}

	public void setMappingCheck(boolean mappingCheck) {
		this.mappingCheck = mappingCheck;
	}

	public Connection getConnection() throws SQLException {
		if (this.conn != null) {
			return this.conn;
		}
		try {
			if (this.connProperty != null) {
				if (StringUtils.isNotBlank(this.connProperty
						.getDriverClassName()))
					Class.forName(this.connProperty.getDriverClassName());
			}
			long start = System.currentTimeMillis();
			if (this.connProperty.getUser() == null)
				this.conn = DriverManager.getConnection(this.connProperty
						.getUrl());
			else
				this.conn = DriverManager.getConnection(
						this.connProperty.getUrl(),
						this.connProperty.getUser(),
						this.connProperty.getPassword());
			// long end = System.currentTimeMillis();
			// busyConnection.put(this.conn, DateTool.formateTime2String(
			// DateTool.getNow(), "yyyy-MM-dd HH:mm:ss"));
			// log.info("jdbc获取一个连接" + this.conn + ",花费" + (end - start)
			// + " ms,当前使用量:" + busyConnection.size() + ",连接属性为:"
			// + this.connProperty.toString());
			return this.conn;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(this.connProperty.getDriverClassName()
					+ "没找到", e);
		}

	}

	private Connection getConnection(DataSource ds) throws SQLException {
		Connection conn = null;
		if (ds != null) {
			long s = System.currentTimeMillis();
			conn = ds.getConnection();
			if (conn == null || (conn != null && conn.isClosed())) {
				log.info("connection is closed,try again ");
				conn = ds.getConnection();
			}
			long e = System.currentTimeMillis();
			log.info("get connection " + conn + " from " + ds + " cost "
					+ (e - s) + " ms");
		}
		return conn;
	}

	public PreparedStatement getPreparedStatement() {
		return this.ps;
	}

	public CallableStatement getCallableStatement() {
		return this.cs;
	}

	private CallableStatement getCallableStatement(Connection conn, String sql)
			throws SQLException {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("SQL语句不能为空");
		}
		if (conn == null && this.conn == null) {
			this.conn = getConnection();
		} else if (conn != null && this.conn == null) {
			this.conn = conn;
		}
		if (this.conn == null) {
			throw new RuntimeException("jdbc没有获得一个连接");
		}
		if (this.cs == null || createCallableStatement) {
			this.cs = this.conn.prepareCall(sql);
		}
		return this.cs;
	}

	private PreparedStatement getPreparedStatement(Connection conn, String sql,
			TYPE type) throws SQLException {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("SQL语句不能为空");
		}
		if (type == null) {
			throw new RuntimeException("必须指定一种操作类型,见JDBC.TYPE");
		}
		if (conn == null && this.conn == null) {
			this.conn = getConnection();
		} else if (conn != null && this.conn == null) {
			this.conn = conn;
		}
		if (this.conn == null) {
			throw new RuntimeException("jdbc没有获得一个连接");
		}
		if (this.ps == null || createNewPreparedStatement) {
			switch (type) {
			case QUERY:
				this.ps = this.conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				break;
			case PAGINATION:
				this.ps = this.conn
						.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
								ResultSet.CONCUR_READ_ONLY);
				break;
			case UPDATE:
				this.ps = this.conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				break;
			case INSERT:
				this.ps = this.conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);
				break;
			case DDL:
				this.ps = this.conn.prepareStatement(sql);
				break;
			default:
				this.ps = null;
				break;
			}
		}
		return this.ps;
	}

	private void closeResultSet() throws SQLException {
		if (this.rs != null) {
			this.rs.close();
			this.rs = null;
		}
	}

	private void closePreparedStatement() throws SQLException {
		if (this.ps != null) {
			this.ps.close();
			this.ps = null;
		}
	}

	private void closeConnection() throws SQLException {
		if (this.conn != null) {
			if (!this.conn.isClosed()) {
				this.conn.close();
			}
			this.conn = null;
		}
	}

	public void closeAll() {
		try {
			closeResultSet();
		} catch (SQLException e) {
			throw new RuntimeException("关闭成员ResultSet出错", e);
		} finally {
			try {
				closePreparedStatement();
			} catch (SQLException e) {
				throw new RuntimeException("关闭成员PreparedStatement出错", e);
			} finally {
				try {
					closeConnection();
				} catch (SQLException e) {
					throw new RuntimeException("关闭成员Connection出错", e);
				}
			}
		}
	}

	public int execProcedureUpdate(DataSource ds, String procedureName,
			Object[] params) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return execProcedureUpdate(conn, procedureName, params);
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public int execProcedureUpdate(Connection conn, String procedureName,
			Object[] params) {
		if (StringUtils.isBlank(procedureName)) {
			throw new RuntimeException("存储过程名字不能为空");
		}
		StringBuilder sBuilder = new StringBuilder("{call ");
		sBuilder.append(procedureName).append("(");

		StringBuilder paramsBuilder = new StringBuilder();
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				DataTool.makeupArrayStr(paramsBuilder, "?", ",");
			}
		}
		sBuilder.append(paramsBuilder);
		sBuilder.append(")}");
		String sql = sBuilder.toString();
		try {
			this.cs = getCallableStatement(conn, sql);
			if (this.cs != null) {
				if (!ArrayTool.isEmpty(params)) {
					for (int i = 0; i < params.length; i++) {
						this.cs.setObject(i + 1, params[i]);
					}
				}
				long start = System.currentTimeMillis();
				int effectRow = this.cs.executeUpdate();
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilderInfo = new StringBuilder("@");
					sBuilderInfo.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE PROCUDURE:")
							.append(procedureName);
					if (!ArrayTool.isEmpty(params)) {
						sBuilderInfo.append(",Parameters:").append(
								DataTool.createInClause(params));
					}
					sBuilderInfo.append(" 花费").append(end - start)
							.append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilderInfo.append(MemoryTool.getMemString());
					}
					log.info(sBuilderInfo.toString());
				}
				return effectRow;
			}
			throw new RuntimeException("成员CallableStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个存储过程失败,名称 = " + procedureName
					+ ",参数:" + DataTool.createInClause(params), e);
		} finally {
			if (conn == null) {
				closeAll();
			}
		}

	}

	public <T> List<T> execProcedureQuery(DataSource ds, String procedureName,
			Class<T> clazz, Object[] params, int offset, int length)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return execProcedureQuery(conn, procedureName, clazz, params,
					offset, length);
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public <T> List<T> execProcedureQuery(Connection conn,
			String procedureName, Class<T> clazz, Object[] params, int offset,
			int length) throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		if (StringUtils.isBlank(procedureName)) {
			throw new RuntimeException("存储过程名字不能为空");
		}
		if (clazz == null) {
			throw new RuntimeException("clazz不能为空");
		}
		StringBuilder sBuilder = new StringBuilder("{call ");
		sBuilder.append(procedureName).append("(");

		StringBuilder paramsBuilder = new StringBuilder();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				DataTool.makeupArrayStr(paramsBuilder, "?", ",");
			}
		}

		sBuilder.append(paramsBuilder);
		sBuilder.append(")}");
		String sql = sBuilder.toString();
		try {
			this.cs = getCallableStatement(conn, sql);
			if (this.cs != null) {
				if (!ArrayTool.isEmpty(params)) {
					for (int i = 0; i < params.length; i++) {
						this.cs.setObject(i + 1, params[i]);
					}
				}
				long start = System.currentTimeMillis();
				this.rs = this.cs.executeQuery();
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilderInfo = new StringBuilder("@");
					sBuilderInfo.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE PROCUDURE:")
							.append(procedureName);
					if (!ArrayTool.isEmpty(params)) {
						sBuilderInfo.append(",Parameters:").append(
								DataTool.createInClause(params));
					}
					sBuilderInfo.append(" 花费").append(end - start)
							.append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilderInfo.append(MemoryTool.getMemString());
					}
					log.info(sBuilderInfo.toString());
				}
				List<T> localList = null;
				if (offset == 0 && length == 0) {
					localList = resultSet2List(this.rs, clazz);
				} else {
					localList = resultSet2List(rs, clazz);
				}

				return localList;
			}
			throw new RuntimeException("成员CallableStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个存储过程失败,名称 = " + procedureName
					+ ",参数:" + DataTool.createInClause(params), e);
		} finally {
			if (conn == null) {
				closeAll();
			} else {
				closeResultSet();
			}
		}
	}

	public List<Object> execFunctionOracle(DataSource ds, String functionName,
			List<ProcedureParameter> procedureParameterList, int returnType)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return execFunctionOracle(conn, functionName,
					procedureParameterList, returnType);
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public List<Object> execFunctionOracle(Connection conn,
			String functionName,
			List<ProcedureParameter> procedureParameterList, int returnType)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		if (StringUtils.isBlank(functionName)) {
			throw new RuntimeException("函数名字不能为空");
		}
		if (procedureParameterList == null) {
			throw new RuntimeException("函数参数列表不能为null");
		}
		// 参数个数,包括in或out类型
		int parameterLen = procedureParameterList.size();
		StringBuilder sBuilder = new StringBuilder("{?=call ");
		sBuilder.append(functionName).append("(");

		StringBuilder paramsBuilder = new StringBuilder();
		for (int i = 0; i < parameterLen; i++)
			DataTool.makeupArrayStr(paramsBuilder, "?", ",");

		sBuilder.append(paramsBuilder);
		sBuilder.append(")}");
		String sql = sBuilder.toString();

		try {
			this.cs = getCallableStatement(conn, sql);
			if (this.cs != null) {
				this.cs.registerOutParameter(1, returnType);
				for (int i = 0; i < parameterLen; i++) {
					ProcedureParameter procedureParameter = procedureParameterList
							.get(i);
					if (procedureParameter.getType() == PROCEDURE_PARAMTER_TYPE.IN) {
						this.cs.setObject(i + 2,
								procedureParameter.getParameterValue());
					} else {
						if (procedureParameter.getParameterValue() == null
								|| procedureParameter.getParameterValue()
										.getClass() != Integer.class) {
							throw new RuntimeException(
									"函数输出参数不能为null且必须是Integer类型的");
						}
						this.cs.registerOutParameter(i + 2,
								(Integer) procedureParameter
										.getParameterValue());
					}
				}
				long start = System.currentTimeMillis();
				this.cs.execute();
				long end = System.currentTimeMillis();
				if (connProperty.isShowsql()) {
					StringBuilder sBuilderInfo = new StringBuilder("@");
					sBuilderInfo.append(connProperty.getUrl())
							.append(" JDBC EXECUTE FUNCTION:")
							.append(functionName);
					if (!ArrayTool.isEmpty(procedureParameterList)) {
						sBuilderInfo
								.append(",Parameters:")
								.append(DataTool
										.createInClause(procedureParameterList));
					}
					sBuilderInfo.append(" cost").append(end - start)
							.append(" ms");
					// if (connProperty.isShowjvm()) {
					// sBuilderInfo.append(MemoryUtil.getMemString());
					// }
					log.info(sBuilderInfo.toString());
				}

				List<Object> returnAndOput = new ArrayList<Object>();
				returnAndOput.add(this.cs.getObject(1));
				for (int i = 0; i < parameterLen; i++) {
					ProcedureParameter procedureParameter = procedureParameterList
							.get(i);
					if (procedureParameter.getType() == PROCEDURE_PARAMTER_TYPE.OUT) {
						Object object = this.cs.getObject(i + 2);
						returnAndOput.add(object);
					}
				}
				return returnAndOput;
			} else {
				throw new RuntimeException("成员CallableStatement为null");
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行一个函数失败,名称 = " + functionName + ",参数:"
					+ DataTool.createInClause(procedureParameterList), e);
		} finally {
			if (conn == null) {
				closeAll();
			} else {
				closeResultSet();
			}
		}
	}

	public ProcedureResult execProcedureQueryOracle(DataSource ds,
			String procedureName,
			List<ProcedureParameter> procedureParameterList)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			ProcedureResult localProcedureResult = execProcedureQueryOracle(
					conn, procedureName, procedureParameterList);
			return localProcedureResult;
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public ProcedureResult execProcedureQueryOracle(Connection conn,
			String procedureName,
			List<ProcedureParameter> procedureParameterList)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		if (StringUtils.isBlank(procedureName)) {
			throw new RuntimeException("存储过程名字不能为空");
		}
		if (procedureParameterList == null) {
			throw new RuntimeException("存储过程参数列表不能为null");
		}

		int parameterLen = procedureParameterList.size();

		StringBuilder sBuilder = new StringBuilder("{call ");
		sBuilder.append(procedureName).append("(");

		StringBuilder paramsBuilder = new StringBuilder();
		for (int i = 0; i < parameterLen; i++) {
			DataTool.makeupArrayStr(paramsBuilder, "?", ",");
		}
		sBuilder.append(paramsBuilder);
		sBuilder.append(")}");
		String sql = sBuilder.toString();
		try {
			this.cs = getCallableStatement(conn, sql);
			if (this.cs != null) {
				ProcedureParameter procedureParameter = null;
				for (int i = 0; i < parameterLen; i++) {
					procedureParameter = procedureParameterList.get(i);
					if (procedureParameter.getType() == PROCEDURE_PARAMTER_TYPE.IN) {
						this.cs.setObject(i + 1,
								procedureParameter.getParameterValue());
					} else {
						if ((procedureParameter.getParameterValue() == null)
								|| (procedureParameter.getParameterValue()
										.getClass() != Integer.class)) {
							throw new RuntimeException(
									"存储过程输出参数不能为null且必须是Integer类型的");
						}
						this.cs.registerOutParameter(i + 1,
								((Integer) procedureParameter
										.getParameterValue()).intValue());
					}
				}

				long start = System.currentTimeMillis();
				this.cs.execute();
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilderInfo = new StringBuilder("@");
					sBuilderInfo.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE PROCUDURE:")
							.append(procedureName);
					if (!ArrayTool.isEmpty(procedureParameterList)) {
						sBuilderInfo
								.append(",Parameters:")
								.append(DataTool
										.createInClause(procedureParameterList));
					}
					sBuilderInfo.append(" 花费").append(end - start)
							.append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilderInfo.append(MemoryTool.getMemString());
					}
					log.info(sBuilderInfo.toString());
				}
				ProcedureResult pr = new ProcedureResult();
				List<Object> outputList = new ArrayList<Object>();
				List<JdbcResultSet> resultList = new ArrayList<JdbcResultSet>();
				for (int i = 0; i < parameterLen; i++) {
					procedureParameter = (ProcedureParameter) procedureParameterList
							.get(i);
					if (procedureParameter.getType() == PROCEDURE_PARAMTER_TYPE.OUT) {
						Object object = this.cs.getObject(i + 1);
						if ((object instanceof OracleResultSet)) {
							JdbcResultSet jsr = processResultSet((ResultSet) object);
							resultList.add(jsr);
						} else {
							outputList.add(object);
						}
					}
				}
				pr.setOutputList(outputList);
				pr.setResultList(resultList);

				return pr;
			}
			throw new RuntimeException("成员CallableStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个存储过程失败,名称 = " + procedureName
					+ ",参数:" + DataTool.createInClause(procedureParameterList),
					e);
		} finally {
			if (conn == null) {
				closeAll();
			} else {
				closeResultSet();
			}
		}

	}

	public ProcedureResult execProcedure(DataSource ds, String procedureName,
			List<ProcedureParameter> procedureParameterList)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return execProcedure(conn, procedureName, procedureParameterList);
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}

	}

	public ProcedureResult execProcedure(Connection conn, String procedureName,
			List<ProcedureParameter> procedureParameterList)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		if (StringUtils.isBlank(procedureName)) {
			throw new RuntimeException("存储过程名字不能为空");
		}

		ProcedureResult procedureResult = new ProcedureResult();
		List<JdbcResultSet> resultList = new ArrayList<JdbcResultSet>();
		List<Object> outputList = new ArrayList<Object>();

		StringBuilder sBuilder = new StringBuilder("{call ");
		sBuilder.append(procedureName).append("(");

		StringBuilder paramsBuilder = new StringBuilder();
		if (!ArrayTool.isEmpty(procedureParameterList)) {
			for (int i = 0, len = procedureParameterList.size(); i < len; i++) {
				DataTool.makeupArrayStr(paramsBuilder, "?", ",");
			}
		}

		sBuilder.append(paramsBuilder);
		sBuilder.append(")}");
		String sql = sBuilder.toString();
		try {
			this.cs = getCallableStatement(conn, sql);
			if (this.cs != null) {
				if (!ArrayTool.isEmpty(procedureParameterList)) {
					for (int i = 0, len = procedureParameterList.size(); i < len; i++) {
						ProcedureParameter procedureParameter = (ProcedureParameter) procedureParameterList
								.get(i);
						if (procedureParameter.getType() == PROCEDURE_PARAMTER_TYPE.IN) {
							this.cs.setObject(i + 1,
									procedureParameter.getParameterValue());
						} else {
							if ((procedureParameter.getParameterValue() == null)
									|| (procedureParameter.getParameterValue()
											.getClass() != Integer.class)) {
								throw new RuntimeException(
										"存储过程输出参数不能为null且必须是Integer类型的");
							}
							this.cs.registerOutParameter(i + 1,
									((Integer) procedureParameter
											.getParameterValue()).intValue());
						}
					}
				}

				long start = System.currentTimeMillis();
				try {
					this.rs = this.cs.executeQuery();
					JdbcResultSet jrs = null;
					jrs = processResultSet(this.rs);
					resultList.add(jrs);

					while (this.cs.getMoreResults()) {
						this.rs = this.cs.getResultSet();
						jrs = processResultSet(this.rs);
						resultList.add(jrs);
					}
				} catch (Exception e) {

				}
				if (!ArrayTool.isEmpty(procedureParameterList)) {
					for (int i = 0, len = procedureParameterList.size(); i < len; i++) {
						ProcedureParameter procedureParameter = (ProcedureParameter) procedureParameterList
								.get(i);
						if (procedureParameter.getType() == PROCEDURE_PARAMTER_TYPE.OUT) {
							outputList.add(this.cs.getObject(i + 1));
						}
					}
				}

				long end = System.currentTimeMillis();

				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilderInfo = new StringBuilder("@");
					sBuilderInfo.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE PROCUDURE:")
							.append(procedureName);
					if (!ArrayTool.isEmpty(procedureParameterList)) {
						sBuilderInfo
								.append(",Parameters:")
								.append(DataTool
										.createInClause(procedureParameterList));
					}
					sBuilderInfo.append(" 花费").append(end - start)
							.append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilderInfo.append(MemoryTool.getMemString());
					}
					log.info(sBuilderInfo.toString());
				}

				procedureResult.setOutputList(outputList);
				procedureResult.setResultList(resultList);
				ProcedureResult localProcedureResult1 = procedureResult;
				return localProcedureResult1;
			}
			throw new RuntimeException("成员CallableStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个存储过程失败,名称 = " + procedureName
					+ ",参数:" + DataTool.createInClause(procedureParameterList),
					e);
		} finally {
			if (conn == null) {
				closeAll();
			} else {
				closeResultSet();
			}
		}

	}

	public Object[] execProcedure(DataSource ds, String procedureName,
			Object[] inParams, Integer[] outParams) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			long start = System.currentTimeMillis();
			conn = ds.getConnection();
			long end = System.currentTimeMillis();
			log.info("update 获取连接花费" + (end - start) + "ms");
			Object[] arrayOfObject = execProcedure(conn, procedureName,
					inParams, outParams);
			return arrayOfObject;
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public Object[] execProcedure(Connection conn, String procedureName,
			Object[] inParams, Integer[] outParams) {
		if (StringUtils.isBlank(procedureName)) {
			throw new RuntimeException("存储过程名字不能为空");
		}

		int inParamsCount = ArrayTool.isEmpty(inParams) ? 0 : inParams.length;
		int outParamsCount = ArrayTool.isEmpty(outParams) ? 0
				: outParams.length;

		StringBuilder sBuilder = new StringBuilder("{call ");
		sBuilder.append(procedureName);
		sBuilder.append("(");

		StringBuilder paramsBuilder = new StringBuilder();
		for (int i = 0; i < inParamsCount + outParamsCount; i++) {
			DataTool.makeupArrayStr(paramsBuilder, "?", ",");
		}
		sBuilder.append(paramsBuilder);
		sBuilder.append(")}");
		String sql = sBuilder.toString();
		Object[] result = new Object[outParamsCount];
		try {
			this.cs = getCallableStatement(conn, sql);
			if (this.cs != null) {
				for (int i = 0; i < inParamsCount; i++) {
					this.cs.setObject(i + 1, inParams[i]);
				}
				for (int i = 0; i < outParamsCount; i++) {
					int newIndex = inParamsCount + i + 1;
					this.cs.registerOutParameter(newIndex,
							outParams[i].intValue());
				}
				long start = System.currentTimeMillis();
				this.cs.execute();
				for (int i = 0; i < outParamsCount; i++) {
					int newIndex = inParamsCount + i + 1;
					if (outParams[i].intValue() == -10) {
						this.rs = ((ResultSet) this.cs.getObject(newIndex));
						result[i] = processResultSet(this.rs);
					} else {
						result[i] = this.cs.getObject(newIndex);
					}
				}
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilderInfo = new StringBuilder("@");
					sBuilderInfo.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE PROCUDURE:")
							.append(procedureName);
					if (!ArrayTool.isEmpty(inParams)) {
						sBuilderInfo.append(",Parameters:").append(
								DataTool.createInClause(inParams));
					}
					sBuilderInfo.append(" 花费").append(end - start)
							.append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilderInfo.append(MemoryTool.getMemString());
					}
					log.info(sBuilderInfo.toString());
				}
				Object[] arrayOfObject1 = result;
				return arrayOfObject1;
			}
			throw new RuntimeException("成员CallableStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个存储过程失败,名称 = " + procedureName
					+ ",参数:" + DataTool.createInClause(inParams), e);
		} finally {
			if (conn == null) {
				closeAll();
			}
		}

	}

	public <T> T queryForObject(DataSource ds, String sql, Class<T> clazz,
			Object[] params) throws InstantiationException,
			IllegalAccessException, InvocationTargetException, SQLException,
			NoSuchMethodException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return queryForObject(conn, sql, clazz, params);

		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public <T> T queryForObject(Connection conn, String sql, Class<T> clazz,
			Object[] params) throws InstantiationException,
			IllegalAccessException, InvocationTargetException, SQLException,
			NoSuchMethodException {
		List<T> list = query(conn, sql, clazz, params, 0, 0);
		if (ArrayTool.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	public <T> List<T> query(DataSource ds, String sql, Class<T> clazz,
			Object[] params, int offset, int length)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return query(conn, sql, clazz, params, offset, length);
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public <T> List<T> query(Connection conn, String sql, Class<T> clazz,
			Object[] params, int offset, int length)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("SQL语句不能为空");
		}
		if (clazz == null)
			throw new RuntimeException("clazz不能为空");
		try {
			this.ps = getPreparedStatement(conn, sql, TYPE.QUERY);
			if (this.ps != null) {
				if (!ArrayTool.isEmpty(params)) {
					for (int i = 0; i < params.length; i++) {
						this.ps.setObject(i + 1, params[i]);
					}
				}

				long start = System.currentTimeMillis();
				this.rs = this.ps.executeQuery();
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilder = new StringBuilder("@");

					sBuilder.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE SQL:").append(sql);
					if (!ArrayTool.isEmpty(params)) {
						sBuilder.append(",Parameters:").append(
								DataTool.createInClause(params));
					}
					sBuilder.append(" 花费").append(end - start).append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilder.append(MemoryTool.getMemString());
					}
					log.info(sBuilder.toString());
				}
				List<T> localList = null;
				if (offset == 0 && length == 0) {
					localList = resultSet2List(this.rs, clazz);
				} else {
					localList = resultSet2List(this.rs, clazz, offset, length);
				}

				return localList;
			}
			throw new RuntimeException("成员PreparedStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个sql失败,sql = " + sql
					+ ",Parameters:" + DataTool.createInClause(params), e);
		} finally {
			if (conn == null) {
				closeAll();
			}
		}

	}

	public List<Map<String, Object>> query(DataSource ds, String sql,
			Object[] params, int offset, int length)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return query(conn, sql, params, offset, length);
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public List<Map<String, Object>> query(Connection conn, String sql,
			Object[] params, int offset, int length)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("SQL语句不能为空");
		}

		try {
			this.ps = getPreparedStatement(conn, sql, TYPE.QUERY);
			if (this.ps != null) {
				if (!ArrayTool.isEmpty(params)) {
					for (int i = 0; i < params.length; i++) {
						this.ps.setObject(i + 1, params[i]);
					}
				}

				long start = System.currentTimeMillis();
				this.rs = this.ps.executeQuery();
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilder = new StringBuilder("@");

					sBuilder.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE SQL:").append(sql);
					if (!ArrayTool.isEmpty(params)) {
						sBuilder.append(",Parameters:").append(
								DataTool.createInClause(params));
					}
					sBuilder.append(" 花费").append(end - start).append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilder.append(MemoryTool.getMemString());
					}
					log.info(sBuilder.toString());
				}
				List<Map<String, Object>> localList = null;
				if (offset == 0 && length == 0) {
					localList = resultSet2List(this.rs);
				} else {
					localList = resultSet2List(this.rs, offset, length);
				}

				return localList;
			}
			throw new RuntimeException("成员PreparedStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个sql失败,sql = " + sql
					+ ",Parameters:" + DataTool.createInClause(params), e);
		} finally {
			if (conn == null) {
				closeAll();
			}
		}

	}

	public JdbcResultSet query(DataSource ds, String sql, Object[] params)
			throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return query(conn, sql, params);

		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public JdbcResultSet query(Connection conn, String sql, Object[] params)
			throws SQLException {
		if (StringUtils.isBlank(sql))
			throw new RuntimeException("SQL语句不能为空");
		try {
			this.ps = getPreparedStatement(conn, sql, TYPE.QUERY);
			if (this.ps != null) {
				if (!ArrayTool.isEmpty(params)) {
					for (int i = 0; i < params.length; i++) {
						this.ps.setObject(i + 1, params[i]);
					}
				}

				long start = System.currentTimeMillis();
				this.rs = this.ps.executeQuery();
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilder = new StringBuilder("@");
					sBuilder.append(this.connProperty.getUrl())
							.append(" JDBC EXECUTE SQL:").append(sql);
					if (!ArrayTool.isEmpty(params)) {
						sBuilder.append(",Parameters:").append(
								DataTool.createInClause(params));
					}
					sBuilder.append(" 花费").append(end - start).append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilder.append(MemoryTool.getMemString());
					}
					log.info(sBuilder.toString());
				}
				JdbcResultSet localJdbcResultSet = processResultSet(this.rs);
				return localJdbcResultSet;
			}
			throw new RuntimeException("成员PreparedStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个sql失败,sql = " + sql
					+ ",Parameters:" + DataTool.createInClause(params), e);
		} finally {
			if (conn == null) {
				closeAll();
			}
		}

	}

	public int update(DataSource ds, String sql, Object[] params)
			throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			int i = update(conn, sql, params);
			return i;
		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public int update(Connection conn, String sql, Object[] params)
			throws SQLException {
		long start = System.currentTimeMillis();
		if (StringUtils.isBlank(sql))
			throw new RuntimeException("SQL语句不能为空");
		try {
			this.ps = getPreparedStatement(conn, sql, TYPE.UPDATE);
			if (this.ps != null) {
				if (!ArrayTool.isEmpty(params)) {
					for (int i = 0; i < params.length; i++) {
						this.ps.setObject(i + 1, params[i]);
					}

				}

				int effectRow = this.ps.executeUpdate();
				long end = System.currentTimeMillis();
				if (this.connProperty.isShowsql()) {
					StringBuilder sBuilder = new StringBuilder("@");
					sBuilder.append(this.connProperty.getUrl());
					sBuilder.append(" JDBC EXECUTE SQL::" + sql);
					if (!ArrayTool.isEmpty(params)) {
						sBuilder.append(",Parameters:").append(
								DataTool.createInClause(params));
					}
					sBuilder.append(" 花费").append(end - start).append("ms ");
					if (this.connProperty.isShowjvm()) {
						sBuilder.append(MemoryTool.getMemString());
					}
					log.info(sBuilder.toString());
				}
				int i = effectRow;
				return i;
			}
			throw new RuntimeException("成员PreparedStatement为null");
		} catch (SQLException e) {
			throw new RuntimeException("执行一个sql失败,sql = " + sql
					+ ",Parameters:" + DataTool.createInClause(params), e);
		} finally {
			// 加上else，oracle下批量操作要关闭
			if (conn == null) {
				closeAll();
			} else {
				closePreparedStatement();
			}
		}

	}

	public <T> T insert(DataSource ds, String sql, Class<T> clazz,
			Object[] params) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			this.conn = conn;
			return insert(conn, sql, clazz, params);

		} catch (SQLException e) {
			throw new RuntimeException("从池里获取连接失败", e);
		} finally {
			closeAll();
		}
	}

	public <T> T insert(Connection conn, String sql, Class<T> clazz,
			Object[] params) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("SQL语句不能为空");
		}
		if (clazz == null) {
			throw new RuntimeException("clazz不能为空");
		}
		String firstWord = getFirstWord(sql);
		if (!"insert".equals(firstWord.toLowerCase())) {
			throw new RuntimeException("本方法只为insert语句服务");
		}
		try {
			this.ps = getPreparedStatement(conn, sql, TYPE.INSERT);
			if (this.ps != null) {
				if (!ArrayTool.isEmpty(params)) {
					Integer paramCount = getParameterCount(this.ps, sql);
					if (paramCount != null && paramCount != params.length) {
						throw new RuntimeException("实际参数和sql中的参数(" + paramCount
								+ ")个数不匹配,sql=" + sql + ",参数:"
								+ DataTool.createInClause(params));
					} else {
						for (int i = 0; i < params.length; i++) {
							this.ps.setObject(i + 1, params[i]);
						}
					}
				}
				long start = System.currentTimeMillis();
				this.ps.executeUpdate();
				long end = System.currentTimeMillis();
				if (connProperty.isShowsql()) {
					StringBuilder sBuilder = new StringBuilder("@");
					sBuilder.append(connProperty.getUrl());
					sBuilder.append(" JDBC EXECUTE SQL::" + sql);
					if (!ArrayTool.isEmpty(params)) {
						sBuilder.append(",Parameters:").append(
								DataTool.createInClause(params));
					}
					sBuilder.append(" cost").append(end - start).append(" ms");
					// if (connProperty.isShowjvm()) {
					// sBuilder.append(MemoryUtil.getMemString());
					// }
					log.info(sBuilder.toString());
				}

				this.rs = this.ps.getGeneratedKeys();
				if (getColumnNames(this.rs).length == 1
						&& clazz != String.class) {
					throw new RuntimeException("非复合主键返回的类型只能为String");
				}
				if (this.rs.next()) {
					if (clazz == String.class) {
						Object obj = rs.getObject(1);
						Constructor<T> con = clazz.getConstructor(clazz);
						return con.newInstance(String.valueOf(obj));
					} else {
						return result2Object(rs, clazz);
					}
				} else {
					return null;
				}
			} else {
				throw new RuntimeException("成员PreparedStatement为null");
			}
		} catch (SQLException e) {
			throw new RuntimeException("执行一条sql失败,sql = " + sql, e);
		} finally {
			if (conn == null) {
				closeAll();
			} else {
				closeResultSet();
			}
		}
	}

	private <T> List<T> resultSet2List(ResultSet rs, Class<T> clazz)
			throws SQLException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<T> list = new ArrayList<T>();
		String[] columnNames = getColumnNames(rs);

		while (rs.next()) {
			resultSet2List(columnNames, rs, clazz, list);
		}
		return list;
	}

	private List<Map<String, Object>> resultSet2List(ResultSet rs)
			throws SQLException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] columnNames = getColumnNames(rs);
		while (rs.next()) {
			resultSet2List(columnNames, rs, list);
		}
		return list;
	}

	private List<Map<String, Object>> resultSet2List(ResultSet rs, int offset,
			int length) throws SQLException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] columnNames = getColumnNames(rs);
		int k = 0;
		while (rs.next()) {
			if ((k > offset - 1) && (k < offset + length)) {
				resultSet2List(columnNames, rs, list);
			}
			if (k >= offset + length)
				break;
			k++;

		}
		return list;
	}

	private <T> List<T> resultSet2List(ResultSet rs, Class<T> clazz,
			int offset, int length) throws SQLException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<T> list = new ArrayList<T>();
		String[] columnNames = getColumnNames(rs);
		int k = 0;
		while (rs.next()) {
			if ((k > offset - 1) && (k < offset + length)) {
				resultSet2List(columnNames, rs, clazz, list);
			}
			if (k >= offset + length)
				break;
			k++;

		}
		return list;
	}

	private void resultSet2List(String[] columnNames, ResultSet rs,
			Class clazz, List list) throws SQLException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Object t = null;
		if ((clazz == Integer.class) || (clazz == Integer.TYPE)) {
			Integer columnValue = Integer.valueOf(rs.getInt(columnNames[0]));
			t = columnValue;
		} else if ((clazz == Long.class) || (clazz == Long.TYPE)) {
			Long columnValue = Long.valueOf(rs.getLong(columnNames[0]));
			t = columnValue;
		} else if ((clazz == Float.class) || (clazz == Float.TYPE)) {
			Float columnValue = Float.valueOf(rs.getFloat(columnNames[0]));
			t = columnValue;
		} else if ((clazz == Double.class) || (clazz == Double.TYPE)) {
			Double columnValue = Double.valueOf(rs.getDouble(columnNames[0]));
			t = columnValue;
		} else if ((clazz == Date.class) || (clazz == Timestamp.class)) {
			Timestamp columnValue = rs.getTimestamp(columnNames[0]);
			t = columnValue;
		} else if (JavaBean.isJavaString(clazz)) {
			String columnValue = rs.getString(columnNames[0]);
			columnValue = StringUtils.trim(columnValue);
			t = columnValue;
		} else {
			t = clazz.newInstance();
			for (int i = 0, len = columnNames.length; i < len; i++) {
				Object columnValue = rs.getObject(columnNames[i]);
				if (columnValue != null) {
					if (JavaBean.isJavaString(columnValue.getClass())) {
						String str = (String) columnValue;
						columnValue = StringUtils.trim(str);
					}
				}
				String fieldName = null;
				if (this.connProperty.getDriverClassName().indexOf("sybase") >= 0)
					fieldName = JavaBean.columnName2FiledName(columnNames[i]);
				else if (this.connProperty.getDriverClassName().indexOf(
						"oracle") >= 0)
					fieldName = JavaBean
							.columnName2FiledNameOracle(columnNames[i]);
				else {
					fieldName = JavaBean.columnName2FiledName(columnNames[i]);
				}
				if (this.mappingCheck) {
					if (columnValue == null)
						continue;
					PropertyUtils.setProperty(t, fieldName, columnValue);
				} else {
					// 不抛NoSuchMethodException异常

					if (columnValue != null) {
						String javaType = columnValue.getClass().getName();
						// System.out.println(columnNames[i]+"@@@"+fieldName+"###"+columnValue+"###"+columnValue.getClass().getName());
						if ("oracle.sql.TIMESTAMP".equals(javaType)) {
							columnValue = ((oracle.sql.TIMESTAMP) columnValue)
									.timestampValue();
						} else if ("oracle.sql.CLOB".equals(javaType)) {
							columnValue = ((oracle.sql.CLOB) columnValue)
									.stringValue();
						} else if ("net.sourceforge.jtds.jdbc.ClobImpl"
								.equals(javaType)) {
							net.sourceforge.jtds.jdbc.ClobImpl clob = ((net.sourceforge.jtds.jdbc.ClobImpl) columnValue);
							long leng = clob.length();
							columnValue = clob.getSubString(1, (int) leng);
						}
						BeanUtils.setProperty(t, fieldName, columnValue);
					}
				}
			}
		}

		list.add(t);

	}

	private void resultSet2List(String[] columnNames, ResultSet rs,
			List<Map<String, Object>> list) throws SQLException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0, len = columnNames.length; i < len; i++) {
			Object columnValue = rs.getObject(columnNames[i]);
			if (columnValue != null) {
				if (JavaBean.isJavaString(columnValue.getClass())) {
					String str = (String) columnValue;
					columnValue = StringUtils.trim(str);
				}
			}
			String fieldName = null;
			if (this.connProperty.getDriverClassName().indexOf("sybase") >= 0)
				fieldName = JavaBean.columnName2FiledName(columnNames[i]);
			else if (this.connProperty.getDriverClassName().indexOf("oracle") >= 0)
				fieldName = JavaBean.columnName2FiledNameOracle(columnNames[i]);
			else {
				fieldName = JavaBean.columnName2FiledName(columnNames[i]);
			}
			map.put(fieldName, columnValue);

		}
		list.add(map);

	}

	private JdbcResultSet processResultSet(ResultSet rs) throws SQLException {
		JdbcResultSet resultSet = new JdbcResultSet();
		String[] columnNames = getColumnNames(rs);
		resultSet.setFields(Arrays.asList(columnNames));
		List<Object[]> datas = new ArrayList<Object[]>();
		while (rs.next()) {
			Object[] t = new Object[columnNames.length];
			for (int i = 0; i < columnNames.length; i++) {
				Object columnValue = rs.getObject(i + 1);
				if (columnValue != null) {
					String javaType = columnValue.getClass().getName();
					if (JavaBean.isJavaString(columnValue.getClass())) {
						String str = (String) columnValue;
						columnValue = StringUtils.trim(str);
					} else if ("oracle.sql.TIMESTAMP".equals(javaType)) {
						columnValue = ((oracle.sql.TIMESTAMP) columnValue)
								.timestampValue();
					} else if ("oracle.sql.CLOB".equals(javaType)) {
						columnValue = ((oracle.sql.CLOB) columnValue)
								.stringValue();
					} else if ("net.sourceforge.jtds.jdbc.ClobImpl"
							.equals(javaType)) {
						net.sourceforge.jtds.jdbc.ClobImpl clob = ((net.sourceforge.jtds.jdbc.ClobImpl) columnValue);
						long len = clob.length();
						columnValue = clob.getSubString(1, (int) len);
					}
				}
				t[i] = columnValue;
			}
			datas.add(t);
			t = null;
		}
		resultSet.setDatas(datas);
		return resultSet;
	}

	// public void format(JdbcResultSet resultSet, String destFile)
	// {
	// List<String> fields = resultSet.getFields();
	// StringBuilder sbField = new StringBuilder();
	// for (String field : fields) {
	// DataTool.makeupArrayStr(sbField, field, "\t");
	// }
	// sbField.append("\n");
	// List<Object[]> datas = resultSet.getDatas();
	// StringBuilder sbDatas = new StringBuilder();
	// for (Object[] line : datas) {
	// StringBuilder sbLine = new StringBuilder();
	// for (Object obj : line) {
	// DataTool.makeupArrayStr(sbLine,
	// DataTool.getDefalutVal(obj, "NULL"), "\t");
	// }
	// DataTool.makeupArrayStr(sbDatas, sbLine, "\n");
	// }
	// String finalString = sbDatas;
	// if (StringUtils.isBlank(destFile))
	// DataTool.p(finalString);
	// else
	// try {
	// String extension = FilenameUtils.getExtension(destFile);
	// if (Arrays.asList(new String[] { "xls", "xlsx" }).contains(extension)) {
	// ExcelTemplate.ExcelVersion version = ExcelTemplate.ExcelVersion.XLS;
	// version = "xlsx".equals(extension) ? ExcelTemplate.ExcelVersion.XLSX :
	// ExcelTemplate.ExcelVersion.XLS;
	// ExcelUtil.writerExcel("sheet1", resultSet, version,
	// new FileOutputStream(new File(destFile)));
	// }
	// else {
	// FileUtils.writeStringToFile(new File(destFile), finalString);
	// }
	// } catch (IOException e) {
	// log.error("", e);
	// throw new RuntimeException("输出文件时异常");
	// }
	// }

	private <T> T result2Object(ResultSet rs, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, SQLException, NoSuchMethodException {
		List<T> list = resultSet2List(rs, clazz);
		if (ArrayTool.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	private String[] getColumnNames(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] columnNames = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = metaData.getColumnLabel(i + 1);
		}
		return columnNames;
	}

	private Integer getParameterCount(PreparedStatement ps, String sql) {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("SQL语句不能为空");
		}
		if (ps == null) {
			throw new RuntimeException("PreparedStatement不能为null");
		}
		Integer count = null;
		try {
			count = Integer.valueOf(ps.getParameterMetaData()
					.getParameterCount());
		} catch (Exception localException) {

		}
		return count;
	}

	private String getFirstWord(String sql) {
		if (StringUtils.isBlank(sql))
			return "";
		String[] strArr = sql.split("\\s+", -1);
		if (!ArrayTool.isEmpty(strArr)) {
			return strArr[0];
		}
		return "";
	}

	public String[] query(DataSource ds, String sql) throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return query(conn, sql);

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			closeAll();
		}
	}

	public String[] query(Connection conn, String sql) {
		if (StringUtils.isBlank(sql)) {
			throw new RuntimeException("SQL语句不能为空");
		}
		try {
			this.ps = getPreparedStatement(conn, sql, TYPE.QUERY);
			if (this.ps != null) {
				this.rs = this.ps.executeQuery();
				String[] columnNames = getColumnNames(rs);
				StringBuffer result = new StringBuffer(
						"<table class=\"simple\" border=\"1\" >");
				result.append("<thead><tr>");
				for (int i = 0; i < columnNames.length; i++) {
					result.append("<th>");
					result.append(columnNames[i]);
					result.append("</th>");
				}
				result.append("</thead>");
				result.append("<tbody>");
				int ii = 0;
				int columnCount = columnNames.length;
				while (rs.next()) {
					ii++;
					result.append("<tr class='odd'>");
					for (int i = 1; i <= columnCount; i++) {
						result.append("<td>");
						String temp = rs.getString(i);
						if (StringUtils.isNotEmpty(temp)) {
							if (temp.indexOf(">") > 0) {
								temp = temp.replaceAll(">", "&gt;");
							}
							if (temp.indexOf("<") > 0) {
								temp = temp.replaceAll("<", "&lt;");
							}
						}

						result.append(temp);
						result.append("</td>");
					}
					result.append("</tr>");
				}
				result.append("</tbody>");
				result.append("</table>");

				return new String[] { String.valueOf(ii), result.toString() };

			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			closeAll();
		}
		return null;
	}

	public static enum PROCEDURE_PARAMTER_TYPE {
		IN, OUT;
	}

	public static enum TYPE {
		QUERY, UPDATE, PAGINATION, INSERT, DDL;
	}

	public static void main(String[] args) throws SQLException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		ConnConf conf = new ConnConf("com.sybase.jdbc3.jdbc.SybDataSource",
				"sa", "cellcom",
				"jdbc:sybase:Tds:192.168.7.224:5000/qy12345?CHARSET=cp936");
		JDBC jdbc = new JDBC(conf);
		List<Object> list = new ArrayList<Object>();
		list.add("张三");
		list.add(null);
		Long a = jdbc.insert(jdbc.getConnection(),
				"insert into t_business_other(name,submit_time) values(?,?)",
				Long.class, list.toArray());
		System.out.println(">>>>>>>>>>" + a);
		// ConnConf conf = new ConnConf(null,DB_TYPE.SYBASE);
		// ConnConf conf = new ConnConf("com.mysql.jdbc.Driver",
		// "wx", "cellcom",
		// "jdbc:mysql://192.168.7.205:3306/pbt?generateSimpleParameterMetadata=true&characterEncoding=utf8");
		// JDBC jdbc = new JDBC(conf);
		// StringBuilder sql = new StringBuilder();
		// ProcedureParameter param1 = new ProcedureParameter();
		// param1.setType(PROCEDURE_PARAMTER_TYPE.IN);
		// param1.setParameterValue(7L);
		//
		// List<ProcedureParameter> params =new ArrayList<ProcedureParameter>();
		// params.add(new ProcedureParameter(7L,PROCEDURE_PARAMTER_TYPE.IN));
		// params.add(new ProcedureParameter(8L,PROCEDURE_PARAMTER_TYPE.IN));
		// params.add(new ProcedureParameter(-1L,PROCEDURE_PARAMTER_TYPE.IN));
		// params.add(new ProcedureParameter(null,PROCEDURE_PARAMTER_TYPE.IN));
		// params.add(new ProcedureParameter(null,PROCEDURE_PARAMTER_TYPE.IN));
		// ProcedureResult result = jdbc.execProcedure(jdbc.getConnection(),
		// "sp_getreportkpi", params);
		// List<JdbcResultSet> list = result.getResultList();
		// for(int i=0;i<list.size();i++){
		// JdbcResultSet rs = list.get(i);
		// List<String> fields = rs.getFields();
		// List<Object[]> datas = rs.getDatas();
		//
		// for(int j=0;j<datas.size();j++){
		//
		// for(int k=0;k<fields.size();k++){
		// System.out.print(rs.getValueByFieldName(fields.get(k), j)+"---");
		// }
		// System.out.println();
		// }
		//
		// }

		// Date date = new Date();
		// Timestamp a = new Timestamp(System.currentTimeMillis());
		// System.out.println(date.getClass().equals(Date.class));
		// System.out.println(a.getClass().equals(Date.class));

	}
}
