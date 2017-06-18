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
<script type="text/javascript" src="<%=path%>/js/tempForm.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 集中销毁审批</span></div>
<form action="<%=path%>/applicationOrder/approveHandle.do" method="post">

<input type="hidden" name="handleType" value="intoApplicationPreHandle"/>
<div class="content">
	<div class="detal"><h2 class="h4tit">财物集中销毁审批</h2></div>
	<div class="toptit"><h1 class="h1tit">财物列表</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo">${application.applicationNo }</span></h1></div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" class="table">
	  <tr>
	    <th>序号</th>
	    <th>财物编号</th>
		<th>财物名称</th>
		<th>财物数量</th>
		<th>计量单位</th>
		<th>财物类别</th>
		<th>财物特征</th>    
		<th>财物详情</th>    
	  </tr>
	  <c:forEach items="${propertyList }" var="p" varStatus="sta">
	  <tr class="proTr">
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
		<td>${p.proCharacteristic}</td>
		<input type="hidden" value="${p.proStatus }" name="proStatus"/>
		<input type="hidden" value="${p.proType }" name="proType"/>
		<input type="hidden" value="${p.actualPosition }" name="actualPosition"/>
		<td>
			<span class="tdbut">
				<a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty">查看</a>
			</span>
		</td>
	  </tr>
	  </c:forEach>
	 </table>
	 <input type="hidden" value="" name="proJson"/>
	  <ul class="list02a w50">
	   <li><label>申请单位：</label>${mapping:mappingDepartmentNameByAccount(application.account) }</li>
	   <li><label>申请人：</label>${mapping:mappingAccountNameById(application.account)}</li>
	   <li class="w50"><label>案件编号：</label>${application.tCase.jzcaseID }</li>
	   <li class="w50"><label>案件名称：</label>${application.tCase.caseName }</li>
	   <li class="w100"> <label>责任领导：</label>${application.tCase.leader }</li>
	   <li class="w50"><label>牵头部门：</label>${mapping:mappingDepartmentNameById(application.leadDepartmentId) }</li>
	   <li class="w50"><label>协作部门：</label>${mapping:mappingDepartmentNameById(application.assistDepartmentId) }</li>
	   
	   <li class="w100" style="display:block;height:375px;overflow: auto;">
	   		<label style="display:block;">销毁依据：</label>
			<div style="width:85%;float:left;height: 100%;">
				 ${application.applyBasis }
			</div>
	   </li>
	</ul>
	
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
	<div class="dbut"><a href="#" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:history.go(-1)">返回</a>
	<a href="<%=path %>/applicationOrder/queryTrackByApplicationId.do?applicationId=${application.id}" id="processTrack">流程跟踪</a>
	</div>
</div>
</form>
<script type="text/javascript">
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
 	$(document).ready(function(){
		
	 })

</script>
</body>
</html>