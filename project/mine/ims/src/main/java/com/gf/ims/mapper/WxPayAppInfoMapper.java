package com.gf.ims.mapper;

import com.gf.ims.pay.module.WxPayAppInfo;

public interface WxPayAppInfoMapper {

	WxPayAppInfo getByAppid(String appid);

}
