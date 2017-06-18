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
<title>TDCode管理</title>
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="../css/myTable.css" type="text/css" />

<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.form.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/common.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		
		function checkForm() {
			var tdCode = $("#tdCode").val();
			if($.trim(tdCode)==''){
				alert("设备号不能为空");
				return false;
			}
			var mobileNum = $("#mobileNum").val();
			if($.trim(mobileNum)==''){
				alert("手机号不能为空");
				return false;
			}
			document.forms[0].submit();
		}
		
	</script>
</head>

<body>

	<form action="<%=path%>/manager/TDCodeManagerAction_registTDCode.do"
		method="post" id="form">
		<input type="hidden" name="fromPart" value="web"/>
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">TDCode激活</div>
				</div>
			</div>
			<div id="main-tab">
				<div id="info-4">
					<li>设&nbsp;&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;号&nbsp;：<input
						type="text" name="tdCode" value="${requestScope.tdCode }" id="tdCode" style="width: 200px;" />&nbsp;(至少后八位)&nbsp;&nbsp;&nbsp;
						
					</li>
					<li>手&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;&nbsp;号&nbsp;：<input
						type="text" name="mobileNum" value="${requestScope.mobileNum }" id="mobileNum"/>&nbsp;&nbsp;&nbsp;&nbsp;
						
					</li>
					<li>
						<input type="button" value="激活" class="search" onclick="checkForm();" />
					</li>
					
				</div>
			</div>
		</div>
	</form>
	
</body>
</html>
