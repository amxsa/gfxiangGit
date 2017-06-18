<%@page import="cn.cellcom.szba.domain.TProperty"%>
<%@page import="cn.cellcom.szba.biz.TPropertyBiz"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ include file="../../common/common.jsp"%>
<%
	String tempProIds = request.getParameter("tempProIds");
	StringBuffer tempProName=new StringBuffer();
	if(tempProIds!=null){
		List<TProperty> list = TPropertyBiz.queryProperty(tempProIds.split(",")) ;
		if(list!=null){
			for(int i=0;i<list.size();i++){
				tempProName.append(list.get(i).getProName()).append(",");
			}
		}
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
</head>
<body>

	<form id="searchForm" action="<%=path%>/caseProperty/queryForPage.do"
		method="post">
		<input type="hidden" name="tempProIds" id="tempProIds"
			value="${param.tempProIds }" />
		<div >
			<label>财务名称:<%=tempProName.toString() %></label>
		</div>
		<div class="content">
			<div class="toptit">
				<h1 class="h1tit">按条件查询</h1>
			</div>
			<div class="search">
				<p>
					<label>案件编号</label><input style="width: 200px;" name="jzcaseID" type="text"
						value="${condiParams.jzcaseID[0]}"  /><label>案件名称</label><input
						name="caseName" type="text" style="width: 200px;" value="${condiParams.caseName[0] }" />
					<div class="sbut">
						<input name="" type="button" value="查询" onclick="searchCase()" />
						<input style="background:#1F6BB2  no-repeat 10px 5px;padding:0 10px 3px 8px;margin:0 0 0 20px" name="" type="button" value="关联案件" onclick="bind();"  />
					</div>
					
				</p>
			
			</div>
			

		</div>
		<table width="100%" cellpadding="0" cellspacing="1" border="0"
			id="table_hover" class="table">
			<tr>
				<th>选择</th>
				<th>案件名称</th>
				<th>案件编号</th>

				<th>案件性质</th>
				<th>案发时间</th>
				<th>登记部门</th>
				

			</tr>
			<c:forEach items="${list }" var="data" varStatus="sta">
				<tr>
					<td><input type="radio" name="tempJzcaseID" value="${data.jzcaseID }" />
					</td>
					<td>${data.caseName }</td>
					<td>${data.jzcaseID }</td>
					<td>
						<c:if test="${data.caseType=='1' }">
							刑事案件
						</c:if>
						<c:if test="${data.caseType=='2' }">
							行政案件
						</c:if>
					</td>
					<td>
						<fmt:formatDate value="${data.occurDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
						
					</td>
					<td>${data.departmentName }</td>
					

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
	
	function searchCase(){
		
		$("#searchForm").submit();
	}
	
	function bind(){
		var tempJzcaseID =$('input[name="tempJzcaseID"]:checked').val();
		$.ajax({
			  type: 'post',
			  async: false,
			  data:{tempProIds:$("#tempProIds").val(),tempJzcaseID:tempJzcaseID},
			  url: '<%=path%>/caseProperty/bind.do',
			  dataType:"json",
			  success: function(data){
				  if(data.state){
				  	alert(data.msg);
				  }else{
				  	alert(data.msg);
				  }
			  }
		});
	}
	
	
</script>
</body>
</html>