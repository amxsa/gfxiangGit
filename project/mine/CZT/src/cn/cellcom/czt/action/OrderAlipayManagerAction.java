package cn.cellcom.czt.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.encrypt.DESTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.bus.OrderAlipayBus;
import cn.cellcom.czt.bus.OrderBus;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TTdcodeOrder;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class OrderAlipayManagerAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(OrderAlipayManagerAction.class);
	public String addOrderAlipay() throws IOException {
		String spcode = getParameter("spcode");
		String configure = getParameter("configure");
		String unitPrice = getParameter("unitPrice");
		String count = getParameter("count");
		String fromPart = getParameter("fromPart");
		String receiveName = getParameter("receiveName");
		String receiveTelephone = getParameter("receiveTelephone");
		String receiveAddress = getParameter("receiveAddress");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("spcode", spcode);
		params.put("configure", configure);
		params.put("unitPrice", unitPrice);
		params.put("count", count);
		params.put("fromPart", fromPart);
		params.put("name", receiveName);
		params.put("telephone", receiveTelephone);
		params.put("address", receiveAddress);
		TManager manager = (TManager) getSession().getAttribute("login");
		String orderid = new OrderBus().cerateOrderID(
				Env.AREACODE.get(manager.getAreacode())[1], new Date());
		params.put("orderid", orderid);
		params.put("account", manager.getAccount());
		OrderAlipayBus bus = new OrderAlipayBus();
		Data data = new Data(false, "下单失败");
		bus.addOrderAlipay(data, params);
		int amount = Integer.valueOf(unitPrice)*Integer.parseInt(count);
		
		if (data.isState()) {
			String strHtml = bus.buildForm(
					ConfLoad.getProperty("ALIPAY_BACK_URL")
							+ "manager/OrderAlipayManagerAction_pay.do?iswap=0", orderid,
					String.valueOf(amount));
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
		map.put("key", "b764776630a97a476b08e49b901ecb70");
		map.put("ordernum", orderid);
		map.put("amount", amount);
		map.put("desc", "LuLu盒子");
		StringBuffer backUrl= new StringBuffer();
		backUrl.append(ConfLoad.getProperty("ALIPAY_BACK_URL"));
		backUrl.append("manager/OrderAlipayManagerAction_paySuccess.do?data=");
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
		signStr.append(map.get("backurl")).append("56e87f96998e65531b916f3113b9e5da");
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
		log.info("后台支付data："+data);
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
				getRequest().setAttribute("url", getRequest().getContextPath()+"/manager/addAlipayOrder.jsp");
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				getRequest().setAttribute("result", "支付成功，但订单出现错误，请联系客服人员");
				return "error";
			}
		} else if("Y".equals(po.getState())) {
			log.info(orderid+"已支付成功(前台调用)");
			getRequest().setAttribute("result", "支付成功");
			getRequest().setAttribute("url", getRequest().getContextPath()+"/manager/addAlipayOrder.jsp");
			return "success";
		}
		return "error";
	}
}
