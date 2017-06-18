package com.gf.ims.servlet;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gf.ims.common.env.Env;
import com.gf.ims.common.util.NumericUtil;
import com.gf.ims.common.util.OrdersUtil;
import com.gf.ims.common.util.ServletMessageReq;
import com.gf.ims.common.util.ServletUtil;
import com.gf.ims.service.OrdersService;
import com.gf.imsCommon.ims.module.Orders;

import net.sf.json.JSONObject;
/**
 * 用户订购商品，保存商品订单接口
 */
public class SaveOrderServlet extends HttpServlet{
	
	private static final long serialVersionUID = -6609873050420278015L;
	Logger log =Logger.getLogger(SaveOrderServlet.class);
	private OrdersService ordersService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		ordersService=(OrdersService)ctx.getBean("ordersService");
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
		/**
		 * 登录账号
		 */
		String orderNo = "";//订单流水号
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
				Orders o=new Orders();
				o.setId(Env.getUUID());
				o.setCreateTime(new Date());
				o.setOrderInfo("测试商品"+NumericUtil.genenateThree());
				o.setOrderNo(OrdersUtil.generateOrderNo());
				o.setPayStatus(0);
				o.setRemarks("哈哈哈，尽快送货哦");
				o.setSellerId(Env.getUUID());
				o.setType(1);
				o.setUserId(Env.getUUID());
				o.setStatus(0);
				o.setSellerId(Env.getUUID());
				o.setSellerName("无名店铺");
				o.setTotalPrice(0.01);
				ordersService.saveOrder(o);
				orderNo=o.getOrderNo();
			} catch (Exception e) {
				e.printStackTrace();
				reason = e.getMessage();
			}
			
		}
		//消息响应
		Map resultMap=new HashMap<String,String>();
		resultMap.put("result", result);
		resultMap.put("reason", reason);
		resultMap.put("orderNo", orderNo);
		//html5回调
		if(null!=callbackName&&!"".equals(callbackName)){
			String renderStr = callbackName+"("+JSONObject.fromObject(resultMap).toString()+")"; 			
			ServletUtil.sendResponse(resp,renderStr);
		}else{
			ServletUtil.sendResponse(resp, JSONObject.fromObject(resultMap).toString());
		}
	}
	
}
