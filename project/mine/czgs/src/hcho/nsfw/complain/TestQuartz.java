package hcho.nsfw.complain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestQuartz {

	
	public void doSimpleTrigger(){
		System.out.println("TestQuartz.doSimpleTrigger()" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public void doCronTrigger(){
		System.out.println("TestQuartz.doCronTrigger()"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
}
