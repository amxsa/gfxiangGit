<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>URL管理</title>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
	function view(id){
		location.href = "${path}/url/view.do?id="+id;
	}
	function del(id){
		if(!window.confirm("确认删除？")){
			return;
		}	
		var url = "${path}/url/del.do?id="+id;
		var param = "";
		asyncAjax(url,param,function(data){
			if(data && data.state){
				if(data.msg != ""){
					alert(data.msg);
				}
				if(data.state == "Y"){
					reflashForm()
				}
			}			
		});
	}
	function reflashForm(){
		document.forms[0].submit();
	}
</script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> URL管理</span></div>
<div class="content">
<form id="searchForm" action="${path }/url/queryList.do" method="post">
	<div class="toptit"><h1 class="h1tit">按条件查询</h1></div>
	<div class="search">
		<p>
			<label>URL</label><input name="url" type="text" value="${vo.url}"/>
		</p>
		<div class="sbut"><input name="" type="submit" value="查询" /></div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">列表</h1>
	    <div class="but1"><input name="" type="button" value="添加" onclick="view('')"/></div>
	  </div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table">
		<tr>
			<th>URL</th>
			<th>类型</th>
			<th>描述</th>
			<th>组ID</th>
			<th>操作</th>
		</tr>
		<c:forEach var="o" items="${list}">
			<tr>
				<td>${o.url }</td>
				<td>${o.type }</td>
				<td>${o.description }</td>
				<td>${o.groupId }</td>
				<td><span class="tdbut">
					<a href="#" onclick="view('${o.id }')">修改</a>
					<a href="#" onclick="del('${o.id }')">删除</a>
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
</script>
</body>
</html>