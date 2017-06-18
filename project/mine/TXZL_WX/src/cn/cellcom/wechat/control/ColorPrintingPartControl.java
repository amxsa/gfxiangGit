package cn.cellcom.wechat.control;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.ColorPrintingPartBus;
import cn.cellcom.wechat.bus.ColorPrintingUseBus;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TColorPrintPart;
import cn.cellcom.wechat.po.TColorPrintUse;

@Controller
@RequestMapping("/colorprinting/part")
public class ColorPrintingPartControl {

	private static final Log log = LogFactory.getLog(ColorPrintingPartControl.class);
	
	/**
	 * 设置分时段彩印
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/setColorPrinting")
	public String setColorPrinting(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id");
		String starthour = request.getParameter("sHour");
		String endhour = request.getParameter("eHour");
		String startminute = request.getParameter("sMinute");
		String endminute = request.getParameter("eMinute");
		String isloop = request.getParameter("isloop");
		String content = request.getParameter("content");
		String state = request.getParameter("state");
		String startTime = null;
		String endTime = null;
		String time1 = DateTool.formateTime2String(new Date(), "yyyy-MM-dd");//只能存储当日年月日信息  查询时根据是否重复再拼接日期
		startTime=time1+" "+starthour+":"+startminute+":00";
		endTime=time1+" "+endhour+":"+endminute+":00";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("isloop", isloop);
		params.put("content", content);
		params.put("state", state);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		ColorPrintingPartBus bus = new ColorPrintingPartBus();
		
		Login login = (Login) request.getSession().getAttribute("login");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		List<TColorPrintPart> list = bus.query(jdbc, login);
		TColorPrintPart po=null;
		boolean flag = false;
		if (list.size()>0&&starthour!=null&&starthour!="") {
			for (int i = 0; i < list.size(); i++) {
				po = list.get(i);
				if (list.size()==1&&(!state.equals("Y"))) {
					flag=true;
					continue;
				}
				if (StringUtils.isNotBlank(StringUtils.trim(id))) {
					if (list.get(i).getId()==Long.parseLong(id)) {//更新时 排除本身时间冲突
						flag=true;
						continue;
					}
				}
				if (diffHourMi(po.getEndTime(), DateTool.formateString2Time(startTime, "yyyy-MM-dd HH:mm:ss"))) {
					flag=true;
					continue;
				}
				if (diffHourMi(DateTool.formateString2Time(endTime, "yyyy-MM-dd HH:mm:ss"), po.getStartTime())) {
					flag=true;
					continue;
				}
				flag = false;
				break;
			}
		}else{
			flag=true;
		}
		params.put("flag", ""+flag);
		try {
			DataMsg dataMsg = new DataMsg(false, "设置彩印失败");
			dataMsg = bus.saveOrUpdate(params, login, dataMsg);
			log.info(login.getNumber() + "设置彩印结果:"
					+ JSONObject.fromObject(dataMsg).toString());
			if (dataMsg.isState()) {
				PrintTool.print(response, "true|"+dataMsg.getMsg(),null);
			}
			PrintTool.print(response, "false|"+dataMsg.getMsg(),null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	@RequestMapping("/queryPartColorPrinting")
	public String queryPartColorPrinting(HttpServletRequest request,HttpServletResponse response) {
		Login login = (Login) request.getSession().getAttribute("login");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		ColorPrintingPartBus bus=new ColorPrintingPartBus();
		ColorPrintingUseBus pubus=new ColorPrintingUseBus();
		TColorPrintUse po = pubus.query(jdbc, login);
		if (po==null) {
			request.setAttribute("info", "很抱歉，您暂未登陆");
			return "/colorprinting/404b";
		}
		String eTime="24:00";
		String sTime=DateTool.formateTime2String(new Date(), "HH:mm");
		int startHour = Integer.parseInt(DateTool.formateTime2String(new Date(), "HH"));
		int startMinute = Integer.parseInt(DateTool.formateTime2String(new Date(), "mm"));
		if (Calendar.HOUR<22) {
			eTime=startHour+3+":"+startMinute;
			if (startMinute<10) {
				eTime=startHour+3+":0"+startMinute;
			}
		}
		request.setAttribute("sTime", sTime);
		request.setAttribute("eTime", eTime);
		List<TColorPrintPart> list = bus.query(jdbc, login);
		request.setAttribute("list", list);
		return "/colorprinting/partColorPrinting";
		
	}
	
	private static boolean diffHourMi(Date date1, Date date2) {
		int hour1 = DateTool.getHour(date1);
		int hour2 = DateTool.getHour(date2);
		if (hour2 > hour1) {
			return true;
		} else if (hour2 == hour1) {
			int minute1 = DateTool.getMinute(date1);
			int minute2 = DateTool.getMinute(date2);
			if (minute2 > minute1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}
	
}
