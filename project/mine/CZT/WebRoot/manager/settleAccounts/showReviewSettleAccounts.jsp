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
	pageContext.setAttribute("orderState", Env.ORDER_STATE);
	pageContext.setAttribute("areacode", Env.AREACODE);
	pageContext.setAttribute("settleAccounts", Env.SETTLEACCOUNTS);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待结算订单</title>
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
		function checkForm(){
			document.forms[0].submit();
		}
		function getSettle(id){
				
                document.forms[0].action="<%=path%>/manager/settleAccounts/showOrderSettle.jsp?id="+id;
                document.forms[0].submit();
  		}
		
	</script>
</head>

<body>

	<form  action="<%=path%>/manager/SettleAccountsManagerAction_showReviewSettleAccounts.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="operation"  value="settle"  />
		<input type="hidden" name="id" id="id" />
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">待结算订单</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>订单号：<input type="text" name="idQuery"
						value="${requestScope.id }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<!-- 
					<li>电话号码：<input type="text" name="telephone"
						value="${requestScope.telephone }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					 -->
					
					&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>操作时间： <input type="text" name="starttimeQuery"
						value="${requestScope.starttime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" /> -- <input type="text" name="endtimeQuery"
						value="${requestScope.endtime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" /> &nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>订单状态：
						<select name="orderStateQuery">
							<option value="">全部</option>
							<c:forEach var="orderState" items="${orderState }">
								<option value="${orderState.key }" ${requestScope.state==orderState.key?'selected':'' }>${orderState.value }</option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
					
				</div>
				<div id="info-4">
					<li>订单渠道：
					<select name="orderAreaQuery"><option value="">全部</option>
						<c:forEach var="areacode" items="${areacode}">
							<option value="${areacode.key }" ${requestScope.orderArea==areacode.key?'selected':'' }>${areacode.value[0] }</option>
						</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
					<li>设备版本：<select name="configureQuery">
						<option value="">全部</option>
						<option value="H" ${requestScope.configure=='H'?'selected':'' }>4Gwife版</option>
						<option value="L" ${requestScope.configure=='L'?'selected':'' }>普通版</option>
					</select>  &nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>结算状态：
						<select name="settleAccountsStateQuery">
							<option value="">全部</option>
							<c:forEach var="settleAccounts" items="${settleAccounts }">
								<option value="${settleAccounts.key }" ${requestScope.settleAccountsFlag==settleAccounts.key?'selected':'' }>${settleAccounts.value }</option>
							</c:forEach>
						</select>	
						&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li> <input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
				</div>

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							
							<th height="28">订单号</th>
							<th>渠道商名称</th>
							<th>订单时间</th>
							<th>数量</th>
							<th>单价</th>
							<th>合计</th>
							<th>联系号码</th>
							<th>设备版本</th>
							<th>订单状态</th>
							<th>结算状态</th>
							<th>备注</th>
							
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
							
								<td class="zw-txt">${data.orderId }</td>
								<td class="zw-txt">${data.areacode }</td>
								<td class="zw-txt">${data.orderTime }</td>
								<td class="zw-txt">${data.count }</td>
								<td class="zw-txt">${data.originalUnitPrice }</td>
								<td class="zw-txt">${data.originalTotalPrice }</td>
								<td class="zw-txt">${data.telephone }</td>
								<td class="zw-txt">${data.configure }</td>
								<td class="zw-txt">${data.orderState }</td>
								<td class="zw-txt">
									<c:choose>
										<c:when test="${data.state==null}">待结算</c:when>
										<c:otherwise>${data.state }</c:otherwise>
									</c:choose>
								</td>
								<td class="zw-txt"><font color="red">${data.remark }</font></td>
								
								<td class="zw-txt">
									<%-- <a href="<%=path%>/manager/settleAccounts/showOrderSettle.jsp?id=${data.id}" target="_self">发起结算</a> --%>
									<a href="javascript:getSettle('${data.id }')">发起结算</a>
								</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="info-pz">${requestScope.pageResult.linkTo }</div>
			</div>
		</div>
	</form>
	
</body>
</html>
