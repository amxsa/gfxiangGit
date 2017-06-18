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
<title>我的个性化彩印</title>
<link href="<%=path %>/css/caiyin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
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

 	var charLength=50;
	 
     function checkCharNum(){
	 
	 	var content=$("#content").val();
	 	
		if(charLength-content.length>0){
			
			$("#content_length").text(content.length);
		}else{
			$("#content").val(content.substr(0,charLength));
			$("#content_length").text(charLength);
		}
	 }
	 
 function del(state){
	if (window.confirm("确定要删除吗？")) {
		document.forms[0].action="<%=path%>/colorprinting/use/setColorPrinting.do?";
		$("#state").val(state);
		$("form").ajaxSubmit(function (responseResult) {
		if(responseResult!=''){
			var data = responseResult.split("|");
			if(data[0]=='true'){
				document.forms[0].action="<%=path%>/colorprinting/use/queryMyColorPrinting.do";
				checkForm();
			} else {
				alert(data[1]);
			}
		}
		});
	}
	return false;	
 }
 
 function modify(state){
 	document.forms[0].action="<%=path%>/colorprinting/use/setColorPrinting.do";
		$("#state").val(state);
		$("form").ajaxSubmit(function (responseResult) {
		if(responseResult!=''){
			var data = responseResult.split("|");
			if(data[0]=='true'){
				alert("修改成功");
				document.forms[0].action="<%=path%>/colorprinting/use/queryMyColorPrinting.do";
				checkForm();
			} else {
				alert(data[1]);
			}
		}
	});
 }
 
 //关闭彩印服务
 function closeServer(state){
 	document.forms[0].action="<%=path%>/colorprinting/use/setColorPrinting.do";
		$("#state").val(state);
		$("form").ajaxSubmit(function (responseResult) {
		if(responseResult!=''){
			var data = responseResult.split("|");
			if(data[0]=='true'){
				//alert("您已关闭彩印服务，欢迎下次使用！");
				document.forms[0].action="<%=path%>/colorprinting/use/queryMyColorPrinting.do";
				checkForm();
			} else {
				alert(data[1]);
			}
		}
		});
 }
 function checkForm(){
 	document.forms[0].submit();
 }
</script>
</head>
<body>
<div class="top"><a class="back" onClick="history.back()"><img src="<%=path %>/images/wechat/back.png" /></a><h2>我的彩印</h2><span><a href="<%=path%>/colorprinting/use/queryServer.do"><img src="<%=path %>/images/wechat/home.png"></a></span></div>

<div class="box">


 

  
  
  <div class="caiy01">
  <h2><img src="<%=path %>/images/wechat/pic.png"></h2>
   	<c:if test="${empty requestScope.po or  empty requestScope.po.content }">
					<p>
						
						尊敬的<em>${sessionScope.login.number }</em>用户，您尚未设置彩印
					</p>
			</c:if>
			<c:if test="${ not empty requestScope.po.content  }">
					<p>
						
						尊敬的<em>${sessionScope.login.number }</em>用户，您当前设置的彩印为：
					</p>
			</c:if>
			
  </div> 
  <c:if test="${ not empty requestScope.po.content  }">
			<div class="my_cy">
  					<p>${requestScope.po.content }</p>
				<div class="delcx"><a href="javaScript:del('N');">删除彩印</a></div>
				</div>
	</c:if>
  <form action="#" method="post">
  <input type="hidden" name="state" id="state"/>
  <div class="cy_cont">
   <h2>自定义彩印修改：</h2>
	<p><textarea name="content" id="content" cols="" rows=""  placeholder="" onkeypress="checkCharNum();" onkeydown="checkCharNum();" onkeyup="checkCharNum();"></textarea></p>
	<!-- <p class="ts">22/<em>52</em></p> -->
	<p class="ts"><font id="content_length">0</font>/<em>50</em></p>
  </div>
  
   <!-- <div class="fenx"><input name="" type="checkbox" value="">分享到朋友圈</div> -->
   <div class="bbut1"><a href="#" onClick="javaScript:modify('Y');" style="cursor:pointer;">修改设置</a></div>
   <c:if test="${ not empty requestScope.po.content  }">
   <div class="bbut1a"><a href="#" onClick="javaScript:showDtl2('D');" style="cursor:pointer;">关闭彩印服务</a></div>
   </c:if>
   </form>
</div>

<!--弹窗1-->
<div id="divMask" class="mask" style="display:none;">
 <div class="mask_box">
   <h2>自定义彩印修改</h2>
   <div class="content">
	<div class="cy_box" id="contentInfo"></div>
	<p align="center"><em>修改成功</em></p>
   </div>
	<div class="bbut2"><a href="javaScript:hideDtl();">确定</a></div>
 </div>
</div>

<!--弹窗2-->
<div id="divMask2" class="mask" style="display:none;">
 <div class="mask_box">
   <h2>关闭彩印服务</h2>
   <div class="content">
	<p align="center">确定关闭彩印服务吗？<br>点击确定后你还可以再次开通。</p>
   </div>
	<div class="bbut2"><a href="javaScript:closeServer('D');">确定</a></div>
 </div>
</div>

</body>
</html>



