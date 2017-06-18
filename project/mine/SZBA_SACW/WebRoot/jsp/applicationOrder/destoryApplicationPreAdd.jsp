<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>首页</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/uploadify/uploadify.css"/>
<script type="text/javascript" src="<%=path%>/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload.js"></script>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body style="overflow-x: hidden;">
<c:if test="${fn:length(detail.excessPros) > 0}">
	<c:redirect url="/property/queryForPage.do?viewName=applyDestoryPropertyList" />
</c:if>
	<div class="crumb">
		<span>
			当前位置：
			<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物销毁申请
		</span>
	</div>
	<div class="content">
	<div class="detal"><h2 class="h4tit">财物销毁申请</h2></div>
 		<div class="toptit"><h1 class="h1tit">财物列表</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo"></span></h1></div>
 			<form action="" id="">
 
				<table width="100%" cellpadding="0" cellspacing="1" border="0" class="table">
					<tr>
						<th>序号</th>
						<th>财物编号</th>
						<th>财物名称</th>
						<th>财物数量</th>
						<th>计量单位</th>
						<th>财物类别</th>
						<th>财物特征</th>    
						<th>财物详情</th>    
					</tr>
					<c:forEach items="${detail.properties }" var="p" varStatus="sta">
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
						<td>${p.proCharacteristic}</td>
						<input type="hidden" value="${p.proId }" name="proId"/>
						<input type="hidden" value="${p.proNo }" name="proNo"/>
						<input type="hidden" value="${p.proStatus }" name="proStatus"/>
						<input type="hidden" value="${p.proType }" name="proType"/>
						<input type="hidden" value="${p.actualPosition }" name="actualPosition"/>
						<td>
							<span class="tdbut">
								<a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty" class="watchDetail">查看</a>
							</span>
						</td>
					</tr>
					</c:forEach>
					<input type="hidden" value="" name="proJson" id="proJson"/>
				</table>
  				<ul class="list02a w50">
  				<input type="hidden" name="applicationNo"/>
				<li>
					<label>申请单位：</label>${department.name }
				</li>
  				<li> 
					<label>责任领导：</label>${detail.tCase.leader }
				</li>
				<li class="w100">
					<label>申请人：</label>${account.name}
				</li>
				<li class="w50">
					<label>案件编号：</label>${detail.tCase.jzcaseID }
					<input type="hidden" name="jzcaseID" value="${detail.tCase.jzcaseID }"/>
					<input type="hidden" name="caseId" value="${detail.tCase.caseID }"/>
				</li>
				<li class="w50">
					<label>案件名称：</label>${detail.tCase.caseName }
					<input type="hidden" name="caseName" value="${detail.tCase.caseName }"/>
				</li>
				<li class="w100">
					<label>牵头部门：</label>
					<input name="leadDepartmentId" type="hidden"/>
					<input name="leadDepartmentName" type="text" disabled="disabled"/>
					<input type="button" value="选择" style="width:50px;" class="selectDepartment"/>
				</li>
				<li class="w100">
					<label>协助部门：</label>
					<input name="assistDepartmentId" type="hidden"/>
					<input name="assistDepartmentName" type="text" disabled="disabled"/>
					<input type="button" value="选择" style="width:50px;" class="selectDepartment"/>
				</li>
   
   				<li class="w100" style="display:block;height:375px;">
   					<label style="display:block;">销毁依据：</label>
   					<div style="width:85%;float:left;height: 100%;">
	   					<script id="container" name="applyBasis" type="text/plain">
    					</script>
    				</div>
    				<div style="float:left;height: 100%;position: relative;padding-left: 5px;">
    					<input type="button" value="模板" style="width:50px;position: absolute;bottom: 0px;" id="selTemp"/>
    				</div>
	   				<!-- <textarea rows="" cols="" name="applyBasis"></textarea> -->
	   			</li>
			</ul>
			<input type="hidden" name="dpResultStr" value="" id="dpResultStr"/>
			<div class="toptit">
				<h1 class="h1tit">档案资料</h1>
			</div>
			<ul class="ws_list" id="attachmentList">
			</ul>
			<div id="attachmentQuene"></div>
			<input type="file" name="attachment" id="attachmentApplication" multiple="true" />
			
 			<div class="dbut">
 				<a href="javascript:void(0)" onclick="sendData()" id="submitBtn">确定</a>
 				<a href="javascript:history.go(-1)">返回</a>
 			</div>
 			</form>
	</div>
	<div id="roleIdList">
		<c:forEach var="aRecord" items="${rolesInfo}" varStatus="sta">
			<input name="roleId" type="hidden" value="${aRecord.ID}"/>
	  	</c:forEach>	
  	</div>
<script type="text/javascript">
var ue;
$(document).ready(function(){
	 ue = UE.getEditor('container',{
		toolbars: [ ['fontfamily', 'fontsize','paragraph','forecolor','backcolor','emotion',
		             'spechars','justifyleft','justifyright','justifycenter','justifyjustify',
		             'insertorderedlist','insertunorderedlist','rowspacingtop','rowspacingbottom', 'imageleft','imageright',
		             'lineheight','touppercase','tolowercase']],
		autoHeightEnabled:false,
		initialFrameHeight:270
	});
	
	//选择模板
	$('#selTemp').on('click',function(){
		var path=sysPath+'/templet/queryTemplet.do?contentType=34';
		art.dialog.data('contentType',34);  //销毁模板 34
		art.dialog.open(path, {
		    title: '选择模板',
		    width: 650,
		    height:350,
		    top:10,
		    ok: function () {
		    	var iframe = this.iframe.contentWindow;
		    	var table_hover = iframe.document.getElementById('table_hover');
		    	var checkedLen=$(table_hover).find('input[name="id"]:checked').length;
		    	if(checkedLen==0) { 
		    		alert('至少选择一个模板');
		    		return false;
		    	}
		    	var checkId=$(table_hover).find('input[name="id"]:checked').val();
		    	$.ajax({ 
		    		url:sysPath+'/templet/queryById.do?id='+checkId,
		    		async : false,
		    		type:"post", 
		    		dataType:"json",
		    		success:function(data){
		    			if(data.state=='S'){
		    				ue.setContent(data.obj.content);
		    			}
		    		}
		    	});
		    },
		    cancel: true
		});
	});
	 
	//选择部门
	$('.selectDepartment').on('click',function(){
		var thisObj=$(this);
		var path=sysPath+'/jsp/plugins/selectDepartment.jsp';
		art.dialog.open(path, {
		    title: '选择部门',
		    width: 400,
		    height:300,
		    top:10,
		    // 在open()方法中，init会等待iframe加载完毕后执行
		    init: function () {
		    	var iframe = this.iframe.contentWindow;
		    	$(iframe.document.getElementById('checked')).val(thisObj.prev().prev().val());
		    	this.iframe.contentWindow.initData();	
		    },
		    ok: function () {
		    	var retData=this.iframe.contentWindow.getCheckedData();	
		    	if(retData==null){
		    		return false;
		    	}else{
		    		var selId=retData.id;
		    		var selName=retData.name;
		    		thisObj.prev().val(selName);
		    		thisObj.prev().prev().val(selId);
		    	}
		    },
		    cancel: true
		});
	});
	
	$.ajax({
		url: sysPath + "/applicationOrder/getApplicationNo.do",
		type:"post",
		success:function(backData){
			$(".applicationNo").text(backData);
			$(":hidden[name='applicationNo']").val(backData);
		},
		error:function(){
			
		}
	});
});
function sendData(){
	
	var proIds = "[";
	var proTr = $(".table tr");
	var proStatusIndex="";
	$(proTr).each(function(i){
		var proId = $(this).find("input[name='proId']").val();
		var proNo = $(this).find("input[name='proNo']").val();
		var proStatus = $(this).find("input[name='proStatus']").val();
		var proType = $(this).find("input[name='proType']").val();
		
		var actualPosition = $(this).find("input[name='actualPosition']").val();
		proStatusIndex=actualPosition;  //财物的实际位置，用于跑流程
		if(proId != null && proId != ''){
			proIds += "{'proId':'"+proId+"', 'proNo':'"+proNo+"','proStatus':'"+proStatus+"', 'proType':'"+proType+"', 'actualPosition':'"+actualPosition+"'},";  //此处将财物状态带上，是为了在财物调用和处置流程时作为一个判断标识
		}
	});
	proIds = proIds.substring(0, proIds.length-1);
	proIds += "]";
	$("#proJson").val(proIds);
	
	var role="BAMJ";
	$('#roleIdList input[name="roleId"]').each(function(idx,el){
		if($(el).val()==5){
			role="ZAKMJ";
		}
	});
	var dpResultStr = "";
	if(role=="BAMJ"){
		if(proStatusIndex=="ZCK"){
			//财物在暂存库
			dpResultStr = "{'disposal':{'code':'', 'description':''},'procedure':{'code':'PROCEDURE009', 'description':'办案民警集中销毁（暂存库）'}}";
		}else if(proStatusIndex=="ZXK"){
			//财物在中心库
			dpResultStr = "{'disposal':{'code':'', 'description':''},'procedure':{'code':'PROCEDURE010', 'description':'办案民警集中销毁（中心库）'}}";
		}
		
	}else if(role=="ZAKMJ"){
		//治安科办理的销毁财物都在中心库中
		dpResultStr = "{'disposal':{'code':'', 'description':''},'procedure':{'code':'PROCEDURE011', 'description':'治安科民警集中销毁（中心库）'}}";
	}
	
	$("#dpResultStr").val(dpResultStr);
	
	disableBtn("#submitBtn");
	$.ajax({
		url:sysPath+"/applicationOrder/doApplyNew.do",
		data:$("form:first").serialize(),
		type:"post",
		dataType:"json",
		success:function(backData){
			if(backData.state == 'Y' || backData == null){
				alert(backData.msg);
				enableBtn("#submitBtn", "打印预览", 'changeToPrintPage('+backData.obj.applicationId+')', ue);
				//window.location.href = sysPath + "/property/queryForPage.do?viewName=applyDestoryPropertyList";
			}else if(backData.state == 'F'){
				alert(backData.msg);
				var proList = backData.obj;
				$(proTr).each(function(i){
					var proId = $(this).find("input[name='proId']").val();
					for(var j = 0; j < proList.length; j++){
						if(proId == proList[j].proId){
							$(this).addClass("error");
						};
					}
				});
				enableBtn("#submitBtn", "确定", 'sendData()');
			}else{
				alert("未知错误");
				enableBtn("#submitBtn", "确定", 'sendData()');
			}
		},
		error:function(){
			alert("提交失败");
			enableBtn("#submitBtn", "确定", 'sendData()');
		}
		
	});
}
</script>
</body>
</html>
