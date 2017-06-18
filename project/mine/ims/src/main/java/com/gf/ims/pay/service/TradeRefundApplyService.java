package com.gf.ims.pay.service;

import java.util.List;

import com.gf.ims.pay.module.TradeRefundApply;

public interface TradeRefundApplyService {
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	TradeRefundApply getById(String id);
	/**
	 * 保存
	 * @param tradeRefundApply
	 */
	void save(TradeRefundApply tradeRefundApply);
	/**
	 * 更新
	 * @param tradeRefundApply
	 */
	void update(TradeRefundApply tradeRefundApply);
	/**
	 * 分页条件查询
	 * @param qb
	 * @param pageSize
	 * @return
	 */
//	Page list(TradeRefundApplyQueryBean qb,int pageSize);
	/**
	 * 模糊查询
	 * @param propertyInfoId 物业号
	 * @param key 查询关键字(申请单号、合作方编号、合作方订单号、支付宝流水号、退款批次号、商品名称、消费者账号、商家账户)
	 * @param pageSize
	 * @return
	 */
//	 Page fuzzySerach(String key,Integer refund_status,  String orderby,int pageNum,int pageSize);
	 /**
	  * 根据退款批次号和支付宝交易号查找
	  * @param trade_no
	  * @param batch_no
	  * @return
	  */
	 TradeRefundApply findByTrade_noAndBatch_no(String trade_no,String batch_no);
	 /**
	  * 判断合作伙伴的退款申请单号是否重复
	  * @param refund_apply_no
	  * @param partner_no
	  * @return
	  */
	 boolean isRefund_apply_noRepeat(String refund_apply_no,String partner_no);
	/**
	 * 统计个数
	 * @param tradeStatus
	 * @return
	 */
	long countByTrade_status(Integer refund_status);
	
	/**
	 * 查询集合
	 * @param applyIds
	 * @return
	 */
	List<TradeRefundApply> getByIds(List<String> applyIds);
	
	/**
	 * 判断是否存在未处理的交易流水申请
	 * @param outTradeNo 合作方订单号
	 * @param trade_no 支付宝或者微信交易流水号
	 * @return
	 */
	boolean isRefund_apply_tradeNo_noRepeat(String outTradeNo,String trade_no);
}
