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
<title>痕迹登记表-查看财物</title>
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
				财物名称：
			</td>
			<td width="39%" name="name">
			</td>
			<td width="11%" style="text-align: right;">
				财物数量：
			</td>
			<td width="39%" name="quantity">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				单位：
			</td>
			<td name="unit">
			</td>
			<td style="text-align: right;">
				财物特征：
			</td>
			<td name="characteristic">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				提取部位：
			</td>
			<td name="position">
			</td>
			<td style="text-align: right;">
				提取方法：
			</td>
			<td name="method">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				备注：
			</td>
			<td colspan="3" name="remark">
			</td>
		</tr>
	</table>
	<div class="toptit">
		<h1 class="h1tit">财物图片</h1>
	</div>
	<ul class="list02a w50" style="margin-bottom:2px;">		
		<li class="w100" style="padding-bottom:0px;">
			<ul class="cwpic" id="imgList" style="border:none;margin-bottom:0px;padding-bottom: 0px;padding-top: 0px;">
				
			</ul>
		</li>
	</ul>
</div>
<script language="javascript" src="<%=path%>/jsp/localeSeized/evidenceRecord/evidenceRecordLookProperty.js"></script>
</body>
</html>