package cn.cellcom.wechat.bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;

public class LeavewordBus {
	public Integer showLhCount(JDBC jdbc, String regNo, String type,
			String startTime, String endTime) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*) from ");
			if ("1".equals(type)) {
				sql.append(" v_leaveword_month ");
			} else {
				sql.append(" v_leaveword ");
			}
			sql.append("where reg_no = ? ");
			List<Object> params = new ArrayList<Object>();
			params.add(regNo);
			if (StringUtils.isNotBlank(startTime)
					&& StringUtils.isNotBlank(endTime)) {
				// sql.append(" and ")
			}
			Integer count = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(), sql.toString(),
					Integer.class, params.toArray());
			if (count != null)
				return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	
}
