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
	pageContext.setAttribute("spcode", Env.SPCODE);
	pageContext.setAttribute("unitPrice", Env.UNIT_PRICE);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单新增</title>
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
	src="../js/common.js"></script>
<script language="javascript" type="text/javascript"
	language="javascript">
		function checkConfigure(){
			var spcode = $("#spcode").val();
			if(spcode=='O10'){
				$("#configure").empty();
				$("#configure").append("<option value='L'>低配</option>");
				$("#defaultUnitPrice").empty();
				$("#defaultUnitPrice").append("<option value=549>588</option>");
			}else if(spcode=='O11'){
				$("#configure").empty();
				$("#configure").append("<option value='H'>高配</option>");
				$("#defaultUnitPrice").empty();
				$("#defaultUnitPrice").append("<option value=549>788</option>");
			}
		}
		
		function isUnsignedInteger(a){
    		var reg =/^[1-9]{1,3}$/;
    		if (!reg.exec(a)) 
    			return false;
			return true;
		}
		function GO() {
			$("#unitPrice").val($("#defaultUnitPrice").val());
			var count = $("#count").val();
			if(count==''){
				alert('请填写产品数量');
				return false;
			}
			var receiveName = $("#receiveName").val();
			if(receiveName==''){
				alert('请填写配送姓名');
				return false;
			}
			var receiveTelephone = $("#receiveTelephone").val();
			if(receiveTelephone==''){
				alert('请填写配送电话');
				return false;
			}
			var receiveAddress = $("#receiveAddress").val();
			if(receiveAddress==''){
				alert('请填写配送地址');
				return false;
			}
			document.forms[0].submit();
		}
		
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 150px	">订单新增</div>
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
			<form action="<%=path%>/manager/OrderAlipayManagerAction_addOrderAlipay.do" method="post">
				<c:if test="${sessionScope.login.roleid==0 }">
					<!-- 1 代表管理员 -->
					<input type="hidden" name="fromPart" value="1"/>
				</c:if>
				<c:if test="${sessionScope.login.roleid!=0 }">
					<!-- 2代表地市经销商 -->
					<input type="hidden" name="fromPart" value="2"/>
				</c:if>
				<input type="hidden" name="unitPrice" value="" id="unitPrice"/>
				<table  width="911" align="center" class="table-slyle-hs">
					<tr >
						<td width="150">产品厂家：</td>
						<td>
							<select name="spcode" id="spcode" onchange="checkConfigure();">
								<c:forEach var="spcode" items="${spcode }">
									<option value="${spcode.key }">${spcode.value }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>产品配置：</td>
						<td>
							<select name="configure" id="configure" >
								
								<option value="L">低配</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>产品单价：</td>
						<td>
							
								<select name="defaultUnitPrice" id="defaultUnitPrice" >
								
										<option value="588">588</option>
										<option value="788">788</option>
										
									
								</select>
							&nbsp;&nbsp;&nbsp;
						
							
						</td>
					</tr>
					<tr>
						<td>产品数量：</td>
						<td>
							<input name="count" id="count" maxlength="3" size="4" />
						</td>
					</tr>
					<tr>
						<td>配送信息：</td>
						<td>
							
							姓名：<input type="text" size="12" name="receiveName" value=""   id="receiveName"/>
							号码：<input type="text" size="12" name="receiveTelephone" value=""  id="receiveTelephone"/>
							
							地址：<input type="text" size="70"  name="receiveAddress" value=""  id="receiveAddress"/>
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
		
		
	</div>
</body>
</html>
