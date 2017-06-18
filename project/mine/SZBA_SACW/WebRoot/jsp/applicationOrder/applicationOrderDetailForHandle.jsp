<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.cellcom.szba.common.RequestUtils" %>
<%@ page language="java" import="cn.open.db.Pager" %>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>申请单详情</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/tempForm.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 申请单详情</span></div>
<div class="content">
	<div class="detal"><h2 class="h4tit">申请单详情</h2></div>
	<div class="toptit">
	    <h1 class="h1tit">子申请单列表</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo">${application.applicationNo }</span></h1>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0"
		class="table">
		<tr>
			<th>序号</th>
			<th>申请单编号</th>
			<th>申请原因</th>
			<th>申请处置方式</th>
			<th>当前环节</th>
			<th>上一环节</th>
			<th>提交时间</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${childrenApplications }" var="a" varStatus="sta">
			<tr>
				<td>${sta.count }</td>
				<td>${a.applicationNo }</td>
				<td>${a.applyBasis }</td>
				<td>${a.disposalDescription }</td>
				<td>${a.currTaskName }</td>
				<td>${a.preTaskName }</td>
				<td><fmt:formatDate value="${a.applicationCreateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${mapping:mappingApplicationStatus(a.applicationStatus) }</td>
				<td>
				<span class="tdbut">
					<a href="<%=path %>/applicationOrder/queryById.do?applicationId=${a.applicationId }">查看详情</a> 
					<a href="javascript:void(0)" onclick="showTable('.childrenTable${sta.count }', this)">展开</a>
				</span>
				</td>
			</tr>
			<tr>
				<td colspan="8" class="childrenTable${sta.count }" style="display:none">
					<table width="100%" cellpadding="0" cellspacing="1" border="0"
		class="table" style="padding:1px;">
						<tr style="background:#E9E9E9">
							<td>财物编号</td> 
							<td>财物名称</td> 
							<td>财物数量</td> 
							<td>计量单位</td> 
							<td>财物持有人</td>
							<td>财物类别</td> 
							<td>财物特征</td> 
							<td>电子标签</td> 
							<td>所属库位</td>
							<td>操作</td>  
						</tr>
						<c:forEach items="${a.pros }" var="p" varStatus="sta2">
						<tr>
							<td>${p.proNo}</td>
							<td>${p.proName }</td>
							<td>
								<c:if test="${p.proQuantity%1.0==0}">
									<fmt:formatNumber type="number" value="${p.proQuantity }"
											maxFractionDigits="0" />
									</c:if> <c:if test="${p.proQuantity %1.0!=0}">${p.proQuantity }
								</c:if> 
							</td>
							<td>${p.proUnit }</td>
							<td>${p.civiName }</td>
							<td>${mapping:mappingProType(p.proType) }</td>
							<td>${p.proCharacteristic }</td>
							<td>${p.proRfidNum }</td>
							<td>${p.warehouseId }</td>
							<td>
								<span class="tdbut"><a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty">查看</a></span>
							</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:forEach>
		
	</table>
	<ul class="list02 w50">
		<li><label>申请单位：</label>${mapping:mappingDepartmentNameByAccount(application.account) }</li>
		<li><label>责任领导：</label>${application.tCase.leader }</li>
		<li class="w100"><label>申请人：</label>${mapping:mappingAccountNameById(application.account)}</li>
		<li><label>案件编号：</label>${application.tCase.jzcaseID }</li>
		<li><label>案件名称：</label>${application.tCase.caseName }</li>
		<li><label>申请类型：</label>${mapping:mappingApplicationType(application.procedureCode) }</li>
		<li><label>下个审批人：</label><span class="nextHandlers"></span></li>
		<li class="w100"><strong>申请原因：</strong>${application.applyBasis}</li>
	
	</ul>
	
	<div class="toptit">
		<h1 class="h1tit">档案资料</h1>
	</div>
	<ul class="ws_list">
		<c:forEach var="attachment" items="${attachmentList }">
		<li><a href="javascript:downloadFile('${attachment.uploadUrl}')">${attachment.attaName }</a></li>
		</c:forEach>
	</ul>
	
	<div class="dbut">
	<a href="javascript:void(0)" class="goBackByQuery">返回</a>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(".goBackByQuery").click(function(){
			Common.tempForm.initData('${refererUrl}', '${conditions}');
		});
		
		$.ajax({
			url:"${pageContext.request.contextPath}/applicationOrder/queryNextHandlers.do",
			data:"applicationId=${application.id}",
			type:"post",
			success:function(backData){
				var handlers = backData.split(",");
				var str = "";
				$(handlers).each(function(){
					str = str + "${mapping:mappingAccountID("+this+")}";
				});
				if(str === ""){
					$(".nextHandlers").text("无");
				}else{
					$(".nextHandlers").text(str);
				}
			},
			error:function(){
				alert("查询审批人失败");
			}
			
		});
	});
	
	
	function showTable(className, mThis){
	 	var table = $(className);
	 	if(table.css("display") === 'none'){
	 		table.css("display", "");
	 		$(mThis).text("收起");
	 	}else{
	 		table.css("display", "none");
	 		$(mThis).text("展开");
	 	}
	}
</script>
</body>
</html>