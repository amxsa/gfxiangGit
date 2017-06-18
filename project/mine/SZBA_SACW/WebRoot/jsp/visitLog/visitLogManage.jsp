<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
</head>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 访问日志管理</span>
</div>
<div class="content">
<form id="searchForm" action="<%=path%>/visitLog/queryForPage.do" method="post">
	<div class="toptit">
		<h1 class="h1tit">按条件查询</h1>
	</div>
	<div class="search"><div class="sp">
		<p>
			<label>账号</label><input name="account" type="text" value="<%=RequestUtils.getAttribute(params,"account",0)%>"/>
			<label>sessionId</label><input name="sessionId" style="width:260px;" type="text" value="<%=RequestUtils.getAttribute(params,"sessionId",0)%>" />
			<label style="width:30px;">ip</label><input name="ip" type="text" style="width:120px;" value="<%=RequestUtils.getAttribute(params,"ip",0)%>"/>
			<label style="width:30px;">url</label><input name="url" type="text" style="width:230px;" value="<%=RequestUtils.getAttribute(params,"url",0)%>" />
		</p>
		</div>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">访问日志列表</h1>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" >
		<tr>
			<th width="10%">账号</th>
			<th width="20%">sessionId</th>
			<th width="14%">ip</th>
			<th width="14%">访问时间</th>
			<th width="18%">请求路径</th>
			<th width="14%">请求方式</th>
			<th width="10%">操作</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
				<td>${v.account }</td>
				<td>${v.sessionId }</td>
				<td>${v.ip }</td>
				<td><fmt:formatDate value="${v.visitTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${fn:substring(v.url,0,fn:indexOf(v.url, ".do"))}... </td>
				<td>${v.httpMethod }</td>
				<td>
					<span class="tdbut"><a href="#" onclick="todetailPage(${v.id})">详情</a></span>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class='page'></div>
</form>
</div>
<script type="text/javascript">
	function todetailPage(id){
		window.location.href="<%=path%>/visitLog/todetailPage.do?id="+id;
	}
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});
</script>
</body>
</html>