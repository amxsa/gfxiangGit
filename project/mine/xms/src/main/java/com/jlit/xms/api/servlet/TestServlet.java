package com.jlit.xms.api.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jlit.model.vo.UserVO;
import com.jlit.xms.api.message.ServletMessageReq;
import com.jlit.xms.common.spring.ApplicationContextTool;
import com.jlit.xms.enums.ServletRspErrorCode;
import com.jlit.xms.service.DeptService;
import com.jlit.xms.util.ServletUtil;
import common.DB.JDBC;

public class TestServlet extends HttpServlet{
	
	private static final long serialVersionUID = -7065185352276153784L;
	Logger log =Logger.getLogger(TestServlet.class);
	private DeptService deptService;
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		this.deptService = (DeptService) ctx.getBean("deptService");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//组装响应数据并发送
		exractRspInvoke(req,resp);
	}
	/**
	 * 处理请求
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void exractRspInvoke(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String result = ServletRspErrorCode.MESSAGE_RSP_SUCCESS;
		String error = "";
		String token="";
		ServletMessageReq smr=null;
		String userAccount=null;//用户帐号
		JSONObject body=null;
		//程序完成标志
		boolean endFlag=false;
		//html5回调参数
		String callbackName = null;		
		callbackName = req.getParameter("jsoncallback");
		String str = req.getParameter("str");
		String reqStr = "";
		//用户对象
		UserVO user=null;
		Map<String,Object> resultMap=null;
		try{
			//客户端请求JSON串
			if(null==str){
				 reqStr = ServletUtil.praseRequst(req);
			}else{
				reqStr  = str;
			}
			log.info("reqStr:"+reqStr);
			smr = new ServletMessageReq(reqStr);
			body=smr.getBody();
			token=smr.getToken();
		}catch (Exception e) {
			e.printStackTrace();
			result = ServletRspErrorCode.MESSAGE_FORMAT_ERROR_CODE;
			error = e.getMessage();
			endFlag=true;
		}
		if(!endFlag){
			try {
				resultMap=new HashMap<String,Object>();
				JDBC jdbc = ApplicationContextTool.getJdbc();
				deptService.testTransaction(jdbc);
			} catch (Exception e) {
				e.printStackTrace();
				result = ServletRspErrorCode.MESSAGE_SERVICE_RESPONSE_ERROR_CODE;
				error = e.getMessage();
			}
		}
		//消息响应
		resultMap.put("result", result);
		resultMap.put("reason", error);
		log.info("发送给客户端的参数:"+resultMap.toString());
		//html5回调
		if(null!=callbackName&&!"".equals(callbackName)){
			
			String renderStr = callbackName+"("+JSONObject.fromObject(resultMap).toString()+")"; 			
			ServletUtil.sendResponse(resp,renderStr);
		}else{
			ServletUtil.sendResponse(resp, JSONObject.fromObject(resultMap).toString());
		}
	}
}
