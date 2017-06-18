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
<title>我的彩印</title>
<link href="<%=path%>/css/caiyin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
<script type="text/javascript">
	 var reflushFlag=false;
	 function showDtl(flag) {
	 	
	 	if(flag){
	 		reflushFlag=true;
	 	}else{
	 		reflushFlag=false;
	 	}
	 	$("#divMask").show();
	 }
	
	 function hideDtl() {
	 	$("#divMask").hide();
	 	$("#msgResult").text('');
	 	
	 	if(reflushFlag){
	 		window.location.href='<%=path %>/user/colorPrinting/queryMyColorPrinting.do';
	 	}
	 }
	 var charLength=50;
	 
	 function checkCharNum(obj){
	 	var content=$("#content").val();
		if(charLength-content.length>0){
			
			$("#content_length").text(content.length);
		}else{
			$("#content").val(content.substr(0,charLength));
			$("#content_length").text(charLength);
		}
	 }
	 
     var ajaxFormOption = {
          type:"post",  //提交方式  
          dataType:"json", //数据类型  
          url:"<%=path%>/user/colorPrinting/saveOrUpdateMyColorPrinting.do",
          success: function (data) { //提交成功的回调函数  
          	if(data!=null){
          		if(data.state){
          			$("#msgResult").text(data.msg);
          			showDtl(true);
          		}else{
          			$("#msgResult").text(data.msg);
          			showDtl(false);
          		}
            }else{
              	$("#msgResult").text('操作异常,请稍后再试!');
            }
         }
     };
	 function GO(state){
	 	$("#state").val(state);
	 	var content=$("#content").val();
	   	$('#form1').ajaxSubmit(ajaxFormOption);
	   	//document.forms[0].submit();
	 }
	 
	 function del(state){
	 	$("#confirmMsgResult").text("您确定要删除？");
	 	$("#confirmState").val('N');
	 	$("#confirms").show();
	 }
	 
	 function cancel(state){
	 	$("#confirmMsgResult").text("您确定要取消？");
	 	$("#confirmState").val('D');
	 	$("#confirms").show();
	 }
	 
	 function confirmSure(confirmState){
	 	$("#confirms").hide();
	 	if(confirmState=='Y'){
	 		GO($("#confirmState").val());
	 	}else{
	 		$("#confirms").hidden();
	 		$("#confirmState").val('');
	 	}
	 }
	 
</script>

</head>
<body>
	<form action="<%=path%>/user/colorPrinting/saveOrUpdateMyColorPrinting.do"
		method="post" id="form1">
		<input type="hidden" name="state" id="state"  />
		<div class="top">
			<a class="back" onclick="history.back();"><img src="<%=path %>/images/back.png" /></a>
			<h2>我的彩印</h2>
			<span><a href="<%=path%>/user/index.jsp"><img src="<%=path %>/images/home.png"></a></span>
			</span>
		</div>
		<div class="box">
			<c:if test="${empty requestScope.po or  empty requestScope.po.content }">
				<div class="caiy01">
					<p>
						尊敬的<em>${sessionScope.login.number }</em>用户，您尚未设置个性化彩印。
					</p>
				</div>
			</c:if>
			<c:if test="${ not empty requestScope.po.content  }">
				<div class="caiy02">
					<p>
						尊敬的<em>${sessionScope.login.number }</em>用户<span>(<a href="javaScript:cancel('D');" class="red">取消服务</a>)</span>，您当前设置的个性化彩印为：
					</p>
					<h2>${requestScope.po.content }</h2>
					<div class="bbut2">
						<a href="javaScript:del('N');">删除彩印</a>
					</div>
				</div>
			</c:if>

			<h1 class="tith">自定义彩印</h1>

			<div class="cy_cont">
				
					<p>
						<textarea name="content" id="content" cols="" rows="1" placeholder="" onkeypress="checkCharNum();" onkeydown="checkCharNum();" onkeyup="checkCharNum();"></textarea>
					</p>
					<p class="ts">
						<font id="content_length">0</font>/<em>50</em>
					</p>
				
				
			</div>
			<!--  
			<div class="fenx">
				<input name="" type="checkbox" value="">分享到朋友圈
			</div>
			-->
			<div class="bbut1">
				<a href="#" onclick="GO('Y');" style="cursor:pointer;">保存/修改</a>
				
			</div>
		</div>

		

		<div id="divMask" class="mask" style="display:none;">
			<div class="mask_box">
				<h2>设置结果</h2>
				<div class="content">
					<!--  
						<p align="center">
							<strong id="successContent"></strong>
						</p>
					-->
					<p  >
						<em id="msgResult">修改成功</em>
					</p>
				</div>
				<div class="bbut2">
					<a href="javaScript:hideDtl();">确定</a>
				</div>
			</div>
		</div>
		
		
		<div id="confirms" class="mask" style="display:none;">
			<input type="hidden" name="confirmState" id="confirmState" />
			<div class="mask_box">
				<h2>操作提示</h2>
				<div class="content">
					<!--  
						<p align="center">
							<strong id="successContent"></strong>
						</p>
					-->
					<p  >
						<em id="confirmMsgResult">您确定删除？</em>
					</p>
				</div>
				<div class="bbut2">
					<a href="javaScript:confirmSure('Y');">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javaScript:confirmSure('N');">取消</a>
				</div>
			</div>
		</div>
		
	</form>


</body>
</html>

