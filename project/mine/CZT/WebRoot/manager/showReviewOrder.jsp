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
<title>待处理订单</title>
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
		function del(id){
			if(confirm('您确认要删除?')){
				document.forms[0].action="<%=path%>/manager/OrderManagerAction_deleteOrder.do"
				$("#id").val(id);
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							parent.leftFrame.total();
							document.forms[0].action="<%=path%>/manager/OrderManagerAction_showReviewOrder.do?currentPage=${param.currentPage}";
							checkForm();
						}else{
							alert(data[1]);
						}
					}
				});
			}
		}
		
		function shenhe(id){
			if(confirm('您确认审核通过?')){
				document.forms[0].action="<%=path%>/manager/OrderManagerAction_reviewOrder.do"
				$("#id").val(id);
				$("#form").ajaxSubmit(function (responseResult) {
					if(responseResult!=''){
						var data = responseResult.split("|");
						if(data[0]=='true'){
							alert(data[1]);
							parent.leftFrame.total();
							document.forms[0].action="<%=path%>/manager/OrderManagerAction_showOrder.do?currentPage=${param.currentPage}&stateQuery=1&typeQuery=showReviewOrder";
							checkForm();
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

	<form action="<%=path%>/manager/OrderManagerAction_showReviewOrder.do"
		method="post" id="form">
		<input type="hidden" name="currentPage" value="${param.currentPage }" id="currentPage"/>
		<input type="hidden" name="id" value="" id="id"/>
		<div id="main">
			<div id="table">
				<div id="ptk">
					<div id="tabtop-tt" style="width: 200px	">待审核的单</div>
				</div>
			</div>

			<div id="table">
				<div id="ptk">
					<div id="tabtop-z">输入查询条件</div>
				</div>
			</div>

			<div id="main-tab">
				<div id="info-4">
					<li>订单号：<input type="text" name="idQuery"
						value="${requestScope.id }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<!-- 
					<li>电话号码：<input type="text" name="telephone"
						value="${requestScope.telephone }" />&nbsp;&nbsp;&nbsp;&nbsp;</li>
					 -->
					 <!--  
					<c:if test="${sessionScope.login.roleid==0 }">
						<li>订单状态：
						<select name="stateQuery">
							<option value="">全部</option>
							<option value="1" ${requestScope.state=='1'?'selected':'' }>待审核</option>
							<option value="4" ${requestScope.state=='4'?'selected':'' }>二次修改带审核</option>
						</select>
					</c:if>	
					-->
					&nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li>操作时间： <input type="text" name="starttimeQuery"
						value="${requestScope.starttime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" /> -- <input type="text" name="endtimeQuery"
						value="${requestScope.endtime }" style="width:120px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
						readonly="true" /> &nbsp;&nbsp;&nbsp;&nbsp;</li>
					<li> <input type="button" value="查询" class="search" onclick="checkForm();" />
					</li>
				</div>
				

			</div>
			<div id="main-tablist">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="showData" class="simple">
					<thead>
						<tr class="odd">
							
							<th height="28">订单号</th>
							<th>产品配置</th>
							<th>单价</th>
							<th>数量</th>
							<th>合计</th>
							<th>来源</th>
							<th>收货人</th>
							<th>收货人号码</th>
							<th>收货人地址</th>
							<th>订单状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="data" items="${pageResult.content}" varStatus="v">

							<tr class="odd" align="center" height="25" >
							
								<td class="zw-txt">${data.id }</td>
								<td class="zw-txt">${data.configureStr }</td>
								<td class="zw-txt">${data.unitPrice }</td>
								<td class="zw-txt">${data.count }</td>
								<td class="zw-txt">${data.total }</td>
								<td class="zw-txt">${data.fromPartStr }</td>
								<td class="zw-txt">${data.receiveName }</td>
								<td class="zw-txt">${data.receiveTelephone }</td>
								<td class="zw-txt">${data.receiveAddress}</td>
								<td class="zw-txt">${data.stateStr}</td>
								<td class="zw-txt"><a href="javaScript:shenhe('${data.id}')">审核</a>
								<a href="<%=path%>/manager/showOrderDetail.jsp?id=${data.id}">详情</a></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="info-pz">${requestScope.pageResult.linkTo }</div>
			</div>
		</div>
	</form>
	
</body>
</html>
