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
		$.ajaxSetup ({
		    cache: false //关闭AJAX相应的缓存 
		});
		function GO() {
			
			document.forms[0].submit();
		}
		
		$(document).ready(
			function(){
				<%-- $("#orderDetail").load("<%=path%>/manager/SettleAccountsManagerAction_settleOrderVerify.do?id=${param.id}",{"a":Math.random()}); --%>
				
				$.ajax({
				   type: "POST",
				   url: "<%=path%>/manager/SettleAccountsManagerAction_settleOrderVerify.do?id=${param.id}",
				   data: {},
				   success: function(po){
				  	 $("#id").val(po.orderId);
				     $("#orderId").text(po.orderId);
				     $("#telephone").text(po.telephone);
				     $("#areacode").text(po.areacode);
				     $("#count").text(po.count+"(套)");
				     $("#orderState").text(po.orderState);
				     $("#originalTotalPrice").text(po.originalTotalPrice+"(元)");
				     $("#originator").text(po.originator);
				     $("#submitTime").text(po.timeStr);
				     $("#projectName").val(po.projectName);
				     $("#unitPrice").val(po.unitPrice);
				     $("#contractNo").val(po.contractNo);
				     $("#invoiceNo").val(po.invoiceNo);
				   },
				   dataType:"json"
				});
			});
	</script>
	<script language="javascript" type="text/javascript">
		
		function checkForm(){
			document.form[0].submit();
		}
		
		function cancle(id){// 审核不通过
			var reason=$("#reason").val();
			if (reason==""||reason==undefined||reason==null) {
				alert("请填写审核不通过原因");
				return false;
			}
			$("#remark").val(reason);
			$("#style").val("N");
			document.forms[0].action="<%=path%>/manager/SettleAccountsManagerAction_onVerify.do";
			$("#form").ajaxSubmit(function (responseResult) {
				if(responseResult!=''){
					var data = responseResult.split("|");
					if(data[0]=='true'){
						alert(data[1]);
						parent.leftFrame.total();
						document.forms[0].action="<%=path%>/manager/SettleAccountsManagerAction_showReviewSettleAccounts.do";
						document.forms[0].submit();
					}else{
						alert(data[1]);
					}
				}
			});
		}
		function getVerify(){
			$("#style").val("Y");
			document.forms[0].action="<%=path%>/manager/SettleAccountsManagerAction_onVerify.do";
			$("#form").ajaxSubmit(function (responseResult) {
				if(responseResult!=''){
					var data = responseResult.split("|");
					if(data[0]=='true'){
						alert(data[1]);
						parent.leftFrame.total();
						document.forms[0].action="<%=path%>/manager/SettleAccountsManagerAction_showReviewSettleAccounts.do";
						document.forms[0].submit();
					}else{
						alert(data[1]);
					}
				}
			});
		}
		
	
</script>
</head>

<body>
	<div id="main">
		<div id="table">
			<div id="ptk">
				<div id="tabtop-tt" style="width: 150px	">订单审核</div>
			</div>
		</div>
		<form name="form" id="form">
			<input type="hidden" name="operation" value="${operation }"/>
			<input type="hidden" name="settleAccountsFlag" value="${settleAccountsFlag }"/>
			<input type="hidden" name="starttimeQuery" value="${starttimeQuery }"/>
			<input type="hidden" name="endtimeQuery" value="${endtimeQuery }"/>
			<input type="hidden" name="orderAreaQuery" value="${orderAreaQuery }"/>
			<input type="hidden" name="configureQuery" value="${configureQuery }"/>
			<input type="hidden" name="orderStateQuery" value="${orderStateQuery }"/>
		
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="remark" name="remark" />
			<input type="hidden" id="name" name="name" />
			<input type="hidden" name="operation" value="verify"/>
			<input type="hidden" id="style" name="style" />
			<div id="main-tab">
				<div id="orderDetail"></div>
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
			<hr>
			<tr height="35">
				<td width="15%" align="center"><font style="font-weight: bold;">结算发起人：</font></td>
				<td width="35%" id="originator"></td>
				<td align="center"><font style="font-weight: bold;">结算发起时间：</font></td >
				<td id="submitTime"></td>
			</tr>
		</table>
		<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;">订单项目结算依据：</span>
		
			<input type="hidden" name="id" id="id">
			<table width="911" class="table-slyle-hs">
				<tr height="35">
					<td width="15%" align="center">项目名称：</td>
					<td  width="35%"><input name="projectName" id="projectName" type="text"  readonly="readonly"></td>
					<td width="15%" align="center">结算单价(rmb)：</td>
					<td width="35%"><input name="unitPrice" id="unitPrice" type="text"  readonly="readonly"></td>
				</tr>
				<tr height="35">
					<td width="15%" align="center">项目合同编号：</td>
					<td width="35%"><input name="contractNo" type="text" id="contractNo"  readonly="readonly"></td>
					<td width="15%" align="center">（或）发票编号：</td>
					<td width="35%"><input name="invoiceNo" type="text" id="invoiceNo"  readonly="readonly"></td>
				</tr>
				<tr height="35">
					<td width="15%" align="center">审核人：</td>
					<td width="35%"><input name="verifyName" id="verifyName" type="text"><font color="red">*必填</font></td>
					<td width="15%" align="center"></td>
					<td width="35%"></td>
				</tr>
				<tr height="35">
					<td width="15%" align="center">审核备注：</td>
					<td colspan="3" align="left">
						<div id="div1">
							<textarea rows="2" cols="100" id="reason" name="reason"></textarea><br>
						</div>
					</td>
				</tr>
				<tr height="35">
					<td colspan="4" align="center">
					<input type="button" value="审核不通过" onclick="cancle()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="审核通过" onclick="getVerify()">
					</td>
				</tr>
			</table>
			</div>
		</form>
	</div>
</body>
</html>
