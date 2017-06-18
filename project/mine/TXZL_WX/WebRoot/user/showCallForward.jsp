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
<title>呼转设置</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path %>/js/table_hover.js"></script>
<link type="text/css" href="<%=path %>/css/banner.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="<%=path %>/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery-1.6.2.min.js"></script>

<script type="text/javascript" language="javaScript">
	function setCFDConfig() {
		
		document.getElementById("setCFDConfig").submit();
		
	}
	function cancelCFDConfig(id) {
		$(id).css("color", "gray").css("text-decoration", "none");

	}
</script>
</head>
<body>

<div class="top">
<!--  
<a class="back" onclick="history.back();"><img src="<%=path %>/images/back.png" /></a>
-->
<h2>呼转设置</h2><span><a href="<%=path%>/user/index.jsp"><img src="<%=path %>/images/home.png"></a></span>
</div>
	<div class="layout">
		
		
			<form action="<%=path%>/user/callForward/setCFDConfig.do"
			method="post" id="setCFDConfig">
			<div class="louhua"><h3><em>设置了呼叫转移您将无法收到漏话短信!</em></h3></div>
			<div class="hzsz">
				<ul>
					<li><label>呼转号码</label><input type="text" name="number" id="number"
						maxlength="20" size="12" /></li>
					<li><label>呼转类型</label> <select name="type" id="type">
							<option value="U">无条件呼转</option>
							<option value="B">遇忙呼转</option>
							<option value="N">无应答呼转</option>
							<option value="D">不可及呼转</option>
					</select>
					</li>
				</ul>
			</div>
			<div class="hzbut">
				<input type="button" value="设置" class="btn" id="btn"
					onclick="setCFDConfig();" />
			</div>
			 <div class="louhua">
				  <p><strong>无条件呼转：</strong>即全部来电转移，所有呼叫均转移到预先设置的电话号码</p>
				  <p><strong>遇忙呼转：</strong>也叫占线转移，如果用户正在通话的时候，所有呼叫均转移到预先设置的电话号码</p>
				  <p><strong>无应答呼转：</strong>用户的手机振铃如果无人接听，所有呼叫均转移到预先设置的电话号码</p>
				  <p><strong>不可及呼转：</strong>如果用户手机关机、未在有效服务区内、信号差而出现的不能实现正常通话时，所有呼叫均转移到预先设置的电话号码</p>
			 </div>
		</form>
		<c:if test="${not empty requestScope.list }">
		<form
			action="<%=path%>/user/callForward/showCallforward.do"
			method="post">
			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th>呼转号码</th>
					<th>时间</th>
					<th>类型</th>
					<th>处理状态</th>
					<th>操作</th>
				</tr>
				<c:forEach var="po" items="${requestScope.list}">
					<tr>
						<td>${po.cfNumber }</td>
						<td><fmt:formatDate value="${po.submitTime }" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>${po.cfTypeStr }</td>
						<td>${po.handleStatusStr }</td>
						<td><span class="cancel"><a
								href="<%=path%>/user/callForward/cancelCFDConfig.do?id=${po.id }"
								id="link${po.id}">取消</a> </span></td>
					</tr>
				</c:forEach>
			</table>
			</form>
		</c:if>
		
		
		
	</div>
	

</body>
</html>