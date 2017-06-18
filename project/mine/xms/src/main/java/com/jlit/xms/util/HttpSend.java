package com.jlit.xms.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 *处理Http请求
 */
public class HttpSend {

	private static Logger log = Logger.getLogger(HttpSend.class);

	/**
	 * 通过指定的URL完成请求操作
	 * @param dest_url
	 * @param commString
	 * @return
	 * @throws Exception 
	 */
	public static String connectURL(String dest_url, String commString) throws Exception {
		log.debug("send data to Url:" + dest_url);
		String rec_string = "";
		URL url = null;
		OutputStream out = null;
		BufferedReader rd = null;
		HttpURLConnection urlconn = null;
		try {
			url = new URL(dest_url);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setReadTimeout(5000);
			urlconn.setRequestProperty("content-type", "text/plain");
			urlconn.setRequestMethod("POST");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			out = urlconn.getOutputStream();
			out.write(commString.getBytes());
			out.flush();
			out.close();
			rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = rd.read()) > -1) {
				sb.append((char) ch);
			}
			rec_string = sb.toString();
			log.info("resp=" + rec_string);
			rd.close();
			urlconn.disconnect();
		} catch (Exception e) {
			rec_string = "";
			throw e;

		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (urlconn != null) {
					urlconn.disconnect();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		}

		return rec_string;
	}

	public static String connectURLLongConn(String dest_url, String commString, int endChar) {

		String rec_string = "";
		URL url = null;
		OutputStream out = null;
		BufferedReader rd = null;
		HttpURLConnection urlconn = null;
		try {
			url = new URL(dest_url);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setReadTimeout(5000);
			urlconn.setRequestProperty("content-type", "text/plain");
			urlconn.setRequestMethod("POST");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			out = urlconn.getOutputStream();
			out.write(commString.getBytes());
			out.flush();
			out.close();
			rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = rd.read()) > -1) {
				sb.append((char) ch);
				if (ch == endChar) {
					break;
				}
			}
			rec_string = sb.toString();
			rd.close();
			urlconn.disconnect();
		} catch (Exception e) {
			log.error(e, e);
			return "";
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (urlconn != null) {
					urlconn.disconnect();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		}

		return rec_string;
	}

	/**
	 * 通过指定的URL完成请求操作,请求调用有异常则返回“-1”字符串
	 * @param dest_url
	 * @return
	 */
	public static String connectURL(String dest_url) {
		log.debug("connectURL:" + dest_url);
		PrintWriter out = null;
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		String gwret = "-1";

		try {
			URL url = new URL(dest_url);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			connection.setReadTimeout(10000);
			inputStream = connection.getInputStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer retsb = new StringBuffer();
			int ch;
			while ((ch = rd.read()) > -1) {
				retsb.append((char) ch);
			}
			gwret = retsb.toString();
		} catch (Exception e) {
			//log.error(e, e);
			log.info("调用connectURL方法出现异常");
			return "-1";
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				log.error(e, e);
			}
		}

		return gwret;
	}

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer(500);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(" <RegUser>");
		sb.append(" <privilege account=\"23234\" password=\"234234234\" />");
		sb.append(" <UserData UserId=\"234234\" Mobile=\"13444444444\" Password=\"23423\" ");
		sb.append(" Sex=\"234234\" Age=\"234234\" Nick=\"234\" ");
		sb.append(" RelativeNumber=\"\" ServiceState=\"234\" ");
		sb.append(" Nation=\"234\" IDCard=\"23423\" />");
		sb.append("</RegUser>");
		long t = System.currentTimeMillis();
		//connectURLLongConn("http://211.99.191.47:8090/registUser",sb.toString(),62);
		//System.out.println(connectURLLongConn("http://211.99.191.70:8082/registUser",sb.toString(),62));
		//System.out.println(connectURL("http://www.google.cn"));
		//System.out.println(connectURL("http://localhost:8090/SmartHomeSysWeb/WAPUserOptDeviceServlet?operaCode=00ff&deviceNum=00000000000000000000000002140244"));
		//System.out.println(connectURL("http://192.168.0.68:9080/SmartHomeSysWeb/WAPUserOptDeviceServlet?operaCode=00ff&deviceNum=00000000000000000000000002140244"));
		//System.out.println(connectURL("http://121.8.131.226:9080/smartHome/WAPUserOptDeviceServlet?operaCode=00ff&deviceNum=00000000000000000000000002140244"));
		//System.out.println(connectURL("http://192.168.0.68:9080/SmartHomeSysWeb/WAPUserOptDeviceServlet?operaCode=10110000&alarmID=0000000000000000000000000c000021"));
		System.out
				.println(connectURL("http://192.168.0.68:9080/SmartHomeSysWeb/WAPUserOptDeviceServlet?operaCode=0010&alarmID=0000000000000000000000000c000021"));
		t = System.currentTimeMillis() - t;
		System.out.println("cost:" + t);
	}
}
