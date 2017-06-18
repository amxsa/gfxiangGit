package cn.cellcom.szba.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TRack;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TRackBiz {
	private static Log log=LogFactory.getLog(TRackBiz.class);
	
	private  static String SQL_QUERY_RACK_BY_SID="SELECT ID as\"id\",rack_num as \"rackNum\"," +"rack_name as \"rackName\""+
			" FROM t_rack where STOREROOM_ID=?";
	private static final String SQL_QUERY_RACK = "SELECT r.id,r.rack_num as \"rackNum\", " +" r.rack_height as \"rackHeight\", "+
			" r.rack_length as \"rackLength\" ," +" r.rack_load as \"rackLoad\","+" r.storeroom_id as \"storeroomId\","+" r.create_time as \"createTime\" ,"+
			" r.valid_status as \"validStatus\" FROM t_rack r";
			
	
	//查询存储柜下拉框所需货架编号和货架名
	private  static String SQL_QUERY_RACK_RACK_NAME="SELECT ID as\"id\",rack_num as \"rackNum\"," +"rack_name as \"rackName\""+
	" FROM t_rack ";
	
	private static final String SQL_QUERY_RACK_BY_ID = "SELECT a.id,a.rack_num as \"rackNum\", " +" a.rack_height as \"rackHeight\", "+
	" a.rack_length as \"rackLength\" ," +" a.rack_load as \"rackLoad\","+" a.storeroom_id as \"storeroomId\","+ " b.storeroom_name as \"storeroomName\""+
	" FROM t_rack a left join t_storeroom b on b.id=a.storeroom_id WHERE rack_num=?";
	
	private static final String SQL_INSERT_RACK = "INSERT INTO t_rack(rack_num,rack_height,rack_length,rack_load,storeroom_id,storehouse_id) " +
	" VALUES(?,?,?,?,?,?)";
	private static final String SQL_UPDATE_RACK = "UPDATE t_rack SET rack_num=?,rack_height=?,rack_length=?,rack_load=?,storeroom_id=?,storehouse_id=? " +
	" WHERE rack_num=?";
	
	private static final String SQL_QUERY_RACK_BY_RID = "SELECT a.id,a.rack_num as \"rackNum\", " +" a.rack_height as \"rackHeight\", "+
			" a.rack_length as \"rackLength\" ," +" a.rack_load as \"rackLoad\","+" a.storeroom_id as \"storeroomId\","+ " b.storeroom_name as \"storeroomName\""+
			" FROM t_rack a left join t_storeroom b on b.id=a.storeroom_id WHERE a.id=?";
	/**
	 * 通过ID查实体
	 */
	public static TRack queryByRId(Long id) {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		TRack entity=new TRack();
		try {
			entity=jdbc.queryForObject(Env.DS,SQL_QUERY_RACK_BY_RID , TRack.class,id);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}
	
	private static String commonCondition(){
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append(" and (rack_num like blike:rackNum)");
		sqlBuffer.append(" and (rack_height like blike:rackHeight)");
		sqlBuffer.append(" and (rack_length like blike:rackLength)");
		sqlBuffer.append(" and (rack_load like blike:rackLoad)");
		sqlBuffer.append(" and (r.storeroom_id like blike:storeroomId)");
		sqlBuffer.append(" and (r.create_time like blike:createTime)");
		sqlBuffer.append(" and (r.valid_status like blike:validStatus)");
		sqlBuffer.append(" order by r.create_time desc");
		
		return sqlBuffer.toString();
	}
	
	/**
	 * @param 带条件的分页查询 type=1,代表及时暂存库又是中心库管理员，type=2代表是暂存库或者中心库管理员
	 */
	public static ListAndPager<TRack> queryForPage(Map<String, String[]> params, Pager page,String deptId,Long type){
	JDBC jdbc = SpringWebApplicataionContext.getJdbc();
	ListAndPager<TRack> list = new ListAndPager<TRack>();
	try {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append(SQL_QUERY_RACK);
		if(StringUtils.isNotBlank(deptId)&&type==2){
			sqlBuffer.append(" ,(select sr.id,sr.STOREROOM_NAME from t_storeroom sr "
					+ "LEFT JOIN t_storehouse sh on sh.id=sr.storehouse_id where "
					+ "sh.dept_id="+deptId+") tt");
			sqlBuffer.append(" WHERE 1=1 and r.storeroom_id=tt.id");
		}else if(StringUtils.isNotBlank(deptId)&&type==1){
			sqlBuffer.append(" ,(select sr.id,sr.STOREROOM_NAME from t_storeroom sr "
					+ "LEFT JOIN t_storehouse sh on sh.id=sr.storehouse_id where "
					+ "sh.storehouse_num='1' or sh.dept_id="+deptId+") tt");
			sqlBuffer.append(" WHERE 1=1 and r.storeroom_id=tt.id");
		}else{
			sqlBuffer.append(" LEFT JOIN t_storeroom sr ON r.storeroom_id=sr.id");
			sqlBuffer.append(" WHERE 1=1");
		}
		sqlBuffer.append(commonCondition());
		
		DbRequest dbRequest = SqlParser.parse(sqlBuffer.toString(), params);
		String sql = dbRequest.getSql();
		Object[] objParams = dbRequest.getParameters();
		list = jdbc.queryPage(Env.DS, sql, TRack.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
	} catch (Exception e) {
		log.error(e);
		e.printStackTrace();
	} finally{
		jdbc.closeAll();
	}
	return list;
	}
	/**
	 * 通过ID查实体
	 */
	public static TRack queryById(String locationNum) {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		TRack entity=new TRack();
		try {
			entity=jdbc.queryForObject(Env.DS,SQL_QUERY_RACK_BY_ID , TRack.class,locationNum);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}

	/**
	 * 增加库位
	 */
	public static String insert(TRack entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			TRack Rack = null;
			Rack = jdbc.queryForObject(Env.DS, SQL_QUERY_RACK_BY_ID, TRack.class, entity.getRackNum());
			if(null != Rack){
				result = "exist";
			} else {
				TStoreRoom tsr=TStoreRoomBiz.queryByRId(entity.getStoreroomId());
				int rows = jdbc.update(Env.DS, SQL_INSERT_RACK,entity.getRackNum(),entity.getRackHeight(),
						entity.getRackLength(),entity.getRackLoad(),entity.getStoreroomId(),tsr.getStorehouseId());
				if(rows == 1){
					result = "success";
				}
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
	 * 修改库位信息
	 */
	public static int update(TRack entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try {
			TRack record = jdbc.queryForObject(Env.DS, SQL_QUERY_RACK_BY_ID, TRack.class, entity.getRackNum());
			if(null == record){
				return 0;
			}
			if(StringUtils.isNotEmpty(entity.getRackNum())){
				record.setRackNum(entity.getRackNum());
			}
			if(null !=String.valueOf(entity.getRackHeight())){
				record.setRackHeight(entity.getRackHeight());
			}
			if(null !=String.valueOf(entity.getRackLength())){
				record.setRackLength(entity.getRackLength());
			}
			if(null !=String.valueOf(entity.getRackLoad())){
				record.setRackLoad(entity.getRackLoad());
			}
			Long storeHouseId=record.getStorehouseId();
			if(null !=String.valueOf(entity.getStoreroomId())){
				record.setStoreroomId(entity.getStoreroomId());
				TStoreRoom tsr=TStoreRoomBiz.queryByRId(entity.getStoreroomId());
				storeHouseId=tsr.getStorehouseId();
			}
			result = jdbc.update(Env.DS, SQL_UPDATE_RACK,record.getRackNum(),record.getRackHeight(),
					record.getRackLength(),record.getRackLoad(),record.getStoreroomId(),storeHouseId,record.getRackNum());
		} catch (Exception e) {
			log.error("", e);
		} finally{
			jdbc.closeAll();
		}
		return result;
	}
	//查询存储柜下拉框所需货架编号和货架名
	public static List<TRack> queryName() {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TRack> list;
		try {
			list=jdbc.query(Env.DS,SQL_QUERY_RACK_RACK_NAME , TRack.class);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	//查询指定库房下的货架
	public static List<TRack> queryNameBySid(String storeroomId) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TRack> list;
		try {
			list=jdbc.query(Env.DS,SQL_QUERY_RACK_BY_SID , TRack.class,storeroomId);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
}
