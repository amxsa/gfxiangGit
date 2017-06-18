<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物登记</span></div>
<form id="searchForm" action="<%=path%>/property/queryForPage.do?viewName=registerPropertyList" method="post">
<div class="content">
 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
 <div class="search">
 <div class="sp">
  <p>
  	<label>案件编号</label>
  	<input name="condiJzcaseId" type="text" value="${condition.condiJzcaseId }" style="width:130px;"/>
  	<label>案件名称</label>
  	<input name="condiCaseName" type="text" value="${condition.condiCaseName }" style="width:120px;"/>
  </p>
  <p>
  	<label>财物编号</label><input name="condiProNo" type="text" value="${condition.condiProNo}" style="width:170px;"/>
  	<label>财物名称</label><input name="condiProName" type="text" value="${condition.condiProName }" style="width:120px;"/>
  </p>
  <p style="float:none; clear: both;">
  	<label>立案时间</label>
  	<input name="condiStartTime" type="text"  value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
  	<i>至</i>
  	<input name="condiEndTime" type="text"  value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
  </p>
  </div>
  <div class="sbut">
  <input name="" type="button" value="查询" onclick="searchPro()"/>
  <c:if test="${!mapping:isOnlyContainRole(rolesInfo, 'XTGLY') }">
  	<input style="background:#1F6BB2  no-repeat 10px 5px;padding:0 10px 3px 8px;margin:0 0 0 20px" name="" type="button" value="添加财物" onclick="addProperty()"/>
  </c:if>
  </div>
  </div>
 
 
  <table cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" style="word-break:break-all; word-wrap:break-word;width:100%;">
  <tr>
    <th width="10%">案件编号</th>
    <th width="10%">案件名称</th>
    <th width="13%">财物编号</th>
	<th width="11%">财物名称</th>
	<th width="7%">财物类别</th>    
	<th width="5%">财物来源</th>    
	<th width="5%">数量</th>
	<th width="5%">计量单位</th>
	<th width="5%">登记人</th>
	<th width="7%">登记时间</th>
	<th width="6%">登记部门</th>
	<th width="7%">财物状态</th>
    <th width="13%">操作</th>
  </tr>
   <c:forEach items="${properties}" var="pro" varStatus="sta">
	  <tr>
	    <td>${pro.jzcaseId}</td>
	    <td>${pro.caseName }</td>
	    <td>${pro.proNo}</td>
	    <td>${pro.name }</td>
		<td>${mapping:mappingProType(pro.type)}</td>
		<td>${mapping:mappingProOrigin(pro.origin)}</td>
		<td>
		<c:if test="${pro.quantity%1.0==0}"><fmt:formatNumber type="number" value="${pro.quantity}" maxFractionDigits="0"/></c:if>
		<c:if test="${pro.quantity %1.0!=0}">${pro.quantity}</c:if></td>
		<td>${pro.unit}</td>
		<td>${mapping:mappingAccountNameById(pro.creator)}</td>
		<td><fmt:formatDate value="${pro.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${mapping:mappingDepartmentNameByAccount(pro.creator)}</td>
	    <td>${mapping:mappingProStatus(pro.status) }
		</td>
	    <td>
	    	<span class="tdbut">
	    	<a href="javascript:lookDetail('${pro.id}')">查看</a>
	    	<c:if test="${pro.creator==loginForm.ACCOUNT&&pro.status=='YDJ'}">
	    		<a href="javascript:toEditPage('${pro.id}')">修改</a>
	    	</c:if>
	    	<!--  <a href="javascript:deleteData('${pro.id}')">删除</a>--></span>
	    </td>
	    
	  </tr>
  </c:forEach>
 </table>
 
	    
 <div class='page'></div>

</form>
</div>
<script type="text/javascript">
	//添加
	function addProperty(){
		window.location.href = sysPath + "/jsp/propertyInfo/propertyPreAdd.jsp";
	}
	
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});
	
	function searchPro(){
		
		$("#searchForm").submit();
	}
	
	function getConditionStr(){
		var condiProNo = $(":text[name='condiProNo']").val();
		var condiProName = $(":text[name='condiProName']").val();
		var condiJzcaseId = $(":text[name='condiJzcaseId']").val();
		var condiCaseName = $(":text[name='condiCaseName']").val();
		var condiStartTime = $(":text[name='condiStartTime']").val();
		var condiEndTime = $(":text[name='condiEndTime']").val();
		var currentIndex = "${page.currentIndex}";
		var sizePerPage = "${page.sizePerPage}";
		
		var condiParams= condiProNo + ";"+condiProName+";"+condiJzcaseId+";"+condiCaseName+";"
						+condiStartTime+";"+condiEndTime+";"+currentIndex+";"+sizePerPage;
		return condiParams;
	}
	function toEditPage(proId){
		window.location.href=sysPath + "/jsp/propertyInfo/propertyPreModify.jsp?proId="+proId+"&cParams="+getConditionStr();
	}
	
	function lookDetail(proId){
		window.location.href=sysPath + "/jsp/propertyInfo/watchProperty.jsp?proId="+proId;
	}

	function deleteData(id){
		var flag = window.confirm("确定删除此记录？");
		if(flag){
			$.ajax({
				url:"<%=path%>/property/delete.do",
				data:"proId="+id,
				type:"post",
				success:function(backData){
					if(backData == "Y"){
						alert("删除成功");
						searchPro();
					}else{
						alert("删除失败");
					}
				},
				error:function(){
					alert("网络连接出错，请稍候重试");
				}
				
			});
		}
	}
</script>
</body>
</html>