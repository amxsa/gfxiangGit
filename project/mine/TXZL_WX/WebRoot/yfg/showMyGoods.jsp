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
<title>一分购抢购列表</title>
<link href="<%=path%>/css/onefengou.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript">
	function sureReceive(orderid) {
		
		$.post("<%=path%>/yfg/UserAction_updateReceive.do?orderid="+orderid,{openid:'${param.openid}'},function(data){
			if(data!=null){
				GO();
			}
		});
	}
	function GO(){
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="<%=path%>/yfg/UserAction_showMyGoods.do" method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<div class="top">

			<h2>抢购列表</h2>
			<span><a href="<%=path%>/user/index.jsp"><img
					src="<%=path%>/images/yfg/home.png"> </a> </span>
		</div>
		<div class="yfg_user2" >
			<select name=""><option>全部订单</option>
			</select>
			<div class="serch">
				<input type="text" name="name" maxlength="20" value="${requestScope.name }"
					placeholder="输入关键字" class="b3" /><input name="" type="button"
					value="" onclick="GO();"   class="b4">
			</div>
		</div>
		<div class="yfg_list2">
			<ul>
				<c:forEach var="list" items="${requestScope.list }">
					<li>
						<div class="lt">
							<img src="<%=path%>/images/yfg/${list.bigImg}">
	
						</div>
						<div class="rt">
							<h2>${list.name}${list.nameDes }</h2>
							<div class="price">
								<p>共${list.payCnt }件商品</p>
								<h3>￥${list.amount }</h3>
							</div>
							<div class="bbut5">
								<c:if test="${list.isReceive=='N' }">
									<a href="javaScript:sureReceive('${list.orderid }')">确认收货</a>
								</c:if>
	
							</div>
						</div>
					</li>
				</c:forEach>	
			</ul>
		</div>
		<div class="page">${requestScope.linkToMobile }</div>
	</form>
</body>
</html>



