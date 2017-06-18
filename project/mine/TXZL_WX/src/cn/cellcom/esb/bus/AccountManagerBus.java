package cn.cellcom.esb.bus;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.esb.po.Msg;
import cn.cellcom.esb.po.TChangeSetid;
import cn.cellcom.esb.po.TOperateCrmLog;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.RegisterBus;
import cn.cellcom.wechat.common.ConfLoad;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.DataMsg;

import cn.cellcom.wechat.po.TRegister;

public class AccountManagerBus {
	private static final Log log = LogFactory.getLog(AccountManagerBus.class);

	public DataMsg orderAccountByUser(Map<String, String> params,
			DataMsg dataMsg) {

		// 验证用户信息，存放用户套餐（null-代表订购）
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		Msg msg = checkRegister(params.get("number"), ESBEnv.ORDER,
				params.get("setid"),jdbc);
		// Msg msg =new Msg(true,"21");
		if (!msg.isFlag()) {
			dataMsg.setMsg(msg.getMsg());
			return dataMsg;
		} else {
			params.put("oldSetid", msg.getMsg());
		}
		PrintTool.printMap(params, true);
		// 无旧套餐就订购,否则就升级(根据oldSetid是否有值来判断)
		try {
			dataMsg = orderESB(params, dataMsg);
			return dataMsg;
		} catch (Exception e) {
			log.error("", e);
			return dataMsg;
		}
	}

	private DataMsg orderESB(Map<String, String> params, DataMsg dataMsg) {
		ESBClient client = new ESBClient();
		ESBBuildXML buildXML = new ESBBuildXML();
		String func014XML = buildXML.buildEsbFUNC014("FUNC014",
				params.get("number"), params.get("areacode"));
		Msg msg = func014Query(client, func014XML);
		if (msg.isFlag()) {
			// 如果是升级,再次查询FUNC014获取服务标识存于serviceFlag2
			if (StringUtils.isNotBlank(params.get("oldSetid"))) {
				params = putFunc014(msg.getMsg(), params, ESBEnv.QUERY);
				// 从params 中取出number1 然后作为号码再次调用func014查服务标识（2014021）
				String func014XMLTemp = buildXML.buildEsbFUNC014("FUNC014",
						params.get("number1"), params.get("areacode"));
				msg = func014Query(client, func014XMLTemp);
				if (msg.isFlag()) {
					String serviceFlag2 = StringUtils.substringBetween(
							msg.getMsg(),
							"param_id=\"2014021\" param_name=\"服务标识\">",
							"</col>");
					params.put("serviceFlag2", serviceFlag2);
				} else {
					dataMsg.setMsg(msg.getMsg());
					return dataMsg;
				}
			} else {
				params = putFunc014(msg.getMsg(), params, ESBEnv.ORDER);
			}
		} else {
			dataMsg.setMsg(msg.getMsg());
			return dataMsg;
		}
		String func015XML = buildXML.buildEsbFUNC015("FUNC015",
				params.get("number"), params.get("areacode"));
		msg = func015Query(client, func015XML);
		if (msg.isFlag()) {
			params = putFunc015(msg.getMsg(), params);
		} else {
			dataMsg.setMsg(msg.getMsg());
			return dataMsg;
		}
		String subs699XML = buildXML.buildEsbSUBS699Order("SUBS699", params);
		dataMsg = subs699(params, dataMsg, client, subs699XML, ESBEnv.ORDER);
		if (dataMsg.isState()) {
			params.put("operator", params.get("number"));
			operateLog(params);
			return dataMsg;
		} else {
			dataMsg.setMsg(dataMsg.getMsg());
			return dataMsg;
		}
	}

	public DataMsg accountCancel(Map<String, String> params, DataMsg dataMsg) {
		Msg msg = checkRegister(params.get("number"), ESBEnv.CANCEL, null,null);
		// Msg msg = new Msg(true,"22");
		if (!msg.isFlag()) {
			dataMsg.setMsg(msg.getMsg());
			return dataMsg;
		} else {
			params.put("setid", dataMsg.getMsg());
		}
		ESBClient client = new ESBClient();
		ESBBuildXML buildXML = new ESBBuildXML();
		String func014XML = buildXML.buildEsbFUNC014("FUNC014",
				params.get("number"), params.get("areacode"));
		msg = func014Query(client, func014XML);

		if (msg.isFlag()) {
			params = putFunc014(msg.getMsg(), params, ESBEnv.CANCEL);
		} else {
			dataMsg.setMsg(msg.getMsg());
			return dataMsg;
		}
		// 从params 中取出number1 然后作为号码再次调用func014查服务标识（2014021）
		String func014XMLTemp = buildXML.buildEsbFUNC014("FUNC014",
				params.get("number1"), params.get("areacode"));
		msg = func014Query(client, func014XMLTemp);
		if (msg.isFlag()) {
			String serviceFlag2 = StringUtils.substringBetween(msg.getMsg(),
					"param_id=\"2014021\" param_name=\"服务标识\">", "</col>");
			params.put("serviceFlag2", serviceFlag2);
		} else {
			dataMsg.setMsg(msg.getMsg());
			return dataMsg;
		}
		String func015XML = buildXML.buildEsbFUNC015("FUNC015",
				params.get("number"), params.get("areacode"));
		msg = func015Query(client, func015XML);
		if (msg.isFlag()) {
			params = putFunc015(msg.getMsg(), params);
		} else {
			dataMsg.setMsg(msg.getMsg());
			return dataMsg;
		}
		PrintTool.printMap(params, true);
		String subs699XML = buildXML.buildEsbSUBS699Cancel("SUBS699", params);
		dataMsg = subs699(params, dataMsg, client, subs699XML, ESBEnv.CANCEL);
		if (dataMsg.isState()) {
			params.put("operateType", "D");
			operateLog(params);
			return dataMsg;
		} else {
			dataMsg.setMsg(dataMsg.getMsg());
			return dataMsg;
		}

	}

	private Msg checkRegister(String number, String operateType, String setid,
			JDBC jdbc) {
		Msg msg = new Msg(true, null);
		TRegister po = new RegisterBus().queryByRegNo(jdbc, number,null);
		if (ESBEnv.ORDER.equals(operateType)) {
			if (po != null) {
				if ((po.getStatus().equals("Y") || po.getStatus().equals("B"))
						&& StringUtils.isNotBlank(po.getServNbr())) {
					Date date = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.set(Calendar.DAY_OF_MONTH, 1);
					TChangeSetid changeSetid = null;
					try {
						changeSetid = jdbc
								.queryForObject(
										ApplicationContextTool.getDataSource(),
										"select setid,update_time from t_change_setid  where number=? and product_type=? and update_time>?",
										TChangeSetid.class,
										new Object[] {
												number,
												"T",
												DateTool.formateTime2String(
														calendar.getTime(),
														"yyyy-MM-dd") });
					} catch (Exception e) {
						
						e.printStackTrace();
					} 
					
					if(changeSetid!=null){
						Date updateTime = changeSetid.getUpdateTime();
						if (po.getRegTime().before(updateTime)) {
							msg.setFlag(false);
							msg.setMsg("您本月已升级"
									+ Env.SETINFO.get(Long.valueOf(String.valueOf(changeSetid.getSetid())))
									+ ",下个月生效使用");
							return msg;
						}
					}
					
					if (String.valueOf(po.getSetid()).equals(setid)) {
						msg.setFlag(false);
						msg.setMsg("您订购的" + Env.SETINFO.get(setid)
								+ "已生效,请重新使用");
						return msg;
					} else {
						// 放入旧套餐,用来区分是订购还是升级
						if (UserUtil.isTXZLUser(po.getNumber(),po.getStatus(),po.getSetid(),po.getUsage(),po.getServNbr()))
							msg.setMsg(String.valueOf(po.getSetid()));
					}
				}
			}
		} else {
			if (po == null) {
				msg.setFlag(false);
				msg.setMsg("未订购个人通信助理");
			} else {
				if ((po.getStatus().equals("Y") || po.getStatus().equals("B"))
						&& StringUtils.isNotBlank(po.getServNbr())) {
					if (UserUtil.isTXZLUser(po.getNumber(),po.getStatus(),po.getSetid(),po.getUsage(),po.getServNbr())){
						msg.setFlag(false);
						msg.setMsg("未订购个人通信助理");
					} else {
						msg.setMsg(String.valueOf(po.getSetid()));
					}
				} else {
					msg.setFlag(false);
					msg.setMsg("非通信助理正式用户或已经退订通信助理业务");
				}
			}
		}
		return msg;
	}

	private DataMsg subs699(Map<String, String> params, DataMsg dataMsg,
			ESBClient client, String subs699XML, String operateType) {
		try {
			String subs699Result = client.callWebService(
					ConfLoad.getProperty("sysCode"),
					ConfLoad.getProperty("sysPwd"), "", subs699XML,
					ESBEnv.timeout);
			System.out.println(subs699Result);
			log.info(params.get("number") + " " + operateType + " result:"
					+ subs699Result);
			String resultcode = StringUtils.substringBetween(subs699Result,
					"<resultcode>", "</resultcode>");
			if ("0".equals(resultcode)) {
				dataMsg.setState(true);
				if (ESBEnv.ORDER.equals(operateType)) {
					if (StringUtils.isBlank(params.get("oldSetid")))
						dataMsg.setMsg("订购成功");
					else
						dataMsg.setMsg("升级成功,下个月生效使用");
				} else {
					dataMsg.setMsg("退订成功");
				}
				return dataMsg;
			} else {
				String failed = StringUtils.substringBetween(subs699Result,
						"<reason>", "</reason>");
				// 特性标识为7783已经存在,不能再做新增操作
				if (failed.indexOf("特性标识") == 0 && failed.indexOf("已经存在") > 0) {
					dataMsg.setMsg("您本月已升级"
							+ Env.SETINFO.get(params.get("setid")) + ",下个月生效使用");
				} else {
					dataMsg.setMsg(failed);
				}
			}
		} catch (Exception e) {
			log.error("", e);
			dataMsg.setMsg("订购信息异常");
		}
		return dataMsg;
	}

	private Msg func014Query(ESBClient client, String func014XML) {
		Msg msg = new Msg(false, "");
		try {
			String func014Result = client.callWebService(
					ConfLoad.getProperty("sysCode"),
					ConfLoad.getProperty("sysPwd"), "call", func014XML,
					ESBEnv.timeout);
			log.info(func014Result);
			msg.setFlag(true);
			msg.setMsg(func014Result);
			return msg;
		} catch (Exception e) {
			log.error("", e);
			msg.setMsg("查询号码信息异常");
		}
		return msg;
	}

	private Map<String, String> putFunc014(String func014Result,
			Map<String, String> params, String operateType) {

		if (func014Result == null)
			return params;
		String resultcode = StringUtils.substringBetween(func014Result,
				"<resultcode>", "</resultcode>");
		// 有0返回代表成功
		if ("0".equals(resultcode)) {
			// 取出相应值
			String serviceFlag = StringUtils.substringBetween(func014Result,
					"param_id=\"2014021\" param_name=\"服务标识\">", "</col>");
			params.put("serviceFlag", serviceFlag);
			String rentType = StringUtils.substringBetween(func014Result,
					"param_id=\"2014005\" param_name=\"月租类型\">", "</col>");
			params.put("rentType", rentType);
			String personType = StringUtils.substringBetween(func014Result,
					"param_id=\"2014006\" param_name=\"用户类型\">", "</col>");
			params.put("personType", personType);
			String billingAttr = StringUtils.substringBetween(func014Result,
					"param_id=\"2014007\" param_name=\"计费属性\">", "</col>");
			params.put("billingAttr", billingAttr);
			// 001-后付费，002-预付费(智能网平台),003-预付费(计费账务系统)
			String payType = StringUtils.substringBetween(func014Result,
					"param_id=\"2014013\" param_name=\"付费类型\">", "</col>");
			params.put("payType", payType);
			String fromArea = StringUtils.substringBetween(func014Result,
					"param_id=\"2014016\" param_name=\"局向\">", "</col>");
			params.put("fromArea", fromArea);
			String accountFlag = StringUtils.substringBetween(func014Result,
					"param_id=\"2014128\" param_name=\"帐户标识\">", "</col>");
			params.put("accountFlag", accountFlag);
			if (ESBEnv.CANCEL.equals(operateType)
					|| ESBEnv.QUERY.equals(operateType)) {
				// 18925008300获取号码(2014113)值CTXZL5154722，然后根据CTXZL5154722再次调用func014查询相关属性
				String temp = StringUtils.substringBetween(func014Result,
						"set_id=\"206\">", "</result>");
				params.put("number1", xmlElementBy2014113(temp));
				log.info("二次查询func014的number>>>>>>>>>>>>>"
						+ params.get("number1"));
			}

		}
		return params;
	}

	/**
	 * 获取func014里面206 里面 param_id =2014119 的值为3779或3780或3778或640(通信助理) 所对应
	 * param_id =2014113 的值
	 * 
	 * @param str
	 * @return
	 * @throws DocumentException
	 */
	private String xmlElementBy2014113(String str) {
		StringBuilder xml = new StringBuilder("<root>");
		xml.append(str).append("</root>");
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml.toString());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Element root = doc.getRootElement();
		List<Element> list = root
				.selectNodes("/root/row/col[@param_id=\"2014119\"]");
		String result = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element el = list.get(i);
				if ("3779".equals(el.getText()) || "3780".equals(el.getText())
						|| "3778".equals(el.getText())
						|| "640".equals(el.getText())) {
					result = el.getParent()
							.selectSingleNode("col[@param_id=\"2014113\"]")
							.getText();
					break;
				}
			}
		}
		return result;
	}

	private Msg func015Query(ESBClient client, String func015XML) {
		Msg msg = new Msg(false, "");
		try {
			String func015Result = client.callWebService(
					ConfLoad.getProperty("sysCode"),
					ConfLoad.getProperty("sysPwd"), "call", func015XML,
					ESBEnv.timeout);
			log.info(func015Result);
			msg.setFlag(true);
			msg.setMsg(func015Result);
		} catch (Exception e) {
			log.error("", e);
			msg.setMsg("查询用户信息异常");
		}
		return msg;
	}

	private Map<String, String> putFunc015(String func015Result,
			Map<String, String> params) {
		if (func015Result == null)
			return params;
		String resultcode = StringUtils.substringBetween(func015Result,
				"<resultcode>", "</resultcode>");
		if ("0".equals(resultcode)) {
			String personFlag = StringUtils.substringBetween(func015Result,
					"param_id=\"2015001\" param_name=\"客户标识\">", "</col>");
			params.put("personFlag", personFlag);
			String name = StringUtils.substringBetween(func015Result,
					"param_id=\"2015003\" param_name=\"客户名称\">", "</col>");
			params.put("name", name);
			String cardType = StringUtils.substringBetween(func015Result,
					"param_id=\"2015031\" param_name=\"证件类型\">", "</col>");
			params.put("cardType", cardType);
			String idcard = StringUtils.substringBetween(func015Result,
					"param_id=\"2015032\" param_name=\"证件号码\">", "</col>");
			params.put("idcard", idcard);
		}
		return params;
	}

	private void operateLog(Map<String, String> params) {
		TOperateCrmLog po = new TOperateCrmLog();
		if (StringUtils.isNotBlank(params.get("oldSetid"))) {
			params.put("operateType", "U");
			po.setOperateDesc(params.get("oldSetid") + "|"
					+ params.get("setid"));
		} else {
			po.setOperateDesc(params.get("setid"));
		}
		Date date = new Date();
		StringBuffer des = new StringBuffer();
		des.append("IP为").append(params.get("ip")).append("的号码")
				.append(params.get("number")).append("在");
		des.append(DateTool.formateTime2String(date, "yyyy-MM-dd HH:mm:ss"));
		des.append("通过CRM");
		des.append(ESBEnv.operateType.get(params.get("operateType")));
		des.append("了").append(Env.SETINFO.get(params.get("setid")));
		log.info(des.toString());
		po.setIp(params.get("ip"));
		// log.setOperateDesc("regNo:" + params.get("number") + ",areacode:"
		// + params.get("areacode") + ",setid:" + params.get("setid"));
		po.setOperateTime(date);
		po.setOperateType(params.get("operateType"));
		po.setOperator(params.get("operator"));
		po.setPlatform(params.get("platform"));
		po.setRegNo(params.get("number"));
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"insert into t_operate_crm_log(reg_no,operate_type,operator,platform,operate_time,operate_desc,ip) values(?,?,?,?,?,?,?)",
					new Object[] { po.getRegNo(), po.getOperateType(),
							po.getOperator(), po.getPlatform(),
							DateTool.getTimestamp(po.getOperateTime()),
							po.getOperateDesc(), po.getIp() });
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("写t_operate_crm_log异常", e);

		}

	}

	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		Map<String, String> params = new HashMap<String, String>();
		AccountManagerBus test = new AccountManagerBus();
		// String number = "18998931688";
		// String number = "18933929306";
		String number = "18925008300";
		String areacode = "020";
		String setid = "51";
		String password = "123456";
		params.put("number", number);
		params.put("areacode", areacode);
		params.put("setid", setid);
		params.put("password", password);
		params.put("platform", "WAP");
		DataMsg dataMsg = new DataMsg(false, "订购失败");
		dataMsg = test.orderAccountByUser(params, dataMsg);
		System.out.println(">>>>" + dataMsg.getMsg());
		// long b = System.currentTimeMillis();
		// System.out.println("订购耗时:"+(b-a)+"毫秒");

		// 根据号码查询业务号码，在根据业务号码查其属性（203）
		// ESBClient client = new ESBClient();
		// ESBBuildXML buildXML =new ESBBuildXML();
		// String func014XML = buildXML.buildEsbFUNC014("FUNC014",
		// "18925008300","020");
		// Msg msg = test.func014Query(client, func014XML);
		// String func014Result = msg.getMsg();
		// System.out.println(func014Result);
		// params = test.putFunc014(func014Result, params,ESBEnv.QUERY );
		// func014XML = buildXML.buildEsbFUNC014("FUNC014",
		// params.get("number1"));
		// func014Result = test.func014Query(client, func014XML);
		// String func015XML = buildXML.buildEsbFUNC015("FUNC015",
		// "18923594467","0660");
		// msg = test.func015Query(client, func015XML);
		// System.out.println(msg.getMsg());
		// dataMsg= test.accountCancel(params,dataMsg);
		// System.out.println(">>>>" + dataMsg.getMsg());

	}

}
