package com.gf.ims.web.acion;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
public class FileUploadAction{
	Logger log =Logger.getLogger(FileUploadAction.class);
	
	@RequestMapping(value="/imageUpload")
	public String toImageUploadPage(){
	
		return "/pages/file/fileUpload";
	}
	
}
