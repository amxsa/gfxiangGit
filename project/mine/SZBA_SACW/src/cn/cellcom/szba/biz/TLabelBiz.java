package cn.cellcom.szba.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TDemage;
import cn.cellcom.szba.domain.TLabel;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TLabelBiz {
	private static Log log = LogFactory.getLog(TLabelBiz.class);
	private static String SQL_INSERT_LABEL="insert into t_label(ID,NEW_LNAME,PRO_NO,CHANGE_ACCOUNT,DESCRIPTION,VALID_STATUS) values(?,?,?,?,?,?)";
	private static String SQL_DELETE_LABEL="update t_label set valid_status='N' WHERE id=?";
	private static String SQL_QUERY_labelBYID ="select \"id\",\"newLname\",\"createTime\",\"description\",\"proName\",\"proNO\" from vw_label where \"id\"=?";
	//private static String SQL_QUERY_labelBYID ="select NEW_LNAME as \"newLname\",DESCRIPTION as \"description\",PRO_NO as \"proNO\" from t_label where ID=?";
	private static String SQL_EDIT_PROBYID ="update t_property set RFID_NUM=? where PRO_NO=?";
	private static String SQL_EDIT_labelBYID ="update t_label set NEW_LNAME=?,CHANGE_ACCOUNT=?,DESCRIPTION=? where id=?";
	private static String SQL_QUERY_LABEL="select * from vw_label"+
			" where 1=1  " +
			" and (\"jzcaseId\" = string:condiJzcaseId) " +
			" and (\"caseName\" like blike:condiCaseName) " +
			" and (\"createTime\" >=date:condiStartTime)" +
			" and (\"createTime\" <=date:condiEndTime)" +
			" and (\"newLname\" = string:condiProNo) " +
			" and (\"proName\" like blike:condiProName) " +
			" order by \"id\" desc";
	/**
	 * 带条件的分页查询
	 * @param params 条件
	 * @param page   分页参数
	 * @return
	 */
	public static ListAndPager<TLabel> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TLabel> list = new ListAndPager<TLabel>();
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_LABEL, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TLabel.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	public static String insert(String pno,String newName,String description,String account){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
	int rows = jdbc.update(Env.DS, SQL_INSERT_LABEL,UUID.randomUUID().toString(),newName,pno,account,description,"Y");
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
	/**
	 * 根据ID删除
	 * @param account
	 * @return
	 */
	public static int updateById(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int num;
		try{
			num = jdbc.update(Env.DS, SQL_DELETE_LABEL,id);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return num;
	}
//	/**
//	 * 根据ID更改信息
//	 * @param account
//	 * @return
//	 */
//	public static String editById(String id,String pid,String DTime,String dAccount,String description){
//		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
//	
//		String result = "fail";
//		try{
//			int num=jdbc.update(Env.DS, SQL_EDT_DAMAGE,pid,dAccount,description,DTime,id);
//			if(num>0){
//				result = "success";
//				}
//			
//		} catch (Exception e){
//			log.error(e);
//			
//		} finally{
//			jdbc.closeAll();
//		}
//		return result;
//	}
	/**
	 * 更改标签
	 * @param properId 财物id
	 * @return
	 */
	public static boolean editLabel(String id,String pno,String olaName,String newName,String description,String accStr){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			TLabel tLabel=findByTDId(id);
			jdbc.update(conn, SQL_EDIT_PROBYID, tLabel.getNewLname(),pno);
			jdbc.update(conn, SQL_EDIT_labelBYID, newName,accStr,description,id);
			
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException("删除财物失败");
		} finally{
			jdbc.closeAll();
			try {
				if(conn != null)
				conn.close();
			} catch (SQLException e) {
				log.error("", e);
			}
			conn = null;
		}
		return true;
	}
	public static TLabel findByTDId(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TLabel tLabel;
		try{
			tLabel = jdbc.queryForObject(Env.DS, SQL_QUERY_labelBYID, TLabel.class, id);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return tLabel;
	}
}
