package com.gf.ims.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
/**
 * http请求工具
 * @author laizs
 * @time 2015-11-2上午11:47:44
 * @file HttpConnectUtil.java
 *
 */

public class HttpConnectUtil {
	private String charset;
	private String method;
	private static HttpConnectUtil instance = null;

	private HttpConnectUtil(String charset, String method) {
		this.charset = charset;
		this.method = method;
	}

	public final static HttpConnectUtil getInstance() {
		if (null == instance) {
			synchronized (HttpConnectUtil.class) {
				if (null == instance) {
					synchronized (HttpConnectUtil.class) {
						instance = new HttpConnectUtil("UTF-8", "POST");
					}
				}
			}
		}
		return instance;
	}
	/**
	 * 发送post数据
	 * @param urlStr
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String sendStrOfPost(String urlStr, String content) throws Exception{
		StringBuffer sResult = new StringBuffer();
		boolean bResult = false;
		String charsetName = charset;
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream httpIS = null;
		java.io.BufferedReader http_reader = null;
		String responseStr = null;
		try {
			url = new URL(urlStr);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod(this.method); // POST方式提交数据
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Length",
					String.valueOf(content.getBytes().length));
			PrintWriter out = null;
			out = new PrintWriter(new OutputStreamWriter(
					httpConnection.getOutputStream(), charsetName));// 此处改动
			// 发送请求
			out.print(content);
			out.flush();
			out.close();
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// 发送正常
				bResult = true;
				// 读取数据
				httpIS = httpConnection.getInputStream();
				http_reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(httpIS, charsetName));
				String line = null;
				while ((line = http_reader.readLine()) != null) {
					sResult.append(line);
				}
				responseStr = sResult.toString();
			} else {
				throw new RuntimeException("http请求（" + urlStr + "）未返回200的响应");
			}
		} finally {
			try {
				if (http_reader != null)
					http_reader.close();
				if (httpIS != null)
					httpIS.close();
				if (httpConnection != null)
					httpConnection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseStr;

	}

	public static void main(String[] args) {
		Map header = new HashMap();
		header.put("token", "test");
		header.put("time_stamp", "13454354");
		Map body = new HashMap();
		body.put("pageSize", "10");
		body.put("pageNum", "1");
		Map data = new HashMap();
		data.put("header", header);
		data.put("body", body);
		String content = JSONObject.fromObject(data).toString();
		System.out.println(content);
		String url = "http://192.168.0.121/searchPatientCaseList";
		try {
			System.out.println(HttpConnectUtil.getInstance().sendStrOfPost(url,
					content));
		} catch (Exception e) {
		}
		

	}
}
