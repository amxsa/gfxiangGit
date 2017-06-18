package cn.cellcom.wechat.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.common.xml.XMLTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.OpenIDBus;
import cn.cellcom.wechat.common.ConfLoad;
import cn.cellcom.wechat.common.WechatTool;
import cn.cellcom.wechat.po.Articles;
import cn.cellcom.wechat.po.TOpenid;
import cn.cellcom.wechat.po.UserInfo;

public class WechatServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(WechatServlet.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info(">>>>>>>>>>>>>>>>>>");
		String echostr = req.getParameter("echostr");//
		if (echostr != null)
			resp.getWriter().write(echostr);
		else
			doPost(req, resp);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(".......");
		Map<String, String> map = null;
		try {
			map = XMLTool.parseXml(req);
		} catch (DocumentException e) {
			log.error("", e);
		}
		if (map != null) {
			String str = XMLTool.map2Xml(map, false);
			log.info("微信自动参数xml:");
			log.info(str);
			String result = replyMenu(map, req);
			log.info("微信菜单XML:" + result);
			PrintTool.print(resp, result, null);
		} else {
			resp.getWriter().print("");
		}
	}

	private String replyMenu(Map<String, String> map, HttpServletRequest request) {
		// 根据Event判断第一关注和取消关注
		String Event = map.get("Event");
		String EventKey = map.get("EventKey");
		String FromUserName = map.get("FromUserName");
		String ToUserName = map.get("ToUserName");
		if (ConfLoad.getProperty("WECHAT_SERVICE_NO").equals(ToUserName)) {
			// 菜单事件
			if ("subscribe".equals(Event)) {
				// 写入t_openid
				JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
				OpenIDBus openIdBus = new OpenIDBus();
				openIdBus.addOpenid(jdbc, FromUserName, true);
				StringBuffer str = new StringBuffer();
				str.append("亲，小秘终于等到您啦！绑定天翼号码马上享：");
				str.append("\n");
				str.append("1、漏话记录查询").append("\n");
				str.append("2、随时呼叫转移").append("\n");
				str.append("3、更多活动和优惠").append("\n");
				str.append("点击").append("&lt;a href=\"")
						.append(ConfLoad.getProperty("URL"))
						.append("user/bindWechat/checkBind.do?openid=");
				str.append(FromUserName).append("\"&gt;这里")
						.append("&lt;/a&gt;绑定");
				return buildMsg(FromUserName, ToUserName, str.toString());
			} else if ("unsubscribe".equals(Event)) {
				OpenIDBus openIdBus = new OpenIDBus();
				openIdBus.delelteOpenid(
						(JDBC) ApplicationContextTool.getBean("jdbc"),
						FromUserName);
				return "";
			} else {
				if ("event".equals(map.get("MsgType"))) {
					if (EventKey != null) {
						List<Articles> articles = new ArrayList<Articles>();
						if ("V3001".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/zhanghao.jpg");
							po1.setTitle("号码绑定");
							po1.setDescription("绑定手机号后，可直接查看漏话，设置呼转等操作");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/bindWechat/checkBind.do?openid="
									+ FromUserName);
							articles.add(po1);
							String str = buildMsg(FromUserName, ToUserName, articles);
							return str;
						}
						if ("V3002".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/huodong.jpg");
							po1.setTitle("精彩活动");
							po1.setDescription("");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/activity/activity.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						}
						if ("V3003".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/yewubanli.jpg");
							po1.setTitle("业务办理");
							po1.setDescription("");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/busHandle/busHandle.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} 
						if ("V3004".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/caiyin.jpg");
							po1.setTitle("我的彩印");
							po1.setDescription("");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/colorPrinting/queryMyColorPrinting.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} 
						
						
						if ("V1001".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/louhua1.jpg");
							po1.setTitle("最近漏话");
							po1.setDescription("可查看您最近的漏话记录");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/leaveword/showLeaveword.do?openid="
									+ FromUserName + "&amp;type=2");
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} else if ("V1002".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/louhua2.jpg");
							po1.setTitle("漏话提醒");
							po1.setDescription("可查看您最近的漏话信息");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/leaveword/leavewordMsg.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} else if ("V2001".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/huzhuan.jpg");
							po1.setTitle("呼转设置");
							po1.setDescription("可直接设置呼转号码");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/callForward/showCallForward.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} else if ("V2002".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/liuyan.jpg");
							po1.setTitle("我的留言");
							po1.setDescription("可查看最近的留言内容");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/leaveword/showLeavewordTxt.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} else if ("V2003".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/mishu.jpg");
							po1.setTitle("人工秘书");
							po1.setDescription("");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/call/callSecretary.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} else if ("V2004".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/limaoguaji.jpg");
							po1.setTitle("礼貌挂机");
							po1.setDescription("当您提前设置了指定时间的某种挂机场景，用户拨打您的号码时，如果拒接，对方将听到您设置的场景提示音");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "user/politeOff/showPoliteOff.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						} else if ("V2005".equals(EventKey)) {
							Articles po1 = new Articles();
							po1.setPicUrl(ConfLoad.getProperty("URL")
									+ "images/wechat/yifengou.jpg");
							po1.setTitle("一分购");
							po1.setDescription("");
							po1.setUrl(ConfLoad.getProperty("URL")
									+ "yfg/GoodsAction_showGoods.do?openid="
									+ FromUserName);
							articles.add(po1);
							return buildMsg(FromUserName, ToUserName, articles);
						}else {
							return buildMsgURL(FromUserName, ToUserName,
									EventKey);
						}
						
					}
				} else if ("text".equals(map.get("MsgType"))
						|| "image".equals(map.get("MsgType"))) {
					return buildMsg(FromUserName, ToUserName, "您好，请点击菜单使用");
				}
			}

		}

		return null;
	}

	private String buildMsg(String FromUserName, String ToUserName,
			String content) {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<ToUserName>").append(FromUserName).append("</ToUserName>");
		str.append("<FromUserName>").append(ToUserName)
				.append("</FromUserName>");
		int a = (int) System.currentTimeMillis();
		str.append("<CreateTime>").append(a).append("</CreateTime>");
		str.append("<MsgType>text</MsgType>");
		str.append("<Content>").append(content).append("</Content>");
		str.append("</xml>");
		return str.toString();
	}

	private String buildMsgURL(String FromUserName, String ToUserName,
			String url) {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<ToUserName>").append(ToUserName).append("</ToUserName>");
		str.append("<FromUserName>").append(FromUserName)
				.append("</FromUserName>");
		int a = (int) System.currentTimeMillis();
		str.append("<CreateTime>").append(a).append("</CreateTime>");
		str.append("<MsgType>event</MsgType>");
		str.append("<Event>view</Event>");
		str.append("<EventKey>").append(url).append("</EventKey>");
		str.append("</xml>");
		return str.toString();
	}

	private String buildMsg(String FromUserName, String ToUserName,
			List<Articles> articles) {
		StringBuffer str = new StringBuffer();
		str.append("<xml>");
		str.append("<ToUserName>").append(FromUserName).append("</ToUserName>");
		str.append("<FromUserName>").append(ToUserName)
				.append("</FromUserName>");
		int a = (int) System.currentTimeMillis();
		str.append("<CreateTime>").append(a).append("</CreateTime>");
		str.append("<MsgType>news</MsgType>");
		str.append("<ArticleCount>").append(articles.size())
				.append("</ArticleCount>");
		str.append("<Articles>");
		for (int i = 0; i < articles.size(); i++) {
			Articles po = articles.get(i);
			str.append("<item>");
			if (StringUtils.isNotBlank(po.getTitle()))
				str.append("<Title>").append(po.getTitle()).append("</Title>");
			if (StringUtils.isNotBlank(po.getDescription()))
				str.append("<Description>").append(po.getDescription())
						.append("</Description>");
			if (StringUtils.isNotBlank(po.getPicUrl()))
				str.append("<PicUrl>").append(po.getPicUrl())
						.append("</PicUrl>");
			if (StringUtils.isNotBlank(po.getUrl()))
				str.append("<Url>").append(po.getUrl()).append("</Url>");
			str.append("</item>");
		}
		str.append("</Articles>");
		str.append("</xml>");
		return str.toString();
	}

}
