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

import net.sf.json.JSONObject;
/**
 * 获取订单微信支付的支付状态（客户端在微信快捷支付流程完成后调用）
 *
 */
public class GetWxPayOrderStatusServlet extends HttpServlet{
	private static final long serialVersionUID = -6609873050420278015L;
	Logger log =Logger.getLogger(GetWxPayOrderStatusServlet.class);
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
	/**
	 * 处理请求
	 *@author laizs
	 *@param req
	 *@param resp
	 *@throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exractRspInvoke(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String result = "";
		String reason = "";
		//程序完成标志
		boolean endFlag=false;
		String token = "";
		String appid="";//参数-微信支付的APP的appid
		PayRequest payRequest=null;
		Map<String,String> resultObjMap=new HashMap<String, String>();
		try{
			//客户端请求JSON串
			String reqStr = ServletUtil.praseRequst(req);
			log.info("reqStr:"+reqStr);
			ServletMessageReq smr = new ServletMessageReq(reqStr);
			appid=smr.getBody().getString("appid");
			if(StringUtils.isBlank(appid)){
				throw new Exception("appid must not be null");
			}
			payRequest=PayRequest.parseRequest(smr.getBody());
			token=smr.getToken();
		}catch (Exception e) {
			e.printStackTrace();
			reason = e.getMessage();
			endFlag=true;
		}
		if(!endFlag){
			try {
				// 构造返回给合作方同步页面return_url的参数
				Map<String, String> myParams = new HashMap<String, String>();
				// 支付方式
				myParams.put("pay_method", payRequest.getPayMethod() + "");
				// 合作方编号
				myParams.put("partner_no", payConfig.getPartnerNo());
				// 订单号
				myParams.put("out_trade_no", payRequest.getOrderNo());
				myParams.put("channel_flag", payRequest.getChannel_flag()+"");//支付渠道来源
				myParams.put("appid", appid);//appid
				String uppRsp = MypaySubmit.sendPostInfo(myParams, payConfig.getGetWxPayOrderStatusUrl(), payConfig.getSecretKey());
				log.info("调用支付中心获取微信预付单信息响应："+uppRsp);
				JSONObject uppRspJson=JSONObject.fromObject(uppRsp);
				String return_code=(String) uppRspJson.get("return_code");//
				String return_msg=(String) uppRspJson.get("return_msg");
				if("SUCCESS".equals(return_code)){
					String result_code=(String) uppRspJson.get("result_code");
					if("SUCCESS".equals(result_code)){
						//交易状态结果 
						/*
						SUCCESS—支付成功
						REFUND—转入退款
						NOTPAY—未支付
						CLOSED—已关闭
						REVOKED—已撤销（刷卡支付）
						USERPAYING--用户支付中
						PAYERROR--支付失败(其他原因，如银行返回失败)
						*/
						String trade_state=(String) uppRspJson.get("trade_state");
						resultObjMap.put("trade_state", trade_state);
					}else{
						String err_code_des=(String) uppRspJson.get("err_code_des");
						reason = "查询失败，失败信息："+err_code_des;
					}
				}else{
					reason = "查询失败，失败信息："+return_msg;
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
		log.info("获取订单微信支付的支付状态："+rspString);
		ServletUtil.sendResponse(resp, rspString);
	}
	
	
}
