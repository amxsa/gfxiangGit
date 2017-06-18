package cn.cellcom.czt.service;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import cn.cellcom.common.data.PrintTool;
import cn.cellcom.czt.bus.WeatherBus;
import cn.cellcom.czt.common.DataPo;
import cn.cellcom.czt.common.ServiceTool;
import cn.cellcom.web.struts.Struts2Base;

public class WeatherServiceAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory
			.getLog(WeatherServiceAction.class);

	public String queryWeather() throws IOException  {
		String spname = getParameter("spname");
		String areaid = getParameter("areaid");
		String type = getParameter("type");
		String timestamp = getParameter("timestamp");
		String authstring = getParameter("authstring");
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("spname", spname);
		params.put("areaid", areaid);
		params.put("type", type);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		PrintTool.printLog("启辰查询天气参数", params);
		WeatherBus bus = new WeatherBus();
		DataPo dataPo = new DataPo("F", -2001);
		try {
			dataPo = bus.queryWeather(params, dataPo);
			Object obj = JSONObject.fromObject(dataPo);
			log.info("启辰查询天气返回:" + obj.toString());
			PrintTool.print(getResponse(), obj, "json");
		} catch (Exception e) {
			ServiceTool.errorDB("启辰查询天气异常", getResponse(), e);
		}

		return null;

	}
}
