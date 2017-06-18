package com.gf.ims.pay.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gf.ims.mapper.TradeRefundApplyMapper;
import com.gf.ims.pay.module.TradeRefundApply;
import com.gf.ims.pay.service.TradeRefundApplyService;

@Service("tradeRefundApplyService")
public class TradeRefundApplyServiceImpl implements TradeRefundApplyService{
	@Resource(name="tradeRefundApplyMapper")
	private  TradeRefundApplyMapper tradeRefundApplyMapper;

	
	public TradeRefundApply getById(String id) {
		return this.tradeRefundApplyMapper.get(id);
	}

	
	public void save(TradeRefundApply tradeRefundApply) {
		this.tradeRefundApplyMapper.save(tradeRefundApply);
		
	}

	
	public void update(TradeRefundApply tradeRefundApply) {
		this.tradeRefundApplyMapper.update(tradeRefundApply);
		
	}

	
//	public Page list(TradeRefundApplyQueryBean qb, int pageSize) {
//		return this.tradeRefundApplyMapper.searchRefundApply(qb, pageSize);
//	}

	
//	public Page fuzzySerach(String key, Integer refund_status, String orderby,
//			int pageNum, int pageSize) {
//		return this.tradeRefundApplyMapper.fuzzySerach(key, refund_status, orderby, pageNum, pageSize);
//	}

	
	public TradeRefundApply findByTrade_noAndBatch_no(String trade_no,
			String batch_no) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from TradeRefundApply t where 1=1 and t.trade_no=? and t.batch_no=?");
		List list=this.tradeRefundApplyMapper.find(hql.toString(), trade_no,batch_no);
		if(null!=list && list.size()>0){
			return (TradeRefundApply) list.get(0);
		}
		return null;
	}

	
	public long countByTrade_status(Integer refund_status) {
//		String hql="select count(*) from TradeRefundApply t where t.refund_status =?";
//		List<Long> countlist = tradeRefundApplyMapper.getHibernateTemplate().find(hql, refund_status);
//		return countlist.get(0);
		return 0;
	}

	
	public boolean isRefund_apply_noRepeat(String refund_apply_no,
			String partner_no) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from TradeRefundApply t where 1=1 and t.refund_apply_no=? and t.partner_no=?");
		List list=this.tradeRefundApplyMapper.find(hql.toString(), refund_apply_no,partner_no);
		if(null!=list && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询集合
	 * @param applyIds
	 * @return
	 */
	public List<TradeRefundApply> getByIds(List<String> applyIds){
		if(applyIds!=null && applyIds.size()>0){
			return tradeRefundApplyMapper.find(applyIds);
		}
		return new ArrayList<TradeRefundApply>(0);
	}	
	
	/**
	 * 判断是否存在未处理的交易流水申请
	 * @param outTradeNo 合作方订单号
	 * @param trade_no 支付宝或者微信交易流水号
	 * @return
	 */
	public boolean isRefund_apply_tradeNo_noRepeat(String outTradeNo,String trade_no){
		StringBuffer hql = new StringBuffer();
		hql.append(" from TradeRefundApply t where 1=1 and refund_status=0 and t.out_trade_no=? and t.trade_no=?");
		List list=this.tradeRefundApplyMapper.find(hql.toString(),outTradeNo,trade_no);
		if(null!=list && list.size()>0){
			return true;
		}
		return false;		
	}

}
