<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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

  </head>
  
  <body>
   <form action="${pageContext.request.contextPath }/property/queryForPage.do" method="post">
   		财物编号：<input type="text" name="proID"/><br>
   		财物名称：<input type="text" name="proName"/><br>
   		状态：<input type="text" name="proStatus"/><br>
   		案件编号：<input type="text" name="jzcaseID"/><br>
   		案件名称：<input type="text" name="caseName"/><br>
   		立案开始时间：<input type="text" name="startTime"/><br>
   		立案结束时间：<input type="text" name="endTime"/><br>
   		<input type="submit" value="提交"/>
   </form>
   
   <form action="${pageContext.request.contextPath }/property/queryDetail.do" method="post">
   		财物编号：<input type="text" name="id"/><br>
   		
   		<input type="submit" value="提交"/>
   </form>
  </body>
</html>
