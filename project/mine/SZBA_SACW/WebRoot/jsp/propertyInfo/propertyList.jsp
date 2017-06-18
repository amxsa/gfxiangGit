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
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			案件关联</span>
	</div>
	<form id="searchForm" action="<%=path%>/property/queryForPage.do?viewName=propertyList"
		method="post">
		<input type="hidden" name="tempProIds" id="tempProIds" value="" />
		<div class="content">
			<div class="toptit">
				<h1 class="h1tit">按条件查询</h1>
			</div>
			<div class="search">
				<div class="sp">
				<%--这个隐藏域是用了说明，返回到的页面的--%>
				<p>
					<label>财物编号</label><input name="condiProNo" type="text" value="${condition.condiProNo}" style="width:170px;"/>
					<label>财物名称</label><input name="condiProName" type="text" value="${condition.condiProName }" style="width:120px;"/>
				</p>
				<p>
					<label>案件编号</label><input name="condiJzcaseId" type="text" value="${condition.condiJzcaseId }" style="width:130px;"/>
					<label>案件名称</label><input name="condiCaseName" type="text" value="${condition.condiCaseName }" style="width:120px;"/>
				</p>
				<p>
					<label>立案时间</label><input name="condiStartTime" type="text" value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
					<i>至</i><input name="condiEndTime" type="text" value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
				</p></div>
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchPro()" />
					<input style="background:#1F6BB2  no-repeat 10px 5px;padding:0 10px 3px 8px;margin:0 0 0 20px" name="" type="button" value="关联案件" onclick="caseProperty()"  />
				</div>
				
			</div>


			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th>选择</th>
					<th>财物编号</th>
					<th>案件编号</th>
					<th>案件名称</th>
					<th>财物名称</th>
					<th>财物类别</th>    
					<th>财物来源</th>    
					<th>财物数量</th>
					<th>财物单位</th>
					<th>登记人</th>
					<th>登记时间</th>
					<th>登记部门</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${properties }" var="pro" varStatus="sta">
					<tr>
						<td>
							<c:if test="${empty pro.caseId }">
								<input type="checkbox" name="proId" value="${pro.id }" />
							</c:if>
						</td>
						<td>${pro.proNo }</td>
					    <td>${pro.jzcaseId}</td>
					    <td>${pro.caseName }</td>
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
						<td><span class="tdbut"> 
							<c:if test="${not empty pro.caseId }">
								<a href="#" onclick="cancelBind('${pro.id }');">取消关联</a>
							</c:if>
							<a href="<%=path%>/property/queryDetail.do?proId=${pro.proId }&viewName=watchProperty">详情</a>
						</span>
						
						</td>
						
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
	
	function caseProperty(){
		var proId = getCheckboxValue("proId");
		if(proId!=''){
			$("#tempProIds").val(proId);
			$("#searchForm").attr("action","<%=path%>/caseProperty/queryForPage.do");
			$("#searchForm").submit();
		}else{
			alert('请选择财务');
		}
		
	}
	
	function getConditionStr(){
		var condiProId = $(":text[name='condiProId']").val();
		var condiProName = $(":text[name='condiProName']").val();
		var condiJzcaseId = $(":text[name='condiJzcaseId']").val();
		var condiCaseName = $(":text[name='condiCaseName']").val();
		var condiStartTime = $(":text[name='condiStartTime']").val();
		var condiEndTime = $(":text[name='condiEndTime']").val();
		var currentIndex = "${page.currentIndex}";
		var sizePerPage = "${page.sizePerPage}";
		
		var condiParams= "condiProId=" +condiProId + "&condiProName="+condiProName+"&condiJzcaseId="+condiJzcaseId
						 +"&condiCaseName="+condiCaseName+"&condiStartTime="+condiStartTime+"&condiEndTime="+condiEndTime
						 +"&currentIndex="+currentIndex+"&sizePerPage="+sizePerPage;
		return condiParams;
	}
	function toEditPage(proId){
		window.location.href = sysPath + "/property/queryDetail.do?proId="+proId+"&viewName=propertyPreModify&"+getConditionStr();
	}

	function cancelBind(id){
		var flag = window.confirm("您确定取消关联？");
		if(flag){
			$.ajax({
				url:"<%=path%>/caseProperty/cancelBind.do",
				data:"proId="+id,
				type:"post",
				dataType:"json",
				success: function(data){
				  if(data.state){
				  	alert(data.msg);
				  	searchPro();
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