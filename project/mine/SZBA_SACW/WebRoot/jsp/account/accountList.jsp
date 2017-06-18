<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
   String message = (String) request.getAttribute("message");
   Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户管理</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet"/>
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 用户管理</span></div>
<div class="content">
<form id="searchForm" action="<%=path%>/account/queryByList.do" method="post">
    <input type="hidden" name="id" value="${roleId }" />
    <input type="hidden" name="accountStr" value="${accountStr }" />
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search">
	<div class="sp">
		<p>
			<label>账号</label><input name="account" type="text" value="<%=RequestUtils.getAttribute(params,"account",0)%>"/>
			<label>工号</label><input name="workno" type="text" value="<%=RequestUtils.getAttribute(params,"workno",0)%>"/>
			<label>状态</label><select name="status">
				<option value="">全选</option><option value="Y">激活</option></select>
		</p>
		<p>
			<label>姓名</label><input name="name" type="text" value="<%=RequestUtils.getAttribute(params,"name",0)%>"/>
			<label>手机号码</label><input name="mobile" type="text" value="<%=RequestUtils.getAttribute(params,"mobile",0)%>"/>
			<label>性别</label><select name="gender">
				<option value="">全选</option><option value="M">男</option><option value="F">女</option></select>
		</p>
		<div class="selt">
			<label>所属部门</label>
			<div class="department-box model-select-box">
				<div class="department-text model-select-text"></div>
				<div class="department-dropdown model-select-dropdown" style="display:none;"></div>
				<input class="department-input" type="hidden" name="departmentID" value="<%=RequestUtils.getAttribute(params,"departmentID",0)%>"></input>
			</div>
		</div>
		</div>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>

	<div class="toptit">
	    <h1 class="h1tit">用户列表</h1>
	    <div class="but1"><input name="" type="button" value="给角色添加用户"  onclick="batchAllot();"/></div>
	  </div>
<!-- <form id="iconForm" action=""  method="post"> -->
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
		    <th><input type="checkbox" name="all" value="全选" onclick="selectAll('all')"/>全选</th>
			<th>账号</th>
			<th>工号</th>
			<th>部门</th>
			<th>姓名</th>
			<th>性别</th>
			<th>手机号码</th>
			<th>证件类型</th>    
			<th>证件号码</th>
			<th>状态</th>
		</tr>
		<c:forEach var="v" items="${data}">
			<tr>
			     <td ><input type="checkbox" id="account" name="accounts" value="${v.account }"/></td>
				<td>${v.account }</td>
				<td>${v.workno }</td>
				<td class="departmentIDs">${v.departmentID}</td>
				<td>${v.name }</td>
				<td>
					<c:if test="${v.gender=='M' }">男</c:if>
					<c:if test="${v.gender=='F' }">女</c:if>
				</td>
				<td>${v.mobile }</td>
				<td><c:if test="${v.idType=='IDCard' }">身份证</c:if></td>
				<td>${v.idNum }</td>
				<td><c:if test="${v.status=='Y' }">激活</c:if>
					<c:if test="${v.status=='N' }">禁用</c:if></td>
			</tr>
		</c:forEach>
	</table>
<!-- </form> -->
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
	      alert("请选择要分配的用户");
	      return flag;
	   }
	   var f=document.getElementById('searchForm');
	   f.action="<%=request.getContextPath()%>/role/allotRoleAccount.do?roleId=${roleId }";
	   f.submit();
	
    }
    
	function getQueryParams(){
		var account = $('input[name="account"]').val();
		var workno = $('input[name="workno"]').val();
		var status = $('select[name="status"]').val();
		var name = $('input[name="name"]').val();
		var mobile = $('input[name="mobile"]').val();
		var gender = $('select[name="gender"]').val();
		var departmentID = $('input[name="departmentID"]').val();
		
		var params = "&accountQ="+account+"&worknoQ="+workno+"&statusQ="+status+"&nameQ="+name+"&mobileQ="+mobile+"&genderQ="+gender+"&departmentIDQ="+departmentID;
		return params;
	}

     $(document).ready(function() {
    	//已选用户回显 
   		var accountStr=$('#searchForm input[name="accountStr"]').val();
   		if(accountStr!=null&&accountStr!=''){
   			var accountArr=accountStr.split(';');
   			$('#searchForm input[name="accounts"]').each(function(idx,el){
   				var ri=$(el).val();
   				for(var i=0;i<accountArr.length;i++){
   					if(ri==accountArr[i]){
   						$(el).prop('checked',true);
   					}
   				}
   			});
   		}
    		
		var message = '<%=message%>'
		if(message != ''){
			if(message=='success'){
				alert('添加成功！');
				var f=document.getElementById('searchForm');
				f.action="<%=request.getContextPath()%>/account/queryByList.do?id=${roleId }";
				f.submit();
			} else if(message=='fail'){
				alert('添加失败，请稍后重试！');
			} else if(message=='exist'){
				alert('添加失败：该记录已经存在！');
				var f=document.getElementById('searchForm');
				f.action="<%=request.getContextPath()%>/account/queryByList.do?id=${roleId }";
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
		
		Common.departments.setting.checkall=true;
		Common.departments.method.initDropdown();
		
		$('.departmentIDs').each(function(){
			$(this).html(Common.departments.method.getName($(this).html()));
		});
	});
</script>
</body>
</html>