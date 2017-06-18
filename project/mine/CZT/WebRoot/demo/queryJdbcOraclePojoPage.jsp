<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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

<title>My JSP 'success.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=path%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function query(){
		document.forms[0].submit();
	}
</script>
</head>

<body>
	
	<form action="<%=path%>/demo/DemoAction_queryJdbcOraclePojoPage.do"
		method="post">
		时间<input name="starttime" type="text"
						value="${request.starttime }"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 120px;">~<input
						name="endtime" type="text" value="${request.endtime }" style="width: 120px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;<input type="button" value="GO" onclick="query();">
		<table>
			<c:forEach var="list1" items="${pageResult.content}">
				<tr>
					<td>${list1.anum }</td>

					<td>${list1.bnum }</td>
					<td>${list1.starttime }</td>
					<td>${list1.deelno }</td>
				</tr>
			</c:forEach>
			
		</table>
		${requestScope.pageResult.linkTo }
	</form>
</body>

</html>
