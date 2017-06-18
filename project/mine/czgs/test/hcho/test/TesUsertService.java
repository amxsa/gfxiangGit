package hcho.test;

import static org.junit.Assert.*;
import hcho.nsfw.user.service.UserService;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TesUsertService {

	private ClassPathXmlApplicationContext ac;
	@Before
	public void hello(){
		ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	@Test
	public void test(){
		UserService userService = (UserService) ac.getBean("userService");
		Set<String> set = userService.findPrivilegeById("402881e550198e400150198ed7f30000");
		for (String string : set) {
			System.out.println(string);
		}
	}

}
