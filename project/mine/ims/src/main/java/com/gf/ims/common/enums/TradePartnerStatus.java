package com.gf.ims.common.enums;
/**
 * 订单合作伙伴状态
 * @author lzs
 *
 */
public enum TradePartnerStatus {
	
	VALID(1),//有效
	INVALID(0),//无效
	;
	private TradePartnerStatus(Integer value) {
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
