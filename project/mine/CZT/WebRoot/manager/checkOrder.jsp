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
<title>选择订单</title>
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
		function checkOrderid(){
			var id = $("input[type='radio']:checked").val();
			if(id==''){
				alert("请选择订单");
			}else{
				$("#orderId", window.opener.document).val(id);
				window.close();
			}
		}
		
	</script>
</head>

<body>
		<div id="main">
			
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr height="30">
							<td colspan="10"><input type="button" value="确定" onclick="checkOrderid();"/></td>
						</tr>
						<tr class="odd">
							<th height="28">选择</th>
							<th height="28">订单号</th>
							<th>产品配置</th>
							<th>单价</th>
							<th>数量</th>
							<th>处理数量</th>
							<th>合计</th>
							<th>来源</th>
							<th>收货人</th>
							<th>收货人号码</th>
							<th>收货人地址</th>
							<th>订单状态</th>
							<th>提交时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${list}" varStatus="v">

							<tr class="odd" align="center" height="25" >
								<td class="zw-txt"><input type="radio" name="id" value="${data.id }"/></td>
								<td class="zw-txt">${data.id }</td>
								<td class="zw-txt">${data.configureStr }</td>
								<td class="zw-txt">${data.unitPrice }</td>
								<td class="zw-txt">${data.count }</td>
								<td class="zw-txt">${data.dealCount }</td>
								<td class="zw-txt">${data.total }</td>
								<td class="zw-txt">${data.fromPartStr }</td>
								<td class="zw-txt">${data.receiveName }</td>
								<td class="zw-txt">${data.receiveTelephone }</td>
								<td class="zw-txt">${data.receiveAddress}</td>
								<td class="zw-txt">${data.stateStr}</td>
								<td class="zw-txt">${data.submitTime}</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				
			</div>
		</div>
	
	
</body>
</html>
