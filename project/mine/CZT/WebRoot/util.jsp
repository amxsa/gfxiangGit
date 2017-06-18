<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>SQL</title>
		<style type="text/css">
<!--
body {
	background-image: url(../images_bak/bg.gif);
	background-repeat: repeat-x;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	color: #666666
}

.show {
	width: 50px;
	height: 20px;
	visibility: visible
}

.hidden {
	width: 50px;
	height: 20px;
	visibility: hidden
}
-->
</style>
		<link href="style.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
<!--
.STYLE2 {
	color: #2189B6;
	font-size: 16px;
}

.STYLE3 {
	color: #1896D8;
	font-size: 16px;
}

.STYLE4 {
	color: #073887
}

body,td,th {
	font-size: 12px;
}
-->
</style>
		<script type="text/javascript">
	function init() {
		document.getElementById('name').focus();
	}
	function check() {
		var name=document.getElementById('name').value;
		var password=document.getElementById('password').value;
		if(name=='') {
			alert('用户名不能为空');
			return false;
		}
		if(password=='') {
			alert('密码不能为空');
			return false;
		}
		return true;
	}
</script>
	</head>
	<body onload="init()">
		<c:if test="${empty sessionScope.workSQL}">
			<form action="<%=path %>/workhelp/SQLAction_login.do" method="post" 
			 	onsubmit="return check();" >
				<table border="0" cellpadding="5" cellspacing="0" align="center"
					background="">
					<tr bgcolor="#073887">
						<td height="25" colspan="3" align="center" bgcolor="#3296C3">
							<font color="#FFFFFF"><strong>管理员登录</strong> </font>
						</td>
					</tr>
					<tr>
						<td width="180" height="25" align="right">
							用户名：
						</td>
						<td height="25" colspan="2">
							<input type="text" id="name" name="name" class="input1"
								size="22" />
						</td>
					</tr>
					<tr>
						<td width="180" height="25" align="right">
							密&nbsp;&nbsp;码：
						</td>
						<td height="25" colspan="2">
							<input type="password" id="password" name="password"
								maxlength="20" class="input1" size="23">
						</td>
					</tr>

					<tr>
						<td height="25" colspan="3" align="center">
							<font color="#ff0000">${requestScope.msg}</font>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<table align="center">
								<tr>
									<td height="25" align="right">
									<input type="submit" value="登录">
										
									</td>
									<td width="30">
										&nbsp;
									</td>
									<td height="25" align="left">
										<input type="reset" value="取消">
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</c:if>
		<c:if test="${not empty sessionScope.workSQL}">
			<a href="<%=path%>/workhelp/sql.jsp">执行</a>
		</c:if>
	</body>
</html>

