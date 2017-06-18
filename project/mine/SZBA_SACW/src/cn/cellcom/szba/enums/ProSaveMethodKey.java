package cn.cellcom.szba.enums;

public enum ProSaveMethodKey {

	/**
	 * 常规保存
	 */
	CGBC("常规保存"),
	/**
	 * 冷藏
	 */
	LC("冷藏"),
	/**
	 *涉密保存
	 */
	SMBC("涉密保存"),
	/**
	 * 防磁保存
	 */
	FCBC("防磁保存"),
	/**
	 * 枪支弹药
	 */
	QZDY("枪支弹药"),
	/**
	 * 车场保管
	 */
	CCBG("车场保管")
	;

	private String cnStatus;
	private ProSaveMethodKey(String str){
		cnStatus = str;
	}
	public String getCnKey(){
		return cnStatus;
	}
}
