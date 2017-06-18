<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>

</head>
<body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物入中心库申请</span></div>
<div class="content">
 
  <ul class="list02 w50">
 		<li><label>案件编号：</label>${detail.tCase.jzcaseID }</li>
		<li><label>案件名称：</label>${detail.tCase.caseName }</li>
 </ul>
 <table width="98%" cellpadding="0" cellspacing="1" border="0" class="table">
  <tr>
    <th>序号</th>
    <th>财物编号</th>
    <th>电子标签</th>
	<th>财物名称</th>
	<th>财物数量</th>
	<th>计量单位</th>
	<th>当前仓位</th>
	<th>财物详情</th>
  </tr>
  <c:forEach items="${detail.properties }" var="p" varStatus="sta">
  <tr>
    <td>${sta.count }</td>
    <td>${p.proId }</td>
    <td>${p.proRfidNum }</td>
	<td>${p.proName }</td>
	<td>
		<c:if test="${p.proQuantity%1.0==0}">
			<fmt:formatNumber type="number" value="${p.proQuantity }" maxFractionDigits="0"/>
		</c:if>
		<c:if test="${p.proQuantity %1.0!=0}">${p.proQuantity }
		</c:if>
		
	</td>
	<td>${p.proUnit }</td>
	<td>${p.wareSerialNumber }</td>
	<td><span class="tdbut"><a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty">查看</a></span></td>
  </tr>
  </c:forEach>
 </table>
 <div class="toptit"><h1 class="h1tit">财物处置</h1></div>
 <ul class="list02">
   <li><label>处置方式：</label>
   	<select name="dpResult">
   		<%--<option value="{'disposal':{'code':'${dp.disposal.code }', 'description':'${dp.disposal.description }'},'procedure':{'code':'${dp.procedure.code }', 'description':'${dp.procedure.description }'}}">${dp.disposal.description }</option>--%>
   		<option value="{'disposal':{'code':'RZXK', 'description':'入中心库'},'procedure':{'code':'PROCEDURE002', 'description':'一般财物入中心库流程'}}">入中心库</option>
   		
   	</select>
   </li>
   <li><label>申请理由依据：</label><textarea name="applyBasis" cols="" rows=""></textarea></li>
 </ul>
 
 <div class="dbut"><a href="javascript:void(0)" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:history.go(-1)">返回</a></div>
</div>
<script type="text/javascript">


function sendData(){
	
	var dpResult = $("select[name='dpResult']").val();
	var applyBasis = $("textarea[name='applyBasis']").val();
	var proIds = "";
	var proTr = $(".table tr");
	$(proTr).each(function(){
		var proId = $(this).children("td").eq(1).text();
		if(proId != null && proId != ''){
			proIds += "{'proId':"+proId+"},";
		}
	});
	proIds = proIds.substring(0, proIds.length-1);
	//console.info("proIds="+proIds);
	
	var json = {
			'dpResult':eval("("+dpResult+")"),
			'applyBasis':applyBasis,
			'proIds':[eval("("+proIds+")")],
			'caseName':"${detail.tCase.caseName }"
	};
	//console.info("json="+eval('('+json+')'));
	disableBtn("#submitBtn");
	$.ajax({
		url:sysPath+"/applicationOrder/doApply.do",
		data:JSON.stringify(json),
		type:"post",
		contentType: "application/json; charset=utf-8",
		dataType:"json",
		success:function(backData){
			if(backData.state == 'Y' || backData == null){
				alert("申请已提交，请耐心等待审批");
				window.location.href=sysPath+"/warehouse/queryForPage.do?viewName=inventoryManager";
			}else if(backData.state == 'F'){
				alert(backData.msg);
				enableBtn("#submitBtn", "确定", 'sendData()');
			}else{
				alert("未知错误");
				enableBtn("#submitBtn", "确定", 'sendData()');
			}
		},
		error:function(){
			alert("提交失败");
		}
		
	});
}	


		
</script>
</body>
</html>
