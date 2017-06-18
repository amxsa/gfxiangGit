package cn.cellcom.esb.bus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;



public class ESBEnv {
	public static Map<String, Object[]> SMSCODE = new HashMap<String, Object[]>();
	public static Map<String, Integer> model = null;
	public static int timeout = 6000;
	public final static String ORDER = "ORDER";
	public final static String CANCEL = "CANCEL";
	public final static String QUERY = "QUERY";
	public final static String BUS_DATA_ORDER_LH = "<row rownum=\"1\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"漏话提醒\">7782</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row>";
	public final static String BUS_DATA_ORDER_JC = "<row rownum=\"2\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"号簿管理\">7777</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"3\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"语音拨号\">7778</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"4\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"改号通知\">7779</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"5\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"日程提醒\">7780</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"6\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"通讯录同步\">7783</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row>";
	public final static String BUS_DATA_ORDER_XX ="<row rownum=\"7\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"人工秘书\">7781</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row>";
	public static Map<String, String> operateType = null;
	static {
		model = new HashMap<String, Integer>();
		model.put("51", 1);
		model.put("21", 6);
		model.put("22", 7);
		
		operateType = new HashMap<String, String>();
		operateType.put("A", "订购");
		operateType.put("D","退订");
		operateType.put("U", "升级");

	}

}
