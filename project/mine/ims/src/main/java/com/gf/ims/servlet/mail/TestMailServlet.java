package com.gf.ims.servlet.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gf.ims.common.util.ExportexcelWithSimpleCall;
import com.gf.ims.common.util.ExportexcelWithSimpleCall.RowCallBack;
import com.gf.ims.common.util.MailUtil;
import com.gf.ims.common.util.ServletMessageReq;
import com.gf.ims.common.util.ServletUtil;
import com.gf.ims.service.PoiService;
import com.gf.imsCommon.other.module.User;

import net.sf.json.JSONObject;

/**
 * 用户订购商品，保存商品订单接口
 */
public class TestMailServlet extends HttpServlet {

	private static final long serialVersionUID = -6609873050420278015L;
	Logger log = Logger.getLogger(TestMailServlet.class);
	private PoiService poiService;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		poiService = (PoiService) ctx.getBean("poiService");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 组装响应数据并发送
		exractRspInvoke(req, resp);
	}

	/**
	 * 处理请求
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private synchronized void exractRspInvoke(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String result = "";
		String reason = "";
		// 程序完成标志
		boolean endFlag = false;
		// 用户对象
		JSONObject body = null;
		/**
		 * 登录账号
		 */
		String orderNo = "";// 订单流水号
		// html5回调参数
		String callbackName = null;
		callbackName = req.getParameter("jsoncallback");
		String str = req.getParameter("str");
		String reqStr = "";
		try {
			// 客户端请求JSON串
			if (null == str) {
				reqStr = ServletUtil.praseRequst(req);
			} else {
				reqStr = java.net.URLDecoder.decode(str, "UTF-8");
			}
			log.info("reqStr:" + reqStr);
			ServletMessageReq smr = new ServletMessageReq(reqStr);
			body = smr.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			endFlag = true;
		}

		if (!endFlag) {
			try {
				List<User> userMobileList = poiService.getUserMobile();
				String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
				User user = userMobileList.get(0);
				List<User> list=new ArrayList<>(); 
				list.add(user);
				String[] headers = new String[] { "手机号码" };
				ExportexcelWithSimpleCall<User> ep = new ExportexcelWithSimpleCall<User>();
				HSSFWorkbook workbook = ep.getExcel("用户手机号码", headers, list, new RowCallBack() {
					@Override
					public void invoke(Object t, HSSFRow row, HSSFCellStyle style) {
						User user = (User) t;
						ExportexcelWithSimpleCall.createCell(row, style, 0).setCellValue(user.getMobile());
					}
				});
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				workbook.write(baos);
				baos.flush();
				byte[] bt = baos.toByteArray();
				InputStream is = new ByteArrayInputStream(bt, 0, bt.length);
				baos.close();

				Map<InputStream, String> inputStreamMap=new HashMap<InputStream, String>();
//				inputStreamMap.put(is, "用户手机号码.xls|application/msexcel");
				Map<String, String> fileMap=new HashMap<String, String>();
				fileMap.put("C:\\Users\\Administrator\\Desktop\\aa.txt", "aa.txt");
				
				File file = File.createTempFile("prefix", ".suffix");
				inputstreamtofile(is, file);
				fileMap.put(file.getAbsolutePath(), "临时文件.xls");
				MailUtil.sendEmailWithAttachment(true,"空腹的邮件", "哈哈哈标题", "xianggf@hori-gz.com", "空腹", "Gfxiang_tx10!", "1020009891@qq.com", "1020009891@qq.com", "哈哈哈", fileMap,inputStreamMap);
			} catch (Exception e) {
				e.printStackTrace();
				reason = e.getMessage();
			}

		}
		// 消息响应
		Map resultMap = new HashMap<String, String>();
		resultMap.put("result", result);
		resultMap.put("reason", reason);
		// html5回调
		if (null != callbackName && !"".equals(callbackName)) {
			String renderStr = callbackName + "(" + JSONObject.fromObject(resultMap).toString() + ")";
			ServletUtil.sendResponse(resp, renderStr);
		} else {
			ServletUtil.sendResponse(resp, JSONObject.fromObject(resultMap).toString());
		}
	}

	public void inputstreamtofile(InputStream ins, File file) throws Exception {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
	}

	
}
