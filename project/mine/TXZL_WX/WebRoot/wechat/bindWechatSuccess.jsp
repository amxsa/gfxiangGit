<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>号码绑定</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javaScript">
	function jiechu(){
		location.href="<%=path%>/user/bindWechat/deleteBind.do?openid=${sessionScope.login.wechatNo}";
	}
	function index(){
		//location.href="<%=path%>/user/LeavewordAction_showLeaveword.do?openid=${sessionScope.login.wechatNo}";
		location.href="<%=path%>/user/leaveword/showLeaveword.do";
	}
</script>
</head>
<body>
	<div class="top">
<!--  
<a class="back" onclick="history.back();"><img src="<%=path %>/images/back.png" /></a>
-->
<h2>号码绑定</h2><span><a href="<%=path%>/user/index.jsp"><img src="<%=path %>/images/home.png"></a></span>
</div>
	<div class="layout">
		
		
			<p align="center">您好，您绑定的电话号码是 ${sessionScope.login.number }
				<br />
				套餐是 ：
				<c:if test="${sessionScope.login.setid=='21' or sessionScope.login.setid=='31' }">
					通信助理基础包
				</c:if>
				<c:if test="${sessionScope.login.setid=='22' or sessionScope.login.setid=='32' }">
					通信助理信息包
				</c:if>
				<c:if test="${sessionScope.login.setid==51 and  not empty sessionScope.login.servNbr }">
					漏话提醒
				</c:if><c:if test="${sessionScope.login.setid==51 and  empty sessionScope.login.servNbr }">
					漏话提醒体验用户
				</c:if>
			</p>
			
		
		<div class="hzbut1">
			<input type="button" value="解除绑定" class="btn" id="btn"
				onclick="jiechu();" /><input type="button" value="马上进入"
				class="btn" id="btn" onclick="index();" />
		</div>
	</div>
	
</body>