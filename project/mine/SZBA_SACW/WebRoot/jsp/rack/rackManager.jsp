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
<title>货架管理</title>
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
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 货架管理</span></div>
<div class="content">
<form id="searchForm" action="<%=path%>/rack/queryForPage.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search"><div class="sp">
		<p>
			<label>货架编号</label><input name="rackNum" type="text" value="<%=RequestUtils.getAttribute(params,"rackNum",0)%>"/>
			<label>货架高度</label><input name="rackHeight" type="text" value="<%=RequestUtils.getAttribute(params,"rackHeight",0)%>"/>
			<label>货架长度</label><input name="rackLength" type="text" value="<%=RequestUtils.getAttribute(params,"rackLength",0)%>"/>
		</p>
		<p>
			<label>货架载重</label><input name="rackLoad" type="text" value="<%=RequestUtils.getAttribute(params,"rackLoad",0)%>"/>
			<label>所属保管室</label>
			<select id="storeroom" name="storeroomId" style="width:120px;">
				<option value="">全部</option>
				<c:forEach var="r" items="${storeRoomList }">
					<option value="${r.id }" <c:if test="${storeroomId==r.id}">selected="selected"</c:if>>${r.storeroomName }</option>
				</c:forEach>
			</select>
			
		</p></div>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">货架列表</h1>
	    <div class="but1"><input name="" type="button" value="添加货架"  onclick="toAddPage();"/></div>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>货架编号</th>
			<th>货架高度</th>
			<th>货架长度</th>
			<th>货架载重</th>
			<th>所属保管室</th>  
			<th>操作</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
				<td>${v.rackNum }</td>
				<td>${v.rackHeight }</td>
				<td>${v.rackLength }</td>
				<td>${v.rackLoad }</td>
				<td>
				<c:forEach var="r" items="${storeRoomList }">
				<c:if test="${v.storeroomId==r.id }">${r.storeroomName }</c:if>
				</c:forEach>
				</td>
				<td><span class="tdbut">
					<input type="hidden" value="${v.rackNum }"/>
					<a href="#" onclick="toEditPage(this);">修改</a>
					<a href="#" onclick="toDetailPage(this);">详情</a>
					</span>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class='page'></div>
</form>
</div>
<script type="text/javascript">
	function getQueryParams(){
		var rackNum = $('input[name="rackNum"]').val();
		var rackHeight = $('input[name="rackHeight"]').val();
		var rackLength = $('input[name="rackLength"]').val();
		var rackLoad = $('input[name="rackLoad"]').val();
		var storeroomId = $('#storeroom option:selected').val();
		var currentIndex = "${page.currentIndex}";
		var sizePerPage = "${page.sizePerPage}";
		/* 获取参数*/
		var params = "&rackNumQ="+rackNum+"&rackHeightQ="+rackHeight+"&rackLengthQ="+
		rackLength+"&rackLoadQ="+rackLoad+"&storeroomIdQ="+storeroomId
		+"&currentIndex="+currentIndex+"&sizePerPage="+sizePerPage;
		return params;
	}
	
	function toEditPage(obj){
		var rackNum=$(obj).prev().val();
		window.location.href="<%=path%>/rack/toEditPage.do?rackNum="+rackNum+getQueryParams();
	}
	
	function toDetailPage(obj){
		var rackNum=$(obj).prev().prev().val();
		window.location.href="<%=path%>/rack/toDetailPage.do?rackNum="+rackNum+getQueryParams();
	}
	
	function toAddPage(){
		window.location.href="<%=path%>/rack/toAddPage.do?Q=q"+getQueryParams();
	}

	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm",${page.currentIndex},${page.sizePerPage},${page.totalPage});
	});
</script>
</body>
</html>