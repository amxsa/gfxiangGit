package cn.cellcom.szba.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.RequestUtils;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TRack;
import cn.cellcom.szba.domain.TStorageLocation;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TStorageLocationBiz {
	private static Log log=LogFactory.getLog(TStorageLocationBiz.class);
	private static final String SQL_QUERY_STORAGELOCATION_BY_STROOMID="SELECT id," +"location_num as \"locationNum\",INVENTORY_STATUS as \"inventoryStatus\" from t_storage_location where STOREROOM_ID=?";
	
	private static final String SQL_QUERY_STORAGELOCATION = "SELECT sl.id," +"sl.location_num as \"locationNum\","+"sl.location_name as \"locationName\","+
			" sl.location_type as \"locationType\","+" sl.goods_num as \"goodsNum\" ,"+"sl.inventory_status as \"inventoryStatus\","+"sl.rfid_num as \"rfidNum\","+"sl.rack_id as \"rackId\","+
			" sl.capacity as \"capacity\","+"sl.deposit_num as \"depositNum\","+"sl.build_num as \"buildNum\","+"sl.build_level as \"buildLevel\","+"sl.counter_num as \"counterNum\","+"sl.address as \"address\","+
			" sl.storeroom_id as \"storeroomID\","+" sl.create_time as \"createTime\" ,"+" sl.valid_status as \"validStatus\" "+
			" FROM t_storage_location sl";
			
	
	private static final String SQL_QUERY_STORAGELOCATION_BY_ID = "SELECT id," +"location_num as \"locationNum\","+"location_name as \"locationName\","+
	" location_type as \"locationType\","+" goods_num as \"goodsNum\" ,"+"inventory_status as \"inventoryStatus\","+
	" capacity as \"capacity\","+" deposit_num as \"depositNum\","+" build_num as \"buildNum\","+" build_level as \"buildLevel\","+" counter_num as \"counterNum\","+" address as \"address\","+
	" rfid_num as \"rfidNum\","+" storeroom_id as \"storeroomID\","+" create_time as \"createTime\" ,"+" valid_status as \"validStatus\", "+ 
	" rack_id as \"rackId\" "+ 
	" FROM t_storage_location WHERE location_num=?";
	
	private static final String SQL_INSERT_STORAGELOCATION = "INSERT INTO t_storage_location(location_num,location_name,location_type,goods_num," +
			" inventory_status,rfid_num,storeroom_id,rack_id,capacity,deposit_num,build_num,build_level,counter_num,address) "+
			"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE_STORAGELOCATION = "UPDATE t_storage_location set location_num =? ,location_name=?,location_type=?,goods_num=?," +
	" inventory_status=?,rfid_num=?,storeroom_id=?,rack_id=?,capacity=?,deposit_num=?,build_num=?,build_level=?,counter_num=?,address=? where location_num=?";

	private static final String SQL_QUERY_RACK_BY_ID = "SELECT id,rack_num as \"rackNum\", " +" rack_height as \"rackHeight\", "+
			" rack_length as \"rackLength\" ," +" rack_load as \"rackLoad\","+" storeroom_id as \"storeroomID\""+ 
			" FROM t_rack WHERE id=?";
	
	private static final String SQL_QUERY_STORAGELOCATION_BY_SIDORRID = "SELECT id," +"location_num as \"locationNum\","+"location_name as \"locationName\","+
			" location_type as \"locationType\","+" goods_num as \"goodsNum\" ,"+"inventory_status as \"inventoryStatus\","+
			" capacity as \"capacity\","+" deposit_num as \"depositNum\","+" build_num as \"buildNum\","+" build_level as \"buildLevel\","+" counter_num as \"counterNum\","+" address as \"address\","+
			" rfid_num as \"rfidNum\","+" storeroom_id as \"storeroomID\","+" create_time as \"createTime\" ,"+" valid_status as \"validStatus\", "+ 
			" rack_id as \"rackId\" "+ 
			" FROM t_storage_location WHERE storeroom_id=? or rack_id=? order by create_time desc";
	
	//type=1,代表及时暂存库又是中心库管理员，type=2代表是暂存库或者中心库管理员
	public static List<TStorageLocation> queryBySidOrRid(String storeroomId,String rackId,String deptId,Long type){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TStorageLocation> list = null;
		try {
			StringBuffer sqlBuffer=new StringBuffer();
			sqlBuffer.append(SQL_QUERY_STORAGELOCATION);
			if(StringUtils.isNotBlank(deptId)){
				if(type.intValue()==1){
					sqlBuffer.append(" WHERE 1=1 and (sl.storeroom_id in (select sr.id from t_storeroom sr "
							+ "LEFT JOIN t_storehouse sh on sh.id=sr.storehouse_id where "
							+ "sh.storehouse_num='1' or sh.dept_id="+deptId+")");
					sqlBuffer.append(" or sl.rack_id in (select rk.id from t_rack rk "
							+ "LEFT JOIN t_storehouse sh on sh.id=rk.storehouse_id where "
							+ "sh.storehouse_num='1' or sh.dept_id="+deptId+"))");
				}else if(type.intValue()==2){
					sqlBuffer.append(" WHERE 1=1 and (sl.storeroom_id in (select sr.id from t_storeroom sr "
							+ "LEFT JOIN t_storehouse sh on sh.id=sr.storehouse_id where "
							+ "sh.dept_id="+deptId+")");
					sqlBuffer.append(" or sl.rack_id in (select rk.id from t_rack rk "
							+ "LEFT JOIN t_storehouse sh on sh.id=rk.storehouse_id where "
							+ "sh.dept_id="+deptId+"))");
				}
			}else{
				sqlBuffer.append(" WHERE 1=1 and (sl.storeroom_id in (select id from t_storeroom)");
				sqlBuffer.append(" or sl.rack_id in (select id from t_rack))");
			}
			if(StringUtils.isNotBlank(storeroomId)){
				sqlBuffer.append(" and (storeroom_id = "+storeroomId+")");
			}else if(StringUtils.isNotBlank(rackId)){
				sqlBuffer.append(" and (rack_id = "+rackId+")");
			}
			list = jdbc.query(Env.DS, sqlBuffer.toString(), TStorageLocation.class);
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	/**
	 * 通过ID查实体
	 */
	public static TRack queryRackById(Long id) {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		TRack entity=new TRack();
		try {
			entity=jdbc.queryForObject(Env.DS,SQL_QUERY_RACK_BY_ID , TRack.class,id);
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
		
		sqlBuffer.append(" and (location_num like blike:locationNum)");
		sqlBuffer.append(" and (location_type like blike:locationType)");
		sqlBuffer.append(" and (goods_num like blike:goodsNum)");
		sqlBuffer.append(" and (inventory_status like blike:inventoryStatus)");
		sqlBuffer.append(" and (rfid_num like blike:rfidNum)");
		sqlBuffer.append(" and (create_time like blike:createTime)");
		sqlBuffer.append(" and (valid_status like blike:validStatus)");
		sqlBuffer.append(" order by sl.create_time desc");
		
		return sqlBuffer.toString();
	}
	
	/**
	 * @param 带条件的分页查询 type=1,代表及时暂存库又是中心库管理员，type=2代表是暂存库或者中心库管理员
	 */
	public static ListAndPager<TStorageLocation> queryForPage(Map<String, String[]> params, Pager page,String deptId,Long type){
	JDBC jdbc = SpringWebApplicataionContext.getJdbc();
	ListAndPager<TStorageLocation> list = new ListAndPager<TStorageLocation>();
	try {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append(SQL_QUERY_STORAGELOCATION);
		
		//deptId不为空，只查看中心库以及登录人本身暂存库库位
		if(StringUtils.isNotBlank(deptId)){
			if(type.intValue()==1){
				sqlBuffer.append(" WHERE 1=1 and (sl.storeroom_id in (select sr.id from t_storeroom sr "
						+ "LEFT JOIN t_storehouse sh on sh.id=sr.storehouse_id where "
						+ "sh.storehouse_num='1' or sh.dept_id="+deptId+")");
				sqlBuffer.append(" or sl.rack_id in (select rk.id from t_rack rk "
						+ "LEFT JOIN t_storehouse sh on sh.id=rk.storehouse_id where "
						+ "sh.storehouse_num='1' or sh.dept_id="+deptId+"))");
			}else if(type.intValue()==2){
				sqlBuffer.append(" WHERE 1=1 and (sl.storeroom_id in (select sr.id from t_storeroom sr "
						+ "LEFT JOIN t_storehouse sh on sh.id=sr.storehouse_id where "
						+ "sh.dept_id="+deptId+")");
				sqlBuffer.append(" or sl.rack_id in (select rk.id from t_rack rk "
						+ "LEFT JOIN t_storehouse sh on sh.id=rk.storehouse_id where "
						+ "sh.dept_id="+deptId+"))");
			}
			
			sqlBuffer.append(" and (storeroom_id like blike:storeroomID)");
			sqlBuffer.append(" and (rack_id like blike:rackId)");
		}else{
			sqlBuffer.append(" WHERE 1=1 and (sl.storeroom_id in (select id from t_storeroom)");
			sqlBuffer.append(" or sl.rack_id in (select id from t_rack))");
			sqlBuffer.append(" and (storeroom_id like blike:storeroomID)");
			sqlBuffer.append(" and (rack_id like blike:rackId)");
		}
		
		sqlBuffer.append(commonCondition());
		DbRequest dbRequest = SqlParser.parse(sqlBuffer.toString(), params);
		String sql = dbRequest.getSql();
		Object[] objParams = dbRequest.getParameters();
		list = jdbc.queryPage(Env.DS, sql, TStorageLocation.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
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
	public static TStorageLocation queryById(String locationNum) {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		TStorageLocation entity=new TStorageLocation();
		try {
			entity=jdbc.queryForObject(Env.DS,SQL_QUERY_STORAGELOCATION_BY_ID , TStorageLocation.class,locationNum);
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
	public static String insert(TStorageLocation entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			TStorageLocation storageLocation = null;
			storageLocation = jdbc.queryForObject(Env.DS, SQL_QUERY_STORAGELOCATION_BY_ID, TStorageLocation.class, entity.getLocationNum());
			if(null != storageLocation){
				result = "exist";
			} else {
				int rows = jdbc.update(Env.DS, SQL_INSERT_STORAGELOCATION,entity.getLocationNum(),entity.getLocationName(),entity.getLocationType(),
						entity.getGoodsNum(),entity.getInventoryStatus(),entity.getRfidNum(),entity.getStoreroomID(),entity.getRackId(),
						entity.getCapacity(),entity.getDepositNum(),entity.getBuildNum(),entity.getBuildLevel(),entity.getCounterNum(),entity.getAddress());
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
	public static int update(TStorageLocation entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try {
			TStorageLocation record = jdbc.queryForObject(Env.DS, SQL_QUERY_STORAGELOCATION_BY_ID, TStorageLocation.class, entity.getLocationNum());
			if(null == record){
				return 0;
			}
			if(StringUtils.isNotEmpty(entity.getLocationNum())){
				record.setLocationNum(entity.getLocationNum());
			}
			if(StringUtils.isNotEmpty(entity.getLocationType())){
				record.setLocationType(entity.getLocationType());
			}
			if(null !=String.valueOf(entity.getGoodsNum())){
				record.setGoodsNum(entity.getGoodsNum());
			}
			if(StringUtils.isNotEmpty(entity.getInventoryStatus())){
				record.setInventoryStatus(entity.getInventoryStatus());
			}
			if(null !=String.valueOf(entity.getRfidNum())){
				record.setRfidNum(entity.getRfidNum());
			}
			if(null !=String.valueOf(entity.getStoreroomID())){
				record.setStoreroomID(entity.getStoreroomID());
			}
			if(null !=String.valueOf(entity.getRackId())){
				record.setRackId(entity.getRackId());
			}
			if(StringUtils.isNotEmpty(entity.getLocationName())){
				record.setLocationName(entity.getLocationName());
			}
			if(null !=String.valueOf(entity.getCapacity())){
				record.setCapacity(entity.getCapacity());
			}
			if(null !=String.valueOf(entity.getDepositNum())){
				record.setDepositNum(entity.getDepositNum());
			}
			if(StringUtils.isNotEmpty(entity.getBuildNum())){
				record.setBuildNum(entity.getBuildNum());
			}
			if(StringUtils.isNotEmpty(entity.getBuildLevel())){
				record.setBuildLevel(entity.getBuildLevel());
			}
			if(null !=String.valueOf(entity.getCounterNum())){
				record.setCounterNum(entity.getCounterNum());
			}
			if(StringUtils.isNotEmpty(entity.getAddress())){
				record.setAddress(entity.getAddress());
			}
			result = jdbc.update(Env.DS, SQL_UPDATE_STORAGELOCATION,record.getLocationNum(),record.getLocationName(),record.getLocationType(),
					record.getGoodsNum(),record.getInventoryStatus(),record.getRfidNum(),record.getStoreroomID(),record.getRackId(),
					record.getCapacity(),record.getDepositNum(),record.getBuildNum(),record.getBuildLevel(),record.getCounterNum(),record.getAddress(), record.getLocationNum());
		} catch (Exception e) {
			log.error("", e);
		} finally{
			jdbc.closeAll();
		}
		return result;
	}
	//查询指定库房下的库位
	public static List<TStorageLocation> queryStorageLocationById(String stlId) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TStorageLocation> list;
		try {
			list=jdbc.query(Env.DS,SQL_QUERY_STORAGELOCATION_BY_STROOMID , TStorageLocation.class,stlId);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
}
