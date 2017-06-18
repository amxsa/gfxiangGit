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
<title>设备流量卡捆绑</title>
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

		document.forms[0].submit();
	}
	function openOrder() {
		window.open ("<%=path%>/manager/OrderManagerAction_checkOrder.do","订单","height=400,width=1000,top=150,left=300,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no"); 
	}
	function openDevice() {
		window.open ("<%=path%>/manager/DeviceManagerAction_showDevice.do?stateQuery=I&fromPart=bindDeviceFluxCard","设备","height=400,width=800,top=200,left=300,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no"); 
	}
	function openFluxCard() {
		window.open ("<%=path%>/manager/FluxCardManagerAction_showFluxCard.do?stateQuery=I&fromPart=bindDeviceFluxCard","流量卡","height=400,width=800,top=200,left=300,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no"); 
	}
</script>

</head>

<body>
	<div id="main">
		<div id="table">
			<div id="ptk">
				<div id="tabtop-tt" style="width: 150px	">设备流量卡捆绑</div>
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
			<form
				action="<%=path%>/manager/BindManagerAction_batchBind.do"
				method="post" >

				<table width="911" align="center" class="table-slyle-hs">
					<tr>
						<td width="150">订单号：</td>
						<td><input type="text" name="orderId" id="orderId" size="46" />&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;<input type="button" onclick="openOrder();" value="选择订单号"/>
						</td>
					</tr>
					<tr>
						<td width="150">设备号：</td>
						<td>
							<textarea rows="5" cols="49" id="deviceId" name="deviceId"></textarea>
							&nbsp;&nbsp;&nbsp;<input type="button" onclick="openDevice();" value="选择设备号"/>
						</td>
					</tr>
					<tr>
						<td width="150">流量卡：</td>
						<td>
							<textarea rows="5" cols="49" id="fluxCardId" name="fluxCardId"></textarea>
							&nbsp;&nbsp;&nbsp;<input type="button" onclick="openFluxCard();" value="选择流量卡"/>
						</td>
					</tr>
					<tr>
						<td width="150">操作人：</td>
						<td>
							<input type="text" name="operateName" id="operateName" size="46" />
						</td>
					</tr>
					<tr>
						<td width="150">备注：</td>
						<td>
							<textarea rows="5" cols="49" id="remark" name="remark"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" value="确定"
							onclick="GO();" /> &nbsp;&nbsp;&nbsp;</td>

					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
