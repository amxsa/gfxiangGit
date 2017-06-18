package cn.cellcom.wechat.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.web.spring.ApplicationContextTool;

import cn.cellcom.wechat.bus.ColorPrintingBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.ColorPrinting;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;

@Controller
@RequestMapping("/user/colorPrinting")
public class ColorPrintingControl {
	private static final Log log = LogFactory
			.getLog(ColorPrintingControl.class);

	@RequestMapping("/queryMyColorPrinting")
	public String queryMyColorPrinting(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");

		// 深圳1336009号段具备该功能
		if (UserUtil.isTXZL_C(login.getNumber(), login.getStatus(),
				login.getSetid(), login.getUsage(), login.getServNbr())) {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			ColorPrintingBus bus = new ColorPrintingBus();
			ColorPrinting po = bus.query(jdbc, login);
			if (po != null && "Y".equals(po.getState())) {
				po.setContent(po.getContent().replaceAll(" ", "&nbsp;"));
				request.setAttribute("po", po);
			}
			return "/colorprinting/showMyColorPrinting";
		} else {
			request.setAttribute("result", "亲，您目前无法享受彩印功能");
			return Env.ERROR;
		}
	}

	@RequestMapping("/saveOrUpdateMyColorPrinting")
	public String saveOrUpdateMyColorPrinting(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String content = request.getParameter("content");
		String state = request.getParameter("state");
		Map<String, String> params = new HashMap<String, String>();
		params.put("content", content);
		params.put("state", state);
		HttpSession session = request.getSession();
		ColorPrintingBus bus = new ColorPrintingBus();
		DataMsg dataMsg = new DataMsg(false, "设置彩印失败");
		Login login = (Login) session.getAttribute("login");
		dataMsg = bus.saveOrUpdate(params, login, dataMsg);
		log.info(login.getNumber() + "设置彩印结果:"
				+ JSONObject.fromObject(dataMsg).toString());
		PrintTool.print(response, JSONObject.fromObject(dataMsg).toString(),
				"json");
		return null;

	}
}
