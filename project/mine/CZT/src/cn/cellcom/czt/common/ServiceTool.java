package cn.cellcom.czt.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.data.PrintTool;



public class ServiceTool {
	private static final Log log = LogFactory.getLog(ServiceTool.class);
	public static void errorDB(String model, HttpServletResponse response,
			Exception e) throws IOException {
		log.error(model + "数据操作异常",e);
		PrintTool.print(response,
				JSONObject.fromObject(ServiceTool.getDataPo("F", -500)), "json");

	}
	public static DataPo getDataPo(String state, Integer code) {
		return new DataPo(state, code, Env.CODE_RESULT.get(code));
	}
}
