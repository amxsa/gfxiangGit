<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link type="text/css" href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 电子物证</span></div>
<form id="searchForm" action="<%=path%>/elecevidence/queryForPage.do" method="post">
<div class="content">
 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
 <div class="search">
  <p>
     <label>财物编号</label><input name="condiProId" type="text" value="${condition.condiProId==0?'':condition.condiProId }"/>
     <label>财物名称</label><input name="condiProName" type="text" value="${condition.condiProName }"/>
  	 <label>状态</label><select name="condiProStatus">
  	 	<option value="">全部</option>
  	 	<option value="WDJ">未登记</option>
  		<option value="YDJ">已登记</option>
  		<option value="ZCK">入暂存库</option>
  		<option value="ZXK">入中心库</option>
  		<option value="DY">调用</option>
  		<option value="CZ">处置</option>
  		<option value="AJ">案结</option>
  		<option value="YDJ2ZCK">申请入暂存库中</option>
  		<option value="ZCK2CZCK">申请出暂存库中</option>
  		<option value="CZCK">出暂存库</option>
  		<option value="CZXK">出中心库</option>
  		<option value="ZCK2ZXK">申请入中心库中</option>
  		<option value="ZXK2CZXK">申请出中心库中</option>
  		<option value="ZCK_DY">暂存库-已调用</option>
  		<option value="ZXK_DY">中心库-已调用</option>
  		<option value="ZCK_CZ">暂存库-已处置</option>
  		<option value="ZXK_CZ">中心库-已处置</option>
  		<option value="CZZ">处置中</option>
  		<option value="DYZ">调用中</option>
  </select>
  </p>
  <p>
     <label>案件编号</label><input name="condiJzcaseId" type="text" value="${condition.condiJzcaseId }"/>
     <label>案件名称</label><input name="condiCaseName" type="text" value="${condition.condiCaseName }"/>
  </p>
  <p>
     <label>立案时间</label><input name="condiStartTime" type="text"  value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
     <i>至</i><input name="condiEndTime" type="text"  value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/></p>
  <div class="sbut"><input name="" type="submit" value="查询" /></div>
  </div>
 
 
  <table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
  <tr>
    <th>序号</th>
    <th>财物名称</th>
    <th>财物数量</th>
    <th>财物持有人</th>
	<th>财物类别</th>
	<th>入库保存</th>
	<th>案件编号</th>
	<th>案件名称</th>
	<th>财物状态</th>    
	<th>处理审批</th>
    <th>操作</th>
  </tr>
   <c:forEach items="${properties }" var="elecPro" varStatus="sta">
	  <tr>
	    <td>${sta.count }</td>
	    <td>${elecPro.name }</td>
	    <td><c:if test="${elecPro.quantity%1.0==0}"><fmt:formatNumber type="number" value="${elecPro.quantity }" maxFractionDigits="0"/></c:if><c:if test="${elecPro.quantity %1.0!=0}">${elecPro.quantity }</c:if>${elecPro.unit }</td>
	    <td></td>
		<td></td>
	    <td></td>
	    <td>${elecPro.caseId }</td>
	    <td>${elecPro.caseName }</td>
		<td></td>
		<td></td>
	    <td>
	       <span class="tdbut"><a href="<%=path%>/elecevidence/updateById.do?proId=${elecPro.elecEvidenceID }">修改</a></span>
	       <span class="tdbut"><a href="<%=path%>/elecevidence/deleteByID.do?proId=${elecPro.elecEvidenceID }">删除</a></span>
	       <span class="tdbut"><a href="<%=path%>/elecevidence/queryDetail.do?proId=${elecPro.elecEvidenceID }">查看</a></span>
	    </td>
	  </tr>
  </c:forEach>
 </table>
 
 <div class='page'></div>

</form>
</div>
<script type="text/javascript">

	$(document).ready(function(){ 
		
		$("select[name='condiProStatus']").val("${condition.condiProStatus}");
	});
	
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});

</script>
</body>
</html>