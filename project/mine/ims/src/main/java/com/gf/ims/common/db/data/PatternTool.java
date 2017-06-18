package com.gf.ims.common.db.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class PatternTool {

	/**
	 * 去掉空格、回车、换行、制表符
	 * 
	 * @param str
	 * @return
	 */
	public static String removeBlack(String str) {
		return str.replaceAll("\\s", "");
	}

	/**
	 * 去掉字符的html,xml标签 如<font>123</font> 返回123
	 * 
	 * @param str
	 * @return
	 */
	public static String removeHtmlTag(String str) {
		return str.replaceAll("</?[^>]+>", "");
	}

	/**
	 * 正则表达验证字符
	 * 
	 * @param str
	 *            要检查的字符
	 * @param regEx
	 *            正则表达式
	 * @param returnInfo
	 *            返回描述信息
	 * @return
	 */
	public static String checkStr(String str, String regEx, String returnInfo) {
		Pattern p = Pattern.compile(regEx);
		if (!p.matcher(str).matches())
			return returnInfo;
		return null;
	}

	public static List<String> getMatchCount(String str, String regex) {
		if (StringUtils.isBlank(str)) {
			throw new RuntimeException("指定的字符串不能为空");
		}
		if (StringUtils.isBlank(regex)) {
			throw new RuntimeException("指定的正则表达式不能为空");
		}
		List<String> resultList = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		int start = 0;
		while (m.find(start)) {
			resultList.add(m.group());
			start = m.end();
		}
		return resultList;
	}
	
	/**
	 * 去掉字符串中的某些数据 如特殊字符 [|&;$%@<>()+\\s.,\"\\?!:']
	 * 
	 * @param param
	 * @param regEx
	 * @return
	 */
	public static String removeJSStr(String param, String regEx) {
		if (StringUtils.trimToNull(param) == null)
			return param;
		Pattern p = Pattern.compile(regEx == null ? RegEx.JS : regEx);
		Matcher m = p.matcher(param);
		return m.replaceAll("");
	}
	
	/**
	 * 选出字符第一次出现的下标
	 * @param param
	 * @param regEx
	 * @return
	 */
	public static int indexOfFirst(String param, String regEx) {
		if (StringUtils.trimToNull(param) == null)
			return 0;
		Matcher matcher = Pattern.compile(regEx).matcher(param);
		if(matcher.find()){
			return matcher.start();
		}
		return 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = getMatchCount("stringstr123st456", "st");
		
		System.out.println(checkStr("510000", "^\\d{6}$", "eroor"));
		
	}

}
