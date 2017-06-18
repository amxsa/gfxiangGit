package cn.cellcom.szba.enums;

/**
 * 财物类别枚举，
 * @author pansenxin
 *
 */
public enum PropertyTypeKey {

	/**
	 * 一般财物
	 */
	YBCW("YBCW"),
	/**
	 * 烟花爆竹
	 */
	YHBZ("YHBZ"),
	/**
	 * 管制刀具(立案)
	 */
	GZDJLA("GZDJLA"),
	/**
	 * 管制刀具(不立案)
	 */
	GZDJBLA("GZDJBLA"),
	/**
	 * 缴获枪支
	 */
	JHQZ("JHQZ"),
	/**
	 * 淫秽物品（图书类）
	 */
	YHWPTS("YHWPTS"),
	/**
	 * 淫秽物品（光盘）
	 */
	YHWPGP("YHWPGP"),
	/**
	 * 赌博工具
	 */
	DBGJ("DBGJ"),
	/**
	 * 不保存的财物
	 */
	BBCCW("BBCCW("),
	/**
	 * 电子物证(有存储介质)
	 */
	DZWZY("DZWZY"),
	/**
	 * 电子物证(无存储介质)
	 */
	DZWZW("DZWZW");
	
	private String cnKey;
	private PropertyTypeKey(String str){
		cnKey = str;
	}
	public String getCnKey(){
		return cnKey;
	}
}
