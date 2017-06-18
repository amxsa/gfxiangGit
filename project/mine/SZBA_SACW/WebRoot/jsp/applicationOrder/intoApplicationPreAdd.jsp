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
<script type="text/javascript" src="<%=path%>/js/tempForm.js"></script>
</head>
<body>
<c:if test="${fn:length(detail.excessPros) > 0}">
	<c:redirect url="/property/queryForPage.do?viewName=applyInPropertyList" />
</c:if>
<div class="crumb"><span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 财物入库申请</span></div>
<div class="content">
<div class="detal"><h2 class="h4tit">财物入库申请</h2></div>
<div class="toptit"><h1 class="h1tit">待入库财物</h1><h1 class="hrtit"><label>申请单编号：</label><span class="applicationNo"></span></h1></div>

 <form action="" id="">
 <table width="100%" cellpadding="0" cellspacing="1" border="0" class="table">
  <tr>
    <th>序号</th>
    <th>财物编号</th>
	<th>财物名称</th>
	<th>财物数量</th>
	<th>计量单位</th>
	<th>财物类别</th>
	<th>财物状态</th>    
	<th>保管方式</th>    
	<th>保管条件描述</th>    
	<th>操作</th>    
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
	<td>${mapping:mappingProStatus(p.proStatus) }</td>
	<td>
	<select name="saveMethod">
		${mapping:generateProSaveMethodSelect() }
	</select>
	</td>
	<td>
		<textarea name="enviDemand"></textarea>
	</td>
	<input type="hidden" value="${p.proStatus }" name="proStatus"/>
	<input type="hidden" value="${p.proType }" name="proType"/>
	<input type="hidden" value="${p.actualPosition }" name="actualPosition"/>
	<input type="hidden" value="${p.proId }" name="proId"/>
	<input type="hidden" value="${p.proName }" name="proName"/>
	<input type="hidden" value="${p.proNo }" name="proNo"/>
	<td>
	<span class="tdbut"><a href="<%=path%>/property/queryDetail.do?proId=${p.proId }&viewName=watchProperty" class="watchDetail">查看</a></span>
	<span class="tdbut"><a href="javascript:void(0)" class="removeElec">移除</a></span>
	</td>
  </tr>
  </c:forEach>
	<input type="hidden" value="" name="proJson"/>
 </table>
  <ul class="list02a w50">
  <input type="hidden" name="applicationNo"/>
   <li><label>申请单位：</label>${department.name }</li>
   <li> <label>责任领导：</label>${detail.tCase.leader }</li>
   <li class="w100"><label>申请人：</label>${account.name}</li>
   <li class="w50"><label>案件编号：</label>${detail.tCase.jzcaseID }</li><input type="hidden" name="jzcaseID" value="${detail.tCase.jzcaseID }"/><input type="hidden" name="caseId" value="${detail.tCase.caseID }"/>
   <li class="w50"><label>案件名称：</label>${detail.tCase.caseName }</li><input type="hidden" name="caseName" value="${detail.tCase.caseName }"/>
   <li class="w100"><label>待入仓库：</label>
   	<select name="dpResultStr">
   	
   		<%--<option value="{'disposal':{'code':'', 'description':'扣车场'},'procedure':{'code':'PROCEDURE001', 'description':'一般财物入暂存库流程'}}">扣车场</option>
   	--%>
   	</select>
   </li>
   
   <li><label>保存期限:</label>
	   <select name="saveDateType">
	   <option value="短期">短期</option>
	   <option value="长期">长期</option>
	   </select>
   </li>
   <li><label>保存到期日期：</label>
	  <input type="text" name="expirationDate" value="" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
   </li>
   <li class="w100"><label>计划入库日期：</label>
	  <input type="text" name="planStorageDate" value="" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
   </li>
   <li class="w100"><label>送货方式：</label>
	  <select name="deliverType">
	   <option value="自送">自送</option>
	   <option value="上门取货">上门取货</option>
	   </select>
   </li>
   
  <li class="w100" style="display:block;height:375px;">
  <label>申请理由依据：</label>
   <div style="width:85%;float:left;height: 100%;">
		<script id="container" name="applyBasis" type="text/plain"></script>
	</div>
	<div style="float:left;height: 100%;position: relative;padding-left: 5px;">
		<input type="button" value="模板" style="width:50px;position: absolute;bottom: 0px;" id="selTemp"/>
	</div>
	   	<%--<textarea rows="" cols="" name="applyBasis"></textarea>
   --%>
   </li>
</ul>

<div class="toptit">
	<h1 class="h1tit">档案资料</h1>
</div>
<ul class="ws_list" id="attachmentList">
</ul>
<div id="attachmentQuene"></div>
<input type="file" name="attachment" id="attachmentApplication" multiple="true" />

<div class="dbut"><a href="javascript:void(0)" onclick="sendData()" id="submitBtn">确定</a><a href="javascript:void(0)" class="goBackByQuery">返回</a></div>
</form>
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
		var path=sysPath+'/templet/queryTemplet.do?contentType=35';
		art.dialog.data('contentType',35);  //入库申请呈批表模版 35
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
	
	$(".goBackByQuery").click(function(){
		Common.tempForm.initData('${refererUrl}', '${conditions}');
	});

	$("select[name='saveDateType']").change(function(){
		if($(this).val() === '长期'){
			$(":text[name='expirationDate']").prop("disabled", true);
			$(":text[name='expirationDate']").parents("li");
		}else{
			$(":text[name='expirationDate']").prop("disabled", false);
		}
	});
	
	$("select[name='dpResultStr']").change(function(){
		var storage = $("select[name='dpResultStr'] option:selected").text();
		var d = new Date();
		if(storage === '分局中心库'){
			d.setDate(d.getDate()+7);
			$(":text[name='planStorageDate']").val(formatDate(d));
		}else{
			$(":text[name='planStorageDate']").val(formatDate(d));
		}
	});
	
	$.ajax({
		url: sysPath + "/applicationOrder/getApplicationNo.do",
		type:"post",
		success:function(backData){
			$(".applicationNo").text(backData);
			$("input[name='applicationNo']").val(backData);
		},
		error:function(){
			
		}
	});
	
	$.ajax({
		url: sysPath + "/storeroom/getStoreHouseByDept.do",
		type:"post",
		data:"deptId=${department.id}",
		dataType:"json",
		success:function(backData){
			
			$(backData).each(function(){
				var opt = $("<option/>");
				
				if(this.storehouseNum !== '1'){
					opt.attr("value", "{'disposal':{'code':'', 'description':'"+this.storehouseName+"'},'procedure':{'code':'PROCEDURE001', 'description':'一般财物入暂存库流程'}}");
				}else{
					opt.attr("value", "{'disposal':{'code':'', 'description':'"+this.storehouseName+"'},'procedure':{'code':'PROCEDURE008', 'description':'特殊财物入中心库流程'}}");
				}
				opt.text(this.storehouseName);
				$("select[name='dpResultStr']").append(opt);
			});
			
		},
		error:function(){
			
		}
	});
	
	var caseType = "${detail.tCase.caseType}";
	var newDate = new Date();
	//1为刑事案件，保存时间默认2个月
	//2 为行政案件，保存时间默认1个月，
	if(caseType === '1'){ 
		 newDate.setMonth(newDate.getMonth()+2);
	}else{
		 newDate.setMonth(newDate.getMonth()+1);
	}
	var dateStr = formatDate(newDate);
	$(":text[name='expirationDate']").val(dateStr);
	
	$(":text[name='planStorageDate']").val(formatDate(new Date()));
	
});



function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}
function sendData(){
	
	var proIds = "[";
	var proTr = $(".table tr");
	
	$(proTr).each(function(i){
		var proId = $(this).find("input[name='proId']").val();
		var proStatus = $(this).find("input[name='proStatus']").val();
		var proType = $(this).find("input[name='proType']").val();
		var proName = $(this).find("input[name='proName']").val();
		var actualPosition = $(this).find("input[name='actualPosition']").val();
		var saveMethod = $(this).find("select[name='saveMethod']").val();
		var enviDemand = $(this).find("textarea[name='enviDemand']").val();
		var proNo = $(this).find("input[name='proNo']").val();
		
		if(proId != null && proId != ''){
			proIds += "{'proId':'"+proId+"', 'proStatus':'"+proStatus+"', 'proType':'"+proType+"', 'actualPosition':'"+actualPosition+"', 'proName':'"+proName+"', 'saveMethod':'"+saveMethod+"', 'enviDemand':'"+enviDemand+"', 'proNo':'"+proNo+"'},"; 
		}
	});
	
	proIds = proIds.substring(0, proIds.length-1);
	proIds += "]";
	$("input[name='proJson']").val(proIds);
	
	disableBtn("#submitBtn");
	$.ajax({
		url:sysPath+"/applicationOrder/doApplyNew.do",
		data:$("form:first").serialize(),
		type:"post",
		dataType:"json",
		success:function(backData){
			if(backData.state == 'Y' || backData == null){
				alert(backData.msg);
				//window.location.href = sysPath + "/property/queryForPage.do?viewName=applyInPropertyList";
				//changeToPrintPage(backData.obj.applicationId);
				enableBtn("#submitBtn", "打印预览", 'changeToPrintPage('+backData.obj.applicationId+')', ue);
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
//);
</script>
</body>
</html>
