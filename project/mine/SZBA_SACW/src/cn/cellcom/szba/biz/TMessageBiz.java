package cn.cellcom.szba.biz;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateTool;
import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TApplication;
import cn.cellcom.szba.domain.TMessage;
import cn.cellcom.szba.vo.template.TemplateMessage;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TMessageBiz {
	
	Log log = LogFactory.getLog(this.getClass());

	private static String SQL_QUERY_T_MESSAGE = 
			"select id, title, content, type, readstatus, create_time as \"createTime\", " +
			"is_relative as \"isRelative\", account, href from t_message " +
			"where 1=1 and ( account = string:account)" +
			" and ( create_time >= date:condiStartTime) "+
			" and ( create_time <= date:condiEndTime) " +
			"order by readstatus, create_time desc";
	
	private static String SQL_QUERY_T_MESSAGE3 = 
			"select id, title, content, type, readstatus, create_time as \"createTime\", " +
			"is_relative as \"isRelative\", account, href from t_message where account = ? order by readstatus";
	private static String SQL_QUERY_T_MESSAGE2 = 
			
			"select id, title, content, type, readstatus, create_time as \"createTime\", " +
			"is_relative as \"isRelative\", account, href from t_message where readstatus='N' and account = ? order by create_time desc";
	
	private static String SQL_UPDATE_T_MESSAGE = "update t_message set readstatus='Y',LREADTIME = TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"') where id = ?";
	
	private static String SQL_QUERY_T_MESSAGE_BYID = "select title,content from t_message where id = ?";
	
	private static String SQL_DELETE_T_MESSAGE_BYID = "delete from t_message where id = ?";
	
	private static String SQL_QUERY_RZCK_T_PROPERTY = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\",pro.TRANSACTOR as \"transactor\" " +
			" from T_PROPERTY pro " +
			" left join T_CASE cas on cas.CASE_ID = pro.CASE_ID " +
			" where 1=1 and pro.STATUS in ('YDJ','SQRZCK','DRZCK') and sysdate < pro.create_time + ? ";
	
	private static String SQL_QUERY_RZCKCQ_T_PROPERTY = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\",pro.TRANSACTOR as \"transactor\" " +
			" from T_PROPERTY pro " +
			" left join T_CASE cas on cas.CASE_ID = pro.CASE_ID " +
			" where 1=1 and pro.STATUS in ('YDJ','SQRZCK','DRZCK') and sysdate > pro.create_time + ? ";
	
	
	private static String SQL_QUERY_ZCKRZXK_T_PROPERTY = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\",pro.TRANSACTOR as \"transactor\" " +
			" from T_PROPERTY pro " +
			" left join T_CASE cas on cas.CASE_ID = pro.CASE_ID " +
			" where 1=1 and pro.STATUS in ('YRZCK','SQZCK2ZXK','ZCK2ZXK','DRZXK')  and sysdate > pro.create_time + 5 and sysdate < pro.create_time + ? ";
	
	private static String SQL_QUERY_ZCKRZXKCQ_T_PROPERTY = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\",pro.TRANSACTOR as \"transactor\" " +
			" from T_PROPERTY pro " +
			" left join T_CASE cas on cas.CASE_ID = pro.CASE_ID " +
			" where 1=1 and pro.STATUS in ('YRZCK','SQZCK2ZXK','ZCK2ZXK','DRZXK') and sysdate > pro.create_time + ? ";
	
	private static String SQL_QUERY_CWDYGH_T_PROPERTY = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\",pro.TRANSACTOR as \"transactor\" " +
			" from T_PROPERTY pro " +
			" left join T_CASE cas on cas.CASE_ID = pro.CASE_ID " +
			" where 1=1 and pro.STATUS in ('DYCZCK','DYCZXK','SQDYGH') and sysdate < pro.create_time + ? ";

	private static String SQL_QUERY_CWDYGHCQ_T_PROPERTY = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\",pro.TRANSACTOR as \"transactor\" " +
			" from T_PROPERTY pro " +
			" left join T_CASE cas on cas.CASE_ID = pro.CASE_ID " +
			" where 1=1 and pro.STATUS in ('DYCZCK','DYCZXK','SQDYGH') and sysdate > pro.create_time + ? ";
	
	private static String SQL_QUERY_TQCW_T_ELEC_RECORD = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\" " +
			" from T_PROPERTY pro " +
			" left join T_EXTRACT_RECORD exr on exr.ID = pro.MIX_ID " +
            " left join T_CASE cas on cas.CASE_ID = exr.CASE_ID " +
			" where 1 = 1 and pro.TABLE_TYPE = 'EXTRAC' and sysdate < exr.CREATE_TIME + ? ";
	
	private static String SQL_QUERY_DB_T_APPLICATION = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\" " +
			" from T_APPLICATION app " +
			" left join T_APPLICATION_PROPERTY apr on apr.APPLICATION_ID = app.ID " +
			" left join T_PROPERTY pro on pro.ID = apr.PROPERTY_ID " +
			" left join T_CASE cas on cas.CASE_ID = app.CASE_ID " +
			" where 1  = 1 and app.STATUS ='N' and sysdate < app.CREATE_TIME + ? ";
	
	private static String SQL_QUERY_CB_T_APPLICATION = "select pro.NAME as \"proname\"," +
			"cas.CASE_NAME as \"casename\" " +
			" from T_APPLICATION app " +
			" left join T_APPLICATION_PROPERTY apr on apr.APPLICATION_ID = app.ID " +
			" left join T_PROPERTY pro on pro.ID = apr.PROPERTY_ID " +
			" left join T_CASE cas on cas.CASE_ID = app.CASE_ID " +
			" where 1  = 1 and app.STATUS ='N' and sysdate > app.CREATE_TIME + ? ";
	
	public ListAndPager<TMessage> queryForPage(Map<String, String[]> params,
			Pager page) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TMessage> list = new ListAndPager<TMessage>();
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_T_MESSAGE, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TMessage.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public ListAndPager<TMessage> queryForPage3(String account,
			Pager page) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TMessage> list = new ListAndPager<TMessage>();
		try {
			list = jdbc.queryPage(Env.DS, SQL_QUERY_T_MESSAGE3, TMessage.class, page.getCurrentIndex(), page.getSizePerPage(), account);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public ListAndPager<TMessage> queryForPage2(String account,
			Pager page) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TMessage> list = new ListAndPager<TMessage>();
		try {
			list = jdbc.queryPage(Env.DS, SQL_QUERY_T_MESSAGE2, TMessage.class, page.getCurrentIndex(), page.getSizePerPage(), account);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}

	public TMessage queryById(long id) {
	
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TMessage list = new TMessage();
		try {
			list = jdbc.queryForObject(Env.DS, SQL_QUERY_T_MESSAGE_BYID, TMessage.class,id);
			jdbc.update(Env.DS, SQL_UPDATE_T_MESSAGE,DateTool.formateTime2String(new Date()),id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	
	}

	public String deleteById(long id) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "";
		try {
			int rows = jdbc.update(Env.DS, SQL_DELETE_T_MESSAGE_BYID , id);
			if(rows>0){
				result = "success";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return result;
	}

	public List<TemplateMessage> queryMessage(String type,String dayCounts) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TemplateMessage> list = new ArrayList<TemplateMessage>();
		try {
			if("RZCK".equals(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_RZCK_T_PROPERTY,TemplateMessage.class,dayCounts);
			}if("RZCKCQ".equals(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_RZCKCQ_T_PROPERTY,TemplateMessage.class,dayCounts);
			}if("ZCKRZXK".endsWith(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_ZCKRZXK_T_PROPERTY,TemplateMessage.class,dayCounts);
			}if("ZCKRZXKCQ".endsWith(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_ZCKRZXKCQ_T_PROPERTY,TemplateMessage.class,dayCounts);
			}if("CWDYGH".endsWith(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_CWDYGH_T_PROPERTY,TemplateMessage.class,dayCounts);
			}if("CWDYGHCQ".endsWith(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_CWDYGHCQ_T_PROPERTY,TemplateMessage.class,dayCounts);
			}if("TQCW".endsWith(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_TQCW_T_ELEC_RECORD,TemplateMessage.class,dayCounts);
			}if("DB".endsWith(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_DB_T_APPLICATION,TemplateMessage.class,dayCounts);
			}if("CB".endsWith(type)){
				list = jdbc.query(Env.DS, SQL_QUERY_CB_T_APPLICATION,TemplateMessage.class,dayCounts);
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	

}
