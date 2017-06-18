/**
 * 
 */
package com.gf.ims.common.db.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * @author Administrator
 * 
 */
public class DateTool {
	/**
	 * 字符串转成时间
	 * 
	 * @param str
	 * @param pattern
	 *            如 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date formateString2Time(String str, String pattern) {
		if (StringUtils.isBlank(str))
			return null;
		try {
			return new SimpleDateFormat(pattern).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间转成字符串
	 * 
	 * @param time
	 * @param pattern
	 *            如 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String formateTime2String(Date time, String pattern) {
		if (time == null)
			return "";
		return new SimpleDateFormat(pattern).format(time);
	}
	
	public static String getYYYYMMDD(Date time) {
		if (time == null)
			return "";
		return new SimpleDateFormat("yyyyMMdd").format(time);
	}
	
	public static String getYYYY_MM_DD(Date time) {
		if (time == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	/**
	 * 将时间向前或向后推，
	 * 
	 * @param date
	 *            要处理的时间
	 * @param type
	 *            计算类型，如SECOND,MINUTE,HOUR
	 * @param value
	 *            要增加或减少的值
	 * @return
	 */
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

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return 1 + c.get(2);
	}

	public static int getDate(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(5);
	}

	public static Integer getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return Integer.valueOf(c.get(7) - 1);
	}

	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(11);
	}

	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(12);
	}

	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(13);
	}

	public static int getMilliSecond(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(14);
	}

	/**
	 * 构建 时间
	 * @param year 年
	 * @param month 月
	 * @param date 日
	 * @return
	 */
	public static Date buildDate(int year, int month, int date) {
		return buildDate(year, month, date, 0, 0, 0, 0);
	}

	/**
	 * 构建时间
	 * @param year 年
	 * @param month 月
	 * @param date 日
	 * @param hour 小时
	 * @param minute  分
	 * @param second 秒
	 * @param millisecond 毫秒
	 * @return
	 */
	public static Date buildDate(int year, int month, int date, int hour,
			int minute, int second, int millisecond) {
		if (year < 0) {
			throw new RuntimeException("year must>0");
		}
		if ((month < 1) || (month > 12)) {
			throw new RuntimeException("month must in [0-12]");
		}
		int days = getActualDays(year, month);
		if ((date < 1) || (date > days)) {
			throw new RuntimeException("date must in [0-" + days + "]");
		}
		Calendar c = Calendar.getInstance();
		c.set(1, year);
		c.set(2, month - 1);
		c.set(5, date);
		if ((hour < 0) || (hour > 23)) {
			throw new RuntimeException("hour must in [0-23]");
		}
		if ((minute < 0) || (minute > 59)) {
			throw new RuntimeException("minute must in [0-59]");
		}
		if ((second < 0) || (second > 59)) {
			throw new RuntimeException("second must in [0-59]");
		}
		if ((millisecond < 0) || (millisecond > 999)) {
			throw new RuntimeException("second must in [0-999]");
		}
		c.set(11, hour);
		c.set(12, minute);
		c.set(13, second);
		c.set(14, millisecond);
		return c.getTime();
	}

	public static boolean isLeap(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		if (date != null) {
			c.setTime(date);
		}
		int year = getYear(c.getTime());
		return c.isLeapYear(year);
	}

	public static int getActualDays(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		if (date != null) {
			c.setTime(date);
		}
		int month = getMonth(c.getTime());
		int days = 31;
		if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			days = 30;
		}
		if (month == 2) {
			if (isLeap(c.getTime()))
				days = 29;
			else {
				days = 28;
			}
		}
		return days;
	}

	public static int getActualDays(int year, int month) {
		if (year < 0) {
			throw new RuntimeException("year must>0");
		}
		if ((month < 1) || (month > 12)) {
			throw new RuntimeException("month must in [0-12]");
		}
		GregorianCalendar c = new GregorianCalendar();
		c.set(1, year);
		c.set(2, month - 1);
		c.set(5, 1);
		c.set(10, 0);
		c.set(12, 0);
		c.set(13, 0);
		return getActualDays(c.getTime());
	}

	/**
	 * 计算两个时间相差值
	 * 
	 * @param date1
	 *            时间1(小值)
	 * @param date2
	 *            时间2(大值)
	 * @param type
	 *            计算类型，如SECOND,MINUTE,HOUR
	 * @return
	 */
	public static Long Diff(Date date1, Date date2, String type) {
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

	/**
	 * 指定范围随机生成时间
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static Date randomDate(Date startTime, Date endTime) {
		Long start = startTime.getTime();
		Long end = endTime.getTime();
		long rtn = start + (long) (Math.random() * (end - start));
		if (rtn == start || rtn == end) {
			return randomDate(new Date(start), new Date(end));
		}
		return new Date(rtn);
	}

	/**
	 * 指定范围随机生成时间
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param pattern
	 *            时间格式 如 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date randomDate(String startTime, String endTime,
			String pattern) {
		Date start = DateTool.formateString2Time(startTime, pattern);
		Date end = DateTool.formateString2Time(endTime, pattern);
		return randomDate(start, end);
	}
	
	public static  Date getNow(){
		return new Date();
	}
	
	public static Timestamp getTimestamp( Date date){
		if(date ==null){
			return new Timestamp(new Date().getTime());
		}else{
			return new Timestamp(date.getTime());
		}
	}

	public static void main(String[] args) {
		Date date1 = diff(new Date(), "HOUR", -120);
	}

}
