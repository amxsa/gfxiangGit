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
<title>库位管理</title>
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
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 库位管理</span></div>
<div class="content">
<form id="searchForm" action="<%=path%>/storageLocation/queryForPage.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search"><div class="sp">
		<p>
			<label>库位编号</label>
			<input name="locationNum" type="text" value="<%=RequestUtils.getAttribute(params,"locationNum",0)%>"/>
			<label>库位类型</label>
			<input type="hidden" value="<%=RequestUtils.getAttribute(params,"locationType",0)%>"/>
			<select name="locationType" style="width:120px;">
				<jsp:include page="/jsp/plugins/storageLocation_options.jsp" />
			</select>
			<label>库存状态</label>
			<input type="hidden" value="<%=RequestUtils.getAttribute(params,"inventoryStatus",0)%>"/>
			<select name="inventoryStatus">
				<jsp:include page="/jsp/plugins/storageLocation_status_options.jsp" />
			</select>
		</p>
		<p>
			<label>RFID编号</label><input name="rfidNum" type="text" value="<%=RequestUtils.getAttribute(params,"rfidNum",0)%>"/>
			<label>所属保管室</label>
			<select id="storeroom" name="storeroomID" style="width:120px;">
				<option value="">全部</option>
				<c:forEach var="r" items="${storeRoomList }">
				<option value="${r.id }" <c:if test="${storeroomID==r.id}">selected="selected"</c:if>>${r.storeroomName }</option>
				</c:forEach>
			</select>
			<label>所属货架</label>
			<select id="rackId" name="rackId" style="width:120px;">
				<option value="">全部</option>
				<c:forEach var="r" items="${rackList }">
				<option value="${r.id }" <c:if test="${rackId==r.id}">selected="selected"</c:if>>${r.rackNum }</option>
				</c:forEach>
			</select>
		</p>
		</div>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">库位列表</h1>
	    <div class="but1"><input name="" type="button" value="添加库位"  onclick="toAddPage();"/></div>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>库位编号</th>
			<th>库位名称</th>
			<th>库位类型</th>
			<th>库位状态</th>
			<th>存放数量</th>
			<th>已放数量</th>
			<th>RFID编号</th>
			<th>所属保管室</th>
			<th>所属货架</th>
			<th>操作</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
				<td>${v.locationNum }</td>
				<td>${v.locationName }</td>
				<td name="locationType">${v.locationType }</td>
				<td name="inventoryStatus">${v.inventoryStatus }</td>
				<td>${v.depositNum }</td>
				<td>${v.goodsNum }</td>
				<td>${v.rfidNum }</td>
				<td>
				<c:forEach var="r" items="${storeRoomList }">
				<c:if test="${v.storeroomID==r.id }">${r.storeroomName }</c:if>
				</c:forEach>
				</td>
				<td>${v.rackName }</td>
				<td><span class="tdbut">
					<input type="hidden" name="locationNum" value="${v.locationNum }"/>
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
		var locationNum = $('input[name="locationNum"]').val();
		var locationType = $('select[name="locationType"]').val();
		var inventoryStatus = $('select[name="inventoryStatus"]').val();
		var rfidNum = $('input[name="rfidNum"]').val();
		var storeroomID = $('#storeroom').val();
		var rackId = $('#rackId').val();
		var currentIndex = "${page.currentIndex}";
		var sizePerPage = "${page.sizePerPage}";
		
		/* 获取参数*/
		var params = "&locationNumQ="+locationNum+"&locationTypeQ="+locationType+
		"&inventoryStatusQ="+inventoryStatus+"&rfidNumQ="+rfidNum+"&rfidNumQ="+
		rfidNum+"&storeroomIDQ="+storeroomID+"&rackIdQ="+rackId+"&currentIndex="+
		currentIndex+"&sizePerPage="+sizePerPage;
		return params;
	}
	
	function toEditPage(obj){
		var locationNum=$(obj).prev().val();
		window.location.href="<%=path%>/storageLocation/toEditPage.do?locationNum="+locationNum+getQueryParams();
	}
	
	function toDetailPage(obj){
		var locationNum=$(obj).prev().prev().val();
		window.location.href="<%=path%>/storageLocation/toDetailPage.do?locationNum="+locationNum+getQueryParams();
	}
	
	function toAddPage(){
		window.location.href="<%=path%>/storageLocation/toAddPage.do?Q=q"+getQueryParams();
	}

	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm",${page.currentIndex},${page.sizePerPage},${page.totalPage});
		
		var slocationType=$('#searchForm select[name="locationType"]');
		slocationType.val($(slocationType).prev().val());
		var inventoryStatus=$('#searchForm select[name="inventoryStatus"]');
		inventoryStatus.val($(inventoryStatus).prev().val());
		
		//显示库位类型
		$('#table_hover tr td').each(function(idx,el){
			var type=$(el).attr('name');
			if(type=='locationType'||type=='inventoryStatus'){
				if($(el).text()!=null&&$(el).text()!=''){
					var text=$('#searchForm select[name="'+type+'"] option[value="'+$(el).text()+'"]').text();
					$(el).html(text);
				}
			}
		});
	});
</script>
</body>
</html>