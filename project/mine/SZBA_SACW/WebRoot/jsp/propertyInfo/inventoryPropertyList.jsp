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
			库存管理列表
		</span>
	</div>
	<form id="searchForm" action="<%=path%>/property/queryForPage.do"
		method="post">
		<div class="content">
			<div class="toptit">
				<h1 class="h1tit">按条件查询</h1>
			</div>
			<div class="search"><div class="sp">
				<input type="hidden" name="viewName" value="inventoryPropertyList" />
				<%--这个隐藏域是用了说明，返回到的页面的--%>
				<p>
					<label>财物编号</label>
					<input name="condiProNo" type="text" value="${condition.condiProNo}"  style="width:170px;"/>
					<label>财物名称</label>
					<input name="condiProName" type="text" value="${condition.condiProName }"  style="width:120px;"/>
				</p>
				<p>
					<label>案件编号</label>
					<input name="condiJzcaseId" type="text" value="${condition.condiJzcaseId }"  style="width:130px;"/>
					<label>案件名称</label>
					<input name="condiCaseName" type="text" value="${condition.condiCaseName }"  style="width:120px;"/>
				</p>
				<p>
					<label>立案时间</label>
					<input name="condiStartTime" type="text" value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
					<i>至</i>
					<input name="condiEndTime" type="text" value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
				</p>

				<div class="selt">
					<p>
						<label>入库人</label>
						<input name="condiIntoPer" type="text" value="${condition.condiIntoPer}" />
					</p>
					<p>
						<label>入库时间</label>
						<input name="condiIntoStartTime" type="text" value="<fmt:formatDate value='${condition.condiIntoStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
						<i>至</i>
						<input name="condiIntoEndTime" type="text" value="<fmt:formatDate value='${condition.condiIntoEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
					</p>
					<p>
						<label>仓库名称</label>
						<input name="condiStoreHouseName" type="text" value="${condition.condiStoreHouseName}"   style="width:120px;"/>
					</p>
				</div>
				</div>
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchPro()" />
				</div>
			</div>

			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th>案件编号</th>
					<th>案件名称</th>
					<th>财物编号</th>
					<th>财物名称</th>
					<th>财物类别</th>
					<th>财物数量</th>
					<th>计量单位</th>
					<th>财物仓库</th>
					<th>财物库位</th>
					<th>状态</th>
					<th>入库时间</th>
					<th>入库经办人</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${properties}" var="pro" varStatus="sta">
					<tr>
						<input type="hidden" name="proId" value="${pro.id}">
						<td>${pro.jzcaseId}</td>
						<td>${pro.caseName }</td>
						<td>${pro.proNo }</td>
						<td>${pro.name }</td>
						<td>${mapping:mappingProType(pro.type)}</td>
						<td><c:if test="${pro.quantity%1.0==0}">
								<fmt:formatNumber type="number" value="${pro.quantity}"
									maxFractionDigits="0" />
							</c:if> <c:if test="${pro.quantity %1.0!=0}">${pro.quantity}</c:if></td>
						<td>${pro.unit}</td>
						<td>${pro.storehouseName }</td>
						<td>${pro.locationName }</td>
						<td>${mapping:mappingProStatus(pro.status) }</td>
						<td><fmt:formatDate value="${pro.intoHouseTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${mapping:mappingAccountNameById(pro.intoHousePerson) }</td>
						<td>
							<span class="tdbut"> 
								<input type="hidden" value="${pro.storehouseName}"/>
								<input type="hidden" value="${pro.storeNum}"/>
								<input type="hidden" value="${pro.locationId}"/>
								<a href="javascript:void(0)" onclick="selStorageLocation(this)">更改库位</a>
							</span>
						</td>
					</tr>
				</c:forEach>
			</table>


			<div class='page'></div>
	</form>
	</div>
	<script type="text/javascript">
//选择库位
function selStorageLocation(thisObj){
	var thisTr=$(thisObj).parent().parent().parent();
	var proId=thisTr.find('input[name="proId"]').val();
	var storeNum=$(thisObj).prev().prev().val();

	var path=sysPath+'/jsp/propertyInfo/inventoryStorageLocation.jsp';
	art.dialog.open(path, {
	    title: '选择库位',
	    width: 700,
	    height:500,
	    top:10,
	    // 在open()方法中，init会等待iframe加载完毕后执行
	    init: function () {
	    	this.iframe.contentWindow.selStorageId=$(thisObj).prev().val();  //库位id
	    	this.iframe.contentWindow.selStorageNum=$(thisObj).prev().prev().val();  //库位所需大小
	    	this.iframe.contentWindow.selStorageName=$(thisObj).prev().prev().prev().val();  //原有仓库
	    	this.iframe.contentWindow.initData();
	    },
	    ok: function () {
	    	var ret = this.iframe.contentWindow.getSelData();
	    	
	    	if(ret.length==0) { 
	    		alert('至少选择一个库位');
	    		return false;
	    	}
	    	if(ret[1]!=0&&storeNum>ret[3]){
	    		alert('该财物所属的容量大于该库位剩余容量，请重新选择');
	    		return false;
	    	}
	    	var ylocationId=$(thisObj).prev().val();  //原来库位id
	    	$.ajax({
				type: 'POST',
				url: sysPath+'/property/inventoryMoveLocation.do?proId='+proId+'&locationId='+ret[1]+'&storeHouseId='+ret[0]+'&ylocationId='+ylocationId,
				dataType:"json",
				success: function(data){
					if(data!=null){
						alert('操作成功');
						$(thisObj).prev().val(ret[1]);  //库位id
						$(thisObj).prev().prev().prev().val(data.storehouseName);  //原有仓库
						
						//改变所在行的仓库以及库位名称
						thisTr.children('td').eq(7).html(data.storehouseName);
						thisTr.children('td').eq(8).html(ret[2]);
					}else{
						alert('操作失败');
					}
				}
	    	});
	    },
	    cancel: true
	});
}
$(document).ready(function() {
	// 生成页码
	Common.method.pages.genPageNumber("searchForm",'${page.currentIndex}', '${page.sizePerPage}','${page.totalPage}');
});

function searchPro() {
	$("#searchForm").submit();
}
	</script>
</body>
</html>