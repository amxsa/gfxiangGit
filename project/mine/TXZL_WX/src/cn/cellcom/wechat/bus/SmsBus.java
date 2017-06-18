package cn.cellcom.wechat.bus;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.po.DataMsg;

public class SmsBus {
	private static final Log log = LogFactory.getLog(SmsBus.class);

	public DataMsg sendSms(Map<String, String> params) {
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		String regNo = params.get("regNo");
		String dest = params.get("dest");
		String model = params.get("model");
		String msgContent = params.get("msgContent");
		String fromPart = params.get("fromPart");
		DataMsg msg = new DataMsg(false, "发送短信失败");
		log.info("注册账号" + regNo + "开始发送[" + model + "]短信,接收号码:" + dest
				+ ",短信内容:" + msgContent);
		StringBuffer sql = new StringBuffer();
		int count = 0;
		try {
			sql.append("insert into t_submit (source, dest, charge_number, data_coding, msg_content, schedule_time, expire_time, account_level, account_type, fee_type_1, fee_type_2, fee_value, fee_code, fixed_fee, msg_type, service_type, state, submit_time, sendtimes, batch_id, from_part,  resend_times, time_slot,  ref, a_number, reg_no) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			Timestamp time = new Timestamp(new Date().getTime());
			Object[] values = { regNo, dest, regNo, 15, msgContent, time, time,
					"0", "0", "0", "00", "0", "0", "0", 6, "11403", "N", time,
					0, "1", fromPart, 0, 0, 3, regNo, regNo };
			count = jdbc.update(ApplicationContextTool.getDataSource(),
					sql.toString(), values);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("发送短信失败", e);
			return msg;
		}
		if (count > 0) {
			log.info("注册账号" + regNo + "发送短信完毕");
			msg.setState(true);
			msg.setMsg("短信发送成功");
			return msg;
		} else {
			return msg;
		}

	}
}
