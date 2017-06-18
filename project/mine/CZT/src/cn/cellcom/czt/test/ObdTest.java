package cn.cellcom.czt.test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import cn.cellcom.common.DB.JDBC;
import cn.cellcom.czt.po.SpTdcode;
import cn.cellcom.web.spring.ApplicationContextTool;

public class ObdTest {
	@Test
	public void test() {
		// TODO Auto-generated method stub
		JDBC jdbc = (JDBC) ApplicationContextTool.getBean("jdbc");
		String sql="select * from sp_tdcode";
		try {
			List<SpTdcode> spTdcodes = jdbc.query(ApplicationContextTool.getDataSource(), sql, SpTdcode.class, null, 0, 0);
			for (SpTdcode spTdcode : spTdcodes) {
				System.out.println(spTdcode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
