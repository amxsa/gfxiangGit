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
<title>人工秘书</title>
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
<script type="text/javascript" language="javaScript">
	function GO(num) {
		window.location.href = "tel:" + num;
	}
</script>
</head>
<div class="top">
	<h2>人工秘书</h2>
	<span><a href="<%=path%>/user/index.jsp"><img
			src="<%=path%>/images/home.png">
	</a>
	</span>
</div>
<div class="layout">

	<div class="louhua">
		<h4>
			<c:choose>
				<c:when
					test="${login.setid !='22' and login.setid !='32'  }">
	  				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人工秘书：当用户无法正常接听电话时，提供人工通信录转接、漏话人工代接服务、人工座席设置呼转及人工座席代发短信服务</br>
	  				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;亲！人工秘书只对办理信息包用户开放，赶快点击<a
						href="<%=path%>/user/upgrade.jsp?setid=21">办理</a>吧
	  			</c:when>
				<c:otherwise> 
	  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人工秘书：当用户无法正常接听电话时，提供人工通信录转接、漏话人工代接服务、人工座席设置呼转及人工座席代发短信服务
	  					<div class="hzbut1">
						<input type="button" value="转人工" class="btn" id="btn"
							onclick="GO(118114);" />
					</div>
				</c:otherwise>
			</c:choose>
		</h4>
	</div>
</div>
</html>