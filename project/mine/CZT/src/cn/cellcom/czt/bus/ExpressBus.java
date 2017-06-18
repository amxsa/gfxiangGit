package cn.cellcom.czt.bus;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TDevice;
import cn.cellcom.czt.po.TExpress;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.spring.ApplicationContextTool;

public class ExpressBus {
	private static final Log log = LogFactory.getLog(ExpressBus.class);
	public Data addExpress(TManager manager, Map<String, String> params) {
		Data data = new Data(false, "配送失败");
		String checkParams = checkExpressParam(params);
		if (checkParams != null) {
			data.setMsg(checkParams);
			return data;
		}
		JDBC jdbc = ApplicationContextTool.getJdbc();
		int count = 0;
		try {
			count = jdbc
					.update(ApplicationContextTool.getDataSource(),
							"insert into t_express(account,order_id,express_id,express_company,express_name,send_time,operate_name,remark,submit_time) values(?,?,?,?,?,?,?,?,?)",
							new Object[] { manager.getAccount(),params.get("orderId"),
									params.get("expressId"),
									params.get("expressCompany"),params.get("expressName"),
									params.get("sendTime"),
									params.get("operateName"),
									params.get("remark"), new Date() });
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("配送异常",e);
		}
		if (count > 0) {
			data.setMsg("配送成功");
			data.setState(true);
			return data;
		} else {
			data.setMsg("配送失败");
		}
		return data;
	}
	
	public TExpress queryByOrderId(JDBC jdbc,String orderId){
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TExpress po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_express where order_id = ? ", TExpress.class,
					new String[] { orderId });
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

	private String checkExpressParam(Map<String, String> params) {
		return null;
	}
}
