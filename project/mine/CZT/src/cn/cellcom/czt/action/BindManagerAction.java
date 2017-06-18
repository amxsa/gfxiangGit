package cn.cellcom.czt.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.bus.BindBus;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.LimiteBind;
import cn.cellcom.czt.po.TBindDeviceFluxcard;
import cn.cellcom.czt.po.TLinkman;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TOrder;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class BindManagerAction extends Struts2Base {
	private static final Log log = LogFactory.getLog(BindManagerAction.class);

	public String showBind() {
		String orderId = getParameter("orderIdQuery");
		String sn = getParameter("snQuery");
		String mobile = getParameter("mobileQuery");
		String starttime = getParameter("starttimeQuery");
		String endtime = getParameter("endtimeQuery");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.state as order_state  from t_bind_device_fluxcard a left join t_order b  on a.order_id = b .id  ");
		sql.append(" where 1 =1 ");
		TManager manager = (TManager) getSession().getAttribute("login");
		List<Object> params = new ArrayList<Object>();
		if (manager.getRoleid() != 0) {
			sql.append(" and a.account =  ? ");
			params.add(manager.getAccount());
		}
		if (StringUtils.isNotBlank(orderId)) {
			sql.append(" and a.order_id =  ?  ");
			params.add(orderId);
			getRequest().setAttribute("orderId", orderId);
		}
		if (StringUtils.isNotBlank(sn)) {
			if (sn.length() < 8) {
				getRequest().setAttribute("result", "SN至少输入8位");
				return "error";
			} else {
				sql.append(" and a.sn like ?  ");
				params.add("%" + sn + "%");
				getRequest().setAttribute("sn", sn);
			}
		}

		if (StringUtils.isNotBlank(mobile)) {
			sql.append(" and a.mobile = ?  ");
			params.add(mobile);
			getRequest().setAttribute("mobile", mobile);
		}
		if (StringUtils.isNotBlank(starttime)
				&& StringUtils.isNotBlank(endtime)) {
			sql.append(" and a.submit_time>? and a.submit_time< ? ");
			params.add(starttime);
			params.add(endtime + " 23:59:59");
		}
		getRequest().setAttribute("starttime", starttime);
		getRequest().setAttribute("endtime", endtime);
		sql.append(" order by a.submit_time desc ");
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<TBindDeviceFluxcard> pageResult = pageBus
					.queryPageDataMysql(jdbc,
							ApplicationContextTool.getDataSource(), page,
							sql.toString(), params.toArray(),
							TBindDeviceFluxcard.class);
			if (pageResult != null) {
				List<TBindDeviceFluxcard> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					TBindDeviceFluxcard po = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						po = list.get(i);
					}
				}
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showBind";

	}

	public String batchBind() {
		String orderId = getParameter("orderId");
		String deviceId = getParameter("deviceId");
		String fluxCardId = getParameter("fluxCardId");
		String operateName = getParameter("operateName");
		String remark = getParameter("remark");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("orderId", orderId);
		params.put("deviceId", deviceId);
		params.put("fluxCardId", fluxCardId);
		params.put("operateName", operateName);
		params.put("remark", remark);
		BindBus bindBus = new BindBus();
		TManager manager = (TManager) getSession().getAttribute("login");
		Data msg = bindBus.bindDevcieFluxCard(manager, params);
		if (msg.isState()) {
			getRequest().setAttribute("result", msg.getMsg());
			//刷新左边菜单的统计数
			getRequest().setAttribute("orderCountRefresh", "true");
			return "success";
		} else {
			getRequest().setAttribute("result", msg.getMsg());
			return "error";
		}
	}

	public String deleteBind() throws IOException {
		
		String id = getParameter("id");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("id", id);
		try {
			BindBus bus = new BindBus();
			TManager manager = (TManager) getSession().getAttribute("login");
			Data msg = bus.deleteDevcieFluxCard(manager, params);
			if (msg.isState()) {
				PrintTool.print(getResponse(), "true|" + msg.getMsg(), null);

			} else {
				PrintTool.print(getResponse(), "false|" + msg.getMsg(), null);

			}
		} catch (Exception e) {
			log.error("删除捆绑失败", e);
			PrintTool.print(getResponse(), "false|删除捆绑失败", null);
		}
		return null;
	}
	
	public String showLimiteBind() {
		String sn = getParameter("sn");
		String fluxCard = getParameter("fluxCard");
		String mobilenum = getParameter("mobilenum");
		String type = getParameter("type");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.sn, a.mobile as fluxcard,b.mobilenum,a.limite_time  from t_bind_device_fluxcard  a left join sp_tdcode b  on a.sn = b.tdcodemd5  ");
		sql.append(" where b.mobilenum is not null  ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(type)) {
			//1=到期预警：   是指设备生命周期到期前两周做预警统计
			if("1".equals(type)){
				sql.append(" and  a.limite_time<date_add(now(), interval 14 day) and a.limite_time>now()" );
				
			}else if("2".equals(type)){
				//2-到期用户： 是指设备生命周期结束用户。
				sql.append(" and  a.limite_time< now() ");
			}
			getRequest().setAttribute("type", type);
		}
		
		if (StringUtils.isNotBlank(sn)) {
			if (sn.length() < 8) {
				getRequest().setAttribute("result", "SN至少输入8位");
				return "error";
			} else if(sn.length()==20) {
				sql.append(" and a.sn = ?   ");
				params.add(sn);
				getRequest().setAttribute("sn", sn);
			}else if(sn.length()>=8&&sn.length()<20){
				sql.append(" and a.sn like ?  ");
				params.add("%" + sn);
				getRequest().setAttribute("sn", sn);
			}else{
				getRequest().setAttribute("result", "SN输入错误");
				return "error";
			}
		}
		if (StringUtils.isNotBlank(fluxCard)) {
			sql.append(" and a.mobile = ?  ");
			params.add(fluxCard);
			getRequest().setAttribute("fluxCard", fluxCard);
		}

		if (StringUtils.isNotBlank(mobilenum)) {
			sql.append(" and b.mobilenum = ?  ");
			params.add(mobilenum);
			getRequest().setAttribute("mobilenum", mobilenum);
		}
		
		sql.append(" order by a.submit_time desc ");
		System.out.println(sql.toString());
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(getParameter("currentPage"), 20,
				getRequest().getRequestURI());
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			PageResult<LimiteBind> pageResult = pageBus
					.queryPageDataMysql(jdbc,
							ApplicationContextTool.getDataSource(), page,
							sql.toString(), params.toArray(),
							LimiteBind.class);
			if (pageResult != null) {
				List<LimiteBind> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					LimiteBind po = null;
					for (int i = 0, len = list.size(); i < len; i++) {
						po = list.get(i);
					}
				}
				
				getRequest().setAttribute("pageResult", pageResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "showLimiteBind";

	}

	
	
}
