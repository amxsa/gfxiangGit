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
<link href="<%=path %>/css/onefengou.css" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="<%=path%>/yfg/GoodsAction_showGoods.do">
		<input type="hidden" name="openid" value="${param.openid }" />
		<div class="top">
			<a class="back" onClick="history.back()"><img
				src="<%=path%>/images/yfg/back.png" />
			</a>
			<h2>抢购列表</h2>
			<span><a href="<%=path%>/yfg/UserAction_showMyGoods.do?openid=${param.openid }"><img src="<%=path%>/images/yfg/cart.png"></a>
			<a href="<%=path%>/user/index.jsp"><img src="<%=path%>/images/yfg/home.png">
			</a>
			</span>
		</div>
		<div class="yfg_user">
			<c:if test="${empty requestScope.user  }">
				<div class="userpic">亲，你尚未开通会员！</div>
				<a href="<%=path %>/yfg/UserAction_checkBindMobile.do?openid=${param.openid}" class="bbut1">马上开通</a>
			</c:if>
			<c:if test="${not empty requestScope.user and  empty requestScope.bindWechat }">
				<div class="userpic pthy"><strong>${user.account }</strong>，<em>普通会员</em></div>
			</c:if>
			<c:if test="${not empty requestScope.user and  not empty requestScope.bindWechat }">
				<div class="userpic vip"><strong>${user.account }</strong>，<em>一分购通信助理会员</em></div>
			</c:if>
			
			
		</div>
		<div class="yfg_list">
			<ul>
				<c:forEach var="list" items="${requestScope.list }">
					<li>
						<div class="lt">
							<a href="<%=path%>/yfg/GoodsAction_showGoodsDetail.do?id=${list.id}&openid=${param.openid }"><img src="<%=path%>/images/yfg/${list.bigImg}"></a>
						</div>
						<div class="mt">
							<h2>
								${list.name}${list.nameDes }
							</h2>
							<h3>${list.diffLimitTime}</h3>
							<c:if test="${list.type=='1' }">
								<div class="price">
									<em>专享：</em>￥${list.discountPrice }
								</div>
							</c:if>
							<c:if test="${list.type=='2' }">
								<div class="price2">
									<em>专享：</em>￥${list.discountPrice }
								</div>
							</c:if>
							<h4>
								<i>￥${list.unitPrice }</i>已抢购${list.buyCount }件
							</h4>
							<div class="jd">
								<label>已抢购${list.buyPercent}</label><span><i style="width:${list.buyPercent };"></i>
								</span>
							</div>
						</div>
						<div class="rt">
							<a href="<%=path%>/yfg/GoodsAction_showGoodsDetail.do?id=${list.id}&openid=${param.openid }" class="bbut2">马上抢</a>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="page">${requestScope.linkToMobile }</div>
	</form>
</body>
</html>



