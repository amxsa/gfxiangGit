package cn.cellcom.wechat.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/busHandle")
public class BusHandleControl {
	@RequestMapping("/busHandle")
	public String busHandle(){
		return "/user/busHandle";
	}
}
