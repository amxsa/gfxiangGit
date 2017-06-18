package cn.cellcom.czt.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.DB.JdbcResultSet;
import cn.cellcom.common.DB.ProcedureResult;
import cn.cellcom.common.data.ArrayTool;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.web.struts.Struts2Base;

public class TotalManagerAction extends Struts2Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String totalMenuOrder() throws IOException {
		TManager manager = (TManager) getSession().getAttribute("login");
		JSONObject obj = new JSONObject();

		JDBC jdbc = ApplicationContextTool.getJdbc();

		try {
			if (manager.getRoleid() == 0) {
				List<Map<String, Object>> list = jdbc
						.query(ApplicationContextTool.getDataSource(),
								"select sum( case when state ='1' then 1 else 0 end )  as review_order_count,sum( case when state in('5','6')  then 1 else 0 end )  as bind_order_count,sum( case when state ='7'  then 1 else 0 end )  as out_order_count,sum( case when state ='8'  then 1 else 0 end )  as send_order_count,sum( case when state ='9'  then 1 else 0 end )  as end_order_count,sum( case when state ='H' then 1 else 0 end ) as hb_review_order_count from t_order where state in('1','5','6','7','8','9','H')",
								null, 0, 0);
				if (list != null && list.size() > 0) {
					Map<String, Object> total = list.get(0);
					obj.put("state", "S");
					obj.put("reviewOrderCount",
							total.get("reviewOrderCount") == null ? 0 : total
									.get("reviewOrderCount"));
					obj.put("bindOrderCount",
							total.get("bindOrderCount") == null ? 0 : total
									.get("bindOrderCount"));
					obj.put("outOrderCount",
							total.get("outOrderCount") == null ? 0 : total
									.get("outOrderCount"));
					obj.put("sendOrderCount",
							total.get("sendOrderCount") == null ? 0 : total
									.get("sendOrderCount"));
					obj.put("endOrderCount",
							total.get("endOrderCount") == null ? 0 : total
									.get("endOrderCount"));
					obj.put("hbReviewOrderCount",
							total.get("hbReviewOrderCount") == null ? 0 : total
									.get("hbReviewOrderCount"));
					PrintTool.print(getResponse(), obj, "json");
				}
			}else if("000".equals(manager.getAreacode())){
				List<Map<String, Object>> list = jdbc
						.query(ApplicationContextTool.getDataSource(),
								"select sum( case when state ='H' then 1 else 0 end ) as hb_review_order_count,sum( case when state ='8'  then 1 else 0 end )  as send_order_count,sum( case when state ='9'  then 1 else 0 end )  as end_order_count  from t_order where account!='admin' and  state in('H','8','9') ",
								null, 0, 0);
				if (list != null && list.size() > 0) {
					Map<String, Object> total = list.get(0);
					obj.put("state", "S");
					obj.put("sendOrderCount",
							total.get("sendOrderCount") == null ? 0 : total
									.get("sendOrderCount"));
					obj.put("endOrderCount",
							total.get("endOrderCount") == null ? 0 : total
									.get("endOrderCount"));
					obj.put("hbReviewOrderCount",
							total.get("hbReviewOrderCount") == null ? 0 : total
									.get("hbReviewOrderCount"));
					PrintTool.print(getResponse(), obj, "json");
				}
			}else {
				obj.put("state", "F");
				obj.put("msg", "无统计菜单数据权限");
				PrintTool.print(getResponse(), obj.toString(), "json");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("state", "F");
			obj.put("msg", "统计菜单数据错误");
			PrintTool.print(getResponse(), obj.toString(), "json");
		}

		return null;
	}
	public String totalMenuSettle() throws IOException {
		TManager manager = (TManager) getSession().getAttribute("login");
		JSONObject obj = new JSONObject();
		
		JDBC jdbc = ApplicationContextTool.getJdbc();
		
		try {
			List<Map<String, Object>> list = jdbc
					.query(ApplicationContextTool.getDataSource(),
							"select sum( case when settle_accounts_flag in ('','F','N') or settle_accounts_flag is null then 1 else 0 end )  as waitSettleCount,sum( case when settle_accounts_flag='B'  then 1 else 0 end )  as settleVerifyCount from t_order where 1=1",
							null, 0, 0);
			if (list != null && list.size() > 0) {
				Map<String, Object> total = list.get(0);
				obj.put("state", "S");
				obj.put("waitSettleCount",
						total.get("waitSettleCount") == null ? 0 : total
								.get("waitSettleCount"));
				obj.put("settleVerifyCount",
						total.get("settleVerifyCount") == null ? 0 : total
								.get("settleVerifyCount"));
				
				PrintTool.print(getResponse(), obj, "json");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("state", "F");
			obj.put("msg", "统计菜单数据错误");
			PrintTool.print(getResponse(), obj.toString(), "json");
		}
		
		return null;
	}

	public String indexTotal() {
		TManager manager = (TManager) getSession().getAttribute("login");
		// JSONObject obj = new JSONObject();
		if (manager.getRoleid() == 0) {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			ProcedureResult procedureResult;
			try {
				procedureResult = jdbc.execProcedure(
						ApplicationContextTool.getDataSource(),
						"sp_index_total", null);
				if (procedureResult != null) {
					List<JdbcResultSet> resultList = procedureResult
							.getResultList();
					if (!ArrayTool.isEmpty(resultList)) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("xianshangOrder", resultList.get(0)
								.getValueByFieldName("xianshang_order", 0));
						map.put("qudaoOrder", resultList.get(0)
								.getValueByFieldName("qudao_order", 0));
						map.put("obdCount", resultList.get(0)
								.getValueByFieldName("obd_count", 0));
						map.put("cztCount", resultList.get(0)
								.getValueByFieldName("czt_count", 0));
						map.put("cancelCount", resultList.get(0)
								.getValueByFieldName("cancel_count", 0));
						map.put("reviewOrderCount", resultList.get(0)
								.getValueByFieldName("review_order_count", 0));
						map.put("bindOrderCount", resultList.get(0)
								.getValueByFieldName("bind_order_count", 0));
						map.put("outOrderCount", resultList.get(0)
								.getValueByFieldName("out_order_count", 0));
						map.put("sendOrderCount", resultList.get(0)
								.getValueByFieldName("send_order_count", 0));
						map.put("endOrderCount", resultList.get(0)
								.getValueByFieldName("end_order_count", 0));
						map.put("expireCount", resultList.get(0)
								.getValueByFieldName("expire_count", 0));
						map.put("exceedCount", resultList.get(0)
								.getValueByFieldName("exceed_count", 0));
						map.put("noActivateCount", resultList.get(0)
								.getValueByFieldName("no_activate_count", 0));
						getRequest().setAttribute("map", map);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "main";
	}

}
