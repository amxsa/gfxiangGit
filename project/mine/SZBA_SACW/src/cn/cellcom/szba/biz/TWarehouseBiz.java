package cn.cellcom.szba.biz;

import static cn.cellcom.szba.common.Env.DS;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TDepartment;
import cn.cellcom.szba.domain.TWarehouse;
import cn.open.db.JDBC;
import cn.open.db.JdbcResultSet;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.ProcedureParameter;
import cn.open.db.ProcedureResult;
import cn.open.db.SqlParser;
import cn.open.db.JDBC.PROCEDURE_PARAMTER_TYPE;
import cn.open.db.SqlParser.DbRequest;

public class TWarehouseBiz {
	private static Log log = LogFactory.getLog(TWarehouseBiz.class);
	
	private static String SQL_INSERT_WAREHOUSE = 
			"insert into t_warehouse(serial_number, name, department_id, category, volume, gross_quantity, exist_quantity, "
			+ "building, floor, room, cabinet, address, status, create_time, creator, valid_status) "
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'),?,?)";
	
	private static String SQL_UPDATE_WAREHOUSE = 
			"update t_warehouse set serial_number = ?, name = ?, department_id = ?, category = ?, volume = ?, gross_quantity = ?, exist_quantity = ?, "
			+ "building = ?, floor = ?, room = ?, cabinet = ?, address = ?, status = ? where id = ?";
	
	private static String SQL_QUERY_WAREHOUSE_BY_DEPARTMENT = 
			"select id, serial_number, name, department_id, category, volume, gross_quantity, exist_quantity, building, floor, "
			+ "room, cabinet, address, status, create_time, creator, valid_status from t_warehouse where valid_status = 'Y' and department_id = ?";
	
	private static String SQL_QUERY_WAREHOUSE_FOR_PAGE = 
			"select id, serial_number, name, department_id, category, volume, gross_quantity, exist_quantity, building, floor, "
			+ "room, cabinet, address, status, create_time, creator, valid_status from t_warehouse "
			+ " where valid_status = 'Y' "
			+ " and (department_id like blike:departmentId)"
			+ " and (serial_number like blike:serialNumber)"
			+ " and (name like blike:name)";
	
	private static String SQL_QUERY_WAREHOUSE_BY_ID = 
			"select id, serial_number, name, department_id, category, volume, gross_quantity, exist_quantity, building, floor, "
			+ "room, cabinet, address, status, create_time, creator, valid_status from t_warehouse where valid_status = 'Y' and id = ?";
	
	private static String SQL_DELETE_WAREHOUSE_BY_ID = 
			"update t_warehouse set valid_status = 'N' where id = ?";
	
	private static String SQL_FIND_WAREHOUSE_BY_DEPARTMENT = "select tw.id, tw.serial_number, tw.name, tw.department_id, "
			+ "  tw.category, tw.volume, tw.gross_quantity, tw.exist_quantity, tw.building, tw.floor, tw.room, "
			+ "  tw.cabinet, tw.address, tw.status, tw.create_time, tw.creator, tw.valid_status"
			+ " from t_warehouse tw inner join t_department td on tw.department_id = td.id"
			+ " where tw.valid_status = 'Y' and ("
			+ "  ((select category from t_department where id = ?) = '1' and tw.department_id = ?) "
			+ "   or ((select category from t_department where id = ?) = '2' and tw.department_id in ("
			+ "     select id from t_department where category = '2')))";
	
	public static boolean insert(TWarehouse entity){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try{
			String today = DateUtil.getCurrentDatetime();
			result = jdbc.update(Env.DS, SQL_INSERT_WAREHOUSE, entity.getSerialNumber(), entity.getName(), entity.getDepartmentId(),
					entity.getCategory(), entity.getVolume(), entity.getGrossQuantity(), entity.getExistQuantity(),
					entity.getBuilding(), entity.getFloor(), entity.getRoom(), entity.getCabinet(), entity.getAddress(),
					entity.getStatus(), today, entity.getCreator(), entity.getValidStatus());
			
		} catch(Exception e){
			log.error(e.getMessage(), e);
		} finally{
			jdbc.closeAll();
		}
		return result > 0 ? true: false;
	}
	
	public static boolean update(TWarehouse entity){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try{
			result = jdbc.update(Env.DS, SQL_UPDATE_WAREHOUSE, entity.getSerialNumber(), entity.getName(), entity.getDepartmentId(),
					entity.getCategory(), entity.getVolume(), entity.getGrossQuantity(), entity.getExistQuantity(),
					entity.getBuilding(), entity.getFloor(), entity.getRoom(), entity.getCabinet(), entity.getAddress(),
					entity.getStatus(), entity.getId());
			
		} catch(Exception e){
			log.error(e.getMessage(), e);
		} finally{
			jdbc.closeAll();
		}
		return result > 0 ? true: false;
	}
	
	public static boolean delete(long id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try{
			result = jdbc.update(Env.DS, SQL_DELETE_WAREHOUSE_BY_ID, id);
			
		} catch(Exception e){
			log.error(e.getMessage(), e);
		} finally{
			jdbc.closeAll();
		}
		return result > 0 ? true: false;
	}
	
	public static List<TWarehouse> findWarehouseByDepartment(long departmentId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TWarehouse> list = null;
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_WAREHOUSE_BY_DEPARTMENT, TWarehouse.class, departmentId);
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public static TWarehouse findWarehouseById(long id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TWarehouse entity = null;
		try {
			entity = jdbc.queryForObject(Env.DS, SQL_QUERY_WAREHOUSE_BY_ID, TWarehouse.class, id);
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}
	
	public static ListAndPager<TWarehouse> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TWarehouse> list = new ListAndPager<TWarehouse>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_WAREHOUSE_FOR_PAGE, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TWarehouse.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public static List<TWarehouse> findWarehouseByDepartment(String departmentId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TWarehouse> list = null;
		
		try {
			list = jdbc.query(Env.DS, SQL_FIND_WAREHOUSE_BY_DEPARTMENT, TWarehouse.class, departmentId, departmentId, departmentId);
		} catch (Exception e) {
			log.error("",e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
}
