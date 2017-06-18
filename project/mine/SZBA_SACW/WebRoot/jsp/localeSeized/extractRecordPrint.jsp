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
.footer {
	width: 100%;
	text-align: left;
	margin-top: 15px;
}
.addr h3{
	line-height:35px;
	font-weight: lighter;
}
.addr p {
	background: url(../../images/underline_bg.jpg) repeat;
	word-break: break-all;
	line-height: 28px;
	border-bottom: none;
}
.btime {
    position: relative;
}
.btime label{
    position: absolute;
    left: 0;
    top: 0;
}
.btime p{
	margin-left: 43px;
}
.btime span{
    margin: 0 2px;
    border-bottom: 1px solid #000;
    overflow: hidden;
    zoom: 1;
}
.footer p {
	margin-bottom: 5px;
	overflow: hidden;
	zoom: 1;
}
.footer p span {
	padding-left: 50%;
}
.jcfooter{
	margin-top:20px;
}
.jcfooter p{
	width:48%;
	display: inline-block;
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
	<div class="main" style="width:755px;margin:0 auto;text-align: left;">
		<h1 class="title">
			<span id="recordTxt"></span>笔录
		</h1>
		<div class="btime">
			<label>时间</label>
			<p id="time">
				
			</p>
		</div>
		<div class="addr" style="margin-bottom:10px;">
			<h3>地点</h3>
			<p style="text-indent: 3em" id="place"></p>
		</div>
		<div class="addr">
			<h3 id="zcryTxt">侦查人员姓名、单位</h3>
			<p style="text-indent: 11em;" id="zcry"></p>
		</div>
		<div class="addr" id="jlryDiv" style="margin-bottom:10px;">
			<h3>记录人姓名、单位</h3>
			<p style="text-indent: 10em;" id="jlry"></p>
		</div>
		<div class="addr" style="margin-bottom:10px;">
			<h3 id="dsrTxt">当事人</h3>
			<p style="text-indent: 4em;" id="dsr"></p>
		</div>
		<div class="addr" style="margin-bottom:10px;">
			<h3 id="targetTxt">对象</h3>
			<p style="text-indent: 3em;" id="target"></p>
		</div>
		<div class="addr" style="margin-bottom:10px;">
			<h3 id="jzrTxt">见证人</h3>
			<p style="text-indent: 4em;" id="jzr"></p>
		</div>
		<div class="addr" id="qtzcryDiv" style="margin-bottom:10px;">
			<h3>其他在场人员</h3>
			<p style="text-indent: 7em;" id="qtzcry"></p>
		</div>
		<div class="addr" style="margin-bottom:10px;">
			<h3>事由和目的</h3>
			<p style="text-indent: 6em;" id="reason"></p>
		</div>
		<div class="addr">
			<h3>过程和结果：</h3>
			<p style="border-bottom: 1px solid #000;background: none;" id="result">
			</p>
		</div>
		<div class="footer">
			<p>
				<span>侦查人员：</span>
			</p>
			<p>
				<span>记录人：</span>
			</p>
			<p>
				<span>当事人：&emsp;&emsp;&emsp;&emsp;（捺指印）</span>
			</p>
			<p>
				<span>见证人：</span>
			</p>
			<p>
				<span>其他在场人员：</span>
			</p>
		</div>
		<div class="jcfooter" style="display:none;">
			<p style="width:100%;">
				<span style="display: inline-block;width:48%;">
					办案民警或者勘验、检查人：
				</span>
				<span style="display: inline-block;width:52%;text-align: right;">
					&emsp;&emsp;年&emsp;&emsp;月&emsp;&emsp;日
				</span>
			</p>
			<p style="width:100%;">
				<span style="display: inline-block;width:48%;">
					当事人、辨认人或者见证人：
				</span>
				<span style="display: inline-block;width:52%;text-align: right;">
					&emsp;&emsp;年&emsp;&emsp;月&emsp;&emsp;日
				</span>
			</p>
		</div>
		<div class="dbut">
			<a href="javascript:void(0)" id="printBut">打印</a>
		</div>
	</div>
<div id="selectDataDiv" style="display:none;">
	<select name="idType">
		<jsp:include page="/jsp/plugins/idType_options.jsp" />
	</select>
</div>
</body>
<script language="javascript" type="text/javascript"> 
var updDepartment=[];
var extractRecordPrint={
	startEvent:function(){
		if(grecordType==4){
			$('#recordTxt').html('提取');
		}else if(grecordType==5){
			$('#recordTxt').html('扣押');
		}else if(grecordType==6){
			$('#recordTxt').html('搜查');
		}else if(grecordType==7){
			$('#recordTxt').html('查封');
		}else if(grecordType==8){
			$('#recordTxt').html('检查');
			$('#zcryTxt').html('办案民警或者勘验、检查人姓名及工作单位');
			$('#jlryDiv').css('display','none');
			$('#dsrTxt').html('当事人基本情况');
			$('#targetTxt').html('检查或者辨认对象');
			$('#jzrTxt').html('见证人基本情况');
			$('#qtzcryDiv').css('display','none');
			
			$('.jcfooter').css('display','block');
			$('.footer').css('display','none');
		}
    	
    	//获取笔录信息
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/record/queryDetails.do';
		var params={
			'recordID':recordID
		}
		var cb=function(data){
			if(data.state=='Y'){
				var record=data.record;
				
				//时间
				var time='<span>'+record.startTime.substring(0,4)+'</span>年'
				+'<span>'+record.startTime.substring(4,6)+'</span>月'
				+'<span>'+record.startTime.substring(6,8)+'</span>日'
				+'<span>'+record.startTime.substring(8,10)+'</span>时'
				+'<span>'+record.startTime.substring(10,12)+'</span>分'
				+'至'
				+'<span>'+record.endTime.substring(0,4)+'</span>年'
				+'<span>'+record.endTime.substring(4,6)+'</span>月'
				+'<span>'+record.endTime.substring(6,8)+'</span>日'
				+'<span>'+record.endTime.substring(8,10)+'</span>时'
				+'<span>'+record.endTime.substring(10,12)+'</span>分';
				$('#time').html(time);
				//地点
				var place=(record.place==''||record.place==null)?'&nbsp':record.place;
				$('#place').html(place);
				//侦查员，多个
				var policesStr='';
				var polices=record.polices;
				if(polices!=null){
					for(var i=0;i<polices.length;i++){
						var sp=polices[i];
						policesStr=policesStr+sp.name+'，'+extractRecordPrint.getDeptNameById(sp.departmentID)+'，';
					}
					policesStr=policesStr.substring(0,policesStr.length-1);
				}
				$('#zcry').html(policesStr);
				//记录员，只有一个
				var recordersStr='';
				var recorders=record.recorders;
				if(recorders!=null&&recorders[0]!=null){
					recordersStr=recorders[0].name+'，'+extractRecordPrint.getDeptNameById(recorders[0].departmentID);
				}
				$('#jlry').html(recordersStr);
				//当事人，多个
				var clientsStr='';
				var clients=record.clients;
				if(clients!=null){
					for(var i=0;i<clients.length;i++){
						var sp=clients[i];
						clientsStr=clientsStr+sp.name+'，'+extractRecordPrint.getGenderById(sp.gender)+'，';
						clientsStr=clientsStr+extractRecordPrint.getIdTypeById(sp.idType)+'，'+sp.idNum+'，';
					}
					clientsStr=clientsStr.substring(0,clientsStr.length-1);
				}
				$('#dsr').html(clientsStr);
				//对象
				var target=(record.target==''||record.target==null)?'&nbsp':record.target;
				$('#target').html(target);
				//见证人，多个
				var witnessesStr='';
				var witnesses=record.witnesses;
				if(witnesses!=null){
					for(var i=0;i<witnesses.length;i++){
						var sp=witnesses[i];
						witnessesStr=witnessesStr+sp.name+'，'+extractRecordPrint.getGenderById(sp.gender)+'，';
						witnessesStr=witnessesStr+extractRecordPrint.getIdTypeById(sp.idType)+'，'+sp.idNum+'，';
					}
					witnessesStr=witnessesStr.substring(0,witnessesStr.length-1);
				}
				$('#jzr').html(witnessesStr);
				//其他在场人员，多个
				var personnelsStr='';
				var personnels=record.personnels;
				if(personnels!=null){
					for(var i=0;i<personnels.length;i++){
						var sp=personnels[i];
						personnelsStr=personnelsStr+sp.name+'，'+extractRecordPrint.getGenderById(sp.gender)+'，';
						personnelsStr=personnelsStr+extractRecordPrint.getIdTypeById(sp.idType)+'，'+sp.idNum+'，';
					}
					personnelsStr=personnelsStr.substring(0,witnessesStr.length-1);
				}
				personnelsStr=personnelsStr==''?'&nbsp':personnelsStr;
				$('#qtzcry').html(personnelsStr);
				
				var reason=(record.reason==''||record.reason==null)?'&nbsp':record.reason;
				$('#reason').html(reason);
				var result=(record.result==''||record.result==null)?'&nbsp':record.result;
				$('#result').html(result);
			}
		}
		clientAjaxPost(localUrl,clientUrl,params,cb);
	},
	
	bindEvent:function(){
		//返回
		$("#gobackBut").click(function(){
			window.location.href = sysPath + "/jsp/localeSeized/extractRecord.jsp?recordType="+grecordType;
    	});
		//打印
		$("#printBut").click(function(){
    		$(".dbut").remove();
    		window.print();
    	});
	},
	
	//返回性别
	getGenderById:function(gender){
		return gender=gender=='M'?'男':'女';
	},
	
	//返回证件类型
	getIdTypeById:function(gender){
		var ret=$('#selectDataDiv select[name="idType"] option[value="'+gender+'"]').text();
		return ret;
	},
	
	initLoadDepartment:function(){
		$.ajax({
			type: 'GET',
			async: false,
			url: sysPath+'/account/loadDepartments.do',
			dataType:"json",
			success: function(data){
				updDepartment=data.data;
			}
		});
	},
	
	getDeptNameById:function(deptId){
		for(var i=0;i<updDepartment.length;i++){
			if(updDepartment[i].id==deptId)
				return updDepartment[i].name;
		}
		return '';
	},
	
	init:function(){
		extractRecordPrint.startEvent();
		extractRecordPrint.bindEvent();
		extractRecordPrint.initLoadDepartment();
	}
}
$(document).ready(function(){
	extractRecordPrint.init();
});
</script> 
</html>