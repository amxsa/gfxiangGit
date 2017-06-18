<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ page language="java" import="cn.cellcom.szba.common.DateTool" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的待办</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 我的待办</span></div>
<div class="content">
 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
<form id="searchForm" action="<%=path%>/applicationOrder/queryForPage.do" method="post"> 
 <div class="search">
 <div class="sp">
  <p><label>申请单编号</label><input name="applicationNo" type="text" value="${condition.applicationNo }"  style="width:120px;"/></p>
 <%--  <p><label>财物编号</label><input name="condiProNo" type="text" value="${condition.condiProNo}"/></p>
  <p><label>财物名称</label><input name="condiProName" type="text" value="${condition.condiProName }"/></p>
  <p><label>案件编号</label><input name="condiJzcaseId" type="text" value="${condition.condiJzcaseId }"/></p> --%>
  <p><label>案件名称</label><input name="condiCaseName" type="text" value="${condition.condiCaseName }"  style="width:120px;"/></p>
  <p>
    <label>立案时间</label>
    <input name="condiStartTime" type="text"  value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
    <i>至</i>
    <input name="condiEndTime" type="text"  value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
  </p> 
  </div>
  <div class="sbut"><input name="" type="submit" value="查询" /></div>
  </div>
	<div class="toptit">
	    <h1 class="h1tit">我的待办</h1>
	</div>
	<input name="applicationStyle" type="hidden" value="${applicationStyle }"/>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
		    <th>序号</th>
			<th>申请单编号</th> 
			<th>案件名称</th>
			<th>申请人</th>
			<th>申请部门</th>
			<th>当前环节</th>
			<th>上一环节</th>
			<th>申请时间</th>
			<th>状态</th>
			<th>操作</th>     
		</tr>
		<c:forEach var="v" varStatus="sta" items="${data}">
			<tr>
			    <td>${sta.count }</td>
				<td>${v.applicationNo}</td>
				<td>${v.caseName }</td>
				<td>${v.accountName }</td>
				<td>${v.departmentName }</td>
				<td>${v.currTaskName }</td>
				<td>${v.preTaskName }</td>
				<td><fmt:formatDate value="${v.applicationCreateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<c:if test="${v.applicationStatus=='N'}">
				   <td>已提交(审批中)</td>
				</c:if>
				<c:if test="${v.applicationStatus=='Y'}">
				   <td>审批通过</td>
				</c:if>
				<c:if test="${v.applicationStatus=='F'}">
				   <td>审批不通过</td>
				</c:if>
				<td><span class="tdbut">
						<%-- <c:if test="${v.applicationStatus=='N'}"><a href="<%=path%>/applicationOrder/queryForPreHandle.do?viewName=inTemporaryHandle&taskId=${v.taskId}&applicationId=${v.applicationId}&procInstId=${v.procInstId}">审核</a></c:if>  --%>
						<c:if test="${v.applicationStatus=='N'}">
						    <a href="javascript:approveApp('${v.taskId}','${v.applicationId}','${v.procInstId}')">审核</a>
						</c:if> 
					   <%--  <a href="<%=path %>/applicationOrder/queryById.do?applicationId=${v.applicationId }">查看详情</a> --%>
					     
					</span>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class='page'></div>
</div>
</form>
<script type="text/javascript">

	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});

	function approveApp(taskId,applicationId,procInstId){
	  var formUrl =  $("#searchForm").serialize();
	  var url = "<%=path%>/applicationOrder/getViewName.do?taskId="+taskId+"&applicationId="+applicationId+"&procInstId="+procInstId+"&formUrl="+formUrl;
	  window.location.href = url;
	}
</script>
</body>
</html>