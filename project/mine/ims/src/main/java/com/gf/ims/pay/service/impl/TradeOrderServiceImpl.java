package com.gf.ims.pay.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.Env;
import com.gf.ims.common.util.NumericUtil;
import com.gf.ims.mapper.TradeOrderMapper;
import com.gf.ims.pay.module.TradeOrder;
import com.gf.ims.pay.service.TradeOrderService;
//import com.jlit.upp.web.queryBean.TradeOrderQueryBean;
@Service("tradeOrderService")
public class TradeOrderServiceImpl implements TradeOrderService {
	@Resource(name="tradeOrderMapper")
	private TradeOrderMapper tradeOrderMapper;

	public void save(TradeOrder to) {
//		this.tradeOrderMapper.save(tradeOrder);
		StringBuffer sql=new StringBuffer();
		sql.append(" insert into trade_order(");
		sql.append("id,pay_method,order_no,order_source_flag,partner_no,order_subject,order_body,total_fee,show_url,");
		sql.append("out_trade_no,inside_order_type,return_url,notify_url,trade_no,buyer_email,seller_email,trade_status,");
		sql.append("check_status,checker_account,create_time,trade_time,check_time,is_refunded,refund_times,left_fee,");
		sql.append("channel_flag,paymethod,defaultbank,appid)");
		sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{
				Env.getUUID(),to.getPayMethod(),to.getOrderNo(),to.getOrderSourceFlag(),to.getPartnerNo(),to.getOrderSubject(),
				to.getOrderBody(),to.getTotalFee(),to.getShowUrl(),to.getOutTradeNo(),to.getInsideOrderType(),to.getReturnUrl(),
				to.getNotifyUrl(),to.getTradeNo(),to.getBuyerEmail(),to.getSellerEmail(),to.getTradeStatus(),to.getCheckStatus(),to.getCheckerAccount(),
				to.getCreateTime(),to.getTradeTime(),to.getCheckTime(),to.getIsRefunded(),to.getRefundTimes(),to.getLeftFee(),
				to.getChannelFlag(),to.getPaymethod(),to.getDefaultbank(),to.getAppid()
		};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
	}

	
	public TradeOrder findByOrder_no(String order_no) {
		String sql="select * from trade_order t where t.order_no=?";
		JDBC jdbc = Env.jdbc;
		try {
			TradeOrder to = jdbc.queryForObject(Env.DS, sql, TradeOrder.class, new Object[]{order_no});
			return to;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return null;
	}

	
	public String createOrder_no() {
		DateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return df.format(new Date())+NumericUtil.genenateThree();
	}

	
	public void update(TradeOrder to) {
//		this.tradeOrderMapper.update(tradeOrder);
		StringBuffer sql=new StringBuffer();
		sql.append(" update trade_order set ");
		sql.append(" pay_method=?,order_no=?,order_source_flag=?,partner_no=?,order_subject=?,order_body=?,total_fee=?,show_url=?, ");
		sql.append(" out_trade_no=?,inside_order_type=?,return_url=?,notify_url=?,trade_no=?,buyer_email=?,seller_email=?,trade_status=?, ");
		sql.append(" check_status=?,checker_account=?,create_time=?,trade_time=?,check_time=?,is_refunded=?,refund_times=?,left_fee=?, ");
		sql.append(" channel_flag=?,paymethod=?,defaultbank=?,appid=? ");
		sql.append(" where id=?  ");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{
				to.getPayMethod(),to.getOrderNo(),to.getOrderSourceFlag(),to.getPartnerNo(),to.getOrderSubject(),
				to.getOrderBody(),to.getTotalFee(),to.getShowUrl(),to.getOutTradeNo(),to.getInsideOrderType(),to.getReturnUrl(),
				to.getNotifyUrl(),to.getTradeNo(),to.getBuyerEmail(),to.getSellerEmail(),to.getTradeStatus(),to.getCheckStatus(),to.getCheckerAccount(),
				to.getCreateTime(),to.getTradeTime(),to.getCheckTime(),to.getIsRefunded(),to.getRefundTimes(),to.getLeftFee(),
				to.getChannelFlag(),to.getPaymethod(),to.getDefaultbank(),to.getAppid(),to.getId()
		};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
	}

	
	public TradeOrder findByTrade_no(String trade_no) {
		String hql="from TradeOrder t where t.trade_no=?";
		List<TradeOrder> list=this.tradeOrderMapper.find(hql, trade_no);
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

//	
//	public Page list(TradeOrderQueryBean qb,int pageSize) {
//		return this.tradeOrderMapper.searchTradeOrder(qb, pageSize);
//	}

	
	public TradeOrder getById(String id) {
//		return this.tradeOrderMapper.get(id);
		JDBC jdbc = Env.jdbc;
		try {
			return jdbc.queryForObject(Env.DS, "select * from trade_order where id=? ", TradeOrder.class, new Object[]{id});
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return null;
	}

	
	public TradeOrder findByOut_trade_noAndParter_noAndTrade_no(String out_trade_no,String partner_no,String trade_no) {
		String sql="select * from trade_order t where t.out_trade_no=? and t.partner_no=? and t.trade_no=? ";
		JDBC jdbc = Env.jdbc;
		try {
			return jdbc.queryForObject(Env.DS, sql, TradeOrder.class, new Object[]{out_trade_no,partner_no,trade_no});
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return null;
	}

	
	public int delWxPayOrder(String out_trade_no,Integer pay_method,String appid,Integer trade_status) {
		String sql="delete from trade_order t where t.out_trade_no=? and t.pay_method=? and t.appid=? and t.trade_status=?";
		Object[] parameters=new Object[]{out_trade_no,pay_method,appid,trade_status};
		JDBC jdbc = Env.jdbc;
		try {
			return jdbc.update(Env.DS, sql, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return 0;
	}
	
	public TradeOrder GetWxPayOrderByCondition(String out_trade_no,Integer pay_method,String appid,String orderby) {
		StringBuffer sql=new StringBuffer("select * from trade_order t where t.trade_no is not null and t.out_trade_no='"+out_trade_no+"' and t.pay_method="+pay_method+" and t.appid='"+appid+"' ");
		JDBC jdbc = Env.jdbc;
		try {
			return jdbc.queryForObject(Env.DS, sql.toString(),TradeOrder.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbc.closeAll();
		}
		return null;
	}
	
}
