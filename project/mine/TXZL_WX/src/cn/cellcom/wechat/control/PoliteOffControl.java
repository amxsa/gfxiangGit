package cn.cellcom.wechat.control;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TPoliteOffWx;

@Controller
@RequestMapping("/user/politeOff")
public class PoliteOffControl {
	private static final Log log = LogFactory.getLog(PoliteOffControl.class);

	@RequestMapping("/showPoliteOff")
	public String showPoliteOff(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		if (UserUtil.isJCOrXXUser(login.getNumber(), login.getSetid(),
				login.getUsage(), login.getServNbr())) {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			try {
				String dateStr = DateTool.formateTime2String(new Date(),
						"yyyy-MM-dd HH:mm:ss");
				// 查询当前设置
				TPoliteOffWx po = jdbc
						.queryForObject(
								ApplicationContextTool.getDataSource(),
								"select * from t_polite_off_wx where reg_no = ?   and start_time<= ?  and expire_time > ?    ",
								TPoliteOffWx.class,
								new String[] { login.getNumber(), dateStr,
										dateStr });
				if (po != null)
					request.setAttribute("po", po);

				Map<String, String[]> politeoffType = new LinkedHashMap<String, String[]>();
				Iterator<Entry<String, String>> iter = Env.POLITEOFF.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					politeoffType.put(entry.getKey(),
							new String[] { entry.getValue(), null, null });
				}
				request.setAttribute("politeoffType", politeoffType);
				return "/user/showPoliteOff";
			} catch (Exception e) {
				log.error("", e);
				return Env.FAIL;
			}
		} else {
			request.setAttribute("result", "亲，您目前无法享受礼貌挂机功能，赶紧升级套餐吧");
			return Env.ERROR;
		}
	}

	@RequestMapping("/showPoliteOffDetail")
	public String showPoliteOffDetail(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		if (UserUtil.isJCOrXXUser(login.getNumber(), login.getSetid(),
				login.getUsage(), login.getServNbr())) {
			String type = request.getParameter("type");
			if (!Env.POLITEOFF.containsKey(type)) {
				request.setAttribute("result", "礼貌挂机缺少参数");
				return Env.ERROR;
			}
			request.setAttribute("type", type);
			request.setAttribute("name", Env.POLITEOFF.get(type));
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			String dateStr = DateTool.formateTime2String(new Date(),
					"yyyy-MM-dd HH:mm:ss");
			try {
				TPoliteOffWx po = jdbc
						.queryForObject(
								ApplicationContextTool.getDataSource(),
								"select * from  t_polite_off_wx where reg_no = ?  and  type = ? and start_time<= ?  and expire_time > ?   ",
								TPoliteOffWx.class,
								new Object[] { login.getNumber(), type,
										dateStr, dateStr });
				if (po != null)
					request.setAttribute("po", po);
				return "/user/showPoliteOffDetail";
			} catch (Exception e) {
				log.error("", e);
				return Env.FAIL;
			}
		} else {
			request.setAttribute("result", "亲，您目前无法享受礼貌挂机功能，赶紧升级套餐吧");
			return Env.ERROR;
		}
	}

	@RequestMapping("/updatePoliteOffState")
	public String updatePoliteOffState(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String state = request.getParameter("state");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"update t_polite_off_wx set state= ?  where id = ? and reg_no = ?  ",
					new Object[] { state, Long.valueOf(id), login.getNumber() });
			PrintTool.print(response, "success", null);
		} catch (Exception e) {
			log.error("", e);
			PrintTool.print(response, "failed", null);
		}
		return null;
	}

	@RequestMapping("/deletePoliteOff")
	public String deletePoliteOff(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");

		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"delete from t_polite_off_wx   where id = ? and reg_no = ?  ",
					new Object[] { Long.valueOf(id), login.getNumber() });
			PrintTool.print(response, "success", null);
		} catch (Exception e) {
			e.printStackTrace();
			PrintTool.print(response, "failed", null);
		}
		return null;
	}

	@RequestMapping("/setPoliteOff")
	public String setPoliteOff(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		String diffTime = request.getParameter("diffTime");
		String state = request.getParameter("state");
		if (StringUtils.isBlank(type) || StringUtils.isBlank(diffTime)) {
			request.setAttribute("result", "礼貌挂机缺少参数");
			return Env.ERROR;
		}
		if (!Env.POLITEOFF.containsKey(type)) {
			request.setAttribute("result", "礼貌挂机参数值错误");
			return Env.ERROR;
		}
		HttpSession session = request.getSession();
		Login login = (Login) session.getAttribute("login");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			Date startTime = new Date();
			Date expireTime = DateTool.diff(startTime, "MINUTE",
					Integer.parseInt(diffTime));
			jdbc.update(ApplicationContextTool.getDataSource(),
					"delete from t_polite_off_wx where reg_no= ?",
					new String[] { login.getNumber() });
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"insert into t_polite_off_wx(reg_no,status,type,start_time,expire_time,submit_time) values(?,'2',?,?,?,getdate())",
					new Object[] {
							login.getNumber(),
							type,
							DateTool.formateTime2String(startTime,
									"yyyy-MM-dd HH:mm:ss"),
							DateTool.formateTime2String(expireTime,
									"yyyy-MM-dd HH:mm:ss") });

		} catch (Exception e) {
			log.error("", e);
			return Env.FAIL;
		}
		return showPoliteOff(request, response);
	}
}
