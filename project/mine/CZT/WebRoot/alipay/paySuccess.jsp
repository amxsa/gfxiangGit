<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>支付成功</title>
<link href="<%=path%>/css/alipay.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="top"><a class="back" onClick="history.back()"><img src="<%=path%>/images/back.png" /></a><h2>温馨提示</h2><span><a href="#"><img src="<%=path%>/images/home.png"></a></span></div>

<div class="err">
  <p><img src="<%=path%>/images/succ.png"></p>
  <h3>${requestScope.result }</h3>
</div>

<div class="dbut">
	<c:if test="${not empty requestScope.url }">
		<a href="${requestScope.url }">返回</a>
	</c:if>
	<c:if test="${empty requestScope.url }">
		<a href="javaScript:history.back();">返回</a>
	</c:if>
</div>



</body>
</html>
