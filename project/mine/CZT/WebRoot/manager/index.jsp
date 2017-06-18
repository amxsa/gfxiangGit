<%@ page contentType="text/html; charset=UTF-8" language="java"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path",path);
//未登录则进行页面转向
	
	if (request.getSession().getAttribute("login") == null) {
		response.sendRedirect("manager/logon.jsp");
	}
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车路路后台管理</title>
</head>
<frameset id="frameRows" rows="114,7,*" frameborder="no" border="0" framespacing="0">
  <frame name="topFrame" noresize="noresize" scrolling="no" src="<%=path%>/manager/top.jsp" />
  <frame name="topControl" noresize="noresize" scrolling="no" src="<%=path%>/manager/topControl.html" />
  <frameset id="frameCols" cols="185,7,*" frameborder="no" border="0" framespacing="0">
  	<frame src="<%=path%>/manager/left.jsp" name="leftFrame" scrolling="auto" />
	<frame src="<%=path%>/manager/leftControl.html" name="leftControl" scrolling="no" />
  	<frame src="<%=path%>/manager/TotalManagerAction_indexTotal.do" name="mainFrame" />
  </frameset>
</frameset><noframes></noframes>
</html>


