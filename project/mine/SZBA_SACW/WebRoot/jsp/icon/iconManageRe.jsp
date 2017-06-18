<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
    Pager pager = (Pager) request.getAttribute("page");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>桌面图标管理</title>
<link type="text/css" href="<%=basePath%>css/main.css" rel="stylesheet"/>
<script language="javascript" type="text/javascript" src='<%=request.getContextPath()%>/js/jquery-1.9.1.min.js'></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/table_hover.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 桌面图标管理</span></div>
<div class="content">
<div class="toptit">
	    <h1 class="h1tit">图标列表</h1>
	    <div class="but1"><input name="" type="button" value="分配给角色"  onclick="batchAllot()"/></div>
	  </div>
<form id="iconForm" action=""  method="post">
<input type="hidden" name="iconStr" value="${iconStr}"/>
<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
		    <th><input type="checkbox" name="all" value="全选" onclick="selectAll('all')"/>全选</th>
			<th>名称</th>
			<th>描述</th>
			<th>优先级</th>
			<th>图标</th>
			<th>链接地址</th>
			<th>目标窗口</th>
			<th>操作</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
			    <td ><input type="checkbox" id="icon" name="icons" value="${v.id }"/></td>
				<td>${v.name }</td>
				<td>${v.description }</td>
				<td>${v.priority }</td>
				<td>${v.icon }</td>
				<td>${v.url }</td>
				<td>${v.target }</td>
				<td><span class="tdbut">
					<a href="<%=path%>/icon/toEditPage.do?id=${v.id }">修改</a>
					<a href="<%=path%>/icon/toDelete.do?id=${v.id }" onclick="del()">删除</a>
					</span>
				</td>
			</tr>
		</c:forEach> 
	</table>
</form>
	<div class='pageNumber'></div>
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
	      alert("请选择要分配的图标");
	      return flag;
	   }
	   var f=document.getElementById('iconForm');
	   f.action="<%=request.getContextPath()%>/role/allotIconRoleRe.do?roleId=${roleId}";
	   f.submit();
	
}

 function del(){
    confirm("删除图标将无法恢复，是否确认删除？");
 }

$(document).ready(function() {
	//已选图标回显 
		var iconStr=$('#iconForm input[name="iconStr"]').val();
		if(iconStr!=null&&iconStr!=''){
			var iconArr=iconStr.split(';');
			$('#iconForm input[name="icons"]').each(function(idx,el){
				var ri=$(el).val();
				for(var i=0;i<iconArr.length;i++){
					if(ri==iconArr[i]){
						$(el).prop('checked',true);
					}
				}
			});
		}
		
		var message = '<%=message%>'
		if(message != ''){
			if(message=='success'){
				alert('添加成功！');
				 var f=document.getElementById('iconForm');
			     f.action="<%=request.getContextPath()%>/icon/queryForPage.do?id=${roleId }";
			     f.submit();
			} else if(message=='fail'){
				alert('添加失败，请稍后重试！');
			} else if(message=='exist'){
				alert('添加失败：该记录已经存在！');
				var f=document.getElementById('iconForm');
			     f.action="<%=request.getContextPath()%>/icon/queryForPage.do?id=${roleId }";
			     f.submit();
			}
		}
});
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
		// 设置下拉框默认值
		$('select[name="status"]').val('<%=RequestUtils.getAttribute(params,"status",0)%>');
		$('select[name="gender"]').val('<%=RequestUtils.getAttribute(params,"gender",0)%>');
		
	});
	
</script> 
</body>
</html>