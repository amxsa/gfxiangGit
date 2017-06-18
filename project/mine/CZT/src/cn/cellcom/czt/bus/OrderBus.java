package cn.cellcom.czt.bus;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.SpTdcode;

import cn.cellcom.czt.po.TBindDeviceFluxcard;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TOrder;
import cn.cellcom.czt.po.TOrderState;

import cn.cellcom.web.spring.ApplicationContextTool;

public class OrderBus {
	private static final Log log = LogFactory.getLog(OrderBus.class);

	public Data addOrder(TManager manager, Map<String, String> params) {
		Data data = new Data(false, "新增订单失败");
		String checkParams = checkAddOrderParam(params);
		if (checkParams == null) {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			Date date = null;
			String orderTime = params.get("orderTime");
			if (StringUtils.isNotBlank(orderTime)) {
				date = DateTool.formateString2Time(orderTime,
						"yyyy-MM-dd HH:mm:ss");
			} else {
				date = new Date();
			}
			String id = cerateOrderID(
					Env.AREACODE.get(manager.getAreacode())[1], date);
			// H:新增订单,需号百人员审核
			String state = "H";
			// 管理员无需审核 直接到预备组装
			if (manager.getRoleid() == 0) {
				state = "5";
			}
			Long unitPrice = Long.valueOf(params.get("unitPrice"));
			Integer count = Integer.parseInt(params.get("count"));
			Object[] values = { id, manager.getAccount(), params.get("spcode"),
					params.get("configure"), unitPrice, count,
					unitPrice * count, 0, params.get("fromPart"), state,
					params.get("receiveName"), params.get("receiveTelephone"),
					params.get("receiveAddress"), params.get("otherOrderId"),
					params.get("remark"), date, params.get("orderType") };
			log.info(manager.getAccount() + "开始新增订单" + id);
			try {
				int counts = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_order(id,account,spcode,configure,unit_price,count,total,deal_count,from_part,state,receive_name,receive_telephone,receive_address,other_order_id,remark,submit_time,order_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
								values);
				if (counts > 0) {
					addOrderState(jdbc, manager.getAccount(), state,null, id);
				}
				log.info(manager.getAccount() + "开始新增订单" + id + "结束。。。。");
				data.setState(true);
				data.setMsg("新增订单成功");
				return data;
			} catch (Exception e) {
				e.printStackTrace();
				log.error("写入工单操作失败", e);
			}
		} else {
			data.setMsg(checkParams);
			return data;
		}
		return data;
	}

	private String checkAddOrderParam(Map<String, String> params) {
		String configure = params.get("configure");
		if ("L".equals(configure) || "H".equals(configure)) {

		} else {
			return "订单配置错误";
		}

		String count = params.get("count");
		if (StringUtils.isNotBlank(count)) {
			String checkCount = PatternTool.checkStr(count, "^[1-9]\\d*$",
					"订单数量错误");
			if (checkCount != null)
				return checkCount;
		} else {
			return "订单数量错误";
		}
		String receiveName = params.get("receiveName");
		if (StringUtils.isBlank(receiveName)) {
			return "收货人姓名错误";
		}
		String receiveTelephone = params.get("receiveTelephone");
		if (StringUtils.isBlank(receiveTelephone)) {
			return "收货人联系电话错误";
		}
		String receiveAddress = params.get("receiveAddress");
		if (StringUtils.isBlank(receiveAddress)) {
			return "收货人地址错误";
		}
		String fromPart = params.get("fromPart");
		if (!Env.ORDER_FROM_PART.containsKey(fromPart)) {
			return "订单来源错误";
		}
		return null;
	}

	public Data updateOrder(Map<String, String> params) {
		Data data = new Data(false, "操作失败");
		String operateType = params.get("operateType");
		if (StringUtils.isBlank(operateType)) {
			return data;
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		if ("updateExpress".equals(operateType)) {
			try {
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"update t_express set express_company = ? ,express_id=?,express_name=?,send_time=?,operate_name = ?, remark = ? where id = ? ",
								new Object[] { params.get("expressCompany"),
										params.get("expressId"),
										params.get("expressName"),
										params.get("sendTime"),
										params.get("operateName"),
										params.get("remark"),
										Integer.parseInt(params.get("id")) });
				if (count > 0) {
					data.setState(true);
					data.setMsg("修改物流信息成功");
					return data;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return data;
			}
		}

		if ("updateOrder".equals(operateType)) {
			try {
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"update t_order set spcode = ? ,configure=?,unit_price=?,total=?,receive_name = ?,receive_telephone=?,receive_address=?, remark = ?,order_type=? where id = ? ",
								new Object[] {
										params.get("spcode"),
										params.get("configure"),
										Integer.parseInt(params
												.get("unitPrice")),
										Integer.parseInt(params.get("total")),
										params.get("receiveName"),
										params.get("receiveTelephone"),
										params.get("receiveAddress"),
										params.get("remark"),
										params.get("orderType"),
										params.get("id") });
				if (count > 0) {
					data.setState(true);
					data.setMsg("修改订单基本信息成功");
					return data;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return data;
			}
		}

		if ("updateBind".equals(operateType)) {
			TDCodeBus tdcodeBus = new TDCodeBus();
			JDBC jdbc2 = (JDBC) ApplicationContextTool.getBean("jdbc");
			//先把旧的查询处来，为后面修改做准备
			SpTdcode spTdcode = tdcodeBus.queryTDCodeByMD5(jdbc2,
					params.get("oldSn"));
			SpTdcode spTdcodeNew = null;
			if (!params.get("oldSn").equals(params.get("sn"))) {
				spTdcodeNew = tdcodeBus.queryTDCodeByMD5(jdbc2,
						params.get("sn"));
				if(spTdcodeNew==null||StringUtils.isBlank(spTdcodeNew.getBarcode())){
					data.setMsg("新的设备不存在或者新的设备PN为空");
					return data;
				}
				if(spTdcode.getGroupId()>10000){
					if(spTdcodeNew.getGroupId()==10000){
						data.setMsg("新的设备组ID和旧设备不在同一组 ");
						return data;
					}
				}
			}
			Connection conn = null;
			try {
				conn = ApplicationContextTool.getConnection();
				conn.setAutoCommit(false);
				if (!params.get("oldSn").equals(params.get("sn"))) {
					jdbc.update(
							conn,
							"update t_device set sn = ?,spcode = ? ,configure = ?  where sn = ? ",
							new Object[] {
									params.get("sn"),
									StringUtils.substring(params.get("sn"), 0,
											3),
									StringUtils.substring(params.get("sn"), 3,
											4), params.get("oldSn") });
				}

				if (!params.get("oldMobile").equals(params.get("mobile"))) {
					jdbc.update(
							conn,
							"update t_flux_card set mobile = ?  where mobile = ?",
							new Object[] { params.get("mobile"),
									params.get("oldMobile") });
				}

				jdbc.update(
						conn,
						"update t_bind_device_fluxcard set sn = ? ,mobile=?,limite_time=?, remark = ? where id = ? ",
						new Object[] { params.get("sn"), params.get("mobile"),
								params.get("limiteTime"), params.get("remark"),
								Integer.parseInt(params.get("id")) });
				// 设备相同，记得将流量卡，到期时间更新到sp_tdcode
				if (params.get("oldSn").equals(params.get("sn"))) {
					jdbc.update(
							conn,
							"update sp_tdcode set fluxcard=?,limite_time=?  where tdcodemd5 = ?   ",
							new Object[] { params.get("mobile"),
									params.get("limiteTime"),
									params.get("oldSn") });
				}else{
					// 将sp_tdcode中SN的相关信息进行处理：即旧的SN的订单号，流量卡，到期时间置为空，新的SN 增加订单号，流量卡，到期时间
					jdbc.update(
							conn,
							"update sp_tdcode set orderid=null,fluxcard=null,limite_time=null,idcard_state=null  where tdcodemd5 = ?   ",
							new Object[] {
									params.get("oldSn") });
					jdbc.update(
							conn,
							"update sp_tdcode set orderid = ?,fluxcard=?,limite_time=? ,idcard_state=?  where tdcodemd5 = ?   ",
							new Object[] {spTdcode.getOrderid(),spTdcode.getFluxcard(),spTdcode.getLimiteTime(),spTdcode.getIdcardState(),
									params.get("sn") });
				}
				conn.commit();
				conn.setAutoCommit(true);
				data.setState(true);
				data.setMsg("修改设备流量卡捆绑成功");
				// 行业版的同步给亿讯
				if (params.get("oldSn").equals(params.get("sn"))) {
					if (spTdcode != null && spTdcode.getGroupId() > 10000) {
						LoginBus loginBus = new LoginBus();
						loginBus.syncTDCode(jdbc2, spTdcode, "U", tdcodeBus);
					}
				}else{
					if (spTdcode != null && spTdcode.getGroupId() > 10000) {
						Map<String,String> map = new HashMap<String,String>();
						map.put("oldSn", params.get("oldSn"));
						map.put("newSn", params.get("sn"));
						map.put("limiteTime", params.get("limiteTime"));
						map.put("fluxCard", params.get("mobile"));
						map.put("groupId", String.valueOf(spTdcode.getGroupId()));	
						map.put("deviceId", String.valueOf(spTdcodeNew.getBarcode()));	
						String result  =syncUpdateTDCodeSN(map);
						if(result!=null){
							data.setMsg("修改设备流量卡捆绑成功(但是同步亿讯数据异常，请联系管理员)");
						}
					}
					
				}
				return data;
			} catch (Exception e) {
				try {
					if (conn != null)
						conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				log.error("", e);
				return data;
			} finally {
				if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				jdbc.closeAll();
				
			}
		}

		return data;

	}

	public Data updateOrderState(TManager manager, String operateType,
			String state, String id, Map<String, String> params)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		Data data = new Data(false, "审核订单失败");
		if ("hbReviewOrder".equals(operateType)) {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			TOrder order = queryOrderByID(jdbc, id);
			if (order == null) {
				data.setMsg("需审核地市的订单未找到");
				return data;
			}
			if (state.equals(order.getState())) {
				String shenheState = params.get("shenheState");
				String shenheResult = params.get("shenheResult");
				if("N".equals(shenheState)){
					state = shenheState;
					shenheResult="，原因："+shenheResult;
				}else if("Y".equals(shenheState)){
					state = "1";
					shenheResult="， 结果："+shenheResult;
				}
				int count = updateOrderState(jdbc, manager.getAccount(), state,shenheResult,
						order.getId());
				if (count > 0) {
					data.setMsg("审核地市的订单成功");
					data.setState(true);
					return data;
				} else {
					data.setMsg("审核地市的订单失败");
					return data;
				}
			} else {
				data.setMsg("需审核地市的订单状态错误");
				return data;
			}
		}else if ("reviewOrder".equals(operateType)) {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			TOrder order = queryOrderByID(jdbc, id);
			if (order == null) {
				data.setMsg("需审核号百订单未找到");
				return data;
			}
			if (state.equals(order.getState())) {
				int count = updateOrderState(jdbc, manager.getAccount(), "5",null,
						order.getId());
				if (count > 0) {
					data.setMsg("审核号百订单成功");
					data.setState(true);
					return data;
				} else {
					data.setMsg("审核号百订单失败");
					return data;
				}
			} else {
				data.setMsg("需审核号百订单状态错误");
				return data;
			}
		} else if ("outOrder".equals(operateType)) {
			// outOrder: 出库
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			TOrder order = queryOrderByID(jdbc, id);
			if (order == null) {
				data.setMsg("需出库的订单未找到");
				return data;
			}
			if (state.equals(order.getState())) {
				// 将需出库的订单改成已出库
				List<TBindDeviceFluxcard> list = jdbc
						.query(ApplicationContextTool.getDataSource(),
								"select * from t_bind_device_fluxcard where order_id = ? ",
								TBindDeviceFluxcard.class,
								new String[] { order.getId() }, 0, 0);
				if (list != null && list.size() > 0) {
					// 出库时间 延后一年即为生命周期（limite_time）
					// 考虑到以前的旧数据没来得及录入，将2015年3月1号前的订单以订单号的时间延长一年，3月1号以后以当前时间延后一年
					Date date = new Date();
					String dateTemp = StringUtils.substring(order.getId(), 2,
							16);
					Date temp = DateTool.formateString2Time(dateTemp,
							"yyyyMMddHHmmss");
					if (temp.before(DateTool.buildDate(2015, 3, 1, 0, 0, 0, 0))) {
						date = temp;
					}
					TBindDeviceFluxcard bind = null;

					for (int i = 0, len = list.size(); i < len; i++) {
						bind = list.get(i);
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update t_device set state ='O' where sn = ?  ",
								new Object[] { bind.getSn() });
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update t_flux_card set state ='O'  where mobile = ?  ",
								new Object[] { bind.getMobile() });
						// 将sp_tdcode的 state 改成 1 表示出库,flux_card,limite_time也修改
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update sp_tdcode set status = 1,orderid = ?,fluxcard=?,limite_time=?  where tdcodemd5 = ?   ",
								new Object[] { bind.getOrderId(),
										bind.getMobile(),
										DateTool.diff(date, "YEAR", 1),
										bind.getSn() });
					}
					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"update t_bind_device_fluxcard set limite_time = ?   where order_id = ?   ",
							new Object[] { DateTool.diff(date, "YEAR", 1),
									order.getId() });
					// 行业版的 将数据同步给亿讯,写t_sync_tdcode
					List<SpTdcode> spcodeList = jdbc
							.query(ApplicationContextTool.getDataSource(),
									"select * from sp_tdcode where orderid = ? and group_id>10000  ",
									SpTdcode.class,
									new String[] { order.getId() }, 0, 0);
					TDCodeBus tdcodeBus = new TDCodeBus();
					LoginBus loginBus = new LoginBus();
					if (spcodeList != null && spcodeList.size() > 0) {
						for (int i = 0, len = spcodeList.size(); i < len; i++) {
							loginBus.syncTDCode(jdbc, spcodeList.get(i), "A",
									tdcodeBus);
						}
					}
				}
				int count = updateOrderState(jdbc, manager.getAccount(), "8",null,
						order.getId());
				if (count > 0) {
					data.setMsg("出库成功");
					data.setState(true);
					return data;
				} else {
					data.setMsg("出库失败");
					return data;
				}

			} else {
				data.setMsg("需出库状态错误");
				return data;
			}
		} else if ("sendOrder".equals(operateType)) {
			// sendOrder 配送
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			TOrder order = queryOrderByID(jdbc, id);
			if (order == null) {
				data.setMsg("需配送的订单未找到");
				return data;
			}
			if (state.equals(order.getState())) {
				ExpressBus expressBus = new ExpressBus();
				data = expressBus.addExpress(manager, params);
				if (data.isState()) {
					int count = updateOrderState(jdbc, manager.getAccount(),"9",null,
							 order.getId());
					if (count > 0) {
						data.setMsg("配送成功");
						data.setState(true);
						return data;
					} else {
						data.setMsg("配送失败");
						return data;
					}
				} else {
					return data;
				}
			} else {
				data.setMsg("需配送状态错误");
				return data;
			}
		} else if ("endOrder".equals(operateType)) {
			// endOrder 归档
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			TOrder order = queryOrderByID(jdbc, id);
			if (order == null) {
				data.setMsg("需归档的订单未找到");
				return data;
			}
			// 已经出库的单(state=9)直接归档 或者准备配送单可直接归档(state=8)
			if ("9".equals(order.getState()) || "8".equals(order.getState())) {
				int count = updateOrderState(jdbc, manager.getAccount(), "0",null,
						order.getId());
				if (count > 0) {
					data.setMsg("归档成功");
					data.setState(true);
					return data;
				} else {
					data.setMsg("归档失败");
					return data;
				}
			} else {
				data.setMsg("需归档单状态错误");
				return data;
			}
		}

		return data;
	}

	public int updateOrderState(JDBC jdbc, String account, String state,String result,
			String id) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		Object[] params = { state, id };
		try {
			log.info(account + "修改订单" + id + "状态为" + state + "["
					+ Env.ORDER_STATE.get(state) + "]");
			int count = jdbc.update(ApplicationContextTool.getDataSource(),
					"update  t_order set state = ? where id = ? ", params);
			if (count > 0) {
				addOrderState(jdbc, account, state,result, id);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改订单操作状态失败", e);
		}
		return 0;
	}

	public int addOrderState(JDBC jdbc, String account, String state,String result,
			String orderId) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		StringBuffer describeMsg = new StringBuffer();
		if(Env.ORDER_STATE.containsKey(state)){
			describeMsg.append(Env.ORDER_STATE.get(state));
		}
		if(result!=null){
			describeMsg.append(result);
		}
		Object[] params = { account, orderId, state,
				describeMsg.toString(), new Date() };
		try {
			int count = jdbc
					.update(ApplicationContextTool.getDataSource(),
							"insert into t_order_state(account,order_id,state,describe_msg,submit_time) values(?,?,?,?,?)",
							params);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("写入订单操作状态失败", e);
		}
		return 0;
	}

	public TOrder queryOrderByID(JDBC jdbc, String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TOrder po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_order  where id =  ? ", TOrder.class,
					new String[] { id });
			return po;
		} catch (Exception e) {
			throw new RuntimeException("t_order查询异常", e);
		}
		

	}

	public List<TOrderState> queryOrderStateByOrderID(JDBC jdbc, String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			List<TOrderState> list = jdbc.query(
					ApplicationContextTool.getDataSource(),
					"select * from t_order_state where order_id = ? ",
					TOrderState.class, new Object[] { orderId }, 0, 0);
			if (list != null && list.size() > 0)
				return list;
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public  synchronized String cerateOrderID(String code, Date date) {
		StringBuffer id = new StringBuffer();
		if (date == null) {
			date = new Date();
		}
		id.append(code).append(
				DateTool.formateTime2String(date, "yyyyMMddHHmmss"));
		id.append(String.valueOf(new Random().nextDouble()).substring(3, 9));
		return id.toString();
	}

	public boolean checkOrderState(String state) {
		if (StringUtils.isBlank(state)) {
			return false;
		} else {
			if (!Env.ORDER_STATE.containsKey(state)) {
				return false;
			} else {
				return true;
			}
		}
	}
	public String syncUpdateTDCodeSN(Map<String,String> map) {
		String oldSn=map.get("oldSn");
		String newSn=map.get("newSn");
		String fluxCard = map.get("fluxCard");
		String groupId =map.get("groupId");
		String limiteTime =map.get("limiteTime");
		String deviceId =map.get("deviceId");
		
		HttpUrlClient client = new HttpUrlClient();

		JSONObject params = new JSONObject();
		params.put("carUser", ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		Long time = System.currentTimeMillis();
		params.put("time", time);
		// MD5 32位(oldSn+carUser+ time+password) password为系统分配的密码
		StringBuffer str = new StringBuffer();
		str.append(oldSn);
		str.append(ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		str.append(time).append(ConfLoad.getProperty("YZ_TOKENID_PASSWORD"));
		String sign = MD5.getMD5(str.toString().getBytes());
		params.put("sign", sign);
		params.put("newHgDeviceSn", newSn);
		params.put("oldHgDeviceSn", oldSn);
		params.put("cardNo", fluxCard);
		params.put("copId", groupId);
		params.put("dueDate", limiteTime);
		params.put("deviceId", deviceId);
		try{
			String result = client.post("同步设备流量给亿讯(修改SN)", ConfLoad.getProperty("OBD_HYB_URL")
					+ "api/obd/updateHgSnInfo?", params, 20000, true);
			// 返回格式
			// 
			log.info("同步设备流量给亿讯(修改SN)返回结果：" + result);
			if (result != null) {
				JSONObject jsonObj = JSONObject.fromObject(result);
				if (jsonObj != null && jsonObj.getInt("code") == 0) {
					if (jsonObj.getJSONObject("data").get("state") != null
							&& jsonObj.getJSONObject("data")
									.getInt("state") == 0) {
						return null;
					}
				}
			}
			return "修改设备失败";
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
				
	}
}
