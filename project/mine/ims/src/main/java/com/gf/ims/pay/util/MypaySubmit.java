package com.gf.ims.pay.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/* *
 *类名：MypaySubmit
 *功能：接口请求提交类
 *说明：
 */
public class MypaySubmit {
	public static Logger log=Logger.getLogger(MypaySubmit.class);
    /**
     * 对请求的参数数组生成MD5签名，并将签名参数添加到请求的数组中
     * @param sParaTemp 请求前的参数数组
     * @param secrect_key MD5密钥
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,String secrect_key) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara =MyPayUtil.paramsFilter(sParaTemp);
        //生成签名结果
        String mysign =MyPayUtil.getMysign(sPara, secrect_key);
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", "MD5");
        return sPara;
    }

    /**
     * 构造提交表单HTML数据
     * @param sParaTemp 请求参数数组
     * @param gateway 网关地址
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @param secrect_key MD5签名的密钥
     * @return 提交表单HTML文本
     */
    public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod,
                                   String strButtonName,String secrect_key) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp,secrect_key);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + gateway
                      +  "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }

    /**
     * 构造模拟远程HTTP的POST请求，获取请求响应结果
     * @param sParaTemp 请求参数数组
     * @param gateway 网关地址
     * @param secrect_key MD5签名的密钥
     * @return 返回请求响应结果
     * @throws Exception
     */
    public static String sendPostInfo(Map<String, String> sParaTemp, String gateway, String secrect_ke)
                                                                                    throws Exception {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp,secrect_ke);

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(AlipayConfig.input_charset);
        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(gateway);
        HttpResponse response = httpProtocolHandler.execute(request);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        log.info("服务器发送post请求<|>gateway<|>"+gateway+"<|>params<|>"
    			+sParaTemp+"<|>response<|>"+strResult);
        return strResult;
    }
    /**
	 * 将支付结果异步通知数据反馈给合作伙伴
	 * 只有合作伙伴返回success这7个字符才认为合作伙伴收到支付结果
	 * @param params
	 * @param partner_notify_url
	 * @param partner_secret_key
	 * @return
	 */
	public final static boolean sendNotifyToPartner(Map<String,String> params,String partner_notify_url,String partner_secret_key){
		try {
			//通知合作方
			String responseStr=sendPostInfo(params, partner_notify_url, partner_secret_key);
			if(StringUtils.isNotBlank(responseStr)&&responseStr.equals("success")){
				  log.info("服务器同步交易结果给合作伙伴<|>partner_notify_url<|>"+partner_notify_url+"<|>params<|>"
			    			+params+"<|>结果<|>true<|>true表示合伙伙伴收到交易结果并处理完成，false表示合作伙伴为收到交易结果或异常");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 log.info("服务器同步交易结果给合作伙伴<|>partner_notify_url<|>"+partner_notify_url+"<|>params<|>"
	    			+params+"<|>结果<|>false<|>true表示合伙伙伴收到交易结果并处理完成，false表示合作伙伴为收到交易结果或异常");
		return false;
	}
}
