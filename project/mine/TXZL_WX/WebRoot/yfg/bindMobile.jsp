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
<title>开通优惠价会员</title>
<link href="<%=path%>/css/onefengou.css" rel="stylesheet" type="text/css" >
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" language="javaScript">

	function sendSms(){
		var mobile = $("#mobile").val();
		
		$.post("<%=path%>/yfg/UserAction_sendSms.do", {
			mobile : mobile
		}, function(data) {
			if (data != "") {
				array = data.split("|");
				$("#msg").text(array[0]);
				if(array.length>1){
					$("#passwordSms").val(array[1]);
				}
			}
		});
	}
	function bind(){
		document.forms[0].submit();
	}
</script>	
</head>
<body>
	<form action="<%=path%>/yfg/UserAction_bindMobile.do" method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<!-- 商品ID，如果有数据说明从商品进入，然后发现未开通一分购，开通后，直接进入商品详情页面 -->
		<input type="hidden" name="id" value="${param.id }" />
		<div class="top">
			<a class="back" onClick="history.back()"><img
				src="<%=path%>/images/yfg/back.png" />
			</a>
			<c:if test="${empty requestScope.mobile }">
				<h2>开通优惠价会员</h2>
			</c:if>
			<c:if test="${not empty requestScope.mobile }">
				<h2>开通通信助理一分购会员会员</h2>
			</c:if>
			<span>
				<a href="<%=path%>/user/index.jsp"><img src="<%=path%>/images/yfg/home.png"></a>
			</span>
		</div>

		<div class="box">
			<c:if test="${empty requestScope.mobile }">
				<h3 class="tith3">绑定手机号码，成为优惠价会员</h3>
				<div class="user_1">
					<img src="<%=path%>/images/yfg/yfglogo.jpg">
					<p>绑定手机即可成为一分购优惠价会员</p>
				</div>
			</c:if>
			<c:if test="${not empty requestScope.mobile }">
				<h3 class="tith3">绑定手机号码，成为通信助理一分购会员</h3>
				<div class="user_1">
					<img src="<%=path%>/images/yfg/yfglogo.jpg">
					<p>绑定手机即可成为通信助理一分购会员</p>
				</div>
			</c:if>
			<div class="user_2">
				<ul class="list2" style="padding-left:15%;">
					<c:if test="${empty requestScope.mobile }">
						<li><input type="text" name="mobile" id="mobile"  value="" 
							placeholder="输入手机号" class="b1" />
								<input name="" type="button" value="获取短信验证码" class="b2" onclick="sendSms();" />
							
						</li>
						<li><input type="text" name="passwordSms" id="passwordSms"  maxlength="6" size="6" value=""
							placeholder="输入6位验证码" />
						</li>
						<input type="hidden" name="smsValidate" value="Y" />
					</c:if>
					<c:if test="${not empty requestScope.mobile }">
						<li>手机号：<input type="text" name="mobile"  readonly="readonly" value="${ requestScope.mobile }"
							placeholder="" class="b1" />
						</li>
						<input type="hidden" name="smsValidate" value="N" />
					</c:if>
					<li style="color: red;" id="msg">${requestScope.msg }</li>
				</ul>
			</div>
			<div class="bbut3">
				<a href="javaScript:bind();">马上开通</a>
			</div>
		</div>
	</form>
</body>
</html>



