<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<%@ include file="../../common/client.jsp"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Map<String, String[]> params = (Map<String, String[]>) request.getAttribute("params");
String recordType=request.getParameter("recordType");  //笔录类型
String thumbnailUrl=request.getParameter("thumbnailUrl");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>笔录打印预览</title>
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
.addr {
	position: relative;
	margin: 0;
	padding: 0;
	line-height: 28px;
}

.addr h3 {
	position: absolute;
	line-height:35px;
	font-weight: lighter;
	left: 0px;
	top: -4px;
	z-index: 10;
	background: #fff;
	color: #000;
}

.addr p {
	width: 100%;
	resize: none;
	overflow-y: hidden;
	border-bottom: 1px solid #000;
	margin-bottom: 8px;
	background: url(../../images/underline_bg.jpg) repeat;
	word-break: break-all;
	line-height: 28px;
	border-bottom: none;
}
</style>
<script src="<%=basePath %>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>
<script language="javascript">
//笔录类型 4-提取笔录 5-扣押笔录 6-搜查笔录 7-查封笔录 8-检查笔录
var grecordType='<%=recordType%>';  
var thumbnailUrl='<%=thumbnailUrl%>';
</script>
<body>
	<div class="main">
		<h1 class="title">
			<label id="recordTxt">扣押</label>物品照片
		</h1>
		<div style="width:100%;text-align: center;">
			<div>
				<img src="<%=thumbnailUrl%>"/>
			</div>
			<div style="width: 200px;text-align: right;display: inline-block;height: 30px;line-height: 30px;">
				[捺指印]
			</div>
			<div style="height: 30px;line-height: 30px;">
				以上拍摄对象为在&nbsp;&nbsp;&nbsp;&nbsp;（地方）扣押的&nbsp;&nbsp;&nbsp;&nbsp;（财物名称）
			</div>
		</div>
		<div class="addr">
			<h3>拍摄人：</h3>
			<p style="text-indent: 3em">&nbsp;</p>
		</div>
		<div class="addr">
			<h3>拍摄时间：</h3>
			<p style="text-indent: 3em">&nbsp;</p>
		</div>
		<div class="addr">
			<h3>拍摄地点：</h3>
			<p style="text-indent: 3em">&nbsp;</p>
		</div>
		<div class="addr">
			<h3>物品持有人确认（签名）：</h3>
			<p style="text-indent: 3em;text-align: center;">[捺指印]</p>
		</div>
	</div>
	<div class="dbut">
		<a href="javascript:void(0)" id="printBut">打印</a>
	</div>
</body>
<script language="javascript" type="text/javascript"> 
$(document).ready(function(){
	//打印
	$("#printBut").click(function(){
		$(".dbut").remove();
		window.print();
	});
});
</script> 
</html>