<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils,cn.cellcom.szba.common.utils.TRoleUtils,cn.cellcom.szba.enums.RoleKey,cn.cellcom.szba.domain.TRole" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
List<TRole> roles = (List<TRole>) request.getAttribute("roles");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>角色管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 库位管理</span></div>
<div class="content">
<form id="searchForm" action="<%=path%>/warehouse/toWarehouseManage.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search">
		<p>
			<label>库位编号</label><input name="serialNumber" type="text" value="<%=RequestUtils.getAttribute(params,"serialNumber",0)%>"/>
			<label>库位名称</label><input name="name" type="text" value="<%=RequestUtils.getAttribute(params,"name",0)%>"/>
			<%if(TRoleUtils.contain(roles, RoleKey.XTGLY.toString())){ %>
			<label>所属部门</label>
			<div class="department-box model-select-box">
				<div class="department-text model-select-text"></div>
				<div class="department-dropdown model-select-dropdown" style="display:none;"></div>
				<input class="department-input" type="hidden" name="departmentID" value="<%=RequestUtils.getAttribute(params,"departmentID",0)%>"></input>
			</div>
			<%} %>
		</p>
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
			<th>库位地址</th>
			<th>所属仓库</th>
			<th>操作</th>
		</tr>
		<c:forEach var="v" items="${data }">
			<tr>
				<td>${v.serialNumber}</td>
				<td>${v.name }</td>
				<td>${v.address }</td>
				<td class="departmentIDs">${v.departmentId}</td>
				<td><span class="tdbut">
					<a href="#" onclick="toUpdate('${v.id}');">修改</a>
					<a href="#" onclick="toDelete('${v.id}','${v.name}');">删除</a>
					<a href="#" onclick="toShow('${v.id}');">查看</a>
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
		var serialNumber = $('input[name="serialNumber"]').val();
		var name = $('input[name="name"]').val();
		var departmentID = $('input[name="departmentID"]').val();
		
		var params = "&serialNumberQ="+serialNumber+"&nameQ="+name+"&departmentIDQ="+departmentID;
		return params;
	}
	
	function toAddPage(){
		window.location.href="<%=path%>/warehouse/toAddWarehouse.do?Q=q"+getQueryParams();
	}
	
	function toUpdate(id){
		window.location.href="<%=path%>/warehouse/toUpdateWarehouse.do?id="+id+getQueryParams();
	}
	
	function toDelete(id, name){
		var result = confirm("确认删除库位 -- " + name + " 吗？");
		if(result==true){
			data = {
				id: id
			};
			
			$.ajax({
			  type: 'POST',
			  async: false,
			  url: sysPath+'/warehouse/deleteWarehouse.do',
			  data: data,
			  dataType:"json",
			  success: function(data){
				  if(data.result==true){
					  alert("删除成功。");
					  location.reload();
				  }
			  }
			});
		}
	}
	
	function toShow(id){
		window.location.href="<%=path%>/warehouse/toShowWarehouse.do?id="+id+getQueryParams();
	}
	
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
		
		Common.departments.setting.checkall=true;
		Common.departments.setting.showTop=true;
		Common.departments.method.initDropdown();
		
		$('.departmentIDs').each(function(){
			$(this).html(Common.departments.method.getName($(this).html()));
		});
	});
</script>
</body>
</html>