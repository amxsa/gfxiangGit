<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.Map" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>重置密码</title>
<link type="text/css" href="<%=basePath%>css/main.css" rel="stylesheet" />
<script language="javascript" type="text/javascript" src='<%=request.getContextPath()%>/js/jquery-1.9.1.min.js'></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<%-- <script language="javascript" src="<%=request.getContextPath()%>/js/table_hover.js"></script> --%> 
</head> 
<body>

<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 重置密码</span></div>
<form name="accountInfo" id="accountInfo" method="post" action="<%=request.getContextPath()%>/account/changePassword.do">
<div class="content">
 <ul class="list02">
   <li><label>新密码：</label><input id="password" name="newPsd" type="password" placeholder="第一次输入"/></li>
   <li><label>确认密码：</label><input id="password2" name="newPsd2" type="password" placeholder="第二次输入"/></li>
 </ul>
 <div class="dbut">
     <input name="" type="button" value="保存"  onclick="submitForm()"/>
     <a href="javascript:history.go(-1);">返回</a> 
 </div>
</div>          
</form>


<script type="text/javascript">
   function submitForm(){
      if(checkSubmit()){
           var newPsd= $.trim($("#password").val());
           var result="";
	       $.ajax({
					type: "POST",
					url: "<%=request.getContextPath()%>/account/changePassword.do?accountPass=${accountPass }&newPsd="+newPsd,
					async:true,
					dataType:"json",
					success: function(data){
					   result = data.message;
	                   if(result!='true'){
		                  alert("重置密码失败");
		               }
		               else{
		                  alert("重置密码成功");
		                  window.location.href="<%=request.getContextPath()%>/account/queryForPage.do";
		               }
					}
				});
        }
     } 
  
   function checkSubmit() {
	    var pass1= $.trim($("#password").val());
		var pass2= $.trim($("#password2").val());
		if(pass1=='') {
			alert("新密码不能为空");
			return false;
		}
		if(pass2==''){
		    alert("确认密码不能为空");
			return false;
		}
		/* var reg=/\d{6}/; */
		var reg = /^\d{6}$/;
		if(!reg.test(pass1)){
			alert("新密码必须是6位数字");
			return false;
		}
		if(pass1!=pass2){
			alert("新密码和确认密码不一致");
			$("#password").val("");
			$("#password2").val("");
			return false;
		}
		return true;		
	}
</script>
</body>
</html>
