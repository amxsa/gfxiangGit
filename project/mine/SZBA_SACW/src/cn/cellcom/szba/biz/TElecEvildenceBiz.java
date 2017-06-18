package cn.cellcom.szba.biz;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TElecEvidence;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TElecEvildenceBiz {
   
	private static Log log = LogFactory.getLog(TPropertyBiz.class);
	
	private static String SQL_QUERY_ELECEVIDENCELIST = "select elec.id as \"elecEvidenceID\" ,elec.name as \"name\"," +
			"elec.quantity as \"quantity\" ,elec.unit as \"unit\" ,c.case_id as \"caseId\" ," +
			"c.case_name as \"caseName\" " +
			" from t_elec_evidence elec" +
			" left join t_detail_list d on d.list_id = elec.list_id " +
			" left join t_record r on r.record_id = d.record_id " +
			" left join t_case c on c.case_id = r.case_id " +
			" where 1=1 and elec.valid_status='Y' and ( elec.creator = string:account) " +
			" and (c.case_id = string:condiJzcaseId) " +
			" and (c.case_name like blike:condiCaseName) " +
			" and (c.create_time >=date:condiStartTime)" +
			" and (c.create_time <=date:condiEndTime)" +
			" and (elec.id = string:condiProId) " +
			" and (elec.name like blike:condiProName) " +
			//" and (\"proStatus\" = string:condiProStatus) " +
			" order by c.create_time desc";
	
	public static ListAndPager<TElecEvidence> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TElecEvidence> list = null;
		DbRequest dbRequest;
		try {
			dbRequest = SqlParser.parse(SQL_QUERY_ELECEVIDENCELIST, params);
			String sql  = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TElecEvidence.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return list!=null?list:new ListAndPager<TElecEvidence>();
	}
}
