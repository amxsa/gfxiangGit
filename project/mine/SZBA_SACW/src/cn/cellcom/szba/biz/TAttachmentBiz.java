package cn.cellcom.szba.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TAttachment;
import cn.open.db.JDBC;

public class TAttachmentBiz {
	private static Log log = LogFactory.getLog(TAttachmentBiz.class);
	
	private static String SQL_QUERY_BY_KEY = 
			"select upload_url as \"uploadUrl\", name as \"attaName\"" +
			" from t_attachment" +
			" where ##### and valid_status = 'Y'" +
			" order by priority asc";
	
	private static String SQL_INSERT_ATTACH_BY_KEY = 
			"insert into t_attachment(type, upload_url, name, creator, #####, priority)" +
			" values(?, ?, ?, ?, ?, ?)";

	private static String SQL_DEL_BY_KEY = 
			"delete from t_attachment where ##### = ?";
	/**
	 * 添加附件的操作
	 * @param attaList 附件列表
	 * @param foreignKey 附件所关联物品的id
	 * @param type 表示附件所属的类型，0为案件， 21为财物， 23为电子物证，24为申请表, 25为案结处置依据, 33为财物处置
	 * @return
	 * @throws SQLException 
	 */
	public static void addAttachInfo(JDBC jdbc, Connection conn, List<TAttachment> attaList, String type, String foreignKey) throws SQLException{
		
		String sqlDelStr = new String(SQL_DEL_BY_KEY);
		String sqlInsertStr = new String(SQL_INSERT_ATTACH_BY_KEY);
		
		Map<String, String> map = replaceTagByType(type, sqlDelStr, sqlInsertStr);
		sqlDelStr = map.get("sqlDelStr");
		sqlInsertStr = map.get("sqlInsertStr");
		
		jdbc.update(conn, sqlDelStr, foreignKey);
		if(attaList.size() > 0){
			int i = 0;
			for(TAttachment a : attaList){
				List<Object> params = new ArrayList<Object>();
				params.add(a.getType());
				params.add(a.getUploadUrl());
				params.add(a.getAttaName());
				params.add(a.getCreator().getAccount());
				params.add(foreignKey);
				params.add(i);
				i++;
				
				jdbc.update(conn, sqlInsertStr, params.toArray());
				
			}
		}
		return;
	}
	public static void addAttachInfo(List<TAttachment> attaList, String type, String foreignKey){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		try{
			conn.setAutoCommit(false);
			
			addAttachInfo(jdbc, conn, attaList, type, foreignKey);
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
			try {
				if(conn != null){
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return;
	}
	
	/**
	 * 查询所有数据
	 * @param type 表示附件所属的类型，0为案件， 21为财物， 23为电子物证，24为申请表, 25为案结处置依据
	 * @param foreignKey 附件所关联物品的id
	 * @return
	 */
	public static List<TAttachment> queryAll(String type, String foreignKey){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TAttachment> list = null;
		String sqlStr = new String(SQL_QUERY_BY_KEY);
		switch(type){
			case Env.TYPE_CASE:  sqlStr=sqlStr.replace("#####", "case_id = ?");break;
			case Env.TYPE_PROPERTY: sqlStr=sqlStr.replace("#####", "property_id = ?");break;
			case Env.TYPE_ELEC_RECORD: sqlStr=sqlStr.replace("#####", "elec_evidence_id = ?");break;
			case Env.TYPE_APPLICATION: sqlStr=sqlStr.replace("#####", "apply_id = ?");break;
			case Env.TYPE_CASE_DISPOSAL: sqlStr=sqlStr.replace("#####", "disposal_id = ?");break;
			case Env.TYPE_HANDLE_RESULT: sqlStr=sqlStr.replace("#####", "handle_result_id = ?");break;
		}
		try {
			list = jdbc.query(Env.DS, sqlStr, TAttachment.class, foreignKey);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
		return list;
	}
	
	public static Map<String, String> replaceTagByType(String type, String sqlDelStr, String sqlInsertStr){
		switch(type){
			case Env.TYPE_CASE:  sqlDelStr=sqlDelStr.replace("#####", "case_id");
				   sqlInsertStr= sqlInsertStr.replace("#####", "case_id");break;
			case Env.TYPE_PROPERTY: sqlDelStr=sqlDelStr.replace("#####", "property_id");
				   sqlInsertStr= sqlInsertStr.replace("#####", "property_id"); break;
			case Env.TYPE_ELEC_RECORD: sqlDelStr=sqlDelStr.replace("#####", "elec_evidence_id");
				   sqlInsertStr= sqlInsertStr.replace("#####", "elec_evidence_id"); break;
			case Env.TYPE_APPLICATION: sqlDelStr=sqlDelStr.replace("#####", "apply_id");
			 	   sqlInsertStr= sqlInsertStr.replace("#####", "apply_id"); break;
			case Env.TYPE_CASE_DISPOSAL: sqlDelStr=sqlDelStr.replace("#####", "disposal_id");
				   sqlInsertStr= sqlInsertStr.replace("#####", "disposal_id"); break;
			case Env.TYPE_HANDLE_RESULT: sqlDelStr=sqlDelStr.replace("#####", "handle_result_id");
			sqlInsertStr= sqlInsertStr.replace("#####", "handle_result_id"); break;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("sqlDelStr", sqlDelStr); 
		map.put("sqlInsertStr", sqlInsertStr);
		
		return map;
	}
}
