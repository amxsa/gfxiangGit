<%@page import="cn.cellcom.czt.common.Env"%>
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
<title>订单新增</title>
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="../css/myTable.css" type="text/css" />

<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="../datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/common.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		
		function GO() {
			var oldPassword = $("#oldPassword").val();
			if(oldPassword==''){
				alert('旧密码不能为空');
				return false;
			}
			var newPassword = $("#newPassword").val();
			if(newPassword==''){
				alert('新密码不能为空');
				return false;
			}
			var confirmPassword = $("#confirmPassword").val();
			if(confirmPassword==''){
				alert('确认密码不能为空');
				return false;
			}
			
			if(newPassword!=confirmPassword){
				alert('新旧密码不一致');
				return false;
			}
			document.forms[0].submit();
		}
		
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">修改密码</div>
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
			<form action="<%=path%>/manager/LoginManagerAction_updatePassword.do" method="post">
				
				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="150">旧的密码：</td>
						<td>
							<input type="password" name="oldPassword" id="oldPassword" maxlength="15" size="15" />
						</td>
					</tr>
					<tr>
						<td>新的密码：</td>
						<td>
							<input  type="password" name="newPassword" id="newPassword" maxlength="15" size="15" />
						</td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td>
							<input  type="password" name="confirmPassword" id="confirmPassword" maxlength="15" size="15" />
						</td>
					</tr>
					
					<tr>
						<td colspan="2"><input type="button" value="确定" onclick="GO();"/>
							&nbsp;&nbsp;&nbsp;
							
						</td>
						
					</tr>
				</table>
			</form>
		</div>
	</div>
	<c:if test="${not empty result}">
		<script language="javascript">
			 alert('${result}');
		</script>
	</c:if>
</body>
</html>
