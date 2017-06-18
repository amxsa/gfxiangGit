<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车路路后台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style type="text/css">
		body{background:#CAEDFA url(<%=path%>/images/login_bg.jpg) no-repeat center 0;margin:0;padding:0;color:#333;text-align:center;}
		a{text-decoration:none}
		a:link,a:visited{color:#333;text-decoration:underline}
		a:hover{color:#FF3000;text-decoration:none;}
		a:active{color:#333;text-decoration:none}
		ul,li{list-style:none;margin:0px;}
		img{border:none;}
		.login_box{width:350px;height:280px;margin:180px auto 0;text-align:left;padding:50px 20px 0 50px;}
		.login_box .use{height:120px;padding-left:75px;background:url(<%=path%>/images/login_bg1.gif) no-repeat 0 4px;}
		.login_box .use p{margin:0 0 15px;padding:0;}
		.login_box .use input{width:220px;height:26px;line-height:24px;border:1px solid #B3B3B3;background:#F0F0F0;font-size:14px;padding:0 5px;}
		.sub{padding-left:75px;}
		.sub input{margin-right:10px;width:102px;height:34px;line-height:34px;outline:none;border:none;background:url(<%=path%>/images/login_b.gif) no-repeat;cursor:pointer;color:#fff;padding-left:18px;font-size:20px;font-family:"微软雅黑";}
		.sub input.b1{background-position:0 0;}
		.sub input.b2{background-position:0 -68px;}
		.sub input.b1:hover{background-position:0 -34px;}
		.sub input.b2:hover{background-position:0 -102px;}
		.login_copy{width:100%;position:absolute;margin:0 auto;text-align:center;color:#222;line-height:30px;position:fixed;bottom:5px;left:0;}
		</style>
		<script language="javascript" type="text/javascript" src='<%=path %>/js/jquery.js'> </script>
		
	</head>
	<script>
	
	function forLogon(){
		
		if($("#textfield").val()==""){
			alert("请输入用户名");
			$("#textfield").focus();
			return false;
		} 
		if($("#textfield2").val()==""){
			alert("请输入密码");
			$("#textfield2").focus();
			return false;
		} 
		$("#LogonFormId").submit();
		
	}
	function forReset(){
		document.forms[0].reset();
	}	
	</script>
	<body >
		<form action="<%=path %>/manager/LoginManagerAction_login.do" id="LogonFormId" method="post" >
		<div id="maindiv">
		  <div class="login_box">
			 <div class="use">
			  <p><input name="username" type="text" id="textfield" class="input" placeholder="请输入用户名" /></p>
			  <p><input name="password" type="password" id="textfield2" class="input" placeholder="请输入密码" /></p>
			 	<p style="font: 12px;color: red;">
			 		<c:if test="${not empty result }">
        					${result }
        			</c:if>
        		<p>
			 </div>
			 <div class="sub"><input type="button" class="b1" value="登录" onclick="forLogon();" /><input type="button" class="b2" value="重置" onclick="forReset();"/></div>
			 
			</div>
		</form>
	</body>
</html>


