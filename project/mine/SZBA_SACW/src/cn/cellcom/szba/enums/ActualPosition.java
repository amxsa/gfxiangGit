package cn.cellcom.szba.enums;

/**
 * 财务实际位置的枚举
 * @author hzj
 *
 */
public enum ActualPosition {

	/**
	 * 办案民警
	 */
	BAMJ("办案民警"),
	/**
	 * 暂存库
	 */
	ZCK("暂存库"),
	/**
	 * 中心库
	 */
	ZXK("中心库"),
	/**
	 * 配送中（暂存库to中心库）
	 */
	PSZZCK2ZXK("配送中（暂存库to中心库）"),
	/**
	 * 不在管理范围
	 */
	BZKZ("不在管理范围")
	;
	private String cnStatus;
	private ActualPosition(String str){
		cnStatus = str;
	}
	public String getCnStatus(){
		return cnStatus;
	}
}
