<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<title>新增TDCode</title>
<link rel="stylesheet" href="<%=path %>/themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="<%=path %>/css/myTable.css" type="text/css" />

<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="<%=path %>/datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/common.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=path %>/js/jquery.form.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		function GO() {
			var name=$("#name").val();
			var priority=$("#priority").val();
			if(name==null||name==undefined||name==""){
				alert("角色名不可为空！");
				$("#name").focus();
				return false;
			}
			if(priority==null||priority==undefined||priority==""){
				alert("等级不可为空！");
				$("#priority").focus();
				return false;
			}
			document.forms[0].action="<%=path%>/permission/RoleAction_addRole.do";
				$("form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/permission/RoleAction_showRole.do";
											checkForm();
						} else {
							alert(data[1]);
							return false;
						}
					}
				});
		}
		function back(){
			window.location="<%=path%>/permission/RoleAction_showRole.do";
		}
		function checkForm(){
			document.forms[0].submit();
		}
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">新增角色</div>
				</div>
			</div>
		<div id="table">
			<div id="ptk">
				<div id="tabtop-l"></div>
				<div id="tabtop-z">输入信息</div>
				<div id="tabtop-r1"></div>
			</div>
		</div>
		<div id="main-tab">
			<form action="<%=path%>/permission/RoleAction_addRole.do" method="post">

				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="150">角色名称：</td>
						<td><input type="text" name="name" id="name" maxlength="50"/></td>
					</tr>
					<tr>
						<td>等级：</td>
						<td><input type="text" name="priority" id="priority" maxlength="4"/></td>
					</tr>
					<tr>
						<td>描述</td>
						<td><input type="text" name="description" maxlength="100"/></td>
					</tr>
					
					<tr>
						<td colspan="2"><input type="button" value="确定" onclick="GO();"/>
							&nbsp;&nbsp;&nbsp;
							<input name="" type="button" value="返回" onclick="javascript:back()" />
						</td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
