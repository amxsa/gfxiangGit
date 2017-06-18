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
import cn.cellcom.szba.domain.TStoreHouse;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;

public class TStoreRoomBiz {
	private static Log log = LogFactory.getLog(TStoreRoomBiz.class);
	
	private static String SQL_QUERY_STOREROOM="SELECT a.id , a.storeroom_num as \"storeroomNum\", " + " a.storeroom_name as \"storeroomName\", " +
	" a.storeroom_area as \"storeroomArea\"," +" a.storeroom_type as \"storeroomType\","+" a.storeroom_addr as \"storeroomAddr\","+
	" a.room_num as \"roomNum\","+"a.storehouse_id as \"storehouseId\","+"b.storehouse_name as \"storehouseName\","+"a.create_time as \"createTime\","+"a.valid_status as \"validStatus\""+
	" FROM t_storeroom a left join t_storehouse b on b.id=a.storehouse_id" +
	" WHERE 1=1 " +
	" and (a.id like blike:id) " +
	" and (a.storeroom_num like blike:storeroomNum) " +
	" and (a.storeroom_name like blike:storeroomName)" +
	" and (a.storeroom_area like blike:storeroomArea) " +
	" and (a.storeroom_type like blike:storeroomType) " +
	" and (a.storeroom_addr like blike:storeroomAddr) " + 
	" and (a.room_num like blike:roomNum) " +
	" and (a.create_time like blike:createTime) " +
	" and (a.valid_status like blike:validStatus) "+
	" and (a.storehouse_id like blike:storehouseId) "+
	" and ((b.dept_id=string:deptId) or (b.storehouse_num=string:storehouseNum))"+
	" order by a.create_time desc";
	
	//查询库房下拉框所需库房编号和库房名
	private  static String SQL_QUERY_STOREROOM_STOREROOM_NAME="SELECT a.ID as\"id\",a.storeroom_num as \"storeroomNum\"," +"a.storeroom_name as \"storeroomName\","
	+"a.storeroom_type as \"storeroomType\","+"a.storehouse_id as \"storehouseId\""+
	" FROM t_storeroom a";
	
	private static String SQL_QUERY_STOREROOM_BY_STORENUM="SELECT a.*,b.storehouse_name as \"storehouseName\" "
			+ "FROM t_storeroom a left join t_storehouse b on b.id=a.storehouse_id WHERE a.storeroom_num=?";
	
	private static String SQL_QUERY_STOREROOM_BY_ID="SELECT a.*,b.storehouse_name as \"storehouseName\" "
			+ "FROM t_storeroom a left join t_storehouse b on b.id=a.storehouse_id WHERE a.id=?";
	
	private static String SQL_INSERT_STOREROOM="INSERT INTO t_storeroom(storeroom_num,storeroom_name,storeroom_area," +
			" storeroom_type,storeroom_addr,room_num,storehouse_id) "+
			"values(?,?,?,?,?,?,?)";
	
	private static String SQL_UPDATE_STOREROOM="UPDATE t_storeroom set storeroom_num =? ,storeroom_name=?,storeroom_area=?," +
	" storeroom_type=?,storeroom_addr=?,room_num=?,storehouse_id=? where storeroom_num=?";
	
	public static TStoreRoom queryByRId(Long id) {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		TStoreRoom entity=new TStoreRoom();
		try {
			entity=jdbc.queryForObject(Env.DS,SQL_QUERY_STOREROOM_BY_ID , TStoreRoom.class,id);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}
	
	//查询仓库下拉框所需仓库id和仓库名  type=1,代表及时暂存库又是中心库管理员，type=2代表是暂存库或者中心库管理员
	public static List<TStoreHouse> queryStoreHouse(Long deptId,Long type) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TStoreHouse> list;
		try {
			String SQL_QUERY_STOREHOUSE="SELECT id as \"id\",storehouse_name as \"storehouseName\", storehouse_num FROM t_storehouse "
					+ "where 1=1 ";
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(SQL_QUERY_STOREHOUSE);
			if(deptId!=null&&type.intValue()==2){
				sqlBuffer.append("and dept_id="+deptId+" ");
			}
			if(deptId!=null&&type.intValue()==1){
				sqlBuffer.append("and dept_id="+deptId+" or dept_id=0 ");
			}
			sqlBuffer.append("order by create_time");
			list=jdbc.query(Env.DS,sqlBuffer.toString() , TStoreHouse.class);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
		
	/**
	 * 带条件的分页查询
	 * @param params 条件
	 * @param page   分页参数
	 * @return
	 */
	public static ListAndPager<TStoreRoom> queryForPage(Map<String, String[]> params, Pager page,String deptId,Long type){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TStoreRoom> list = new ListAndPager<TStoreRoom>();
		try {
			Map<String, String[]> extraMap = new HashMap<String, String[]>();
			if(StringUtils.isNotBlank(deptId)&&type.intValue()==2){
				extraMap.put("deptId", new String[]{deptId});
			}else if(StringUtils.isNotBlank(deptId)&&type.intValue()==1){
				extraMap.put("deptId", new String[]{deptId});
				extraMap.put("storehouseNum", new String[]{"1"});
			}
			extraMap = RequestUtils.addExtraParamToRequestParamMap(params, extraMap);
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_STOREROOM, extraMap);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TStoreRoom.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}


	public static TStoreRoom queryById(String storeroomNum) {
		JDBC jdbc=SpringWebApplicataionContext.getJdbc();
		TStoreRoom entity=new TStoreRoom();
		try {
			entity=jdbc.queryForObject(Env.DS,SQL_QUERY_STOREROOM_BY_STORENUM , TStoreRoom.class,storeroomNum);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}

	/**
	 * 增加库房
	 */
	public static String insert(TStoreRoom entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			TStoreRoom storeroomNum = null;
			storeroomNum = jdbc.queryForObject(Env.DS, SQL_QUERY_STOREROOM_BY_STORENUM, TStoreRoom.class, entity.getStoreroomNum());
			if(null != storeroomNum){
				result = "exist";
			} else {
				int rows = jdbc.update(Env.DS, SQL_INSERT_STOREROOM, entity.getStoreroomNum(),entity.getStoreroomName(),
						entity.getStoreroomArea(),entity.getStoreroomType(),entity.getStoreroomAddr(),entity.getRoomNum(),
						entity.getStorehouseId());
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
	 * 修改库房信息
	 */
	public static int update(TStoreRoom entity) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try {
			TStoreRoom record = jdbc.queryForObject(Env.DS, SQL_QUERY_STOREROOM_BY_STORENUM, TStoreRoom.class, entity.getStoreroomNum());
			if(null == record){
				return 0;
			}
			if(StringUtils.isNotEmpty(entity.getStoreroomNum())){
				record.setStoreroomNum(entity.getStoreroomNum());
			}
			if(StringUtils.isNotEmpty(entity.getStoreroomName())){
				record.setStoreroomName(entity.getStoreroomName());
			}
			if(StringUtils.isNotEmpty(entity.getStoreroomType())){
				record.setStoreroomType(entity.getStoreroomType());
			}
			if(null !=String.valueOf(entity.getStoreroomArea())){
				record.setStoreroomArea(entity.getStoreroomArea());
			}
			if(StringUtils.isNotEmpty(entity.getStoreroomAddr())){
				record.setStoreroomAddr(entity.getStoreroomAddr());
			}
			if(null !=String.valueOf(entity.getStorehouseId())){
				record.setStorehouseId(entity.getStorehouseId());
			}
			if(StringUtils.isNotEmpty(entity.getRoomNum())){
				record.setRoomNum(entity.getRoomNum());
			}
			result = jdbc.update(Env.DS, SQL_UPDATE_STOREROOM,record.getStoreroomNum(),record.getStoreroomName(),
					record.getStoreroomArea(),record.getStoreroomType(),record.getStoreroomAddr(),record.getRoomNum(),
					record.getStorehouseId(),record.getStoreroomNum());
		} catch (Exception e) {
			log.error("", e);
		} finally{
			jdbc.closeAll();
		}
		return result;
	}

	//查询库房下拉框所需库房编号和库房名 type=1,代表及时暂存库又是中心库管理员，type=2代表是暂存库或者中心库管理员
	public static List<TStoreRoom> queryName(Long deptId,Long type) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TStoreRoom> list;
		try {
			StringBuffer sqlBuffer=new StringBuffer();
			sqlBuffer.append(SQL_QUERY_STOREROOM_STOREROOM_NAME);
			if(deptId!=null&&type.intValue()==2){
				sqlBuffer.append(" left join t_storehouse st on st.id = a.storehouse_id ");
				sqlBuffer.append(" where st.dept_id="+deptId+"");
			}else if(deptId!=null&&type.intValue()==1){
				sqlBuffer.append(" left join t_storehouse st on st.id = a.storehouse_id ");
				sqlBuffer.append(" where st.storehouse_num='1' ");
				sqlBuffer.append("or st.dept_id="+deptId+"");
			}
			list=jdbc.query(Env.DS,sqlBuffer.toString() , TStoreRoom.class);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}


	
}
