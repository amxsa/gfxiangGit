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
<title>数据同步</title>
<link rel="stylesheet" href="../themes/skyblue/skyblueMain.css"
	type="text/css" title="styles1" />
<link rel="stylesheet" href="../css/myTable.css" type="text/css" />
<script language="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"></script>
<!--请在下面增加js-->
<script language="javascript" type="text/javascript"
	src="../datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/jquery.form.js"></script>
<script language="javascript" type="text/javascript"
	src="../js/common.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		function GO() {
			var groupId = $("#groupId").val();
			if(groupId==''){
				alert('请选择组');
				return false;
			}
			var file =$("#file").val();
			if(file==''){
				alert('上传文件不能为空');
				return false;
			}
			if(file.endWith('.xls')||file.endWith('.xlsx')){
				document.forms[0].submit();
			}else{
				alert('请选择Excel文件上传');
				return false;
			}
			
		}
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">修改设备所在组</div>
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
			<form
				action="<%=path%>/manager/TDCodeManagerAction_uploadTDCodeGroupBatch.do"
				method="post" enctype="multipart/form-data">

				<table width="911" align="center" class="table-slyle-hs">
					<tr>
						<td width="150">模板下载：</td>
						<td><a href="<%=path%>/template/tdcodeGroup.xlsx" style="color: red;" title="下载文件">tdcodeGroup.xlsx</a></td>
					</tr>
					<tr>
						<td width="150">选择组：</td>
						<td>
							<select name="groupId" id="groupId">
							<option value="" >请选择</option>
							<c:forEach var="group" items="${requestScope.groupList }">
								<option value="${group.number }" >${group.name }</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="150">选择文件：</td>
						<td><input name="tdcodeGroup.file" id="file" size="30" type="file"
							style="border:1px solid #C5C5C5;background:#fff;" />
							</li></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" value="确定"
							onclick="GO();" /> &nbsp;&nbsp;&nbsp;</td>

					</tr>
				</table>
			</form>
		</div>
		
		
	</div>
</body>
</html>
