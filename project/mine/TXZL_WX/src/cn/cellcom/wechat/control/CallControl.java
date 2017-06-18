package cn.cellcom.wechat.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/call")
public class CallControl {
	@RequestMapping("/callSecretary")
	public String callSecretary() {
		return "/user/callSecretary";
	}
}
