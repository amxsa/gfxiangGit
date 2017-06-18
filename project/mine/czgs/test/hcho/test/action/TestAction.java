package hcho.test.action;

import javax.annotation.Resource;

import hcho.test.service.TestService;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport {
	
	@Resource
	private TestService testService;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		testService.say();
		return "success";
	}

	
}
