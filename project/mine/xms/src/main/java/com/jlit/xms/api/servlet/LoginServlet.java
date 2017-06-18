package com.jlit.xms.api.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jlit.model.User;
import com.jlit.xms.api.message.ServletMessageReq;
import com.jlit.xms.common.spring.ApplicationContextTool;
import com.jlit.xms.enums.ServletRspErrorCode;
import com.jlit.xms.service.DeptService;
import com.jlit.xms.util.ServletUtil;

import common.DB.JDBC;
import net.sf.json.JSONObject;

@WebServlet(name="login",urlPatterns={"userLogin"})
public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = -7065185352276153784L;
	Logger log =Logger.getLogger(LoginServlet.class);
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
		ServletMessageReq smr=null;
		JSONObject body=null;
		//程序完成标志
		boolean endFlag=false;
		//html5回调参数
		String callbackName = null;		
		callbackName = req.getParameter("jsoncallback");
		String str = req.getParameter("str");
		String reqStr = "";
		String userAccount="";
		String pwd="";
		//用户对象
		User user=null;
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
		}catch (Exception e) {
			e.printStackTrace();
			error = e.getMessage();
			endFlag=true;
		}
		if(!endFlag){
			try {
				try {
					userAccount=body.getString("userAccount");
					if (StringUtils.isBlank(userAccount)) {
						throw new RuntimeException("账户不可为空");
					}
				} catch (Exception e) {
					result="0";
					e.printStackTrace();
				}
				try {
					pwd=body.getString("pwd");
					if (StringUtils.isBlank(pwd)) {
						throw new RuntimeException("密码不可为空");
					}
				} catch (Exception e) {
					result="0";
					e.printStackTrace();
				}
				pwd=body.getString("pwd");
				resultMap=new HashMap<String,Object>();
				JDBC jdbc = ApplicationContextTool.getJdbc();
				String sql="select * from user where user_account=? and password=? ";
				user = jdbc.queryForObject(ApplicationContextTool.getDataSource(), sql, User.class, new Object[]{userAccount,pwd});
				if (user!=null) {
					result="1";
				}else{
					result="0";
					error="账户或密码错误";
				}
			} catch (Exception e) {
				e.printStackTrace();
				error = e.getMessage();
			}
		}
		//消息响应
		resultMap.put("result", result);
		resultMap.put("reason", error);
		resultMap.put("user", user);
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
