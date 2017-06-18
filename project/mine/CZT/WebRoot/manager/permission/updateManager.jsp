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
			var account=$("#account").val();
			var password1=$("#password").val();
			if(account==null||account==undefined||account==""){
				alert("账号不可为空！");
				$("#account").focus();
				return false;
			}
			if(password1==null||password1==undefined||password1==""){
				alert("密码不可为空！");
				$("#password").focus();
				return false;
			}
			if(password1.length<6){
				alert("密码长度至少6位");
				$("#password").focus();
				return false;
			} 
			document.forms[0].action="<%=path%>/permission/ManagerAction_updateManager.do";
				$("form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/permission/ManagerAction_showManager.do";
											checkForm();
						} else {
							alert(data[1]);
							return false;
						}
					}
				});
			
		}
		function checkForm(){
			document.forms[0].submit();
		}
		function back(){
			window.location="<%=path%>/permission/ManagerAction_showManager.do";
		}
		
		$(document).ready(function(){
			var roleid="${manager.roleid}";
			var areaCode="${manager.areacode}";
			var state="${manager.state}";
			var departmentId="${manager.departmentId}";
			$("#roleid option[value='"+roleid+"']").attr("selected",true);
			$("#areacode option[value='"+areaCode+"']").attr("selected",true);
			$("#state option[value='"+state+"']").attr("selected",true);
			$("#state option[value='"+departmentId+"']").attr("selected",true);
		});
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">更新账户</div>
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
			<form action="<%=path%>/permission/ManagerAction_updateManager.do" method="post">
			<input type="hidden" name="regTime" value="${manager.regTime }"/>
				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="150">账号：</td>
						<td><input type="text" name="account" id="account" maxlength="10" value="${manager.account }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" name="password" id="password" maxlength="20" value="${manager.password }"/></td>
					</tr>
					<tr>
						<td>名称</td>
						<td><input type="text" name="name" maxlength="20" value="${manager.name }"/></td>
					</tr>
					<tr>
						<td>手机号</td>
						<td><input type="text" name="telephone" maxlength="20" value="${manager.telephone }"/></td>
					</tr>
					<tr>
						<td>详细地址</td>
						<td><input type="text" name="address" maxlength="200" value="${manager.address }"/></td>
					</tr>
					<tr>
						<td>地市</td>
						<td>
							<select name="areacode" id="areacode">
								<c:forEach var="areaCode" items="${requestScope.areaCode }">
									<option value="${areaCode.key }">${areaCode.value[0] }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>角色</td>
						<td>
							<select name="roleid" id="roleid">
								<c:forEach var="role" items="${requestScope.roleList }">
									<option value="${role.id }">${role.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>组织</td>
						<td>
							<select name="departmentId">
								<c:forEach var="depart" items="${requestScope.departList }">
									<option value="${depart.id }">${depart.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>状态</td>
						<td>
							<select name="state" id="state">
									<option value="Y">有效</option>
									<option value="N">无效</option>
							</select>
						</td>
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
