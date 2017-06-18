<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主体框架</title>
<style type="text/css">
body{margin:0px;height:100%;overflow:hidden;}
.navPoint{cursor:pointer;} 
</style> 
<script>
function switchSysBar(){ 
var locate=location.href.replace('center.jsp','');
var ssrc=document.all("img1").src.replace(locate,'');
if (ssrc=="images/switch_left.gif")
{ 
document.all("img1").src="images/switch_right.gif";
document.all("frmTitle").style.display="none" 
} 
else
{ 
document.all("img1").src="images/switch_left.gif";
document.all("frmTitle").style.display="" 
} 
} 
</script>

</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
  <tr>
    <td width="200" height="100%" id=frmTitle name="fmTitle" align="center" valign="top">
	<iframe height="100%" width="200" src="left.jsp" border="0" frameborder="0" name="leftmenu" id="leftmenu" scrolling="auto">
	浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>	</td>
    <td width="10"  height="100%" valign="middle" style="background:#DADBDB;" onclick=switchSysBar()><span class="navPoint"><img src="images/switch_left.gif" name="img1" id=img1></span></td>
    <td align="center" height="100%" valign="top"><iframe height="100%" width="100%" border="0" scrolling="auto" frameborder="0" src="main.jsp" name="main" id="main"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe></td>
  </tr>
</table>
</body>
</html>
