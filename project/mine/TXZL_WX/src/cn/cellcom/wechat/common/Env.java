package cn.cellcom.wechat.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.cellcom.wechat.po.TCity;
import cn.cellcom.wechat.po.TMobileHead;
import cn.cellcom.wechat.po.TSetinfo;



public class Env {
	public final static Map<String, String> POLITEOFF = new LinkedHashMap<String, String>();
	
	public static Map<Long, String> SETINFO = new HashMap<Long, String>();
	public static Map<String, String> AREACODE = new LinkedHashMap<String, String>(); // 区号集合
	public static List<TMobileHead> MOBILE_HEAD_LIST = new ArrayList<TMobileHead>(); // 手机号段集合
	public static LinkedHashMap<Long, TSetinfo> SETINFO_MAP = new LinkedHashMap<Long, TSetinfo>();// 套餐集合
	public static LinkedHashMap<Long, TSetinfo> C_SETINFO_MAP = new LinkedHashMap<Long, TSetinfo>();// C套餐集合
	public static LinkedHashMap<Long, String> PROVINCE = new LinkedHashMap<Long, String>();// 省份集合
	public static LinkedHashMap<String, TCity> CODE_CITY = new LinkedHashMap<String, TCity>();
	// 匹配中国电信手机号码或者是广东省内的固定电话号码的正则表达式
	public static final String ALL_PATTERN = "(^1(8[019]|3[3]|5[3]|7[7])\\d{8}$)|(^0(20\\d{8}$|75[4,5,7]\\d{8}$|75[0-3,6,8-9]\\d{7}$|760\\d{8}$|76[2,3,6,8,9]\\d{7}$|66[0,2,3,8]\\d{7}$))";
	public static final String C_PATTERN = "^1(8[019]|3[3]|5[3]|7[7])\\d{8}$";
	
	/** ***** 以下是漏话设置常量***** */
	public final static String FLAG_Y = "Y";
	public final static String FLAG_F = "F";
	public final static String FLAG_B = "B";
	public final static String FLAG_N = "N";
	public final static String FLAG_D = "D";
	public final static String FLAG_U = "U";
	public final static String FLAG_G = "G";
	public final static String INTERNAL_ADDRESS = "http://172.108.116.28:8080/TXZL_WebServices/services/InternalService";// 请求28接口
	public final static String MESSAGEREQUEST = "message_request"; // 业务请求消息
	public final static String MESSAGERESULT = "message_result";// 业务反馈消息
	public final static String MESSAGECONFIRM = "message_confirm";// 确认消息
	public final static String WORKTYPE_FORWARD = "0";// 正向工单
	public final static String WORDTYOE_REVERSED = "1";// 反向工单
	public final static String CALLFORWARD_UNCOND = "call_forward_uncond";// 无条件呼叫前传
	public final static String CALLFORWARD_BUSY = "call_forward_busy";// 遇忙呼叫前传
	public final static String CALLFORWARD_NOGET = "call_forward_noget";// 漏话提醒设置
	public final static String CALLFORWARD_NORESPONSE = "call_forward_noresponse";// 无应答呼叫前传
	public final static String PRODCODE = "product_3gphone";// 3G后付费电话
	public final static String ACT_TYPE = "operation_change";// 动作标识
	public final static Map<String, String> CALLFORWARD_STATUS = new HashMap<String, String>();
	public final static Map<String, String> CALLFORWARD_OPERATETYPE = new HashMap<String, String>();
	public final static Map<String, String> CALLFORWARD_TYPE = new HashMap<String, String>();
	/**************结束漏话设置常量**************/
	public final static String ERROR ="/common/error";
	public final static String FAIL ="/common/fail";
	public final static String SUCCESS ="/common/success";
	public final static List<String> CODES3 = new ArrayList<String>();
	public final static String PASSWORD = "111111";
	public static Map<String, Date> WECHAT_YZM = new HashMap<String, Date>();//微信短信验证码内存
	
	public static Set<String> ALLOW_ALL_URL = new HashSet<String>();// 允许所有访问的地址
	public static Set<String> ALLOW_WECHAT_URL = new HashSet<String>();// 允许微信访问的地址
	public static Set<String> ALLOW_WECHAT_BIND_URL = new HashSet<String>();// 允许微信绑定手机访问的地址
	public static Map<String, Date> YFG_YZM = new HashMap<String, Date>();//一分购短信验证码内存
	//微信全局token
	public static Map<String ,Object[]> ACCESS_TOKEN_LIMITE_TIME = new LinkedHashMap<String, Object[]>();
	static {
		SETINFO.put(21L,"通信助理基础包");
		SETINFO.put(31L,"通信助理基础包");
		SETINFO.put(22L,"通信助理信息包");
		SETINFO.put(32L,"通信助理信息包");
		SETINFO.put(51L,"漏话提醒");
		
		
		POLITEOFF.put("1", "开会");
		POLITEOFF.put("2", "出差");
		POLITEOFF.put("3", "不方便接听");
		POLITEOFF.put("4", "开车");
		POLITEOFF.put("5", "上课");
		POLITEOFF.put("6", "休息");
		POLITEOFF.put("7", "学习");
		POLITEOFF.put("8", "培训");
		POLITEOFF.put("9", "手机没电");
		
		
		CALLFORWARD_TYPE.put("U", "无条件呼转");
		CALLFORWARD_TYPE.put("B", "遇忙呼转");
		CALLFORWARD_TYPE.put("N", "无应答呼转");
		CALLFORWARD_TYPE.put("D", "不可及呼转");
		CALLFORWARD_TYPE.put("G", "漏话呼转");

		CALLFORWARD_STATUS.put("N", "未处理");
		CALLFORWARD_STATUS.put("S", "设置中");
		CALLFORWARD_STATUS.put("Y", "设置成功");
		CALLFORWARD_STATUS.put("F", "设置失败");
		
		AREACODE.put("000", "广东");
		AREACODE.put("020", "广州");
		AREACODE.put("0660", "汕尾");
		AREACODE.put("0662", "阳江");
		AREACODE.put("0663", "揭阳");
		AREACODE.put("0668", "茂名");
		AREACODE.put("0750", "江门");
		AREACODE.put("0751", "韶关");
		AREACODE.put("0752", "惠州");
		AREACODE.put("0753", "梅州");
		AREACODE.put("0754", "汕头");
		AREACODE.put("0755", "深圳");
		AREACODE.put("0756", "珠海");
		AREACODE.put("0757", "佛山");
		AREACODE.put("0758", "肇庆");
		AREACODE.put("0759", "湛江");
		AREACODE.put("0760", "中山");
		AREACODE.put("0762", "河源");
		AREACODE.put("0763", "清远");
		AREACODE.put("0766", "云浮");
		AREACODE.put("0768", "潮州");
		AREACODE.put("0769", "东莞");
		
		CODES3.add("010");
		CODES3.add("020");
		CODES3.add("021");
		CODES3.add("028");
		CODES3.add("022");
		CODES3.add("024");
		CODES3.add("023");
		CODES3.add("027");
		CODES3.add("029");
		CODES3.add("025");
		
		
		
		
		/**
		ALLOW_WECHATURL= new HashSet<String>();
		ALLOW_WECHATURL.add("BindWechatAction_bind");
		ALLOW_WECHATURL.add("RegisterAction_register");
		ALLOW_WECHATURL.add("GoodsAction_showGoods");
		ALLOW_WECHATURL.add("GoodsAction_showGoodsDetail");
		ALLOW_WECHATURL.add("UserAction_checkBindMobile");
		ALLOW_WECHATURL.add("UserAction_bindMobile");
		ALLOW_WECHATURL.add("UserAction_bindTxzlMobile");
		ALLOW_WECHATURL.add("UserAction_openTxzlMobile");
		ALLOW_WECHATURL.add("UserAction_showMyGoods");
		ALLOW_WECHATURL.add("UserAction_updateReceive");
		ALLOW_WECHATURL.add("OrderAction_addOrder");
		ALLOW_WECHATURL.add("OrderAction_paySuccess");
		*/
	}
}
