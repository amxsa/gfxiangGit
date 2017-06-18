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
<title>支付宝支付</title>
<link href="<%=path%>/css/onefengou.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" language="javaScript">
	function GO() {
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="<%=path%>/yfg/OrderAction_paySuccess.do" method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<input type="hidden" name="data" value="${requestScope.data }" />
		<div class="top">
			<a class="back" onClick="history.back()"><img
				src="<%=path%>/images/yfg/back.png" /> </a>
			<h2>支付订单</h2>
			<span><a href="01home.html"><img
					src="<%=path%>/images/yfg/home.png"> </a> </span>
		</div>

		<div class="box">
			<h2 class="tith2">支付宝会员</h2>
			<div class="zhifu2">
				<h3>如果您还没有支付宝账户，可以使用</h3>
				<ul>
					<li><span>信用卡快捷支付（推荐）</span><a href="#"></a></li>
					<li><span>储蓄卡快捷支付</span><a href="#"></a></li>
				</ul>
			</div>
			<div class="zhifu1">
				<p>
					<input type="text" name="name" value=""
						placeholder="支付宝账户，手机号码或email地址" />
				</p>
				<p>
					<input type="password" name="name" value="" placeholder="支付宝支付密码" />
				</p>
			</div>
			<div class="bbut3">
				<a href="javaScript:GO();">下一步</a>
			</div>
		</div>
	</form>
</body>
</html>


