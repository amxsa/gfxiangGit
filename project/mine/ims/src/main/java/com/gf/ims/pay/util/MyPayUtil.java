package com.gf.ims.pay.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付请求简单工具类
 * 
 * @author lzs
 * 
 */
public class MyPayUtil {
	/**
	 * 获取支付请求GET过来反馈信息
	 * 封装到Map对象中
	 * @param request
	 * @return
	 */
	public final static Map<String, String> getParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr =new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}
		return params;
	}
	
	/** 
     * 除去请求参数的Map对象中的空值和签名参数
     * sign 和sign_type 参数都被出去
     * @param 请求参数Map
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paramsFilter(Map<String, String> paramsMap) {

        Map<String, String> result = new HashMap<String, String>();
        if (paramsMap == null || paramsMap.size() <= 0) {
            return result;
        }

        for (String key : paramsMap.keySet()) {
            String value = paramsMap.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    /**
     * 根据请求参数信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param secrect_key md5签名的密钥
     * @return 生成的签名结果
     */
    public final static String getMysign(Map<String, String> Params,String secrect_key) {
        Map<String, String> sParaNew = paramsFilter(Params);//过滤空值、sign与sign_type参数
        //获得签名结果
        String prestr = createLinkString(sParaNew);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + secrect_key; //把拼接后的字符串再与安全校验码直接连接起来
        String mysign = AlipayMd5Encrypt.md5(prestr);
        return mysign;
    }
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public final static String createLinkString(Map<String, String> params) {

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

}
