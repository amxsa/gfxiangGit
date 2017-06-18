<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>分时段彩印</title>
<link href="<%=path %>/css/caiyin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
<script type="text/javascript" >
 function initDoc() {
 	document.body.height = screen.height;
 	document.getElementById("bodyDiv").style.height = screen.height;
 	setHeight();
 }
 //设置成功后
 function addSuccess(){
 	document.getElementById("divMask3").style.display="block";
 }
 function showDtl() {
 	
 	document.getElementById("divMask").style.display="block";	
 }
 //进入更新页面
 function preUpdate(id,content,startTime,endTime) {
 	$("#partcontent").val(content);
 	$("#cpid").val(id);
 	document.getElementById("divMask").style.display="block";
 }
 //编辑彩印内容后关闭页面
 var content;
 function editContent() {
 	
 	content=$("#partcontent").val();
	if (content==""||content==undefined||content==null) {
		alert("彩印内容不可为空");
		return false;
	}
	$("#editcp").text(content);
	var id=$("#cpid").val();
	if (id!=null&&id!=undefined&&id!="") {//存在id就是更新操作(更新内容)
		$.ajax({
			url:"<%=path%>/colorprinting/part/setColorPrinting.do",
			data:{"id":id,"content":content,"state":"UC"},
			type:"post",
			success:function(responseResult){
				if(responseResult!=''){
					var data = responseResult.split("|");
					if(data[0]=='true'){
						alert(data[1]);
						document.forms[0].action="<%=path%>/colorprinting/part/queryPartColorPrinting.do";
						checkForm();
					} else {
						alert(data[1]);
					}
				}
			},
			dataType:"text"
		});
	}
	
 	document.getElementById("divMask").style.display="none";	
 }
 function hideDtl(){
 	document.getElementById("divMask").style.display="none";
 }
 
 //进入更改时间页面
 function showDtl2(id,isLoop) {
	
	$("#startHour").empty();
	$("#endHour").empty();
	$("#startMinute").empty();
	$("#endMinute").empty();
 	document.getElementById("divMask2").style.display="block";
 	$("#cpid1").val(id);
 	
 	if (isLoop=="Y") {
		$("#loop").prop("checked",true);
	}
	if (isLoop=="N") {
		$("#loop").prop("checked",false);
	}
 	for(i=0;i<=23;i++){
 		if (i<10){
			i="0"+i;
 		}
 		$("<option value='"+i+"'>"+i+"</option>").appendTo("#startHour");
 		$("<option value='"+i+"'>"+i+"</option>").appendTo("#endHour");
 	}	
 	for(j=0;j<=55;j+=5){
 		if (j<10){
			j="0"+j;
 		}
 		$("<option value='"+j+"'>"+j+"</option>").appendTo("#startMinute");
 		$("<option value='"+j+"'>"+j+"</option>").appendTo("#endMinute");
 		j=parseInt(j);
 	}
 	if (id!=null) {//如果更改时间  默认选中当前小时
 		var timeid="time"+id;
		var nowtime=$("#"+timeid).text();
		var arr=nowtime.split("-");
		var time1=arr[0].split(":");
		var time2=arr[1].split(":");
		shour=parseInt(time1[0]);
		sminute=parseInt(time1[1]);
		ehour=parseInt(time2[0]);
		eminute=parseInt(time2[1]);
		
		var a=sminute%5;
		if (sminute<5) {//0 1 2 3 4
			if (a>0) {
				sminute="0"+(parseInt(sminute/5)+5);
				$("#startMinute option[value='"+sminute+"']").prop("selected",true);
			}else{
				sminute="0"+sminute;
				$("#startMinute option[value='"+sminute+"']").prop("selected",true);
			}
		}else if (sminute==5) {
			$("#startMinute option[value='05']").prop("selected",true);
		}else{
			if (a>0) {
				sminute=parseInt(sminute/5)*5+5;
				$("#startMinute option[value='"+sminute+"']").prop("selected",true);
			}else{
				$("#startMinute option[value='"+sminute+"']").prop("selected",true);
			}
		}
		
		var b=eminute%5;
		if (eminute<5) {//0 1 2 3 4
			if (a>0) {
				eminute="0"+(parseInt(eminute/5)+5);
				$("#endMinute option[value='"+eminute+"']").prop("selected",true);
			}else{
				eminute="0"+eminute;
				$("#endMinute option[value='"+eminute+"']").prop("selected",true);
			}
		}else if (eminute==5) {
			$("#endMinute option[value='05']").prop("selected",true);
		}else{
			if (a>0) {
				eminute=parseInt(eminute/5)*5+5;
				$("#endMinute option[value='"+eminute+"']").prop("selected",true);
			}else{
				$("#endMinute option[value='"+eminute+"']").prop("selected",true);
			}
		}
		
		if (shour<10) {
			shour="0"+shour;
			$("#startHour option[value='"+shour+"']").prop("selected",true);
		}else{
			$("#startHour option[value='"+shour+"']").prop("selected",true);
		}
		if (ehour<10) {
			ehour="0"+ehour;
			$("#endHour option[value='"+ehour+"']").prop("selected",true);
		}else{
			$("#endHour option[value='"+ehour+"']").prop("selected",true);
		}
	}else{
		var lasttime=$(".ptime").last().text();
		if (lasttime!=null&&lasttime!=undefined&&lasttime!="") {//有数据新增时 默认从最大的结束时间开始
			var arr=lasttime.split("-");
			var lasthour=arr[1].split(":")[0];
			lasthour=parseInt(lasthour);
			
			if (lasthour<7) {
				lasthour1="0"+(lasthour+1);
				lasthour2="0"+(lasthour+3);
				$("#startHour option[value='"+lasthour1+"']").prop("selected",true);
				$("#endHour option[value='"+lasthour2+"']").prop("selected",true);
			}else if (lasthour==7||lasthour==8) {
				lasthour1="0"+(lasthour+1);
				lasthour2=lasthour+3;
				$("#startHour option[value='"+lasthour1+"']").prop("selected",true);
				$("#endHour option[value='"+lasthour2+"']").prop("selected",true);
			}else if (lasthour<21&&lasthour>=9) {
				lasthour1=lasthour+1;
				lasthour2=lasthour+3;
				$("#startHour option[value='"+lasthour1+"']").prop("selected",true);
				$("#endHour option[value='"+lasthour2+"']").prop("selected",true);
			}else{
				$("#startHour option[value='"+lasthour+"']").prop("selected",true);
				$("#endHour option[value='23']").prop("selected",true);
				$("#endMinute option[value='55']").prop("selected",true);
			}
		}else{//无数据时  默认使用当前时间
			var nowtime=$("#time").text();
			var arr=nowtime.split("-");
			var time1=arr[0].split(":");
			var time2=arr[1].split(":");
			shour=parseInt(time1[0]);
			sminute=parseInt(time1[1]);
			ehour=parseInt(time2[0]);
			eminute=parseInt(time2[1]);
			
			var a=sminute%5;
			if (sminute<5) {//0 1 2 3 4
				if (a>0) {
					sminute="0"+(parseInt(sminute/5)+5);
					$("#startMinute option[value='"+sminute+"']").prop("selected",true);
				}else{
					sminute="0"+sminute;
					$("#startMinute option[value='"+sminute+"']").prop("selected",true);
				}
			}else if (sminute==5) {
				$("#startMinute option[value='05']").prop("selected",true);
			}else{
				if (a>0) {
					sminute=parseInt(sminute/5)*5+5;
					$("#startMinute option[value='"+sminute+"']").prop("selected",true);
				}else{
					$("#startMinute option[value='"+sminute+"']").prop("selected",true);
				}
			}
			
			var b=eminute%5;
			if (eminute<5) {//0 1 2 3 4
				if (a>0) {
					eminute="0"+(parseInt(eminute/5)+5);
					$("#endMinute option[value='"+eminute+"']").prop("selected",true);
				}else{
					eminute="0"+eminute;
					$("#endMinute option[value='"+eminute+"']").prop("selected",true);
				}
			}else if (eminute==5) {
				$("#endMinute option[value='05']").prop("selected",true);
			}else{
				if (a>0) {
					eminute=parseInt(eminute/5)*5+5;
					$("#endMinute option[value='"+eminute+"']").prop("selected",true);
				}else{
					$("#endMinute option[value='"+eminute+"']").prop("selected",true);
				}
			}
			
			if (shour<10) {
				shour="0"+shour;
				$("#startHour option[value='"+shour+"']").prop("selected",true);
			}else{
				$("#startHour option[value='"+shour+"']").prop("selected",true);
			}
			if (ehour<10) {
				ehour="0"+ehour;
				$("#endHour option[value='"+ehour+"']").prop("selected",true);
			}else{
				$("#endHour option[value='"+ehour+"']").prop("selected",true);
			}
		}
	}
 }
 	function cancelTime(){
 		$("#divMask2").hide();
 	}
 
 //更改时间完成后
 var startHour;
 var startMinute;
 var endHour;
 var endMinute;
 var isloop="N";
 function editTime() {
 	if ($("#loop").prop("checked")==true) {
		isloop="Y";
	}else{
		isloop="N";
	}
 	startHour=$("#startHour").prop("selected",true).val();
 	startMinute=$("#startMinute").prop("selected",true).val();
 	endHour=$("#endHour").prop("selected",true).val();
 	endMinute=$("#endMinute").prop("selected",true).val();
 	var shour=parseInt(startHour);
 	var sminute=parseInt(startMinute);
 	var ehour=parseInt(endHour);
 	var eminute=parseInt(endMinute);
 	if (shour>ehour) {
		alert("开始时间不能大于结束时间");
		return false;
	}
	if (shour==ehour) {
		if (sminute>eminute) {
			alert("开始时间不能大于结束时间");
			return false;
		}
		if (eminute-sminute<10) {
			alert("时间间隔需10分钟以上");
			return false;
		}
	}
	var id=$("#cpid1").val();
	if (id!=null&&id!=undefined&&id!="") {//若存在id 就是更新操作
		$.ajax({
			url:"<%=path%>/colorprinting/part/setColorPrinting.do",
			data:{"id":id,"sHour":startHour,"sMinute":startMinute,"eHour":endHour,"eMinute":endMinute,"isloop":isloop,"state":"UT"},
			type:"post",
			success:function(responseResult){
				if(responseResult!=''){
					var data = responseResult.split("|");
					if(data[0]=='true'){
						alert(data[1]);
						document.forms[0].action="<%=path%>/colorprinting/part/queryPartColorPrinting.do";
						checkForm();
					} else {
						alert(data[1]);
					}
				}
			},
			dataType:"text"
		});
	}else{
	 	var time=startHour+":"+startMinute+"-"+endHour+":"+endMinute;
	 	$("#time").text(time);
	}
	 	document.getElementById("divMask2").style.display="none";	
 }
 
 //关闭时段彩印（直接删除）
 function closePart(id){
 	if (confirm("是否删除？")) {
		document.forms[0].action="<%=path%>/colorprinting/part/setColorPrinting.do";
	 	$("#state").val("N");
	 	$("#id").val(id);
	 	$("form").ajaxSubmit(function (responseResult) {
			if(responseResult!=''){
				var data = responseResult.split("|");
				if(data[0]=='true'){
					alert(data[1]);
					document.forms[0].action="<%=path%>/colorprinting/part/queryPartColorPrinting.do";
					checkForm();
				} else {
					alert(data[1]);
				}
			}
		});
	}else{
		return false;
	}
 	
 	/* $("#partId").removeClass("on");
 	$("#partId").addClass("off"); */
 }
 
 function doFocus(){
 	$("#content").val("");
 }

 function doSubmit(){//提交表单
 	var onsize=$(".on").length;
 	if (onsize>=4) {
		alert("最多只能添加4个时段");
		return false;
	}
	document.forms[0].action="<%=path%>/colorprinting/part/setColorPrinting.do";
	var time=$("#time").text();
	var arr=time.split("-");
	var time1=arr[0].split(":");
	var time2=arr[1].split(":");
	startHour=time1[0];
	startMinute=time1[1];
	endHour=time2[0];
	endMinute=time2[1];
	$("#isloop").val(isloop);
	$("#sHour").val(startHour);
	$("#eHour").val(endHour);
	$("#sMinute").val(startMinute);
	$("#eMinute").val(endMinute);
	$("#content").val(content);
	$("#state").val("Y");
	$("form").ajaxSubmit(function (responseResult) {
		if(responseResult!=''){
			var data = responseResult.split("|");
			if(data[0]=='true'){
				alert(data[1]);
				document.forms[0].action="<%=path%>/colorprinting/part/queryPartColorPrinting.do?";
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
<div class="top"><a class="back" onClick="history.back()"><img src="<%=path %>/images/wechat/back.png" /></a><h2>分时段彩印</h2><span><a href="<%=path%>/colorprinting/use/queryServer.do"><img src="<%=path %>/images/wechat/home.png"></a></span></div>
<c:forEach items="${requestScope.list }" var="cp">
	<div class="box2">
	   <div class="fs_box">
	     <div class="fs_lt"><h3><a href="#"  style="cursor:pointer;">${cp.content }<img onclick="preUpdate('${cp.id}','${cp.content }')" src="<%=path %>/images/wechat/edit.png"></a></h3><p>有效期：
	     <em onclick="showDtl2('${cp.id}','${cp.isLoop }')">
		     <span class="ptime" id="time${cp.id }">
		      	<fmt:formatDate value="${cp.startTime }" pattern="HH:mm" />-<fmt:formatDate value="${cp.endTime }" pattern="HH:mm"/>
		     </span>
	      </em></p></div>
	      <div class="fs_rt"><a href="#" id="${cp.id }" onclick="javaScript:closePart('${cp.id}')" class="del"><img src="<%=path %>/images/wechat/del.png"></a></div>
	   </div>
	</div>
</c:forEach>

<form action="" method="post">
<input type="hidden" name="sHour" id="sHour"/>
<input type="hidden" name="eHour" id="eHour"/>
<input type="hidden" name="sMinute" id="sMinute"/>
<input type="hidden" name="eMinute" id="eMinute"/>
<input type="hidden" name="content" id="content"/>
<input type="hidden" name="isloop" id="isloop"/>
<input type="hidden" name="state" id="state"/>
<input type="hidden" name="id" id="id"/>
<div class="box2">
   <div class="fs_box">
     <div class="fs_lt"><h3><a href="#" onClick="javaScript:showDtl();" style="cursor:pointer;"><span id="editcp">请编辑自定义彩印</span><img src="<%=path %>/images/wechat/edit.png"></a></h3><p>有效期：
     	<em onClick="javaScript:showDtl2();">
     		<span id="time">${sTime }-${eTime }</span>
     	</em>
     </p></div>
      <div class="fs_rt"><a href="javaScript:doSubmit()" class="add"><img src="<%=path %>/images/wechat/add.png"></a></div>
   </div>
</div>
</form>
<!--弹窗1-->
<div id="divMask" class="mask" style="display:none;">
<input  type="hidden" id="cpid" name="cpid"/>
 <div class="mask_box">
   <h2>编辑彩印</h2>
   <div class="content">
	<div class="cy_box2"><textarea name="partcontent" id="partcontent" cols="" rows="" placeholder="" ></textarea></div>
   </div>
	<div class="bbut2"><a href="javaScript:hideDtl();" class="b1">取消</a><a href="javaScript:editContent(); ">确定</a></div>
  </div>
</div>

<!--弹窗2-->
<div id="divMask2" class="mask" style="display:none;">
 <div class="mask_box">
   <h2>设置彩印时间</h2>
   <div class="content">
   <input  type="hidden" id="cpid1" name="cpid1"/>
      <div class="tctime">
	    <p>
    		<label>开始时间：</label>
    		<select name="startHour" id="startHour" ></select>时
    		<select name="startMinute" id="startMinute"></select>分
	    </p>
		<p>
			<label>结束时间：</label>
			<select name="endHour" id="endHour"></select>时
			<select name="endMinute" id="endMinute"></select>分
		</p>
	  </div>
	  <div class="cf"><input name="loop" id="loop"  type="checkbox">是否每天重复</div>
   </div>
	<div class="bbut2"><a href="javaScript:cancelTime();">取消</a>&nbsp;&nbsp;<a href="javaScript:editTime();">确定</a></div>
 </div>
</div>


<!-- 弹窗3 -->
<div id="divMask3" class="mask" style="display:none;">
 	<div class="mask_box"><p align="center"><em>修改成功</em></p>
	<div class="bbut2"><a href="javaScript:showSuccess();">确定</a></div>
 </div>
</div>
</body>
</html>
