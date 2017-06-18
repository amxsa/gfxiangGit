<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link type="text/css" href="<%=path%>/css/main.css" rel="stylesheet" />
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 流程跟踪</span></div>

	<div class="content">
		
		<%--入暂存库没有审批信息<c:if test="${application.procedureCode!='PROCEDURE001' }"> --%>
			<div class="toptit">
				<h1 class="h1tit">流程跟踪</h1>
			</div>
			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				class="table">
				<tr>
					<th>提交时间</th>
					<th>环节</th>
					<th>处理人员</th>
					<th>处理时间</th>
					<th>意见</th>
				</tr>
				<c:forEach var="track" items="${trackList}">
				<tr>	
					<td><fmt:formatDate value="${track.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${track.userTaskName }</td>
					<td>${mapping:mappingAccountNameById(track.handlers) }</td>
					<td><fmt:formatDate value="${track.finishTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						${track.comment }
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<div class="toptit">
				<h1 class="h1tit">流程图</h1>
			</div>
			<ul class="list02">
				<li class="w100">
					<img src="${pageContext.request.contextPath}${picPath}"/>
				</li>
			</ul>
	</div>
	<div class="dbut">
	    <a href="javascript:history.go(-1)">返回</a>
	</div>
</div>
<script type="text/javascript">
	
</script>
</body>
</html>