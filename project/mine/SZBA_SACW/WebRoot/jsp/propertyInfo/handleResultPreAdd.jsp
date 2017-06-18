<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />

<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>处置登记</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/uploadify/uploadify.css" />

<script type="text/javascript" src="<%=path%>/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload.js"></script>
</head>
<body>
	<div class="crumb">
		<span>
			当前位置：
			<a href="<%=path%>/property/queryForPage.do?viewName=registerProperty" target="main">首页 </a>>>处置登记
		</span>
	</div>
	<div class="content">
		<form action="" method="post" id="submitForm">
			<table cellpadding="0" cellspacing="1" border="0" class="table">
			  <tr>
			    <th>序号</th>
			    <th>财物编号</th>
				<th>财物名称</th>
				<th>财物数量</th>
				<th>计量单位</th>
				<th>财物类别</th>
				<th>财物特征</th>    
				<th>备注</th>    
				<th>财物详情</th>    
			  </tr>
			  <c:forEach items="${propertyList }" var="p" varStatus="sta">
			  <tr>
			    <td>${sta.count }</td>
			    <td>${p.proNo }</td>
				<td>${p.proName }</td>
				<td>
					<c:if test="${p.proQuantity%1.0==0}">
						<fmt:formatNumber type="number" value="${p.proQuantity }" maxFractionDigits="0"/>
					</c:if>
					<c:if test="${p.proQuantity %1.0!=0}">${p.proQuantity }
					</c:if>
					
				</td>
				<td>${p.proUnit }</td>
				<td>${mapping:mappingProType(p.proType) }</td>
				<td>${p.proCharacteristic }</td>
				<td>
					${p.proRemark}
				</td>
				<input type="hidden" value="${p.proId }" name="propertyId"/>
				<td>
				<span class="tdbut"><a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty">查看</a></span>
				</td>
			  </tr>
			  </c:forEach>
			 </table>
			<div class="toptit">
				<h1 class="h1tit">处置结果</h1>
			</div>
			<ul class="list02a">

				<li class="w100"><label>处置结果：</label>
					<c:choose>
						<c:when test="${result.status != 'Y' }">
							<textarea name="handleResult" cols="" >
							</textarea>
						</c:when>
						<c:otherwise>
						${result.handleResult }
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
			
			<div class="toptit">
				<h1 class="h1tit">档案资料</h1>
			</div>

			<ul class="ws_list" id="attachmentList">
				<c:forEach items="${attachmentList }" var="att">
					<li><a href="javascript:void(0)" onclick="downloadFile('${att.uploadUrl}')">${att.attaName }</a></li>
				</c:forEach>
			</ul>
			<c:if test="${result.status != 'Y' }">
				<div id="attachmentQuene"></div>
				<input type="file" name="attachment" id="attachmentHandleResult" multiple="true" />
			</c:if>
				<div class="dbut">
					<c:if test="${result.status != 'Y'}">
						<input type="button" value="添加" id="submit"/>
					</c:if>
					<input type="button" value="返回" id="goBack"/>
				</div>
			
		</form>
	</div>
</body>
<script type="text/javascript">
var submitFun = function(){
	$("#submit").unbind("click");
	$.ajax({
		url:"<%=path%>/property/addHandleResult.do",
		data:$("#submitForm").serialize(),
		type:"post",
		success:function(backData){
			if(backData === 'Y'){
				alert("登记成功");
				window.location.href=sysPath + "/property/queryForPage.do?viewName=handleResultPropertyList";
			}else{
				alert("登记失败");
				$("#submit").bind("click", submitFun);
			}
		},
		error:function(){
			alert("登记失败");
			$("#submit").bind("click", submitFun);
		}
	});
};
$(document).ready(function(){
	
	$("#submit").bind("click", submitFun);
	$("#goBack").click(function(){
		window.location.href=sysPath + "/property/queryForPage.do?viewName=handleResultPropertyList";
	});

});
</script>
</html>
