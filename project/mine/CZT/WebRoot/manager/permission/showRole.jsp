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
		function addPermission(id){
			window.location.href="<%=path%>/manager/permission/addPermission.jsp?roleId="+id;
		}
		function checkForm() {
			document.forms[0].submit();
		}
		function deleteRole(id){
			if(confirm('您确认要删除?')){
				document.forms[0].action="<%=path%>/permission/RoleAction_deleteRole.do";
				$("#id").val(id);
				$("#form").ajaxSubmit(function (responseResult) {
					
					if(responseResult!=''){
						
						var data = responseResult.split("|");
						if(data[0]=='true'){
							document.forms[0].action="<%=path%>/permission/RoleAction_showRole.do?";
											checkForm();
						} else {
							alert(data[1]);
						}
					}
				});
			}
		}
		function update(id){
			window.location="<%=path%>/permission/RoleAction_preUpdateRole.do?id="+id;
		}
		function addRole(){
			window.location="<%=path%>/permission/RoleAction_preAddRole.do";
		}
	</script>
	<script type=""></script>
</head>

<body>

	<form action="<%=path%>/permission/RoleAction_showRole.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }"
			id="currentPage" /> <input type="hidden" name="id" value="" id="id" />
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">用户角色查询</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>角色名称：<input type="text" name="nameParam"
						value="${requestScope.nameParam }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>
					<li>描述：<input type="text" name="descriptionParam"
						value="${requestScope.descriptionParam }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>

					<li><input type="button" value="查询" class="search"
						onclick="checkForm();" /></li>
				</div>
				<div><input type="button" value="新增" class="search" onclick="addRole()"/>
				</div>
			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">

							<th height="28">角色名称</th>
							<th>等级</th>
							<th>描述</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25">

								<td class="zw-txt">${data.name }</td>
								<td class="zw-txt">${data.priority }</td>
								<td class="zw-txt" width="35%">${data.description}</td>
								<td class="zw-txt">
									<fmt:formatDate value="${data.createTime }" pattern="yyyy-MM-dd HH-mm-ss" />
								</td>
								<td class="zw-txt">
									<input type="button" value="删除" onclick="deleteRole('${data.id}')" />
									<input type="button" value="修改" onclick="update('${data.id}')" />
									<input type="button" value="设置权限" onclick="addPermission('${data.id}');" />
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
