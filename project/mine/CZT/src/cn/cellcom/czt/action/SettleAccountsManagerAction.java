package cn.cellcom.czt.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.bus.SettleAccountsManagerBus;
import cn.cellcom.czt.po.DataMsg;
import cn.cellcom.czt.po.OrderSettleAccounts;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class SettleAccountsManagerAction extends Struts2Base {
	public String showReviewSettleAccounts() {
		String operation = getParameter("operation");
		String settleAccountsFlag = getParameter("settleAccountsStateQuery");
		String id = getParameter("idQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		String orderArea = getParameter("orderAreaQuery");
		String configure = getParameter("configureQuery");
		String orderState = getParameter("orderStateQuery");
		StringBuffer sql = new StringBuffer();
		Map<String, String> params=new HashMap<String, String>();
		params.put("settleAccountsFlag", settleAccountsFlag);
		params.put("id", id);
		params.put("starttime", starttime);
		params.put("endtime", endtime);
		params.put("orderArea", orderArea);
		params.put("configure", configure);
		params.put("orderState", orderState);
		params.put("settleAccountsFlag", settleAccountsFlag);
		//待结算or审核or已结算
		if (operation!=null) {
			if (operation.equals("settle")) {
				sql.append("select a.id as orderId,a.submit_time as orderTime,a.remark as remark,a.receive_telephone as telephone ,a.configure,a.unit_price as originalUnitPrice,a.count as count,a.total as originalTotalPrice,a.state as orderState,b.areacode,a.settle_accounts_flag as state,a.id as id from t_order a left join t_manager b on a.account = b.account   where  (a.settle_accounts_flag is null or a.settle_accounts_flag='F' or a.settle_accounts_flag= '') ");
				getPageResult(params,sql);
				return "showReviewSettleAccounts";
			}else if (operation.equals("verify")) {
				sql.append("select a.id as orderId,a.submit_time as orderTime,a.remark as remark,a.receive_telephone as telephone ,a.configure,a.unit_price as originalUnitPrice,a.count as count,a.total as originalTotalPrice,a.state as orderState,b.areacode,a.settle_accounts_flag as state,a.id as id from t_order a left join t_manager b on a.account = b.account left join t_order_settle_accounts c on a.id=c.order_id   where  (a.settle_accounts_flag='B' or a.settle_accounts_flag='Y') ");
				getPageResult(params,sql);
				return "showSettleAccountsVerify";
			}else if (operation.equals("getVerify")) {//已结算
				sql.append("select a.id as orderId,a.submit_time as orderTime,a.remark as remark,a.receive_telephone as telephone ,a.configure,a.unit_price as originalUnitPrice,a.count as count,a.total as originalTotalPrice,a.state as orderState,b.areacode,a.settle_accounts_flag as state,c.total as total,a.id as id from t_order a left join t_manager b on a.account = b.account left join t_order_settle_accounts c on a.id=c.order_id   where  a.settle_accounts_flag='Y' ");
				getPageResult(params,sql);
				return "showHaveSettle";
			}
		}
		return null;
	}
	
	private void getPageResult(Map<String, String> map, StringBuffer sql) {
		TManager manager = (TManager) getSession().getAttribute("login");
		String id = map.get("id");
		String starttime = map.get("starttime");
		String endtime = map.get("endtime");
		String orderArea = map.get("orderArea");
		String configure = map.get("configure");
		String settleAccountsFlag = map.get("settleAccountsFlag");
		String settleAccountsState = map.get("settleAccountsState");
		String state = map.get("orderState");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(id)) {
			sql.append(" and a.id =  ?  ");
			params.add(id);
			getRequest().setAttribute("id", id);
		}
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and a.submit_time>? and a.submit_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		if (StringUtils.isNotBlank(orderArea)) {
			sql.append(" and b.areacode =  ?  ");
			params.add(orderArea);
			getRequest().setAttribute("orderArea", orderArea);
		}
		if (StringUtils.isNotBlank(configure)) {
			sql.append(" and a.configure =  ?  ");
			params.add(configure);
			getRequest().setAttribute("configure", configure);
		}
		if (StringUtils.isNotBlank(settleAccountsFlag)) {
			sql.append(" and a.settle_accounts_flag =  ?  ");
			params.add(settleAccountsFlag);
			getRequest().setAttribute("settleAccountsFlag", settleAccountsFlag);
		}
		
		if (StringUtils.isNotBlank(state)) {
			sql.append(" and a.state =  ?  ");
			params.add(state);
			getRequest().setAttribute("state", state);
		}
		sql.append(" order by a.submit_time desc ");
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<OrderSettleAccounts> pageResult = pageBus.queryPageDataMysql(jdbc,
					ApplicationContextTool.getDataSource(), page,
					sql.toString(), params.toArray(), OrderSettleAccounts.class);
			if (pageResult != null) {
				List<OrderSettleAccounts> list = pageResult.getContent();
				
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//订单结算页面
	public String settleOrder() {
		String id = getParameter("id");
		try {
			if (StringUtils.isNotBlank(id)) {
				SettleAccountsManagerBus bus=new SettleAccountsManagerBus();
				JDBC jdbc = ApplicationContextTool.getJdbc();
				OrderSettleAccounts po = bus.querySettleOrderById(jdbc, id);
				if (po != null) {
					getRequest().setAttribute("po", po);
					PrintTool.print(getResponse(), JSONObject.fromObject(po), "json");
					return null;
				} else {
					getRequest().setAttribute("result", "订单未找到");
					return "error";
				}
			} else {
				getRequest().setAttribute("result", "订单号不能为空");
				return "error";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "orderSettle";
	}
	
	public String launchSettle(){
		String orderId = getParameter("id");
		String count = getParameter("countHidden");
		String account = getParameter("account");
		String originator = getParameter("originator");//发起人
		String projectName = getParameter("projectName");
		String unitPrice = getParameter("unitPrice");//结算单价
		String contractNo = getParameter("contractNo");
		String invoiceNo = getParameter("invoiceNo");
		try {
			Map<String, String> params=new HashMap<String, String>();
			if (StringUtils.isBlank(orderId)) {
				PrintTool.print(getResponse(), "false|订单号为空，不合法的订单", null);
				return null;
			}
			params.put("orderId", orderId);
			if (StringUtils.isBlank(count)) {
				PrintTool.print(getResponse(), "false|订单产品数量为空，不合法的订单", null);
				return null;
			}else {
				if (Integer.parseInt(count)<0) {
					PrintTool.print(getResponse(), "false|订单产品数量不可为负数，不合法的订单", null);
					return null;
				}
				params.put("count", count);
			}
			
			if (StringUtils.isBlank(account)) {
				PrintTool.print(getResponse(), "false|不合法的订单", null);
				return null;
			}
			params.put("account", account);
			
			if (StringUtils.isBlank(originator)) {
				PrintTool.print(getResponse(), "false|订单无发起人，不合法的订单", null);
				return null;
			}
			params.put("originator", originator);
			
			if (StringUtils.isBlank(projectName)) {
				PrintTool.print(getResponse(), "false|订单无项目名，不合法的订单", null);
				return null;
			}
			params.put("projectName", projectName);
			
			if (StringUtils.isNotBlank(unitPrice)) {
				if (Integer.parseInt(unitPrice)<0) {
					PrintTool.print(getResponse(), "false|单价不可为负数，不合法的订单", null);
					return null;
				}
				params.put("unitPrice", unitPrice);
			}else {
				PrintTool.print(getResponse(), "false|单价不可为空", null);
				return null;
			}
			
			if (StringUtils.isBlank(contractNo)&&StringUtils.isBlank(invoiceNo)) {
				PrintTool.print(getResponse(), "false|合同编号和发票编号至少选择一个", null);
				return null;
			}
			if (StringUtils.isNotBlank(contractNo)) {
				params.put("contractNo", contractNo);
			}
			if (StringUtils.isNotBlank(invoiceNo)) {
				params.put("invoiceNo", invoiceNo);
			}
			params.put("unitPrice", unitPrice);
			JDBC jdbc = ApplicationContextTool.getJdbc();
			SettleAccountsManagerBus bus=new SettleAccountsManagerBus();
			DataMsg data=bus.launchSettleAccounts(params,jdbc);
			PrintTool.print(getResponse(), data.getState()+"|"+data.getInfo(), null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//加载结算审核界面
	public String settleOrderVerify(){
		String id = getParameter("id");
		try {
			if (StringUtils.isNotBlank(id)) {
				SettleAccountsManagerBus bus=new SettleAccountsManagerBus();
				JDBC jdbc = ApplicationContextTool.getJdbc();
				OrderSettleAccounts po = bus.queryVerifySettleOrderById(jdbc, id);
				if (po != null) {
					po.setTimeStr(DateTool.formateTime2String(po.getSubmitTime(), "yyyy-MM-dd HH:mm:ss"));
					PrintTool.print(getResponse(), JSONObject.fromObject(po), "json");
					return null;
//					getRequest().setAttribute("po", po);
					
				} else {
					getRequest().setAttribute("result", "订单未找到");
					return "error";
				}
			} else {
				getRequest().setAttribute("result", "订单号不能为空");
				return "error";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "settleOrderVerify";
	}
	//加载已结算订单界面
	public String settleAccountsDetail(){
		String id = getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			SettleAccountsManagerBus bus=new SettleAccountsManagerBus();
			JDBC jdbc = ApplicationContextTool.getJdbc();
			OrderSettleAccounts po = bus.querySettleAccountsDetailById(jdbc, id);
			if (po != null) {
				getRequest().setAttribute("po", po);
			} else {
				getRequest().setAttribute("result", "订单未找到");
				return "error";
			}
		} else {
			getRequest().setAttribute("result", "订单号不能为空");
			return "error";
		}
		return "settleAccountsDetail";
	}
	
	//审核通过or不通过
	public String onVerify(){
		
		String style = getParameter("style");
		String orderId = getParameter("id");
		String verifyName = getParameter("verifyName");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		DataMsg data=null;
		try {
			if (StringUtils.isBlank(verifyName)) {
				PrintTool.print(getResponse(), "false|审核人不可为空", null);
			}
			if (style.equals("N")) {//不通过
				String remark = getParameter("remark");
				SettleAccountsManagerBus bus=new SettleAccountsManagerBus();
				if (StringUtils.isBlank(orderId)) {
					PrintTool.print(getResponse(), "false|订单号为空", null);
					return null;
				}
				data=bus.disVerify(jdbc,orderId,remark,verifyName);
			}else if(style.equals("Y")){//通过审核
				SettleAccountsManagerBus bus=new SettleAccountsManagerBus();
				data=bus.verify(jdbc,orderId,verifyName);
				PrintTool.print(getResponse(), data.getState()+"|"+data.getInfo(), null);
				return null;
			}else {
				
			}
			PrintTool.print(getResponse(), data.getState()+"|"+data.getInfo(), null);
		} catch (IOException e) {
			try {
				PrintTool.print(getResponse(), "false|操作失败", null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
		
	}
	
	public  String verifyDetail(){
		String orderId = getParameter("id");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		DataMsg data=null;
		SettleAccountsManagerBus bus=new SettleAccountsManagerBus();
		OrderSettleAccounts po=bus.getVerifyDetail(jdbc,orderId);
		if (po != null) {
			getRequest().setAttribute("po", po);
		} else {
			getRequest().setAttribute("result", "订单未找到");
			return "error";
		}
		return "verifyDetail";
	}
}
