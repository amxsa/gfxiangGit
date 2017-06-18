package cn.cellcom.esb.bus;



import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.data.PatternTool;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;




public class AccountManagerHelp {
	
	public static String checkParamByUser(String number,String password,String setid,String areacode,String yzm,String operateType){
		if (StringUtils.isBlank(number) || StringUtils.isBlank(setid) ||StringUtils.isBlank(yzm)||StringUtils.isBlank(operateType)) {
			return "缺少必要号码,套餐,验证码,操作类型参数";
		}
		String result = checkParam(number, password, setid,areacode,operateType);
		if(result!=null)
			return result;
		if(ESBEnv.SMSCODE.containsKey(number)){
			String  orderSmsCode=  String.valueOf(ESBEnv.SMSCODE.get(number)[0]);
			if(!yzm.equals(orderSmsCode))
				return "短信验证码错误";
			return null;
		}else{
			return "短信验证码错误";
		}
			
	}
	
	public static String checkParamByClient(String number,String password,String setid,String areacode,String operateType){
		if (StringUtils.isBlank(number) 
				|| StringUtils.isBlank(setid)||StringUtils.isBlank(operateType) ) {
			return "缺少必要号码,套餐,操作类型参数";
		}
		return checkParam(number, password, setid,areacode,operateType);
	}
	
	public static String checkParam(String number,String password,String setid,String areacode,String operateType) {
		String error = PatternTool.checkStr(number, Env.C_PATTERN,
				"手机号码格式错误");
		if (error != null)
			return error;
		if(!"U".equals(operateType)&&!"A".equals(operateType))
			return "非订购或升级操作";
		if("A".equals(operateType)){
			if(StringUtils.isBlank(password)){
				return "密码不能为空!";
			}
			error = PatternTool.checkStr(password, "^[0-9]{1}\\d{5}$", "密码格式错误");
		}
			
		if (error != null)
			return error;
		Long setidTemp=Long.valueOf(setid);
		if(setidTemp==null)
			return "套餐错误";
		if (setidTemp!=51&&setidTemp!=21&&setidTemp!=22)
			return "套餐错误";
		if(StringUtils.isBlank(areacode))
			return "非广东省区号";
		if (!Env.AREACODE.containsKey(areacode))
			return "非广东省区号";
		return null;
	}
	
	
	public static void main(String[] args) {
		

	}

}
