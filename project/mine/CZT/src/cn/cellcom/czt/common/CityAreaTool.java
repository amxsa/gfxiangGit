package cn.cellcom.czt.common;

import org.apache.commons.lang.StringUtils;

public class CityAreaTool {
	public static String[] getArea(String city){
		if(StringUtils.isBlank(city))
			return null;
		if(Env.CITY_AREA.containsKey(city)){
			return Env.CITY_AREA.get(city);
		}
		return null;
	}
}
