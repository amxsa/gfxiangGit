package cn.cellcom.szba.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TCategory;
import cn.cellcom.szba.domain.TCategoryAttribute;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TCategoryBiz {

	static Log log = LogFactory.getLog(TCategoryBiz.class);
	
	private static String SQL_QUERY_CATEGORY_TREE = 
			"select id,parent_id,name,save_demand,pack_demand,envi_demand,create_time,update_time,levels,priority,valid_status,type "
			+ "from t_category where valid_status = 'Y' start with id = 1 connect by prior id = parent_id order siblings by priority";
	
	private static String SQL_QUERY_CATEGORY_BY_ID =
			"select id,parent_id,name,save_demand,pack_demand,envi_demand,create_time,update_time,levels,priority,valid_status,type "
			+ "from t_category where id = ?";
	
	private static String SQL_QUERY_MAX_ID_BY_PARENT_ID =
			"select max(id) as id from t_category where parent_id = ?";
	
	private static String SQL_INSERT_CATEGORY = 
			"insert into t_category(id, name, save_demand, pack_demand, envi_demand, levels, parent_id, priority, valid_status, type, create_time) "
			+ "values(?,?,?,?,?,?,?,?,?,?, TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'))";
	
	private static String SQL_UPDATE_CATEGORY = 
			"update t_category set name=?, save_demand=?, pack_demand=?, envi_demand=?, levels=?, parent_id=?, priority=?, "
			+ "valid_status=?, type=?, update_time=TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"') where id=?";
	
	/**分类属性*/
	private static String SQL_INSERT_CATEGORY_ATTRIBUTE = 
			"insert into t_category_attribute(category_id, attr_name, attr_type, attr_value, search_type) "
			+ "values(?,?,?,?,?)";
	
	private static String SQL_QUERY_CATEGORY_ATTRIBUTE_BY_CATEGORY_ID=
			"select id,category_id, attr_name, attr_type, attr_value, search_type "
			+ "from t_category_attribute where category_id = ? or category_id=(select parent_id from t_category where id=?)";
	
	private static String SQL_DELETE_CATEGORY_ATTRIBUTE_BY_CATEGORY_ID = "delete from t_category_attribute where category_id=?";
	
	private static String SQL_QUERY_CATEGORY_BY_NAME=
			"select id,parent_id,name,save_demand,pack_demand,envi_demand,create_time,update_time,levels,priority,valid_status,type "
			+ "from t_category where name = ?";
	
	public static List<TCategory> findCategoryByName(String name){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TCategory> list = null;
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_CATEGORY_BY_NAME, TCategory.class,name);
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	/**根据分类id查询属性*/
	public static List<TCategoryAttribute> findCategoryAttributeByCategoryId(HttpServletRequest requ){
		List<TCategoryAttribute> retList=null;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try {
			//DbRequest dbRequest = SqlParser.parse(SQL_QUERY_CATEGORY_ATTRIBUTE_BY_CATEGORY_ID, requ.getParameter("categoryId"), requ.getParameter("categoryId"));
			//Object[] params = dbRequest.getParameters();
			//String sql = dbRequest.getSql();
			retList = jdbc.query(Env.DS,SQL_QUERY_CATEGORY_ATTRIBUTE_BY_CATEGORY_ID , TCategoryAttribute.class , requ.getParameter("categoryId"), requ.getParameter("categoryId"));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return retList;
	}

	public static List<TCategory> findCategory(){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TCategory> list = null;
		
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_CATEGORY_TREE, TCategory.class);
			
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public static TCategory save(TCategory category){
		if(null==category) return null;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		int result = 0;
		TCategory entity = null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			if(null==category.getId()){ // insert
				// 构造id
				Long maxId = jdbc.queryForObject(conn, SQL_QUERY_MAX_ID_BY_PARENT_ID, Long.class, category.getParentId());
				Long id = maxId;
				switch(category.getLevels()){
				case 1: id = id + 1000000; break;
				case 2: 
					id = id==0?(category.getParentId() + 1000):id+1000; 
					break;
				case 3: 
					id = id==0?(category.getParentId() + 1):id+1; 
					break;
				}
				category.setId(id);
				category.setPriority(id);
		
				result = jdbc.update(conn, SQL_INSERT_CATEGORY, category.getId(), category.getName(), category.getSaveDemand(), category.getPackDemand(),
						category.getEnviDemand(), category.getLevels(), category.getParentId(), category.getPriority(), category.getValidStatus(),category.getType(), DateUtil.getCurrentDatetime());
				if(result > 0){
					entity = jdbc.queryForObject(conn, SQL_QUERY_CATEGORY_BY_ID, TCategory.class, id);
					
					//保存分类属性
					if(StringUtils.isNotBlank(category.getAttrString())){
						String[] attrArr=category.getAttrString().split(";",-1);
						for(int i=0;i<attrArr.length;i++){
							String[] singleAttr=attrArr[i].split("\\|",-1);
							jdbc.update(conn, SQL_INSERT_CATEGORY_ATTRIBUTE, category.getId(),singleAttr[0], 
									singleAttr[1], singleAttr[2],singleAttr[3]);
						}
					}
				}
			} else{// update
				result = jdbc.update(conn, SQL_UPDATE_CATEGORY, category.getName(), category.getSaveDemand(), category.getPackDemand(),
						category.getEnviDemand(), category.getLevels(), category.getParentId(), category.getPriority(), 
						category.getValidStatus(), category.getType(), DateUtil.getCurrentDatetime(), category.getId());
				if(result > 0 ){
					entity = jdbc.queryForObject(conn, SQL_QUERY_CATEGORY_BY_ID, TCategory.class, category.getId());
					
					//先删除原来所有分类，再添加
					jdbc.update(conn, SQL_DELETE_CATEGORY_ATTRIBUTE_BY_CATEGORY_ID, category.getId());
					if(StringUtils.isNotBlank(category.getAttrString())){
						String[] attrArr=category.getAttrString().split(";",-1);
						for(int i=0;i<attrArr.length;i++){
							String[] singleAttr=attrArr[i].split("\\|",-1);
							jdbc.update(conn, SQL_INSERT_CATEGORY_ATTRIBUTE, category.getId(),singleAttr[0], 
									singleAttr[1], singleAttr[2],singleAttr[3]);
						}
					}
				}
			}
			
			conn.commit();
		} catch (Exception e) {
			log.error("", e);
		} finally{
			jdbc.closeAll();
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("", e);
			}
		}
		return entity;
	}
	
	public static TCategory queryById(Long id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TCategory entity = null;
		try {
			entity = jdbc.queryForObject(Env.DS, SQL_QUERY_CATEGORY_BY_ID, TCategory.class, id);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			jdbc.closeAll();
		}
		return entity;
	}
}
