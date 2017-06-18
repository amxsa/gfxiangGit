<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物查看</span></div>
<div class="content">
 
  <ul class="list02 w50">
   <li><label>案件编号：</label>${detail.tCase.jzcaseID }</li>
   <li><label>案件名称：</label>${detail.tCase.caseName }</li>
   <li><label>单位：</label>${application.departmentName }</li>
   <li><label>申请单编号：</label>${application.applicationNo }</li>
   <li><label>责任领导：</label>${detail.tCase.leader }</li>
   <li><label>办案民警：</label>${fn:length(detail.polices)>0?detail.polices[0].poliName:''}</li>
 </ul>
 <table width="100%" cellpadding="0" cellspacing="1" border="0" class="table">
  <tr>
    <th>序号</th>
    <th>财物编号</th>
	<th>财物名称</th>
	<th>财物数量</th>
	<th>计量单位</th>
	<th>财物持有人</th>
	<th>财物类别</th>
	<th>财物特征</th>    
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
	<td>${p.proOwner }</td>
	<td>${mapping:mappingProType(p.proType)}</td>
	<td>${p.proCharacteristic }</td>
  </tr>
  </c:forEach>
 </table>

 <div class="dbut"><a href="javascript:history.go(-1)">确定</a><a href="javascript:history.go(-1)">返回</a></div>
</div>
</body>
</html>
