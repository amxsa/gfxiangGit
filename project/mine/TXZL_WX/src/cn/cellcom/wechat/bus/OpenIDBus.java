package cn.cellcom.wechat.bus;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.common.WechatTool;
import cn.cellcom.wechat.po.TOpenid;
import cn.cellcom.wechat.po.UserInfo;

public class OpenIDBus {
	private static final Log log = LogFactory.getLog(OpenIDBus.class);

	public TOpenid query(JDBC jdbc, String openid) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TOpenid po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_openid where openid = ? ", TOpenid.class,
					new String[] { openid });
			return po;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean addOpenid(JDBC jdbc, String openid, boolean isNickname) {
		if (StringUtils.isBlank(openid) || openid.length() != 28) {
			return false;
		}
		String nickname = "";
		if (isNickname) {
			UserInfo userinfo = WechatTool.getUserinfo(WechatTool.getToken(),
					openid);
			if (userinfo == null) {
				return false;
			}
			nickname = userinfo.getNickname().replaceAll("[^\\u0000-\\uFFFF]", "");
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		TOpenid po = query(jdbc, openid);
		if (po == null) {
			try {
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"insert into t_openid(openid,nickname,submit_time) values(?,?,?)",
						new Object[] {
								openid,
								nickname,
								DateTool.formateTime2String(new Date(),
										"yyyy-MM-dd HH:mm:ss") });
			} catch (SQLException e) {
				log.error("", e);
			}
		}
		return true;

	}

	public int delelteOpenid(JDBC jdbc, String openid) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			int count = jdbc.update(ApplicationContextTool.getDataSource(),
					"delete from t_openid where openid = ?",
					new String[] { openid });
			return count;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public static void main(String[] args) {

	}
}
