package cn.cellcom.wechat.bus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.cellcom.common.HttpUrlClient;
import cn.cellcom.wechat.common.ConfLoad;
import cn.cellcom.wechat.common.WechatTool;

public class WechatBus {
	public String getToken() {
		WechatTool tool = new WechatTool();
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
		url.append(tool.getAppId()).append("&secret=")
				.append(tool.getAppSecret());
		String result = HttpUrlClient.doGet("微信获取全局Token", url.toString(),
				null, "UTF-8", 5000, true);
		if (result != null) {
			JSONObject obj = JSONObject.fromObject(result);
			if (obj != null && obj.containsKey("access_token")) {
				return obj.getString("access_token");
			}
		}
		return null;
	}

	

	// 点击菜单推图文信息
	public String createMenu2PicTxt() {
		//String token = getToken();
		//System.out.println("token:" + token);
		JSONArray array3 = new JSONArray();
		JSONObject obj3_1 = new JSONObject();
		obj3_1.put("type", "click");
		obj3_1.put("name", "号码绑定");
		obj3_1.put("key", "V3001");

		JSONObject obj3_2 = new JSONObject();
		obj3_2.put("type", "click");
		obj3_2.put("name", "精彩活动");
		obj3_2.put("key", "V3002");

		JSONObject obj3_3 = new JSONObject();
		obj3_3.put("type", "click");
		obj3_3.put("name", "业务办理");
		obj3_3.put("key", "V3003");
		
		JSONObject obj3_4 = new JSONObject();
		obj3_4.put("type", "click");
		obj3_4.put("name", "我的彩印");
		obj3_4.put("key", "V3004");
		
		JSONObject obj3_5= new JSONObject();
		obj3_5.put("type", "click");
		obj3_5.put("name", "多方通话");
		obj3_5.put("key",  "V3005");

		array3.add(obj3_1);
		array3.add(obj3_2);
		array3.add(obj3_3);
		array3.add(obj3_4);
		array3.add(obj3_5);

		

		JSONObject obj3 = new JSONObject();
		obj3.put("name", "更多");
		obj3.put("sub_button", array3);

		JSONArray array4 = new JSONArray();
		JSONObject obj4_1 = new JSONObject();
		obj4_1.put("type", "click");
		obj4_1.put("name", "呼转设置");
		obj4_1.put("key", "V2001");

		JSONObject obj4_2 = new JSONObject();
		obj4_2.put("type", "click");
		obj4_2.put("name", "我的留言");
		obj4_2.put("key", "V2002");

		JSONObject obj4_3 = new JSONObject();
		obj4_3.put("type", "click");
		obj4_3.put("name", "人工秘书");
		obj4_3.put("key", "V2003");

		JSONObject obj4_4 = new JSONObject();
		obj4_4.put("type", "click");
		obj4_4.put("name", "礼貌挂机");
		obj4_4.put("key", "V2004");
		/**
		JSONObject obj4_5 = new JSONObject();
		obj4_5.put("type", "click");
		obj4_5.put("name", "一分购");
		obj4_5.put("key", "V2005");
		*/

		array4.add(obj4_1);
		array4.add(obj4_2);
		array4.add(obj4_3);
		array4.add(obj4_4);
		//array4.add(obj4_5);

		JSONObject obj4 = new JSONObject();

		obj4.put("name", "我的助理");
		obj4.put("sub_button", array4);

		JSONArray array5 = new JSONArray();
		JSONObject obj5_1 = new JSONObject();
		obj5_1.put("type", "click");
		obj5_1.put("name", "最近漏话");
		obj5_1.put("key", "V1001");

		JSONObject obj5_2 = new JSONObject();
		obj5_2.put("type", "click");
		obj5_2.put("name", "漏话提醒");
		obj5_2.put("key", "V1002");

		array5.add(obj5_1);
		array5.add(obj5_2);
		JSONObject obj5 = new JSONObject();

		obj5.put("name", "我的漏话");
		obj5.put("sub_button", array5);

		JSONArray array2 = new JSONArray();
		array2.add(obj5);
		array2.add(obj4);
		array2.add(obj3);
		JSONObject obj6 = new JSONObject();
		obj6.put("button", array2);
		System.out.println(obj6.toString());
		return obj6.toString();

	}
	
	// 点击菜单直接访问页面
	public String createMenu2Url() throws UnsupportedEncodingException {
	
		
		JSONArray array3 = new JSONArray();
		JSONObject obj3_1 = new JSONObject();
		obj3_1.put("type", "view");
		obj3_1.put("name", "号码绑定");
		
		obj3_1.put("url", ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=BindWechatAction_checkBind");

		JSONObject obj3_2 = new JSONObject();
		obj3_2.put("type", "view");
		obj3_2.put("name", "精彩活动");
		obj3_2.put("url", ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=ActivityAction_activity");

		JSONObject obj3_3 = new JSONObject();
		obj3_3.put("type", "view");
		obj3_3.put("name", "业务办理");
		obj3_3.put("url",  ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=BusHandleAction_busHandle");
		
		

		array3.add(obj3_1);
		array3.add(obj3_2);
		array3.add(obj3_3);


		JSONObject obj3 = new JSONObject();
		obj3.put("name", "更多");
		obj3.put("sub_button", array3);

		JSONArray array4 = new JSONArray();
		JSONObject obj4_1 = new JSONObject();
		obj4_1.put("type", "view");
		obj4_1.put("name", "呼转设置");
		obj4_1.put("url",  ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=CallforwardAction_showCallforward");

		JSONObject obj4_2 = new JSONObject();
		obj4_2.put("type", "view");
		obj4_2.put("name", "我的留言");
		obj4_2.put("url", ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=LeavewordAction_showLeavewordTxt");

		JSONObject obj4_3 = new JSONObject();
		obj4_3.put("type", "view");
		obj4_3.put("name", "人工秘书");
		obj4_3.put("url", ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=CallAction_callSecretary");

		JSONObject obj4_4 = new JSONObject();
		obj4_4.put("type", "view");
		obj4_4.put("name", "礼貌挂机");
		obj4_4.put("url", ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=PoliteOffAction_showPoliteOff");

		array4.add(obj4_1);
		array4.add(obj4_2);
		array4.add(obj4_3);
		array4.add(obj4_4);

		JSONObject obj4 = new JSONObject();

		obj4.put("name", "我的助理");
		obj4.put("sub_button", array4);

		JSONArray array5 = new JSONArray();
		JSONObject obj5_1 = new JSONObject();
		obj5_1.put("type", "view");
		obj5_1.put("name", "最近漏话" );
		obj5_1.put("url", ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=LeavewordAction_showLeaveword&type=2");

		JSONObject obj5_2 = new JSONObject();
		obj5_2.put("type", "view");
		obj5_2.put("name", "漏话提醒");
		obj5_2.put("url", ConfLoad.getProperty("URL")+"user/WechatAction_goMenuUrl.do?method=LeavewordAction_leavewordMsg");

		array5.add(obj5_1);
		array5.add(obj5_2);
		JSONObject obj5 = new JSONObject();

		obj5.put("name", "我的漏话");
		obj5.put("sub_button", array5);

		JSONArray array2 = new JSONArray();
		array2.add(obj5);
		array2.add(obj4);
		array2.add(obj3);
		JSONObject obj6 = new JSONObject();
		obj6.put("button", array2);
		System.out.println(obj6.toString());
		return obj6.toString();

	}
	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		HttpUrlClient client =new HttpUrlClient();
//		String token = client.doPost("获取全局token", "http://palf.118114.cn/user/WechatAction_refreshToken.do", null, "utf-8", 5000, false);
//		System.out.println("token:" + token);
		WechatBus bus = new WechatBus();
		bus.createMenu2PicTxt();
//		String menu = bus.createMenu2PicTxt();
//		System.out.println(URLEncoder.encode("&"));
	
	}
}
