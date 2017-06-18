package com.jlit.xms.web.action;


import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/base")
public class BaseAction {
	
    protected transient final Log log = LogFactory.getLog(getClass());

	@Resource
	ServletContext application;
	
	@RequestMapping("/goURL/{folder1}/{folder2}/{file}")
	public String goURL(@PathVariable String folder1,@PathVariable String folder2,@PathVariable String file){
		
		return "/"+folder1+"/"+folder2+"/"+file;
		
	}
	
	
}
