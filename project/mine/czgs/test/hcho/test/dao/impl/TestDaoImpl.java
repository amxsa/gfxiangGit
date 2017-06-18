package hcho.test.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import hcho.test.dao.TestDao;

public class TestDaoImpl extends HibernateDaoSupport implements TestDao {

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("hello world");
	}

}
