package com.gf.ims.pay.service;

import com.gf.ims.pay.module.TradePartner;

public interface TradePartnerService {
	/**
	 * 生成合作伙伴密钥
	 * @return
	 */
	String createSecrect_key();
	/**
	 * 
	 */
	void save(TradePartner tradePartner);
	/**
	 * 根据partner_no查询
	 * @param partner_no
	 * @return
	 */
	TradePartner findByPartner_no(String partner_no,int status);
	/**
	 *  根据partner_no查询
	 * @param partner_no
	 * @return
	 */
	TradePartner findByPartner_no(String partner_no);
	/**
	 * 分页条件查询
	 * @param qb
	 * @param pageSize
	 * @return
	 */
//	Page list(TradePartnerQueryBean qb,int pageSize);
	/**
	 * 
	 * @param id
	 * @return
	 */
	TradePartner getById(String id);
	/**
	 * 
	 * @param tradePartner
	 */
	void update(TradePartner tradePartner);
}
