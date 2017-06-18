package cn.cellcom.wechat.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.ColorPrintingBus;
import cn.cellcom.wechat.bus.ColorPrintingUseBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.ColorPrinting;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TColorPrintPart;
import cn.cellcom.wechat.po.TColorPrintUse;

@Controller
@RequestMapping("/colorprinting/use")
public class ColorPrintingUseControl {

	private static final Log log = LogFactory.getLog(ColorPrintingUseControl.class);
	
	/**
	 * 查询当前使用的彩印
	 * @param regNo  注册号码
	 * @param number  被叫号码
	 * @return
	 */
	@RequestMapping("/queryColorPrinting")
	public String queryColorPrinting(String regNo,String number) {
		ColorPrintingUseBus bus = new ColorPrintingUseBus();
		DataMsg data=bus.queryColorPrintingUse(regNo,number);
		return data.getMsg();
	}
	
	@RequestMapping("/setServer")
	public String setServer(HttpServletRequest request,HttpServletResponse response) {
		ColorPrintingUseBus bus = new ColorPrintingUseBus();
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		DataMsg dataMsg = bus.openServer(login.getNumber());
		request.setAttribute("server", "Y");
		request.setAttribute("info", dataMsg.getMsg());
		return "/colorprinting/colorPrintIndex";
	}
	
	@RequestMapping("/queryServer")
	public String queryServer(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		if (UserUtil.isTXZL_C(login.getNumber(), login.getStatus(),
				login.getSetid(), login.getUsage(), login.getServNbr())) {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			ColorPrintingUseBus bus = new ColorPrintingUseBus();
			TColorPrintUse po = bus.query(jdbc, login);
			if (po==null||(po != null&&po.getCpServer().equals("N"))) {
				request.setAttribute("server", "亲，您暂未开启彩印服务");
				return "/colorprinting/colorPrintIndex";
			}
		} else {
			request.setAttribute("server", "亲，您目前无法享受彩印功能");
			return Env.ERROR;
		}
		request.setAttribute("server", "Y");
		return "/colorprinting/colorPrintIndex";
	}
	
	@RequestMapping("/queryMyColorPrinting")
	public String queryMyColorPrinting(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		ColorPrintingUseBus bus = new ColorPrintingUseBus();
		TColorPrintUse po = bus.query(jdbc, login);
		if (po!=null) {
			request.setAttribute("po", po);
		}else{
			request.setAttribute("info", "很抱歉，您暂未登陆");
			return "/colorprinting/404b";
		}
		
		return "/colorprinting/myColorPrinting";
	}
	
	@RequestMapping("/setColorPrinting")
	public String setColorPrinting(HttpServletRequest request,HttpServletResponse response) {
		
		String content = request.getParameter("content");
		String state = request.getParameter("state");
		Map<String, String> params = new HashMap<String, String>();
		params.put("content", content);
		params.put("state", state);
		HttpSession session = request.getSession();
		ColorPrintingUseBus bus = new ColorPrintingUseBus();
		try {
			DataMsg dataMsg = new DataMsg(false, "设置彩印失败");
			Login login = (Login) session.getAttribute("login");
			dataMsg = bus.saveOrUpdate(params, login, dataMsg);
			log.info(login.getNumber() + "设置彩印结果:"
					+ JSONObject.fromObject(dataMsg).toString());
			if (dataMsg.isState()) {
				PrintTool.print(response, "true|"+dataMsg.getMsg(),null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
