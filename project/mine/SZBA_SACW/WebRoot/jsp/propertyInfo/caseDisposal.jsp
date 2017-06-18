<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'caseDisposal.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=path%>/js/uploadify/uploadify.css"/>
	<script type="text/javascript" src="<%=path%>/js/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/upload.js"></script>
  </head>
  
  <body>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物处置案结登记</span></div>
<div class="content">
 
  <ul class="list02 w50" style="background:#F9FAFC;">
   <li><label>单位：</label>${police.deptName }</li>
  <li><label>申请单编号：</label>${application.applicationNo }</li>
 </ul>
 <ul class="list02 w33">
   <li><label>财物编号：</label>${pro.proNo }</li>
   <li><label>财物名称：</label>${pro.proName }</li>
   <li><label>查扣时间：</label><fmt:formatDate value='${detail.date }' pattern='yyyy-MM-dd HH:mm:ss'/></li>
   <li><label>办案民警：</label>${police.poliName }</li>
   <li><label>查扣地点：</label>${pro.proSeizurePlace }</li>
   <li><label>案件编号：</label>${tCase.jzcaseID }</li>
   <li><label>案件名称：</label>${tCase.caseName }</li>
   <li><label>责任领导：</label>${tCase.leader }</li>
   <li><label>车牌号码：</label>粤BCS55</li>
   <li><label>车型品牌：</label>别克</li>
   <li><label>车型颜色：</label>黑色</li>
   <li><label>发动机号：</label>2033004</li>
   <li><label>车架号：</label>lSSJ82N152890</li>
   <li><label>被查扣人：</label>${owner.civiName }</li>
   <li><label>联系电话：</label>${owner.civiPhone }</li>
   <li><label>查扣原因：</label>${pro.proSeizureBasis }</li>
   <li><label>其它信息：</label>${pro.proRemark }</li>
   <li><label>现存放位置：</label>中心库C1-203</li>
 </ul>
 
 <ul class="list02">
   <li><label>案结处置方式：</label>
   	<select name="disposal">
   		<option value="TH">退还</option>
   		<option value="XH">销毁</option>
   		<option value="PM">拍卖</option>
   	</select>
  <%-- <select name="dpResult">
   	<c:forEach items="${dpList }" var="dp">
   		<option value="{'disposal':{'code':'${dp.disposal.code }'},'procedure':{'code':'${dp.procedure.code }'}}">${dp.disposal.description }</option>
   		
   	</c:forEach>
   	</select>--%>
   </li>
   <li><label>处置方式说明：</label><textarea name="instruction" cols="" rows=""></textarea></li>
 </ul>
 
 <div class="toptit"><h1 class="h1tit">依据</h1></div>
 <form >
  <ul class="ws_list" id="attachmentList">
 </ul>
 <input type="hidden" name="uploadUrl" value=""/>
 <input type="hidden" name="attaName" value=""/>
 <input type="file" name="attachment" id="attachment" multiple="true"/>
 </form>
 <div class="dbut"><a href="javascript:void(0)" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:history.go(-1)">返回</a></div>
</div>
</body>

<script type="text/javascript">
var fileArray = [];
var filePathArray = [];
var fileCount = 0;


function sendData(){
	
	var disposal = $("select[name='disposal']").val();
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
				window.location.href=sysPath + "/property/queryForPage.do?viewName=applyInPropertyList";
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
</html>
