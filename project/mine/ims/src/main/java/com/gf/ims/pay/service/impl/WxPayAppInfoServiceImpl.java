package com.gf.ims.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gf.ims.common.db.jdbc.JDBC;
import com.gf.ims.common.env.Env;
import com.gf.ims.mapper.WxPayAppInfoMapper;
import com.gf.ims.pay.module.WxPayAppInfo;
import com.gf.ims.pay.service.WxPayAppInfoService;
/**
 * 微信支付app信息service实现类
 *
 */
@Service("wxPayAppInfoService")
public class WxPayAppInfoServiceImpl implements WxPayAppInfoService {
	@Resource(name="wxPayAppInfoMapper")
	private WxPayAppInfoMapper wxPayAppInfoMapper;
	public WxPayAppInfo getByAppid(String appid) {
		if(StringUtils.isNotBlank(appid)){
			String sql="select * from wx_pay_app_info  where appid = ? ";
			JDBC jdbc = Env.jdbc;
			try {
				WxPayAppInfo wp = jdbc.queryForObject(Env.DS, sql, WxPayAppInfo.class, new Object[]{appid});
				return wp;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				jdbc.closeAll();
			}
		}
		return null;
	}

}
