package cn.cellcom.czt.service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.data.PrintTool;

import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.bus.OrderBus;
import cn.cellcom.czt.common.DataPo;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.common.ServiceTool;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class OrderServiceAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(OrderServiceAction.class);

	public String addOrder() throws IOException {

		String configure = getParameter("configure");

		String receiveName = getParameter("receiveName");
		String receiveTelephone = getParameter("receiveTelephone");
		String receiveAddress = getParameter("receiveAddress");
		String otherOrderId = getParameter("orderId");
		String timestamp = getParameter("timestamp");
		String authstring = getParameter("authstring");
		Map<String, String> params = new LinkedHashMap<String, String>();
		// 目前微信没有选择厂家的，默认元征
		params.put("spcode", "O11");
		params.put("configure", configure);
		params.put("unitPrice", getParameter("unitPrice"));
		params.put("count", getParameter("count"));
		params.put("receiveName", receiveName);
		params.put("receiveTelephone", receiveTelephone);
		params.put("receiveAddress", receiveAddress);
		params.put("receiveTelephone", receiveTelephone);
		params.put("otherOrderId", otherOrderId);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		params.put("ip", getRequest().getRemoteAddr());
		PrintTool.printLog("同步接口新增订单", params);
		params.put("fromPart", "W");
		DataPo dataPo = new DataPo("F", -4001);
		DataPo dataPoTemp = checkAddOrderParam(dataPo, params);
		Object obj = null;
		if (dataPoTemp != null) {
			obj = JSONObject.fromObject(dataPo);
		} else {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			Date date = new Date();

			TManager manager = new TManager();
			manager.setAccount("weixin");
			manager.setAreacode("000");
			OrderBus bus = new OrderBus();
			String id = bus.cerateOrderID(
					"WX", date);
			// 1:订单未处理
			String state = "1";
			Long unitPrice = Long.valueOf(params.get("unitPrice"));
			Integer count = Integer.parseInt(params.get("count"));
			Object[] values = { id, manager.getAccount(), params.get("spcode"),
					params.get("configure"), unitPrice, count,
					unitPrice * count, 0, params.get("fromPart"), state,
					params.get("receiveName"), params.get("receiveTelephone"),
					params.get("receiveAddress"), params.get("otherOrderId"),
					"", date, "1" };
			log.info(manager.getAccount() + "开始新增订单" + id);
			try {
				int counts = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_order(id,account,spcode,configure,unit_price,count,total,deal_count,from_part,state,receive_name,receive_telephone,receive_address,other_order_id,remark,submit_time,order_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
								values);
				if (counts > 0) {
					bus.addOrderState(jdbc, manager.getAccount(), state,null, id);
				}
				log.info(manager.getAccount() + "开始新增订单" + id + "结束。。。。");
				dataPo.setCode(0);
				dataPo.setState("S");
				dataPo.setMsg("订单录入成功");
				obj = JSONObject.fromObject(dataPo);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("写入工单操作失败", e);
				ServiceTool.errorDB("新增订单返回异常", getResponse(), e);
				return null;
			}
		}
		log.info("同步接口新增订单返回:" + obj.toString());
		PrintTool.print(getResponse(), obj, "json");
		return null;
	}

	private DataPo checkAddOrderParam(DataPo po, Map<String, String> params) {
		String configure = params.get("configure");
		String count = params.get("count");
		String receiveName = params.get("receiveName");
		String receiveTelephone = params.get("receiveTelephone");
		String receiveAddress = params.get("receiveAddress");
		String otherOrderId = params.get("otherOrderId");
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		if (StringUtils.isBlank(configure) || StringUtils.isBlank(count)
				|| StringUtils.isBlank(receiveName)
				|| StringUtils.isBlank(receiveTelephone)
				|| StringUtils.isBlank(receiveAddress)
				|| StringUtils.isBlank(otherOrderId)|| StringUtils.isBlank(timestamp)|| StringUtils.isBlank(timestamp)) {
			po.setCode(-4002);
			return po;
		}
		String temp = MD5.getMD5((otherOrderId + Env.SERVICE_SP + timestamp).getBytes());
		if (temp == null
				|| !authstring.toUpperCase().equals(temp.toUpperCase())) {
			po.setCode(-4003);
			return po;
		}
		if ("L".equals(configure) || "H".equals(configure)) {

		} else {
			po.setCode(-4004);
			return po;
		}

		if (StringUtils.isNotBlank(count)) {
			String checkCount = PatternTool.checkStr(count, "^[1-9]\\d*$",
					"订单数量错误");
			if (checkCount != null) {
				po.setCode(-4005);
				return po;
			}
		} else {
			po.setCode(-4005);
			return po;
		}

		if (StringUtils.isBlank(receiveName)) {
			po.setCode(-4006);
			return po;
		}

		if (StringUtils.isBlank(receiveTelephone)) {
			po.setCode(-4007);
			return po;
		}

		if (StringUtils.isBlank(receiveAddress)) {
			po.setCode(-4008);
			return po;
		}
		return null;
	}
}
