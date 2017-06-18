package com.gf.ims.common.enums;
/**
 * 订单支付状态
 * @author laizs
 * @time 2014-5-27 下午2:32:10
 * @file OrderPayStatus.java
 */
public enum OrderPayStatus {
	
	NO_PAY("未支付",0),
	HASED_PAY("已支付",1)
	;
	

	private OrderPayStatus(String name, int value) {
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
    private int value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
    


	
}
