/**
 * 
 */
package cn.cellcom.szba.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * @author Administrator
 * 
 */
public class DateTool {
	// yyyy-MM-dd HH:mm:ss
	public static Date formateString2Time(String str, String pattern) {
		if (str == null)
			return null;
		try {
			return new SimpleDateFormat(pattern).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 格式化string型时间为yyyy-MM-dd的date
	public static Date formateString2Date(String str) {
		if (str == null)
			return null;
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 格式化string型时间为yyyy-MM-dd的date
	public static Date formateString2Date2(String str) {
		if (str == null)
			return null;
		try {

			str = str.replaceAll("-", "");

			return new SimpleDateFormat("yyyyMMdd").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 格式化string型时间为yyyy-MM-dd的date
	public static Date formateString2Date3(String str) {
		if (str == null)
			return null;
		try {

			str = str.replaceAll("-", "");

			return new SimpleDateFormat("yyyyMMdd HHmmss").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 格式化string型时间为yyyy-MM-dd HH:mm:ss的date
	public static Date formateString2DateTime(String str) {
		if (str == null)
			return null;
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	// 格式化string型时间为格林威治时间的date
	public static Date formateString2DateTimeUS(String str) {
		if (str == null)
			return null;
		try {
			
		    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
		    
		    Date d=sdf.parse(str);
	
		    sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// yyyy-MM-dd HH:mm:ss
	public static String formateTime2String(Date time, String pattern) {
		if (time == null)
			return "";
		String dateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		dateString = sdf.format(time);
		return dateString;
	}

	// yyyy-MM-dd HH:mm:ss
	public static String formateTime2String(Date time) {
		if (time == null)
			return "";
		String dateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateString = sdf.format(time);
		return dateString;
	}

	// yyyyMMddHHmmssSSS
	public static String formateTime2String2(Date time) {
		if (time == null)
			return "";
		String dateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		dateString = sdf.format(time);
		return dateString;
	}

	// yyyyMMdd
	public static String formateTime2StringYMD(Date time) {
		if (time == null)
			return "";
		String dateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		dateString = sdf.format(time);
		return dateString;
	}

	// yyyy-MM-dd
	public static String formateDate2String(Date time) {
		if (time == null)
			return "";
		String dateString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateString = sdf.format(time);
		return dateString;
	}

	public static Date diff(Date date, String type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if ("YEAR".equals(type)) {
			calendar.add(Calendar.YEAR, value);
		} else if ("MONTH".equals(type)) {
			calendar.add(Calendar.MONTH, value);
		} else if ("DAY".equals(type)) {
			calendar.add(Calendar.DAY_OF_MONTH, value);
		} else if ("HOUR".equals(type)) {
			calendar.add(Calendar.HOUR_OF_DAY, value);
		} else if ("MINUTE".equals(type)) {
			calendar.add(Calendar.MINUTE, value);
		} else if ("SECOND".equals(type)) {
			calendar.add(Calendar.SECOND, value);
		}
		return calendar.getTime();
	}

	public static Long Diff2(Date date1, Date date2,String type) {
		if(date1 == null || date2 == null){
			return null;
		}
		
		long sec = (date2.getTime() - date1.getTime()) / 1000;
		
		if ("DAY".equals(type)) {
			return sec / (3600 * 24);
		}else{
			return sec;
		}
	}
	public static Long Diff2(String date1, String date2,String type) {
		if(StringUtils.isBlank(date1) || StringUtils.isBlank(date2)){
			return null;
		}
		return Diff2(DateTool.formateString2DateTime(date1),DateTool.formateString2DateTime(date2),type);
	}
	public static String Diff(Date date1, Date date2) {
		long sec = (date2.getTime() - date1.getTime()) / 1000;
		if (sec < 60) {
			return sec + "秒前";
		} else if (sec / 60 < 60 && sec / 60 >= 1) {
			return sec / 60 + "分钟前";
		} else if (sec / 3600 < 24 && sec / 3600 >= 1) {
			return sec / 3600 + "小时前";
		} else if (sec / (3600 * 24) >= 1 && sec / (3600 * 24) < 7) {
			return sec / (3600 * 24) + "天前";
		} else {
			return formateTime2String(date1, "yyyy-MM-dd");
		}
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(1);
	}

	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);

		}
		return c.get(12);
	}

	public static Long Diff(Date date1, Date date2, String type) {
		if(date1 == null || date2 == null){
			return 0l;
		}
		
		long sec = (date2.getTime() - date1.getTime()) / 1000;
		if ("SECOND".equals(type)) {
			return sec;
		} else if ("MINUTE".equals(type)) {
			return sec / 60;
		} else if ("HOUR".equals(type)) {
			return sec / 3600;
		} else if ("DAY".equals(type)) {
			return sec / 86400;
		} else {
			return sec / 3153600;
		}
	}

	public static String formatDD(int i) {
		if (i < 10) {
			return new StringBuilder("0").append(i).toString();
		} else {
			return String.valueOf(i);
		}
	}

	public static Date randomDate(Date startTime, Date endTime) {
		Long start = startTime.getTime();
		Long end = endTime.getTime();
		// Math.random()生成0到1之间的一个随机数
		// 随机数接近0时，生成的日期接近开始日期，随机数接近1时，生成的日期接近结束日期
		long rtn = start + (long) (Math.random() * (end - start));
		if (rtn == start || rtn == end) {
			return randomDate(new Date(start), new Date(end));
		}
		return new Date(rtn);
	}

	public static Date randomDate(String startTime, String endTime,
			String pattern) {
		Date start = DateTool.formateString2Time(startTime, pattern);
		Date end = DateTool.formateString2Time(endTime, pattern);
		return randomDate(start, end);
	}

	/**
	 * <p>
	 * 将数字类型的时间转换为日历类型的时间
	 * </p>
	 * 
	 * @param dateNum
	 *            201011
	 * @return c 日历类型的时间
	 * @throws CSSBaseBizCheckedException
	 */
	public static Date getNewDate() {

		Calendar c = Calendar.getInstance();

		clearTime(c);

		// 返回应答事件
		return new Date(c.getTimeInMillis());
	}

	/**
	 * <p>
	 * 日期的时间值清零
	 * </p>
	 * 
	 * @param c
	 *            指定时间
	 */
	public static void clearTime(Calendar c) {
		if (c != null) {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
		}
	}

	/**
	 * 当前日期的前几天
	 * 
	 * @param dateInt
	 * @return
	 */
	public static Date getBeforeDate(int dateInt) {
		Date d = new Date();
		return new Date(d.getTime() - dateInt * 24 * 60 * 60 * 1000);
	}

	public static void main(String[] args) {
		Date date1 = diff(new Date(),"HOUR",-120);
		
		// System.out.println(date1.toString());
		// System.out.println(Diff(date1,new Date()));
		
// System.out.println(formateString2Time("20121129081212", "yyyyMMddHHmmss"));
// String start = "20130221 12:01";
// String end = "20130221 13:01";
		// Long a = random(DateTool.formateString2Time(start, "yyyyMMdd HH:mm"),
		// DateTool.formateString2Time(end, "yyyyMMdd HH:mm"));
		// System.out.println("a".toUpperCase());
		
// System.out.println(DateTool.formateTime2String2(new
// Date()));//20131016102426546
		try{
			String date = "Wed Aug 01 00:00:00 CST 2012";
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
		    
		    Date d=sdf.parse(date);
	
		    sdf=new SimpleDateFormat("yyyyMMdd");
	
		    System.out.println(sdf.format(d));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
