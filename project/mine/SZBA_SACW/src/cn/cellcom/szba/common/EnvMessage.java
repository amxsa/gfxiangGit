package cn.cellcom.szba.common;

import java.util.Date;

import cn.cellcom.szba.domain.TMessage;

public class EnvMessage {
   
	public static final TMessage SYS_TIT_CON = new TMessage("系统触发消息标题1","系统触发消息内容1");
	
	public static final TMessage ACC_TIT_CON = new TMessage("用户触发消息标题1","用户触发消息内容1");
	
	public static enum TYPE {
		
		  SYS_TRIGGER("0") , ACC_TRIGGER("1");
		  
		  private String value ;
		  
		  private TYPE(String value){
			  this.value = value;
		  }
		  
		  public String value(){
			  return value;
		  }

		 }
   public static final String READ_STATUS = "N";
   
   public static final Date L_READ_TIME = null;

   public static final String HREF = "main";
   
   public static final Date CREATE_TIME = new Date();
   
   public static enum IS_RELATIVE {
	   
	      IS_RELATIVE("Y") , NO_RELATIVE("N");
		  
		  private String value ;
		  
		  private IS_RELATIVE(String value){
			  this.value = value;
		  }
		  
		  public String value(){
			  return value;
		  }
		 } 
}
