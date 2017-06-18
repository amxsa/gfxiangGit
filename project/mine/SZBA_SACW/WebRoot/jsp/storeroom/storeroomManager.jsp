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
<title>保管室管理</title>
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
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 保管室管理</span></div>
<div class="content">
<form id="searchForm" action="<%=path%>/storeroom/queryForPage.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search"><div class="sp">
		<p>
			<label>保管室编号</label><input name="storeroomNum" type="text" value="<%=RequestUtils.getAttribute(params,"storeroomNum",0)%>"/>
			<label>所属仓库</label>
			<select id="storehouseId" name="storehouseId" style="width:120px;">
   				<option value="">请选择</option>
				<c:forEach var="r" items="${storeHouseList }">
					<option value="${r.id }" <c:if test="${storehouseId==r.id}">selected="selected"</c:if>>${r.storehouseName }</option>
				</c:forEach>
			</select>
		</p></div>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">保管室列表</h1>
	    <div class="but1"><input name="" type="button" value="添加保管室"  onclick="toAddPage();"/></div>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>保管室编号</th>
			<th>保管室名称</th>
			<th>保管室面积</th>
			<th>保管室类型</th>
			<th>所属地址</th>
			<th>货架数量</th>
			<th>所属仓库</th>    
			<th>操作</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
				<td>${v.storeroomNum }</td>
				<td>${v.storeroomName }</td>
				<td>${v.storeroomArea }</td>
				<td name="storeroomType">${v.storeroomType }</td>
				<td>${v.storeroomAddr }</td>
				<td>${v.roomNum }</td>
				<td>${v.storehouseName }</td>
				<td><span class="tdbut">
					<input type="hidden" value="${v.storeroomNum }"/>
					<a href="#" onclick="toEditPage(this);">修改</a>
					<a href="#" onclick="toDetailPage(this);">详情</a>
					</span>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class='page'></div>
</form>
<div id="selectDataDiv" style="display:none;">
	<select name="storeroomType">
		<jsp:include page="/jsp/plugins/storeroomType_options.jsp" />
	</select>
</div>
</div>
<script type="text/javascript">
	function getQueryParams(){
		var storeroomNum = $('input[name="storeroomNum"]').val();
		var storehouseId = $('select[name="storehouseId"]').val();
		var currentIndex = "${page.currentIndex}";
		var sizePerPage = "${page.sizePerPage}";
		
		/* 获取参数*/
		var params = "&storeroomNumQ="+storeroomNum+"&storehouseIdQ="+storehouseId
		+"&currentIndex="+currentIndex+"&sizePerPage="+sizePerPage;
		return params;
	}
	
	function toEditPage(obj){
		var storeroomNum=$(obj).prev().val();
		window.location.href="<%=path%>/storeroom/toEditPage.do?storeroomNum="+storeroomNum+getQueryParams();
	}
	
	function toDetailPage(obj){
		var storeroomNum=$(obj).prev().prev().val();
		window.location.href="<%=path%>/storeroom/toDetailPage.do?storeroomNum="+storeroomNum+getQueryParams();
	}
	
	function toAddPage(){
		window.location.href="<%=path%>/storeroom/toAddPage.do?Q=q"+getQueryParams();
	}

	$(document).ready(function() {
		$('#table_hover tr td').each(function(idx,el){
			if($(el).attr('name')=='storeroomType'){
				var text=$(el).text();
				if(text!=''){
					text=$('#selectDataDiv select[name="storeroomType"] option[value="'+text+'"]').text();
					$(el).html(text);
				}
			}
		})
		// 生成页码
		Common.method.pages.genPageNumber("searchForm",${page.currentIndex},${page.sizePerPage},${page.totalPage});
	});
</script>
</body>
</html>