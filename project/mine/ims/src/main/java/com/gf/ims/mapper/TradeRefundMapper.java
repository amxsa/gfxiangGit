package com.gf.ims.mapper;

import java.util.List;

import com.gf.ims.pay.module.TradeRefund;

public interface TradeRefundMapper {

	TradeRefund get(String id);

	List<TradeRefund> find(String hql, String batch_no);

	void save(TradeRefund tradeRefund);

	void update(TradeRefund tradeRefund);

}
