package com.gf.ims.mapper;

import java.util.List;

import com.gf.ims.pay.module.TradePartner;

public interface TradePartnerMapper {

	void update(TradePartner tradePartner);

	List<TradePartner> find(String hql, String partner_no);

	TradePartner get(String id);

	List<TradePartner> find(String hql, String partner_no, int status);

	void save(TradePartner tradePartner);

}
