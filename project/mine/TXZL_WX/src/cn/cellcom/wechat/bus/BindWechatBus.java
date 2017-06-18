package cn.cellcom.wechat.bus;


import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;

import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.po.DataMsg;

import cn.cellcom.wechat.po.TBindWechat;


public class BindWechatBus {
	public DataMsg checkBind(String openid){
		TBindWechat po = queryByWechatNo(null, openid);
		DataMsg msg = new DataMsg(false,"微信号未绑定");
		if(po!=null){
			msg.setState(true);
			msg.setMsg("微信号已绑定");
			JSONObject obj = new JSONObject();
			obj.put("regNo", po.getRegNo());
			msg.setObj(obj);
		}
		return msg;
	}
	
	public DataMsg bind(Map<String, String> params){
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		String openid = params.get("openid");
		DataMsg msg = new DataMsg(false,"手机号绑定失败");
		try{
			TBindWechat po = queryByWechatNo(jdbc, openid);
			if(po!=null){
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msg;
		
	}
	
	public TBindWechat queryByWechatNo(JDBC jdbc, String openid) {
		if (StringUtils.isBlank(openid)) {
			return null;
		}
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TBindWechat po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_bind_wechat where wechat_no = ? ",
					TBindWechat.class, new String[] { openid });
			return po;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	
}
