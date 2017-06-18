package cn.cellcom.czt.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.ArrayTool;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.encrypt.DESTool;
import cn.cellcom.common.encrypt.MD5;

import cn.cellcom.czt.bus.OrderAlipayBus;
import cn.cellcom.czt.bus.OrderBus;
import cn.cellcom.czt.common.CityAreaTool;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TTdcodeOrder;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class OrderAlipayAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(OrderAlipayAction.class);

	/**
	 * 第一次进入获取某一个城市
	 * @return
	 * @throws IOException
	 */
	public String getPreCityArea() throws IOException{
		getRequest().setAttribute("citys",Env.CITY_AREA.keySet());
		String[] areas = CityAreaTool.getArea(Env.CITY_AREA.keySet().iterator().next());
		getRequest().setAttribute("areas",areas);
		return "addTdCodeOrder";
	}
	
	public String getCityArea() throws IOException{
		String city = getParameter("city");
		String[] areas = CityAreaTool.getArea(city);
		if(areas!=null){
			JSONObject obj = new JSONObject();
			for(int i=0;i<areas.length;i++){
				obj.put(areas[i], areas[i]);
			}
			PrintTool.print(getResponse(), ArrayTool.getStrByArray(areas, ","), null);
		}else{
			PrintTool.print(getResponse(), "", null);
		}
		return null;
	}
	
	public String addTdCodeOrder() throws IOException {
		String city = getParameter("city");
		String openid = getParameter("openid");
		String area = getParameter("area");
		String address = getParameter("address");
		String name = getParameter("name");
		String telephone = getParameter("telephone");
		String zipcode = getParameter("zipcode");
		String unitPrice = getParameter("unitPrice");
		
		String orderid = new OrderBus().cerateOrderID("WX", new Date());
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("openid", openid);
		params.put("city", city);
		params.put("area", area);
		params.put("address", address);
		params.put("name", name);
		params.put("telephone", telephone);
		params.put("zipcode", zipcode);
		params.put("orderid", orderid);
		params.put("unitPrice", unitPrice);
		params.put("fromPart", "W");
		params.put("account", "weixin");
		if(!"588".equals(unitPrice)&&!"788".equals(unitPrice)){
			getRequest().setAttribute("result", "价格错误");
			return "error";
		}
		//目前 588 是成为的低配
		if("588".equals(unitPrice)){
			params.put("spcode", "O10");
			params.put("configure", "L");
		}
		//目前 788 是元征的高配
		if("788".equals(unitPrice)){
			params.put("spcode", "O11");
			params.put("configure", "H");
		}
		
		
		
		Data data = new Data(false, "下单失败");
		OrderAlipayBus bus = new OrderAlipayBus();
		data = bus.addTdCodeOrder(data, params);
		if (data.isState()) {
			String strHtml = bus.buildForm(
					ConfLoad.getProperty("ALIPAY_BACK_URL")
							+ "service/OrderAlipayAction_pay.do", orderid,
							unitPrice);
			PrintTool.print(getResponse(), strHtml, null);
			return null;
		} else {
			getRequest().setAttribute("result", data.getMsg());
			return "error";
		}
	}

	public String pay() throws IOException {
		String orderid = getParameter("orderid");
		String amount = getParameter("amount");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		OrderAlipayBus bus = new OrderAlipayBus();
		TTdcodeOrder po = bus.queryByOrderid(jdbc, orderid);
		if (po == null || "Y".equals(po.getState())) {
			getRequest().setAttribute("result", "订单不存在或已支付");
			return "error";
		}
		HttpUrlClient client = new HttpUrlClient();
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * 支付账号钱到赛宸子账号用 key=88bfcebf98b5683a1d760f1cbc18df902，decryptkey=4ed895be6006a0e589f887505（目前用与车主通微信公众号购买LuLu盒子）
		 * 后来 广州渠道商通过后台提交订单必须先走支付，钱直接到广州号百翼支付账号里面
		 * key = b764776630a97a476b08e49b901ecb70 ，decryptkey = 56e87f96998e65531b916f3113b9e5da
		 */
		map.put("key", ConfLoad.getProperty("ALIPAY_KEY"));
		map.put("ordernum", orderid);
		map.put("amount", amount);
		map.put("desc", "LuLu盒子");
		StringBuffer backUrl= new StringBuffer();
		backUrl.append(ConfLoad.getProperty("ALIPAY_BACK_URL"));
		backUrl.append("service/OrderAlipayAction_paySuccess.do?data=");
		try {
			//订单加密  时间+@+订单号  DES加密 秘钥 是：CZT@LULU
			String orderidDes = DESTool.encryptDES(String.valueOf(System.currentTimeMillis())+"@"+orderid, Env.SERVICE_SP);
			backUrl.append(URLEncoder.encode(orderidDes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("backurl",backUrl.toString() );
		StringBuffer signStr = new StringBuffer();
		signStr.append(map.get("key")).append(map.get("ordernum"))
				.append(map.get("amount")).append(map.get("desc"));
		signStr.append(map.get("backurl")).append(
				ConfLoad.getProperty("ALIPAY_DECRYPT_KEY"));
		String sign = MD5.getMD5(signStr.toString().getBytes("utf-8"));
		map.put("sign", sign);
		String iswap = getParameter("iswap");
		if(iswap!=null){
			map.put("iswap", iswap);
		}else{
			map.put("iswap", "1");
		}
		
		String str = client.doPost("支付模块",
				ConfLoad.getProperty("ALIPAY_URL"), map, "UTF-8",
				10000, true);
		log.info("支付模块返回结果" + str);
		PrintTool.print(getResponse(), str, null);
		return null;
	}

	public String paySuccess() {
		String data = getParameter("data");
		log.info("微信支付data："+data);
		if(data==null){
			getRequest().setAttribute("result", "订单不存在");
			return "error";
		}
		
		String orderid=null;
		try {
			data = DESTool.decryptDES(data, Env.SERVICE_SP);
			if(data!=null&&data.indexOf("@")>-1)
				orderid = data.split("@")[1];
			else{
				getRequest().setAttribute("result", "订单不存在");
				return "error";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			getRequest().setAttribute("result", "订单不存在");
			return "error";
		}
		if(orderid==null){
			getRequest().setAttribute("result", "订单不存在");
			return "error";
		}
		OrderAlipayBus bus = new OrderAlipayBus();
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		TTdcodeOrder po = bus.queryByOrderid(jdbc, orderid);
		if(po==null){
			getRequest().setAttribute("result", "订单不存在");
			return "error";
		}
		// 支付平台后台（必须调用）和前台调用（用户关了就一次，用户点返回商户，就两次）
		if ("N".equals(po.getState())) {
			log.info(orderid+"已支付成功(后台调用)");
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
				getRequest().setAttribute("result", "支付成功");
				getRequest().setAttribute("url", getRequest().getContextPath()+"/alipay/showProduct.jsp?openid="+po.getOpenid());
				return "paySuccess";
			} catch (Exception e) {
				e.printStackTrace();
				getRequest().setAttribute("result", "支付成功，但订单出现错误，请联系客服人员");
				return "error";
			}
		} else if("Y".equals(po.getState())) {
			log.info(orderid+"已支付成功(前台调用)");
			getRequest().setAttribute("result", "支付成功");
			getRequest().setAttribute("url", getRequest().getContextPath()+"/alipay/showProduct.jsp?openid="+po.getOpenid());
			return "paySuccess";
		}
		return "error";
	}

}
