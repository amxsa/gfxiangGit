package cn.cellcom.szba.biz;


import java.sql.Connection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TDemage;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TDamageBiz {
	private static Log log = LogFactory.getLog(TDamageBiz.class);

	//删除损毁，更新损毁删除状态
	private static String SQL_DEL_DAMAGE = "update t_damage set valid_status='N' WHERE id=?";
	
	private static String SQL_QUERY_DAMAGEBYID ="select \"id\",\"proNO\",\"proName\",\"createTime\",\"damageAccount\",\"description\" from vw_damage where \"id\"=?";
	private static String SQL_EDT_DAMAGE = "update t_damage set PRO_NO=?, damage_account=?, description=?, create_time=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') WHERE ID=?";

	private static String SQL_QUERY_PROPERTY="SELECT * from vw_prolist"+
			                                 " where 1=1" +
			                                 " and (\"proNo\" = string:condiProNo) " +
			                     			" and (\"proName\" like blike:condiProName) ";
	private static String SQL_QUERY_DAMAGE ="select * from vw_damage"+
			" where 1=1  " +
			" and (\"jzcaseId\" = string:condiJzcaseId) " +
			" and (\"caseName\" like blike:condiCaseName) " +
			" and (\"createTime\" >=date:condiStartTime)" +
			" and (\"createTime\" <=date:condiEndTime)" +
			" and (\"proNO\" = string:condiProNo) " +
			" and (\"proName\" like blike:condiProName) " +
			" order by \"id\" desc";
	private static String SQL_INSERT_DAMAGE="insert into t_damage(CREATE_TIME,DAMAGE_ACCOUNT,DESCRIPTION,PRO_NO,PROPERTY_ID,VALID_STATUS) VALUES(to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?)";
	
			
/**
	 * 带条件的分页查询
	 * @param params 条件
	 * @param page   分页参数
	 * @return
	 */
	public static ListAndPager<TDemage> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TDemage> list = new ListAndPager<TDemage>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_DAMAGE, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TDemage.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	public static ListAndPager<TProperty> queryForTProPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TProperty> list = new ListAndPager<TProperty>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_PROPERTY, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TProperty.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
//	public static List<TProperty> findTProperty(){
//		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
//		List<TProperty> list = null;
//		
//		try {
//			list = jdbc.query(Env.DS, SQL_QUERY_PROPERTY, TProperty.class);
//			
//		} catch (Exception e) {
//			log.error(e);
//		} finally{
//			jdbc.closeAll();
//		}
//		return list;
//	}
	
	public static String insert(String pId,String pno,String DTime,String dAccount,String description){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
	int rows = jdbc.update(Env.DS, SQL_INSERT_DAMAGE,DTime,dAccount,description,pno,pId,"Y");
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
	 * 在调用归还申请的时候，进行财物损毁登记
	 * @param damage 
	 * @param jdbc
	 * @param conn
	 */
	public static void insertByApplication(TDemage damage, JDBC jdbc, Connection conn){
		try {
			jdbc.update(conn, 
					"insert into t_damage(damage_account, description, pro_no, property_id, application_id, damage_status) values(?,?,?,?,?,?)"
					, damage.getDamageAccount(), damage.getDescription(), damage.getProNO(),damage.getPropertyId()
					, damage.getApplicationId(), damage.getDamageStatus());
		} catch (Exception e) {
			log.error("", e);
		} 
		
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
			num = jdbc.update(Env.DS, SQL_DEL_DAMAGE,id);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return num;
	}
	/**
	 * 根据ID更改信息
	 * @param account
	 * @return
	 */
	public static String editById(String id,String pid,String DTime,String dAccount,String description){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
	
		String result = "fail";
		try{
			int num=jdbc.update(Env.DS, SQL_EDT_DAMAGE,pid,dAccount,description,DTime,id);
			if(num>0){
				result = "success";
				}
			
		} catch (Exception e){
			log.error(e);
			
		} finally{
			jdbc.closeAll();
		}
		return result;
	}
	/**
	 * 根据ID查找财物
	 * @param account
	 * @return
	 */
	public static TDemage findByTDId(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TDemage tDemage;
		try{
			tDemage = jdbc.queryForObject(Env.DS, SQL_QUERY_DAMAGEBYID, TDemage.class, id);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return tDemage;
	}
	
}
