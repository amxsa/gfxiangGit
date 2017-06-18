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
<title>模板</title>
</head>
<body>

	<form id="searchForm" action="<%=path%>/templet/queryTemplet.do"
		method="post">
		<input type="hidden" name="contentType" value="${contentType}"/>
		<div style="float: left;height: 40px;line-height: 40px;padding-left: 10px;">
			<div style="float: left;">
				<label>名称</label>
				<input name="name" style="width:120px;" type="text" value="${name}"/>
			</div>
			<div class="sbut" style="float: left;clear: none;margin-left: 20px;">
				<input type="button" value="查询" onclick="searchTemplet()" />
				<input type="button" value="添加"  onclick="addTemplet();"/>
			</div>
		</div>
		<table width="100%" cellpadding="0" cellspacing="1" border="0"
			id="table_hover" class="table"  style="word-break:break-all; word-wrap:break-all;">
			<thead>
				<tr>
					<th style="width:6%;"></th>
					<th style="width:7%;">序号</th>
					<th style="width:30%;">模板名称</th>
					<th style="width:20%;">类型</th>
					<th style="width:37%;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="data" varStatus="sta">
					<tr>
						<td>
							<input type="radio" name="id" value="${data.id }" />
						</td>
						<td>${ sta.index + 1}</td>
						<td>${data.name }</td>
						<td>
							<c:if test="${data.type=='0' }">
								系统默认
							</c:if>
							<c:if test="${data.type=='1' }">
								自定义
							</c:if>
						</td>
						<td>
							<span class="tdbut">
	    						<a href="javascript:lookData('${data.id}')">查看</a>
						    	<c:if test="${data.account==loginForm.ACCOUNT}">
						    		<a href="javascript:updateData('${data.id}')">修改</a>
						    		<a href="javascript:deleteData('${data.id}')">删除</a>
						    	</c:if>
	    					</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class='page'></div>
	</form>
	<input type="hidden" id="hcurrentIndex" value="${page.currentIndex}"/>
	<input type="hidden" id="hsizePerPage" value="${page.sizePerPage}"/>
	<input type="hidden" id="htotalPage" value="${page.totalPage}"/>
<script type="text/javascript">
$(document).ready(function() {
 	Common.method.pages.genPageNumber("searchForm", $('#hcurrentIndex').val(), $('#hsizePerPage').val(), $('#htotalPage').val());
});
function searchTemplet(){
	$('#searchForm').submit();
}
function addTemplet(){
	var path=sysPath+'/jsp/templet/templetAdd.jsp';
	art.dialog.open(path, {
	    title: '添加模板',
	    width: 800,
	    height:560,
	    top:10,
	    ok: function () {
	    	var ret=this.iframe.contentWindow.addDataImpl();
	    	if(!ret){
	    		return false;
	    	}
	    	searchTemplet();	
	    },
	    cancel: true
	});
}
function updateData(id){
	var path=sysPath+'/templet/queryDetail.do?type=update&id='+id;
	art.dialog.open(path, {
	    title: '修改模板',
	    width: 800,
	    height:550,
	    top:10,
	    ok: function () {
	    	var ret=this.iframe.contentWindow.updateDataImpl();
	    	if(!ret){
	    		return false;
	    	}
	    	searchTemplet();	
	    },
	    cancel: true
	});
}
function lookData(id){
	var path=sysPath+'/templet/queryDetail.do?type=look&id='+id;
	art.dialog.open(path, {
	    title: '查看模板',
	    width: 800,
	    height:550,
	    top:10,
	    cancel: true
	});
}
function deleteData(id){
	var flag = window.confirm("确定删除此模板？");
	if(flag){
		$.ajax({
			url:sysPath+'/templet/delete.do',
			data:"id="+id,
			type:"post",
			success:function(data){
				if(data.state=='S'){
					alert("删除成功");
					searchTemplet();
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