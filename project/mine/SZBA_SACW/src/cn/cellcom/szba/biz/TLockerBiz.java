package cn.cellcom.szba.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TLocker;
import cn.cellcom.szba.domain.TLocker;
import cn.cellcom.szba.domain.TRack;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TLockerBiz {

	private static Log log=LogFactory.getLog(TLockerBiz.class);
	/*private static final String SQL_QUERY_LOCKER = "SELECT id,locker_num as \"lockerNum\","+" locker_volume as \"lockerVolume\","+
	" locker_type as \"lockerType\"," +" goods_num as \"goodsNum\"," +" inventory_status as \"inventoryStatus\","+"rfid_num as \"rfidNum\","+
	" rack_id as \"rackID\"," +"create_time as \"createTime\", "+"valid_status as \"validStatus\" " +
	" FROM t_locker " +
	" WHERE 1=1 " +
	" and (locker_num like blike:lockerNum) " +
	" and (locker_volume like blike:lockerVolume) " +
	" and (locker_type like blike:lockerType)" +
	" and (goods_num like blike:goodsNum) " +
	" and (inventory_status like blike:inventoryStatus) " +
	" and (rfid_num like blike:rfidNum) " +
	" and (rack_id like blike:rackID) " +
	" and (create_time like blike:createTime) " +
	" and (valid_status like blike:validStatus) " +
	" order by create_time";*/
	
	private static final String SQL_QUERY_LOCKER = "SELECT l.id,l.locker_num as \"lockerNum\","+" l.locker_volume as \"lockerVolume\","+
			" l.locker_type as \"lockerType\"," +" l.goods_num as \"goodsNum\"," +" l.inventory_status as \"inventoryStatus\","+"l.rfid_num as \"rfidNum\","+
			" l.rack_id as \"rackID\"," +"l.create_time as \"createTime\", "+"l.valid_status as \"validStatus\" " +
			" FROM t_locker l LEFT JOIN t_rack r ON l.rack_id=r.rack_num " +
			" WHERE 1=1 " +
			" and (locker_num like blike:lockerNum) " +
			" and (locker_volume like blike:lockerVolume) " +
			" and (locker_type like blike:lockerType)" +
			" and (goods_num like blike:goodsNum) " +
			" and (inventory_status like blike:inventoryStatus) " +
			" and (rfid_num like blike:rfidNum) " +
			" and (l.rack_id like blike:rackID) " +
			" and (l.create_time like blike:createTime) " +
			" and (l.valid_status like blike:validStatus) " +
			" order by l.create_time";
	
	private static final String SQL_QUERY_LOCKER_BY_ID = "SELECT id,locker_num as \"lockerNum\","+" locker_volume as \"lockerVolume\","+
	" locker_type as \"lockerType\"," +" goods_num as \"goodsNum\"," +" inventory_status as \"inventoryStatus\","+"rfid_num as \"rfidNum\","+
	" rack_id as \"rackID\"," +"create_time as \"createTime\", "+"valid_status as \"validStatus\""+
	" FROM t_locker WHERE locker_num=?";
	
	private static final String SQL_INSERT_LOCKER = "INSERT INTO t_locker(locker_num,locker_volume,locker_type,goods_num," +
	" inventory_status,rfid_num,rack_id) VALUES(?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE_LOCKER = "UPDATE t_locker SET locker_num=?,locker_volume=?,locker_type=?,goods_num=?," +
	" inventory_status=?,rfid_num=?,rack_id=? " +
	" WHERE locker_num=?";
	private static final String SQL_QUERY_LOCKER_BY_RACKID = "SELECT id,locker_num as \"lockerNum\",INVENTORY_STATUS as \"inventoryStatus\" from t_locker where RACK_ID=?";
	/**
	 * @param 带条件的分页查询
	 */
	public static ListAndPager<TLocker> queryForPage(Map<String, String[]> params, Pager page){
	JDBC jdbc = SpringWebApplicataionContext.getJdbc();
	ListAndPager<TLocker> list = new ListAndPager<TLocker>();
	try {
		DbRequest dbRequest = SqlParser.parse(SQL_QUERY_LOCKER, params);
		String sql = dbRequest.getSql();
		Object[] objParams = dbRequest.getParameters();
		list = jdbc.queryPage(Env.DS, sql, TLocker.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
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
	public static TLocker queryById(String lockerNum) {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		TLocker entity=new TLocker();
		try {
			entity=jdbc.queryForObject(Env.DS,SQL_QUERY_LOCKER_BY_ID , TLocker.class,lockerNum);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}

	/**
	 * 增加储物柜
	 */
	public static String insert(TLocker entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			TLocker Locker = null;
			Locker = jdbc.queryForObject(Env.DS, SQL_QUERY_LOCKER_BY_ID, TLocker.class, entity.getLockerNum());
			if(null != Locker){
				result = "exist";
			} else {
				int rows = jdbc.update(Env.DS, SQL_INSERT_LOCKER,entity.getLockerNum(),entity.getLockerVolume(),entity.getLockerType(),
						entity.getGoodsNum(),entity.getInventoryStatus(),entity.getRfidNum(),entity.getRackID());
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
	public static int update(TLocker entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try {
			TLocker record = jdbc.queryForObject(Env.DS, SQL_QUERY_LOCKER_BY_ID, TLocker.class, entity.getLockerNum());
			if(null == record){
				return 0;
			}
			if(StringUtils.isNotEmpty(entity.getLockerNum())){
				record.setLockerNum(entity.getLockerNum());
			}
			if(null !=String.valueOf(entity.getLockerVolume())){
				record.setLockerVolume(entity.getLockerVolume());
			}
			if(StringUtils.isNotEmpty(entity.getLockerType())){
				record.setLockerNum(entity.getLockerType());
			}
			if(null !=String.valueOf(entity.getGoodsNum())){
				record.setGoodsNum(entity.getGoodsNum());
			}
			if(StringUtils.isNotEmpty(entity.getInventoryStatus())){
				record.setLockerNum(entity.getInventoryStatus());
			}
			if(StringUtils.isNotEmpty(entity.getRfidNum())){
				record.setLockerNum(entity.getRfidNum());
			}
			if(null !=String.valueOf(entity.getRackID())){
				record.setRackID(entity.getRackID());
			}
			result = jdbc.update(Env.DS, SQL_UPDATE_LOCKER,entity.getLockerNum(),entity.getLockerVolume(),entity.getLockerType(),
					entity.getGoodsNum(),entity.getInventoryStatus(),entity.getRfidNum(),entity.getRackID(),entity.getLockerNum());
		} catch (Exception e) {
			log.error("", e);
		} finally{
			jdbc.closeAll();
		}
		return result;
	}
	//查询指定货架下的存储柜
	public static List<TLocker> queryLockById(String rackId) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TLocker> list;
		try {
			list=jdbc.query(Env.DS,SQL_QUERY_LOCKER_BY_RACKID , TLocker.class,rackId);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
}
