<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>错误提醒</title>
<link type="text/css" href="<%=path%>/css/main.css" rel="stylesheet" />
<style type="text/css">
.err {
	padding: 5px 0 5px;
}

.err p {
	text-align: left;
}

.err img {
	margin-bottom: 5px;
}

.err h3 {
	font-size: 16px;
	line-height: 18px;
	margin-bottom: 10px;
	color: #FF3300;
	text-align: left;
}

.err p {
	margin-bottom: 8px;
	font-size: 14px;
	line-height: 24px;
}
</style>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			错误提示</span>
	</div>
	<div class="content">
		<div class="err">
			<h3>温馨提醒</h3>
			<c:if test="${not empty requestScope.dataMsg.msg }">
				<p style="margin-left: 30px;">${requestScope.dataMsg.msg }</p>
				<p class="sbut">
					<!--  
					<c:if test="${empty requestScope.url }">
						<a href="javaScript:history.back(-1);">返回</a>
					</c:if>
					-->
					<c:if test="${not empty requestScope.url}">
						<a href="${requestScope.url }">返回</a>
					</c:if>
				</p>
			</c:if>
			<c:if test="${empty requestScope.dataMsg}">
				<p style="margin-left: 30px;">
					当前访问的页面出错,可能是服务器正处于维护状态,请稍后再试。
				</p>
				
			</c:if>
		</div>
	</div>
</body>
</html>