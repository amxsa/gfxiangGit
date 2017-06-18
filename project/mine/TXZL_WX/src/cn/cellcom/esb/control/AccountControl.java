package cn.cellcom.esb.control;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.data.PrintTool;

import cn.cellcom.esb.bus.AccountManagerBus;
import cn.cellcom.esb.bus.AccountManagerHelp;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;

@Controller
@RequestMapping("/user/account")
public class AccountControl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(AccountControl.class);

	@RequestMapping("/orderAccount")
	public String orderAccount(HttpServletRequest request,
			HttpServletResponse response) {
		String number = request.getParameter("number");
		String setid = request.getParameter("setid");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(password)) {
			password = Env.PASSWORD;
		}
		String yzm = request.getParameter("yzm");
		String platform = request.getParameter("platform");
		String operateType = request.getParameter("operateType");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("number", number);
		params.put("password", password);
		params.put("setid", setid);
		params.put("yzm", yzm);
		String areacode = UserUtil.getAreaCode(number);
		params.put("areacode", areacode);
		params.put("platform", platform);
		params.put("operateType", operateType);
		params.put("ip", request.getRemoteAddr());
		PrintTool.printLog(params);
		String checkResult = AccountManagerHelp.checkParamByUser(
				params.get("number"), params.get("password"),
				params.get("setid"), params.get("areacode"), params.get("yzm"),
				operateType);
		DataMsg dataMsg = new DataMsg(false, "订购失败");
		if (checkResult != null) {
			dataMsg.setMsg(dataMsg.getMsg() + "，原因：" + checkResult);
		} else {
			dataMsg = new AccountManagerBus().orderAccountByUser(params,
					dataMsg);
		}
		log.info(params.get("number") + "订购或升级个人通信助理结果(CRM)："
				+ dataMsg.getMsg());
		HttpSession session = request.getSession();
		if (dataMsg.isState()) {
			request.setAttribute("result", dataMsg.getMsg());
			Login login = (Login) session.getAttribute("login");
			if (login != null) {
				login.setSetid(Long.valueOf(setid));
			}
			return Env.SUCCESS;
		} else {
			request.setAttribute("result", dataMsg.getMsg());
			return Env.ERROR;
		}
	}
}
