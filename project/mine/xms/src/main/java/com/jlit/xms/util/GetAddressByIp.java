package com.jlit.xms.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

public class GetAddressByIp {
	  /**
     * 获取客户端IP地址
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 根据IP地址获取地理位置
     */
    public String getAddressByIP(String ip) {
        try {
            URL url = new URL("http://www.ip138.com/ips138.asp?ip=" + ip);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "GBK"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            ip = result.substring(result.indexOf("主数据：") + 4,
                    result.indexOf("</li><li>参考数据"));
            char[] ipStr = ip.toCharArray();
            System.out.println(ipStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
}
