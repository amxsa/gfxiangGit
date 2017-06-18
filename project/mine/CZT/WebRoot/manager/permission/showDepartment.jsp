<%@page import="cn.cellcom.czt.common.Env"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息查询</title>
<link rel="stylesheet" href="<%=path %>/themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="<%=path %>/css/myTable.css" type="text/css" />

<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="<%=path %>/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/jquery.form.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/common.js"></script>
<script language="javascript" type="text/javascript">
		
		function checkForm() {
			document.forms[0].submit();
		}
		function deleteDepart(id){
			if(confirm('您确认要删除?')){
				document.forms[0].action="<%=path%>/permission/DepartmentAction_deleteDepartment.do";
				$("#id").val(id);
				$("#form").ajaxSubmit(function (responseResult) {
					
					if(responseResult!=''){
						
						var data = responseResult.split("|");
						if(data[0]=='true'){
							document.forms[0].action="<%=path%>/permission/DepartmentAction_showDepartment.do?";
											checkForm();
						} else {
							alert(data[1]);
						}
					}
				});
			}
		}
		function updateDepart(id){
			window.location="<%=path%>/permission/DepartmentAction_preUpdateDepartment.do?id="+id;
		}
		function addDepart(){
			window.location="<%=path%>/permission/DepartmentAction_preAddDepartment.do";
		}
	</script>
	<script type=""></script>
</head>

<body>

	<form action="<%=path%>/permission/DepartmentAction_showDepartment.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }"
			id="currentPage" /> <input type="hidden" name="id" value="" id="id" />
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">组织查询</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>组织名称：<input type="text" name="nameParam"
						value="${requestScope.nameParam }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>
					<li><input type="button" value="查询" class="search"
						onclick="checkForm();" /></li>
				</div>
				<div><input type="button" value="新增" class="search" onclick="addDepart()"/>
				</div>
				
			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">

							<th height="28">组织名称</th>
							<th>联系人</th>
							<th>手机号</th>
							<th width="30%">邮箱</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25">

								<td class="zw-txt">${data.name }</td>
								<td class="zw-txt">${data.linkman }</td>
								<td class="zw-txt">${data.telephone}</td>
								<td class="zw-txt">${data.email}</td>
								<td class="zw-txt">
									<input type="button" value="删除" onclick="deleteDepart('${data.id}')" />
									<input type="button" value="修改" onclick="updateDepart('${data.id}')" />
								</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="info-pz">${requestScope.pageResult.linkTo }</div>
			</div>
		</div>
	</form>

</body>
</html>
