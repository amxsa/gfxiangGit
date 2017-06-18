package cn.cellcom.wechat.control;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.esb.bus.ESBEnv;
import cn.cellcom.wechat.bus.SmsBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.po.DataMsg;
@Controller
@RequestMapping("/user/sms")
public class SmsControl {
	@RequestMapping("/sendSms")
	public String sendSms(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String dest = request.getParameter("number");
		if (StringUtils.isBlank(dest)) {
			DataMsg dataMsg = new DataMsg(false,"手机号码不能为空");
			PrintTool.print(response, JSONObject.fromObject(dataMsg), "json");
			return null;
		}
		String error = PatternTool.checkStr(dest, Env.C_PATTERN, "手机号码格式错误");
		if (error != null) {
			DataMsg dataMsg = new DataMsg(false,"手机号码格式错误");
			PrintTool.print(response, JSONObject.fromObject(dataMsg), "json");
			return null;
		}
		Date date = new Date();
		Date sendDate = Env.WECHAT_YZM.get(dest);
		if (sendDate != null) {
			Date dateTemp = DateTool.diff(sendDate, "MINUTE", 3);
			if (dateTemp.after(date)) {
				DataMsg dataMsg = new DataMsg(false,"验证码的间隔时间小于3分钟");
				PrintTool.print(response, JSONObject.fromObject(dataMsg), "json");
				return null;
			} else {
				Env.WECHAT_YZM.remove(dest);
				Env.WECHAT_YZM.put(dest, date);
			}
		} else {
			Env.WECHAT_YZM.put(dest, date);
		}
		String smsCode = String.valueOf(new Random().nextDouble()).substring(3,
				9);
		StringBuilder msgContent = new StringBuilder("通信助理微信验证码：");
		msgContent.append(smsCode).append("，请勿将验证码告知他人。");
		SmsBus smsBus = new SmsBus();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("regNo", "1065911468");
		params.put("dest", dest);
		params.put("model", "微信绑定手机验证");
		params.put("msgContent", msgContent.toString());
		params.put("fromPart", "WX");
		DataMsg msg = smsBus.sendSms(params);
		HttpSession session = request.getSession();
		if (msg.isState()) {
			session.setAttribute("smsWechatCode", smsCode);
		}
		PrintTool.print(response, JSONObject.fromObject(msg), "json");
		return null;
	}

	@RequestMapping("/sendOrderSms")
	public String sendOrderSms(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String dest = request.getParameter("number");
		if (StringUtils.isBlank(dest)) {
			DataMsg dataMsg = new DataMsg(false,"手机号码不能为空");
			PrintTool.print(response, JSONObject.fromObject(dataMsg), "json");
			return null;
		}
		String error = PatternTool.checkStr(dest, Env.C_PATTERN, "手机号码格式错误");
		if (error != null) {
			DataMsg dataMsg = new DataMsg(false,"手机号码格式错误");
			PrintTool.print(response, JSONObject.fromObject(dataMsg), "json");
			return null;
		}
		Date date = new Date();
		Object[] arr = ESBEnv.SMSCODE.get(dest);
		if (arr != null) {
			Date sendDate = (Date) arr[1];
			Date dateTemp = DateTool.diff(sendDate, "MINUTE", 3);
			if (dateTemp.after(date)) {
				DataMsg dataMsg = new DataMsg(false,"验证码的间隔时间小于3分钟");
				PrintTool.print(response, JSONObject.fromObject(dataMsg), "json");
			}
		}

		String smsCode = String.valueOf(new Random().nextDouble()).substring(3,
				9);
		StringBuilder msgContent = new StringBuilder("通信助理微信验证码：");
		msgContent.append(smsCode).append("，请勿将验证码告知他人。");
		SmsBus smsBus = new SmsBus();
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("regNo", "1065911468");
		params.put("dest", dest);
		params.put("model", "微信开通或升级通信助理验证");
		params.put("msgContent", msgContent.toString());
		params.put("fromPart", "WX");
		DataMsg msg = smsBus.sendSms(params);

		if (msg.isState()) {
			ESBEnv.SMSCODE.remove(dest);
			ESBEnv.SMSCODE.put(dest, new Object[] { smsCode, date });
			PrintTool.print(response, JSONObject.fromObject(msg), "json");
		}

		return null;
	}
}
