<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>角色列表</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 角色列表</span></div>
<div class="content">
<div class="toptit">
	    <h1 class="h1tit">角色列表</h1>
	    <div class="but1"><input name="" type="button" value="图标分配给对应角色"  onclick="batchAllot()"/></div>
</div>
<form id="iconForm" action=""  method="post">
	<!-- 上一页面 提交过来的iconid -->
	<input type="hidden" name="iconids" value="${iconIds}" />
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
		    <th><input type="checkbox" name="all" value="全选" onclick="selectAll('all')"/>全选</th>
			<th>角色名称</th>
			<th>角色描述</th>
			<th>角色优先级</th>
			<th>角色创建时间</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
			    <td ><input type="checkbox" id="role" name="roles" value="${v.id }"/></td>
				<td>${v.name }</td>
				<td>${v.description }</td>
				<td>${v.priority }</td>
				<td>${v.createTime }</td>
			</tr>
		</c:forEach>
	</table>
	<div class='page'></div>
</form>
</div>
<script type="text/javascript">

   function selectAll(myName) {
	  var selectOne=document.getElementsByTagName("input");
	  for(var i=0;i<selectOne.length;i++){
		if(selectOne[i].type=="checkbox" && selectOne[i].name != myName){
			selectOne[i].checked = document.getElementsByName(myName)[0].checked;
		 }
	  }
   }


    function batchAllot(){
       var selectOne=document.getElementsByTagName("input");
	   var flag = false;
	   for(var i=0;i<selectOne.length;i++){
		   if(selectOne[i].type=="checkbox" && selectOne[i].name != "all"){
			   flag = selectOne[i].checked;
			   if ( flag ) {
			 	  break;
			 }
		 }
	 }
	  if(flag == false){
	      alert("请选择要分配给的角色");
	      return flag;
	   }
	      var f=document.getElementById('iconForm');
	      f.action="<%=request.getContextPath()%>/role/allotIconRole.do?iconIds=${iconIds}";
	      f.submit();
}

$(document).ready(function() {
		var message = '<%=message%>'
		if(message != ''){
			if(message=='success'){
				alert('添加成功！');
			} else if(message=='fail'){
				alert('添加失败，请稍后重试！');
			} else if(message=='exist'){
				alert('添加失败：该记录已经存在！');
			}
		}
});
	/* $(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
		
	}); */
</script>
</body>
</html>