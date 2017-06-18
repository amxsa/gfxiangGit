package cn.cellcom.blank.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cellcom.blank.common.AlipayFunction;



public class AlipayBus {
	/*
	 * 功能：构造表单提交HTML
	 * 
	 * @param partner 合作身份者ID
	 * 
	 * @param seller_email 签约支付宝账号或卖家支付宝帐户
	 * 
	 * @param return_url 付完款后跳转的页面 要用 以http开头格式的完整路径，不允许加?id=123这类自定义参数
	 * 
	 * @param notify_url 交易过程中服务器通知的页面 要用 以http开格式的完整路径，不允许加?id=123这类自定义参数
	 * 
	 * @param show_url 网站商品的展示地址，不允许加?id=123这类自定义参数
	 * 
	 * @param out_trade_no 请与贵网站订单系统中的唯一订单号匹配
	 * 
	 * @param subject 订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
	 * 
	 * @param body 订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里
	 * 
	 * @param price 订单总金额，显示在支付宝收银台里的“商品单价”里
	 * 
	 * @param logistics_fee 物流费用，即运费。
	 * 
	 * @param logistics_type 物流类型，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
	 * 
	 * @param logistics_payment
	 * 物流支付方式，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
	 * 
	 * @param quantity 商品数量，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品。
	 * 
	 * @param receive_name 收货人姓名，如：张三
	 * 
	 * @param receive_address 收货人地址，如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号
	 * 
	 * @param receive_zip 收货人邮编，如：123456
	 * 
	 * @param receive_phone 收货人电话号码，如：0571-81234567
	 * 
	 * @param receive_mobile 收货人手机号码，如：13312341234
	 * 
	 * @param logistics_fee_1 第二组物流费用，即运费。
	 * 
	 * @param logistics_type_1 第二组物流类型，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
	 * 
	 * @param logistics_payment_1
	 * 第二组物流支付方式，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
	 * 
	 * @param logistics_fee_2 第三组物流费用，即运费。
	 * 
	 * @param logistics_type_2 第三组物流类型，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
	 * 
	 * @param logistics_payment_2
	 * 第三组物流支付方式，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
	 * 
	 * @param buyer_email 默认买家支付宝账号
	 * 
	 * @param discount 折扣，是具体的金额，而不是百分比。若要使用打折，请使用负数，并保证小数点最多两位数
	 * 
	 * @param input_charset 字符编码格式 目前支持 GBK 或 utf-8
	 * 
	 * @param key 安全校验码
	 * 
	 * @param sign_type 签名方式 不需修改
	 * 
	 * @return 表单提交HTML文本
	 */
	public static String BuildForm(String partner, String seller_email,
			String return_url, String notify_url, String show_url,
			String out_trade_no, String subject, String body, String price,
			String logistics_fee, String logistics_type,
			String logistics_payment, String quantity, String receive_name,
			String receive_address, String receive_zip, String receive_phone,
			String receive_mobile, String logistics_fee_1,
			String logistics_type_1, String logistics_payment_1,
			String logistics_fee_2, String logistics_type_2,
			String logistics_payment_2, String buyer_email, String discount,
			String input_charset, String key, String sign_type) {
		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("service", "trade_create_by_buyer");
		sPara.put("payment_type", "1");
		sPara.put("partner", partner);
		sPara.put("seller_email", seller_email);
		sPara.put("return_url", return_url);
		sPara.put("notify_url", notify_url);
		sPara.put("_input_charset", input_charset);
		sPara.put("show_url", show_url);
		sPara.put("out_trade_no", out_trade_no);
		sPara.put("subject", subject);
		sPara.put("body", body);
		sPara.put("price", price);
		sPara.put("logistics_fee", logistics_fee);
		sPara.put("logistics_type", logistics_type);
		sPara.put("logistics_payment", logistics_payment);
		sPara.put("quantity", quantity);
		sPara.put("receive_name", receive_name);
		sPara.put("receive_address", receive_address);
		sPara.put("receive_zip", receive_zip);
		sPara.put("receive_phone", receive_phone);
		sPara.put("receive_mobile", receive_mobile);
		sPara.put("logistics_fee_1", logistics_fee_1);
		sPara.put("logistics_type_1", logistics_type_1);
		sPara.put("logistics_payment_1", logistics_payment_1);
		sPara.put("logistics_fee_2", logistics_fee_2);
		sPara.put("logistics_type_2", logistics_type_2);
		sPara.put("logistics_payment_2", logistics_payment_2);
		sPara.put("buyer_email", buyer_email);
		sPara.put("discount", discount);

		Map sParaNew = AlipayFunction.ParaFilter(sPara); // 除去数组中的空值和签名参数
		String mysign = AlipayFunction.BuildMysign(sParaNew, key);// 生成签名结果

		StringBuffer sbHtml = new StringBuffer();
		List keys = new ArrayList(sParaNew.keySet());
		String gateway = "https://www.alipay.com/cooperate/gateway.do?";

		// GET方式传递
		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\""
				+ gateway
				+ "_input_charset="
				+ input_charset
				+ "\" method=\"get\">");
		// POST方式传递（GET与POST二必选一）
		// sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\""
		// + gateway + "_input_charset=" + input_charset +
		// "\" method=\"post\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sParaNew.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name
					+ "\" value=\"" + value + "\"/>");
		}
		sbHtml.append("<input type=\"hidden\" name=\"sign\" value=\"" + mysign
				+ "\"/>");
		sbHtml.append("<input type=\"hidden\" name=\"sign_type\" value=\""
				+ sign_type + "\"/>");

		// submit按钮控件请不要含有name属性
		// sbHtml.append("<input type=\"submit\" value=\"支付宝确认付款\"></form>");

		sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
		return sbHtml.toString();
	}

	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 整合Alipay发货信息同步 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 功能：把数组所有元素按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @param input_charset
	 *            编码格式
	 * @return 拼接后字符串
	 */
	public static String CreateLinkString_urlencode(Map params,
			String input_charset) {
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);

			try {
				prestr = prestr + key + "="
						+ URLEncoder.encode(value, input_charset) + "&";
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		}

		return prestr;
	}

	/**
	 * 功能：远程xml解析
	 * 
	 * @param partner
	 *            合作身份者ID
	 * @param trade_no
	 *            支付宝交易号。它是登陆支付宝网站在交易管理中查询得到，一般以8位日期开头的纯数字（如：20100419XXXXXXXXXX）
	 * @param logistics_name
	 *            物流公司名称
	 * @param invoice_no
	 *            物流发货单号
	 * @param transport_type
	 *            物流发货时的运输类型，三个值可选：POST（平邮）、EXPRESS（快递）、EMS（EMS）
	 * @param seller_ip
	 *            卖家本地电脑IP地址
	 * @param input_charset
	 *            字符编码格式 目前支持 GBK 或 utf-8
	 * @param key
	 *            安全校验码
	 * @param sign_type
	 *            签名方式 不需修改
	 * @return 获得解析结果
	 */
	public static String PostXml(String partner, String trade_no,
			String logistics_name, String invoice_no, String transport_type,
			String seller_ip, String input_charset, String key, String sign_type)
			throws Exception {
		Map sPara = new HashMap();
		sPara.put("service", "send_goods_confirm_by_platform");
		sPara.put("partner", partner);
		sPara.put("trade_no", trade_no);
		sPara.put("logistics_name", logistics_name);
		sPara.put("invoice_no", invoice_no);
		sPara.put("transport_type", transport_type);
		sPara.put("seller_ip", seller_ip);
		sPara.put("_input_charset", input_charset);

		Map sParaNew = AlipayFunction.ParaFilter(sPara); // 除去数组中的空值和签名参数
		String mysign = AlipayFunction.BuildMysign(sParaNew, key);// 生成签名结果

		sParaNew.put("sign", mysign);
		sParaNew.put("sign_type", "MD5");

		String strUrl = "https://www.alipay.com/cooperate/gateway.do?_input_charset=utf-8";
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		os.write(CreateLinkString_urlencode(sParaNew, input_charset).getBytes(
				"utf-8"));
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		String xmlResult = "";
		while ((line = br.readLine()) != null) {
			xmlResult += "\n" + line;
		}
		br.close();

		return xmlResult;
	}
}
