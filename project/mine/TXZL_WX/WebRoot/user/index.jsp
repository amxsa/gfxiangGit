<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>我的漏话</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path %>/js/table_hover.js"></script>
<link type="text/css" href="<%=path %>/css/banner.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="<%=path %>/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/zepto.min.js"></script>

<script type="text/javascript">
	window.jQuery=window.Zepto;
 
 </script>
 </head>
 <body>

<jsp:include page="menuTab.jsp"></jsp:include>
<div class="home">
 <ul>
 
  <li class="a1"><a href="<%=path %>/user/leaveword/showLeaveword.do?type=2" title="最近漏话"><img src="<%=path%>/images/b02.jpg"></a></li>
  <li class="a2"><a href="<%=path %>/user/leaveword/leavewordMsg.do" title="漏话提醒"><img src="<%=path%>/images/b05.jpg"></a></li>
  <li class="a3"><a href="<%=path %>/user/callForward/showCallForward.do" title="呼转设置"><img src="<%=path%>/images/b01.jpg"></a></li>
  <li class="a4"><a href="<%=path %>/user/leaveword/showLeavewordTxt.do" title="我的留言"><img src="<%=path%>/images/b06.jpg"></a></li>
  <li class="a5"><a href="<%=path %>/user/call/callSecretary.do" title="人工秘书"><img src="<%=path%>/images/b11.jpg"></a></li>
  <li class="a6"><a href="<%=path %>/user/bindWechat/checkBind.do?openid=${login.wechatNo}" title="号码绑定"><img src="<%=path%>/images/b04.jpg"></a></li>
  <!--  
  <li class="a7"><a href="<%=path %>/user/wechatAction.do?method=activity" title="精彩活动"><img src="<%=path%>/images/b12.jpg"></a></li>
  -->
  
  <li class="a7"><a href="<%=path %>/user/politeOff/showPoliteOff.do" title="礼貌挂机"><img src="<%=path%>/images/b03.jpg"></a></li>
  
  <li class="a8"><a href="<%=path %>/user/busHandle/busHandle.do" title="业务办理"><img src="<%=path%>/images/b10.jpg"></a></li>
 	<%-- <li class="a9"><a href="<%=path %>/colorprinting/part/queryPartColorPrinting.do" title="彩印"><img src="<%=path%>/images/b09.jpg"></a></li> --%>
 	<li class="a9"><a href="<%=path %>/colorprinting/use/queryServer.do" title="彩印"><img src="<%=path%>/images/b09.jpg"></a></li>
<%--<li class="a9"><a href="<%=path %>/colorprinting/03fsd.html" title="彩印"><img src="<%=path%>/images/b09.jpg"></a></li>--%>  
 <!-- <li class="a3"><a href="" title="礼貌挂机(敬请期待)"><img src="<%=path%>/images/b03.jpg"></a></li>
  
  
 
  <li class="a7"><a href="#" title="签到"><img src="<%=path%>/images/b07.jpg"></a></li>
  <li class="a8"><a href="#" title="积分抽奖"><img src="<%=path%>/images/b08.jpg"></a></li>
  <li class="a9"><a href="#" title="彩印(敬请期待)"><img src="<%=path%>/images/b09.jpg"></a></li>
  -->
 </ul>
</div>


</body>
</html>