<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setAttribute("decorator", "none");
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>



		<table width="911" class="table-slyle-hs">
			<tr height="35">
				<td width="15%" align="center">订单号：</td>
				<td colspan="3" width="85%">${order.id }</td>
			</tr>
			<tr height="35">
				<td width="15%" align="center">订单账号：</td>
				<td width="35%">${order.account }</td>
				<td width="15%" align="center">所属厂家：</td>
				<td width="35%">${order.spcode }</td>
			</tr>
			<tr height="35">
				<td width="15%" align="center">型号配置：</td>
				<td width="35%">${order.configure }</td>
				<td width="15%" align="center">订单时间：</td>
				<td width="35%"><fmt:formatDate value="${order.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr height="35">
				<td align="center">产品单价：</td>
				<td>${order.unitPrice }元</td>
				<td align="center">产品数量：</td>
				<td>${order.count }个</td>
			</tr>
			<tr height="35">
				<td align="center">产品总价：</td>
				<td>${order.total }元</td>
				<td align="center">已受理数：</td>
				<td>${order.dealCount }个</td>
			</tr>
			<tr height="35">
				<td align="center">订单状态：</td>
				<td>${order.state }</td>
				<td align="center">订单来源：</td>
				<td>${order.fromPart }</td>
			</tr>
			<tr height="35">
				<td align="center">订单类型：</td>
				<td colspan="3">
					<c:if test="${order.orderType=='1' }">购买</c:if>
					<c:if test="${order.orderType=='2' }">领用</c:if>
				</td>
				
			</tr>
			<tr height="35">
				<td align="center">收货人姓名：</td>
				<td>${order.receiveName }</td>
				<td align="center">收货人电话：</td>
				<td>${order.receiveTelephone }</td>
			</tr>
			<tr height="35">
				<td align="center">收货人地址：</td>
				<td colspan="3">${order.receiveAddress }</td>
			</tr>
			<tr height="35">
				<td align="center">订单备注：</td>
				<td colspan="3">${order.remark }</td>
			</tr>
			<tr height="35">
				<td align="center">订单流程：</td>
				<td colspan="3"><c:forEach var="orderState"
						items="${orderDetail}">
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
									<td height="28">SN</td>
									<td >PN</td>
									<td>流量卡</td>
									<td>到期时间</td>
									<td>备注</td>
									<td>捆绑时间</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="bindList" items="${bindList}" varStatus="v">
				
									<tr class="odd" align="center" height="25" >
									
										<td class="zw-txt">${bindList.sn }</td>
										<td class="zw-txt">${bindList.barcode }</td>
										<td class="zw-txt">${bindList.mobile }</td>
										
										<td class="zw-txt">
											<fmt:formatDate value="${bindList.limiteTime}" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td class="zw-txt">${bindList.remark }</td>
										<td class="zw-txt">
											<fmt:formatDate value="${bindList.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" />
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
						物流公司：${express.expressCompanyStr }</br>
						物流单号：${express.expressId }</br>
						签收人：${express.expressName }</br>
						配送时间：<fmt:formatDate value="${express.sendTime}" pattern="yyyy-MM-dd HH:mm:ss" /></br>
						操作人：${express.operateName}</br>
						备注：${express.remark}
					</td>
				</tr>
			</c:if>
		</table>
		
		