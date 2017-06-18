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
<title>访亲友、爱相随，送父母、显孝心</title>
<link rel="stylesheet" type="text/css" href="css/css.css"/>
</head>

<body>
<div class="ad01">
 <img src="images/ad02.png"/>
 <h2>访亲友、爱相随，送父母、显孝心</h2>
  <div class="ad_content">
    <ul>
    <li>关爱健康，就送健康检测仪；</li>
      <li>国内第一款血糖仪/血压计智能一体机；</li>
        <li>既能检测血糖，又能检测血压。解决中老年慢病患者的全部问题；</li>
          <li>直接与医院联网，医生时刻关注，短信提醒异常结果；</li>
            <li>永久保存体检结果，使您无论在何时何地都能关注他们的健康。</li>
     </ul>
      </div>
   </div>
 

<!-- <div class="buy"><a href="#">立刻购买</a></div> -->

</body>
</html>
