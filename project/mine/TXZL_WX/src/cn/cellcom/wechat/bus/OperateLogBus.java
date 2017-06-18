package cn.cellcom.wechat.bus;



import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;

public class OperateLogBus {
	public void addOperateLog(JDBC jdbc, String ip, String regNo,
			String operator, Class pojo, String operateType, String operateDesc) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		String className = pojo.getName();
		String tableName = className.substring(className.lastIndexOf(".") + 1);
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"insert into t_operate_log(ip,reg_no,operator,operate_table,operate_type,operate_desc,operate_time) values(?,?,?,?,?,?,getdate())",
					new String[] { ip, regNo, operator, tableName, operateType,
							operateDesc });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
