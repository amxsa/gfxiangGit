package com.gf.ims.pay.service;

import com.gf.ims.pay.module.AliPayAppInfo;

/**
 * 阿里支付商家信息service
 * @author linsp
 * @date 2016-05-26
 */
public interface AliPayAppInfoService {

	/**
	 * 根据appid获取
	 *@param appid
	 *@return
	 */
	public AliPayAppInfo getByAppid(String appid);	
}
