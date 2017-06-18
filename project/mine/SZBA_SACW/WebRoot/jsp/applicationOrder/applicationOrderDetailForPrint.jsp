<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请单打印预览</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/print.css" type="text/css"></link></head>
<script src="${pageContext.request.contextPath }/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<body>
	<div class="main">
		<h1 class="title">
			<span>${mapping:generateApplicationTitle(application.procedureCode) }</span>
		</h1>
		<div class="addr">
			<h3>申请单编号：</h3>
			<p style="text-indent:7em">${application.applicationNo }&nbsp;</p>
		</div>
		<div class="addr">
			<h3>责任领导：</h3>
			<p style="text-indent:6em">${application.tCase.leader }&nbsp;</p>
		</div>
		<div class="addr">
			<h3>案件编号：</h3>
			<p style="text-indent:6em">${application.tCase.jzcaseID }&nbsp;</p>
		</div>
		<div class="addr">
			<h3>案件名称：</h3>
			<p style="text-indent:6em">${application.tCase.caseName }&nbsp;</p>
		</div>
		<div class="addr">
			<h3>申请人：</h3>
			<p style="text-indent:5em">${mapping:mappingAccountNameById(application.account)}&nbsp;</p>
		</div>
		<div class="addr">
			<h3>单位：</h3>
			<p style="text-indent:4em">${mapping:mappingDepartmentNameByAccount(application.account) }&nbsp;</p>
		</div>
		<div class="addr">
			<h3>申请类型：</h3>
			<p style="text-indent:6em">${mapping:mappingApplicationType(application.procedureCode) }&nbsp;</p>
		</div>
		<c:if test="${application.procedureCode == 'PROCEDURE001' || application.procedureCode == 'PROCEDURE008'}">
			<div class="addr">
				<h3>待入仓库：</h3>
				<p style="text-indent:6em">${application.targetStorehouse }&nbsp;</p>
			</div>
			<div class="addr">
				<h3>保存期限：</h3>
				<p style="text-indent:6em">${application.saveDateType }&nbsp;</p>
			</div>
			<div class="addr">
				<h3>保存到期日期：</h3>
				<p style="text-indent:9em"><fmt:formatDate value="${application.expirationDate }" pattern="yyyy-MM-dd"/>&nbsp;</p>
			</div>
			<div class="addr">
				<h3>计划入库时间：</h3>
				<p style="text-indent:9em"><fmt:formatDate value="${application.planStorageDate }" pattern="yyyy-MM-dd"/>&nbsp;</p>
			</div>
			<div class="addr">
				<h3>送货方式：</h3>
				<p style="text-indent:6em">${application.deliverType }&nbsp;</p>
			</div>
		</c:if>
		
		<c:if test="${application.procedureCode == 'PROCEDURE006' || application.procedureCode == 'PROCEDURE007'}">
			<div class="addr">
				<h3>计划归还时间：</h3>
				<p style="text-indent:9em"><fmt:formatDate value="${application.planStorageDate }" pattern="yyyy-MM-dd"/>&nbsp;</p>
			</div>
		</c:if>
		
		<c:if test="${application.procedureCode == 'PROCEDURE002' }">
			<div class="addr">
				<h3>接收单位：</h3>
				<p style="text-indent:6em">${application.targetStorehouse }&nbsp;</p>
			</div>
		</c:if>
		
		<c:if test="${application.procedureCode == 'PROCEDURE009' || application.procedureCode == 'PROCEDURE0010' || application.procedureCode == 'PROCEDURE011'}">
			<div class="addr">
				<h3>牵头部门：</h3>
				<p style="text-indent:6em">	${mapping:mappingDepartmentNameById(application.leadDepartmentId) }&nbsp;</p>
			</div>
			<div class="addr">
				<h3>协助部门：</h3>
				<p style="text-indent:6em">${mapping:mappingDepartmentNameById(application.assistDepartmentId) }&nbsp;</p>
			</div>
		</c:if>
		
		<div class="input">
			<h3>申请原因：</h3>
			<div>
			${application.applyBasis}&nbsp;
			</div>
		</div>
		<hr />
		<table border="0" cellpadding="0" cellspacing="0"
			class="table">
			<tr>
				<th width="6%">序号</th>
				<th>财物编号</th>
				<th>财物名称</th>
				<th>财物数量</th>
				<th>计量单位</th>
				<th>财物持有人</th>
				<th>财物类别</th>
				<th>财物特征</th>
				<th>电子标签</th>
				<th>所属库位</th>
			</tr>
			<c:forEach items="${propertyList }" var="p" varStatus="sta">
				<tr class="proTr">
				<td>${sta.count }</td>
				<td class="proId">${p.proNo}</td>
				<td>${p.proName }</td>
				<td><c:if test="${p.proQuantity%1.0==0}">
						<fmt:formatNumber type="number" value="${p.proQuantity }"
							maxFractionDigits="0" />
					</c:if>
					<c:if test="${p.proQuantity %1.0!=0}">${p.proQuantity }</c:if> </td>
				<td>${p.proUnit }</td>
				<td>${p.civiName }</td>
				<td>${mapping:mappingProType(p.proType) }</td>
				<td>${p.proCharacteristic }</td>
				<td>${p.proRfidNum }</td>
				<td>${p.warehouseId }</td>
			</tr>
			</c:forEach>
		</table>
		<div class="dbut">
		<a href="javascript:void(0)" id="printBut">打印</a>
		</div>
	</div>
</body>
<script language="javascript" type="text/javascript"> 
    $(document).ready(function(){
    	
    	$("#printBut").click(function(){
    		$(".dbut").remove();
    		window.print();
    	});
    });
</script> 
</html>