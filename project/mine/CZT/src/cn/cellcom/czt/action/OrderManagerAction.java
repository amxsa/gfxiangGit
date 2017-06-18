package cn.cellcom.czt.action;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.czt.bus.BindBus;
import cn.cellcom.czt.bus.ExpressBus;
import cn.cellcom.czt.bus.OrderBus;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TBindDeviceFluxcard;
import cn.cellcom.czt.po.TExpress;
import cn.cellcom.czt.po.TLinkman;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TOrder;
import cn.cellcom.czt.po.TOrderState;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class OrderManagerAction extends Struts2Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(OrderManagerAction.class);
	private TOrder order = new TOrder();

	public TOrder getOrder() {
		return order;
	}

	public void setOrder(TOrder order) {
		this.order = order;
	}
	
	/**
	 * 待管理员审核的单
	 * @return
	 */
	public String showReviewOrder() {
		String state = getParameter("state");
		// state = (1:待审核的单，4：产品经理二次修改待审核，)
		String id = getParameter("idQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_order  where 1=1  ");
		TManager manager = (TManager) getSession().getAttribute("login");
		List<Object> params = new ArrayList<Object>();
		if (manager.getRoleid() == 0) {
			if (StringUtils.isNotBlank(state)) {
				sql.append(" and state =  ? ");
				params.add(state);
				getRequest().setAttribute("state", state);
			} else {
				sql.append(" and state  in ('1','4') ");
			}
		} else {
			sql.append(" and account  = ?  and state in ('2','3') ");
			params.add(manager.getAccount());
		}
		if (StringUtils.isNotBlank(id)) {
			sql.append(" and id =  ?  ");
			params.add(id);
			getRequest().setAttribute("id", id);
		}
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and submit_time>? and submit_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by submit_time desc ");
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TOrder> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TOrder.class);
			if (pageResult != null) {
				List<TOrder> list = pageResult.getContent();
				if (list != null && list.size() > 0) {

					for (int i = 0, len = list.size(); i < len; i++) {

					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showReviewOrder";

	}

	public String showOrder() {
		String id = getParameter("idQuery");
		String state = getParameter("stateQuery");
		String fromPart = getParameter("fromPartQuery");
		String orderType = getParameter("orderTypeQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_order  where 1=1  ");
		TManager manager = (TManager) getSession().getAttribute("login");
		List<Object> params = new ArrayList<Object>();
		if (manager.getRoleid() != 0) {
			if(Env.GD.equals(manager.getAreacode())){
				sql.append(" and account!='admin' ");
				
			}else{
				sql.append(" and account  = ?   ");
				params.add(manager.getAccount());
			}
		}
		if (StringUtils.isNotBlank(id)) {
			sql.append(" and id =  ?  ");
			params.add(id);
			getRequest().setAttribute("id", id);
		}
		if (StringUtils.isNotBlank(state)) {
			sql.append(" and state =  ?  ");
			params.add(state);
			getRequest().setAttribute("state", state);
		}
		//fromPartQuery 1-线上订单(是指通过微信公众号、客户端过来的订单)，2-渠道订单(是指通过产品管理系统订单模块提交的订单,包括今后的各地市、渠道商订单)
		if (StringUtils.isNotBlank(fromPart)) {
			//from_part(K-客户端，W-微信，1-管理员，2-地市管理员)
			if("1".equals(fromPart)){
				sql.append(" and from_part in ('K','W')  ");
			}else if("2".equals(fromPart)){
				sql.append(" and from_part in ('1','2')  ");
			}
			getRequest().setAttribute("fromPart", fromPart);
		}
		// 1-购买，2-领用
		if (StringUtils.isNotBlank(orderType)) {
			sql.append(" and order_type =  ?  ");
			params.add(orderType);
			getRequest().setAttribute("orderType", orderType);
		}
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and submit_time>? and submit_time< ? ");
			params.add(starttime);
			params.add(endtime);
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by submit_time desc ");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		try {
			PageResult<TOrder> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TOrder.class);
			if (pageResult != null) {
				List<TOrder> list = pageResult.getContent();
				if (list != null && list.size() > 0) {

					for (int i = 0, len = list.size(); i < len; i++) {

					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 出库单
		if ("showOutOrder".equals(getParameter("typeQuery"))) {
			return "showOutOrder";
		}
		// 配送单
		if ("showSendOrder".equals(getParameter("typeQuery"))) {
			return "showSendOrder";
		}
		// 归档(结束)单
		if ("showEndOrder".equals(getParameter("typeQuery"))) {
			return "showEndOrder";
		}
		// 华工OBD受理单
		if ("showHGObdOrder".equals(getParameter("typeQuery"))) {
			return "showHGObdOrder";
		}
		return "showOrder";
	}

	public String orderDetail() {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			OrderBus bus = new OrderBus();
			JDBC jdbc = ApplicationContextTool.getJdbc();
			TOrder po = bus.queryOrderByID(jdbc, id);
			if (po != null) {
				po.setFromPart(Env.ORDER_FROM_PART.get(po.getFromPart()));
				po.setSpcode(Env.SPCODE.get(po.getSpcode()));
				po.setConfigure(Env.ORDER_CONFIGURE.get(po.getConfigure()));
				po.setState(Env.ORDER_STATE.get(po.getState()));
				getRequest().setAttribute("order", po);
				TManager manager = (TManager) getSession()
						.getAttribute("login");
				if (manager.getRoleid() == 0) {
					List<TOrderState> stateList = bus.queryOrderStateByOrderID(
							jdbc, po.getId());
					if (stateList != null)
						getRequest().setAttribute("orderDetail", stateList);
					BindBus bindBus = new BindBus();
					List<TBindDeviceFluxcard> bindList = bindBus
							.queryByOrderId(jdbc, po.getId());
					if (bindList != null) {
						
						getRequest().setAttribute("bindList", bindList);
					}
					ExpressBus expressBus = new ExpressBus();
					TExpress express = expressBus.queryByOrderId(jdbc,
							po.getId());
					if(express!=null)
						getRequest().setAttribute("express", express);

				} else {
					if (manager.getAccount().equals(po.getAccount())||Env.GD.equals(manager.getAreacode())) {
						List<TOrderState> stateList = bus
								.queryOrderStateByOrderID(jdbc, po.getId());
						if (stateList != null)
							getRequest().setAttribute("orderDetail", stateList);
						BindBus bindBus = new BindBus();
						List<TBindDeviceFluxcard> bindList = bindBus
								.queryByOrderId(jdbc, po.getId());
						if (bindList != null) {
							getRequest().setAttribute("bindList", bindList);
						}
					} else {
						getRequest().setAttribute("result", "订单未找到");
						return "error";
					}
				}
			} else {
				getRequest().setAttribute("result", "订单未找到");
				return "error";
			}
		} else {
			getRequest().setAttribute("result", "订单号不能为空");
			return "error";
		}
		return "orderDetail";
	}

	public String addOrder() {
		String spcode = getParameter("spcode");
		String configure = getParameter("configure");
		String unitPrice = getParameter("unitPrice");
		String count = getParameter("count");
		String fromPart = getParameter("fromPart");
		String receiveName = getParameter("receiveName");
		String receiveTelephone = getParameter("receiveTelephone");
		String receiveAddress = getParameter("receiveAddress");
		String otherOrderId = getParameter("otherOrderId");
		String remark = getParameter("remark");
		String orderTime =  getParameter("orderTime");
		String orderType = getParameter("orderType");
		if(StringUtils.isBlank(orderType)){
			orderType="1";
		}
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("spcode", spcode);
		params.put("configure", configure);
		params.put("unitPrice", unitPrice);
		params.put("count", count);
		params.put("fromPart", fromPart);
		params.put("receiveName", receiveName);
		params.put("receiveTelephone", receiveTelephone);
		params.put("receiveAddress", receiveAddress);
		params.put("otherOrderId", otherOrderId);
		params.put("remark", remark);
		params.put("orderTime", orderTime);
		params.put("orderType", orderType);
		OrderBus bus = new OrderBus();
		TManager manager = (TManager) getSession().getAttribute("login");
		Data msg = bus.addOrder(manager, params);
		if (!msg.isState()) {
			getRequest().setAttribute("result", msg.getMsg());
			return "error";
		} else {
			getRequest().setAttribute("url", "addOrder.jsp");
			getRequest().setAttribute("result", msg.getMsg());
			//用于刷新左边菜单(设备流量捆绑)的统计数
			if(manager.getRoleid()==0){
				getRequest().setAttribute("orderCountRefresh", "true");
			}
			
			return "success";
		}

	}
	public String preUpdateOrder() {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				TOrder order = jdbc.queryForObject(ApplicationContextTool.getDataSource(), "select * from t_order where id= ? ", TOrder.class, new String[]{id});
				if(order!=null){
					
					getRequest().setAttribute("order", order);
					OrderBus bus = new OrderBus();
					List<TOrderState> stateList = bus.queryOrderStateByOrderID(
							jdbc, order.getId());
					if (stateList != null)
						getRequest().setAttribute("orderDetail", stateList);
					BindBus bindBus = new BindBus();
					List<TBindDeviceFluxcard> bindDeviceFluxcard = bindBus.queryByOrderId(jdbc, id);
					if(bindDeviceFluxcard!=null)
						getRequest().setAttribute("bindList", bindDeviceFluxcard);
					TExpress express = jdbc.queryForObject(ApplicationContextTool.getDataSource(), "select * from t_express where order_id = ?", TExpress.class, new String[]{order.getId()});
					if(express!=null)
						getRequest().setAttribute("express", express);
					return "updateOrder";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		getRequest().setAttribute("result", "订单未找到");
		return "error";
	}
	
	public String updateOrder() throws IOException  {
		
		String operateType = getParameter("operateType");
		/*
		 * operateType=updateExpress 表示修改物流
		 * operateType=updateOrder 表示修改订单基本信息
		 * operateType=updateBind 表示修改订单设备流量捆绑
		 */
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("operateType", operateType);
		OrderBus bus = new OrderBus();
		Data data = null;
		if("updateExpress".equals(operateType)){
			params.put("operateType", operateType);
			params.put("id", getParameter("id"));
			params.put("expressCompany", getParameter("expressCompany"));
			params.put("expressId", getParameter("expressId"));
			params.put("expressName", getParameter("expressName"));
			params.put("sendTime", getParameter("sendTime"));
			params.put("operateName", getParameter("operateName"));
			params.put("remark", getParameter("remark"));
			data = bus.updateOrder(params);
		}
		if("updateOrder".equals(operateType)){
			params.put("operateType", operateType);
			params.put("id", getParameter("id"));
			params.put("spcode", getParameter("spcode"));
			params.put("configure", getParameter("configure"));
			params.put("unitPrice", getParameter("unitPrice"));
			params.put("total", getParameter("total"));
			params.put("orderType", getParameter("orderType"));
			params.put("receiveName", getParameter("receiveName"));
			params.put("receiveTelephone", getParameter("receiveTelephone"));
			params.put("receiveAddress", getParameter("receiveAddress"));
			params.put("remark", getParameter("remark"));
			data = bus.updateOrder(params);
		}
		if("updateBind".equals(operateType)){
			params.put("operateType", operateType);
			params.put("id",StringUtils.trimToNull(getParameter("id")));
			params.put("oldSn", StringUtils.trimToNull(getParameter("oldSn")));
			params.put("oldMobile", StringUtils.trimToNull(getParameter("oldMobile")));
			params.put("sn", StringUtils.trimToNull(getParameter("sn")));
			params.put("mobile", StringUtils.trimToNull(getParameter("mobile")));
			params.put("limiteTime", StringUtils.trimToNull(getParameter("limiteTime")));
			params.put("remark", StringUtils.trimToNull(getParameter("remark")));
			data = bus.updateOrder(params);
		}
		if(data!=null)
			PrintTool.print(getResponse(), data.isState()+"|"+data.getMsg(), null);
		else
			PrintTool.print(getResponse(), "false|操作失败", null);
		return null;
	}

	public String deleteOrder() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from t_order  where  id = ?  ");
			List<Object> params = new ArrayList<Object>();
			params.add(id);
			TManager manager = (TManager) getSession().getAttribute("login");
			if(manager.getRoleid()!=0&&!Env.GD.equals(manager.getAreacode())){
				sql.append(" and account = ? ");
				params.add(manager.getAccount());
			}
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				int count = jdbc.update(ApplicationContextTool.getDataSource(),
						sql.toString(),
						params.toArray());
				if (count > 0) {
					//同时删除订单状态记录表
					jdbc.update(ApplicationContextTool.getDataSource(), "delete from t_order_state where order_id = ? ", new String[]{id});
					log.info(manager.getAccount() + "删除订单" + id+"成功");
					PrintTool.print(getResponse(), "true|删除成功", null);
				} else {
					PrintTool.print(getResponse(), "false|删除失败", null);
				}

			} catch (Exception e) {
				log.error("删除订单失败", e);
				PrintTool.print(getResponse(), "false|删除失败", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|请选择订单", null);
		}
		return null;
	}

	

	
	/**
	 * 审核订单
	 * @return
	 * @throws IOException
	 */
	public String reviewOrder() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			TManager manager = (TManager) getSession().getAttribute("login");
			log.info(manager.getAccount() + "准备审核号百人员提交的订单[" + id + "]。。。");
			OrderBus bus = new OrderBus();
			try {
				Data msg = bus.updateOrderState(manager, "reviewOrder", "1", id,
						null);
				if (msg.isState()) {
					PrintTool
							.print(getResponse(), "true|" + msg.getMsg(), null);
					log.info(manager.getAccount() + "审核号百人员订单[" + id + "]成功。。。");
				} else {
					PrintTool.print(getResponse(), "false|" + msg.getMsg(),
							null);
					log.info(manager.getAccount() + "审核号百人员订单[" + id + "]失败。。。");
				}
			} catch (Exception e) {
				log.error("审核地市订单失败", e);
				PrintTool.print(getResponse(), "false|审核号百人员订单失败，数据操作异常", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|审核号百人员订单未找到", null);
		}
		return null;
	}
	
	/**
	 * 订单出库
	 * @return
	 * @throws IOException
	 */
	public String outOrder() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			TManager manager = (TManager) getSession().getAttribute("login");
			log.info(manager.getAccount() + "准备将订单[" + id + "]出库。。。");
			OrderBus bus = new OrderBus();
			try {
				Data msg = bus.updateOrderState(manager, "outOrder", "7", id,
						null);
				if (msg.isState()) {
					PrintTool
							.print(getResponse(), "true|" + msg.getMsg(), null);
					log.info(manager.getAccount() + "订单[" + id + "]出库成功。。。");
				} else {
					PrintTool.print(getResponse(), "false|" + msg.getMsg(),
							null);
					log.info(manager.getAccount() + "订单[" + id + "]出库失败。。。");
				}
			} catch (Exception e) {
				log.error("出库失败", e);
				PrintTool.print(getResponse(), "false|出库失败，数据操作异常", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|需出库的订单未找到", null);
		}
		return null;
	}

	/**
	 * 订单归档
	 * @return
	 * @throws IOException
	 */
	public String endOrder() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			TManager manager = (TManager) getSession().getAttribute("login");
			log.info(manager.getAccount() + "准备将订单[" + id + "]归档。。。");
			OrderBus bus = new OrderBus();
			try {
				Data msg = bus.updateOrderState(manager, "endOrder", "9", id,
						null);
				if (msg.isState()) {
					PrintTool
							.print(getResponse(), "true|" + msg.getMsg(), null);
					log.info(manager.getAccount() + "订单[" + id + "]归档成功。。。");
				} else {
					PrintTool.print(getResponse(), "false|" + msg.getMsg(),
							null);
					log.info(manager.getAccount() + "订单[" + id + "]归档失败。。。");
				}
			} catch (Exception e) {
				log.error("归档失败", e);
				PrintTool.print(getResponse(), "false|归档失败，数据操作异常", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|需归档的订单未找到", null);
		}
		return null;
	}

	/**
	 * 订单配送
	 * @return
	 */
	public String sendOrder() {
		String orderId = getParameter("orderId");
		String expressCompany = getParameter("expressCompany");
		String expressId = getParameter("expressId");
		String expressName = getParameter("expressName");
		String sendTime = getParameter("sendTime");
		String operateName = getParameter("operateName");
		String remark = getParameter("remark");

		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("orderId", orderId);
		params.put("expressCompany", expressCompany);
		params.put("expressId", expressId);
		params.put("expressName", expressName);
		params.put("sendTime", sendTime);
		params.put("operateName", operateName);
		params.put("remark", remark);

		OrderBus bus = new OrderBus();
		TManager manager = (TManager) getSession().getAttribute("login");
		try {
			log.info(manager.getAccount() + "准备将订单[" + orderId + "]派送。。。");
			Data msg = bus.updateOrderState(manager, "sendOrder", "8", orderId,
					params);
			if (!msg.isState()) {
				log.info(manager.getAccount() + "将订单[" + orderId + "]派送失败。。。");
				getRequest().setAttribute("result", msg.getMsg());
				return "error";
			} else {
				log.info(manager.getAccount() + "将订单[" + orderId + "]派送成功。。。");
				getRequest().setAttribute("result", msg.getMsg());
				//刷新左边菜单的统计数
				getRequest().setAttribute("orderCountRefresh", "true");
				return "success";
			}
		} catch (Exception e) {
			log.error("配送失败", e);
			return "error";
		}
	}

	public String checkOrder() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_order  where state in ('5','6') order by submit_time desc   ");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			List<TOrder> list = jdbc.query(
					ApplicationContextTool.getDataSource(), sql.toString(),
					TOrder.class, null, 0, 0);
			if (list != null && list.size() > 0) {
				for (int i = 0, len = list.size(); i < len; i++) {

				}
				getRequest().setAttribute("list", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "checkOrder";

	}
	
	public String queryOrderId(){
		String id = getParameter("idQuery");
		String fromPart = getParameter("fromPart");
		if(StringUtils.isNotBlank(id)){
			OrderBus orderBus = new OrderBus();
			JDBC jdbc = ApplicationContextTool.getJdbc();
			TOrder po = orderBus.queryOrderByID(jdbc, id);
			if(po!=null){
				po.setFromPart(Env.ORDER_FROM_PART.get(po.getFromPart()));
				po.setSpcode(Env.SPCODE.get(po.getSpcode()));
				po.setConfigure(Env.ORDER_CONFIGURE.get(po.getConfigure()));
				po.setState(Env.ORDER_STATE.get(po.getState()));
				getRequest().setAttribute("order", po);
				TManager manager = (TManager) getSession().getAttribute("login");
				if (manager.getAccount().equals(po.getAccount())||Env.GD.equals(manager.getAreacode())) {
					OrderBus bus = new OrderBus();
					List<TOrderState> stateList = bus
							.queryOrderStateByOrderID(jdbc, po.getId());
					if (stateList != null&&stateList.size()>0){
						getRequest().setAttribute("orderDetail", stateList);
					}
				}
				//号百审核订单查询
				if("updateHBReviewOrder".equals(fromPart)){
					return "updateHBReviewOrder";
				}
				//管理员审核订单查询
				if("updateReviewOrder".equals(fromPart)){
					return "updateReviewOrder";
				}
				
			}else{
				getRequest().setAttribute("result", "订单未找到");
				return "error";
			}
		}
		getRequest().setAttribute("result", "订单未找到");
		return "error";
	}
	
	
	
	/**
	 * 待号百人员审核的单
	 * @return
	 */
	public String showHBReviewOrder() {
		String state = getParameter("state");
		// state = (1:待审核的单，4：产品经理二次修改待审核，)
		String id = getParameter("idQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_order  where 1=1  ");
		TManager manager = (TManager) getSession().getAttribute("login");
		List<Object> params = new ArrayList<Object>();
//		if (manager.getRoleid() == 0) {
//			if (StringUtils.isNotBlank(state)) {
//				sql.append(" and state =  ? ");
//				params.add(state);
//				getRequest().setAttribute("state", state);
//			}
//		} else {
//			if (StringUtils.isNotBlank(state)) {
//				sql.append(" and state =? ");
//				params.add(state);
//			}else{
//				sql.append(" and state in('H','T') ");
//			}
//			
//		}
		
		if (StringUtils.isNotBlank(id)) {
			sql.append(" and id =  ?  ");
			params.add(id);
			getRequest().setAttribute("id", id);
		}
		sql.append(" and state in('H','T') ");
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and submit_time>? and submit_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by submit_time desc ");
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TOrder> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), TOrder.class);
			if (pageResult != null) {
				List<TOrder> list = pageResult.getContent();
				if (list != null && list.size() > 0) {

					for (int i = 0, len = list.size(); i < len; i++) {

					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showHBReviewOrder";

	}
	
	
	/**
	 * 号百人员审核订单
	 * @return
	 * @throws IOException
	 */
	public String updateHBReviewOrder() throws IOException {
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			TManager manager = (TManager) getSession().getAttribute("login");
			log.info(manager.getAccount() + "准备审核地市提交的订单[" + id + "]。。。");
			OrderBus bus = new OrderBus();
			try {
				String shenheState = getParameter("shenheState");
				String shenheResult = getParameter("shenheResult");
				Map<String, String> params = new HashMap<String,String>();
				params.put("shenheState", shenheState);
				params.put("shenheResult", shenheResult);
				Data msg = bus.updateOrderState(manager, "hbReviewOrder", "H", id,
						params);
				if (msg.isState()) {
					PrintTool
							.print(getResponse(), "true|" + msg.getMsg(), null);
					log.info(manager.getAccount() + "审核地市订单[" + id + "]成功。。。");
				} else {
					PrintTool.print(getResponse(), "false|" + msg.getMsg(),
							null);
					log.info(manager.getAccount() + "审核地市订单[" + id + "]失败。。。");
				}
			} catch (Exception e) {
				log.error("审核地市订单失败", e);
				PrintTool.print(getResponse(), "false|审核地市订单失败，数据操作异常", null);
			}
		} else {
			PrintTool.print(getResponse(), "false|审核地市订单未找到", null);
		}
		return null;
	}

}
