package com.gf.ims.pay.service;

import com.gf.ims.pay.module.TradeOrder;
//import com.jlit.upp.web.queryBean.TradeOrderQueryBean;

public interface TradeOrderService {
	/**
	 * 保存
	 * @param tradeOrder
	 */
	void save(TradeOrder tradeOrder);
	/**
	 * 根据系统订单号查找
	 * @param order_no
	 * @return
	 */
	TradeOrder findByOrder_no(String order_no);
	
	/**
	 * 生成系统唯一的订单号
	 * 当前时间精确到毫秒格式+三位随机数
	 * @return
	 */
	String createOrder_no();
	/**
	 * 更新
	 * @param tradeOrder
	 */
	void update(TradeOrder tradeOrder);
	/**
	 * 根据 买家第三方支付账号查找订单（理论上唯一或空）
	 * @param trade_no
	 * @return
	 */
	TradeOrder findByTrade_no(String trade_no);
	/**
	 * 分页条件查询
	 * @param qb
	 * @return
	 */
//	Page list(TradeOrderQueryBean qb,int pageSize);
	/**
	 * 
	 * @param id
	 * @return
	 */
	TradeOrder getById(String id);
	/**
	 * 根据合作伙伴编号和具体订单号和支付宝流水号查找
	 * @param out_trade_no
	 * @param partner_no
	 * @param trade_no
	 * @return
	 */
	TradeOrder findByOut_trade_noAndParter_noAndTrade_no(String out_trade_no,String partner_no,String trade_no);
	/**
	 * 根据条件删除微信支付类型的订单
	 *@author laizs
	 *@param out_trade_no
	 *@param pay_method
	 *@param appid
	 */
	int delWxPayOrder(String out_trade_no,Integer pay_method,String appid,Integer trade_status );
	/**
	 * 根据条件查询微信支付类型的订单
	 *@author laizs
	 *@param out_trade_no
	 *@param pay_method
	 *@param appid
	 *@param orderby
	 *@return
	 */
	TradeOrder GetWxPayOrderByCondition(String out_trade_no,Integer pay_method,String appid,String orderby);
	

}
