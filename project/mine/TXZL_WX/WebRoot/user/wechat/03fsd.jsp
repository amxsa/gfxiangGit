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
</script>
</head>
<body>
<div class="top"><a class="back" onClick="history.back()"><img src="../../images/wechat/back.png" /></a><h2>分时段彩印</h2><span><a href="01home.html"><img src="../../images/wechat/home.png"></a></span></div>

<div class="box2">
  <div class="fs_box">
     <div class="fs_lt"><h3>如果我们能看懂一朵花所蕴含的奥秘，我们的整个生活就会发生变化。</h3><p>有效期：<em>8:00—12:00</em></p></div>
	 <div class="fs_rt"><a href="#" class="on"></a></div>
  </div>
   <div class="fs_box">
     <div class="fs_lt"><h3><a href="#" onClick="javaScript:showDtl();" style="cursor:pointer;">请编辑定义彩印<img src="../../images/wechat/edit.png"></a></h3><p>有效期：<em>12:00—15:00</em></p></div>
	 <div class="fs_rt"><a href="#" class="off"></a></div>
   </div>
   <div class="fs_box">
     <div class="fs_lt"><h3><a href="#" onClick="javaScript:showDtl();" style="cursor:pointer;">请编辑定义彩印<img src="../../images/wechat/edit.png"></a></h3><p>有效期：<em>15:00—00:00</em></p></div>
	 <div class="fs_rt"><a href="#" class="off"></a></div>
   </div>
</div>

<!--弹窗1-->
<div id="divMask" class="mask" style="display:none;">
 <div class="mask_box">
   <h2>编辑彩印</h2>
   <div class="content">
	<div class="cy_box2"><textarea name="" cols="" rows="" placeholder="">请编辑彩印</textarea></div>
   </div>
	<div class="bbut2"><a href="javaScript:hideDtl();" class="b1">取消</a><a href="javaScript:hideDtl();">确定</a></div>
  </div>
</div>


</body>
</html>
