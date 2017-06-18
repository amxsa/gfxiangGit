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
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 申请单详情</span></div>
<div class="content">
	<div class="detal"><h2 class="h4tit">${mapping:generateApplicationTitle(application.procedureCode) }</h2></div>
	<div class="toptit"><h1 class="h1tit">财物列表</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo">${application.applicationNo }</span></h1></div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0"
		class="table" id="table_hover">
		<tr>
			<th>序号</th>
			<th>财物编号</th>
			<th>财物名称</th>
			<th>财物数量</th>
			<th>计量单位</th>
			<th>财物持有人</th>
			<th>财物类别</th>
			<c:if test="${application.procedureCode == 'PROCEDURE006' || application.procedureCode == 'PROCEDURE007'}">
			<th>损毁状态</th> 
			</c:if>
			<th>财物特征</th>
			<th>电子标签</th>
			<th>所属库位</th>
			<%--<th>库位</th>--%><th>操作</th>
		</tr>
		<c:forEach items="${propertyList }" var="p" varStatus="sta">
			<tr class="proTr">
				<td>${sta.count }</td>
				<td class="proId">${p.proNo}</td>
				<td>${p.proName }</td>
				<td><c:if test="${p.proQuantity%1.0==0}">
						<fmt:formatNumber type="number" value="${p.proQuantity }"
							maxFractionDigits="0" />
					</c:if> <c:if test="${p.proQuantity %1.0!=0}">${p.proQuantity }
					</c:if> 
				</td>
				<td>${p.proUnit }</td>
				<td>${p.civiName }</td>
				<td>${mapping:mappingProType(p.proType) }</td>
				<c:if test="${application.procedureCode == 'PROCEDURE006' || application.procedureCode == 'PROCEDURE007'}">
					<td><span class="tdbut"><a href="javascript:void(0)" class="showDamageStatus">${mapping:mappingDamageStatus(p.damageStatus) }</a></span></td>
					<input type="hidden" value="${p.damageStatus }" name="damageStatus"/>
					<input type="hidden" value="${p.damageReason }" name="damageReason"/>
				</c:if>
				<td>${p.proCharacteristic }</td>
				<td>${p.proRfidNum }</td>
				<td>${p.warehouseId }</td>
			
			<td><span class="tdbut"><a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty">查看</a></span></td>
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
		<c:if test="${application.procedureCode == 'PROCEDURE001' || application.procedureCode == 'PROCEDURE008'}">
			<li class="w100"><label>待入仓库：</label>
		   	${application.targetStorehouse }
		    </li>
		   
		   <li><label>保存期限：</label>
			   ${application.saveDateType }
		   </li>
		   <li><label>保存到期日期：</label>
			  <fmt:formatDate value="${application.expirationDate }" pattern="yyyy-MM-dd"/>
		   </li>
		   <li><label>计划入库时间：</label>
			  <fmt:formatDate value="${application.planStorageDate }" pattern="yyyy-MM-dd"/>
		   </li>
		   <li><label>送货方式：</label>
			  ${application.deliverType }
		   </li>
		</c:if>
		<c:if test="${application.procedureCode == 'PROCEDURE004' || application.procedureCode == 'PROCEDURE005' || application.procedureCode == 'PROCEDURE006' || application.procedureCode == 'PROCEDURE007'}">
			<li class="w100"><label>借调期限：</label>
			<span style="width:150px;float:left;margin-left:5px;"><fmt:formatDate value="${application.planStartDate }" pattern="yyyy-MM-dd"/></span><i style="float:left;margin-left:5px;">至</i>
			<span style="width:150px;float:left;margin-left:5px;"><fmt:formatDate value="${application.planStorageDate }" pattern="yyyy-MM-dd"/></span>
			<label >时长：</label><span style="float:left;margin-left:5px;">${application.duration}</span><i style="float:left">天</i>
		   </li>
		</c:if>
		
		<c:if test="${application.procedureCode == 'PROCEDURE002' }">
			<li class="w100"><label>接收单位：</label>
		   	${application.targetStorehouse }
		    </li>
		     <li class="w100"><label>计划移库时间：</label>
			  <fmt:formatDate value="${application.planStorageDate }" pattern="yyyy-MM-dd"/>
		   	</li>
		  	<li class="w100"><label>移库方式：</label>
			  ${application.deliverType }
		   	</li>
		</c:if>
		<c:if test="${application.procedureCode == 'PROCEDURE009' || application.procedureCode == 'PROCEDURE0010' || application.procedureCode == 'PROCEDURE011'}">
			<li class="w100"><label>牵头部门：</label>
		   	${mapping:mappingDepartmentNameById(application.leadDepartmentId) }
		    </li>
			<li class="w100"><label>协助部门：</label>
		   	${mapping:mappingDepartmentNameById(application.assistDepartmentId) }
		    </li>
		</c:if>
		<li class="w100" style="display:block;height:375px;overflow: auto;">
		<strong>申请原因：</strong>
			<div style="width:85%;float:left;height: 100%;">
				${application.applyBasis }
			</div>
		</li>
	</ul>
	
	<div class="toptit">
		<h1 class="h1tit">档案资料</h1>
	</div>
	<ul class="ws_list">
		<c:forEach var="attachment" items="${attachmentList }">
		<li><a href="javascript:void(0)" onclick="downloadFile('${attachment.uploadUrl}')">${attachment.attaName }</a></li>
		</c:forEach>
	</ul>
	
	<div class="toptit">
			<h1 class="h1tit">审批信息</h1>
	</div>
		<table width="100%" cellpadding="0" cellspacing="1" border="0"
			class="table">
			<tr>
				<th>处理人员</th>
				<th>处理时间</th>
				<th>处理意见</th>
			</tr>
			<c:forEach var="aRecord" items="${commentList}">
			<tr>
				<td>${mapping:mappingAccountID(aRecord.userId)}</td>
				<td><fmt:formatDate value='${aRecord.time}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${aRecord.message}</td>
			</tr>
			</c:forEach>
		</table>
	<div class="dbut">
	<a href="javascript:doGoBack()" class="goBack">返回</a>
	<a href="javascript:changeToPrintPage(${application.id })">打印预览</a>
	<a href="<%=path %>/applicationOrder/queryTrackByApplicationId.do?applicationId=${application.id}" id="processTrack">流程跟踪</a>
	</div>
</div>
<script type="text/javascript">
	
	$(document).ready(function(){
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
		
		$(".showDamageStatus").click(function(){
			var path=sysPath+'/jsp/damage/showDamageStatus.jsp';
			art.dialog.data('status',$(this).text());
			art.dialog.data('reason',$(this).parents("td").nextAll("input[name='damageReason']").val());
			art.dialog.open(path, {
			    title: '选择损毁状态',
			    width: 650,
			    height:350,
			    top:10,
			    cancel: true
			});
		});
		
	});
	
	function doGoBack(){
		Common.tempForm.initData('${refererUrl}', '${conditions}');
	}
</script>
</body>
</html>