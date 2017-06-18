<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>温馨提示</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<link type="text/css" href="<%=path%>/css/banner.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript">
	window.jQuery = window.Zepto;
</script>

</head>
<body>

	<div class="top">

		<a class="back" onclick="history.back();"><img
			src="<%=path%>/images/back.png" />
		</a>

		<h2>温馨提示</h2>
		<span><a href="<%=path%>/user/index.jsp"><img
				src="<%=path%>/images/home.png">
		</a>
		</span>
	</div>
	<div class="layout">

		<div class="crumb2">
			<h3>温馨提示</h3>
		</div>
		<ul>

			<li style="" id="result"><c:if
					test="${not empty requestScope.result}">
					${requestScope.result}
					
					<c:if test="${empty requestScope.url}">
						<a href="javaScript:history.back(-1);" class="red">返回</a>
					</c:if>
					<c:if test="${not empty requestScope.url}">
						<a href="${requestScope.url }" class="red">返回</a>
					</c:if>
					
				</c:if> <c:if test="${empty requestScope.result}">
					找不到您所需要的页面,可能是服务器正处于维护状态。
				</c:if>
			</li>
		</ul>

	</div>


</body>
</html>