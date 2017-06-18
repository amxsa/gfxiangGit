<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'testProList.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
.tab td{
	border: 1px solid #dddddd;
	padding:5px;
}
</style>
</head>

<body>
	<table class="tab">
		<tr>
			<td>
				添加初始值
			</td>
			<td>
				<form action="<%=basePath%>/test/addIdHolder.do" method="post">
					类型：<input type="text" name="idType" /><br> 
					初始值：<input type="text" name="idValue" /><br> 
					<input type="submit" value="提交" />
				</form>
			</td>
		</tr>
		<tr>
			<td>
				根据类型获取值
			</td>
			<td>
				<form action="<%=basePath%>/test/getIdHolder.do" method="post">
					类型：<input type="text" name="idType" /><br> 
					<input type="submit" value="提交" />
				</form>
			</td>
		</tr>
	</table>
</body>
</html>
