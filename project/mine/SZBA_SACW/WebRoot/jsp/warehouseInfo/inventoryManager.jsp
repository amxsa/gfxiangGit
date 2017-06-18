<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>库存管理</title>
<link type="text/css" href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 库存管理</span></div>
<form id="searchForm" action="" method="post">
<div class="content">
 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
 <div class="search">
  
  <p><label>财物编号</label>
  <input name="condiProId" type="text" value="<%=RequestUtils.getAttribute(params,"condiProId",0)%>"/>
  <label>财物名称</label>
  <input name="condiProName" type="text" value="<%=RequestUtils.getAttribute(params,"condiProName",0)%>"/>
  
  </p>
  <p><label>案件编号</label><input name="condiJzcaseId" type="text" value="<%=RequestUtils.getAttribute(params,"condiJzcaseId",0)%>"/>
     <label>案件名称</label><input name="condiCaseName" type="text" value="<%=RequestUtils.getAttribute(params,"condiCaseName",0)%>"/>
     <%--<label>办案单位</label><input name="condiDeptName" type="text" value="${params[condiDeptName][0] }"/>
  --%>
  </p>
   <p><label>库位编号</label><input name="wareSerialNumber" type="text" value="<%=RequestUtils.getAttribute(params,"wareSerialNumber",0)%>"/>
  </p>
  <div class="selt">
  <label>入库日期</label>
  <input name="condiStartTime" type="text" value="<%=RequestUtils.getAttribute(params,"condiStartTime",0)%>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
  <i>至</i>
  <input name="condiEndTime" type="text"  value="<%=RequestUtils.getAttribute(params,"condiEndTime",0)%>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
  </div>
  <div class="sbut"><input name="" type="button" value="查询" onclick="searchPro()"/></div>
  </div>
 
 
  <table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
  <tr>
    <th>序号</th>
    <th>财物编号</th>
	<th>财物名称</th>
	<th>财物数量</th>
	<th>计量单位</th>
	<th>案件编号</th>
	<th>案件名称</th>
	<th>财物状态</th>
	<th>库位编号</th>
    <th>操作</th>
  </tr>
   <c:forEach items="${properties }" var="pro" varStatus="sta">
	  <tr>
	    <td>${sta.count }</td>
	    <td>${pro.proId }</td>
	    <td>${pro.proName }</td>
		<td><c:if test="${pro.proQuantity%1.0==0}"><fmt:formatNumber type="number" value="${pro.proQuantity }" maxFractionDigits="0"/></c:if><c:if test="${pro.proQuantity %1.0!=0}">${pro.proQuantity }</c:if></td>
		<td>${pro.proUnit }</td>
		<td>${pro.jzcaseId }</td>
	    <td><span style="width:30px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${pro.caseName }</span></td>
	    <td>
	    <c:choose>
	    	<%--<c:when test="${pro.proStatus == 'WDJ' }">未登记</c:when>--%>
	    	<c:when test="${pro.proStatus == 'YDJ' }">已登记</c:when>
	    	<c:when test="${pro.proStatus == 'ZCK' }">入暂存库</c:when>
	    	<c:when test="${pro.proStatus == 'ZXK' }">入中心库</c:when>
	    	<c:when test="${pro.proStatus == 'DY' }">调用</c:when>
	    	<c:when test="${pro.proStatus == 'CZ' }">处置</c:when>
	    	<c:when test="${pro.proStatus == 'AJ' }">案结</c:when>
	    	<c:otherwise>
	    		已登记
	    	</c:otherwise>
	    </c:choose>
		</td>
		<td>${pro.wareSerialNumber }</td>
	    <td>
	    <span class="tdbut">
		    <a href="<%=path%>/applicationOrder/queryForPreHandle.do?viewName=warehouseInfo/applyToCenter">申请入中心库</a>
		    <a href="javascript:void(0)">变更库位</a>
	    </span></td>
	  </tr>
  </c:forEach>
 </table>
 
 <div class='page'></div>

</form>
</div>
<script type="text/javascript">
	
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});
	
	function searchPro(){
		
		$("#searchForm").submit();
	}
	
	function deleteData(){
		var flag = window.confirm("确定删除此记录？");
		if(flag){
			$.ajax({
				url:sysPath+"/property/delete.do",
				data:"proId=${pro.proId }",
				type:"post",
				success:function(backData){
					if(backData == "Y"){
						alert();
					}else{
						
					}
				},
				error:function(){
					
				}
				
			});
		}
	}
</script>
</body>
</html>