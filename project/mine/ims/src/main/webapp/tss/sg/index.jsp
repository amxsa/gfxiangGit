<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.threegms.sdplatform.util.StringUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String fansId = StringUtil.getReqId(request,"fansId");
String openid = StringUtil.getReqId(request,"openid");
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<!-- 删除苹果默认的工具栏和菜单栏 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<!-- 设置苹果工具栏颜色 -->
<meta name="format-detection" content="telephone=no, email=no" />
<!-- 忽略页面中的数字识别为电话，忽略email识别 -->
<!-- 启用360浏览器的极速模式(webkit) -->
<meta name="renderer" content="webkit">
<!-- 避免IE使用兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
<meta name="HandheldFriendly" content="true">
<!-- 微软的老式浏览器 -->
<meta name="MobileOptimized" content="320">
<!-- uc强制竖屏 -->
<meta name="screen-orientation" content="portrait">
<!-- QQ强制竖屏 -->
<meta name="x5-orientation" content="portrait">
<!-- UC强制全屏 -->
<meta name="full-screen" content="yes">
<!-- QQ强制全屏 -->
<meta name="x5-fullscreen" content="true">
<!-- UC应用模式 -->
<meta name="browsermode" content="application">
<!-- QQ应用模式 -->
<meta name="x5-page-mode" content="app">
<!-- windows phone 点击无高光 -->
<meta name="msapplication-tap-highlight" content="no">
<!-- 适应移动端end -->
<title>韶关首页</title>
<link rel="stylesheet" type="text/css" href="css/bath.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script src="js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="js/check.js"></script>
<script type="text/javascript" src="js/getJson.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/hiddenMenu.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('.confirm_btn').click(function(event) {
    	ajaxSg.sg_ajaxClick({url:'${pageContext.request.contextPath}/front/manager/login'},function(data){
    	//alert(dat.status);
    	//data = eval("("+data+")");
    	//alert(data["msg"]);
		if (data.status) {
			if(data["msg"]=="0"){
				msgBox(data["message"],function(){
				});
				$("#empNo").focus(); 
				return;
			}else if(data["msg"]=="1"){
				msgBox(data["message"],function(){
				});
				$("#password").val("").focus(); 
				return;
			}else if(data["msg"]=="3"){
				msgBox(data["message"],function(){
				});
				$("#empNo").val("").focus(); 
				$("#password").val(""); 
				return;
			}else if(data["msg"]=="2"){
				location.href="${pageContext.request.contextPath}/front/manager/toIndex?fansId="+$('#fansId').val()+"&openid="+$('#openid').val();
			}
		}
    	}
    	);
    });
})
</script>
</head>
<body>
	<div class="wrapper">
		<div class="main_content">
			 <input  type="hidden" name="fansId" id="fansId"  sg_json="*" value="<%=fansId %>"/>
			 <input  type="hidden" name="openid" id="openid" sg_json="*" value="<%=openid %>"/>
			<img src="img/ban.png" alt="" width="100%" class="ban" />
			<!-- 登录 -->
			<div class="item">
				<span class="left_icon"></span> <input type="text"
					placeholder="请输入工号/手机号" class="ipt" name="empNo" id="empNo"
					sg_check="empty|工号不能为空" sg_json="*" />
			</div>
			<div class="item">
				<span class="left_icon2"></span> <input type="password"
					placeholder="请输入密码" class="ipt" name="password" id="password"
					sg_check="empty|密码不能为空" sg_json="*" />
			</div>
			<div class="confirm_btn">登录</div>
		</div>
	</div>
</body>
</html>