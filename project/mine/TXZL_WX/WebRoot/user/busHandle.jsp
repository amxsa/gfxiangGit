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
<title>业务办理</title>
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
	function upgrade(setid,servNbr){
		if(servNbr=='')
			document.location.href="<%=path%>/user/order.jsp";
		else 
			document.location.href="<%=path%>/user/upgrade.jsp?setid="+ setid;
							
	}
</script>
</head>
<div class="top">
	<!--  
<a class="back" onclick="history.back();"><img src="<%=path%>/images/back.png" /></a>
-->
	<h2>业务办理</h2>
	<span><a href="<%=path%>/user/index.jsp"><img
			src="<%=path%>/images/home.png">
	</a>
	</span>
</div>
<div class="layout">


	<table width="100%" cellpadding="0" cellspacing="1" border="0"
		id="table_hover" class="table">
		<tr>
			<th width="30%">业务名称</th>
			<th>服务内容</th>
			<th width="30%">收费方式</th>
		</tr>
		<c:if test="${login.setid=='51'}">
			<tr>
				<td>漏话提醒</td>
				<td>漏话提醒、漏话历史</td>
				<td>每月2元 <br />
				<c:if test="${empty login.servNbr}">
						<a
							href="javaScript:upgrade('51','${login.servNbr }');"
							class="red">马上升级</a>
					</c:if></td>
			</tr>
			<tr>
				<td>通信助理基础包</td>
				<td class="left">漏话提醒、漏话历史、通信录</td>
				<td>每月4元<br />
				<a
					href="javaScript:upgrade('21','${login.servNbr }');"
					class="red">马上升级</a></td>
			</tr>
			<tr>
				<td>通信助理信息包</td>
				<td class="left">漏话提醒、漏话历史、通信录、人工秘书、呼叫转移</td>
				<td>每月10元<br />
				<a
					href="javaScript:upgrade('22','${login.servNbr }');"
					class="red">马上升级</a></td>
			</tr>
		</c:if>
		<c:if test="${login.setid==21 or login.setid==31}">
			<tr>
				<td>通信助理基础包</td>
				<td class="left">漏话提醒、漏话历史、通信录</td>
				<td>每月4元&nbsp;</td>
			</tr>
			<tr>
				<td>通信助理信息包</td>
				<td class="left">漏话提醒、漏话历史、通信录、人工秘书、呼叫转移</td>
				<td>每月10元<br />
				<a
					href="javaScript:upgrade('21','${login.servNbr }');"
					class="red">马上升级</a></td>
			</tr>
		</c:if>
		<c:if test="${login.setid==22 or  login.setid==32}">
			<tr>
				<td>通信助理信息包</td>
				<td class="left">漏话提醒、漏话历史、通信录、人工秘书、呼叫转移</td>
				<td>每月10元&nbsp;</td>
			</tr>
		</c:if>

	</table>

</div>



</html>