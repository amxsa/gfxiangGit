<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setAttribute("decorator", "none");
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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
		function cancle(){
			window.close();
		}
</script>
		<form name="form" id="form">
		<table width="911" class="table-slyle-hs">
			<tr height="35">
				<td width="15%" align="center">订单编号：</td>
				<td  width="35%">${po.orderId }</td>
				<td width="15%" align="center">订单联系电话：</td>
				<td width="35%">${po.telephone }</td>
			</tr>
			<tr height="35">
				<td width="15%" align="center">订单渠道：</td>
				<td width="35%">${po.areacode }</td>
				<td width="15%" align="center">订单设备数量：</td>
				<td width="35%">${po.count }(套)</td>
			</tr>
			<tr height="35">
				<td width="15%" align="center">订单状态：</td>
				<td width="35%">${po.orderState }</td>
				<td align="center">订单总价：</td>
				<td>${po.originalTotalPrice }元</td>
			</tr>
		</table>
		<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;">输入订单项目结算依据：</span>
		
			<input type="hidden" name="id" id="id">
			<table width="911" class="table-slyle-hs">
				<tr height="35">
					<td width="15%" align="center">项目名称：</td>
					<td  width="35%">${po.projectName }</td>
					<td width="15%" align="center">结算单价(rmb)：</td>
					<td width="35%">${po.unitPrice }</td>
				</tr>
				<tr height="35">
					<td width="15%" align="center">项目合同编号：</td>
					<td width="35%">${po.contractNo }</td>
					<td width="15%" align="center">（或）发票编号：</td>
					<td width="35%">${po.invoiceNo }</td>
				</tr>
				<hr>
				<tr height="35">
					<td  align="center" width="15%">项目订单状态</td>
					<c:choose>
						<c:when test="${po.state=='已结算'}">
							<td width="35%">审核通过</td>
						</c:when>
						<c:otherwise>
							${po.state }
						</c:otherwise>
					</c:choose>
				</tr>
				<tr height="35">
					<td width="15%" align="center">审核人：</td>
					<td width="35%">${po.verifyName }</td>
					<td width="15%" align="center">结算审核通过时间</td>
					<td width="35%"><fmt:formatDate value="${po.verifyTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr height="35">
					<td colspan="4" align="center">
					<input type="button" value="关闭" onclick="cancle()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
		