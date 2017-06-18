<%@page import="cn.cellcom.czt.common.Env"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("spcode", Env.SPCODE);
	pageContext.setAttribute("configure", Env.ORDER_CONFIGURE);
	pageContext.setAttribute("expressCompany", Env.EXPRESS);
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">    
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">    
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>订单配送</title>
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
		function updateExpress(id){
			var expressCompany =$("#expressCompany").val();
			var expressId =$("#expressId").val();
			var expressName =$("#expressName").val();
			var sendTime =$("#sendTime").val();
			var operateName =$("#operateName").val();
			
			var remark =$("#expressRemark").val();
			$.post("<%=path%>/manager/OrderManagerAction_updateOrder.do",
			{
				operateType:"updateExpress",
				id:id,
				expressCompany:expressCompany,
				expressId:expressId,
				expressName:expressName,
				sendTime:sendTime,
				operateName:operateName,
				remark:remark
			},
			function(data){
				var str = data.split("|");
				alert(str[1]);
				
			});
		}
		
		function updateOrder(id){
			
			var spcode =$("#spcode").val();
			var configure =$("#configure").val();
			var unitPrice =$("#unitPrice").val();
			var orderType =$("#orderType").val();
			var receiveName =$("#receiveName").val();
			var totalTemp = '${order.count }'*unitPrice;
			$("#total").val(totalTemp);
			var total =$("#total").val();
			var receiveTelephone =$("#receiveTelephone").val();
			var receiveAddress =$("#receiveAddress").val();
			
			var remark =$("#remark").val();
			$.post("<%=path%>/manager/OrderManagerAction_updateOrder.do",
			{
				operateType:"updateOrder",
				id:id,
				spcode:spcode,
				configure:configure,
				unitPrice:unitPrice,
				total:total,
				orderType:orderType,
				receiveName:receiveName,
				receiveTelephone:receiveTelephone,
				receiveAddress:receiveAddress,
				remark:remark
			},
			function(data){
				var str = data.split("|");
				alert(str[1]);
				
			});
		}
		
		function updateBind(id,oldSn,oldMobile){
			
			var sn =$("#sn"+id).val();
			var mobile =$("#mobile"+id).val();
			var limiteTime =$("#limiteTime"+id).val();
			var remark =$("#remark"+id).val();
			$.post("<%=path%>/manager/OrderManagerAction_updateOrder.do",
			{
				operateType:"updateBind",
				id:id,
				oldSn:oldSn,
				oldMobile:oldMobile,
				sn:sn,
				mobile:mobile,
				limiteTime:limiteTime,
				remark:remark
			},
			function(data){
				var str = data.split("|");
				alert(str[1]);
				refreshs('${order.id }');
			});
		}
		function refreshs(id){
			window.location.href="<%=path%>/manager/OrderManagerAction_preUpdateOrder.do?id="+id;
		}
	</script>

</head>

<body>
	<div id="main">
		<div id="table">
			<div id="ptk">
				<div id="tabtop-tt" style="width: 150px	">修改订单</div>
			</div>
		</div>
		
		<div id="main-tab">
			<table width="911" class="table-slyle-hs">
			<tr height="35">
				<td width="10%" align="center">订单号：</td>
				<td colspan="3" width="85%">
					${order.id }
				</td>
			</tr>
			<tr height="35">
				<td width="10%" align="center">订单账号：</td>
				<td width="35%">${order.account }</td>
				<td width="10%" align="center">所属厂家：</td>
				<td width="35%">
					<select name="spcode" id="spcode">
						<c:forEach var="spcode" items="${spcode }">
							<option value="${spcode.key }" ${spcode.key==order.spcode?'selected':''}>${spcode.value }</option>
						</c:forEach>
					</select>
					
				
				</td>
			</tr>
			<tr height="35">
				<td width="10%" align="center">型号配置：</td>
				<td width="35%">
					<select name="configure" id="configure">
						<c:forEach var="configure" items="${configure }">
							<option value="${configure.key }" ${configure.key==order.configure?'selected':''}>${configure.value }</option>
						</c:forEach>
					</select>
				</td>
				<td width="10%" align="center">订单时间：</td>
				<td width="35%"><fmt:formatDate value="${order.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr height="35">
				<td align="center">产品单价：</td>
				<td><input type="text" name="unitPrice" id="unitPrice" value="${order.unitPrice }" size="3" maxlength="3"/>元</td>
				<td align="center">产品数量：</td>
				<td>  ${order.count }个</td>
			</tr>
			<tr height="35">
				<td align="center">产品总价：</td>
				<td> <input type="text" name="total" id="total" value="${order.total }" size="5" maxlength="5"/>元</td>
				<td align="center">已受理数：</td>
				<td>${order.dealCount }个</td>
			</tr>
			<tr height="35">
				<td align="center">订单状态：</td>
				<td>${order.stateStr }</td>
				<td align="center">订单来源：</td>
				<td>${order.fromPartStr }</td>
			</tr>
			<tr height="35">
				<td align="center">订单类型：</td>
				<td colspan="3">
					<select name="orderType" id="orderType">
						<option value="1" ${order.orderType=='1'?'selected':'' }>购买</option>
						<option value="2" ${order.orderType=='2'?'selected':'' }>领用</option>
					</select>
				</td>
				
			</tr>
			<tr height="35">
				<td align="center">收货人姓名：</td>
				<td><input name="receiveName" id="receiveName" value="${order.receiveName }"/></td>
				<td align="center">收货人电话：</td>
				<td><input name="receiveTelephone" id="receiveTelephone" value="${order.receiveTelephone }"/></td>
			</tr>
			<tr height="35">
				<td align="center">收货人地址：</td>
				<td colspan="3"> <input name="receiveAddress" id="receiveAddress"  maxlength="200" size="120" value="${order.receiveAddress }"/></td>
			</tr>
			<tr height="35">
				<td align="center">订单备注：</td>
				<td colspan="3">
				<textarea rows="3" cols="130" name="remark" id="remark">${order.remark }</textarea>
				
			</tr>
			<tr height="35">
				<td colspan="4" align="center">
					<input type="button" value="修改订单" onclick="updateOrder('${order.id}');" />
					&nbsp;&nbsp;&nbsp;
					<input type="button" value="刷新" onclick="refreshs('${order.id}');" />
				</td>
			</tr>
			<tr height="35">
				<td align="center">订单流程：</td>
				<td colspan="3">
					<c:forEach var="orderState" items="${orderDetail}">
								<-- 账号： ${orderState.account}，流水描述：${orderState.describeMsg}，时间：<fmt:formatDate value="${orderState.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /> --> <br />
					</c:forEach></td>
			</tr>
			<c:if test="${not empty bindList }">
				<tr height="35">
					<td align="center">设备流量卡捆绑：</td>
					<td colspan="3">
						<table class="table-slyle-hs">
							<thead>
								<tr class="odd" align="center">
									<td height="28" >SN</td>
									<td height="28" >PN</td>
									<td>流量卡</td>
									<td>到期时间</td>
									<td>备注</td>
									<td>捆绑时间</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="bindList" items="${bindList}" varStatus="v">
				
									<tr class="odd" align="center" height="25" >
									
										<td ><input type="text"   name="sn${bindList.id}" id="sn${bindList.id}" value="${bindList.sn }" style="width: 140px;" readonly="readonly"/></td>
										<td ><input type="text"   name="barcode${bindList.id}" id="barcode${bindList.id}" value="${bindList.barcode }" style="width: 140px;" readonly="readonly" /></td>
										<td class="zw-txt"><input type="text" name="mobile${bindList.id}" id="mobile${bindList.id}" value="${bindList.mobile }" style="width: 120px;"/></td>
										
										<td class="zw-txt">
											<input type="text" name="limiteTime${bindList.id}"  id="limiteTime${bindList.id}"
												onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 120px;"
												readonly="true" value="<fmt:formatDate value="${bindList.limiteTime}" pattern="yyyy-MM-dd HH:mm:ss" />" />
											
										</td>
										<td class="zw-txt"><input type="text" name="remark${bindList.id}" id="remark${bindList.id}" value="${bindList.remark }" style="width: 120px;"/></td>
										<td class="zw-txt">
											<fmt:formatDate value="${bindList.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td class="zw-txt">
											<a href="javaScript:updateBind('${bindList.id }','${bindList.sn }','${bindList.mobile }');" style="color: red;">修改</a>
											&nbsp;&nbsp;
											
											<a href="javaScript:refreshs('${order.id}');" style="color: red;">刷新</a>
										</td>
									</tr>
				
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty express }">
				<tr height="35">
					<td align="center">物流信息：</td>
					<td colspan="3">
						物流公司：<select name="expressCompany" id="expressCompany">
							<c:forEach var="expressCompany" items="${expressCompany }">
								<option value="${expressCompany.key }" ${expressCompany.key==express.expressCompany?'selected':''}>${expressCompany.value }</option>
							</c:forEach>
						</select></br>
						物流单号：<input name="expressId" id="expressId" value="${express.expressId }"/></br>
						签&nbsp;收&nbsp;&nbsp;人： <input name="expressName"  id="expressName" value="${express.expressName }"/></br>
						配送时间：<input type="text" name="sendTime"  id="sendTime"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							readonly="true" value="<fmt:formatDate value="${express.sendTime}" pattern="yyyy-MM-dd HH:mm:ss" />" /> </br>
						操&nbsp;作&nbsp;&nbsp;人： <input type="text" name="operateName" id="operateName" value="${express.operateName}"  /></br>
						备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：<input type="text" name="expressRemark" id="expressRemark" value="${express.remark}"  />
						
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
					<input type="button" value="修改物流" onclick="updateExpress('${express.id}');"/>
					&nbsp;&nbsp;&nbsp;
					<input type="button" value="刷新" onclick="refreshs('${order.id}');" /></td>
				</tr>
			</c:if>
		</table>
			<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="javaScript:history.back();"/>
			
		</div>
		
	</div>
</body>
</html>
