package com.gf.ims.pay.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class WXPayUtil {

	
	
	/**
	 * 判断微信异步通知的签名是否合法
	 * @param reqMap
	 * @param sign
	 * @return
	 */
	public static boolean checkSign(Map<String,String> reqMap,String wxKey){
		String sign = reqMap.get("sign");//得到微信异步通知过来的签名
		
		Map<String, String> fileterMap = paraFilter(reqMap);//过滤参数集合中的签名参数
		System.out.println("过滤掉sign之后的信息："+JSONObject.fromObject(fileterMap).toString());
		String mySign = getMySign(fileterMap,wxKey).toUpperCase();
		System.out.println("本地得到我的签名："+mySign);
		System.out.println("微信返回的异步通知签名："+sign);
		if(mySign.equals(sign)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 读取请求为xml
	 * @return
	 */
	public static Map<String,String> readRequestToXml(HttpServletRequest request){
		//将微信异步通知的请求读取成一个字符串
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String len = null;
			while((len = br.readLine())!= null){
				sb.append(len +"\n");
			}
			//将xml格式的字符串读取到map中
			String xmlText = sb.toString();
			Document postXml = DocumentHelper.parseText(xmlText);
		    Element root=postXml.getRootElement();
		    Map<String,String> eleMap = new HashMap<String,String>();
		    for(Iterator it = root.elementIterator();it.hasNext();){
		    	Element element = (Element) it.next();
		    	eleMap.put(element.getName(), element.getText());
		    }
		    
		    return eleMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String readMapToXml(Map<String,String> map){
		Set<Entry<String,String>> set = map.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		
		Element rootElement = DocumentHelper.createElement("xml");
		Document document = DocumentHelper.createDocument(rootElement);
		while(iterator.hasNext()){
			Entry<String, String> next = iterator.next();
			String key = next.getKey();
			String value = next.getValue();
			
			rootElement.addElement(key).addText(value);
		}
		
		return document.asXML();
	}
	
	/** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    public static String getMySign(Map<String,String> reqMap,String wxKey){
    	String signStr = createLinkString(reqMap);//得到排序后拼接的参数字符串
    	signStr = signStr +"&key="+wxKey;//拼接微信的appKey
    	String mySign = MD5.getMessageDigest(signStr.getBytes());
    	return mySign;
    }
    
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
	 * 生成签名参数
	 *@author laizs
	 *@param params
	 *@param api_key
	 *@return
	 */
	public static String genPackageSign(List<NameValuePair> params,String api_key) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(api_key);
//		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		String packageSign=MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
		return packageSign;
	}
	/**
	 * 参数列表封装成xml格式
	 *@author laizs
	 *@param params
	 *@return
	 */
	public static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");
			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * 生成随机字符串
	 *@author laizs
	 *@return
	 */
	public static  String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	/**
	 * 将xml格式的字符串转成map对象
	 *@author laizs
	 *@param content
	 *@return
	 *@throws DocumentException
	 */
	public static  Map<String,String> decodeXml(String content) throws DocumentException {
		
		Map<String, String> xml = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(content);
		Element rootE=document.getRootElement();
		for(Iterator i =rootE.elementIterator();i.hasNext();){
		    	Element propertyNode=(Element) i.next();
		    	xml.put(propertyNode.getName(), propertyNode.getText());
		}
		return xml;

	}
	/**
	 * 生成随机字符串
	 *@author laizs
	 *@param length
	 *@return
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
	/**
	 * 生成时间戳
	 *@author laizs
	 *@return
	 */
	public static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
}
