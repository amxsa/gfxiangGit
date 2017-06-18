package cn.cellcom.szba.common.elutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.domain.TAccount;
import cn.cellcom.szba.domain.TDepartment;
import cn.cellcom.szba.enums.ActualPosition;
import cn.cellcom.szba.enums.ApplicationStatusKey;
import cn.cellcom.szba.enums.CategoryAttributeKey;
import cn.cellcom.szba.enums.DamageStatusKey;
import cn.cellcom.szba.enums.DisposalKey;
import cn.cellcom.szba.enums.ProSaveMethodKey;
import cn.cellcom.szba.enums.PropertyStatusKey;
public class ElMappingFunction {

	private static Log log = LogFactory.getLog(ElMappingFunction.class);
	/**
	 * @param typeCode
	 * @return
	 */
	public static Map<String, String> proOriginMap;
	
	static {
		proOriginMap = new HashMap<String, String>();
		
		proOriginMap.put("KY", "扣押");
		proOriginMap.put("TQ", "提取");
		proOriginMap.put("CF", "查封");
		proOriginMap.put("DJ", "冻结");
		proOriginMap.put("QT", "其它");
	}
	/**
	 * 财物编号加上前缀
	 * @param typeCode
	 * @return
	 */
	public static String mappingProNo(String proNo){
		if(StringUtils.isNotBlank(proNo)){
			return "SZBA"+proNo;
		}else{
			log.error("proNo 不存在");
			return "";
		}
	}
	
	/**
	 * 将财物类别码转为中文
	 * @param typeCode
	 * @return
	 */
	public static String mappingProType(String typeCode){
		try {
			return CategoryAttributeKey.valueOf(typeCode).getCnKey();
		} catch (Exception e) {
			
			if(StringUtils.isNotBlank(typeCode)){
				log.error("typeCode = "+typeCode+", 不存在", e);
				return "";
			}
			if(StringUtils.startsWith(typeCode, "Q")){
				if(typeCode.contains("InTemporary")){
					return "申请入暂存库失败";
				}else if(typeCode.contains("InCenter")){
					return "申请入中心库失败";
				}
			}
			return "";
			
		}
	}
	
	/**
	 * 将财物状态码转为中文
	 * @param statusCode
	 * @return
	 */
	public static String mappingProStatus(String statusCode){
		if(StringUtils.isBlank(statusCode)){
			return "";
		}
		return PropertyStatusKey.valueOf(statusCode).getCnStatus();
	}
	
	/**
	 * 将财物来源的编码转为中文
	 * @param originCode
	 * @return
	 */
	public static String mappingProOrigin(String originCode){
		return proOriginMap.get(originCode);
	}
	
	/**
	 * 根据账号id.转换成一定格式的文本,供页面上展示 
	 * @param account
	 * @return
	 */
	public static String mappingAccountID(String accountID) {
		StringBuilder result=new StringBuilder();
		if(StringUtils.isNotBlank(accountID)) {
			TAccount account = Env.accountMap.get(accountID);
			if(account!=null) {
				result.append(account.getName());
				
				TDepartment department = Env.departmentMap.get(account.getDepartmentID());
				if(department!=null) {
					result.append("[").append(department.getName()).append("]");	
				}
			}
		}
		return result.toString();
	}
	
	public static String mappingAccountNameById(String accountID){
		TAccount account = Env.accountMap.get(accountID);
		if(account != null){
			return account.getName();
		}else{
			return "";
		}
	}
	
	public static String mappingDepartmentNameById(Long deptId){
		TDepartment department = Env.departmentMap.get(deptId);
		if(department != null){
			return department.getName();
		}else{
			return "";
		}
	}
	
	public static String mappingDepartmentNameByAccount(String accountId){
		StringBuilder result=new StringBuilder();
		if(StringUtils.isNotBlank(accountId)) {
			TAccount account = Env.accountMap.get(accountId);
			if(account!=null) {
				
				TDepartment department = Env.departmentMap.get(account.getDepartmentID());
				if(department!=null) {
					result.append(department.getName());	
				}
			}
		}
		return result.toString();
	}

	/**
	 * 将财物保存方式转为中文
	 * @param statusCode
	 * @return
	 */
	public static String mappingProSaveMethod(String statusCode){
		if(StringUtils.isBlank(statusCode)){
			return "";
		}
		return ProSaveMethodKey.valueOf(statusCode).getCnKey();
	}
	
	/**
	 * 申请单状态变换
	 * @param statusCode
	 * @return
	 */
	public static String mappingApplicationStatus(String statusCode){
		if(StringUtils.isBlank(statusCode)){
			return "";
		}
		return ApplicationStatusKey.valueOf(statusCode).getCnKey();
	}
	
	/**
	 * 申请单类型转换
	 * @param typeCode
	 * @return
	 */
	public static String mappingApplicationType(String typeCode){
		if(StringUtils.isBlank(typeCode)){
			return "";
		}
		return Env.PROOPERATETYPE.get(typeCode);
	}
	
	/**
	 * 损毁登记状态转换
	 * @param statusCode
	 * @return
	 */
	public static String mappingDamageStatus(String statusCode){
		if(StringUtils.isBlank(statusCode)){
			return "";
		}
		return DamageStatusKey.valueOf(statusCode).getCnStatus();
	}

	/**
	 * 判断角色集合中是否包含某种角色
	 * @param roles 角色集合
	 * @param targetRoleKey 目标角色
	 * @return
	 */
	public static Boolean isOnlyContainRole(List<Map<String, Object>> roles, String targetRoleKey){
		if(roles.size() != 1) return false; 
		if(roles.get(0).containsKey(targetRoleKey)) 
			return true;
		else 
			return false;
		
	}
	
	public static String generateProSaveMethodSelect(){
		StringBuffer buffer = new StringBuffer();
		
		for(ProSaveMethodKey entry : ProSaveMethodKey.values()){
			buffer.append("<option value='"+entry.toString()+"'>"+entry.getCnKey()+"</option>");
		}
		return buffer.toString();
	}
	
	public static String generatePropertyStatusKeySelect(){
		StringBuffer buffer = new StringBuffer();
		
		for(PropertyStatusKey entry : PropertyStatusKey.values()){
			buffer.append("<option value='"+entry.toString()+"'>"+entry.getCnStatus()+"</option>");
		}
		return buffer.toString();
	}
	
	public static String generateActualPositionSelect(){
		StringBuffer buffer = new StringBuffer();
		
		for(ActualPosition entry : ActualPosition.values()){
			buffer.append("<option value='"+entry.toString()+"'>"+entry.getCnStatus()+"</option>");
		}
		return buffer.toString();
	}
	
	public static String generateDisposalKeySelect(String actualPosition){
		StringBuilder builder = new StringBuilder();
		
		if("BAMJ".equals(actualPosition)){
			builder.append("<option value='"+DisposalKey.TH+"'>"+DisposalKey.TH.getCnStatus()+"</option>");
			builder.append("<option value='"+DisposalKey.FH+"'>"+DisposalKey.FH.getCnStatus()+"</option>");
			builder.append("<option value='"+DisposalKey.BM+"'>"+DisposalKey.BM.getCnStatus()+"</option>");
		}else{
			for(DisposalKey entry : DisposalKey.values()){
				builder.append("<option value='"+entry.toString()+"'>"+entry.getCnStatus()+"</option>");
			}
		}
		return builder.toString();
	}
	
	public static String generateUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 生成损毁登记的下拉列表
	 * @return
	 */
	public static String generateDamageStatusSelect(){
		StringBuffer buffer = new StringBuffer();
		
		for(DamageStatusKey entry : DamageStatusKey.values()){
			buffer.append("<option value='"+entry.toString()+"'>"+entry.getCnStatus()+"</option>");
		}
		return buffer.toString();
	}
	/**
	 * 生成申请单标题
	 * @return
	 */
	public static String generateApplicationTitle(String procedureCode){
		String title = "";
		switch(procedureCode){
			case "PROCEDURE001":case "PROCEDURE008":
				title = "财物入库申请";
				break;
			case "PROCEDURE002":
				title = "财物移库申请";
				break;
			case "PROCEDURE003":
				title = "财物处置申请";
				break;
			case "PROCEDURE004":case "PROCEDURE005":
				title = "财物调用申请";
				break;
			case "PROCEDURE006":case "PROCEDURE007":
				title = "财物归还申请";
				break;
			case "PROCEDURE009":case "PROCEDURE010":case "PROCEDURE011":
				title = "财物销毁申请";
				break;
			default:
				break;
		
		}
		return title;
	}
	
	public static void main(String[] args){
		System.out.println(generateProSaveMethodSelect());
	}
	
}
