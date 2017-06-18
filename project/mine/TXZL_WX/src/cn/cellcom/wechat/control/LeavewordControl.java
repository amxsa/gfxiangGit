package cn.cellcom.wechat.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.web.page.PageBus;
import cn.cellcom.web.page.PageResult;
import cn.cellcom.web.page.PageTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.LeavewordBus;
import cn.cellcom.wechat.bus.RegisterBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.UserUtil;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TCity;
import cn.cellcom.wechat.po.TLeaveword;
import cn.cellcom.wechat.po.TRegister;

@Controller
@RequestMapping("/user/leaveword")
public class LeavewordControl {
	@RequestMapping("/showLeaveword")
	public String showLeaveword(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		HttpSession session = request.getSession();
		// type=1 最近7天，type=2 最近一个月
		LeavewordBus bus = new LeavewordBus();
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		Login login = (Login) session.getAttribute("login");
		Integer lhcount = bus.showLhCount(jdbc, login.getNumber(), "1", null,
				null);
		request.setAttribute("lhcount", lhcount);
		PageBus pageBus = new PageBus();
		PageTool page = new PageTool(request.getParameter("currentPage"), 10,
				request.getRequestURI());
		// 查询7天或者一个月的分页（视图不同）
		List<Object> params = new ArrayList<Object>();
		StringBuffer SQL = new StringBuffer();
		SQL.append("select * from ");
		if ("1".equals(type)) {
			SQL.append(" v_leaveword ");
		} else {
			SQL.append(" v_leaveword_month");
		}
		SQL.append(" where reg_no = ?");
		params.add(login.getNumber());
		String ANum = request.getParameter("ANum");
		if (StringUtils.isNotBlank(ANum)) {
			SQL.append(" and a_num= ? ");
			params.add(ANum);
		}
		String msgtype = request.getParameter("msgtype");
		// 1:文本，2，语音，3：全部
		if (StringUtils.isNotBlank(msgtype)) {
			if ("1".equals(msgtype)) {
				SQL.append(" and msgtype = ?  ");
				params.add("T");

			} else if ("2".equals(msgtype)) {
				SQL.append(" and msgtype = ?  ");
				params.add("V");
			}
		}
		SQL.append(" order by recordtime desc ");
		PageResult<TLeaveword> pageResult = null;
		try {
			pageResult = pageBus.queryPageData(jdbc,
					ApplicationContextTool.getDataSource(), page,
					SQL.toString(), params.toArray(), TLeaveword.class);
			if (pageResult != null) {
				List<TLeaveword> list = pageResult.getContent();
				if (list != null && list.size() > 0) {
					list = getANumCity(list);
				}
				request.setAttribute("list", list);
				request.setAttribute("linkToMobile",
						pageResult.getLinkToMobile());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 体验用户算还差多少时间到期
		if (StringUtils.isBlank(login.getServNbr())) {
			String diffDay = diffLimiteDay(jdbc, login.getNumber());
			if (StringUtils.isNotBlank(diffDay)) {
				request.setAttribute("limiteDay", diffDay);
			}
		}
		return "/user/showLeaveword";
	}

	@RequestMapping("/leavewordMsg")
	public String leavewordMsg(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		Login login = (Login) session.getAttribute("login");
		try {
			List<Map<String, Object>> list = jdbc
					.query(ApplicationContextTool.getDataSource(),
							"select recordtime from v_leaveword_month where reg_no = ?",
							new String[] { login.getNumber() }, 0, 0);
			if (list != null && list.size() > 0) {
				int lhcount = list.size();
				Integer cnt1 = 0;
				Integer cnt2 = 0;
				Integer cnt3 = 0;
				request.setAttribute("lhcount", lhcount);
				Date recordTime = null;
				int hour = 0;
				// 计算时间段漏话数
				for (int i = 0, len = list.size(); i < len; i++) {
					recordTime = (Date) list.get(i).get("recordtime");
					if (recordTime != null) {
						hour = DateTool.getHour(recordTime);
						if (hour >= 9 && hour <= 12) {
							++cnt1;
						} else if (hour > 12 && hour <= 18) {
							++cnt2;
						} else if (hour > 18 && hour < 23) {
							++cnt3;
						}
					}

				}
				List<Integer> cnts = new ArrayList();
				cnts.add(cnt1);
				cnts.add(cnt2);
				cnts.add(cnt3);
				Collections.sort(cnts);
				request.setAttribute("timecount", cnts);
				if (cnts.get(2) == cnt1) {
					request.setAttribute("timeSub", "9点-12点");
				} else if (cnts.get(2) == cnt2) {
					request.setAttribute("timeSub", "13点-18点");
				} else if (cnts.get(2) == cnt3) {
					request.setAttribute("timeSub", "18点-23点");
				}
				// 漏话最多的号码
				List<Map<String, Object>> maxNumMsg = jdbc
						.query(ApplicationContextTool.getDataSource(),
								"select  t.a_num,t.cnt from (select a_num ,count(a_num) as cnt from v_leaveword_month where reg_no = ? group by a_num) t order  by t.cnt desc  ",
								new String[] { login.getNumber() }, 0, 0);
				if (maxNumMsg != null && maxNumMsg.size() > 0) {
					request.setAttribute("maxNumMsg", maxNumMsg.get(0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 体验用户算还差多少时间到期
		if (StringUtils.isBlank(login.getServNbr())) {
			String diffDay = diffLimiteDay(jdbc, login.getNumber());
			if (StringUtils.isNotBlank(diffDay)) {
				request.setAttribute("limiteDay", diffDay);
			}
		}
		return "/user/leavewordMsg";
	}

	private String diffLimiteDay(JDBC jdbc, String regNo) {
		RegisterBus registerBus = new RegisterBus();
		TRegister register = registerBus.queryByRegNo(jdbc, regNo, "reg_time");
		Date regTime = register.getRegTime();
		Date limiteTime = DateTool.diff(regTime, "DAY", 60);
		Date date = new Date();

		if (limiteTime.after(date)) {
			Long diffDay = DateTool.Diff(date, limiteTime, "DAY");
			if (diffDay >= 0) {
				return String.valueOf(diffDay);
			}
		}
		return null;
	}

	@RequestMapping("/showLeavewordTxt")
	public String showLeavewordTxt(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		Login login = (Login) session.getAttribute("login");
		if (UserUtil.isJCOrXXUser(login.getNumber(), login.getSetid(),
				login.getUsage(), login.getServNbr())) {
			String type = request.getParameter("type");
			request.setAttribute("type", type);
			// type=1 最近7天，type=2 最近一个月
			PageBus pageBus = new PageBus();
			PageTool page = new PageTool(request.getParameter("currentPage"),
					10, request.getRequestURI());
			List<Object> params = new ArrayList<Object>();
			StringBuffer SQL = new StringBuffer();

			SQL.append("select * from ");
			if ("1".equals(type)) {
				SQL.append(" v_leaveword ");
			} else {
				SQL.append(" v_leaveword_month");
			}
			SQL.append(" where reg_no = ? and msgtype = ? ");
			params.add(login.getNumber());
			params.add("T");
			String ANum = request.getParameter("ANum");
			if (StringUtils.isNotBlank(ANum)) {
				SQL.append(" and a_num= ? ");
				params.add(ANum);
			}

			SQL.append(" order by recordtime desc ");
			PageResult<TLeaveword> pageResult = null;
			try {
				pageResult = pageBus.queryPageData(jdbc,
						ApplicationContextTool.getDataSource(), page,
						SQL.toString(), params.toArray(), TLeaveword.class);
				if (pageResult != null) {
					List<TLeaveword> list = pageResult.getContent();
					if (list != null && list.size() > 0) {
						list = getANumCity(list);
					}
					request.setAttribute("list", list);
					request.setAttribute("linkToMobile",
							pageResult.getLinkToMobile());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "/user/showLeavewordTxt";
		} else {
			request.setAttribute("result", "亲，您目前是漏话提醒用户，无法使用留言功能，赶紧升级套餐吧");
			return Env.ERROR;
		}

	}

	private List<TLeaveword> getANumCity(List<TLeaveword> list) {
		TLeaveword po = null;
		TCity city = null;
		Long province = null;
		StringBuilder cityName = null;
		for (int i = 0, len = list.size(); i < len; i++) {
			po = list.get(i);
			cityName = new StringBuilder();
			String areacode = UserUtil.getAreaCode(po.getANum());
			if (areacode != null) {
				city = Env.CODE_CITY.get(areacode);
				if (city != null) {
					province = city.getParentId();
				}
				if (province != null)
					cityName.append(Env.PROVINCE.get(province));
				cityName.append(city != null ? city.getName() : "");
				po.setANumCity(cityName.toString());
			}
		}
		return list;
	}

	private String getMsgInfo(Login login, HttpServletRequest request,
			int lhcount) {
		StringBuilder msg = new StringBuilder("尊敬的");
		msg.append(login.getNumber());
		msg.append("，");
		int hour = DateTool.getHour(new Date());
		if (hour > 0 && hour < 8) {
			msg.append("早上好");
		} else if (hour >= 8 && hour < 12) {
			msg.append("上午好");
		} else if (hour >= 12 && hour < 19) {
			msg.append("下午好");
		} else {
			msg.append("晚上好");
		}
		msg.append("，您最近7天漏话").append(lhcount).append("条，");
		StringBuilder link = new StringBuilder();
		link.append("<a href=\"").append(request.getContextPath())
				.append("/user/help.jsp");
		link.append("\" class=\"red\">【升级】</a>");
		if (login.getSetid() == 51) {
			if (StringUtils.isNotBlank(login.getServNbr())) {
				msg.append("欢迎您享受更多服务").append(link.toString());
			} else {
				msg.append("欢迎您升级产品享受更多服务").append(link.toString());
			}
		} else if (login.getSetid() == 21 || login.getSetid() == 31) {
			msg.append("欢迎您升级产品享受更多服务").append(link.toString());
		} else if (login.getSetid() == 22 || login.getSetid() == 32) {
			msg.append("欢迎使用通信助理！");
		}
		return msg.toString();

	}
}
