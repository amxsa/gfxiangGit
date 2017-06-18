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
<title>LuLu盒子</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/alipay.css">
<style type="text/css">
body{background:#FEFEFE;}
.tswz{ font-size:180%;color:#FF0000; line-height:150%; text-align:center; padding:10px;}
</style>
</head>
<body>
<div class="ad_img">
 <img src="<%=path%>/images/apliay/BT20150121X01_01.jpg">
 <table width="100%">
  <tr>
    <td><a href="<%=path %>/service/OrderAlipayAction_getPreCityArea.do?price=588&openid=${param.openid}"><img src="<%=path%>/images/apliay/BT20150121X01_02_1.jpg"></a></td>
    <td><a href="<%=path %>/service/OrderAlipayAction_getPreCityArea.do?price=788&openid=${param.openid}"><img src="<%=path%>/images/apliay/BT20150121X01_03_1.jpg"></a></td>
  </tr>
</table>
<div class="tswz">购买请关注车主通公众号</div>
 <img src="<%=path%>/images/apliay/BT20150121X01_08_1.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_09.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_10.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_11.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_12.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_13.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_14.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_15.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_16.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_17.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_18.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_19.jpg">
 <img src="<%=path%>/images/apliay/BT20150121X01_20.jpg">
</div>
</body>
</html>
