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
<script language="javascript"
	src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>

</head>
<body>
	<div class="crumb">
		<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>>
			销毁申请列表
		</span>
	</div>
	<form id="searchForm" action="<%=path%>/property/queryForPage.do?viewName=applyDestoryPropertyList"
		method="post">
		<div class="content">
			<div class="toptit">
				<h1 class="h1tit">按条件查询</h1>
			</div>
			<div class="search">
			<div class="sp">
				<%--这个隐藏域是用了说明，返回到的页面的--%>
				<p>
					<label>财物编号</label>
						<input name="condiProNo" type="text" value="${condition.condiProNo}"  style="width:170px;"/>
					<label>财物名称</label><input
						name="condiProName" type="text" value="${condition.condiProName }"  style="width:120px;"/>
				</p>

				<p>
					<label>案件编号</label><input name="condiJzcaseId" type="text"
						value="${condition.condiJzcaseId }"  style="width:130px;"/><label>案件名称</label><input
						name="condiCaseName" type="text"
						value="${condition.condiCaseName }"  style="width:120px;"/>
				</p>
				<p style="float:none; clear: both;">
					<label>立案时间</label><input name="condiStartTime" type="text"
						value="<fmt:formatDate value='${condition.condiStartTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;" />
						<i>至</i>
						<input name="condiEndTime" type="text" value="<fmt:formatDate value='${condition.condiEndTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:140px;"/>
				</p>
				</div>
				<div class="sbut">
					<input name="" type="button" value="查询" onclick="searchPro()" />
				</div>
			</div>

			<div class="toptit">
				<h1 class="h1tit">财物销毁列表</h1>
				<div class="but1"><input name="" type="button" value="批量申请"  onclick="doQueryBatchApply()"/></div>
			</div>

			<table width="100%" cellpadding="0" cellspacing="1" border="0"
				id="table_hover" class="table">
				<tr>
					<th><input type="checkbox" name="" class="checkAll" />选定</th>
					<th>案件编号</th>
					<th>案件名称</th>
					<th>财物编号</th>
					<th>财物名称</th>
					<th>财物类别</th>
					<th>财物数量</th>
					<th>计量单位</th>
					<th>登记人</th>
					<th>登记时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${properties }" var="pro" varStatus="sta">
					<tr>
						<td><input type="checkbox" name="proId" value="${pro.proId }"
							class="checkBoxPreRow" />
							<input type="hidden" name="actualPosition" value="${pro.actualPosition }"/>
						</td>
						<td>${pro.jzcaseId }</td>
						<td>${pro.caseName }</td>
						<td>${pro.proNo}</td>
						<td>${pro.name }</td>
						<td>${mapping:mappingProType(pro.type)}</td>
						<td>
							<c:if test="${pro.quantity%1.0==0}">
								<fmt:formatNumber type="number" value="${pro.quantity }"
									maxFractionDigits="0" />
							</c:if>
							<c:if test="${pro.quantity %1.0!=0}">${pro.quantity }</c:if>
						</td>
						<td>${pro.unit }</td>
						<td>${mapping:mappingAccountNameById(pro.creator)}</td>
						<td><fmt:formatDate value="${pro.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>
							<span class="tdbut">
								<a href="javascript:void(0)" onclick="doQueryApply(this, '${pro.id}')">申请实施销毁</a>
							</span>
						</td>
					</tr>
				</c:forEach>
			</table>
			<div class='page'></div>
	</form>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){ 
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
		
		var jscaseId = "";
		var flag = true;
		var typeArr=[];  //一般财物不能与其他类别一起处理
		var actualPosition="";
		
		$(checkdeTrs).each(function(){
			tem = $(this).children("td").eq(2).text();
			if(jscaseId !== "" && jscaseId !== tem){
				alert("同个案件的财物才可以批量销毁");
				flag = false;
				return false;
			}
			//同个仓库才能批量处理
			var ap=$(this).find("input[name='actualPosition']").eq(0).val();
			if(actualPosition !== "" && actualPosition !== ap){
				alert("同个仓库的财物才可以批量销毁");
				flag = false;
				return false;
			}
			
			tem2 = $(this).children("td").eq(5).text();
			typeArr.push(tem2);
			if(tem2 === "" || tem2 == null){
				alert("请先登记财物的类别");
				flag = false;
				return false;
			}
			if((tem === "" || tem == null) && "管制刀具（不立案）" !== tem2){
				alert("'"+$(this).children("td").eq(4).text()+"'" + ",此财物需要先关联案件");
				flag = false;
				return false;
			}
			
			jscaseId = tem;
			actualPosition=ap;
		});
		//一般财物不能与其他类型批量处理
		for(var i=0;i<typeArr.lengthg;i++){
			if(typeArr[i]=='一般财物'){
				for(var j=0;i<typeArr.lengthg;j++){
					if(typeArr[i]!=typeArr[j]){
						flag = false;
						return false;
					}
				}
			}
		}
		if(!flag){
			return;
		}
		$("#searchForm").attr("action", "<%=path%>/applicationOrder/queryForBatchApply.do?viewName=destoryApplicationPreAdd" );
		searchPro();
	}

	function doQueryApply(mythis, proId){
		var trParent = $(mythis).parents("tr");
		var jscaseId = trParent.children("td").eq(2).text();
		
		var tem2 = trParent.children("td").eq(5).text();
		
		if(tem2 === "" || tem2 == null){
			alert("请先登记财物的类别");
			return;
		}
		if((jscaseId === "" || jscaseId == null) && "管制刀具（不立案）" !== tem2){
			alert("'"+trParent.children("td").eq(4).text()+"'" + ",此财物需要先关联案件");
			return;
		}
		$("#searchForm").attr("action", "<%=path%>/applicationOrder/queryForBatchApply.do?viewName=destoryApplicationPreAdd&proId="+proId );
		searchPro();
	}
	
	function searchPro(){
		$("#searchForm").submit();
	}
</script>
</body>
</html>
