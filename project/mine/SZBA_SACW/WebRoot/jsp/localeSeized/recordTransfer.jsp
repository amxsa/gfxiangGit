<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<%@ include file="../../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>笔录移交</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/table_hover.js"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="crumb">
	<span>当前位置：<a href="<%=path%>/main.jsp" target="main">首页 </a>>> 笔录移交列表</span>
</div>
<div class="content">
<form id="searchForm" action="javascript:void(0)" method="post">
	<div class="toptit">
		<h1 class="h1tit">按条件查询</h1>
	</div>
	<div class="search">
		<div class="sp">
			<p>
				<label>接收人</label>
				<input name="receiverName" id="receiverName" type="text" style="width:100px;"/>
			</p>
			<p>
				<label>笔录时间</label>
				<input name="startTime" id="startTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
				<i>至</i>
				<input name="endTime" id="endTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px;"/>
			</p>
			<p>
				<label>案件编号</label>
				<input name="jzcaseID" id="jzcaseID" type="text" style="width:130px;"/>
			</p>
			<p>
				<label>案件名称</label>
				<input name="caseName" id="caseName" type="text" style="width:120px;"/>
			</p>
		</div>
		<div class="sbut">
			<input name="" type="button" id="searchData" value="查询" />
		</div>
	</div>
	<div class="toptit">
	    <h1 class="h1tit">笔录移交列表</h1>
	</div>
	<table width="100%" cellpadding="0" cellspacing="1" border="0" id="table_hover" class="table" >
		<thead>
			<tr>
				<th width="20%">移交时间</th>
				<th width="20%">记录员</th>
				<th width="15%">接收人</th>
				<th width="15%">类型</th>
				<th width="10%">状态</th>
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
</form>
<div id="refuseInfoDiv" style="display:none;">
	<textarea rows="7" cols="40" name="rreason" id="rreason"></textarea>
</div>
<div id="lookInfoDiv" style="display:none;">
	<table class="table3" width="100%" id="xqTab" style="table-layout: fixed;">
		<tr>
			<td width="30%" style="text-align: right;">
				类型：
			</td>
			<td width="70%" name="type">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				记录员：
			</td>
			<td name="recorder">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				发起人：
			</td>
			<td name="transferee">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				接收人：
			</td>
			<td name="receiver">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				状态：
			</td>
			<td name="transferStatus">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				移交时间：
			</td>
			<td name="createTime">
			</td>
		</tr>
		<tr id="handleDateTr" style="display:none;">
			<td style="text-align: right;">
				处理时间：
			</td>
			<td name="handleDate">
			</td>
		</tr>
		<tr id="reasonTr" style="display:none;">
			<td style="text-align: right;">
				拒绝原因：
			</td>
			<td name="reason">
			</td>
		</tr>
	</table>
</div>
</div>
<script type="text/javascript">
var recordTransfer={
	
	bindEvent:function(){
		$('#searchData').on('click',function(){
			recordTransfer.search();
		});
	},
		
	search:function(){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/transfer/transferHistory.do';
		var params={
			'jzcaseID':$.trim($('#jzcaseID').val()),
			'caseName':$.trim($('#caseName').val()),
			'receiverName':$.trim($('#receiverName').val()),
			'startTime':$.trim($('#startTime').val()),
			'endTime':$.trim($('#endTime').val())
		}
		var cb=function(data){
			$('#table_hover tbody').empty();
			var tr='';
			if(data.state=='Y'){
				var records=data.transfers;
				if(records.length!=0){
					for(var i=0;i<records.length;i++){
						var obj=records[i];
						var createTime=obj.createDate.substring(0,4)
						+'-'+obj.createDate.substring(4,6)
						+'-'+obj.createDate.substring(6,8)
						+' '+obj.createDate.substring(8,10)
						+':'+obj.createDate.substring(10,12)
						+':'+obj.createDate.substring(12,14);

						tr=tr+'<tr><td>'+createTime+'</td>'
						+'<td>'+obj.recorder+'</td>'
						+'<td>'+obj.receiverName+'</td>'
						+'<td>'+recordTransfer.getType(obj.type)+'</td>' 
						+'<td>'+recordTransfer.getStatus(obj.transferStatus)+'</td>'
						+'<td>'
						+'<span class="tdbut">'
						+'<a href="javascript:recordTransfer.lookData(\''+createTime+'\',\''+obj.recorder+'\',\''+obj.transferID+'\',\''+obj.type+'\')">查看</a>'
						+'</span></td></tr>';
					}
				}else{
					tr='<tr><td colspan="6" style="color:red;">暂无移交记录</td></tr>';
				}
			}else{
				tr='<tr><td colspan="6" style="color:red;">请求错误，请重试</td></tr>';
			}
			$('#table_hover tbody').append(tr);
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	//查看
	lookData:function(createTime,recorder,recordID,type){
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/transfer/transferResult.do';
		var params={
			'transferID':recordID,
			'type':type
		}
		var cb=function(data){
			if(data.state=='Y'){
				var transfer=data.transfer;
				if(transfer.transferStatus=='YJJ'){
					$('#handleDateTr').css('display','');
					$('#reasonTr').css('display','');
				}
				if(transfer.transferStatus=='YJWC'){
					$('#handleDateTr').css('display','');
				}
				for(var i in transfer){
					var text=transfer[i];
					if(i=='transferStatus'){
						text=recordTransfer.getStatus(text);
					}else if(i=='handleDate'&&text!=null){
						text=text.substring(0,4)
						+'-'+text.substring(4,6)
						+'-'+text.substring(6,8)
						+' '+text.substring(8,10)
						+':'+text.substring(10,12)
						+':'+text.substring(12,14);
					}
					$('#xqTab td[name="'+i+'"]').html(text);
				}
			}
		}
		$('#xqTab td[name="createTime"]').html(createTime);
		$('#xqTab td[name="recorder"]').html(recorder);
		$('#xqTab td[name="type"]').html(recordTransfer.getType(type));
		clientAjaxPost(localUrl,clientUrl,params,cb);
		art.dialog({
			content:$('#lookInfoDiv').html(),
		    title: '查看',
		    width: 500,
		    top:100,
		    cancel: true
		});
	},
	
	getStatus:function(status){
		var ret='';
		if(status=='YJWC'){
			ret='移交完成';
		}else if(status=='YJZ'){
			ret='等待接收';
		}else if(status=='YJJ'){
			ret='拒绝';
		}
		return ret;
	},
	
	getType:function(type){
		var ret='';
		if(type==4){
			ret='提取笔录';
		}else if(type==5){
			ret='扣押笔录';
		}else if(type==6){
			ret='搜查笔录';
		}else if(type==7){
			ret='查封笔录';
		}else if(type==8){
			ret='检查笔录';
		}else if(type==12){
			ret='提取痕迹物证登记';
		}else if(type==13){
			ret='证据保全清单';
		}else if(type==23){
			ret='电子物证';
		}
		return ret;
	},
	
	init:function(){
		recordTransfer.bindEvent();
		recordTransfer.search();
	}
}
$(document).ready(function() {
	recordTransfer.init();
});
</script>
</body>
</html>