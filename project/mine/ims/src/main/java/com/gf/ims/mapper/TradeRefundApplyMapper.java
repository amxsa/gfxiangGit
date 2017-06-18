package com.gf.ims.mapper;

import java.util.List;

import com.gf.ims.pay.module.TradeRefundApply;

public interface TradeRefundApplyMapper {

	TradeRefundApply get(String id);

	void save(TradeRefundApply tradeRefundApply);

	void update(TradeRefundApply tradeRefundApply);

	List find(String string, String trade_no, String batch_no);

	List<TradeRefundApply> find(List<String> applyIds);


}
