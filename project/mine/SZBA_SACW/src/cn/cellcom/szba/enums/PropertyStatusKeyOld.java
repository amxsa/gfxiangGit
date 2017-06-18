package cn.cellcom.szba.enums;

/**
 * 财物状态枚举类，未登记，已登记，入暂存库，入中心库，调用，处置，案结
 * @author 陈奕平
 *
 */
public enum PropertyStatusKeyOld {

	/**
	 * 未登记
	 */
	WDJ,
	/**
	 * 已登记
	 */
	YDJ,
	
	/**
	 * 暂存库
	 */
	ZCK,
	
	
	/**
	 *已登记提交申请至暂存库 
	 */
	YDJ2ZCK,
	
	
	/**
	 * 暂存库提交申请至出暂存库
	 */
	ZCK2CZCK,
	
	/**
	 * 出暂存库
	 */
	CZCK,
	
	/**
	 * 中心库
	 */
	ZXK,
	
	/**
	 * 暂存库申请至中心库
	 */
	ZCK2ZXK,
	
	/**
	 * 中心库申请至出中心库
	 */
	ZXK2CZXK,
	
	/**
	 * 出中心库 
	 */
	CZXK,
	/**
	 * 调用(未入库的调用)
	 */
	DY,
	
	/**
	 * 调用中
	 */
	DYZ,
	
	/**
	 * 中心库调用
	 */
	ZXK_DY,
	
	/**
	 * 暂存库调用
	 */
	ZCK_DY,
	
	/**
	 * 处置(未入库的处置)
	 */
	CZ,
	
	
	/**
	 * 处置申请中
	 */
	CZZ,
	
	/**
	 * 从中心库申请财物处置
	 */
	ZXK_CZ,
	
	/**
	 * 从暂存库申请财物处置
	 */
	ZCK_CZ,
	
	/**
	 * 案结
	 */
	AJ
}
