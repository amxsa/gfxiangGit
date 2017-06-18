package cn.cellcom.szba.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.biz.TPictureBiz;
import cn.cellcom.szba.common.DataMsgTool;
import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.FileUpload;
import cn.cellcom.szba.common.PrintTool;
import cn.cellcom.szba.domain.TPicture;
import cn.cellcom.szba.vo.UploadFileVO;
import cn.open.util.DateUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RequestMapping("/upload")
@Controller
public class UploadController {

	private FileUpload upload = new FileUpload();
	private TPictureBiz picBiz = new TPictureBiz();
	private Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping("/attachment")
	public String uploadAttachment(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			UploadFileVO vo = upload.upload(request, "1", Env.ENV_PRO.getProperty("file_dir"));
			PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgSuccess(vo)), "json");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			try {
				PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgError(e, -108)), "json");
			} catch (IOException e1) {
				log.error(e1.getMessage(),e1);
			}
		}
		
		return null;
	}
	
	@RequestMapping("/image")
	public String uploadImage(HttpServletRequest request, HttpServletResponse response){
		try {
			log.info("===> 上传文件中……");
			UploadFileVO vo = upload.upload(request, "2", Env.ENV_PRO.getProperty("photo_dir"));
			
			PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgSuccess(vo)), "json");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			try {
				PrintTool.print(response, PrintTool.printJSON(DataMsgTool.getDataMsgError(e, -108)), "json");
			} catch (IOException e1) {
				log.error(e1.getMessage(),e1);
			}
		}
		return null;
	}
}
