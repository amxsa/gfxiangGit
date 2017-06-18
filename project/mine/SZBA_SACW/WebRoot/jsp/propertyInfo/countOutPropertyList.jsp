<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			出库统计</span>
	</div>
	<form id="searchForm" action="<%=path%>/property/queryForPage.do?viewName=countOutPropertyList"
		method="post">
		<div class="content">
			<div class="toptit">
				<h1 class="h1tit">按条件查询</h1>
			</div>
			<div class="search"><div class="sp">
				<p>
					<label>财物编号</label><input name="condiProNo" type="text" value="${condition.condiProNo}"  style="width:170px;"/><label>财物名称</label><input
						name="condiProName" type="text" value="${condition.condiProName }"   style="width:120px;"/>

				</p>
				<p>
					<label>案件编号</label><input name="condiJzcaseId" type="text"
						value="${condition.condiJzcaseId }"   style="width:130px;"/><label>案件名称</label><input
						name="condiCaseName" type="text"
						value="${condition.condiCaseName }"   style="width:120px;"/>
				</p>
				<p>
					<label>立案时间</label><input name="condiStartTime" type="text"
						value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/><i>至</i><input
						name="condiEndTime" type="text"
						value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
				</p></div>
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchPro()" />
					<input name="" type="button" value="导出" onclick="exportOutPro()" />
				</div>
				
			</div>


			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th>案件名称</th>
					<th>案件编号</th>
					<th>涉案人</th>
					<th>财物名称</th>
					<th>财物数量</th>
					<th>计量单位</th>
					<th>登记人</th>
					<th>财物状态</th>
					<th>出库时间</th>
					<th>经办人</th>
					<th>所在仓库</th>
					<th>库位</th>
				</tr>
				<c:forEach items="${properties }" var="cop" varStatus="sta">
					<tr>
						<td>${cop.caseName}</td>
					    <td>${cop.jzcaseId}</td>
					    <td>${cop.suspectName }</td>
					    <td>${cop.name }</td>
						<td><fmt:formatNumber type="number" value="${cop.quantity}" maxFractionDigits="0"/></td>
						<td>${cop.unit}</td>
						<td>${cop.transactor}</td>
						<td>${mapping:mappingProStatus(cop.status) }</td>
						<td><fmt:formatDate value="${cop.outHouseTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					    <td>${cop.handler}</td>
					    <td>${cop.storehouseName }</td>
						<td>${cop.locationName }</td>
					</tr>
				</c:forEach>
			</table>


			<div class='page'></div>
	</form>
	</div>
	<script type="text/javascript">
	
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});
	
	function searchPro(){
	   var f = document.getElementById('searchForm');
       f.action="<%=path%>/property/queryForPage.do?viewName=countOutPropertyList";
       f.submit();
	}
	
	function exportOutPro(){
	   var f = document.getElementById('searchForm');
       f.action="<%=path%>/property/exportOutProperty.do?viewName=countOutPropertyList";
       f.submit();
	}
</script>
</body>
</html>