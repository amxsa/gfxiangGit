package com.gf.ims.pay.service;

import com.gf.ims.pay.module.WxPayAppInfo;

/**
 * 微信支付app信息service
 * @author laizs
 * @time 2015-11-4下午2:19:29
 * @file WxPayAppInfoService.java
 *
 */
public interface WxPayAppInfoService {
	/**
	 * 根据appid获取
	 *@author laizs
	 *@param appid
	 *@return
	 */
	public WxPayAppInfo getByAppid(String appid);
}
