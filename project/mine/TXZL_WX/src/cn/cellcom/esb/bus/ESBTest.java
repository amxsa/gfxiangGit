package cn.cellcom.esb.bus;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.encrypt.MD5;



public class ESBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String url = "http://localhost:8080/TXZL_WAP/service/";
		String url ="http://t.gd118114.cn/service/";
		ESBTest test = new ESBTest();
		Long timestamp = 20121227164226L;
		String regNo = "18925008300";
		long a = System.currentTimeMillis();
		//test.testOrderAccount("orderAccount", regNo, "123456", "21", "", timestamp, url);
		test.testCancelAccount("cancelAccount", "18933929306", timestamp, url);
	}
	
	public String testOrderAccount(String method, String regNo,String password,String setid,String areacode, Long timestamp,
			String url) {
		HttpUrlClient clent = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("method", method);
		params.put("regNo", regNo);
		params.put("password", password);
		params.put("setid", setid);
		params.put("areacode", areacode);
		params.put("timestamp", String.valueOf(timestamp));
		String authstring = MD5.getMD5((regNo + "cellcom" + timestamp).getBytes());
		params.put("authstring", authstring);
		String result = clent.doPost(method, url + "AccountManager.flow?", params,
				"UTF-8", 10000, true);
		System.out.println(">>>>orderAccount:" + result);
		return result;
	}
	
	public String testCancelAccount(String method, String regNo, Long timestamp,
			String url) {
		HttpUrlClient clent = new HttpUrlClient();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("method", method);
		params.put("regNo", regNo);
		params.put("timestamp", String.valueOf(timestamp));
		String authstring = MD5.getMD5((regNo + "cellcom" + timestamp).getBytes());
		params.put("authstring", authstring);
		String result = clent.doPost(method, url + "AccountManager.flow?", params,
				"UTF-8", 20000, true);
		System.out.println(">>>>cancelAccount:" + result);
		return result;
	}

}
