package cn.cellcom.szba.biz;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TDetail;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.JDBC;

public class TDetailListBiz {

	public static String SQL_QUERY_BY_PROID = 
			"select list.list_id as \"listID\", list.type from t_detail_list list" +
			" join t_property pro on pro.mix_id = list.list_id and pro.table_type  = 'LIST'" +
			" where pro.id = ?";
	public static String SQL_QUERY_PRO_BY_LIST = 
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
			" join t_detail_list list on list.list_id = pro.mix_id and pro.table_type = 'LIST'" +
			" where list.list_id = ?";
	
	public static TDetail queryByProId(String proId) throws Exception{
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TDetail detail = jdbc.queryForObject(Env.DS, SQL_QUERY_BY_PROID, TDetail.class, proId);
		if(detail != null){
			detail.setPolices( PersonBiz.queryPoliceByMixId(Env.DS, detail.getListID(), "LIST") );
			detail.setCivilians( PersonBiz.queryCivilianByMixId(Env.DS, detail.getListID(), "LIST") );
			
			detail.setProperties(jdbc.query(Env.DS, SQL_QUERY_PRO_BY_LIST, TProperty.class, detail.getListID()));
		}
		return detail != null?detail:new TDetail();
	}
}
