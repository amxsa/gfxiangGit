package com.gf.ims.pay.service;

import java.util.List;
import java.util.Map;

import com.gf.ims.pay.module.TradeRefund;
import com.gf.ims.pay.module.TradeRefundApply;

public interface TradeRefundService {
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	TradeRefund getById(String id);
	/**
	 * 保存
	 * @param tradeRefund
	 */
	void save(TradeRefund tradeRefund);
	/**
	 * 根据退款批次号获取
	 * @param batch_no
	 * @return
	 */
	TradeRefund getByBatch_no(String batch_no);
	/**
	 * 更新
	 * @param tradeRefund
	 */
	void update(TradeRefund tradeRefund);
	/**
	 * 创建退款批次单
	 * @param applyIds 退款申请id
	 * @param oprator_account 
	 */
	TradeRefund createTradeRefund(List<String> applyIds,String oprator_account);
	/**
	 * 退款完成后
	 * @param batch_no
	 * @param success_num
	 * @param result_details
	 */
	void updateTradeRefund(String batch_no,Integer success_num,String result_details );
	/**
	 * 分页查询
	 * @param qb
	 * @param pageSize
	 * @return
	 */
//	Page list(TradeRefundQueryBean qb,int pageSize);
	/**
	 * 退款结束后，通知合作方
	 * @param tradeRefundApply
	 * @return
	 */
	void refundNotifyClient(TradeRefundApply tradeRefundApply);
	
	/**
	 * 微信退款成功后逻辑
	 * @param batch_no
	 * @param rsp
	 */
	void updateWxTradeRefund(String batch_no,Map<String,String> wxRspMap);
	
	
}
