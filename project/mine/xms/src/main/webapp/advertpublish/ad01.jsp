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
<title>有温度的智能血压计</title>
<link rel="stylesheet" type="text/css" href="css/css.css"/>
</head>

<body>
<div class="ad01">
  <!--  <img src="http://${host}:${port}${contextPath}/upload/advert/ad02.png"/>-->
 <img src="images/ad01.png" />
 <h2>有温度的智能血压计</h2>
  <div class="ad_content">
    <ul>
    <li>国内第一款可与医院联网的智能血压计；</li>
      <li>医生时刻关注您的体检结果，短信自动提醒异常结果，使检测充满温暖；</li>
        <li>一机在手，全家健康无忧；</li>
          <li>体检健康档案永久保存，为您的健康生活方式做向导；</li>
            <li>血压值曲线图，指导您合理使用降压药；</li>
     </ul>
      </div>
   </div>
 

<!--  <div class="buy"><a href="#">立刻购买</a></div>-->

</body>
</html>
