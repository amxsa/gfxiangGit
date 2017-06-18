package cn.cellcom.czt.bus;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.file.excel.ExcelTemplate;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TDevice;

import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.spring.ApplicationContextTool;

public class DeviceBus {
	private static final Log log = LogFactory.getLog(DeviceBus.class);

	public Data addBatchDevice(TManager manager, File file) {
		Data data = new Data(false, "上传设备失败");
		if (file != null && file.exists()) {
			ExcelTemplate excel = new ExcelTemplate();
			System.out.println(">>>>>>>" + file.getPath());

			excel.readTemplateByFile(file);
			List<Map<String, Object>> list = excel.readRow(0, 1, new String[] {
					"sn" });
			log.info("读取" + file.getPath() + "设备条数:" + list.size() + "条");
			if (list != null && list.size() > 0) {
				Map<String, Object> map = null;
				int count = 0;
				int success = 0;
				int fail = 0;
				StringBuffer successStr = new StringBuffer();
				StringBuffer failStr = new StringBuffer();
				String[] addDeviceResult = null;
				for (int i = 0, len = list.size(); i < len; i++) {
					map = list.get(i);
					if(map.get("sn")==null)
						continue;
					addDeviceResult = addDevice(manager.getAccount(), map);
					if ("true".equals(addDeviceResult[0])) {
						success++;
						successStr.append(map.get("sn")).append(",");
						if (i > 0 && i % 5 == 0)
							successStr.append("<br>");
					} else {
						fail++;
						failStr.append(map.get("sn")).append(" [")
								.append(addDeviceResult[1]).append("],");
						failStr.append("<br>");
					}

				}
				StringBuffer result = new StringBuffer();
				result.append("成功条数：").append(success).append("条，失败条数：")
						.append(fail).append("条");
				result.append("<br />成功：<br />");
				result.append(successStr);
				result.append("<br />失败：<br />");
				result.append(failStr);
				data.setState(true);
				data.setMsg(result.toString());
				return data;
			}
		} else {
			data.setMsg("文件未找到");
		}
		return data;
	}

	public String[] addDevice(String account, Map<String, Object> map) {
		String sn = String.valueOf(map.get("sn"));
		if (StringUtils.isBlank(sn)) {
			return new String[] { "false", "SN不能为空" };
		}
		String checkSn = PatternTool.checkStr(sn, "^[a-zA-Z][a-zA-Z0-9]{19}$", "SN格式错误");
		if (checkSn != null)
			return new String[] { "false", checkSn };
//		if(map.get("spcode")==null){
//			return new String[] { "false", sn+"的spcode不能为空" };
//		}
//		String spcode = String.valueOf(map.get("spcode"));
//		if (StringUtils.isNotBlank(spcode)) {
//			if (!Env.SPCODE.containsKey(spcode.toUpperCase())) {
//				return new String[] { "false", "spcode值错误" };
//			}
//		} else {
//			return new String[] { "false", "spcode不能为空" };
//		}
//		if(map.get("configure")==null){
//			return new String[] { "false", sn+"的configure不能为空" };
//		}
//		String configure = String.valueOf(map.get("configure"));
//		if (StringUtils.isNotBlank(configure)) {
//			if (!Env.ORDER_CONFIGURE.containsKey(configure.toUpperCase())) {
//				return new String[] { "false", "configure值错误" };
//			}
//		} else {
//			return new String[] { "false", "configure不能为空" };
//		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			TDevice po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select sn from t_device where sn = ? ", TDevice.class,
					new String[] { sn });
			if (po == null) {
				Date date = new Date();
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_device(sn,account,spcode,configure,state,submit_time,submit_time2) values(?,?,?,?,?,?,?)",
								new Object[] { sn, account, StringUtils.substring(sn, 0, 3).toUpperCase(), StringUtils.substring(sn, 3, 4).toUpperCase(),
										"I",date,date.getTime()  });
				if (count > 0)
					return new String[] { "true", "" };
				else
					return new String[] { "false", "数据操作错误" };
			} else {
				return new String[] { "false", "已经存在" };
			}
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
		return new String[] { "false", "数据操作错误" };
	}

	public TDevice queryDeviceBySN(JDBC jdbc, String SN) {
		if (StringUtils.isBlank(SN)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TDevice po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_device where sn =  ? ", TDevice.class,
					new String[] { SN });
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
}
