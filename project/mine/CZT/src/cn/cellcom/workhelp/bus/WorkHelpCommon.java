package cn.cellcom.workhelp.bus;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.workhelp.po.FileItem;

public class WorkHelpCommon {

	/**
	 * 格式转换 将\转换为/
	 * 
	 * @param str
	 * @return
	 */
	public static String changeSeparator(String str) {
		if (StringUtils.isBlank(str))
			return "";
		return str.replace('\\', '/');
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String escapeSecond(String src) {
		return escape(src).replace("%", "/");
	}

	public static String unescapeSecond(String src) {
		return unescape(src.replace("/", "%"));
	}

	public static String concat(String a, String b) {
		return a + b;
	}

	public static boolean contain(String full, String part) {
		if (full.indexOf(part) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String round2(double number) {
		if (Double.isNaN(number)) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(number);
		// Double.parseDouble(df.format(number));
	}

	public static int round(double number) {
		if (Double.isNaN(number)) {
			return 0;
		}
		DecimalFormat df = new DecimalFormat("#");
		return Integer.parseInt(df.format(number));
	}

	public static boolean checkMobileNumber(String number) {
		Pattern pattern = Pattern.compile("^1[358]\\d{9}$");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}

	public static String htmlEscape(String str) {
		// System.out.println(str);
		// String result=str.replaceAll("\"", "\\\\\"").replaceAll("'",
		// "\\\\'");
		String result = str.replaceAll("\"", "&#34").replaceAll("'", "\\\\'");
		// System.out.println(result);
		return result;
	}

	public static String escape1(String str) {
		if (StringUtils.isBlank(str)) {
			return StringUtils.EMPTY;
		}
		return escapeSecond(str);
	}

	public static String unescape1(String str) {
		if (StringUtils.isBlank(str)) {
			return StringUtils.EMPTY;
		}
		return unescapeSecond(str);
	}
	
	/**
	 * 去掉字符串中的某些数据 如特殊字符 [|&;$%@<>()+\\s.,\"\\?!:']
	 * @param param
	 * @param regEx
	 * @return
	 */
	public static String removeStr(String param, String regEx) {
		if(StringUtils.trimToNull(param)==null)
			return param;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(param);
		return m.replaceAll("");
		
	}
	
	public static void main(String[] args) {
//		String str ="/home/obduser/web/backup/abc.txt";
//		String str1 = escapeSecond(str);
//		System.out.println(unescapeSecond(str1));
		File file = new File("/home/obduer/abc.txt");
		FileItem a = new FileItem();
		a.setFileName(file.getName());
		a.setFile(file);
		a.setParent(file.getParent());
		System.out.println(a.getFileNameC());
		String st=a.getFullPathC();
		st="=/2fhome/2fobduser/2fweb/2fapache/2dtomcat/2d7/2e0/2e57/2fRUNNING/2etxt";
		System.out.println(unescapeSecond(st));
		
	}
}
