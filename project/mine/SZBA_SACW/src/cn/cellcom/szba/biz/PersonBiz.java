package cn.cellcom.szba.biz;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TCivilian;
import cn.cellcom.szba.domain.TPolice;
import cn.open.db.JDBC;

public class PersonBiz {

	private static String SQL_QUERY_CIVI_BY_MIXID = 
			"select id as \"civiId\", name as \"civiName\", id_num as \"idNum\", type civi_type, " +
			" address as \"civiAddress\", phone as \"civiPhone\"" +
			" from t_civilian " +
			" where mix_id = ? and valid_status = 'Y' and table_type = ?" +
			" order by type asc, priority asc";
	
	private static String SQL_QUERY_POLICE_BY_MIXID = 
			"select po.id as \"poliId\", po.name as \"poliName\", dept.id as \"deptID\", type poli_type, " +
			" dept.name as \"deptName\" " +
			" from t_police po" +
			" left join t_department dept on po.department_id = dept.id" +
			" where po.mix_id = ? and po.valid_status = 'Y' and po.table_type = ?" +
			" order by po.type asc, po.priority asc";
	
	private static JDBC jdbc;
	static {
		jdbc = SpringWebApplicataionContext.getJdbc();
	}
	
	public static List<TPolice> queryPoliceByMixId(DataSource ds, String mixId, String tableType) throws Exception{
		
		List<TPolice> polices = jdbc.query(ds, SQL_QUERY_POLICE_BY_MIXID, TPolice.class, mixId, tableType);
		return polices != null? polices : new ArrayList<TPolice>();
	}
	
	public static List<TCivilian> queryCivilianByMixId(DataSource ds, String mixId, String tableType) throws Exception{
		
		List<TCivilian> civilians = jdbc.query(ds, SQL_QUERY_CIVI_BY_MIXID, TCivilian.class, mixId, tableType);
		return civilians != null ? civilians : new ArrayList<TCivilian>();
	}
}
