package cn.cellcom.czt.common;

import java.security.acl.Group;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.DB.JdbcResultSet;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.po.TTdcodeGroup;
import cn.cellcom.web.spring.ApplicationContextTool;

public class Env {
	public static String SERVICE_KEY = "cellnum";
	public static String SERVICE_KEY_APP = "cellcom";
	public static String PASSWORD = "123456";
	public static String OBD_USER="53EC50B9035A395EEE6E9218BFF2E070";
	public static String OBD_PASS="123456";
	public static String GD="000";
	public static Map<String ,String> ERROR_CODE =null;
	public static Map<String ,String> SPCODE = null;
	public static Map<String ,String> OPERATE_TYPE = new LinkedHashMap<String, String>();
	public static Map<String, String[]> AREACODE = new LinkedHashMap<String, String[]>(); // 区号集合
	public static Set<Long> UNIT_PRICE =new LinkedHashSet<Long>();
	public static final String ALL_PATTERN = "(^1(8[019]|3[3]|5[3])\\d{8}$)|(^0(20\\d{8}$|75[4,5,7]\\d{8}$|75[0-3,6,8-9]\\d{7}$|760\\d{8}$|76[2,3,6,8,9]\\d{7}$|66[0,2,3,8]\\d{7}$))";
	public static Map<String ,String> ORDER_STATE = new LinkedHashMap<String, String>();
	public static Map<String ,String> ORDER_FROM_PART = new LinkedHashMap<String, String>();
	public static Map<String ,String> ORDER_CONFIGURE = new LinkedHashMap<String, String>();
	public static Map<String ,String> DEVICE_STATE = new LinkedHashMap<String, String>();
	public static Map<String ,String> FLUXCARD_STATE = new LinkedHashMap<String, String>();
	public static Map<String ,String> EXPRESS = new LinkedHashMap<String, String>();
	public static Map<String ,String> SETTLEACCOUNTS = new LinkedHashMap<String, String>();
	public static Set<String> YEARMONTH = null;
	// 查询号码相关变量
	public static String SERVICE_SP = "CZT@LULU";
	public final static Map<Integer, String> CODE_RESULT = new HashMap<Integer,String>();
	//天气预报接口 变量
	public static final String WEATHER_URL = "http://open.weather.com.cn/data/";
	public static Set<String> WEATHER_TYPE =new HashSet<String>();
	public static Set<String> WEATHER_SPNAME =new HashSet<String>();
	public static Set<String> ALLOW_URL =new HashSet<String>();
	public static Map<String, String[]> CITY_AREA = new LinkedHashMap<String, String[]>(); 
	
	//组id
	public static Map<Integer, TTdcodeGroup> TDCODE_GROUP =new HashMap<Integer, TTdcodeGroup>();
	
	
	static{
		
		ALLOW_URL.add("LoginManagerAction_login");
		ALLOW_URL.add("LoginManagerAction_login1");
		ALLOW_URL.add("OrderAlipayManagerAction_pay");
		ALLOW_URL.add("OrderAlipayManagerAction_paySuccess");
		
		ERROR_CODE = new HashMap<String, String>();
		ERROR_CODE.put("ERR00","操作成功");
		ERROR_CODE.put("ERR01","远程无法连接");
		ERROR_CODE.put("ERR02","数据处理异常");
		ERROR_CODE.put("ERR03","接口用户名或接口密码错误");
		ERROR_CODE.put("ERR04","查询参数出错");
		ERROR_CODE.put("ERR07","该接口用户没有权限");
		ERROR_CODE.put("ERR08","设备不存在");
		ERROR_CODE.put("ERR09","设备已经绑定");
		ERROR_CODE.put("ERR010","客户已经存在");
		ERROR_CODE.put("ERR011","客户名或客户密码错误");
		ERROR_CODE.put("ERR012","客户已经绑定设备");
		ERROR_CODE.put("ERR013","客户未绑定设备");
		ERROR_CODE.put("ERR014","系统内部错误");
		SPCODE= new LinkedHashMap<String,String>();
		SPCODE.put("O10", "成为");
		SPCODE.put("O11", "元征");
		SPCODE.put("O12", "北斗云");
		SPCODE.put("O13", "华工OBD");
		
		OPERATE_TYPE.put("R", "激活");
		OPERATE_TYPE.put("L", "登录");
		OPERATE_TYPE.put("D", "注销");
		OPERATE_TYPE.put("S", "发放");
		
		AREACODE.put("000", new String[]{"广东","GD"});
		AREACODE.put("020", new String[]{"广州","GZ"});
		AREACODE.put("0660", new String[]{"汕尾","SW"});
		AREACODE.put("0662", new String[]{"阳江","YJ"});
		AREACODE.put("0663", new String[]{"揭阳","JY"});
		AREACODE.put("0668", new String[]{"茂名","MM"});
		AREACODE.put("0750", new String[]{"江门","JM"});
		AREACODE.put("0751", new String[]{"韶关","SG"});
		AREACODE.put("0752", new String[]{"惠州","HZ"});
		AREACODE.put("0753", new String[]{"梅州","MZ"});
		AREACODE.put("0754", new String[]{"汕头","ST"});
		AREACODE.put("0755", new String[]{"深圳","SZ"});
		AREACODE.put("0756", new String[]{"珠海","ZH"});
		AREACODE.put("0757", new String[]{"佛山","FS"});
		AREACODE.put("0758", new String[]{"肇庆","ZQ"});
		AREACODE.put("0759", new String[]{"湛江","ZJ"});
		AREACODE.put("0760", new String[]{"中山","ZS"});
		AREACODE.put("0762", new String[]{"河源","HY"});
		AREACODE.put("0763", new String[]{"清远","QY"});
		AREACODE.put("0766", new String[]{"云浮","YF"});
		AREACODE.put("0768", new String[]{"潮州","CZ"});
		AREACODE.put("0769", new String[]{"东莞","DG"});
		
		UNIT_PRICE.add(389L);
		UNIT_PRICE.add(398L);
		UNIT_PRICE.add(449L);
		UNIT_PRICE.add(588L);
		ORDER_STATE.put("H", "产品经理新增订单，需号百人员审核");
		ORDER_STATE.put("N", "号百人员审核不通过");
		ORDER_STATE.put("T", "产品经理修改订单，需号百人员重新审核");
		ORDER_STATE.put("1", "号百审核订单通过，需管理员审核");
		//ORDER_STATE.put("2", "订单审核通过需产品经理确认");
		ORDER_STATE.put("3", "管理员审核不通过");
		ORDER_STATE.put("4", "号百人员修改订单，需管理员重新审核");
		ORDER_STATE.put("5", "管理员审核通过，订单货品预备组装");
		ORDER_STATE.put("6", "订单货品正在组装");
		ORDER_STATE.put("7", "订单货品组装完毕准备出库");
		ORDER_STATE.put("8", "订单已出库");
		ORDER_STATE.put("9", "订单货品正在派送");
		ORDER_STATE.put("0", "订单货品确认收货，订单完成");
		ORDER_FROM_PART.put("1", "管理员");
		ORDER_FROM_PART.put("2", "地市运营商");
		ORDER_FROM_PART.put("K", "客户端");
		ORDER_FROM_PART.put("W", "微信");
		
		ORDER_CONFIGURE.put("H", "高配");
		ORDER_CONFIGURE.put("L", "低配");
		DEVICE_STATE.put("I", "入库");
		DEVICE_STATE.put("B", "绑定");
		DEVICE_STATE.put("O", "出库");
		FLUXCARD_STATE.put("I", "入库");
		FLUXCARD_STATE.put("B", "绑定");
		FLUXCARD_STATE.put("O", "出库");
		
		
	
		
		EXPRESS.put("3", "圆通");
		EXPRESS.put("1", "顺丰");
		EXPRESS.put("2", "EMS");
		SETTLEACCOUNTS.put("N", "待结算");
		SETTLEACCOUNTS.put("B", "待审核");
		SETTLEACCOUNTS.put("F", "审核不通过");
		SETTLEACCOUNTS.put("Y", "已结算");
		YEARMONTH = new LinkedHashSet<String>();
		Date start = DateTool.formateString2Time("201410", "yyyyMM");
		for (int i = 0; i < 60; i++) {
			YEARMONTH.add(DateTool.formateTime2String(
					DateTool.diff(start, "MONTH", i), "yyMM"));
		}
		
		WEATHER_TYPE.add("index_f");
		WEATHER_TYPE.add("index_v");
		WEATHER_TYPE.add("forecast_f");
		WEATHER_TYPE.add("forecast_v");
		
		WEATHER_SPNAME.add("QICHEN");
		
		CODE_RESULT.put(1, "查询数据不存在");
		CODE_RESULT.put(0, "操作成功");
		CODE_RESULT.put(-500, "内部错误");
		CODE_RESULT.put(-1001, "手机号码查询失败");
		CODE_RESULT.put(-1002, "手机号码查询失败，原因缺少必要参数");
		CODE_RESULT.put(-1003, "手机号码查询失败，原因字符校检错误");
		CODE_RESULT.put(-2001, "查询天气失败");
		CODE_RESULT.put(-2002, "查询天气失败，原因城市编号不能为空");
		CODE_RESULT.put(-2003, "查询天气失败，类型值错误");
		CODE_RESULT.put(-2004, "查询天气失败，原因字符校检错误");
		CODE_RESULT.put(-2005, "查询天气失败，城市编号不存在");
		CODE_RESULT.put(-2006, "查询天气失败，天气数据未找到");
		CODE_RESULT.put(-2007, "查询天气失败，厂商账号错误");
		
		CODE_RESULT.put(-3001, "设备流量卡号查询失败");
		CODE_RESULT.put(-3002, "设备流量卡号查询失败，原因缺少必要参数");
		CODE_RESULT.put(-3003, "设备流量卡号查询失败，原因字符校检错误");
		
		CODE_RESULT.put(-4001, "订单录入失败");
		CODE_RESULT.put(-4002, "订单录入失败，原因缺少必要参数");
		CODE_RESULT.put(-4003, "订单录入失败，原因字符校检错误");
		CODE_RESULT.put(-4004, "订单录入失败，原因设备配置错误");
		CODE_RESULT.put(-4005, "订单录入失败，原因订单数量错误");
		CODE_RESULT.put(-4006, "订单录入失败，原因收货人姓名错误");
		CODE_RESULT.put(-4007, "订单录入失败，原因收货人联系电话错误");
		CODE_RESULT.put(-4008, "订单录入失败，原因收货人地址错误");
		
		CITY_AREA.put("广州市",new String[]{"荔湾区","越秀区","海珠区","天河区","白云区","黄浦区","番禺区","花都区","南沙区","萝岗区","增城市","从化市"});
		CITY_AREA.put("佛山市",new String[]{"禅城区","南海区","顺德区","山水区","高明区"});
		CITY_AREA.put("茂名市",new String[]{"茂南区","茂港区","电白县","高州市","化州市","信宜市"});
		CITY_AREA.put("阳江市",new String[]{"江城区","阳西县","阳东县","阳春市"});
		CITY_AREA.put("东莞市",new String[]{"东城街道","南城街道","万江街道","莞城街道","石龙镇","石排镇","茶山镇","企石镇","桥头镇","东坑镇","横沥镇","常平镇","虎门镇","长安镇","沙田镇","厚街镇","寮步镇","大岭山镇","大朗镇","黄江镇","樟木头镇","谢岗镇","塘厦镇","清溪镇","凤岗镇","麻涌镇","中堂镇","高埗镇","石碣镇","望牛墩镇","洪梅镇","道滘镇"});
		CITY_AREA.put("韶关市",new String[]{"武江区","浈江区","曲江区","始兴县","仁化县","翁源县","乳源县","新丰县","乐昌市","南雄市"});
		CITY_AREA.put("湛江市",new String[]{"赤坎区","霞山区","坡头区","麻章区","遂溪县","徐闻县","廉江市","雷州市","吴川市"});
		CITY_AREA.put("潮州市",new String[]{"湘桥区","潮安区","饶平县"});
		CITY_AREA.put("清远市",new String[]{"清城区","佛冈县","阳山县","连山县","连南县","清新区","英德市","连州市"});
		CITY_AREA.put("云浮市",new String[]{"云城区","新兴县","郁南县","云安县","罗定市"});
		CITY_AREA.put("汕头市",new String[]{"龙湖区","金平区","濠江区","潮阳区","潮南区","澄海区","南澳县"});
		CITY_AREA.put("惠州市",new String[]{"惠城区","惠阳区","博罗县","惠东县","龙门县"});
		CITY_AREA.put("汕尾市",new String[]{"城区","海丰县","陆河县","陆丰市"});
		CITY_AREA.put("深圳市",new String[]{"光明新区","罗湖区","福田区","南山区","宝安区","龙岗区","盐田区"});
		CITY_AREA.put("梅州市",new String[]{"梅江区","梅县","打浦县","丰顺县","五华县","平远县","蕉岭县","兴宁市"});
		CITY_AREA.put("河源市",new String[]{"源城区","紫金县","龙川县","连平县","和平县","东源县"});
		CITY_AREA.put("江门市",new String[]{"蓬江区","江海区","新会区","台山市","开平市","鹤山市","恩平市"});
		CITY_AREA.put("珠海市",new String[]{"香洲区","斗门区","金湾区"});
		CITY_AREA.put("肇庆市",new String[]{"端州区","鼎湖区","广宁县","怀集县","封开县","德庆县","高要市","四会市"});
		CITY_AREA.put("中山市",new String[]{"石岐区街道","东区街道","火炬开发区街道","西区街道","南区街道","五桂山街道","小榄镇","黄圃镇","民众镇","东凤镇","东升镇","古镇镇","沙溪镇","坦洲镇","港口镇","三角镇","横栏镇","南头镇","阜沙镇","南朗镇","三乡镇","板芙镇","大涌镇","神湾镇"});
		CITY_AREA.put("揭阳市",new String[]{"榕城区","揭东区","揭西县","惠来县","普宁县"});
	}


	
	
}
	

