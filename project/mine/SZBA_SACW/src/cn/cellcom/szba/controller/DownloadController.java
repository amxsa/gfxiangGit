package cn.cellcom.szba.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cellcom.szba.common.Env;
import cn.cellcom.szba.common.FileTool;

@Controller
@RequestMapping("/down")
public class DownloadController {

	@RequestMapping("/downAttachment")
	public String downFile(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		StringBuffer str = new StringBuffer();
		str.append(Env.ENV_PRO.get("file_dir"));
		str.append(fileName);
		
		File file =new File(str.toString());
		if(file.exists()){
			FileTool.download(response, file);
			return null;
		}else{
			return "common/fail";
		}
	}
}
