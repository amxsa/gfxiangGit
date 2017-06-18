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
		if(type=='bind'){
			document.forms[0].action="<%=path%>/yfg/bindTxzlMobile.jsp";
			document.forms[0].submit();
		}else if(type=='open'){
			document.forms[0].action="<%=path%>/yfg/openTxzlMobile.jsp";
			document.forms[0].submit();
		}
		
	}
</script>
</head>
<body>
	<form action="" method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<input type="hidden" name="id" value="${param.id }" />
		
		<div class="top">
			<a class="back" onClick="history.back()"><img
				src="<%=path%>/images/yfg/back.png" />
			</a>
			<h2>开通一分购会员</h2>
			<span>
				<a href="<%=path%>/user/index.jsp"><img src="<%=path%>/images/yfg/home.png"></a>
			</span>
		</div>

		<div class="box">
			<h3 class="tith3">开通一分购通信助理会员</h3>
			<div class="user_1">
				<img src="<%=path%>/images/yfg/yfglogo2.jpg">
				<p>开通广东电信通信助理任何个套餐产品，即可成为优惠价会员，更可以成为一分购会员，优惠多多，尊享一分抢购商品。</p>
			</div>
			<div class="bbut6">
				<a href="javaScript:GO('bind');">个人通信助理用户通道</a>
				<a href="javaScript:GO('open');">非个人通信助理用户通道</a>
			</div>
		</div>

	</form>
</body>
</html>



