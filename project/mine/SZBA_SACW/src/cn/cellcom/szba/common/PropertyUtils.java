package cn.cellcom.szba.common;

import org.apache.commons.lang3.StringUtils;

import cn.cellcom.szba.enums.PropertyStatusKey;

public class PropertyUtils {

	public static boolean isProJsonBlank(String proJson){
		if("]".equals(proJson) || StringUtils.isBlank(proJson)){
			return true;
		}
		return false;
	}
}
