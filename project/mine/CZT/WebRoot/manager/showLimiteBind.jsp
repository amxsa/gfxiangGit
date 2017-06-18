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
		
		
	</script>
</head>

<body>

	<form action="<%=path%>/manager/BindManagerAction_showLimiteBind.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="type" value="${requestScope.type }" />
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">
					<c:if test="${requestScope.type=='1' }">
						即将到期用户
					</c:if>
					<c:if test="${requestScope.type=='2' }">
						已经到期用户
					</c:if>
					
					</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					
					<li>SN：<input type="text" name="sn"
						value="${requestScope.sn }" />&nbsp;(后八位查询)&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>流量卡：<input type="text" name="fluxCard"
						value="${requestScope.fluxCard }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>用户手机号：<input type="text" name="mobilenum"
						value="${requestScope.mobilenum }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
					<li><input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							
							<th height="28">SN</th>
							
							<th>流量卡</th>
							<th>用户手机号</th>
							<th>到期时间</th>
							<th>到期类型</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
							
								<td class="zw-txt">${data.sn }</td>
								<td class="zw-txt">${data.fluxcard}</td>
								<td class="zw-txt">${data.mobilenum}</td>
								<td class="zw-txt"><fmt:formatDate value="${data.limiteTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								
								<td class="zw-txt" style="color: red;">${data.limiteUser}</td>
								
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
