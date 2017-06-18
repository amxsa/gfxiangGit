package com.gf.ims.xcx.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gf.ims.common.util.ServletMessageReq;
import com.gf.ims.common.util.ServletUtil;
import com.gf.ims.service.UserService;
import com.gf.ims.web.queryBean.UserQueryBean;
import com.gf.imsCommon.ims.module.User;

import net.sf.json.JSONObject;

public class TestServlet extends HttpServlet{
	
	private static final long serialVersionUID = -6609873050420278015L;
	Logger log =Logger.getLogger(TestServlet.class);
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		userService=(UserService)ctx.getBean("userService");
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private synchronized void exractRspInvoke(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String result = "";
		String reason = "";
		//程序完成标志
		boolean endFlag=false;
		//用户对象
		JSONObject body=null;
		String name = "";
		List<User> list=null;
		//html5回调参数
		String callbackName = null;		
		callbackName = req.getParameter("jsoncallback");
		String str = req.getParameter("str");
		String reqStr = "";
		try{
			//客户端请求JSON串
			if(null==str){
				 reqStr = ServletUtil.praseRequst(req);
			}else{
				reqStr  = java.net.URLDecoder.decode(str, "UTF-8");
			}
			log.info("reqStr:"+reqStr);
			ServletMessageReq smr = new ServletMessageReq(reqStr);
			body = smr.getBody();
		}catch (Exception e) {
			e.printStackTrace();
			endFlag=true;
		}
		
		if(!endFlag){
			try {
				list=userService.findUserList(new UserQueryBean()).getContent();
				name=body.getString("name");
				log.info("----------------------"+name);
			} catch (Exception e) {
				e.printStackTrace();
				reason = e.getMessage();
			}
			
		}
		//消息响应
		Map resultMap=new HashMap<String,String>();
		resultMap.put("result", result);
		resultMap.put("reason", reason);
		resultMap.put("list", list);
		log.info("respStr:"+JSONObject.fromObject(resultMap).toString());
		//html5回调
		if(null!=callbackName&&!"".equals(callbackName)){
			String renderStr = callbackName+"("+JSONObject.fromObject(resultMap).toString()+")"; 			
			ServletUtil.sendResponse(resp,renderStr);
		}else{
			ServletUtil.sendResponse(resp, JSONObject.fromObject(resultMap).toString());
		}
	}
	
}
