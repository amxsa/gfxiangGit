<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
%>
<div class="banner scroll-wrapper" id="idContainer2" ontouchstart="touchStart(event)" ontouchmove="touchMove(event);" ontouchend="touchEnd(event);">
 <ul class="scroller" style="position:relative;left:0px;width:300%" id="idSlider2">
   <li style="width:-100.0%"><img src="<%=path %>/images/banner.jpg" alt="" /></li>
   <li style="width:-100.0%"><img src="<%=path %>/images/banner2.jpg" alt="" /></li>
   <li style="width:-100.0%"><img src="<%=path %>/images/banner3.jpg" alt="" /></li>
 </ul>        
 <ul class="new-banner-num new-tbl-type" id="idNum">
 </ul>
</div>								
<input type="hidden" value="3" id="activity"/>
<input type="hidden" value="3" id="crazy"/>
	<script type="text/javascript" src="<%=path %>/js/scroll.js"></script>
