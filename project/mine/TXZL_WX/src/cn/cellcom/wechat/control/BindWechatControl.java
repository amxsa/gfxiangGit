package cn.cellcom.wechat.control;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.web.spring.ApplicationContextTool;

import cn.cellcom.wechat.bus.BindWechatBus;
import cn.cellcom.wechat.bus.RegisterBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TRegister;

@Controller
@RequestMapping("/user/bindWechat")
public class BindWechatControl {

	private static final Log log = LogFactory.getLog(BindWechatControl.class);

	@RequestMapping("/checkBind")
	public String checkBind(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String openid = (String) session.getAttribute("openid");
		if (StringUtils.isNotBlank(openid)) {
			BindWechatBus bus = new BindWechatBus();
			DataMsg msg = bus.checkBind(openid);
			if (msg.isState()) {
				String regNo = msg.getObj().getString("regNo");
				RegisterBus registerBus = new RegisterBus();
				JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
				TRegister register = registerBus
						.queryByRegNo(jdbc, regNo, null);
				if (register != null && "Y".equals(register.getStatus())) {
					Login login = registerBus.setLogin(register, openid);
					if (login != null) {
						session.setAttribute("login", login);
					}
					return "/wechat/bindWechatSuccess";
				} else {
					try {
						jdbc.update(ApplicationContextTool.getDataSource(),
								"delete from t_bind_wechat where reg_no = ?",
								new String[] { regNo });
					} catch (Exception e) {
						log.error("", e);
						return Env.FAIL;
					}
					msg.setMsg("您尚未开通通信助理功能");
					return "/wechat/bindWechat";
				}

			} else {
				return "/wechat/bindWechat";
			}
		} else {
			request.setAttribute("result", "请点击菜单从新使用");
			return Env.ERROR;
		}
	}

	@RequestMapping("/bind")
	public String bind(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String openid = (String) session.getAttribute("openid");
		String smsWechatCode = (String) session.getAttribute("smsWechatCode");
		String account = request.getParameter("account");
		String passwordSms = request.getParameter("passwordSms");
		// 微信号加密后是28
		if (StringUtils.isBlank(openid) || openid.length() != 28) {
			request.setAttribute("result", "微信号格式错误");
			return Env.ERROR;
		}
		String str = PatternTool.checkStr(account, Env.C_PATTERN, "手机号码格式错误");
		if (str != null) {
			request.setAttribute("result", str);
			return Env.ERROR;
		}
		if (!passwordSms.equals(smsWechatCode)) {
			request.setAttribute("result", "短信验证码错误");
			return Env.ERROR;
		}
		BindWechatBus bus = new BindWechatBus();
		DataMsg msg = bus.checkBind(openid);
		if (msg.isState()) {
			request.setAttribute("result", msg.getMsg());
			return Env.ERROR;
		} else {
			RegisterBus registerBus = new RegisterBus();
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			TRegister register = registerBus.queryByRegNo(jdbc, account, null);
			if (register != null) {
				if ("Y".equals(register.getStatus())) {
					Login login = registerBus.setLogin(register, openid);
					if (login != null) {
						session.setAttribute("login", login);
					}
					try {
						jdbc.update(
								ApplicationContextTool.getDataSource(),
								"insert into t_bind_wechat(reg_no,wechat_no,bind_time) values(?,?,?)",
								new Object[] {
										account,
										openid,
										DateTool.formateTime2String(new Date(),
												"yyyy-MM-dd HH:mm:ss") });
					} catch (Exception e) {
						log.error("写入t_bind_wechat异常", e);
						return Env.FAIL;
					}
					return "/wechat/bindWechatSuccess";
				} else {
					try {
						jdbc.update(ApplicationContextTool.getDataSource(),
								"delete from t_bind_wechat where reg_no = ?",
								new String[] { account });
					} catch (Exception e) {
						e.printStackTrace();
						return "fail";
					}
					session.setAttribute("number", account);
					return "/user/register";
				}
			} else {
				session.setAttribute("number", account);
				return "/user/register";
			}
		}
	}

	@RequestMapping("/deleteBind")
	public String deleteBind(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		String wechatNo = login.getWechatNo();
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			jdbc.update(ApplicationContextTool.getDataSource(),
					"delete from t_bind_wechat where wechat_no  = ?",
					new String[] { wechatNo });
			request.setAttribute("msg", "解绑成功");
			session.removeAttribute("login");
			return "/wechat/bindWechat";
		} catch (Exception e) {
			log.error("",e);
			return Env.FAIL;
		}
	}

}
