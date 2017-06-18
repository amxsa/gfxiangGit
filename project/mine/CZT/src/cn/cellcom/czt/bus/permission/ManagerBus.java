package cn.cellcom.czt.bus.permission;

import java.util.Map;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.spring.ApplicationContextTool;
public class ManagerBus {
	
	
	public Data addManager(Map<String, String> params){
		
		return null;
	}
	public TManager queryManager(String account){
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				if (account != null) {
					TManager manager = jdbc.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select * from t_manager where account=?", TManager.class,
							new Object[] { account });
					return manager;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
	
}
