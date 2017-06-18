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
<title>我的漏话</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path %>/js/table_hover.js"></script>
<link type="text/css" href="<%=path %>/css/banner.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="<%=path %>/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
<script type="text/javascript">
	window.jQuery=window.Zepto;
 
 </script>
 <script type="text/javascript" language="javaScript">
			
		
</script>
</head>

<div class="top">
<!--  
<a class="back" onclick="history.back();"><img src="<%=path %>/images/back.png" /></a>
-->
<h2>漏话提醒</h2><span><a href="<%=path%>/user/index.jsp"><img src="<%=path %>/images/home.png"></a></span>
</div>
	
	
	<div class="layout">
		
		 <div class="louhua">
			
			 	 <c:if test="${ not empty login.servNbr }">
					<h3>亲！您最近1个月有<em>${requestScope.lhcount }</em>条漏话</h3>
				</c:if>
				<c:if test="${  empty login.servNbr }">
					<h3>亲！您最近1个月有<em>${requestScope.lhcount }</em>条漏话
						（您的体验期还剩下<em>${requestScope.limiteDay }</em>天，<a href="<%=path%>/user/upgrade.jsp">点击办理</a>）
					</h3>
				</c:if>
				 <c:if test="${requestScope.lhcount>0 }">
				 	<c:if test="${maxNumMsg.cnt>0 }">
				 		<p><strong>漏话最多时段：</strong>${timeSub}</p>	 
				 	</c:if>
					  <p><strong>漏话最多次数：</strong>${maxNumMsg.cnt }次</p>
					  <p><strong>漏话最多号码：</strong>${maxNumMsg.ANum }</p>
				 </c:if>
			 
		 </div>
		
	</div>

	
	

</html>
