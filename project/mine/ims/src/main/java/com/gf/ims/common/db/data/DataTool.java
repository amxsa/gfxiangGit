package com.gf.ims.common.db.data;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.gf.ims.common.db.bean.JavaBean;

public class DataTool {

	
	/**
	 * 随机生成float类型数字
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static float randomFloat(float start, float end) {
		if (start > end) {
			throw new RuntimeException("start>end");
		}
		float rand = (float) (Math.random() * (end - start));
		return start + rand;
	}

	/**
	 * 随机生成int类型数字
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int randomInteger(int start, int end) {
		if (start > end) {
			throw new RuntimeException("start>end");
		}
		int rand = (int) (Math.random() * (end - start));
		return start + rand;
	}

	/**
	 * 随机生成long类型数字
	 * @param start
	 * @param end
	 * @return
	 */
	public static long randomLong(long start, long end) {
		if (start > end) {
			throw new RuntimeException("start>end");
		}
		long rand = (long) (Math.random() * (end - start));
		return start + rand;
	}

	/**
	 *  随机生成string类型数字
	 * @param start
	 * @param end
	 * @param size 位数,不够的用0补充
	 * @return
	 */
	public static String randomString(int start, int end, int size) {
		if (start > end) {
			throw new RuntimeException("start>end");
		}
		if (size <= 0) {
			throw new RuntimeException("size must >0");
		}
		int random = randomInteger(start, end);
		String randomStr = String.valueOf(random);
		int len = randomStr.length();
		if (len < size) {
			randomStr = StringUtils.leftPad(randomStr, size, '0');
		}
		return randomStr;
	}
	
	public static String addSymbol(String src, String symbol) {
		if (src == null || symbol == null) {
			throw new RuntimeException("src is null or symbol is null");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(symbol).append(src).append(symbol);
		return sb.toString();
	}

	public static String addSingleQuote(String src) {
		return addSymbol(src, "'");
	}

	public static String createArrayStr(String startStr, String endStr,
			String splitStr, boolean autoType, Object[] arrays) {
		startStr = StringUtils.isBlank(startStr) ? "[" : startStr;
		endStr = StringUtils.isBlank(endStr) ? "]" : endStr;
		splitStr = StringUtils.isBlank(splitStr) ? "," : splitStr;

		StringBuilder sb = new StringBuilder();
		if (!ArrayTool.isEmpty(arrays)) {
			for (Object s : arrays) {
				if (autoType) {
					if (s == null) {
						makeupArrayStr(sb, s, splitStr);
					} else if ((JavaBean.isJavaString(s.getClass()))
							|| (JavaBean.isJavaDatetime(s.getClass())))
						makeupArrayStr(sb, addSingleQuote(s.toString()),
								splitStr);
					else
						makeupArrayStr(sb, s, splitStr);
				} else {
					makeupArrayStr(sb, s, splitStr);
				}
			}
		}
		return startStr + sb.toString() + endStr;
	}

	public static String createInClause(Object[] arrays) {
		return createArrayStr("(", ")", ",", true, arrays);
	}


	public static String createInClause(Collection<?> collection) {
		return createArrayStr("(", ")", ",", true, collection.toArray());
	}

	public static void makeupArrayStr(StringBuilder sb, Object currentStr,
			String splitStr) {
		if (sb == null) {
			throw new RuntimeException("sb is null");
		}
		splitStr = splitStr == null ? "," : splitStr;
		if (sb.length() > 0) {
			sb.append(splitStr);
		}
		sb.append(currentStr);
	}

	public static String getStringSize(long size) {
		if (size == 0L)
			return "0";
		long b = size % 1024L;
		long k = size % 1048576L / 1024L;
		long m = size % 1073741824L / 1048576L;
		StringBuffer sb = new StringBuffer();
		if (m > 0L)
			sb.append(m).append("M");
		if (k > 0L)
			sb.append(k).append("K");
		if (b > 0L)
			sb.append(b).append("B");
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(createArrayStr("(", ")", "?", false, new String[]{"2","3"}));

	}

}
