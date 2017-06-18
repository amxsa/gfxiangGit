<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<style>
	.table th, .table td{
	font-size: 12px;
	padding-left:0px;
	padding-right:1px;
		}
</style>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 处置申请列表</span></div>
<form id="searchForm" action="<%=path%>/property/queryForPage.do?viewName=applyHandlePropertyList" method="post">
<div class="content">

 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
 <div class="search">
 <div class="sp">
  <p><label>财物编号</label><input name="condiProNo" type="text" value="${condition.condiProNo}" style="width:170px;"/><label>财物名称</label><input name="condiProName" type="text" value="${condition.condiProName }" style="width:120px;"/>
   <label>状态</label><select name="condiProStatus">
  	 	<option value="">全部</option>
  		<option value="YDJ">已登记</option>
  		<option value="ZCK">入暂存库</option>
  		<option value="ZXK">入中心库</option>
  </select>
  </p>
  
  <p><label>案件编号</label><input name="condiJzcaseId" type="text" value="${condition.condiJzcaseId }" style="width:130px;"/><label>案件名称</label><input name="condiCaseName" type="text" value="${condition.condiCaseName }" style="width:120px;"/></p>
  <p><label>立案时间</label><input name="condiStartTime" type="text"  value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/><i>至</i><input name="condiEndTime" type="text"  value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/></p>
  </div>
  <div class="sbut"><input name="" type="button" value="查询" onclick="searchPro()"/></div>
  </div>
 
 <div class="toptit">
    <h1 class="h1tit">财物处置列表</h1>
    <div class="but1"><input name="" type="button" value="批量处置"  onclick="doQueryBatchApply()"/></div>
  </div>

 <table width="99%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
  <tr>
    <th><input type="checkbox" name="" class="checkAll"/>选定</th>
    <th>案件编号</th>
    <th>案件名称</th>
    <th>财物编号</th>
	<th>财物名称</th>
	<th>财物类别</th>    
	<th>财物数量</th>
	<th>计量单位</th>
	<th>财物状态</th>
	<th>财物登记人</th>
	<th style="width:7%">财物登记时间</th>
	<th>所在仓库</th>
	<th>库位</th>
	<th>入库时间</th>
	<th>入库经办人</th>
    <th>操作</th>
  </tr>
    <c:forEach items="${properties }" var="pro" varStatus="sta">
	  <tr>
	  	<td><input type="checkbox" name="proId" value="${pro.id}" class="checkBoxPreRow"/></td>
	    <td>${pro.jzcaseId}</td>
	    <td>${pro.caseName }</td>
	    <td>${pro.proNo }</td>
	    <td>${pro.name }</td>
		<td>${mapping:mappingProType(pro.type)}</td>
		<td><c:if test="${pro.quantity%1.0==0}"><fmt:formatNumber type="number" value="${pro.quantity }" maxFractionDigits="0"/></c:if><c:if test="${pro.quantity %1.0!=0}">${pro.quantity }</c:if></td>
		<td>${pro.unit }</td>
	    <td>
	    	${mapping:mappingProStatus(pro.status) }
	    </td>
	    <td>
	    	${mapping:mappingAccountNameById(pro.creator) }
	    </td>
	    <td>
	    	<fmt:formatDate value="${pro.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
		<td>${pro.storehouseName }</td>
		<td>${pro.locationName }</td>
		<td>
			<fmt:formatDate value="${pro.intoHouseTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td>${mapping:mappingAccountNameById(pro.intoHousePerson) }</td>
	    <td>
		    <span class="tdbut">
		    <a href="javascript:void(0)" onclick="doQueryApply(this, '${pro.id}')">申请</a>
		    </span>
	    </td>
	  </tr> 
  </c:forEach>
 </table>
 <%--

 <div class="page"><a href="#">首页</a><a href="#">上一页</a><a href="#" class="cur">1</a><a href="#">2</a><a href="#">下一页</a><a href="#">末页</a> 共20页</div>
--%>
	 <div class='page'></div>
</form>
</div>
<script type="text/javascript">
	
	$(document).ready(function() {
		$("select[name='condiProStatus']").val("${condition.condiProStatus}");
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});
	
	function doQueryBatchApply(){
		var checkdeTrs = $(":checkbox[name='proId']:checked").parents("tr");
		if(checkdeTrs.length <= 0){
			alert("请先选择财物");
			return;
		}
		
		$("#searchForm").attr("action", "<%=path%>/applicationOrder/queryForBatchApply.do?viewName=handleApplicationPreAdd" );
		
		searchPro();
	}
	
	function doQueryApply(mythis, proId){
		$("#searchForm").attr("action", "<%=path%>/applicationOrder/queryForBatchApply.do?viewName=handleApplicationPreAdd&proId="+proId );
		
		searchPro();
	}
	
	function searchPro(){
		$("#searchForm").submit();
	}
</script>
</body>
</html>
