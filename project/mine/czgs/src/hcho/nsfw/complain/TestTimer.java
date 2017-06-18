package hcho.nsfw.complain;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

public class TestTimer {

	/**
	 * 
	 * Timer 不可控   不建议用
	 * @param args
	 */
	public static void main(String[] args) {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("new TimerTask() {...}.run()"+new Date());
			}
		}, 1000, 2000);
	}
	
	@Test
	public void testCalendar(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.YEAR, 5);//测试时间2015年
		cal.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println(cal.getTime());//2020年当前月1号
	}
}
