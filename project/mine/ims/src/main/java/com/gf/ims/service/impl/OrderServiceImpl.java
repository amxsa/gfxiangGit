package com.gf.ims.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.enums.OrderPayStatus;
import com.gf.ims.common.enums.OrderStatusForGoods;
import com.gf.ims.common.env.Env;
import com.gf.ims.pay.proccesser.PayProcesserFactory;
import com.gf.ims.service.OrdersService;
import com.gf.imsCommon.ims.module.Orders;
@Service(value="ordersService")
public class OrderServiceImpl implements OrdersService {
	@Resource
	private PayProcesserFactory payProcesserFactory;
	
	
	public void saveOrder(Orders o) {
		StringBuffer sql=new StringBuffer();
		sql.append(" insert into orders(");
		sql.append(" id,order_no,user_id,total_price,type,pay_method,pay_status,status,order_info, ");
		sql.append(" out_trade_no,trade_no,buyer_email,create_time,pay_time,finish_time,channel_flag,is_refunded, ");
		sql.append(" pay_name,seller_id,seller_name,remarks,send_message_flag )");
		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{
				o.getId(),o.getOrderNo(),o.getOrderNo(),o.getTotalPrice(),o.getType(),o.getPayMethod(),o.getPayStatus(),o.getStatus(),
				o.getOrderInfo(),o.getOutTradeNo(),o.getTradeNo(),o.getBuyerEmail(),o.getCreateTime(),o.getPayTime(),o.getFinishTime(),
				o.getChannelFlag(),o.getIsRefunded(),o.getPayName(),o.getSellerId(),o.getSellerName(),o.getRemarks(),o.getSendMessageFlag()
		};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
	}

	public void update(Orders o) {
		StringBuffer sql=new StringBuffer();
		sql.append(" update orders set");
		sql.append(" order_no=?,user_id=?,total_price=?,type=?,pay_method=?,pay_status=?,status=?,order_info=?, ");
		sql.append(" out_trade_no=?,trade_no=?,buyer_email=?,create_time=?,pay_time=?,finish_time=?,channel_flag=?,is_refunded=?, ");
		sql.append(" pay_name=?,seller_id=?,seller_name=?,remarks=?,send_message_flag=? ");
		sql.append(" where id=? ");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{
				o.getOrderNo(),o.getOrderNo(),o.getTotalPrice(),o.getType(),o.getPayMethod(),o.getPayStatus(),o.getStatus(),
				o.getOrderInfo(),o.getOutTradeNo(),o.getTradeNo(),o.getBuyerEmail(),o.getCreateTime(),o.getPayTime(),o.getFinishTime(),
				o.getChannelFlag(),o.getIsRefunded(),o.getPayName(),o.getSellerId(),o.getSellerName(),o.getRemarks(),o.getSendMessageFlag(),o.getId()
		};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		
	}

	public Orders getByOrderNo(String orderNo) {
		StringBuffer sql=new StringBuffer("select * from orders where order_no=? ");
		JDBC jdbc = Env.jdbc;
		try {
			Orders orders = jdbc.queryForObject(Env.DS, sql.toString(),Orders.class, new Object[]{orderNo});
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return null;
	}

	public void delete(Orders orders) {
		StringBuffer sql=new StringBuffer();
		sql.append(" delete from orders where id=? ");
		JDBC jdbc = Env.jdbc;
		try {
			jdbc.update(Env.DS, sql.toString(), new Object[]{orders.getId()});
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
	}

	public Orders getById(String id) {
		StringBuffer sql=new StringBuffer("select * from orders where id=? ");
		JDBC jdbc = Env.jdbc;
		try {
			Orders orders = jdbc.queryForObject(Env.DS, sql.toString(),Orders.class, new Object[]{id});
			return orders;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return null;
	}

	@Override
	public Orders updateOrderByPaySucess(Orders orders, String tradeNo, String out_trade_no, String buyer_email,
			HttpServletRequest request, HttpServletResponse response) {
		//更新订单状态数据
		orders.setPayStatus(OrderPayStatus.HASED_PAY.getValue());
		orders.setStatus(OrderStatusForGoods.HASED_PAY.getValue());
		orders.setTradeNo(tradeNo);
		orders.setOutTradeNo(out_trade_no);
		orders.setPayTime(new Date());
		if(StringUtils.isNotBlank(buyer_email)){
			orders.setBuyerEmail(buyer_email);//买家支付账号
		}
		this.update(orders);
		//处理订单支付成功后各种不同订单的他的业务逻辑
		this.payProcesserFactory.payAfterProcess(orders, request, response);
		return orders;
	}


}
