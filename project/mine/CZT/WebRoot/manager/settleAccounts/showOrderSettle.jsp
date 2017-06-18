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
	request.setAttribute("operation", request.getParameter("operation"));
	request.setAttribute("id", request.getParameter("id"));
	request.setAttribute("settleAccountsFlag", request.getParameter("settleAccountsStateQuery"));
	request.setAttribute("starttimeQuery", request.getParameter("starttimeQuery"));
	request.setAttribute("endtimeQuery", request.getParameter("endtimeQuery"));
	request.setAttribute("orderAreaQuery", request.getParameter("orderAreaQuery"));
	request.setAttribute("configureQuery", request.getParameter("configureQuery"));
	request.setAttribute("orderStateQuery", request.getParameter("orderStateQuery"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">    
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">    
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>订单结算</title>
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
<script language="javascript" type="text/javascript"
	language="javascript">
		
		$(document).ready(function(){
			<%-- $("#orderDetail").load("<%=path%>/manager/SettleAccountsManagerAction_settleOrder.do?id=${param.id}",{"a":Math.random()}); --%>
			$.ajax({
				   type: "POST",
				   url: "<%=path%>/manager/SettleAccountsManagerAction_settleOrder.do?id=${param.id}",
				   data: {},
				   success: function(po){
				  	 $("#id").val(po.orderId);
				     $("#orderId").text(po.orderId);
				     $("#telephone").text(po.telephone);
				     $("#areacode").text(po.areacode);
				     $("#count").text(po.count+"(套)");
				     $("#countHidden").val(po.count);
				     $("#account").val(po.account);
				     $("#orderState").text(po.orderState);
				     $("#originalTotalPrice").text(po.originalTotalPrice+"(元)");
				     $("#originalUnitPrice").val(po.originalUnitPrice);
				   },
				   dataType:"json"
				});
		});
		
	
</script>
	<script language="javascript" type="text/javascript">
		function cancle(){
			window.close();
		}
		function checkForm(){
			document.form[0].submit();
		}
		function doSubmit(){
			document.forms[0].action="<%=path%>/manager/SettleAccountsManagerAction_showReviewSettleAccounts.do";
			document.forms[0].submit();
		}
		function launch(){
			if(confirm('您确认要进行结算?')){
				
				var projectName=$("#projectName").val();
				if (projectName==null||projectName==""||projectName==undefined) {
					alert("请输入合法的项目名称");
					return false;
				}
				var unitPrice=$("#unitPrice").val();
				var re =new RegExp("^[0-9]{1,5}$");
				if (!re.exec(unitPrice)) {
					alert("请输入合法的单价");
					return false;
				}
				var originalUnitPrice=parseInt($("#originalUnitPrice").val());
				
				if (originalUnitPrice<parseInt(unitPrice)) {
					alert("结算单价不可大于产品单价("+originalUnitPrice+"元)");
					return false;
				}
				var originator=$("#originator").val();
				if (originator==null||originator==""||originator==undefined) {
					alert("请输入合法的发起人名称");
					return false;
				}
				document.forms[0].action="<%=path%>/manager/SettleAccountsManagerAction_launchSettle.do";
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							parent.leftFrame.total();
							doSubmit();
						}else{
							alert(data[1]);
						}
					}
				});
		}
		
}
</script>
</head>

<body>
	<div id="main">
		<div id="table">
			<div id="ptk">
				<div id="tabtop-tt" style="width: 150px	">订单结算详情</div>
			</div>
		</div>
		<div id="main-tab">
			<form name="form" id="form">
				<input type="hidden" name="operation" value="${operation }"/>
				<input type="hidden" name="settleAccountsFlag" value="${settleAccountsFlag }"/>
				<input type="hidden" name="starttimeQuery" value="${starttimeQuery }"/>
				<input type="hidden" name="endtimeQuery" value="${endtimeQuery }"/>
				<input type="hidden" name="orderAreaQuery" value="${orderAreaQuery }"/>
				<input type="hidden" name="configureQuery" value="${configureQuery }"/>
				<input type="hidden" name="orderStateQuery" value="${orderStateQuery }"/>
				<input type="hidden" name="id" id="id">
				<input type="hidden" name="countHidden" id="countHidden">
				<input type="hidden" name="account" id="account">
				<input type="hidden" name="originalUnitPrice" id="originalUnitPrice">
		<table width="911" class="table-slyle-hs">
			<tr height="35">
				<td width="15%" align="center">订单编号：</td>
				<td width="35%" id="orderId"></td>
				<td width="15%" align="center">订单联系电话：</td>
				<td width="35%" id="telephone"></td>
			</tr>
			<tr height="35">
				<td width="15%" align="center">订单渠道：</td>
				<td width="35%" id="areacode"></td>
				<td width="15%" align="center">订单设备数量：</td>
				<td width="35%" id="count"></td>
			</tr>
			<tr height="35">
				<td width="15%" align="center">订单状态：</td>
				<td width="35%" id="orderState"></td>
				<td align="center">订单总价：</td>
				<td id="originalTotalPrice"></td>
			</tr>
		</table>
			<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;">输入订单项目结算依据：</span>
		
			
			<table width="911" class="table-slyle-hs">
				<tr height="35">
					<td width="15%" align="center">项目名称：</td>
					<td  width="35%"><input name="projectName" id="projectName" type="text"></td>
					<td width="15%" align="center">结算单价(rmb)：</td>
					<td width="35%"><input name="unitPrice" id="unitPrice" type="text"></td>
				</tr>
				<tr height="35">
					<td width="15%" align="center">项目合同编号：</td>
					<td width="35%"><input name="contractNo" id="contractNo" type="text"></td>
					<td width="15%" align="center">（或）发票编号：</td>
					<td width="35%"><input name="invoiceNo" id="invoiceNo" type="text"></td>
				</tr>
				<tr height="35">
					<td width="15%" align="center">发起人：</td>
					<td width="35%"><input name="originator" id="originator" type="text"></td>
					<td width="15%" align="center" colspan="2"></td>
				</tr>
				<tr height="35">
					<td colspan="4" align="center">
					<input type="button" value="取消结算" onclick="cancle()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="确定结算" onclick="launch()">
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>
</body>
</html>
