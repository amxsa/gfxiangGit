<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>体验漏话提醒</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" language="javaScript">
	function kaitong(){
		
		window.location.href="<%=path%>/user/order.jsp";
	}
	function tiyan(){
		window.location.href="<%=path%>/user/register/register.do?openid=${param.openid}";
	}
</script>
</head>
<body>
	<div class="header"></div>
	<div class="layout">
		<div class="content">
			<p>
				尊敬的<em>${sessionScope.number}</em>,您尚未开通漏话提醒服务
			</p>
			<p>
				<a href="javaScript:tiyan();" class="but01">马上体验</a>
			</p>
			<p>即可成为免费体验用户，体验两个月漏话提醒服务。体验期结束后我们将发短信提醒您发起订购，祝您使用愉快。</p>
			<p>
				<a href="javaScript:kaitong();" class="but02">马上开通</a>
			</p>
			<p>即可订购漏话提醒服务，服务费每月2元</p>
			<p>
				漏话提醒服务开通后如有漏话（忙时或关机时未接到的呼入电话），您就可以收到漏话提醒短信。
			</p>
		</div>
	</div>
	
</body>