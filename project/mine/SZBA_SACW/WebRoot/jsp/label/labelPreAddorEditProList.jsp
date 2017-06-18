
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

	<form id="searchForm" action="<%=path %>/label/queryForTProPage.do"
		method="post">
		<div class="search">
			<p>
				<label>财物编号</label>
				<input name="condiProNo" style="width:120px;" type="text" value="${condition.condiProNo}"/>
				<label>财物名称</label>
				<input name="condiProName" style="width:120px;"type="text" value="${condition.condiProName }"/>
				
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchCase()" />
				</div>
			</p>
		</div>
		<table width="100%" cellpadding="0" cellspacing="1" border="0"
			id="table_hover" class="table"  style="word-break:break-all; word-wrap:break-all;">
			<thead>
				<tr>
					<th style="width:5%;">选择</th>
					<th style="width:13%;">案件名称</th>
					<th style="width:13%;">案件编号</th>
					<th style="width:13%;">财物名称</th>
					<th style="width:13%;">财物类别</th>
					<th style="width:13%;">财物来源</th>
					<th style="width:13%;">财物数量</th>
					<th style="width:3%;">计量单位</th>
					<th style="width:13%;">财物特征</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tProperties" items="${tProperties}">
 	<tr>
	   <td ><input type="radio" name="proNo" id="proNo" value="${tProperties.proNo }" /></td>

	   <td>${tProperties.caseName }</td>
	   <td>${tProperties.jzcaseId }</td>
	   <td><a id="${tProperties.proNo }">${tProperties.proName }</a></td>
	 
	   <c:if test="${tProperties.proType=='YBCW' }">
	 	<td>一般财物</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='YHBZ' }">
	 	<td>烟花爆竹</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='JHQZ' }">
	 	<td>缴获枪支</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='GZDJLA' }">
	 	<td>管制刀具(立案)</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='GZDJBLA' }">
	 	<td>管制刀具(立案)</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='YHWPTS' }">
	 	<td>淫秽物品（图书）</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='YHWPGP' }">
	 	<td>淫秽物品（光盘）</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='DBGJ' }">
	 	<td>赌博工具</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='BBCCW' }">
	 	<td>不保存的财物</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='DZWZY' }">
	 	<td>电子物证（有存储介质)</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='DZWZW' }" >
	 	<td>电子物证（无存储介质）</td>
	 	</c:if>
	 	<c:if test="${tProperties.proType=='' }">
	 	<td></td>
	 	</c:if>
	   <td>${mapping:mappingProOrigin(tProperties.proOrigin )}</td>
	   <td>${tProperties.proQuantity }</td>
	   <td>${tProperties.proUnit }</td>
	   <td>${tProperties.proCharacteristic }</td>
	   
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