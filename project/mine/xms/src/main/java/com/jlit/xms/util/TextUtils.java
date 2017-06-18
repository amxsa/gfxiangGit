package com.jlit.xms.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;

public class TextUtils {
	
	private static String numbers="0123456789";
	
	private static String words="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 判断字符串是否为空或者为空串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str==null||str.length()==0;
	}
	
	/**
	 * 判断字符串是否为空、空串、0
	 * @param str
	 * @return
	 */
	public static boolean isEmptyOrZero(String str){
		return str==null||str.length()==0||"0".equals(str.trim());
	}
	
	/**
	 * 判断字符串是否为空或者为空串
	 * @param str
	 * @param istrim 是否对字符串进行去空格处理再判断
	 * @return
	 */
	public static boolean isEmpty(String str,boolean istrim){
		if(!istrim)
			return str==null||str.length()==0;
		return str==null||str.trim().length()==0;
	}
	
	
	/**
	 * 判断多个字符串中是否存在空字符串
	 * @param str
	 * @return
	 */
	public static boolean isExistEmpty(String... str){
		if(str==null || str.length==0){
			return true;
		}
		for (int i = 0; i < str.length; i++) {
			if(str[i]==null||str[i].trim().length()==0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断多个对象中是否存在空值
	 * @param elements
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isExistEmpty(Object... elements){
		if(elements==null || elements.length==0){
			return true;
		}
		for (int i = 0; i < elements.length; i++) {
			if(elements[i]==null){
				return true;
			}
			if(elements[i] instanceof String){
				
				String string=((String)elements[i]);
				
				if(string.trim().length()==0){
					return true;
				}
			}
			if(elements[i] instanceof Collection){
				
				Collection collection=((Collection)elements[i]);
				
				if(collection.size()==0){
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 判断第一个字符串是否为空，不为空则返回该字符串，为空则返回第二个字符串
	 * @param str
	 * @return
	 */
	public static String choose(String str,String str2){
		return isEmpty(str)?str2:str;
	}
	

	/**
	 * 产生1-9999之间的随机数
	 * @return
	 */
	public static String genSalt() {
		try {
			
			Integer salt=(int)(Math.random()*9999 +1);
			
			return salt.toString();
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 产生随机数
	 * @param min  随机数开始数字
	 * @param max  随机数结束数字
	 * @return
	 */
	public static Integer random(Integer min,Integer max) {
	try {
			Integer random=new Random().nextInt(max-min+1)+min;
			return random;
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 产生任意长度的随机数
	 * @param length  长度
	 * @return
	 */
	public static String random(int length) {
	try {
			StringBuilder random=new StringBuilder();
			
			for (int i = 0; i < length; i++) {
				int index=random(0,numbers.length()-1);
				random.append(numbers.charAt(index));
			}
			return random.toString();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 产生任意长度的随机数
	 * @param length  长度
	 * @return
	 */
	public static String randomString(int length) {
	try {
			StringBuilder random=new StringBuilder();
			
			for (int i = 0; i < length; i++) {
				int index=random(0,words.length()-1);
				random.append(words.charAt(index));
			}
			return random.toString();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public static String nl2br(String content) {

		if(isEmpty(content)) 
			return content;
		
		return content.replaceAll("\n", "<br/>");
	}
	
	/**
	 * 截取字符串
	 * @param str     字符串
	 * @param start   开始位置
	 * @param length  截取长度
	 * @param isApend 是否在字符串后加上...
	 */
	public static String sub(String str,Integer start,Integer length,boolean isApend){
		
		if(isEmpty(str)||start>str.length()-1) return str;
		
		length=(length+start)>str.length()?(str.length()-start):length;
		
		String sub=str.substring(start, start+length);
		
		return isApend?sub+"...":sub;
	}
	
	
	/**
	 * 截取字符串,并在字符串后加上...
	 * @param str     字符串
	 * @param length  截取长度
	 */
	public static String sub(String str,Integer length){
		if(str==null) 
			 return null;
		return sub(str, 0, length, str.length()>length);
	}
	
	/**
	 * 把用户名中间字符变为*号
	 * @param name
	 */
	public static String encodeUsername(String name) {
		
		if(isEmpty(name)||name.length()<=1) return name;
		
		char start=name.charAt(0);
		
		if(name.length()==2){
			return start+"*";
		}
		
		char end=name.charAt(name.length()-1);
		
		StringBuilder nameStr=new StringBuilder();
		
		nameStr.append(start);
		
		for (int i = 0; i < name.length()-2; i++) {
			nameStr.append("*");
		}
		nameStr.append(end);
		
		return nameStr.toString();
	}
	
	/*public static String htmlToText(String html){
		
		Document parse = Jsoup.parse(html);
		
		return parse.text();
	}*/

	public static <T> String join(char joinChar, List<T> elements) {
		
		StringBuilder result=new StringBuilder();
		
		if(CollectionUtils.isEmpty(elements)){
			return "";
		}
		
		for (T element : elements) {
			result.append(element.toString()).append(joinChar);
		}
		result.deleteCharAt(result.length()-1);
		return result.toString();
	}
	
	public static <T> String join(String joinStr, List<T> elements) {
		
		StringBuilder result=new StringBuilder();
		
		if(CollectionUtils.isEmpty(elements)){
			return "";
		}
		
		for (T element : elements) {
			result.append(element.toString()).append(joinStr);
		}
		result.deleteCharAt(result.length()-joinStr.length());
		return result.toString();
	}
	
	public static <T> String join(List<T> elements) {
		
		
		return join(',',elements);
	}
	
	public static <T> String join(Object... elements) {
		return join(',',Arrays.asList(elements));
	}

	public static List<Integer> splitAsInt(String str,String regex) {
		
		List<Integer> list=new ArrayList<Integer>();
		
		if(TextUtils.isEmpty(str)){
			return list;
		}
		
		String[] arrays = str.split(regex);
		
		for (int i = 0; i < arrays.length; i++) {
			list.add(Integer.valueOf(arrays[i]));
		}
		
		return list;
	}
	
	/**
	 * 获取去除区域码后的手机号码
	 * @param phone: 包含区域码的手机号码,如： +86-13434982121
	 * @return 去除区域码后的手机号码，如：13434982121
	 */
	public static String getPhone(String phone){
		if(isEmpty(phone)) return null;

		if(phone.trim().startsWith("+") && phone.indexOf("-")>0){

			String[] phones = phone.split("-",2);

			phone=phones[1];
		}

		return phone;
	}
	
	/**
	 * 获取手机号码中的区域码
	 * @param phone: 包含区域码的手机号码,如： +86-13434982121
	 * @return 手机号码中的区域码，如：+86
	 */
	public static String getPhoneCode(String phone){
		
		String phoneCode="+86";
		
		if(isEmpty(phone)) return phoneCode;
		
		if(phone.trim().startsWith("+") && phone.indexOf("-")>0){
			
			String[] phones = phone.split("-",2);
			
			phoneCode=phones[0];
			phone=phones[1];
		}
		
		return phoneCode;
	}
	

	public static String sortAndJoin(Integer... arrays) {
		
		if(arrays==null||arrays.length==0){
			return null;
		}
		Arrays.sort(arrays);
		
		return join(arrays);
	}
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(sortAndJoin(5,1,2,3));

	}

	
}
