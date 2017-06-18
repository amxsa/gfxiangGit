package com.gf.ims.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
	
	IMS_SUPER("系统管理员","0"),
	IMS_NORMAL("普通用户","1"),
	IMS_OTHER("其它用户","2");
	

	public static Map<String,String> getUserTypeMap(){
		UserType[] types = UserType.values();
		Map<String, String> map=new HashMap<String, String>();
		for (UserType userType : types) {
			map.put(userType.name,userType.value);
		}
        return map;
	}
	
	private UserType(String name, String value) {
		this.name = name;
		this.value = value;
	}
	/**
     * 名称
     */
    private String name;
    /**
     * 类型的值
     */
    private String value;

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
