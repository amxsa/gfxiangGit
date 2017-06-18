package com.gf.ims.pay.proccesser;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gf.ims.common.enums.OrderStatusForGoods;
import com.gf.imsCommon.ims.module.Orders;
//import com.trisun.message.sms.SmsSender;

/**
 * 订单支付成功后处理器工厂
 */
public class PayProcesserFactory implements IPayProcesser{
	private final static Logger logger=Logger.getLogger(PayProcesserFactory.class);
	
	private Map<String, IPayProcesser> processerMap;
	

	public Map<String, IPayProcesser> getProcesserMap() {
		return processerMap;
	}

	public void setProcesserMap(Map<String, IPayProcesser> processerMap) {
		this.processerMap = processerMap;
	}

	public void payAfterProcess(Orders orders, HttpServletRequest request,
			HttpServletResponse response) {
		String typeStr=String.valueOf(orders.getType()) ;
		logger.info("支付后订单其他业务逻辑处理<|>orderNo<|>"+orders.getOrderNo()+"<|>type<|>"+orders.getType());
//		IPayProcesser iPayProcesser=processerMap.get(typeStr);
//		if(null!=iPayProcesser){
//			iPayProcesser.payAfterProcess(orders, request, response);
//		}else{
//			logger.warn("支付后订单业务逻辑处理<|>找不到业务逻辑处理器");
//		}
	}

	public int checkOrderPayValid(Orders order) {
		if(order==null){
			return 2;//订单不存在
		}
		String typeStr=String.valueOf(order.getType()) ;
		IPayProcesser iPayProcesser=processerMap.get(typeStr);
		if(null!=iPayProcesser){
			return iPayProcesser.checkOrderPayValid(order);
		}else{
			logger.warn("判断订单是否可支付逻辑处理<|>找不到业务逻辑处理器");
			return 0;
		}
	}
	
	
	public void paySuccessPage(Orders orders, HttpServletRequest request,
			HttpServletResponse response) {
		String typeStr=String.valueOf(orders.getType()) ;
		logger.info("订单支付成功之后显示的页面逻辑处理<|>orderNo<|>"+orders.getOrderNo()+"<|>type<|>"+orders.getType());
		IPayProcesser iPayProcesser=processerMap.get(typeStr);
		if(null!=iPayProcesser){
			iPayProcesser.paySuccessPage(orders, request, response);
		}else{
			logger.warn("订单支付成功之后显示的页面逻辑处理<|>找不到业务逻辑处理器");
		}
		
	}

	public void payTimeOutProcess(Orders orders) {
		//只有订单是“未付款”状态的订单才做超时处理
		if(OrderStatusForGoods.SUBMIT.getValue()==orders.getStatus()){
			String typeStr=String.valueOf(orders.getType()) ;
			logger.info("订单支付超时逻辑处理<|>orderNo<|>"+orders.getOrderNo()+"<|>type<|>"+orders.getType());
			IPayProcesser iPayProcesser=processerMap.get(typeStr);
			if(null!=iPayProcesser){
				iPayProcesser.payTimeOutProcess(orders);
			}else{
				logger.warn("订单支付超时逻辑处理<|>找不到业务逻辑处理器");
			}
		}
	}
	

}
