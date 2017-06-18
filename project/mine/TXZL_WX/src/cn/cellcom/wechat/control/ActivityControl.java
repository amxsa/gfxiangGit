package cn.cellcom.wechat.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/activity")
public class ActivityControl {
	@RequestMapping("/activity")
	public String activity() {
		return "/user/activity";
	}
}
