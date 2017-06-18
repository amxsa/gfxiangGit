package hcho.test.service.impl;

import hcho.test.dao.TestDao;
import hcho.test.service.TestService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Resource
	private TestDao testDao;
	@Override
	public void say() {
		// TODO Auto-generated method stub
		testDao.say();
	}

}
