package cn.cellcom.szba.filter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import cn.cellcom.szba.biz.TPropertyLogBiz;
import cn.cellcom.szba.biz.TVisitLogBiz;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.JsonUtil;
import cn.cellcom.szba.common.PropertyUtils;
import cn.cellcom.szba.databean.DisposalAndProcedure;
import cn.cellcom.szba.domain.TProperty;
import cn.cellcom.szba.domain.TVisitLog;
import cn.open.util.ArrayUtil;
import cn.open.util.CommonUtil;

import com.google.gson.reflect.TypeToken;

public class LogFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	// 记录用户访问情况

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request; // 获得HTTP请求的对象
		HttpSession session = httpRequest.getSession();

		// 记录财物轨迹
		recordPropertyLog(httpRequest);
		
		//记录操作日志
		recordVisitLog(httpRequest,session);
		
		filterChain.doFilter(request, response);
	}

	// 记录财物轨迹
	private void recordPropertyLog(HttpServletRequest httpRequest) {
		String servletPath = httpRequest.getServletPath();

		// 记录财物的修改，删除
		String proOperateType = Env.PROOPERATETYPE.get(servletPath);
		if (proOperateType != null) {

			String proId = httpRequest.getParameter("proId"); // 财物id
			if (StringUtils.isNotBlank(proId)) {
				try {
					TPropertyLogBiz.save(httpRequest, proId, proOperateType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 财物各种申请记录轨迹
		String dpResultStr = httpRequest.getParameter("dpResultStr");
		if (StringUtils.isNotBlank(dpResultStr)) {
			DisposalAndProcedure dpResult = (DisposalAndProcedure) JsonUtil
					.jsonToBean(dpResultStr, DisposalAndProcedure.class);
			proOperateType = Env.PROOPERATETYPE.get(dpResult.getProcedure()
					.getCode());
			if (StringUtils.isNotBlank(proOperateType)) {
				String proJson = httpRequest.getParameter("proJson");
				if(!PropertyUtils.isProJsonBlank(proJson)){
					List<TProperty> propertyList = new ArrayList<TProperty>();

					Type type = new TypeToken<List<TProperty>>() {
					}.getType();
					propertyList = JsonUtil.jsonToList(proJson, type);
					if (propertyList != null && !propertyList.isEmpty()) {
						for (TProperty p : propertyList) {
							TPropertyLogBiz.save(httpRequest, p.getProId(),
									proOperateType);
						}
					}
				}
			}
		}
	}

	// 记录操作日志
	private void recordVisitLog(HttpServletRequest httpRequest,HttpSession session) {
		String servletPath = httpRequest.getServletPath();
		
		if (StringUtils.endsWith(servletPath, ".do")) {
			// 请求ip地址
			String remoteAddr = httpRequest.getRemoteAddr();
			// 登录人
			String account = "";
			// seesionId
			String sessionId = "";
			if (session.getAttribute("loginForm") != null) {
				sessionId = session.getId();
				account = ((Map) session.getAttribute("loginForm")).get(
						"ACCOUNT").toString();
			}

			// 请求路径
			String uri = httpRequest.getRequestURI();
			// 请求参数
			String params = getRequestLine(httpRequest);
			// 请求类型
			String httpMethod = httpRequest.getMethod();

			TVisitLog visitLog = new TVisitLog();
			visitLog.setAccount(account);
			visitLog.setHttpMethod(httpMethod);
			visitLog.setIp(remoteAddr);
			visitLog.setSessionId(sessionId);
			visitLog.setUrl(uri + "?" + params);
			TVisitLogBiz.insert(visitLog);
		}
	}

	public static String getRequestLine(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = getParamterValue(request, paramName);
			String currentStr = paramName + "=" + paramValue;
			CommonUtil.makeupArrayStr(sb, currentStr, "&");
		}
		return sb.toString();
	}

	public static String getParamterValue(HttpServletRequest request,
			String name) {
		String[] values = request.getParameterValues(name);
		if (ArrayUtil.isEmpty(values)) {
			return "";
		} else if (values.length == 1) {
			return values[0];
		} else {
			return CommonUtil.createArrayStr("[", "]", ",", false, values);
		}
	}
}