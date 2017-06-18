package cn.cellcom.czt.bus;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.encrypt.DESTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TTdcodeOrder;
import cn.cellcom.czt.wechat.HttpUtils;
import cn.cellcom.czt.wechat.UserInfo;
import cn.cellcom.czt.wechat.WechatTool;
import cn.cellcom.czt.wechat.XMLTool;
import cn.cellcom.web.spring.ApplicationContextTool;

public class OrderPayBus {

	private static final Log log = LogFactory.getLog(OrderPayBus.class);
	
	public String buildYZFForm(String url,String orderid,String amount){
		StringBuffer str = new StringBuffer();
		str.append("<form action=\"").append(url).append("\" method =\"post\"> ");
		str.append("<input type=\"hidden\" name=\"orderid\" value=\"").append(orderid).append("\" />");
		str.append("<input type=\"hidden\" name=\"amount\" value=\"").append(amount).append("\" />");
		str.append("</form>");
		str.append("<script>document.forms[0].submit();</script>");
		return str.toString();
			
	}

	public Data payOrderBefore(Data data, Map<String, String> params) {
		Data dataTemp = new Data(false,"下单失败");
		dataTemp = checkOrder(params,dataTemp);
		if (!dataTemp.isState()) {
			log.info("支付预处理参数验证:" + dataTemp.getMsg());
			data.setMsg(dataTemp.getMsg());
			return data;
		}
		String address = params.get("city") + params.get("area")
				+ params.get("address");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"insert into t_tdcode_order(orderid,unit_price,count,address,name,telephone,zipcode,state,openid,submit_time,spcode,configure,from_part,account,pay_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[] { params.get("orderid"),
							Integer.parseInt(params.get("unitPrice")), 1,
							address, params.get("name"),
							params.get("telephone"), params.get("zipcode"),
							"N", params.get("openid"),
							DateTool.getTimestamp(null), params.get("spcode"),
							params.get("configure"), params.get("fromPart"),
							params.get("account"),params.get("payType") });
			if("2".equals(params.get("payType"))){
				data.setObj(dataTemp.getObj());
			}
			data.setState(true);
			data.setMsg("下单成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
		return data;
	}

	private Data checkOrder(Map<String, String> params,Data data) {
		
		String city = params.get("city");
		String area = params.get("area");
		String address = params.get("address");
		String telephone = params.get("telephone");
		String zipcode = params.get("zipcode");
		String name = params.get("name");
		String payType = params.get("payType");
		String unitPrice = params.get("unitPrice");
		String orderid= params.get("orderid");
		if (StringUtils.isBlank(name)) {
			data.setMsg("姓名不能为空");
			return data;
		}
		if (name.length() > 10) {
			data.setMsg( "姓名长度需少于10个字符");
			return data;
		}
		if (StringUtils.isBlank(telephone)) {
			 data.setMsg("手机不能为空");
			 return data;
		}

		if (!"588".equals(unitPrice) && !"788".equals(unitPrice)) {
			data.setMsg("价格错误");
			return data;
		}
		// 目前 588 是成为的低配
		if ("588".equals(unitPrice)) {
			params.put("spcode", "O10");
			params.put("configure", "L");
		}
		// 目前 788 是元征的高配
		if ("788".equals(unitPrice)) {
			params.put("spcode", "O11");
			params.put("configure", "H");
		}

		String checkTel = PatternTool.checkStr(telephone, "^\\d{10,13}$",
				"手机号码格式错误");
		if (checkTel != null){
			data.setMsg(checkTel);
			return data;
		}
		if (StringUtils.isBlank(city)) {
			data.setMsg("城市不能为空");
			return data;
			
		}
		if (StringUtils.isBlank(area)) {
			data.setMsg("地区不能为空");
			return data;
		}
		if (address.length() < 5 || address.length() > 40){
			data.setMsg("地址输入太短或太长");
			return data;
		}
		if (StringUtils.isBlank(zipcode)) {
			data.setMsg("邮编不能为空");
			return data;
		}
		String checkZip = PatternTool.checkStr(zipcode, "^\\d{6}$", "邮编格式错误");
		if (checkZip != null){
			data.setMsg(checkZip);
			return data;
		}
		if ("1".equals(payType)) {
			data.setState(true);
			data.setMsg("");
			return data;
		} else if ("2".equals(payType)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("openid", params.get("openid"));
			map.put("ip", "183.63.133.137");
			map.put("unitPrice", unitPrice);
			map.put("orderid", orderid);
			//调用亿迅预支付接口
			Data dataMsg= payWX(map);
			return dataMsg;
		} else {
			data.setMsg("支付类型错误");
			return data;
		}
	}

	public Data payWX(Map<String, String> map) {
		Data data = new Data(false, "支付失败");
		String unitPrice = map.get("unitPrice");
		String openid = map.get("openid");
		String orderid =map.get("orderid");
		if (!"588".equals(unitPrice) && !"788".equals(unitPrice)) {
			data.setMsg("支付金额错误");
			return data;
		}
		if (StringUtils.isNotBlank(openid) && openid.length() == 28) {
			UserInfo userinfo = null;
			try {
				WechatTool wechatTool =new WechatTool();
				String accessToken=  wechatTool.getToken();
				userinfo = wechatTool.getUserinfo(accessToken,
						openid);
			} catch (Exception e) {
				e.printStackTrace();
				data.setMsg("未关注车主通公众号");
				return data;
			}
			int subscribe = userinfo.getSubscribe();
			if (subscribe == 0) {
				data.setMsg("未关注车主通公众号");
				return data;
			}
		} else {
			data.setMsg("未关注车主通公众号");
			return data;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("device_info", "WEB");
		params.put("body", "辘辘盒子");
		params.put("detail", "hg");
		params.put("attach", "attach");
		params.put("fee_type", "CNY");
		params.put("total_fee", "1");
		params.put("spbill_create_ip", "183.63.133.137");
		StringBuffer nitifyUrl= new StringBuffer();
		nitifyUrl.append(ConfLoad.getProperty("WECHAT_PAY_BACK_URL")).append("CZT/service/OrderPayAction_paySuccessByWX.do");
		nitifyUrl.append("?data=").append(orderid);
		
		params.put("notify_url",nitifyUrl.toString());
		params.put("trade_type", "JSAPI");
		params.put("time_start", "");
		params.put("time_expire", "");
		params.put("limit_pay", "");
		params.put("product_id", "");
		params.put("goods_tag", "");
		params.put("openid", openid);
		String paternerKey = ConfLoad.getProperty("WECHAT_PAY_PATERNER_KEY");
		String packageSign = getSign(params, paternerKey);
		try {
			if (StringUtils.isNotBlank(packageSign)) {
				params.put("sign", packageSign);
				params.put("tokenId", WechatTool.getTokenId());
				JSONObject req = JSONObject.fromObject(params);
				log.info("预支付参数:" + req.toString());
				System.out.println("预支付参数:" + req.toString());
				JSONObject result = HttpUtils.post(
						ConfLoad.getProperty("WECHAT_PAY_URL")
								+ "api/weixinPay/unifiedOrder", req);
				log.info(openid + "微信预支付结果:" + result);
				if (result != null && result.containsKey("code")&&result.getInt("code")==0) {
					data.setObj(result);
					data.setState(true);
					data.setMsg("预支付签名OK");
					return data;
				}
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	private String getSign(Map<String, String> map, String key)  {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + key;
		try {
			result = MD5.getMD5(result.getBytes("UTF-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public Data paySuccessByWX(Map map){
		Data dataMsg = new Data(false, "支付成功，但是回调失败");
		// 验证签名合法性
		//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
		String key = "1234567890QWERTYUIOPASDFGHJKLZXC";
		//自己业务增加的数据 及 sign 不参与签名;
		String sign =(String) map.get("sign");
		String data = (String)map.get("data");
		if(sign==null||data==null){
			dataMsg.setMsg("非正常回调，原因：非微信支付");
			return dataMsg;
		}
		map.remove("sign");
		map.remove("data");
		String checkSign = getSign(map, key);
		System.out.println("本地签名sign="+checkSign);
		System.out.println("微信签名sign="+sign);
		if(!checkSign.equals(sign)){
			dataMsg.setMsg("非正常回调，原因：签名错误");
			return dataMsg;
		}
		//数据合法，判断订单是否合法 
		String orderid =data;
		if(orderid==null){
			dataMsg.setMsg(dataMsg.getMsg()+"，订单不存在");
			return dataMsg;
		}
		OrderAlipayBus bus = new OrderAlipayBus();
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		TTdcodeOrder po = bus.queryByOrderid(jdbc, orderid);
		if(po==null){
			dataMsg.setMsg(dataMsg.getMsg()+"，订单不存在");
			return dataMsg;
		}
		
		
		if ("N".equals(po.getState())) {
			try {
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update t_tdcode_order set state='Y' where orderid = ? ",
						new String[] { orderid });
				String state = "1";
				
				Object[] values = { po.getOrderid(), po.getAccount(), po.getSpcode(),
						po.getConfigure(), po.getUnitPrice(), po.getCount(),
						po.getUnitPrice()*po.getCount(), 0, po.getFromPart(), state, po.getName(),
						po.getTelephone(), po.getAddress(), "", "", new Date(),
						"1","Y" };
				int counts = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_order(id,account,spcode,configure,unit_price,count,total,deal_count,from_part,state,receive_name,receive_telephone,receive_address,other_order_id,remark,submit_time,order_type,pay_state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
								values);
				if (counts > 0) {
					OrderBus orderBus = new OrderBus();
					orderBus.addOrderState(jdbc, po.getAccount(), state,null,
							po.getOrderid());
				}
				log.info(po.getAccount() + "新增订单" + po.getOrderid() + "结束。。。。");
				dataMsg.setState(true);
				dataMsg.setMsg("支付成功,订单操作OK");
				return dataMsg;
			} catch (Exception e) {
				e.printStackTrace();
				return dataMsg;
			}
		} else if("Y".equals(po.getState())) {
			log.info(orderid+"已支付成功,微信再次调用，不做处理");
			dataMsg.setState(true);
			dataMsg.setMsg("支付成功,订单操作OK");
			return dataMsg;
		}
		return dataMsg;
	}
	
	public static void main(String[] args) throws JDOMException, IOException {
		OrderPayBus bus = new OrderPayBus();
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("unitPrice", "588");
//		map.put("openid", "okU2Tjjidurcjd-peWw0PCnLtppo");
//		map.put("ip", "192.168.7.54");
//		map.put("orderid", "WX20151016134352853827");
//		System.out.println(JSONObject.fromObject(bus.payWX(map)).toString());
//		String str="<xml><appid><![CDATA[wx2dcc25fc80501211]]></appid><attach><![CDATA[attach]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><data><![CDATA[WX20151020165517152146]]></data><device_info><![CDATA[WEB]]></device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1220892601]]></mch_id><nonce_str><![CDATA[m8dn313asoyhrxmlzfwdhtn64k2cust6]]></nonce_str><openid><![CDATA[okU2Tjjidurcjd-peWw0PCnLtppo]]></openid><out_trade_no><![CDATA[20151020165516]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[679F1AA466D2003CD84A03AEEE8750AC]]></sign><time_end><![CDATA[20151020165526]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1006470755201510201267745637]]></transaction_id></xml>";
//		Map map = XMLTool.doXMLParse(str);
//		System.out.println(map.get("sign"));
//		
//		map.remove("sign");
//		map.remove("data");
//		System.out.println(map.toString());
//		String sign = bus.getSign(map, "1234567890QWERTYUIOPASDFGHJKLZXC");
//		System.out.println(sign);
		//Data dataMsg = bus.paySuccessByWX(map);
		
		//System.out.println(">>>>>>>"+sign);
		Map<String, String> map = new HashMap<String, String>();
		String sign = bus.getSign(map, "1234567890QWERTYUIOPASDFGHJKLZXC");
		System.out.println(sign);
	
	}

}
