package cn.cellcom.wechat.common;

import org.apache.commons.lang.StringUtils;


import cn.cellcom.common.data.PatternTool;

import cn.cellcom.wechat.po.TMobileHead;
import cn.cellcom.wechat.po.TRegister;

public class UserUtil {

	public final static String[] CODE = { "000", "0755", "0757", "0769",
			"0754", "0760" };

	/**
	 * 判断是否是基础包用户
	 * 
	 * @param login
	 * @return
	 */
	public static boolean isJCUser(String number,Long setid,Integer usage,String servNbr) {
		if (setid == 21L || setid == 31L) {
			return true;
		} 
		return false;
	}

	/**
	 * 判断是否是信息包用户
	 * 
	 * @param login
	 * @return
	 */
	public static boolean isXXUser(String number,Long setid,Integer usage,String servNbr) {
		if (setid == 22L || setid == 32L) {
			return true;
		} 
		return false;
	}

	/**
	 * 判断是否是通信助理用户
	 * 
	 * @param login
	 * @return
	 */
	public static boolean isJCOrXXUser(String number ,Long setid,Integer usage,String servNbr) {
		if (setid == 21L || setid == 31L || setid == 22L || setid == 32L) {
			return true;
		} 
		return false;
	}

	/**
	 * 判断是否是漏话正式用户
	 * 
	 * @param login
	 * @return
	 */
	public static boolean isLHUser(String number,Long setid) {
		if (setid == 51L) {
			return true;
		} 
		return false;
	}

	/**
	 * 判断是否是基础包或信息包或漏话用户
	 * 
	 * @param login
	 * @return
	 */
	public static boolean  isTXZLUser(String number,String state,Long setid,Integer usage,String servNbr) {
		if ((setid == 51L || setid == 21L || setid == 31L || setid == 22L
				|| setid == 32L)&&"Y".equals(state)) {
			return true;
		} 
		return false;
	}

	public static boolean isTXZL_C(String number,String state, Long setid,Integer usage,String servNbr) {
		boolean result = isTXZLUser(number,state,setid,usage,servNbr);
		if (!result)
			return result;
		String resultStr = PatternTool.checkStr(number, Env.C_PATTERN, "您不是通信助理C网用户");
		if (resultStr != null)
			return false;

		return true;
	}
	
	public static boolean isTXZL_C(TRegister register) {
		if(register==null)
			return false;
		boolean result = isTXZLUser(register.getNumber(),register.getStatus(),register.getSetid(),register.getUsage(),register.getServNbr());
		if (!result)
			return result;
		String resultStr = PatternTool.checkStr(register.getNumber(), Env.C_PATTERN, "您不是通信助理C网用户");
		if (resultStr != null)
			return false;

		return true;
	}

	private static boolean isStandard(String number, String postCode) {
		if (StringUtils.isBlank(number) || StringUtils.isBlank(postCode)) {
			return false;
		}
		// 默认号码都为7位
		boolean a = number.length() == 7;
		for (String code : CODE) {// 如果和该数组任一个相等的，表示固话应该为8位
			if (code.equals(postCode)) {
				a = number.length() == 8;
				break;
			}
		}
		boolean c = !number.startsWith("0"); // 不以０开头的
		return a && c;
	}

	/**
	 * 区号+固话号码 形成完整号码
	 * 
	 * @param number
	 * @param postCode
	 * @return
	 */
	public static String addPostCode(String number, String postCode) {
		if (isStandard(number, postCode)) {
			return new StringBuilder(postCode).append(number).toString();
		}
		return number;
	}

	
	/**
	 * 获取号码区号
	 * 
	 * @param number
	 * @return
	 */
	public static String getAreaCode(String number) {
		if (number == null)
			return null;
		TMobileHead po = null;
		if (number.startsWith("1") && number.length() == 11) {
			for (int i = 0, len = Env.MOBILE_HEAD_LIST.size(); i < len; i++) {
				po = Env.MOBILE_HEAD_LIST.get(i);
				if (po != null && number.startsWith(po.getHeadnum()))
					return po.getHeadzone();
			}
		} else if (number.startsWith("0")
				&& (number.length() == 11 || number.length() == 12 || number
						.length() == 7)) {
			// 区号三位的情况
			String areacode = null;
			if (Env.CODES3.contains(number.substring(0, 3))) {
				areacode = number.substring(0, 3);
			} else {
				areacode = number.substring(0, 4);
			}
			return areacode;
		}

		return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
