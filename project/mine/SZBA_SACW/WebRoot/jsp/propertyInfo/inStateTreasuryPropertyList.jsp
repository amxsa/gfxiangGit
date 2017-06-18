<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script language="javascript"
	src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>

</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			应入国库</span>
	</div>
	<form id="searchForm"
		action="<%=path%>/property/queryForPage.do?viewName=inStateTreasuryPropertyList"
		method="post">
		<div class="content">

			<div class="toptit">
				<h1 class="h1tit">按条件查询</h1>
			</div>
			<div class="search">
				<input type="hidden" name="viewName"
					value="intoTreasuryPropertyList" />
				<%--这个隐藏域是用了说明，返回到的页面的 --%>
				<p>
					<label>财物编号</label><input name="condiProNo" type="text" value="${condition.condiProNo}"/> <label>财物名称</label><input
						name="condiProName" type="text" value="${condition.condiProName }" />
				</p>
				<p>
					<label>案件编号</label><input name="condiJzcaseId" type="text"
						value="${condition.condiJzcaseId }" /> <label>案件名称</label><input
						name="condiCaseName" type="text"
						value="${condition.condiCaseName }" />
				</p>
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchPro()" /> <input
						style="background:#1F6BB2  no-repeat 10px 5px;padding:0 10px 3px 8px;margin:0 0 0 20px"
						name="" type="button" value="移入国库 " onclick="btnTreasury()" />
				</div>

			</div>

			<div class="toptit">
				<h1 class="h1tit">入国库列表</h1>
				<%--<div class="but1"><input name="" type="button" value="批量处置申请"  onclick="location.href='022b批量处置申请.html'"/></div>--%>
			</div>

			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th><input type="checkbox" name="" class="checkAll" />选定</th>
					<th>案件名称</th>
					<th>案件编号</th>
					<th>财物编号</th>
					<th>财物名称</th>
					<th>财物类型</th>
					<th>数量</th>
					<th>评估价</th>
					<th>金额</th>
					<th>没收时间</th>
					<th>拍卖时间</th>
					<th>拍卖方式</th>
					<th>拍卖类型</th>
					<th>发票编号</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${properties }" var="pro" varStatus="sta">
					<tr>
						<td><input type="checkbox" name="proId" value="${pro.proId }"
							class="checkBoxPreRow" />
						</td>
						<td>${pro.caseName }</td>
						<td>${pro.jzcaseId }</td>
						<td>${pro.proNo }</td>
						<td>${pro.name }</td>
						<td>${mapping:mappingProType(pro.type)}</td>

						<td>${pro.quantity }</td>
						<td><c:if test="${pro.quantity%1.0==0}">
								<fmt:formatNumber type="number" value="${pro.quantity }"
									maxFractionDigits="0" />
							</c:if> <c:if test="${pro.quantity %1.0!=0}">${pro.quantity }</c:if>${pro.unit}
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><span class="tdbut" > <a href="#">详情</a> <a
								href="#" onclick="intoTreasury('${pro.proId }');">入国库</a> </span></td>
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
	$(document).ready(function(){ 
		
		$("select[name='condiProStatus']").val("${condition.condiProStatus}");
	});
	
	$(document).ready(function() {
		// 生成页码
		Common.method.pages.genPageNumber("searchForm", ${page.currentIndex}, ${page.sizePerPage}, ${page.totalPage});
	});
	

	function searchPro(){
		$("#searchForm").submit();
	}
	
	function intoTreasury(){
OpenWindow=window.open("", "newwin", "height=400, width=500,top=300,left=500,resizable=no,location=no");
OpenWindow.document.write("<TITLE>入国库</TITLE>")
OpenWindow.document.write("<BODY BGCOLOR=#ffffff>")
OpenWindow.document.write("<p>拍卖方式：<input></intput></p>")
OpenWindow.document.write("<p>拍卖人：&nbsp<input></intput></p>")
OpenWindow.document.write("<p>拍卖时间：<input></intput></p>")
OpenWindow.document.write("<p>发票编号：<input></intput></p>")
OpenWindow.document.write("<p>拍卖价格：<input></intput></p>")
OpenWindow.document.write("<p><input type='submit' value='确认登记'/><input type='submit' value='取消' style='margin-left: 30px' onclick='closeTreasury();'/></p>")
OpenWindow.document.write("</BODY>")
OpenWindow.document.write("</HTML>")
OpenWindow.document.close()
}

	function closeTreasury(){
	alert("123")
	
	}
</script>
</body>
</html>
