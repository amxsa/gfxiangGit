<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<title>新增TDCode</title>
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="../css/myTable.css" type="text/css" />

<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="../datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/common.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		
		function GO() {
			
			document.forms[0].submit();
		}
		function checkProductMode(obj){
			if(obj.value=='1'){
				$("#endNumId").hide();
				$("#endNum").val("");
			}else{
				$("#endNumId").show();
				$("#endNum").val("");
			}
		}
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">新增TDCode</div>
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
			<form action="<%=path%>/manager/TDCodeManagerAction_addTDCode.do" method="post">

				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="150">厂家：</td>
						<td>
							<select name="spcode">
								<c:forEach var="spcode" items="${requestScope.spcode }">
									<option value="${spcode.key }">${spcode.value }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>配置：</td>
						<td>
							<select name="version">
								<option value="H">高配</option>
								<option value="L">低配</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>生成方式</td>
						<td>
							<input type="radio" name="productMode" value="1" onclick="checkProductMode(this);"/> 单个&nbsp;&nbsp;&nbsp;
							<input type="radio" name="productMode" value="2" checked="checked" onclick="checkProductMode(this);"/>批量
						</td>
					</tr>
					<tr>
						<td>流水号：</td>
						<td>
							<input type="text" name="startNum" id="startNum" maxlength="6"/>
							<font id="endNumId">
							--
							<input type="text" name="endNum" id="endNum" maxlength="6"/>
							</font>
							&nbsp;&nbsp;<font style="color: red;">取值范围(000000-999999)</font>
						</td>
					</tr>
					<tr>
						<td>日期：</td>
						<td>
							<select name="date">
								<c:forEach var="da" items="${requestScope.date }">
									<option value="${da}">${da}</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;<font style="color: red;">正式数据请选择当月</font>
						</td>
					</tr>
					<tr>
						<td>群组：</td>
						<td>
							<select name="groupId">
								<c:forEach var="list" items="${requestScope.list }">
									<option value="${list.number}">${list.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" value="确定" onclick="GO();"/>
							&nbsp;&nbsp;&nbsp;
							<input name="" type="button" value="返回" onclick="javaScript:history.back();" />
						</td>
						
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
