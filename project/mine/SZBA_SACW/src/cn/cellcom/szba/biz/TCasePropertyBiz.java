package cn.cellcom.szba.biz;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DataMsg;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;

import cn.cellcom.szba.domain.TCase;
import cn.cellcom.szba.domain.TProperty;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TCasePropertyBiz {
	private static Log log = LogFactory.getLog(TCasePropertyBiz.class);
	
	//添加财物关联案件查询 pansenxin
	public static ListAndPager<TCase> queryCaseToPro(
			Map<String, String[]> paramsMap, Pager page,
			Map<String, Object> loginForm) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String originalSql = "select c.case_id as case_i_d ,c.jzcase_id as jzcase_i_d  ,c.case_name, c.occur_date, c.case_type, c.leader, "
				+ "c.creator as creator_temp ,d.name as department_name from t_case c left join t_department d on c.department_id = d.id  "
				+ "where  1=1 and (c.jzcase_id = string:jzcaseID) and (c.case_name like blike:caseName) and (c.valid_status='Y')   order by c.create_time desc ";
		ListAndPager<TCase> list = null;
		try {
			DbRequest dbRequest = SqlParser.parse(originalSql, paramsMap);
			Object[] params = dbRequest.getParameters();
			String sql = dbRequest.getSql();
			list = jdbc.queryPage(Env.DS, sql, TCase.class,
					page.getCurrentIndex(), page.getSizePerPage(), params);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
		return list;
	}

	public static ListAndPager<TCase> queryForPage(
			Map<String, String[]> paramsMap, Pager page,
			Map<String, Object> loginForm) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String originalSql = "select c.case_id as case_i_d ,c.jzcase_id as jzcase_i_d  ,c.case_name, c.occur_date, c.case_type,c.creator as creator_temp ,d.name as department_name from t_case c left join t_department d on c.department_id = d.id  where  1=1 and (c.id=string:caseID) and (c.name like blike:caseName) and (c.valid_status='Y')   order by c.create_time desc ";
		ListAndPager<TCase> list = null;
		try {
			DbRequest dbRequest = SqlParser.parse(originalSql, paramsMap);
			Object[] params = dbRequest.getParameters();
			String sql = dbRequest.getSql();
			list = jdbc.queryPage(Env.DS, sql, TCase.class,
					page.getCurrentIndex(), page.getSizePerPage(), params);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
		return list;
	}
	
	public static DataMsg updateCaseProperty(Map<String,String> params) throws SQLException{
		DataMsg data = new DataMsg(false,"操作失败");
		
		String operateType = params.get("operateType");
		//bind :关联，cancelBind:取消关联
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		if("bind".equals(operateType)){
			String tempProIds =params.get("tempProIds");
			String tempJzcaseID =params.get("tempJzcaseID");
			if(StringUtils.isNotBlank(tempProIds)&&StringUtils.isNotBlank(tempJzcaseID)){
				String[] proIds = tempProIds.split(",");
				Connection conn = null;
				try {
					conn = Env.DS.getConnection();
					conn.setAutoCommit(false);
					if(proIds==null||proIds.length==0){
						data.setMsg("案件或财务未找到");
						return data;
					}
					for(int i=0;i<proIds.length;i++){
						if(StringUtils.isNotBlank(proIds[i])){
							jdbc.update(conn, "update t_property set case_id= ?  where  id =  ? ", tempJzcaseID,proIds[i]);
						}
					}
					conn.commit();
					data.setState(true);
					data.setMsg("关联成功");
					return data;
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						log.error("", e1);
					}
					log.error("", e);
					throw new RuntimeException(e);
				} finally {
					jdbc.closeAll();
					if(conn!=null){
						try {
							conn.close();
						} catch (SQLException e) {
							log.error("", e);
						}
					}
				}
			}else{
				data.setMsg("案件或财务未找到");
				return data;
			}
		}else if("cancelBind".equals(operateType)){
			String proId =params.get("proId");
			jdbc.update(Env.DS, "update t_property set case_id=null where  id = ? ", proId);
			data.setState(true);
			data.setMsg("取消关联成功");
		}
		return data;
		
	}
	
	public static TCase getByApplicationId(Long appId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TCase tCase = null;
		try {
			tCase = jdbc.queryForObject(Env.DS, 
					"select case.case_id as \"caseID\", case.jzcase_id as \"jzcaseID\", case.case_name, case.leader " +
					"from t_case case left join t_application a on a.case_id = case.case_id where a.id = ?", 
					TCase.class, appId);
		} catch (Exception e) {
			log.error("", e);
		}
		return tCase;
	}
	
}
