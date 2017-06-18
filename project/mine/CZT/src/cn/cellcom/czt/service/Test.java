package cn.cellcom.czt.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.common.Env;





public class Test {
	public static void main(String[] args) throws AxisFault {
		Test test = new Test();
		
		//String url ="http://183.62.251.19:8091/thinkczt/index.php";
		String url = "http://localhost:8080/CZT/";
		//String mobileNum="13911223344";
//		String url ="http://183.63.133.137:80/CZT/";
		String mobileNum="13632445442";
		//String url ="http://183.62.251.19:8091/thinkczt/";
		//String url ="http://183.62.251.19:8089/thinkczt/";
		//String tdcode = "o10l0001851411";
		//String tdcode = "o00h0000451410";
		
		String tdCodemd5="o10l3b43ddf26083660e";
		//tdCodemd5="O10H77fa4e5c8f621a04";
		//String mobileid = "39f724af42e60456";
		long timestamp =System.currentTimeMillis();
		test.regist(url, mobileNum, tdCodemd5, timestamp);
		//String authstring =MD5.getMD5((mobileNum+Env.SERVICE_KEY_APP+String.valueOf(timestamp)).getBytes());
		//test.login(url, mobileNum, timestamp,authstring);
		//test.releasebind(url, mobileNum, tdCodemd5, timestamp);
		//CztServiceServiceStub stub = new CztServiceServiceStub();
		//System.out.println(MD5.getMD5("13002057175cellcom20141202110827".getBytes()));
		//String tdCodemd5Part="2a93999";
		//String authstring= MD5.getMD5((StringUtils.substring(tdCodemd5,tdCodemd5.length()-8,tdCodemd5.length())+Env.SERVICE_KEY_APP+String.valueOf(timestamp)).getBytes());
		//timestamp=20141231112440L;
		//String mobileNum="13632445442";
		//authstring =  "c470136ab5e029db21aef04b8997fd9c";
		//test.registSN(url,"yz", mobileNum,StringUtils.substring(tdCodemd5,tdCodemd5.length()-8,tdCodemd5.length()), timestamp,authstring);
		//test.transfer(null);
		//String SN = "o10l38c024794b798040";
		//authstring = MD5.getMD5((SN+"CZT@LULU"+timestamp).getBytes());
		//url ="http://localhost:8080/CZT/";
		//url = "http://183.62.251.19:8091/thinkczt/";
		//url = "http://183.63.133.137:80/CZT/";
		//test.queryMobile(url, SN, timestamp, authstring);
//		mobileNum="17701955366";
//		authstring = MD5.getMD5((mobileNum+"CZT@LULU"+timestamp).getBytes());
//		System.out.println(authstring);
//		test.querySN("http://183.63.133.137:80/CZT/", mobileNum, timestamp, authstring);
//		String areaid = "101280800";
//		String spname="QICHEN";
//		authstring = MD5.getMD5((spname+"WEATHER@$^*"+timestamp).getBytes());
		//test.queryWeather(url,spname, areaid, "forecast_f", timestamp, authstring);
//		String orderId= "163916135";
//		timestamp=20150428111234L;
//		authstring = MD5.getMD5((orderId+"CZT@LULU"+timestamp).getBytes());
//		test.addOrder(url, "H", "588", "1", "张三", "1890000000", "测试地址", orderId, timestamp, authstring);
//		test.alipay();
		//test.queryIDCardState(url, mobileNum, timestamp, authstring);
		String pn ="00000000000000687E9FCA58";
		String authstring =MD5.getMD5((pn+Env.SERVICE_KEY_APP+String.valueOf(timestamp)).getBytes());
		test.queryTdCode(url, pn, timestamp, authstring);
	}
	public void regist(String url,String mobileNum,String tdCodemd5,long timestamp){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("mobileNum", mobileNum);
		params.put("tdCode", tdCodemd5);
		params.put("timestamp", String.valueOf(timestamp));
		String result = client.doPost("", url+"?g=Cztapi&m=Shb&a=Regist", params, "UTF-8", 20000, false);
		System.out.println(">>>>"+result);
	}
	
	public void registSN(String url,String spCode,String mobileNum,String tdCodemd5,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("spCode", spCode);
		params.put("mobileNum", mobileNum);
		params.put("tdCode", tdCodemd5);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result = client.doPost("", url+"?g=Cztapi&m=Shb&a=RegistSN", params, "UTF-8", 20000, false);
		System.out.println(">>>>"+result);
	}
	
	public void login(String url,String mobileNum,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("mobileNum", mobileNum);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result = client.doPost("", url+"?g=Cztapi&m=Shb&a=Logins", params, "UTF-8", 20000, false);
		System.out.println(">>>>"+result);
	}
	
	public void releasebind(String url,String mobileNum,String tdCodemd5,long timestamp){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("tdCode", tdCodemd5);
		params.put("mobileNum", mobileNum);
		params.put("timestamp", String.valueOf(timestamp));
		String result = client.doPost("", url+"?g=Cztapi&m=Shb&a=Releasebind", params, "UTF-8", 20000, false);
		System.out.println(">>>>"+result);
	}
	
	public void transfer(String url){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("account", "admin");
		params.put("password", "cellc0m");
		params.put("sql", "update sp_tdcode set  status=1  where tdcodemd5='O10Hc68bbb012bf62dd9' ");
		String result = client.doPost("数据割接", "http://192.168.7.54:8080/CZT/transfer/DataTransferAction_transferUpdate.do", params, "UTF-8", 20000, false);
		System.out.println(">>>>"+result);
	}
	public void queryMobile(String url ,String SN,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("SN", SN);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result =client.doPost("SN查询手机", url+"service/TDCodeServcieAction_queryMobile.action", params, "UTF-8", 2000, false);
		System.out.println(">>>>"+result);
	}
	
	public void querySN(String url ,String mobileNum,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("mobileNum", mobileNum);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result =client.doPost("手机号查询SN", url+"service/TDCodeServcieAction_querySN.action", params, "UTF-8", 2000, false);
		System.out.println(">>>>"+result);
	}
	
	public void queryWeather(String url ,String spname,String areaid,String type,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("spname", spname);
		params.put("areaid", areaid);
		params.put("type", type);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result =client.doPost("启辰查询天气", url+"service/WeatherServiceAction_queryWeather.action", params, "UTF-8", 2000, false);
		System.out.println(">>>>"+result);
	}
	
	public void addOrder(String url ,String configure,String unitPrice,String count,String receiveName,String receiveTelephone,String receiveAddress,String orderId,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("configure", configure);
		params.put("unitPrice", unitPrice);
		params.put("count", count);
		params.put("receiveName", receiveName);
		params.put("receiveTelephone", receiveTelephone);
		params.put("receiveAddress", receiveAddress);
		params.put("orderId", orderId);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result =client.doPost("新增订单", url+"service/OrderServiceAction_addOrder.action", params, "UTF-8", 2000, false);
		System.out.println(">>>>"+result);
	}
	
	public void  alipay(){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", " 88bfcebf98b5683a1d760f1cbc18df902");
		map.put("ordernum", "20150512101335993141");
		map.put("amount", "0.01");
		map.put("desc", "测试订单");
		map.put("backurl", "http://183.62.251.19:8091/thinkczt/service/OrderAlipayAction_paySuccess.do");
		StringBuffer signStr = new StringBuffer();
		signStr.append(map.get("key")).append(map.get("ordernum")).append(map.get("amount")).append(map.get("desc"));
		signStr.append(map.get("backurl")).append("4ed895be6006a0e589f887505");
		System.out.println(signStr.toString());
		String sign = MD5.getMD5(signStr.toString().getBytes());
		System.out.println(sign);
		map.put("sign", sign);
//		String str = client.doPost("支付模块", "http://115.29.169.98:8033/App/yzfpay.aspx?", map, "UTF-8", 10000, true);
//		System.out.println(str);
	}
	
	public void queryIDCardState(String url ,String mobileNum,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("mobileNum", mobileNum);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result =client.doPost("查询实名身份信息", url+"IdCardServiceAction_queryIDCardState.action", params, "UTF-8", 10000, false);
		System.out.println(">>>>"+result);
	}
	
	public void queryTdCode(String url ,String pn,long timestamp,String authstring){
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("pn", pn);
		params.put("timestamp", String.valueOf(timestamp));
		params.put("authstring", authstring);
		String result =client.doPost("查询二维码", url+"PnServiceAction_queryTdCode.action", params, "UTF-8", 10000, false);
		System.out.println(">>>>"+result);
	}
}
