package cn.cellcom.czt.bus;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.common.ConfLoad;
import cn.cellcom.czt.common.DataPo;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.common.UrlMaker;
import cn.cellcom.czt.po.TCity;
import cn.cellcom.web.spring.ApplicationContextTool;

public class WeatherBus {
	private static final Log log = LogFactory.getLog(WeatherBus.class);

	public DataPo queryWeather(Map<String, String> params, DataPo dataPo) {
		Integer checkResult = checkParams(params);
		if(checkResult!=null){
			dataPo.setCode(checkResult);
			return dataPo;
		}
		String areaid = params.get("areaid");
		String type = params.get("type");
		// 最终字符串结果
		StringBuffer url = new StringBuffer();
		// key
		StringBuffer data = new StringBuffer();
		// 拼接调用接口的url
		url.append(Env.WEATHER_URL);
		url.append("?areaid=");
		url.append(areaid);
		url.append("&type=");
		url.append(type);
		url.append("&date=");
		url.append(DateTool.formateTime2String(new Date(), "yyyyMMddHHmm"));
		
		// 需要加密的数据 ,用于生成令牌
		data.append(url.toString());
		data.append("&appid=");
		data.append(ConfLoad.getProperty("WEATHER_Public_Key"));
		// 令牌
		String str = UrlMaker.standardURLEncoder(data.toString(),
				ConfLoad.getProperty("WEATHER_Private_Key"));

		// appid前六位和令牌拼接到url
		url.append("&appid=");
		url.append(ConfLoad.getProperty("WEATHER_APP_ID"));
		url.append("&key=");
		url.append(str);
		
		TCity city = new TCity();
//		try {
//			
//			city = queryCity(areaid);
//		} catch (Exception e) {
//			throw new RuntimeException("启辰查询天气异常", e);
//		}
//		if (city != null) {
			String result = HttpUrlClient.doGet("启辰查询天气", url.toString(), null,
					"UTF-8", 8000, true);
			log.info("启辰查询天气结果:"+result);
			if (result != null) {
				JSONObject jsonObj = JSONObject.fromObject(result);
				dataPo.setCode(0);
				dataPo.setState("S");
				JSONArray array = new JSONArray();
				array.add(jsonObj);
				dataPo.setParambuf(array);
				
				return dataPo;
			} else {
				dataPo.setCode(-2006);
				return dataPo;
			}
			
//		} else {
//			dataPo.setCode(-2005);
//			return dataPo;
//		}
		
	}
	
	private Integer checkParams(Map<String, String> params){
		String spname = params.get("spname");
		String areaid = params.get("areaid");
		String type = params.get("type");
		String timestamp = params.get("timestamp");
		String authstring = params.get("authstring");
		if(StringUtils.isBlank(spname)||!Env.WEATHER_SPNAME.contains(spname)){
			return -2007;
		}
		if(StringUtils.isBlank(areaid)){
			return  -2002;
		}
		if(StringUtils.isBlank(type)||!Env.WEATHER_TYPE.contains(type)){
			return  -2003; 
		}
		if(StringUtils.isBlank(timestamp)||StringUtils.isBlank(authstring)){
			return -2004;
		}
		
		String temp = MD5.getMD5((spname+"WEATHER@$^*"+timestamp).getBytes());
		if(temp==null||!authstring.toUpperCase().equals(temp.toUpperCase())){
			return -2004;
		}
		
		return null;
	}

	public TCity queryCity(String areaid) {
		if (StringUtils.isBlank(areaid)) {
			return null;
		}
		JDBC jdbc = ApplicationContextTool.getJdbc();
		try {
			TCity po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_city where areaid= ? ", TCity.class,
					new String[] { areaid });
			if (po != null) {
				return po;
			}
		} catch (Exception e) {
			throw new RuntimeException("查询城市异常", e);
		}
		return null;
	}
}
