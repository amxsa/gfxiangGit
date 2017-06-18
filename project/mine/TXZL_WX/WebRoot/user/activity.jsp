<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>精彩活动</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<link type="text/css" href="<%=path%>/css/banner.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript">
	window.jQuery=window.Zepto;
 
 </script>
<script type="text/javascript" language="javaScript">
	
</script>
</head>
<div class="top">
	<!--  
<a class="back" onclick="history.back();"><img src="<%=path%>/images/back.png" /></a>
-->
	<h2>精彩活动</h2>
	<span><a href="<%=path%>/user/index.jsp"><img
			src="<%=path%>/images/home.png">
	</a>
	</span>
</div>
<div class="layout">
	 <div class="louhua">
			  <h4>敬请期待。。。</h4>
	</div>
</div>
</html>