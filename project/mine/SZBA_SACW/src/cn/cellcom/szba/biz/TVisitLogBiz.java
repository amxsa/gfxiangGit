package cn.cellcom.szba.biz;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TVisitLog;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TVisitLogBiz {
	private static Log log = LogFactory.getLog(TVisitLogBiz.class);
	
	private static String SQL_QUERY_VISITLOG = "SELECT id, session_id, account, ip, visit_time, url, http_method " +
			"FROM t_visit_log " +
			"WHERE 1=1 and (session_id = string:sessionId) " +
			"  and (account = string:account) " +
			"  and (ip = string:ip) " +
			"  and (url like blike:url) order by visit_time desc";

	private static String SQL_QUERY_VISITLOG_BYID = "SELECT id, session_id, account, ip, visit_time, url, http_method "
			+ "FROM t_visit_log WHERE id=?";

	private static String SQL_INSERT_VISITLOG = "INSERT INTO t_visit_log(session_id, account, ip, url, http_method) "
			+ "VALUES(?,?,?,?,?)";

	/**
	 * 根据账号查询用户
	 * @param account
	 * @return
	 */
	public TVisitLog queryById(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TVisitLog entity = null;
		try{
			entity = jdbc.queryForObject(Env.DS, SQL_QUERY_VISITLOG_BYID, TVisitLog.class, id);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}
	
	/**
	 * 带条件的分页查询
	 * @param params 条件
	 * @param page   分页参数
	 * @return
	 */
	public ListAndPager<TVisitLog> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TVisitLog> list = new ListAndPager<TVisitLog>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_VISITLOG, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TVisitLog.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	/**
	 * 新增
	 * 
	 * @param entity
	 * @return
	 */
	public static String insert(TVisitLog visitLog) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			int rows = jdbc.update(Env.DS, SQL_INSERT_VISITLOG,
					visitLog.getSessionId(), visitLog.getAccount(),
					visitLog.getIp(), visitLog.getUrl(),
					visitLog.getHttpMethod());
			if (rows > 0) {
				result = "success";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			jdbc.closeAll();
		}
		return result;
	}

}
