<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link type="text/css" href="<%=path%>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 实施入库申请列表</span></div>
<div class="content">
 <div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
<form id="searchForm" action="<%=path%>/applicationOrder/queryForBeIn.do" method="post"> 
 <div class="search">
 <div class="sp">
 <p><label>申请单编号</label><input name="applicationNo" type="text" value="${condition.applicationNo }" style="width:120px;"/></p>
  <p><label>财物编号</label><input name="condiProNo" type="text" value="${condition.condiProNo}"  style="width:170px;"/></p>
  <p><label>财物名称</label><input name="condiProName" type="text" value="${condition.condiProName }"  style="width:120px;"/></p>
  <p><label>案件编号</label><input name="condiJzcaseId" type="text" value="${condition.condiJzcaseId }"  style="width:130px;"/></p>
  <p><label>案件名称</label><input name="condiCaseName" type="text" value="${condition.condiCaseName }"  style="width:120px;"/></p>
  <!-- 
  <p><label>申请人</label><input name="applicationId" type="text" value="${condition.applicationId }"/></p>
  <p><label>申请部门</label><input name="applicationId" type="text" value="${condition.applicationId }"/></p>
   -->
  <p><label>入库状态</label>
  	<select name="proInStatus">
  		<option value="">全部</option>
  		<option value="1" <c:if test="${condition.proInStatus==1 }">selected="selected"</c:if>>已入库</option>
  		<option value="2" <c:if test="${condition.proInStatus==2 }">selected="selected"</c:if>>未入库</option>
  	</select>
  </p>
  <!-- <p><label>办案民警</label><input name="" type="text" /></p><p><label>办案单位</label><input name="" type="text" /></p>
  <p><label>申请时间</label>
        <input name="condiStartTime" type="text"  value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    <i>至</i>
        <input name="condiEndTime" type="text"  value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </p>-->
    </div>
  <div class="sbut"><input name="" type="submit" value="查询" /></div>
  </div>
 
 <table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
  <tr>
    <th>案件名称</th>
    <th>案件编号</th>
    <th>申请单编号</th>
    <th>申请时间</th>
    <th>申请单位</th>
    <th>申请人</th>
    <th>入库完成时间</th>
    <th>入库经办人</th>
    <th>状态</th>
    <th>操作</th>
  </tr>
  <c:forEach var="aRecord" items="${result}">
  <tr>
    <td>${aRecord.caseName}</td>
    <td>${aRecord.caseId}</td>
    <td>${aRecord.applicationNo}</td>
    <td><fmt:formatDate value="${aRecord.applicationCreateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    <td>${mapping:mappingDepartmentNameByAccount(aRecord.account)}</td>
    <td>${mapping:mappingAccountNameById(aRecord.account)}</td>
    <td><fmt:formatDate value="${aRecord.handleTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    <td>${mapping:mappingAccountNameById(aRecord.handler)}</td>
    <td>
		${mapping:mappingApplicationStatus(aRecord.applicationStatus) }  
    </td>
    <td><span class="tdbut">
     <c:if test="${aRecord.hisStatus=='1'}"><a href="javascript:void(0)" class="doApply" onclick="doApply('preIn','${aRecord.taskId}','${aRecord.applicationId}','${aRecord.procInstId}')">入库</a></c:if>
    <c:if test="${aRecord.hisStatus=='2' }"><a href="javascript:queryApplyDetail(${aRecord.applicationId})">查看详情</a> </c:if>
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
	
</script>
</body>
</html>