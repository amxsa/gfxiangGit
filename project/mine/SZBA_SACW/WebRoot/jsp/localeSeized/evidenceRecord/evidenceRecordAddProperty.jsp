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
<title>物证痕迹登记-添加财物</title>
<link type="text/css" href="<%=path %>/css/main.css" rel="stylesheet" />
<link type="text/css" href="<%=path%>/css/zTreeStyle.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=path%>/js/uploadify/uploadify.css" />
<script src="<%=path%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript" src="<%=path%>/js/common.js"></script>
<script language="javascript" src="<%=path%>/js/department.js"></script>
<script language="javascript" src="<%=path%>/js/jquery.ztree.core-3.5.js"></script>
<!-- 时间控件 -->
<script language="javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/js/uploadify/jquery.uploadify.min.js"></script>
</head>
<body style="overflow-x: hidden;">
<div class="content">
	<table class="table3" width="100%" id="cwTab" style="table-layout: fixed;">
		<tr>
			<td width="11%" style="text-align: right;">
				<span class="need">*</span>财物名称：
			</td>
			<td width="39%">
				<input name="name" type="text" need="need" errormsg="财物名称"/>
				<span></span>
			</td>
			<td width="11%" style="text-align: right;">
				财物数量：
			</td>
			<td width="39%">
				<input name="quantity" type="text" need="need"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				单位：
			</td>
			<td>
				<input name="unit" type="text"/>
			</td>
			<td style="text-align: right;">
				财物特征：
			</td>
			<td>
				<input name="characteristic" type="text"/>
			</td>
		</tr>
		<c:if test="${param.recordType == '12' }">
		<tr>
			<td style="text-align: right;">
				提取部位：
			</td>
			<td>
				<input name="position" type="text"/>
			</td>
			<td style="text-align: right;">
				提取方法：
			</td>
			<td>
				<input name="method" type="text"/>
			</td>
		</tr>
		</c:if>
		<tr>
			<td style="text-align: right;">
				备注：
			</td>
			<td colspan="3">
				<textarea name="remark" cols="30" rows="3" style="width:480px;"></textarea>
			</td>
		</tr>
	</table>
	<div class="toptit" style="margin:0;">
		<h1 class="h1tit">财物图片</h1>
	</div>
	<ul class="list02a w50" style="margin-bottom:2px;">		
		<li class="w100" style="padding-bottom:0px;">
			<ul class="cwpic" id="imgList" style="border:none;margin-bottom:0px;padding-bottom: 0px;padding-top: 0px;">
				
			</ul>
			<input type="button" value="添加" style="width:50px;margin-left: 11px;margin: 10px;" id="addPicturePic">
		</li>
	</ul>
	
	<!-- 添加图片输入框 -->
<div id="uploadPicDiv" style="display:none;">
	<ul class="list02a w50" id="uploadPicUl" style="width:600px;background:#ffffff;font: 12px/1.11 'Microsoft Yahei', Tahoma, Arial, Helvetica, STHeiti;">
		<li class="w100">
			<label>拍照时间：</label>
			<input type="text" name="captureTime" style="width:200px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</li>
		<li class="w100">
			<label>拍摄地点：</label>
			<input type="text" name="capturePlace" style="width:300px;"/>
		</li>
		<li class="w100" style="height:60px;">
			<label>图片描述：</label>
			<textarea rows="3" cols="3" name="picDescription"></textarea>
		</li>
		<li class="w100" style="padding-left: 30px;height:none;">
			<input type="file" name="image" id="imageInfo" multiple="true" />
			<div id="showPicDiv"></div>
		</li>
		<input name="picId" type="hidden"/>
		<input name="originalUrl" type="hidden"/>
		<input name="width" type="hidden"/>
		<input name="height" type="hidden"/>
	</ul>
</div>
</div>
<script language="javascript" src="<%=path%>/jsp/localeSeized/evidenceRecord/evidenceRecordAddProperty.js"></script>
</body>
</html>