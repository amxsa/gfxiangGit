<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link type="text/css" href="<%=path%>/css/main.css" rel="stylesheet" />
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物审批</span></div>
<form action="<%=path%>/applicationOrder/approveHandle.do" method="post">
<input type="hidden" name="proData" />
<div class="content">
	<ul class="list02 w50">
		<li><label>案件编号：</label>${detail.tCase.jzcaseID }</li>
		<li><label>案件名称：</label>${detail.tCase.caseName }</li>
		<li><label>申请单编号：</label>${application.applicationNo }</li>
		<li><label>责任领导：</label>${detail.tCase.leader }</li>
	</ul>
	<table width="100%" cellpadding="0" cellspacing="1" border="0"
		class="table">
		<tr>
			<th>序号</th>
			<th>财物编号</th>
			<th>财物名称</th>
			<th>财物数量</th>
			<th>计量单位</th>
			<th>财物持有人</th>
			<th>财物类别</th>
			<th>财物特征</th>
			<th>财物详情</th>
		</tr>
		<c:forEach items="${detail.properties }" var="p" varStatus="sta">
			<tr class="proTr">
				<input type="hidden" name="proId" value="${p.proId }"/>
				<td>${sta.count }</td>
				<td>${p.proNo }</td>
				<td>${p.proName }</td>
				<td><c:if test="${p.proQuantity%1.0==0}">
						<fmt:formatNumber type="number" value="${p.proQuantity }"
							maxFractionDigits="0" />
					</c:if> <c:if test="${p.proQuantity %1.0!=0}">${p.proQuantity }
					</c:if> </td>
				<td>${p.proUnit }</td>
				<td>${p.civiName }</td>
				<td>${mapping:mappingProType(p.proType) }</td>
				<td>${p.proCharacteristic }</td>
				<td><span class="tdbut"><a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty">查看</a></span></td>
			</tr>
		</c:forEach>
	</table>
	
	<ul class="list02">
		<li><label>单位：</label>${application.departmentName }</li>
		<c:if test="${application.procedureCode=='PROCEDURE001' }">
			<li><label>申请人：</label>${application.accountName }</li>
		</c:if>
		<c:if test="${application.procedureCode!='PROCEDURE001' }">
			<li><label>申请人：</label><span id="proposer">${application.departmentId }:${application.accountName }</span></li>
		</c:if>
		<li><label>申请处置方式：</label>${application.disposalDescription }</li>
		<li><strong>申请原因：</strong>${application.applyBasis}</li>
	</ul>
	<%--入暂存库没有审批信息<c:if test="${application.procedureCode!='PROCEDURE001' }"> --%>
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
		<li><label>审批结果:</label>
			<select name="status">
				<option value="Y">同意</option>
				<option value="N">不同意</option>
			</select>
		</li>
		<li><label>审批意见：</label>
		<textarea name="result" cols="" rows=""></textarea></li>
	</ul>
	<input type="hidden" name="taskId" value="${taskId}" />
	<input type="hidden" name="procInstId" value="${procInstId}" />
	<input type="hidden" name="applicationId" value="${application.id}" />
	<input type="hidden" name="proId" value="${proId}" />
	<input type="hidden" name="proStatus" value="${proStatus}" />
	<div class="dbut"><a href="#" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:history.go(-1)">返回</a></div>
</div>
</div>
</form>
<script type="text/javascript">
function sendData(){
	proList = '[';
	flag = false;
	$('.proTr').each(function(){
		proId=$('input:hidden[name="proId"]', this).html();
		rfidNum=$('td.rfidNum input',this).val();
		warehouseId=$('td.warehouseId select',this).val();
		proData="";
		if(flag){
			proData=","
		}
		flag=true;
		proData=proData+'{"proId":"'+proId+'"}';
		proList=proList+proData;
	})
	proList=proList+"]";
	
	//$('input[name="proData"]').val(encodeURIComponent(proList));
	$('input[name="proData"]').val(proList);
	//$('form').submit();
	disableBtn("#submitBtn");
	var action = $("form:first").attr("action");
	$.ajax({
		url:action,
		data:$("form:first").serialize(),
		type:"post",
		success:function(backData){
			if(backData.state === "Y"){
				alert("已审批完成");
				window.location.href=sysPath+"/applicationOrder/queryForPage.do?applicationStyle=done";
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
 $(document).ready(function(){
	var content = $('#proposer').html(); 
	if(content && content!=''){
		arr = content.split(":");
		content = Common.departments.method.getName(arr[0]) + " : " + arr[1];
		$('#proposer').html(content); 
	}
	
	//$('#submitBtn').click(
	
	//)
 })

</script>
</body>
</html>