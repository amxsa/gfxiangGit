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
<title>我的留言</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<link type="text/css" href="<%=path%>/css/banner.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
	window.jQuery = window.Zepto;
</script>
<script type="text/javascript" language="javaScript">
	function showLeavewordTxt() {
		document.forms[0].submit();
	}
</script>
</head>

<div class="top">
	<h2>我的留言</h2>
	<span><a href="<%=path%>/user/index.jsp"><img
			src="<%=path%>/images/home.png"> </a> </span>
</div>
<form action="<%=path%>/user/leaveword/showLeavewordTxt.do"
	method="post">
	<div class="layout">

		<div class="crumb">
			<c:if test="${empty list }">
				<h3>亲！恭喜您！最近${requestScope.type=='1'?'7天':'1个月' }没有留言信息</h3>
				<div align="center">
					<img src="<%=path%>/images/hand.jpg">
				</div>
			</c:if>
			<c:if test="${ not empty list }">
				<h3>
					<p>
						<input type="radio" name="type" value="1" ${type==
							'1' ?'checked':'' } onclick="showLeavewordTxt();" /> 最近七天 <input
							type="radio" name="type" value="2" ${type== '2' or empty
							type ? 'checked':'' }  onclick="showLeavewordTxt();" /> 最近一个月
					</p>
				</h3>
			</c:if>
		</div>
		<c:if test="${ not empty list}">
			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th width="30%">号码</th>
					<th width="30%">时间</th>
					<th>留言信息</th>
				</tr>
				<c:forEach var="po" items="${requestScope.list}">
					<tr>
						<td><font class="det">${po.ANum}</font>${po.ANumCity }</td>
						<td><fmt:formatDate value="${po.recordtime}"
								pattern="MM-dd HH:mm" /></td>
						<td>${po.recordfile}</td>
					</tr>
				</c:forEach>
			</table>
			<div class="page">${requestScope.linkToMobile }</div>
		</c:if>
	</div>

</form>


</html>
