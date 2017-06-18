package com.jlit.xms.util;

import java.util.Calendar;
import java.util.Date;

public class SimpleDateUtils {
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
}
