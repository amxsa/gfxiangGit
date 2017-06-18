package com.gf.ims;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gf.ims.common.util.HttpClientCommonUtil;

import net.sf.json.JSONObject;

public class TestPay {

	@Test
	public void test(){
		Map<String,Object> header = new HashMap<String, Object>();
		header.put("token", "test");
		header.put("time_stamp", "13454354");
		Map<String,Object> body = new HashMap<String, Object>();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("header", header);
		data.put("body", body);
		String content = JSONObject.fromObject(data).toString();
		System.out.println("content:"+content);
		String url = "http://127.0.0.1:8090/ims/servlet/saveOrder";
		try {
			String responStr = HttpClientCommonUtil.sendMessageReq(url, content);
			System.out.println(responStr);
			JSONObject object = JSONObject.fromObject(responStr);
			String orderNo = (String) object.get("orderNo");
			JSONObject json=getWxPrepairid(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JSONObject getWxPrepairid(String orderNo) {
		Map<String,Object> header = new HashMap<String, Object>();
		Map<String,Object> body = new HashMap<String, Object>();
		Map<String,Object> data = new HashMap<String, Object>();
		body.put("pay_method", "2");
		body.put("orderNo", orderNo);
		body.put("appid", "wxe4219b2fbba0dd40");
		body.put("channel_flag", "3");
		header.put("token", "test");
		header.put("time_stamp", "13454354");
		data.put("body", body);
		data.put("header", header);
		String content = JSONObject.fromObject(data).toString();
		System.out.println("requestParams:"+content);
		String url = "http://127.0.0.1:8090/ims/servlet/getWxPrepayidRequest";
		try {
			String responStr = HttpClientCommonUtil.sendMessageReq(url, content);
			System.out.println(responStr);
			JSONObject object = JSONObject.fromObject(responStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
