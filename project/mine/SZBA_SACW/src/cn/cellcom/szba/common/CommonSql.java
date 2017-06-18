package cn.cellcom.szba.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.utils.TRoleUtils;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TRole;
import cn.cellcom.szba.enums.CategoryAttributeKey;
import cn.cellcom.szba.enums.PropertyStatusKey;
import cn.cellcom.szba.enums.PropertyTypeKey;
import cn.cellcom.szba.enums.RoleIdKey;

public class CommonSql {

	private static Log log = LogFactory.getLog(CommonSql.class);

	public static String baseSql1 = "select * from vw_prolist where 1=1 ";
	public static String BASE_PRO_SELECT = "select case.jzcase_id , case.case_id , case.case_name, case.register_date, pro.id, pro.name , pro.quantity, pro.unit, pro.category_id, pro.origin , pro.creator, pro.create_time, pro.status, pro.type, pro.actual_position, pro.from_part, pro.pro_no, pro.store_num ";
	public static String BASE_PRO_FROM = " from t_property pro left join t_case case on pro.case_id = case.case_id ";//left join t_account acc on acc.account = pro.transactor and acc.status = 'Y' left join t_department dept on dept.id = acc.department_id and dept.status ='Y' ";
	public static String BASE_PRO_WHERE = " where 1 = 1 and pro.valid_status = 'Y'";

	public static String BASE_APPLIC_SELECT = "select ";
	public static String BASE_APPLIC_FROM = "";
	public static String BASE_APPLIC_WHERE = "";

	/**
	 * 根据不同视图生成不同的查询财物的语句
	 * 
	 * @param viewName
	 *            视图名称
	 * @param account
	 *            当前账号的TAccount对象
	 * @param roles
	 *            当前账号的角色列表
	 * @return 一条伪SQL
	 */
	public static String getPropertySqlByViewName(String viewName,
			TAccount account, List<TRole> roles) {

		StringBuffer sqlBuffer = new StringBuffer();
		// StringBuffer sqlBuffer = new StringBuffer(BASE_SELECT + BASE_FROM);
		boolean flag = false;

		// 这种查询语句可分为以下几种：
		if (isExistsInMap(Env.PROVIEWNAMEGROUP1, viewName)) {
			// 1、财物登记、销毁申请、远程识证、案结登记、案件关联、财物轨迹、应入国库
			sqlBuffer.append(BASE_PRO_SELECT)
					.append(BASE_PRO_FROM)
					.append(BASE_PRO_WHERE);
			
			// 以下是根据不同的业务去拼装where 子句
			if (viewName.equals("propertyList")) {// 案件关联 by liheng

				joinCommonConditionWithAccount(sqlBuffer, account);
				sqlBuffer.append(" order by pro.create_time desc ");
				return sqlBuffer.toString();

			} else if (viewName.equals("disposalPropertyList")) { // 案结登记 by

				joinCommonConditionWithAccount(sqlBuffer, account);
				sqlBuffer.append(" and pro.type in('BBCCW')");
				sqlBuffer.append(" order by    nvl(case.jzcase_id,'1')  desc,  case.create_time  desc ");
				return sqlBuffer.toString();

			} else if (viewName.equals("inStateTreasuryPropertyList")) {// 应入国库
				joinCommonConditionWithinstateTreasuryPropert(sqlBuffer, account);
				return sqlBuffer.toString();
			} else if(viewName.equals("registerPropertyList")||viewName.equals("hisTracePropertyList")){
				// 财物登记列表
				// 判断当前用户的角色是否包含法制科、经侦大队和预审大队角色等特殊角色；如果包含，则可以查询全部财物
				if (TRoleUtils.compareRoles(roles, Env.rolesQueryAll)) {
					joinCommonConditionWithoutAccount(sqlBuffer);
				}else{
					joinCommonConditionWithAccount(sqlBuffer, account);
				}

				sqlBuffer.append("order by pro.create_time desc");
				return sqlBuffer.toString();
			}else if (viewName.equals("applyDestoryPropertyList")) {
				/**
				 * 销毁申请
				 * 一般财物，办案民警，办案单位领导审核即可
				 * 烟花爆竹，赌博工具，管制刀具，枪支类财物  为治安科民警
				 * */
				if(TRoleUtils.contain(roles,String.valueOf(RoleIdKey.BAMJ))){
					joinCommonConditionWithAccount(sqlBuffer, account);
					sqlBuffer.append(" and pro.status = '"+PropertyStatusKey.DXH+"' ");
					sqlBuffer.append(" and pro.type = '"+PropertyTypeKey.YBCW+"' ");
				}else if(TRoleUtils.contain(roles,String.valueOf(RoleIdKey.ZAKMJ))){
					joinCommonConditionWithoutAccount(sqlBuffer);
					sqlBuffer.append(" and pro.status = '"+PropertyStatusKey.DXH+"' ");
					sqlBuffer.append(" and pro.type in ('"+PropertyTypeKey.YHBZ+"','"+PropertyTypeKey.GZDJLA+"','"+PropertyTypeKey.GZDJBLA+"','"+PropertyTypeKey.JHQZ+"','"+PropertyTypeKey.YHWPTS+"','"+PropertyTypeKey.YHWPGP+"','"+PropertyTypeKey.DBGJ+"') ");
				}
				sqlBuffer.append("order by pro.create_time desc");
				return sqlBuffer.toString();
			}

		} else if (isExistsInMap(Env.PROVIEWNAMEGROUP2, viewName)) {
			// 2、入库申请、销毁登记、销毁实施
			sqlBuffer
					.append(BASE_PRO_SELECT)
					.append(BASE_PRO_FROM)
					.append(BASE_PRO_WHERE);

			// 以下是根据不同的业务去拼装where 子句
			if (viewName.equals("applyInPropertyList")) {
				joinCommonConditionWithAccount(sqlBuffer, account);
				sqlBuffer.append(" and ( pro.status = '"+PropertyStatusKey.YDJ+"')")
				.append(" and (pro.type != '"+CategoryAttributeKey.BBCCW+"') ")
				.append(" and ( pro.type is not null) ")
				.append(" and (pro.case_id is not null or pro.type = '"+CategoryAttributeKey.GZDJBLA+"') ");
			}
			sqlBuffer.append("order by pro.create_time desc");
			return sqlBuffer.toString();
		} else if (isExistsInMap(Env.PROVIEWNAMEGROUP3, viewName)) {
			// 3、移库申请、处置申请、调用申请、调用返还申请、入库统计、财物处置登记、库存管理
			StringBuffer sqlBuffer1 = new StringBuffer(BASE_PRO_SELECT)
					.append(" ,po.into_house_time, po.into_house_person, po.out_house_time, po.out_house_person ")
					.append(" ,loca.location_name, loca.id as \"locationId\", house.storehouse_name, house.storehouse_num ");
			
			StringBuffer sqlBuffer2 = new StringBuffer(BASE_PRO_FROM)
					.append(" left join t_property_operation po on po.property_id = pro.id ")
					.append(" left join t_storage_location loca on loca.id = pro.warehouse_id")
					.append(" left join t_storehouse house on house.id = pro.storehouse_id");
			
			if("handleResultPropertyList".equals(viewName)){
				sqlBuffer1.append(",hr.status handle_result_status, hr.id handle_result_id ");
				sqlBuffer2.append(" left join t_handle_result hr on hr.property_id = pro.id and hr.property_id is not null");
				
			}
			sqlBuffer.append(sqlBuffer1).append(sqlBuffer2);
			sqlBuffer.append(BASE_PRO_WHERE );
			
			// 以下是根据不同的业务去拼装where 子句
			if(viewName.equals("applyInvokeOutPropertyList")){ //财务调用出库列表  by liheng
				
				joinCommonConditionWithAccount(sqlBuffer, account);
				sqlBuffer.append(" and pro.status in('").append(PropertyStatusKey.YRZCK.toString()).append("','").append(PropertyStatusKey.YRZXK.toString()).append("') ");
				sqlBuffer.append(" order by case.create_time  desc ,nvl(case.jzcase_id,'1')  desc");
				return sqlBuffer.toString();
					
			}else if(viewName.equals("applyInvokeIntoPropertyList")){  //财物调用归还列表
				joinCommonConditionWithAccount(sqlBuffer, account);
				sqlBuffer.append(" and pro.status in('").append(PropertyStatusKey.DYCZCK.toString()).append("','").append(PropertyStatusKey.DYCZXK.toString()).append("') ");
				sqlBuffer.append(" order by case.create_time  desc ,nvl(case.jzcase_id,'1')  desc");
				return sqlBuffer.toString();
			}
			else if(viewName.equals("applyMovePropertyList")){ //财物移库列表
				
				joinCommonConditionWithoutAccount(sqlBuffer);
				sqlBuffer.append(" and pro.status in('").append(PropertyStatusKey.YRZCK.toString()).append("') ");
				sqlBuffer.append(" order by nvl(case.jzcase_id,'1')  desc,  case.create_time  desc ");
				return sqlBuffer.toString();
				
			}else if(viewName.equals("applyHandlePropertyList")){   //财物处置列表
				joinCommonConditionWithAccount(sqlBuffer, account);
				sqlBuffer.append(" and pro.case_id is not null and pro.status in('").append(PropertyStatusKey.YRZCK.toString()).append("','").append(PropertyStatusKey.YRZXK.toString()).append("','").append(PropertyStatusKey.YDJ.toString()).append("') ");
				sqlBuffer.append(" order by case.create_time  desc ,nvl(case.jzcase_id,'1')  desc");
				return sqlBuffer.toString();
				
			}else if(viewName.equals("handleResultPropertyList")){ //财物处置登记列表
				joinCommonConditionWithAccount(sqlBuffer, account);
				sqlBuffer.append(" and pro.case_id is not null and pro.status in('")
				.append(PropertyStatusKey.YTH.toString()).append("','")
				.append(PropertyStatusKey.YFH.toString()).append("','")
				.append(PropertyStatusKey.YMS.toString()).append("','")
				.append(PropertyStatusKey.YPM.toString()).append("','")
				.append(PropertyStatusKey.XH.toString()).append("','")
				.append(PropertyStatusKey.YSJGK.toString()).append("') ")
				.append(" order by nvl(hr.status, 'a') desc,nvl(po.into_house_time, po.out_house_time) desc, pro.create_time desc");
				return sqlBuffer.toString();
			}else if(viewName.equals("inventoryPropertyList")){
				//库存管理
				joinCommonConditionWithoutAccount(sqlBuffer);
				sqlBuffer.append("and (po.into_house_person in (select account from t_account where 1=1 and (name like blike:condiIntoPer)))");
				sqlBuffer.append(" and (po.into_house_time >=date:condiIntoStartTime)");
				sqlBuffer.append(" and (po.into_house_time <=date:condiIntoEndTime)");
				sqlBuffer.append(" and (pro.storehouse_id in (select id from t_storehouse where 1=1 and (storehouse_name like blike:condiStoreHouseName)))");
				
				sqlBuffer.append("order by pro.create_time desc");
				return sqlBuffer.toString();
			}else{
				return "";
			}
		} else if (isExistsInMap(Env.PROVIEWNAMEGROUP4, viewName)) {
			// 4、出库统计
			sqlBuffer
					.append(BASE_PRO_SELECT)
					.append(" ,po.into_house_time, po.into_house_person, po.out_house_time, po.out_house_person")
					.append(" ,wh.name as \"locationName\" ")
			        .append(" ,sh.storehouse_name ")
					.append(BASE_PRO_FROM)
					.append(" inner join t_property_operation po on po.property_id = pro.id ")
					.append(" left join t_warehouse wh on wh.id = pro.warehouse_id ")
			        .append(" left join t_storehouse sh on sh.id = pro.storehouse_id ")
					.append(" where 1=1 and pro.status in ('"
							+ PropertyStatusKey.YCZCK.toString() + "', '"
							+ PropertyStatusKey.YCZXK.toString() + "' )");
			sqlBuffer
					.append(" and ( pro.id like plike:condiProCode) ")
					.append(" and ( pro.name like blike:condiProName) ")
					.append(" and ( case.jzcase_id like plike:condiJzcaseId) ")
					.append(" and ( case.case_name like blike:condiCaseName) ")
					.append(" and ( case.register_date >= date:condiStartTime) ")
					.append(" and ( case.register_date <= date:condiEndTime) ")
					.append(" order by po.out_house_time ");
					/*.append(" order by applic.HANDLE_TIME ");*/
		} else if (isExistsInMap(Env.PROVIEWNAMEGROUP5, viewName)) {
			// 5、财物统计
			sqlBuffer
			       .append(BASE_PRO_SELECT)
			       .append(" ,po.into_house_time, po.into_house_person, po.out_house_time, po.out_house_person")
			       .append(" ,cate.name as \"categoryName\" ")
			       .append(" ,wh.name as \"locationName\" ")
			       .append(" ,sh.storehouse_name ")
			       .append(BASE_PRO_FROM)
			       .append(" left join t_category cate on cate.id = pro.category_id ")
			       .append(" left join t_warehouse wh on wh.id = pro.warehouse_id ")
			       .append(" left join t_storehouse sh on sh.id = pro.storehouse_id ")
			       .append(" left join t_property_operation po on po.property_id = pro.id ")
			       .append(" where 1=1 ");
	        sqlBuffer
			       .append(" and ( pro.id like plike:condiProCode) ")
			       .append(" and ( pro.name like blike:condiProName) ")
			       .append(" and ( pro.status = string:condiProStatus) ")
			       .append(" and ( case.jzcase_id like plike:condiJzcaseId) ")
			       .append(" and ( case.case_name like blike:condiCaseName) ")
			       .append(" and ( case.register_date >= date:condiStartTime) ")
			       .append(" and ( case.register_date <= date:condiEndTime) ")
			       .append(" order by po.into_house_time ,pro.pro_no desc ");
		}
		
		else if (isExistsInMap(Env.PROVIEWNAMEGROUP6, viewName)) {
			// 6、入库统计
			sqlBuffer
					.append(BASE_PRO_SELECT)
					.append(" ,po.into_house_time, po.into_house_person, po.out_house_time, po.out_house_person")
					.append(" ,wh.name as \"locationName\" ")
			        .append(" ,sh.storehouse_name ")
					.append(BASE_PRO_FROM)
					.append(" inner join t_property_operation po on po.property_id = pro.id ")
					.append(" left join t_warehouse wh on wh.id = pro.warehouse_id ")
			        .append(" left join t_storehouse sh on sh.id = pro.storehouse_id ")
					.append(" where 1=1 and pro.status in ('"
							+ PropertyStatusKey.YRZCK.toString() + "', '"
							+ PropertyStatusKey.YRZXK.toString() + "' )");
			sqlBuffer
					.append(" and ( pro.id like plike:condiProCode) ")
					.append(" and ( pro.name like blike:condiProName) ")
					.append(" and ( case.jzcase_id like plike:condiJzcaseId) ")
					.append(" and ( case.case_name like blike:condiCaseName) ")
					.append(" and ( case.register_date >= date:condiStartTime) ")
					.append(" and ( case.register_date <= date:condiEndTime) ")
					.append(" order by po.into_house_time ,case.case_name ");
		}

		return sqlBuffer.toString();
	}

	public static void joinCommonConditionWithAccount(StringBuffer sqlBuffer,
			TAccount account) {
		
		commonCondition(sqlBuffer)
		.append("and pro.transactor = ").append(account.getAccount());
	}

	public static void joinCommonConditionWithoutAccount(StringBuffer sqlBuffer) {
		commonCondition(sqlBuffer);
	}

	public static StringBuffer commonCondition(StringBuffer sqlBuffer){
		sqlBuffer.append("and (case.jzcase_id like plike:condiJzcaseId)")
		.append("and (case.case_name like blike:condiCaseName) ")
		.append("and (case.register_date >=date:condiStartTime)")
		.append("and (case.register_date <=date:condiEndTime)")
		.append("and (pro.id like plike:condiProId) ")
		.append("and (pro.name like blike:condiProName) ")
		.append("and (pro.status = string:condiProStatus) ")
		.append("and (pro.pro_no like plike:condiProNo) ");
		
		return sqlBuffer;
	}
	
	public static void joinCommonConditionWithinstateTreasuryPropert(StringBuffer sqlBuffer,
			TAccount account) {
		sqlBuffer.append(" and ( pro.id like blike:condiProId)");
		sqlBuffer.append(" and ( pro.name like blike:condiProName)");
		sqlBuffer.append(" and ( case.jzcase_id like blike:condiJzcaseId)");
		sqlBuffer.append(" and ( case.case_name like blike:condiCaseName)");
	}

	public static boolean isExistsInMap(Map<String, String> map, String key) {
		return map.get(key) != null;
	}
	
	/**
	 * 点击财物查询按纽生成的sql
	 * 
	 * @param whereClause
	 * @param roles
	 * @return
	 */
	@Deprecated
	public static String getQueryPropertySql(String whereClause,
			List<TRole> roles) {
		StringBuffer sqlBuffer = new StringBuffer(baseSql1);
		boolean flag = false;

		// 判断当前用户的角色是否包含法制科、经侦大队和预审大队角色等特殊角色；如果包含，则可以查询全部财物
		flag = TRoleUtils.compareRoles(roles, Env.rolesQueryAll);
		if (flag) {
			sqlBuffer.append(whereClause);
			return sqlBuffer.toString();
		}
		// 判断当前用户的角色是否包含办案单位领导角色；如果包含，则可以查询本部门的财物
		flag = TRoleUtils.compareRoles(roles, Env.rolesQueryByDept);
		if (flag) {
			sqlBuffer
					.append(" and ( (\"deptId\" = int:deptId) or (\"transactor\" = string*account) or (\"jzcaseId\" in strings:jzcaseId) )");
		} else {
			sqlBuffer
					.append(" and ( (\"transactor\" = string*account) or (\"jzcaseId\" in strings:jzcaseId) )");
		}
		sqlBuffer.append(whereClause);

		log.info("=====>queryPropertySql: " + sqlBuffer.toString());
		return sqlBuffer.toString();
	}

	/**
	 * 点击财物登记按纽生成的sql
	 * 
	 * @param whereClause
	 * @return
	 */
	@Deprecated
	public static String getEditPropertySql(String whereClause) {
		StringBuffer sqlBuffer = new StringBuffer(baseSql1);
		sqlBuffer.append(" and  \"proStatus\" in (");

		for (String proStatus : Env.editProperties) {
			sqlBuffer.append("'" + proStatus + "',");
		}
		sqlBuffer = sqlBuffer.replace(sqlBuffer.length() - 1,
				sqlBuffer.length(), "");

		sqlBuffer.append(")");

		sqlBuffer.append(" and (\"transactor\" = string*account)");

		sqlBuffer.append(whereClause);

		log.info("=====>editPropertySql: " + sqlBuffer.toString());
		return sqlBuffer.toString();
	}

	/**
	 * 点击财物处置生成的sql
	 * 
	 * @param whereClause
	 * @param roles
	 * @return
	 */
	@Deprecated
	public static String getHandlerPropertySql(String whereClause,
			List<TRole> roles) {
		StringBuffer sqlBuffer = new StringBuffer(baseSql1);
		sqlBuffer.append(" and  \"proStatus\" in (");
		for (String proStatus : Env.handlerProperties) {

			sqlBuffer.append("'" + proStatus + "',");
		}
		sqlBuffer = sqlBuffer.replace(sqlBuffer.length() - 1,
				sqlBuffer.length(), "");
		sqlBuffer.append(") ");

		// 判断当前用户的角色是否包含办案单位领导角色；如果包含，则可以查询本部门的财物
		boolean flag = TRoleUtils.compareRoles(roles, Env.rolesQueryByDept);
		if (flag) {
			sqlBuffer
					.append(" and ( (\"deptId\" = int:deptId) or (\"transactor\" = string*account) or (\"jzcaseId\" in strings:jzcaseId) )");
		} else {
			sqlBuffer
					.append(" and ( (\"transactor\" = string*account) or (\"jzcaseId\" in strings:jzcaseId) )");
		}

		sqlBuffer.append(whereClause);

		log.info("=====>handlerPropertySql: " + sqlBuffer.toString());
		return sqlBuffer.toString();
	}

	public static String getDestroyPropertySql() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" where \"proStatus\" = 'DXH'");
		return "";
	}

	public static void main(String[] args) {
		StringBuffer sqlBuffer = new StringBuffer();
		// sqlBuffer.append(BASE_SELECT).append(BASE_FROM).append(BASE_WHERE);
		// sqlBuffer.append(BASE_SELECT).append(",civi.name civiName").append(
		// BASE_FROM).append(" left join t_civilian civi on civi.table_type = pro.table_type and civi.mix_id = pro.mix_id and civi.type = '"+Env.TYPE_OWNER+"'").append(BASE_WHERE);
		sqlBuffer
				.append(BASE_PRO_SELECT)
				.append(" ,applic.handle_time, applic.handler ")
				.append(BASE_PRO_FROM)
				.append(" left join t_application_property ap on ap.property_id = pro.id left join t_application applic on applic.id = ap.application_id ")
				.append(" where 1 = 1 and pro.status in ('"
						+ PropertyStatusKey.YRZCK.toString() + "', '"
						+ PropertyStatusKey.YRZXK.toString() + "' )");
		System.out.println(sqlBuffer.toString());
	}

}
