package cn.cellcom.szba.filter;

import static cn.cellcom.szba.common.Env.ENV_PRO;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cellcom.szba.biz.TAccountBiz;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.domain.ClientAccount;
import cn.cellcom.szba.domain.TAccount;
import cn.open.encrypt.Md5Util;
import cn.open.http.HttpClientHelper;
import cn.open.http.HttpResult;

public class SessionFilter implements Filter {

	private static Log log = LogFactory.getLog(SessionFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String uri = request.getServletPath();
		// jsp或do,需要拦截
		if (StringUtils.endsWith(uri, "jsp") || StringUtils.endsWith(uri, "do")) {
			// 如果白名单有的。则放过
			if (Env.WHITE_URL.contains(uri)) {
				chain.doFilter(req, resp);
			} else {
				Object loginForm = session.getAttribute("loginForm");
				if (loginForm != null) {
					// 判断客户端是否失效
					if (uri.indexOf("/client/") != -1) {
						checkClientLoginOut(request, session);
					}

					chain.doFilter(req, resp);
				} else {
					response.sendRedirect(request.getContextPath()
							+ "/timeout.jsp");
				}
			}
		} else {
			chain.doFilter(req, resp);
		}
	}

	// 判断客户端是否登录失效，失效则重新登录
	private void checkClientLoginOut(HttpServletRequest request,
			HttpSession session) {
		try {
			ClientAccount ca = (ClientAccount) session
					.getAttribute("ClientAccount");
			String sessionID = ca.getSessionID();
			String timestamp = ca.getTimestamp();
			String authstring = ca.getAuthstring();

			Map<String, String> params = new HashMap<String, String>();
			params.put("sessionID", sessionID);
			params.put("timestamp", timestamp);
			params.put("authstring", authstring);
			String clientUrl = ENV_PRO.getProperty("client_url");
			String url = clientUrl + "/common/checkSession.do";
			HttpClientHelper hh = new HttpClientHelper();
			HttpResult hr = hh.post(url, params, null);
			String ret = hr.getText();
			log.info("client checkSession:ret=" + ret);
			ClientAccount cca = (ClientAccount) JsonUtil.jsonToBean(ret,
					ClientAccount.class);
			// 失效重新登录
			if (!"Y".equals(cca.getState())) {
				String accStr = ((Map) request.getSession().getAttribute(
						"loginForm")).get("ACCOUNT").toString();
				TAccountBiz biz = new TAccountBiz();
				TAccount account = biz.getPassword(accStr);
				params = new HashMap<String, String>();
				params.put("account", account.getAccount());

				String password = account.getPassword();
				params.put("password", password);

				timestamp = String.valueOf(new Date().getTime());
				params.put("timestamp", timestamp);

				String str = account.getAccount() + password + timestamp
						+ "XckyDx#ab";
				authstring = Md5Util.md5Encrypt(str);
				params.put("authstring", authstring);

				url = clientUrl + "/account/login.do";
				hh = new HttpClientHelper();
				hr = hh.post(url, params, null);
				ret = hr.getText();
				ca = (ClientAccount) JsonUtil.jsonToBean(ret,
						ClientAccount.class);
				ca.setTimestamp(timestamp);
				authstring = Md5Util.md5Encrypt(
						timestamp + "XckyDx#ab");
				ca.setAuthstring(authstring);
				session.setAttribute("ClientAccount", ca);
				log.info("client again login:ret=" + ret);
			}
		} catch (Exception ex) {
			log.error("", ex);
		}
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {
	}
}
