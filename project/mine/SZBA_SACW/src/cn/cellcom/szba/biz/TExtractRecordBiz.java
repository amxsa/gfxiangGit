package cn.cellcom.szba.biz;

import java.util.List;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TCivilian;
import cn.cellcom.szba.domain.TExtractRecord;
import cn.cellcom.szba.domain.TPolice;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.JDBC;

public class TExtractRecordBiz {
	
	private static String SQL_QUERY_RECORD_BY_ID = 
			"select * from t_extract_record re" +
			" where re.id = ?";
	private static String SQL_QUERY_PROPERTY_BY_EXT = 
			"select  pro.id as \"proId\" ,pro.name as \"proName\", pro.quantity as \"proQuantity\", " +
			" pro.unit as \"proUnit\", pro.owner as \"proOwner\" ," +
			" pro.seizure_basis as \"proSeizureBasis\", " +
			" pro.category_id as \"categoryId\", pro.characteristic as \"proCharacteristic\" ," +
			" pro.status as \"proStatus\", pro.type as \"proType\"," +
			" pro.origin as \"proOrigin\", pro.nature as \"proNature\"," +
			" pro.seizure_place as \"proSeizurePlace\"," +
			" pro.seizure_basis as \"proSeizureBasis\"," +
			" pro.remark as \"proRemark\", pro.mix_id as \"proMixId\"," +
			" pro.table_type, pro.save_demand, pro.save_method, pro.envi_demand, " +
			" pro.apprai_result, pro.apprai_situation" +
			" from t_property pro " +
			" join t_extract_record ext on ext.id = pro.mix_id and pro.table_type = 'EXTRAC'" +
			" where ext.id = ?";
	
	public static TExtractRecord queryRecordByProId(String id){
		
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TExtractRecord record = null;
		 try {
			
			 record = jdbc.queryForObject(Env.DS, SQL_QUERY_RECORD_BY_ID, TExtractRecord.class, id);
			 if(record != null){
				 List<TPolice> polices = PersonBiz.queryPoliceByMixId(Env.DS, id, "EXTRAC");
				 List<TCivilian> civilians = PersonBiz.queryCivilianByMixId(Env.DS, id, "EXTRAC");
				 
				 List<TProperty> properties = jdbc.query(Env.DS, SQL_QUERY_PROPERTY_BY_EXT, TProperty.class, id);
				 
				 record.setProperties(properties);
				 record.setCivilians(civilians);
				 record.setPolices(polices);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			jdbc.closeAll();
		}
		 
		return record != null ? record : new TExtractRecord();
	}

}
