package com.jlit.xms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJDBCForSy {

	static ResultSet rs = null;
	static Statement st = null;
	static Connection conn = null;

	/* 获取数据库连接的函数 */
	public static Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");//

			con = DriverManager.getConnection(
					"jdbc:mysql://192.168.0.3:3306/chims", "wsrp", "jl2012");

		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}

	public TestJDBCForSy() {

	}

	public static String getGtjFlag(String jbs, String gtjFlag) {

		StringBuffer sb = new StringBuffer();

		try {
			if (jbs != null && !"".equals(jbs)) {
				String[] jbss = jbs.split("\\|");
				for (int i = 0; i < jbss.length; i++) {

					if ("1".equals(jbss[0])) { // 勾选疾病史为无时
						sb.append("000");
						return sb.toString();
					} else {
						if (jbss[1].indexOf("1") == 0) {// 高血压
							sb.append("1");
						} else {
							sb.append("0");
						}
						if (jbss[2].indexOf("1") == 0) {// 糖尿病
							sb.append("1");
						} else {
							sb.append("0");
						}
						if (jbss[7].indexOf("1") == 0) {// 精神病
							sb.append("1");
						} else {
							sb.append("0");
						}

						gtjFlag = sb.toString();

						return gtjFlag;
					}

				}

			}
		} catch (Exception e) {
			gtjFlag = "";
			e.printStackTrace();
		}

		return gtjFlag;
	}

	public static void query() {

		conn = getConnection(); // 同样先要获取连接，即连接到数据库
		try {
			String sql = "select * from sqyl_grjkda_sy s where s.gtj_flag = '' or s.gtj_flag is null"; // 查询数据的sql语句
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量

			ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			System.out.println("最后的查询结果为：");
			while (rs.next()) { // 判断是否还有下一个数据

				String id = rs.getString(1);
				String jbs = rs.getString(33);

				String gtj_flag = "";

				gtj_flag = getGtjFlag(jbs, "");

				System.out.println("jbs:  " + jbs + "     gtj_flag:" + gtj_flag);

				update(id, gtj_flag);

			}
//			conn.close(); // 关闭数据库连接

		} catch (SQLException e) {
			System.out.println("查询数据失败");
		} finally {
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) { // 关闭声明
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) { // 关闭连接对象
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void update(String id, String value) {
		conn = getConnection(); // 同样先要获取连接，即连接到数据库
		try {
			String sql = "update sqyl_grjkda_sy s set s.gtj_flag='" + value
					+ "' where id = '" + id + "'";// 更新数据的sql语句

			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量

			int count = st.executeUpdate(sql);// 执行更新操作的sql语句，返回更新数据的个数

			System.out.println("sqyl_grjkda_sy表中更新 " + count + " 条数据"); // 输出更新操作的处理结果

			conn.close(); // 关闭数据库连接

		} catch (SQLException e) {
			System.out.println("更新数据失败");
		}
	}

	public static void main(String[] args) {
		// ClassPathXmlApplicationContext resource = new
		// ClassPathXmlApplicationContext(
		// new
		// String[]{"com/jlit/chims/resources/applicationContext-messageService.xml",
		// "com/jlit/chims/resources/applicationContext-dao.xml"}
		// );
		//
		// TestJDBC testJDBC= (TestJDBC)resource.getBean("testJDBC");
		// testJDBC.testJDBC();
		// TestJDBCForSy testJDBC = new TestJDBCForSy();
		// testJDBC.query();

		TestJDBCForSy t = new TestJDBCForSy();
		// String jbs = "0|1=2013-06-28|0=|0=|0=|0=|0=|0=|0=|0=|0=|0=|0=";
		// System.out.println("aaa:"+t.getGtjFlag(jbs, ""));
		t.query();
	}

}
