<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>流程管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript">

function lookFlowImage(key){
	window.location="<%=path%>/flowconf/imageFlow.do?key="+key; 
}


function doDelete(id) {
	if(confirm('该操作会影响正在运行中的流程信息,是否继续?')) {
		window.location="<%=path%>/flowconf/deleteFlow.do?deploymentId="+id;
	}
}
</script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 流程管理</span></div>
<div class="content">
<form id="searchForm" action="" method="post">
	<div class="toptit">
	    <h1 class="h1tit">流程列表</h1>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>ID</th>
			<th>KEY</th>
			<th>版本</th>
			<th>名称</th>
			<th>部署ID</th>
			<th>描述</th>
			<th>操作</th>
		</tr>
		<c:forEach var="aRecord" items="${result}">
			<tr>
				<td>${aRecord.id }</td>
				<td>${aRecord.key}</td>
				<td>${aRecord.version}</td>
				<td>${aRecord.name}</td>
				<td>${aRecord.deploymentId}</td>
				<td>${aRecord.description}</td>
				<td><span class="tdbut">
						<a href="<%=path%>/flowconf/detailFlow.do?key=${aRecord.key}">详情</a>
						<a href="javascript:doDelete('${aRecord.deploymentId}')">卸载</a>
						<a href="javascript:lookFlowImage('${aRecord.key}')">查看流程图</a>
					</span>
				</td>
			</tr>
		</c:forEach>
	</table>
</form>
</div>
</body>
</html>