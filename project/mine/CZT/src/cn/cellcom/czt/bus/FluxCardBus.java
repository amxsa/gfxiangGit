package cn.cellcom.czt.bus;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
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
import cn.cellcom.czt.po.TFluxCard;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.web.spring.ApplicationContextTool;

public class FluxCardBus {
	private static final Log log = LogFactory.getLog(FluxCardBus.class);

	public Data addBatchFluxCard(TManager manager, File file) {
		Data data = new Data(false, "上传流量卡失败");
		if (file != null && file.exists()) {
			ExcelTemplate excel = new ExcelTemplate();
			System.out.println(">>>>>>>" + file.getPath());

			excel.readTemplateByFile(file);
			List<Map<String, Object>> list = excel.readRow(0, 1, new String[] {
					"mobile", "areacode" });
			log.info("读取" + file.getPath() + "流量卡条数:" + list.size() + "条");
			if (list != null && list.size() > 0) {
				Map<String, Object> map = null;
				int success = 0;
				int fail = 0;
				StringBuffer successStr = new StringBuffer();
				StringBuffer failStr = new StringBuffer();
				String[] addFluxCardResult = null;
				for (int i = 0, len = list.size(); i < len; i++) {
					map = list.get(i);
					if (map.get("mobile") == null)
						continue;
					String mobile = String.valueOf(map.get("mobile"));
					addFluxCardResult = addFluxCard(manager.getAccount(),
							mobile,
							String.valueOf(map.get("areacode") == null ? ""
									: map.get("areacode")));
					if ("true".equals(addFluxCardResult[0])) {
						success++;
						successStr.append(mobile).append(",");
						if (i > 0 && i % 5 == 0)
							successStr.append("<br>");
					} else {
						fail++;
						failStr.append(mobile).append(" [")
								.append(addFluxCardResult[1]).append("],");
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

	public String[] addFluxCard(String account, String mobile, String areacode) {
		if (StringUtils.isBlank(mobile)) {
			return new String[] { "false", "流量卡不能为空" };
		}
		String mobileResult = PatternTool.checkStr(mobile, "^1\\d{10,19}$",
				"流量卡格式错误");
		if (mobileResult != null) {
			return new String[] { "false", mobileResult };
		}
		if (StringUtils.isBlank(areacode)) {
			return new String[] { "false", "areacode不能为空" };
		}
		if (!Env.AREACODE.containsKey(areacode)) {
			return new String[] { "false", "areacode值非广东省区号" };
		}

		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			TFluxCard po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select mobile from t_flux_card where mobile = ? ",
					TFluxCard.class, new String[] { mobile });
			if (po == null) {
				Date date = new Date();
				
				int count = jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_flux_card(mobile,account,state,areacode,submit_time,submit_time2) values(?,?,?,?,?,?)",
								new Object[] { mobile, account, "I", areacode,
										date,date.getTime()});
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

	public TFluxCard queryFluxCardByMobile(JDBC jdbc, String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TFluxCard po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_flux_card where mobile = ? ",
					TFluxCard.class, new String[] { mobile });
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
