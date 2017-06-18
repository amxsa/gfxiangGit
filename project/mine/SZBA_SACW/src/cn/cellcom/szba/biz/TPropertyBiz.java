package cn.cellcom.szba.biz;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.CommonSql;
import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.common.utils.IdHolderUtils;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TAttachment;
import cn.cellcom.szba.domain.TCase;
import cn.cellcom.szba.domain.TCaseDisposal;
import cn.cellcom.szba.domain.TCategory;
import cn.cellcom.szba.domain.TCivilian;
import cn.cellcom.szba.domain.TDetail;
import cn.cellcom.szba.domain.THandleResult;
import cn.cellcom.szba.domain.TIdentify;
import cn.cellcom.szba.domain.TPicture;
import cn.cellcom.szba.domain.TPolice;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TPropertyAttribute;
import cn.cellcom.szba.domain.TPropertyDetail;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.domain.TStorageLocation;
import cn.cellcom.szba.domain.TStoreHouse;
import cn.cellcom.szba.domain.TStoreRoom;
import cn.cellcom.szba.enums.PropertyStatusKey;
import cn.cellcom.szba.vo.PropertyVO;
import cn.open.db.JDBC;
import cn.open.db.ListAndPager;
import cn.open.db.Pager;
import cn.open.db.SqlParser;
import cn.open.db.SqlParser.DbRequest;
import cn.open.util.ArrayUtil;
import cn.open.util.CommonUtil;

public class TPropertyBiz {
	static Log log = LogFactory.getLog(TPropertyBiz.class);
	
	//添加财物
	private static String SQL_INSERT_PROPERTY = 
			"INSERT INTO t_property(id , name, quantity, unit, characteristic, origin, category_id, type"
			+ ",place,authority,nature,is_save,save_place,evaluate_value, creator, create_time, valid_status, from_part"
			+ ",case_id,seizure_place,seizure_basis,remark,identify_id,transactor,pro_no,spec) " + 
			"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'),?,?,?,?,?,?,?,?,?,?)";
	
	//修改财物
	private static String SQL_UPDATE_PROPERTY = 
			"UPDATE t_property SET name = ?, quantity = ?, unit = ?, characteristic = ?, origin = ?, category_id = ?, type = ?, "
			+ "place = ?, authority = ?, nature = ?, is_save = ?, save_place = ?,evaluate_value = ?,"
			+ "seizure_place = ?,seizure_basis = ?,remark = ?,case_id=?,spec=?,pro_no=? WHERE id = ?";
	
	//根据id查询财物信息
	public static String SQL_QUERY_PROPERTY_BY_PROID = 
			"select id as \"proId\",pro_no as \"proNo\", name as \"proName\", quantity as \"proQuantity\", unit as \"proUnit\", characteristic as \"proCharacteristic\",creator as \"account\","
			+ "origin as \"proOrigin\", category_id as \"categoryId\", type as \"proType\",place as \"proPlace\",authority as \"proDepartment\","
			+ "nature as \"proNature\",is_save as \"proIsSave\",save_place as \"proSavePlace\",evaluate_value as \"proEvaluateValue\","
			+ "case_id as \"caseId\",seizure_place as \"proSeizurePlace\",seizure_basis as \"proSeizureBasis\",remark as \"proRemark\",identify_id as \"identifyId\",status as \"proStatus\",spec as \"proSpec\",from_part as \"proFormPart\" from t_property where id=?";
	
	//添加鉴定信息
	private static String SQL_INSERT_IDENTIFY=
			"INSERT INTO t_identify(ide_result , ide_date, ide_uint, ide_person, ide_handle, creator) "
			+ "VALUES(?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'),?,?,?,?)";
	//查询鉴定信息
	private static String SQL_QUERY_IDENTIFY_BY_ID="SELECT id, ide_result , ide_date, ide_uint, ide_person, ide_handle, creator "
			+ "FROM t_identify WHERE id=?";
	
	//删除财物，更新财物删除状态
	private static String SQL_DEL_PROPERTY = "update t_property set valid_status = 'N' where id = ?";
	
	private static String SQL_QUERY_CIVI_BY_PROID = 
			"select  civi.name as civi_name " +
			" from t_civilian civi" +
			" join t_property_civilian pc on pc.civilian_id = civi.id" +
			" where civi.valid_status = 'Y' and civi.type = '"+Env.TYPE_HOLDER+"' and pc.property_id = ?";
	
	public static String SQL_QUERY_PRO_BY_PROID = 
			"select  pro.id pro_id ,pro.name pro_name, pro.quantity as \"proQuantity\", " +
			" pro.unit as \"proUnit\", pro.owner as \"proOwner\" ," +
			" pro.seizure_basis as \"proSeizureBasis\", " +
			" pro.category_id as \"categoryId\", pro.characteristic as \"proCharacteristic\" ," +
			" pro.status as \"proStatus\", pro.type as \"proType\"," +
			" pro.origin as \"proOrigin\", pro.nature as \"proNature\"," +
			" pro.seizure_place as \"proSeizurePlace\",pro.spec as \"proSpec\"," +
			" pro.seizure_basis as \"proSeizureBasis\", pro.store_num as \"storeNum\"," +
			" pro.remark as \"proRemark\", pro.mix_id as \"proMixId\",pro.case_id as \"caseId\",pro.identify_id as \"identifyId\"," +
			" pro.table_type, pro.save_demand, pro.save_method, pro.envi_demand, " +
			" pro.rfid_num pro_rfid_num,pro.creator account" +
			" from t_property pro " +
			" where pro.id = ? and pro.valid_status = 'Y'";
	private static String SQL_QUERY_DETAIL_BY_LISITD = 
			"select list.list_id as \"listID\", list.create_time as \"date\", rec.case_id as \"caseId\", rec.record_id" +
			" from t_detail_list list" +
			" join t_record rec on rec.record_id = list.record_id" +
			" where list.list_id = ? and list.valid_status = 'Y'";
	private static String SQL_QUERY_DETAIL_BY_EXTID = 
			"select id as \"listID\", create_time as \"date\", case_id as \"caseId\"," +
			" id as record_id " +
			" from t_extract_record" +
			" where id = ? and valid_status = 'Y'";
	
	private static String SQL_QUERY_OWNER_BY_MIXID = 
			"select id as \"civiId\", name as \"civiName\", id_num as \"idNum\", id_type," +
			" address as \"civiAddress\", phone as \"civiPhone\"" +
			" from t_civilian " +
			" where mix_id = ? and type = '18' and valid_status = 'Y' and table_type = ?";
	
	private static String SQL_QUERY_POLICE_BY_MIXID = 
			"select po.id as \"poliId\", po.name as \"poliName\", dept.id as \"deptID\", " +
			" dept.name as \"deptName\" " +
			" from t_police po" +
			" left join t_department dept on po.department_id = dept.id" +
			" where po.mix_id = ? and po.valid_status = 'Y' and po.table_type = ?" +
			" order by po.priority asc";
	
	private static String SQL_QUERY_CASE_BY_CASEID = 
			"select case_id as \"caseID\",jzcase_id as \"jzcaseID\", case_type," +
			" case_name as \"caseName\", leader" +
			" from t_case" +
			" where case_id = ? and valid_status = 'Y'";
	
	private static String SQL_INSERT_CASEDISPOSAL = 
			"insert into t_case_disposal(disposal, disposal_person, " +
			"instruction, pro_id, attach_id)" +
			" values(?, ?, ?, ?, ?)";
	
	private static String SQL_QUERY_BY_APPLICATIONID = 
			"select  pro.id pro_id ,pro.name pro_name, pro.quantity as \"proQuantity\", " +
			" pro.unit as \"proUnit\", pro.owner as \"proOwner\" ,pro.pro_no as \"proNo\" ," +
			" pro.seizure_basis as \"proSeizureBasis\", " +" pro.warehouse_id as \"warehouseId\", "+
			" pro.category_id as \"categoryId\", pro.characteristic as \"proCharacteristic\" ," +
			" pro.status as \"proStatus\", pro.type as \"proType\"," +
			" pro.origin as \"proOrigin\", pro.nature as \"proNature\"," +
			" pro.seizure_place as \"proSeizurePlace\"," +
			" pro.seizure_basis as \"proSeizureBasis\"," +
			" pro.remark as \"proRemark\", pro.mix_id as \"proMixId\"," +
			" pro.table_type, pro.save_demand, pro.save_method, pro.envi_demand, " +
			" pro.rfid_num pro_rfid_num," +" pro.store_num as \"storeNum\"" +
			" ,pro.save_method,pro.envi_demand, pro.damage_status , d.description damage_reason" +
			" from t_property pro " +
			" join t_application_property ap on ap.property_id = pro.id" +
			" join t_application a on a.id = ap.application_id" +
			" left join t_damage d on d.property_id = pro.id and d.application_id = a.id" +
			" where ap.application_id = ?";
	/*
	 *持有人姓名，身份证号，住址，联系电话 ,
	 */
	private static String SQL_UPDATE_OWNER = "update t_civilian set" +
			" address = ?, phone = ?" +
			" where id = ?";
	
	private static String SQL_INSERT_OWNER = 
			"insert into t_civilian(id, name,id_type, id_num, address, phone, creator, mix_id, type, table_type,unit,gender) " +
			" values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static String SQL_INSERT_PROPERTY_OWNER = 
			"insert into t_property_civilian(property_id,civilian_id,create_time) " +
			" values(?,?,TO_DATE(?, '"+DateUtil.OracleDatetimeFormatString+"'))";
	
	private static String SQL_QUERY_CIVILIAN_BY_PROID = 
			"select a.id as \"civiId\", a.name as \"civiName\",a.id_type as \"idType\", a.id_num as \"idNum\", a.address as \"civiAddress\", a.phone as \"civiPhone\", "
			+ "a.mix_id as \"mixId\", a.type as \"civiType\", a.table_type as \"tableType\",a.unit as \"unit\",a.gender as \"civiGender\", a.create_time as \"createTime\"" +
			" from t_civilian a , t_property_civilian b" +
			" where a.id=b.civilian_id and b.property_id = ? and a.type = ?";
	
	private static String SQL_DELETE_CIVILIAN_BY_PROID = 
			"delete from t_civilian where id in (select civilian_id from t_property_civilian where property_id =?)";
	
	private static String SQL_DELETE_PROPERTY_CIVILIAN = 
			"delete from t_property_civilian where property_id = ?";
	
	
	/**
	 * 警员信息 单位 名称 默认类型16
	 */
	private static String SQL_UPDATE_POLICE = "update t_police set name = ?, department_id = ? where id = ?";
	private static String SQL_INSERT_POLICE = 
			"insert into t_police(id, name, department_id, creator, type, mix_id, table_type) " +
			" values(?, ?, ?, ?, ?, ?, ?)";
	
	/**财物属性*/
	private static String SQL_INSERT_PROPERTY_ATTRIBUTE = 
			"insert into t_property_attribute(pro_id, attr_id, attr_name, attr_type, attr_value) "
			+ "values(?,?,?,?,?)";
	
	private static String SQL_SELECT_PROPERTY_ATTRIBUTE_BY_PROID=
			"select id, pro_id, attr_id, attr_name, attr_type, attr_value "
			+ "from t_property_attribute where (pro_id = string:proId)";
	
	private static String SQL_DELETE_PROPERTY_ATTRIBUTE_BY_PROID = 
			"delete from t_property_attribute where pro_id=?";
	
	/*
	 * 更新清单，扣押时间
	 */
	private static String SQL_UPDATE_DETAIL = "update t_detail_list set" +
			" create_time = to_date(?,'yyyy-mm-dd hh24:mi:ss') " +
			" where list_id = ?";
	
	private static String SQL_UPDATE_PRO_RF_WH = "update t_property set" +
			" rfid_num = ?, warehouse_id = ? where id = ?";
	
	private static String SQL_WHERE_CLAUSE =
			" and (\"jzcaseId\" like plike:condiJzcaseId) " +
			" and (\"caseName\" like blike:condiCaseName) " +
			" and (\"caseRegisterDate\" >=date:condiStartTime)" +
			" and (\"caseRegisterDate\" <=date:condiEndTime)" +
			" and (\"proId\" like plike:condiProId) " +
			" and (\"proName\" like blike:condiProName) " +
			" and (\"proStatus\" = string:condiProStatus) " +
			" and (\"wareSerialNumber\" like plike:wareSerialNumber) " + 
			" order by \"proCreateTime\" desc";
	
	
	public static String SQL_QUERY_PRO_BY_PROIDS = 
			"select  pro.id pro_id ,pro.name pro_name, pro.quantity pro_quantity, " +
			" pro.unit pro_unit, pro.owner pro_owner ," +
			" pro.seizure_basis pro_seizure_basis, " +
			" pro.category_id category_id, pro.characteristic pro_characteristic ," +
			" pro.status pro_status, pro.type pro_type," +
			" pro.origin pro_origin, pro.nature pro_nature," +
			" pro.seizure_place pro_seizure_place," +
			" pro.seizure_basis pro_seizure_basis," +
			" pro.remark pro_remark, pro.mix_id pro_mix_id," +
			" pro.table_type, pro.save_demand, pro.save_method, pro.envi_demand, " +
			" pro.case_id, pro.actual_position, pro.pro_no, pro.is_running" +
			" from t_property pro " +
			" where 1 =1 and ( pro.id in strings:proId) and pro.valid_status = 'Y'";
	
	private static String SQL_WHERE_CLAUSE_CASE = " and (\"jzcaseId\" like plike:condiJzcaseId) "
			+ " and (\"caseName\" like blike:condiCaseName) "
			+ " and (\"caseRegisterDate\" >=date:condiStartTime)"
			+ " and (\"caseRegisterDate\" <=date:condiEndTime)"
			+ " and (\"proId\" like plike:condiProId) "
			+ " and (\"proName\" like blike:condiProName) "
			+ " and (\"proStatus\" = string:condiProStatus) "
			+ " and (\"wareSerialNumber\" like plike:wareSerialNumber) "
			+ " order by \"jzcaseId\"  desc,  \"proCreateTime\" desc";
	
	private static String SQL_WHERE_CLAUSE_CASE_DISPOSAL = " and \"proType\"='BBCCW'    and (\"jzcaseId\" like plike:condiJzcaseId) "
			+ " and (\"caseName\" like blike:condiCaseName) "
			+ " and (\"caseRegisterDate\" >=date:condiStartTime)"
			+ " and (\"caseRegisterDate\" <=date:condiEndTime)"
			+ " and (\"proId\" like plike:condiProId) "
			+ " and (\"proName\" like blike:condiProName) "
			+ " order by \"jzcaseId\"  desc,  \"proCreateTime\" desc";
	/**
	 * 库存管理-财物移库
	 * */
	public static TStoreHouse inventoryMoveLocation(HttpServletRequest requ){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TProperty> proList = new ArrayList<TProperty>();
		
		String proId=requ.getParameter("proId");  //财物id
		String locationId=requ.getParameter("locationId");  //库位id
		String storeHouseId=requ.getParameter("storeHouseId");  //仓库id
		String ylocationId=requ.getParameter("ylocationId");  //原来库位id
		
		TStoreHouse entity=null;
		try {
			TProperty property = jdbc.queryForObject(Env.DS, SQL_QUERY_PRO_BY_PROID, TProperty.class, proId);
			if(property!=null){
				//更改选择的库位信息
				jdbc.update(Env.DS, "update t_property set warehouse_id = ?,storehouse_id = ? where id = ?", locationId,storeHouseId, proId);
				TStorageLocation ts=jdbc.queryForObject(Env.DS,"select * from t_storage_location where id = ?" , TStorageLocation.class,locationId);
				if(ts!=null){
					String inventoryStatus="E";
					Long newNum=ts.getGoodsNum()+property.getStoreNum();
					if(newNum.longValue()==ts.getDepositNum().longValue()){
						inventoryStatus="F";
					}else if(newNum.longValue()<ts.getDepositNum().longValue()){
						inventoryStatus="E";
					}
					jdbc.update(Env.DS, "update t_storage_location set inventory_status = ?,goods_num=? where id = ?",inventoryStatus,newNum, locationId);
				}
				
				String SQL_QUERY_STOREROOM_BY_ID="SELECT id as \"id\",storehouse_name as \"storehouseName\" FROM t_storehouse where id=?";
				entity=jdbc.queryForObject(Env.DS,SQL_QUERY_STOREROOM_BY_ID , TStoreHouse.class,storeHouseId);
				
				//释放原来的库位信息
				TStorageLocation yts=jdbc.queryForObject(Env.DS,"select * from t_storage_location where id = ?" , TStorageLocation.class,ylocationId);
				if(yts!=null){
					Long newNum=0l;
					if(property.getStoreNum()!=null){
						newNum=yts.getGoodsNum()-property.getStoreNum();
					}
					String inventoryStatus="E";
					if(newNum.longValue()==0){
						inventoryStatus="U";
					}else if(newNum.longValue()>0){
						inventoryStatus="E";
					}
					jdbc.update(Env.DS, "update t_storage_location set inventory_status = ?,goods_num=? where id = ?",inventoryStatus,newNum, ylocationId);
				}
			}
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		return entity;
	}
	
	/**
	 * 带条件的分页查询
	 * @param params 条件
	 * @param page   分页参数
	 * @return
	 */
	public static ListAndPager<PropertyVO> queryForPage(Map<String, String[]> params, Pager page, Map<String, Object> loginForm, String viewName){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
//		Map<String, String[]> extraMap = new HashMap<String, String[]>();
		ListAndPager<PropertyVO> list = null;
		String accInfo = loginForm.get("ACCOUNT").toString();
		TAccount account=new TAccount();
		account.setAccount(accInfo);
		account.setDepartmentID(CommonUtil.isLong(loginForm.get("DEPARTMENT_ID").toString(), true));
		List<TRole> roles = TRoleBiz.accountRoles(loginForm.get("ACCOUNT").toString());
		try {
			
			DbRequest dbRequest = SqlParser.parse(CommonSql.getPropertySqlByViewName(viewName, account, roles),params);
			
			String sql = dbRequest.getSql();
			Object[] objParams = dbRequest.getParameters();
			//objParams = ArrayUtils.add(objParams, accInfo);
			list = jdbc.queryPage(Env.DS, sql, PropertyVO.class, page.getCurrentIndex(), page.getSizePerPage(), objParams);
			List<PropertyVO> proList = list.getList();
			
			if(CommonSql.isExistsInMap(Env.PROVIEWNAMEGROUP2, viewName)){
				packProVOCivilians(proList, jdbc);
			}
			
		} catch (Exception e) {
			log.error("", e);
		} 
		return list!=null?list:new ListAndPager<PropertyVO>();
	}
	
	public static TDetail queryDetail(String proId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TDetail detailList = new TDetail();
		String tableType = "";
		String poliTableType = "";
		String mixId = "";
		String poliMixId = "";
		try {
			TProperty property = jdbc.queryForObject(Env.DS, SQL_QUERY_PRO_BY_PROID, TProperty.class, proId);
			
			TCase tCase = null;
			if("LIST".equals(property.getTableType())){
				detailList = jdbc.queryForObject(Env.DS, SQL_QUERY_DETAIL_BY_LISITD, TDetail.class, property.getProMixId());
				poliTableType = "REC";
				if(detailList != null){
					poliMixId = detailList.getRecordId();
				}else{
					detailList = new TDetail();
				}
			}else{
				detailList = jdbc.queryForObject(Env.DS, SQL_QUERY_DETAIL_BY_EXTID, TDetail.class, property.getProMixId());
				poliTableType = property.getTableType();
				poliMixId = property.getProMixId();
				if(detailList == null){
					detailList = new TDetail();
				}
			}
			
			tableType = property.getTableType();
			mixId = property.getProMixId();
			
			
			//detailList = detailList != null?detailList:new TDetail();
			tCase = jdbc.queryForObject(Env.DS, SQL_QUERY_CASE_BY_CASEID, TCase.class, detailList.getCaseId());
			List<TCivilian> owners = jdbc.query(Env.DS, SQL_QUERY_OWNER_BY_MIXID, TCivilian.class, mixId, tableType);
			List<TPolice> polices = jdbc.query(Env.DS, SQL_QUERY_POLICE_BY_MIXID, TPolice.class, poliMixId, poliTableType);
			
			
			detailList.getProperties().add(property);
			detailList.settCase(tCase!=null?tCase:new TCase());
			detailList.setOwner(owners);
			detailList.setPolices(polices);
			
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		return detailList;
	}
	
	public static TDetail queryForApply(String proId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		TDetail detailList = null;
		List<TProperty> proList = new ArrayList<TProperty>();
		List<TPolice> polices = new ArrayList<TPolice>();
		TCase tCase = new TCase();
		
		String tableType = "";
		String mixId = "";
		try {
			TProperty property = new TProperty();
			if(StringUtils.isNotBlank(proId)){
				property = jdbc.queryForObject(Env.DS, SQL_QUERY_PRO_BY_PROID, TProperty.class, proId);
				proList.add(property);
				if("LIST".equals(property.getTableType()))
					detailList = jdbc.queryForObject(Env.DS, SQL_QUERY_DETAIL_BY_LISITD, TDetail.class, property.getProMixId());
				else if ("EXTRAC".equals(property.getTableType())){
					detailList = jdbc.queryForObject(Env.DS, SQL_QUERY_DETAIL_BY_EXTID, TDetail.class, property.getProMixId());
				}
			}
			
			if(detailList != null){
				detailList.setProperties(proList);
				
				if("LIST".equals(property.getTableType())){
					tableType = "REC";
					mixId = detailList.getRecordId();
				}else{
					tableType = property.getTableType();
					mixId = property.getProMixId();
				}
				
				polices = jdbc.query(Env.DS, SQL_QUERY_POLICE_BY_MIXID, TPolice.class, mixId, tableType);
				detailList.setPolices(polices);
				tCase = jdbc.queryForObject(Env.DS, SQL_QUERY_CASE_BY_CASEID, TCase.class, detailList.getCaseId());
				detailList.settCase(tCase);
			}
			
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		return detailList!=null?detailList:new TDetail();
	}
	
	public static TDetail queryForBatchApply(String[] proId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TProperty> proList = new ArrayList<TProperty>();
		TDetail detail = new TDetail();
		
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		paramMap.put("proId", proId);
		
		try {
			DbRequest request = SqlParser.parse(SQL_QUERY_PRO_BY_PROIDS, paramMap);
			proList = jdbc.query(Env.DS, request.getSql(), TProperty.class, request.getParameters());
			
			List<TProperty> excessPros = new ArrayList<TProperty>();
			for(TProperty pro : proList){
				if(judgeRunningStatus(pro.getProStatus()) ){
					excessPros.add(pro);
				}
			}
			detail.setExcessPros(excessPros);
			
			detail.setProperties(proList);
			
			if(proList.size() > 0){
				TCase tCase = jdbc.queryForObject(Env.DS, SQL_QUERY_CASE_BY_CASEID, TCase.class, proList.get(0).getCaseId());
				detail.settCase(tCase);
			}
			
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		return detail;
	}
	
	public static List<TProperty> queryByIds(String[] proId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TProperty> proList = new ArrayList<TProperty>();
		
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		paramMap.put("proId", proId);
		
		try {
			DbRequest request = SqlParser.parse(SQL_QUERY_PRO_BY_PROIDS, paramMap);
			proList = jdbc.query(Env.DS, request.getSql(), TProperty.class, request.getParameters());
			
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} 
		return proList;
	}
	
	/**
	 * 财物详情查询 by pansenxin
	 * */
	public static TPropertyDetail findDetailById(String proId){
		TPropertyDetail ret=new TPropertyDetail();
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try {
			//获取财物详情
			TProperty tproperty = jdbc.queryForObject(Env.DS, SQL_QUERY_PROPERTY_BY_PROID, TProperty.class, proId);
			//获取分类名称
			if(tproperty.getCategoryId()!=null){
				String SQL_QUERY_CATEGORY_BY_ID =
						"select id,parent_id,name,save_demand,pack_demand,envi_demand,create_time,update_time,levels,priority,valid_status "
						+ "from t_category where id = ?";
				TCategory category= jdbc.queryForObject(Env.DS, SQL_QUERY_CATEGORY_BY_ID, TCategory.class, tproperty.getCategoryId());
				if(category!=null)
					tproperty.setCategoryName(category.getName());
			}
			
			ret.setTproperty(tproperty);
			//获取图片信息
			List<TPicture> picList = TPictureBiz.queryPhotographs(proId, null, null);
			ret.setTpictureList(picList);
			//获取财物持有人信息
			List<TCivilian> owners = jdbc.query(Env.DS, SQL_QUERY_CIVILIAN_BY_PROID, TCivilian.class, proId, 18);
			if(owners!=null&&!owners.isEmpty()){
				ret.setOwnerInfo(owners.get(0));
			}
			//获取财物所有人信息
			List<TCivilian> everyOnes = jdbc.query(Env.DS, SQL_QUERY_CIVILIAN_BY_PROID, TCivilian.class, proId, 32);
			if(everyOnes!=null&&!everyOnes.isEmpty()){
				ret.setEveryOneInfo(everyOnes.get(0));
			}
			//获取案件信息
			TCase tcase= jdbc.queryForObject(Env.DS, SQL_QUERY_CASE_BY_CASEID, TCase.class, tproperty.getCaseId());
			ret.setCaseInfo(tcase);
			//获取保管人信息
			List<TCivilian> bgPersons = jdbc.query(Env.DS, SQL_QUERY_CIVILIAN_BY_PROID, TCivilian.class, proId, 19);
			if(bgPersons!=null&&!bgPersons.isEmpty()){
				ret.setBgPerson(bgPersons.get(0));
			}
			//获取见证人信息
			List<TCivilian> jzPersons = jdbc.query(Env.DS, SQL_QUERY_CIVILIAN_BY_PROID, TCivilian.class, proId, 17);
			if(jzPersons!=null&&!jzPersons.isEmpty()){
				ret.setJzPerson(jzPersons.get(0));
			}
			//获取当事人信息
			List<TCivilian> dsPersons = jdbc.query(Env.DS, SQL_QUERY_CIVILIAN_BY_PROID, TCivilian.class, proId, 22);
			if(dsPersons!=null&&!dsPersons.isEmpty()){
				ret.setDsPerson(dsPersons.get(0));
			}
			//获取鉴定信息
			TIdentify tidentify = jdbc.queryForObject(Env.DS, SQL_QUERY_IDENTIFY_BY_ID, TIdentify.class, tproperty.getIdentifyId());
			ret.setIdentifyInfo(tidentify);
			//获取法律文件信息
			List<TAttachment> attachmentList=TAttachmentBiz.queryAll("21", tproperty.getProId());
			ret.setAttachmentList(attachmentList);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return ret;
	}
	
	/**
	 * 财物属性查询 by pansenxin
	 * */
	public static List<TPropertyAttribute> findPropertyAttributeByPropertyId(Map<String, String[]> paramsMap){
		List<TPropertyAttribute> retList=null;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try {
			DbRequest dbRequest = SqlParser.parse(SQL_SELECT_PROPERTY_ATTRIBUTE_BY_PROID, paramsMap);
			Object[] params = dbRequest.getParameters();
			String sql = dbRequest.getSql();
			retList = jdbc.query(Env.DS,sql , TPropertyAttribute.class, params);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally{
			jdbc.closeAll();
		}
		return retList;
	}
	
	/**
	 * 财物修改 by pansenxin
	 * */
	public static boolean update(TProperty property,List<TPicture> picList,String ownerInfo,String everyOneInfo,
			String cateAttrInfo,String bgPerson,String jzPerson,String dsPerson,List<TAttachment> attList,String departmentId){
		boolean ret=false;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		try {
			conn=Env.DS.getConnection();
			conn.setAutoCommit(false);
			String proId=property.getProId();
			TProperty tproperty = jdbc.queryForObject(Env.DS, SQL_QUERY_PROPERTY_BY_PROID, TProperty.class, proId);
			if(tproperty!=null){
				//app登记没有财物编号，需要修改时补上去
				if(StringUtils.isBlank(tproperty.getProNo())&&property.getCategoryId()!=null){
					TCategory cate=TCategoryBiz.queryById(property.getCategoryId());
					String cateIdIdx="00000";
					/*if(cate!=null&&cate.getParentId()!=null&&cate.getParentId()!=1){
						cateIdIdx=String.valueOf(cate.getParentId()).substring(0,5);
					}*/
					if(cate!=null){
						cateIdIdx=String.valueOf(cate.getId()).substring(0,5);
					}
					String proNo=IdHolderUtils.getProIdNo(cateIdIdx,departmentId,"PRONO");
					tproperty.setProNo(proNo);
				}
				jdbc.update(Env.DS, SQL_UPDATE_PROPERTY,property.getProName(),property.getProQuantity()
						,property.getProUnit(),property.getProCharacteristic(),property.getProOrigin()
						,property.getCategoryId(),property.getProType(),property.getProPlace(),property.getProDepartment()
						,property.getProNature(),property.getProIsSave(),property.getProSavePlace(),property.getProEvaluateValue()
						,property.getProSeizurePlace(),property.getProSeizureBasis(),property.getProRemark(),property.getCaseId()
						,property.getProSpec(),tproperty.getProNo(),property.getProId());
				
				
				//保存图片信息
				for(TPicture pic : picList){
					pic.setPropertyID(proId);
				}
				TPictureBiz.addPicInfo(conn, picList, proId);
				
				//保存持有人、所有人信息、保管人，见证人，当事人  先删除后添加
				jdbc.update(conn, SQL_DELETE_CIVILIAN_BY_PROID, property.getProId());
				jdbc.update(conn, SQL_DELETE_PROPERTY_CIVILIAN, property.getProId());
				if(StringUtils.isNotBlank(ownerInfo)){
					saveRaletePerson(ownerInfo,jdbc,conn,tproperty,1,18);
				}
				if(StringUtils.isNotBlank(everyOneInfo)){
					saveRaletePerson(everyOneInfo,jdbc,conn,tproperty,1,32);
				}
				if(StringUtils.isNotBlank(bgPerson)){
					saveRaletePerson(bgPerson,jdbc,conn,tproperty,2,19);
				}
				if(StringUtils.isNotBlank(jzPerson)){
					saveRaletePerson(jzPerson,jdbc,conn,tproperty,2,17);
				}
				if(StringUtils.isNotBlank(dsPerson)){
					saveRaletePerson(dsPerson,jdbc,conn,tproperty,2,22);
				}
				
				//保存分类属性 先删除再保存
				jdbc.update(conn, SQL_DELETE_PROPERTY_ATTRIBUTE_BY_PROID, proId);
				if(StringUtils.isNotBlank(cateAttrInfo)){
					String[] attrArr=cateAttrInfo.split(";",-1);
					for(int i=0;i<attrArr.length;i++){
						String[] singleAttr=attrArr[i].split("\\|",-1);
						jdbc.update(conn, SQL_INSERT_PROPERTY_ATTRIBUTE, proId,singleAttr[0], 
								singleAttr[1], singleAttr[2],singleAttr[3]);
					}
				}
				
				//保存附件信息
				for(TAttachment att : attList){
					att.setPropertyID(proId);
				}
				TAttachmentBiz.addAttachInfo(jdbc, conn, attList, "21", proId);
				
				ret=true;
			}else{
				ret=false;
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
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
		return ret;
	}
	
	/**
	 * 财物添加 by pansenxin
	 * */
	public static String add(TProperty property,List<TPicture> picList,String ownerInfo,String everyOneInfo,List<TAttachment> attList,
			String cateAttrInfo,String bgPerson,String jzPerson,String dsPerson,String identifyInfo,String departmentId){
		String ret="";
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		try {
			conn=Env.DS.getConnection();
			conn.setAutoCommit(false);
			//添加鉴定信息
			long identifyId=0;
			if(StringUtils.isNotBlank(identifyInfo)){
				identifyId=saveIdentifyInfo(identifyInfo,jdbc,conn,property.getAccount());
			}
			
			//财物id
			String proId=UUID.randomUUID().toString();
			//财物编号 分类ID(5位数字)+财物扣押人所在部门ID(4位数字)+YYMMDD+5位数字（序列），共20位
			TCategory cate=TCategoryBiz.queryById(property.getCategoryId());
			String cateIdIdx="00000";
			/*if(cate!=null&&cate.getParentId()!=null&&cate.getParentId()!=1){
				cateIdIdx=String.valueOf(cate.getParentId()).substring(0,5);
			}*/
			if(cate!=null){
				cateIdIdx=String.valueOf(cate.getId()).substring(0,5);
			}
			String proNo=IdHolderUtils.getProIdNo(cateIdIdx,departmentId,"PRONO");
			
			int rows=jdbc.update(Env.DS, SQL_INSERT_PROPERTY, proId,property.getProName(),property.getProQuantity()
					,property.getProUnit(),property.getProCharacteristic(),property.getProOrigin()
					,property.getCategoryId(),property.getProType(),property.getProPlace(),property.getProDepartment()
					,property.getProNature(),property.getProIsSave(),property.getProSavePlace(),property.getProEvaluateValue()
					,property.getAccount(),DateUtil.getCurrentDatetime()
					,"Y","web",property.getCaseId(),property.getProSeizurePlace(),
					property.getProSeizureBasis(),property.getProRemark(),identifyId,property.getAccount(),proNo,property.getProSpec());
			if(rows>0){
				property.setProId(proId);
				
				//保存图片信息
				for(TPicture pic : picList){
					pic.setPropertyID(proId);
				}
				TPictureBiz.addPicInfo(conn, picList, proId);
				
				//保存持有人、所有人信息、保管人，见证人，当事人
				if(StringUtils.isNotBlank(ownerInfo)){
					saveRaletePerson(ownerInfo,jdbc,conn,property,1,18);
				}
				if(StringUtils.isNotBlank(everyOneInfo)){
					saveRaletePerson(everyOneInfo,jdbc,conn,property,1,32);
				}
				if(StringUtils.isNotBlank(bgPerson)){
					saveRaletePerson(bgPerson,jdbc,conn,property,2,19);
				}
				if(StringUtils.isNotBlank(jzPerson)){
					saveRaletePerson(jzPerson,jdbc,conn,property,2,17);
				}
				if(StringUtils.isNotBlank(dsPerson)){
					saveRaletePerson(dsPerson,jdbc,conn,property,2,22);
				}
				
				//保存附件信息
				for(TAttachment att : attList){
					att.setPropertyID(proId);
				}
				TAttachmentBiz.addAttachInfo(jdbc, conn, attList, "21", proId);
				
				//保存分类属性
				if(StringUtils.isNotBlank(cateAttrInfo)){
					String[] attrArr=cateAttrInfo.split(";",-1);
					for(int i=0;i<attrArr.length;i++){
						String[] singleAttr=attrArr[i].split("\\|",-1);
						jdbc.update(conn, SQL_INSERT_PROPERTY_ATTRIBUTE, proId,singleAttr[0], 
								singleAttr[1], singleAttr[2],singleAttr[3]);
					}
				}
				ret=proId;
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
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
		return ret;
	}
	/**
	 * 保存鉴定信息
	 * */
	private static long saveIdentifyInfo(String str,JDBC jdbc,Connection conn,String account) throws Exception{
		String ideResult=str.split(";",-1)[0];
		String ideDate=str.split(";",-1)[1];
		String ideUint=str.split(";",-1)[2];
		String idePerson=str.split(";",-1)[3];
		String ideHandle=str.split(";",-1)[4];
		long id=jdbc.insertWidthID(conn, "SEQ_IDENTIFY", Long.class, SQL_INSERT_IDENTIFY, 
				ideResult,ideDate,ideUint,idePerson,ideHandle,account);
		return id;
	}
	
	/**
	 * type=1 保存持有人，所有人
	 * type=2 保存保管人，见证人，当事人
	 * code ”17”:见证人“18”:持有人  ”19”:保管人 “20”:其他在场人员 “22”:当事人 “32”所有人
	 * */
	//保存持有人，所有人，保管人，见证人，当事人  先删除再保存
	private static void saveRaletePerson(String str,JDBC jdbc,Connection conn,TProperty property,int type,int code) throws Exception{
		if(type==1){
			String civiName=str.split(";",-1)[0];
			String civiAddress=str.split(";",-1)[1];
			String idType=str.split(";",-1)[2];
			String idNum=str.split(";",-1)[3];
			String civiPhone=str.split(";",-1)[4];
			String unit=str.split(";",-1)[5];
			String strId=UUID.randomUUID().toString();
			
			jdbc.update(conn, SQL_INSERT_OWNER, strId, civiName, idType, idNum, 
					civiAddress,civiPhone,property.getAccount(), null, code, null,unit,null);
			jdbc.update(conn, SQL_INSERT_PROPERTY_OWNER, property.getProId(), strId,DateUtil.getCurrentDatetime());
		}else if(type==2){
			String civiName=str;
			String strId=UUID.randomUUID().toString();
			jdbc.update(conn, SQL_INSERT_OWNER, strId, civiName, null, null, 
					null,null,property.getAccount(), null, code, null,null,null);
			jdbc.update(conn, SQL_INSERT_PROPERTY_OWNER, property.getProId(), strId,DateUtil.getCurrentDatetime());
		}
	}
	
	/**
	 * @param detail
	 * @return
	 */
	public static boolean update(TProperty property, TCivilian owner, TPolice police, TDetail detail, List<TPicture> picList, List<TAttachment> attList,String cateAttrInfo){ 
		//更新操作，案件信息不更新
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			String status = jdbc.queryForObject(conn, "select STATUS from t_property where id = ?", String.class, property.getProId());
			if("WDJ".equals(status) || StringUtils.isBlank(status) || StringUtils.isBlank(property.getProStatus())){
				property.setProStatus("YDJ");
			}
			
			//property.setProOwner(owner.getCiviName());
			jdbc.updateObject(conn, property);
			//String policeTableType = "";
			/*if(StringUtils.isBlank(police.getPoliId())){
				if("LIST".equals(property.getTableType())){
					policeTableType  = "REC";
				}else if("EXTRAC".equals(property.getTableType())){
					policeTableType  = "EXTRAC";
				}
				jdbc.update(conn, SQL_INSERT_POLICE, UUID.randomUUID().toString(), police.getPoliName(), police.getDeptID(), police.getCreator().getAccount(), "16", detail.getRecordId(), policeTableType);
			}else{
				jdbc.update(conn, SQL_UPDATE_POLICE, police.getPoliName(), police.getDeptID(), police.getPoliId());
			}*/
			//如果原来的没有持有人的详细信息就添加一个
			/*if(StringUtils.isBlank(owner.getCiviId())){
				
				jdbc.update(conn, SQL_INSERT_OWNER, UUID.randomUUID().toString(), owner.getCiviName(), owner.getIdNum(), 
						owner.getCiviAddress(),owner.getCiviPhone(), 
						owner.getCreator().getAccount(), detail.getListID(), "18", property.getTableType());
			}else{
				jdbc.update(conn, SQL_UPDATE_OWNER, owner.getCiviAddress(),owner.getCiviPhone(), owner.getCiviId());
			}*/
			//jdbc.update(conn, SQL_UPDATE_DETAIL, fmt.format(detail.getDate()), detail.getListID());
			/*if(StringUtils.isNotBlank(owner.getCiviId())){
				//jdbc.update(conn, SQL_UPDATE_OWNER, owner.getCiviAddress(),owner.getCiviPhone(), owner.getCiviId());
				jdbc.update(conn, owner);
			}else{
				
				owner.setMixId(detail.getListID());
				owner.setTableType(tableType);
				owner.setCiviId(UUID.randomUUID().toString());
			}*/
			
			//TPictureBiz.addPicInfo(conn, picList, property.getProId());
			//TAttachmentBiz.addAttachInfo(conn, attList, Env.TYPE_PROPERTY, property.getProId());
			
			//先删除再保存分类属性
			jdbc.update(conn, SQL_DELETE_PROPERTY_ATTRIBUTE_BY_PROID, property.getProId());
			if(StringUtils.isNotBlank(cateAttrInfo)){
				String[] attrArr=cateAttrInfo.split(";",-1);
				for(int i=0;i<attrArr.length;i++){
					String[] singleAttr=attrArr[i].split("\\|",-1);
					jdbc.update(conn, SQL_INSERT_PROPERTY_ATTRIBUTE, property.getProId(),singleAttr[0], 
							singleAttr[1], singleAttr[2],singleAttr[3]);
				}
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
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
	
	
	/**
	 * 删除财物(由于之前财物中无删除状态，所以使用此接口)
	 * @param properId 财物id
	 * @return
	 */
	public static boolean delProperty(String properId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			
			jdbc.update(conn, SQL_DEL_PROPERTY, properId);
			jdbc.update(conn, "update t_picture set is_display = 'N' where property_id = ?", properId);
			jdbc.update(conn, "update t_attachment set valid_status = 'N' where property_id = ?", properId);
			
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
	
	/**
	 * 案结处置
	 */
	public static long caseDisposal(TCaseDisposal disposal){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		Long disposalId = null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			
			jdbc.update(conn, SQL_INSERT_CASEDISPOSAL, 
				disposal.getDisposal(), disposal.getDisposalPerson(),
				disposal.getInstruction(), disposal.getProId(), 
				disposal.getAttachId());
			disposalId = jdbc.queryForObject(conn, "select seq_case_disposal.currval from dual", Long.class);
			
			jdbc.update(conn, "update t_property set status = 'AJ' where id = ?", disposal.getProId());
			
			conn.commit();
			
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
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
		
		return disposalId != null?disposalId:0;
	}
	
	public static boolean updateRfidAndWarehouse(String proId, String rfidNum, Long warehouseId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		int result = 0;
		try {
			result = jdbc.update(Env.DS, SQL_UPDATE_PRO_RF_WH, rfidNum, warehouseId, proId);
		} catch (SQLException e) {
			log.error("",e);
		} 
		if(result>0){
			return true;
		} else {
			return false;
		}
	}
	
	public static List<TProperty> queryByApplicationId(Long applicationId){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		List<TProperty> list = null;
		try {
			list = jdbc.query(Env.DS, SQL_QUERY_BY_APPLICATIONID, TProperty.class, applicationId);
			TPropertyBiz.packTProCivilians(list, jdbc);
		} catch (Exception e) {
			log.error("", e);
		}finally{
			jdbc.closeAll();
		}
		return list;
	}

	/**
	 * 根据财务ID 查询财务属性 (by liheng)
	 * @param proIds
	 * @return
	 */
	public static List<TProperty> queryProperty(String[] proIds) {
		if (proIds == null || proIds.length == 0)
			return null;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		StringBuffer s = new StringBuffer("(");
		for(int i=0;i<proIds.length;i++){
			s.append("'").append(proIds[i]).append("',");
		}
		s.deleteCharAt(s.length()-1);
		s.append(")");
		try {
			List<TProperty> list = jdbc.query(Env.DS,
					"select name as pro_name,quantity as pro_quantity,unit as pro_unit,owner as pro_owner from t_property where id in" + s.toString(),
					TProperty.class, null);
			if(!ArrayUtil.isEmpty(list)){
				return list;
			}
			return null;
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * 新增处置登记
	 * @param handleResult
	 */
	public static void addHandleResult(THandleResult handleResult, List<TAttachment> attachmentList){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn =null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);
			
			Long handleResultId = jdbc.insertWidthID(conn, "SEQ_HANDLE_RESULT", Long.class, "insert into t_handle_result(property_id, handle_result, account) values(?, ?, ?)", handleResult.getPropertyId(), handleResult.getHandleResult(), handleResult.getAccount());
			
			TAttachmentBiz.addAttachInfo(jdbc, conn, attachmentList, Env.TYPE_HANDLE_RESULT, handleResultId.toString());
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("", e1);
			}
			log.error("", e);
			throw new RuntimeException(e);
		} finally{
			jdbc.closeAll();
			if(conn != null){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					log.error("", e);
				}
			}
		}
		
	}
	
	/**
	 * 查询处置登记结果
	 * @param id
	 * @return
	 */
	public static THandleResult getHandleResultById(String id){
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		THandleResult result = null;
		try {
			result = jdbc.queryForObject(Env.DS, "select * from t_handle_result where id = ?", THandleResult.class, id);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}finally{
			jdbc.closeAll();
		}
		return result;
	}
	
	public static Map<String, Boolean> isRunningStatus(String...proIds){
		
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		StringBuilder str = new StringBuilder("select id, status from t_property where id in ");
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		str.append(CommonUtil.createInClause(proIds));
		try {
			List<PropertyVO> pros = jdbc.query(Env.DS, str.toString(), PropertyVO.class);
			for(PropertyVO vo : pros){
				if(judgeRunningStatus(vo.getStatus())){
					map.put(vo.getId(), true);
				}else{
					map.put(vo.getId(), false);
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		
		return map;
	}
	
	/*public static void updateRunningStatus(String status, String...proIds){
		
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		StringBuilder str = new StringBuilder("update t_property set is_running = ? where id in ");
		
		str.append(CommonUtil.createInClause(proIds));
		
		try {
			jdbc.update(Env.DS, str.toString(), status);
		} catch (SQLException e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
	}*/
	
	public static boolean judgeRunningStatus(String proStatus){
		return	!(
				PropertyStatusKey.YDJ.toString().equals(proStatus)
				||PropertyStatusKey.YRZCK.toString().equals(proStatus)
				||PropertyStatusKey.YRZXK.toString().equals(proStatus)
				||PropertyStatusKey.DXH.toString().equals(proStatus)
				||PropertyStatusKey.DYCZCK.toString().equals(proStatus)
				||PropertyStatusKey.DYCZXK.toString().equals(proStatus)
			)	
		;
	}
	
	public static void packProVOCivilians(List<PropertyVO> proList, JDBC jdbc) throws Exception{
		for(PropertyVO pro : proList){
			List<TCivilian> civi = jdbc.query(Env.DS, SQL_QUERY_CIVI_BY_PROID, TCivilian.class, pro.getId());
			if(civi.size() > 0){
				pro.setCiviName(civi.get(0).getCiviName());
			}
		}
	}
	public static void packTProCivilians(List<TProperty> proList, JDBC jdbc) throws Exception{
		for(TProperty pro : proList){
			List<TCivilian> civi = jdbc.query(Env.DS, SQL_QUERY_CIVI_BY_PROID, TCivilian.class, pro.getProId());
			if(civi.size() > 0){
				pro.setCiviName(civi.get(0).getCiviName());
			}
		}
	}
		
}
