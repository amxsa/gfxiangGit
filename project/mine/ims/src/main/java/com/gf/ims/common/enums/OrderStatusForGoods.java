package com.gf.ims.common.enums;
/**
 * 实物型订单状态
 */
public enum OrderStatusForGoods {
	
	SUBMIT("待付款",0),
	HASED_PAY("待发货",1),
	SEND("待收货",2),
	REFUND("退款中",3),
	AFTER_SERVICE("售后处理中",4),
	FINISH("交易成功",5),
	CLOSE("交易关闭",6);

	

	private OrderStatusForGoods(String name, int value) {
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
	
	/**
	 * 获取订单状态的名称
	 *@return
	 */
	public static String getOrderStatusName(int status){
		String orderStatusName="";
		OrderStatusForGoods[] s=OrderStatusForGoods.values();
		for(OrderStatusForGoods o:s){
			if(o.getValue()==status){
				orderStatusName=o.getName();
			}
		}
		return orderStatusName;
	}

//	public static String getOrderStatus(int type,int status){
//		
//		switch (type) {
//		
//			case 3:
//			
//				return OrderStatusForService.getOrderStatusName(status);
//			
//			case 7:
//				
//				return OrderStatusForToStore.getOrderStatusName(status);
//
//			default:
//				
//				return OrderStatusForGoods.getOrderStatusName(status);
//		}
//	}
	
}
