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
<title>礼貌挂机</title>
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
	function checkState(obj){
		
		
		if(obj=='state2'){
			
			$.post("<%=path %>/user/politeOff/deletePoliteOff.do?id=${po.id}",{},function(data){
				if(data=="success"){
					window.location.href="<%=path %>/user/politeOff/showPoliteOff.do";
				}
			});
		}
		
		
	}
</script>
</head>

<div class="top">
	
	<h2>礼貌挂机</h2>
	<span><a href="<%=path%>/user/index.jsp"><img
			src="<%=path%>/images/home.png">
	</a>
	</span>
</div>

	<div class="gj_max">
		<c:if test="${not empty requestScope.po }">
			<div class="gj_lt">
				<img src="<%=path %>/images/wechat/B_${po.type}.jpg" />
				<p>
					当前场景：<i>${po.name }</i>
				</p>
			</div>
			<div class="gj_rt">
				<h1>
					有效时段：<em>${po.startExpireTime }</em>
				</h1>
				<ul class="lb_kg">
					<!--  <li class="lb"><a href="#"><img src="<%=path %>/images/lb.png">
					</a>
					</li>
					-->
					<li   id="stateOff" onclick="checkState('state2');">关闭<em></em><input type="radio" value="N" name="state" id="state2"   />
					</li>
					
				</ul>
			</div>
		</c:if>
		<c:if test="${ empty requestScope.po }">
			<p align="center">当前时间未设置礼貌挂机！</p>
		</c:if>
	</div>
	<div class="gj_list">
		<ul>
			<c:forEach var="politeoffType" items="${requestScope.politeoffType }">
				<li><em><img src="<%=path %>/images/wechat/A_${politeoffType.key}.jpg">
				</em><i>${politeoffType.value[0]}</i> <a href="<%=path %>/user/politeOff/showPoliteOffDetail.do?type=${politeoffType.key}" class="more"></a>
				 <a href="javaScript:showDtl('<%=path %>/wav/MH34${politeoffType.key}.wav');" class="test"></a>
				<!-- <span>${politeoffType.value[1]}</span> -->
				
				</li>
			</c:forEach>
		</ul>
	</div>
	<script type="text/javascript">
		 function initDoc() {
			 document.body.height = screen.height;
			 document.getElementById("bodyDiv").style.height = screen.height;
			 setHeight();
		 }
		 function showDtl(path) {
		 	document.getElementById("divMask").style.display="block";
		 	$("#audio").attr("src",path);
		 	$("#audio").attr("autoplay","autoplay");
		 }
		 function hideDtl() {
		 	document.getElementById("divMask").style.display="none";	
		 }
		 function listen() {
		  
		 }
		</script>
		<div id="divMask" class="mask" style="display:none;">
		  <div class="mask_box">
		    <div class="content">
		       <p><audio src="" id="audio"    controls="controls"></audio></p>
		       <div class="btn_list"> <a href="javaScript:hideDtl();" >关闭</a></div>
		    </div>
		  </div>
		</div>
</html>
