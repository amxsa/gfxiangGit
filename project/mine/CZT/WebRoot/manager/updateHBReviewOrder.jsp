<%@page import="cn.cellcom.czt.common.Env"%>
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
	pageContext.setAttribute("operateType", Env.OPERATE_TYPE);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>号百人员审核订单</title>
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
			var shenheState  = $('input[name="shenheState"]:checked').val();
			var confirmStr='';
			if(shenheState=='N'){
				var shenheResult =$.trim($("#shenheResult").val());
				if(shenheResult==''){
					alert('审核不通过，请填写原因');
					return false;
				}
				confirmStr="您确定审核不通过";
			}else if(shenheState=='Y'){
				confirmStr="您确定审核通过";
			}
			if(confirm(confirmStr)){
				document.forms[0].action="<%=path%>/manager/OrderManagerAction_updateHBReviewOrder.do"
				
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							//刷新左边统计数
							parent.leftFrame.total();
							document.forms[0].action="<%=path%>/manager/OrderManagerAction_showHBReviewOrder.do?currentPage=${param.currentPage}";
							document.forms[0].submit();
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
					<div id="tabtop-tt" style="width: 150px	">审核订单</div>
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
			<form action="<%=path%>/manager/OrderManagerAction_updateHBReviewOrder.do" method="post" id="form">
				<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
				<input type="hidden" name="stateQuery" value="${param.stateQuery }" id="stateQuery"/>
				<input type="hidden" name="starttimeQuery" value="${param.starttimeQuery }" id="starttimeQuery"/>
				<input type="hidden" name="endtimeQuery" value="${param.endtimeQuery }" id="endtimeQuery"/>
				
				<input type="hidden" name="id" value="${order.id }" id="id"/>
				
				<table width="911" class="table-slyle-hs">
					<tr height="35">
						<td width="15%" align="center">订单号：</td>
						<td colspan="3" width="85%">${order.id }</td>
					</tr>
					<tr height="35">
						<td width="15%" align="center">订单账号：</td>
						<td width="35%">${order.account }</td>
						<td width="15%" align="center">所属厂家：</td>
						<td width="35%">${order.spcode }</td>
					</tr>
					<tr height="35">
						<td width="15%" align="center">型号配置：</td>
						<td width="35%">${order.configure }</td>
						<td width="15%" align="center">订单时间：</td>
						<td width="35%"><fmt:formatDate value="${order.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					<tr height="35">
						<td align="center">产品单价：</td>
						<td>${order.unitPrice }元</td>
						<td align="center">产品数量：</td>
						<td>${order.count }个</td>
					</tr>
					<tr height="35">
						<td align="center">产品总价：</td>
						<td>${order.total }元</td>
						<td align="center">已受理数：</td>
						<td>${order.dealCount }个</td>
					</tr>
					<tr height="35">
						<td align="center">订单状态：</td>
						<td>${order.state }</td>
						<td align="center">订单来源：</td>
						<td>${order.fromPart }</td>
					</tr>
					<tr height="35">
						<td align="center">订单类型：</td>
						<td colspan="3">
							<c:if test="${order.orderType=='1' }">购买</c:if>
							<c:if test="${order.orderType=='2' }">领用</c:if>
						</td>
						
					</tr>
					<tr height="35">
						<td align="center">收货人姓名：</td>
						<td>${order.receiveName }</td>
						<td align="center">收货人电话：</td>
						<td>${order.receiveTelephone }</td>
					</tr>
					<tr height="35">
						<td align="center">收货人地址：</td>
						<td colspan="3">${order.receiveAddress }</td>
					</tr>
					<tr height="35">
						<td align="center">订单备注：</td>
						<td colspan="3">${order.remark }</td>
					</tr>
					<tr height="35">
						<td align="center">订单流程：</td>
						<td colspan="3">
							<c:forEach var="orderState" items="${orderDetail}">
								<-- 账号： ${orderState.account}，流水描述：${orderState.describeMsg}，时间：<fmt:formatDate value="${orderState.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /> --> <br />
							</c:forEach></td>
					</tr>
					<tr>
						<td align="center">审核结果：</td>
						<td colspan="3">
							<input type="radio" name="shenheState" checked="checked" value="Y"/>审核通过&nbsp;&nbsp;
							<input type="radio" name="shenheState" value="N"/>审核不通过&nbsp;&nbsp;
							
						</td>
					</tr>
					<tr>
						<td align="center">描述原因：</td>
						<td colspan="3">
							<input type="text" name="shenheResult" id="shenheResult" size="100" maxlength="200" />(最多200 字符)
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" value="确定" onclick="GO();"/>
							&nbsp;&nbsp;&nbsp;
							
							<input type="button" value="返回" onclick="javaScript:history.back(-1);"/>
						</td>
						
					</tr>
				</table>
			</form>
		</div>
	</div>
	
</body>
</html>
