package com.gf.ims.pay.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gf.ims.common.util.ServletMessageReq;
import com.gf.ims.common.util.ServletUtil;
import com.gf.ims.pay.bean.PayConfig;
import com.gf.ims.pay.proccesser.PayProcesserFactory;
import com.gf.ims.pay.util.MypaySubmit;
import com.gf.ims.service.OrdersService;
import com.gf.imsCommon.ims.module.Orders;

import net.sf.json.JSONObject;
/**
 * 获取微信支付预约单信息接口
 *
 */
public class GetWxPrepayidRequestServlet extends HttpServlet{
	private static final long serialVersionUID = -6609873050420278015L;
	Logger log =Logger.getLogger(GetWxPrepayidRequestServlet.class);
	private PayConfig payConfig; 
	private OrdersService ordersService;
	private PayProcesserFactory payProcesserFactory;
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		this.payConfig=(PayConfig) ctx.getBean("payConfig");
		this.ordersService=(OrdersService) ctx.getBean("ordersService");
		this.payProcesserFactory=(PayProcesserFactory) ctx.getBean("payProcesserFactory");
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//组装响应数据并发送
		exractRspInvoke(req,resp);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exractRspInvoke(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String result ="";
		String reason = "";
		//程序完成标志
		boolean endFlag=false;
		String token = "";
		PayRequest payRequest=null;//封装了请求参数
		String appid="";//参数-微信支付的APP的appid
		String reqStr = "";
		//html5回调参数
		String callbackName = null;		
		callbackName = req.getParameter("jsoncallback");
		String str = req.getParameter("str");
		Map<String,String> resultObjMap=new HashMap<String, String>();
		try{
			//客户端请求JSON串
			if(null==str){
				 reqStr = ServletUtil.praseRequst(req);
			}else{
				reqStr  = java.net.URLDecoder.decode(str, "UTF-8");
			}
			log.info("reqStr:"+reqStr);
			ServletMessageReq smr = new ServletMessageReq(reqStr);
			payRequest=PayRequest.parseRequest(smr.getBody());
			appid=(String) smr.getBody().get("appid");
			if(StringUtils.isBlank(appid)){
				throw new Exception("appid must not be null");
			}
			token=smr.getToken();
			
		}catch (Exception e) {
			e.printStackTrace();
			reason = e.getMessage();
			endFlag=true;
		}
		if(!endFlag){
			try {
				//从token获取用户账号
//				String account =ServletMessageCommon.getAccountToken(token);
//				//account="zhaoxin";
//				if (StringUtils.isNotBlank(account)) {
//					userVO=this.iUserService.findByUserAccount(account);
//					if (userVO == null) {
//						throw new Exception("Token("+token+")已经过期");
//					}
//				} else {
//					throw new Exception("Token("+token+")已经过期");
//				}
			} catch (Exception e) {
				e.printStackTrace();
				//令牌对应账号为空
//				result = ServletRspErrorCode.MESSAGE_TOKEN_VALIDATE_ERROR_CODE;
//				reason = ServletRspErrorCode.MESSAGE_TOKEN_VALIDATE_ERROR_CONTENT;
				endFlag=true;
			}
		}
		
		if(!endFlag){
			try {
				
				Map<String, String> myParams = new HashMap<String, String>();// 构造返回给合作方同步页面return_url的参数
				Orders orders= this.ordersService.getByOrderNo(payRequest.getOrderNo());//获取订单信息
				
				validAndUpdateOrders(orders,payRequest);//验证并更新订单状态
				
				putBaseInfo(myParams,appid,payRequest);//获取保存支付所需要的基本信息
				
				putOrderInfo(myParams,orders);//获取保存支付所需要的订单信息
				
				JSONObject uppRspJson = sendToHoriUpp(myParams);//发送请求至upp支付处理中心，处理请求				

				//先判断结果码是否正确
				String return_code=(String) uppRspJson.get("return_code");
				String return_msg=(String) uppRspJson.get("return_msg");
				if("SUCCESS".equals(return_code)){
					//返回给客户端调起支付的参数列表
					resultObjMap.put("appid", (String)uppRspJson.get("appid"));
					resultObjMap.put("noncestr", (String)uppRspJson.get("noncestr"));
					resultObjMap.put("package", (String)uppRspJson.get("package"));
					resultObjMap.put("partnerid", (String)uppRspJson.get("partnerid"));
					resultObjMap.put("prepayid", (String)uppRspJson.get("prepayid"));
					resultObjMap.put("timestamp", (String)uppRspJson.get("timestamp"));
					resultObjMap.put("sign", (String)uppRspJson.get("sign"));
				}else{
					reason = "调用upp获取微信预付单异常，异常信息-->"+return_msg;
				}
			} catch (Exception e) {//服务器异常
				e.printStackTrace();
				reason = e.getMessage();
			}
		}
		
		//消息响应
		Map resultMap=new HashMap<String,String>();
		resultMap.put("result", result);
		resultMap.put("reason", reason);
		resultMap.put("obj", resultObjMap);
		String rspString=JSONObject.fromObject(resultMap).toString();
		log.info("获取微信支付预约单信息接口响应："+rspString);
		if(null!=callbackName&&!"".equals(callbackName)){
			String renderStr = callbackName+"("+JSONObject.fromObject(resultMap).toString()+")"; 			
			ServletUtil.sendResponse(resp,renderStr);
		}else {
			ServletUtil.sendResponse(resp, rspString);
		}
	}
	
	
	/***
	 * 发送请求至upp支付处理中心，处理请求
	 * @param myParams
	 * @return
	 * @throws Exception
	 */
	private JSONObject sendToHoriUpp(Map<String, String> myParams) throws Exception {
		String uppRsp = MypaySubmit.sendPostInfo(myParams, payConfig.getPayGetWxPrepayidUrl(), payConfig.getSecretKey());
		log.info("调用upp获取微信快捷支付预付单信息响应："+uppRsp);
		JSONObject uppRspJson=JSONObject.fromObject(uppRsp);
		return uppRspJson;
	}
	/**
	 * 验证 订单状态
	 * @param totalOrder
	 * @param orders
	 * @param payRequest
	 * @throws Exception
	 */
	private void validAndUpdateOrders(Orders orders,PayRequest payRequest) throws Exception {
		if(orders ==null ){
			throw new Exception("订单不存在或已经取消");
		}
		if(orders !=null){
			//判断订单是否可以支付
			int payValid=this.payProcesserFactory.checkOrderPayValid(orders);
			log.info("判断订单是否可以支付<|>orderNo<|>结果<|>"+payValid);
			if (orders.getStatus()==6) {//订单关闭
				throw new Exception("订单已经取消或关闭");
			}
			if(payValid==2){
				throw new Exception("订单不存在或已经取消");
			}else if(payValid==3){
				throw new Exception("订单未在规定时间支付，订单失效");
			}else if(payValid==4){
				throw new Exception("订单已经支付,不能重复支付");
			}		
			//更新订单表的支付方式字段和支付渠道字段
			orders.setPayMethod(payRequest.getPayMethod());
			orders.setChannelFlag(payRequest.getChannel_flag());
			this.ordersService.update(orders);			
		}

	}
	/**
	 * 设置基本信息
	 * @param myParams
	 * @param appid
	 * @param payRequest
	 */
	private void putBaseInfo(Map<String, String> myParams, String appid,PayRequest payRequest) {
		//appid	
		myParams.put("appid", appid);					
		// 支付方式
		myParams.put("pay_method", payRequest.getPayMethod() + "");
		//支付渠道来源
		myParams.put("channel_flag", payRequest.getChannel_flag()+"");
		// return_url
		myParams.put("return_url", payConfig.getReturnUrl());
		// notify_url
		myParams.put("notify_url", payConfig.getNotifyUrl());
		// 合作方编号
		myParams.put("partner_no", payConfig.getPartnerNo());
	}
	
	/**
	 * 设置订单信息
	 * @param myParams
	 * @param orders
	 * @param totalOrder
	 */
	private void putOrderInfo(Map<String, String> myParams, Orders orders) {
		// 订单总额
		//由于增加了余额消费的方式，所以第三方支付的金额的获取规则有修改
		double onlinePay=0d;		
		if(orders!=null){
			// 订单号
			myParams.put("out_trade_no", orders.getOrderNo());
			// 商品名称
			myParams.put("subject", orders.getOrderInfo());
			// 商品描述
			myParams.put("body", orders.getOrderInfo());		
			onlinePay=orders.getTotalPrice();
		}
		myParams.put("total_fee", onlinePay + "");
	}	
}
