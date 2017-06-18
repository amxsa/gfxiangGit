package cn.cellcom.szba.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.domain.ClientAccount;
import cn.open.http.HttpClientHelper;

public class Test {
	private static String apiIp="http://192.168.7.159:8080/client";
	/**
	 * 用户登陆 参数：帐号（必填）、密码（必填）、验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		String url=apiIp + "/account/login.do";
		/*
		String account="1001";
		String password="e10adc3949ba59abbe56e057f20f883e";
		String timestamp="1452760740141";
		String authstring="16aefd996c8677db9a1c0a62bebbcd29";
		//String params = "account="+account+"&password="+password+"&timestamp="+timestamp+"&authstring="+authstring;
		String params = "{'account':'"+account+"','password':'"+password+"','timestamp':'"+timestamp+"','authstring':'"+authstring+"'}";
		
		
		Map<String,String> paramss = toHashMap(params);*/
		//HttpClientHelper hh=new HttpClientHelper();
		Map<String, String> params=new HashMap<String, String>();
		params.put("account", "1001");
		params.put("password", "e10adc3949ba59abbe56e057f20f883e");
		params.put("timestamp", "1452760740141");
		params.put("authstring", "16aefd996c8677db9a1c0a62bebbcd29");
		HttpClientHelper hh=new HttpClientHelper();
		//HttpResult hr=hh.post(url, params, null);
		//return hr.getText();
		//String ret=HttpHelper.postMap(url, params, null);
		return "";
	}
	
	 private static HashMap<String, String> toHashMap(Object object)  
	   {  
	       HashMap<String, String> data = new HashMap<String, String>();  
	       // 将json字符串转换成jsonObject  
	       JSONObject jsonObject = JSONObject.fromObject(object);  
	       Iterator it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           String value = (String) jsonObject.get(key);  
	           data.put(key, value);  
	       }  
	       return data;  
	   }  

	public static void main(String args[]) {
		try {
			Test test=new Test();
			String response=test.login();
			System.out.println("response:"+response);
			ClientAccount ca=(ClientAccount) JsonUtil.jsonToBean(response, ClientAccount.class);
			System.out.println(ca.getAccount()+":"+ca.getSessionID());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
