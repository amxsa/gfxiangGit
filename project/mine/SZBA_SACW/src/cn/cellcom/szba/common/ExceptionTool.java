package cn.cellcom.szba.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import cn.cellcom.szba.domain.DataMsg;

/**
 * 
 * @author 
 *
 */
public class ExceptionTool{
	//设置错误返回信息
	public static String error(HttpServletRequest request,String returnKeyNew,String message,Exception e){
		String returnKey = "/error/error";
		if (e instanceof BaseBizException) {
			BaseBizException be = (BaseBizException)e;
			if( be.getCode() == -1){
				message = be.getMessage();
				returnKey = returnKeyNew;
			}else if(be.getCode() == Env.EXCCODE){
				message = be.getMessage();
				returnKey = "/error/alert";
			}
		}
		
		request.setAttribute("message", message);
		request.setAttribute("exception", e);
		request.setAttribute("happenTime", DateTool.formateTime2String(new Date()));
		
		return returnKey;
	}
	//设置错误返回信息
	public static String error(HttpServletRequest request,Exception e){
		String returnKey = Env.ERROR_VIEW;
		DataMsg dataMsg = new DataMsg();
		if (e instanceof BaseBizException) {
			BaseBizException be = (BaseBizException)e;
			dataMsg.setCode(be.getCode()+"");
			dataMsg.setState("F");
			dataMsg.setMsg(be.getMessage());
		}else{
			dataMsg.setCode("1");
			dataMsg.setState("F");
			dataMsg.setMsg(e.getMessage());
		}
		
		request.setAttribute("dataMsg", dataMsg);
		
		return returnKey;
	}
}
