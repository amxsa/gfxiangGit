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
<title>开通一分购会员</title>
<link href="<%=path%>/css/onefengou.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" language="javaScript">
	function GO(type) {
		$("#type").val(type);
		document.forms[0].action="<%=path%>/yfg/bindTxzlMobile.jsp";
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="" method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		
		

		<div class="box">
			<h3 class="tith3">支付结果</h3>
			<div class="user_1">
				<img src="<%=path%>/images/yfg/succ.png">
				<p>您好，您已成功支付</p>
			</div>
			<div class="bbut6">
				<a href="<%=path%>/yfg/GoodsAction_showGoods.do?openid=${param.openid}">继续购买</a>
				<a href="<%=path%>/yfg/UserAction_showBuyGoods.do?openid=${param.openid}">查看购物</a>
			</div>
		</div>

	</form>
</body>
</html>



