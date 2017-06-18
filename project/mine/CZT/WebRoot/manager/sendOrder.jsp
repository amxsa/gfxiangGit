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
	pageContext.setAttribute("express", Env.EXPRESS);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		
		function GO() {
			
			document.forms[0].submit();
		}
		
		$(document).ready(
			function(){
				$("#orderDetail").load("<%=path%>/manager/OrderManagerAction_orderDetail.do?id=${param.id}");
			});
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
			<div id="ptk">
				<div id="tabtop-tt" style="width: 150px	">订单配送</div>
			</div>
		</div>
		<div id="table">
			<div id="ptk">
				<div id="tabtop-l"></div>
				<div id="tabtop-z">订单信息</div>
				<div id="tabtop-r1"></div>
			</div>
		</div>
		<div id="main-tab">
			<div id="orderDetail"></div>
		</div>
		<div id="table">
			<div id="ptk">
				<div id="tabtop-l"></div>
				<div id="tabtop-z">配送填写</div>
				<div id="tabtop-r1"></div>
			</div>
		</div>
		<div id="main-tab">
			<form action="<%=path%>/manager/OrderManagerAction_sendOrder.do"
				method="post">
				<input type="hidden" name="orderId" value="${param.id}" />
				<table width="911" align="center" class="table-slyle-hs">
					<tr>
						<td width="150" align="center">快递厂家：</td>
						<td><select name="expressCompany">
								<c:forEach var="express" items="${express }">
									<option value="${express.key}">${express.value}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td width="150" align="center">物流单号：</td>
						<td><input type="text" name="expressId" size="30" />&nbsp;&nbsp;

						</td>
					</tr>
					<tr>
						<td width="150" align="center">签收人：</td>
						<td><input type="text" name="expressName" size="30" />&nbsp;&nbsp;

						</td>
					</tr>
					<tr>
						<td width="150" align="center">配送时间：</td>
						<td><input type="text" name="sendTime" size="30"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							readonly="true" /></td>
					</tr>
					<tr>
						<td width="150" align="center">操作人：</td>
						<td><input type="text" name="operateName" size="30" />&nbsp;&nbsp;

						</td>
					</tr>
					<tr>
						<td align="center">快递备注：</td>
						<td><textarea rows="5" cols="130" name="remark"></textarea>
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
