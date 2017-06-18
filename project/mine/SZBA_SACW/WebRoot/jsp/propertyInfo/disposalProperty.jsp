<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/uploadify/uploadify.css"/>
<link type="text/css" href="${pageContext.request.contextPath}/css/zTreeStyle.css" rel="stylesheet">
<style type="text/css">
	.list02a .ztree li{
		clear:both;
	}

</style>


<script language="javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadify/jquery.uploadify.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/category.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
</head>
<body>
<div class="crumb"><span>当前位置：<a href="${pageContext.request.contextPath}/main.jsp" target="main">首页 </a>>> 案结登记编辑</span></div>
<div class="content">
<form action="${pageContext.request.contextPath}/property/update.do" method="post">
	
 <div class="toptit"><h1 class="h1tit">财物信息</h1></div>
 <ul class="list02a w50">
   <li><label>财物编号：</label>${pro.proNo }</li>
   <li><label>财物数量：</label><c:if test="${pro.proQuantity%1.0==0}"><fmt:formatNumber type="number" value="${pro.proQuantity }" maxFractionDigits="0"/></c:if><c:if test="${pro.proQuantity %1.0!=0}">${pro.proQuantity }</c:if></li>
   <li><label>财物名称：</label>${pro.proName }</li>
   <li><label>财物单位：</label>${pro.proUnit }</li>
   
   <li><label>财物类别：</label>
   		${mapping:mappingProType(pro.proType) }
   </li>
   <li><label>财物来源：</label>
   	${mapping:mappingProOrigin(pro.proOrigin) }
</li>
   <li><label>保存方式：</label>${pro.saveMethod }</li>
   <li><label>财物分类：</label>
  	<span class="categoryName"></span>
   	
   </li>
   <li class="w100 h50"><label>储存条件要求：</label>${pro.saveDemand }</li>
   <li class="w100 h50"><label>保管要求：</label>${pro.enviDemand }</li>
   <li class="w100 h50"><label>财物特征：</label>${pro.proCharacteristic }</textarea></li>
  
  
 </ul>
  <div class="toptit"><h1 class="h1tit">财物鉴定信息</h1></div>
   <ul class="list02a w50">
   <li class="w100 h50"><label>财物鉴定情况：</label>
   	${pro.appraiSituation }
   	</li>
   <li class="w100 h50"><label>财物鉴定结论：</label>
   	${pro.appraiResult }
   </li>
   </ul>
 
 <div class="toptit"><h1 class="h1tit">财物图片</h1></div>
 <ul class="cwpic" id="imgList">
 <%--
   <li><a href="#"><img src="<%=path%>/images/nopic.jpg" /></a><p>财物正面图</p></li>
   <li><a href="#"><img src="<%=path%>/images/nopic.jpg" /></a><p>财物侧面图</p></li>
   <li><a href="#"><img src="<%=path%>/images/add.jpg" /></a><p>添加图片</p></li>
   --%>
 </ul>
  <div class="toptit"><h1 class="h1tit">案结登记</h1></div>
  <ul class="list02 w50">
  	 <textarea rows="10" cols="100" name="instruction"></textarea>
  </ul>
 
 <div class="toptit"><h1 class="h1tit">财物持有人信息</h1></div>
 <ul class="list02 w50">
 	
   <li><label>姓名：</label>${owner.civiName==null?pro.proOwner:owner.civiName }</li>
   <li><label>身份证号：</label>${owner.idNum }</li>
   <li><label>住址：</label>${owner.civiAddress }</li>
   <li><label>联系电话：</label>${owner.civiPhone }</li>
 </ul>
 
 <div class="toptit"><h1 class="h1tit">财物扣押信息</h1></div>
 <ul class="list02 w50">
   <li><label>案件编号：</label>${tCase.jzcaseID }</li>
   <li><label>案件名称：</label>${tCase.caseName }</li>
   
   <c:choose>
 	<c:when test="${fn:length(polices) > 0}">
 		<c:forEach items="${polices }" var="police" varStatus="sta">
	 		<input type="hidden" name="poliId" value="${police.poliId }">
	 		<li><label>查扣民警${sta.count }：</label>${police.poliName }</li>
	 		<li><label>扣押单位：</label><span>${police.deptName }</span></li>
 		</c:forEach>
 	</c:when>
 	<c:otherwise>
 		<input type="hidden" name="poliId" value="">
	 	<li><label>查扣民警：</label></li>
	 	<li><label>扣押单位：</label><span></span></li>
 	</c:otherwise>
 </c:choose>
   
   <li><label>扣押时间：</label><fmt:formatDate value='${detail.date }' pattern='yyyy-MM-dd HH:mm:ss'/></li>
   <li><label>扣押地点：</label>${pro.proSeizurePlace }</li>
   <li><label>责任领导：</label>${tCase.leader }</li>
   <li><label>财物性质：</label>${pro.proNature }</li>
   <li class="w100"><label>扣押依据：</label>${pro.proSeizureBasis }</li>
   <li class="w100"><label>备注：</label>${pro.proRemark }</li>
 </ul>
 <div class="toptit"><h1 class="h1tit">文件上传</h1></div>
  <form >
  <ul class="ws_list" id="attachmentList">
 </ul>
  <input type="hidden" name="disposal"  value="AJ"/> <!-- 处理方式，默认写AJ(案结) -->
 <input type="hidden" name="uploadUrl" value=""/>
 <input type="hidden" name="attaName" value=""/>
 <input type="file" name="attachment" id="attachment" multiple="true"/>
 </form>

 <div class="dbut">
 <a href="#" onclick="sendData()" id="submitBtn">案结</a>
 <a href="#" onclick="javascript:history.go(-1);">返回</a>
 

 </div>
 </form>
 <%--弹窗start--%>
<div id="popDiv1" class="mydiv" style="display: none;width:400px;height:400px">
	<h1>详情浏览框</h1>
	<div class="mtree" style="width:350px;height:300px">
		<ul id="common-tree" class="fl_edit">
		
		</ul> 
	</div>
	<div class="dbut2"><a href="javascript:closeDiv(1)" >关闭</a></div>
</div>
<div id="bg1" class="bg" style="display: none;"></div>
<iframe id='popIframe1' class='popIframe' frameborder='0'></iframe>

<%--弹窗end--%>

</div>

<script type="text/javascript">	
var fileArray = [];
var filePathArray = [];
var fileCount = 0;


function sendData(){
	
	var disposal = $("input[name='disposal']").val();
	var instruction = $("textarea[name='instruction']").val();
	
	var attachment = null;
	if(fileArray.length > 0){
		attachment = "";
	}
	$(fileArray).each(function(i){
		attachment += "{'attaName':'"+fileArray[i]+"', 'uploadUrl':'"+filePathArray[i]+"'},";
	});
	if(attachment != null && attachment != '' && attachment.length>0){
		attachment = attachment.substring(0, attachment.length-1);
	}
	alert(fileArray+"========"+attachment);
	var json = {
			'proId':"${pro.proId}",
			'disposal':disposal,
			'instruction':instruction,
			'attachment':[eval("("+attachment+")")]
	};
	console.info("json="+JSON.stringify(json));
	
	disableBtn("#submitBtn");
	$.ajax({
		url:sysPath+"/property/caseDisposal.do",
		data:JSON.stringify(json),
		type:"post",
		contentType: "application/json; charset=utf-8",
		success:function(backData){
			if(backData.state === 'Y'){
				alert("处置申请已提交");
				window.location.href=sysPath + "/property/queryForPage.do?viewName=disposalProperty";
			}else if(backData.state === 'F'){
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


$(function() {
	
	//显示上传文件表单
	showFileTable("1");
	
	//上传附件
	$('#attachment').uploadify({
		'formData'     : {},
    	'fileTypeDesc' : 'Files',
        'sizeLimit': 5000000,//控制上传文件的大小，单位byte服务器默认只支持30MB(30000000)，修改服务器设置请查看相关资料
		'queueID':'queue',
		'swf'      : '${pageContext.request.contextPath}/js/uploadify/uploadify.swf',
		'uploader' : '${pageContext.request.contextPath}/upload/attachment.do',
		'buttonText' : '上传处置依据',
		'onUploadSuccess' : function(file, data, response) {
			var dataObj = JSON.parse(data);
			console.info(dataObj);
			if(dataObj){
				if(dataObj.state == "Y"){
					if(dataObj.object){
						pushFile(dataObj.object.fileName,dataObj.object.path, "1");
					}
				}else{
					alert(dataObj.msg);
				}
			}
		}
	});
});
</script>

</body>
</html>
