package cn.cellcom.czt.bus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;

import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TFluxIdcard;

import cn.cellcom.web.spring.ApplicationContextTool;

public class FLuxIdcardBus {
	private static final Log log = LogFactory.getLog(FLuxIdcardBus.class);

	public TFluxIdcard queryByTdcodemd5(JDBC jdbc, String tdcodemd5) {
		if (StringUtils.isBlank(tdcodemd5)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TFluxIdcard po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_flux_idcard where tdcodemd5 = ? ",
					TFluxIdcard.class, new String[] { tdcodemd5 });
			return po;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	public TFluxIdcard queryByMobileNum(JDBC jdbc, String mobileNum) {
		if (StringUtils.isBlank(mobileNum)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TFluxIdcard po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_flux_idcard where mobile_num = ? ",
					TFluxIdcard.class, new String[] { mobileNum });
			return po;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	
	public Data shenheFluxIdcard(Map<String,String> params) {
		Data data = new Data(false, "审核身份信息失败");
		String tdcodemd5= params.get("tdcodemd5");
		String state= params.get("state");
		String resultDescribe= params.get("resultDescribe");
		JDBC jdbc = ApplicationContextTool.getJdbc();
		Connection conn = null;
		try {
			
			conn = ApplicationContextTool.getDataSource().getConnection();
			conn.setAutoCommit(false);
			jdbc.update(conn,
					"update t_flux_idcard set state=?,result_describe=?  where tdcodemd5= ? ",
					new Object[] {state,resultDescribe, tdcodemd5});
			jdbc.update(conn, "update sp_tdcode set idcard_state=? where tdcodemd5= ?", new Object[] {state, tdcodemd5});
			conn.commit();
			conn.setAutoCommit(true);
			data.setState(true);
			data.setMsg("审核身份信息成功");
			log.info(params.get("account")+"审核设备"+tdcodemd5+"["+params.get("fluxcard")+"]的状态是:"+state);
			return data;
		}catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.error("", e);
			return data;
		} finally {
			jdbc.closeAll();
		}
		
	}

}
