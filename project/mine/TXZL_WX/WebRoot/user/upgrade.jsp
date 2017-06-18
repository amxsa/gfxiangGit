<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>升级套餐</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" language="javaScript">
		function upgrade(){
			/**
			$("#msg").text('');
			var number = $.trim($("#number").val());
			var yzm =$.trim($("#yzm").val());
			if(!(/^1{1}(8[019]|33|53|7[7])[0-9]{8}$/).exec(number)){
				$("#msg").text('手机号码格式错误');
				return false;
			}
			if(!(/^[0-9]{1}[0-9]{5}$/).exec(yzm)){
				$("#msg").text('验证码格式错误');
				return false;
			}
			*/
			document.forms[0].action="<%=path%>/user/account/orderAccount.do";
			document.forms[0].submit();
		}
		function bak(){
			//document.forms[0].action="<%=path%>/user/busHandle.jsp";
			//document.forms[0].submit();
			//window.history.back();
			
		}
		function sendSms(){
			var number = $("#number").val();
			$.post("<%=path%>/user/sms/sendOrderSms.do", {
			number : number
			}, function(data) {
				if (data != "") {
					$("#msg").text(data.msg);
				}
			});
		}
		
	</script>
</head>
<body>
	<div class="top">

<a class="back" onclick="history.back();"><img src="<%=path %>/images/back.png" /></a>

<h2>升级套餐</h2><span><a href="<%=path%>/user/index.jsp"><img src="<%=path %>/images/home.png"></a></span>
</div>
	<form
		action=""
		method="post">
		<input type="hidden" name="platform" value="WX" /> <input
			type="hidden" name="operateType" value="U" />
		
		<div class="login">
			<ul>
				
					<li style="color: red;" id="msg">
						${requestScope.msg }
					</li>
				
				<li class="mb"><label>手机号码</label><input class="md" type="text"
					name="number" id="number" maxlength="11"
					value="${sessionScope.login.number }" /></li>
				<li class="ms"><label>短信验证</label><input name="" type="radio"
					value="" checked> 动态验证密码<input class="yzm" type="botton"
					value="获取验证" onclick="sendSms();" /></li>
				<li class="pass"><label>验证码</label><input type="password"
					class="password" name="yzm" id="yzm" maxlength="6" /></li>
				<li class="mb"><label>所选套餐</label> <select name="setid">

						<c:if test="${not empty param.setid}">
							<c:if test="${param.setid=='51' and  empty login.servNbr }">
								<option value="51">漏话提醒</option>
								<option value="21">通信助理基础包</option>
								<option value="22">通信助理信息包</option>
							</c:if>
							<c:if test="${param.setid=='51' and  not empty login.servNbr }">
								<option value="21">通信助理基础包</option>
								<option value="22">通信助理信息包</option>
							</c:if>
							<c:if test="${param.setid=='21' or param.setid=='31'}">
								<option value="22">通信助理信息包</option>
							</c:if>
						</c:if>
						<c:if test="${empty param.setid}">
							<option value="51">漏话提醒</option>
							<option value="21">通信助理基础包</option>
							<option value="22">通信助理信息包</option>
						</c:if>
				</select></li>
				<li class="login_but"><button onclick="javaScript:return upgrade();">升级套餐</button></li>
				
			</ul>
		</div>
		
	</form>
</body>
</html>