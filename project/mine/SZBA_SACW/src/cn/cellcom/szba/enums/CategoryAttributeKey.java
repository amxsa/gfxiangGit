package cn.cellcom.szba.enums;

/**
 * 财物类别枚举类
 * @author 陈奕平
 *
 */
public enum CategoryAttributeKey {

	/**
	 * 一般财物
	 */
	YBCW("一般财物"),
	/**
	 * 烟花爆竹
	 */
	YHBZ("烟花爆竹"),
	/**
	 * 管制刀具(立案)
	 */
	GZDJLA("管制刀具（立案）"),
	/**
	 * 管制刀具(不立案)
	 */
	GZDJBLA("管制刀具（不立案）"),
	/**
	 * 缴获枪支
	 */
	JHQZ("缴获枪支"),
	/**
	 * 淫秽物品（图书类）
	 */
	YHWPTS("淫秽物品（图书类）"),
	/**
	 * 淫秽物品（光盘）
	 */
	YHWPGP("淫秽物品（光盘）"),
	/**
	 * 赌博工具
	 */
	DBGJ("赌博工具"),
	/**
	 * 不保存的财物
	 */
	BBCCW("不保存的财物"),
	/**
	 * 电子物证(有存储介质)
	 */
	DZWZY("电子物证(有存储介质)"),
	/**
	 * 电子物证(无存储介质)
	 */
	DZWZW("电子物证(无存储介质)");
	
	private String cnKey;
	private CategoryAttributeKey(String str){
		cnKey = str;
	}
	public String getCnKey(){
		return cnKey;
	}
}
