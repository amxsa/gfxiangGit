/**
 * 
 */
package cn.cellcom.szba.common;

import cn.cellcom.szba.domain.DataMsgExt;


/**
 * @author Administrator
 * 
 */
public class DataMsgTool {
	public static DataMsgExt getDataMsgSuccess(){
		
		DataMsgExt dataMsg = new DataMsgExt("Y", 0, "操作成功");
		
		return dataMsg;
	}
	public static DataMsgExt getDataMsgSuccess(Object object){
		
		DataMsgExt dataMsg = new DataMsgExt("Y", 0, "操作成功",object);
		
		return dataMsg;
	}
	
	//
	public static DataMsgExt getDataMsgSuccessAlertMsg(String msg){
		
		DataMsgExt dataMsg = new DataMsgExt("Y", 0, msg);
		
		return dataMsg;
	}
	
	//组织有异常的DataMsg
	public static DataMsgExt getDataMsgError (Exception e,int code){
		
		DataMsgExt dataMsg = null;
		//有自定义异常先取自定义异常
		if (e instanceof BaseBizException) {
			BaseBizException e1 = (BaseBizException) e;
			
			if(e1.getCode() != Env.EXCCODE){
				dataMsg = new DataMsgExt(e1.getCode());
			}else{
				dataMsg = new DataMsgExt(e1.getMessage());
			}
		}else{
			dataMsg = new DataMsgExt(code);
		}
		
		return dataMsg;
	}
	//组织有异常的DataMsg
	public static DataMsgExt getDataMsgError (int code){
		
		DataMsgExt dataMsg = null;

		dataMsg = new DataMsgExt(code);
		
		return dataMsg;
	}
	
}
