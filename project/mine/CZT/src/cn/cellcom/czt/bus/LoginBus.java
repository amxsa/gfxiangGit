package cn.cellcom.czt.bus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.common.file.FileTool;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.DataMsg;
import cn.cellcom.czt.po.SpTdcode;
import cn.cellcom.czt.po.TBindDeviceFluxcard;
import cn.cellcom.czt.po.TFluxIdcard;
import cn.cellcom.czt.po.TManager;
import cn.cellcom.czt.po.TOrder;
import cn.cellcom.czt.po.TSyncTDCode;
import cn.cellcom.czt.po.TSyncTDCodeOBD;
import cn.cellcom.czt.po.TTdcodeOperateHistory;
import cn.cellcom.czt.webservice.client.CztServiceServiceStub;
import cn.cellcom.czt.webservice.client.CztServiceServiceStub.Off;
import cn.cellcom.czt.webservice.client.CztServiceServiceStub.OffResponse;
import cn.cellcom.czt.webservice.client.CztServiceServiceStub.RegisterResponse;
import cn.cellcom.web.spring.ApplicationContextTool;

public class LoginBus {
	private static final Log log = LogFactory.getLog(LoginBus.class);
	private static Map<String, String> FROM_PART = new HashMap<String, String>();
	static {
		FROM_PART.put("wlhyxh", "物流行业协会");
		FROM_PART.put("web", "管理后台");
	}

//	public DataMsg regist(Map<String, String> params) {
//		String tdCodemd5 = params.get("tdCode");
//		String mobileNum = params.get("mobileNum");
//		String fromPart = params.get("fromPart");
//		DataMsg msg = new DataMsg();
//		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
//		JSONObject obj = new JSONObject();
//		try {
//			SpTdcode po = jdbc.queryForObject(
//					ApplicationContextTool.getDataSource(),
//					"select * from sp_tdcode where tdcodemd5 = ? ",
//					SpTdcode.class, new String[] { tdCodemd5 });
//			if (po == null) {
//				setTdcodeError(obj, 0, "注册失败");
//				msg.setData(obj);
//				return msg;
//			}
//			// status是否启用新增的二维码(1:启用，启用后才可以激活，0:未启用)
//			if (po.getStatus() != 1) {
//				setTdcodeError(obj, 2, "该二维码未发布或被冻结");
//				msg.setData(obj);
//				return msg;
//			}
//			// 已激活标识为1，不再请求激活，返回错误码5(已激活)
//			if (po.getObdactive() == 1) {
//				setTdcodeError(obj, 5, "该二维码已被注册");
//				msg.setData(obj);
//				return msg;
//			}
//			// 需要条码才可以激活
//			if (StringUtils.isBlank(po.getBarcode())) {
//				setTdcodeError(obj, 6, "该二维码缺少设备条码");
//				msg.setData(obj);
//				return msg;
//			}
//			
//			
//			String mobilemd5Str = MD5.getMD5((mobileNum + Env.SERVICE_KEY)
//					.getBytes());
//			// 取第8-24位
//			mobilemd5Str = StringUtils.substring(mobilemd5Str, 8, 24);
//			String result = null;
//			if (StringUtils.left(tdCodemd5, 3).toUpperCase().equals("O10")) {
//				result = regist(tdCodemd5, mobileNum, mobilemd5Str,
//						po.getBarcode(), params.get("fromPart"));
//				if (StringUtils.isBlank(result)) {
//					setTdcodeError(obj, 0, "后台注册失败");
//					msg.setData(obj);
//					return msg;
//				}
//				JSONObject jsonObj = JSONObject.fromObject(result);
//				if (jsonObj == null) {
//					setTdcodeError(obj, 4, "后台注册失败");
//					msg.setData(obj);
//					return msg;
//				}
//				if (!jsonObj.containsKey("error")) {
//					setTdcodeError(obj, 4, "后台注册失败");
//					msg.setData(obj);
//					return msg;
//				}
//				String error = (String) jsonObj.get("error");
//				if (!"ERR00".equals(error)) {
//					setTdcodeError(obj, 4, "后台注册失败");
//					msg.setData(obj);
//					return msg;
//				}
//
//				Date date = new Date();
//				jdbc.update(
//						ApplicationContextTool.getDataSource(),
//						"update sp_tdcode set mobilenum=?,mobileid= ? ,obdactive = ?,tdverify= ? ,activetime = ?   where tdcodemd5= ? ",
//						new Object[] { mobileNum, mobilemd5Str, 1, 1, date,
//								po.getTdcodemd5() });
//				// 增加日志记录
//				TTdcodeOperateHistory history = new TTdcodeOperateHistory();
//				history.setTdcodemd5(po.getTdcodemd5());
//				history.setMobile(mobileNum);
//				history.setSpcode(StringUtils.substring(po.getTdcode(), 0, 4));
//				history.setTdcode(po.getTdcode());
//				history.setOperateType("R");
//				history.setOperateDescribe(mobileNum + "激活设备号");
//				history.setFromPart(fromPart);
//				addOperateHistory(jdbc, history);
//				obj.put("status", 1);
//				obj.put("description", "激活成功");
//				obj.put("uid", mobilemd5Str);
//				obj.put("passwd", Env.PASSWORD);
//				obj.put("spcode", "cw");
//				// 将数据同步给亿讯,写t_sync_tdcode
//				TDCodeBus bus = new TDCodeBus();
//				syncTDCode(jdbc, po, "U", bus);
//				// 将成为数据通知给亿讯
//				result = registYZ(params.get("fromPart"), po.getTdcodemd5(),
//						mobileNum, po.getBarcode(), null, mobilemd5Str,
//						Env.PASSWORD);
//				if (result != null) {
//					jsonObj = JSONObject.fromObject(result);
//					if (jsonObj.getInt("code") == 0) {
//
//					} else {
//						log.info(po.getTdcodemd5() + "[" + mobileNum
//								+ "]调用亿讯(激活)接口异常，需手工处理");
//					}
//				}
//			} else if (StringUtils.left(tdCodemd5, 3).toUpperCase()
//					.equals("O11")) {
//				result = registYZ(params.get("fromPart"), tdCodemd5, mobileNum,
//						po.getBarcode(), params.get("osType"), null, null);
//				if (StringUtils.isBlank(result)) {
//					setTdcodeError(obj, 4, "后台注册失败");
//					msg.setData(obj);
//					return msg;
//				}
//				JSONObject jsonObj = JSONObject.fromObject(result);
//				if (jsonObj.getInt("code") == 0) {
//					if (jsonObj.getJSONObject("data").get("state") != null
//							&& jsonObj.getJSONObject("data").getInt("state") == 0) {
//						Date date = new Date();
//						jdbc.update(
//								ApplicationContextTool.getDataSource(),
//								"update sp_tdcode set mobilenum=?,mobileid= ? ,obdactive = ?,tdverify= ? ,activetime = ?   where tdcodemd5= ? ",
//								new Object[] { mobileNum, po.getBarcode(), 1,
//										1, date, po.getTdcodemd5() });
//						// 增加日志记录
//						TTdcodeOperateHistory history = new TTdcodeOperateHistory();
//						history.setTdcodemd5(po.getTdcodemd5());
//						history.setMobile(mobileNum);
//						history.setSpcode(StringUtils.substring(po.getTdcode(),
//								0, 4));
//						history.setTdcode(po.getTdcode());
//						history.setOperateType("R");
//						history.setOperateDescribe(mobileNum + "激活设备号");
//						history.setFromPart(fromPart);
//						addOperateHistory(jdbc, history);
//						// 将数据同步给亿讯,写t_sync_tdcode
//						TDCodeBus bus = new TDCodeBus();
//						syncTDCode(jdbc, po, "U", bus);
//
//						obj.put("status", 1);
//						obj.put("description", "激活成功");
//						obj.put("uid", po.getBarcode());
//						obj.put("passwd", Env.PASSWORD);
//						// 返回厂家
//						obj.put("spcode", "yz");
//					} else {
//						setTdcodeError(obj, 4, "后台注册失败");
//					}
//				} else {
//					setTdcodeError(obj, 4, "后台注册失败");
//				}
//			}
//		} catch (Exception e) {
//			log.error("查询sp_tdcode异常", e);
//			setTdcodeError(obj, 3, "数据异常");
//		}
//		msg.setData(obj);
//		return msg;
//
//	}
	
	
	public DataMsg regist(Map<String, String> params) {
		String tdCodemd5 = params.get("tdCode");
		String mobileNum = params.get("mobileNum");
		String fromPart = params.get("fromPart");
		DataMsg msg = new DataMsg();
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		JSONObject obj = new JSONObject();
		try {
			SpTdcode po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from sp_tdcode where tdcodemd5 = ? ",
					SpTdcode.class, new String[] { tdCodemd5 });
			if (po == null) {
				setTdcodeError(obj, 0, "注册失败");
				msg.setData(obj);
				return msg;
			}
			// status是否启用新增的二维码(1:启用，启用后才可以激活，0:未启用)
			if (po.getStatus() != 1) {
				setTdcodeError(obj, 2, "该二维码未发放");
				msg.setData(obj);
				return msg;
			}
			// 已激活标识为1，不再请求激活，返回错误码5(已激活)
			if (po.getObdactive() == 1) {
				setTdcodeError(obj, 5, "该二维码已被激活");
				msg.setData(obj);
				return msg;
			}
			// 需要条码才可以激活
			if (StringUtils.isBlank(po.getBarcode())) {
				setTdcodeError(obj, 6, "该二维码缺少设备条码");
				msg.setData(obj);
				return msg;
			}
			
			if(StringUtils.isBlank(po.getIdcardState())){
				setTdcodeError(obj, 7, "身份信息需实名");
				msg.setData(obj);
				return msg;
			}
			if("B".equals(po.getIdcardState())){
				setTdcodeError(obj, 8, "身份信息正在实名中");
				msg.setData(obj);
				return msg;
			}
			if("F".equals(po.getIdcardState())){
				TFluxIdcard fluxIdcard = jdbc.queryForObject(ApplicationContextTool.getDataSource(), "select result_describe from t_flux_idcard where tdcodemd5=? ", TFluxIdcard.class, new String[]{po.getTdcodemd5()});
				if(fluxIdcard==null){
					setTdcodeError(obj, 9, "身份信息实名未通过");
					msg.setData(obj);
					return msg;
				}
				setTdcodeError(obj, 9, "身份信息实名未通过，原因："+fluxIdcard.getResultDescribe());
				msg.setData(obj);
				return msg;
			}
			
			String mobilemd5Str = MD5.getMD5((mobileNum + Env.SERVICE_KEY)
					.getBytes());
			// 取第8-24位
			mobilemd5Str = StringUtils.substring(mobilemd5Str, 8, 24);
			String result = null;
			if (StringUtils.left(tdCodemd5, 3).toUpperCase().equals("O10")) {
				result = regist(tdCodemd5, mobileNum, mobilemd5Str,
						po.getBarcode(), params.get("fromPart"));
				if (StringUtils.isBlank(result)) {
					setTdcodeError(obj, 0, "后台注册失败");
					msg.setData(obj);
					return msg;
				}
				JSONObject jsonObj = JSONObject.fromObject(result);
				if (jsonObj == null) {
					setTdcodeError(obj, 4, "后台注册失败");
					msg.setData(obj);
					return msg;
				}
				if (!jsonObj.containsKey("error")) {
					setTdcodeError(obj, 4, "后台注册失败");
					msg.setData(obj);
					return msg;
				}
				String error = (String) jsonObj.get("error");
				if (!"ERR00".equals(error)) {
					setTdcodeError(obj, 4, "后台注册失败");
					msg.setData(obj);
					return msg;
				}
				
				Date date = new Date();
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update sp_tdcode set mobilenum=?,mobileid= ? ,obdactive = ?,tdverify= ? ,activetime = ?   where tdcodemd5= ? ",
						new Object[] { mobileNum, mobilemd5Str, 1, 1, date,
								po.getTdcodemd5() });
				// 增加日志记录
				TTdcodeOperateHistory history = new TTdcodeOperateHistory();
				history.setTdcodemd5(po.getTdcodemd5());
				history.setMobile(mobileNum);
				history.setSpcode(StringUtils.substring(po.getTdcode(), 0, 4));
				history.setTdcode(po.getTdcode());
				history.setOperateType("R");
				history.setOperateDescribe(mobileNum + "激活设备号");
				history.setFromPart(fromPart);
				addOperateHistory(jdbc, history);
				obj.put("status", 1);
				obj.put("description", "激活成功");
				obj.put("uid", mobilemd5Str);
				obj.put("passwd", Env.PASSWORD);
				obj.put("spcode", "cw");
				// 将数据同步给亿讯,写t_sync_tdcode
				TDCodeBus bus = new TDCodeBus();
				syncTDCode(jdbc, po, "U", bus);
				// 将成为数据通知给亿讯
				result = registYZ(params.get("fromPart"), po.getTdcodemd5(),
						mobileNum, po.getBarcode(), null, mobilemd5Str,
						Env.PASSWORD);
				if (result != null) {
					jsonObj = JSONObject.fromObject(result);
					if (jsonObj.getInt("code") == 0) {

					} else {
						log.info(po.getTdcodemd5() + "[" + mobileNum
								+ "]调用亿讯(激活)接口异常，需手工处理");
					}
				}
			} else if (StringUtils.left(tdCodemd5, 3).toUpperCase()
					.equals("O11")||StringUtils.left(tdCodemd5, 3).toUpperCase()
					.equals("O12")||StringUtils.left(tdCodemd5, 3).equals("O13")) {
				result = registYZ(params.get("fromPart"), tdCodemd5, mobileNum,
						po.getBarcode(), params.get("osType"), null, null);
				if (StringUtils.isBlank(result)) {
					setTdcodeError(obj, 4, "后台注册失败");
					msg.setData(obj);
					return msg;
				}
				JSONObject jsonObj = JSONObject.fromObject(result);
				if (jsonObj.getInt("code") == 0) {
					
					if (jsonObj.getJSONObject("data").get("state") != null
							&& jsonObj.getJSONObject("data").getInt("state") == 0) {
						Date date = new Date();
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update sp_tdcode set mobilenum=?,obdactive = ?,tdverify= ? ,activetime = ?   where tdcodemd5= ? ",
								new Object[] { mobileNum,  1,
										1, date, po.getTdcodemd5() });
						// 增加日志记录
						TTdcodeOperateHistory history = new TTdcodeOperateHistory();
						history.setTdcodemd5(po.getTdcodemd5());
						history.setMobile(mobileNum);
						history.setSpcode(StringUtils.substring(po.getTdcode(),
								0, 4));
						history.setTdcode(po.getTdcode());
						history.setOperateType("R");
						history.setOperateDescribe(mobileNum + "激活设备号");
						history.setFromPart(fromPart);
						addOperateHistory(jdbc, history);
						// 将数据同步给亿讯,写t_sync_tdcode
						TDCodeBus bus = new TDCodeBus();
						
						syncTDCode(jdbc, po, "U", bus);
						obj.put("status", 1);
						obj.put("description", "激活成功");
						obj.put("uid", po.getBarcode());
						obj.put("passwd", Env.PASSWORD);
						// 返回厂家
						if("O11".equals(StringUtils.left(tdCodemd5, 3))){
							obj.put("spcode", "yz");
						}else if("O12".equals(StringUtils.left(tdCodemd5, 3))){
							obj.put("spcode", "bdy");
						}else if("O13".equals(StringUtils.left(tdCodemd5, 3))){
							obj.put("spcode", "hgobd");
						}
						
					} else {
						setTdcodeError(obj, 4, "后台注册失败");
					}
				} else {
					setTdcodeError(obj, 4, "后台注册失败");
				}
			}
		} catch (Exception e) {
			log.error("查询sp_tdcode异常", e);
			setTdcodeError(obj, 3, "数据异常");
		}
		msg.setData(obj);
		return msg;

	}

//	public DataMsg registSN(Map<String, String> params) {
//		DataMsg msg = new DataMsg();
//
//		String tdCode = params.get("tdCode");
//		String timestamp = params.get("timestamp");
//		String authstring = params.get("authstring");
//		JSONObject obj = new JSONObject();
//
//		if (StringUtils.isBlank(tdCode) || StringUtils.isBlank(timestamp)
//				|| StringUtils.isBlank(authstring)) {
//			setTdcodeError(obj, 6, "验证字符错误");
//			msg.setData(obj);
//			return msg;
//		}
//		String authStr = MD5.getMD5((tdCode + Env.SERVICE_KEY_APP + timestamp)
//				.getBytes());
//		if (!authstring.equals(authStr)) {
//			setTdcodeError(obj, 6, "验证字符错误");
//			msg.setData(obj);
//			return msg;
//		}
//		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
//		try {
//			List<SpTdcode> list = jdbc
//					.query(ApplicationContextTool.getDataSource(),
//							"select tdcodemd5  from sp_tdcode where right(tdcodemd5,8) = ? ",
//							SpTdcode.class,
//							new String[] { tdCode.toLowerCase() }, 0, 0);
//			if (list != null && list.size() > 0) {
//				if (list.size() == 1) {
//					params.remove("tdCode");
//					params.put("tdCode", list.get(0).getTdcodemd5());
//					return regist(params);
//				} else {
//					setTdcodeError(obj, 0, "注册失败");
//				}
//			} else {
//				setTdcodeError(obj, 0, "注册失败");
//			}
//		} catch (Exception e) {
//			setTdcodeError(obj, 3, "数据异常");
//			log.error("", e);
//		}
//		msg.setData(obj);
//		return msg;
//	}
	
	public DataMsg registSN(Map<String, String> params) {
		DataMsg msg = new DataMsg();

		String tdCode = params.get("tdCode");
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		JSONObject obj = new JSONObject();

		if (StringUtils.isBlank(tdCode) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(authstring)) {
			setTdcodeError(obj, 10, "验证字符错误");
			msg.setData(obj);
			return msg;
		}
		String authStr = MD5.getMD5((tdCode + Env.SERVICE_KEY_APP + timestamp)
				.getBytes());
		if (!authstring.equals(authStr)) {
			setTdcodeError(obj, 10, "验证字符错误");
			msg.setData(obj);
			return msg;
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			List<SpTdcode> list = jdbc
					.query(ApplicationContextTool.getDataSource(),
							"select tdcodemd5  from sp_tdcode where right(tdcodemd5,8) = ? ",
							SpTdcode.class,
							new String[] { tdCode.toLowerCase() }, 0, 0);
			if (list != null && list.size() > 0) {
				if (list.size() == 1) {
					params.remove("tdCode");
					params.put("tdCode", list.get(0).getTdcodemd5());
					return regist(params);
				} else {
					setTdcodeError(obj, 4, "后台注册失败");
				}
			} else {
				setTdcodeError(obj, 4, "后台注册失败");
			}
		} catch (Exception e) {
			setTdcodeError(obj, 4, "后台注册失败");
			log.error("", e);
		}
		msg.setData(obj);
		return msg;
	}

	private void setTdcodeError(JSONObject obj, Integer status,
			String description) {
		obj.put("status", status);
		obj.put("description", description);
		obj.put("uid", "");
		obj.put("passwd", "");
		obj.put("tdcode", "");
	}

//	public DataMsg login(Map<String, String> params) {
//		DataMsg msg = new DataMsg();
//		JSONObject obj = new JSONObject();
//		String mobileNum = params.get("mobileNum");
//		String timestamp = params.get("timestamp");
//		String authstring = params.get("authstring");
//		String fromPart = params.get("fromPart");
//		if (StringUtils.isBlank(mobileNum) || StringUtils.isBlank(timestamp)
//				|| StringUtils.isBlank(authstring)) {
//			setTdcodeError(obj, 3, "验证字符错误");
//			msg.setData(obj);
//			return msg;
//		}
//		String authStr = MD5
//				.getMD5((mobileNum + Env.SERVICE_KEY_APP + timestamp)
//						.getBytes());
//		if (!authstring.equals(authStr)) {
//			setTdcodeError(obj, 3, "验证字符错误");
//			msg.setData(obj);
//			return msg;
//		}
//		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
//
//		try {
//			SpTdcode po = jdbc
//					.queryForObject(
//							ApplicationContextTool.getDataSource(),
//							"select status,mobileid,tdcode,tdcodemd5 from sp_tdcode where mobilenum = ? ",
//							SpTdcode.class, new String[] { mobileNum });
//			if (po != null) {
//
//				if (po.getStatus() == 1) {
//					obj.put("status", 1);
//					obj.put("description", "登录成功");
//					obj.put("uid", po.getMobileid());
//					obj.put("passwd", Env.PASSWORD);
//					obj.put("tdcode", po.getTdcodemd5());
//					if (po.getTdcodemd5().toUpperCase().startsWith("O10")) {
//						obj.put("spcode", "cw");
//					} else if (po.getTdcodemd5().toUpperCase()
//							.startsWith("O11")) {
//						obj.put("spcode", "yz");
//					}
//					TTdcodeOperateHistory history = new TTdcodeOperateHistory();
//					history.setTdcodemd5(po.getTdcodemd5());
//					history.setMobile(mobileNum);
//					history.setSpcode(StringUtils.substring(po.getTdcode(), 0,
//							4));
//					history.setTdcode(po.getTdcode());
//					history.setOperateType("L");
//					history.setOperateDescribe(mobileNum + "登录手机端");
//					history.setFromPart(fromPart);
//					addOperateHistory(jdbc, history);
//				} else {
//					setTdcodeError(obj, 0, "登录失败");
//				}
//			} else {
//				setTdcodeError(obj, 2, "未注册");
//			}
//		} catch (Exception e) {
//			log.error("查询sp_tdcode异常", e);
//			e.printStackTrace();
//		}
//		msg.setData(obj);
//		return msg;
//	}

	
	public DataMsg login(Map<String, String> params) {
		DataMsg msg = new DataMsg();
		JSONObject obj = new JSONObject();
		String mobileNum = params.get("mobileNum");
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		String fromPart = params.get("fromPart");
		if (StringUtils.isBlank(mobileNum) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(authstring)) {
			setTdcodeError(obj, 3, "验证字符错误");
			msg.setData(obj);
			return msg;
		}
		String authStr = MD5
				.getMD5((mobileNum + Env.SERVICE_KEY_APP + timestamp)
						.getBytes());
		if (!authstring.equals(authStr)) {
			setTdcodeError(obj, 3, "验证字符错误");
			msg.setData(obj);
			return msg;
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");

		try {
			SpTdcode po = jdbc
					.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select status,mobileid,tdcode,tdcodemd5,idcard_state from sp_tdcode where mobilenum = ? ",
							SpTdcode.class, new String[] { mobileNum });
			if(po==null){
				setTdcodeError(obj, 2, "未注册");
				msg.setData(obj);
				return msg;
			}
			if(po.getStatus()!=1){
				setTdcodeError(obj, 0, "登录失败");
				msg.setData(obj);
				return msg;
			}
			
			if(StringUtils.isBlank(po.getIdcardState())){
				obj.put("status", 4);
				obj.put("description", "身份信息需实名");
				obj.put("uid", "");
				obj.put("passwd", "");
				obj.put("tdcode", po.getTdcodemd5());
				
				msg.setData(obj);
				return msg;
			}
			if("B".equals(po.getIdcardState())){
				obj.put("status", 5);
				obj.put("description", "身份信息正在实名中");
				obj.put("uid", "");
				obj.put("passwd", "");
				obj.put("tdcode", po.getTdcodemd5());
				msg.setData(obj);
				
				return msg;
			}
			if("F".equals(po.getIdcardState())){
				
				TFluxIdcard fluxIdcard = jdbc.queryForObject(ApplicationContextTool.getDataSource(), "select result_describe from t_flux_idcard where tdcodemd5=? ", TFluxIdcard.class, new String[]{po.getTdcodemd5()});
				if(fluxIdcard==null){
					obj.put("status", 4);
					obj.put("description", "身份信息需实名");
					obj.put("uid", "");
					obj.put("passwd", "");
					obj.put("tdcode", po.getTdcodemd5());
					msg.setData(obj);
					return msg;
				}
				obj.put("status", 6);
				obj.put("description", "身份信息实名未通过，原因："+fluxIdcard.getResultDescribe());
				obj.put("uid", "");
				obj.put("passwd", "");
				obj.put("tdcode", po.getTdcodemd5());
				msg.setData(obj);
				return msg;
			}
			obj.put("status", 1);
			obj.put("description", "登录成功");
			obj.put("uid", po.getMobileid());
			obj.put("passwd", Env.PASSWORD);
			obj.put("tdcode", po.getTdcodemd5());
			if (po.getTdcodemd5().toUpperCase().startsWith("O10")) {
				obj.put("spcode", "cw");
			} else if (po.getTdcodemd5().toUpperCase()
					.startsWith("O11")) {
				obj.put("spcode", "yz");
			}else if (po.getTdcodemd5().toUpperCase()
					.startsWith("O12")) {
				obj.put("spcode", "bdy");
			}else if(po.getTdcodemd5().toUpperCase()
					.startsWith("O13")){
				obj.put("spcode", "hgobd");
			}
			TTdcodeOperateHistory history = new TTdcodeOperateHistory();
			history.setTdcodemd5(po.getTdcodemd5());
			history.setMobile(mobileNum);
			history.setSpcode(StringUtils.substring(po.getTdcode(), 0,
					4));
			history.setTdcode(po.getTdcode());
			history.setOperateType("L");
			history.setOperateDescribe(mobileNum + "登录手机端");
			history.setFromPart(fromPart);
			addOperateHistory(jdbc, history);
			
			
		} catch (Exception e) {
			log.error("查询sp_tdcode异常", e);
			e.printStackTrace();
		}
		msg.setData(obj);
		return msg;
	}
	
	public DataMsg releasebind(Map<String, String> params) {
		DataMsg msg = new DataMsg();
		String mobileNum = params.get("mobileNum");
		String tdCodemd5 = params.get("tdCode");
		String fromPart = params.get("fromPart");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		JSONObject obj = new JSONObject();
		try {
			SpTdcode po = jdbc
					.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select ID,mobilenum, mobileid,tdcode,tdcodemd5 , status,barcode,group_id,fluxcard,limite_time  from sp_tdcode where mobilenum = ? and tdcodemd5= ?  ",
							SpTdcode.class,
							new String[] { mobileNum, tdCodemd5 });
			if (po == null) {
				obj.put("status", 0);
				obj.put("description", "解绑失败");
				msg.setData(obj);
				return msg;
			}
			// 调用厂家的接口
			if (StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
					.equals("O10")) {
				String result = off(po, fromPart);
				if (StringUtils.isBlank(result)) {
					obj.put("status", 2);
					obj.put("description", "设备平台错误");
					msg.setData(obj);
					return msg;
				}
				JSONObject jsonObj = JSONObject.fromObject(result);
				if (jsonObj == null) {
					log.info("成为解绑返回非json格式");
					obj.put("status", 2);
					obj.put("description", "设备平台错误");
					msg.setData(obj);
					return msg;
				}
				if (!jsonObj.containsKey("error")) {
					obj.put("status", 2);
					obj.put("description", "设备平台错误");
					msg.setData(obj);
					return msg;
				}
				String error = (String) jsonObj.get("error");
				// 成功
				if ("ERR00".equals(error)) {
					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"update sp_tdcode set mobilenum=null,mobileid= ? ,obdactive = ?,activetime=null  where ID = ?",
							new Object[] { "", 0, po.getID() });
					obj.put("status", 1);
					obj.put("description", "release_ok");
					TTdcodeOperateHistory history = new TTdcodeOperateHistory();
					history.setTdcodemd5(po.getTdcodemd5());
					history.setMobile(mobileNum);
					history.setSpcode(StringUtils.substring(po.getTdcode(), 0,
							4));
					history.setTdcode(po.getTdcode());
					history.setOperateType("D");
					history.setOperateDescribe(mobileNum + "注销设备号");
					history.setFromPart(fromPart);
					addOperateHistory(jdbc, history);
					// 将行业版数据同步给亿讯,写t_sync_tdcode
					TDCodeBus bus = new TDCodeBus();
					syncTDCode(jdbc, po, "R", bus);
					result = releasebindYZ(mobileNum, fromPart);
					if (result != null) {
						jsonObj = JSONObject.fromObject(result);
						if (jsonObj.getInt("code") == 0) {

						} else {
							log.info(mobileNum + "调用亿讯(注销)接口异常，需手工处理");
						}
					}
				} else {
					obj.put("status", 2);
					obj.put("description", "设备平台错误");
					return msg;
				}

			} else if (StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
					.equals("O11")||StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
					.equals("O12")||StringUtils.left(tdCodemd5, 3).equals("O13")) {
				String result = releasebindYZ(
						String.valueOf(po.getMobilenum()), fromPart);
				if (StringUtils.isBlank(result)) {
					obj.put("status", 2);
					obj.put("description", "设备平台错误");
					return msg;
				}
				JSONObject jsonObj = JSONObject.fromObject(result);
				if (jsonObj.getInt("code") == 0) {
					if (jsonObj.getJSONObject("data").get("state") != null
							&& jsonObj.getJSONObject("data").getInt("state") == 0) {
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update sp_tdcode set mobilenum=null,mobileid= ? ,obdactive = ?,activetime=null  where ID = ?",
								new Object[] { "", 0, po.getID() });
						obj.put("status", 1);
						obj.put("description", "release_ok");
						TTdcodeOperateHistory history = new TTdcodeOperateHistory();
						history.setTdcodemd5(po.getTdcodemd5());
						history.setMobile(mobileNum);
						history.setSpcode(StringUtils.substring(po.getTdcode(),
								0, 4));
						history.setTdcode(po.getTdcode());
						history.setOperateType("D");
						history.setOperateDescribe(mobileNum + "注销设备号");
						history.setFromPart(fromPart);
						addOperateHistory(jdbc, history);
						// 将数据同步给亿讯,写t_sync_tdcode
						TDCodeBus bus = new TDCodeBus();
						syncTDCode(jdbc, po, "R", bus);
					} else {
						obj.put("status", 2);
						obj.put("description", "release_err");
					}
				} else {
					obj.put("status", 2);
					obj.put("description", "release_err");
				}

			}
		} catch (Exception e) {
			log.error("查询sp_tdcode异常", e);
			e.printStackTrace();
		}
		msg.setData(obj);
		return msg;
	}

	public Data registManager(Map<String, String> params)
			throws RemoteException {
		Data data = new Data(false, "激活失败");
		String tdCode = params.get("tdCode");
		String mobileNum = params.get("mobileNum");
		String fromPart = params.get("fromPart");
		if (StringUtils.isBlank(tdCode) || StringUtils.isBlank(mobileNum)) {
			data.setMsg("设备号或手机号码不能为空");
			return data;
		}
		if (tdCode.length() < 8) {
			data.setMsg("设备号至少输入8位");
			return data;
		}
		String errorMobile = PatternTool.checkStr(mobileNum, "^1[0-9]{9}\\d",
				"错误号码");
		if (errorMobile != null) {
			data.setMsg(errorMobile);
			return data;
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		List<SpTdcode> list = null;
		try {
			list = jdbc.query(ApplicationContextTool.getDataSource(),
					"select * from sp_tdcode where tdcodemd5 like  ? ",
					SpTdcode.class, new String[] { "%" + tdCode + "%" }, 0, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("", e);
			return data;
		}
		if (list == null || list.size() == 0) {
			data.setMsg("设备未找到");
			return data;
		}
		if (list != null && list.size() > 1) {
			data.setMsg("查找出多个设备,请增加设备号的位数");
			return data;
		}
		SpTdcode po = list.get(0);
		// status是否启用(发放)新增的二维码(1:启用，启用后才可以激活，0:未启用)
		if (po.getStatus() != 1) {
			data.setMsg(po.getTdcodemd5() + "设备未发放");
			return data;
		}
		if (po.getObdactive() == 1) {
			data.setMsg(po.getTdcodemd5() + "设备已激活");
			return data;
		}
		if (StringUtils.isBlank(po.getBarcode())) {
			data.setMsg(po.getTdcodemd5() + "设备条码为空");
			return data;
		}

		String mobilemd5Str = MD5.getMD5((mobileNum + Env.SERVICE_KEY)
				.getBytes());
		// 取第8-24位
		mobilemd5Str = StringUtils.substring(mobilemd5Str, 8, 24);
		if (StringUtils.isBlank(po.getBarcode())) {
			data.setMsg(po.getTdcodemd5() + "设备条码为空");
			return data;
		}
		if(StringUtils.isBlank(po.getIdcardState())||!"S".equals(po.getIdcardState())){
			data.setMsg(po.getTdcodemd5() + "设备未实名或者实名未通过");
			return data;
		}
		if (StringUtils.left(po.getTdcodemd5(), 3).toUpperCase().equals("O10")) {
			String result = regist(po.getTdcodemd5(), mobileNum, mobilemd5Str,
					po.getBarcode(), fromPart);
			if (StringUtils.isBlank(result)) {
				data.setMsg("请求OBD接口失败");
				return data;
			}
			JSONObject jsonObj = JSONObject.fromObject(result);
			if (jsonObj != null) {
				if (jsonObj.containsKey("error")) {
					String error = (String) jsonObj.get("error");
					// 成功
					Date date = new Date();
					if ("ERR00".equals(error)) {
						try {
							jdbc.update(
									ApplicationContextTool.getDataSource(),
									"update sp_tdcode set mobilenum=?,mobileid= ? ,obdactive = ?,tdverify= ? ,activetime = ?   where tdcodemd5= ? ",
									new Object[] { mobileNum, mobilemd5Str, 1,
											1, date, po.getTdcodemd5() });

						} catch (SQLException e) {
							e.printStackTrace();

						}

						// 增加日志记录
						TTdcodeOperateHistory history = new TTdcodeOperateHistory();
						history.setTdcodemd5(po.getTdcodemd5());
						history.setMobile(mobileNum);
						history.setSpcode(StringUtils.substring(po.getTdcode(),
								0, 4));
						history.setTdcode(po.getTdcode());
						history.setOperateType("R");
						history.setOperateDescribe(mobileNum + "激活设备号");
						history.setFromPart(fromPart);
						addOperateHistory(jdbc, history);
						data.setMsg("激活成功");
						data.setState(true);
						// 将行业版数据同步给亿讯,写t_sync_tdcode
						po.setMobilenum(Long.valueOf(mobileNum));
						TDCodeBus bus = new TDCodeBus();
						syncTDCode(jdbc, po, "U", bus);
						// 将成为数据通知给亿讯
						result = registYZ(params.get("fromPart"),
								po.getTdcodemd5(), mobileNum, po.getBarcode(),
								null, mobilemd5Str, Env.PASSWORD);
						if (result != null) {
							jsonObj = JSONObject.fromObject(result);
							if (jsonObj.getInt("code") == 0) {

							} else {
								data.setMsg(po.getTdcodemd5() + "[" + mobileNum
										+ "]激活成功" + ",但是调用亿讯接口异常，请联系管理员");
								log.info("调用亿讯(激活)接口异常，需手工处理");
							}
						}
						return data;
					} else {
						if (Env.ERROR_CODE.containsKey(error)) {
							data.setMsg(Env.ERROR_CODE.get(error));
						} else {
							data.setMsg(result);
						}
					}
				}
			}

		} else if (StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
				.equals("O11")||StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
				.equals("O12")||StringUtils.left(po.getTdcodemd5(), 3).equals("O13")) {
			String result = registYZ(params.get("fromPart"), po.getTdcodemd5(),
					mobileNum, po.getBarcode(), params.get("osType"),
					mobilemd5Str, Env.PASSWORD);
			if (StringUtils.isBlank(result)) {
				data.setMsg("亿迅接口返回错误");
				return data;
			}
			JSONObject jsonObj = JSONObject.fromObject(result);
			if (jsonObj.getInt("code") == 0) {
				Date date = new Date();
				try {
					jdbc.update(
							ApplicationContextTool.getDataSource(),
							"update sp_tdcode set mobilenum=?,obdactive = ?,tdverify= ? ,activetime = ?   where tdcodemd5= ? ",
							new Object[] { mobileNum, 1, 1,
									date, po.getTdcodemd5() });
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// 增加日志记录
				TTdcodeOperateHistory history = new TTdcodeOperateHistory();
				history.setTdcodemd5(po.getTdcodemd5());
				history.setMobile(mobileNum);
				history.setSpcode(StringUtils.substring(po.getTdcode(), 0, 4));
				history.setTdcode(po.getTdcode());
				history.setOperateType("R");
				history.setOperateDescribe(mobileNum + "激活设备号");
				addOperateHistory(jdbc, history);
				data.setMsg("激活成功");
				data.setState(true);
				// 将行业版数据同步给亿讯,写t_sync_tdcode
				TDCodeBus bus = new TDCodeBus();
				po.setMobilenum(Long.valueOf(StringUtils.trim(mobileNum)));
				syncTDCode(jdbc, po, "U", bus);
				return data;
			} else {
				data.setMsg(jsonObj.getString("desc"));
			}
		}
		return data;

	}

	public Data releasebindManager(String mobileNum, String fromPart) {
		Data data = new Data(false, "注销失败");
		if (StringUtils.isBlank(mobileNum)) {
			data.setMsg("手机号码不能为空");
			return data;
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			SpTdcode po = jdbc
					.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select ID,mobilenum, mobileid,tdcode,tdcodemd5 , status,barcode,group_id,fluxcard,limite_time  from sp_tdcode where mobilenum = ?  ",
							SpTdcode.class, new String[] { mobileNum });
			if (po == null) {
				data.setMsg(mobileNum + "平台不存在");
				return data;
			}

			if (StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
					.equals("O10")) {
				String result = off(po, fromPart);
				if (StringUtils.isBlank(result)) {
					data.setMsg("请求成为接口失败");
					return data;
				}
				JSONObject jsonObj = JSONObject.fromObject(result);
				if (jsonObj == null) {
					data.setMsg("成为OBD接口返回格式解析失败");
					return data;
				}

				if (jsonObj.containsKey("error")) {
					String error = (String) jsonObj.get("error");
					// 成功
					if ("ERR00".equals(error)) {
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update sp_tdcode set mobilenum=null,mobileid= ? ,obdactive = ?,activetime = null  where tdcodemd5 = ?",
								new Object[] { "", 0, po.getTdcodemd5() });

						TTdcodeOperateHistory history = new TTdcodeOperateHistory();
						history.setTdcodemd5(po.getTdcodemd5());
						history.setMobile(mobileNum);
						history.setSpcode(StringUtils.substring(po.getTdcode(),
								0, 4));
						history.setTdcode(po.getTdcode());
						history.setOperateType("D");
						history.setOperateDescribe(mobileNum + "注销设备号");
						history.setFromPart(fromPart);
						addOperateHistory(jdbc, history);
						data.setState(true);
						data.setMsg(mobileNum + "注销成功");
						// 将行业版数据同步给亿讯,写t_sync_tdcode
						TDCodeBus bus = new TDCodeBus();
						syncTDCode(jdbc, po, "R", bus);
						// 要将成为数据给亿讯，
						result = releasebindYZ(mobileNum, fromPart);
						if (result != null) {
							jsonObj = JSONObject.fromObject(result);
							if (jsonObj.getInt("code") == 0) {

							} else {
								data.setMsg(mobileNum
										+ "注销成功,但是调用亿讯接口异常，请联系管理员");
								log.info("调用亿讯(注销)接口异常，需手工处理");
							}
						}

						return data;
					} else {
						data.setMsg(error + "|" + Env.ERROR_CODE.get(error));
						return data;
					}
				}

			} else if (StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
					.equals("O11")||StringUtils.left(po.getTdcodemd5(), 3).toUpperCase()
					.equals("O12")||StringUtils.left(po.getTdcodemd5(), 3).equals("O13")) {
				String result = releasebindYZ(
						String.valueOf(po.getMobilenum()), fromPart);
				if (StringUtils.isBlank(result)) {
					data.setMsg("亿迅接口返回错误");
					return data;
				}
				JSONObject jsonObj = JSONObject.fromObject(result);
				if (jsonObj.getInt("code") == 0) {
					if (jsonObj.getJSONObject("data").get("state") != null
							&& jsonObj.getJSONObject("data").getInt("state") == 0) {
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"update sp_tdcode set mobilenum=null,mobileid= ? ,obdactive = ?,activetime=null  where ID = ?",
								new Object[] { "", 0, po.getID() });
						TTdcodeOperateHistory history = new TTdcodeOperateHistory();
						history.setTdcodemd5(po.getTdcodemd5());
						history.setMobile(mobileNum);
						history.setSpcode(StringUtils.substring(po.getTdcode(),
								0, 4));
						history.setTdcode(po.getTdcode());
						history.setOperateType("D");
						history.setOperateDescribe(mobileNum + "注销设备号");
						history.setFromPart(fromPart);
						addOperateHistory(jdbc, history);
						data.setState(true);
						data.setMsg(mobileNum + "注销成功");
						// 将数据同步给亿讯,写t_sync_tdcode
						TDCodeBus bus = new TDCodeBus();
						syncTDCode(jdbc, po, "R", bus);

						return data;
					} else {
						data.setMsg(jsonObj.getString("desc"));
						return data;
					}
				} else {
					data.setMsg(jsonObj.getString("desc"));
					return data;
				}
			}
		} catch (Exception e) {
			log.error("查询sp_tdcode异常", e);
			data.setMsg("查询sp_tdcode异常");
			return data;
		}
		return data;
	}

	/**
	 * 成为解绑接口
	 * 
	 * @param po
	 * @return
	 * @throws RemoteException
	 */
	public String off(SpTdcode po, String fromPart) throws RemoteException {
		CztServiceServiceStub stub = new CztServiceServiceStub(
				ConfLoad.getProperty("OBD_WEBSERVICE_WSDL"));
		Off off10 = new Off();
		org.apache.axis2.databinding.types.soapencoding.String user = new org.apache.axis2.databinding.types.soapencoding.String();
		user.setString(Env.OBD_USER);
		off10.setKey(user);
		org.apache.axis2.databinding.types.soapencoding.String pass = new org.apache.axis2.databinding.types.soapencoding.String();
		pass.setString(Env.OBD_PASS);
		off10.setSercet(pass);

		org.apache.axis2.databinding.types.soapencoding.String userid = new org.apache.axis2.databinding.types.soapencoding.String();
		userid.setString(po.getMobileid());
		off10.setUser(userid);
		org.apache.axis2.databinding.types.soapencoding.String userpass = new org.apache.axis2.databinding.types.soapencoding.String();
		userpass.setString(MD5.getMD5(Env.PASSWORD.getBytes()).toUpperCase());
		off10.setPass(userpass);
		org.apache.axis2.databinding.types.soapencoding.String output = new org.apache.axis2.databinding.types.soapencoding.String();
		output.setString("");
		off10.setOutput(output);
		// 要加上Options HTTPConstants.CHUNKED =false 不然访问不了
		Options options = stub._getServiceClient().getOptions();
		options.setProperty(HTTPConstants.CHUNKED, false);
		OffResponse response = stub.off(off10);
		String fromPartStr = "APP";
		if (fromPart != null) {
			fromPartStr = FROM_PART.get(fromPart);
		}
		log.info(po.getMobilenum() + "[来至" + fromPartStr
				+ "]注销[off]调用成为webservice发送请求参数:"
				+ JSONObject.fromObject(off10).toString());

		String result = response.getOffReturn().getString();
		log.info(po.getMobilenum() + "[来至" + fromPartStr
				+ "]注销[off]调用成为webservice返回结果:" + result);
		return result;
	}

	/**
	 * 激活成为接口
	 * 
	 * @param tdCodemd5
	 * @param mobileNum
	 * @param mobilemd5Str
	 * @return
	 * @throws RemoteException
	 */
	public String regist(String tdCodemd5, String mobileNum,
			String mobilemd5Str, String barcode, String fromPart)
			throws RemoteException {
		CztServiceServiceStub stub = new CztServiceServiceStub(
				ConfLoad.getProperty("OBD_WEBSERVICE_WSDL"));
		cn.cellcom.czt.webservice.client.CztServiceServiceStub.Register register0 = new cn.cellcom.czt.webservice.client.CztServiceServiceStub.Register();

		org.apache.axis2.databinding.types.soapencoding.String user = new org.apache.axis2.databinding.types.soapencoding.String();
		user.setString(Env.OBD_USER);
		register0.setKey(user);
		org.apache.axis2.databinding.types.soapencoding.String pass = new org.apache.axis2.databinding.types.soapencoding.String();
		pass.setString(Env.OBD_PASS);
		register0.setSercet(pass);

		org.apache.axis2.databinding.types.soapencoding.String userid = new org.apache.axis2.databinding.types.soapencoding.String();
		userid.setString(mobilemd5Str);
		register0.setUser(userid);
		org.apache.axis2.databinding.types.soapencoding.String userpass = new org.apache.axis2.databinding.types.soapencoding.String();
		userpass.setString(MD5.getMD5(Env.PASSWORD.getBytes()).toUpperCase());
		register0.setPass(userpass);
		org.apache.axis2.databinding.types.soapencoding.String sn = new org.apache.axis2.databinding.types.soapencoding.String();
		sn.setString(tdCodemd5);
		register0.setSn(sn);
		org.apache.axis2.databinding.types.soapencoding.String output = new org.apache.axis2.databinding.types.soapencoding.String();
		output.setString("");
		register0.setOutput(output);
		String fromPartStr = "APP";
		if (fromPart != null) {
			fromPartStr = FROM_PART.get(fromPart);
		}
		log.info(mobileNum + "[来至" + fromPartStr + "]激活[register]" + tdCodemd5
				+ "调用成为webservice发送请求参数:"
				+ JSONObject.fromObject(register0).toString());
		// 要加上Options HTTPConstants.CHUNKED =false 不然访问不了
		Options options = stub._getServiceClient().getOptions();
		options.setProperty(HTTPConstants.CHUNKED, false);
		RegisterResponse response = stub.register(register0);
		String result = response.getRegisterReturn().getString();
		log.info(mobileNum + "[来至" + fromPartStr + "]激活[register]" + tdCodemd5
				+ "调用成为webservice返回结果:" + result);
		return result;
	}

	/**
	 * 激活元征接口
	 * 
	 * @return
	 */
	public String registYZ(String fromPart, String tdCodemd5, String mobileNum,
			String barcode, String mobileType, String mobileid, String password) {
		HttpUrlClient client = new HttpUrlClient();

		JSONObject params = new JSONObject();
		params.put("userId", mobileNum);
		params.put("carUser", ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		Long time = System.currentTimeMillis();
		params.put("time", time);
		// MD5 32位(userId+ carUser+ time+password) password为系统分配的密码
		StringBuffer str = new StringBuffer(mobileNum);
		str.append(ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		str.append(time).append(ConfLoad.getProperty("YZ_TOKENID_PASSWORD"));
		String sign = MD5.getMD5(str.toString().getBytes());
		params.put("sign", sign);
		params.put("hgDeviceSn", tdCodemd5);
		params.put("deviceId", barcode);
		if (StringUtils.left(tdCodemd5, 3).toUpperCase().equals("O10")) {
			params.put("account", mobileid);
			params.put("password", password);
		}
		String fromPartStr = "APP";
		if (fromPart != null) {
			fromPartStr = FROM_PART.get(fromPart);
		}
		String result = client.post(mobileNum + "[来至" + fromPartStr + "]激活"
				+ tdCodemd5 + "调用亿讯接口", ConfLoad.getProperty("YZ_URL")
				+ "api/obd/bind?", params, 20000, true);
		// 返回格式 {"code":0,"desc":"嘻嘻嘻嘻嘻嘻","data":{"state":6}}
		log.info(mobileNum + "[来至" + fromPartStr + "]激活" + tdCodemd5
				+ "调用亿讯接口返回结果：" + result);
		if (result != null)
			return result;
		return null;
	}

	public String releasebindYZ(String mobileNum, String fromPart) {
		HttpUrlClient client = new HttpUrlClient();
		JSONObject params = new JSONObject();
		params.put("userId", mobileNum);
		params.put("carUser", ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		Long time = System.currentTimeMillis();
		params.put("time", time);
		// MD5 32位(userId+ carUser+ time+password) password为系统分配的密码
		StringBuffer str = new StringBuffer(mobileNum);
		str.append(ConfLoad.getProperty("YZ_TOKENID_ACCOUNT"));
		str.append(time).append(ConfLoad.getProperty("YZ_TOKENID_PASSWORD"));
		String sign = MD5.getMD5(str.toString().getBytes());
		params.put("sign", sign);
		String fromPartStr = "APP";
		if (fromPart != null) {
			fromPartStr = FROM_PART.get(fromPart);
		}
		String result = client.post(mobileNum + "[来至" + fromPartStr
				+ "]解绑调用亿讯接口", ConfLoad.getProperty("YZ_URL")
				+ "api/obd/unBind?", params, 20000, true);
		// 返回格式 {"code":0,"desc":"嘻嘻嘻嘻嘻嘻","data":{"state":6}}
		log.info(mobileNum + "[来至" + fromPartStr + "]解绑调用亿讯接口返回结果：" + result);
		if (result != null)
			return result;
		return null;

	}

	private int addOperateHistory(JDBC jdbc, TTdcodeOperateHistory po) {
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		if (po != null) {
			String fromPart = po.getFromPart();
			if (StringUtils.isBlank(fromPart)) {
				fromPart = "mobile";
			}
			Object[] params = { po.getMobile(), po.getSpcode(), po.getTdcode(),
					po.getTdcodemd5(), po.getOperateType(),
					po.getOperateDescribe(), fromPart, new Date() };
			try {
				return jdbc
						.update(ApplicationContextTool.getDataSource(),
								"insert into t_tdcode_operate_history(mobile,spcode,tdcode,tdcodemd5,operate_type,operate_describe,from_part,operate_time) values(?,?,?,?,?,?,?,?)",
								params);

			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;

	}

	public boolean syncTDCode(JDBC jdbc, SpTdcode po, String operateType,
			TDCodeBus bus) {
		//同步给亿迅的 ID【必填】^手机1^设备编号1【必填】^二维码1【必填】^流量卡号1【必填】^所属分组1【必填】
		if (po != null && po.getGroupId() != null && po.getGroupId() > 10000) {
			TDCodeBus tdcodeBus = new TDCodeBus();
			po =tdcodeBus.queryTDCodeByMD5(jdbc, po.getTdcodemd5());
			TSyncTDCode sync = new TSyncTDCode();
			sync.setBarcode(po.getBarcode());
			sync.setFluxcard(po.getFluxcard());
			sync.setGroupId(po.getGroupId());
			sync.setLimiteTime(po.getLimiteTime());
			if (po.getMobilenum() == null) {
				sync.setMobilenum(null);
			} else {
				sync.setMobilenum(String.valueOf(po.getMobilenum()));
			}
			sync.setOperateType(operateType);
			sync.setTdcodemd5(po.getTdcodemd5());
			sync.setSubmitTime(DateTool.getTimestamp(null));
			sync.setState("N");

			bus.addSycnTDCode(jdbc, sync);
			
			
			//pn sn group_id flux_card limit_time不为空时 已激活  同步给obd
			if (StringUtils.isNotBlank(po.getTdcodemd5())
					&&StringUtils.isNotBlank(po.getBarcode())
					&&StringUtils.isNotBlank(po.getFluxcard())
					&&po.getLimiteTime()!=null) {
				if (po.getTdcodemd5().startsWith("O13")) {
					bus.addSycnTDCode2HGObd(jdbc,po);
				}
			}
			return true;
		}
		return false;

	}

	public void transfer(Map<String, String> params) {
		if ("true".equals(ConfLoad.getProperty("DATATRANSFER"))) {
			if (params.get("serviceIp") != null
					&& ("183.62.251.19".equals(params.get("serviceIp")) || "192.168.7.202"
							.equals(params.get("serviceIp")))) {
				HttpUrlClient client = new HttpUrlClient();
				Map<String, String> dbParams = new LinkedHashMap<String, String>();
				dbParams.put("account", "admin");
				dbParams.put("password", "cellc0m");
				dbParams.put("sql", params.get("sql"));
				String dbResult = client
						.doPost("数据割接到亿讯平台",
								"http://183.63.133.137:80/CZT/transfer/DataTransferAction_transferUpdate.do",
								dbParams, "UTF-8", 20000, true);
				if ("true".equals(dbResult)) {
					log.info("割接到亿讯平台SQL:" + params.get("sql") + "成功");
				}
			}
		}
	}

	public DataMsg checkIDCard(Map<String, String> params, File image1,
			File image2, File image3) {
		String tdCode = params.get("tdCode");
		String image1ExtName = params.get("image1ExtName");
		String image2ExtName = params.get("image2ExtName");
		String image3ExtName = params.get("image3ExtName");
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		String fromPart = params.get("fromPart");
		JSONObject obj = new JSONObject();
		DataMsg msg = new DataMsg();
		if (StringUtils.isBlank(tdCode) || StringUtils.isBlank(timestamp)
				|| StringUtils.isBlank(authstring)) {
			setTdcodeError(obj, 2, "验证字符错误");
			msg.setData(obj);
			return msg;
		}
		String authStr = MD5.getMD5((tdCode + Env.SERVICE_KEY_APP + timestamp)
				.getBytes());
		if (!authstring.equals(authStr)) {
			setTdcodeError(obj, 2, "验证字符错误");
			msg.setData(obj);
			return msg;
		}
		if (image1 == null || image2 == null || image3 == null) {
			setTdcodeError(obj, 3, "上传文件错误");
			msg.setData(obj);
			return msg;
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		SpTdcode po = null;
		try {
			if(tdCode.length()>=8&&tdCode.length()<20){
				po = jdbc.queryForObject(ApplicationContextTool.getDataSource(),
						"select tdcodemd5,status, idcard_state,fluxcard,orderid from sp_tdcode where tdcodemd5 like ?  ",
						SpTdcode.class, new String[] { "%"+tdCode });
			}else{
				po = jdbc.queryForObject(ApplicationContextTool.getDataSource(),
						"select tdcodemd5,status, idcard_state,fluxcard,orderid from sp_tdcode where tdcodemd5 = ? ",
						SpTdcode.class, new String[] { tdCode });
			}
			
			if (po == null) {
				setTdcodeError(obj, 4, "设备二维码不存在");
				msg.setData(obj);
				return msg;
			}
			// status是否启用新增的二维码(1:启用，启用后才可以激活，0:未启用)
			if (po.getStatus() != 1) {
				setTdcodeError(obj, 5, "设备二维码未发放");
				msg.setData(obj);
				return msg;
			}
			//已验证的，无需验证
			if ("S".equals(po.getIdcardState())) {
				setTdcodeError(obj,6, " 实名验证通过，无需在验证 ");
				msg.setData(obj);
				return msg;
			}
			if(StringUtils.isBlank(po.getOrderid())){
				setTdcodeError(obj,7, "订单未找到，无法实名验证 ");
				msg.setData(obj);
				return msg;
			}
		} catch (Exception e) {
			setTdcodeError(obj, 0, "实名验证失败 ");
			msg.setData(obj);
			return msg;
		}
		// 上传文件
		String filePath = ConfLoad.getProperty("IDCARD_PATH");
		File image1_new =  new File(filePath, po.getTdcodemd5() + "_1."
				+ image1ExtName);
		File image2_new = new File(filePath, po.getTdcodemd5() + "_2."
					+ image2ExtName);
		File image3_new = new File(filePath, po.getTdcodemd5() + "_3."
				+ image3ExtName);
		try {
			FileUtils.copyFile(image1,image1_new);
			FileUtils.copyFile(image2,image2_new);
			FileUtils.copyFile(image3,image3_new );
		} catch (IOException e) {
			e.printStackTrace();
			setTdcodeError(obj, 3, "文件上传错误 ");
			msg.setData(obj);
			return msg;
		}

		// 存入表,先判断是否存在
		FLuxIdcardBus fluxIdcardBus = new FLuxIdcardBus();
		TFluxIdcard fluxIdcard = fluxIdcardBus.queryByTdcodemd5(jdbc, po.getTdcodemd5());
		String fromPartStr = "APP";
		if (fromPart != null) {
			fromPartStr = FROM_PART.get(fromPart);
		}
		try {
			//根据订单ID找到 订单录入的账号，区号，为身份审核做准备
			String orderid =po.getOrderid();
			TOrder order= jdbc.queryForObject(ApplicationContextTool.getDataSource(), "select account,from_part from t_order where id = ? ", TOrder.class, new String[]{orderid});
			TManager manager = jdbc
					.queryForObject(
							ApplicationContextTool.getDataSource(),
							"select areacode from t_manager where account = ? ",
							TManager.class, new String[] { order.getAccount() });
			String areacode = null;
			if(manager!=null){
				areacode = manager.getAreacode();
			}
			if (fluxIdcard == null) {
				Date date = new Date();
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"insert into t_flux_idcard(tdcodemd5,fluxcard,image1,image2,image3,state,submit_time,from_part,account,areacode,order_from_part,submit_time2,mobile_num) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] {po.getTdcodemd5(),po.getFluxcard(), image1_new.getName(),
							image2_new.getName(), image3_new.getName(), "N",
								DateTool.getTimestamp(date),fromPartStr,order.getAccount(),areacode,order.getFromPart(),date.getTime(),params.get("mobileNum") });
				//同时将sp_tdcode 的idcard_state 改成正在审核ing (idcard_state=B)
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update sp_tdcode set idcard_state=? where tdcodemd5=?", new String[]{"B",po.getTdcodemd5()});
				obj.put("status", 1);
				
				obj.put("description", "上传成功，等待实名审核");
				msg.setData(obj);
				return msg;
			}
			if ("F".equals(fluxIdcard.getState())||"N".equals(fluxIdcard.getState())) {
				Date date = new Date();
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update t_flux_idcard set image1=?, image2=?,image3=?,fluxcard=? ,from_part = ? ,state='N',result_describe=null,account = ?,areacode=?,order_from_part=?,submit_time = ?,submit_time2= ? where tdcodemd5=?",
						new Object[] {image1_new.getName(),
							image2_new.getName(), image3_new.getName(),po.getFluxcard(),fromPartStr,order.getAccount(),areacode,order.getFromPart(),DateTool.getTimestamp(date),date.getTime(),po.getTdcodemd5()});
				//同时将sp_tdcode 的idcard_state 改成正在审核ing (idcard_state=B)
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"update sp_tdcode set idcard_state=? where tdcodemd5=?", new String[]{"B",po.getTdcodemd5()});
				
				
				obj.put("status", 1);
				obj.put("description", "上传成功，等待审核");
				msg.setData(obj);
				return msg;
			}else if("S".equals(fluxIdcard.getState())){
				setTdcodeError(obj,6, "身份验证通过，无需在验证 ");
				msg.setData(obj);
				return msg;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTdcodeError(obj, 0, "身份验证失败 ");
		msg.setData(obj);
		return msg;
	}
	
	public DataMsg queryIDCardState(Map<String, String> params){
		JSONObject obj = new JSONObject();
		String mobileNum =params.get("mobileNum");
		DataMsg msg = new DataMsg();
		JSONObject data = new JSONObject();
		if (StringUtils.isBlank(mobileNum)) {
			data.put("status", 2);
			data.put("description", "缺少手机号码");
			msg.setData(data);
			return msg;
		}
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		String authStr = MD5.getMD5((mobileNum + Env.SERVICE_KEY_APP + timestamp)
				.getBytes());
		if (!authstring.equals(authStr)) {
			
			data.put("status", 3);
			data.put("description", "验证字符错误");
			msg.setData(data);
			return msg;
		}
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		FLuxIdcardBus fluxIdcardBus = new FLuxIdcardBus();
		TFluxIdcard po = fluxIdcardBus.queryByMobileNum(jdbc, mobileNum);
		if(po==null){
			data.put("status", 4);
			data.put("description", "用户未提交实名信息");
			msg.setData(data);
			return msg;
		}
		TDCodeBus tdcodeBus = new TDCodeBus();
		SpTdcode tdcodePo = tdcodeBus.queryTDCodeByMD5(jdbc, po.getTdcodemd5());
		if(tdcodePo!=null){
			if(StringUtils.isBlank(tdcodePo.getIdcardState())){
				data.put("status", 4);
				data.put("description", "用户未提交实名信息");
				msg.setData(data);
				return msg;
			}else if("B".equals(tdcodePo.getIdcardState())){
				obj.put("status", 1);
				obj.put("description", "查询成功");
				obj.put("idcardState", tdcodePo.getIdcardState());
				obj.put("idcardResult", "身份信息正在实名中");
				obj.put("tdCode", tdcodePo.getTdcodemd5());
				obj.put("pn", tdcodePo.getBarcode());
				msg.setData(obj);
				return msg;
			}else if("S".equals(tdcodePo.getIdcardState())){
				obj.put("status", 1);
				obj.put("description", "查询成功");
				obj.put("idcardState", tdcodePo.getIdcardState());
				obj.put("idcardResult", "身份信息实名通过");
				obj.put("tdCode", tdcodePo.getTdcodemd5());
				obj.put("pn", tdcodePo.getBarcode());
				
				msg.setData(obj);
				return msg;
			}else if("F".equals(tdcodePo.getIdcardState())){
				obj.put("status", 1);
				obj.put("description", "查询成功");
				obj.put("idcardState", tdcodePo.getIdcardState());
				obj.put("idcardResult", "身份信息实名未通过，原因："+po.getResultDescribe());
				obj.put("tdCode", tdcodePo.getTdcodemd5());
				obj.put("pn", tdcodePo.getBarcode());
				msg.setData(obj);
				return msg;
			}else{
				setTdcodeError(obj, 4, "用户未提交实名信息");
				msg.setData(obj);
				return msg;
			}
		}else{
			setTdcodeError(obj, 4, "用户未提交实名信息");
			msg.setData(obj);
			return msg;
		}
	}

}
