<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>流程详情</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>><a href="javascript:history.go(-1)"> 流程管理</a>>> 流程详情</span></div>
<div class="content">
<form id="searchForm" action="" method="post">
	<!-- <div class="toptit">
	    <h1 class="h1tit">详情列表</h1>
	</div> -->
	  <%-- <table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>任务ID</th>
			<th>任务名称</th>
			<th>任务创建时间</th>
			<th>任务的办理人</th>
			<th>流程定义ID</th>
			<th>执行对象ID</th>
			<th>流程实例ID</th>
			
			<th>业务主键ID</th>
			<th>活动实例ID</th>
			<th>流程定义名</th>
			<th>是否挂起</th>
		</tr>
		<c:forEach var="t" items="${taskList}">
		<c:forEach var="p" items="${processInstanceList }">
		<c:if test="${t.processInstanceId==p.processInstanceId }">
			<tr>
				<td>${t.id }</td>
				<td>${t.name }</td>
				<td><fmt:formatDate value="${t.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td>${t.assignee }</td>
				<td>${t.processDefinitionId }</td>
				<td>${t.executionId }</td>
				<td>${t.processInstanceId }</td>
				
				<td>${p.businessKey }</td>
				<td>${p.activityId }</td>
				<td>${p.processDefinitionName }</td>
				<td>${p.suspended }</td>
			</tr>
		</c:if>
		</c:forEach>
		</c:forEach>
	</table> --%>
	  
	  
	  <div class="toptit">
	    <h1 class="h1tit">流程任务列表</h1>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>任务ID</th>
			<th>任务名称</th>
			<th>任务创建时间</th>
			<th>任务的办理人</th>
			<th>流程实例ID</th>
			<th>执行对象ID</th>
			<th>流程定义ID</th>
		</tr>
		<c:forEach var="v" items="${taskList}">
			<tr>
				<td>${v.id }</td>
				<td>${v.name }</td>
				<td><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td>${v.assignee }</td>
				<td>${v.processInstanceId }</td>
				<td>${v.executionId }</td>
				<td>${v.processDefinitionId }</td>
			</tr>
		</c:forEach>
	</table>
	<div class="toptit">
	    <h1 class="h1tit">流程实例列表</h1>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>实例ID</th>
			<th>定义ID</th>
			<th>业务主键ID</th>
			<th>父节点实例ID</th>
			<th>活动实例ID</th>
			<th>流程定义名</th>
			<th>流程定义ID</th>
			<th>流程定义版本</th>
			<th>部署ID</th>
			<th>是否挂起</th>
		</tr>
		<c:forEach var="v" items="${processInstanceList}">
			<tr>
			<td>${v.id }</td>
			<td>${v.processDefinitionId }</td>
			<td>${v.businessKey }</td>
			<td>${v.parentId }</td>
			<td>${v.activityId }</td>
			<td>${v.processDefinitionName }</td>
			<td>${v.processDefinitionKey }</td>
			<td>${v.processDefinitionVersion }</td>
			<td>${v.deploymentId }</td>
			<td>${v.suspended }</td>
			</tr>
		</c:forEach>
	</table>
</form>
</div>
</body>
</html>