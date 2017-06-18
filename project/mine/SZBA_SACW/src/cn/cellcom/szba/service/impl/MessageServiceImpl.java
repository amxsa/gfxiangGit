package cn.cellcom.szba.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import cn.cellcom.szba.biz.TMessageBiz;
import cn.cellcom.szba.common.DateTool;
import cn.cellcom.szba.common.DateUtil;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.EnvMessage;
import cn.cellcom.szba.common.SpringWebApplicataionContext;
import cn.cellcom.szba.service.MessageService;
import cn.cellcom.szba.vo.template.TemplateMessage;
import cn.open.db.JDBC;

public class MessageServiceImpl implements MessageService {
	private static Log log = LogFactory.getLog(MessageServiceImpl.class);

	private TMessageBiz messageBiz = new TMessageBiz();

	@Autowired
	private VelocityEngine velocityEngine;

	// for xml
	private Map<String, String> msgTemplateKeyMap;

	private static String SQL_UPDATE_T_MESSAGE = "insert into t_message(title,content,type,readstatus,account,href,create_time,is_relative)"
			+ "values(?,?,?,?,?,?,TO_DATE(?, '"
			+ DateUtil.OracleDatetimeFormatString + "'),?)";

	@Override
	/*
	 * 实现往消息记录表写一条记录
	 * 
	 * @see cn.cellcom.szba.service.MessageService#SendMessage(java.lang.String,
	 * java.lang.String, char, java.lang.String, char)
	 */
	public boolean SendMessage(String title, String content, String type,
			String account, String isRelative) {
		boolean result = false;
		int rows = 0;
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		try {
			rows = jdbc.update(Env.DS, SQL_UPDATE_T_MESSAGE, title, content,
					type, EnvMessage.READ_STATUS, account, EnvMessage.HREF,
					DateTool.formateTime2String(new Date()), isRelative);
			if (rows > 0) {
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			jdbc.closeAll();
		}
		return result;
	}

	@Override
	public boolean SendMessageToMore(String title, String content, String type,
			String handlers, String isRelative) {

		String[] accounts = handlers.split(",");
		JDBC jdbc = SpringWebApplicataionContext.getJdbc();
		Connection conn = null;
		try {
			conn = Env.DS.getConnection();
			conn.setAutoCommit(false);

			for (String account : accounts) {
				jdbc.update(conn, SQL_UPDATE_T_MESSAGE, title, content, type,
						EnvMessage.READ_STATUS, account, EnvMessage.HREF,
						DateTool.formateTime2String(new Date()), isRelative);

			}

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return false;
		} finally {
			jdbc.closeAll();
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public Boolean publicMessage(String type, String dayCounts) {
		Map<String, Object> param = new HashMap<String, Object>();
		log.info("type: " + type);
		log.info("dayCounts: " + dayCounts);
		List<TemplateMessage> list = messageBiz.queryMessage(type,dayCounts);
		String contents;
		String[] str;
		String title;
		String content;
		/*
		 * RZCK:入暂存库；RZCKCQ:入暂存库超期 ZCKRZXK：暂存库入中心库；ZCKRZXKCQ：暂存库入中心库超期
		 * CWDYGH：财务调用归还；CWDYGHCQ：财务调用归还超期 TQCW：提取财务；DB：待办；CB：催办
		 */
		String filePath = msgTemplateKeyMap.get(type);
		log.info("filePath: " + filePath);
		for(TemplateMessage tm : list) {
		   param.put("casename", tm.getCasename());
		   param.put("transactor", tm.getTransactor());
		   param.put("proname", tm.getProname());
		   contents = generateTemplateString(filePath, param);
	       str = contents.split("-");
		   title = str[0].replaceAll("title：", "");
		   content = str[1].replaceAll("content：", "");
		   this.SendMessage(title, content,
		   EnvMessage.TYPE.SYS_TRIGGER.value(), tm.getTransactor(),
		   EnvMessage.IS_RELATIVE.IS_RELATIVE.value());
		 }
		return true;
	}

	private String generateTemplateString(String templateFilePath,
			Map<String, Object> model) {
		String content = null;
		try {
			content = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine, templateFilePath, "UTF-8", model);
		} catch (Exception e) {
		}
		return content;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public Map<String, String> getMsgTemplateKeyMap() {
		return msgTemplateKeyMap;
	}

	public void setMsgTemplateKeyMap(Map<String, String> msgTemplateKeyMap) {
		this.msgTemplateKeyMap = msgTemplateKeyMap;
	}

}
