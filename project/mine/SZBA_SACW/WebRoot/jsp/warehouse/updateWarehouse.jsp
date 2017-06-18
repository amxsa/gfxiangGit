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
 	<li><label><span style="color:red;">*</span>库位编号</label><input name="serialNumber" type="text" value="${data.serialNumber }" /></li>
	<li><label><span style="color:red;">*</span>库位名称</label><input name="name" type="text" value="${data.name }" /></li>
	<li><label><span style="color:red;">*</span>所属部门</label>
	<%if(isXTGLY==true){ %>
		<div class="department-box model-select-box">
			<div class="department-text model-select-text"></div>
			<div class="department-dropdown model-select-dropdown" style="display:none;"></div>
			<input class="department-input" type="hidden" name="departmentID" value="${data.departmentId }"></input>
		</div>
	<%} else{ %>
		<span id="span-departmentId"></span>
		<input type="hidden" name="departmentID" value="${data.departmentId }" />
	<%} %>
	</li>
	<li><label>库位类别</label>
		<select name="category">
			<option value="0">枪柜</option><option value="1">防磁柜</option>
		</select>
	</li>
 	<li><label>体积大小</label><input name="volume" type="text" value="${data.volume }" /></li>
 	<li><label>存放数量</label><input name="grossQuantity" type="text" value="${data.grossQuantity }" /></li>
 	<li><label>已放数量</label><input name="existQuantity" type="text" value="${data.existQuantity }" /></li>
 	<li><label>楼号</label><input name="building" type="text" value="${data.building }" /></li>
 	<li><label>楼层</label><input name="floor" type="text" value="${data.floor }" /></li>
	<li><label>房间号</label><input name="room" type="text" value="${data.room }" /></li>
	<li><label>柜号</label><input name="cabinet" type="text" value="${data.cabinet }" /></li>
	<li><label>库位地址</label><input name="address" type="text" value="${data.address }" /></li>
	<li class="w100"><label>库存状态</label>
		<select name="status">
			<option value="0">空闲</option><option value="1">未满</option><option value="2">已满</option>
		</select>
	</li>
 </ul>

 <div class="dbut"><a href="#" onclick="javascript:save_();">保存</a><a href="#" onclick="goback();">返回</a></div>
 </form>
</div>
<script type="text/javascript">
	function golink(){
		param = "serialNumber="+'<%=RequestUtils.getAttribute(params,"serialNumberQ",0)%>' 
		  	+ "&name="+'<%=RequestUtils.getAttribute(params,"nameQ",0)%>'
		  	+ "&departmentID="+'<%=RequestUtils.getAttribute(params,"departmentIDQ",0)%>'
	  	
		return "<%=path%>/warehouse/toWarehouseManage.do?"+param;		  	
	}
	
	function save_(){
		 var data = {
			id: $('input[name="id"]').val(),
			serialNumber: $('input[name="serialNumber"]').val(),
			name: $('input[name="name"]').val(),
			departmentId: $('input[name="departmentID"]').val(),
			category: $('select[name="category"]').val(),
			volume: $('input[name="volume"]').val(),
			grossQuantity: $('input[name="grossQuantity"]').val(),
			existQuantity: $('input[name="existQuantity"]').val(),
			building: $('input[name="building"]').val(),
			floor: $('input[name="floor"]').val(),
			room: $('input[name="room"]').val(),
			cabinet: $('input[name="cabinet"]').val(),
			address: $('input[name="address"]').val(),
			status: $('select[name="status"]').val()
		};
		
		$.ajax({
		  type: 'POST',
		  async: false,
		  url: sysPath+'/warehouse/updateWarehouse.do',
		  data: data,
		  dataType:"json",
		  success: function(data){
			  if(data.result==true){
				  location.href=golink();
			  }
		  }
		});
	}
	
	function goback(){
		location.href=golink();
	}
	
	$(document).ready(function() {
		$('select[name="category"]').val('${data.category}');
		$('select[name="status"]').val('${data.status}');
		<%if(isXTGLY==true){%>
			Common.departments.method.initDropdown();
		<%} else{%>			
			$('#span-departmentId').html(Common.departments.method.getName('${data.departmentId}'));
		<%} %>
	});
</script>
</body>
</html>