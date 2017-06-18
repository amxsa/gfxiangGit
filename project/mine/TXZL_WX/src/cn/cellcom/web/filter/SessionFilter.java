package cn.cellcom.web.filter;

import java.io.IOException;
import java.util.Set;

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

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.common.data.PrintTool;
import cn.cellcom.web.spring.ApplicationContextTool;
import cn.cellcom.wechat.bus.BindWechatBus;
import cn.cellcom.wechat.bus.OpenIDBus;
import cn.cellcom.wechat.bus.RegisterBus;
import cn.cellcom.wechat.common.Env;
import cn.cellcom.wechat.common.WechatTool;
import cn.cellcom.wechat.po.DataMsg;
import cn.cellcom.wechat.po.Login;
import cn.cellcom.wechat.po.TBindWechat;
import cn.cellcom.wechat.po.TRegister;
import cn.cellcom.wechat.po.UserInfo;

public class SessionFilter implements Filter {

	private static final Log log = LogFactory.getLog(SessionFilter.class);

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

		if (uri.startsWith("//"))
			uri = uri.substring(1, uri.length());
		// || StringUtils.endsWith(uri, "jsp")
		if (StringUtils.endsWith(uri, "do") || StringUtils.endsWith(uri, "jsp")) {
			String openid = null;
			if (session.getAttribute("openid") == null) {
				openid = request.getParameter("openid");
			} else {
				openid = String.valueOf(session.getAttribute("openid"));
			}
			log.info("url:" + uri + ",openid:" + openid);
			if (contains(uri, Env.ALLOW_ALL_URL)) {
				chain.doFilter(req, resp);
				return;
			} else if (contains(uri, Env.ALLOW_WECHAT_URL)) {
				if (session.getAttribute("openid") == null) {
					OpenIDBus bus = new OpenIDBus();
					boolean bool = bus.addOpenid(null, openid, false);
					if (!bool) {
						sendError(request, response, null);
					} else {
						session.setAttribute("openid", openid);
						chain.doFilter(req, resp);
						return;
					}
				} else {
					chain.doFilter(req, resp);
					return;
				}

			} else if (contains(uri, Env.ALLOW_WECHAT_BIND_URL)) {
				if (session.getAttribute("login") == null) {
					JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
					try {
						OpenIDBus bus = new OpenIDBus();
						boolean bool = bus.addOpenid(null, openid, false);
						if (!bool) {
							sendError(request, response, null);
						} else {
							session.setAttribute("openid", openid);
							BindWechatBus bind = new BindWechatBus();
							TBindWechat po = bind.queryByWechatNo(null, openid);
							if (po == null) {
								response.sendRedirect(request.getContextPath()
										+ "/wechat/bindWechat.jsp");
							} else {
								RegisterBus registerBus = new RegisterBus();
								TRegister register = registerBus.queryByRegNo(
										null, po.getRegNo(), null);
								if (register != null) {
									if ("Y".equals(register.getStatus())) {
										Login login = registerBus.setLogin(
												register, openid);
										if (login != null) {
											session.setAttribute("login", login);
										}
										chain.doFilter(req, resp);
									} else {
										jdbc.update(
												ApplicationContextTool
														.getDataSource(),
												"delete from t_bind_wechat where reg_no = ?",
												new String[] { register
														.getNumber() });
										response.sendRedirect(request
												.getContextPath()
												+ "/wechat/bindWechat.jsp");
									}
								} else {
									response.sendRedirect(request
											.getContextPath()
											+ "/wechat/bindWechat.jsp");
								}
							}
						}

					} catch (Exception e) {
						sendError(request, response, null);
					}
				} else {
					chain.doFilter(req, resp);
				}
			} else {
				sendError(request, response, null);
			}
		} else {
			chain.doFilter(req, resp);
			return;
		}
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {
	}

	private boolean contains(String uri, Set<String> urls) {
		if (StringUtils.isBlank(uri))
			return false;
		if (urls.size() > 0) {
			for (String value : urls) {
				if (uri.indexOf(value) > -1) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	private void sendError(HttpServletRequest request,
			HttpServletResponse response, String errorMsg)
			throws ServletException, IOException {
		if (errorMsg == null) {
			errorMsg = "请重新点击菜单使用";
		}
		if (StringUtils.isNotBlank(request.getHeader("X-Requested-With"))) {
			DataMsg msg = new DataMsg(false, "请重新点击菜单使用");
			PrintTool.print(response, msg.getMsg(), null);
			return;
		} else {
			request.setAttribute("result", errorMsg);
			request.getRequestDispatcher("/common/error.jsp").forward(request,
					response);
		}

	}
}
