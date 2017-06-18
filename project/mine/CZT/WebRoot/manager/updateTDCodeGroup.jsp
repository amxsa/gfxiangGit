<%@page import="cn.cellcom.czt.common.Env"%>
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
<title>修改TDCode组信息</title>
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
			var name = $.trim($("#name").val());
			if(name==''){
				alert('组名不能为空');
				return false;
			}
			$.post("<%=path%>/manager/TDCodeManagerAction_modifyTDCodeGroup.do",{number:'${param.number}',name:name,operateType:'U'},function (data){
				result = data.split("|");
				if(result[0]=='true'){
					alert(result[1]);
				}else{
					alert(result[1]);
				}
			});
			
		}
		
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">修改TDCode组</div>
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
			<form action="<%=path%>/manager/TDCodeManagerAction_modifyTDCodeGroup.do" method="post">
				<input type="hidden" name="operateType" value="U"/>
				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="150">编号：</td>
						<td>
							${param.number}
						</td>
					</tr>
					<tr>
						<td>组名：</td>
						<td>
							<input name="name" id="name" value="${param.updateName}" maxlength="20" size="30" />
						</td>
					</tr>
					
					
					<tr>
						<td colspan="2"><input type="button" value="修改" onclick="GO();"/>
							&nbsp;&nbsp;&nbsp;
							
						</td>
						
					</tr>
				</table>
			</form>
		</div>
	</div>
	<c:if test="${not empty result}">
		<script language="javascript">
			 alert('${result}');
		</script>
	</c:if>
</body>
</html>
