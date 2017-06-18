package cn.cellcom.wechat.control;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.CallforwardBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TCallforward;

@Controller
@RequestMapping("/user/callForward")
public class CallForwardControl {
	@RequestMapping("/showCallForward")
	public String showCallForward(HttpServletRequest request,
			HttpServletResponse response) {
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		if (UserUtil.isJCOrXXUser(login.getNumber(), login.getSetid(),
				login.getUsage(), login.getServNbr())) {
			StringBuffer sql = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sql.append("select * from t_callforward where reg_no = ? ");
			params.add(login.getNumber());
			sql.append("  and (operate_type!='C' or (handle_status!='Y' and operate_type='C')) and cf_type!='G' order by submit_time desc ");
			try {
				List<TCallforward> list = jdbc.query(
						ApplicationContextTool.getDataSource(), sql.toString(),
						TCallforward.class, params.toArray(), 0, 0);
				if (list != null && list.size() > 0) {
					request.setAttribute("list", list);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("result", "亲，您目前是漏话提醒用户，无法享受呼转功能，赶紧升级套餐吧");
			return Env.ERROR;
		}
		return "/user/showCallForward";
	}

	@RequestMapping("/setCFDConfig")
	public String setCFDConfig(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		String cfNumber = request.getParameter("number");
		if (StringUtils.isBlank(type) || StringUtils.isBlank(cfNumber)) {
			request.setAttribute("result", "请填写呼转的号码");
			return Env.ERROR;
		}
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		if (UserUtil.isJCOrXXUser(login.getNumber(), login.getSetid(),
				login.getUsage(), login.getServNbr())) {
			String error = PatternTool.checkStr(cfNumber, "^\\d{10,13}$",
					"呼叫转移号码格式错误");
			if (error != null) {
				request.setAttribute("result", error);
				return Env.ERROR;
			}
			if (cfNumber.startsWith("00")) {
				request.setAttribute("result", "呼叫转移号码只允许为国内号码");
				return Env.ERROR;
			}
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("type", type);
			params.put("cfNumber", cfNumber);
			params.put("operateType", "S");
			params.put("operator", login.getNumber());
			CallforwardBus bus = new CallforwardBus();
			DataMsg dataMsg = bus.sendHLR(params, login);
			if (dataMsg.isState()) {
				request.setAttribute("result", dataMsg.getMsg());
				request.setAttribute("url", request.getContextPath()
						+ "/user/callForward/showCallForward.do");
				return Env.SUCCESS;
			} else {
				request.setAttribute("result", dataMsg.getMsg());
				return Env.ERROR;
			}

		} else {
			request.setAttribute("result", "亲，您目前是漏话提醒用户，无法享受呼转功能，赶紧升级套餐吧");
		}
		return "error";
	}

	@RequestMapping("/cancelCFDConfig")
	public String cancelCFDConfig(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			request.setAttribute("result", "取消失败");
			return Env.ERROR;
		}
		CallforwardBus bus = new CallforwardBus();
		TCallforward callforward = bus.showCallforwardByID(Long.valueOf(id));
		if (callforward == null) {
			request.setAttribute("result", "取消失败，数据未找到");
			return Env.ERROR;
		}
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("type", callforward.getCfType());
		params.put("cfNumber", "NONE");
		params.put("operateType", "C");
		params.put("operator", login.getNumber());
		DataMsg dataMsg = bus.sendHLR(params, login);
		if (dataMsg.isState()) {
			request.setAttribute("result", dataMsg.getMsg());
			request.setAttribute("url", request.getContextPath()
					+ "/user/callForward/showCallForward.do");
			return Env.SUCCESS;
		} else {
			request.setAttribute("result", dataMsg.getMsg());
			return Env.ERROR;
		}
	}
}
