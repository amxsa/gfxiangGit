package com.gf.ims.web.websocket;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/socket")
public class SocketAction {
	
    protected transient final Log log = LogFactory.getLog(getClass());

	@RequestMapping("test")
	public String test(){
		
		return "/pages/test/websocket/test";
		
	}
}
