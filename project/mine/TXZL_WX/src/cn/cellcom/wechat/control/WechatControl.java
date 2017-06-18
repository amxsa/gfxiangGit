package cn.cellcom.wechat.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.common.data.PrintTool;
import cn.cellcom.wechat.common.ConfLoad;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.WechatTool;

@Controller
@RequestMapping("/user/wechat")
public class WechatControl {
	@RequestMapping("/oauth")
	public String oauth(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		System.out.println(">>>>>>>>>>>>>code:" + code);
		if (StringUtils.isNotBlank(code)) {
			WechatTool wechat = new WechatTool();
			String openid = wechat.getCode2openid(code);
			if (openid != null) {
				String url = request.getParameter("url");
				// 连接中的其他参数也带上
				Enumeration<String> enumeration = request.getParameterNames();
				StringBuffer str = new StringBuffer(url);
				str.append("?openid=").append(openid);
				while (enumeration.hasMoreElements()) {
					String name = enumeration.nextElement();
					// state 和 code是微信返回来的，所以要去掉
					if (!"code".equals(name) && !"url".equals(name)
							& !"state".equals(name)) {
						str.append("&").append(name).append("=")
								.append(request.getParameter(name));
					}
				}
				request.setAttribute("url", str.toString());
				request.setAttribute("openid", openid);
				return "/user/oauth";
			}
		}
		request.setAttribute("result", "请点击菜单从新使用");
		return Env.ERROR;
	}

	@RequestMapping("/oauth")
	public String goMenuUrl(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String method = request.getParameter("method");
		// 连接中的其他参数也带上
		Enumeration<String> enumeration = request.getParameterNames();
		StringBuffer str = new StringBuffer();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			if (!"method".equals(name)) {
				str.append("&").append(name).append("=")
						.append(request.getParameter(name));
			}
		}
		WechatTool wechat = new WechatTool();
		if (method != null) {
			StringBuffer redirectUri = new StringBuffer();
			redirectUri.append(ConfLoad.getProperty("URL")).append(
					"user/WechatAction_oauth.do?url=");
			redirectUri.append(ConfLoad.getProperty("URL")).append("user/")
					.append(method).append(".do");
			if (str.length() > 0)
				;
			redirectUri.append(str.toString());
			String menuUrl = wechat.getOauthCode(redirectUri.toString());
			System.out.println(">>>>>>>+++++" + menuUrl.toString());
			request.setAttribute("menuUrl", menuUrl.toString());
		}
		return "/user/menuUrl";
	}

	@RequestMapping("/refreshToken")
	public String refreshToken(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		WechatTool wechat = new WechatTool();
		String token = wechat.getToken();
		PrintTool.print(response, token, null);
		return null;
	}
}
