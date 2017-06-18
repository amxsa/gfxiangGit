package com.gf.ims.pay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.Env;
import com.gf.ims.mapper.AliPayAppInfoMapper;
import com.gf.ims.pay.module.AliPayAppInfo;
import com.gf.ims.pay.service.AliPayAppInfoService;


/**
 * 阿里支付商家信息service
 */
@Service("aliPayAppInfoService")
public class AliPayAppInfoServiceImpl implements AliPayAppInfoService {

	@Resource
	private AliPayAppInfoMapper aliPayAppInfoMapper;
	
	public AliPayAppInfo getByAppid(String appid) {
		JDBC jdbc = Env.jdbc;
		try {
			AliPayAppInfo info = jdbc.queryForObject(Env.DS, "select * from ali_pay_app_info where app_id=? ", AliPayAppInfo.class, new Object[]{appid});
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.closeAll();
		}
		return null;
	}

}
