<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0;" />
<meta name=​"format-detection" content=​"telephone=no"><meta charset="utf-8" />
<title>车路路盒子</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/alipay.css">
<style type="text/css">
body{background:#FEFEFE;}
.tswz{ font-size:180%;color:#FF0000; line-height:150%; text-align:center; padding:10px;}
</style>
<script language="javascript" type="text/javascript"
	src="<%=path%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	function GO(unitPrice){
		$("#unitPrice").val(unitPrice);
		document.forms[0].submit();
	}
</script>
</head>
<body>
<form action="<%=path %>/alipay/addTdCodeOrder.jsp?openid=${param.openid}" method="post">
<input type="hidden" name="unitPrice" value="${param.price }" id="unitPrice" />
<!--  
<div class="ad_img">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_01.jpg">
 <table width="100%">
  <tr>
    <td><a href="javaScript:GO(588);"><img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_02.jpg"></a></td>
    <td><a href="javaScript:GO(788);"><img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_03.jpg"></a></td>
  </tr>
</table>
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_08.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_09.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_10.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_11.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_12.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_13.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_14.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_15.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_16.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_17.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_18.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_19.jpg">
 <img src="<%=path%>/<%=path%>/images/apliay/BT20150121X01_20.jpg">
</div>
-->
 
<div class="layout1" style="margin-top: 6px;">
  <img src="<%=path%>/images/apliay/info_01.jpg">
  <img src="<%=path%>/images/apliay/info_02.jpg">
    <img src="<%=path%>/images/apliay/info_w1.jpg">
  <img src="<%=path%>/images/apliay/info_03.jpg">
  <img src="<%=path%>/images/apliay/info_04.jpg">
  <img src="<%=path%>/images/apliay/info_w2.jpg">
  <img src="<%=path%>/images/apliay/info_05.jpg">
  <img src="<%=path%>/images/apliay/info_06.jpg">
</div>


</form>
</body>
</html>
