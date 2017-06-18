<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
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
<link href="<%=path %>/css/caiyin.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
 function initDoc() {
 document.body.height = screen.height;
 document.getElementById("bodyDiv").style.height = screen.height;
 setHeight();
 }
 function showDtl() {
 document.getElementById("divMask").style.display="block";	
 }
 function hideDtl() {
 document.getElementById("divMask").style.display="none";	
 }
 function showDtl2() {
 document.getElementById("divMask2").style.display="block";	
 }
 function hideDtl2() {
 document.getElementById("divMask2").style.display="none";	
 }
</script>
</head>
<body>
<div class="top"><a class="back" onClick="history.back()"><img src="../../images/wechat/back.png" /></a><h2>我的彩印</h2><span><a href="01home.html"><img src="../../images/wechat/home.png"></a></span></div>

<div class="box">

  <div class="caiy01">
   <p><img src="../../images/wechat/pic.png">尊敬的<em>18926252143</em>用户，您尚未设置彩印。</p>
  </div>

  <div class="cy_cont">
   <h2>自定义彩印：</h2>
	<p><textarea name="" cols="" rows="" placeholder="">夏天就是不好，穷的时候我连西北风都没有喝。</textarea></p>
	<p class="ts">22/<em>52</em></p>
  </div>
  
   <div class="fenx"><input name="" type="checkbox" value="">分享到朋友圈</div>
   <div class="bbut1"><a href="#" onClick="javaScript:showDtl();" style="cursor:pointer;">修改设置</a></div>
   <div class="close_fw"><span><em>关闭服务</em></span></div>
   <div class="bbut1a"><a href="#" onClick="javaScript:showDtl2();" style="cursor:pointer;">关闭彩印服务</a></div>
</div>

<!--弹窗1-->
<div id="divMask" class="mask" style="display:none;">
 <div class="mask_box">
   <h2>自定义彩印</h2>
   <div class="content">
	<div class="cy_box">夏天就是不好，穷的时候我连西北风都没有喝。</div>
	<p align="center"><em>设置成功</em></p>
   </div>
	<div class="bbut2"><a href="02my.html">确定</a></div>
 </div>
</div>

<!--弹窗2-->
<div id="divMask2" class="mask" style="display:none;">
 <div class="mask_box">
   <h2>关闭彩印服务</h2>
   <div class="content">
	<p align="center">确定关闭彩印服务吗？<br>点击确定后你还可以再次开通。</p>
   </div>
	<div class="bbut2"><a href="javaScript:hideDtl2();">确定</a></div>
 </div>
</div>
</body>
</html>
