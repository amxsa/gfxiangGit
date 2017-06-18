<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="<%=path %>/css/caiyin.css" rel="stylesheet" type="text/css">
<title>404</title>
</head>
<body>
<div class="box">
 <div class="err">
  <p><img src="../../images/wechat/loss.png"></p>
  <h3>${requestScope.info }</h3>
  <p>请稍后重试</p>
 </div>
 <div class="bbut4"><a href="">返回</a></div>
</div>
</body>
</html>