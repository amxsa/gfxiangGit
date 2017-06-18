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
<title>流量卡身份信息实名专员审核详情</title>
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
		
		
		
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">流量卡信息实名专员审核详情</div>
				</div>
			</div>
		<div id="table">
			<div id="ptk">
				<div id="tabtop-l"></div>
				<div id="tabtop-z">信息详情</div>
				<div id="tabtop-r1"></div>
			</div>
		</div>
		
			
		
		<div id="main-tab">
			<li style="margin-left: 15px;margin-bottom: 5px;"><input type="button" value="返回" onclick="javaScript:history.back();"/></li>
			<table  width="911" align="center" class="table-slyle-hs">
				<tr >
					<td width="150">设备号：</td>
					<td>
						${po.tdcodemd5 }
					</td>
					<td>流量卡：</td>
					<td>
						${po.fluxcard }
					</td>
				</tr>
				<tr>
					<td>实名状态：</td>
					<td>
						${po.stateStr }
						
					</td>
					<td>描述原因：</td>
					<td>
						${po.resultDescribe }
					</td>
				</tr>
				
				<tr>
					<td>身份证正面：</td>
					<td colspan="3"> 
						<img src="<%=path%>/manager/FluxIdcardManagerAction_readFluxIdcardImage.do?image=${po.image1}" alt=""  />
					</td>
					
				</tr>
				<tr>
					<td>身份证反面：</td>
					<td colspan="3">
						<img src="<%=path%>/manager/FluxIdcardManagerAction_readFluxIdcardImage.do?image=${po.image2}" alt=""  />
					</td>
					
				</tr>
				<tr>
					<td>手持身份证：</td>
					<td colspan="3">
						<img src="<%=path%>/manager/FluxIdcardManagerAction_readFluxIdcardImage.do?image=${po.image3}" alt=""  />
					</td>
					
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
