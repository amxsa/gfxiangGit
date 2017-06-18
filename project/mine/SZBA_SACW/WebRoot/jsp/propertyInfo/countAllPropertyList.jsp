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
			财务统计</span>
	</div>
	<form id="searchForm" action="<%=path%>/property/queryForPage.do?viewName=countAllPropertyList"
		method="post">
		<input type="hidden" name="viewName" value="countAllPropertyList" />
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
					<label>财物状态</label>
			             <select name="condiProStatus"  style="width:200px;">
   			                   <option value=""></option>
			                   <option value="WDJ">未登记</option>
			                   <option value="YDJ">已登记</option>
			                   <option value="DRZCK">待入暂存库</option>
			                   <option value="DRZXK">待入中心库</option>
			                   <option value="DCZCK">待出暂存库</option>
			                   <option value="DCZXK">待出中心库</option>
			                   <option value="DTH">待退还</option>
			                   <option value="DMS">待没收</option>
			                   <option value="DPM">待拍卖</option>
			                   <option value="DFH">待发还</option>
			                   <option value="DXH">待销毁</option>
			                   <option value="DSJGK">待收缴国库</option>
			                   <option value="SQRZCK">申请入暂存库</option>
			                   <option value="SQRZXK">申请入中心库</option>
			                   <option value="SQZCK2ZXK">申请转库（暂存库to中心库）</option>
			                   <option value="SQCZCK">申请出暂存库</option>
			                   <option value="SQCZXK">申请出中心库</option>
			                   <option value="SQTH">申请退还</option>
			                   <option value="SQMS">申请没收</option>
			                   <option value="SQPM">申请拍卖</option>
			                   <option value="SQFH">申请发还</option>
			                   <option value="SQXH">申请销毁</option>
			                   <option value="SQSJGK">申请收缴国库</option>
			                   <option value="SQDY">申请调用</option>
			                   <option value="SQDYGH">申请调用归还</option>
			                   <option value="DYCZCK">调用出暂存库</option>
			                   <option value="DYCZXK">调用出中心库</option>
			                   <option value="ZCK2ZXK">转库中（暂存库to中心库）</option>
			                   <option value="YTH">已退还</option>
			                   <option value="YMS">已没收</option>
			                   <option value="YPM">已拍卖</option>
			                   <option value="YFH">已发还</option>
			                   <option value="XH">销毁</option>
			                   <option value="YSJGK">已收缴国库</option>
			                   <option value="YRZCK">已入暂存库</option>
			                   <option value="YRZXK">已入中心库</option>
			                   <option value="YCZCK">已出暂存库</option>
			                   <option value="YCZXK">已出中心库</option>
			                   <option value="AJ">案结</option>
		                 </select>
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
				</p>
				</div>
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchPro()" />
					<input name="" type="button" value="导出" onclick="exportAllPro()" />
				</div>
				
			</div>


			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th>案件名称</th>
					<th>案件编号</th>
					<th>涉案人</th>
					<th>财物名称</th>
					<th>财物类别</th>
					<th>财物来源</th>
					<th>财物数量</th>
					<th>计量单位</th>
					<th>财物特征</th>
					<th>登记人</th>
					<th>登记时间</th>
					<th>登记部门</th>
					<th>财物状态</th>
					<th>入库时间</th>
					<th>入库经办人</th>
					<th>所在仓库</th>
					<th>库位</th>
					<th>出库时间</th>
					<th>出库经办人</th>
				</tr>
				<c:forEach items="${properties }" var="cop" varStatus="sta">
					<tr>
						<td>${cop.caseName}</td>
					    <td>${cop.jzcaseId}</td>
					    <td>${cop.suspectName }</td>
					    <td>${cop.name }</td>
					    <td>${cop.categoryName }</td>
					    <td>${cop.origin }</td>
						<td><fmt:formatNumber type="number" value="${cop.quantity}" maxFractionDigits="0"/></td>
						<td>${cop.unit}</td>
						<td>${cop.characteristic }</td>
						<td>${cop.transactor}</td>
						<td><fmt:formatDate value="${cop.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${cop.authority}</td>
						<td>${mapping:mappingProStatus(cop.status) }</td>
						<td><fmt:formatDate value="${cop.intoHouseTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${cop.intoHousePerson}</td>
					    <td>${cop.storehouseName}</td>
					    <td>${cop.locationName}</td>
					    <td><fmt:formatDate value="${cop.outHouseTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${cop.outHousePerson }</td>
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
	    
	    //设置下拉框默认值
	    $('select[name="condiProStatus"]').val('${condition.condiProStatus}');
	});
	
	
	function searchPro(){
	   var f = document.getElementById('searchForm');
       f.action="<%=path%>/property/queryForPage.do?viewName=countAllPropertyList";
       f.submit();
	}
	
	function exportAllPro(){
	   var f = document.getElementById('searchForm');
       f.action="<%=path%>/property/exportAllProperty.do?viewName=countAllPropertyList";
       f.submit();
	}
	
	
</script>
</body>
</html>