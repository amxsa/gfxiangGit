<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>修改用户</title>
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a
			href="<%=path%>/visitLog/queryForPage.do"
			target="main">首页 </a>>> 查看访问日志</span>
	</div>
	<div class="content">
		<div class="toptit">
			<h1 class="h1tit">访问日志</h1>
		</div>
		<ul class="list02a w50" style="height:400px;">
			<li>
				<label>账号：</label>
				<span>${entity.account }</span>
			</li>
			<li>
				<label>sessionId：</label>
				<span>${entity.sessionId }</span>
			</li>
			
			<li>
				<label>ip：</label>
				<span>${entity.ip }</span>
			</li>
			<li>
				<label>访问时间：</label>
				<span><fmt:formatDate value="${entity.visitTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			</li>
			
			<li class="w100">
				<label>操作方式：</label>
				<span>${entity.httpMethod }</span>
			</li>
			
			<li class="w100" style="height:220px;">
				<label>url：</label>
				<div style="word-wrap: break-word;word-break:break-all;">
					${entity.url }
				</div>
			</li>
		</ul>
		<div class="dbut">
			<!-- <a href="#" onclick="goback();">返回</a> -->
			<a href="javascript:history.go(-1)" >返回</a>
		</div>
	</div>
<script type="text/javascript">
	//返回按钮点击事件
	function goback(){
		history.go(-1);
	}
</script>
</body>
</html>