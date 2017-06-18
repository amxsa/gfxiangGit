package cn.cellcom.demo.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.cellcom.common.data.PatternTool;
import cn.cellcom.common.date.DateTool;
import cn.cellcom.common.encrypt.MD5;
import cn.cellcom.czt.common.Env;
import cn.cellcom.czt.po.DataMsg;

public class Test {

	public static void main(String[] args) {

//		String driver = "oracle.jdbc.driver.OracleDriver";
//		String url = "jdbc:oracle:thin:@192.168.7.212:1521:borel";
//		String user = "test";
//		String pwd = "test";
//
//		Connection conn = null;
//		CallableStatement cs = null;
//		ResultSet rs = null;
//		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pwd);
//			cs = conn.prepareCall(" { call SP_TEST (?,?,?,?) }");
//			cs.setString(1, "18925008300");
//			cs.setTimestamp(2, DateTool.getTimestamp(null));
//			cs.setTimestamp(3, DateTool.getTimestamp(null));
////			cs.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
////			cs.registerOutParameter(3, Types.VARCHAR);
//			//cs.registerOutParameter(5, java.sql.Types.INTEGER);
//			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
//			cs.execute();
////			String str = cs.getString(3);
////			System.out.println(str);
//			rs = (ResultSet) cs.getObject(4);
////
//			while (rs.next()) {
//				System.out.println("\t" + rs.getString(1) + "\t"
//						+ rs.getString(2) + "\t");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//					if (cs != null) {
//						cs.close();
//					}
//					if (conn != null) {
//						conn.close();
//					}
//				}
//			} catch (SQLException e) {
//			}
//		}
		//System.out.println(DateTool.formateTime2String(new Date(), "yyMM"));
//		System.out.println(Integer.parseInt("000047"));
//		JSONObject obj = new JSONObject();
//		Set set = new HashSet<String>();
//		obj.put("a", set);
//		JSONArray b =(JSONArray) obj.get("a");
//		System.out.println(b.size());
//		String mobilemd5Str = MD5
//				.getMD5(("15360443777" + Env.SERVICE_KEY)
//						.getBytes());
//		// 取第8-24位
//		mobilemd5Str = StringUtils.substring(mobilemd5Str, 8,
//				24);
//		System.out.println(mobilemd5Str);
//		String dateTemp = StringUtils.substring("GD20141229151237751478", 2, 16);
//		System.out.println(dateTemp);
//		System.out.println(MD5.getMD5("QICHENWEATHER@$^*20150305111234".getBytes()));
//		System.out.println(PatternTool.checkStr("000000080912B9=", "^[0-9A-Z]{15}$", "a"));
//		System.out.println(StringUtils.rightPad("AFALajflajflaj", 8));
//		Set<String> set = new HashSet<String>();
//		int year = 2015;
//		int indexid = 0;
//		for(int y= year;y<2018;y++){
//			for(int j=1;j<13;j++){
//				for(int i=1;i<10000;i++){
//					indexid++;
//					if(j<10){
//						String str = StringUtils.right( getTdcodemd5(i,String.valueOf(y),"0"+j),8);
//						System.out.println(str);
//						if(set.contains(str)){
//							System.out.println(">>>>>>>>>>>>>>>>>>>"+str);
//						}else{
//							set.add(str);
//						}
//					}else{
//						String str = StringUtils.right( getTdcodemd5(i,String.valueOf(y),String.valueOf(j)),8);
//						System.out.println(str);
//						if(set.contains(str)){
//							System.out.println(">>>>>>>>>>>>>>>>>>>"+str);
//						}else{
//							set.add(str);
//						}
//					}
//				}
//			}
//		}
//		
//		System.out.println("set="+set.size()+",indexid="+indexid);
		
		//System.out.println(PatternTool.checkStr("O10He55577e727c59112", "^[a-zA-Z][a-zA-Z0-9]{19}$", "123"));
		System.out.println(StringUtils.trimToEmpty(new StringBuffer().append(new DataMsg().getInfo()).toString()));;
	}
	
	public static String getTdcodemd5(int i,String year,String month){
		String startNum ="";
		if(i<10){
			startNum="00000"+i;
		}else if(i>=10&&i<100){
			startNum="0000"+i;
		}else if(i>=100&&i<1000){
			startNum="000"+i;
		}else if(i>=1000&&i<10000){
			startNum="00"+i;
		}else if(i>=10000&&i<100000){
			startNum="0"+i;
		}else{
			startNum=String.valueOf(i);
		}
		String tdcodemd5 = StringUtils.substring(
				MD5.getMD5((startNum + StringUtils.right(year, 2)+month).getBytes()),
				8, 24);
		return tdcodemd5;
	}

}
