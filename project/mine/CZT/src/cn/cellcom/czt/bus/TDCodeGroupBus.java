package cn.cellcom.czt.bus;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.DB.JdbcResultSet;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.TTdcodeGroup;
import cn.cellcom.web.spring.ApplicationContextTool;

public class TDCodeGroupBus {
	public Map<Integer, TTdcodeGroup> getGroup() {
		// TODO Auto-generated method stub
		try {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			List<TTdcodeGroup> list = jdbc.query(
					ApplicationContextTool.getDataSource(),
					"select *  from t_tdcode_group", TTdcodeGroup.class, null,
					0, 0);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Env.TDCODE_GROUP.put(list.get(i).getNumber(), list.get(i));
				}
				return Env.TDCODE_GROUP;
			}
			return null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
