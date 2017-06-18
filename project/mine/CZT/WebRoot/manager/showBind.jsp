<%@page import="cn.cellcom.czt.common.Env"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
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
<title>设备流量卡捆绑查询</title>
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
			document.forms[0].submit();
		}
		function del(id){
			if(confirm('您确认要删除?')){
				document.forms[0].action="<%=path%>/manager/BindManagerAction_deleteBind.do"
				$("#id").val(id);
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/manager/BindManagerAction_showBind.do?currentPage=${param.currentPage}";
							checkForm();
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

	<form action="<%=path%>/manager/BindManagerAction_showBind.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="id" value="" id="id"/>
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">设备流量卡捆绑查询</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>订单号：<input type="text" name="orderIdQuery"
						value="${requestScope.orderId }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>SN：<input type="text" name="snQuery"
						value="${requestScope.sn }" />&nbsp;(后八位查询)&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>手机号：<input type="text" name="mobileQuery"
						value="${requestScope.mobile }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>操作时间： <input type="text" name="starttimeQuery"
						value="${requestScope.starttime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" /> -- <input type="text" name="endtimeQuery"
						value="${requestScope.endtime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>
					<li><input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							
							<th height="28">订单号</th>
							<th>SN</th>
							<th>流量卡</th>
							<th>订单状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
							
								<td class="zw-txt">${data.orderId }</td>
								<td class="zw-txt">${data.sn }</td>
								<td class="zw-txt">${data.mobile}</td>
								<td class="zw-txt">${data.orderStateStr}</td>
								<td class="zw-txt">
									<c:if test="${data.orderState=='6' or data.orderState=='7'  }">
										<a href="javaScript:del('${data.id}')">删除</a>
									</c:if>
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
