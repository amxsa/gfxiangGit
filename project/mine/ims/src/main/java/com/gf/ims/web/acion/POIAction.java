package com.gf.ims.web.acion;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gf.ims.common.util.ExportexcelWithSimpleCall;
import com.gf.ims.common.util.ExportexcelWithSimpleCall.RowCallBack;
import com.gf.ims.service.PoiService;
import com.gf.imsCommon.other.module.User;

@Controller
@RequestMapping("/poi")
public class POIAction{
	Logger log =Logger.getLogger(POIAction.class);
	@Resource
	private PoiService poiService;
	
	@RequestMapping(value="/poiPage")
	public String poiPage(){
		
		return "/pages/test/poi/test";
	}
	
	@RequestMapping(value="/exportUser")
	public void exportUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<User> userMobileList=poiService.getUserMobile();
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		StringBuffer sb=new StringBuffer("用户手机号码").append(date).append(".xls");
		String exportFileName=sb.toString();
		OutputStream out=getOutputStreamByDownload(exportFileName, response,request);
		String[]headers=new String[]{"手机号码"};
    	ExportexcelWithSimpleCall<User> ep=new ExportexcelWithSimpleCall<User>();
		ep.exportExcel("用户手机号码", headers, userMobileList, out, new RowCallBack(){
			@Override
			public void invoke(Object t, HSSFRow row,HSSFCellStyle style) {
				User user = (User) t;
				ExportexcelWithSimpleCall.createCell(row , style,0).setCellValue(user.getMobile());
			}
		});
		 out.close();
	}
	
	public static  OutputStream getOutputStreamByDownload(String fileName,HttpServletResponse response,HttpServletRequest request){
		response.reset();
		response.setContentType("application/force-download");
		//根据不同浏览器输出
		String agent = request.getHeader("User-Agent");
		boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
		try {
			if (isMSIE) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		
		OutputStream os=null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return os;
	}
}
