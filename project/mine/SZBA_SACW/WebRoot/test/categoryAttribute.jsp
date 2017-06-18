<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>用户管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 处置方式</span></div>
<div class="content">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search">
		<p>	
		<label>财物分类：</label>
		<select name="categoryId">
			<c:forEach var="v" items="${categoryAttribute }">
				<option value="${v.code }">${v.description }</option>
			</c:forEach>
		</select>
		<label>财物状态：</label>
		<select name="status">
			<option value="YDJ">已登记</option>
			<option value="ZCK">暂存库</option>
			<option value="ZXK">中心库</option>
		</select>
		<label>用户角色:</label>
		<select name="role">
			<c:forEach var="v" items="${role }">
				<option value="${v.roleKey }">${v.name }</option>
			</c:forEach>
		</select>
		</p>
		<div class="sbut"><input name="submit" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">处置方式</h1>
  	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<thead>
			<th>处置方式</th>
			<th>流程号</th>
			<th>流程</th>
		</thead>
		<tbody id="tbo">
		</tbody>
	</table>
</div>
<script type="text/javascript">

	$(document).ready(function() {
		$('input[name="submit"]').click(function(){
			var data = {
				"categoryId": $('select[name="categoryId"]').val(),
				"status": $('select[name="status"]').val(),
				"role": $('select[name="role"]').val()
			}
			$.ajax({
			  type: 'GET',
			  async: false,
			  url: '../test/getDisposalAndProcedure.do',
			  dataType:"json",
			  data: data,
			  success: function(data){
				  data = data.data;
				  $('#tbo').html('');
				  for(var i= 0;i<data.length; ++i){
					  tr = "<tr><td>"+data[i].disposal.description+"</td><td>"+data[i].procedure.code+"</td><td>"+data[i].procedure.description+"</td></tr>";
					  $('#tbo').append(tr);
				  }
			  }
			});
		})
		
	});
</script>
</body>
</html>