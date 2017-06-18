<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="cn.cellcom.common.date.DateTool"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
	int hour = DateTool.getHour(new Date());
	List hours = new ArrayList();
	for(int i=hour;i<=23;i++){
		if(i<10)
			hours.add("0"+i);
		else	
			hours.add(i);
	}
	pageContext.setAttribute("hours", hours);
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
<title>礼貌挂机设置</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<link type="text/css" href="<%=path%>/css/banner.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/zepto.min.js"></script>

<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=path%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	window.jQuery = window.Zepto;
</script>
<script type="text/javascript" language="javaScript">
	function checkForm(){
		document.forms[0].submit();
	}
	function checkState(obj){
		var state = $('input[name="state"]:checked').val();
		
		if(obj=='state2'){
			$("#state2").attr("checked","true");
			$("#state1").attr("checked","");
			$("#stateOn").attr("className","");
			$("#stateOff").attr("className","select");
			$('input[name="state"][value="N"]').attr("checked",'checked');
		}else{
			$("#state1").attr("checked","true");
			$("#state2").attr("checked","");
			$("#stateOn").attr("className","select");
			$("#stateOff").attr("className","");
			$('input[name="state"][value="Y"]').attr("checked",'checked');
		}
	}
	function del(){
		document.forms[0].action="<%=path %>/user/politeOff/cancelPoliteOff.do";
		document.forms[0].submit();
	}
	 function showDtl1() {
	 	document.getElementById("divMask1").style.display="block";
	 }
	function showDtl2() {
		 document.getElementById("divMask2").style.display="block";	
	}
	 function hideDtl1() {
	 	document.getElementById("divMask1").style.display="none";
	 	$("#diffTime").val($("#minutes1").val());
	 }
	  function hideDtl2() {
	 	document.getElementById("divMask2").style.display="none";
	 	$("#expireTime").val($("#hours2").val()+":"+$("#minutes2").val());
	 }
	
	
	
	
</script>
</head>

<body>
	<div class="top">
		<a class="back" onclick="javaScript:window.location.href='<%=path %>/user/politeOff/showPoliteOff.do'"><img
			src="<%=path %>/images/back.png" /> </a>
		<h2>礼貌挂机设置</h2>
		<span><a href="<%=path%>/user/index.jsp"><img
				src="<%=path%>/images/home.png"> </a> </span>
	</div>
	<form action="<%=path %>/user/politeOff/setPoliteOff.do" method="post">
		<input type="hidden" name="type" value="${requestScope.type }" />
		
		<div class="gj_det">
			<ul>
				<li class="a1"><img src="<%=path %>/images/wechat/B_${requestScope.type }.jpg"></li>
				<li class="a2"><h2>
						当前场景：<i>${requestScope.name}</i>
					</h2></li>
				<li class="a3"><a href="javaScript:showDtl('<%=path %>/wav/MH34${requestScope.type}.wav');" class="test"></a></li> 
			</ul>
		</div>
		</div>
		<div class="gj_det2">
			<table width="100%" border="0" cellpadding="6" cellspacing="6"
				class="table">
				
				<tr>
				    <td>有效时段</td>
				    <td align="center"><input name="diffTime" id="diffTime" type="text" value="" onClick="javaScript:showDtl1();">分钟</td>
				 </tr>
				 <!--  
				<tr>
					<td>开启语音留言：</td>
					<td><ul class="lb_kg" >
							<li ${po.state=='Y' or empty po.state ?'class="select"':'class=""' } id="stateOn" onclick="checkState('state1');">开启<em></em><input type="radio" value="Y"
								name="state"  checked="checked" id="state1" />
							</li>
							<li ${po.state=='N'?'class="select"':'class=""' }  id="stateOff" onclick="checkState('state2');">关闭<em></em><input type="radio" value="N" name="state" id="state2"   />
							</li>
						</ul></td>
				</tr>
				-->
				<input type="hidden" name="state" value="Y" />
			</table>
			<!--  
		<p>如果通信助理用户，开启语音留言后，在您无法接听电话时，主叫可以通过 电话直接给您留言，您可以再我的留言中收听别人给您的留言。</p>
		-->
			<div class="hzbut1">
				<input type="button" value="保存设置" class="btn" id="btn" onclick="checkForm();" />
				<c:if test="${not empty po }">
					<input type="button" value="删除设置" class="btn" id="btn" onclick="del();" />
				</c:if>
			</div>
		</div>
		<script type="text/javascript">
		
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
		
		<div id="divMask1" class="mask" style="display:none;">
		 <div class="mask_box">
		  <div class="time">
		    <h2>时段选择</h2>
			
			
			<select name="minutes" id="minutes1">
			 <option value="5">5分钟</option>
			 <option value="10">10分钟</option>
			 <option value="20">20分钟</option>
			 <option value="30">30分钟</option>
			 <option value="40">40分钟</option>
			 <option value="50">50分钟</option>
			 <option value="60">1小时</option>
			 <option value="90">1.5小时</option>
			 <option value="120">2小时</option>
			 <option value="180">3小时</option>
			 <option value="240">4小时</option>
			 <option value="300">5小时</option>
			 <option value="360">6小时</option>
			</select>
			<div class="timebut"><a class="btn" href="javaScript:hideDtl1();" >确定</a></div>
		   </div>
		 </div>
		</div>
		
		<div id="divMask2" class="mask" style="display:none;">
		 <div class="mask_box">
		  <div class="time">
		    <h2>开始时间设置</h2>
			小时:
		    <select name="hours" id="hours2" >
				<c:forEach var="hours2" items="${hours }">
		    		 <option value="${hours2 }">${hours2 }</option>
		    	</c:forEach>
			</select>
			分钟:
			<select name="minutes" id="minutes2">
			 <option value="00">00</option>
			 <option value="05">05</option>
			 <option value="10">10</option>
			 <option value="15">15</option>
			 <option value="20">20</option>
			 <option value="25">25</option>
			 <option value="30">30</option>
			 <option value="35">35</option>
			 <option value="40">40</option>
			 <option value="45">45</option>
			 <option value="50">50</option>
			 <option value="55">55</option>
			
			</select>
			<div class="timebut"><a class="btn" href="javaScript:hideDtl2();" >确定</a></div>
		   </div>
		 </div>
		</div>
		
	</form>
</body>

</html>