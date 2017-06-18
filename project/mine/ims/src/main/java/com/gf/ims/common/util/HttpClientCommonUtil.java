package com.gf.ims.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

/**
 * HttpClient收发数据工具
 * @author viliam 2014-05-20
 *
 */
public class HttpClientCommonUtil {
	
	/**
	 * 终端向服务器发送请求,携带XML数据内容
	 * @param message
	 * @return
	 */
	public static String sendMessageReq(String requestUrl,String message){
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(requestUrl);
		
		//将消息内容转成字节码传输
		byte[] messageToByte = null;
		if(message == null){
			messageToByte = new byte[0];
		}else{
			try {
				messageToByte = message.getBytes("utf8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		//字节请求实体
		RequestEntity entity = new ByteArrayRequestEntity(messageToByte);
		postMethod.setRequestEntity(entity);
		
		try {
			//返回响应状态码
			int backCode =  httpClient.executeMethod(postMethod);
			System.out.println("服务端返回响应码："+backCode);
			//服务端响应的结果数据
			String backResult = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			return backResult;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
	}

	/**
	 * 输出内容
	 */
	public static void printInfo(String info){
		System.out.println("********\n\t" + info + "\n********");
	}
	
	
	/**
	 * 服务端响应客户端最后的消息内容
	 * @param result
	 * @return
	 */
	public static void lastResultConvert(String result){
		int leng=0;
		leng=result.length();
		if(leng==0){
			printInfo("客户端收到服务器响应:空消息");
		}
		else{
			printInfo("客户端收到服务器响应:"+result);
		}
	}
}
