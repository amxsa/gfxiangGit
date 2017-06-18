<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>笔录信息</title>
<style type="text/css">
	.list02a .ztree li{
		clear:both;
	}

</style>

</head>
<body>
<div class="crumb"><span>当前位置：<a href="${pageContext.request.contextPath}/main.jsp" target="main">首页 </a>>>
<c:if test="${record.type == '12' }">提取痕迹物证登记表</c:if>
<c:if test="${record.type == '27' }">调取物证清单</c:if>
</span></div>
<div class="content">
	
 <div class="toptit"><h1 class="h1tit">警员人员信息</h1></div>
 <ul class="list02a w50">
 	<c:choose>
 		<c:when test="${fn:length(record.polices) > 0 }">
 			<c:forEach items="${record.polices }" var="poli" >
 			<c:if test="${poli.poliType == '26' }">
 			<li><label>提取人：</label>${poli.poliName }</li>
   			<li><label>单位：</label>${poli.deptName }</li>
   			</c:if>
 			</c:forEach>
 			<c:forEach items="${record.polices }" var="poli" >
 			<c:if test="${poli.poliType == '19' }">
 			<li><label>保管人：</label>${poli.poliName }</li>
   			<li><label>单位：</label>${poli.deptName }</li>
   			</c:if>
 			</c:forEach>
 			
 		</c:when>
 		<c:otherwise>
 			<li><label>提取人：</label>${poli.poliName }</li>
   			<li><label>单位：</label>${poli.deptName }</li>
 			<li><label>保管人：</label>${poli.poliName }</li>
   			<li><label>单位：</label>${poli.deptName }</li>
 		</c:otherwise>
 	</c:choose>
 	
 	
    
 </ul>
 <div class="toptit"><h1 class="h1tit">涉案人员信息</h1></div>
 <ul class="list02a w50">
 	<c:choose>
 	<c:when test="${fn:length(record.civilians) > 0 }">
	 	<c:forEach items="${record.civilians }" var="civi">
	 		<c:if test="${civi.civiType == '17' }">
			   <li><label>见证人：</label>${civi.civiName }</li>
			   <li><label>证件号：</label>${civi.idNum }</li>
	  		</c:if>
	   </c:forEach>
	 	
   </c:when>
   <c:otherwise>
   		<li><label>见证人：</label>${civi.civiName }</li>
		<li><label>证件号：</label>${civi.idNum }</li>
   </c:otherwise>
   </c:choose>
 </ul>
 
  <div class="toptit"><h1 class="h1tit">财物信息</h1></div>
  <table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
  <tr>
    <th>财物编号</th>
    <th>案件编号</th>
    <th>案件名称</th>
	<th>财物名称</th>
	<th>财物数量</th>
	<th>财物持有人</th>
	<th>扣押依据</th>
	<th>财物类别</th>    
	<th>财物状态</th>
    <th>操作</th>
  </tr>
   <c:forEach items="${record.properties }" var="pro" varStatus="sta">
	  <tr>
	    <td>${pro.proId }</td>
	    <td>${pro.jzcaseId }</td>
	    <td>${pro.caseName }</td>
	    <td>${pro.proName }</td>
		<td><c:if test="${pro.proQuantity%1.0==0}"><fmt:formatNumber type="number" value="${pro.proQuantity }" maxFractionDigits="0"/></c:if><c:if test="${pro.proQuantity %1.0!=0}">${pro.proQuantity }</c:if>${pro.proUnit }</td>
		<td>${pro.proOwner==''?pro.civiName:pro.proOwner }</td>
		<td>${pro.proSeizureBasis }</td>
		<td>${mapping:mappingProType(pro.proType)}</td>
	    <td>${mapping:mappingProStatus(pro.proStatus) }
		</td>
	    <td><span class="tdbut"><a href="<%=path%>/property/queryDetail.do?proId=${pro.proId }&viewName=watchProperty">查看</a></span></td>
	  </tr>
  </c:forEach>
 </table>
 
 <div class="dbut"><a href="javascript:history.go(-1)">返回</a></div>
</div>

<script type="text/javascript">	

$(function() {
	
});
</script>

</body>
</html>
