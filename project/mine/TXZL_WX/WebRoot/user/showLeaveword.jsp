<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>我的漏话</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<link type="text/css" href="<%=path%>/css/banner.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript">
	window.jQuery=window.Zepto;
 
 </script>
<script type="text/javascript" language="javaScript">
			
	function showLeaveword(){
		document.forms[0].submit();
	}
	function showLeavewordDetail(ANum,name,type){
		document.getElementById("name").value=name;
		document.forms[0].action="<%=path%>/user/leavewordAction.do?method=showLeavewordDetail&ANum="+ ANum + "&type=" + type;
		document.forms[0].submit();
	}
	function callout(num) {

		window.location.href = "tel:" + num;
	}
	function sendSms(num, regNo, areacode, setid, isFree, servNbr) {

		window.location.href = "sms:" + num;
	}
</script>
</head>

<div class="top">
	<!--  
<a class="back" onclick="history.back();"><img src="<%=path%>/images/back.png" /></a>
-->
	<h2>最近漏话</h2>
	<span><a href="<%=path%>/user/index.jsp"><img
			src="<%=path%>/images/home.png">
	</a>
	</span>
</div>
<form action="<%=path%>/user/leaveword/showLeaveword.do"
	method="post">
	<input type="hidden" name="name" id="name" />

	<div class="layout">
		
		<div class="louhua">
			<c:if test="${empty list }">
				<h3>
					亲！恭喜您！最近 ${requestScope.type=='1'?'7天':'1个月' }没有漏话。
					<c:if
						test="${  empty login.servNbr and login.setid=='51' }">
						（您的体验期还剩下<em>${requestScope.limiteDay }</em>天，<a
							href="<%=path%>/user/upgrade.jsp">点击办理</a>）
					</c:if>
				</h3>
				<div align="center">
					<img src="<%=path%>/images/hand.jpg">
				</div>
			</c:if>
			<c:if test="${not empty list }">
				<h3>
					亲！您最近1个月有<em>${requestScope.lhcount }</em>条漏话
					<c:if test="${  empty login.servNbr and login.setid=='51' }">
						（您的体验期还剩下<em>${requestScope.limiteDay }</em>天，<a
							href="<%=path%>/user/upgrade.jsp">点击办理</a>）
					</c:if>
					<p>
						<input type="radio" name="type" value="1" ${type=='1' ?'checked':'' } onclick="showLeaveword();" /> 最近七天 <input
							type="radio" name="type" value="2" ${type== '2' or empty type ? 'checked':'' }  onclick="showLeaveword();" /> 最近一个月
					</p>
				</h3>


			</c:if>


		</div>
		<c:if test="${not empty list }">
			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th width="30%">号码</th>
					<th width="20%">呼叫</th>
					<th width="20%">短信</th>
					<th>拨打时间</th>

				</tr>
				<c:forEach var="po" items="${requestScope.list}">
					<tr>
						<td> ${po.ANum}</br>${po.ANumCity }</td>
						<td><a href="javaScript:callout('${po.ANum }');" id=""><img
								src="<%=path%>/images/mb.jpg"> </a></td>
						<td><a
							href="javaScript:sendSms('${po.ANum }','${login.number }','${login.setid }','${login.areacode }','${login.isFree }','${login.servNbr }');"><img
								src="<%=path%>/images/mes.jpg" /> </a></td>
						<td><fmt:formatDate value="${po.recordtime}"
								pattern="MM-dd HH:mm" />
						</td>

					</tr>
				</c:forEach>
			</table>
			<div class="page">${requestScope.linkToMobile }</div>
		</c:if>
	</div>

</form>


</html>
