package com.gf.ims.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gf.imsCommon.ims.module.Orders;

/**
 * 订单service
 */
public interface OrdersService {
	/**
	 * 新增订单
	 * @param order
	 */
	void saveOrder(Orders orders);
	/**
	 * 更新订单
	 * @param order
	 */
	void update(Orders orders);
	/**
	 * 根据订单流水号获取
	 * @param orderNo
	 * @return
	 */
	Orders getByOrderNo(String orderNo);
	/**
	 * 删除订单
	 * @param orders
	 */
	void delete(Orders orders);
	/**
	 * 分页查询
	 * @param queryBean
	 * @return
	 */
//	public Page searchOrders(OrdersQueryBean queryBean);
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	Orders getById(String id);
	
	Orders updateOrderByPaySucess(Orders orders,String tradeNo,String out_trade_no,String buyer_email,
			HttpServletRequest request,HttpServletResponse response);
	
}
