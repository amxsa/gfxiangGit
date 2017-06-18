<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<%
	String tempProIds = request.getParameter("tempProIds");
	StringBuffer tempProName=new StringBuffer();
	if(tempProIds!=null){
		List<TProperty> list = TPropertyBiz.queryProperty(tempProIds.split(",")) ;
		if(list!=null){
			for(int i=0;i<list.size();i++){
				tempProName.append(list.get(i).getProName()).append(",");
			}
		}
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
</head>
<body>

	<form id="searchForm" action="<%=path%>/property/queryCase.do"
		method="post">
		<div class="search" style="padding-left: 70px;">
			<p>
				<label>案件编号</label>
				<input name="jzcaseID" style="width:180px;" type="text" value="${condiParams.jzcaseID[0]}" />
				<label>案件名称</label>
				<input name="caseName" style="width:180px;" type="text" value="${condiParams.caseName[0] }"/>
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
					<th style="width:40%;">案件名称</th>
					<th style="width:20%;">案件编号</th>
					<th style="width:17%;">案件性质</th>
					<th style="width:16%;">责任领导</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="data" varStatus="sta">
					<tr>
						<td>
							<input type="radio" name="caseID" value="${data.caseID }" />
						</td>
						<td>${data.caseName }</td>
						<td>${data.jzcaseID }</td>
						<td>
							<c:if test="${data.caseType=='1' }">
								刑事案件
							</c:if>
							<c:if test="${data.caseType=='2' }">
								行政案件
							</c:if>
						</td>
						<td>${data.leader }</td>
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