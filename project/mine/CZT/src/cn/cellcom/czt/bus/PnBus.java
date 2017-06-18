package cn.cellcom.czt.bus;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.DataMsg;
import cn.cellcom.czt.po.SpTdcode;

import cn.cellcom.web.spring.ApplicationContextTool;

public class PnBus {
	public DataMsg queryTdCode(Map<String, String> params){
		
		String pn =params.get("pn");
		DataMsg msg = new DataMsg();
		JSONObject data = new JSONObject();
		try{
			if (StringUtils.isBlank(pn)) {
				data.put("status", 2);
				data.put("description", "缺少设备条码");
				msg.setData(data);
				return msg;
			}
			String timestamp = params.get("timestamp");
			String authstring = params.get("authstring");
			String authStr = MD5.getMD5((pn + Env.SERVICE_KEY_APP + timestamp)
					.getBytes());
			if (!authstring.equals(authStr)) {
				data.put("status", 3);
				data.put("description", "验证字符错误");
				msg.setData(data);
				return msg;
			}
			//PN是12位或者13位
			if(pn.length()>13||pn.length()<12){
				data.put("status", 4);
				data.put("description", "设备条码未找到");
				msg.setData(data);
				return msg;
			}
			//13位校验规则：1-12位，∑偶数位*2+∑奇数位，所得和的个位数作为校验位
			if(pn.length()==13){
				int jishu=0;
				int oushu=0;
				for(int i=0;i<pn.length()-1;i++){
					if((i+1)%2==1){
						jishu+=Integer.parseInt(String.valueOf(pn.charAt(i)));
					}else{
						oushu+=Integer.parseInt(String.valueOf(pn.charAt(i)));
					}
				}
				String str = String.valueOf(oushu*2+jishu);
				
				if(str.length()>1){
					str = str.substring(str.length()-1, str.length());
					if(pn.endsWith(str)){
						
					}else{
						data.put("status", 4);
						data.put("description", "设备条码未找到");
						msg.setData(data);
						return msg;
					}
				}else{
					data.put("status", 4);
					data.put("description", "设备条码未找到");
					msg.setData(data);
					return msg;
				}
				
			}
			SpTdcode po = queryTdCodeByPn(pn);
			if(po==null){
				data.put("status", 4);
				data.put("description", "设备条码未找到或设备二维码与条码未绑定");
				msg.setData(data);
				return msg;
			}
			data.put("status", 1);
			data.put("description", "查询成功 ");
			data.put("tdCode", po.getTdcodemd5());
			msg.setData(data);
			return msg;
		}catch(Exception e){
			data.put("status", 0);
			data.put("description", "查询失败 ");
			msg.setData(data);
			return msg;
		}
		
	}
	public  SpTdcode  queryTdCodeByPn(String pn){
		
		SpTdcode po = null;
		try {
			JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
			po = jdbc.queryForObject(ApplicationContextTool.getDataSource(),
					"select tdcodemd5 from sp_tdcode where barcode = ? ",
					SpTdcode.class, new String[] { pn });
			if (po != null) {
				return po;
			}
		} catch (Exception e) {
			throw new RuntimeException("sp_tdcode查询异常", e);
		}

		return null;
	}
}
