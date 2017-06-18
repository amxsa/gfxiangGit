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
<title>TDCode组新增</title>
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
			var name = $.trim($("#name").val());
			if(name==''){
				alert('组名不能为空');
				return false;
			}
			if(confirm('您确认要增加？')){
				
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							document.forms[0].action="<%=path%>/manager/TDCodeManagerAction_showTDCodeGroup.do";
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
					<div id="tabtop-tt" style="width: 150px	">TDCode组新增</div>
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
			<form action="<%=path%>/manager/TDCodeManagerAction_modifyTDCodeGroup.do" method="post" id="form">
				<input type="hidden" name="operateType" value="A"/>
				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="80">组名：</td>
						<td>
							<input name="name" id="name"  maxlength="20" size="30" />
						</td>
					</tr>
					
					<tr>
						<td colspan="2"><input type="button" value="确定" onclick="GO();"/>
							&nbsp;&nbsp;&nbsp;
							
						</td>
						
					</tr>
				</table>
			</form>
		</div>
		<br />
		<br />
		<font style="color: red;">
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;说明：组编号由系统自动产生<br />
			

		</font>
		
	</div>
</body>
</html>
