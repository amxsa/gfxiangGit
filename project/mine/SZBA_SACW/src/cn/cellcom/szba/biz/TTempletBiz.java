package cn.cellcom.szba.biz;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TTemplet;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TTempletBiz {
	private static Log log = LogFactory.getLog(TTempletBiz.class);
	private static String SQL_UPDATE_TEMPLET = 
			 "update t_templet set name = ?, content = ?" +
			 " where id = ? and type != '0'";
	private static String SQL_DELETE_TEMPLET = 
			 "delete from t_templet where id = ? and type != '0'";
	private static String SQL_QUERY_TEMPLET = 
			 "select * from t_templet where 1=1 and "
					 +"(content_type = string:contentType) and (name like blike:name) order by create_time desc";
	
	private static String SQL_QUERY_BY_TEMPLETID = 
			 "select * from t_templet where id = ?";
	
	private static String SQL_INSERT_TEMPLET = 
			"insert into t_templet(id,name, type, content_type,account,content,create_time)"
	+" values(?,?,?,?,?,?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'))";
	

	public boolean add(TTemplet templet){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		boolean result = false;
		try {
			int rows = jdbc.update(Env.DS, SQL_INSERT_TEMPLET,UUID.randomUUID().toString(),templet.getName(),templet.getType(),
					templet.getContentType(),templet.getAccount(),templet.getContent(), DateUtil.getCurrentDatetime());
			if(rows == 1){
				result = true;
			}
		} catch (Exception e) {
			result = false;
			log.error(e);
			throw new RuntimeException(e);
		} 
		return result;
	}
	
	public boolean update(TTemplet templet){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try {
			int row = jdbc.update(Env.DS, SQL_UPDATE_TEMPLET, templet.getName(), templet.getContent(), templet.getId());
			if(row <= 0){
				return false;
			}
		} catch (SQLException e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		
		return true;
	}
	
	public boolean delete(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try {
			int row = jdbc.update(Env.DS, SQL_DELETE_TEMPLET, id);
			
			if(row <= 0){
				return false;
			}
		} catch (SQLException e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		
		return true;
	}
	
	public ListAndPager<TTemplet> queryTemplet(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TTemplet> list = new ListAndPager<TTemplet>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_TEMPLET, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TTemplet.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
		
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public TTemplet queryDetail(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TTemplet t = null;
		try {
			t = jdbc.queryForObject(Env.DS, SQL_QUERY_BY_TEMPLETID, TTemplet.class, id);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		return t != null ? t : new TTemplet();
	}
	
}
