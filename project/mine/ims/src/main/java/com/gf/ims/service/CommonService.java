package com.gf.ims.service;

import java.util.List;

import com.gf.imsCommon.ims.module.Area;
import com.gf.imsCommon.ims.module.City;
import com.gf.imsCommon.ims.module.Province;

public interface CommonService {

	List<Province> getProvince();
	
	List<City> getCityByFather(String provinceId);
	
	List<Area> getAreaByFather(String cityId);
}
