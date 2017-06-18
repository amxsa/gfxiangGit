package cn.cellcom.czt.bus;


import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.czt.po.Data;
import cn.cellcom.czt.po.TTdcodeOrder;
import cn.cellcom.web.spring.ApplicationContextTool;

public class OrderAlipayBus {
	public Data addTdCodeOrder(Data data, Map<String, String> params) {
		String checkResult = checkOrder(params);
		if (checkResult != null) {
			data.setMsg(checkResult);
			return data;
		}
		
		Date date = new Date();
		String address = params.get("city") + params.get("area")
				+ params.get("address");
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		System.out.println(">>>>>"+Integer.parseInt(params.get("unitPrice")));
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"insert into t_tdcode_order(orderid,unit_price,count,address,name,telephone,zipcode,state,openid,submit_time,spcode,configure,from_part,account) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[] { params.get("orderid"), Integer.parseInt(params.get("unitPrice")),1,address,
							params.get("name"), params.get("telephone"),
							params.get("zipcode"), "N", params.get("openid"),
							DateTool.getTimestamp(null),params.get("spcode"),params.get("configure"), params.get("fromPart"),params.get("account")});
			data.setState(true);
			data.setMsg("下单成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
		return data;
	}

	public TTdcodeOrder queryByOrderid(JDBC jdbc, String orderid) {
		if (StringUtils.isBlank(orderid))
			return null;
		if (jdbc == null) {
			jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		}
		try {
			TTdcodeOrder po = jdbc.queryForObject(
					ApplicationContextTool.getDataSource(),
					"select * from t_tdcode_order where orderid= ? ",
					TTdcodeOrder.class, new String[] { orderid });
			if (po != null) {
				return po;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String buildForm(String url,String orderid,String amount){
		StringBuffer str = new StringBuffer();
		str.append("<form action=\"").append(url).append("\" method =\"post\"> ");
		str.append("<input type=\"hidden\" name=\"orderid\" value=\"").append(orderid).append("\" />");
		str.append("<input type=\"hidden\" name=\"amount\" value=\"").append(amount).append("\" />");
		str.append("</form>");
		str.append("<script>document.forms[0].submit();</script>");
		return str.toString();
			
	}
	
	private String checkOrder(Map<String, String> params) {
		String city = params.get("city");
		String area = params.get("area");
		String address = params.get("address");
		String telephone = params.get("telephone");
		String zipcode = params.get("zipcode");
		String openid = params.get("openid");
		String name = params.get("name");
		if(StringUtils.isBlank(name)){
			return "姓名不能为空";
		}
		if(name.length()>10){
			return "姓名长度需少于10个字符";
		}
		if(StringUtils.isBlank(telephone)){
			return "手机不能为空";
		}
		String checkTel = PatternTool.checkStr(telephone, "^\\d{10,13}$", "手机号码格式错误");
		if(checkTel!=null)
			return checkTel;
		if(StringUtils.isBlank(city)){
			return "城市不能为空";
		}
		if(StringUtils.isBlank(area)){
			return "地区不能为空";
		}
		if(address.length()<5||address.length()>40)
			return "地址输入太短或太长";
		if(StringUtils.isBlank(zipcode)){
			return "邮编不能为空";
		}
		String checkZip = PatternTool.checkStr(zipcode, "^\\d{6}$", "邮编格式错误");
		if(checkZip!=null)
			return checkZip;
		
		return null;
	}
	
	public Data addOrderAlipay(Data data, Map<String, String> params) {
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		try {
			jdbc.update(
					ApplicationContextTool.getDataSource(),
					"insert into t_tdcode_order(orderid,unit_price,count,address,name,telephone,zipcode,state,openid,submit_time,spcode,configure,from_part,account) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[] { params.get("orderid"), params.get("unitPrice"),params.get("count"),params.get("address"),
							params.get("name"), params.get("telephone"),
							"", "N", "",
							DateTool.getTimestamp(null),params.get("spcode"),params.get("configure"), params.get("fromPart"),params.get("account")});
			data.setState(true);
			data.setMsg("下单成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
		return data;
	}

}
