<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
String recordType=request.getParameter("recordType");  //笔录类型
String recordID=request.getParameter("recordID");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>笔录打印预览</title>
<link rel="stylesheet" href="<%=basePath %>/css/print.css" type="text/css"></link>
<style type="text/css">
html{
	padding:0% 0%;
}

</style>
<script src="<%=basePath %>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>
<script language="javascript">
//笔录类型 4-提取笔录 5-扣押笔录 6-搜查笔录 7-查封笔录 8-检查笔录
var grecordType='<%=recordType%>';  
var recordID='<%=recordID%>';
</script>
<body style="width:100%;">
	<div class="main" style="width:755px;margin:0 auto;text-align: left;padding-top:20px;">
		<h1 class="title">
			抓获过程
		</h1>
		<div style="width:100%;" id="content">
		</div>
		
		<div class="jcfooter" style="margin-top:20px;">
			<div style="width:100%;height:32px;line-height: 32px;text-align: right;">
				办案民警：&emsp;&emsp;&emsp;
			</div>
			<div style="width:100%;height:32px;line-height: 32px;text-align: right;">
				&emsp;&emsp;年&emsp;&emsp;月&emsp;&emsp;日
			</div>
		</div>
		<div class="dbut">
			<a href="javascript:void(0)" id="printBut">打印</a>
		</div>
	</div>
</body>
<script language="javascript" type="text/javascript"> 
var extractRecordCatchProcessPrint={
	startEvent:function(){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/catchProcess/detail.do';
		var params={
			'recordID':recordID
		}
		var cb=function(data){
			if(data.state=='Y'){
				var catchProcess=data.catchProcess;
				if(catchProcess!=null){
					$('#content').html(catchProcess.content);
				}
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	bindEvent:function(){
		//打印
		$("#printBut").click(function(){
    		$(".dbut").remove();
    		window.print();
    	});
	},
	
	init:function(){
		extractRecordCatchProcessPrint.startEvent();
		extractRecordCatchProcessPrint.bindEvent();
	}
}
$(document).ready(function(){
	extractRecordCatchProcessPrint.init();
});
</script> 
</html>