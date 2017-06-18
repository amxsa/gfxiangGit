<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="cn.cellcom.szba.common.Env"%>
<%@ taglib prefix="mapping" uri="http://SZBA/el/mapping"%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path",request.getContextPath());
	String photoUrl = Env.ENV_PRO.getProperty("photo_base_url");
	request.setAttribute("photoUrl", photoUrl);
	String fileUrl = Env.ENV_PRO.getProperty("file_base_url");
	request.setAttribute("fileUrl", fileUrl);
	
%>
<link type="text/css" href="<%=request.getContextPath() %>/css/main.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<!-- 时间控件 -->
<!-- 公用js方法类 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/load/js/load.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonUtil.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<!-- 输入验证 -->
<link href="<%=request.getContextPath()%>/css/validform.css" type="text/css" rel="Stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Validform/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<!-- artDialog弹出窗口 -->
<script src="<%=request.getContextPath()%>/js/artDialog/artDialog.js?skin=opera" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/artDialog/plugins/iframeTools.js" type="text/javascript"></script>

<!-- ueditor 编辑器 -->
<script src="<%=request.getContextPath()%>/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/ueditor/ueditor.all.js" type="text/javascript"></script>
<!--[if lte IE 8]> 
<script src="${pageContext.request.contextPath }/js/compatibleJs/IE8.js" type=”text/javascript”></script>
<![endif]-->


<script type="text/javascript">

//根路径
var sysPath = "${path}";
var photoUrl = "${photoUrl}";
var fileUrl = "${fileUrl}";

function showDiv(num){
	document.getElementById('popDiv'+num).style.display='block';
	document.getElementById('popIframe'+num).style.display='block';
	document.getElementById('bg'+num).style.display='block';
}

function closeDiv(num){
	document.getElementById('popDiv'+num).style.display='none';
	document.getElementById('bg'+num).style.display='none';
	document.getElementById('popIframe'+num).style.display='none';
}

function showImgDetail(mythis, num){
	$(".fl_edit").html("");
	var src = $(mythis).children("img").attr("src");
	var txt = $(mythis).text();
	$(".fl_edit").append('<li><img class="imgDetail" src="'+src+'" alt="" style="width:300px;height:240px"></li>');
	$(".fl_edit").append('<li><strong><label>图片描述：</label></strong><span class="imgDetailInfo">'+txt+'</span></li>');
	
	showDiv(num);
}

function showFileDetail(mythis,num){
	
	$(".fl_edit").html("");
	var txt = $(mythis).text();
	$(".fl_edit").append('<li><strong><label>文件名：</label></strong><span class="">'+txt+'</span></li>');
	$(".fl_edit").append('<li><strong><label>文件描述：</label></strong><span class=""></span></li>');
	
	showDiv(num);
}

function disableBtn(mythis, msg){
	$(mythis).text(msg);
	//$(mythis).removeProp("onclick");
	$(mythis).attr("onclick","");
	$(mythis).unbind("click");
	$(mythis).css("background","#d8d8d8");
	$(mythis).css("color","#222");
}

function enableBtn(mythis, msg, fntName, ue){
	if(ue != null){
		$("input:text").prop("disabled", true);
		$("#attachmentApplication").remove();
		$("textarea").prop("disabled", true);
		$("select").prop("disabled", true);
		$(".removeElec").remove();
		$(".watchDetail").remove();
		$(".selectDamageStatus").unbind("click");
		$(".selectDepartment").unbind("click");
		$("#selTemp").remove();
		ue.setDisabled('fullscreen');
	}
	$(mythis).text(msg);
	//$(mythis).prop("disabled", false);
	//$(mythis).one("click",fntName);
	$(mythis).attr("onclick","").click(function(){
		eval(fntName);
	});
	
	$(mythis).css("background","#1F6BB2");
	$(mythis).css("color","#fff");
}

function changeToPrintPage(applicationId){
	window.open(sysPath + "/applicationOrder/queryById.do?applicationId="+applicationId+"&isPrint=Y");
}
function showHandleDetail(applicationId){
	window.location.href = sysPath + "/applicationOrder/queryById.do?applicationId="+applicationId;
}


function doApply(viewName, taskId, applicationId, procInstId){
	$("form:first").attr("action", "<%=path%>/applicationOrder/queryForPreHandle.do?viewName="+viewName+"&taskId="+taskId+"&applicationId="+applicationId+"&procInstId="+procInstId );
	$("form:first").submit();
}

function queryApplyDetail(applicationId){
	$("form:first").attr("action", "<%=path %>/applicationOrder/queryById.do?applicationId="+ applicationId);
	$("form:first").submit();
}

function downloadFile(fileName){
	window.location.href=sysPath + "/down/downAttachment.do?fileName="+fileName;
}
function setCivilianData(tabId,liType, persons){
	if(persons!=null){
		if(persons.length>1){
			for(var i=1;i<persons.length;i++){
				var nli=evidenceRecordLook.getRelevantLi(liType);
				$(tabId).append(nli);
			}
		}
		var idx=1;
		for(var i=0;i<persons.length;i++){
			var sp=persons[i];
			$($(tabId+' tr').eq(idx)).find('td[name="name"]').html(sp.name);
			$($(tabId+' tr').eq(idx)).find('td[name="idType"]').html(evidenceRecordLook.getIdTypeById(sp.idType));
			idx=idx+1;
			$($(tabId+' tr').eq(idx)).find('td[name="gender"]').html(evidenceRecordLook.getGenderById(sp.gender));
			$($(tabId+' tr').eq(idx)).find('td[name="idNum"]').html(sp.idNum);
			idx=idx+1;
		}
	}
}
function setPoliceData(tabId,liType, persons){
	if(persons!=null){
		if(persons.length>1){
			for(var i=1;i<persons.length;i++){
				var nli=evidenceRecordLook.getRelevantLi(liType);
				$(tabId).append(nli);
			}
		}
		for(var i=0;i<persons.length;i++){
			var sp=persons[i];
			$($(tabId + ' tr').eq(i+1)).find('td[name="name"]').html(sp.name);
			$($(tabId+ ' tr').eq(i+1)).find('td[name="departmentName"]').html(evidenceRecordLook.updGetDeptNameById(sp.departmentID));
		}
	}
}
$(document).ready(function(){
	
	$(".batchApplyPreHandle").click(function(){
		var Application = function(){
				var applicationId = ""
				var taskId = "";
				var procInstId = "";
		};
		var app = null;
		var apps = [];
		var parentTrs = $(".checkBoxPreRow:checked").parents("tr");
		
		if(parentTrs.length <= 0){
			alert("请选择申请单");
			return;
		}
		parentTrs.each(function(){
			var applicationId = $(this).find("input[name='applicationId2']").val();
			var taskId = $(this).find("input[name='taskId2']").val();
			var procInstId = $(this).find("input[name='procInstId2']").val();
			
			app = new Application();
			app.applicationId = applicationId;
			app.taskId = taskId;
			app.procInstId = procInstId;
			apps.push(app);
		});
		
		var appJson = JSON.stringify(apps);
		var path = sysPath + "/jsp/applicationOrder/batchApplicationPreHandle.jsp"
		art.dialog.open(path, {
		    title: '批量审批',
		    width: 600,
		    top:10,
		    // 在open()方法中，init会等待iframe加载完毕后执行
		    init: function () {
		    	var iframe = this.iframe.contentWindow;
		    	var appInput = iframe.document.getElementById('applications');
		    	appInput.value = appJson;
		    },
		    button: [
             {
                 name: '审批',
                 callback: function () {
                		var iframe = this.iframe.contentWindow;
        		    	var submitForm = iframe.document.getElementById('submitForm');
        		    	
        		    	$.ajax({
        					url:"<%=path%>/applicationOrder/batchApproveHandle.do",
        					data:$(submitForm).serialize(),
        					type:"post",
        					dataType:"json",
        					success:function(backData){
        						if(backData.state === 'Y'){
        							alert("审批完成");
        							window.location.reload();
        							art.close();
        						}else{
        							alert(backData.msg);
        						}
        					},
        					error:function(){
        						alert("连接失败");
        					}
        				});
        		    	return false;
                 },
                 focus: true
             },
             {
                 name: '取消',
                 callback: function () {
                	window.location.reload();
                    return true;
                 }
             }
         ]
		});
	});
});
</script>