package com.gf.ims.xcx.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUrlClient {

	private static final Log log = LogFactory.getLog(HttpUrlClient.class);

	/**
	 * post请求返回字符串
	 * 
	 * @param module
	 *            模块
	 * @param url
	 *            访问地址
	 * @param params
	 *            参数
	 * @param coding
	 *            编码格式
	 * @param timeout
	 *            超时时间 如果等于0，不设置
	 * 
	 * @return
	 */
	public static String doPost(String model, String url,
			Map<String, String> params, String coding, int timeout,
			boolean isLog) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				coding);
		PostMethod method = new PostMethod(url);
		StringBuffer bufferLog = new StringBuffer(url);
		if (params != null && !params.isEmpty()) {
			Iterator<Entry<String, String>> iterator = params.entrySet()
					.iterator();
			Entry<String, String> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				method.addParameter(entry.getKey(),
						entry.getValue() == null ? "" : entry.getValue());
				bufferLog.append(entry.getKey()).append("=")
						.append(entry.getValue()).append("&");
			}
		}
		if (isLog)
			log.info(model + "请求的URL:"
					+ StringUtils.removeEnd(bufferLog.toString(), "&"));

		try {
			HttpConnectionManagerParams managerParams = client
					.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(timeout);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(8000);
			int stateCode = client.executeMethod(method);
			InputStream ins = null;
			BufferedReader br = null;
			if (stateCode == HttpStatus.SC_OK) {
				ins = method.getResponseBodyAsStream();
				br = new BufferedReader(new InputStreamReader(ins, coding));

				StringBuffer responseText = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					responseText.append(line);
				}
				if (br != null)
					br.close();
				if (ins != null)
					ins.close();
				return responseText.toString();
			} else {
				log.error(">>>>>>> post requeset stateCode=" + stateCode);
				return null;
			}
		} catch (HttpException e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("", e);
			return null;
		} finally {
			method.releaseConnection();

		}
	}

	
	
	public static String doGet(String model, String url,
			Map<String, String> params, String coding, int timeout,
			boolean isLog) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				coding);
		StringBuffer str= new StringBuffer(url);
		if (params != null && !params.isEmpty()) {
			if(url.indexOf("?")>-1){
			}else{
				str.append("?");
			}
			Iterator<Entry<String, String>> iterator = params.entrySet()
					.iterator();
			Entry<String, String> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				str.append(entry.getKey()).append("=")
				.append(entry.getValue()).append("&");
			}
			url = StringUtils.removeEnd(str.toString(), "&");
		}
		if(isLog)
			log.info(url);
		GetMethod method = new GetMethod(url);
		log.info(model+"请求GET地址"+url);
		try {
			HttpConnectionManagerParams managerParams = client
					.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(timeout);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(8000);
			int stateCode = client.executeMethod(method);
			InputStream ins = null;
			BufferedReader br = null;
			if (stateCode == HttpStatus.SC_OK) {
				ins= method.getResponseBodyAsStream();
				 br = new BufferedReader(new InputStreamReader(
						ins, coding));
				
				StringBuffer responseText = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					responseText.append(line);
				}
				if (br != null)
					br.close();
				if (ins != null)
					ins.close();
				return responseText.toString();
			} else {
				log.error(">>>>>>> get requeset stateCode="+stateCode);
				return null;
			}
		} catch (HttpException e) {
			e.printStackTrace();
			log.error("",e);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("",e);
			return null;
		} finally {
			method.releaseConnection();
			
		}
	}
	public static void main(String[] args) {
		HttpUrlClient client = new HttpUrlClient();
		JSONObject params = new JSONObject();
//
//		params.put("account", "api_hgxy");
//		params.put("password", "api_hgxy");
//		params.put("accountType", "1");
//		String result = client.post("元征获取tokenId", "http://59.41.186.76:13278/api/sys/login", params, 20000, false);
//		System.out.println(result);
	}
}