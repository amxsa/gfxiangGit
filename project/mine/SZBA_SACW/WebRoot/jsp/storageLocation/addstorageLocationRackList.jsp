<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
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
<title>首页</title>
</head>
<body>

	<form id="searchForm" action="<%=path%>/rack/queryForPageToStorage.do"
		method="post">
		<div class="search">
			<p>
				<label>货架编号</label><input name="rackNum" type="text" value="<%=RequestUtils.getAttribute(params,"rackNum",0)%>"/>
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchCase()" />
				</div>
			</p>
		</div>
		<table width="100%" cellpadding="0" cellspacing="1" border="0"
			id="table_hover" class="table"  style="word-break:break-all; word-wrap:break-all;">
			<thead>
				<tr>
					<th style="width:6%;">选择</th>
					<th>货架编号</th>
					<th>货架高度</th>
					<th>货架长度</th>
					<th>所属保管室</th>  
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data }" var="v" varStatus="sta">
					<tr>
						<td>
							<input type="radio" name="id" value="${v.id }" />
						</td>
						<td>${v.rackNum }</td>
						<td>${v.rackHeight }</td>
						<td>${v.rackLength }</td>
						<td>
						<c:forEach var="r" items="${storeRoomList }">
						<c:if test="${v.storeroomId==r.id }">${r.storeroomName }</c:if>
						</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class='page'></div>
	</form>
<script type="text/javascript">
	
	$(document).ready(function() {
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});
	function searchCase(){
		$("#searchForm").submit();
	}
</script>
</body>
</html>