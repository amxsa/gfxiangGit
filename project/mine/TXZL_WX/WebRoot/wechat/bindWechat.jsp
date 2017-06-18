<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path  = request.getContextPath();
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
<title>绑定号码</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" language="javaScript">
		function bind(){
			document.forms[0].submit();
		}
		function sendSms(){
			var number = $("#account").val();
			$.post("<%=path%>/user/sms/sendSms.do", {
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
	<form action="<%=path%>/user/bindWechat/bind.do"
		method="post">
		<input type="hidden" name="openid" value="${param.openid }" />
		<div class="header">
			<img src="<%=path%>/images/banner.jpg" />
		</div>
		<div class="login">
			<ul>

				<li style="color: red;" id="msg">${requestScope.msg }</li>
				<li class="mb"><label>手机号码</label><input class="md" type="text"
					name="account" id="account" maxlength="12" placeholder="手机号码" /></li>
				<li class="ms"><label>登录模式</label><input name="" type="radio"
					value="" checked> 动态验证密码<input class="yzm" type="button"
					value="获取密码" onclick="sendSms();" /> <!--  
				<p>
					<input name="" type="radio" value="">189邮箱密码
				</p>
				--></li>
				<li class="pass"><label>输入密码</label><input type="password"
					class="password" name="passwordSms" id="password"
					placeholder="输入密码" maxlength="6" /></li>
				<li class="login_but"><button type="button" onclick="bind();">绑定</button>


				</li>
			</ul>
		</div>

	</form>
</body>
</html>