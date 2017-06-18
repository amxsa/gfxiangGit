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
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物出库申请单</span></div>
<div class="content">
<div class="detal"><h2 class="h4tit">财物出库</h2></div>
 <div class="toptit"><h1 class="h1tit">财物列表</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo">${application.applicationNo }</span></h1></div>
 <form action="<%=path%>/applicationOrder/approveHandle.do" id="" method="post">
 
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
	<th>出库拍照</th>   
  </tr>
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
	<td><input type="button"value="出库拍照"/></td>
	
	<input type="hidden" value="${p.proStatus }" name="proStatus"/>
	<input type="hidden" value="${p.proType }" name="proType"/>
	<input type="hidden" value="${p.actualPosition }" name="actualPosition"/>
	<input type="hidden" value="${p.proId }" name="proId"/>
	<input type="hidden" value="${p.proNo }" name="proNo"/>
	
  </tr>
  </c:forEach>
 </table>
	<input type="hidden" value="" name="proJson"/>
	  <ul class="list02a w50">
	   <li><label>申请单位：</label>${mapping:mappingDepartmentNameByAccount(application.account) }</li>
	   <li> <label>责任领导：</label>${application.tCase.leader }</li>
	   <li class="w100"><label>申请人：</label>${mapping:mappingAccountNameById(application.account)}</li>
	   <li class="w50"><label>案件编号：</label>${application.tCase.jzcaseID }</li>
	   <li class="w50"><label>案件名称：</label>${application.tCase.caseName }</li>
	   <li class="w100" style="display:block;height:375px;overflow: auto;">
	   <label>申请理由依据：</label>
		   ${application.applyBasis }
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
		<li><label>出库说明：</label>
		<textarea name="result" cols="" rows=""></textarea></li>
	</ul>
	<input type="hidden" name="taskId" value="${taskId}" />
	<input type="hidden" name="procInstId" value="${procInstId}" />
	<input type="hidden" name="applicationId" value="${application.id}" />
	<input type="hidden" name="isOut" value="Y" />
	
	<div class="dbut"><a href="#" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:void(0)" class="goBackByQuery">返回</a>
	<a href="<%=path %>/applicationOrder/queryTrackByApplicationId.do?applicationId=${application.id}" id="processTrack">流程跟踪</a>
	</div>
 <!-- <div class="dbut"><a href="javascript:void(0)" id="submitBtn">确定入库</a><a href="javascript:history.go(-1)">返回</a></div> -->
 </form>
</div>

<script type="text/javascript">
$(document).ready(function(){
	$(".goBackByQuery").click(function(){
		Common.tempForm.initData('${refererUrl}', '${conditions}');
	});
});
function sendData(){
	disableBtn("#submitBtn");
	var action = $("form:first").attr("action");
	$.ajax({
		url:action,
		data:$("form:first").serialize(),
		type:"post",
		success:function(backData){
			if(backData.state === "Y"){
				alert("已审批完成");
				window.location.href=sysPath+"/applicationOrder/queryForBeOut.do";
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
