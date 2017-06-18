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
<title>存储柜管理</title>
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
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 存储柜管理</span></div>
<div class="content">
<form id="searchForm" action="<%=path%>/locker/queryForPage.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search">
		<p>
			<label>存储柜编号</label><input name="lockerNum" type="text" value="<%=RequestUtils.getAttribute(params,"lockerNum",0)%>"/>
			<label>存储柜面积</label><input name="lockerVolume" type="text" value="<%=RequestUtils.getAttribute(params,"lockerVolume",0)%>"/>
			<label>存储柜类型</label><input name="lockerType" type="text" value="<%=RequestUtils.getAttribute(params,"lockerType",0)%>"/>
		</p>
		<p>
			<label>所属货架</label><input name="goodsNum" type="text" value="<%=RequestUtils.getAttribute(params,"goodsNum",0)%>"/>
			<label>清查状态</label><input name="inventoryStatus" type="text" value="<%=RequestUtils.getAttribute(params,"inventoryStatus",0)%>"/>
			<label>RFID标签</label><input name="rfidNum" type="text" value="<%=RequestUtils.getAttribute(params,"rfidNum",0)%>"/>
		</p>
		<p>
			<label>所属货架</label><select id="rack" name="rackID">
				<option></option>
				<c:forEach var="r" items="${rackList }">
				<option value="${r.rackNum}">${r.rackName }</option>
				</c:forEach>
			</select>
		</p>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">存储柜列表</h1>
	    <div class="but1"><input name="" type="button" value="添加存储柜"  onclick="toAddPage();"/></div>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>存储柜编号</th>
			<th>存储柜面积</th>
			<th>存储柜类型</th>
			<th>所属货架</th>
			<th>清查状态</th>
			<th>RFID标签</th>
			<th>所属货架</th>    
			<th>操作</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
				<td>${v.lockerNum }</td>
				<td>${v.lockerVolume }</td>
				<td>${v.lockerType }</td>
				<td>${v.goodsNum }</td>
				<td>${v.inventoryStatus }</td>
				<td>${v.rfidNum }</td>
				<td>
				<c:forEach var="r" items="${rackList }">
				<c:if test="${v.rackID==r.rackNum }">${r.rackName }</c:if>
				</c:forEach>
				</td>
				<td><span class="tdbut">
					<a href="#" onclick="toEditPage(${v.lockerNum });">修改</a>
					<a href="#" onclick="toDetailPage(${v.lockerNum});">详情</a>
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
		var lockerNum = $('input[name="lockerNum"]').val();
		var lockerVolume = $('input[name="lockerVolume"]').val();
		var lockerType = $('input[name="lockerType"]').val();
		var goodsNum = $('input[name="goodsNum"]').val();
		var inventoryStatus = $('input[name="inventoryStatus"]').val();
		var rfidNum = $('input[name="rfidNum"]').val();
		var rackID = $('#rack option:selected').val();
		/* 获取参数*/
		var params = "&lockerNumQ="+lockerNum+"&lockerVolumeQ="+lockerVolume+"&lockerTypeQ="+
		lockerType+"&goodsNumQ="+goodsNum+"&inventoryStatusQ="+inventoryStatus+"&rfidNumQ="+
		rfidNum+"&rackIDQ="+rackID;
		return params;
	}
	
	function toEditPage(lockerNum){
		window.location.href="<%=path%>/locker/toEditPage.do?lockerNum="+lockerNum+getQueryParams();
	}
	
	function toDetailPage(lockerNum){
		window.location.href="<%=path%>/locker/toDetailPage.do?lockerNum="+lockerNum+getQueryParams();
	}
	
	function toAddPage(){
		window.location.href="<%=path%>/locker/toAddPage.do?Q=q"+getQueryParams();
	}

	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm",${page.currentIndex},${page.sizePerPage},${page.totalPage});
	});
</script>
</body>
</html>