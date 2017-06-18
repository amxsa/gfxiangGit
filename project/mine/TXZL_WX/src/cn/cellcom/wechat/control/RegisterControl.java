package cn.cellcom.wechat.control;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.RegisterBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TRegister;

@Controller
@RequestMapping("/user/register")
public class RegisterControl {
	@RequestMapping("/register")
	public String register(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String number = (String) session.getAttribute("number");
		if (number == null) {
			request.setAttribute("result", "请输入手机号码");
			return Env.ERROR;
		}
		String checkNumber = PatternTool.checkStr(number, Env.C_PATTERN,
				"手机号码格式错误");
		if (checkNumber != null) {
			request.setAttribute("result", checkNumber);
			return Env.ERROR;
		}

		RegisterBus register = new RegisterBus();
		DataMsg msg = register.saveRegister(request.getRemoteAddr(), number);
		if (msg.isState()) {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			String openid = request.getParameter("openid");
			TRegister po = (TRegister) msg.getList().get(0);
			Login login = register.setLogin(po, openid);
			try {
				jdbc.update(
						ApplicationContextTool.getDataSource(),
						"insert into t_bind_wechat(reg_no,wechat_no,bind_time) values(?,?,?)",
						new Object[] {
								login.getNumber(),
								openid,
								DateTool.formateTime2String(new Date(),
										"yyyy-MM-dd HH:mm:ss") });
			} catch (Exception e) {
				e.printStackTrace();
				return Env.FAIL;
			}
			session.setAttribute("login", login);
			return "/wechat/bindWechatSuccess";
		} else {
			request.setAttribute("result", msg.getMsg());
			return Env.ERROR;
		}
	}
}
