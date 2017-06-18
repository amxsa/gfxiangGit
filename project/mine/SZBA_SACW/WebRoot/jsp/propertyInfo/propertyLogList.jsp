<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>访问日志管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/tempForm.js"></script>
</head>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 查看财物轨迹</span>
</div>
<div class="content">
<form id="searchForm" action="<%=path%>/propertyLog/queryForPage.do" method="post">
	<input name="condiProNo" type="hidden" value="${condition.condiProNo}"/>
	<input name="condiProName" type="hidden" value="${condition.condiProName }"/>
	<input name="condiJzcaseId" type="hidden" value="${condition.condiJzcaseId }"/>
	<input name="condiCaseName" type="hidden" value="${condition.condiCaseName }"/>
	<input name="condiStartTime" type="hidden"  value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
	<input name="condiEndTime" type="hidden"  value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
	<input name="refererUrl" type="hidden" value="${refererUrl }"/>
	<input type="hidden" name="condiProId" value="${condition.condiProId}"/>
	<div class="toptit">
		<h1 class="h1tit">按条件查询</h1>
	</div>
	<div class="search">
		<p>
			<label>日期</label>
			<input style="width:150px;" name="condigStartTime" type="text"  
				value="<fmt:formatDate value='${condition.condigStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
			<i>至</i>
			<input style="width:150px;" name="condigEndTime" type="text"  
				value="<fmt:formatDate value='${condition.condigEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/></p>
		<div class="sbut">
			<input name="" type="submit" value="查询" />
			<input name="" type="button" value="返回" onclick="goBack()" style="margin-left:10px;"/>
		</div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">财物轨迹列表</h1>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" >
		<tr>
			<th width="10%">序号</th>
			<th width="20%">日期</th>
			<th width="20%">人员</th>
			<th width="20%">ip</th>
			<th width="30%">事项</th>
		</tr>
		<c:forEach var="v" items="${data}" varStatus="status">
			<tr>
				<td>${ status.index + 1}</td> 
				<td><fmt:formatDate value="${v.operateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${mapping:mappingAccountNameById(v.operator)}</td>
				<td>${v.ip }</td>
				<td>${v.memo} </td>
			</tr>
		</c:forEach>
	</table>
	<div class='page'></div>
</form>
</div>
<script type="text/javascript">
function goBack(){
	Common.tempForm.initData('${refererUrl}', '${conditions}');
}

$(document).ready(function() {
	// 生成页码
	Common.method.pages.genPageNumber("searchForm", ${pages.currentIndex}, ${pages.sizePerPage}, ${pages.totalPage});
});
</script>
</body>
</html>