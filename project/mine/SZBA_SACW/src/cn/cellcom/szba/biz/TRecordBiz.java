package cn.cellcom.szba.biz;

import java.util.List;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TCivilian;
import cn.cellcom.szba.domain.TPolice;
import cn.cellcom.szba.domain.TRecord;
import cn.open.db.JDBC;

public class TRecordBiz {

	private static String SQL_QUERY_RECORD_BY_ID = 
			"select re.*, d.list_id from t_record re" +
			" left join t_detail_list d on d.record_id = re.record_id and d.valid_status = 'Y'" +
			" where re.record_id = ? and re.valid_status = 'Y' ";
	
	
	public static TRecord queryRecordById(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TRecord record = null;
		List<TPolice> polices = null;
		List<TCivilian> civilians = null;
		 try {
			
			 record = jdbc.queryForObject(Env.DS, SQL_QUERY_RECORD_BY_ID, TRecord.class, id);
			 if(record != null){
				 polices = PersonBiz.queryPoliceByMixId(Env.DS, id, "REC");
				 civilians = PersonBiz.queryCivilianByMixId(Env.DS, id, "REC");
				 
				 record.setPolices(polices);
				 record.setCivilians(civilians);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		 
		return record != null ? record : new TRecord();
	}
}
