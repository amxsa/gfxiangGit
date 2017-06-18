<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>登录-宝安分局涉案财物管理系统</title>
<link type="text/css" href="css/main.css" rel="stylesheet" />
<style type="text/css">body{background:#7AC0F3 url(images/login_bg.jpg) no-repeat center 0;}</style>
<!--[if lte IE 8]> 
<script src="${pageContext.request.contextPath }/js/compatibleJs/IE8.js" type=”text/javascript”></script>
<![endif]-->


<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="<%=path%>/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
	function login() {
		var accVal=$("#account").val();
		if(accVal==""){
			alert('请输入账号');
			return false;
		}
		if($("#password").val()==""){
			alert('请输入密码');
			return false;
		}
		var options = {
			beforeSubmit :	function showRequest() {
				$('#inputlogin').val('登录中...');
				document.getElementById("inputlogin").disabled = true;
				return true;  
        	},
			success: function(data) {
				document.getElementById("inputlogin").disabled = false;
				$('#inputlogin').val('登录');
    			if(data.msg)
    				alert(data.msg);
    			else
    				window.location='index.jsp';
			}
		};
		$('form[name="command"]').ajaxSubmit(options);
	}
	
	$(function(){  
		$('#account').focus();
	});  
    function keyLogin(evt){
    	evt = (evt) ? evt : ((window.event) ? window.event : "");
    	var key = evt.keyCode?evt.keyCode:evt.which;
	   if(key==13){
	    login();
	   }
	}
</script>

</head>
<body onkeydown="keyLogin(event);">
<div class="login_box">
<form name="command" action="<%=path%>/account/login.do" method="post">
  <input type="hidden" name="ip" value="<%=request.getRemoteAddr()%>" />
  <p><label>用户名</label><input type="text" name="account" id="account" placeholder="请输入账号" /></p>
  <p><label>密&nbsp;&nbsp;&nbsp;码</label><input type="password" name="password" id="password" placeholder="请输入密码" /></p>
  <p class="logbut"><a href="javascript:void(0);"><input type="button" id="inputlogin" class="a1" value="登 录"  onclick="javascript:login();"/></a><a href="javascript:void(0);"><input type="button" class="a2" value="PKI登录" /></a></p>
</form>
</div>

</body>
</html>
