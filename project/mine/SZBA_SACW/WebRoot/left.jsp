<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>菜单导航</title>
<link type="text/css" href="css/main.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<style type="text/css">
body{background:#E4F0FC;border-right:1px solid #F4F4F4;}
*{margin:0;padding:0}
html, body{height:100%;}
*html .leftside{height:100%;}
</style>
<script language="javascript">
function expandIt(divId){
 var objDiv = document.getElementById(divId);
var spobjDiv=document.getElementById("sp"+divId);
 if (objDiv.style.display=="none"){ 
 spobjDiv.src='images/menu_minus.gif';
 objDiv.style.display="";
 }else{
 spobjDiv.src='images/menu_plus.gif';
 objDiv.style.display="none";
 }}
 function goTo(id,href) {
 	if(href!='') {
 		 $(".on").removeClass("on");
 		$("#"+id).prev("h3").addClass("on");
 		var frames=parent.window.frames;
 		frames[1].window.location='${pageContext.request.contextPath}'+href;
 		return;
 	} else {
 		expandIt(id);
 		return;
 	}
 }
 function setOnClass(mythis){
	 $(".on").removeClass("on");
	$(mythis).parent("p").addClass("on");
 }

</script>
</head>
<body>
<div class="leftside">
<h1 class="tit_bg">导航菜单</h1>
<div class="category_tree">
<c:forEach var="aRecord" items="${menusInfo}">
	<c:if test="${aRecord.LEVELS==1}">
		<h2 onclick="expandIt('${aRecord.ID}');" class="a1">${aRecord.NAME}<img src="images/menu_minus.gif" id="sp${aRecord.ID}" /></h2>
  		<span id="${aRecord.ID}" style="display:block;">
		<c:forEach var="second" items="${menusInfo}">
			<c:if test="${second.PARENT_ID==aRecord.ID}">
				<h3 onclick="goTo('${second.ID}','${second.URL}')">${second.NAME}<img src="images/menu_minus.gif" id="sp${second.ID}" /></h3>
				<span id="${second.ID}" style="display:block">
				<c:forEach var="third" items="${menusInfo}">
					<c:if test="${third.PARENT_ID==second.ID}">
						<c:set var="href" value="javascript:alert('建设中..');" />
						<c:if test="${not empty  third.URL}">
							<c:set var="href" value="${pageContext.request.contextPath}${third.URL}" />
						</c:if>
		  				<p><a href="${href}" target=${empty third.TARGET?'main':third.TARGET} onclick="setOnClass(this)">${third.NAME}</a></p>
					</c:if>
				</c:forEach>
				</span>
			</c:if>
		</c:forEach>
		</span>
	</c:if>
</c:forEach>
<!-- 
 <h2 onClick="expandIt('001');" class="a1">工作事项<img src="images/menu_minus.gif" id="sp001"></h2>
  <span id="001" style="display:block;">
    <h3 class="on"><a href="01我的事项.html" target="main">我的事项</a></h3>
  </span>
 <h2 onClick="expandIt('002');" class="a2">财物管理<img src="images/menu_plus.gif" id="sp002"></h2>
  <span id="002" style="display:none;">
    <h3 onClick="expandIt('002a');" >财物登记<img src="images/menu_minus.gif" id="sp002a"></h3>
	<span id="002a" style="display:block">
	  <p><a href="021财物登记.html" target="main">财物登记</a></p>
	  <p><a href="021财物登记.html" target="main">财物登记</a></p>
	  <p><a href="021财物登记.html" target="main">财物登记</a></p>
	</span>
    <h3><a href="022财物处置.html" target="main">财物处置</a></h3>
    <h3><a href="023轨迹查询.html" target="main">轨迹查询</a></h3>
    <h3><a href="024电子物证.html" target="main">电子物证</a></h3>
  </span>
  <h2 onClick="expandIt('005');" class="a5">个人中心<img src="images/menu_minus.gif" id="sp005"></h2>
   <span id="005" style="display:block;">
	<h3><a href="11修改密码.html" target="main">修改密码</a></h3>
	<h3><a href="12我的消息.html" target="main">我的消息</a></h3>
   </span>
   <h2 onClick="expandIt('003');" class="a3">派出所领导-财物审批<img src="images/menu_minus.gif" id="sp003"></h2>
   <span id="003" style="display:block;">
	<h3><a href="031财物审批.html" target="main">未审批（1）</a></h3>
   </span>
   <h2 onClick="expandIt('004');" class="a4">暂存库保管员<img src="images/menu_minus.gif" id="sp004"></h2>
   <span id="004" style="display:block;">
	<h3><a href="041财物入库.html" target="main">财物入库</a></h3>
	<h3><a href="042财物出库.html" target="main">财物出库</a></h3>
	<h3><a href="050分类.html" target="main">分类页面</a></h3>
   </span>--> 
 </div>
  
</div>
</body>
</html>