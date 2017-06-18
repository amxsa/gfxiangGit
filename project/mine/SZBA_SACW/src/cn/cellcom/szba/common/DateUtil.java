package cn.cellcom.szba.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String DateFormatString = "yyyy-MM-dd";
	public static final String DatetimeFormatString = "yyyy-MM-dd HH:mm:ss";
	public static final String TimeFormatString = "HH:mm:ss";
	public static final String OracleDatetimeFormatString = "yyyy-mm-dd hh24:mi:ss";
	
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatString);
	public static SimpleDateFormat timeFormat = new SimpleDateFormat(TimeFormatString);
	public static SimpleDateFormat datetimeFormat = new SimpleDateFormat(DatetimeFormatString);

	public static String getCurrentDate(){
		return dateFormat.format(new Date());
	}
	
	public static String getCurrentTime(){
		return timeFormat.format(new Date());
	}
	
	public static String getCurrentDatetime(){
		return datetimeFormat.format(new Date());
	}
	
	public static Date getDateFromDateString(String pattern) throws ParseException{
		return dateFormat.parse(pattern);
	}
	
	public static Date getTimeFromDateString(String pattern) throws ParseException{
		return timeFormat.parse(pattern);
	}
	
	public static Date getDatetimeFromDateString(String pattern) throws ParseException{
		return datetimeFormat.parse(pattern);
	}
	
	public static Date getDate(){
		return new Date();
	}
}
