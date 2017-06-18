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
		function checkPrice(){
			var configure = $("#configure").val();
			if(configure=='H'){
				$("#defaultUnitPrice").empty();
				$("#defaultUnitPrice").append("<option value=549>549</option>");
				$("#defaultUnitPrice").append("<option value=620>620</option>");
				$("#defaultUnitPrice").append("<option value=688>688</option>");
			}else{
				$("#defaultUnitPrice").empty();
				$("#defaultUnitPrice").append("<option value=449>449</option>");
				$("#defaultUnitPrice").append("<option value=520>520</option>");
				$("#defaultUnitPrice").append("<option value=588>588</option>");
			}
		}
		
		function checkDefineUnitPrice(){
			var defineUnitPriceFlag = $("#defineUnitPriceFlag").attr("checked");
			if(defineUnitPriceFlag==true){
				$("#defaultUnitPrice").empty();
				$("#defineUnitPrice").show();
			}else{
				checkPrice();
				$("#defineUnitPrice").val("");
				$("#defineUnitPrice").hide();
			}
		}
		function isUnsignedInteger(a){
    		var reg =/^[0-9]{1,4}$/;
    		if (!reg.exec(a)) 
    			return false;
			return true;
		}
		function GO() {
			var defineUnitPriceFlag = $("#defineUnitPriceFlag").attr("checked");
			if(defineUnitPriceFlag==true){
				var defineUnitPrice = $("#defineUnitPrice").val();
				if(!isUnsignedInteger(defineUnitPrice)){
					alert('自定单价错误');
					return false;
				}
				$("#unitPrice").val($("#defineUnitPrice").val());
			}else{
				$("#unitPrice").val($("#defaultUnitPrice").val());
			}
			
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
		function checkReceiveType(obj){
			if(obj.value=='1'){
				/**
				$("#receiveName").val('${sessionScope.login.name}');
				$("#receiveTelephone").val('${sessionScope.login.telephone}');
				$("#receiveAddress").val('${sessionScope.login.address}');
				*/
				
			}else if(obj.value=='2') {
				
			}else if(obj.value=='3'){
				/**
				$("#receiveName").attr("readonly","");
				$("#receiveTelephone").attr("readonly","");
				$("#receiveAddress").attr("readonly","");
				*/
				$("#receiveName").val('');
				$("#receiveTelephone").val('');
				$("#receiveAddress").val('');
				var count = $("#count").val();
				/**
				if(count!='1'){
					alert('配送客户产品数量只能为1');
					$("#count").val("1");
				}
				*/
			}
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
			<form action="<%=path%>/manager/OrderManagerAction_addOrder.do" method="post">
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
							<select name="spcode">
								<c:forEach var="spcode" items="${spcode }">
									<option value="${spcode.key }">${spcode.value }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>产品配置：</td>
						<td>
							<select name="configure" id="configure" onchange="checkPrice();">
								<option value="H">高配</option>
								<option value="L">低配</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>产品单价：</td>
						<td>
							
								<select name="defaultUnitPrice" id="defaultUnitPrice" >
								
										<option value="549">549</option>
										<option value="620">620</option>
										<option value="688">688</option>
									
								</select>
							&nbsp;&nbsp;&nbsp;
							<input type="checkbox" name="defineUnitPriceFlag" id="defineUnitPriceFlag" value="1" onclick="checkDefineUnitPrice();"/> 自定单价
							&nbsp;<input type="text" name="defineUnitPrice" size="4" maxlength="4" id="defineUnitPrice" style="display: none;" />
							
						</td>
					</tr>
					<tr>
						<td>产品数量：</td>
						<td>
							<input name="count" id="count" maxlength="4" size="4" />
						</td>
					</tr>
					<tr>
						<td>配送信息：</td>
						<td>
							<input type="radio" name="receiveType" value="1" checked="checked" onclick="checkReceiveType(this);"/> 配送自己&nbsp;&nbsp;&nbsp;
							<!--  <input type="radio" name="receiveType" value="2"  onclick="checkReceiveType(this);"/>联系人查找 -->
							<input type="radio" name="receiveType" value="3"  onclick="checkReceiveType(this);"/>配送客户
							<br/>
							姓名：<input type="text" size="12" name="receiveName" value=""   id="receiveName"/>
							号码：<input type="text" size="12" name="receiveTelephone" value=""  id="receiveTelephone"/>
							
							地址：<input type="text" size="70"  name="receiveAddress" value=""  id="receiveAddress"/>
						</td>
					</tr>
					<c:if test="${sessionScope.login.roleid==0}">
						<tr>
							<td>订单时间：</td>
							<td>
								<input name="orderTime" id="orderTime" maxlength="30" size="30" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							readonly="true"/>&nbsp;&nbsp;&nbsp;(系统未上线前的旧订单时间填写)
							</td>
						</tr>
						<tr>
							<td>附属单号：</td>
							<td>
								<input name="otherOrderId" id="other_order_id" maxlength="30" size="30" />&nbsp;&nbsp;&nbsp;(可填写微信单号或其它代表唯一单号,可填可不填)
							</td>
						</tr>
						<tr>
							<td>订单类型：</td>
							<td>
								<input type="radio" name="orderType" value="1" checked="checked" /> 购买&nbsp;&nbsp;
								<input type="radio" name="orderType" value="2"  /> 领用&nbsp;&nbsp;
							</td>
						</tr>
					</c:if>
					<tr>
						<td>订单备注：</td>
						<td>
							<textarea rows="5" cols="130" name="remark"></textarea>
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
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：<br />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、根据产品配置会出现不同的单价，如果不在单价范围类，可自定单价输入<br />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、配送信息中如果选择配送自己，则代表产品配送给自己（可在个人资料中修改自己的信息），由自己在发放给客户；
			    <br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果选择配送客户，则代表直接将产品发给客户
			    </font>
		
	</div>
</body>
</html>
