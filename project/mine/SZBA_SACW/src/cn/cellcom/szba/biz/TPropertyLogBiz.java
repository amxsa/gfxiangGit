package cn.cellcom.szba.biz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TDetail;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TPropertyLog;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TPropertyLogBiz {
	static Log log = LogFactory.getLog(TPropertyLogBiz.class);

	private static String SQL_INSERT_PRO_LOG = "insert into t_property_log(property_id,operator,operate_time,ip,content,type,memo)"
			+ "values(?,?,TO_DATE(?, '"
			+ DateUtil.OracleDatetimeFormatString
			+ "'),?,?,?,?)";
	
	private static String SQL_QUERY_PRO_LOG = "SELECT id,property_id,operator,operate_time,ip,content,type,memo " +
			"FROM t_property_log WHERE 1=1 "
			+ " and ( operate_time >= date:condigStartTime) "
			+ " and ( operate_time <= date:condigEndTime) "
			+" and (property_id = string:condiProId) order by operate_time desc";

	public static void save(HttpServletRequest httpRequest, String proId,
			String proOperateType) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		// long proId = Long.parseLong(proIdStr);
		Map<String, Object> loginForm = (Map<String, Object>) httpRequest
				.getSession().getAttribute("loginForm");
		// 操作者
		String operator = loginForm.get("ACCOUNT").toString();
		// 操作时间
		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置显示格式
		String nowTime = "";
		nowTime = df.format(dt);
		
		// 客户端ip
		String ip = httpRequest.getRemoteAddr();
		// 根据财物ID查询详情后生成json字符串
		TDetail detail = TPropertyBiz.queryDetail(proId);
		TProperty pro = new TProperty();
		if (detail.getProperties().size() > 0) {
			pro = detail.getProperties().get(0);
		}
		String json = "";
		if (pro != null) {
			json = JsonUtil.beanToJson(pro);
		}
		try {
			jdbc.update(Env.DS, SQL_INSERT_PRO_LOG, proId, operator, nowTime,
					ip, json, null,proOperateType);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			jdbc.closeAll();
		}
	}
	
	/**
	 * 带条件的分页查询
	 * @param params 条件
	 * @param page   分页参数
	 * @return
	 */
	public ListAndPager<TPropertyLog> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TPropertyLog> list = new ListAndPager<TPropertyLog>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_PRO_LOG, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TPropertyLog.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
}
