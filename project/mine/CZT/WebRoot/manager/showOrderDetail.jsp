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
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">    
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">    
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>订单配送</title>
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
		$.ajaxSetup ({
		    cache: false //关闭AJAX相应的缓存 
		});
		function GO() {
			
			document.forms[0].submit();
		}
		
		$(document).ready(
			function(){
				$("#orderDetail").load("<%=path%>/manager/OrderManagerAction_orderDetail.do?id=${param.id}",{"a":Math.random()});
			});
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
			<div id="ptk">
				<div id="tabtop-tt" style="width: 150px	">订单详情</div>
			</div>
		</div>
		
		<div id="main-tab">
			<div id="orderDetail"></div>
			<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javaScript:history.back();"/>
			<input type="button" value="打印" onclick="javaScript:window.print();"/>
		</div>
		
	</div>
</body>
</html>
