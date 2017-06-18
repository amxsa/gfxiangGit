<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>头部</title>
<link type="text/css" href="css/main.css" rel="stylesheet" />
<script type="text/javascript">
	function logout() {
		if(confirm('确认退出吗?')) {
			parent.window.location='account/logout.do';
		}
	}
</script>
</head>
<body>
<div class="header">
  <div class="logo1"></div>
  <div class="login1"><p>&nbsp;&nbsp;&nbsp; 今日是<em><span id="time"><script>setInterval("time.innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);</script></span></em></p>
  <p><em class="i1">${loginForm.NAME}</em>，${loginForm.DEPART_NAME}<span class="i3"><a href="#" onclick="logout();">安全退出</a></span></p></div>
</div>
</body>
