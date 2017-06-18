package com.jlit.xms.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	
	static private Log log = LogFactory.getLog(DateUtil.class);
	
	static public String getDateStr(Date date,String fmt) {    
        SimpleDateFormat format = new SimpleDateFormat(fmt);    
        return format.format(date);    
    }
	
	static public Date getDate(String date,String fmt){
        SimpleDateFormat format = new SimpleDateFormat(fmt);    
        try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}   		
	}
	
	/**
	 * 根据开始时间,结束时间,切分时段获取切分后的时间段
	 * 时间拼接方式:开始时间@结束时间$开始时间@结束时间$开始时间@结束时间
	 * 
	 * @param startTime
	 * @param endTime
	 * @param segment
	 * @return
	 */
	public static String getSegTime(long time1, long time2, int segment){
		Date startTime = new Date(time1);
		Date endTime = new Date(time2);
		long end = endTime.getTime()/1000;
		long start = 0;
		StringBuffer sb = new StringBuffer();
		while(end - start >= segment*60){
			sb.append(DateUtil.getDateStr(startTime, "yyyy-MM-dd HH:mm")+"@");
		    start = (startTime.getTime()/1000)+60*segment;
			startTime.setTime(start*1000);
			sb.append(DateUtil.getDateStr(startTime, "yyyy-MM-dd HH:mm")+"$");
		}
		long differ = end - start;
		if(differ>0&&differ<segment*60){
			sb.append(DateUtil.getDateStr(startTime, "yyyy-MM-dd HH:mm")+"@");
			sb.append(DateUtil.getDateStr(endTime, "yyyy-MM-dd HH:mm")+"$");
		}
		return sb.toString();
	}
	
	/**
	 * 获取秒数
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis();
	}

	/**
	 * 取指定天数后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return cal.getTime();
	}
	
	/**
	 * 日期相减
	 * @param date
	 * @param date1
	 * @return
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}
	
	public static Date praseStringToDate(String dateStr,String fmt){
		if(dateStr == null){
			log.info("字符串转换成时间类型不能为空。");
		}
		SimpleDateFormat sdf =  new SimpleDateFormat(fmt);
		Date newCreateTime = null;
		if(!"".equals(dateStr) ){
			try {
				 newCreateTime = sdf.parse(dateStr);
			} catch (ParseException e) {
				log.warn("时间转换出错!!create_time:"+dateStr);
				e.printStackTrace();
			}
		}
		return newCreateTime;
	}
	
	/**
	 * 格式化字符串，去空格，包括前后跟中间
	 */
	public static String formatString(String str){ 
    	if(str.trim().indexOf(" ") == -1){
    		return str.trim();
    	}else{
    		StringBuffer sb = new StringBuffer();
    		String[] strs = str.trim().split(" ");
    		for(int i=0; i<strs.length; i++){
    			sb.append(strs[i]);
    		}
    		return sb.toString();
    	}
    }
	
	/**
	 * 计算年龄周岁
	 * @param birthDate
	 * @return
	 */
	public static int diffYear(Date birthDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int result = 0;
//		String cYear = sdf.format(new Date()).substring(0,4);//得到当前的年份
//		String birthYear = sdf.format(birthDate).substring(0,4);//得到生日年份
//		int age = Integer.parseInt(cYear) - Integer.parseInt(birthYear);
//		return age;
		try {
			String nowDate = dateFormat.format(new Date());
			String curDate = dateFormat.format(birthDate);
			long day = (dateFormat.parse(nowDate).getTime() - dateFormat.parse(curDate).getTime()) / (24*60*60*1000) + 1;
			String year = new DecimalFormat("#.00").format(day/365f);
			String age[] = year.split("\\.");
			result = age[0].replace("", "").length() < 1 ? 0 : Integer.parseInt(age[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDateToString(String format, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/**
	 * 时间划分
	 * @param num
	 * @return
	 */
	public static List<Date> partitionDate(int num, Date startDate) {
		List<Date> dateList = new ArrayList<Date>();
		if(num == 8 || num == 10) {
			for(int i=0; i<num; i++) {
				int base = 365 / num;
				base += base*i;
				Date curDate = DateUtil.addDate(startDate, base);
				dateList.add(curDate);
			}
		} else {
			int base = 12 / num;
			for(int i=1; i<num+1; i++) {
				Date nowDate = new Date(startDate.getYear(), startDate.getMonth(), startDate.getDate());
				int month = nowDate.getMonth();
				month += i*base;
				if(month > 12) {
					month = month - 12;
					int year = nowDate.getYear();
					nowDate.setYear(year + 1);
				}
				nowDate.setMonth(month);
				dateList.add(nowDate);
			}
		}
		return dateList;
	}
	
	/**
	 * 得到某一年的第一天  
	 * @param d 某年格式，yyyy
	 * @return
	 * @throws ParseException
	 */
	public static Date getYearFirstDay(String d) throws ParseException {  
		Date date=new SimpleDateFormat("yyyy").parse(d);
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_YEAR, calendar     
	            .getActualMinimum(Calendar.DAY_OF_YEAR));     
	    
	    return calendar.getTime();     
	}    
	
	/**
	 * 得到某年的最后一天   
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date getYearLastDay(String d) throws ParseException  {     
		Date date=new SimpleDateFormat("yyyy").parse(d);
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_YEAR, calendar     
	            .getActualMaximum(Calendar.DAY_OF_YEAR));    
	    return calendar.getTime();     
	} 
	
	
	/**
	 * 得到某月的第一天  
	 * @param d 某月格式，yyyy-MM
	 * @return
	 * @throws ParseException
	 */
	public static Date getMonthFirstDay(String d) throws ParseException {  
		Date date=new SimpleDateFormat("yyyy-MM").parse(d);
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMinimum(Calendar.DAY_OF_MONTH));     
	    
	    return calendar.getTime();     
	}     
	    
	/**
	 * 得到某月的最后一天   
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date getMonthLastDay(String d) throws ParseException {     
		Date date=new SimpleDateFormat("yyyy-MM").parse(d);
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMaximum(Calendar.DAY_OF_MONTH));    
	    return calendar.getTime();     
	} 
	/**
	 * 得到某月的下个月的第一天   
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextMonthFirstDay(String d) throws ParseException {     
		Date date=new SimpleDateFormat("yyyy-MM").parse(d);
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);
	    calendar.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMaximum(Calendar.DAY_OF_MONTH)); 
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    return calendar.getTime();     
	}
	/**
	 * 得到下一天时间   
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextDay(String d) throws ParseException {     
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(d);
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    return calendar.getTime();     
	}  
	/**
	 * 得到下一天时间   
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextDay(Date d) throws ParseException {     
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(d);
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    return calendar.getTime();     
	}
	/**
	 * 得到某一天第一秒时间
	 * @return
	 */
	public static Date getDayStartTime(Date d){
		 Calendar calendar = Calendar.getInstance(); 
		 calendar.setTime(d);
		 calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
				 calendar.get(Calendar.DATE), 0, 0, 0);
		 return calendar.getTime();
	}
	/**
	 * 得到某一天最后一秒时间
	 * @return
	 */
	public static Date getDayEndTime(Date d){
		 Calendar calendar = Calendar.getInstance(); 
		 calendar.setTime(d);
		 calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
				 calendar.get(Calendar.DATE), 23, 59, 59);
		 return calendar.getTime();
	}
	public static void main(String[] args) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(getDayStartTime(new Date())));
		System.out.println(sdf.format(getDayEndTime(new Date())));
	}
	/**
	 * 得到星期几
	 * @param DateStr
	 * @return
	 */
	 public  static String getWeekDay(String DateStr){
	      SimpleDateFormat formatYMD=new SimpleDateFormat("yyyy-MM-dd");//formatYMD表示的是yyyy-MM-dd格式
	      SimpleDateFormat formatD=new SimpleDateFormat("E");//"E"表示"day in week"
	      Date d=null;
	      String weekDay="";
	      try{
	         d=formatYMD.parse(DateStr);//将String 转换为符合格式的日期
	         weekDay=formatD.format(d);
	      }catch (Exception e){
	         e.printStackTrace();
	      }
	      //System.out.println("日期:"+DateStr+" ： "+weekDay);
	     return weekDay;
	 }
}
