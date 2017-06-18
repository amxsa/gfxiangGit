package cn.cellcom.common.data;

public class RegEx {
	public static final String fix = "";
	public static final String email = "";
	// 去掉JS
	public static final String JS = "[|&;$%@'\"\',<>()+\\s]";
	// 广东电信号码
	public static final String GD_NUM = "(^1(8[019]|3[3]|5[3])\\d{8}$)|(^0(20\\d{8}$|75[4,5,7]\\d{8}$|75[0-3,6,8-9]\\d{7}$|760\\d{8}$|76[2,3,6,8,9]\\d{7}$|66[0,2,3,8]\\d{7}$))";
	public static final String YYYY_MM_DD = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
	public static final String YYYYMMDD="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";
}
