package com.gf.ims.pay.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @FileName：MyBashController.java
 * @Description：基类controller
 * @author lzs
 * @version 1.0
 */
public abstract class MyBashController {
	public Logger log = Logger.getLogger(this.getClass());
	/**
	 * 记录接口请求参数的日志 日志格式："类名称<|>requestMapping:请求路径<|>parameters:请求参数json格式
	 * 
	 * @param requestMapping
	 *            请求路径
	 * @param request
	 * @param response
	 */
	protected void requestParametersLog(String requestMapping,HttpServletRequest request) {
		try {
			Enumeration pNames = request.getParameterNames();
			Map map = new HashMap<String, String>();
			if (StringUtils.isNotBlank(ObjectUtils.toString(pNames))) {
				while (pNames.hasMoreElements()) {
					String name = (String) pNames.nextElement();
					map.put(name, request.getParameter(name));
				}
			}
			JSONObject requestJson = JSONObject.fromObject(map);
			log.info(this.getClass().getName() + "<|>requestMapping:"
					+ requestMapping + "<|>parameters:"
					+ requestJson.toString());
		} catch (Exception e) {
		}
	}
}
