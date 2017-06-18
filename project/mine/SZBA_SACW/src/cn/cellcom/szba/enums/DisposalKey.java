package cn.cellcom.szba.enums;

/**
 * 处置方式枚举类
 * @author 陈奕平
 *
 */
public enum DisposalKey {

	/**
	 * 入暂存库
	 */
	RZCK("入暂存库"),
	/**
	 * 入中心库
	 */
	RZXK("入中心库"),
	/**
	 * 出暂存库
	 */
	CZCK("出暂存库"),
	/**
	 * 出中心库
	 */
	CZXK("出中心库"),
	/**
	 * 调用
	 */
	DY("调用"),
	/**
	 * 退还
	 */
	TH("退还"),
	/**
	 * 发还
	 */
	FH("发还"),
	/**
	 * 销毁
	 */
	XH("销毁"),
	/**
	 * 没收
	 */
	MS("没收"),
	/**
	 * 拍卖
	 */
	PM("拍卖"),
	/**
	 * 收缴国库
	 */
	SJGK("收缴国库"),
	/**
	 * 变卖
	 */
	BM("变卖")
	;
	private String cnStatus;
	private DisposalKey(String str){
		cnStatus = str;
	}
	public String getCnStatus(){
		return cnStatus;
	}
	
}
