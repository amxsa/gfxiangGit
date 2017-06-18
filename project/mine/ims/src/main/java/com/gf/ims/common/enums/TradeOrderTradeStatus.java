package com.gf.ims.common.enums;
/**
 * 订单支付状态
 * @author lzs
 *
 */
public enum TradeOrderTradeStatus {
	
	NOT_COMPLETE(0),//未完成
	COMPLETE(1),//已完成
	;
	private TradeOrderTradeStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
    private Integer value;
	
}
