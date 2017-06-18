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
boolean isXTGLY = TRoleUtils.contain(roles, RoleKey.XTGLY.toString());
String departmentId = ((Map)session.getAttribute("loginForm")).get("DEPARTMENT_ID").toString();
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>角色管理</title>
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/zTree/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 库位管理</span></div>
<div class="content">
<form id="checkForm" action="<%=path %>/warehouse/update]Warehouse.do" method="post">
 <div class="toptit"><h1 class="h1tit">库位信息</h1></div>
 <ul class="list02a w50">
 	<input name="id" type="hidden" value="${data.id }" />
 	<li><label>库位编号</label>${data.serialNumber }</li>
	<li><label>库位名称</label>${data.name }</li>
	<li><label>所属部门</label>
		<span id="span-departmentId"></span>
	</li>
	<li><label>库位类别</label>
		<span id="span-category"></span>
	</li>
 	<li><label>体积大小</label>${data.volume }</li>
 	<li><label>存放数量</label>${data.grossQuantity }</li>
 	<li><label>已放数量</label>${data.existQuantity }</li>
 	<li><label>楼号</label>${data.building }</li>
 	<li><label>楼层</label>${data.floor }</li>
	<li><label>房间号</label>${data.room }</li>
	<li><label>柜号</label>${data.cabinet }</li>
	<li><label>库位地址</label>${data.address }</li>
	<li class="w100"><label>库存状态</label>
		<span id="span-status"></span>
	</li>
 </ul>

 <div class="dbut"><a href="#" onclick="goback();">返回</a></div>
 </form>
</div>
<script type="text/javascript">
	function golink(){
		param = "serialNumber="+'<%=RequestUtils.getAttribute(params,"serialNumberQ",0)%>' 
		  	+ "&name="+'<%=RequestUtils.getAttribute(params,"nameQ",0)%>'
		  	+ "&departmentID="+'<%=RequestUtils.getAttribute(params,"departmentIDQ",0)%>'
	  	
		return "<%=path%>/warehouse/toWarehouseManage.do?"+param;		  	
	}
	
	function goback(){
		//location.href=golink();
		history.back();
	}
	
	$(document).ready(function() {
		$('#span-departmentId').html(Common.departments.method.getName('${data.departmentId}'));
		var category = '';
		switch('${data.category }'){
		case '0': category = '枪柜'; break;
		case '1': category = '防磁柜'; break;
		default: category = '';
		}
		$('#span-category').html(category);
		var status = '';
		switch('${data.status}'){
		case '0': status = '空闲'; break;
		case '1': status = '未满'; break;
		case '2': status = '已满'; break;
		default: status = '';
		}
		$('#span-status').html(status);
		
	});
</script>
</body>
</html>