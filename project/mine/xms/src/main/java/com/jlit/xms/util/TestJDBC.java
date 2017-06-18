package com.jlit.xms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJDBC {
	
	static ResultSet rs = null;
	static Statement st = null;
	static Connection conn = null;
	
	/* 获取数据库连接的函数*/  
    public static Connection getConnection() {  
        Connection con = null;  //创建用于连接数据库的Connection对象  
        try {  
        	 Class.forName("com.mysql.jdbc.Driver");// 
             
    		 con = DriverManager.getConnection(  
                        "jdbc:mysql://192.168.0.3:3306/chims", "wsrp", "jl2012");
              
        } catch (Exception e) {  
            System.out.println("数据库连接失败" + e.getMessage());  
        }  
        return con; //返回所建立的数据库连接  
    } 
	
	public TestJDBC(){
		
	}
	
	
	
	
	/** 
     * 提取每个汉字的首字母 
     *  
     * @param str 
     * @return String 
     */  
    public static String getPinYinHeadChar(String str) {  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            // 提取汉字的首字母  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }  
    
    
    public static void query() {  
        
        conn = getConnection(); //同样先要获取连接，即连接到数据库  
        try {  
            String sql = "select * from device_manage";     // 查询数据的sql语句  
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量  
              
            ResultSet rs = st.executeQuery(sql);    //执行sql查询语句，返回查询数据的结果集  
            System.out.println("最后的查询结果为：");  
            while (rs.next()) { // 判断是否还有下一个数据  
                  
                // 根据字段名获取相应的值  
                String name = rs.getString(5);  
                
                String xmszm = getPinYinHeadChar(name);
                
                System.out.println("name:  "+name +" xmszm:"+xmszm);  
                
                update(name,xmszm);
                  
               
              
            }  
            conn.close();   //关闭数据库连接  
              
        } catch (SQLException e) {  
            System.out.println("查询数据失败");  
        }  
    }  
    
    
    public static void update(String name,String value) {  
        conn = getConnection(); //同样先要获取连接，即连接到数据库  
        try {  
            String sql = "update device_manage set xmszm='"+value+"' where user_account = '"+name+"'";// 更新数据的sql语句  
              
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量  
              
            int count = st.executeUpdate(sql);// 执行更新操作的sql语句，返回更新数据的个数  
              
            System.out.println("device_manage表中更新 " + count + " 条数据");      //输出更新操作的处理结果  
              
            conn.close();   //关闭数据库连接  
              
        } catch (SQLException e) {  
            System.out.println("更新数据失败");  
        }  
    } 

	public static void main(String[] args) {
//		ClassPathXmlApplicationContext resource = new ClassPathXmlApplicationContext(
//				new String[]{"com/jlit/chims/resources/applicationContext-messageService.xml",
//						"com/jlit/chims/resources/applicationContext-dao.xml"}
//		);
//		
//		TestJDBC testJDBC= (TestJDBC)resource.getBean("testJDBC");
//		testJDBC.testJDBC();
		TestJDBC testJDBC = new TestJDBC(); 
		testJDBC.query();
	}

	
}
