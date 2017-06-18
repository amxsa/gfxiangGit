package com.gf.ims.pay.servlet;
import java.io.IOException;
import java.util.Date;
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

import com.gf.ims.common.enums.OrderPayStatus;
import com.gf.ims.common.util.ServletUtil;
import com.gf.ims.pay.bean.PayConfig;
import com.gf.ims.pay.proccesser.PayProcesserFactory;
import com.gf.ims.pay.util.MyPayUtil;
import com.gf.ims.service.OrdersService;
import com.gf.imsCommon.ims.module.Orders;
/**
 * 支付结果页面同步通知处理servlet
 */
public class PayReturnUrlServlet extends HttpServlet{
	
	private static final long serialVersionUID = -6609873050420278015L;
	Logger log =Logger.getLogger(PayReturnUrlServlet.class);
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
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void exractRspInvoke(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 获取支付请求GET过来反馈信息
		Map<String, String> params = MyPayUtil.getParams(request);
		boolean verifySignResult = verifySign(params);
		log.info("------------------------------------------支付结果同步通知开始-------------------------------------------------");
		log.info("支付结果同步通知微信返回参数------------"+params.toString());
		log.info("支付结果同步通知参数验证结果------------"+verifySignResult);
		if(verifySignResult){
			//订单号
			String orderNo = params.get("out_trade_no");
			//交易结果
			String trade_status = params.get("trade_status");
			//支付平台 支付流水号
			String tradeNo=params.get("trade_no");
			//第三方支付平台交易流水号
			String out_trade_no=params.get("pay_trade_no");
			if (StringUtils.isNotBlank(trade_status)&& "TRADE_SUCCESS".equals(trade_status)) {
				log.info("支付结果同步通知<|>订单（" + orderNo + "）支付成功-------------开始更新订单");
				Orders orders=this.ordersService.getByOrderNo(orderNo);
				if(orders.getPayStatus()==OrderPayStatus.NO_PAY.getValue()){
					//更新订单状态数据
					this.ordersService.updateOrderByPaySucess(orders, tradeNo, out_trade_no, null, request, response);
					log.info("支付结果同步通知<|>订单（" + orderNo + "）支付后业务处理成功");
				}else{
					//如果订单状态已经是已支付状态，表示订单支付完成已经处理过订单，不能重复处理订单
					log.info("支付结果同步通知<|>订单（" + orderNo + "）已经成功处理，不能重复处理");
				}
				//跳转至支付成功页面
				this.payProcesserFactory.paySuccessPage(orders, request, response);
				
			} else {
				log.warn("支付结果同步通知<|>订单（" + orderNo + "）支付失败，交易结果码："+trade_status);
			}
		}else{
			log.warn("请求签名参数验证不正确");
			ServletUtil.sendResponse(response, "请求签名参数验证不正确");
			return;
		}
		log.info("------------------------------------------支付结果同步通知结束-------------------------------------------------");
	}
	/**
	 * 验证签名
	 * 
	 * @param params
	 * @return
	 */
	private boolean verifySign(Map<String, String> params) {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = MyPayUtil.paramsFilter(params);
		// 生成签名结果
		String mysign = MyPayUtil.getMysign(sPara, this.payConfig.getSecretKey());
		String sign = params.get("sign");
		if (StringUtils.isNotBlank(sign) && sign.equals(mysign)) {
			return true;
		}
		return false;
	}
}
