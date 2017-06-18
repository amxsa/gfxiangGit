<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的电脑</title>
<script language="javascript">
	function setTitle(path) {
		document.title=path;
		
	}
</script>
</head>
<frameset rows="*" cols="20%,80%" id="all">
    <frame src="<%=path%>/workhelp/FileAction_init.do?sid=<%=System.currentTimeMillis()%>" scrolling="auto"  id="left" name="left"  />
    <frame src="<%=path%>/workhelp/FileAction_show.do?sid=<%=System.currentTimeMillis()%>" scrolling="auto" id="main" name="main"  />
</frameset>
</html>






  