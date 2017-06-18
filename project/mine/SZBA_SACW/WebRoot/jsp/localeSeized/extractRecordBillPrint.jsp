<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
String recordType=request.getParameter("recordType");  //笔录类型
String listID=request.getParameter("listID");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>清单打印预览</title>
<style type="text/css">
html,body {
	height: 100%;
}

body,div,ul,li,h1,h2,h3,input,button,textarea,p,th,td {
	margin: 0;
	padding: 0;
	font-family: Song;
	font-size: 15px;
	color: #000;
}

:focus {
	outline: 0;
}

input {
	vertical-align: middle;
}

ul,li {
	list-style: none;
}

em,i {
	font-style: normal;
}

.main {
	margin: 0 auto;
	padding: 1% 3%;
}

.title {
	width: 100%;
	font-size: 30px;
	height: 30px;
	font-weight: 500;
	text-align: center;
	margin-bottom: 16px;
}

.tablep {
	width: 100%;
	border-collapse: collapse;
	border: 2px solid #333;
}

.tablep tr {
	background: #fff;
}

.tablep th {
	padding: 5px 2px;
	border: 1px solid #000;
	color: #000;
	text-align: center;
	font-weight: 500;
}

.tablep td {
	padding: 5px 2px;
	border: 1px solid #000;
	text-align: center;
}

.tablep td.left {
	text-align: left;
}

.tablep td .inner_table {
	width: 100%;
	margin: 0px;
	border: none;
}

.tablep td .inner_table td {
	border: none;
	text-align: left;
	padding: 4px 2px;
}

#finger_print {
	border: 2px solid black;
	background-color: gray;
}

.img_main {
	font-family: Song;
}

.img_content {
	float: center;
	margin: 0 auto;
	text-align: center;
	width: 100%;
}

.input {
	position: relative
}

.word {
	position: absolute;
	line-height: 15px;
	left: 0px;
	top: 1px;
	z-index: 10;
	background: #fff
}

.footer {
	width: 100%;
	line-height: 40px;
	text-align: center;
}

.main p img {
	margin: 0 auto 20px;
	border: 2px solid #000;
}

.input {
	padding-bottom: 15px;
}

.input p {
	position: relative;
	margin-bottom: 8px;
}

.input p label {
	position: absolute;
	line-height: 25px;
	left: 0px;
	top: 1px;
	z-index: 10;
	background: #fff;
	color: #000;
}

.input p textarea {
	width: 100%;
	line-height: 24px;
	height: 24px;
	resize: none;
	overflow-y: hidden;
	border: none;
	background: url(../../images/underline_bg.gif) repeat;
	padding: 0 2px;
}

.lzy {
	width: 400px;
	margin: 0 auto 20px;
	text-align: right;
}

.lzy2 {
	width: 420px;
	margin: 0 auto 40px;
	text-align: center;
}
</style>
<script src="<%=basePath %>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>
<script language="javascript">
//笔录类型 4-提取笔录 5-扣押笔录 6-搜查笔录 7-查封笔录 8-检查笔录
var grecordType='<%=recordType%>';  
var listID='<%=listID%>';  
</script>
<body>
<div class="main" style="width:94%;">
	<h1 class="title"><label id="recordTxt">扣押</label>清单</h1>
	<!-- 用于提取笔录，扣押笔录 -->
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tablep" id="tableOne">
		<tr id="thTxtOne">
			<th width="7%">编号</th>
			<th width="20%">名称</th>
			<th width="10%">数量</th>
			<th width="35%">特征</th>
			<th width="23%">备注</th>
		</tr>
		
		<tr>
			<td colspan="5">
				<table class="inner_table">
					<tr>
						<td>财物、文件持有人：</td>
						<td>见证人：</td>
						<td>保管人：</td>
						<td>
							办案单位：（公章）
							<br/>
							办案人：
						</td>
					</tr>
					<tr>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!-- 用于查封笔录 -->
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tablep" id="tableTwo" style="display:none;">
		<tr id="thTxtTwo">
			<th width="7%">编号</th>
			<th width="18%">名称</th>
			<th width="7%">数量</th>
			<th width="28%">特征</th>
			<th width="14%">财物所在地</th>
			<th width="12%">登记机关</th>
			<th width="14%">备注</th>
		</tr>
		
		<tr>
			<td colspan="7">
				<table class="inner_table">
					<tr>
						<td>财物、文件持有人：</td>
						<td>见证人：</td>
						<td>保管人：</td>
						<td>
							办案单位：（公章）
							<br/>
							办案人：
						</td>
					</tr>
					<tr>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!-- 用于检查笔录 -->
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="tablep" id="tableThree" style="display:none;">
		<tr id="thTxtThree">
			<th width="7%">编号</th>
			<th width="20%">名称</th>
			<th width="10%">数量</th>
			<th width="35%">物品特征或者场所地址</th>
			<th width="23%">备注</th>
		</tr>
		
		<tr>
			<td colspan="5">
				<table class="inner_table">
					<tr>
						<td>财物、文件持有人：</td>
						<td>见证人：</td>
						<td>保管人：</td>
						<td>
							办案单位：（公章）
							<br/>
							办案人：
						</td>
					</tr>
					<tr>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
						<td>&emsp;&emsp;年&emsp;月&emsp;日</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<p class="footer">本清单一式三份，一份附卷，一份交财物、文件持有人，一份交证据保管人员。</p>
</div>	
<div class="dbut">
	<a href="javascript:void(0)" id="printBut">打印</a>
</div>

</body>
<script language="javascript" type="text/javascript"> 
//笔录类型 4-提取笔录 5-扣押笔录 6-搜查笔录 7-查封笔录 8-检查笔录
var extractRecordBillPrint={
	startEvent:function(){
		if(grecordType==4||grecordType==5||grecordType==6){
			$('#recordTxt').html('扣押');
			$('#tableOne').css('display','block');
			$('#tableTwo').css('display','none');
			$('#tableThree').css('display','none');
		}else if(grecordType==7){
			$('#recordTxt').html('查封');
			$('#tableOne').css('display','none');
			$('#tableTwo').css('display','block');
			$('#tableThree').css('display','none');
		}else if(grecordType==8){
			$('#recordTxt').html('证据保存');
			$('#tableOne').css('display','none');
			$('#tableTwo').css('display','none');
			$('#tableThree').css('display','block');
		}
    	
		var localUrl=sysPath+'/client/clientRequestMap.do';
		var clientUrl='/detailedList/queryDetails.do';
		var params={
			'listID':listID
		}
		var cb=function(data){
			if(data.state=='Y'){
				var bill=data.detailedList;
				//显示财物
				if(bill.properties!=null&&bill.properties.length>0){
					var tr='';
					if(grecordType==4||grecordType==5||grecordType==6){
						for(var i=0;i<bill.properties.length;i++){
							var ret=bill.properties[i];
							tr=tr+'<tr>'
							+'<td>'+(i+1)+'</td>'
					    	+'<td>'+ret.name+'</td>'
					    	+'<td>'+ret.quantity+'</td>'
					    	+'<td>'+ret.characteristic+'</td>'
					    	+'<td>'+ret.remark+'</td></tr>';
						}
						tr=tr+'<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>以下空白</td><td>&nbsp;</td></tr>';
						for(var ii=0;ii<5;ii++){
							tr=tr+'<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
						}
				    	$('#thTxtOne').after(tr);
					}else if(grecordType==7){
						for(var i=0;i<bill.properties.length;i++){
							var ret=bill.properties[i];
							tr=tr+'<tr>'
							+'<td>'+(i+1)+'</td>'
					    	+'<td>'+ret.name+'</td>'
					    	+'<td>'+ret.quantity+'</td>'
					    	+'<td>'+ret.characteristic+'</td>'
					    	+'<td>'+ret.place+'</td>'
					    	+'<td>'+ret.authority+'</td>'
					    	+'<td>'+ret.remark+'</td></tr>';
						}
						tr=tr+'<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>以下空白</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
						for(var ii=0;ii<5;ii++){
							tr=tr+'<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
						}
				    	$('#thTxtTwo').after(tr);
					}else if(grecordType==8){
						for(var i=0;i<bill.properties.length;i++){
							var ret=bill.properties[i];
							tr=tr+'<tr>'
							+'<td>'+(i+1)+'</td>'
					    	+'<td>'+ret.name+'</td>'
					    	+'<td>'+ret.quantity+'</td>'
					    	+'<td>'+ret.characteristic+'，'+ret.place+'</td>'
					    	+'<td>'+ret.remark+'</td></tr>';
						}
						tr=tr+'<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>以下空白</td><td>&nbsp;</td></tr>';
						for(var ii=0;ii<5;ii++){
							tr=tr+'<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
						}
				    	$('#thTxtThree').after(tr);
					}
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
		extractRecordBillPrint.startEvent();
		extractRecordBillPrint.bindEvent();
	}
}
$(document).ready(function(){
	extractRecordBillPrint.init();
});
</script> 
</html>