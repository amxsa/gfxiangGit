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
<title>用户信息查询</title>
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
<script language="javascript" type="text/javascript">
		
		function checkForm() {
			document.forms[0].submit();
		}
		function deleteManager(account){
			if(confirm('您确认要删除?')){
				document.forms[0].action="<%=path%>/permission/ManagerAction_deleteManager.do?account="+account;
				$("#form").ajaxSubmit(function (responseResult) {
				$("#id").val(id);
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							document.forms[0].action="<%=path%>/permission/ManagerAction_showManager.do?currentPage=${param.currentPage}";
											checkForm();
										} else {
											alert(data[1]);
										}
									}
								});
			}
		}
		function updateManager(account){
			window.location="<%=path%>/permission/ManagerAction_preUpdateManager.do?account="+account;
		}
		function addManager(){
			window.location="<%=path%>/permission/ManagerAction_preAddManager.do";
		}
	</script>
	<script type=""></script>
</head>

<body>

	<form action="<%=path%>/permission/ManagerAction_showManager.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }"
			id="currentPage" /> <input type="hidden" name="id" value="" id="id" />
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">用户信息 查询</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>用户账号：<input type="text" name="accountParam"
						value="${requestScope.accountParam }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>
					<li>用户名称：<input type="text" name="nameParam"
						value="${requestScope.nameParam }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</li>

					<li><input type="button" value="查询" class="search"
						onclick="checkForm();" /></li>
				</div>
				<div><input type="button" value="新增" class="search" onclick="addManager()"/></div>
				
			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">

							<th height="28">账号</th>
							<th>密码</th>
							<th>名称</th>
							<th>手机号码</th>
							<th>地区</th>
							<th>组织</th>
							<th>角色</th>
							<th>注册时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25">

								<td class="zw-txt">${data.account }</td>
								<td class="zw-txt">${data.password }</td>
								<td class="zw-txt">${data.name }</td>
								<td class="zw-txt">${data.telephone }</td>
								<c:forEach items="${requestScope.areaCode }" var="code">
									<c:if test="${data.areacode==code.key }">
									<td class="zw-txt">${code.value[0]}</td>
									</c:if>
								</c:forEach>
								<td class="zw-txt">${data.departmentName }</td>
								<td class="zw-txt">${data.roleName }</td>
								<td class="zw-txt">
									<fmt:formatDate value="${data.regTime }" pattern="yyyy-MM-dd HH-mm-ss" />
								</td>
								<td class="zw-txt">
									<input type="button" value="删除" onclick="deleteManager('${data.account}')" />
									<input type="button" value="修改" onclick="updateManager('${data.account}')" />
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
