package cn.cellcom.szba.common.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.cellcom.szba.biz.TIdHolderBiz;
import cn.cellcom.szba.common.DateTool;
import cn.cellcom.szba.domain.IdHolder;

//根据类型生成编号工具 pansenxin
public class IdHolderUtils {
	private static String currYearStr = "";
	/**
	 * 获取财物编号
	 * 分类ID(5位数字)+财物扣押人所在部门ID(4位数字)+YYMMDD+5位数字（序列），共20位
	 * 一天生成99999条数据才会重复
	 * */
	public synchronized static String getProIdNo(String cateId,String deptId,String idType){
		String retNo="";
		String sqeValue="00001";
		IdHolder idHolder=TIdHolderBiz.findIdHolderByIdType(idType);
		if(idHolder!=null){
			sqeValue=idHolder.getIdValue();
			
			//同步修改
			Integer sqeInt=Integer.parseInt(sqeValue)+1;
			if(sqeInt==100000)
				sqeInt=1;
			int idx=String.valueOf(sqeInt).length();
			switch (idx) {
				case 1:
					sqeValue="0000"+sqeInt;
					break;
				case 2:
					sqeValue="000"+sqeInt;
					break;
				case 3:
					sqeValue="00"+sqeInt;
					break;
				case 4:
					sqeValue="0"+sqeInt;
					break;
				case 5:
					sqeValue=String.valueOf(sqeInt);
					break;
				default:
					break;
			}
			idHolder.setIdValue(sqeValue);
			TIdHolderBiz.update(idHolder);
		}
		retNo=cateId+deptId+DateTool.formateTime2String(new Date(),"yyMMdd")+sqeValue;
		return retNo;
	}
	
	/**
	 * 申请单编号生成规则
	 * 部门名称+YYYY+不定的序列
	 * @param departmentName 部门名称
	 * @param idType 
	 * @return
	 */
	public synchronized static String getApplicationNo(String departmentName, String idType){
		Integer flag = 0;
		if(StringUtils.isBlank(currYearStr)){
			currYearStr = DateTool.formateTime2String(new Date(),"yyyy");
		}else{
			flag = Integer.parseInt(currYearStr) - Integer.parseInt(DateTool.formateTime2String(new Date(),"yyyy"));
		}
		
		String applicationNo="";
		String sqeValue="1";
		IdHolder idHolder=TIdHolderBiz.findIdHolderByIdType(idType);
		if(idHolder!=null){
			sqeValue=idHolder.getIdValue();
			
			//同步修改
			Integer sqeInt=Integer.parseInt(sqeValue)+1;
			if(sqeInt>=Integer.MAX_VALUE || flag < 0){
				sqeInt=1;
			}
			
			sqeValue = String.valueOf(sqeInt);
			idHolder.setIdValue(sqeValue);
			
			TIdHolderBiz.update(idHolder);
		}
		applicationNo=departmentName + "[" + currYearStr + "]" +sqeValue;
		return applicationNo;
	}
}
