package cn.cellcom.esb.bus;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.SequenceGenerator;
import cn.cellcom.wechat.common.ConfLoad;



public class ESBBuildXML {
	private static final Log logger = LogFactory.getLog(ESBBuildXML.class);

	/**
	 * ESB的区号是去掉区号前面的0,广州的是200
	 * 
	 * @param areacode
	 * @return
	 */
	public String getAreacode(String areacode) {
		if ("020".equals(areacode))
			return "200";
		else
			return StringUtils.substring(areacode, 1);
	}

	/**
	 * 请求时ESB接口的基本数据
	 * 
	 * @param funcCode
	 * @return
	 */
	public String buildBaseInfo(String funcCode, String areacode) {
		StringBuilder str = new StringBuilder();
		str.append("<BaseInfo>");
		str.append("<ReqCode>").append("GDTXZL").append(
				SequenceGenerator.generate()).append("</ReqCode>");
		str.append("<SysCode>").append(ConfLoad.getProperty("sysCode")).append(
				"</SysCode>");
		str.append("<SysPwd>").append(ConfLoad.getProperty("sysPwd")).append(
				"</SysPwd>");
		str.append("<SourceCode>1</SourceCode>");
		str.append("<TargetCode>").append(areacode).append("</TargetCode>");
		str.append("<FuncCode>").append(funcCode).append("</FuncCode>");
		str.append("<Sync>1</Sync><Version>3.0</Version>");
		str.append("<bak1></bak1><bak2></bak2>");
		str.append("</BaseInfo>");
		return str.toString();
	}

	/**
	 * 查询号码信息 或者查询号码的套餐信息(比如输入2014113的号码CTXZL5154722 ,就可以看到号码套餐的功能)
	 * 
	 * @param funcCode
	 * @param number
	 * @return
	 */

	public String buildEsbFUNC014(String funcCode, String number,
			String areacode) {
		areacode = getAreacode(areacode);
		StringBuilder str = new StringBuilder();
		str.append("<Business>");
		str.append(buildBaseInfo(funcCode, areacode));
		str.append("<SendData>");
		str.append("<inputdatas>");
		str.append("<controlinfo><staffid>").append(
				ConfLoad.getProperty("staffid")).append("</staffid><password>")
				.append(ConfLoad.getProperty("password")).append(
						"</password><posttype>").append(areacode).append(
						"</posttype></controlinfo>");
		str.append("<params sets=\"2\">");
		str.append("<param rows=\"1\" cols=\"3\" set_id=\"101\">");
		str.append("<row rownum=\"1\">");
		str
				.append("<col colnum=\"1\" param_id=\"1014001\" param_name=\"1014001\">1</col>");
		str
				.append("<col colnum=\"2\" param_id=\"1014002\" param_name=\"1014002\">1</col>");
		str
				.append(
						"<col colnum=\"3\" param_id=\"1014003\" param_name=\"1014003\">")
				.append(number).append("</col>");
		str.append("</row></param>");
		str.append("<param rows=\"4\" cols=\"3\" set_id=\"102\">");
		str.append("<row rownum=\"1\">");
		str
				.append("<col colnum=\"1\" param_id=\"1014011\" param_name=\"结果集号\">201</col>");
		str.append("</row>");
		str.append("<row rownum=\"2\">");
		str
				.append("<col colnum=\"1\" param_id=\"1014011\" param_name=\"结果集号\">203</col>");
		str.append("</row>");
		str.append("<row rownum=\"3\">");
		str
				.append("<col colnum=\"1\" param_id=\"1014011\" param_name=\"结果集号\">207</col>");
		str.append("</row>");
		str.append("<row rownum=\"4\">");
		str
				.append("<col colnum=\"1\" param_id=\"1014011\" param_name=\"结果集号\">206</col>");
		str.append("</row>");
		str.append("</param>");
		str.append("</params>");
		str.append("</inputdatas></SendData>");
		str.append("</Business>");
		
		return str.toString();
	}

	/**
	 * 查询用户信息
	 * 
	 * @param funcCode
	 * @param number
	 * @return
	 */
	public String buildEsbFUNC015(String funcCode, String number,
			String areacode) {
		areacode = getAreacode(areacode);
		StringBuilder str = new StringBuilder();
		str.append("<Business>");
		str.append(buildBaseInfo(funcCode, areacode));
		str.append("<SendData>");
		str.append("<inputdatas>");
		str.append("<controlinfo><staffid>").append(
				ConfLoad.getProperty("staffid")).append("</staffid><password>")
				.append(ConfLoad.getProperty("password")).append(
						"</password><posttype>").append(areacode).append(
						"</posttype></controlinfo>");
		str.append("<params sets=\"2\">");
		str.append("<param rows=\"1\" cols=\"2\" set_id=\"101\">");
		str.append("<row rownum=\"1\">");
		str
				.append("<col colnum=\"1\" param_id=\"1015001\" param_name=\"1014001\">2</col>");
		str
				.append(
						"<col colnum=\"2\" param_id=\"1015002\" param_name=\"1014002\">")
				.append(number).append("</col>");
		str.append("</row></param>");
		str.append("<param rows=\"2\" cols=\"1\" set_id=\"102\">");
		str.append("<row rownum=\"1\">");
		str
				.append("<col colnum=\"1\" param_id=\"1015011\" param_name=\"结果集号\">201</col>");
		str.append("</row>");
		str.append("<row rownum=\"2\">");
		str
				.append("<col colnum=\"1\" param_id=\"1015011\" param_name=\"结果集号\">202</col>");
		str.append("</row>");
		str.append("</param>");
		str.append("</params>");
		str.append("</inputdatas></SendData>");
		str.append("</Business>");
		return str.toString();
	}

	public String buildEsbSUBS699Order(String funcCode,
			Map<String, String> params) {
		String areacode = getAreacode(params.get("areacode"));
		StringBuilder str = new StringBuilder();
		str.append("<Business>");
		str.append(buildBaseInfo(funcCode, areacode));
		str.append("<SendData>");
		str.append("<inputdatas>");
		str.append("<controlinfo><staffid>").append(
				ConfLoad.getProperty("staffid")).append("</staffid><password>")
				.append(ConfLoad.getProperty("password")).append("</password>");
		str.append("<posttype>").append(areacode).append(
				"</posttype></controlinfo>");
		str.append("<params sets=\"10\">");
		str.append("<param rows=\"1\" cols=\"8\" set_id=\"101\">");
		str.append("<row rownum=\"1\">");
		str
				.append("<col colnum=\"1\" param_id=\"1699001\" param_name=\"1699001\">1</col>");
		str
				.append("<col colnum=\"2\" param_id=\"1699002\" param_name=\"1699002\"/>");
		str
				.append("<col colnum=\"3\" param_id=\"1699003\" param_name=\"1699003\"/>");
		str
				.append("<col colnum=\"4\" param_id=\"1699004\" param_name=\"1699004\">1</col>");
		str
				.append("<col colnum=\"5\" param_id=\"1699005\" param_name=\"1699005\">");
		str.append(getAreacode(params.get("areacode"))).append("</col>");
		str
				.append("<col colnum=\"6\" param_id=\"1699006\" param_name=\"1699006\">");
		str.append(ConfLoad.getProperty("staffid")).append("</col>");

		str
				.append("<col colnum=\"7\" param_id=\"1699007\" param_name=\"1699007\">000</col>");
		str
				.append("<col colnum=\"8\" param_id=\"1699008\" param_name=\"1699008\">1</col>");
		str.append("</row></param>");

		str.append("<param rows=\"1\" cols=\"10\" set_id=\"102\">");
		str.append("<row rownum=\"1\">");
		str
				.append(
						"<col colnum=\"1\" param_id=\"1699011\" param_name=\"1699011\">")
				.append(getAreacode(params.get("areacode"))).append("</col>");
		str
				.append(
						"<col colnum=\"2\" param_id=\"1699012\" param_name=\"1699012\">")
				.append(params.get("name")).append("</col>");
		str
				.append(
						"<col colnum=\"3\" param_id=\"1699013\" param_name=\"1699013\">")
				.append(params.get("number")).append("</col>");
		str.append(
				"<col colnum=\"4\" param_id=\"1699014\" param_name=\"客户标识\">")
				.append(params.get("personFlag")).append("</col>");
		str
				.append("<col colnum=\"5\" param_id=\"1699015\" param_name=\"1699015\"/>");
		str
				.append("<col colnum=\"6\" param_id=\"1699016\" param_name=\"1699016\">000</col>");
		str
				.append("<col colnum=\"7\" param_id=\"1699017\" param_name=\"1699017\">000</col>");
		str
				.append("<col colnum=\"8\" param_id=\"1699018\" param_name=\"1699018\"/>");
		str
				.append("<col colnum=\"9\" param_id=\"1699019\" param_name=\"1699019\"/>");
		str
				.append("<col colnum=\"10\" param_id=\"1699020\" param_name=\"1699020\"/>");
		str.append("</row></param>");

		str.append(" <param rows=\"1\" cols=\"6\" set_id=\"103\">");
		str.append("<row rownum=\"1\">");
		// 业务标识指付费类型，订购（2236:后付费，2245：预付费），升级（2237:后付费，2246：预付费）
		// ,付费类型从func014的2014013获取
		str
				.append("<col colnum=\"1\" param_id=\"1699031\" param_name=\"业务标识\">");
		if (StringUtils.isNotBlank(params.get("oldSetid"))) {
			str.append(params.get("payType").equals("001") ? "2237" : "2246");
		} else {
			str.append(params.get("payType").equals("001") ? "2236" : "2245");
		}
		str.append("</col>");
		str
				.append(
						"<col colnum=\"2\" param_id=\"1699032\" param_name=\"1699032\">")
				.append(params.get("name")).append("</col>");
		str
				.append(
						"<col colnum=\"3\" param_id=\"1699033\" param_name=\"1699033\">")
				.append(params.get("cardType")).append("</col>");
		str
				.append(
						"<col colnum=\"4\" param_id=\"1699034\" param_name=\"1699034\">")
				.append(params.get("idcard")).append("</col>");
		str
				.append("<col colnum=\"5\" param_id=\"1699035\" param_name=\"1699035\">1</col>");
		str
				.append("<col colnum=\"6\" param_id=\"1699036\" param_name=\"1699036\">CCRT</col>");
		str.append("</row></param>");
		str.append("<param rows=\"1\" cols=\"20\" set_id=\"108\">");
		str.append("<row rownum=\"1\">");
		str.append(
				"<col colnum=\"1\" param_id=\"1699121\" param_name=\"客户标识\">")
				.append(params.get("personFlag")).append("</col>");
		// 产品标识不同，3779:后付费，3780：预付费
		str
				.append("<col colnum=\"2\" param_id=\"1699122\" param_name=\"产品标识\">");
		str.append(params.get("payType").equals("001") ? "3779" : "3780");
		str.append("</col>");

		str
				.append("<col colnum=\"3\" param_id=\"1699123\" param_name=\"服务标识\">");
		if (StringUtils.isBlank(params.get("oldSetid"))) {
			str.append("-1");
		}else{
			str.append(params.get("serviceFlag2"));
		}
		str.append("</col>");
		str.append(
				"<col colnum=\"4\" param_id=\"1699124\" param_name=\"所属局向\">")
				.append(params.get("fromArea")).append("</col>");
		str.append(
				"<col colnum=\"5\" param_id=\"1699125\" param_name=\"月租类型\">")
				.append(params.get("rentType")).append("</col>");
		str.append(
				"<col colnum=\"6\" param_id=\"1699126\" param_name=\"用户类型\">")
				.append(params.get("personType")).append("</col>");
		str.append(
				"<col colnum=\"7\" param_id=\"1699127\" param_name=\"计费属性\">")
				.append(params.get("billingAttr")).append("</col>");
		str.append(
				"<col colnum=\"8\" param_id=\"1699128\" param_name=\"服务密码\">")
				.append(StringUtils.isNotBlank(params.get("oldSetid"))?"":params.get("password")).append("</col>");
		str
				.append("<col colnum=\"9\" param_id=\"1699129\" param_name=\"1699129\"/>");
		str.append(
				"<col colnum=\"10\" param_id=\"1699130\" param_name=\"付费类型\">")
				.append(params.get("payType")).append("</col>");

		str
				.append("<col colnum=\"11\" param_id=\"1699131\" param_name=\"1699131\"/>");
		str
				.append("<col colnum=\"12\" param_id=\"1699132\" param_name=\"1699132\"/>");
		str
				.append("<col colnum=\"13\" param_id=\"1699133\" param_name=\"1699133\"/>");
		str
				.append("<col colnum=\"14\" param_id=\"1699134\" param_name=\"1699134\"/>");
		str
				.append("<col colnum=\"15\" param_id=\"1699135\" param_name=\"1699135\"/>");
		str
				.append("<col colnum=\"16\" param_id=\"1699136\" param_name=\"1699136\"/>");
		str
				.append("<col colnum=\"17\" param_id=\"1699137\" param_name=\"1699137\"/>");
		str
				.append("<col colnum=\"18\" param_id=\"1699138\" param_name=\"占号标志\"/>");
		if(StringUtils.isBlank(params.get("oldSetid"))){
			str
			.append("<col colnum=\"19\" param_id=\"1699139\" param_name=\"1699139\">001</col>");
			str
			.append("<col colnum=\"20\" param_id=\"1699140\" param_name=\"号码生成标志\">1</col>");
		}else{
			str
			.append("<col colnum=\"19\" param_id=\"1699139\" param_name=\"1699139\">000</col>");
			str
			.append("<col colnum=\"20\" param_id=\"1699140\" param_name=\"号码生成标志\"></col>");
		}
		str.append("</row></param>");
		str.append(buildBusData(params));
		if (StringUtils.isBlank(params.get("oldSetid"))) {
			// 多媒体账号
			str.append("<param rows=\"1\" cols=\"8\" set_id=\"117\">");
			str.append("<row rownum=\"1\">");
			str
					.append("<col colnum=\"1\" param_id=\"1699291\" param_name=\"帐号标识\">-1</col>");
			str
					.append(
							"<col colnum=\"2\" param_id=\"1699292\" param_name=\"帐号\">")
					.append(params.get("number")).append("</col>");

			str
					.append(
							"<col colnum=\"3\" param_id=\"1699293\" param_name=\"帐号密码\">")
					.append(params.get("password")).append("</col>");
			str
					.append("<col colnum=\"4\" param_id=\"1699294\" param_name=\"帐号来源\">A0N</col>");
			str
					.append("<col colnum=\"5\" param_id=\"1699295\" param_name=\"服务级别\">S0M</col>");
			str
					.append("<col colnum=\"6\" param_id=\"1699296\" param_name=\"数据操作方式\">001</col>");
			str
					.append("<col colnum=\"7\" param_id=\"1699297\" param_name=\"账号密码加密标志\">0</col>");
			str
					.append("<col colnum=\"8\" param_id=\"1699298\" param_name=\"帐号平台类型\">PLTM_BSTONE</col>");
			str.append("</row></param>");

			str.append("<param rows=\"1\" cols=\"4\" set_id=\"119\">");
			str.append("<row rownum=\"1\">");
			str
					.append(
							"<col colnum=\"1\" param_id=\"1699331\" param_name=\"关系服务标识\">")
					.append(params.get("serviceFlag")).append("</col>");
			str
					.append("<col colnum=\"2\" param_id=\"1699332\" param_name=\"关系类型\">001</col>");
			str
					.append("<col colnum=\"3\" param_id=\"1699333\" param_name=\"数据操作方式\">001</col>");
			str
					.append("<col colnum=\"4\" param_id=\"1699334\" param_name=\"关系原因\">03</col>");
			str.append("</row></param>");

			str.append("<param rows=\"1\" cols=\"10\" set_id=\"123\">");
			str.append("<row rownum=\"1\">");
			str
					.append("<col colnum=\"1\" param_id=\"1699401\" param_name=\"付费类别\">0</col>");
			str
					.append(
							"<col colnum=\"2\" param_id=\"1699402\" param_name=\"账户标识\">")
					.append(params.get("accountFlag")).append("</col>");
			str
					.append("<col colnum=\"3\" param_id=\"1699403\" param_name=\"账目类型组\"/>");
			str
					.append("<col colnum=\"4\" param_id=\"1699404\" param_name=\"代付费服务标识\"/>");
			str
					.append("<col colnum=\"5\" param_id=\"1699405\" param_name=\"代付费服务号码\"/>");
			str
					.append("<col colnum=\"6\" param_id=\"1699406\" param_name=\"缴费模式\"/>");
			str
					.append("<col colnum=\"7\" param_id=\"1699407\" param_name=\"缴费额度\"/>");
			str
					.append("<col colnum=\"8\" param_id=\"1699408\" param_name=\"数据操作方式\">001</col>");
			str
					.append("<col colnum=\"9\" param_id=\"1699409\" param_name=\"收费局\"/>");
			str
					.append("<col colnum=\"10\" param_id=\"1699410\" param_name=\"付费关系标识\"/>");
			str.append("</row></param>");

			str.append("<param rows=\"1\" cols=\"9\" set_id=\"112\">");
			str.append("<row rownum=\"1\">");
			str
					.append("<col colnum=\"1\" param_id=\"1699201\" param_name=\"发票标识\">-1</col>");
			str
					.append("<col colnum=\"2\" param_id=\"1699202\" param_name=\"发票名称使用类型\">001</col>");
			str
					.append("<col colnum=\"3\" param_id=\"1699203\" param_name=\"发票格式\">0</col>");
			str
					.append("<col colnum=\"4\" param_id=\"1699204\" param_name=\"投寄方式\">000000</col>");
			str
					.append("<col colnum=\"5\" param_id=\"1699205\" param_name=\"地址标识\"/>");
			str
					.append("<col colnum=\"6\" param_id=\"1699206\" param_name=\"投寄顺序\"/>");
			str
					.append("<col colnum=\"7\" param_id=\"1699207\" param_name=\"缴费前出发票标志\"/>");
			str
					.append("<col colnum=\"8\" param_id=\"1699208\" param_name=\"代表号码标识\"/>");
			str
					.append("<col colnum=\"9\" param_id=\"1699209\" param_name=\"数据操作方式\">001</col>");
			str.append("</row></param>");

			str.append("<param rows=\"1\" cols=\"7\" set_id=\"113\">");
			str.append("<row rownum=\"1\">");
			str
					.append("<col colnum=\"1\" param_id=\"1699221\" param_name=\"账单标识\">-1</col>");
			str
					.append("<col colnum=\"2\" param_id=\"1699222\" param_name=\"账单格式\"/>");
			str
					.append("<col colnum=\"3\" param_id=\"1699223\" param_name=\"投寄方式\">000000</col>");
			str
					.append("<col colnum=\"4\" param_id=\"1699224\" param_name=\"地址标识\"/>");
			str
					.append("<col colnum=\"5\" param_id=\"1699225\" param_name=\"投寄顺序\"/>");
			str
					.append("<col colnum=\"6\" param_id=\"1699226\" param_name=\"数据操作方式\">001</col>");
			str.append("</row></param>");
		}

		str.append("</params>");
		str.append("</inputdatas>");
		str.append("</SendData>");
		str.append("</Business>");
		logger.info(params.get("number") + "订购XML>>>>>>>" + str.toString());
		return str.toString();
	}

	/**
	 * 构建业务数据块
	 * 
	 * @param params
	 * @return
	 */
	private String buildBusData(Map<String, String> params) {
		StringBuffer str = new StringBuffer();
		String oldSetid = params.get("oldSetid");
		if (StringUtils.isBlank(oldSetid)) {
			str.append("<param rows=\"").append(
					ESBEnv.model.get(params.get("setid"))).append(
					"\" cols=\"3\" set_id=\"110\">");
			if ("51".equals(params.get("setid"))) {
				str.append(ESBEnv.BUS_DATA_ORDER_LH);
			} else if ("21".equals(params.get("setid"))) {
				str.append(ESBEnv.BUS_DATA_ORDER_LH).append(
						ESBEnv.BUS_DATA_ORDER_JC);
			} else if ("22".equals(params.get("setid"))) {
				str.append(ESBEnv.BUS_DATA_ORDER_LH).append(
						ESBEnv.BUS_DATA_ORDER_JC).append(
						ESBEnv.BUS_DATA_ORDER_XX);
			}
			str.append("</param>");
		} else {
			if ("51".equals(oldSetid)) {
				str.append("<param rows=\"").append(
						ESBEnv.model.get(params.get("setid")) - 1).append(
						"\" cols=\"3\" set_id=\"110\">");
				if ("21".equals(params.get("setid"))) {
					str
							.append("<row rownum=\"1\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"号簿管理\">7777</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"2\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"语音拨号\">7778</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"3\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"改号通知\">7779</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"4\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"日程提醒\">7780</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"5\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"通讯录同步\">7783</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row>");

				} else if ("22".equals(params.get("setid"))) {
					str
							.append("<row rownum=\"1\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"号簿管理\">7777</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"2\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"语音拨号\">7778</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"3\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"改号通知\">7779</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"4\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"日程提醒\">7780</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row><row rownum=\"5\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"通讯录同步\">7783</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row>");
					str
							.append("<row rownum=\"6\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"人工秘书\">7781</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row>");

				}
				str.append("</param>");
			} else if ("21".equals(oldSetid)) {
				if("22".equals(params.get("setid"))){
					str.append("<param rows=\"1\" cols=\"3\" set_id=\"110\">");
					str
							.append("<row rownum=\"1\"><col colnum=\"1\" param_id=\"1699181\" param_name=\"人工秘书\">7781</col><col colnum=\"2\" param_id=\"1699182\" param_name=\"1699182\"/><col colnum=\"3\" param_id=\"1699183\" param_name=\"1699183\">001</col></row>");
					str.append("</param>");
				}
			}
		}
		return str.toString();
	}

	/**
	 * 
	 * @param reqCode
	 * @param sysCode
	 * @param sysPwd
	 * @param funcCode
	 * @param staffid
	 * @param password
	 * @param params
	 * @return
	 */
	public String buildEsbSUBS699Cancel(String funcCode,
			Map<String, String> params) {
		String areacode = getAreacode(params.get("areacode"));
		StringBuilder str = new StringBuilder();
		str.append("<Business>");
		str.append(buildBaseInfo(funcCode, areacode));
		str.append("<SendData>");
		str.append("<inputdatas>");
		str.append("<controlinfo><staffid>").append(
				ConfLoad.getProperty("staffid")).append("</staffid><password>")
				.append(ConfLoad.getProperty("password")).append("</password>");
		str.append("<posttype>").append(areacode).append(
				"</posttype></controlinfo>");
		str.append("<params sets=\"4\">");
		str.append("<param rows=\"1\" cols=\"8\" set_id=\"101\">");
		str.append("<row rownum=\"1\">");
		str
				.append("<col colnum=\"1\" param_id=\"1699001\" param_name=\"1699001\">1</col>");
		str
				.append("<col colnum=\"2\" param_id=\"1699002\" param_name=\"1699002\"/>");
		str
				.append("<col colnum=\"3\" param_id=\"1699003\" param_name=\"1699003\"/>");
		str
				.append("<col colnum=\"4\" param_id=\"1699004\" param_name=\"1699004\">1</col>");
		str
				.append("<col colnum=\"5\" param_id=\"1699005\" param_name=\"1699005\">");
		str.append(getAreacode(params.get("areacode"))).append("</col>");
		str
				.append("<col colnum=\"6\" param_id=\"1699006\" param_name=\"1699006\">");
		str.append(ConfLoad.getProperty("staffid")).append("</col>");
		str
				.append("<col colnum=\"7\" param_id=\"1699007\" param_name=\"1699007\">000</col>");
		str
				.append("<col colnum=\"8\" param_id=\"1699008\" param_name=\"1699008\">1</col>");
		str.append("</row></param>");

		str.append("<param rows=\"1\" cols=\"10\" set_id=\"102\">");
		str.append("<row rownum=\"1\">");
		str
				.append(
						"<col colnum=\"1\" param_id=\"1699011\" param_name=\"1699011\">")
				.append(getAreacode(params.get("areacode"))).append("</col>");
		str
				.append(
						"<col colnum=\"2\" param_id=\"1699012\" param_name=\"1699012\">")
				.append(params.get("name")).append("</col>");
		str
				.append(
						"<col colnum=\"3\" param_id=\"1699013\" param_name=\"1699013\">")
				.append(params.get("number")).append("</col>");
		str.append(
				"<col colnum=\"4\" param_id=\"1699014\" param_name=\"客户标识\">")
				.append(params.get("personFlag")).append("</col>");
		str
				.append("<col colnum=\"5\" param_id=\"1699015\" param_name=\"1699015\"/>");
		str
				.append("<col colnum=\"6\" param_id=\"1699016\" param_name=\"1699016\">000</col>");
		str
				.append("<col colnum=\"7\" param_id=\"1699017\" param_name=\"1699017\">000</col>");
		str
				.append("<col colnum=\"8\" param_id=\"1699018\" param_name=\"1699018\"/>");
		str
				.append("<col colnum=\"9\" param_id=\"1699019\" param_name=\"1699019\"/>");
		str
				.append("<col colnum=\"10\" param_id=\"1699020\" param_name=\"1699020\"/>");
		str.append("</row></param>");

		str.append(" <param rows=\"1\" cols=\"6\" set_id=\"103\">");
		str.append("<row rownum=\"1\">");
		// 业务标识指付费类型，2238:后付费，2247：预付费 ,付费类型从func014的2014013获取
		str
				.append("<col colnum=\"1\" param_id=\"1699031\" param_name=\"业务标识\">");
		str.append(params.get("payType").equals("001") ? "2238" : "2247");
		str.append("</col>");
		str
				.append(
						"<col colnum=\"2\" param_id=\"1699032\" param_name=\"1699032\">")
				.append(params.get("name")).append("</col>");
		str
				.append(
						"<col colnum=\"3\" param_id=\"1699033\" param_name=\"1699033\">")
				.append(params.get("cardType")).append("</col>");
		str
				.append(
						"<col colnum=\"4\" param_id=\"1699034\" param_name=\"1699034\">")
				.append(params.get("idcard")).append("</col>");
		str
				.append("<col colnum=\"5\" param_id=\"1699035\" param_name=\"1699035\">1</col>");
		str
				.append("<col colnum=\"6\" param_id=\"1699036\" param_name=\"1699036\">CCRT</col>");
		str.append("</row></param>");

		str.append("<param rows=\"1\" cols=\"20\" set_id=\"108\">");
		str.append("<row rownum=\"1\">");
		str.append(
				"<col colnum=\"1\" param_id=\"1699121\" param_name=\"客户标识\">")
				.append(params.get("personFlag")).append("</col>");
		// 产品标识不同，3779:后付费，3780：预付费
		str
				.append("<col colnum=\"2\" param_id=\"1699122\" param_name=\"产品标识\">");
		str.append(params.get("payType").equals("001") ? "3779" : "3780");
		str.append("</col>");
		str.append(
				"<col colnum=\"3\" param_id=\"1699123\" param_name=\"服务标识\">")
				.append(params.get("serviceFlag2")).append("</col>");
		str.append(
				"<col colnum=\"4\" param_id=\"1699124\" param_name=\"所属局向\">")
				.append(params.get("fromArea")).append("</col>");
		str.append(
				"<col colnum=\"5\" param_id=\"1699125\" param_name=\"月租类型\">")
				.append(params.get("rentType")).append("</col>");
		str.append(
				"<col colnum=\"6\" param_id=\"1699126\" param_name=\"用户类型\">")
				.append(params.get("personType")).append("</col>");
		str.append(
				"<col colnum=\"7\" param_id=\"1699127\" param_name=\"计费属性\">")
				.append(params.get("billingAttr")).append("</col>");
		str
				.append("<col colnum=\"8\" param_id=\"1699128\" param_name=\"服务密码\"/>");

		str
				.append("<col colnum=\"9\" param_id=\"1699129\" param_name=\"1699129\"/>");
		str.append(
				"<col colnum=\"10\" param_id=\"1699130\" param_name=\"付费类型\">")
				.append(params.get("payType")).append("</col>");
		str
				.append("<col colnum=\"11\" param_id=\"1699131\" param_name=\"1699131\"/>");
		str
				.append("<col colnum=\"12\" param_id=\"1699132\" param_name=\"1699132\"/>");
		str
				.append("<col colnum=\"13\" param_id=\"1699133\" param_name=\"1699133\"/>");
		str
				.append("<col colnum=\"14\" param_id=\"1699134\" param_name=\"1699134\"/>");
		str
				.append("<col colnum=\"15\" param_id=\"1699135\" param_name=\"1699135\"/>");
		str
				.append("<col colnum=\"16\" param_id=\"1699136\" param_name=\"1699136\"/>");
		str
				.append("<col colnum=\"17\" param_id=\"1699137\" param_name=\"1699137\"/>");
		str
				.append("<col colnum=\"18\" param_id=\"1699138\" param_name=\"占号标志\"/>");
		str
				.append("<col colnum=\"19\" param_id=\"1699139\" param_name=\"1699139\">000</col>");
		str
				.append("<col colnum=\"20\" param_id=\"1699140\" param_name=\"号码生成标志\"/>");
		str.append("</row></param>");
		str.append("</params>");
		str.append("</inputdatas>");
		str.append("</SendData>");
		str.append("</Business>");
		return str.toString();
	}

	public static void main(String[] args) {

	}
}
