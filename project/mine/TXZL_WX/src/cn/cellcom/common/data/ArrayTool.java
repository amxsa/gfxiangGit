package cn.cellcom.common.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;

public class ArrayTool {

	private static final Log log = LogFactory.getLog(ArrayTool.class);

	/**
	 * 判断集合是否有数据
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null) || (collection.size() == 0);
	}

	/**
	 * 判断Object[]是否有数据
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] objects) {
		return (objects == null) || (objects.length == 0);
	}

	/**
	 * 判断Map是否有数据
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null) || (map.size() == 0);
	}

	/**
	 * object[]转成list存储
	 * 
	 * @param objs
	 * @return
	 */
	public static List<Object> asList(Object[] objs) {
		return Arrays.asList(objs);
	}

	/**
	 * 数组分割成字符
	 * 
	 * @param objs
	 *            数组
	 * @param mark
	 *            符合
	 * @return
	 */
	public static String getStrByArray(Object[] objs, String mark) {
		if (objs == null || objs.length == 0)
			return "";
		else {
			StringBuffer returnStr = new StringBuffer("");
			for (int i = 0, j = objs.length; i < j; i++) {
				if (null != objs[i]) {
					if (objs[i] instanceof Date) {
						returnStr.append(
								DateTool.formateTime2String((Date) objs[i],
										"yyyy-MM-dd HH:mm:ss")).append(mark);
					} else {
						returnStr.append(objs[i]).append(mark);
					}
				}
			}
			returnStr.deleteCharAt(returnStr.length()-mark.length());
			return returnStr.toString();
		}
	}

	/**
	 * 判断数组中是否包括某个对象
	 * 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(Object[] array, Object obj) {
		boolean flag = false;
		if (isEmpty(array)) {
			throw new RuntimeException("array is empty or is null");
		}
		for (int i = 0, len = array.length; i < len; i++) {
			if (array[i].equals(obj)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 去掉集合中存储字符串数组的空格，回车等字符
	 * @param collection
	 * @return
	 */
	public static Collection repeatTrim(Collection<String[]> collection) {
		if (isEmpty(collection))
			return null;
		for (String[] strs : collection) {
			for(int i=0,len=strs.length;i<len;i++){
				strs[i] = PatternTool.removeBlack(strs[i]);
			}
		}
		return collection;
	}

	public static void main(String[] args) {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{"123  ","\r\n 123 "});
		list.add(new String[]{"456  "," 4 5 6  ","</br>1234 "});
		repeatTrim(list);
		for(int i=0;i<list.size();i++){
			String[] strs = list.get(i);
			for(String str :strs){
				System.out.println(">>>>>"+str);
			}
		}
		System.out.println(getStrByArray(new Object[]{false,"123"}, ","));
		
	}
}
