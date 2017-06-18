<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	session.removeAttribute("WorkSQL");
	response.sendRedirect(path+"/util.jsp");
%>
