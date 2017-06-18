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
			document.forms[0].submit();
		}
		
	</script>
</head>

<body>

	<form action="<%=path%>/manager/TDCodeManagerAction_queryComparisonHyb.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">行业版数据同步查询</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					
					<li>设备二维码(SN)：<input type="text" name="tdcodemd5"
						value="${requestScope.tdcodemd5 }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
					<li>设备二维码所在组: <select name="groupId"><option value="">全部</option>
							<c:forEach var="group" items="${requestScope.groupList }">
								<c:if test="${group.number>10000 }">
									<option value="${group.number }" ${requestScope.groupId==group.number?'selected':'' }>${group.name }</option>
								</c:if>
							</c:forEach>
							
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li><input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							
							<th height="28">设备二维码(SN)</th>
							<th>流量卡</th>
							<th>条码(PN)</th>
							<th>用户手机</th>
							<th>有效时间</th>
							<th>状态</th>
							<th>所在组ID</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
							
								<td class="zw-txt">${data['hgDeviceSn'] }</td>
								<td class="zw-txt">${data['cardNo'] }</td>
								<td class="zw-txt">${data['deviceId'] }</td>
								<td class="zw-txt">${data['userId']}</td>
								<td class="zw-txt">${data['dueDate']}</td>
								<td class="zw-txt">
									<c:if test="${data['status']=='0' }">初始</c:if>
									<c:if test="${data['status']=='1' }">绑定</c:if>
									<c:if test="${data['status']=='2' }">失效</c:if>
									<c:if test="${data['status']=='3' }">暂停</c:if>
								</td>
								<td class="zw-txt">
									${data['copId'] }
								</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="info-pz">${requestScope.pageResult.linkTo }</div>
				<c:if test="${not empty result }">
					<div id="info-pz" style="color: red;">${ result}</div>
				</c:if>
			</div>
		</div>
	</form>
	
</body>
</html>
