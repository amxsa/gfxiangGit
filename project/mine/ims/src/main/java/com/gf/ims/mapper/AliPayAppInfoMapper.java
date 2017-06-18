package com.gf.ims.mapper;

import com.gf.ims.pay.module.AliPayAppInfo;

public interface AliPayAppInfoMapper {

	AliPayAppInfo getByAppid(String appid);

}
