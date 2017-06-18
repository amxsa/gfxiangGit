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
</head>
<body>
<div class="top"><a class="back" onClick="history.back()"><img src="../../images/wechat/back.png" /></a><h2>404</h2><span><a href="08cart.html"><img src="../../images/wechat/cart.png"></a><a href="01home.html"><img src="../../images/wechat/home.png"></a></span></div>

<div class="box">
 <div class="err">
  <p><img src="../../images/wechat/err.png"></p>
  <h3>地址输入错误，请重新返回。</h3>
 </div>
 <div class="bbut3"><a href="01home.html">返回</a></div>
</div>

<div class="box">
 <div class="err">
  <p><img src="../../images/wechat/succ.png"></p>
  <h4>输入格式正确，请进入下一步。</h4>
 </div>
 <div class="bbut3"><a href="01home.html">返回</a></div>
</div>
</body>
</html>