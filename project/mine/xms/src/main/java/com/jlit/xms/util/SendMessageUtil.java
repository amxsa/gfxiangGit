package com.jlit.xms.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

public class SendMessageUtil {
	
	private static final Log log = LogFactory.getLog(SendMessageUtil.class);

	
	
	
	public static void sendMessageToXMPP(String account,String contents,String subjects){
		
		List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
		//参数
		NameValuePair to=new BasicNameValuePair("to", account);
		NameValuePair body=new BasicNameValuePair("body", contents);
		NameValuePair subject=new BasicNameValuePair("subject", "");
		
		paramsList.add(to);
		paramsList.add(body);
		paramsList.add(subject);
		//		http://127.0.0.1:9090/plugins/servlets/pushsysmsg
		String url="http://"+StaticValue.xmpp_server_address + ":" + StaticValue.xmpp_server_port +"/plugins/servlets/pushsysmsg";
		
		log.info("设备上报的用户体验数据,推送到XMPP统一处理:"+contents + " url:"+url);
		
		try {
			HttpClientUtil.get(url, paramsList);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static void sendMessageForAccountsToXMPP(String account,String contents,String subjects){
		
		List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
		//参数
		NameValuePair to=new BasicNameValuePair("to", account);
		NameValuePair body=new BasicNameValuePair("body", contents);
		NameValuePair subject=new BasicNameValuePair("subject", "");
		
		paramsList.add(to);
		paramsList.add(body);
		paramsList.add(subject);
		//		http://127.0.0.1:9090/plugins/servlets/pushsysmsg
		String url="http://"+StaticValue.xmpp_server_address + ":" + StaticValue.xmpp_server_port +"/plugins/servlets/pushmultisysmsg";
		
		log.info("设备上报的公告数据,推送到XMPP统一处理:"+contents + " url:"+url);
		
		try {
			HttpClientUtil.get(url, paramsList);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
