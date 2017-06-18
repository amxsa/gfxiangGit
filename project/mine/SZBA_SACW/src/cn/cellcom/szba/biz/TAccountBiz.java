package cn.cellcom.szba.biz;

import static cn.cellcom.szba.common.Env.DS;
import static cn.cellcom.szba.common.Env.accountMap;
import static cn.cellcom.szba.common.Env.departmentMap;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TDepartment;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.domain.TStoreHouse;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;
import cn.open.util.ArrayUtil;

public class TAccountBiz {
	private static Log log = LogFactory.getLog(TAccountBiz.class);
	
	private static String SQL_QUERY_ACCOUNT = "SELECT ta.account, ta.workno, ta.status, ta.mobile, ta.name, ta.gender, ta.id_type as \"idType\", " +
		"  ta.id_num as \"idNum\", ta.department_id as \"departmentID\", ta.create_time as \"createTime\", " +
		"  ta.update_time as \"updateTime\", ta.expire_time as \"expireTime\" " +
		"FROM t_account ta " +
		"WHERE 1=1 and (ta.account like blike:account) " +
		"  and (ta.workno like blike:workno) " +
		"  and (ta.status = string:status) " +
		"  and (ta.mobile like blike:mobile) " +
		"  and (ta.name like blike:name) " +
		"  and (ta.gender like blike:gender) " +
		"  and (ta.id_type = string:idType) " +
		"  and (ta.id_num like blike:idNum) " +
		"  and (ta.department_id = string:departmentID)";
	
	private static String SQL_QUERY_ACCOUNT_BY_ID = "SELECT ta.account, ta.password, ta.workno, ta.status, ta.mobile, ta.name, ta.gender, ta.id_type as \"idType\"," + 
		"  ta.id_num as \"idNum\", ta.department_id as \"departmentID\", ta.create_time as \"createTime\", " + 
		"  ta.update_time as \"updateTime\", ta.expire_time as \"expireTime\" " +
		"FROM t_account ta " +
		"WHERE ta.account = ?";
	
	private static String SQL_INSERT_ACCOUNT = 
			"INSERT INTO t_account(account, workno, password, status, mobile, name, gender, id_type, id_num, department_id) " + 
			"VALUES(?,?,?,?,?,?,?,?,?,?)";
	
	private static String SQL_UPDATE_ACCOUNT = 
			"UPDATE t_account SET workno = ?, password = ?, status = ?, mobile = ?, name = ?, gender = ?, id_type = ?, "
			+ "id_num = ?, department_id = ?, update_time = TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"') WHERE account = ?";
	
	private static String SQL_QUERY_DEPARTMENT = 
			"select id,parent_id,name,category,work_priority,status,levels from t_department where status = 'Y' start with id = 1 connect by prior id = parent_id";
	
	private static String SQL_QUERY_MAX_DEPARTMENT_ID_BY_LEVELS = "select max(id) as id from t_department where levels = ?";
	
	private static String SQL_INSERT_DEPARTMENT = "insert into t_department(id, name, parent_id, status, levels, work_priority, "
			+ "category, create_time) values(?,?,?,?,?,?,?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'))";
	
	private static String SQL_UPDATE_DEPARTMENT = "update t_department set name = ?, status = ?, work_priority = ?, "
			+ "category = ?, update_time = TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"') where id = ?";
	
	private static String SQL_QUERY_DEPARTMENT_BY_ID = "select id,parent_id,name,category,work_priority,status,levels from t_department where id = ?";
	
	//根据角色查看用户
	private static String SQL_QUERY_ROLE_ACCOUNT ="select b.* from t_role_account a left join t_account b on a.account_id=b.account " +
					" where a.role_id = ?";
	
	//仓库
	private static String SQL_INSERT_STOREHOUSE = "insert into t_storehouse(storehouse_num,storehouse_name,"
			+ "storehouse_type,dept_id ) values(?,?,?,?)";
	private static String SQL_UPDATE_STOREHOUSE = "update t_storehouse set storehouse_name=? where id=?";
	private static String SQL_DELETE_STOREHOUSE = "delete from t_storehouse where id=?";
	private static String SQL_QUERY_STOREHOUSE_BY_DEPTID = "select * from t_storehouse where dept_id = ?";
	
	/**
	 * 根据角色查看用户
	 * @param params
	 * @param page
	 * @return 
	 * @return
	 */
	public List<TAccount> roleAccounts(Long roleId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TAccount> list = new ArrayList<TAccount>();
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_ROLE_ACCOUNT, TAccount.class, roleId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}finally{
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
	public ListAndPager<TAccount> queryForPage(Map<String, String[]> params, Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TAccount> list = new ListAndPager<TAccount>();
		
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_QUERY_ACCOUNT, params);
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			list = jdbc.queryPage(Env.DS, sql, TAccount.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
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
	public ListAndPager<TAccount> queryForPage2(Pager page){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		ListAndPager<TAccount> list = new ListAndPager<TAccount>();
		String sql = "SELECT ta.account, ta.workno, ta.status, ta.mobile, ta.name, ta.gender, ta.id_type as \"idType\", " +
		"  ta.id_num as \"idNum\", ta.department_id as \"departmentID\", ta.create_time as \"createTime\", " +
		"  ta.update_time as \"updateTime\", ta.expire_time as \"expireTime\" " +
		"FROM t_account ta where 1=1 ";
		try {
          list = jdbc.queryPage(Env.DS, sql, TAccount.class, page.getCurrentIndex(), page.getSizePerPage());	
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	/**
	 * 根据账号查询用户
	 * @param account
	 * @return
	 */
	public TAccount queryById(String account){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TAccount entity = null;
		try{
			entity = jdbc.queryForObject(Env.DS, SQL_QUERY_ACCOUNT_BY_ID, TAccount.class, account);
		} catch (Exception e){
			log.error(e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
		}
		return entity;
	}
	
	/**
	 * 新增用户
	 * @param entity
	 * @return
	 */
	public String insert(TAccount entity){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String result = "fail";
		try {
			TAccount account = null;
			account = jdbc.queryForObject(Env.DS, SQL_QUERY_ACCOUNT_BY_ID, TAccount.class, entity.getAccount());
			if(null != account){
				result = "exist";
			} else {
				int rows = jdbc.update(Env.DS, SQL_INSERT_ACCOUNT, entity.getAccount(), entity.getWorkno(), entity.getPassword(),
						entity.getStatus(), entity.getMobile(), entity.getName(), entity.getGender(), entity.getIdType(), entity.getIdNum(),
						entity.getDepartmentID());
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
	 * 修改用户信息
	 * @param entity
	 * @return
	 */
	public int update(TAccount entity){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try {
			TAccount record = jdbc.queryForObject(Env.DS, SQL_QUERY_ACCOUNT_BY_ID, TAccount.class, entity.getAccount());
			if(null == record){
				return 0;
			}
			if(StringUtils.isNotEmpty(entity.getWorkno())){
				record.setWorkno(entity.getWorkno());
			}
			if(StringUtils.isNotEmpty(entity.getPassword())){
				record.setPassword(entity.getPassword());
			}
			if(StringUtils.isNotEmpty(entity.getStatus())){
				record.setStatus(entity.getStatus());
			}
			if(StringUtils.isNotEmpty(entity.getMobile())){
				record.setMobile(entity.getMobile());
			}
			if(StringUtils.isNotEmpty(entity.getName())){
				record.setName(entity.getName());
			}
			if(StringUtils.isNotEmpty(entity.getGender())){
				record.setGender(entity.getGender());
			}
			if(StringUtils.isNotEmpty(entity.getIdType())){
				record.setIdType(entity.getIdType());
			}
			if(StringUtils.isNotEmpty(entity.getIdNum())){
				record.setIdNum(entity.getIdNum());
			}
			if(null != entity.getDepartmentID()){
				record.setDepartmentID(entity.getDepartmentID());
			}
			String today = DateUtil.getCurrentDatetime();
			result = jdbc.update(Env.DS, SQL_UPDATE_ACCOUNT, record.getWorkno(), record.getPassword(), record.getStatus(), record.getMobile(),
						record.getName(), record.getGender(), record.getIdType(), record.getIdNum(), record.getDepartmentID(), today,
						record.getAccount());
		} catch (Exception e) {
			log.error("", e);
		} finally{
			jdbc.closeAll();
		}
		
		return result;
	}

	public int modifyAccountInfo(String account, String pwd) {
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String sql = "select Account as account,PassWord as password from t_account where Account = ?";
		TAccount acc;
		int result = 0;
		try {
			acc = jdbc.queryForObject(Env.DS, sql, TAccount.class, account);
		} catch (Exception e) {
			log.error("没有account=" + account + "的账号", e);
			throw new RuntimeException(e);
		}
		String sql2 = "update t_account set PassWord = ? where Account = ?";

		try {
			result = jdbc.update(Env.DS, sql2, pwd, acc.getAccount());
		} catch (Exception e) {
			log.error("更新密码失败", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public TAccount getPassword(String accStr) {
		
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		String sql = "select Account as account,PassWord as password from t_account where Account = ?";
		TAccount acc;
		int result = 0;
		try {
			acc = jdbc.queryForObject(Env.DS, sql, TAccount.class, accStr);
		} catch (Exception e) {
			log.error("没有account=" + accStr + "的账号", e);
			throw new RuntimeException(e);
		}
		return acc;
	}
	
	public List<TDepartment> findDepartment(){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TDepartment> list = null;
		
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_DEPARTMENT, TDepartment.class);
			
		} catch (Exception e) {
			log.error(e);
		} finally{
			jdbc.closeAll();
		}
		return list;
	}
	
	public TDepartment saveDepartment(TDepartment department){
		if(null==department) return null;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		int result = 0;
		TDepartment entity = null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			if(null==department.getId()){ // insert
				Long id = jdbc.queryForObject(conn, SQL_QUERY_MAX_DEPARTMENT_ID_BY_LEVELS, Long.class, department.getLevels());
				id += 1;
				department.setId(id);
				
				result = jdbc.update(conn, SQL_INSERT_DEPARTMENT, department.getId(), department.getName(), department.getParentId(),
						department.getStatus(), department.getLevels(), department.getWorkPriority(), department.getCategory(), DateUtil.getCurrentDatetime());
				if(result>0){
					entity = jdbc.queryForObject(conn, SQL_QUERY_DEPARTMENT_BY_ID, TDepartment.class, id);
				}
				
				//创建部门组织时选择了有暂存库则根据部门组织创建创库
				if(!"0".equals(department.getCategory())){
					TStoreHouse tsh=new TStoreHouse();
					String storehouseNum=UUID.randomUUID().toString();
					tsh.setStorehouseNum(storehouseNum);
					String storehouseName=department.getName()+"保管室";
					tsh.setStorehouseName(storehouseName);
					tsh.setStorehouseType("1");
					tsh.setDeptID(department.getId());
					
					jdbc.update(conn, SQL_INSERT_STOREHOUSE, tsh.getStorehouseNum(), tsh.getStorehouseName(), tsh.getStorehouseType(),tsh.getDeptId());
				}
			} else{
				result = jdbc.update(conn, SQL_UPDATE_DEPARTMENT, department.getName(), department.getStatus(), department.getWorkPriority(), 
						department.getCategory(), DateUtil.getCurrentDatetime(), department.getId());
				if(result>0){
					entity = jdbc.queryForObject(conn, SQL_QUERY_DEPARTMENT_BY_ID, TDepartment.class, department.getId());
				}
				
				
				TStoreHouse tsh=jdbc.queryForObject(conn, SQL_QUERY_STOREHOUSE_BY_DEPTID, TStoreHouse.class, department.getId());
				if("N".equals(department.getStatus())||"0".equals(department.getCategory())){
					//如果部门修改为无暂存库或者直接删除，则将相应的仓库删除。
					if(tsh!=null){
						jdbc.update(Env.DS, SQL_DELETE_STOREHOUSE, tsh.getId());
					}
				}else{
					//如果部门只是修改其他字段，则判断部门是否有相应的仓库，有则同步修改名称，没有则新增
					if(tsh!=null){
						String storehouseName=department.getName()+"保管室";
						jdbc.update(Env.DS, SQL_UPDATE_STOREHOUSE, storehouseName, tsh.getId());
					}else{
						TStoreHouse ntsh=new TStoreHouse();
						String storehouseNum=UUID.randomUUID().toString();
						ntsh.setStorehouseNum(storehouseNum);
						String storehouseName=department.getName()+"保管室";
						ntsh.setStorehouseName(storehouseName);
						ntsh.setStorehouseType("1");
						ntsh.setDeptID(department.getId());
						jdbc.update(conn, SQL_INSERT_STOREHOUSE, ntsh.getStorehouseNum(), ntsh.getStorehouseName(), ntsh.getStorehouseType(),ntsh.getDeptId());
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
	public static void initDepartments() throws Exception{
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TDepartment> departmentList = jdbc.query(DS, "select * from t_department", TDepartment.class);
		if(ArrayUtil.isNotEmpty(departmentList)) {
			log.info("load departmentList === "+departmentList.size());
			for(TDepartment department:departmentList) {
				departmentMap.put(department.getId(), department);
			}
		}
	}
	
	public static void initAccounts() throws Exception{
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TAccount> accountList = jdbc.query(DS, "select ACCOUNT,WORKNO,PASSWORD,STATUS,MOBILE,NAME,GENDER,ID_TYPE,ID_NUM,DEPARTMENT_ID department_i_d,CREATE_TIME,UPDATE_TIME,EXPIRE_TIME from t_account", TAccount.class);
		if(ArrayUtil.isNotEmpty(accountList)) {
			log.info("load accountList === "+accountList.size());
			for(TAccount account:accountList) {
				accountMap.put(account.getAccount(), account);
			}
		}
	}
}
