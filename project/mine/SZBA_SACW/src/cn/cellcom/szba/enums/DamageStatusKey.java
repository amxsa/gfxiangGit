package cn.cellcom.szba.enums;

public enum DamageStatusKey {
	WH("完好"),
	SH("损毁"),
	DS("短少"),
	DH("调换"),
	MS("灭失"),
	OTHERWISE("其他")
	;
	private String cnStatus;
	private DamageStatusKey(String str){
		cnStatus = str;
	}
	public String getCnStatus(){
		return cnStatus;
	}
}
