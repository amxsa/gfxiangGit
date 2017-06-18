package cn.cellcom.szba.run;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.sql.SqlConsumeTimeTest;


public class SqlConsumeTime extends TimerTask{
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(SqlConsumeTime.class);
	
	private File logFile = null;
	
	public SqlConsumeTime() {
		this.logFile = getFile();
	}
	
	@Override
	public void run() {
		BufferedReader bufferedReader = null;
		String readStr = null;
		BufferedWriter bw=null;
		//创建新文件，写入sql耗时大于等于2000的记录
		String filename=getFileProps().replace(".log", "_big.log");
		Date date = new Date();  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File bigConsumeTime=new File(filename+"."+sdf.format(date));
		try {
		  //读取日志文件
		  bufferedReader = new BufferedReader(new FileReader(logFile));
		  bigConsumeTime.createNewFile();
		  bw= new BufferedWriter(new FileWriter(bigConsumeTime));
		  while((readStr = bufferedReader.readLine())!=null){
			  boolean isSqlStr = validateFileLine(readStr);
			  if(isSqlStr){
				  int ConsumeTime=getConsumeTime(readStr);
				  if(ConsumeTime>2){
					  if(ConsumeTime>=2000){
						  bw.write(readStr);
						  bw.write("\n");
					  }
					  System.out.println(readStr);
				  }
			  }
		  }
		  bw.flush();
		  bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取log4j文件中字段log4j.appender.file.File的值
	 */
	private String getFileProps(){
		Properties props=null;
		InputStream in = SqlConsumeTimeTest.class.getClassLoader().getResourceAsStream("log4j.properties");
        props = new Properties();
        try {
			props.load(in);
			//关闭资源
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props.getProperty("log4j.appender.file.File");
	}
	
	/**
	 * 获取文件
	 */
	private File getFile(){
        File logFile = new File(getFileProps());
        return logFile;
	}
	
	/**
	 * 验证读取到的数据行是否是sql语句
	 * @param readStr
	 */
	private boolean validateFileLine(String readStr){
		boolean isSqlStr=false;
		if(readStr.toUpperCase().contains("EXECUTE")&&readStr.toUpperCase().contains("COST")){
			isSqlStr=true;
		}
		return isSqlStr;
	}
	
	/**
	 * 从读取到的sql语句行中获取消耗时间
	 */
	private int getConsumeTime(String readStr){
		int costIndex=readStr.indexOf("cost");
		int msIndex=readStr.indexOf("ms",costIndex);
		String ConsumeTimeStr=readStr.substring(costIndex+4,msIndex ).trim();
		int ConsumeTime=Integer.parseInt(ConsumeTimeStr);
		return ConsumeTime;
	}
	
	/**
	 * 获取执行时间
	 */
	public static Date getFixedTime(){
		Calendar cal = Calendar.getInstance();
		//每天定点执行
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 01, 00, 00); 
		return cal.getTime();
	}
	
	/**
	 * 获取一天的毫秒数
	 */
	public static long getMilliseconds(){
        return 24 * 60 * 60 * 1000;
	}
}
