<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%
	String host = request.getServerName();
	int port = request.getServerPort();
	String contextPath = request.getContextPath();
	request.setAttribute("host", host);
	request.setAttribute("port", port);
	request.setAttribute("contextPath", contextPath);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>和谐社会 ，健康到家</title>
<link rel="stylesheet" type="text/css" href="css/css.css"/>
</head>

<body>
<div class="ad01">
  <img src="http://${host}:${port}${contextPath}/upload/advert/ad03.png"/>
 <h2>和谐社会 ，健康到家</h2>
  <div class="ad_content">
  	    为体现党和政府的关怀，禅城区卫生局开展送健康服务活动，凡符合条件者都可以申请领取健康<br/>
	    医疗自助体检设备，请抓紧时间办理。
	  <br/>  <br/>
	    活动细则：
    <ul>
    <li>本活动主要面向佛山市禅城区户籍人员；</li>
      <li>申请者必须是社区医院中在编的“三高”患者；</li>
        <li>申请者可以由亲属登记手机号，系统核实后将主动联系。</li>
     </ul>
      </div>
   </div>
 

<!--  <div class="buy"><a href="#">立刻购买</a></div>-->

</body>
</html>
