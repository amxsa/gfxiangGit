package cn.cellcom.czt.bus;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TBindDeviceFluxcard;
import cn.cellcom.czt.po.TDevice;
import cn.cellcom.czt.po.TFluxCard;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TOrder;
import cn.cellcom.web.spring.ApplicationContextTool;

public class BindBus {
	private static final Log log = LogFactory.getLog(BindBus.class);

	public Data bindDevcieFluxCard(TManager manager, Map<String, String> params) {
		Data data = new Data(false, "绑定设备流量卡失败");
		String checkParams = checkBindDevcieFluxCardParam(params);
		if (checkParams != null) {
			data.setMsg(checkParams);
			return data;
		}
		OrderBus orderBus = new OrderBus();
		JDBC jdbc = ApplicationContextTool.getJdbc();
		TOrder order = orderBus.queryOrderByID(jdbc, params.get("orderId"));
		if (!"5".equals(order.getState()) && !"6".equals(order.getState())) {
			data.setMsg("订单状态非预备组装或非正在组装");
			return data;
		}
		DeviceBus deviceBus = new DeviceBus();
		String[] deviceSn = params.get("deviceId").split(",");
		if (deviceSn == null || deviceSn.length == 0) {
			data.setMsg("设备号个数为0");
			return data;
		}
		TDevice device = null;
		StringBuffer deviceFail = new StringBuffer();
		for (int i = 0; i < deviceSn.length; i++) {
			device = deviceBus.queryDeviceBySN(jdbc, deviceSn[i]);
			if (device == null || !"I".equals(device.getState())) {
				deviceFail.append(deviceSn[i]).append(",");
			}
		}
		if (deviceFail.length() > 0) {
			data.setMsg(deviceFail.toString() + "设备不存在或状态非入库状态");
			return data;
		}
		StringBuffer fluxCardFail = new StringBuffer();
		FluxCardBus fluxCardBus = new FluxCardBus();
		String[] fluxCardMobile = params.get("fluxCardId").split(",");
		if (fluxCardMobile == null || fluxCardMobile.length == 0) {
			data.setMsg("流量卡个数为0");
			return data;
		}
		TFluxCard fluxCard = null;
		for (int i = 0; i < fluxCardMobile.length; i++) {
			fluxCard = fluxCardBus.queryFluxCardByMobile(jdbc,
					fluxCardMobile[i]);
			if (fluxCard == null || !"I".equals(fluxCard.getState())) {
				fluxCardFail.append(fluxCardMobile[i]).append(",");
			}
		}
		if (fluxCardFail.length() > 0) {
			data.setMsg(fluxCardFail.toString() + "流量卡不存在或状态非入库状态");
			return data;
		}
		if (order.getCount() >= deviceSn.length) {
			if (order.getCount() - order.getDealCount() >= deviceSn.length) {
				Date date = new Date();
				String batchId = DateTool.formateTime2String(date,
						" yyyyMMddHHmmss");
				try {
					int successCount = 0;
					for (int i = 0; i < deviceSn.length; i++) {
						int count = jdbc
								.update(ApplicationContextTool.getDataSource(),
										"insert into t_bind_device_fluxcard(account,batch_id,order_id,sn,mobile,operate_name,remark,submit_time) values(?,?,?,?,?,?,?,?)",
										new Object[] { manager.getAccount(),
												batchId, order.getId(),
												deviceSn[i], fluxCardMobile[i],
												params.get("operateName"),
												params.get("remark"), date });
						if (count > 0) {
							jdbc.update(
									ApplicationContextTool.getDataSource(),
									"update t_device set state = 'B' where sn = ?   ",
									new String[] { deviceSn[i] });
							jdbc.update(
									ApplicationContextTool.getDataSource(),
									"update t_flux_card set state = 'B' where mobile = ?   ",
									new String[] { fluxCardMobile[i] });

						}
						successCount += count;
					}
					if (successCount == deviceSn.length) {
						jdbc.update(ApplicationContextTool.getDataSource(),
								"update t_order set deal_count = deal_count+"
										+ successCount + " where id  = ?",
								new String[] { order.getId() });

						if (order.getDealCount() + successCount == order
								.getCount()) {
							// 订单数量处理完毕 修改状态7
							orderBus.updateOrderState(jdbc,
									manager.getAccount(), "7",null, order.getId());
						} else {
							if ("5".equals(order.getState())) {
								orderBus.updateOrderState(jdbc,
										manager.getAccount(), "6",null,
										order.getId());
							}
						}
						data.setMsg("设备和流量卡捆绑成功");
						data.setState(true);
						return data;
					} else {
						data.setMsg("订单需处理数量与捆绑数量不一致,请与技术人员联系");
						return data;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("写入t_bind_device_fluxcard异常", e);
				}

			} else {
				data.setMsg("订单未处理数量必须小于等于捆绑的数量");
				return data;
			}
		} else {
			data.setMsg("订单数量必须小于等于捆绑的数量");
			return data;
		}
		return data;
	}

	public Data deleteDevcieFluxCard(TManager manager,
			Map<String, String> params) {
		Data data = new Data(false, "删除设备流量卡捆绑失败");
		String id = params.get("id");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		TBindDeviceFluxcard bind = queryById(jdbc, id);
		if (bind == null) {
			data.setMsg("设备流量卡捆绑未找到");
			return data;
		}
		try {
			int count = jdbc.update(ApplicationContextTool.getDataSource(),
					"delete from t_bind_device_fluxcard where id = ? ",
					new Object[] { Integer.valueOf(id) });
			if (count > 0) {
				jdbc.update(ApplicationContextTool.getDataSource(),
						"update t_device set state ='I' where sn = ?  ",
						new Object[] { bind.getSn() });
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update t_flux_card set state ='I'  where mobile = ?  ",
						new Object[] { bind.getMobile() });
				// 修改订单处理数、状态
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update t_order set deal_count = deal_count-1, state = '6'   where id  = ?",
						new String[] { bind.getOrderId() });
				data.setState(true);
				data.setMsg("删除捆绑成功");
				return data;
			} else {
				data.setMsg("删除设备流量卡捆绑失败");
				return data;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除t_bind_device_fluxcard异常", e);
			return data;
		}

	}

	public TBindDeviceFluxcard queryById(JDBC jdbc, String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TBindDeviceFluxcard po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_bind_device_fluxcard where id =  ? ",
					TBindDeviceFluxcard.class,
					new Object[] { Integer.valueOf(id) });
			return po;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String checkBindDevcieFluxCardParam(Map<String, String> params) {
		String orderId = params.get("orderId");
		if (StringUtils.isBlank(orderId)) {
			return "订单号不能为空";
		}
		String deviceId = params.get("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			return "设备号不能为空";
		}
		String fluxCardId = params.get("fluxCardId");
		if (StringUtils.isBlank(fluxCardId)) {
			return "流量卡不能为空";
		}
		if (deviceId.split(",").length != fluxCardId.split(",").length) {
			return "设备号已流量卡数目不匹配";
		}

		return null;
	}

	public List<TBindDeviceFluxcard> queryByOrderId(JDBC jdbc, String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			List<TBindDeviceFluxcard> list = jdbc.query(
					ApplicationContextTool.getDataSource(),
					"select a.*,b.barcode from t_bind_device_fluxcard a left join sp_tdcode b on a.sn=b.tdcodemd5 where a.order_id = ? ",
					TBindDeviceFluxcard.class, new String[] { orderId }, 0, 0);
			if (list != null && list.size() > 0)
				return list;
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
