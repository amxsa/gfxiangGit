package com.gf.ims.pay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.Env;
import com.gf.ims.mapper.TradePartnerMapper;
import com.gf.ims.pay.module.TradePartner;
import com.gf.ims.pay.service.TradePartnerService;
import com.gf.ims.pay.util.AlipayMd5Encrypt;
@Service("tradePartnerService")
public class TradePartnerServiceImpl implements TradePartnerService{
	@Resource(name="tradePartnerMapper")
	private TradePartnerMapper tradePartnerMapper;
	
	public String createSecrect_key() {
		long time=System.currentTimeMillis();
		return AlipayMd5Encrypt.md5(time+"");
	}

	
	public void save(TradePartner tp) {
//		this.tradePartnerMapper.save(tradePartner);
		StringBuffer sql=new StringBuffer();
		sql.append(" insert into trade_partner(");
		sql.append(" id,partner_no,name,contactor,contact_tel,secret_key,create_time,update_time,status) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?) ");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{
				Env.getUUID(),tp.getPartnerNo(),tp.getName(),tp.getContactor(),tp.getContactor(),tp.getSecretKey(),
				tp.getCreateTime(),tp.getUpdateTime(),tp.getStatus()
		};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
	}

	
	public TradePartner findByPartner_no(String partner_no,int status) {
		String sql="select * from trade_partner t where t.partner_no = ? and t.status=? ";
		JDBC jdbc = Env.jdbc;
		try {
			TradePartner tp = jdbc.queryForObject(Env.DS, sql, TradePartner.class, new Object[]{partner_no,status});
			return tp;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return null;
	}

	
//	public Page list(TradePartnerQueryBean qb, int pageSize) {
//		return  this.tradePartnerMapper.searchPartner(qb, pageSize);
//	}

	
	public TradePartner getById(String id) {
		String sql="select * from trade_partner t where t.id=? ";
		JDBC jdbc = Env.jdbc;
		try {
			TradePartner tp = jdbc.queryForObject(Env.DS, sql, TradePartner.class, new Object[]{id});
			return tp;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return null;
	}

	
	public void update(TradePartner tp) {
//		this.tradePartnerMapper.update(tradePartner);
		StringBuffer sql=new StringBuffer();
		sql.append(" update trade_partner set ");
		sql.append(" partner_no=?,name=?,contactor=?,contact_tel=?,secret_key=?,create_time=?,update_time=?,status=? ");
		sql.append(" where id=? ");
		JDBC jdbc = Env.jdbc;
		Object[] params=new Object[]{
				tp.getPartnerNo(),tp.getName(),tp.getContactor(),tp.getContactor(),tp.getSecretKey(),
				tp.getCreateTime(),tp.getUpdateTime(),tp.getStatus(),tp.getId()
		};
		try {
			jdbc.update(Env.DS, sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
	}

	
	public TradePartner findByPartner_no(String partner_no) {
		String sql="select * from trade_partner t where t.partner_no = ? ";
		JDBC jdbc = Env.jdbc;
		try {
			TradePartner tp = jdbc.queryForObject(Env.DS, sql, TradePartner.class, new Object[]{partner_no});
			return tp;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return null;
	}

}
