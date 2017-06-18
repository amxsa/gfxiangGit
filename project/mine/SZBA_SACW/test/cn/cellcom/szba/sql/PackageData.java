package cn.cellcom.szba.sql;

import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.open.db.JDBC;

public class PackageData {

	ClassPathXmlApplicationContext ap =null;
	@Before
	public void init() {
		 ap=new ClassPathXmlApplicationContext("spring.xml");
	}
	@Test
	public  void test() throws Exception{
		
		/*JDBC jdbc = (JDBC)ap.getBean("jdbc");
		DataSource ds = (DataSource) ap.getBean("dataSource");
		Date date = new Date();
		jdbc.update(ds, "insert into table1 (id, menu_id, role_id, create_time) values(?, ?, ?, ?)", 3, 1, 1, date);
		*/
		System.out.println("{\"createDate\":\"20151119171835\",\"extractID\":\"fe5784f5-ad85-4b2c-bb47-2bbb124de736\",\"polices\":[{\"departmentID\":2044,\"id\":\"4b5aa945-d7d4-4494-91f3-5e6c580e2b6e\",\"name\":\"15\",\"type\":0},{\"departmentID\":2044,\"id\":\"3a9eece4-48fd-4758-b741-8e088733bb73\",\"name\":\"楼\",\"type\":0}],\"properties\":[{\"characteristic\":\"拖\",\"id\":\"ed7608e4-a9a9-4bc4-b1ba-327facc54bb9\",\"method\":\"\",\"name\":\"看看\",\"photographs\":[{\"description\":\"楼\",\"localUrl\":\"/storage/emulated/0/tencent/MicroMsg/1444740312026_42a9ca80.jpg\",\"picID\":\"4d20a6e8-7140-4447-947b-068866f4705f\",\"propertyId\":\"ed7608e4-a9a9-4bc4-b1ba-327facc54bb9\",\"uploadDate\":\"20151119171859\",\"type\":21}],\"position\":\"\",\"quantity\":\"2\",\"remark\":\"\",\"unit\":\"克\"}],\"witnesses\":[{\"id\":\"e8e74466-6f22-47fc-9862-96d5fd5a7011\",\"idNum\":\"183455554\",\"name\":\"来看看\",\"type\":0}]}");
	}
}
