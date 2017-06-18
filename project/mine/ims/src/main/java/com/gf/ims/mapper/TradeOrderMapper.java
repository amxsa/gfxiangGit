package com.gf.ims.mapper;

import java.util.List;

import com.gf.ims.pay.module.TradeOrder;

public interface TradeOrderMapper {

	void save(TradeOrder tradeOrder);

	List<TradeOrder> find(String hql, String order_no);

	void update(TradeOrder tradeOrder);

	TradeOrder get(String id);

	List<TradeOrder> find(String hql, String out_trade_no, String partner_no, String trade_no);

	int executeUpdate(String hql, Object[] parameters);

	List<TradeOrder> find(String string);

}
