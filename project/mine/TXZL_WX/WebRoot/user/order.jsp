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
<title>开通通信助理</title>
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
		function GO(){
			$("#msg").text('');
			var number = $.trim($("#number").val())
			var password = $.trim($("#password").val());
			var password2 =$.trim( $("#password2").val());
			var yzm =$.trim($("#yzm").val());
			if(!(/^1{1}(8[019]|33|53|7[7])[0-9]{8}$/).exec(number)){
				$("#msg").text('手机号码格式错误');
				return false;
			}
			if(!(/^[0-9]{1}[0-9]{5}$/).exec(yzm)){
				$("#msg").text('验证码格式错误');
				return false;
			}
			if(!(/^[0-9]{1}[0-9]{5}$/).exec(password)){
				$("#msg").text('密码格式错误');
				return false;
			}
			if(password!=password2){
				$("#msg").text('两次密码输入不一致');
				return false;
			}
			document.forms[0].submit();
		}
		function sendSms(){
			$("#msg").text("");
			var number = $("#number").val();
			if(!(/^1{1}(8[019]|33|53|7[7])[0-9]{8}$/).exec(number)){
				$("#msg").text('手机号码格式错误');
				return false;
			}
			$.post("<%=path%>/user/sms/sendOrderSms.do",{number:number},function(data){
				
				if(data!=""){
					$("#msg").text(data);
				}
			});
		}
</script>
</head>

	<div class="top">

<a class="back" onclick="history.back();"><img src="<%=path %>/images/back.png" /></a>

<h2>开通通信助理</h2><span><a href="<%=path%>/user/index.jsp"><img src="<%=path %>/images/home.png"></a></span>
</div>
	<form
		action="<%=path%>/user/account/orderAccount.do"
		method="post">
		<input type="hidden" name="platform" value="WX" /> <input
			type="hidden" name="operateType" value="A" />
		
		<div class="layout">

			<div class="crumb2">
				<h3>开通通信助理</h3>
			</div>
			<div class="addtx">
				<ul>
					<li id="msg" style="color: red;">
						
					</li>
					<li><label>手机号码</label><input type="text" name="number"
						id="number" maxlength="11" value="${empty sessionScope.login.number?sessionScope.number:sessionScope.login.number  }" /></li>

					<li><label>短信验证</label> <input type="text" name="yzm"
						id="yzm" maxlength="6" value="" style="width: 50px;" /> &nbsp;
						 <a href="#" onclick="sendSms();" id="smsid" style="color: blue;">获取验证</a></li>
					<li class="aa"><label>所选套餐</label> <select name="setid">

							<option value="51" ${param.setid=='51'?'selected':'' }>漏话提醒</option>
							<option value="21" ${param.setid=='21'?'selected':'' }>通信助理基础包</option>
							<option value="22" ${param.setid=='22'?'selected':'' }>通信助理信息包</option>

					</select>
					</li>
					<li><label>初始密码</label><input type="password" name="password"
						id="password" maxlength="6" value="" /></li>
					<li><label>确认密码</label><input type="password"
						name="password2" id="password2" maxlength="6" value="" /></li>

				</ul>
			</div>
			<div class="addbut2">
				<input type="button" value="确定" onClick="GO();" /> <input
					type="button" value="返回" onClick="javaScript:history.back(-1);" />
			</div>
		</div>
		
	</form>

</html>