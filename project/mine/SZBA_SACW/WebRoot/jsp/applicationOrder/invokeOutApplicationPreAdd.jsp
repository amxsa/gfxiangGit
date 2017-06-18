<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/uploadify/uploadify.css"/>

<script type="text/javascript" src="<%=path%>/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload.js"></script>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/tempForm.js"></script>
</head>
<body>
<c:if test="${fn:length(detail.excessPros) > 0}">
	<c:redirect url="/property/queryForPage.do?viewName=applyInvokeOutPropertyList"/>
</c:if>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物调用申请</span></div>
<div class="content">
<div class="detal"><h2 class="h4tit">财物调用申请</h2></div>
 <div class="toptit"><h1 class="h1tit">财物列表</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo"></span></h1></div>
 <form action="" id="">
 
 <table width="100%" cellpadding="0" cellspacing="1" border="0" class="table">
  <tr>
    <th>序号</th>
    <th>财物编号</th>
	<th>财物名称</th>
	<th>财物数量</th>
	<th>计量单位</th>
	<th>财物类别</th>
	<th>财物特征</th>    
	<th>备注</th> 
	<th>财物详情</th>    
  </tr>
  <c:forEach items="${detail.properties }" var="p" varStatus="sta">
  <tr>
    <td>${sta.count }</td>
    <td>${p.proNo }</td>
	<td>${p.proName }</td>
	<td>
		<c:if test="${p.proQuantity%1.0==0}">
			<fmt:formatNumber type="number" value="${p.proQuantity }" maxFractionDigits="0"/>
		</c:if>
		<c:if test="${p.proQuantity %1.0!=0}">${p.proQuantity }
		</c:if>
	</td>
	<td>${p.proUnit }</td>
	<td>${mapping:mappingProType(p.proType) }</td>
	<td>${p.proCharacteristic }</td>
	<td>${p.proRemark }</td>
	<input type="hidden" value="${p.proStatus }" name="proStatus"/>
	<input type="hidden" value="${p.proType }" name="proType"/>
	<input type="hidden" value="${p.actualPosition }" name="actualPosition"/>
	<input type="hidden" value="${p.proId }" name="proId"/>
	<input type="hidden" value="${p.proNo }" name="proNo"/>
	<td>
	<span class="tdbut">
	<a href="<%=path%>/jsp/propertyInfo/watchProperty.jsp?proId=${p.proId }" class="watchDetail">查看</a>
	</span>
	<span class="tdbut"><a href="javascript:void(0)" class="removeElec">移除</a></span>
	</td>
  </tr>
  </c:forEach>
 </table>
 <input type="hidden" value="" name="proJson"/>
  <ul class="list02a w50">
  <input type="hidden" name="applicationNo"/>
   <li><label>申请单位：</label>${department.name }</li>
   <li><label>申请人：</label>${account.name}</li>
   <li class="w50"><label>案件编号：</label>${detail.tCase.jzcaseID }</li><input type="hidden" name="jzcaseID" value="${detail.tCase.jzcaseID }"/><input type="hidden" name="caseId" value="${detail.tCase.caseID }"/>
   <li class="w50"><label>案件名称：</label>${detail.tCase.caseName }</li><input type="hidden" name="caseName" value="${detail.tCase.caseName }"/>

   <input type="hidden" name="dpResultStr" value=""/>
   <li class="w100"><label>借调期限：</label>
   <input name="planStartDate" type="text" style="width:150px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  /><span class="need-right">*</span><i style="float:left;margin-left:5px;">至</i>
	<input name="planStorageDate" type="text" style="width:150px;margin-left:5px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /><span class="need-right">*</span>
	<label>时长：</label><input name="duration" readonly="readonly" type="text" style="width:60px;margin-left:5px;" /><i style="float:left">天</i>
   		</li>
   <li class="w100" style="display:block;height:375px;"> 	
   <label>调用原因：</label>
	   	<div style="width:85%;float:left;height: 100%;">
			<script id="container" name="applyBasis" type="text/plain"></script>
		</div>
		<div style="float:left;height: 100%;position: relative;padding-left: 5px;">
			<input type="button" value="模板" style="width:50px;position: absolute;bottom: 0px;" id="selTemp"/>
		</div>
	</li>
</ul>

<div class="toptit">
	<h1 class="h1tit">档案资料</h1>
</div>
<ul class="ws_list" id="attachmentList">
</ul>
<div id="attachmentQuene"></div>
<input type="file" name="attachment" id="attachmentApplication" multiple="true" />

 <div class="dbut"><a href="javascript:void(0)" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:void(0)" class="goBackByQuery">返回</a></div>
 </form>
</div>
	
<script type="text/javascript">
var ue;
$(document).ready(function(){
	ue = UE.getEditor('container',{
		toolbars: [ ['fontfamily', 'fontsize','paragraph','forecolor','backcolor','emotion',
		             'spechars','justifyleft','justifyright','justifycenter','justifyjustify',
		             'insertorderedlist','insertunorderedlist','rowspacingtop','rowspacingbottom', 'imageleft','imageright',
		             'lineheight','touppercase','tolowercase']],
		autoHeightEnabled:false,
		initialFrameHeight:270
	});
	
	//选择模板
	$('#selTemp').on('click',function(){
		var path=sysPath+'/templet/queryTemplet.do?contentType=36';
		art.dialog.data('contentType',36);  //调用申请呈批表模版 36
		art.dialog.open(path, {
		    title: '选择模板',
		    width: 650,
		    height:350,
		    top:10,
		    ok: function () {
		    	var iframe = this.iframe.contentWindow;
		    	var table_hover = iframe.document.getElementById('table_hover');
		    	var checkedLen=$(table_hover).find('input[name="id"]:checked').length;
		    	if(checkedLen==0) { 
		    		alert('至少选择一个模板');
		    		return false;
		    	}
		    	var checkId=$(table_hover).find('input[name="id"]:checked').val();
		    	$.ajax({ 
		    		url:sysPath+'/templet/queryById.do?id='+checkId,
		    		async : false,
		    		type:"post", 
		    		dataType:"json",
		    		success:function(data){
		    			if(data.state=='S'){
		    				ue.setContent(data.obj.content);
		    			}
		    		}
		    	});
		    },
		    cancel: true
		});
	});
	
	$(".goBackByQuery").click(function(){
		Common.tempForm.initData('${refererUrl}', '${conditions}');
	});
	
	$("input[name='planStartDate']").val(formatDate(new Date()));
	$("input[name='planStorageDate']").blur(function(){
		var planStorageDateStr = this.value;
		var startDateStr = $("input[name='planStartDate']").val();
		
		if(planStorageDateStr != null && planStorageDateStr !== ''){
			
			 var planStorageDate = new Date(planStorageDateStr.replace(/-/g,"/"));
			 var startDate = new Date(startDateStr.replace(/-/g,"/"));
			 var d = (planStorageDate.getTime()-startDate.getTime())/(1000*60*60*24);  
			 if(d < 0){
				 alert("结束时间不能比开始时间大");
				 this.value = "";
				 $("input[name='duration']").val("");
				 return;
			 }
			 $("input[name='duration']").val(d);
		}else{
			 $("input[name='duration']").val("");
		}
		
		$("input[name='duration']").val();
	});
	$.ajax({
		url: sysPath + "/applicationOrder/getApplicationNo.do",
		type:"post",
		success:function(backData){
			$(".applicationNo").text(backData);
			$("input[name='applicationNo']").val(backData);
		},
		error:function(){
			
		}
	});
});
function sendData(){
	if($("input[name='planStorageDate']").val() === ''){
		alert("借调结束时间不能为空！");
		return;
	}
	if($("input[name='planStartDate']").val() === ''){
		alert("借调开始时间不能为空！");
		return;
	}
	
	var proIds = "[";
	
	var proTr = $(".table tr");
	var temp = "";
	var flag = true;
	$(proTr).each(function(i){
		var proId = $(this).find("input[name='proId']").val();
		var proNo = $(this).find("input[name='proNo']").val();
		var proStatus = $(this).find("input[name='proStatus']").val();
		var proType = $(this).find("input[name='proType']").val();
		var actualPosition = $(this).find("input[name='actualPosition']").val();
		if(proId != null && proId != ''){
			proIds += "{'proId':'"+proId+"', 'proNo':'"+proNo+"','proStatus':'"+proStatus+"', 'proType':'"+proType+"', 'actualPosition':'"+actualPosition+"'},";  //此处将财物状态带上，是为了在财物调用和处置流程时作为一个判断标识
			
			if(temp !== "" && actualPosition !== temp){
				alert("需要调用的财物，不是同个仓库的");
				flag = false;
				return false;
			}else{
				temp = actualPosition;
			}
		}
		
		
	});
	
	if(!flag){
		return;
	}
	
	var dpResultStr = "";
	if(temp == 'ZCK'){
		dpResultStr = "{'disposal':{'code':'', 'description':''},'procedure':{'code':'PROCEDURE004', 'description':'财物调用出暂存库流程'}}";
	}else{
		dpResultStr = "{'disposal':{'code':'', 'description':''},'procedure':{'code':'PROCEDURE005', 'description':'财物调用出中心库流程'}}";
	}
	$(":hidden[name='dpResultStr']").val(dpResultStr);
	
	proIds = proIds.substring(0, proIds.length-1);
	proIds += "]";
	$(":hidden[name='proJson']").val(proIds);
	
	disableBtn("#submitBtn");
	$.ajax({
		url:sysPath+"/applicationOrder/doApplyNew.do",
		data:$("form:first").serialize(),
		type:"post",
		dataType:"json",
		success:function(backData){
			if(backData.state == 'Y' || backData == null){
				alert(backData.msg);
				enableBtn("#submitBtn", "打印预览", 'changeToPrintPage('+backData.obj.applicationId+')', ue);
				//window.location.href = sysPath + "/property/queryForPage.do?viewName=applyInvokeOutPropertyList";
			}else if(backData.state == 'F'){
				alert(backData.msg);
				var proList = backData.obj;
				$(proTr).each(function(i){
					var proId = $(this).find("input[name='proId']").val();
					for(var j = 0; j < proList.length; j++){
						if(proId == proList[j].proId){
							$(this).addClass("error");
						};
					}
				});
				enableBtn("#submitBtn", "确定", 'sendData()');
			}else{
				alert("未知错误");
				enableBtn("#submitBtn", "确定", 'sendData()');
			}
		},
		error:function(){
			alert("提交失败");
			enableBtn("#submitBtn", "确定", 'sendData()');
		}
		
	});
}	
//);
</script>
</body>
</html>
