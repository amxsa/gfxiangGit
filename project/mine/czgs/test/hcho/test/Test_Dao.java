package hcho.test;

import static org.junit.Assert.*;
import hcho.test.entity.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_Dao {

	private ClassPathXmlApplicationContext ac;
	@Before
	public void hello(){
		ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void test() {
		SessionFactory sf = (SessionFactory) ac.getBean("sessionFactory");
		Session session = sf.openSession();
		
		session.save(new Person("test1"));
		session.beginTransaction();
		session.getTransaction().commit();
		session.close();
	}

}
