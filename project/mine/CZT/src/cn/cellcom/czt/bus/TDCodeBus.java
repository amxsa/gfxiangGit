package cn.cellcom.czt.bus;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.DB.JdbcResultSet;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.common.file.excel.ExcelTemplate;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.DataPo;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.SpTdcode;
import cn.cellcom.czt.po.TBarCode;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TSyncTDCode;
import cn.cellcom.czt.po.TSyncTDCodeOBD;
import cn.cellcom.czt.po.TTdcodeGroup;
import cn.cellcom.czt.wechat.HttpUtils;
import cn.cellcom.web.spring.ApplicationContextTool;

public class TDCodeBus {
	private static final Log log = LogFactory.getLog(TDCodeBus.class);
	private static final int threadSize = 100;
	public int addTDCode(SpTdcode po) {
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			return jdbc
					.update(ApplicationContextTool.getDataSource(),
							"insert into sp_tdcode(tdcode,tdcodemd5,orderid,status,createtime,submittime,group_id) values(?,?,?,?,?,?,?)",
							new Object[] { po.getTdcode(), po.getTdcodemd5(),
									null, 0, System.currentTimeMillis(),
									new Date(), po.getGroupId() });
		} catch (SQLException e) {
			log.error("TDCode生成失败", e);
			e.printStackTrace();
		}
		return 0;

	}

	public JSONObject addBatchTDCode(String spcode, String version,
			String startNum, String endNum, String date, String groupId) {
		int start = Integer.parseInt(startNum);
		int end = Integer.parseInt(endNum);
		JSONObject json = new JSONObject();
		if (start < end) {
			Set<String> codeList = new HashSet<String>();
			for (int i = start; i <= end; i++) {
				if (i > 0 && i < 10) {
					codeList.add(spcode + version + "00000" + i + date);
				} else if (i >= 10 && i < 100) {
					codeList.add(spcode + version + "0000" + i + date);
				} else if (i >= 100 && i < 1000) {
					codeList.add(spcode + version + "000" + i + date);
				} else if (i >= 1000 && i < 10000) {
					codeList.add(spcode + version + "00" + i + date);
				} else if (i >= 10000 && i < 100000) {
					codeList.add(spcode + version + "0" + i + date);
				} else {
					codeList.add(spcode + version + date);
				}
			}
			if (codeList.size() > 0) {
				Set<String> inputTdcode = new HashSet<String>();
				Set<String> successTdcode = new HashSet<String>();
				Set<String> errorTdcode = new HashSet<String>();
				JDBC jdbc = ApplicationContextTool.getJdbc();
				Iterator<String> iter = codeList.iterator();
				SpTdcode po = null;
				try {
					while (iter.hasNext()) {
						String tdcode = iter.next();
						po = jdbc.queryForObject(
								ApplicationContextTool.getDataSource(),
								"select ID from sp_tdcode where tdcode = ? ",
								SpTdcode.class, new String[] { tdcode });
						if (po != null) {
							errorTdcode.add(tdcode + "已存在");
						} else {
							inputTdcode.add(tdcode);
						}
					}
					if (inputTdcode.size() > 0) {
						Iterator<String> iter1 = inputTdcode.iterator();
						po = null;
						while (iter1.hasNext()) {
							String tdcode = iter1.next();
							po = new SpTdcode();
							po.setTdcode(tdcode);
							// tdcodemd5规则 厂家+版本+ （ 编号+年月 的MD5取8-24位）
							String tdcodemd5 = StringUtils.substring(MD5
									.getMD5(StringUtils.substring(tdcode, 4)
											.getBytes()), 8, 24);
							po.setTdcodemd5(spcode + version + tdcodemd5);
							po.setGroupId(new Integer(groupId));
							Integer cnt = jdbc
									.queryForObject(
											ApplicationContextTool
													.getDataSource(),
											"select count(1) from sp_tdcode where right(tdcodemd5,8) = ?   ",
											Integer.class,
											new String[] { StringUtils.right(
													po.getTdcodemd5(), 8) });
							if (cnt == null || cnt == 0) {
								int count = addTDCode(po);
								if (count > 0) {
									successTdcode.add(tdcode);
								} else {
									errorTdcode.add(tdcode);
								}
							} else {
								errorTdcode.add(tdcode + "生成的SN["
										+ po.getTdcodemd5() + "]后八位已存在");
							}

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				json.put("success", successTdcode);
				json.put("error", errorTdcode);

			} else {
				json.put("failed", "流水号错误");
			}
		} else {
			json.put("failed", "流水号范围错误");

		}
		return json;
	}

	public Data sendTDCode(Map<String, String> params) {
		Data data = new Data(false, "发放失败");
		String tdcodemd5 = params.get("tdcodemd5");
		if (StringUtils.isNotBlank(tdcodemd5)) {
			JDBC jdbc = ApplicationContextTool.getJdbc();
			try {
				SpTdcode po = queryTDCodeByMD5(jdbc, tdcodemd5);
				if (po != null) {
					StringBuffer sql = new StringBuffer();
					sql.append("update sp_tdcode set status= 1  where tdcodemd5 = ? ");
					int count = jdbc.update(
							ApplicationContextTool.getDataSource(),
							sql.toString(), new Object[] { tdcodemd5 });
					// 数据割接
					StringBuffer dbSql = new StringBuffer();
					dbSql.append(
							"update sp_tdcode set status= 1  where tdcodemd5 ='")
							.append(tdcodemd5).append("'");
					LoginBus loginBus = new LoginBus();
					params.put("sql", dbSql.toString());
					loginBus.transfer(params);

					if (count > 0) {
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"insert into t_tdcode_operate_history(mobile,spcode,tdcode,tdcodemd5,operate_type,operate_describe,from_part,operate_time) values(?,?,?,?,?,?,?,?)",
								new Object[] {
										po.getMobilenum(),
										StringUtils.substring(po.getTdcode(),
												0, 4), po.getTdcode(),
										po.getTdcodemd5(), "S",
										"发放设备" + po.getTdcodemd5(), "web",
										new Date() });
						data.setState(true);
						data.setMsg(po.getTdcodemd5() + "发放成功");
					} else {
						data.setMsg(po.getTdcodemd5() + "已发放");
					}
				} else {
					data.setMsg(tdcodemd5 + "未找到");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return data;
			}
		} else {
			data.setMsg("设备号未找到");
		}
		return data;
	}

	public Data addBatchBarCodeBindTDCode(TManager manager, File file) {
		Data data = new Data(false, "SN与条码捆绑失败");
		if (file != null && file.exists()) {
			ExcelTemplate excel = new ExcelTemplate();
			excel.readTemplateByFile(file);
			List<Map<String, Object>> list = excel.readRow(0, 1, new String[] {
					"sn", "barcode" });
			log.info("读取" + file.getPath() + "SN与条码捆绑条数:" + list.size() + "条");
			if (list != null && list.size() > 0) {
				Map<String, Object> map = null;
				int success = 0;
				int fail = 0;
				StringBuffer successStr = new StringBuffer();
				StringBuffer failStr = new StringBuffer();
				String[] addDeviceResult = null;
				for (int i = 0, len = list.size(); i < len; i++) {
					map = list.get(i);
					if (map.get("sn") == null)
						continue;
					addDeviceResult = addBarCodeBindTDCode(
							manager.getAccount(), map);
					if ("true".equals(addDeviceResult[0])) {
						success++;
						successStr.append(map.get("sn")).append(",");
						if (success > 0 && success % 5 == 0)
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

	public String[] addBarCodeBindTDCode(String account, Map<String, Object> map) {
		String sn = String.valueOf(map.get("sn"));
		if (map.get("barcode") == null) {
			return new String[] { "false", sn + "的barcode不能为空" };
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {

			String barcode = StringUtils.trimToNull(String.valueOf(map.get("barcode")));
			if(barcode==null){
				return new String[] { "false", sn + "的barcode不能为空" };
			}
			if (!Env.SPCODE.containsKey(StringUtils.left(sn, 3).toUpperCase())) {
				return new String[] { "false", sn + "非设备二维码格式" };
			}
			// 成为条码格式00000008002AF50
			if ("O10".equals(StringUtils.left(sn, 3).toUpperCase())) {
				String barcodeResult = PatternTool.checkStr(barcode,
						"^[0-9A-Z]{15}$", sn + "的条码" + barcode + "格式错误");
				if (barcodeResult != null) {
					return new String[] { "false", barcodeResult };
				}
			}

			if ("O11".equals(StringUtils.left(sn, 3))) {
				String barcodeResult = PatternTool.checkStr(barcode,
						"\\d{12}$", sn + "的条码" + barcode + "格式错误");
				if (barcodeResult != null) {
					return new String[] { "false", barcodeResult };
				}
			}
			
			if ("O12".equals(StringUtils.left(sn, 3))) {
				String barcodeResult = PatternTool.checkStr(barcode,
						"\\d{12}$", sn + "的条码" + barcode + "格式错误");
				if (barcodeResult != null) {
					return new String[] { "false", barcodeResult };
				}
			}

			SpTdcode tdcodePo = queryTDCodeByMD5(jdbc, sn);
			if (tdcodePo != null) {
				if (StringUtils.isBlank(tdcodePo.getBarcode())) {
					PnBus bus  = new PnBus();
					SpTdcode po = bus.queryTdCodeByPn(barcode);
					if(po!=null){
						return new String[] {
								"false",
								po.getTdcodemd5() + "已经存在" + barcode + "，和目前导入的"
										+ barcode + "相同" };
					}
					int count = jdbc
							.update(ApplicationContextTool.getDataSource(),
									"update sp_tdcode set barcode= ? where tdcodemd5 = ? ",
									new Object[] { barcode, sn });
					return new String[] { "true",
							sn + "捆绑 " + barcode + "成功" };
				} else {
					if (barcode.equals(tdcodePo.getBarcode())) {
						return new String[] { "false",
								sn + "已经存在" + barcode + "" };
					} else {
						return new String[] {
								"false",
								sn + "已经存在" + tdcodePo.getBarcode() + "，和目前导入的"
										+ barcode + "冲突" };
					}
				}
			} else {
				return new String[] { "false", sn + "未找到 " };
			}
		} catch (Exception e) {
			log.error("", e);
			return new String[] { "false", sn + "数据查询异常 " };
		}

	}

	public SpTdcode queryTDCodeByMD5(JDBC jdbc, String tdcodemd5) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		SpTdcode po = null;
		try {
			po = jdbc.queryForObject(ApplicationContextTool.getDataSource(),
					"select * from sp_tdcode where tdcodemd5 = ? ",
					SpTdcode.class, new String[] { tdcodemd5 });
			if (po != null) {
				return po;
			}
		} catch (Exception e) {
			throw new RuntimeException("sp_tdcode查询异常", e);
		}

		return null;
	}

	public TBarCode queryBarCodeByTDCodemd5(JDBC jdbc, String tdcodemd5)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		TBarCode po = jdbc.queryForObject(
				ApplicationContextTool.getDataSource(),
				"select * from t_bar_code where tdcodemd5 = ? ",
				TBarCode.class, new String[] { tdcodemd5 });
		if (po != null) {
			return po;
		}
		return null;
	}

	public DataPo queryMobile(Map<String, String> params, DataPo data) {
		String SN = params.get("SN");
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		if (StringUtils.isBlank(SN) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(authstring)) {
			data.setCode(-1002);
			return data;
		}
		String temp = MD5.getMD5((SN + Env.SERVICE_SP + timestamp).getBytes());
		if (temp == null
				|| !authstring.toUpperCase().equals(temp.toUpperCase())) {
			data.setCode(-1003);
			return data;
		}

		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			SpTdcode po = queryTDCodeByMD5(jdbc, SN);
			if (po != null) {
				data.setCode(0);
				JSONArray array = new JSONArray();
				JSONObject obj = new JSONObject();
				obj.put("SN", po.getTdcodemd5());
				obj.put("mobile", String.valueOf(po.getMobilenum()));

				array.add(obj);
				data.setParambuf(array);
			} else {
				data.setCode(1);
			}
			data.setState("S");
		} catch (Exception e) {
			throw new RuntimeException("查询手机号码异常", e);
		}
		return data;
	}

	public DataPo querySNByMobile(Map<String, String> params, DataPo data) {
		String mobileNum = params.get("mobileNum");
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		if (StringUtils.isBlank(mobileNum) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(authstring)) {
			data.setCode(-3002);
			return data;
		}
		String temp = MD5.getMD5((mobileNum + Env.SERVICE_SP + timestamp)
				.getBytes());
		if (temp == null
				|| !authstring.toUpperCase().equals(temp.toUpperCase())) {
			data.setCode(-3003);
			return data;
		}

		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			SpTdcode po = jdbc
					.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select tdcodemd5,fluxcard,mobilenum from sp_tdcode where mobilenum = ? ",
							SpTdcode.class, new String[] { mobileNum });
			if (po != null) {
				data.setCode(0);
				JSONArray array = new JSONArray();
				JSONObject obj = new JSONObject();
				obj.put("SN", po.getTdcodemd5());
				obj.put("mobileNum", String.valueOf(po.getMobilenum()));
				obj.put("fluxcard", po.getFluxcard());
				array.add(obj);
				data.setParambuf(array);
			} else {
				data.setCode(1);
			}
			data.setState("S");
		} catch (Exception e) {
			throw new RuntimeException("查询设备流量卡异常", e);
		}
		return data;
	}

	public Data modifyTDCodeGroup(Map<String, String> params) {
		Data data = new Data(false, "组操作失败");

		String name = params.get("name");
		String operateType = params.get("operateType");
		String checkResult = checkTDCodeGroupParam(name, params.get("number"),
				operateType);
		if (checkResult != null) {
			data.setMsg(checkResult);
			return data;
		}

		// 组编号以10000开始，10000代表普通用户(默认数据库有数据)，10000以后的代表行业版厂家
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			
			if ("A".equals(operateType)) {
				Integer count = jdbc.queryForObject(
						ApplicationContextTool.getDataSource(),
						"select count(*) from t_tdcode_group where name = ? ",
						Integer.class, new String[] { name });
				if (count > 0) {
					data.setMsg("存在组[" + name + "],增加失败");
					return data;
				}
				Integer maxNumber = queryMaxGroupNumber(jdbc);
				if (maxNumber == 0)
					maxNumber = 10001;
				// 先用亿讯接口将组信息同步给对方，返回成功，在写入本地库
				String result = syncTDCodeGroup(null, maxNumber + 1, name,
						"1");
				if (result != null) {
					JSONObject jsonObj = JSONObject.fromObject(result);
					if (jsonObj != null && jsonObj.getInt("code") == 0) {
						if (jsonObj.getJSONObject("data").get("state") != null
								&& jsonObj.getJSONObject("data")
										.getInt("state") == 0) {
							count = jdbc
									.update(ApplicationContextTool
											.getDataSource(),
											"insert into t_tdcode_group(number,name,submit_time) values(?,?,?) ",
											new Object[] { maxNumber + 1, name,
													DateTool.getTimestamp(null) });
							data.setState(true);
							data.setMsg("增加成功");
							return data;
						}
					}
				}
			}
			if ("U".equals(operateType)) {
				Integer number = Integer.parseInt(params.get("number"));
				TTdcodeGroup po = queryGroupByNumber(jdbc, number);
				if (po == null) {
					data.setMsg("组ID未找到,修改失败");
					return data;
				}
				if (po.getName().equals(name)) {
					data.setState(true);
					data.setMsg("修改成功");
					return data;
				}
				if(number==10000){
					int count = jdbc
							.update(ApplicationContextTool
									.getDataSource(),
									"update t_tdcode_group set name = ? where number = ?  ",
									new Object[] { name, number });
					data.setState(true);
					data.setMsg("修改成功");
					return data;
				}
				// 先用亿讯接口将组信息同步给对方，返回成功，在写入本地库
				String result = syncTDCodeGroup(null, number, name, "2");
				if (result != null) {
					JSONObject jsonObj = JSONObject.fromObject(result);
					if (jsonObj != null && jsonObj.getInt("code") == 0) {
						if (jsonObj.getJSONObject("data").get("state") != null
								&& jsonObj.getJSONObject("data")
										.getInt("state") == 0) {
							int count = jdbc
									.update(ApplicationContextTool
											.getDataSource(),
											"update t_tdcode_group set name = ? where number = ?  ",
											new Object[] { name, number });
							data.setState(true);
							data.setMsg("修改成功");
							return data;
						}
					}
				}
			}
			if ("D".equals(operateType)) {
				Integer number = Integer.parseInt(params.get("number"));
				TTdcodeGroup po = queryGroupByNumber(jdbc, number);
				if (po == null) {
					data.setMsg("组ID未找到,删除失败");
					return data;
				}
				int count = jdbc.queryForObject(
						ApplicationContextTool.getDataSource(),
						"select count(*) from sp_tdcode where group_id = ? ",
						Integer.class, new Object[] { number });
				if (count > 0) {
					data.setMsg("该组中已存在设备，如需删除，请联系管理人员");
					return data;
				}
				
				// 先掉用亿讯接口将组信息同步给对方，返回成功，在写入本地库
				String result = syncTDCodeGroup(null, number, name, operateType);
				if (result != null) {
					JSONObject jsonObj = JSONObject.fromObject(result);
					if (jsonObj != null && jsonObj.getInt("code") == 0) {
						if (jsonObj.getJSONObject("data").get("state") != null
								&& jsonObj.getJSONObject("data")
										.getInt("state") == 0) {
							count = jdbc
									.update(ApplicationContextTool
											.getDataSource(),
											"delete from t_tdcode_group where number = ?  ",
											new Object[] { number });
							data.setState(true);
							data.setMsg("删除成功");
							return data;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}
		return data;
	}
	
	public List<TTdcodeGroup> queryGroup(JDBC jdbc){
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			List<TTdcodeGroup> groupList = jdbc.query(ApplicationContextTool.getDataSource(), "select number,name from t_tdcode_group ",TTdcodeGroup.class , null, 0, 0);
			if(groupList!=null&&groupList.size()>0)
				return groupList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		} 
		return null;
	}
	

	public TTdcodeGroup queryGroupByNumber(JDBC jdbc, Integer number)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		TTdcodeGroup po = jdbc.queryForObject(
				ApplicationContextTool.getDataSource(),
				"select * from t_tdcode_group  where number = ? ",
				TTdcodeGroup.class, new Object[] { number });
		if (po != null)
			return po;
		return null;
	}

	public Integer queryMaxGroupNumber(JDBC jdbc)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		Integer max = jdbc.queryForObject(
				ApplicationContextTool.getDataSource(),
				"select max(number)  from t_tdcode_group  ", Integer.class,
				null);
		return max;
	}

	private String checkTDCodeGroupParam(String name, String number,
			String operateType) {

		if (!"A".equals(operateType) && !"U".equals(operateType)
				&& !"D".equals(operateType)) {
			return "操作类型错误";
		}
		if ("A".equals(operateType) || "U".equals(operateType)) {
			if (StringUtils.isBlank(name)) {
				return "组名不能为空";
			}
		}
		if ("D".equals(operateType) || "U".equals(operateType)) {
			if (StringUtils.isBlank(number)) {
				return "组编号不能为空";
			}
			
		}
		return null;
	}

	public String syncTDCodeGroup(HttpUrlClient client, Integer number,
			String name, String operateType) {
		if (client == null) {
			client = new HttpUrlClient();
		}
		JSONObject params = new JSONObject();
		params.put("carUser", ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		Long time = System.currentTimeMillis();
		params.put("time", time);
		// MD5 32位(carUser+ time+password) password为系统分配的密码
		StringBuffer str = new StringBuffer();
		str.append(ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		str.append(time).append(ConfLoad.getProperty("YZ_TOKENID_PASSWORD"));
		String sign = MD5.getMD5(str.toString().getBytes());
		params.put("sign", sign);
		params.put("content", number + "^" + name);
		params.put("operate", operateType);
		String result = client.post("同步设备组给亿讯", ConfLoad.getProperty("OBD_HYB_URL")
				+ "api/obd/pushGroup?", params, 20000, true);
		// 返回格式 {"code":0,"desc":"xxxxx","data":{"state":6}}
		log.info("调用同步设备组返回结果：" + result);
		if (result != null)
			return result;
		return null;
	}

	public String syncTDCode(String content) {
		HttpUrlClient client = new HttpUrlClient();

		JSONObject params = new JSONObject();
		params.put("carUser", ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		Long time = System.currentTimeMillis();
		params.put("time", time);
		// MD5 32位(carUser+ time+password) password为系统分配的密码
		StringBuffer str = new StringBuffer();
		str.append(ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		str.append(time).append(ConfLoad.getProperty("YZ_TOKENID_PASSWORD"));
		String sign = MD5.getMD5(str.toString().getBytes());
		params.put("sign", sign);

		params.put("content", content);
		
		String result = client.post("同步设备流量给亿讯", ConfLoad.getProperty("OBD_HYB_URL")
				+ "api/obd/batchPushRelation?", params, 20000, true);
		// 返回格式
		// {"code":0,"desc":"操作成功","data":[{"id":"7","hgDeviceSn":"O10L149be2ddf4eb7d4a","status":"0"},{"id":"8","hgDeviceSn":"O10L9184a0d8dcfa3fe6","status":"0"}]}
		log.info("调用同步设备流量给亿讯返回结果：" + result);
		if (result != null) {
			JSONObject jsonObj = JSONObject.fromObject(result);
			if (jsonObj != null ) {
				JSONArray array = jsonObj.getJSONArray("data");
				if (array != null && array.size() > 0) {
					log.info("同步设备需要处理的数据:" + array.toString());
					JSONObject obj = null;
					StringBuffer success = new StringBuffer();
					StringBuffer fail = new StringBuffer();
					StringBuffer successSql = new StringBuffer(
							"delete from t_sync_tdcode where id in (");
					StringBuffer failSql = new StringBuffer(
							"update t_sync_tdcode set state='F' where id in (");
					int successCount =0;
					int failCount = 0;
					for (int i = 0; i < array.size(); i++) {
						obj = array.getJSONObject(i);
						if ("0".equals(obj.getString("status"))) {
							success.append(obj.getString("hgDeviceSn")).append(
									",");
							successSql.append(obj.getString("id")).append(",");
							++successCount;
						} else {
							fail.append(obj.getString("hgDeviceSn"))
									.append(",");
							failSql.append(obj.getString("id")).append(",");
							++failCount;
						}
						
					}
					if(successCount>0)
						successSql.deleteCharAt(successSql.length()-1);
					if(failCount>0)
						failSql.deleteCharAt(failSql.length()-1);
					successSql.append(")");
					failSql.append(")");
					try {
						if (success.length() > 0) {
							log.info("成功的SN:" + success.toString() + ",执行的SQL:"
									+ successSql.toString());
							JDBC jdbc = (JDBC) ApplicationContextTool
									.getBean("jdbc");
							jdbc.update(ApplicationContextTool.getDataSource(),
									successSql.toString(), null);
						}

						if (fail.length() > 0) {
							log.info("失败的SN:" + fail.toString() + ",执行的SQL:"
									+ failSql.toString());
							JDBC jdbc = (JDBC) ApplicationContextTool
									.getBean("jdbc");
							jdbc.update(ApplicationContextTool.getDataSource(),
									failSql.toString(), null);
						}
					} catch (Exception e) {
						
						e.printStackTrace();
						log.error("同步设备数据SQL异常", e);
					}

				}
			} else {
				log.error("调用同步设备流量给亿讯返回异常:" + result);
			}
		}

		return null;
	}

	public void syncTDCodeTask() {
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			List<TSyncTDCode> list = querySyncTDCode(jdbc);
			if (list != null&&list.size()>0) {
				log.info("开始同步设备给OBD。。。。");
				long a = System.currentTimeMillis();
				int size = list.size();
				int threadCount = 1;
				if (size > threadSize) {
					if (size % threadSize == 0)
						threadCount = size / threadSize;
					else
						threadCount = size / threadSize + 1;
				}
				List<TSyncTDCode> single = null;
				for (int k = 0; k < threadCount; k++) {
					single = list.subList(threadSize * k, (k + 1)
							* threadSize > size ? size : (k + 1) * threadSize);
					StringBuffer tdcodeStr = new StringBuffer();
					TSyncTDCode po = null;
					for (int i = 0, len = single.size(); i < len; i++) {
						po = single.get(i);
						// 我传入的ID1^手机号^设备编号1^二维码1^流量卡号1^所属分组1^到期时间1^操作类型 ，多个以|隔开
						tdcodeStr.append(po.getId()).append("^");
						tdcodeStr.append(po.getMobilenum()==null?"":po.getMobilenum()).append("^");
						tdcodeStr.append(po.getBarcode()==null?"":po.getBarcode()).append("^")
								.append(po.getTdcodemd5());
						tdcodeStr.append("^").append(po.getFluxcard()==null?"":po.getFluxcard())
								.append("^").append(po.getGroupId()==null?"":po.getGroupId());
						tdcodeStr.append("^").append(po.getLimiteTime()==null?"":
								DateTool.formateTime2String(po.getLimiteTime(),
										"yyyy-MM-dd HH:mm:ss"));
						if("A".equals(po.getOperateType())){
							tdcodeStr.append("^").append("1");
						}else if("U".equals(po.getOperateType())){
							tdcodeStr.append("^").append("2");
						}else if("D".equals(po.getOperateType())){
							tdcodeStr.append("^").append("3");
						}else if("R".equals(po.getOperateType())){
							tdcodeStr.append("^").append("4");
						}
						tdcodeStr.append("|");
					}
					if(tdcodeStr.length()>0){
						tdcodeStr.deleteCharAt(tdcodeStr.length()-1);
						syncTDCode(tdcodeStr.toString());
					}
					
				}
				
				log.info("本次同步设备给OBD耗时:" + (System.currentTimeMillis() - a)
						+ "ms");
			} else {
				log.info("无设备同步给OBD");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据组id获取组id名称 gfxiang
	 * @param jdbc
	 * @param groupId
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SQLException
	 */
	public String queryGroupById(JDBC jdbc,String groupId)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		
		String[] ids = jdbc.query(ApplicationContextTool.getDataSource(), "SELECT NAME FROM t_tdcode_group WHERE number="+groupId);
		
		JdbcResultSet query = jdbc.query(ApplicationContextTool.getConnection(), "SELECT NAME FROM t_tdcode_group WHERE number=?", new String[]{groupId});
		List<Object[]> datas = query.getDatas();
		String id = (String) datas.get(0)[0];
		if (ids!=null) {
			return id;
		}
		return null;
	}

	public List<TSyncTDCode> querySyncTDCode(JDBC jdbc)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		List<TSyncTDCode> list = jdbc
				.query(ApplicationContextTool.getDataSource(),
						"select * from t_sync_tdcode where state = 'N' order by submit_time asc ",
						TSyncTDCode.class, null, 0, 99);
		if (list != null && list.size() > 0)
			return list;
		return null;
	}
	
	
	/**
	 * 查询每次需同步给obd的中间表数据  gfxiang	
	 * @param jdbc
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SQLException
	 */
	public List<TSyncTDCodeOBD> querySyncTDCodeObd(JDBC jdbc)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		List<TSyncTDCodeOBD> list = jdbc
				.query(ApplicationContextTool.getDataSource(),
						"select ID,tdcode,tdcodemd5,status,orderid,mobilenum,mobileid,fluxcard,limite_time,idcard_state,tdverify,obdactive,group_id,activetime,submittime from t_sync_tdcode_obd where flag = 'N' order by submittime asc limit 0,30 ",
						TSyncTDCodeOBD.class, null, 0, 0);
		if (list != null && list.size() > 0)
			return list;
		return null;
	}

	public void addSycnTDCode(JDBC jdbc, TSyncTDCode po) {
		if (po != null) {
			if (po.getTdcodemd5() != null && po.getGroupId() != null
					&& po.getFluxcard() != null) {
				if (jdbc == null) {
					jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
				}
				try {

					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"insert into t_sync_tdcode(tdcodemd5,fluxcard,barcode,mobilenum,group_id,limite_time,operate_type,state,submit_time) values(?,?,?,?,?,?,?,?,?)",
							new Object[] { po.getTdcodemd5(), po.getFluxcard(),
									po.getBarcode(), po.getMobilenum(),
									po.getGroupId(), po.getLimiteTime(),
									po.getOperateType(), po.getState(),
									po.getSubmitTime() });
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("t_sync_tdcode写入异常", e);
				}
			} else {
				log.info("TSyncTDCode的数据不完整，无法写入");
				PrintTool.printBean("写入t_sync_tdcode", po, true);
			}
		}
	}
	
	/**
	 * 查询同步的行业版数据
	 * @param map
	 * @return
	 */
	public DataPo querySyncHyb(Map<String, String> map){
		DataPo data = new DataPo();
		data.setState("F");
		data.setMsg("查询失败");
		String groupId= map.get("groupId");
		String tdcodemd5= map.get("tdcodemd5");
		HttpUrlClient client = new HttpUrlClient();

		JSONObject params = new JSONObject();
		params.put("carUser", ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		Long time = System.currentTimeMillis();
		params.put("time", time);
		// MD5 32位(carUser+ time+password) password为系统分配的密码
		StringBuffer str = new StringBuffer();
		str.append(ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		str.append(time).append(ConfLoad.getProperty("YZ_TOKENID_PASSWORD"));
		String sign = MD5.getMD5(str.toString().getBytes());
		params.put("sign", sign);
		params.put("copId", groupId);
		params.put("hgDeviceSn", tdcodemd5);
		params.put("page", map.get("currentPage"));
		params.put("pageSize", map.get("pageSize"));
		String result = client.post("查询同步设备流量", ConfLoad.getProperty("OBD_HYB_URL")
				+ "api/obd/queryCopDevice?", params, 20000, true);
		/* 返回格式
		* 错误 {"code":-8,"desc":"参数错误： 参数不能为空","data":null}
		* 正确   {"code":0,"desc":"操作成功","data":{"nums":"0","infos":[]}}
		*/
		log.info("查询同步设备流量返回结果：" + result);
		if (result != null) {
			JSONObject jsonObj = JSONObject.fromObject(result);
			if (jsonObj != null && jsonObj.getInt("code") == 0) {
				JSONObject jsonData = jsonObj.getJSONObject("data");
				if(jsonData!=null){
					if(jsonData.containsKey("nums")){
						int nums = jsonData.getInt("nums");
						//用msg 存nums;
						data.setMsg(String.valueOf(nums));
					}
					if(jsonData.containsKey("infos")){
						data.setParambuf(jsonData.getJSONArray("infos"));
					}
					data.setState("S");
					return data;
				}
				
			}
		}
		return data;
	}
	
	public Data updateTDCodeGroup(TManager manager, Integer groupId,File file) {
		Data data = new Data(false, "修改设备组失败");
		if (file != null && file.exists()) {
			ExcelTemplate excel = new ExcelTemplate();
			excel.readTemplateByFile(file);
			List<Map<String, Object>> list = excel.readRow(0, 1, new String[] {
					"sn" });
			log.info("读取修改设备组" + file.getPath() + "SN条数:" + list.size() + "条");
			if (list != null && list.size() > 0) {
				Map<String, Object> map = null;
				int success = 0;
				int fail = 0;
				StringBuffer successStr = new StringBuffer();
				StringBuffer failStr = new StringBuffer();
				String[] addDeviceResult = null;
				for (int i = 0, len = list.size(); i < len; i++) {
					map = list.get(i);
					if (map.get("sn") == null)
						continue;
					addDeviceResult = updateTDCodeGroup(
							manager.getAccount(), groupId,map);
					if ("true".equals(addDeviceResult[0])) {
						success++;
						successStr.append(map.get("sn")).append(",");
						if (success > 0 && success % 5 == 0)
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
	
	public String[] updateTDCodeGroup(String account,Integer groupId, Map<String, Object> map) {
		String sn =StringUtils.trimToEmpty(String.valueOf(map.get("sn"))) ;
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			SpTdcode tdcodePo = queryTDCodeByMD5(jdbc, sn);
			if (tdcodePo != null) {
				if (groupId>10000&&StringUtils.isNotBlank(tdcodePo.getBarcode())&&StringUtils.isNotBlank(tdcodePo.getFluxcard())&&tdcodePo.getLimiteTime()!=null) {
					int count = jdbc
							.update(ApplicationContextTool.getDataSource(),
									"update sp_tdcode set group_id= ? where tdcodemd5 = ? ",
									new Object[] { groupId, sn });
//					TSyncTDCode sync = new TSyncTDCode();
//					sync.setBarcode(tdcodePo.getBarcode());
//					sync.setFluxcard(tdcodePo.getFluxcard());
//					sync.setGroupId(groupId);
//					sync.setLimiteTime(tdcodePo.getLimiteTime());
//					if (tdcodePo.getMobilenum() == null) {
//						sync.setMobilenum(null);
//					} else {
//						sync.setMobilenum(String.valueOf(tdcodePo.getMobilenum()));
//					}
//					sync.setOperateType("U");
//					sync.setTdcodemd5(tdcodePo.getTdcodemd5());
//					sync.setSubmitTime(DateTool.getTimestamp(null));
//					sync.setState("N");
//					addSycnTDCode(jdbc, sync);
					tdcodePo.setGroupId(groupId);
					LoginBus loginBus = new LoginBus();
					TDCodeBus tdcodeBus = new TDCodeBus();
					loginBus.syncTDCode(jdbc, tdcodePo, "A",
							tdcodeBus);
					return new String[] { "true", sn  };
				}else{
					return new String[] { "false", "设备缺少设备条码或流量卡或生命周期数据"  };
				}
				
			} else {
				return new String[] { "false", sn + "未找到 " };
			}
		} catch (Exception e) {
			log.error("", e);
			return new String[] { "false", sn + "数据查询异常 " };
		}

	}

	
	
	 public void syncTDCodeTaskHGObd() {
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		JSONObject jo=new JSONObject();
		Map<Integer,TTdcodeGroup> group_czt = Env.TDCODE_GROUP;
		try {
			List<TSyncTDCodeOBD> list = querySyncTDCodeObd(jdbc);
			String jsonStr="\"[";
			if (list != null&&list.size()>0) {
			
				for (int i = 0; i <list.size(); i++) {
					TSyncTDCodeOBD obd = list.get(i);
					String tdcode = obd.getTdcode();
					String tdcodemd5 = obd.getTdcodemd5();
					String barcode = obd.getBarcode();
					int status = obd.getStatus();
					String orderid = obd.getOrderid();
					String mobilenum = obd.getMobilenum();
					String mobileid = obd.getMobileid();
					String fluxcard = obd.getFluxcard();
					Timestamp limiteTime = obd.getLimiteTime();
					int idcardState = obd.getIdcardState();
					int tdverify = obd.getTdverify();
					int obdactive = obd.getObdactive();
					String groupid = group_czt.get(obd.getGroupId()).getName();
					Timestamp activetime = obd.getActivetime();
					Timestamp submittime = obd.getSubmittime();
					//String groupid = queryGroupById(jdbc,String.valueOf(groupId));
			
					jsonStr+="{\"i2bcode\":\""+tdcode+"\",\"enc2bcode\":\""+tdcodemd5+
							"\",\"bcodePN\":\""+barcode+"\",\"relstatus\":\""+status+"\",\"orderId\":\""+orderid+"\",\"cellphone\":\""+mobilenum+
							"\",\"encCPhone\":\""+mobileid+"\",\"smiCard\":\""+fluxcard+"\",\"effTime\":\""+limiteTime+
							"\",\"vnStatus\":\""+idcardState+"\",\"hisStatus\":\""+tdverify+"\",\"actStatus\":\""+obdactive+"\",\"group\":\""+groupid+
							"\",\"actTime\":\""+activetime+"\",\"updTime\":\""+submittime+"\"},";
				}
				
				jsonStr=jsonStr.substring(0, jsonStr.length()-1);
				jsonStr+="]\"";
				long a = System.currentTimeMillis();
				String tokenId = HttpUtils.loginObd();
				jo.put("tokenId", tokenId);
			
				if (StringUtils.isBlank(tokenId)) {
					log.info("请求华工OBD接口获取tokenid失败");
					return;
				}
		        jo.put("obdpdm", jsonStr);
				JSONObject jsonObject = HttpUtils.post("http://obd.chelulu.cn:8888/obd-web/api/obdpdm",jo);
				if (jsonObject!=null) {
					log.info("请求华工OBD接口返回结果:"+jsonObject.toString());
					if (jsonObject.get("resultfFag").toString().equals("0")) {
						int i = deleteTdcode(jdbc, list);
					}else{
						int k = updateTdcode(jdbc, list);
						if (k!=0) {
						}
					}
				}else{
					log.info("请求华工OBD接口无数据返回");
				}
			} else {
				log.info("请求华工OBD无数据");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("求华工OBD异常",e);
		}
	}
	
	public void syncTDCodeTaskHGObd_bak() {
		
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		Map<Integer,TTdcodeGroup> group_czt = Env.TDCODE_GROUP;
		
		JSONObject jo=new JSONObject();
		JSONObject pdm=new JSONObject();
		List<String> pdmList=new ArrayList<String>();
		
		try {
			List<TSyncTDCodeOBD> list = querySyncTDCodeObd(jdbc);
			if (list != null&&list.size()>0) {
			
				for (int i = 0; i <list.size(); i++) {
					TSyncTDCodeOBD obd = list.get(i);
					
					Timestamp limiteTime = obd.getLimiteTime();
					int groupId = obd.getGroupId();
					Timestamp activetime = obd.getActivetime();
					Timestamp submittime = obd.getSubmittime();
					String submittimeString = DateTool.formateTime2String(submittime, "yyyy-MM-dd HH:mm:ss");
					String activetimeString = DateTool.formateTime2String(activetime, "yyyy-MM-dd HH:mm:ss");
					String limiteTimeString = DateTool.formateTime2String(limiteTime, "yyyy-MM-dd HH:mm:ss");
					
					pdm.put("i2bcode", obd.getTdcode());
					pdm.put("enc2bcode", obd.getTdcodemd5());
					pdm.put("bcodePN", obd.getBarcode());
					pdm.put("relstatus", obd.getStatus());
					pdm.put("orderId", obd.getOrderid());
					pdm.put("cellphone", obd.getMobilenum());
					pdm.put("encCPhone", obd.getMobileid());
					pdm.put("smiCard", obd.getFluxcard());
					pdm.put("effTime", limiteTimeString);
					pdm.put("vnStatus", obd.getIdcardState());
					pdm.put("hisStatus", obd.getTdverify());
					pdm.put("actStatus", obd.getObdactive());
					pdm.put("group", group_czt.get(groupId).getName());
					pdm.put("actTime", activetimeString);
					pdm.put("updTime", submittimeString);
					
					pdmList.add(pdm.toString());
				}
				
				log.info("开始同步设备给OBD！！");
				long a = System.currentTimeMillis();
				/*String tokenId = HttpUtils.loginObd();*/
				jo.put("tokenId", "123");
			
				/*if (StringUtils.isBlank(tokenId)) {
					log.info("获取令牌失败！");
					return;
				}*/
				jo.put("obdpdm", pdmList.toString());
		        //进行数据同步
				JSONObject jsonObject = HttpUtils.postObd("http://obd.chelulu.cn:8080/obd-web/api/obdpdm",jo);
				if (jsonObject!=null) {
					if (jsonObject.get("resultfFag").toString().equals("0")) {//同步成功
//						int i = deleteTdcode(jdbc, list);
						log.info("同步数据已删除！");
					}else{
						int k = updateTdcode(jdbc, list);
						if (k!=0) {
						log.info("同步数据失败，已更新失败数据状态为F");
						}
					}
				}
				
				log.info("本次同步设备给OBD耗时:" + (System.currentTimeMillis() - a)+ "ms");
			} else {
				log.info("无设备同步给OBD");
			}
			//*/
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}


	private int deleteTdcode(JDBC jdbc, List<TSyncTDCodeOBD> list) throws Exception {
		// TODO Auto-generated method stub
		int j=0;
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		for (int i = 0; i < list.size(); i++) {
			j = jdbc.update(ApplicationContextTool.getDataSource(), "delete from t_sync_tdcode_obd  where tdcode=?",new String[]{list.get(i).getTdcode()});
		}
		
		return j;
	
	}

	private int updateTdcode(JDBC jdbc,List<TSyncTDCodeOBD> list) throws SQLException {
		// TODO Auto-generated method stub
		int j=0;
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		for (int i = 0; i < list.size(); i++) {
			String tdcode = list.get(i).getTdcode();
			System.out.println("tdcode"+tdcode);
			j = jdbc.update(ApplicationContextTool.getDataSource(), "update t_sync_tdcode_obd set flag='F' where tdcode=?",new String[]{tdcode});
		}
		
		return j;
	}
	/**
	 * 同步数据给obd
	 * @param jdbc
	 * @param ob
	 */
	public void addSycnTDCode2HGObd(JDBC jdbc,SpTdcode  po) {
		if (po != null) {
				if (jdbc == null) {
					jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
				}
				try {
					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"insert into t_sync_tdcode_obd(tdcode,tdcodemd5,barcode,status,orderid,mobilenum,mobileid,fluxcard,limite_time,idcard_state,tdverify,obdactive,group_id,activetime,submittime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
							new Object[] { po.getTdcode(), po.getTdcodemd5(),po.getBarcode(),po.getStatus(),po.getOrderid(),
									po.getMobilenum(),po.getMobileid(),po.getFluxcard(),po.getLimiteTime(),po.getIdcardState(),po.getTdverify(),po.getObdactive(),po.getGroupId(),po.getActivetime(),po.getSubmittime() });
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("t_sync_tdcode_obd写入异常", e);
				}
			} else {
				log.info("TSyncTDCodeOBD的数据不完整，无法写入");
				PrintTool.printBean("写入t_sync_tdcode_obd", po, true);
			}
		
	}
		
}
