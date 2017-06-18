<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../common/common.jsp"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%
String msg=(String)request.getAttribute("msg");


%>
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
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物入库申请单</span></div>
<div class="content">
<div class="detal"><h2 class="h4tit">财物入库</h2></div>
 <div class="toptit"><h1 class="h1tit">财物列表</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo">${application.applicationNo }</span></h1></div>
 
 <form action="<%=path%>/applicationOrder/approveHandle.do" id="" method="post">
 
 <table width="100%" cellpadding="0" cellspacing="1" border="0" class="table" id="proTable">
 	<thead>
	  <tr>
	    <th>序号</th>
	    <th>财物编号</th>
		<th>财物名称</th>
		<th>财物数量</th>
		<th>计量单位</th>
		<th>财物类别</th>
		<th>财物特征</th>    
		<th>备注</th>
		<th>保管方式</th>    
		<th>保管条件要求</th>   
		<th>占用大小</th>  
		<th>库位</th> 
		<th>电子标签</th>
		<th>档案材料</th>   
	  </tr>
	 </thead>
<tbody>
  <c:forEach items="${propertyList }" var="p" varStatus="sta">
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
	<td>${mapping:mappingProSaveMethod(p.saveMethod)}</td>
	<td>${p.enviDemand }</td>
	
	<%--<td><input type="button"value="选择" onclick="opendiv()"/></td>
	<td><input type="button" value="打印"/></td>
	<td><input type="button" value="上传"/></td>
	--%>
	<td><input type="text" style="width:50px;" name="storeNum"/></td>
	<td>
		<input type="hidden" name="storeHouseId"/>
		<input type="hidden" name="freeNum"/>
		<input type="hidden" name="warehouseId"/>
		<span name="warehouseNum"></span>
		<input type="button"value="选择" onclick="selStorageLocation(this)" value="选择">
	</td>
	<td><span>打印</span></td>
	<td><span>上传</span></td>
	<input type="hidden" value="${p.proStatus }" name="proStatus"/>
	<input type="hidden" value="${p.proType }" name="proType"/>
	<input type="hidden" value="${p.actualPosition }" name="actualPosition"/>
	<input type="hidden" value="${p.proId }" name="proId"/>
	<input type="hidden" value="${p.proNo }" name="proNo"/>
	
  </tr>
  </c:forEach>
 </tbody>
 </table>
	<input type="hidden" value="" name="proJson"/>
	  <ul class="list02a w50">
	   <li><label>申请 单位：</label>${mapping:mappingDepartmentNameByAccount(application.account) }</li>
	   <li> <label>责任领导：</label>${application.tCase.leader }</li>
	   <li class="w100"><label>申请人：</label>${mapping:mappingAccountNameById(application.account)}</li>
	   <li class="w50"><label>案件编号：</label>${application.tCase.jzcaseID }</li>
	   <li class="w50"><label>案件名称：</label>${application.tCase.caseName }</li>
	   <li class="w100"><label>待入仓库：</label>
	   	${application.targetStorehouse }
	   	</select>
	   </li>
	   
	   <li><label>保存期限：</label>
		   ${application.saveDateType }
	   </li>
	   <li><label>保存到期日期：</label>
		  <fmt:formatDate value="${application.expirationDate }" pattern="yyyy-MM-dd"/>
	   </li>
	   <li class="w100"><label>计划入库时间：</label>
		  <fmt:formatDate value="${application.planStorageDate }" pattern="yyyy-MM-dd"/>
	   </li>
	   <li class="w100"><label>送货方式：</label>
		  ${application.deliverType }
	   </li>
	   
	   <li class="w100" style="display:block;height:375px;overflow: auto;">
	   	<label>申请理由依据：</label>
		   <div style="width:85%;float:left;height: 100%;">
			${application.applyBasis }
		</div>
		</li>
	</ul>
	
	<div class="toptit">
		<h1 class="h1tit">档案资料</h1>
	</div>
	<ul class="ws_list">
		<c:forEach var="attachment" items="${attachmentList }">
		<li><a href="javascript:void(0)" onclick="downloadFile('${attachment.uploadUrl}')">${attachment.attaName }</a></li>
		</c:forEach>
	</ul>
	<div class="toptit">
			<h1 class="h1tit">审批信息</h1>
		</div>
		<table width="100%" cellpadding="0" cellspacing="1" border="0"
			class="table">
			<tr>
				<th>处理人员</th>
				<th>处理时间</th>
				<th>处理意见</th>
			</tr>
			<c:forEach var="aRecord" items="${commentList}">
			<tr>
				<td>${mapping:mappingAccountID(aRecord.userId)}</td>
				<td><fmt:formatDate value='${aRecord.time}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${aRecord.message}</td>
			</tr>
			</c:forEach>
		</table>
	<%--</c:if>--%>
	
	<ul class="list02">
		<li><label>审验结果:</label>
			<select name="status">
				<option value="Y">同意</option>
				<option value="N">不同意</option>
			</select>
		</li>
		<li><label>入库说明：</label>
		<textarea name="result" cols="" rows=""></textarea></li>
	</ul>
	<input type="hidden" name="taskId" value="${taskId}" />
	<input type="hidden" name="procInstId" value="${procInstId}" />
	<input type="hidden" name="applicationId" value="${application.id}" />
	<input type="hidden" name="isIn" value="Y" />
	<div class="dbut"><a href="#" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:void(0)" class="goBackByQuery">返回</a>
	<a href="<%=path %>/applicationOrder/queryTrackByApplicationId.do?applicationId=${application.id}" id="processTrack">流程跟踪</a>
	</div>
 <!-- <div class="dbut"><a href="javascript:void(0)" id="submitBtn">确定入库</a><a href="javascript:history.go(-1)">返回</a></div> -->
 </form>
</div>
<div style="display: none;position: absolute;top: 23%;left: 30%;width: 300PX;" id="storeroom">

库房：<select id="one" onchange="doSelect(this.value)"> 
<c:forEach items="${lRooms }" var="lRooms">
<option value="${lRooms.id }">${lRooms.storeroomName }</option> 
</c:forEach>

</select><input type="button" value="确定" onclick="showLock()"/> </br>
货架：<select  id="district" >
<c:forEach items="${tRacks }" var="tRacks">
<option value="${tRacks.id }">${tRacks.rackName }</option> 
</c:forEach>
</select>
	<table>
	<c:if test="${!empty tLockers}">
	<tr>
	<td>存储柜</td>
	</tr>
	<c:forEach items="${tLockers }" var="tLockers">
	
	<tr>

	<c:if test="${tLockers.inventoryStatus=='E' }">
	<td style="background-color: green;">${tLockers.lockerNum }</td>
	</c:if>
	<c:if test="${tLockers.inventoryStatus=='U' }">
	<td style="background-color:yellow;">${tLockers.lockerNum }</td>
	</c:if>
	<c:if test="${tLockers.inventoryStatus=='F' }">
	<td style="background-color:red;">${tLockers.lockerNum }</td>
	</c:if>
	</tr>
	</c:forEach>
	</c:if>
	
	
	<c:if test="${empty tLockers}">
	<tr>
	<td>库位</td>
	</tr>
	<c:forEach items="${tStorageLocations }" var="ts">
	
	<tr>

	<c:if test="${ts.inventoryStatus=='E' }">
	<td style="background-color: green;">${ts.locationNum }</td>
	</c:if>
	<c:if test="${ts.inventoryStatus=='U' }">
	<td style="background-color:yellow;">${ts.locationNum }</td>
	</c:if>
	<c:if test="${ts.inventoryStatus=='F' }">
	<td style="background-color:red;">${ts.locationNum }</td>
	</c:if>
	</tr>
	</c:forEach>
	</c:if>
	</table>
</div>
<script type="text/javascript">
//选择库位
function selStorageLocation(thisObj){
	var storeNum=parseInt($(thisObj).parent().prev().find('input[name="storeNum"]').val());
	if(storeNum==''){
		alert('请选填写占用大小，再选择库位');
		return;
	}
	var path=sysPath+'/jsp/applicationOrder/preInStorageLocationList.jsp';
	art.dialog.open(path, {
	    title: '选择库位',
	    width: 700,
	    height:500,
	    top:10,
	    // 在open()方法中，init会等待iframe加载完毕后执行
	    init: function () {
	    	this.iframe.contentWindow.selStorageId=$(thisObj).prev().prev().val();
	    	this.iframe.contentWindow.initData();
	    },
	    ok: function () {
	    	var ret = this.iframe.contentWindow.getSelData();
	    	if(ret.length==0) { 
	    		alert('至少选择一个库位');
	    		return false;
	    	}
	    	if(ret[1]!=0&&storeNum>ret[3]){
	    		alert('该财物所属的容量大于该库位剩余容量，请重新选择');
	    		return false;
	    	}
	    	$(thisObj).prev().html(ret[2]);
	    	$(thisObj).prev().prev().val(ret[1]);
	    	$(thisObj).prev().prev().prev().val(ret[3]);
	    	$(thisObj).prev().prev().prev().prev().val(ret[0]);
	    },
	    cancel: true
	});
}
function opendiv(){
$("#storeroom").css("display","block");
}

function doSelect(id){
$.ajax({
		url:sysPath+"/applicationOrder/showTrack.do",
		data:{storeroomId:id},
		type:"post",
		dataType:"json",
		success:function(result){
			try {
				 $("#district").empty(); //清空下拉列表
			 $(result).each(function(i, item) {
			 	$("#district").append(" <option value='" + item.id + "'>" + item.rackName + "</option>");
			 }
			 );
			 }
			 catch (e) {
			 alert(e);
			 return;
			 }
		}
		
	});

}
function showLock(){
var s=$("#district").val();
var one=$("#one").val();
if(s!=null){
window.location.href="<%=path %>/applicationOrder/showById.do?viewname=showLockById&rackId="+s;
}
if(s==null){
window.location.href="<%=path %>/applicationOrder/showById.do?viewname=showStorageLocationById&stroomId="+one;
}
}
$(document).ready(function() {
		$(".goBackByQuery").click(function(){
			Common.tempForm.initData('${refererUrl}', '${conditions}');
		});
		
		var message = '<%=msg%>';
		
		if(message != ''){
			if(message=='ok'){
				$("#storeroom").css("display","block");
			} 
		}
});

function sendData(){
	var proIds = "[";
	var proTr = $("#proTable tbody tr");
	var selWare=true;
	$(proTr).each(function(i){
		var proId = $(this).find("input[name='proId']").val();
		var proNo = $(this).find("input[name='proNo']").val();
		var proStatus = $(this).find("input[name='proStatus']").val();
		var proType = $(this).find("input[name='proType']").val();
		var storeNum=$(this).find("input[name='storeNum']").val();
		var warehouseId=$(this).find("input[name='warehouseId']").val();
		if(warehouseId==null||warehouseId==''){
			selWare=false;
		}
		var storeHouseId=$(this).find("input[name='storeHouseId']").val();
		
		var actualPosition = $(this).find("input[name='actualPosition']").val();
		proStatusIndex=actualPosition;  //财物的实际位置，用于跑流程
		if(proId != null && proId != ''){
			proIds += "{'proId':'"+proId+"', 'proNo':'"+proNo+"','storeNum':'"+storeNum+"','proStatus':'"+proStatus+"', 'proType':'"+proType+"', 'actualPosition':'"+actualPosition+"', 'warehouseId':'"+warehouseId+"', 'storeHouseId':'"+storeHouseId+"'},";  //此处将财物状态带上，是为了在财物调用和处置流程时作为一个判断标识
		}
	});
	if(!selWare){
		alert('请选择库位再入库');
		return;
	}
	proIds = proIds.substring(0, proIds.length-1);
	proIds += "]";
	$("input[name='proJson']").val(proIds);
	
	//判断财物所需的占用大小不能大于实际空闲大小
	var selArr=[];
	$("#proTable tbody tr").each(function(){
		var storeNum=$(this).find("input[name='storeNum']").val();
		var warehouseId=$(this).find("input[name='warehouseId']").val();
		var freeNum=$(this).find("input[name='freeNum']").val();
		var storeName=$(this).find("span[name='warehouseNum']").text();
		
		//判断是否已经存在
		var existIdx=-1;
		for(var i=0;i<selArr.length;i++){
			var singleObj=selArr[i];
			if(singleObj.id==warehouseId){
				existIdx=i;
			}
		}
		if(existIdx!=-1){
			selArr[existIdx].storeNum=selArr[existIdx].storeNum+parseInt(storeNum);
		}else{
			var selObj={};
			selObj.name=storeName;
			selObj.id=warehouseId;
			selObj.freeNum=parseInt(freeNum);
			selObj.storeNum=parseInt(storeNum);
			selArr.push(selObj);
		}
	});
	var errorDesc='';
	for(var i=0;i<selArr.length;i++){
		var singleObj=selArr[i];
		if(singleObj.id!=0&&singleObj.storeNum>singleObj.freeNum){
			errorDesc=errorDesc+'库位'+singleObj.name+'所需的数量大于可用数量;';
		}
	}
	if(errorDesc!=''){
		alert(errorDesc);
		return;
	}
	
	disableBtn("#submitBtn");
	var action = $("form:first").attr("action");
	$.ajax({
		url:action,
		data:$("form:first").serialize(),
		type:"post",
		success:function(backData){
			if(backData.state === "Y"){
				alert("已审批完成");
				Common.tempForm.initData('${refererUrl}','${conditions}');
			}else{
				alert(backData.msg);
				enableBtn("#submitBtn", "确定", 'sendData()');
			}
		},
		error:function(){
			alert("连接失败");
			enableBtn("#submitBtn", "确定", 'sendData()');
		}
		
	});		
}
</script>
</body>
</html>
