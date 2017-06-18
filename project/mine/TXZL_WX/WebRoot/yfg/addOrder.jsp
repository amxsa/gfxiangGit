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
<title>提交订单</title>
<link href="<%=path %>/css/onefengou.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function GO(){
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="<%=path%>/yfg/OrderAction_addOrder.do" method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<input type="hidden" name="goodsid" value="${param.goodsid }" />
		
		<input type="hidden" name="payType" value="${param.payType }" />
		<input type="hidden" name="unitPrice" value="${param.unitPrice }" />
		<input type="hidden" name="payCnt" value="${param.payCnt }" />
		
		<div class="top">
			<a class="back" onClick="history.back()"><img
				src="<%=path%>/images/yfg/back.png" />
			</a>
			<h2>提交订单</h2>
			<span><a href="<%=path%>/user/index.jsp"><img src="<%=path%>/images/yfg/home.png">
			</a>
			</span>
		</div>
	
		<div class="box">
			<h3 class="tith3">填写订单</h3>
			<ul class="list2">
				<li><label>姓名</label><input type="text" name="name"
					maxlength="20" value="" placeholder="请输入联系人姓名" />
				</li>
				<li><label>联系方式</label><input type="text" name="telephone"
					maxlength="20" value="" placeholder="请输入联系电话" />
				</li>
				<li><label>邮编</label><input type="text" name="zipcode"
					maxlength="20" value="" placeholder="请输入邮编" />
				</li>
				<li><label>地址</label><input type="text" name="address"
					maxlength="20" value="" placeholder="请输入详细联系地址" />
				</li>
			</ul>
			<div class="bbut3">
				<a href="javaScript:GO();">下一步</a>
			</div>
		</div>
	</form>

</body>
</html>


