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

	<form action="<%=path%>/manager/TDCodeManagerAction_showTDCodeHistory.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">TDCode手机历史记录查询</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>手机号：<input type="text" name="mobile"
						value="${requestScope.mobile }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>加密二维码(SN)：<input type="text" name="tdcodemd5"
						value="${requestScope.tdcodemd5 }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					
					<li>操作类型: <select name="operateType"><option value="">全部</option>
							<c:forEach var="operateType" items="${operateType }">
								<option value="${operateType.key }" ${requestScope.operateType==operateType.key?'selected':'' }>${operateType.value }</option>
							</c:forEach>
							
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>操作来源: <select name="fromPart"><option value="">全部</option>
							
								<option value="mobile" ${requestScope.fromPart=='mobile'?'selected':'' }>手机</option>
								<option value="web" ${requestScope.fromPart=='web'?'selected':'' }>网页</option>
							
					</select>&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>操作时间： <input type="text" name="starttime"
						value="${requestScope.starttime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" /> -- <input type="text" name="endtime"
						value="${requestScope.endtime }" style="width:80px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
						readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li><input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							
							<th height="28">号码</th>
							<th>加密二维码(SN)</th>
							<th>内部二维码</th>
							<th>操作类型</th>
							<th>操作描述</th>
							<th>操作时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
							
								<td class="zw-txt">${data.mobile }</td>
								<td class="zw-txt">${data.tdcodemd5 }</td>
								<td class="zw-txt">${data.tdcode }</td>
								<td class="zw-txt">${data.operateType}</td>
								<td class="zw-txt">${data.operateDescribe }</td>
								<td class="zw-txt">
									<fmt:formatDate value="${data.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
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
