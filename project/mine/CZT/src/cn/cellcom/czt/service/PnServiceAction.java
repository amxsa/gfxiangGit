package cn.cellcom.czt.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.data.PrintTool;

import cn.cellcom.czt.bus.PnBus;
import cn.cellcom.czt.po.DataMsg;
import cn.cellcom.web.struts.Struts2Base;

public class PnServiceAction extends Struts2Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(PnServiceAction.class);
	public String queryTdCode() throws IOException{
		String pn = StringUtils.trimToNull(getParameter("pn"));
		String fromPart = getParameter("fromPart");
		String timestamp =getParameter("timestamp");
		String authstring =getParameter("authstring");
		Map<String ,String > params = new LinkedHashMap<String, String>();
		params.put("pn", pn);
		params.put("fromPart", fromPart);
		params.put("timestamp", timestamp);
		params.put("authstring", authstring);
		PrintTool.printLog(params);
		PnBus bus = new PnBus();
		DataMsg msg = bus.queryTdCode(params);
		log.info(pn+"查询设备二维码:"+JSONObject.fromObject(msg));
		PrintTool.print(getResponse(), JSONObject.fromObject(msg), "json");
		return null;
	}
	
}
