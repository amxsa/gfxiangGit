package com.gf.ims.pay.proccesser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gf.imsCommon.ims.module.Orders;

/**
 * 订单支付成功后处理器接口
 * @author laizs
 * @time 2014-5-28 上午11:13:22
 * @file OrderPayAfterProcesser.java
 */
public interface IPayProcesser {
	/**
	 * 订单支付成功后订单处理器，根据具体订单的类型处理具体的业务逻辑
	 * 如：预约挂号信息订单，支付成功后需要修改预约挂号信息的状态
	 * @param orders
	 * @param request
	 * @param response
	 */
	void payAfterProcess(Orders orders,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 判断订单的支付有效性
	 * 1有效  2订单不存在 3订单未在规定时间支付 4订单已经支付
	 * @param orders 订单对象
	 * @return
	 */
	int checkOrderPayValid(Orders order);
	
	/**
	 * 判断订单的支付有效性
	 * 1有效  2订单不存在 3订单未在规定时间支付 4订单已经支付
	 * @param totalOrder 总订单对象
	 * @return
	 */
//	int checkTotalOrderPayValid(TotalOrder totalOrder);	
	/**
	 * 订单支付成功之后显示的页面
	 * @param orders
	 * @param request
	 * @param response
	 */
	void paySuccessPage(Orders orders,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 订单超时未支付的逻辑处理
	 *@author laizs
	 *@param orders
	 */
	void payTimeOutProcess(Orders orders);
}
